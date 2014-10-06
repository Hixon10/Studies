package ru.spbau.komarov.task3

object Main3 {

  def main(args: Array[String]) = {

    //test
    val t = new True
    val f = new False
    val x  = new X()

    println( (x and !x) == f )
    println( (x and x) == x )
    println( (x or x) == x )
    println( !(x and (x or f)) == (x or x or x) )

  }
}


abstract class LogicalExpr {

  def and(that: LogicalExpr) :And= new And(this, that)

  def or(that: LogicalExpr):Or= new Or(this, that)

  def unary_! = new Not(this)

  def == (that: LogicalExpr) : Boolean =
    (this.ifXIsTrue == that.ifXIsTrue) && (this.ifXIsFalse == that.ifXIsFalse)

  def ifXIsTrue() : Boolean = this match {
    case False() => false
    case True() => true
    case X() => true
    case And(a,b) => a.ifXIsTrue && b.ifXIsTrue
    case Or (a,b) => a.ifXIsTrue || b.ifXIsTrue
    case Not (a) => !a.ifXIsTrue
  }

  def ifXIsFalse() : Boolean = this match {
    case False() => false
    case True() => true
    case X() => false
    case And(a,b) => a.ifXIsFalse && b.ifXIsFalse
    case Or (a,b) => a.ifXIsFalse || b.ifXIsFalse
    case Not (a) => !a.ifXIsFalse
  }
}

case class False() extends LogicalExpr
case class True() extends LogicalExpr
case class X() extends LogicalExpr
case class And(le1: LogicalExpr, le2: LogicalExpr) extends LogicalExpr
case class Or(le1: LogicalExpr, le2: LogicalExpr) extends LogicalExpr
case class Not(le: LogicalExpr) extends LogicalExpr
