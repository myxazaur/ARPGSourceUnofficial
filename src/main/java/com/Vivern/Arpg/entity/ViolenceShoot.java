//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ViolenceShoot extends EntityThrowable {
   public final ItemStack weaponstack;
   public float magicPower = 1.0F;
   public Vec3d direction = Vec3d.ZERO;
   public Random directionRand;
   static ResourceLocation explode = new ResourceLocation("arpg:textures/violence_explode.png");
   static ResourceLocation trace = new ResourceLocation("arpg:textures/shard_trace.png");
   static ResourceLocation star2 = new ResourceLocation("arpg:textures/star2.png");

   public ViolenceShoot(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.VIOLENCE);
      this.directionRand = new Random(this.getEntityId());
   }

   public ViolenceShoot(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.VIOLENCE);
      this.directionRand = new Random(this.getEntityId());
   }

   public ViolenceShoot(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.VIOLENCE);
      this.directionRand = new Random(this.getEntityId());
   }

   public ViolenceShoot(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
      this.directionRand = new Random(this.getEntityId());
   }

   public void generateDirection() {
      this.direction = new Vec3d(
         this.directionRand.nextGaussian() / 7.0, -0.04F - this.directionRand.nextFloat() * 0.02F, this.directionRand.nextGaussian() / 7.0
      );
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
   }

   protected float getGravityVelocity() {
      return 0.03F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 100) {
         this.setDead();
      }

      if (this.ticksExisted % 6 == 0) {
         this.generateDirection();
         this.motionX = this.motionX + this.direction.x;
         this.motionY = this.motionY + this.direction.y;
         this.motionZ = this.motionZ + this.direction.z;
         this.velocityChanged = true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.violence_impact,
               SoundCategory.AMBIENT,
               1.2F,
               0.875F + this.rand.nextFloat() / 4.0F,
               false
            );
         AnimatedGParticle anim = new AnimatedGParticle(
            explode,
            0.9F + this.rand.nextFloat() * 0.3F,
            -0.01F,
            18,
            230,
            this.world,
            this.prevPosX,
            this.prevPosY,
            this.prevPosZ,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            0
         );
         anim.framecount = 18;
         anim.alphaGlowing = true;
         anim.randomDeath = false;
         anim.animDelay = 1;
         this.world.spawnEntity(anim);

         for (int i = 0; i < 5; i++) {
            float scl = 0.13F + this.rand.nextFloat() * 0.1F;
            int lt = 23 + this.rand.nextInt(10);
            GUNParticle part = new GUNParticle(
               star2,
               scl,
               0.04F,
               lt,
               230,
               this.world,
               this.prevPosX,
               this.prevPosY,
               this.prevPosZ,
               (float)this.rand.nextGaussian() / 7.0F,
               (float)this.rand.nextGaussian() / 13.0F + 0.1F,
               (float)this.rand.nextGaussian() / 7.0F,
               0.8F + this.rand.nextFloat() * 0.2F,
               0.2F,
               0.8F + this.rand.nextFloat() * 0.2F,
               true,
               this.rand.nextInt(360)
            );
            part.alphaGlowing = true;
            part.scaleTickAdding = -scl / lt;
            this.world.spawnEntity(part);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         if (result.entityHit != null) {
            if (Team.checkIsOpponent(this.thrower, result.entityHit) && Weapons.canDealDamageTo(result.entityHit)) {
               this.expl();
            }
         } else if (this.world
               .getBlockState(result.getBlockPos())
               .getBlock()
               .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
            != null) {
            this.expl();
            this.tryPlaceBurn(result);
         }
      }
   }

   public void expl() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (Team.checkIsOpponent(this.thrower, entity)) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, this, WeaponDamage.soul),
                  parameters.getEnchanted("damage", might) * this.magicPower,
                  this.getThrower(),
                  entity,
                  true,
                  parameters.getEnchanted("knockback", impulse)
               );
               entity.hurtResistantTime = 0;
               Weapons.setPotionIfEntityLB(
                  entity, PotionEffects.DEMONIC_BURN, parameters.getEnchantedi("potion_time", witchery), parameters.geti("potion_power")
               );
            }
         }
      }

      this.world.setEntityState(this, (byte)8);
      this.setDead();
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      super.onEntityUpdate();
      Vec3d pos1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      Vec3d pos2 = this.getPositionVector();
      if (this.world.isRemote && pos1.lengthSquared() > 1.0E-6 && pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, trace, 0.16F, 230, 1.0F, 0.2F, 0.8F, 0.08F, pos1.distanceTo(pos2), 11, 0.0F, 0.0F, pos1, pos2
         );
         laser.alphaGlowing = true;
         laser.texstart = 0.1F;
         laser.offset = 0.1F;
         laser.setPosition(pos1.x, pos1.y, pos1.z);
         this.world.spawnEntity(laser);
      }
   }

   public void tryPlaceBurn(RayTraceResult result) {
      if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack) > 0) {
         BlockPos pos = result.sideHit == EnumFacing.UP ? this.getPosition() : this.getPosition().down();
         IBlockState state = this.world.getBlockState(pos);
         if (BlocksRegister.DEMONICFIRE.canPlaceBlockAt(this.world, pos) && Weapons.easyBreakBlockFor(this.world, 0.2F, pos)) {
            this.world.destroyBlock(pos, true);
            this.world.setBlockState(pos, BlocksRegister.DEMONICFIRE.getDefaultState());
         }
      }
   }
}
