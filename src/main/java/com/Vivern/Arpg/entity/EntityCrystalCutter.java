package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCrystalCutter extends EntityThrowable {
   public final ItemStack weaponstack;
   public float cutterSize = 2.5F;
   public boolean destroying = false;
   public boolean triple = false;
   public int destroyTick = 0;
   public int livetime = 100;
   public double rightNodeX = 0.0;
   public double rightNodeY = 0.0;
   public double rightNodeZ = 0.0;
   public double leftNodeX = 0.0;
   public double leftNodeY = 0.0;
   public double leftNodeZ = 0.0;
   static ResourceLocation shard = new ResourceLocation("arpg:textures/plasma_railgun_bullet_tex.png");
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/plasma_cloud.png");

   public EntityCrystalCutter(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALCUTTER);
   }

   public EntityCrystalCutter(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALCUTTER);
   }

   public EntityCrystalCutter(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CRYSTALCUTTER);
   }

   public EntityCrystalCutter(World world, EntityLivingBase thrower, ItemStack itemstack) {
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
   }

   protected float getGravityVelocity() {
      return 0.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted < 2 || this.ticksExisted % 40 == 0) {
         if (this.triple) {
            this.world.setEntityState(this, (byte)11);
         }

         int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack);
         this.world.setEntityState(this, (byte)(-range));
      }

      if (this.ticksExisted > this.livetime && !this.destroying) {
         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.crystal_cutter_impact,
               SoundCategory.AMBIENT,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
         this.destroying = true;
         this.motionX *= 0.5;
         this.motionY *= 0.5;
         this.motionZ *= 0.5;
      }

      if (this.destroying) {
         this.destroyTick++;
         if (this.destroyTick > 4 && !this.world.isRemote) {
            this.setDead();
            this.world.setEntityState(this, (byte)9);
         }
      }

      float cutterSizeTo = this.cutterSize * GetMOP.getfromto((float)(this.ticksExisted + 1), 0.0F, 10.0F) / 2.0F;
      Vec3d motionVec2d = new Vec3d(this.motionX, 0.0, this.motionZ);
      Vec3d pichyawVec = GetMOP.Vec3dToPitchYaw(motionVec2d);
      Vec3d leftVec = GetMOP.PitchYawToVec3d((float)pichyawVec.x, (float)pichyawVec.y + 90.0F)
         .normalize()
         .scale(cutterSizeTo);
      Vec3d rightVec = GetMOP.PitchYawToVec3d((float)pichyawVec.x, (float)pichyawVec.y - 90.0F)
         .normalize()
         .scale(cutterSizeTo);
      Vec3d thisVec = this.getPositionVector();
      Vec3d absRightVec = thisVec.add(rightVec);
      Vec3d absLeftVec = thisVec.add(leftVec);
      this.rightNodeX = absRightVec.x;
      this.rightNodeY = absRightVec.y;
      this.rightNodeZ = absRightVec.z;
      this.leftNodeX = absLeftVec.x;
      this.leftNodeY = absLeftVec.y;
      this.leftNodeZ = absLeftVec.z;
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      float raySize = this.triple ? parameters.get("cutter_thickness_triple") : parameters.get("cutter_thickness");
      List<Entity> list = GetMOP.findEntitiesOnPath(absLeftVec, absRightVec, this.world, this.thrower, raySize, 0.2F);
      int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
      int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);

      for (Entity entity : list) {
         if (entity != this && Team.checkIsOpponent(this.thrower, entity)) {
            if (this.thrower != null) {
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, null, WeaponDamage.plasma),
                  parameters.getEnchanted("damage", might),
                  this.getThrower(),
                  entity,
                  true,
                  parameters.getEnchanted("knockback", impulse),
                  this.thrower.posX,
                  this.thrower.posY,
                  this.thrower.posZ
               );
            }

            entity.hurtResistantTime = 2;
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 9) {
         for (int ss = 0; ss < 5; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               cloud,
               0.4F + this.rand.nextFloat() / 6.0F,
               0.0F,
               30,
               200,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 17.0F,
               (float)this.rand.nextGaussian() / 17.0F,
               (float)this.rand.nextGaussian() / 17.0F,
               1.0F,
               0.35F + this.rand.nextFloat() / 10.0F,
               0.6F,
               true,
               this.rand.nextInt(360)
            );
            bigsmoke.alphaTickAdding = -0.03F;
            bigsmoke.alphaGlowing = true;
            this.world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 6; ss++) {
            EntityCubicParticle part = new EntityCubicParticle(
               shard,
               0.005F + this.rand.nextFloat() * 0.003F,
               0.017F,
               16 + this.rand.nextInt(10),
               150,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F + 0.1F,
               (float)this.rand.nextGaussian() / 14.0F,
               1.0F,
               0.35F + this.rand.nextFloat() / 10.0F,
               0.6F,
               false,
               this.rand.nextFloat(),
               this.rand.nextFloat(),
               this.rand.nextFloat(),
               0.14F,
               true,
               0.0F
            );
            this.world.spawnEntity(part);
         }
      }

      if (id == 8) {
         this.destroying = true;
         this.motionX *= 0.5;
         this.motionY *= 0.5;
         this.motionZ *= 0.5;
      }

      if (id == 7) {
         this.destroying = true;
         this.motionX *= 0.05;
         this.motionY *= 0.05;
         this.motionZ *= 0.05;
      }

      if (id == 11) {
         this.triple = true;
      }

      if (id < 0) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         this.cutterSize = parameters.getEnchanted("cutter_size", -id);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit == null
         && this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null
         && !this.destroying) {
         BlockPos rightPos = new BlockPos(this.rightNodeX, this.rightNodeY, this.rightNodeZ);
         BlockPos leftPos = new BlockPos(this.leftNodeX, this.leftNodeY, this.leftNodeZ);
         if (this.world.getBlockState(rightPos).getBlock().getCollisionBoundingBox(this.world.getBlockState(rightPos), this.world, rightPos)
               != null
            || this.world.getBlockState(leftPos).getBlock().getCollisionBoundingBox(this.world.getBlockState(leftPos), this.world, leftPos)
               != null) {
            this.world
               .playSound(
                  (EntityPlayer)null,
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.crystal_cutter_impact,
                  SoundCategory.AMBIENT,
                  0.8F,
                  0.9F + this.rand.nextFloat() / 5.0F
               );
            if (!this.world.isRemote) {
               this.world.setEntityState(this, (byte)7);
               this.destroying = true;
               this.motionX *= 0.05;
               this.motionY *= 0.05;
               this.motionZ *= 0.05;
            }
         }
      }
   }
}
