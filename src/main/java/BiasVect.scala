import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.nd4s.Implicits._
// import INDArrayExtensions._

/**
  * Created by Tanner on 1/15/2017.
  */
class BiasVect[T: VectT](val array: INDArray) {
  if (array.shape.toVector != Vector(1, BiasVect.length[T]))
    throw new IllegalArgumentException("The given array had the wrong shape.")
}

object BiasVect {

  final def addBias[T: VectT](vector: T): BiasVect[T] = {
    val vectorIND: INDArray = TensT.toINDArray(vector)
    val biasedVectorIND: INDArray = Nd4j.hstack(vectorIND, Array(1).toNDArray)
    TensT.fromINDArray[BiasVect[T]](biasedVectorIND)
  }

  def length[T: VectT
  ]: Int = implicitly[VectT[T]].length + 1

  implicit def biasVectT[T: VectT]: VectT[BiasVect[T]] =
    new VectT[BiasVect[T]] {
      override def length: Int = BiasVect.length[T]

      override protected def fromINDArrayInner(array: INDArray): BiasVect[T] =
        new BiasVect[T](array)

      override protected def toINDArrayInner(vector: BiasVect[T]): INDArray =
        vector.array
    }


}
