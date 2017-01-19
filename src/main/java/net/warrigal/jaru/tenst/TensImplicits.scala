package net.warrigal.jaru.tenst

import org.nd4j.linalg.api.ndarray.INDArray

/**
  * Created by Tanner on 1/17/2017.
  */
object TensImplicits {

  implicit class RichTens[T: TensT](tensor: T) {

    def mul(scalar: Float): T =
      TensT.fromINDArray(tensor.toINDArray mul scalar)

    def toINDArray: INDArray = TensT.toINDArray(tensor)

    def sub(rightTensor: T): T =
      TensT.fromINDArray(tensor.toINDArray sub rightTensor.toINDArray)

  }

}
