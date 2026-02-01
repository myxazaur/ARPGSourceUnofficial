package com.vivern.arpg.neural;

import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.CreateItemFile;
import com.vivern.arpg.tileentity.WriteBlank;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class NeuralHelper {
   public static Random rand = new Random();
   public static ArrayList<float[]> datasetNOISE;
   public static ArrayList<float[]> datasetSPELL;
   public static ArrayList<float[]> datasetWASTE;
   public static BackpropNetwork network;

   public static void learn() {
      String learnTo = "sowilo";
      if (Debugger.floats[11] == 1.0F) {
         String relativePath = "/Users/Vivern/Desktop/Modding/networks/" + learnTo + ".txt";
         network.saveToFile(relativePath);
         Debugger.floats[11] = 0.0F;
      } else if (Debugger.floats[12] == 1.0F) {
         String relativePath = "/Users/Vivern/Desktop/Modding/networks/" + learnTo + ".txt";
         network = (BackpropNetwork)Network.loadFromFile(relativePath);
         Debugger.floats[12] = 0.0F;
      } else {
         if (network == null) {
            int inputsCount = 600;
            Layer[] layers = new Layer[]{new SigmoidLayer(inputsCount, 300), new SigmoidLayer(300, 30), new SigmoidLayer(30, 10), new SigmoidLayer(10, 1)};
            network = new BackpropNetwork(layers);
            network.randomize(-1.0F, 1.0F);
         }

         if (datasetNOISE == null) {
            datasetNOISE = loadDatasetImages("noise", 1500, 20, 30);
            datasetSPELL = loadDatasetImages(learnTo, 1000, 20, 30);
            datasetWASTE = loadDatasetImages(learnTo + "_w", 2000, 20, 30);
         }

         float[] answerTrue = new float[]{1.0F};
         float[] answerFalse = new float[]{0.0F};
         float learnSpeed = Debugger.floats[8];
         float learnMomentum = Debugger.floats[10];

         for (int i = 0; i < Debugger.floats[7]; i++) {
            if (Debugger.floats[9] == 1.0F) {
               network.learnPattern(datasetSPELL.get(rand.nextInt(datasetSPELL.size())), answerTrue, learnSpeed, learnMomentum);
            }

            if (Debugger.floats[9] == 2.0F) {
               network.learnPattern(datasetNOISE.get(rand.nextInt(datasetNOISE.size())), answerFalse, learnSpeed, learnMomentum);
            }

            if (Debugger.floats[9] == 0.0F) {
               if (rand.nextFloat() < 0.33) {
                  network.learnPattern(datasetSPELL.get(rand.nextInt(datasetSPELL.size())), answerTrue, learnSpeed, learnMomentum);
               } else if (rand.nextFloat() < 0.5) {
                  network.learnPattern(datasetNOISE.get(rand.nextInt(datasetNOISE.size())), answerFalse, learnSpeed, learnMomentum);
               } else {
                  network.learnPattern(datasetWASTE.get(rand.nextInt(datasetWASTE.size())), answerFalse, learnSpeed, learnMomentum, 0.35F);
               }
            }
         }

         float midErrorSpell = 0.0F;

         for (int i = 0; i < 20; i++) {
            midErrorSpell += network.computeError(datasetSPELL.get(rand.nextInt(datasetSPELL.size())), answerTrue);
         }

         midErrorSpell /= 20.0F;
         float midErrorNoise = 0.0F;

         for (int i = 0; i < 20; i++) {
            midErrorNoise += network.computeError(datasetNOISE.get(rand.nextInt(datasetNOISE.size())), answerFalse);
         }

         midErrorNoise /= 20.0F;
         float midErrorWasted = 0.0F;

         for (int i = 0; i < 20; i++) {
            midErrorWasted += network.computeError(datasetWASTE.get(rand.nextInt(datasetWASTE.size())), answerFalse);
         }

         midErrorWasted /= 20.0F;
         System.out.println("dataset size: s " + datasetSPELL.size() + "  n " + datasetNOISE.size());
         System.out.println("middle errors: s " + midErrorSpell + "  n " + midErrorNoise + "  w " + midErrorWasted);
      }
   }

   public static ArrayList<float[]> loadDatasetImages(String folder, int max, int rescaleWidth, int rescaleHeight) {
      ArrayList<float[]> list = new ArrayList<>();

      for (int i = 0; i <= max; i++) {
         String path = "/Users/Vivern/Desktop/Modding/dataset/" + folder + "/" + i + ".png";
         BufferedImage rawImage = getImageInPC(path);
         if (rawImage != null) {
            BufferedImage image = new BufferedImage(rescaleWidth, rescaleHeight, 2);
            CreateItemFile.resampleImageBilinear(rawImage, image);
            float[] data = bufferedImageToNetworkInput(image);
            list.add(data);
         }
      }

      return list;
   }

   public static float[] bufferedImageToNetworkInput(BufferedImage image) {
      float[] data = new float[image.getWidth() * image.getHeight()];

      for (int h = 0; h < image.getHeight(); h++) {
         for (int w = 0; w < image.getWidth(); w++) {
            Vec3d color = ColorConverters.DecimaltoRGB(image.getRGB(w, h));
            data[h * image.getWidth() + w] = MathHelper.clamp(
               (float)(1.0 - (color.x + color.y + color.z) / 3.0), 0.0F, 1.0F
            );
         }
      }

      return data;
   }

   public static float[] writeBlankToNetworkInput(WriteBlank image) {
      float[] data = new float[image.sizeX * image.sizeY];

      for (int h = 0; h < image.sizeY; h++) {
         for (int w = 0; w < image.sizeX; w++) {
            data[h * image.sizeX + w] = MathHelper.clamp((image.pixels[w][h] + 128.0F) / 255.0F, 0.0F, 1.0F);
         }
      }

      return data;
   }

   public static BufferedImage getImageInPC(String path) {
      File file = new File(path);

      try {
         return ImageIO.read(file);
      } catch (IOException var3) {
         return null;
      }
   }

   public static String matrixToString(float[][][] matrix, char numberEnding) {
      String stringMatrix = Arrays.deepToString(matrix);
      char[] chars = stringMatrix.toCharArray();
      ArrayList<Character> list = new ArrayList<>();
      boolean lastIsNumber = false;

      for (int i = 0; i < chars.length; i++) {
         if (!Character.isDigit(chars[i]) && chars[i] != '.') {
            if (lastIsNumber) {
               lastIsNumber = false;
               list.add(numberEnding);
            }

            if (chars[i] == '[') {
               list.add('{');
            } else if (chars[i] == ']') {
               list.add('}');
            } else {
               list.add(chars[i]);
            }
         } else {
            lastIsNumber = true;
            list.add(chars[i]);
         }
      }

      StringBuilder sb = new StringBuilder();

      for (Character character : list) {
         sb.append(character);
      }

      return sb.toString();
   }
}
