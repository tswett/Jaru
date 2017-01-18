/**
  * Created by Tanner on 1/17/2017.
  */
object TensImplicits {

  implicit class RichTens[T: TensT](tensor: T) {
    def mul(scalar: Float): T =
      TensT.fromINDArray(TensT.toINDArray(tensor) mul scalar)

    def sub(rightTensor: T): T =
      TensT.fromINDArray(TensT.toINDArray(tensor) sub
        TensT.toINDArray(rightTensor))

  }

}