package factory

import prototype.{IntegerType, ProtoType}

import scala.collection.mutable.ArrayBuffer

class FactoryType {
  def getTypeNameList: ArrayBuffer[String] = {
    val list: ArrayBuffer[String] = new ArrayBuffer[String]()
    list.addOne("Integer")
    list.addOne("DateTime")
    list
  }

  def getBuilderByName(name: String): ProtoType = {
    name match {
      case "Integer" =>
        return new IntegerType

      /*case "DateTime" =>
        return new DateTimeType*/

    }
    null
  }
}
