package code.Lectures.part1AdvancedScala

object Monads extends App {

  trait Attempt[+A]{
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }

  object Attempt{
    def apply[A](a : => A) : Attempt[A] =
    try{
      Success(a)
    }catch {
      case e: Throwable => Fail(e)
    }
  }

  case class Success[+A](value: A) extends Attempt[A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B] =
      try{
        f(value)
      }catch {
        case e:Throwable => Fail(e)
      }
  }

  case class Fail(e: Throwable) extends Attempt[Nothing] {
    def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this
  }

  /*
  monad laws
  left-identity
  unit.flatMap(f) = f(x)
  Attempt(x).flatMap(f) = f(x) //only makes sense for success case
  Success(x).flatMap = F(x) //proof

  right-identity
  attempt.flatMap(unit) = attempt
  Success(x).flatMap(x => Attempt(x)) = Attempt(x) = Success(x)

  associativity
  attempt.flatMap(f).flatMap(g) == attempt.flatMap(x => f(x).flatMap(g))
  Fail(e).flatMap(f).flatMap(g) = Fail(e)
  Fail(e).flatMap(x => f(x).flatMap(g)) = Fail(e)

  Success(v).flatMap(f).flatMap=
    f(v).flatMap(g) OR Fail(e)

    Success(v).flatMap(x => f(x).flatMap(g)) =
      f(v).flatMap(g) OR Fail(e)

   */

  val attempt = Attempt{
    throw new RuntimeException("My Own Monad, yes!")
  }

  println(attempt)

  //lazy Monad implementation
  class Lazy[+A](value : => A) {
    private lazy val internalValue = value

    def use: A = internalValue
    def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(internalValue)
  }

  object Lazy{
    def apply[A](value: => A): Lazy[A] = new Lazy(value) //unit
  }

  val lazyInstance = Lazy{
    println("Today I feel like being lazy")
    42
  }

  println(lazyInstance.use)

  val flatMapInstance = lazyInstance.flatMap(x => Lazy{
    10 * x
  })

  val flatMapInstance2 = lazyInstance.flatMap(x => Lazy{
    10 * x
  })

  flatMapInstance.use
  flatMapInstance2.use

  //2- map and flattern in terms of flatmap
  /*
  Monad[T]{ //List
  def flatMap[B](f:T => Monad[B]): Monad[B] = ...(implemented)

  def map[B](f:T => B): Monad[B] = flatMap(x => unit(f(x)) //Monad[B]
  def flatten(m: Monad[Monad[T]]): Monad[T] = m.flatMap((x:Monad[T]) => x)

  List(1,2,3).map(_ *2) = List(1,2,3).flatMap(x => List(x*2))
  List(List(1,2), List(3,4).flatten - List(List(1,2), List(3,4)).flatMap( x => x) = List(1,2,3,4)
  }
   */
}
