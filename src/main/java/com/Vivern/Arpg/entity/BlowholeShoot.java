//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.MovingSoundEntity;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.mobs.HostileProjectiles;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;

public class BlowholeShoot extends EntityThrowable implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public float bubbleSize = 0.25F;
   public boolean firstUpdate1 = true;
   public int impacts = 0;
   public int timeCollide = 0;
   public ArrayList<Entity> impacted = new ArrayList<>();
   public float renderAngleX1 = 0.0F;
   public float renderAngleZ1 = 0.0F;
   public float renderAngleX2 = 0.0F;
   public float renderAngleZ2 = 0.0F;
   public float renderSizeSwingX = 0.0F;
   public float renderSizeSwingY = 0.0F;
   public float renderSizeSwingZ = 0.0F;
   static ResourceLocation waterhammer = new ResourceLocation("arpg:textures/waterhammer.png");
   public static ResourceLocation bubbleglow1 = new ResourceLocation("arpg:textures/bubbleglow1.png");
   public static ResourceLocation bubbleglow2 = new ResourceLocation("arpg:textures/bubbleglow2.png");

   public BlowholeShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.BLOWHOLE);
      this.setRandomRenderValues();
   }

   public BlowholeShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.BLOWHOLE);
      this.setRandomRenderValues();
   }

   public BlowholeShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.BLOWHOLE);
      this.setRandomRenderValues();
   }

   public BlowholeShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float bubbleSize) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.bubbleSize = bubbleSize;
      this.setRandomRenderValues();
   }

   public void setRandomRenderValues() {
      if (this.world.isRemote) {
         this.renderAngleX1 = (float)(this.rand.nextGaussian() * 6.0);
         this.renderAngleZ1 = (float)(this.rand.nextGaussian() * 6.0);
         this.renderAngleX2 = (float)(this.rand.nextGaussian() * 3.0);
         this.renderAngleZ2 = (float)(this.rand.nextGaussian() * 3.0);
         this.renderSizeSwingX = (float)this.rand.nextGaussian();
         this.renderSizeSwingY = (float)this.rand.nextGaussian();
         this.renderSizeSwingZ = (float)this.rand.nextGaussian();
      }
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = 0.15;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * mot * 0.5;
      }
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public float getActualSize(int ticks, int prevTicks, float partialTicks) {
      float partial = GetMOP.partial((float)ticks, (float)prevTicks, partialTicks);
      return Math.min(partial / 5.0F + 0.2F * this.bubbleSize, this.bubbleSize);
   }

   public void onUpdate() {
      if (this.ticksExisted < 2 || this.ticksExisted % 40 == 0) {
         IEntitySynchronize.sendSynchronize(this, 64.0, this.bubbleSize, 0.0, 0.0, 0.0);
      }

      super.onUpdate();
      if (this.bubbleSize >= 0.8F) {
         float siz = this.getActualSize(this.ticksExisted, this.ticksExisted - 1, 0.0F) / 2.0F;
         AxisAlignedBB aabb = new AxisAlignedBB(
            this.posX - siz,
            this.posY - siz + this.height / 2.0F,
            this.posZ - siz,
            this.posX + siz,
            this.posY + siz + this.height / 2.0F,
            this.posZ + siz
         );
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, aabb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               this.onImpact(entity, false);
            }
         }
      }

      if (this.ticksExisted > 80) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.blowhole_impact,
               SoundCategory.AMBIENT,
               0.8F + this.bubbleSize / 5.0F,
               1.25F + this.rand.nextFloat() / 10.0F - this.bubbleSize / 5.0F
            );
         IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY + this.height / 2.0F, this.posZ, 1.0);
         this.setDead();
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      if (a == 0.0) {
         this.bubbleSize = (float)x;
         if (this.firstUpdate1 && x >= 0.8) {
            this.firstUpdate1 = false;
            Minecraft.getMinecraft()
               .getSoundHandler()
               .playSound(
                  new MovingSoundEntity(
                     this,
                     Sounds.blowhole_fly,
                     SoundCategory.PLAYERS,
                     0.4F + this.bubbleSize / 5.0F,
                     1.25F + this.rand.nextFloat() / 10.0F - this.bubbleSize / 5.0F,
                     true
                  )
               );
         }
      } else {
         for (int ss = 0; ss < 2.0F + this.bubbleSize; ss++) {
            float maxsize = (0.8F + this.rand.nextFloat() * 0.2F) * this.bubbleSize;
            int lt = 6 + this.rand.nextInt(5);
            GUNParticle bubble = new GUNParticle(
               waterhammer,
               0.04F,
               -0.001F,
               lt,
               130,
               this.world,
               x,
               y,
               z,
               0.0F,
               0.0F,
               0.0F,
               0.5F - this.rand.nextFloat() / 5.0F,
               0.8F - this.rand.nextFloat() / 5.0F,
               1.0F - this.rand.nextFloat() / 10.0F,
               true,
               this.rand.nextInt(180)
            );
            bubble.alphaTickAdding = -0.03F;
            bubble.alphaGlowing = true;
            bubble.scaleTickAdding = maxsize / lt;
            bubble.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
            this.world.spawnEntity(bubble);
         }

         int countOfParticles = 5 + (int)(this.bubbleSize * 8.0F);
         float R = (0.1F + this.bubbleSize + (float)this.rand.nextGaussian() / 30.0F) * 0.3F;
         float reduceSpeed = 5.0F;

         for (int i = 0; i < countOfParticles; i++) {
            float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
            float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (this.rand.nextBoolean()) {
               Z *= -1.0F;
            }

            GUNParticle particle = new GUNParticle(
               this.rand.nextFloat() < 0.5F ? bubbleglow1 : bubbleglow2,
               0.05F + this.bubbleSize / 30.0F + (float)this.rand.nextGaussian() / 40.0F,
               0.025F,
               22 + this.rand.nextInt(16),
               150,
               this.world,
               x + X,
               y + Y,
               z + Z,
               X / reduceSpeed,
               Y / reduceSpeed,
               Z / reduceSpeed,
               0.5F - this.rand.nextFloat() / 5.0F,
               0.8F - this.rand.nextFloat() / 5.0F,
               1.0F - this.rand.nextFloat() / 5.0F,
               true,
               0,
               true,
               2.0F
            );
            particle.alphaGlowing = true;
            this.world.spawnEntity(particle);
         }
      }
   }

   public void onImpact(Entity entity, boolean fromSpecialExplode) {
      if (!fromSpecialExplode && entity instanceof BlowholeShoot) {
         BlowholeShoot other = (BlowholeShoot)entity;
         if (this.bubbleSize < 0.8F && other.bubbleSize > 0.8F) {
            if (other.specialExplode()) {
               this.setDead();
            }
         } else if (this.bubbleSize > 0.8F && other.bubbleSize < 0.8F && this.specialExplode()) {
            other.setDead();
         }
      }

      if (Team.checkIsOpponent(this.thrower, entity) && !this.impacted.contains(entity) && !this.world.isRemote) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
         int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
         Weapons.dealDamage(
            new WeaponDamage(this.weaponstack, this.getThrower(), this, false, this.bubbleSize < 0.8, this, WeaponDamage.water),
            parameters.getEnchanted("damage", might),
            this.getThrower(),
            entity,
            true,
            parameters.getEnchanted("knockback", impulse),
            this.posX,
            this.posY,
            this.posZ
         );
         entity.hurtResistantTime = 0;
         if (this.bubbleSize < 0.8) {
            IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY + this.height / 2.0F, this.posZ, 1.0);
            this.setDead();
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.blowhole_impact,
                  SoundCategory.AMBIENT,
                  0.8F + this.bubbleSize / 5.0F,
                  1.25F + this.rand.nextFloat() / 10.0F - this.bubbleSize / 5.0F
               );
         } else if (this.bubbleSize < 1.8) {
            this.impacts++;
            this.impacted.add(entity);
            if (this.impacts > 3) {
               IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY + this.height / 2.0F, this.posZ, 1.0);
               this.setDead();
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.blowhole_impact,
                     SoundCategory.AMBIENT,
                     0.8F + this.bubbleSize / 5.0F,
                     1.25F + this.rand.nextFloat() / 10.0F - this.bubbleSize / 5.0F
                  );
            }
         } else {
            this.impacted.add(entity);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         this.onImpact(result.entityHit, false);
      } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null
         && !this.world.isRemote) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.blowhole_impact,
               SoundCategory.AMBIENT,
               0.8F + this.bubbleSize / 5.0F,
               1.25F + this.rand.nextFloat() / 10.0F - this.bubbleSize / 5.0F
            );
         if (result.hitVec != null) {
            IEntitySynchronize.sendSynchronize(
               this, 64.0, result.hitVec.x, result.hitVec.y, result.hitVec.z, 1.0, 0.0, 0.0
            );
         }

         this.setDead();
      }
   }

   public boolean specialExplode() {
      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0 && this.bubbleSize > 0.8F) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
         int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
         float plasmadamage = parameters.getEnchanted("damage_plasma", might);
         float plasmaknockback = parameters.getEnchanted("knockback_plasma", impulse);
         float R = this.bubbleSize * 0.6F;

         for (int i = 0; i < this.bubbleSize * 4.0F; i++) {
            float rand1 = this.rand.nextFloat() * 2.0F - 1.0F;
            float rand2 = this.rand.nextFloat() * 2.0F - 1.0F;
            float X = rand1 * R;
            float new_R = (float)Math.sqrt(R * R - X * X);
            float Y = rand2 * new_R;
            float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
            if (this.rand.nextBoolean()) {
               Z *= -1.0F;
            }

            float bright = this.rand.nextFloat() < 0.3F ? this.rand.nextFloat() : 0.0F;
            HostileProjectiles.Plasma plasma = new HostileProjectiles.Plasma(
               this.world,
               this.getThrower(),
               0.9F,
               0.0F,
               100,
               this.posX + X,
               this.posY + Y,
               this.posZ + Z,
               plasmadamage,
               plasmaknockback,
               1,
               false,
               bright * 0.57F,
               0.36F + 0.55F * bright,
               0.45F + this.rand.nextFloat() * 0.12F + 0.43F * bright
            );
            this.world.spawnEntity(plasma);
         }

         IEntitySynchronize.sendSynchronize(this, 64.0, this.posX, this.posY, this.posZ, 1.0);
         float siz = this.getActualSize(this.ticksExisted, this.ticksExisted - 1, 0.0F) / 2.0F;
         AxisAlignedBB aabb = new AxisAlignedBB(
            this.posX - siz,
            this.posY - siz + this.height / 2.0F,
            this.posZ - siz,
            this.posX + siz,
            this.posY + siz + this.height / 2.0F,
            this.posZ + siz
         );
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, aabb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               this.onImpact(entity, true);
            }
         }

         if (this.getThrower() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)this.getThrower();
            double hposY = this.posY + this.height / 2.0F;
            siz *= 2.0F;
            float radiusSq = siz * siz;
            int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, this.weaponstack);
            boolean silktouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, this.weaponstack) > 0;
            int harvestlvl = parameters.geti("harvest_lvl");
            float hardnessBreaks = parameters.get("hardness_breaks");

            for (BlockPos blockpos : GetMOP.getBlockPosesCollidesAABB(this.world, aabb.grow(2.0), false)) {
               if (blockpos.distanceSqToCenter(this.posX, hposY, this.posZ) < radiusSq
                  && Weapons.easyBreakBlockFor(this.world, hardnessBreaks, blockpos)) {
                  Weapons.destroyBlockAsTool(this.world, blockpos, player, "pickaxe", 5, fortune, silktouch);
               }
            }
         }

         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.blowhole_special,
               SoundCategory.AMBIENT,
               0.9F + this.bubbleSize / 5.0F,
               1.25F + this.rand.nextFloat() / 10.0F - this.bubbleSize / 5.0F
            );
         this.setDead();
         return true;
      } else {
         return false;
      }
   }

   public boolean handleWaterMovement() {
      return false;
   }
}
