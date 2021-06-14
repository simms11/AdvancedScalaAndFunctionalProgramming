package code.Lectures.part1AdvancedScala

object Currying extends App{

  //curried functions
  val supperAdder: Int => Int => Int =
    x => y => x +y

  val add3 = supperAdder(3)(5)// Int => Int = y => 3 + y
  println(add3)

  //Method
  def curriedAdder(x:Int)(y:Int): Int = x + y //curried method

  // use this method with fewer params
  val add4: Int => Int = curriedAdder(4)
  //Transforming a method into a function is called Lifting
  //Lifting is also known as ETA-EXPANSION.

  //functions != method (JVM limitation)
  def inc(x:Int):Int = x+1
  List(1,2,3).map(x => inc(x)) //ETA-EXPANSION

  //Partial Functions force the compiler to do ETA-EXPANSION when we want
  val add5 = curriedAdder(5) _ //_ tells compiler to convert expression to Int => Int

  val simpleAddFunction = (x:Int, y:Int) => x + y
  def simpleAddMethod(x:Int, y:Int) = x + y
  def curriedAddMethod(x:Int)(y:Int) = x +y
  //add7:Int => Int = y => 7+ y

  val add7 = (x:Int) => simpleAddFunction(7,x)
  val add7_2 = simpleAddFunction.curried(7)
  val add7_3 = curriedAddMethod(7) _
  val add7_4 = curriedAddMethod(7)(_)

  //underscores are powerful
  def concatenator(a: String, b: String, c:String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _:String, ", nice to meet you!")
  println(insertName("Alsahid"))

  val fillInTheBlanks = concatenator("Hello ", _:String, _:String)
  println(fillInTheBlanks("Alsahid ","Scala is cool." ))

  def curried(x:String):Int = x.toInt

  /*
  exercises
  produce a list of numbers and return their string representation with different formats
  use the %4.2f, %8.6f and %14.12f with a curriend formatter function.
   */

  def curriedFormatter(s: String)(number:Double):String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1,9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _
  val seriousFormat = curriedFormatter("%8.f6") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(simpleFormat))
  println(numbers.map(preciseFormat))


  def byName(n: => Int) = n +1
  def byFunction(f: ()=> Int) = f()+1

  def method: Int = 42
  def parentMethod(): Int = 42

  byName(23)
  byName(method)
  byFunction(parentMethod)
}
