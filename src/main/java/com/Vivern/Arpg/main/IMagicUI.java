package com.Vivern.Arpg.main;

import com.Vivern.Arpg.entity.EntityMagicUI;
import com.Vivern.Arpg.recipes.Seal;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMagicUI {
   SoundCategory soundc = SoundCategory.NEUTRAL;

   default void acceptSeal(World world, EntityPlayer player, Seal seal, @Nullable BlockPos pos, @Nullable Entity entity) {
   }

   default void acceptMana(World world, EntityPlayer player, float amount, @Nullable BlockPos pos, @Nullable Entity entity) {
   }

   default void acceptElementEnergy(World world, EntityPlayer player, ShardType type, float amount, @Nullable BlockPos pos, @Nullable Entity entity) {
   }

   @SideOnly(Side.CLIENT)
   default void renderDecoratives(double x, double y, double z, int ticksExisted, float removeTick, float partialTicks, RenderManager renderManager) {
   }

   default void onProvideLink(
      World world, EntityPlayer player, @Nullable BlockPos thispos, @Nullable Entity thisentity, @Nullable BlockPos linkpos, @Nullable Entity linkentity
   ) {
   }

   default void onAcceptLink(
      World world, EntityPlayer player, @Nullable BlockPos thispos, @Nullable Entity thisentity, @Nullable BlockPos linkpos, @Nullable Entity linkentity
   ) {
   }

   default void open(World world, EntityPlayer player, @Nullable BlockPos pos, @Nullable Entity entity) {
   }

   static void spawnEntityMUIinRound(
      World world, EntityPlayer player, @Nullable BlockPos pos, @Nullable Entity entitySource, double radius, float yUp, EntityMagicUI... entitymuis
   ) {
      if (pos != null) {
         double x = pos.getX() + 0.5;
         double y = pos.getY();
         double z = pos.getZ() + 0.5;
         double doublePi = Math.PI * 2;
         int c = 0;
         float onedisplace = 360.0F / entitymuis.length;

         for (double i = 0.0; i < doublePi; i += doublePi / entitymuis.length) {
            double newPosX = x + radius * Math.cos(i);
            double newPosZ = z + radius * Math.sin(i);
            entitymuis[c].setPosition(newPosX, y + yUp, newPosZ);
            entitymuis[c].removing = false;
            entitymuis[c].displace = c * onedisplace;
            world.spawnEntity(entitymuis[c]);
            c++;
         }
      }
   }

   static void spawnEntityMUIinRound(
      World world, EntityPlayer player, @Nullable BlockPos pos, @Nullable Entity entitySource, double radius, float yUp, List<EntityMagicUI> entitymuis
   ) {
      if (pos != null) {
         double x = pos.getX() + 0.5;
         double y = pos.getY();
         double z = pos.getZ() + 0.5;
         double doublePi = Math.PI * 2;
         int c = 0;
         float onedisplace = 360.0F / entitymuis.size();

         for (double i = 0.0; i < doublePi; i += doublePi / entitymuis.size()) {
            double newPosX = x + radius * Math.cos(i);
            double newPosZ = z + radius * Math.sin(i);
            entitymuis.get(c).setPosition(newPosX, y + yUp, newPosZ);
            entitymuis.get(c).removing = false;
            entitymuis.get(c).displace = c * onedisplace;
            world.spawnEntity(entitymuis.get(c));
            c++;
         }
      }
   }

   static boolean checkNoNearOpened(World world, @Nullable BlockPos pos, @Nullable Entity entitySource, int radius) {
      if (pos != null) {
         AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius));

         for (EntityMagicUI mui : world.getEntitiesWithinAABB(EntityMagicUI.class, aabb)) {
            if (mui.origin.equals(pos)) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   default void onDestroy(World world, @Nullable BlockPos pos, @Nullable Entity entity) {
   }
}
