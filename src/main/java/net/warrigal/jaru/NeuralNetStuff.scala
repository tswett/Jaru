package net.warrigal.jaru

import net.warrigal.jaru.tenst.TensImplicits._
import net.warrigal.jaru.tenst._
import org.nd4s.Implicits._

/**
  * Created by Tanner on 1/8/2017.
  */
object NeuralNetStuff {
  type WeightTens = ProductTens[BiasVect[boolSpace.Vect], Scalar]
  //var weights: INDArray = Array(1, 2, 3).toNDArray
  val boolSpace: VectorSpace = new VectorSpace(2)
  val layer: AffineLayer[boolSpace.Vect, Scalar] =
    new AffineLayer[boolSpace.Vect, Scalar]
  val learnRate: Float = 0.03f
  var weights: WeightTens = {
    val weightsVect: BiasVect[boolSpace.Vect] =
      TensT.fromINDArray[BiasVect[boolSpace.Vect]](Array(1, 2, 3).toNDArray)
    ProductTens.tileFromLeft[BiasVect[boolSpace.Vect], Scalar](weightsVect)
  }

  final def loss(input1: Boolean, input2: Boolean): Float =
    Math.pow(desiredOutput(input1, input2) - output(input1, input2), 2).toFloat

  final def train(input1: Boolean, input2: Boolean): Unit = {
    weights = weights sub (gradient(input1, input2) mul learnRate)
  }

  final def gradient(input1: Boolean, input2: Boolean): WeightTens = {
    val dLoss_dOutput: Float =
      2 * (output(input1, input2) - desiredOutput(input1, input2))
    val dOutput_dWeights_vect: BiasVect[boolSpace.Vect] =
      BiasVect.addBias(booleanNetVector(input1, input2))
    val dOutput_dWeights: WeightTens =
      ProductTens.tileFromLeft(dOutput_dWeights_vect)
    dOutput_dWeights mul dLoss_dOutput
  }

  //  final def output(input1: Boolean, input2: Boolean): Float =
  //    booleanNetVector(input1, input2).mul(weights).sumNumber.floatValue
  final def output(input1: Boolean, input2: Boolean): Float =
  layer.outputs(booleanNetVector(input1, input2), weights).value

  //  final def booleanNetVector(input1: Boolean, input2: Boolean): INDArray =
  //    Array(if (input1) 1 else 0, if (input2) 1 else 0, 1).toNDArray
  final def booleanNetVector(input1: Boolean, input2: Boolean):
  boolSpace.Vect =
  boolSpace.From(if (input1) 1.0f else 0.0f, if (input2) 1.0f else 0.0f)

  final def desiredOutput(input1: Boolean, input2: Boolean): Float =
    if (input1 == input2) 0 else 1
}
