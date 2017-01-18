import org.nd4j.linalg.api.ndarray.INDArray

/**
  * Created by Tanner on 1/15/2017.
  */
trait TensT[T] {
  def shape: Vector[Int]

  final def fromINDArray(array: INDArray): T = {
    if (array.shape.toVector == underlyingShape)
      fromINDArrayInner(array)
    else
      throw new IllegalArgumentException("The given array had the wrong shape.")
  }

  final def toINDArray(tensor: T): INDArray = {
    val result: INDArray = toINDArrayInner(tensor)
    if (result.shape.toVector == underlyingShape)
      result
    else
      throw new IllegalStateException("The returned array had the wrong shape.")
  }

  final def underlyingShape: Vector[Int] = rank match {
    case 0 => Vector(1, 1)
    case 1 => Vector(1, shape(0))
    case _ => shape
  }

  final def rank: Int = shape.length

  protected def fromINDArrayInner(array: INDArray): T

  protected def toINDArrayInner(tensor: T): INDArray
}

object TensT {
  def mul[T: TensT](leftTensor: T, rightTensor: T): T =
    fromINDArray[T](toINDArray(leftTensor) mul toINDArray(rightTensor))

  def fromINDArray[T: TensT](array: INDArray): T =
    implicitly[TensT[T]].fromINDArray(array)

  def toINDArray[T: TensT](tensor: T): INDArray =
    implicitly[TensT[T]].toINDArray(tensor)

  def shape[T: TensT]: Vector[Int] = implicitly[TensT[T]].shape

  def rank[T: TensT]: Int = implicitly[TensT[T]].rank

  def underlyingShape[T: TensT]: Vector[Int] =
    implicitly[TensT[T]].underlyingShape
}
