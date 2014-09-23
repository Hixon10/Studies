package ru.spbau.komarov.scheme

abstract class Scheme
case class Res(v: Int) extends Scheme
case class Par (s1: Scheme, s2: Scheme) extends Scheme
case class Seqq(s1: Scheme, s2: Scheme) extends Scheme


class SchemeInfo(val list: List[String]) {
  def h = list.length
  def w = list(0).length
  var row = h/2
}

object ShowingScheme {

  def show(scheme: Scheme) = {
      println(schemeToString(scheme: Scheme))
  }

  def schemeToString(scheme: Scheme) :String = {
    var sb = new StringBuilder
    for(str <- getSchemeInfo(scheme).list) {
      sb ++= (str + "\n")
    }
    sb.toString()
  }

  def getSchemeInfo(scheme: Scheme) : SchemeInfo = scheme match {

    case Res(_) => {
      var si = new SchemeInfo(List("    =====    ",
                                   "---|     |---",
                                   "    =====    "))
      si.row = 1
      si
    }

    case Par(s1,s2) => {
      var si1 = getSchemeInfo(s1)
      var si2 = getSchemeInfo(s2)

      if (si2.w > si1.w) {
        var buf = si2
        si2 = si1
        si1 = buf
      }

      val diff = si1.w - si2.w
      val diffLeft = diff/2
      val diffRight = diff - diffLeft

      var ansList = List[String]()

      for( i <- 0 to si1.list.length-1) {
        if(i < si1.row) {
          ansList :+= ("   " + " " + si1.list(i) + " " + "   ")
        } else if ( i == si1.row ) {
          ansList :+= "   " + "+" + si1.list(i) + "+" + "   "
        } else {
          ansList :+= "   " + "|" + si1.list(i) + "|" + "   "
        }
      }

      ansList :+= "   |" + " "*si1.w + "|   "
      ansList :+= "---+" + " "*si1.w + "+---"
      ansList :+= "   |" + " "*si1.w + "|   "

      for( i <- 0 to si2.list.length-1) {
        if( i < si2.row ) {
          ansList :+= "   " + "|" + " "*diffLeft + si2.list(i) + " "*diffRight + "|" + "   "
        } else if( i == si2.row ) {
          ansList :+= "   " + "+" + "-"*diffLeft + si2.list(i) + "-"*diffRight + "+" + "   "
        } else {
          ansList :+= "   " + " " + " "*diffLeft + si2.list(i) + " "*diffRight + " " + "   "
        }
      }

      var si = new SchemeInfo(ansList)
      si.row = si1.h + 1
      si
    }


    case Seqq(s1,s2) => {
      var si1 = getSchemeInfo(s1)
      var si2 = getSchemeInfo(s2)

      val downH1 = si1.h-1-si1.row
      val downH2 = si2.h-1-si2.row

      var ansList = List[String]()
      var count: Int = 0

      if(si1.row < si2.row ) {
        for(i <- 0 to (si2.row - si1.row-1)) {
          ansList :+= " "*si1.w + "    " + si2.list(count)
          count += 1
        }
        for(i <- 0 to si1.row-1) {
          ansList :+= si1.list(i) + "    " + si2.list(count)
          count += 1
        }
      } else if (si1.row > si2.row ) {
        for(i <- 0 to (si1.row - si2.row-1)) {
          ansList :+= si1.list(count) + "    " + " "*si2.w
          count += 1
        }
        for(i <- 0 to si2.row-1) {
          ansList :+= si1.list(count) + "    " + si2.list(i)
          count += 1
        }
      } else {
        for(i <- 0 to si1.row-1) {
          ansList :+= si1.list(i) + "    " + si2.list(i)
          count += 1
        }
      }

      ansList :+= si1.list(si1.row) + "----" + si2.list(si2.row)

      count = 0

      if(downH1 < downH2) {
        for(i <- 0 to downH1-1) {
          count += 1
          ansList :+= si1.list(si1.row + count) + "    " + si2.list(si2.row + count)
        }
        for(i <- 0 to (downH2 - downH1-1)) {
          count += 1
          ansList :+= " "*si1.w + "    " + si2.list(si2.row + count)
        }
      } else if (downH1 > downH2) {
        for(i <- 0 to downH2-1) {
          count += 1
          ansList :+= si1.list(si1.row + count) + "    " + si2.list(si2.row + count)
        }
        for(i <- 0 to (downH1 - downH2-1)) {
          count += 1
          ansList :+= si1.list(si1.row + count) + "    " + " "*si2.w
        }
      } else {
        for(i <- 0 to downH1-1) {
          count += 1
          ansList :+= si1.list(si1.row + count) + "    " + si2.list(si2.row +count)
        }
      }


      var si = new SchemeInfo(ansList)
      si.row = si1.row
      si
    }

  }
}

