package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBogFlower extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");
   ResourceLocation acid = new ResourceLocation("arpg:textures/acid_splash3.png");
   ResourceLocation lightroots = new ResourceLocation("arpg:textures/light_roots.png");
   public float magicPower = 1.0F;
   public boolean isImpacted = false;

   public EntityBogFlower(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.BOGFLOWER);
   }

   public EntityBogFlower(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.BOGFLOWER);
   }

   public EntityBogFlower(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.BOGFLOWER);
   }

   public EntityBogFlower(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 40 && !this.isImpacted) {
         this.world
            .playSound(
               null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.bog_flower_impact,
               SoundCategory.AMBIENT,
               0.9F,
               0.95F + this.rand.nextFloat() / 10.0F
            );
         this.isImpacted = true;
      }

      if (!this.world.isRemote) {
         if (this.ticksExisted > 500) {
            this.setDead();
         }

         if (this.ticksExisted % 2 == 0) {
            this.world.setEntityState(this, (byte)8);
         }

         if (this.isImpacted) {
            this.expl();
         }
      }
   }

   protected float getGravityVelocity() {
      return 0.013F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         double x = this.posX;
         double y = this.posY;
         double z = this.posZ;
         double doublePi = Math.PI * 2;
         double RADIUS = 5.0 - this.ticksExisted / 160.0;

         for (double i = 0.0; i < doublePi; i += doublePi / 8.0) {
            if (this.rand.nextFloat() < 0.4) {
               double newPosX = x + RADIUS * Math.cos(i);
               double newPosZ = z + RADIUS * Math.sin(i);
               float speedX = (float)(newPosX - x) * -0.04F;
               float speedZ = (float)(newPosZ - z) * -0.04F;
               GUNParticle fire = new GUNParticle(
                  this.acid,
                  0.1F + this.rand.nextFloat() / 10.0F,
                  -0.01F,
                  8 + this.rand.nextInt(10),
                  240,
                  this.world,
                  newPosX,
                  this.posY + 0.1,
                  newPosZ,
                  speedX + (float)this.rand.nextGaussian() / 25.0F,
                  0.0F,
                  speedZ + (float)this.rand.nextGaussian() / 25.0F,
                  1.0F,
                  0.8F + (float)this.rand.nextGaussian() / 5.0F,
                  1.0F,
                  true,
                  this.rand.nextInt(360)
               );
               fire.alphaTickAdding = -0.04F;
               fire.alphaGlowing = true;
               fire.acidRender = true;
               this.world.spawnEntity(fire);
            }
         }

         GUNParticle bigsmoke = new GUNParticle(
            this.largesmoke,
            1.5F + (float)this.rand.nextGaussian() / 3.0F,
            -0.009F,
            30,
            180,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 13.0F,
            (float)this.rand.nextGaussian() / 19.0F,
            (float)this.rand.nextGaussian() / 13.0F,
            0.5F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            0.1F,
            true,
            this.rand.nextInt(360),
            true,
            1.0F
         );
         bigsmoke.alphaTickAdding = -0.029F;
         bigsmoke.alphaGlowing = true;
         this.world.spawnEntity(bigsmoke);
      }

      if (id == 8) {
         int lt = 13 + this.rand.nextInt(5);
         float sc = 0.3F + (float)this.rand.nextGaussian() / 13.0F;
         GUNParticle fire2 = new GUNParticle(
            this.lightroots,
            sc,
            0.0F,
            lt,
            200,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            0.9F + (float)this.rand.nextGaussian() / 5.0F,
            0.9F + (float)this.rand.nextGaussian() / 5.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         fire2.scaleTickAdding = -sc / lt;
         fire2.alphaGlowing = true;
         this.world.spawnEntity(fire2);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         BlockPos pos = result.getBlockPos();
         if (pos != null
            && this.world.getBlockState(pos).getBlock().getCollisionBoundingBox(this.world.getBlockState(pos), this.world, pos) != null
            && !this.isImpacted) {
            this.isImpacted = true;
            this.world
               .playSound(
                  null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.bog_flower_impact,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.95F + this.rand.nextFloat() / 10.0F
               );
         }
      }
   }

   public void expl() {
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      this.setNoGravity(true);
      double damageRadius = 5.0 - this.ticksExisted / 160.0;
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (entitylivingbase.getDistance(this) <= damageRadius) {
                  boolean opon = Team.checkIsOpponent(this.thrower, entitylivingbase);
                  if (opon) {
                     PotionEffect baff = new PotionEffect(
                        PotionEffects.TOXIN,
                        Math.round((100 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack) * 35) * this.magicPower),
                        this.magicPower >= 2.0F ? 1 : 0
                     );
                     entitylivingbase.addPotionEffect(baff);
                  }

                  if (entitylivingbase == this.getThrower() || !opon) {
                     PotionEffect baff = new PotionEffect(PotionEffects.TOXIN, 40);
                     entitylivingbase.addPotionEffect(baff);
                  }
               }
            }
         }

         this.world.setEntityState(this, (byte)5);
      }
   }
}
