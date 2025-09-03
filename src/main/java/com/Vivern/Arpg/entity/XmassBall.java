//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class XmassBall extends EntityThrowable {
   public final ItemStack weaponstack;
   public float damage;
   public boolean alternative = false;
   static ResourceLocation pixel = new ResourceLocation("arpg:textures/pixel.png");
   static Vec3d[] colors = new Vec3d[]{new Vec3d(0.1F, 0.5, 1.0), new Vec3d(1.0, 0.8F, 0.1F), new Vec3d(1.0, 0.5, 1.0), new Vec3d(0.9F, 0.1F, 0.1F)};

   public XmassBall(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.XMASSLAUNCHER);
   }

   public XmassBall(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.XMASSLAUNCHER);
   }

   public XmassBall(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.XMASSLAUNCHER);
   }

   public XmassBall(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public XmassBall(World world, ItemStack itemstack) {
      super(world);
      this.weaponstack = itemstack;
   }

   public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
      float f = -MathHelper.sin(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * (float) (Math.PI / 180.0));
      float f2 = MathHelper.cos(rotationYawIn * (float) (Math.PI / 180.0)) * MathHelper.cos(rotationPitchIn * (float) (Math.PI / 180.0));
      this.shoot(f, f1, f2, velocity, inaccuracy);
      this.motionX = this.motionX + entityThrower.motionX * 0.5;
      this.motionZ = this.motionZ + entityThrower.motionZ * 0.5;
      if (!entityThrower.onGround) {
         this.motionY = this.motionY + entityThrower.motionY * 0.25;
      }
   }

   protected float getGravityVelocity() {
      return 0.04F;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 80) {
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         Vec3d col = ColorConverters.getRainbow(this.rand.nextFloat());

         for (int ss = 0; ss < 5; ss++) {
            int lt = 20 + this.rand.nextInt(10);
            GUNParticle part = new GUNParticle(
               pixel,
               0.0625F,
               0.01F,
               lt,
               200,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 22.0F,
               (float)this.rand.nextGaussian() / 22.0F + 0.08F,
               (float)this.rand.nextGaussian() / 22.0F,
               (float)col.x,
               (float)col.y,
               (float)col.z,
               false,
               this.rand.nextInt(360)
            );
            part.scaleTickAdding = -0.0625F / lt;
            this.world.spawnEntity(part);
         }

         int i = (int)(this.getEntityId() * 1.4827375F) % 4;
         Vec3d col2 = colors[i];

         for (int ss = 0; ss < this.rand.nextInt(3) + 1; ss++) {
            GUNParticle part = new GUNParticle(
               CrystalFanShoot.shards[this.rand.nextInt(3)],
               0.12F + this.rand.nextFloat() * 0.06F,
               0.06F,
               25 + this.rand.nextInt(10),
               -1,
               this.world,
               this.lastTickPosX,
               this.lastTickPosY,
               this.lastTickPosZ,
               (float)this.rand.nextGaussian() / 15.0F,
               (float)this.rand.nextGaussian() / 15.0F + 0.1F,
               (float)this.rand.nextGaussian() / 15.0F,
               (float)col2.x,
               (float)col2.y,
               (float)col2.z,
               false,
               this.rand.nextInt(360),
               true,
               1.2F
            );
            part.dropMode = true;
            part.rotationPitchYaw = new Vec2f(this.rand.nextInt(360), this.rand.nextInt(360));
            part.tracker = new ParticleTracker.TrackerGlassShard(
               (float)this.rand.nextGaussian() * 1.5F,
               (float)this.rand.nextGaussian() * 1.5F,
               (int)this.rand.nextGaussian() * 2,
               false
            );
            this.world.spawnEntity(part);
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
            this.expl(true);
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.expl(false);
      }
   }

   public void expl(boolean isEntity) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack);
      double damageRadius = this.alternative ? parameters.getEnchanted("alternative_ball_radius", range) : parameters.getEnchanted("ball_radius", range);
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               if ((!isEntity || !this.alternative || !(entity instanceof XmassBall)) && Team.checkIsOpponent(this.thrower, entity)) {
                  int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
                  Weapons.dealDamage(
                     new WeaponDamage(this.weaponstack, this.getThrower(), this, false, false, this, WeaponDamage.explode),
                     this.damage,
                     this.getThrower(),
                     entity,
                     true,
                     parameters.getEnchanted("ball_knockback", impulse),
                     this.posX,
                     this.posY,
                     this.posZ
                  );
                  entity.hurtResistantTime = 0;
               }
            }
         }

         this.world
            .playSound(
               (EntityPlayer)null,
               this.posX,
               this.posY,
               this.posZ,
               Sounds.arrow_shell,
               SoundCategory.AMBIENT,
               0.7F,
               1.05F + this.rand.nextFloat() / 5.0F
            );
         this.world.setEntityState(this, (byte)8);
         this.setDead();
      }
   }
}
