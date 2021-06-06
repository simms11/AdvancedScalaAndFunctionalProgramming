package code.Lectures.part1AdvancedScala

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean) {

  def apply(elem: A): Boolean = contains(elem)

  def contains(elem:A): Boolean
  def +(elem:A): MySet[A]
  def ++(anotherSet: MySet[A]):MySet[A]

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f:A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A=> Unit): Unit

  def -(ele: A): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A] //difference
  def &(anotherSet: MySet[A]): MySet[A] //intersection

  def unary_! : MySet[A]
}

class EmptySet[A] extends MySet[A]{
  def contains(elem:A): Boolean = false
  def +(elem:A): MySet[A] = new NonEmptySet[A](elem, this)
  def ++(anotherSet: MySet[A]):MySet[A] = anotherSet

  def map[B](f: A => B): MySet[B] = new EmptySet[B]
  def flatMap[B](f:A => MySet[B]): MySet[B] = new EmptySet[B]
  def filter(predicate: A => Boolean): MySet[A] = this
  def foreach(f: A=> Unit): Unit = ()

  def -(ele: A): MySet[A] = this
  def --(anotherSet: MySet[A]): MySet[A] = this
  def &(anotherSet: MySet[A]): MySet[A] =this

  def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)


}

class PropertyBasedSet[A](property: A => Boolean) extends  MySet[A] {
    override def contains(elem: A): Boolean = property(elem)
    override def +(elem: A): MySet[A] =
      new PropertyBasedSet[A](x => property(x)|| x == elem)

    override def ++(anotherSet: MySet[A]): MySet[A] =
      new PropertyBasedSet[A](x => property(x) || anotherSet(x))

  //all integers => (_ % 3 => [0 1 2]
  override def map[B](f: A => B): MySet[B] = politelyFail
    override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail
    override def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](x =>
    property(x) && predicate(x))

    override def foreach(f: A => Unit): Unit = politelyFail
    override def -(ele: A): MySet[A] = filter(x => x != ele)
    override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)
    override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
    override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !property(x))

  def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole!!")

}
class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  def contains(elem:A): Boolean =
    elem == head || tail.contains(elem)

  def +(elem:A): MySet[A] =
    if(this contains elem) this
    else new NonEmptySet[A](elem, this)

  def ++(anotherSet: MySet[A]):MySet[A] =
    tail ++ anotherSet + head

  def map[B](f: A => B): MySet[B] = (tail map f) + f(head)
  def flatMap[B](f:A => MySet[B]): MySet[B] = (tail flatMap f) ++ f(head)
  def filter(predicate: A => Boolean): MySet[A] = {
    val filteredTail = tail filter predicate
    if(predicate(head)) filteredTail + head
    else filteredTail
  }

  def foreach(f: A=> Unit): Unit = {
    f(head)
    tail foreach f
  }
  def -(elem: A): MySet[A] =
    if(head == elem) tail
    else tail - elem + head

  def --(anotherSet:MySet[A]): MySet[A] = filter(x => !anotherSet(x))
  def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  def unary_! : MySet[A] = new PropertyBasedSet[A](x => !this.contains(x))

}

object MySet{
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], accumlator:MySet[A]): MySet[A] =
      if(valSeq.isEmpty) accumlator
      else buildSet(valSeq.tail, accumlator + valSeq.head)

    buildSet(values.toSeq, new EmptySet[A])
  }
}

object  MySetPlayground extends App{
  val s = MySet(1,2,3,4)
  s + 5 foreach println

  val negative = !s //s.unary_! = all the naturals not equal to 1,2,3,4

  println(negative(2))
  println(negative(5))

  val negativeEven = negative.filter(_ % 2 == 0)
  println(negativeEven(5))

  val negativeEven5 = negativeEven +5
  println(negativeEven5(5))
}