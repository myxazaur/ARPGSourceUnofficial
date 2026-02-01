package com.vivern.arpg.entity;

import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.RenderModule;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlasmaRifleBall extends EntityThrowable implements RenderModule.IRenderModuleMulticolored {
   public final ItemStack weaponstack;
   static ResourceLocation explode = new ResourceLocation("arpg:textures/blueexplode2.png");
   private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(PlasmaRifleBall.class, DataSerializers.BOOLEAN);
   static Vec3d white = new Vec3d(1.0, 1.0, 1.0);
   static Vec3d red = new Vec3d(1.0, 0.37, 0.43);

   public PlasmaRifleBall(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMARIFLE);
   }

   public PlasmaRifleBall(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMARIFLE);
   }

   public PlasmaRifleBall(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.PLASMARIFLE);
   }

   public PlasmaRifleBall(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   protected void entityInit() {
      this.dataManager.register(POWERED, false);
   }

   public void setPowered() {
      this.dataManager.set(POWERED, true);
   }

   public boolean getPowered() {
      return (Boolean)this.dataManager.get(POWERED);
   }

   @Override
   public Vec3d getColor(int index) {
      return this.getPowered() ? red : white;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double mot = parameters.getEnchanted("unstable", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, this.weaponstack));
      this.motionX = this.motionX + entityThrower.motionX * mot;
      this.motionZ = this.motionZ + entityThrower.motionZ * mot;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * mot * 0.5;
      }
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 140) {
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         GUNParticle bigsmoke1 = new GUNParticle(
            explode,
            0.1F,
            0.0F,
            11,
            240,
            this.world,
            this.lastTickPosX,
            this.lastTickPosY,
            this.lastTickPosZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         bigsmoke1.alphaTickAdding = -0.095F;
         bigsmoke1.alphaGlowing = true;
         bigsmoke1.scaleTickAdding = 0.09F;
         this.world.spawnEntity(bigsmoke1);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit) && !this.world.isRemote) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
            Weapons.dealDamage(
               new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.plasma),
               this.getPowered() ? parameters.getEnchanted("damage_powered", might) : parameters.getEnchanted("damage", might),
               this.getThrower(),
               result.entityHit,
               true,
               parameters.getEnchanted("knockback", impulse)
            );
            result.entityHit.hurtResistantTime = 0;
            if (result.entityHit instanceof EntityLivingBase) {
               EntityLivingBase entitylivingbase = (EntityLivingBase)result.entityHit;
               if (entitylivingbase.getHealth() <= 0.0F && this.rand.nextFloat() < 0.15F) {
                  DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ELECTRIC);
               }
            }

            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.plasma_rifle_impact,
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
               Sounds.plasma_rifle_impact,
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
