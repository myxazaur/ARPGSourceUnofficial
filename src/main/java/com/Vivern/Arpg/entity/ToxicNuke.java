//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxicNuke extends EntityThrowable {
   public final ItemStack weaponstack;
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
   ResourceLocation largecloud = new ResourceLocation("arpg:textures/acid_splash2.png");

   public ToxicNuke(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.TOXICNUKECANNON);
   }

   public ToxicNuke(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.TOXICNUKECANNON);
   }

   public ToxicNuke(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.TOXICNUKECANNON);
   }

   public ToxicNuke(World world, EntityLivingBase thrower, ItemStack itemstack) {
      super(world, thrower);
      this.weaponstack = itemstack;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 60) {
         this.expl();
      }

      this.world.setEntityState(this, (byte)8);
   }

   protected float getGravityVelocity() {
      return 0.02F;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
            float distapl = Minecraft.getMinecraft().getRenderViewEntity().getDistance(this);
            if (distapl < 30.0F) {
               Booom.lastTick = Math.round(35.0F - distapl);
               Booom.frequency = 2.7125F;
               Booom.x = (float)this.rand.nextGaussian();
               Booom.y = (float)this.rand.nextGaussian();
               Booom.z = (float)this.rand.nextGaussian();
               Booom.power = 0.6F - distapl / 50.0F;
            }
         }

         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.explode2,
               SoundCategory.AMBIENT,
               4.0F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.toxic_nuke,
               SoundCategory.AMBIENT,
               2.0F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );

         for (int ss = 0; ss < 10; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.largesmoke,
               1.0F + (float)this.rand.nextGaussian() / 2.0F,
               -0.001F,
               34,
               60,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               0
            );
            bigsmoke.alphaTickAdding = -0.028F;
            this.world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 20; ss++) {
            GUNParticle fire = new GUNParticle(
               this.flame,
               1.0F + (float)this.rand.nextGaussian() / 5.0F,
               -0.005F,
               33 + this.rand.nextInt(20),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 4.0F,
               (float)this.rand.nextGaussian() / 27.0F,
               (float)this.rand.nextGaussian() / 4.0F,
               0.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.04F;
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }

         for (int ss = 0; ss < 20; ss++) {
            GUNParticle fire = new GUNParticle(
               this.flame,
               1.5F + (float)this.rand.nextGaussian() / 5.0F,
               0.02F,
               53 + this.rand.nextInt(20),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 27.0F,
               this.rand.nextFloat(),
               (float)this.rand.nextGaussian() / 27.0F,
               0.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.04F;
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }

         for (int ss = 0; ss < 30; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               this.largecloud,
               0.5F + (float)this.rand.nextGaussian() / 5.0F,
               0.005F,
               140 + this.rand.nextInt(20),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 10.0F,
               (float)this.rand.nextGaussian() / 19.0F,
               (float)this.rand.nextGaussian() / 10.0F,
               0.3F,
               1.0F,
               0.7F + (float)this.rand.nextGaussian() / 5.0F,
               true,
               this.rand.nextInt(360),
               true,
               1.6F
            );
            bigsmoke.alphaTickAdding = -0.006F;
            bigsmoke.acidRender = true;
            bigsmoke.dropMode = true;
            this.world.spawnEntity(bigsmoke);
         }
      }

      if (id == 8) {
         this.world
            .spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int[0]);
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (result.entityHit != null) {
         if (Team.checkIsOpponent(this.thrower, result.entityHit)) {
            this.expl();
         }
      } else if (this.world
            .getBlockState(result.getBlockPos())
            .getBlock()
            .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
         != null) {
         this.expl();
      }
   }

   public void expl() {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.weaponstack.getItem());
      double damageRadius = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, this.weaponstack));
      AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
         .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
         .offset(-damageRadius, -damageRadius, -damageRadius);
      if (!this.world.isRemote) {
         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);
         if (!list.isEmpty()) {
            for (Entity entity : list) {
               int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
               int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
               int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, this.weaponstack);
               float wdamage = parameters.getEnchanted("damage", might);
               float wknockback = parameters.getEnchanted("knockback", impulse);
               if (!Team.checkIsOpponent(this.thrower, entity)) {
                  float friendlyfire = parameters.get("friendlyfire");
                  wdamage *= friendlyfire;
                  wknockback *= friendlyfire;
               }

               Weapons.dealDamage(
                  new WeaponDamage(this.weaponstack, this.getThrower(), this, true, false, this, WeaponDamage.explode),
                  wdamage,
                  this.thrower,
                  entity,
                  true,
                  wknockback,
                  this.posX,
                  this.posY - 0.3,
                  this.posZ
               );
               entity.hurtResistantTime = 0;
            }
         }
      }

      if (!this.world.isRemote) {
         Explosion explosion = new Explosion(
            this.world, null, this.posX, this.posY, this.posZ, parameters.get("explosion_power"), false, true
         );
         explosion.doExplosionA();
         explosion.doExplosionB(false);
         int count = parameters.getEnchantedi("shards_count", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, this.weaponstack));

         for (int ss = 0; ss < count; ss++) {
            ToxicNukeShard projectile = new ToxicNukeShard(
               this.world,
               this.getThrower(),
               this.rand.nextGaussian() / 5.0,
               this.rand.nextGaussian() / 5.0,
               this.rand.nextGaussian() / 5.0,
               this.weaponstack
            );
            projectile.setPosition(this.posX, this.posY, this.posZ);
            this.world.spawnEntity(projectile);
         }

         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }
}
