package ru.spbau.komarov.task2

object Main2 {

  def main(args: Array[String]) = {
    //test
    pascal take 10 foreach println
  }

  val pascal: Stream[List[Int]] = List(1) #::
    pascal.map { n => ((0 :: n),(n ::: List(0))).zipped.map(_ + _) }

}
