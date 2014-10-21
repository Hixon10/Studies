package ru.spbau.komarov.parsePolishNotation

import scala.util.parsing.combinator._


class PolishCalculator extends JavaTokenParsers {


  var stack : List[Node] = List[Node]()

  def calculate(expression: String) = calculateNode(parseAll(expr, expression).get)

  def calculateNode(node:Node) : Float = node match {
    case Num(x) => x
    case Add(x,y) => calculateNode(x) + calculateNode(y)
    case Sub(x,y) => calculateNode(x) - calculateNode(y)
    case Mul(x,y) => calculateNode(x) * calculateNode(y)
    case Div(x,y) => calculateNode(x) / calculateNode(y)
  }

  abstract class Node
  case class Add(x:Node, y:Node) extends Node
  case class Sub(x:Node, y:Node) extends Node
  case class Mul(x:Node, y:Node) extends Node
  case class Div(x:Node, y:Node) extends Node
  case class Num(x:Float) extends Node

  def expr:   Parser[Node] = rep(numbers ~ operator) ^^ {
    case blocks =>
      blocks.foreach(block =>
        block match {
          case blocksNumbers ~ op => {
            blocksNumbers.foreach( n =>
              stack = Num(n) :: stack
            )
            stack match {
              case x :: y :: xs => stack = op(y,x) :: xs
              case _ => throw new scala.MatchError("bad expression")
            }
          }
        }
      )
      stack match {
        case x :: _ => x
        case _ => throw new scala.MatchError("bad expression")
      }
  }

  def numbers: Parser[List[Float]] = rep(num)

  def num: Parser[Float] = floatingPointNumber ^^ { _.toFloat }

  def operator: Parser[(Node, Node) => Node] = ("+" | "-" | "*" | "/") ^^ {
    case "+" => add
    case "-" => sub
    case "*" => mul
    case "/" => div
  }

  def add(x: Node, y: Node) = new Add(x,y)
  def sub(x: Node, y: Node) = new Sub(x,y)
  def mul(x: Node, y: Node) = new Mul(x,y)
  def div(x: Node, y: Node) = new Div(x,y)
}

//test
object Main {
  def main(args: Array[String]) {
    val pc = new PolishCalculator
    println(pc.calculate("5 1 2 + 4 * + 3 -")) // 5 + ((1 + 2) × 4) − 3 = 14
    println(pc.calculate("1 2 3 4 5 - - - -")) // (1 - (2 - (3 - (4 - 5)))) = 3
  }
}
