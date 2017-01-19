package net.warrigal.jaru

import net.warrigal.jaru.tenst.TensT

/**
  * Created by Tanner on 1/17/2017.
  */
class Layer[TInputs: TensT, TOutputs: TensT, TWeights: TensT]
(architecture: LayerArch[TInputs, TOutputs, TWeights],
 var weights: TWeights,
 var learnRate: Float) {

}
