

/**
  * Created by Tanner on 1/9/2017.
  */
trait LayerArch[TInputs, TOutputs, TWeights] {
  implicit def TInputsVectT: VectT[TInputs]

  implicit def TOutputsVectT: VectT[TOutputs]

  implicit def TWeightsTensT: TensT[TWeights]

  def outputs(inputs: TInputs, weights: TWeights): TOutputs

  def weightGradients(inputs: TInputs,
                      weights: TWeights,
                      outputGradients: TOutputs): TWeights
}
