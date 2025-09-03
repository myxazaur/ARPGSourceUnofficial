//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.entity.BetweenParticle;
import com.Vivern.Arpg.main.ColorConverters;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SummonedBlaze extends EntitySummoned {
   public int firedelay;
   public static ResourceLocation texturelaser = new ResourceLocation("arpg:textures/fire_beam.png");
   public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largesmoke.png");
   public static ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle.png");
   public static ResourceLocation flame = new ResourceLocation("arpg:textures/flame_big.png");
   public static ResourceLocation displayicon = new ResourceLocation("arpg:textures/blaze_display_icon.png");

   public SummonedBlaze(World world) {
      super(world);
      this.setSize(0.5F, 1.4F);
      this.isImmuneToFire = true;
   }

   public SummonedBlaze(World world, double x, double y, double z) {
      super(world);
      this.setSize(0.5F, 1.4F);
      this.setPositionAndUpdate(x, y, z);
      this.isImmuneToFire = true;
   }

   public SummonedBlaze(World world, double x, double y, double z, EntityPlayer owner, ItemStack itemstack) {
      super(world);
      this.setSize(0.5F, 1.4F);
      this.setPositionAndUpdate(x, y, z);
      this.setOwner(owner);
      this.allowedFollow = true;
      this.followPlayerMaxRange = 15.0;
      this.followPlayerMinRange = 3.0;
      this.team = Team.getTeamFor(owner);
      this.followPoint = owner.getPositionVector();
      this.isImmuneToFire = true;
   }

   @Override
   public ResourceLocation getDisplayIcon() {
      return displayicon;
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return Sounds.summoned_blaze_hurt;
   }

   @Override
   public void expelling() {
      for (int ss = 0; ss < 10; ss++) {
         GUNParticle bigsmoke = new GUNParticle(
            largesmoke,
            0.4F + (float)this.rand.nextGaussian() / 20.0F,
            0.0F,
            12,
            80,
            this.world,
            this.posX,
            this.posY,
            this.posZ,
            (float)this.rand.nextGaussian() / 23.0F,
            (float)this.rand.nextGaussian() / 27.0F,
            (float)this.rand.nextGaussian() / 23.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            this.rand.nextInt(360)
         );
         this.world.spawnEntity(bigsmoke);
      }

      this.setDead();
   }

   @SideOnly(Side.CLIENT)
   public void spawnBetwParticle(Vec3d from, Vec3d to) {
      if (from.lengthSquared() > 1.0E-6 && to.lengthSquared() > 1.0E-6) {
         BetweenParticle laser = new BetweenParticle(
            this.world, texturelaser, 0.35F, 240, 1.0F, 1.0F, 1.0F, 0.15F, from.distanceTo(to), 5, 0.3F, 4.0F, from, to
         );
         laser.ignoreFrustumCheck = true;
         laser.setPosition(from.x, from.y, from.z);
         laser.alphaGlowing = true;
         this.world.spawnEntity(laser);

         for (int ss = 0; ss < 5; ss++) {
            GUNParticle bigsmoke = new GUNParticle(
               largesmoke,
               0.4F + (float)this.rand.nextGaussian() / 20.0F,
               0.0F,
               12,
               80,
               this.world,
               to.x,
               to.y,
               to.z,
               (float)this.rand.nextGaussian() / 23.0F,
               (float)this.rand.nextGaussian() / 27.0F,
               (float)this.rand.nextGaussian() / 23.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(360)
            );
            this.world.spawnEntity(bigsmoke);
         }

         for (int ss = 0; ss < 8; ss++) {
            GUNParticle fire = new GUNParticle(
               sparkle,
               0.08F + (float)this.rand.nextGaussian() / 30.0F,
               0.01F,
               10,
               240,
               this.world,
               to.x,
               to.y,
               to.z,
               (float)this.rand.nextGaussian() / 14.0F,
               (float)this.rand.nextGaussian() / 14.0F + 0.15F,
               (float)this.rand.nextGaussian() / 14.0F,
               1.0F,
               0.8F + (float)this.rand.nextGaussian() / 5.0F,
               1.0F,
               true,
               0
            );
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 2400) {
         this.expelling();
      }

      if (this.ticksExisted % 5 == 0) {
         this.heal(0.2F);
         double max = Double.MAX_VALUE;
         EntityLivingBase targ = null;
         double damageRadius = 15.0;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (GetMOP.findEntityOnPath(
                        15.0,
                        this.getPositionEyes(1.0F),
                        entitylivingbase.getPositionVector().add(0.0, entitylivingbase.height / 2.0F, 0.0),
                        1.0F,
                        this,
                        true,
                        0.1,
                        0.01
                     )
                     == entitylivingbase
                  && entitylivingbase.isCreatureType(EnumCreatureType.MONSTER, false)
                  && entitylivingbase != this.getOwner()
                  && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)
                  && entitylivingbase.getHealth() > 0.0F) {
                  double dist = entitylivingbase.getDistance(this);
                  if (dist < max) {
                     max = dist;
                     targ = entitylivingbase;
                  }
               }
            }
         }

         this.setAttackTarget(targ);
      }

      EntityLivingBase attackTarg = this.getAttackTarget();
      if (attackTarg != null) {
         if (this.ticksExisted % 25 == 0) {
            this.followPoint = GetMOP.findRandPosNearEntity(attackTarg, 3.0F);
         }

         if (this.getDistance(attackTarg) > 6.0F) {
            this.motionX /= 2.0;
            this.motionY /= 2.0;
            this.motionZ /= 2.0;
            SuperKnockback.applyMove(this, -0.3F, attackTarg.posX, attackTarg.posY, attackTarg.posZ);
         } else {
            this.motionX /= 2.0;
            this.motionY /= 2.0;
            this.motionZ /= 2.0;
            SuperKnockback.applyMove(this, 0.4F, attackTarg.posX, attackTarg.posY, attackTarg.posZ);
         }

         this.getLookHelper().setLookPositionWithEntity(attackTarg, 40.0F, 40.0F);
         this.getLookHelper().onUpdateLook();
         this.firedelay--;
         if (this.firedelay < 1) {
            this.firedelay = 15;
            Vec3d vec = GetMOP.PosRayTrace(15.5, 0.0F, this, true, 0.01, 0.01);
            Vec3d pos1 = this.getPositionEyes(0.0F);
            double range = 0.5;
            AxisAlignedBB aabb = new AxisAlignedBB(
               vec.x - range,
               vec.y - range,
               vec.z - range,
               vec.x + range,
               vec.y + range,
               vec.z + range
            );
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
            this.spawnBetwParticle(pos1, vec);
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.fire,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.95F + this.rand.nextFloat() / 10.0F,
                  false
               );
            if (!list.isEmpty() && !this.world.isRemote) {
               for (EntityLivingBase entitylivingbasex : list) {
                  if (entitylivingbasex != this && entitylivingbasex != this.owner) {
                     SuperKnockback.applyKnockback(entitylivingbasex, 0.8F, this.posX, this.posY, this.posZ);
                     IAttributeInstance entityAttribute = entitylivingbasex.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                     double baseValue = entityAttribute.getBaseValue();
                     entityAttribute.setBaseValue(1.0);
                     entitylivingbasex.attackEntityFrom(DamageSource.causeMobDamage(this), 4.0F);
                     entitylivingbasex.hurtResistantTime = 0;
                     entityAttribute.setBaseValue(baseValue);
                     entitylivingbasex.setFire(4);
                  }
               }
            }
         }
      } else if (this.owner != null && this.ticksExisted % 27 == 0) {
         this.followPoint = GetMOP.findRandPosNearEntity(this.owner, 3.0F);
      }

      if (this.followPoint != null) {
         this.motionX /= 2.0;
         this.motionY /= 2.0;
         this.motionZ /= 2.0;
         if (this.owner != null) {
            SuperKnockback.applyMove(
               this,
               ColorConverters.InBorder(-0.4F, -0.1F, -this.getDistance(this.owner) / 5.0F),
               this.followPoint.x,
               this.followPoint.y,
               this.followPoint.z
            );
         }
      }

      if (this.owner != null && this.owner.isDead) {
         this.setDead();
      }

      GUNParticle fire2 = new GUNParticle(
         flame,
         0.2F + (float)this.rand.nextGaussian() / 15.0F,
         -0.009F,
         10 + this.rand.nextInt(5),
         240,
         this.world,
         this.posX,
         this.posY + 1.0,
         this.posZ,
         0.0F,
         -0.15F,
         0.0F,
         1.0F,
         0.8F + (float)this.rand.nextGaussian() / 5.0F,
         1.0F,
         true,
         this.rand.nextInt(100) - 50
      );
      fire2.alphaTickAdding = -0.1F;
      fire2.alphaGlowing = true;
      this.world.spawnEntity(fire2);
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(7.0);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
      this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0);
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0);
      this.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).setBaseValue(1.0);
   }

   protected void initEntityAI() {
      this.tasks.addTask(0, new EntityAIFlyToSummoner(this, 1.0, 15.0F));
   }
}
