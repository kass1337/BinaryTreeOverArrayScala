package structure

import comparator.Comparator

import java.io.{FileInputStream, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
import java.util
import scala.collection.mutable.ArrayBuffer
import java.util.Vector

class BinaryTreeArray extends Serializable {
  private var arrayTree: ArrayBuffer[AnyRef] = null
  private var comparator: Comparator = null
  private var size: Int = 0

  // Инициализация структуры данных
  def this(comparator: Comparator) {
    this()
    size = 10
    arrayTree = new ArrayBuffer[AnyRef](size)
    for (i <- 0 until size) {
      arrayTree.addOne(null)
    }
    this.comparator = comparator
  }

  def this(size: Int, t: ArrayBuffer[AnyRef], c: Comparator) {
    this()
    this.size = size
    this.comparator = c
    this.arrayTree = t
  }

  def save(): Unit = {
    try {
      val outputStream: FileOutputStream = new FileOutputStream("saved.ser")
      val out: ObjectOutputStream = new ObjectOutputStream(outputStream)
      out.writeObject(this)
      out.close()
      outputStream.close()
    } catch {
      case i: IOException =>
        i.printStackTrace()
    }
  }

  def load: BinaryTreeArray = {
    var loadedArrayTree: BinaryTreeArray = null
    try {
      val fileIn: FileInputStream = new FileInputStream("saved.ser")
      val in: ObjectInputStream = new ObjectInputStream(fileIn)
      loadedArrayTree = in.readObject.asInstanceOf[BinaryTreeArray]
      in.close()
      fileIn.close()
    } catch {
      case i: IOException =>
        i.printStackTrace()
      case e: ClassNotFoundException =>
        e.printStackTrace()
    }
    loadedArrayTree
  }

  // Вcпомогательный метод вставки значения в массив
  private def insertRecursive(current: Int, obj: AnyRef): Unit = {
    if (current >= size) { // увеличение размерности при выходе
      size *= 2 // за пределы массива

      for (i <- size / 2 to size) { // с обнулением новой части
        arrayTree.addOne(null)
      }
    }
    if (arrayTree.view(current) == null) {
      arrayTree.insert(current, obj)
      return
    }
    if (comparator.compare(obj, arrayTree.view(current)) < 0) insertRecursive(2 * current + 1, obj)
    else insertRecursive(2 * current + 2, obj)
  }

  // Вставка значения в дерево
  def addValue(value: AnyRef): Unit = {
    insertRecursive(0, value)
  }

  private def findRecursive(current: Int, value: AnyRef): AnyRef = {
    if (current > size) return null
    if (comparator.compare(value, arrayTree.view(current)) == 0) return arrayTree.view(current)
    if (comparator.compare(value, arrayTree.view(current)) < 0) findRecursive(2 * current + 1, value)
    else findRecursive(2 * current + 2, value)
  }

  @throws[Exception]
  private def findByValue(value: AnyRef): AnyRef = {
    val temp: AnyRef = findRecursive(0, value)
    if (temp == null) throw new Exception("Binary tree has no such value")
    temp
  }

  private def scan(current: Int, level: Int, boolTree: Boolean): Unit = {
    if (current >= size) return
    if (arrayTree.view(current) == null) return
    scan(2 * current + 1, level + 1, boolTree)
    if (boolTree) {
      for (i <- 0 until level) {
        System.out.print("\t")
      }
      print(arrayTree.view(current).toString + "\n")
    }
    else print(arrayTree.view(current).toString + " ")
    scan(2 * current + 2, level + 1, boolTree)
  }

  def printTree(): Unit = {
    scan(0, 0, true)
  }

  def printArray(): Unit = {
    scan(0, 0, false)
  }

  // Число вершин в поддереве
  private def getSize(num: Int): Int = {
    if (num >= size || arrayTree.view(num) == null) return 0
    1 + getSize(2 * num + 1) + getSize(2 * num + 2)
  }

  private def getDataAtIndexRecursive(searchIdx: Int, help: Int): AnyRef = {
    var searchIndex = searchIdx
    if (searchIndex >= size || searchIndex >= getSize(help)) return null
    val cntL: Int = getSize(2 * help + 1) // число вершин в левом поддереве
    if (searchIndex < cntL) return getDataAtIndexRecursive(searchIndex, 2 * help + 1) // Логический номер в левом поддереве
    searchIndex -= cntL // отбросить вершины левого поддерева

    if ( {
      searchIndex -= 1; searchIndex + 1
    } == 0) return arrayTree.view(help) // Логический номер – номер текущей вершины
    getDataAtIndexRecursive(searchIndex, 2 * help + 2) // в правое поддерево с остатком Логического номера

  }

  //нумерация "слева-направо", начинается с 0, см. cprog 8.5
  def getDataAtIndex(searchIndex: Int): AnyRef = getDataAtIndexRecursive(searchIndex, 0)

  def removeNodeByIndex(index: Int): Unit = {
    val obj: AnyRef = getDataAtIndex(index)
    removeNodeByValue(0, obj)
  }

  // Функция для удаления узла из BST (array implementation)
  private def removeNodeByValue(current: Int, key: AnyRef): Unit = {
    if (current >= size) return
    // базовый случай: ключ не найден в дереве
    if (arrayTree.view(current) == null) return
    // если заданный ключ меньше корневого узла, повторить для левого поддерева
    if (comparator.compare(key, arrayTree.view(current)) < 0) removeNodeByValue(2 * current + 1, key)
    else { // если данный ключ больше, чем корневой узел, повторить для правого поддерева
      if (comparator.compare(key, arrayTree.view(current)) > 0) removeNodeByValue(2 * current + 2, key)
      else { // ключ найден
        // Случай 1: удаляемый узел не имеет потомков (это листовой узел)
        if (2 * current + 1 > size && 2 * current + 2 > size) { // обновить узел до null
          arrayTree.insert(current, null)
        }
        else if (arrayTree.view(2 * current + 1) == null && arrayTree.view(2 * current + 2) == null) {
          arrayTree.insert(current, null)
        }
        else { // Случай 2: удаляемый узел имеет двух потомков
          if (arrayTree.view(2 * current + 1) != null && arrayTree.view(2 * current + 2) != null) { // найти его неупорядоченный узел-предшественник
            val helpObj: AnyRef = new AnyRef
            val predecessor: AnyRef = findMaximumKey(2 * current + 1, helpObj)
            // копируем значение предшественника в текущий узел
            arrayTree.insert(current, predecessor)
            // рекурсивно удаляем предшественника
            removeNodeByValue(2 * current + 1, predecessor)
          }
          else { // Случай 3: удаляемый узел имеет только одного потомка
            // выбираем дочерний узел
            if (arrayTree.view(2 * current + 1) != null) { // если удаляемый узел имеет потомка в левом поддереве
              // смещаем элементы в массиве
              arrayShiftRecursive(current, 2 * current + 1)
            }
            else { // если удаляемый узел имеет потомка в правом поддереве
              arrayShiftRecursive(current, 2 * current + 2)
            }
          }
        }
      }
    }
  }

  private def arrayShiftRecursive(rootIdx: Int, index: Int): Unit = {
    if (rootIdx > size || index > size) return
    if (arrayTree.view(index) == null) return
    arrayTree.insert(rootIdx, arrayTree.view(index))
    arrayTree.insert(index, null)
    if (2 * index + 1 >= size || 2 * rootIdx + 2 >= size) return
    if (arrayTree.view(2 * index + 1) != null) { // смещаем левое поддерево
      arrayShiftRecursive(2 * rootIdx + 1, 2 * index + 1)
    }
    if (arrayTree.view(2 * index + 2) != null) { // смещаем правое поддерево
      arrayShiftRecursive(2 * rootIdx + 2, 2 * index + 2)
    }
  }

  private def findMaximumKey(index: Int, obj: AnyRef): AnyRef = {
    if (index >= size) return obj
    if (arrayTree.view(index) == null) return obj
    findMaximumKey(2 * index + 2, arrayTree.view(index))
  }

  //рекурсивная балансировка
  private def balance(t: util.Vector[AnyRef], a: Int, b: Int, r: ArrayBuffer[AnyRef]): Unit = {
    if (a > b) return
    if (a == b) return
    val m: Int = (a + b) >>> 1 // взять строку из середины интервала
    insertRecursive(r, 0, t.get(m))
    balance(t, m + 1, b, r) // рекурсивно выполнить для левой и

    balance(t, a, m, r) // правой частей

  }

  //вставка для нового аррайлист при балансировке
  private def insertRecursive(t: ArrayBuffer[AnyRef], current: Int, obj: AnyRef): Unit = {
    if (current >= size) {
      size *= 2
      for (i <- size / 2 to size) {
        t.addOne(null)
      }
    }
    if (t.view(current) == null) {
      t.insert(current, obj)
      return
    }
    if (comparator.compare(obj, t.view(current)) < 0) insertRecursive(t, 2 * current + 1, obj)
    else insertRecursive(t, 2 * current + 2, obj)
  }

  //главный метод балансировки
  def balance: BinaryTreeArray = {
    val sz1: Int = getSize(0)
    val newArray: Vector[AnyRef] = new Vector[AnyRef] //вектор индексов
    val newArrayTree: ArrayBuffer[AnyRef] = new ArrayBuffer[AnyRef](size)
    for (i <- 0 until size) {
      newArrayTree.addOne(null)
    }
    set(newArray, 0)
    balance(newArray, 0, sz1, newArrayTree)
    val balanced: BinaryTreeArray = new BinaryTreeArray(this.size, newArrayTree, this.comparator)
    balanced
  }

  //метод для добавления индексов в вектор
  private def set(t: Vector[AnyRef], n: Int): Unit = {
    if (n >= size || arrayTree.view(n) == null) return
    set(t, 2 * n + 1)
    t.add(arrayTree.view(n))
    set(t, 2 * n + 2)
  }

  // итератор forEach
  def forEach(func: DoWith): Unit = {
    if (arrayTree == null || size <= 0) return
    val sz: Int = getSize(0)
    val v: util.Vector[Integer] = new util.Vector[Integer](size)
    setHelp(v, 0)
    for (i <- 0 until sz) {
      func.doWith(arrayTree.view(v.get(i)))
    }
  }

  //Вспомогательный метод обхода для forEach
  private def setHelp(t: util.Vector[Integer], n: Int): Unit = {
    if (n >= size || arrayTree.view(n) == null) return
    setHelp(t, 2 * n + 1)
    t.add(n)
    setHelp(t, 2 * n + 2)
  }

  private def scan(current: Int, level: Int, str: String): String = {
    if (current >= size) return str
    if (arrayTree.view(current) == null) return str
    var helpStrL: String = new String
    helpStrL = scan(2 * current + 1, level + 1, helpStrL)
    for (i <- 0 until level) {
      helpStrL += "           "
    }
    helpStrL += (arrayTree.view(current).toString + "\n")
    var helpStrR: String = new String
    helpStrR = scan(2 * current + 2, level + 1, helpStrR)
    helpStrL + helpStrR
  }

  override def toString: String = {
    val str: String = ""
    scan(0, 0, str)
  }
}
