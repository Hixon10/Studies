package ru.spbau.komarov.task4


object Main4 {
  def main(args: Array[String]) = {
    //test
    val ce = new CrazyExperiment

    println(ce.isPossibleCalc( (1+2)*3*4*5*6*7*8*9*10) )
    println(ce.isPossibleCalc( 1+((2-3)+(4-5)*6+(7*8+9))-10 ))
    println(ce.isPossibleCalc( (1+2-3)-(4-5*6)-(7 *(8-9-10)) ))

    println(ce.isPossibleCalc( (1+2)*3*4*5*6*7*8*9*10 + 1 ))
    println(ce.isPossibleCalc( (1+2)*3*4*5*6*7*8*9*10 - 10 ))
    println(ce.isPossibleCalc( 66965 ))
    println(ce.isPossibleCalc( -38312 ))


    //ce.printPossVal

  }
}


class CrazyExperiment {
  private val L = 10
  private val arr = Array.ofDim[scala.collection.mutable.SortedSet[Int]](L+1, L+1)
  private var arrExist:Boolean = false

  private def calcArray() = {
    for(i <- 0 to L-1)
      for(j <- i+1 to L)
        arr(i)(j) = scala.collection.mutable.SortedSet[Int]()

    for(i <- 0 to L-1)
      arr(i)(i+1) += i+1

    for(width <- 2 to L) {
      var steps = L-width+1
      for(step <- 0 to steps-1)
        for(i <- 1 to width-1)
          for(a <- arr(step)(step+i))
            for(b <- arr(step+i)(step+width)) {
              arr(step)(step+width) += a+b
              arr(step)(step+width) += a-b
              arr(step)(step+width) += a*b
            }
    }

    arrExist = true
  }

  def isPossibleCalc(x: Int) : Boolean = {
    if(!arrExist)
      calcArray()
    arr(0)(L).contains(x)
  }

  def printPossVal = {
    if(!arrExist)
      calcArray()
    for(i <- arr(0)(L)) print(" " + i)
  }

}
