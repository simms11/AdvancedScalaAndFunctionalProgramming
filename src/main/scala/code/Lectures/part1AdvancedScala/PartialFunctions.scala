package code.Lectures.part1AdvancedScala

object PartialFunctions extends App {

  val aFunction = (x:Int) => x +1 //Function[Int,Int] === Int => Int

  val aFussyFunction = (x:Int) =>
    if(x ==1) 42
    else if (x ==2) 56
    else if(x == 5) 999
    else throw new FunctionNotApplicableException

  class FunctionNotApplicableException extends RuntimeException

  val aNicerFussyFunction = (x:Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
  //{1,2,5} => Int

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 55
    case 5 => 999
  }// partial function value

  println(aPartialFunction(2))
  println(aPartialFunction.isDefinedAt(44))

  //partial function can be lifted from Int => Option[Int]
  val lifted = aPartialFunction.lift
  println(lifted(2))
  println(lifted(883))//will return None

  //partial function chaining
  val pfChain = aPartialFunction.orElse[Int,Int]{
    case 45 => 67
  }
  println(pfChain(45))

  //PF extend normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  //Higher Order Functions accept partial functions as well

  val aMappedList = List(1,2,3).map{
    case 1 => 42
    case 2 => 78
    case 3 => 8000
  }
  println(aMappedList)

  val aManualFussyFunction = new PartialFunction[Int,Int] {
    override def apply(x:Int):Int = x match {
      case 1 => 42
      case 2 => 65
      case 5 => 999
    }

    override def isDefinedAt(x: Int): Boolean =
      x ==1 || x ==2 || x ==5
  }

  //implementing a chatbox partial function

  val chatBox:PartialFunction[String,String] ={
    case "hello" => "Hi I am bot"
    case "bye" => "Sad to see you go :("
  }

  scala.io.Source.stdin.getLines().map(chatBox).foreach(println)

}
