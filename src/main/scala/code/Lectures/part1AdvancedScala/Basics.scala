package code.Lectures.part1AdvancedScala

import scala.annotation.tailrec

object Basics extends App {

  val aCondition:Boolean = false
  val aConditionedVal = if(aCondition) 42 else 65
  //instructions vs expressions

  //compiler infers types
   val aCodeBlock = {
     if(aCondition) 54
     58
   }

  /*
  Unit type = void
  only returns side effects e.g println/log
   */
  val theUnit = println("heyy, Scala")

  //functions
  def aFunction(x:Int):Int = x +1

  //recursion: stack and tail
  @tailrec def factorial(n:Int, accumlator:Int):Int =
    if(n <=0) accumlator
    else factorial(n-1, n* accumlator)

  //OOP
  class Animal
  class Dog extends Animal
  val aDog:Animal = new Dog //subtyping polymorphism

  //abstract data types

  trait Carnivore{
    def eat(a:Animal):Unit
  }

  //can extends multiple traits
  class Crocodile extends Animal with Carnivore{
    override def eat(a: Animal): Unit = ???
  }

  //method notation
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog //scala similar to natural language

  //anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar!")
    /*
    Carnivore = trait. it's abstract so cant be instantiated by itself
    the compiler creates a new class by extending carnivore & overriding a new instance of
    of that class to eat
     */

    //generics
    abstract class MyList[+A] //variance
    //object and companions
    object MyList
    //case classes
    case class Person(name:String, age:Int)
    //exceptions and try/catch/finally

    val throwsException = throw new RuntimeException //type is nothing
    val aPotentialFailure = try{
      throw new RuntimeException
    }catch {
      case e:Exception => "Just caught an exception"
    }finally {
      println("some logs")
    }

    //functional programming
    /*
    in FP the apply method allows instances & singleton objects to be called as if they are functions
     */

    val anIncrementer = new Function[Int,Int] {
      override def apply(v1: Int): Int = v1 + 1
    }
      anIncrementer(2)

      val anonymousIncrementer = (x:Int) => x+1 //1st class language support for lambda
      List(1,2,3).map(anonymousIncrementer) //Higher Order Function

      //map, flatmap & filter are the basis for for-comprehension
      val pairs = for{
        nums <- List(1,2,3)
        char <- List('a','b','c')//if condition
      }yield nums + "-" + char
      //this will cross pair everything within both lists

      //scala collections: Seqs, Arrays, Lists, Vectors, Maps, Tuples
      val aMap = Map(
        "Alsahid" -> 456,
        "Benny" -> 321
      )

      //collections: Options
      val anOption = Some(4)

      //pattern matching
      val x = 2
      val order = x match {
        case 1 => "first"
        case 2 => "second"
        case 3 => "third"
        case _ => x+ "th"
      }

      val benny = Person("Benny",34)
      val greetings = benny match {
        case Person(n, _) => s"Hi $n, how are you?"
      }

  }
}
