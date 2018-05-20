import obiee.parse.common.Parser.ObieeNodeAttribute

import scala.xml.{Elem, MetaData, Node, XML}
import obiee.parse.common._

import scala.collection.Map

// TODO: recursively build Node tree,
// TODO: skip nodes where there are no attributes but do not skip nodes where there is text
// TODO: node internal text needs to be added to the Entity list
// TODO: for each node add parent


object prototype1 extends App {


/*

  //val sampleObieeLogicalTable = scala.io.Source.fromFile()
  //children foreach ( childNode => print (s"Node content: ${childNode.text}") )
  //children foreach ( childNode => print (s"NODE CONTENT: ${childNode.toString()}\n\n") )

  val logicalTableXml = XML.loadFile("C:\\Developer\\OBIEE\\RPD XML\\oracle\\bi\\server\\base\\LogicalTable\\00000f4c-9900-1567-b426-0af982470000.xml")

  val children: List[Node] = logicalTableXml.child.toList
  //val children: List[Elem] = logicalTableXml.child.toList



  val rootNode: Node = logicalTableXml


  def flatChildNodes(root: Node, parent: Node): List[(Node,Node)] = (root,parent) :: (root.child.toList map (flatChildNodes(_,root) ) reduce (_ ::: _) )

  def nodeAndParentAttributes( nodeAndParent: (Node,Node) ): Map[String, String] = nodeAndParent match {
    case (node, parentNode) => {
      val nodeLabel = node.label

      /* add text between tags here */
      val nodeAndParentAttributes =
        node.attributes.asAttrMap ++
        (parentNode.attributes.asAttrMap map {case(key,value) => ("parent_" + key) -> value } )
    }
  }




  //case class NodeAttribute(Map[String, String])

  //case class NodeMap(nodes: Map[String, List[ObieeNodeAttribute]])


  case class Entity(node: Node, parentNode: Option[Node]) {
    def entityType: String = node.label
    def entityAttributes: Map[String,String] =
      node.attributes.asAttrMap ++
      Map("parent_label" -> (if ( parentNode.isEmpty ) "N/A" else parentNode.get.label) ) ++ // add parent label to the map
      (if (parentNode.isEmpty) Map() else parentNode.get.attributes.asAttrMap map {case(key,value) => ("parent_" + key) -> value } ) // add parent attributes to the map
    //def mapEntry: Map[String, Map[String, String]] = Map(label -> nodeAttributes)
  }
  object Entity {
    def apply(node: Node) = Entity(node, None)
  }

  case class Entities(entities: Map[String, List[Entity]]) {
    def addEntity(newEntity: Entity) = Entities(
      entities +
      (newEntity.entityType -> (
          newEntity :: (  if(entities.contains(newEntity.entityType)) entities(newEntity.entityType) else List() )
        )
      )
    )
  }
  val emptyEntities = Entities(Map())


  case class CCCNode(nodeLabel: String, nodeAttributes: List[Map[String, String]])

  val emptyNode = CCCNode("", List())

  //case class CCCNode(nodeLabel: String, nodeAttributes: List[ObieeNodeAttribute])
  //case class CCCNode(nodeLabel: String, nodeAttributes: List[ObieeNodeAttribute])


  class CCCNodes(node: CCCNode) {
    val nodes = Map(node.nodeLabel -> node.nodeAttributes)


    def mergeMaps(src: Map[String,String], tgt: Map[String,String]): Map[String,String] = {
    }


    def mergeNodes(sourceNodes: CCCNodes): CCCNodes = {


    }

    //def this(nodes: CCCNodes) =
  }

  object CCCNodes {
    apply
  }


//  case class NodeMap(nodes: Map[String, List[ObieeNodeAttribute]])











  val attrib = logicalTableXml.attributes.asAttrMap
  print ( attrib )

  val xxx = Parser.emptyXmlEntities

  val yyy: XmlEntities = xxx.addEntity(logicalTableXml.label, attrib)

  print (s"\nYYY: $yyy\n\n")

  def processChildren(children: List[Node], cumm: XmlEntities): XmlEntities = children match {
    case Nil => cumm
    case child::rest if child.toString() != "\n" => {
      //val xxxxx = child.label
      //val yyyyy = child.attributes.asAttrMap
      //val zzzzz = child.toString()
      processChildren(rest, cumm.addEntity(child.label, child.attributes.asAttrMap))
    }
    case child::rest => processChildren(rest, cumm)
  }


  val sss = processChildren(children, yyy)
  print (s"\nSSS: $sss\n\n")


  // ????????? weird values there!!! #PCDATA

  //val sss = children map ( childNode => List( childNode.label, childNode.attributes.asAttrMap ) ) foldLeft (yyy) { (aa,bb) => yyy }




  print (s"\nlabel: ${logicalTableXml.label}, prefix: ${logicalTableXml.prefix}" )





  val zzz=1

*/

}
