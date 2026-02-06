package com.Vivern.Arpg.main;

import com.google.common.base.MoreObjects;
import javax.annotation.concurrent.Immutable;
import net.minecraft.util.math.MathHelper;

@Immutable
public class Vec2i implements Comparable<Vec2i> {
   public static final Vec2i NULL_VECTOR = new Vec2i(0, 0);
   public final int x;
   public final int y;

   public Vec2i(int xIn, int yIn) {
      this.x = xIn;
      this.y = yIn;
   }

   public Vec2i(double xIn, double yIn) {
      this(MathHelper.floor(xIn), MathHelper.floor(yIn));
   }

   @Override
   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof Vec2i)) {
         return false;
      } else {
         Vec2i vec2i = (Vec2i)p_equals_1_;
         return this.getX() != vec2i.getX() ? false : this.getY() == vec2i.getY();
      }
   }

   @Override
   public int hashCode() {
      return this.getY() * 31 + this.getX();
   }

   public int compareTo(Vec2i p_compareTo_1_) {
      return this.getY() == p_compareTo_1_.getY() ? this.getX() - p_compareTo_1_.getX() : this.getY() - p_compareTo_1_.getY();
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public double getDistance(int xIn, int yIn) {
      double d0 = this.getX() - xIn;
      double d1 = this.getY() - yIn;
      return Math.sqrt(d0 * d0 + d1 * d1);
   }

   public double distanceSq(double toX, double toY) {
      double d0 = this.getX() - toX;
      double d1 = this.getY() - toY;
      return d0 * d0 + d1 * d1;
   }

   public double distanceSqToCenter(double xIn, double yIn) {
      double d0 = this.getX() + 0.5 - xIn;
      double d1 = this.getY() + 0.5 - yIn;
      return d0 * d0 + d1 * d1;
   }

   public double distanceSq(Vec2i to) {
      return this.distanceSq(to.getX(), to.getY());
   }

   public double distance(Vec2i to) {
      return this.getDistance(to.getX(), to.getY());
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).toString();
   }
}
