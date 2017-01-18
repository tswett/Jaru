

/**
  * Created by Tanner on 1/10/2017.
  */
class AffineLayer[TInputs: VectT, TOutputs: VectT]
  extends
    LayerArch[TInputs, TOutputs, ProductTens[BiasVect[TInputs], TOutputs]] {
  final override def TInputsVectT: VectT[TInputs] =
    implicitly[VectT[TInputs]]

  final override def TOutputsVectT: VectT[TOutputs] =
    implicitly[VectT[TOutputs]]

  final override def TWeightsTensT:
  TensT[ProductTens[BiasVect[TInputs], TOutputs]] =
    implicitly[TensT[ProductTens[BiasVect[TInputs], TOutputs]]]

  final override def outputs(inputs: TInputs,
                             weights: ProductTens[BiasVect[TInputs], TOutputs]):
  TOutputs =
    weights.contractLeft(BiasVect.addBias(inputs))

  final override def weightGradients(inputs: TInputs,
                                     weights: ProductTens[BiasVect[TInputs],
                                       TOutputs],
                                     outputGradients: TOutputs):
  ProductTens[BiasVect[TInputs], TOutputs] =
    ProductTens.outerProduct(BiasVect.addBias(inputs), outputGradients)
}
