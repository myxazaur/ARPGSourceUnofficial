package com.Vivern.Arpg.dimensions.generationutils;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterUniversal extends Teleporter {
   private final WorldServer worldserv;
   private double x;
   private double y;
   private double z;

   public TeleporterUniversal(WorldServer worldserv, double x, double y, double z) {
      super(worldserv);
      this.worldserv = worldserv;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public void placeInPortal(Entity entity, float rotationYaw) {
      entity.setPosition(this.x, this.y, this.z);
   }
}
