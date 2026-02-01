package com.vivern.arpg.weather;

import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.GetMOP;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.util.vector.Vector4f;

public class TimeOfDayProvider {
   public ArrayList<TimeOfDay> timesOfDay = new ArrayList<>();
   public int dayLength = 24000;
   boolean firstReset = true;

   public TimeOfDayProvider add(int upriseStartTime, int upriseEndTime, int fallStartTime, int fallEndTime, Vector4f... colors) {
      TimeOfDay timeOfDay = new TimeOfDay(upriseStartTime, upriseEndTime, fallStartTime, fallEndTime, colors, this);
      this.timesOfDay.add(timeOfDay);
      return this;
   }

   public TimeOfDayProvider addV(int upriseStartTime, int upriseEndTime, Vector4f... colors) {
      TimeOfDay timeOfDay = new TimeOfDay(upriseStartTime, upriseEndTime, 0, 0, colors, this);
      this.timesOfDay.add(timeOfDay);
      return this;
   }

   public TimeOfDayProvider addO(int upriseStartTime, int upriseEndTime, Object... colors) {
      Vector4f[] vectors = new Vector4f[colors.length];

      for (int i = 0; i < colors.length; i++) {
         if (colors[i] instanceof Integer) {
            int color = (Integer)colors[i];
            float A = (color >> 24 & 0xFF) / 255.0F;
            float R = (color >> 16 & 0xFF) / 255.0F;
            float G = (color >> 8 & 0xFF) / 255.0F;
            float B = (color & 0xFF) / 255.0F;
            vectors[i] = new Vector4f(R, G, B, A);
         } else if (colors[i] instanceof Vector4f) {
            vectors[i] = (Vector4f)colors[i];
         }
      }

      TimeOfDay timeOfDay = new TimeOfDay(upriseStartTime, upriseEndTime, 0, 0, vectors, this);
      this.timesOfDay.add(timeOfDay);
      return this;
   }

   public TimeOfDayProvider cycleAll() {
      for (int i = 0; i < this.timesOfDay.size(); i++) {
         TimeOfDay timeOfDay = this.timesOfDay.get(i);
         TimeOfDay timeOfDayPrev = this.timesOfDay.get(i == 0 ? this.timesOfDay.size() - 1 : i - 1);
         timeOfDayPrev.fallStartTime = timeOfDay.upriseStartTime;
         timeOfDayPrev.fallEndTime = timeOfDay.upriseEndTime;
      }

      for (int i = 0; i < this.timesOfDay.size(); i++) {
         TimeOfDay timeOfDay = this.timesOfDay.get(i);
         timeOfDay.fixtime(this.dayLength);
      }

      return this;
   }

   public Vector4f getColor(int id, long worldTimeLong) {
      int worldTime = (int)(worldTimeLong % this.dayLength);
      float r = 0.0F;
      float g = 0.0F;
      float b = 0.0F;
      float a = 0.0F;

      for (int i = 0; i < this.timesOfDay.size(); i++) {
         TimeOfDay timeOfDay = this.timesOfDay.get(i);
         float power = timeOfDay.getPower(worldTime, this);
         if (power > 0.0F) {
            r += timeOfDay.colors[id].x * power;
            g += timeOfDay.colors[id].y * power;
            b += timeOfDay.colors[id].z * power;
            a += timeOfDay.colors[id].w * power;
         }
      }

      return new Vector4f(r, g, b, a);
   }

   public Vec3d[] getColorsVec3d(int idFirst, int amount, long worldTimeLong) {
      int worldTime = (int)(worldTimeLong % this.dayLength);
      Vec3d[] vectors = new Vec3d[amount];
      float[] r = new float[amount];
      float[] g = new float[amount];
      float[] b = new float[amount];
      int fpa = idFirst + amount;

      for (int i = 0; i < this.timesOfDay.size(); i++) {
         TimeOfDay timeOfDay = this.timesOfDay.get(i);
         float power = timeOfDay.getPower(worldTime, this);
         if (power > 0.0F) {
            for (int j = idFirst; j < fpa; j++) {
               int k = j - idFirst;
               r[k] += timeOfDay.colors[j].x * power;
               g[k] += timeOfDay.colors[j].y * power;
               b[k] += timeOfDay.colors[j].z * power;
            }
         }
      }

      for (int j = 0; j < amount; j++) {
         vectors[j] = new Vec3d(r[j], g[j], b[j]);
      }

      return vectors;
   }

   public Vector4f[] getColors(int idFirst, int amount, long worldTimeLong) {
      int worldTime = (int)(worldTimeLong % this.dayLength);
      Vector4f[] vectors = new Vector4f[amount];
      float[] r = new float[amount];
      float[] g = new float[amount];
      float[] b = new float[amount];
      float[] a = new float[amount];
      int fpa = idFirst + amount;

      for (int i = 0; i < this.timesOfDay.size(); i++) {
         TimeOfDay timeOfDay = this.timesOfDay.get(i);
         float power = timeOfDay.getPower(worldTime, this);
         if (power > 0.0F) {
            for (int j = idFirst; j < fpa; j++) {
               int k = j - idFirst;
               r[k] += timeOfDay.colors[j].x * power;
               g[k] += timeOfDay.colors[j].y * power;
               b[k] += timeOfDay.colors[j].z * power;
               a[k] += timeOfDay.colors[j].w * power;
            }
         }
      }

      for (int j = 0; j < amount; j++) {
         vectors[j] = new Vector4f(r[j], g[j], b[j], a[j]);
      }

      return vectors;
   }

