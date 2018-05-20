import java.io.{BufferedWriter, File, FileWriter}

import obiee.parse.common._

import scala.collection.Map
import scala.xml.{Node, XML}

// TODO: skip nodes where there are no attributes but do not skip nodes where there is text
// TODO: node internal text needs to be added to the Entity list


object prototype2 extends App {


  // === Entity ========================================================================================================
  case class Entity(node: Node, parentNode: Option[Node]) {
    def entityType: String = node.label

    def entityAttributes: Map[String,String] =
      node.attributes.asAttrMap ++
      // TODO: add inner text to attributes
      // TODO: if parent is "empty", add master root params ???
      // TODO: perhaps add ID instead of all master params ???
      Map("parent_label" -> (if ( parentNode.isEmpty ) "N/A" else parentNode.get.label) ) ++ // add parent label to the map
      (if (parentNode.isEmpty) Map() else parentNode.get.attributes.asAttrMap map {case(key,value) => ("parent_" + key) -> value } ) // add parent attributes to the map

    override def toString = s"\nEntity type: $entityType\nEntity attributes:\n$entityAttributes"

    def insertSql: String = s"INSERT INTO $entityType (${entityAttributes.keys}) VALUES (${entityAttributes.values})"
  }
  object Entity {
    def apply(node: Node): Entity = Entity(node, None)
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
    private def addManyEntities(entitiesToAdd: List[Entity], accu: Entities): Entities = entitiesToAdd match {
      case Nil => accu
      case currentEntity :: rest => addManyEntities(rest, accu.addEntity(currentEntity))
    }

    def ++(that: Entities): Entities = {
      // take the larger Map and merge the saller one into it one Entity at a time
      val (largerEntities: Entities, smallerVals: List[Entity]) =
        if (this.entities.values.size < that.entities.values.size)
          (that, this.entities.values.toList.flatten)
        else
          (this, that.entities.values.toList.flatten)

      addManyEntities(smallerVals, largerEntities)
    }

    @scala.annotation.tailrec
    private def addEntitiesRec(newEntities: List[Entity], curr: Entities): Entities = newEntities match {
      case Nil => curr
      case ent :: rest => addEntitiesRec(rest, curr.addEntity(ent))
    }
    def addEntities(newEntities: List[Entity]): Entities = addEntitiesRec(newEntities, this)

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

      val attributeValuesAnalysed: Map[String, Map[String, (Boolean, Int)]] = // Map[EntityName, Map[AttributeName, (isInteger, maxLength)]]
        entities map {
          case (entityType, entities) => (
            entityType,
            mergeMaps(entities map (_.entityAttributes)) map {case(attributeName, allAttributeValues) => (attributeName, (allAttributeValues forall (_.forall(c => c.isDigit)), allAttributeValues map (_.length) max ))} // for each attribute determine if it is integer and determine its max length
          )
        }

      val ddl = attributeValuesAnalysed map {
        case(entityName, attributes) => (entityName, attributes map {
          case(attributeName, (isInteger, length)) => s"\t${attributeName} ${if (isInteger) "NUMBER(" + (length+2).toString + ",0)" else "VARCHAR(" + roundUp10(length).toString + ")"} NULL"
        } mkString(",\n") )
      } map {
        case(entityName, entityAttributesDDL) => s"CREATE TABLE ${entityName} (\n" + entityAttributesDDL + "\n);"
      } mkString("\n------------------------------------------\n")

      ddl
    }


  }
  object Entities {
    val empty = Entities(Map())

    private def entityListFromNode(root: Node, parent: Option[Node]): List[Entity] = // no tailrec
      Entity(root, parent) :: (if(root.child.toList.isEmpty) Nil else (root.child.toList map (entityListFromNode(_, Some(root))) reduce (_ ::: _)))

    def fromNode(root: Node): Entities = {
      val entityList = entityListFromNode(root, None)
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

  val entitiesFromRoot: Entities = Entities.fromNode(root)

  val ddls = entitiesFromRoot.entityDDLs

  writeFile2("testddl.sql", ddls)

  //print ( entitiesFromRoot )





}
