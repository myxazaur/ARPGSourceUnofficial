package com.Vivern.Arpg.neural;

public final class WTALayer implements BackpropLayer {
   private final int size;
   private float minLevel;

   public WTALayer(int size, float minLevel) {
      if (size < 1) {
         throw new IllegalArgumentException();
      } else {
         this.size = size;
         this.minLevel = minLevel;
      }
   }

   @Override
   public int getInputSize() {
      return this.size;
   }

   @Override
   public int getSize() {
      return this.size;
   }

   @Override
   public float[] computeOutput(float[] input) {
      if (input != null && input.length == this.size) {
         int winner = 0;

         for (int i = 1; i < this.size; i++) {
            if (input[i] > input[winner]) {
               winner = i;
            }
         }

         float[] output = new float[this.size];
         if (this.minLevel > 0.0F) {
            float level = Float.MAX_VALUE;

            for (int ix = 0; ix < this.size; ix++) {
               if (ix != winner && Math.abs(input[ix] - input[winner]) < level) {
                  level = Math.abs(input[ix] - input[winner]);
               }
            }

            if (level < this.minLevel) {
               return output;
            }
         }

         output[winner] = 1.0F;
         return output;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public void randomize(float min, float max) {
   }

   @Override
   public float[] computeBackwardError(float[] input, float[] error) {
      if (input != null && input.length == this.size && error != null && error.length == this.size) {
         float[] backwardError = new float[this.size];
         float[] output = this.computeOutput(input);

         for (int i = 0; i < this.size; i++) {
            backwardError[i] = error[i] + output[i] - input[i];
         }

         return backwardError;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public void adjust(float[] input, float[] error, float rate, float momentum) {
   }

   public float getMinLevel() {
      return this.minLevel;
   }

   public void setMinLevel(float minLevel) {
      this.minLevel = minLevel;
   }

   @Override
   public String export() {
      return "";
   }
}
