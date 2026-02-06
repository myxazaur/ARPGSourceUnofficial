package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import com.Vivern.Arpg.recipes.Seal;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AquaticInstancer extends Instancer {
   public static ResourceLocation mana_flow = new ResourceLocation("arpg:textures/mana_flow.png");
   public static ResourceLocation waterhammer = new ResourceLocation("arpg:textures/waterhammer.png");

   public AquaticInstancer() {
      super("aquatic_instancer", 1.2F, 1.1F, 10000);
   }

   @Override
   public void spawnPartickles(World world, EntityPlayer player, boolean deploy) {
      if (deploy) {
         if (player.ticksExisted % 4 == 0) {
            this.bom(0);
         }

         Vec3d partpos = this.getInstancerCorePoint(player, player.getPrimaryHand());

         for (int i = 0; i < 3; i++) {
            float scl = 0.05F + itemRand.nextFloat() * 0.05F;
            int lt = 5 + itemRand.nextInt(7);
            GUNParticle part = new GUNParticle(
               mana_flow,
               scl,
               0.0F,
               lt,
               210,
               world,
               partpos.x + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.y + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.z + (itemRand.nextFloat() - 0.5F) / 2.0F,
               0.0F,
               0.0F,
               0.0F,
               0.5F + itemRand.nextFloat() * 0.1F,
               1.0F,
               0.85F + itemRand.nextFloat() * 0.15F,
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

         for (int i = 0; i < 2; i++) {
            int lt = 20 + itemRand.nextInt(10);
            float scl = 0.05F + itemRand.nextFloat() * 0.043F;
            GUNParticle water = new GUNParticle(
               Seal.splashes[itemRand.nextInt(3)],
               scl,
               0.011F,
               lt,
               180,
               world,
               partpos.x + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.y + (itemRand.nextFloat() - 0.5F) / 2.0F,
               partpos.z + (itemRand.nextFloat() - 0.5F) / 2.0F,
               0.0F,
               0.0F,
               0.0F,
               0.55F - itemRand.nextFloat() * 0.2F,
               0.8F - itemRand.nextFloat() * 0.22F,
               1.0F - itemRand.nextFloat() * 0.2F,
               false,
               itemRand.nextInt(360),
               true,
               1.15F
            );
            water.dropMode = true;
            water.scaleTickAdding = -scl / lt * 0.7F;
            water.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.2F + itemRand.nextFloat() * 0.3F, 10.0F);
            world.spawnEntity(water);
         }
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
               float scl = 0.05F + itemRand.nextFloat() * 0.05F;
               int lt = 15 + itemRand.nextInt(10);
               GUNParticle part = new GUNParticle(
                  mana_flow,
                  scl,
                  0.0F,
                  lt,
                  210,
                  world,
                  result.hitVec.x,
                  result.hitVec.y,
                  result.hitVec.z,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  0.5F + itemRand.nextFloat() * 0.1F,
                  1.0F,
                  0.85F + itemRand.nextFloat() * 0.15F,
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

            world.spawnParticle(
               EnumParticleTypes.WATER_BUBBLE,
               result.hitVec.x,
               result.hitVec.y,
               result.hitVec.z,
               (float)itemRand.nextGaussian() / 20.0F,
               (float)itemRand.nextGaussian() / 20.0F,
               (float)itemRand.nextGaussian() / 20.0F,
               new int[0]
            );
         }

         Vec3d look = player.getLookVec().scale(1.5);
         Vec3d vecpos = partpos.add(look);
         int lt = 13;
         float scl = 0.85F + itemRand.nextFloat() * 0.3F;
         GUNParticle water = new GUNParticle(
            waterhammer,
            scl,
            0.0F,
            lt,
            150,
            world,
            vecpos.x,
            vecpos.y,
            vecpos.z,
            (float)(-look.x) / lt,
            (float)(-look.y) / lt,
            (float)(-look.z) / lt,
            0.75F - itemRand.nextFloat() * 0.2F,
            0.8F - itemRand.nextFloat() * 0.2F,
            1.0F,
            true,
            itemRand.nextInt(360)
         );
         water.tracker = HostileProjectiles.Whirl.spins[itemRand.nextInt(3)];
         water.scaleTickAdding = -scl / lt;
         water.alpha = 0.0F;
         water.alphaTickAdding = 1.0F / lt;
         water.alphaGlowing = true;
         water.rotationPitchYaw = new Vec2f(player.rotationPitch, player.rotationYaw);
         world.spawnEntity(water);
      }
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 4 - rapidity;
   }

   @Override
   public int getMaxLeadership(ItemStack itemstack) {
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
      return 700 + might * 40;
   }
}
