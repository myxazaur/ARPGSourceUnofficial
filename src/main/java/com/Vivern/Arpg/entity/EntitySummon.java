package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.mobs.EntitySummoned;
import com.Vivern.Arpg.mobs.SummonedBlaze;
import com.Vivern.Arpg.mobs.SummonedSnowman;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySummon extends EntityThrowable {
   public final ItemStack weaponstack;
   public int etype = 0;
   public int maxcount = 0;
   static ResourceLocation cloud = new ResourceLocation("arpg:textures/largecloud.png");
   static ResourceLocation blazeSummon = new ResourceLocation("arpg:textures/blaze_summon.png");

   public EntitySummon(World world) {
      super(world);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFBLAZES);
   }

   public EntitySummon(World world, EntityLivingBase thrower) {
      super(world, thrower);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFBLAZES);
   }

   public EntitySummon(World world, double x, double y, double z) {
      super(world, x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.WANDOFBLAZES);
   }

   public EntitySummon(World world, EntityLivingBase thrower, int entityType, int maxCount, ItemStack itemstack) {
      super(world, thrower);
      this.etype = entityType;
      this.maxcount = maxCount;
      this.weaponstack = itemstack;
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 3) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.summon,
               SoundCategory.AMBIENT,
               1.0F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );

         for (int ss = 0; ss < 6; ss++) {
            GUNParticle fire = new GUNParticle(
               cloud,
               0.35F + (float)this.rand.nextGaussian() / 5.0F,
               -0.015F,
               25 + this.rand.nextInt(10),
               240,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 23.0F,
               (float)this.rand.nextGaussian() / 23.0F + 0.05F,
               (float)this.rand.nextGaussian() / 23.0F,
               0.87F,
               1.0F,
               1.0F,
               true,
               this.rand.nextInt(100) - 50
            );
            fire.alphaTickAdding = -0.02F;
            fire.alphaGlowing = true;
            this.world.spawnEntity(fire);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void onEntityUpdate() {
      GUNParticle fire = new GUNParticle(
         cloud,
         0.23F + (float)this.rand.nextGaussian() / 8.0F,
         -0.015F,
         15 + this.rand.nextInt(10),
         240,
         this.world,
         this.posX,
         this.posY,
         this.posZ,
         (float)this.rand.nextGaussian() / 54.0F,
         (float)this.rand.nextGaussian() / 54.0F,
         (float)this.rand.nextGaussian() / 54.0F,
         0.87F,
         1.0F,
         1.0F,
         true,
         this.rand.nextInt(100) - 50
      );
      fire.alphaTickAdding = -0.03F;
      fire.alphaGlowing = true;
      this.world.spawnEntity(fire);
   }

   protected float getGravityVelocity() {
      return 0.07F;
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.world.isRemote) {
         if (this.getThrower() instanceof EntityPlayer && this.getThrower() != null) {
            if (this.etype == 1) {
               int lcount = 0;
               double damageRadius = 100.0;
               AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
                  .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
                  .offset(-damageRadius, -damageRadius, -damageRadius);
               List<EntitySummoned> list = this.world.getEntitiesWithinAABB(EntitySummoned.class, axisalignedbb);
               Set<EntitySummoned> uniqueList = new HashSet<>(list);
               if (!uniqueList.isEmpty()) {
                  for (EntitySummoned loaded : uniqueList) {
                     if (!loaded.isDead && (loaded.getOwner() == this.getThrower() || loaded.getOwnerId() == this.getThrower().getUniqueID())) {
                        lcount++;
                     }
                  }
               }

               if (lcount < this.maxcount) {
                  SummonedBlaze blaze = new SummonedBlaze(
                     this.world, this.posX, this.posY, this.posZ, (EntityPlayer)this.getThrower(), this.weaponstack
                  );
                  this.world.spawnEntity(blaze);
               }
            } else if (this.etype == 2) {
               int lcountx = 0;
               double damageRadiusx = 100.0;
               AxisAlignedBB axisalignedbbx = this.getEntityBoundingBox()
                  .expand(damageRadiusx * 2.0, damageRadiusx * 2.0, damageRadiusx * 2.0)
                  .offset(-damageRadiusx, -damageRadiusx, -damageRadiusx);
               List<EntitySummoned> listx = this.world.getEntitiesWithinAABB(EntitySummoned.class, axisalignedbbx);
               Set<EntitySummoned> uniqueListx = new HashSet<>(listx);
               if (!uniqueListx.isEmpty()) {
                  for (EntitySummoned loadedx : uniqueListx) {
                     if (!loadedx.isDead && (loadedx.getOwner() == this.getThrower() || loadedx.getOwnerId() == this.getThrower().getUniqueID())) {
                        lcountx++;
                     }
                  }
               }

               if (lcountx < this.maxcount) {
                  SummonedSnowman snowman = new SummonedSnowman(
                     this.world, this.posX, this.posY, this.posZ, (EntityPlayer)this.getThrower(), this.weaponstack
                  );
                  this.world.spawnEntity(snowman);
               }
            }
         }

         this.world.setEntityState(this, (byte)3);
         this.setDead();
      }
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
   }
}
