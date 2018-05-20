import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.Map
import scala.xml.{Node, XML}

// TODO: node internal text needs to be added to the Entity list

object prototype3 extends App {

  val key_name = "mdsid"

  // === Entity ========================================================================================================
  case class Entity(node: Node, parentNode: Option[Node], rootNode: Option[Node]) {

    def entityType: String = (if (rootNode.isEmpty) "" else rootNode.get.label + "_") + node.label

    def entityAttributes: Map[String,String] =
      node.attributes.asAttrMap ++ // node attributes
      //  Map("node_text" -> node.text) ++ // node text (between node tags)
      Map("node_text" -> "---TODO---") ++ // node text (between node tags)
      Map("parent_label" -> (if ( parentNode.isEmpty ) "N/A" else parentNode.get.label) ) ++ // add parent label to the map
      Map(s"parent_$key_name" -> (if ( parentNode.isEmpty ) "N/A" else parentNode.get.attributes.asAttrMap.getOrElse(key_name, "N/A"))) ++
      Map("root_label" -> (if ( rootNode.isEmpty ) "N/A" else rootNode.get.label) ) ++ // add root label to the map
      Map(s"root_$key_name" -> (if ( rootNode.isEmpty ) "N/A" else rootNode.get.attributes.asAttrMap.getOrElse(key_name, "N/A")))

    // note: oracle db implicitly converts digit-only strings to integer values. same may not be true for other databases.
    def insertSql: String = s"INSERT INTO $entityType (${entityAttributes.keys mkString(",")}) VALUES (${entityAttributes.values map (av => if (av == "N/A") "NULL" else s"'$av'") mkString(",")})"

    override def toString = s"\nEntity type: $entityType\nEntity attributes:\n$entityAttributes"
  }

  // === Entities ======================================================================================================
  case class Entities(entities: Map[String, List[Entity]]) {

    def addEntity(newEntity: Entity) = Entities(
      entities +
      (newEntity.entityType -> (
          newEntity :: (  if(entities.contains(newEntity.entityType)) entities(newEntity.entityType) else List() )
        )
      )
    )

    @scala.annotation.tailrec
    private def addEntitiesRec(entitiesToAdd: List[Entity], accu: Entities): Entities = entitiesToAdd match {
      case Nil => accu
      case currentEntity :: rest => addEntitiesRec(rest, accu.addEntity(currentEntity))
    }
    def addEntities(newEntities: List[Entity]): Entities = addEntitiesRec(newEntities, this)


    def ++(that: Entities): Entities = {
      val (largerEntities: Entities, smallerVals: List[Entity]) = // take the larger Map and merge the smaller one into it one Entity at a time
        if (this.entities.values.size < that.entities.values.size)  (that, this.entities.values.toList.flatten)
        else                                                        (this, that.entities.values.toList.flatten)

      addEntitiesRec(smallerVals, largerEntities)
    }

    //@scala.annotation.tailrec
    //private def addEntitiesRec(newEntities: List[Entity], curr: Entities): Entities = newEntities match {
    //  case Nil => curr
    //  case ent :: rest => addEntitiesRec(rest, curr.addEntity(ent))
    //}
    //def addEntities(newEntities: List[Entity]): Entities = addEntitiesRec(newEntities, this)

    private def mergeMaps(maps: List[Map[String,String]]): Map[String,List[String]] = {
      val merged = maps map (_.toSeq) reduce (_ ++ _) //  map1.toSeq ++ map2.toSeq
      // merged: Seq[(Int, Int)] = ArrayBuffer((1,2), (1,4))

      // group by key
      val grouped = merged.groupBy(_._1)
      // grouped: scala.collection.immutable.Map[Int,Seq[(Int, Int)]] = Map(1 -> ArrayBuffer((1,2), (1,4)))

      // remove key from value set and convert to list
      val cleaned = grouped.mapValues(_.map(_._2).toList)

      cleaned
    }

    def entityDDLs: String = {
      def roundUp10(i: Int): Int = i + (10 - (i % 10)) % 10

      def attributeSorter(pairs: ((String,String),(String,String))): Boolean = pairs match {
        case((attr1,_),(attr2,_)) =>
          if (attr1 == key_name) true
          else
          if (attr2.indexOf("root") == 0 && attr1.indexOf("root") != 0) true
          else
          if (attr2.indexOf("parent") == 0 && attr1.indexOf("parent") != 0 && attr1.indexOf("root") != 0) true
          else attr1 < attr2
      }

      val attributeValuesAnalysed: Map[String, Map[String, (Boolean, Int)]] = // Map[EntityName, Map[AttributeName, (isInteger, maxLength)]]
        entities map {
          case (entityType, entities) => (
            entityType,
            mergeMaps(entities map (_.entityAttributes)) map {case(attributeName, allAttributeValues) => (attributeName, (allAttributeValues forall (_.forall(c => c.isDigit)), allAttributeValues map (_.length) max ))} // for each attribute determine if it is integer and determine its max length
          )
        }

      val ddl = attributeValuesAnalysed map {
        case(entityName, attributes) => (entityName, attributes.map {
          case(attributeName, (isInteger, length)) => (attributeName, s"\t${attributeName} ${if (isInteger) "NUMBER(" + (length+2).toString + ",0)" else "VARCHAR(" + roundUp10(length).toString + ")"} NULL")
        }.toList sortWith ( (a,b) => attributeSorter((a,b)) )  map { case(_,sql) => sql } mkString(",\n") )
      } map {
        case(entityName, entityAttributesDDL) => s"CREATE TABLE ${entityName} (\n" + entityAttributesDDL + "\n);"
      } mkString("\n------------------------------------------\n")

      ddl
    }

    def entityInserts = {

      entities.keys.toList.sortWith(_<_) map {
        entityName =>
          s"-- $entityName ----------------------------------------\n" +
          (entities(entityName) map ("\t" + _.insertSql) mkString(",\n"))
      } mkString("\n\n")

    }

  }
  object Entities {
    val empty = Entities(Map())

    // TODO: !!!!!!!!!!!! REFACTOR !!! lame recursion!!!!! should be tail rec!!!
    private def entityListFromNode(current: Node, parent: Option[Node], root: Node): List[Entity] = {
      // no tailrec
      val childNodes = current.child.toList filter (_.label != "#PCDATA")

      Entity(current, parent, if (current == root) None else Some(root)) ::
        (if (childNodes.isEmpty) Nil else (childNodes map (entityListFromNode(_, Some(current), root)) reduce (_ ::: _)))
    }

    def fromRootNode(root: Node): Entities = {
      val entityList = entityListFromNode(root, None, root)
      //empty.addEntities(entityList)
      empty.addEntities(entityList)
    }
  }


  // ===================================================================================================================

  def writeFile2(filePath: String, content: String): Unit = {
    val file = new File(filePath)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(content)
    bw.close()
  }



  val logicalTableXml = XML.loadFile("C:\\Developer\\OBIEE\\RPD XML\\oracle\\bi\\server\\base\\LogicalTable\\00000f4c-9900-1567-b426-0af982470000.xml")
  val root: Node = logicalTableXml

  val entitiesFromRoot: Entities = Entities.fromRootNode(root)

  val ddls = entitiesFromRoot.entityDDLs
  writeFile2("testddl.sql", ddls)

  val inserts = entitiesFromRoot.entityInserts
  writeFile2("testinserts.sql", inserts)

}
