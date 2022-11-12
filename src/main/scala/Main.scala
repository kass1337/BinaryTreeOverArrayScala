import factory.FactoryType
import prototype.IntegerType
import structure.BinaryTreeArray
import usertype.IntegerClass
import usertype.DateTimeClass
/*
* 1. Не работает DateTimeClass
* 2. Не рабоатет DateTimeComparator
* 2. Не рабоатет DateTimeType
* */

object Main {
  def main(args: Array[String]): Unit = {
    // Здесь выполняются все операции одним потоком
    val factoryType = new FactoryType

    //СД для ТД Integer
    System.out.println("--------------TEST FOR INTEGER-------------")
    var protoType = factoryType.getBuilderByName("Integer")
    var btsArray = new BinaryTreeArray(protoType.getTypeComparator)

    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)

    println("---------PRINT TREE---------")
    btsArray.printTree

    println("---------PRINT ARRAY--------")
    btsArray.printArray

    println("\n----GET VALUE BY INDEX 2----")
    println("value = " + btsArray.getDataAtIndex(2).toString)

    println("---DELETE VALUE BY INDEX 2--")
    btsArray.removeNodeByIndex(2)
    btsArray.printTree

    println("-----SAVE IN BINARY FILE----")
    btsArray.save

    println("-----------BALANCE----------")
    btsArray = btsArray.balance
    btsArray.printTree

    println("---LOAD FROM BINARY FILE----")
    btsArray = btsArray.load
    btsArray.printTree

    println("---------FOR EACH-----------")
    btsArray.forEach(println)

    //СД для ТД DateTime
    /*println("----------TEST FOR DATETIME-----------")
    protoType = factoryType.getBuilderByName("DateTime")
    btsArray = new BinaryTreeArray(protoType.getTypeComparator)

    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)
    btsArray.addValue(protoType.create)

    println("---------PRINT TREE---------")
    btsArray.printTree

    println("---------PRINT ARRAY--------")
    btsArray.printArray()

    System.out.println("\n----GET VALUE BY INDEX 2----")
    System.out.println("value = " + btsArray.getDataAtIndex(2).toString)

    System.out.println("---DELETE VALUE BY INDEX 2--")
    btsArray.removeNodeByIndex(2)
    btsArray.printTree()

    System.out.println("-----SAVE IN BINARY FILE----")
    btsArray.save()

    System.out.println("-----------BALANCE----------")
    btsArray = btsArray.balance
    btsArray.printTree()

    System.out.println("---LOAD FROM BINARY FILE----")
    btsArray = btsArray.load
    btsArray.printTree()

    System.out.println("---------FOR EACH-----------")
    btsArray.forEach(System.out.println)*/

  }
}