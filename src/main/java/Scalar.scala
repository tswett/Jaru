import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4s.Implicits._

/**
  * Created by Tanner on 1/15/2017.
  */
class Scalar(val value: Float)

object Scalar {

  implicit object LengthVectT extends VectT[Scalar] {
    final override def length: Int = 1

    final override protected def
    fromINDArrayInner(array: INDArray): Scalar =
      if (array.shape.toVector == Vector(1, 1))
        new Scalar(array.getFloat(0, 0))
      else
        throw new IllegalArgumentException("The given array had the wrong shape.")

    final override protected def
    toINDArrayInner(tensor: Scalar): INDArray =
      Array(tensor.value).toNDArray
  }

}

