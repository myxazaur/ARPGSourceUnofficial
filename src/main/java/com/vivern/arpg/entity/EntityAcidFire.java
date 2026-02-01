package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAcidFire extends StandardBullet {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   public static ResourceLocation texture = new ResourceLocation("arpg:textures/shard_trace.png");
   public static ResourceLocation texturexpl = new ResourceLocation("arpg:textures/scatter.png");
   public static ResourceLocation orb = new ResourceLocation("arpg:textures/acid_orb.png");
   public static ParticleTracker texChanger = new ParticleTracker.TrackerChangeTexOnDrop(texturexpl, true, false);
   public float attractPower = 0.1F;
   public Potion potion;

   public EntityAcidFire(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.ACIDFIRE);
   }

   public EntityAcidFire(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.ACIDFIRE);
   }

   public EntityAcidFire(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.ACIDFIRE);
   }

   public EntityAcidFire(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   @Override
   protected void entityInit() {
   }

   @Override
   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.4;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.4;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.4;
      }
   }

   @Override
   public float getGravityVelocity() {
      return 0.001F;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 25) {
         this.setDead();
      }

      if (this.pos4 != Vec3d.ZERO) {
         this.pos5 = this.pos4;
      } else {
         this.pos5 = this.getPositionVector();
      }

      if (this.pos3 != Vec3d.ZERO) {
         this.pos4 = this.pos3;
      } else {
         this.pos4 = this.getPositionVector();
      }

      if (this.pos2 != Vec3d.ZERO) {
         this.pos3 = this.pos2;
      } else {
         this.pos3 = this.getPositionVector();
      }

      if (this.pos1 != Vec3d.ZERO) {
         this.pos2 = this.pos1;
      } else {
         this.pos2 = this.getPositionVector();
      }

      if (this.getPositionVector() != Vec3d.ZERO) {
         this.pos1 = this.getPositionVector();
      } else {
         this.pos1 = this.thrower.getPositionVector();
      }

      if (this.getThrower() != null && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
         EntityLivingBase entityLivingBase = GetMOP.findNearestEnemy(
            this.world, this.getThrower(), this.posX, this.posY + this.height / 2.0F, this.posZ, 2.5, false
         );
         if (entityLivingBase != null) {
            SuperKnockback.applyMove(
               this,
               -this.attractPower,
               entityLivingBase.posX,
               entityLivingBase.posY + entityLivingBase.height / 2.0F,
               entityLivingBase.posZ
            );
            this.attractPower += 0.07F;
            this.motionX *= 0.7F;
            this.motionY *= 0.7F;
            this.motionZ *= 0.7F;
            this.velocityChanged = true;
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         for (int ss = 0; ss < this.rand.nextInt(4) + 2; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               orb,
               0.04F,
               0.03F,
               80,
               240,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               0.9F + this.rand.nextFloat() / 10.0F,
               1.0F - this.rand.nextFloat() / 10.0F,
               0.35F,
               true,
               this.rand.nextInt(360),
               true,
               3.6F
            );
            bigsmoke.alphaTickAdding = -0.0125F;
            bigsmoke.alphaGlowing = true;
            bigsmoke.dropMode = true;
            bigsmoke.scaleTickDropAdding = 0.0175F;
            bigsmoke.scaleMax = 0.15F + this.rand.nextFloat() / 5.0F;
            bigsmoke.tracker = texChanger;
            this.world.spawnEntity(bigsmoke);
         }
      }
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.acid),
               parameters.getEnchanted("damage", might) * this.magicPower,
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse),
               this.posX,
               this.posY,
               this.posZ
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase && this.potion != null) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               PotionEffect lasteff = entitylivingbase.getActivePotionEffect(this.potion);
               int mode = NBTHelper.GetNBTint(this.weaponstack, "mode") + 1;
               int duration = parameters.getEnchantedi("potion" + mode + "_time_add", witchery);
               if (lasteff != null) {
                  duration += lasteff.getDuration();
               }

               PotionEffect baff = new PotionEffect(
                  this.potion,
                  Math.min(duration, parameters.getEnchantedi("potion" + mode + "_time_max", witchery)),
                  parameters.getEnchantedi("potion" + mode + "_power", witchery)
               );
               entitylivingbase.addPotionEffect(baff);
               if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.25 && entitylivingbase.deathTime < 1) {
                  IAttributeInstance entityColorR = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_RED_MAX);
                  IAttributeInstance entityColorG = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_GREEN_MAX);
                  IAttributeInstance entityColorB = entitylivingbase.getEntityAttribute(PropertiesRegistry.ENTITY_COLOR_BLUE_MAX);
                  entityColorR.setBaseValue(0.8);
                  entityColorG.setBaseValue(0.9);
                  entityColorB.setBaseValue(0.3);
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_COLOREDACID);
               }
            }

            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.larva_water_attack,
                  SoundCategory.AMBIENT,
                  0.55F,
                  1.0F + this.rand.nextFloat() / 10.0F,
                  false
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
               this.posX,
               this.posY,
               this.posZ,
               Sounds.larva_water_attack,
               SoundCategory.AMBIENT,
               0.55F,
               1.0F + this.rand.nextFloat() / 10.0F,
               false
            );
         if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)8);
            this.setDead();
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      Vec3d subtraction = this.pos1.subtract(this.pos2);
      if (this.ticksExisted > 1 && this.world.isRemote && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world,
            texture,
            0.1F,
            240,
            0.9F,
            1.0F,
            0.3F,
            0.17F,
            this.pos1.distanceTo(this.pos2),
            Math.min(4, this.ticksExisted - 2),
            0.3F,
            2.0F,
            this.pos1,
            this.pos2
         );
         laser.setPosition(this.posX, this.posY, this.posZ);
         laser.alphaGlowing = true;
         laser.horizontal = false;
         this.world.spawnEntity(laser);
      }
   }
}
