//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.events.AMatrix;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextFormatting;

public class ColorConverters {
   public static Vec3d getRainbow(float hue) {
      float f1 = 0.16666667F;
      float f2 = f1 * 2.0F;
      float f3 = f1 * 3.0F;
      float f4 = f1 * 4.0F;
      float f5 = f1 * 5.0F;
      float red = 1.0F - GetMOP.getfromto(hue, f1, f2) + GetMOP.getfromto(hue, f4, f5);
      float green = GetMOP.getfromto(hue, 0.0F, f1) - GetMOP.getfromto(hue, f3, f4);
      float blue = GetMOP.getfromto(hue, f2, f3) - GetMOP.getfromto(hue, f5, 1.0F);
      return new Vec3d(red, green, blue);
   }

   public static int RGBtoDecimal(float R, float G, float B) {
      B = Math.max(B, 0.0F);
      G = Math.max(G, 0.0F);
      R = Math.max(R, 0.0F);
      B = Math.min(B, 1.0F);
      G = Math.min(G, 1.0F);
      R = Math.min(R, 1.0F);
      return (int)(R * 255.0F) * 65536 + (int)(G * 255.0F) * 256 + (int)(B * 255.0F);
   }

   public static int RGBtoDecimal255(int R, int G, int B) {
      B = Math.max(B, 0);
      G = Math.max(G, 0);
      R = Math.max(R, 0);
      B = Math.min(B, 255);
      G = Math.min(G, 255);
      R = Math.min(R, 255);
      return R * 65536 + G * 256 + B;
   }

   public static int RGBAtoDecimal(float R, float G, float B, float alpha) {
      return RGBAtoDecimal255((int)(R * 255.0F), (int)(G * 255.0F), (int)(B * 255.0F), (int)(alpha * 255.0F));
   }

   public static int RGBAtoDecimal255(int R, int G, int B, int alpha) {
      B = Math.max(B, 0);
      G = Math.max(G, 0);
      R = Math.max(R, 0);
      B = Math.min(B, 255);
      G = Math.min(G, 255);
      R = Math.min(R, 255);
      int col = alpha << 8;
      col = (col | R) << 8;
      col = (col | G) << 8;
      return col | B;
   }

   public static long RGBtoDecimal2097140(int R, int G, int B) {
      B = Math.max(B, 0);
      G = Math.max(G, 0);
      R = Math.max(R, 0);
      B = Math.min(B, 2097140);
      G = Math.min(G, 2097140);
      R = Math.min(R, 2097140);
      return R * 4397996179600L + G * 2097140L + B;
   }

   public static Vec3i Decimal2097140toRGB(long color) {
      long R = color / 4397996179600L;
      long G = (color - R * 4397996179600L) / 2097140L;
      long B = color - R * 4397996179600L - G * 2097140L;
      return new Vec3i(R, G, B);
   }

   public static Vec3d DecimaltoRGB(int color) {
      float R = (color >> 16 & 0xFF) / 255.0F;
      float G = (color >> 8 & 0xFF) / 255.0F;
      float B = (color & 0xFF) / 255.0F;
      return new Vec3d(R, G, B);
   }

   public static float[] DecimaltoRGBA(int color) {
      float A = (color >> 24 & 0xFF) / 255.0F;
      float R = (color >> 16 & 0xFF) / 255.0F;
      float G = (color >> 8 & 0xFF) / 255.0F;
      float B = (color & 0xFF) / 255.0F;
      return new float[]{R, G, B, A};
   }

   public static float[] DecimaltoRGBA(int color, float alpha) {
      float R = (color >> 16 & 0xFF) / 255.0F;
      float G = (color >> 8 & 0xFF) / 255.0F;
      float B = (color & 0xFF) / 255.0F;
      return new float[]{R, G, B, alpha};
   }

   public static long onlyBlue(long color) {
      long R = color / 4397996179600L;
      long G = (color - R * 4397996179600L) / 2097140L;
      long B = color - R * 4397996179600L - G * 2097140L;
      return RGBtoDecimal2097140(0, 0, (int)B);
   }

   public static long onlyGreen(long color) {
      long R = color / 4397996179600L;
      long G = (color - R * 4397996179600L) / 2097140L;
      return RGBtoDecimal2097140(0, (int)G, 0);
   }

   public static long onlyRed(long color) {
      long R = color / 4397996179600L;
      return RGBtoDecimal2097140((int)R, 0, 0);
   }

   public static long getonlyBlue(long color) {
      long R = color / 4397996179600L;
      long G = (color - R * 4397996179600L) / 2097140L;
      return color - R * 4397996179600L - G * 2097140L;
   }

   public static long getonlyGreen(long color) {
      long R = color / 4397996179600L;
      return (color - R * 4397996179600L) / 2097140L;
   }

   public static long getonlyRed(long color) {
      return color / 4397996179600L;
   }

   public static Vec3d mix(Vec3d color1, Vec3d color2) {
      return new Vec3d(
         (color1.x + color2.x) * 0.5,
         (color1.y + color2.y) * 0.5,
         (color1.z + color2.z) * 0.5
      );
   }

