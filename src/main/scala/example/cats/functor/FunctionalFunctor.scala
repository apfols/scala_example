package example.cats.functor

import cats._
import cats.instances.all._

object FunctionalFunctor {
  //need to add kind-projector compiler plugin to ignore one hole in "Function1"
  implicit def function1Functor[In]: Functor[Function1[In, ?]] =
    new Functor[Function1[In, ?]] {
      def map[A, B](fa: In => A)(f: A => B): Function1[In, B] = fa andThen f
    }

  implicit val optionFuctor = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: (A) => B) = fa map f
  }




  def main(args: Array[String]): Unit = {
    def h2(x: Int) = x * 7
    val h1: Int => Int = (x: Int) => x + 1

    val result = function1Functor.map(h1)(h2)(5)

    //due to return type constraints, "andThen" is used. it is not the same as expected
    //(5 + 1) * 7
    println(result)


    val a = Some("hello")


    val m = Functor[Option].lift((x: String) => x.length)

//    Functor[Option].fproduct(a)


  }
}
