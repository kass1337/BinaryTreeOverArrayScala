package factory

import prototype.{IntegerType, DateTimeType, ProtoType}

class FactoryType {
  def getTypeNameList: List[String] = {
    val list = List("Integer", "DateTime")
    list
  }

  def getBuilderByName(name: String): ProtoType = {
    name match {
      case "Integer" =>
        return new IntegerType

      case "DateTime" =>
        return new DateTimeType

    }
    null
  }
}