   public float getPowerOf(int index, long worldTimeLong) {
      int worldTime = (int)(worldTimeLong % this.dayLength);
      return this.timesOfDay.get(index).getPower(worldTime, this);
   }

   public void resetAndDebug() {
      for (int i = 0; i < this.timesOfDay.size(); i++) {
         TimeOfDay timeOfDay = this.timesOfDay.get(i);

         for (int j = 0; j < timeOfDay.colors.length; j++) {
            if (this.firstReset) {
               Debugger.debugColors.put(i + " " + j, timeOfDay.colors[j]);
            } else {
               timeOfDay.colors[j] = Debugger.getDebugColor(i + " " + j);
            }
         }
      }

      this.firstReset = false;
      if (Debugger.floats[15] == 123.0F) {
         Debugger.floats[15] = 0.0F;
         this.printSourceCode();
      }
   }

   public void printSourceCode() {
      System.out.println();
      System.out.println();

      for (int i = 0; i < this.timesOfDay.size(); i++) {
         TimeOfDay timeOfDay = this.timesOfDay.get(i);
         System.out.print(".addO(" + timeOfDay.upriseStartTime + ", " + timeOfDay.upriseEndTime);

         for (int j = 0; j < timeOfDay.colors.length; j++) {
            System.out
               .print(
                  ", new Vector4f("
                     + timeOfDay.colors[j].x
                     + "F, "
                     + timeOfDay.colors[j].y
                     + "F, "
                     + timeOfDay.colors[j].z
                     + "F, "
                     + timeOfDay.colors[j].w
                     + "F)"
               );
         }

         System.out.print(")");
         System.out.print("\n");
      }

      System.out.println();
      System.out.println();
   }

   public void setLightmapColors(
      int providerColorIndex, long worldTime, float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors
   ) {
      float gamma = 1.0F + Minecraft.getMinecraft().gameSettings.gammaSetting * 0.3F;
      skyLight *= gamma;
      blockLight *= gamma;
      Vector4f vector4f = this.getColor(providerColorIndex, worldTime);
      float limit = 0.2F;
      colors[0] = MathHelper.clamp(vector4f.x * skyLight + colors[0] * blockLight, colors[0] * limit, 1.0F);
      colors[1] = MathHelper.clamp(vector4f.y * skyLight + colors[1] * blockLight, colors[1] * limit, 1.0F);
      colors[2] = MathHelper.clamp(vector4f.z * skyLight + colors[2] * blockLight, colors[2] * limit, 1.0F);
   }

   public static class TimeOfDay {
      public int upriseStartTime;
      public int upriseEndTime;
      public int fallStartTime;
      public int fallEndTime;
      public Vector4f[] colors;

      public TimeOfDay(int upriseStartTime, int upriseEndTime, int fallStartTime, int fallEndTime, Vector4f[] colors, TimeOfDayProvider timeOfDayProvider) {
         this.upriseStartTime = upriseStartTime;
         this.upriseEndTime = upriseEndTime;
         this.fallStartTime = fallStartTime;
         this.fallEndTime = fallEndTime;
         this.colors = colors;
         this.fixtime(timeOfDayProvider.dayLength);
      }

      public void fixtime(int daylength) {
         if (this.upriseEndTime < this.upriseStartTime) {
            this.upriseEndTime += daylength;
         }

         if (this.fallStartTime < this.upriseEndTime) {
            this.fallStartTime += daylength;
         }

         if (this.fallEndTime < this.fallStartTime) {
            this.fallEndTime += daylength;
         }
      }

      @Override
      public String toString() {
         return "(upSt: " + this.upriseStartTime + " upEn: " + this.upriseEndTime + " | falSt: " + this.fallStartTime + " falEn: " + this.fallEndTime + ")";
      }

      public float getPower(int clampedWorldTime, TimeOfDayProvider timeOfDayProvider) {
         float selector1 = clampedWorldTime;
         float selector2 = clampedWorldTime + timeOfDayProvider.dayLength;
         float value1 = GetMOP.getfromto(selector1, (float)this.upriseStartTime, (float)this.upriseEndTime)
            - GetMOP.getfromto(selector1, (float)this.fallStartTime, (float)this.fallEndTime);
         float value2 = GetMOP.getfromto(selector2, (float)this.upriseStartTime, (float)this.upriseEndTime)
            - GetMOP.getfromto(selector2, (float)this.fallStartTime, (float)this.fallEndTime);
         return MathHelper.clamp(value1 + value2, 0.0F, 1.0F);
      }
   }
}
