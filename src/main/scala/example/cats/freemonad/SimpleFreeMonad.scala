package example.cats.freemonad

import cats.free.Free

import scala.util.Try
//need to ensure this is imported
import cats.implicits._
import cats.~>

object SimpleFreeMonad {
  type FreeAction[A] = Free[Action, A]

  //step1: create AST
  sealed trait Action[A]

  case class LeftAction(s: String) extends Action[Int]

  case class RightAction(s: String) extends Action[Int]

  case class SumAction(left: Int, right: Int) extends Action[Int]

  //step 2: create dsl
  object Actor {
    def left(s: String): FreeAction[Int] = Free.liftF(LeftAction(s))

    def right(s: String): FreeAction[Int] = Free.liftF(RightAction(s))

    def sum(left: Int, right: Int): FreeAction[Int] = Free.liftF(SumAction(left, right))
  }

  //step 3: create program
  def program(lstr: String, rstr: String): FreeAction[Long] =
    for {
      l <- Actor.left(lstr)
      r <- Actor.right(rstr)
      s <- Actor.sum(l, r)
    } yield s

  //step 4: create interpreter (given two examples)
  val optionInterpreter = new (Action ~> Option) {
    override def apply[A](fa: Action[A]): Option[A] = fa match {
      case LeftAction(s) => Option((s.length * 1000).asInstanceOf[A])
      case RightAction(s) => Option((s.length * 10).asInstanceOf[A])
      case SumAction(l, r) => Option((l + r).asInstanceOf[A])
    }
  }

  val tryInterpreter = new (Action ~> Try) {
    override def apply[A](fa: Action[A]): Try[A] = fa match {
      case LeftAction(s) => Try((s.length * 1000).asInstanceOf[A])
      case RightAction(s) => Try((s.length * 10).asInstanceOf[A])
      case SumAction(l, r) => Try((l + r).asInstanceOf[A])
    }
  }

  def main(args: Array[String]): Unit = {
    //step 5: compose it together
    val optionValue: Option[Long] = program("abc", "andrew").foldMap(optionInterpreter)
    val tryValue: Try[Long] = program("abc", "andrew").foldMap(tryInterpreter)
    println(s"opt;$optionValue;try;$tryValue")
  }

}