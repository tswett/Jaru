package net.warrigal.jaru

import net.warrigal.jaru.tenst.TensImplicits._
import net.warrigal.jaru.tenst.{Scalar, TensT, VectT}

/**
  * Created by Tanner on 1/18/2017.
  */
class TransferLayer[T: VectT](transferFunction: TransferFunction)
  extends LayerArch[T, T, Scalar] {
  final override def TInputsVectT: VectT[T] =
    implicitly[VectT[T]]

  final override def TOutputsVectT: VectT[T] =
    implicitly[VectT[T]]

  final override def TWeightsTensT: TensT[Scalar] =
    implicitly[TensT[Scalar]]

  final override def outputs(inputs: T, weights: Scalar): T =
    TensT.fromINDArray(transferFunction.value(inputs.toINDArray))

  final override def weightGradients(inputs: T,
                                     weights: Scalar,
                                     outputGradients: T): Scalar = Scalar(0)
}
