package com.Vivern.Arpg.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBossLoot extends EntityItem {
   public boolean hasHomePosition = false;
   public double homeX;
   public double homeZ;

   public EntityBossLoot(World worldIn) {
      super(worldIn);
      this.setNoDespawn();
      this.setGlowing(true);
   }

   public EntityBossLoot(World worldIn, double x, double y, double z) {
      super(worldIn, x, y, z);
      this.setNoDespawn();
      this.setGlowing(true);
   }

   public EntityBossLoot(World worldIn, double x, double y, double z, ItemStack stack) {
      super(worldIn, x, y, z, stack);
      this.setNoDespawn();
      this.setGlowing(true);
   }

   public void writeEntityToNBT(NBTTagCompound compound) {
      super.writeEntityToNBT(compound);
      compound.setDouble("homeX", this.homeX);
      compound.setDouble("homeZ", this.homeZ);
      compound.setBoolean("hasHomePosition", this.hasHomePosition);
   }

   public void readEntityFromNBT(NBTTagCompound compound) {
      super.readEntityFromNBT(compound);
      if (compound.hasKey("homeX")) {
         this.homeX = compound.getDouble("homeX");
      }

      if (compound.hasKey("homeZ")) {
         this.homeZ = compound.getDouble("homeZ");
      }

      if (compound.hasKey("hasHomePosition")) {
         this.hasHomePosition = compound.getBoolean("hasHomePosition");
      }
   }

   @SideOnly(Side.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      return true;
   }

   public AxisAlignedBB getRenderBoundingBox() {
      return this.getEntityBoundingBox().grow(64.0);
   }

   public void setHomePosition() {
      this.hasHomePosition = true;
      this.homeX = this.posX;
      this.homeZ = this.posZ;
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      return false;
   }

   public void onUpdate() {
      this.motionX = MathHelper.clamp(this.motionX, -1.0, 1.0);
      this.motionY = MathHelper.clamp(this.motionY, -1.0, 1.0);
      this.motionZ = MathHelper.clamp(this.motionZ, -1.0, 1.0);
      super.onUpdate();
      this.motionX *= 0.95;
      this.motionY *= 0.95;
      this.motionZ *= 0.95;
      if (this.posY > 256.0) {
         this.setPosition(this.posX, 256.0, this.posZ);
         this.motionX = 0.0;
         this.motionY = 0.0;
         this.motionZ = 0.0;
      }

      if (this.posY < 0.0) {
         this.setPosition(this.posX, 0.0, this.posZ);
         this.setNoGravity(true);
         this.motionX = 0.0;
         this.motionY = 0.0;
         this.motionZ = 0.0;
      }

      if (!this.world.isRemote && this.hasHomePosition && this.getDistanceSq(this.homeX, this.posY, this.homeZ) > 64.0) {
         Vec3d home = new Vec3d(this.homeX, this.posY, this.homeZ);
         Vec3d current = this.getPositionVector();
         Vec3d diffirence = current.subtract(home);
         Vec3d scaled = diffirence.normalize().scale(8.0);
         Vec3d posTo = home.add(scaled);
         this.setPosition(posTo.x, posTo.y, posTo.z);
         this.motionX = 0.0;
         this.motionY = 0.0;
         this.motionZ = 0.0;
         this.velocityChanged = true;
      }
   }

   public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
      return null;
   }
}
