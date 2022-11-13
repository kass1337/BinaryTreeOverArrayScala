package prototype

import comparator.Comparator

import java.io.InputStream

trait ProtoType { // Имя типа
  def typeName: String

  // Создание объекта
  def create: AnyRef

  // Клонирование текущего
  def clone(obj: AnyRef): AnyRef

  // Создание и чтения объекта
  def readValue(inputStream: InputStream): AnyRef

  // Создает и парсит содержимое из строки
  def parseValue(someString: String): AnyRef

  // Возврат компаратора для сравнения
  def getTypeComparator: Comparator
}