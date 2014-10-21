package ru.spbau.komarov.parseJSON


class ParseJSON(str: String) {

  val parsedJSON = scala.util.parsing.json.JSON.parseFull(str)

}

//test
object Main {
  def main(args: Array[String]): Unit = {
    val jsonString = """{
                           "firstName": "Иван",
                           "lastName": "Иванов",
                           "address": {
                               "streetAddress": "Московское ш., 101, кв.101",
                               "city": "Ленинград",
                               "postalCode": 101101
                           },
                           "phoneNumbers": [
                               "812 123-1234",
                               "916 123-4567"
                           ]
                        }"""

    val parsedJSON = new ParseJSON(jsonString).parsedJSON;

    val name = parsedJSON match {
      case Some(m: Map[String, Any]) => m("firstName") match {
        case s: String => s
      }
    }

    print(name)
  }
}