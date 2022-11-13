package usertype

class IntegerClass(var value: Int) extends Serializable {
  def getValue: Int = value

  def setValue(value: Int): Unit = {
    this.value = value
  }

  override def toString: String = String.valueOf(value)
}