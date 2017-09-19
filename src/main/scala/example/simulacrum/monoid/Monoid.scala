package example.simulacrum.monoid

import simulacrum._

@typeclass trait Monoid[A] {
  @op("|+|") def mappend(a: A, b: A): A
  def mzero: A
}

@typeclass trait Show[A] {
  def show(a: A): String
}

object Test {
  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
    override def mappend(a: Int, b: Int): Int = a - b

    override def mzero: Int = 0
  }

  implicit val StringShow = new Show[String] {
    override def show(a: String) = s"hello $a"
  }


  def main(args: Array[String]): Unit = {
    import Monoid.ops._
    import Show.ops._


    println(3 |+| 4)

    println("abc".show)

  }
}