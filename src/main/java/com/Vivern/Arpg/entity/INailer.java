package com.Vivern.Arpg.entity;

import net.minecraft.entity.Entity;

public interface INailer {
   void setNailedEntity(Entity var1);

   Entity getNailedEntity();

   boolean isprickedToWall();

   boolean canPrickParticle();
}
