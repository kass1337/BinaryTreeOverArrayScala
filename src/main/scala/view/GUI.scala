package view

import factory.FactoryType
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Alert, Button, ComboBox, TextArea, TextField}
import scalafx.scene.paint.Color.Grey
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.text.Font
import structure.BinaryTreeArray

object GUI extends JFXApp {
  val factoryType = new FactoryType
  var protoType = factoryType.getBuilderByName("Integer")
  var bstArray = new BinaryTreeArray(protoType.getTypeComparator)
  var selectedType = "Integer"

  stage = new PrimaryStage {
    title = "KiTPO. LR #2"
    scene = new Scene(908,577) {

      fill = Grey

      val buttonAddElem = new Button("Добавить элемент")
      buttonAddElem.layoutX = 720
      buttonAddElem.layoutY = 50
      buttonAddElem.setPrefWidth(150)
      buttonAddElem.setPrefHeight(45)

      val deleteElem = new Button("Удалить элемент по индексу")
      deleteElem.layoutX = 690
      deleteElem.layoutY = 110
      deleteElem.setPrefWidth(205)
      deleteElem.setPrefHeight(25)

      val findByIndex = new Button("Поиск по индексу")
      findByIndex.layoutX = 710
      findByIndex.layoutY = 180
      findByIndex.setPrefWidth(170)
      findByIndex.setPrefHeight(25)

      val balance = new Button("Балансировка")
      balance.layoutX = 705
      balance.layoutY = 250
      balance.setPrefWidth(180)
      balance.setPrefHeight(30)

      val saveBinary = new Button("Сохранить в двоичный")
      saveBinary.layoutX = 690
      saveBinary.layoutY = 340
      saveBinary.setPrefWidth(205)
      saveBinary.setPrefHeight(35)

      val loadBinary = new Button("Загрузить из двоичного")
      loadBinary.layoutX = 690
      loadBinary.layoutY = 395
      loadBinary.setPrefWidth(205)
      loadBinary.setPrefHeight(35)

      val deleteTextField = new TextField
      deleteTextField.layoutX = 745
      deleteTextField.layoutY = 145
      deleteTextField.setPrefWidth(100)
      deleteTextField.setPrefHeight(25)

      val findTextField = new TextField
      findTextField.layoutX = 745
      findTextField.layoutY = 215
      findTextField.setPrefWidth(100)
      findTextField.setPrefHeight(25)

      val mainText = new TextArea
      mainText.layoutX = 25
      mainText.layoutY = 55
      mainText.setPrefWidth(645)
      mainText.setPrefHeight(485)
      mainText.setEditable(false)
      mainText.setFont(new Font("Arial", 14))

      val factoryList = new ComboBox(factoryType.getTypeNameList)
      factoryList.layoutX = 25
      factoryList.layoutY = 15
      factoryList.setPrefWidth(100)
      factoryList.setPrefHeight(25)

      content = List(buttonAddElem, deleteElem,
        findByIndex, balance, saveBinary, loadBinary,
        deleteTextField, findTextField, mainText, factoryList)

      factoryList.onAction = (e: ActionEvent) => {
        var item = factoryList.selectionModel.apply.getSelectedItem
        if (selectedType != item)
        {
          selectedType = item
          protoType = factoryType.getBuilderByName(selectedType)
          bstArray = new BinaryTreeArray(protoType.getTypeComparator)
          mainText.setText(bstArray.toString)
        }
      }

      // обработчик добавления элемента
      buttonAddElem.onAction = (e: ActionEvent) => {
        bstArray.addValue(protoType.create)
        mainText.setText(bstArray.toString)
      }

      // обработчик удаления элемента
      deleteElem.onAction = (e: ActionEvent) => {
        if (!deleteTextField.getText.isEmpty) {
          bstArray.removeNodeByIndex(Integer.parseInt(deleteTextField.getText));
          mainText.setText(bstArray.toString)
        }
        else
        new Alert(AlertType.Information) {
          title = "Ошибка!"
          headerText = "Ошибка при удалении!"
          contentText = "Введите значение индекса, не оставляйте поле пустым!"
        }.showAndWait()
      }

      // обработчик поиска элемента по индексу
      findByIndex.onAction = (e: ActionEvent) => {
        if (!findTextField.getText.isEmpty) {
          val findValue = bstArray.getDataAtIndex(findTextField.getText.toInt).toString
          new Alert(AlertType.Information) {
            title = "Результат поиска"
            headerText = "Результат поиска по индексу"
            contentText = "Значение = " + findValue + "\nпо индексу = " + findTextField.getText
          }.showAndWait()
        }
        else
        new Alert(AlertType.Information) {
          title = "Ошибка!"
          headerText = "Ошибка при поиске!"
          contentText = "Введите значение индекса, не оставляйте поле пустым!"
        }.showAndWait()
      }

      // обработчик балансировки
      balance.onAction = (e: ActionEvent) => {
        bstArray = bstArray.balance
        mainText.setText(bstArray.toString)
      }

      // обработчик сохранения
      saveBinary.onAction = (e: ActionEvent) => {
        bstArray.save
      }

      // обработчик загрузки
      loadBinary.onAction = (e: ActionEvent) => {
        bstArray = bstArray.load
        mainText.setText(bstArray.toString)
      }
    }
  }
}