package usertype
class DateTimeClass() extends Serializable {
  try {
    setDay(15)
    setMonth(11)
    setYear(2022)
    setHour(22)
    setMinute(52)
    setSecond(24)
  } catch {
    case ex: Exception =>
      System.out.println("Bad Date")
  }
  private val changeYear = 1582
  private var day = 0
  private var month = 0
  private var year = 0
  private var hour = 0
  private var minute = 0
  private var second = 0

  def this(_day: Int, _month: Int, _year: Int, _hour: Int, _minute: Int, _second: Int) {
    this()
    day = _day
    month = _month
    year = _year
    hour = _hour
    minute = _minute
    second = _second
  }

  def getDay: Int = day

  @throws[Exception]
  def setDay(day: Int): Unit = {
    if (day < 1 || day > 31) throw new Exception("Bad day")
    this.day = day
  }

  def getMonth: Int = month

  @throws[Exception]
  def setMonth(month: Int): Unit = {
    if (month < 1 || month > 12) throw new Exception("Bad month")
    this.month = month
  }

  def getYear: Int = year

  @throws[Exception]
  def setYear(year: Int): Unit = {
    if (year <= 0) throw new Exception("Bad year")
    this.year = year
  }

  def getHour: Int = hour

  @throws[Exception]
  def setHour(hour: Int): Unit = {
    if (hour < 0 || hour > 23) throw new Exception("Bad hour")
    this.hour = hour
  }

  def getMinute: Int = minute

  @throws[Exception]
  def setMinute(minute: Int): Unit = {
    if (minute < 0 || minute > 59) throw new Exception("Bad minute")
    this.minute = minute
  }

  def getSecond: Int = second

  @throws[Exception]
  def setSecond(second: Int): Unit = {
    if (second < 0 || second > 59) throw new Exception("Bad second")
    this.second = second
  }

  def isLeapYear: Boolean = {
    if (this.year > changeYear) return this.year % 4 == 0 && (this.year % 100 != 0 || this.year % 400 == 0)
    this.year % 4 == 0
  }

  private def isDayValid = if ((this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) && this.day == 31) false
  else if (this.month == 2 && isLeapYear && this.day > 29) false
  else if (this.month == 2 && this.day > 28) false
  else true

  override def toString: String = {
    var total = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year) + " "
    if (hour < 10) total += "0"
    total += String.valueOf(hour)
    total += ":"
    if (minute < 10) total += "0"
    total += String.valueOf(minute)
    total += ":"
    if (second < 9) total += "0"
    total += String.valueOf(second)
    total
  }
}