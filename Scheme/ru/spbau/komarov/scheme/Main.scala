package ru.spbau.komarov.scheme

object Main {
  def main(args: Array[String]) {
    // test
    val sch1:Scheme =  new Seqq(new Par(new Res(120), new Res(40)) , new Res(30))
    val sch2:Scheme =  new Par(new Res(100), new Res(20))
    val sch3:Scheme =  new Seqq(new Par(new Res(130), new Res(25)) , new Par(sch1, sch2))
    ShowingScheme.show(sch3)
  }
}

/*
                                       =======
                                  +---|  120  |---+
                                  |    =======    |
                                  |               |           ======
                              +---+               +----------|  30  |---+
                              |   |               |           ======    |
        =======               |   |    ======     |                     |
   +---|  130  |---+          |   +---|  40  |----+                     |
   |    =======    |          |        ======                           |
   |               |          |                                         |
---+               +----------+                                         +---
   |               |          |                                         |
   |    ======     |          |                 =======                 |
   +---|  25  |----+          |            +---|  100  |---+            |
        ======                |            |    =======    |            |
                              |            |               |            |
                              +------------+               +------------+
                                           |               |
                                           |    ======     |
                                           +---|  20  |----+
                                                ======

 */
