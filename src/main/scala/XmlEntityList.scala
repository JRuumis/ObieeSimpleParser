package obiee.parse.common

object Parser {

  // these two - case clases???
  type ObieeNodeAttribute = Map[String, String]
  type ObieeNode = Map[String, List[ObieeNodeAttribute]]

  val emptyEntityList: ObieeNode = Map()
  val emptyXmlEntities = XmlEntities(emptyEntityList)
}
import Parser._




class XmlEntityList(entityList: ObieeNode) {

  //def hasType(entityType: String): Boolean = entityList.contains(entityType)

  //def addType(entityType: String): XmlEntityList =
  //  if (entityList.contains(entityType))  this
  //  else                                  XmlEntityList(entityList + (entityType -> List()))


  def addEntity2(entityType: String, entityAttributes: ObieeNodeAttribute): XmlEntityList = {
    val currentTypeAttributes: List[ObieeNodeAttribute] = entityList.getOrElse(entityType, List())
    val newXXXXX: List[ObieeNodeAttribute] = entityAttributes :: currentTypeAttributes
    val ccccc: ObieeNode = entityList + (entityType -> newXXXXX)
    XmlEntityList(ccccc)

  }

  def addEntity(newEntityType: String, newEntityAttributes: ObieeNodeAttribute): XmlEntityList = {
    val newListOfEntitiesForType: List[ObieeNodeAttribute] = newEntityAttributes :: entityList.getOrElse(newEntityType, List())
    val newEntityList: ObieeNode = entityList + (newEntityType -> newListOfEntitiesForType)
    XmlEntityList(newEntityList)
  }

}

object XmlEntityList {
  def apply(entityList: ObieeNode): XmlEntityList = {
    new XmlEntityList(entityList)
  }
}



case class XmlEntities(entities: ObieeNode) {

  def addEntity(entityType: String, entityAttributes: ObieeNodeAttribute): XmlEntities = {
    val newListOfEntitiesForType: List[ObieeNodeAttribute] = entityAttributes :: entities.getOrElse(entityType, List())
    val newEntityMap: ObieeNode = entities + (entityType -> newListOfEntitiesForType)
    XmlEntities(newEntityMap)
  }
}
