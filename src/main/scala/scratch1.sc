import scala.collection.Map

val a: Map[String,String] = Map()
val b: Map[String,String] = Map("Janis" -> "zveers")
val c: Map[String,String] = Map("Janis" -> "zveers", "Sandrons" -> "rupucis", "Andžela" -> "skukjis")

a == Map()

a == Map.empty
b == Map.empty


c.head
c.tail

/*
c match {
  case Map("a"->"b") => "runc"
  case _ => "cunc"
}*/

c.toList


val aa: List[List[Int]] = List(List(1,2,3), List(4,5,6))
val aa1 = aa reduce (_ ::: _)

//val bb: List[List[Int]] = Nil
//val bb1 = bb reduce (_ ::: _)

val ff: List[Int] = Nil
if (ff.isEmpty) 1 else 2


val (qq,ww) = (1,2)


val sss = "123402"
sss forall (_.isDigit)


(10 - (0 % 10)) % 10

def roundUp10(i: Int): Int = i + (10 - (i % 10)) % 10

(0 to 99) map (i => (i,roundUp10(i))) toList


"parent_id".indexOf("parentx")


List("runcis","muncis","puncis") mkString(">",",","<")


















