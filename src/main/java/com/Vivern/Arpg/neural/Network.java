package com.Vivern.Arpg.neural;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Network implements Serializable {
   private Layer[] layers;

   public Network(Layer[] layers) {
      if (layers != null && layers.length != 0) {
         int size = layers.length;

         for (int i = 0; i < size; i++) {
            if (layers[i] == null || i > 1 && layers[i].getInputSize() != layers[i - 1].getSize()) {
               throw new IllegalArgumentException();
            }
         }

         this.layers = layers;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public final int getInputSize() {
      return this.layers[0].getInputSize();
   }

   public final int getOutputSize() {
      return this.layers[this.layers.length - 1].getSize();
   }

   public final int getSize() {
      return this.layers.length;
   }

   public final Layer getLayer(int index) {
      return this.layers[index];
   }

   public float[] computeOutput(float[] input) {
      if (input != null && input.length == this.getInputSize()) {
         float[] output = input;
         int size = this.layers.length;

         for (int i = 0; i < size; i++) {
            output = this.layers[i].computeOutput(output);
         }

         return output;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void saveToFile(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      } else {
         try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(this);
            outputStream.close();
         } catch (Exception var3) {
            throw new IllegalArgumentException(var3);
         }
      }
   }

   public static Network loadFromFile(String fileName) {
      if (fileName == null) {
         throw new IllegalArgumentException();
      } else {
         Object network = null;

         try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
            network = inputStream.readObject();
            inputStream.close();
         } catch (Exception var3) {
            throw new IllegalArgumentException(var3);
         }

         return (Network)network;
      }
   }

   public void printExport() {
      System.out.print("Layer[] layers = new Layer[] { ");
      System.out.print("\n");

      for (int i = 0; i < this.getSize(); i++) {
         Layer layer = this.getLayer(i);
         String exp = layer.export();
         System.out.print(exp);
         if (i < this.getSize() - 1) {
            System.out.print(",");
         }

         System.out.print("\n");
      }

      System.out.print("};");
   }
}
