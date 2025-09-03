//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityButterfly extends EntityThrowable {
   protected int hitcount = 0;
   public final ItemStack weaponstack;

   public EntityButterfly(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.BUTTERFLY);
   }

   public EntityButterfly(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.BUTTERFLY);
   }

   public EntityButterfly(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.BUTTERFLY);
   }

   public EntityButterfly(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 6) {
         this.setDead();
      }

      this.world.setEntityState(this, (byte)8);
      if (this.hitcount > 2) {
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         ResourceLocation resoue = new ResourceLocation("arpg:textures/butfly.png");
         Entity spelll = new GUNParticle(
            resoue,
            0.12F,
            0.001F,
            32,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 14.0F,
            (float)this.rand.nextGaussian() / 14.0F,
            (float)this.rand.nextGaussian() / 14.0F,
            0.7F + (float)this.rand.nextGaussian() / 5.0F,
            0.8F + (float)this.rand.nextGaussian() / 8.0F,
            0.7F + (float)this.rand.nextGaussian() / 5.0F,
            false,
            0
         );
         this.world.spawnEntity(spelll);
      }
   }

   protected void onImpact(RayTraceResult result) {
      double damageRadius = 1.0;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (Team.checkIsOpponent(this.thrower, entity)) {
               Weapons.dealDamage(
                  null,
                  3.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) / 2.0F,
                  this.thrower,
                  entity,
                  true,
                  0.75F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 2.5F,
                  this.posX,
                  this.posY,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
               this.hitcount++;
            }
         }
      }

      if (result.typeOfHit == Type.BLOCK) {
         this.setDead();
      }
   }
}
