package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityIchor extends StandardBullet {
   public final ItemStack weaponstack;
   public Vec3d pos1 = this.getPositionVector();
   public Vec3d pos2 = this.getPositionVector();
   public Vec3d pos3 = this.getPositionVector();
   public Vec3d pos4 = this.getPositionVector();
   public Vec3d pos5 = this.getPositionVector();
   ResourceLocation texture = new ResourceLocation("arpg:textures/ichorstream.png");
   static ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle.png");
   public float magicPower = 1.0F;

   public EntityIchor(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.ICHSHOWER);
   }

   public EntityIchor(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.ICHSHOWER);
   }

   public EntityIchor(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.ICHSHOWER);
   }

   public EntityIchor(World world, EntityLivingBase thrower, ItemStack itemstack, float power) {
      super(world, thrower);
      this.weaponstack = itemstack;
      this.magicPower = power;
   }

   @Override
   protected void entityInit() {
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(this.posX, this.posY, this.posZ, Sounds.ichorhit, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
      }
   }

   @Override
   public float getGravityVelocity() {
      return 0.3F;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.pos2.equals(this.pos1)) {
         this.pos2 = this.pos1;
      } else {
         this.pos2 = new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ);
      }

      this.pos1 = this.getPositionVector();
   }

   @Override
   public void onImpact(RayTraceResult result) {
      if (!this.world.isRemote && result.entityHit != null && Team.checkIsOpponent(this.thrower, result.entityHit)) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
         int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
         Weapons.dealDamage(
            new WeaponDamage(this.weaponstack, this.getThrower(), this, false, true, this, WeaponDamage.acid),
            parameters.getEnchanted("damage", might) * this.magicPower,
            this.getThrower(),
            result.entityHit,
            true,
            parameters.getEnchanted("knockback", impulse)
         );
         result.entityHit.hurtResistantTime = 0;
         if (result.entityHit instanceof EntityLivingBase) {
            PotionEffect baff = parameters.getPotion(
               "ichor", PotionEffects.ICHOR_CURSE, EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack)
            );
            ((EntityLivingBase)result.entityHit).addPotionEffect(baff);
            DeathEffects.applyDeathEffect(result.entityHit, DeathEffects.DE_COLOREDACID, 0.4F);
         }
      }

      if (result.typeOfHit == Type.BLOCK && !this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      float gaussianX = (float)(this.rand.nextGaussian() / 40.0 + this.motionX / 10.0);
      float gaussianY = (float)(this.rand.nextGaussian() / 40.0 + this.motionY / 10.0);
      float gaussianZ = (float)(this.rand.nextGaussian() / 40.0 + this.motionZ / 10.0);
      GUNParticle part = new GUNParticle(
         sparkle,
         0.025F,
         0.001F,
         25,
         240,
         this.world,
         this.posX + this.rand.nextGaussian() / 20.0,
         this.posY + this.rand.nextGaussian() / 20.0,
         this.posZ + this.rand.nextGaussian() / 20.0,
         gaussianX,
         gaussianY,
         gaussianZ,
         1.0F,
         1.0F,
         1.0F,
         true,
         this.rand.nextInt(360)
      );
      part.scaleTickAdding = -0.001F;
      part.alphaGlowing = true;
      this.world.spawnEntity(part);
      if (!this.world.isRemote) {
      }

      if (this.world.isRemote && this.pos1.lengthSquared() > 1.0E-6 && this.pos2.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, this.texture, 0.25F, 240, 1.0F, 1.0F, 1.0F, 0.0F, this.pos1.distanceTo(this.pos2), 5, -0.15F, 9999.0F, this.pos1, this.pos2
         );
         laser.alphaGlowing = true;
         laser.texstart = 0.1F;
         laser.setPosition(this.posX, this.posY, this.posZ);
         this.world.spawnEntity(laser);
      }
   }
}
