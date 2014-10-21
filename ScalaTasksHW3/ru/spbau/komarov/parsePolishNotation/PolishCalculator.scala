package ru.spbau.komarov.parsePolishNotation

import scala.util.parsing.combinator._


class PolishCalculator extends JavaTokenParsers {

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

  def expr:   Parser[Node] = num ^^{case x => new Num(x)} | (operator ~ expr ~ expr)^^{
    case op ~ ex1 ~ ex2 => op(ex1, ex2)
  }

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
    println(pc.calculate("+ - *  4 6 2 + 5 7"))
    println(pc.calculate("+ - * / 1 2 3 4 5"))
    println(pc.calculate(" * 10 / 14 + 3 4"))
  }
}
