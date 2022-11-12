package model.usertype.prototype

import comparator.Comparator
import comparator.DateTimeComparator
import comparator.Comparator
import comparator.DateTimeComparator


import prototype.ProtoType
import usertype.DateTimeClass
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Random
import java.util.stream.Collectors


class DateTimeType extends ProtoType {
  override def typeName = "DATETIME"

  override def create: AnyRef = { //генерация случайных чисел
    // дата [1; 31]
    val minDay = 1
    val maxDay = 31
    // месяц [1; 12]
    val minMonth = 1
    val maxMonth = 12
    // год [988; 2048]
    val minYear = 988
    val maxYear = 2048
    // часы [0; 23]
    val minHour = 0
    val maxHour = 23
    // минуты, секунды [0 ; 59]
    val minTime = 0
    val maxTime = 59
    val rand = new Random
    val day = rand.nextInt(maxDay - minDay) + 1
    val month = rand.nextInt(maxMonth - minMonth) + 1
    val year = rand.nextInt(maxYear - minYear) + 1
    val hour = rand.nextInt(maxHour - minHour)
    val minute = rand.nextInt(maxTime - minTime)
    val second = rand.nextInt(maxTime - minTime)
    var dateTimeValue = new DateTimeClass()
    //Если рандом нам сгенерировал дату, которой быть не может, то генерируем со статичными значениями
    try dateTimeValue = new DateTimeClass(day, month, year, hour, minute, second)
    catch {
      case ex: Exception =>
        println("Bad date, generating using a static values")
        dateTimeValue = new DateTimeClass
    }
    dateTimeValue
  }

  override def clone(obj: AnyRef): AnyRef = {
    var copyDateTime = new DateTimeClass()
    try copyDateTime = new DateTimeClass(obj.asInstanceOf[DateTimeClass].getDay, obj.asInstanceOf[DateTimeClass].getHour, obj.asInstanceOf[DateTimeClass].getYear, obj.asInstanceOf[DateTimeClass].getHour, obj.asInstanceOf[DateTimeClass].getMinute, obj.asInstanceOf[DateTimeClass].getSecond)
    catch {
      case ex: Exception =>
        copyDateTime = new DateTimeClass
    }
    copyDateTime
  }

  override def readValue(inputStream: InputStream): AnyRef = parseValue(new BufferedReader(new InputStreamReader(inputStream)).lines.collect(Collectors.joining("\n")))

  override def parseValue(someString: String): AnyRef = {
    val words = someString.split(" ")
    val dateStr = words(0).split("/")
    val timeStr = words(1).split(":")
    val dateInt = new Array[Integer](3)
    val timeInt = new Array[Integer](3)
    for (i <- 0 until 3) {
      dateInt(i) = dateStr(i).toInt
      timeInt(i) = timeStr(i).toInt
    }
    var dateTimeValue = new DateTimeClass()
    try dateTimeValue = new DateTimeClass(dateInt(0), dateInt(1), dateInt(2), timeInt(0), timeInt(1), timeInt(2))
    catch {
      case ex: Exception =>
        System.out.println("Bad date, generating using a static values")
        dateTimeValue = new DateTimeClass
    }
    dateTimeValue
  }

  override def getTypeComparator: Comparator = {
    val comparator = new DateTimeComparator
    comparator
  }
}