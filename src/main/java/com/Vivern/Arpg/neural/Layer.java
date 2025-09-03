package com.Vivern.Arpg.neural;

import java.io.Serializable;

public interface Layer extends Serializable {
   int getInputSize();

   int getSize();

   float[] computeOutput(float[] var1);

   String export();
}
