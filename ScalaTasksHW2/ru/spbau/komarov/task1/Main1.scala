package ru.spbau.komarov.task1

import scala.collection.mutable

object Main1 {
  def main(args: Array[String]) = {
    //test
    for(i <- getSquareRootOfTwo(10))
      print(i)
  }

  def getSquareRootOfTwo(n: Int): List[Int] = {
    val precision = new java.math.MathContext(n, java.math.RoundingMode.DOWN);
    var lastX = BigDecimal("0.0", precision)
    var curX = BigDecimal("1.5", precision)
    var i = 0
    var queue = mutable.Queue[BigDecimal](BigDecimal("0.0", precision),
      BigDecimal("0.0", precision),BigDecimal("0.0", precision))

    while(! (curX.toString() equals lastX.toString())){
      lastX = queue.dequeue()
      curX = step(curX, precision)
      queue.enqueue(curX)
      i += 1
    }
    var intList:List[Int] = List()
    var list = curX.toString().toList
    (List('1') ::: (list.tail.tail)).foreach( x => intList ::= x.asDigit)
    intList.reverse
  }

  def step(x: BigDecimal, precision: java.math.MathContext): BigDecimal = {
    val one = BigDecimal("1.0", precision)
    val two = BigDecimal("2.0", precision)
    ((x/two).round(precision) + (one/x).round(precision)).round(precision)
  }

}
