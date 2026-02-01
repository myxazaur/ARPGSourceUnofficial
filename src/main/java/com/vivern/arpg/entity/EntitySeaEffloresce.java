package com.vivern.arpg.entity;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySeaEffloresce extends Entity implements IEntitySynchronize {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public EntityLivingBase thrower;
   public String throwerName;
   public int destroyTick = 0;
   public static ResourceLocation tex = new ResourceLocation("arpg:textures/sea_effloresce.png");
   public int livetime = 400;

   public EntitySeaEffloresce(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.SEAEFFLORESCE);
      this.setSize(0.5F, 0.5F);
   }

   public EntitySeaEffloresce(World world, EntityLivingBase thrower) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.SEAEFFLORESCE);
      this.setSize(0.5F, 0.5F);
      this.thrower = thrower;
   }

   public EntitySeaEffloresce(World world, double x, double y, double z) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.SEAEFFLORESCE);
      this.setSize(0.5F, 0.5F);
      this.setPosition(x, y, z);
   }

   public EntitySeaEffloresce(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.setSize(0.5F, 0.5F);
      this.thrower = thrower;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX;
      this.motionZ = this.motionZ + entityThrower.motionZ;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.5;
      }
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      float f = MathHelper.sqrt(x * x + y * y + z * z);
      x /= f;
      y /= f;
      z /= f;
      x += this.rand.nextGaussian() * 0.0075F * inaccuracy;
      y += this.rand.nextGaussian() * 0.0075F * inaccuracy;
      z += this.rand.nextGaussian() * 0.0075F * inaccuracy;
      x *= velocity;
      y *= velocity;
      z *= velocity;
      this.motionX = x;
      this.motionY = y;
      this.motionZ = z;
      float f1 = MathHelper.sqrt(x * x + z * z);
      this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180.0 / Math.PI));
      this.rotationPitch = (float)(MathHelper.atan2(y, f1) * (180.0 / Math.PI));
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
   }

   protected float getGravityVelocity() {
      return this.isInWater() ? 0.0F : 0.001F;
   }

   @Nullable
   public AxisAlignedBB getCollisionBox(Entity entityIn) {
      return null;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox() {
      return this.getEntityBoundingBox();
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
         this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
         float f = 0.98F;
         if (this.onGround) {
            BlockPos underPos = new BlockPos(
               MathHelper.floor(this.posX),
               MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
               MathHelper.floor(this.posZ)
            );
            IBlockState underState = this.world.getBlockState(underPos);
            f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
         }

         this.motionX *= f;
         this.motionY *= 0.91;
         this.motionZ *= f;
         if (this.onGround) {
            this.motionY *= -0.9F;
         }

         if (this.thrower != null && this.ticksExisted < 3) {
            IEntitySynchronize.sendSynchronize(this, 48.0, this.thrower.getEntityId(), 0.0, 0.0, 0.0, 0.0, 0.0);
         }

         if (this.ticksExisted > this.livetime) {
            this.setDead();
         }

         if (this.thrower != null && this.ticksExisted % 3 == 0) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            float attraction = parameters.get("attraction");
            double distsq = this.getDistanceSq(this.thrower);
            if (this.ticksExisted < 60 && distsq < 49.0) {
               SuperKnockback.applyMove(
                  this,
                  0.02F * attraction,
                  this.thrower.posX,
                  this.thrower.posY + this.thrower.height / 2.0F,
                  this.thrower.posZ
               );
            } else if (this.ticksExisted < 60 && distsq >= 49.0) {
               this.motionX *= 0.7;
               this.motionY *= 0.7;
               this.motionZ *= 0.7;
            }

            if (this.ticksExisted > 60) {
               SuperKnockback.applyMove(
                  this,
                  -0.05F * attraction,
                  this.thrower.posX,
                  this.thrower.posY + this.thrower.height / 2.0F,
                  this.thrower.posZ
               );
               if (this.getDistanceSq(this.thrower) < 2.0) {
                  this.setDead();
               }
            }

            if (distsq > 81.0) {
               SuperKnockback.applyMove(
                  this,
                  -0.2F * attraction,
                  this.thrower.posX,
                  this.thrower.posY + this.thrower.height / 2.0F,
                  this.thrower.posZ
               );
            }

            if (distsq > 169.0) {
               this.setDead();
            }

            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) == 0
               && this.thrower.getHeldItemMainhand().getItem() != ItemsRegister.SEAEFFLORESCE) {
               SuperKnockback.applyMove(
                  this,
                  -0.2F * attraction,
                  this.thrower.posX,
                  this.thrower.posY + this.thrower.height / 2.0F,
                  this.thrower.posZ
               );
               this.destroyTick += 3;
               if (this.destroyTick > 21) {
                  this.setDead();
               }
            }
         }

         if (this.ticksExisted % 10 == 0) {
            WeaponParameters parametersx = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
            double damageRadius = parametersx.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
            AxisAlignedBB aabb = this.getEntityBoundingBox().grow(damageRadius);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, aabb);
            boolean hasattack = false;
            if (!list.isEmpty()) {
               for (Entity entity : list) {
                  if (Team.checkIsOpponent(this.thrower, entity)
                     && Weapons.canDealDamageTo(entity)
                     && !(entity instanceof EntitySeaEffloresce)
                     && GetMOP.thereIsNoBlockCollidesBetween(
                        this.world,
                        new Vec3d(this.posX, this.posY + this.height / 2.0F, this.posZ),
                        new Vec3d(entity.posX, entity.posY + entity.height / 2.0F, entity.posZ),
                        1.0F,
                        null,
                        false
                     )) {
                     int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
                     int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                     Weapons.dealDamage(
                        new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.vines),
                        parametersx.getEnchanted("damage", might) * this.magicPower,
                        this.getThrower(),
                        entity,
                        true,
                        parametersx.getEnchanted("knockback", impulse),
                        this.posX,
                        this.posY,
                        this.posZ
                     );
                     entity.hurtResistantTime = 0;
                     hasattack = true;
                  }
               }
            }

            if (hasattack) {
               this.world
                  .playSound(
                     (EntityPlayer)null,
                     this.posX,
                     this.posY,
                     this.posZ,
                     Sounds.sea_effloresce_impact,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + this.rand.nextFloat() / 5.0F
                  );
            }
         }
      }
   }

   @Override
   public void onClient(double x, double y, double z, double a, double b, double c) {
      Entity en = this.world.getEntityByID((int)x);
      if (en instanceof EntityLivingBase) {
         this.thrower = (EntityLivingBase)en;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
   }

   public void onEntityUpdate() {
      super.onEntityUpdate();
      if (this.world.isRemote) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
         AxisAlignedBB aabb = this.getEntityBoundingBox().grow(damageRadius);

         for (Entity entitylivingbase : this.world.getEntitiesWithinAABBExcludingEntity(this, aabb)) {
            if (entitylivingbase != this.thrower
               && Weapons.canDealDamageTo(entitylivingbase)
               && !(entitylivingbase instanceof EntitySeaEffloresce)
               && !(entitylivingbase instanceof GUNParticle)
               && !(entitylivingbase instanceof BetweenParticle)) {
               Vec3d vec1 = new Vec3d(this.posX, this.posY + this.height / 2.0F, this.posZ);
               Vec3d vec2 = new Vec3d(
                  entitylivingbase.posX, entitylivingbase.posY + entitylivingbase.height / 2.0F, entitylivingbase.posZ
               );
               BetweenParticle laser = new BetweenParticle(
                  this.world, tex, 0.1F, -1, 0.6F, 0.8F, 1.0F, 0.0F, vec1.distanceTo(vec2), 1, 0.3F, 8.0F, vec1, vec2
               );
               laser.setPosition(vec1.x, vec1.y, vec1.z);
               laser.alphaGlowing = false;
               laser.ignoreFrustumCheck = true;
               this.world.spawnEntity(laser);
            }
         }
      }
   }

   protected void entityInit() {
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof EntityPlayer) {
         this.throwerName = this.thrower.getName();
      }

      compound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      this.thrower = null;
      this.throwerName = compound.getString("ownerName");
      if (this.throwerName != null && this.throwerName.isEmpty()) {
         this.throwerName = null;
      }

      this.thrower = this.getThrower();
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
