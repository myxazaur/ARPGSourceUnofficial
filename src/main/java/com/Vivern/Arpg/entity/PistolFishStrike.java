//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PistolFishStrike extends EntityThrowable {
   public final ItemStack weaponstack;
   static ResourceLocation tex = new ResourceLocation("arpg:textures/shard.png");
   public int livetime = 60;

   public PistolFishStrike(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.PISTOLFISH);
   }

   public PistolFishStrike(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.PISTOLFISH);
   }

   public PistolFishStrike(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.PISTOLFISH);
   }

   public PistolFishStrike(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = 0.5;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * mot;
      }
   }

   @Nullable
   public Entity getHypotheticalTarget() {
      Vec3d pos = this.getPositionVector();
      float f1 = 0.99F;
      double HmotionX = this.motionX;
      double HmotionY = this.motionY;
      double HmotionZ = this.motionZ;

      for (int i = 0; i < this.livetime; i++) {
         pos = pos.add(HmotionX, HmotionY, HmotionZ);
         HmotionX *= f1;
         HmotionY *= f1;
         HmotionZ *= f1;
         HmotionY -= this.getGravityVelocity();

         for (Entity entity : GetMOP.getEntitiesInAABBof(this.world, pos, 0.8, this)) {
            if (Team.checkIsOpponent(this.thrower, entity)) {
               return entity;
            }
         }
      }

      return null;
   }

   protected float getGravityVelocity() {
      return 0.01F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote && this.ticksExisted > this.livetime) {
         this.setDead();
      }

      if (this.isInWater()) {
         float f1 = 1.2F;
         this.motionX *= f1;
         this.motionY *= f1;
         this.motionZ *= f1;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         for (int ss = 0; ss < this.rand.nextInt(4) + 2; ss++) {
            GUNParticle part = new GUNParticle(
               tex,
               0.08F + this.rand.nextFloat() / 30.0F,
               0.028F,
               40,
               -1,
               this.world,
               this.prevPosX,
               this.prevPosY,
               this.prevPosZ,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F,
               1.0F,
               0.9F,
               0.85F - this.rand.nextFloat() / 10.0F,
               false,
               this.rand.nextInt(360),
               true,
               1.5F
            );
            this.world.spawnEntity(part);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.pierce),
               parameters.getEnchanted("damage", might),
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse),
               this.posX,
               this.posY,
               this.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               PotionEffect baff = parameters.getPotion("toxin", PotionEffects.TOXIN, witchery);
               entitylivingbase.addPotionEffect(baff);
               if (entitylivingbase.getHealth() <= 0.0F
                  && entitylivingbase.deathTime <= 1
                  && this.rand.nextFloat()
                     < parameters.getEnchanted("feed_drop_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack))) {
                  entitylivingbase.entityDropItem(new ItemStack(ItemsRegister.FISHFEED), 0.1F);
               }
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.normal_projectile,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.normal_projectile,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }
}
