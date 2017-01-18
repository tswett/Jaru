import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j

/**
  * Created by Tanner on 1/15/2017.
  */
class VectorSpace(val length: Int) {

  final def From(data: Float*): Vect =
    TensT.fromINDArray[Vect](Nd4j.create(data.toArray))

  class Vect(val array: INDArray) {
    if (array.shape.toVector != Vector(1, length))
      throw new IllegalArgumentException("The given array had the wrong shape.")
  }

  object Vect {

    implicit object LengthVectT extends VectT[Vect] {
      //implicit def lengthVectT
      final override def length: Int = VectorSpace.this.length

      final override protected def
      fromINDArrayInner(array: INDArray): Vect =
        new Vect(array)

      final override protected def
      toINDArrayInner(tensor: Vect): INDArray =
        tensor.array
    }

  }

}
