package net.warrigal.jaru

import org.nd4j.linalg.api.ndarray.INDArray

/**
  * Created by Tanner on 1/18/2017.
  */
trait TransferFunction {
  def value(input: INDArray): INDArray

  def derivative(input: INDArray): INDArray
}
