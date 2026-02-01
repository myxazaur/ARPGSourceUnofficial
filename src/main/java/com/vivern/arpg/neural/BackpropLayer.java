package com.vivern.arpg.neural;

public interface BackpropLayer extends Layer {
   void randomize(float var1, float var2);

   float[] computeBackwardError(float[] var1, float[] var2);

   void adjust(float[] var1, float[] var2, float var3, float var4);
}