   public static Vec3d mix(Vec3d color1, Vec3d color2, Vec3d color3) {
      return new Vec3d(
         (color1.x + color2.x + color3.x) / 3.0,
         (color1.y + color2.y + color3.y) / 3.0,
         (color1.z + color2.z + color3.z) / 3.0
      );
   }

   public static Vec3d mix(Vec3d color1, Vec3d color2, Vec3d color3, Vec3d color4) {
      return new Vec3d(
         (color1.x + color2.x + color3.x + color4.x) * 0.25,
         (color1.y + color2.y + color3.y + color4.y) * 0.25,
         (color1.z + color2.z + color3.z + color4.z) * 0.25
      );
   }

   public static long mix(long color1, long color2) {
      long R1 = color1 / 4397996179600L;
      long G1 = (color1 - R1 * 4397996179600L) / 2097140L;
      long B1 = color1 - R1 * 4397996179600L - G1 * 2097140L;
      long R2 = color2 / 4397996179600L;
      long G2 = (color2 - R2 * 4397996179600L) / 2097140L;
      long B2 = color2 - R2 * 4397996179600L - G2 * 2097140L;
      return (R1 + R2) / 2L * 4397996179600L + (G1 + G2) / 2L * 2097140L + (B1 + B2) / 2L;
   }

   public static long mix(long color1, long color2, long color3) {
      long R1 = color1 / 4397996179600L;
      long G1 = (color1 - R1 * 4397996179600L) / 2097140L;
      long B1 = color1 - R1 * 4397996179600L - G1 * 2097140L;
      long R2 = color2 / 4397996179600L;
      long G2 = (color2 - R2 * 4397996179600L) / 2097140L;
      long B2 = color2 - R2 * 4397996179600L - G2 * 2097140L;
      long R3 = color3 / 4397996179600L;
      long G3 = (color3 - R3 * 4397996179600L) / 2097140L;
      long B3 = color3 - R3 * 4397996179600L - G3 * 2097140L;
      return (R1 + R2 + R3) / 3L * 4397996179600L + (G1 + G2 + G3) / 3L * 2097140L + (B1 + B2 + B3) / 3L;
   }

   public static long mix(long color1, long color2, long color3, long color4) {
      long R1 = color1 / 4397996179600L;
      long G1 = (color1 - R1 * 4397996179600L) / 2097140L;
      long B1 = color1 - R1 * 4397996179600L - G1 * 2097140L;
      long R2 = color2 / 4397996179600L;
      long G2 = (color2 - R2 * 4397996179600L) / 2097140L;
      long B2 = color2 - R2 * 4397996179600L - G2 * 2097140L;
      long R3 = color3 / 4397996179600L;
      long G3 = (color3 - R3 * 4397996179600L) / 2097140L;
      long B3 = color3 - R3 * 4397996179600L - G3 * 2097140L;
      long R4 = color4 / 4397996179600L;
      long G4 = (color4 - R4 * 4397996179600L) / 2097140L;
      long B4 = color4 - R4 * 4397996179600L - G4 * 2097140L;
      return (R1 + R2 + R3 + R4) / 4L * 4397996179600L + (G1 + G2 + G3 + G4) / 4L * 2097140L + (B1 + B2 + B3 + B4) / 4L;
   }

   public static long mix(long color1, long color2, long color3, long color4, long color5, long color6) {
      long R1 = color1 / 4397996179600L;
      long G1 = (color1 - R1 * 4397996179600L) / 2097140L;
      long B1 = color1 - R1 * 4397996179600L - G1 * 2097140L;
      long R2 = color2 / 4397996179600L;
      long G2 = (color2 - R2 * 4397996179600L) / 2097140L;
      long B2 = color2 - R2 * 4397996179600L - G2 * 2097140L;
      long R3 = color3 / 4397996179600L;
      long G3 = (color3 - R3 * 4397996179600L) / 2097140L;
      long B3 = color3 - R3 * 4397996179600L - G3 * 2097140L;
      long R4 = color4 / 4397996179600L;
      long G4 = (color4 - R4 * 4397996179600L) / 2097140L;
      long B4 = color4 - R4 * 4397996179600L - G4 * 2097140L;
      long R5 = color5 / 4397996179600L;
      long G5 = (color5 - R5 * 4397996179600L) / 2097140L;
      long B5 = color5 - R5 * 4397996179600L - G5 * 2097140L;
      long R6 = color6 / 4397996179600L;
      long G6 = (color6 - R6 * 4397996179600L) / 2097140L;
      long B6 = color6 - R6 * 4397996179600L - G6 * 2097140L;
      return (R1 + R2 + R3 + R4 + R5 + R6) / 6L * 4397996179600L + (G1 + G2 + G3 + G4 + G5 + G6) / 6L * 2097140L + (B1 + B2 + B3 + B4 + B5 + B6) / 6L;
   }

   public static float[] hexToRgb(String colorStr) {
      colorStr = colorStr.toUpperCase();
      return new float[]{
         Integer.valueOf(colorStr.substring(0, 2), 16).intValue() / 255.0F,
         Integer.valueOf(colorStr.substring(2, 4), 16).intValue() / 255.0F,
         Integer.valueOf(colorStr.substring(4, 6), 16).intValue() / 255.0F
      };
   }

