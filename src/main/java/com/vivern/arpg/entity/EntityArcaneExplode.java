package com.vivern.arpg.entity;

import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.AnimatedGParticle;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityArcaneExplode extends Entity {
   public static ResourceLocation magic_explode = new ResourceLocation("arpg:textures/magic_explode.png");
   public static ResourceLocation circle2 = new ResourceLocation("arpg:textures/circle2.png");
   public static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");
   public float damage;
   public float knockback;
   public float damageRadius;
   public EntityLivingBase thrower;
   private String throwerName;
   public boolean spawnparticle = true;
   public boolean playsnd = true;
   public boolean damageTeammates = true;
   public ItemStack weaponstack;

   public EntityArcaneExplode(World worldIn) {
      super(worldIn);
   }

   public boolean isInRangeToRenderDist(double distance) {
      return false;
   }

   public void onUpdate() {
      if (this.spawnparticle && this.world.isRemote) {
         this.spawnparticle = false;
         AnimatedGParticle anim = new AnimatedGParticle(
            magic_explode,
            1.5F,
            0.0F,
            50,
            240,
            this.world,
            this.posX,
            this.posY + this.height / 2.0F,
            this.posZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            0
         );
         anim.framecount = 50;
         anim.useNormalTime = true;
         anim.alphaGlowing = true;
         anim.randomDeath = false;
         this.world.spawnEntity(anim);
      }

      if (!this.world.isRemote) {
         if (this.playsnd) {
            this.playsnd = false;
            this.world.setEntityState(this, (byte)9);
         }

         if (this.ticksExisted >= 20) {
            this.expl();
         }
      }

      super.onUpdate();
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 9) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.arcane_explode,
               SoundCategory.AMBIENT,
               2.1F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );
      }

      if (id == 8) {
         for (int i = 0; i < 20; i++) {
            GUNParticle particle = new GUNParticle(
               star2,
               0.1F,
               0.003F,
               30,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 6.0F,
               (float)this.rand.nextGaussian() / 6.0F,
               (float)this.rand.nextGaussian() / 6.0F,
               0.15F + this.rand.nextFloat() * 0.1F,
               0.3F - this.rand.nextFloat() * 0.2F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.scaleTickAdding = -0.0033333334F;
            this.world.spawnEntity(particle);
         }

         for (int i = 0; i < 2; i++) {
            GUNParticle particle = new GUNParticle(
               circle2,
               0.1F,
               0.0F,
               7,
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               0.0F,
               0.0F,
               0.0F,
               0.15F + this.rand.nextFloat() * 0.1F,
               0.2F - this.rand.nextFloat() * 0.1F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.scaleTickAdding = 0.8F;
            particle.alphaTickAdding = -0.08F;
            if (i == 0) {
               particle.rotationPitchYaw = new Vec2f(85 + this.rand.nextInt(11), 0.0F);
            }

            this.world.spawnEntity(particle);
         }
      }
   }

   protected void entityInit() {
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      compound.setFloat("damage", this.damage);
      compound.setFloat("knockback", this.knockback);
      compound.setFloat("damageRadius", this.damageRadius);
      compound.setBoolean("damageTeammates", this.damageTeammates);
      if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof EntityPlayer) {
         this.throwerName = this.thrower.getName();
      }

      compound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("damage")) {
         this.damage = compound.getFloat("damage");
      }

      if (compound.hasKey("knockback")) {
         this.knockback = compound.getFloat("knockback");
      }

      if (compound.hasKey("damageRadius")) {
         this.damageRadius = compound.getFloat("damageRadius");
      }

      if (compound.hasKey("damageTeammates")) {
         this.damageTeammates = compound.getBoolean("damageTeammates");
      }

      this.thrower = null;
      this.throwerName = compound.getString("ownerName");
      if (this.throwerName != null && this.throwerName.isEmpty()) {
         this.throwerName = null;
      }

      this.thrower = this.getThrower();
   }

   public void expl() {
      if (!this.world.isRemote) {
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(this.damageRadius * 2.0F, this.damageRadius * 2.0F, this.damageRadius * 2.0F)
            .offset(-this.damageRadius, -this.damageRadius, -this.damageRadius);
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         float damagePerc = 0.4F;
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               float dist = this.getDistance(entity);
               float damageA = this.damage * damagePerc * 2.0F;
               float damageB = this.damage - this.damage * damagePerc;
               float finaldamage = Math.max(-dist / (this.damageRadius / damageA) + damageA + damageB, 0.5F * this.damage);
               DamageSource damageSource = new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.magic);
               if (Team.checkIsOpponent(this.thrower, entity)) {
                  Weapons.dealDamage(
                     damageSource, finaldamage, this.getThrower(), entity, true, this.knockback, this.posX, this.posY, this.posZ
                  );
                  entity.hurtResistantTime = 0;
               } else if (this.damageTeammates) {
                  Weapons.dealDamage(
                     damageSource,
                     finaldamage / 2.0F,
                     this.getThrower(),
                     entity,
                     true,
                     this.knockback,
                     this.posX,
                     this.posY,
                     this.posZ
                  );
               }
            }
         }

         this.world.setEntityState(this, (byte)8);
         this.setDead();
      }
   }

   @Nullable
   public EntityLivingBase getThrower() {
      if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty()) {
         this.thrower = this.world.getPlayerEntityByName(this.throwerName);
         if (this.thrower == null && this.world instanceof WorldServer) {
            try {
               Entity entity = ((WorldServer)this.world).getEntityFromUuid(UUID.fromString(this.throwerName));
               if (entity instanceof EntityLivingBase) {
                  this.thrower = (EntityLivingBase)entity;
               }
            } catch (Throwable var21) {
               this.thrower = null;
            }
         }
      }

      return this.thrower;
   }
}
