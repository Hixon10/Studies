package ru.spbau.komarov.parseJSON


class parseJSON(str: String) {

  val parcedJSON = scala.util.parsing.json.JSON.parseFull(str)

}

//test
object Main {
  def main(args: Array[String]): Unit = {
    val jsonPushkin = """{
      "name": "Alex Pushkin",
      "age": 215,
      "books": ["Ruslan i Lyudmila", " Medny vsadnik", "Pikovaya dama"]
}"""

    val obj = new parseJSON(jsonPushkin);

    obj.parcedJSON match {
      case Some(m: Map[String, Any]) => m("name") match {
        case s: String => s
      }
    }
  }
}