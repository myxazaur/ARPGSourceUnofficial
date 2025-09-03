package com.Vivern.Arpg.neural;

public final class SigmoidLayer implements BackpropLayer {
   private final int WEIGHT = 0;
   private final int DELTA = 1;
   private final int inputSize;
   private final boolean bipolar;
   private float[][][] matrix;

   public SigmoidLayer(float[][][] matrix, int inputSize, boolean bipolar) {
      if (inputSize < 1) {
         throw new IllegalArgumentException();
      } else {
         this.matrix = matrix;
         this.inputSize = inputSize;
         this.bipolar = bipolar;
      }
   }

   public SigmoidLayer(int inputSize, int size, boolean bipolar) {
      if (inputSize >= 1 && size >= 1) {
         this.matrix = new float[size][inputSize + 1][2];
         this.inputSize = inputSize;
         this.bipolar = bipolar;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public SigmoidLayer(int inputSize, int size) {
      this(inputSize, size, true);
   }

   @Override
   public int getInputSize() {
      return this.inputSize;
   }

   @Override
   public int getSize() {
      return this.matrix.length;
   }

   @Override
   public float[] computeOutput(float[] input) {
      if (input != null && input.length == this.inputSize) {
         int size = this.matrix.length;
         float[] output = new float[size];

         for (int i = 0; i < size; i++) {
            output[i] = this.matrix[i][0][0];

            for (int j = 0; j < this.inputSize; j++) {
               output[i] += input[j] * this.matrix[i][j + 1][0];
            }

            if (this.bipolar) {
               output[i] = (float)Math.tanh(output[i]);
            } else {
               output[i] = 1.0F / (1.0F + (float)Math.exp(-output[i]));
            }
         }

         return output;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public void randomize(float min, float max) {
      int size = this.matrix.length;

      for (int i = 0; i < size; i++) {
         for (int j = 0; j < this.inputSize + 1; j++) {
            this.matrix[i][j][0] = min + (max - min) * (float)Math.random();
            this.matrix[i][j][1] = 0.0F;
         }
      }
   }

   @Override
   public float[] computeBackwardError(float[] input, float[] error) {
      if (input != null && input.length == this.inputSize && error != null && error.length == this.matrix.length) {
         float[] output = this.computeOutput(input);
         int size = this.matrix.length;
         float[] backwardError = new float[this.inputSize];

         for (int i = 0; i < this.inputSize; i++) {
            backwardError[i] = 0.0F;

            for (int j = 0; j < size; j++) {
               backwardError[i] += error[j] * this.matrix[j][i + 1][0] * (this.bipolar ? 1.0F - output[j] * output[j] : output[j] * (1.0F - output[j]));
            }
         }

         return backwardError;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public void adjust(float[] input, float[] error, float rate, float momentum) {
      if (input != null && input.length == this.inputSize && error != null && error.length == this.matrix.length) {
         float[] output = this.computeOutput(input);
         int size = this.matrix.length;

         for (int i = 0; i < size; i++) {
            float grad = error[i] * (this.bipolar ? 1.0F - output[i] * output[i] : output[i] * (1.0F - output[i]));
            this.matrix[i][0][1] = rate * grad + momentum * this.matrix[i][0][1];
            this.matrix[i][0][0] = this.matrix[i][0][0] + this.matrix[i][0][1];

            for (int j = 0; j < this.inputSize; j++) {
               this.matrix[i][j + 1][1] = rate * input[j] * grad + momentum * this.matrix[i][j + 1][1];
               this.matrix[i][j + 1][0] = this.matrix[i][j + 1][0] + this.matrix[i][j + 1][1];
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public String export() {
      String exportString = "new SigmoidLayer(new float[][][] ";
      String matrixString = NeuralHelper.matrixToString(this.matrix, 'F');
      exportString = exportString + matrixString;
      return exportString + ", " + this.inputSize + ", " + this.bipolar + ")";
   }
}
