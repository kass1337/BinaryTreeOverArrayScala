package comparator

import usertype.IntegerClass

class IntegerComparator extends Comparator with Serializable {
  override def compare(o1: Any, o2: Any): Int = o1.asInstanceOf[IntegerClass].getValue - o2.asInstanceOf[IntegerClass].getValue
}
