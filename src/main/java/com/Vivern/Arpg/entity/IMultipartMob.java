package com.Vivern.Arpg.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public interface IMultipartMob {
   boolean attackEntityFromPart(EntityPart var1, DamageSource var2, float var3);

   EntityLivingBase getMob();

   String getTeamString();

   boolean isDead();
}
