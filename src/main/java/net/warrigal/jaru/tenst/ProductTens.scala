package net.warrigal.jaru.tenst

import org.nd4j.linalg.api.ndarray.INDArray

/**
  * Created by Tanner on 1/15/2017.
  */
class ProductTens[T: TensT, U: TensT](val array: INDArray) {
  val shape: Vector[Int] = ProductTens.shape[T, U]

  if (array.shape.toVector != shape)
    throw new IllegalArgumentException("The given array had the wrong shape.")

  final def contractLeft(leftTensor: T): U = {
    //    val leftTensorIND: INDArray = TensT.toINDArray(leftTensor)
    //    val leftTensorTiled: INDArray = leftTensorIND.repmat(shape: _*)
    val leftTensorTiled: ProductTens[T, U] =
    ProductTens.tileFromLeft[T, U](leftTensor)
    val product: ProductTens[T, U] = TensT.mul(leftTensorTiled, this)
    val productIND: INDArray = TensT.toINDArray(product)
    val sumDimensions: Range =
      TensT.rank[T] until TensT.rank[ProductTens[T, U]]
    val resultIND: INDArray = productIND.sum(sumDimensions: _*)
    TensT.fromINDArray[U](resultIND)
  }

  final def commute: ProductTens[U, T] = {
    val thisIND: INDArray = TensT.toINDArray(this)
    val dimensionsOfT: Seq[Int] = 0 until TensT.rank[T]
    val dimensionsOfU: Seq[Int] =
      TensT.rank[T] until TensT.rank[ProductTens[T, U]]
    val thisCommuted: INDArray =
      thisIND.permute(dimensionsOfU ++ dimensionsOfT: _*)
    TensT.fromINDArray[ProductTens[U, T]](thisCommuted)
  }
}

object ProductTens {

  def tileFromLeft[T: TensT, U: TensT](leftTensor: T): ProductTens[T, U] =
    tileFromRight[U, T](leftTensor).commute

  def tileFromRight[T: TensT, U: TensT](rightTensor: U): ProductTens[T, U] = {
    val rightTensorIND: INDArray = TensT.toINDArray(rightTensor)
    val rightTensorTiled: INDArray = rightTensorIND.repmat(shape[T, U]: _*)
    TensT.fromINDArray[ProductTens[T, U]](rightTensorTiled)
  }

  def outerProduct[T: TensT, U: TensT](leftTensor: T, rightTensor: U):
  ProductTens[T, U] = {
    val leftTensorTiled: ProductTens[T, U] = tileFromLeft[T, U](leftTensor)
    val rightTensorTiled: ProductTens[T, U] = tileFromRight[T, U](rightTensor)
    TensT.mul(leftTensorTiled, rightTensorTiled)
  }

  def shape[T: TensT
  , U: TensT
  ]: Vector[Int] =
    implicitly[TensT[T]].shape ++ implicitly[TensT[U]].shape

  implicit def productTensT[T: TensT, U: TensT]: TensT[ProductTens[T, U]] =
    new TensT[ProductTens[T, U]] {
      override def shape: Vector[Int] = ProductTens.shape[T, U]

      override protected def
      fromINDArrayInner(array: INDArray): ProductTens[T, U] =
        new ProductTens[T, U](array)

      override protected def
      toINDArrayInner(tensor: ProductTens[T, U]): INDArray =
        tensor.array
    }

  //  def tileFromLeft[T: TensT, U: TensT](leftTensor: T): ProductTens[T, U] = {
  //    val leftTensorIND: INDArray = TensT.toINDArray(leftTensor)
  //    val leftTensorTiled: INDArray = leftTensorIND.repmat(shape[T, U]: _*)
  //    TensT.fromINDArray[ProductTens[T, U]](leftTensorTiled)
  //  }
  //
  //  def tileFromRight[T: TensT, U: TensT](rightTensor: U): ProductTens[T, U] =
  //    tileFromLeft[U,T](rightTensor).commute


}
