package ru.spbau.komarov.tasks

object Main{

  def gcd(a: Int,b: Int): Int = {
    if(b == 0) {
      a
    } else {
      gcd(b, a%b)
    }
  }


  def fibonacci( n : Int) : Int = {
    def foo( n: Int, a:Int, b:Int): Int = n match {
      case 0 => a
      case _ => foo(n-1, b, a+b)
    }
    return foo( n, 0, 1)
  }


  def qSort(list: List[Int]): List[Int] = {
    list match {
      case Nil => list
      case _ => qSort(list.tail.filter(_ <= list.head)) ::: list.head :: qSort(list.tail.filter(_ > list.head))
    }
  }


  def main(args: Array[String]) {

    println(gcd(30,42))

    println(fibonacci(10))

    val list = List(4,1,3,2,6,9,5,8,7)
    println(qSort(list))

  }

}