   public static int UnpackLightmapCoordsX(int brightness) {
      return brightness >> 16 & 0xFF;
   }

   public static int UnpackLightmapCoordsZ(int brightness) {
      return brightness & 0xFF;
   }

   public static int InBorder(int min, int max, int number) {
      number = Math.max(number, min);
      return Math.min(number, max);
   }

   public static float InBorder(float min, float max, float number) {
      number = Math.max(number, min);
      return Math.min(number, max);
   }

   public static Vec3d getTeamColor(TextFormatting f) {
      switch (f) {
         case BLACK:
            return new Vec3d(0.1, 0.1, 0.1);
         case DARK_BLUE:
            return new Vec3d(0.1, 0.1, 0.5);
         case DARK_GREEN:
            return new Vec3d(0.1, 0.5, 0.1);
         case DARK_AQUA:
            return new Vec3d(0.1, 0.5, 0.5);
         case DARK_RED:
            return new Vec3d(0.5, 0.0, 0.1);
         case DARK_PURPLE:
            return new Vec3d(0.45, 0.0, 0.5);
         case GOLD:
            return new Vec3d(0.7, 0.55, 0.1);
         case GRAY:
            return new Vec3d(0.6, 0.6, 0.6);
         case DARK_GRAY:
            return new Vec3d(0.3, 0.3, 0.3);
         case BLUE:
            return new Vec3d(0.1, 0.1, 1.0);
         case GREEN:
            return new Vec3d(0.1, 1.0, 0.1);
         case AQUA:
            return new Vec3d(0.2, 0.8, 1.0);
         case RED:
            return new Vec3d(1.0, 0.1, 0.2);
         case LIGHT_PURPLE:
            return new Vec3d(0.9, 0.4, 1.0);
         case YELLOW:
            return new Vec3d(1.0, 0.9, 0.2);
         case WHITE:
            return new Vec3d(1.0, 1.0, 1.0);
         default:
            return new Vec3d(1.0, 0.1, 0.2);
      }
   }

   public static AMatrix AddMatrixNumber(AMatrix matrix, double value) {
      AMatrix resmat = new AMatrix(matrix.strings, matrix.columns);

      for (Vec3d vector : matrix.list) {
         resmat.put(vector.add(0.0, 0.0, value));
      }

      return resmat;
   }

   public static AMatrix AddMatrix(AMatrix matrix, AMatrix matrix2) {
      AMatrix resmat = new AMatrix(matrix.strings, matrix.columns);
      if (matrix.strings == matrix2.strings && matrix.columns == matrix2.columns) {
         for (int si = 0; si < matrix.strings; si++) {
            for (int ci = 0; ci < matrix.columns; ci++) {
               resmat.put(new Vec3d(si, ci, matrix.get(si, ci) + matrix2.get(si, ci)));
            }
         }
      }

      return resmat;
   }

   public static AMatrix MultipleMatrix(AMatrix matrix, AMatrix matrix2) {
      AMatrix resmat = new AMatrix(matrix.strings, matrix2.columns);
      if (matrix.columns == matrix2.strings) {
         for (int si = 0; si < matrix.strings; si++) {
            for (int ci = 0; ci < matrix2.columns; ci++) {
               double resultn = 0.0;

               for (int mci = 0; mci < matrix.columns; mci++) {
                  resultn += matrix.get(si, mci) * matrix2.get(mci, ci);
               }

               resmat.put(new Vec3d(si, ci, resultn));
            }
         }
      }

      return resmat;
   }

   public static void MatrixTest() {
      AMatrix resmat = new AMatrix(2, 3);
      resmat.put(new Vec3d(0.0, 0.0, 1.0));
      resmat.put(new Vec3d(0.0, 1.0, 2.0));
      resmat.put(new Vec3d(0.0, 2.0, -1.0));
      resmat.put(new Vec3d(1.0, 0.0, 0.0));
      resmat.put(new Vec3d(1.0, 1.0, 4.0));
      resmat.put(new Vec3d(1.0, 2.0, 5.0));
      AMatrix resmat2 = new AMatrix(3, 4);
      resmat2.put(new Vec3d(0.0, 0.0, 0.0));
      resmat2.put(new Vec3d(0.0, 1.0, 1.0));
      resmat2.put(new Vec3d(0.0, 2.0, 5.0));
      resmat2.put(new Vec3d(0.0, 3.0, 6.0));
      resmat2.put(new Vec3d(1.0, 0.0, 1.0));
      resmat2.put(new Vec3d(1.0, 1.0, -1.0));
      resmat2.put(new Vec3d(1.0, 2.0, -2.0));
      resmat2.put(new Vec3d(1.0, 3.0, 0.0));
      resmat2.put(new Vec3d(2.0, 0.0, 1.0));
      resmat2.put(new Vec3d(2.0, 1.0, 1.0));
      resmat2.put(new Vec3d(2.0, 2.0, 1.0));
      resmat2.put(new Vec3d(2.0, 3.0, 1.0));
      MultipleMatrix(resmat, resmat2).printMatrix();
   }
}
