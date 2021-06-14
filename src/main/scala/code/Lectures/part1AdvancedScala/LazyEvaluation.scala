package code.Lectures.part1AdvancedScala

object LazyEvaluation extends App{

  //lazy delays the evaluation of values
  lazy val x:Int = throw new RuntimeException

  //examples of implications
  //with size effects
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }
  def simpleCondition: Boolean = false
  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no")

  //in conjunction with call by name
  def byNameMethod(n : => Int): Int = n + n + n +1
  def retrieveMagicValue = {
    println("waiting...")
    Thread.sleep(1000)
    42
  }
  println(byNameMethod(retrieveMagicValue))
  /*
  waiting time was 3secs instead of one
  value n is evaluated 3 times

  lazy val would be a better practice as its evaluated once
  */
  def byNameMethod_2(n : => Int): Int ={
    lazy val t = n
    t + t + t +1
    //  this technique is called CALL BY NEED
  }

  println(byNameMethod_2(retrieveMagicValue))

  //filtering with lazy vals
  def lessThan30(i:Int):Boolean = {
    println(s"$i is less than 30")
    i <30
  }
  def greaterThan20(i:Int): Boolean = {
    print(s"$i is greater than 20")
    i > 20
  }

  val numbers = List(1,25,40,5,24)
  val lt30 =  numbers.filter(lessThan30)
  val gt20 = lt30.filter(greaterThan20)
  print(gt20)

  val lt30Lazy = numbers.withFilter(lessThan30)//withFiler comes with a Lazy val... under the hood
  val gt20Lazy = lt30Lazy.withFilter(greaterThan20)
  println
  gt20Lazy.foreach(println)

  //for-comprehensions use withFilter with guards
  for{
    a <-List(1,2,3) if a %2 == 0
  }yield  a +1
  //same as
  List(1,2,3).withFilter(_ % 2 == 0).map(_+1)//List[Int]

  /*
  implementing a lazily evaluated singly linked STREAM of elements
  naturals = MyStream.from(1)(x => x +1) = stream of natural numbers (potentially infinite)
  naturals.take(100).foreach(println) //lazily evaluates stream of the first 100 naturals (finate stream)
  naturals.foreach(println) // will crash infinite
   */



}
