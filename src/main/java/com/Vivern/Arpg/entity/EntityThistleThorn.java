//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.potions.Spiked;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.HashMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThistleThorn extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public boolean charged = false;
   public EntityThistleThorn other1;
   public EntityThistleThorn other2;
   public boolean canMultiple = false;
   static ResourceLocation tex = new ResourceLocation("arpg:textures/shard.png");
   static ResourceLocation sparkle = new ResourceLocation("arpg:textures/spellblue4.png");

   public EntityThistleThorn(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.PISTOLFISH);
   }

   public EntityThistleThorn(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.PISTOLFISH);
   }

   public EntityThistleThorn(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.PISTOLFISH);
   }

   public EntityThistleThorn(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      double mot = 0.4;
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
   }

   protected float getGravityVelocity() {
      return 0.005F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (this.ticksExisted < 2 && this.charged) {
            this.world.setEntityState(this, (byte)4);
         }

         if (this.rand.nextFloat() < 0.5) {
            this.world.setEntityState(this, (byte)5);
         }
      }

      if (this.ticksExisted > 40) {
         this.setDead();
      }

      if (this.isInWater()) {
         float f1 = 1.156F;
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
               0.04F + this.rand.nextFloat() / 30.0F,
               0.048F,
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
               1.0F,
               0.55F - this.rand.nextFloat() / 10.0F,
               false,
               this.rand.nextInt(360),
               true,
               2.5F
            );
            this.world.spawnEntity(part);
         }
      }

      if (id == 4) {
         this.charged = true;
      }

      if (id == 5) {
         float rr = 0.95F;
         float gg = 1.0F;
         float bb = 0.25F;
         if (this.charged) {
            rr = 0.8F;
            gg = 0.27F;
            bb = 0.95F;
         }

         GUNParticle part = new GUNParticle(
            sparkle,
            0.025F,
            0.001F,
            14,
            240,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 30.0F,
            0.0F,
            (float)this.rand.nextGaussian() / 30.0F,
            rr,
            gg,
            bb,
            false,
            this.rand.nextInt(360),
            true,
            2.0F
         );
         part.scaleTickAdding = -0.0016F;
         this.world.spawnEntity(part);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)
            && Weapons.canDealDamageTo(result.entityHit)
            && !this.world.isRemote) {
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               if (this.charged) {
                  if (entitylivingbase.isPotionActive(PotionEffects.SPIKED)
                     && this.canMultiple
                     && entitylivingbase.getActivePotionEffect(PotionEffects.SPIKED) instanceof Spiked.SpikedEffect) {
                     Spiked.SpikedEffect eff = (Spiked.SpikedEffect)entitylivingbase.getActivePotionEffect(PotionEffects.SPIKED);
                     int lvl = eff.getAmplifier();
                     if (lvl <= 10) {
                        lvl *= 2;
                     } else if (lvl <= 20) {
                        lvl = (int)(lvl * 1.5);
                     } else if (lvl <= 40) {
                        lvl = (int)(lvl * 1.25);
                     } else {
                        lvl = (int)(lvl * 1.2);
                     }

                     lvl = Math.min(lvl, 100);
                     Spiked.SpikedEffect debaff = new Spiked.SpikedEffect(PotionEffects.SPIKED, eff.getDuration(), lvl);
                     int rz = lvl - eff.getAmplifier();
                     debaff.mapTeams = eff.mapTeams;
                     if (this.thrower instanceof EntityPlayer) {
                        debaff.addToTeams(Team.getTeamFor((EntityPlayer)this.thrower), rz);
                     }

                     entitylivingbase.addPotionEffect(debaff);
                     if (this.other1 != null) {
                        this.other1.canMultiple = false;
                     }

                     if (this.other2 != null) {
                        this.other2.canMultiple = false;
                     }
                  }
               } else {
                  int lvlx = 0;
                  int dur = 300;
                  HashMap<String, Integer> teams = null;
                  if (entitylivingbase.isPotionActive(PotionEffects.SPIKED) && entitylivingbase.getActivePotionEffect(PotionEffects.SPIKED) instanceof Spiked.SpikedEffect
                     )
                   {
                     Spiked.SpikedEffect effx = (Spiked.SpikedEffect)entitylivingbase.getActivePotionEffect(PotionEffects.SPIKED);
                     lvlx = Math.min(effx.getAmplifier() + 1, 50);
                     dur = Math.max(effx.getDuration(), dur);
                     teams = effx.mapTeams;
                  }

                  Spiked.SpikedEffect debaffx = new Spiked.SpikedEffect(PotionEffects.SPIKED, dur, lvlx);
                  if (teams != null) {
                     debaffx.mapTeams = teams;
                  }

                  if (this.thrower instanceof EntityPlayer) {
                     debaffx.addToTeams(Team.getTeamFor((EntityPlayer)this.thrower), 1);
                  }

                  entitylivingbase.addPotionEffect(debaffx);
               }

               if (!this.charged
                  && (entitylivingbase.getHealth() <= 0.0F || this.rand.nextFloat() < 0.1F)
                  && !NBTHelper.GetNBTboolean(this.weaponstack, "powered")) {
                  NBTHelper.AddNBTint(this.weaponstack, 1, "charge");
               }
            }

            Weapons.dealDamage(
               null,
               (3.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack)) * this.magicPower,
               this.thrower,
               result.entityHit,
               true,
               0.5F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack) / 2.0F,
               this.thrower.posX,
               this.thrower.posY,
               this.thrower.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.thistle_impact,
                  SoundCategory.AMBIENT,
                  0.6F,
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
               Sounds.thistle_impact,
               SoundCategory.AMBIENT,
               0.6F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }
}
