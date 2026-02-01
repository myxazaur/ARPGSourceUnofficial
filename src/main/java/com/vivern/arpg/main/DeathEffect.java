package com.vivern.arpg.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public abstract class DeathEffect {
   public static HashMap<Integer, DeathEffect> registry = new HashMap<>();
   public static int idStart = 1;
   public List<EntityLivingBase> listMobs = new ArrayList<>();
   public List<EntityLivingBase> toRemove = new ArrayList<>();
   public int id = idStart++;

   public DeathEffect() {
      registry.put(this.id, this);
   }

   public abstract void onRenderLivingPre(Pre var1, EntityLivingBase var2);

   public abstract void onLivingUpdate(World var1, LivingUpdateEvent var2, EntityLivingBase var3, double var4, double var6, double var8);

   public boolean exist(EntityLivingBase entity) {
      return this.listMobs.contains(entity);
   }

   public void add(EntityLivingBase entity) {
      this.listMobs.add(entity);
   }

   public void remove(EntityLivingBase entity) {
      this.toRemove.add(entity);
   }

   public void removeAllUnused() {
      if (!this.toRemove.isEmpty()) {
         this.listMobs.removeAll(this.toRemove);
         this.toRemove.clear();
      }
   }
}
