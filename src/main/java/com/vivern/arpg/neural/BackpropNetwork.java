package com.vivern.arpg.neural;

public final class BackpropNetwork extends Network {
   public BackpropNetwork(Layer[] layers) {
      super(layers);
   }

   public void randomize(float min, float max) {
      int size = this.getSize();

      for (int i = 0; i < size; i++) {
         Layer layer = this.getLayer(i);
         if (layer instanceof BackpropLayer) {
            ((BackpropLayer)layer).randomize(min, max);
         }
      }
   }

   public float computeError(float[] input, float[] goal) {
      if (input != null && input.length == this.getInputSize() && goal != null && goal.length == this.getOutputSize()) {
         int size = this.getSize();
         float[][] outputs = new float[size][];
         outputs[0] = this.getLayer(0).computeOutput(input);

         for (int i = 1; i < size; i++) {
            outputs[i] = this.getLayer(i).computeOutput(outputs[i - 1]);
         }

         Layer layer = this.getLayer(size - 1);
         int layerSize = layer.getSize();
         float[] error = new float[layerSize];
         float totalError = 0.0F;

         for (int i = 0; i < layerSize; i++) {
            error[i] = goal[i] - outputs[size - 1][i];
            totalError += Math.abs(error[i]);
         }

         return totalError;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public float learnPattern(float[] input, float[] goal, float rate, float momentum) {
      if (input != null && input.length == this.getInputSize() && goal != null && goal.length == this.getOutputSize()) {
         int size = this.getSize();
         float[][] outputs = new float[size][];
         outputs[0] = this.getLayer(0).computeOutput(input);

         for (int i = 1; i < size; i++) {
            outputs[i] = this.getLayer(i).computeOutput(outputs[i - 1]);
         }

         Layer layer = this.getLayer(size - 1);
         int layerSize = layer.getSize();
         float[] error = new float[layerSize];
         float totalError = 0.0F;

         for (int i = 0; i < layerSize; i++) {
            error[i] = goal[i] - outputs[size - 1][i];
            totalError += Math.abs(error[i]);
         }

         if (layer instanceof BackpropLayer) {
            ((BackpropLayer)layer).adjust(size == 1 ? input : outputs[size - 2], error, rate, momentum);
         }

         float[] prevError = error;
         Layer prevLayer = layer;
         int i = size - 2;

         while (i >= 0) {
            layer = this.getLayer(i);
            if (prevLayer instanceof BackpropLayer) {
               error = ((BackpropLayer)prevLayer).computeBackwardError(outputs[i], prevError);
            } else {
               error = prevError;
            }

            if (layer instanceof BackpropLayer) {
               ((BackpropLayer)layer).adjust(i == 0 ? input : outputs[i - 1], error, rate, momentum);
            }

            i--;
            prevError = error;
            prevLayer = layer;
         }

         return totalError;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public float learnPattern(float[] input, float[] goal, float rate, float momentum, float admissibleTotalError) {
      if (input != null && input.length == this.getInputSize() && goal != null && goal.length == this.getOutputSize()) {
         int size = this.getSize();
         float[][] outputs = new float[size][];
         outputs[0] = this.getLayer(0).computeOutput(input);

         for (int i = 1; i < size; i++) {
            outputs[i] = this.getLayer(i).computeOutput(outputs[i - 1]);
         }

         Layer layer = this.getLayer(size - 1);
         int layerSize = layer.getSize();
         float[] error = new float[layerSize];
         float totalError = 0.0F;

         for (int i = 0; i < layerSize; i++) {
            error[i] = goal[i] - outputs[size - 1][i];
            totalError += Math.abs(error[i]);
         }

         if (totalError > admissibleTotalError) {
            if (layer instanceof BackpropLayer) {
               ((BackpropLayer)layer).adjust(size == 1 ? input : outputs[size - 2], error, rate, momentum);
            }

            float[] prevError = error;
            Layer prevLayer = layer;
            int i = size - 2;

            while (i >= 0) {
               layer = this.getLayer(i);
               if (prevLayer instanceof BackpropLayer) {
                  error = ((BackpropLayer)prevLayer).computeBackwardError(outputs[i], prevError);
               } else {
                  error = prevError;
               }

               if (layer instanceof BackpropLayer) {
                  ((BackpropLayer)layer).adjust(i == 0 ? input : outputs[i - 1], error, rate, momentum);
               }

               i--;
               prevError = error;
               prevLayer = layer;
            }
         }

         return totalError;
      } else {
         throw new IllegalArgumentException();
      }
   }
}
