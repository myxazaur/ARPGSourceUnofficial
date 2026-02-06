package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnderInstancer extends Instancer {
   public EnderInstancer() {
      super("ender_instancer", 1.35F, 1.1F, 4000);
   }

   @Override
   public void spawnPartickles(World world, EntityPlayer player, boolean deploy) {
      if (deploy) {
         if (player.ticksExisted % 4 == 0) {
            this.bom(0);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());

         for (int i = 0; i < 4; i++) {
            float scl = 0.05F + itemRand.nextFloat() * 0.05F;
            int lt = 5 + itemRand.nextInt(7);
            GUNParticle part = new GUNParticle(
               star,
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
               1.0F - itemRand.nextFloat() * 0.3F,
               0.2F,
               1.0F - itemRand.nextFloat() * 0.3F,
               true,
               itemRand.nextInt(360),
               true,
               1.0F
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            part.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 10.0F);
            world.spawnEntity(part);
            if (itemRand.nextFloat() < 0.65F) {
               float len = 0.8F + itemRand.nextFloat() * 3.5F;
               world.spawnParticle(
                  EnumParticleTypes.PORTAL,
                  partpos.x + (itemRand.nextFloat() - 0.5F) / 2.0F + part.motionX * len,
                  partpos.y + (itemRand.nextFloat() - 0.5F) / 2.0F + part.motionY * len,
                  partpos.z + (itemRand.nextFloat() - 0.5F) / 2.0F + part.motionZ * len,
                  -part.motionX,
                  -part.motionY,
                  -part.motionZ,
                  new int[0]
               );
            }
         }
      } else {
         if (player.ticksExisted % 4 == 0) {
            this.bom(1);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());
         ParticleTracker tracker = new ParticleTracker.TrackerFollowStaticPoint(partpos, true, 0.3F, 0.001F, 0.2F);

         for (int ix = 0; ix < 4; ix++) {
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
               float scl = 0.05F + itemRand.nextFloat() * 0.05F;
               int lt = 15 + itemRand.nextInt(10);
               GUNParticle part = new GUNParticle(
                  star,
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
                  1.0F - itemRand.nextFloat() * 0.3F,
                  0.2F,
                  1.0F - itemRand.nextFloat() * 0.3F,
                  true,
                  itemRand.nextInt(360)
               );
               part.alphaGlowing = true;
               part.scaleTickAdding = -scl / lt;
               part.tracker = tracker;
               part.alpha = 0.0F;
               part.alphaTickAdding = 0.12F;
               world.spawnEntity(part);
               if (itemRand.nextFloat() < 0.5F) {
                  world.spawnParticle(
                     EnumParticleTypes.PORTAL,
                     result.hitVec.x,
                     result.hitVec.y,
                     result.hitVec.z,
                     (float)itemRand.nextGaussian() / 20.0F,
                     (float)itemRand.nextGaussian() / 20.0F,
                     (float)itemRand.nextGaussian() / 20.0F,
                     new int[0]
                  );
               }
            }
         }
      }
   }

   @Override
   public List<EntityLivingBase> getTracedCreatures(ItemStack stack, EntityPlayer player) {
      List<EntityLivingBase> list = GetMOP.MopRayTrace(16.0, 1.0F, player, 2.0, 1.0);
      if (list.isEmpty()) {
         Vec3d vec3d = player.getPositionEyes(1.0F);
         Vec3d vec3d1 = player.getLook(1.0F);
         Vec3d vec3d2 = vec3d.add(vec3d1.x * 16.0, vec3d1.y * 16.0, vec3d1.z * 16.0);
         EntityLivingBase entity = GetMOP.findEntityOnPath(vec3d, vec3d2, player.world, player, 2.0, 1.0, false);
         if (entity != null && entity.isPotionActive(MobEffects.GLOWING)) {
            list.add(entity);
         }
      }

      return list;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 8 - rapidity;
   }

   @Override
   public int getMaxLeadership(ItemStack itemstack) {
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
      return 200 + might * 20;
   }
}
