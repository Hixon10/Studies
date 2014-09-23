package ru.spbau.komarov.scheme

object Main {
  def main(args: Array[String]) {
    // test
    val sch1:Scheme =  new Seqq(new Par(new Res(1), new Res(2)) , new Res(4))
    val sch2:Scheme =  new Par(new Res(1), new Res(2))
    val sch3:Scheme =  new Seqq(new Par(new Res(1), new Res(2)) , new Par(sch1, sch2))

    ShowingScheme.show(sch3)

  }
}

/*
                                     =====
                                +---|     |---+
                                |    =====    |
                                |             |           =====
                            +---+             +----------|     |---+
                            |   |             |           =====    |
        =====               |   |    =====    |                    |
   +---|     |---+          |   +---|     |---+                    |
   |    =====    |          |        =====                         |
   |             |          |                                      |
---+             +----------+                                      +---
   |             |          |                                      |
   |    =====    |          |                =====                 |
   +---|     |---+          |           +---|     |---+            |
        =====               |           |    =====    |            |
                            |           |             |            |
                            +-----------+             +------------+
                                        |             |
                                        |    =====    |
                                        +---|     |---+
                                             =====
 */
