package code.Lectures.part1AdvancedScala

import scala.util.Try

object DarkSugars extends App {

  //syntax sugar #1: methods with single param
  def singleArgMethod(arg:Int): String = s"$arg little birds"

  val description = singleArgMethod {
    //curly braces allowing complex code & param to be declared
    33
  }
  //examples
  val aTryInstance = Try{ //java's try {...}
    throw new RuntimeException
     }
    List(1,2,3).map{ x=> x +1}

  /*
  syntax sugar #2 : single abstract method
  instances of traits with a single method can produce 2 lambdas
   */
  trait Action{
    def act(x:Int):Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x +1
  }

  val aFunkyInstance: Action = (x:Int) => x +1 //magic


  /*
  Runnables
  instances of traits/Java interfaces that can be passed onto threads
  a valid creation:
   */
  //how java instantiates Runnables
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hi, Scala")
  })
  //how scala instantiates Runnables
  val aSweeterThread = new Thread(() => println("Sweet, Scala"))

  abstract class AnAbstractType {
    def implemented:Int = 23
    def f(a:Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a:Int) => println("sweet")

  //syntax sugar #3 the :: and #:: methods are special

  val prependedList = 2 :: List(3,4)
  //infix operators are usually converted to first object(2) .method(::) then second object(List(3,4))
  //compiler rewrites as List(3,4).::(2)

  //scala spec: last char decides associativity of a method
  1::2::3::List(4,5)
  //compiler rewrites as:
  List(4,5).::(3).::(2).::(1)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this //actual implementation here
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  //syntax sugar #4: multi-word method naming

  class TeenGirl(name:String) {
    def `and then said`(gossip:String) = println(s"$name said $gossip")
  }

  val lizzy = new TeenGirl("lizzy")
  lizzy `and then said` "Scala is so cool"

  //syntax sugar #5 : infix types
  class Composite[A,B]
  //normal way to write generic types
  //val composite: Composite[Int,String] = ???
  //can be written infix
  val composite: Int Composite String = ???

  class -->[A,B]
  val towards: Int --> String = ???

  //syntax sugar #6: update is VERY special, like an apply METHOD
  val anArray = Array(1,2,3)
  anArray(2) = 7 //rewritten to anArray.update(2,7)

  //syntax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember:Int = 0 //private for oo encapsulation
    def member:Int = internalMember //getter
    def member_=(value:Int): Unit =
      internalMember = value //setter


  }


}
