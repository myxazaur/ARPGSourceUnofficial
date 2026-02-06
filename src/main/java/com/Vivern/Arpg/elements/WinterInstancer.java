package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WinterInstancer extends Instancer {
   public static ResourceLocation snow1 = new ResourceLocation("arpg:textures/snowflake5.png");
   public static ResourceLocation snow2 = new ResourceLocation("arpg:textures/snowflake4.png");

   public WinterInstancer() {
      super("winter_instancer", 1.4F, 1.2F, 5000);
   }

   @Override
   public void spawnPartickles(World world, EntityPlayer player, boolean deploy) {
      if (deploy) {
         if (player.ticksExisted % 4 == 0) {
            this.bom(0);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());

         for (int i = 0; i < 4; i++) {
            boolean snow = itemRand.nextFloat() < 0.4F;
            float scl = 0.05F + itemRand.nextFloat() * 0.05F;
            int lt = 5 + itemRand.nextInt(7);
            GUNParticle part = new GUNParticle(
               snow ? snow1 : star,
               scl,
               0.0F,
               lt,
               200,
               world,
               partpos.x + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.y + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.z + (itemRand.nextFloat() - 0.5F) / 2.0F,
               0.0F,
               0.0F,
               0.0F,
               snow ? 1.0F : 0.5F,
               1.0F,
               1.0F,
               true,
               itemRand.nextInt(360),
               true,
               1.0F
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            part.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 10.0F);
            world.spawnEntity(part);
         }

         float scl = 0.05F + itemRand.nextFloat() * 0.1F;
         int lt = 10 + itemRand.nextInt(7);
         GUNParticle part = new GUNParticle(
            snow2,
            scl,
            0.0F,
            lt,
            180,
            world,
            partpos.x + (itemRand.nextFloat() - 0.5F) / 5.0F,
            partpos.y + (itemRand.nextFloat() - 0.5F) / 5.0F,
            partpos.z + (itemRand.nextFloat() - 0.5F) / 5.0F,
            0.0F,
            0.0F,
            0.0F,
            0.85F,
            0.9F,
            1.0F,
            true,
            itemRand.nextInt(360),
            true,
            1.0F
         );
         part.scaleTickAdding = 0.06F;
         part.alphaTickAdding = -1.0F / lt;
         part.randomDeath = false;
         part.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.6F, 2.0F);
         world.spawnEntity(part);
      } else {
         if (player.ticksExisted % 4 == 0) {
            this.bom(1);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());
         ParticleTracker tracker = new ParticleTracker.TrackerFollowStaticPoint(partpos, true, 0.3F, 0.001F, 0.2F);

         for (int i = 0; i < 4; i++) {
            RayTraceResult result = GetMOP.fixedRayTraceBlocks(
               world,
               player,
               6.0,
               0.4,
               0.4,
               false,
               false,
               true,
               false,
               player.rotationPitch + itemRand.nextInt(17) - 8.0F,
               player.rotationYaw + itemRand.nextInt(17) - 8.0F
            );
            if (result != null && result.hitVec != null) {
               boolean snow = itemRand.nextFloat() < 0.4F;
               float scl = 0.05F + itemRand.nextFloat() * 0.05F;
               int lt = 15 + itemRand.nextInt(10);
               GUNParticle part = new GUNParticle(
                  snow ? snow1 : star,
                  scl,
                  0.0F,
                  lt,
                  200,
                  world,
                  result.hitVec.x,
                  result.hitVec.y,
                  result.hitVec.z,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  snow ? 1.0F : 0.5F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               part.alphaGlowing = true;
               part.scaleTickAdding = -scl / lt;
               part.tracker = tracker;
               part.alpha = 0.0F;
               part.alphaTickAdding = 0.12F;
               world.spawnEntity(part);
            }
         }
      }
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 7 - rapidity;
   }

   @Override
   public int getMaxLeadership(ItemStack itemstack) {
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
      return 300 + might * 25;
   }
}
