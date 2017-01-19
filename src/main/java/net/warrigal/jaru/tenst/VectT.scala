package net.warrigal.jaru.tenst

/**
  * Created by Tanner on 1/15/2017.
  */
trait VectT[T] extends TensT[T] {
  final override def shape: Vector[Int] = Vector(length)

  def length: Int
}

