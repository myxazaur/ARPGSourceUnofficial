//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalFanBonus extends Entity {
   public CrystalFanBonus(World world) {
      super(world);
      this.setSize(0.9F, 0.9F);
   }

   public CrystalFanBonus(World world, Entity dropper) {
      super(world);
      this.setPosition(dropper.posX, dropper.posY + dropper.height / 2.0F, dropper.posZ);
      this.setSize(0.9F, 0.9F);
      this.motionX = this.rand.nextGaussian() / 13.0 + dropper.motionX / 2.0;
      this.motionY = this.rand.nextGaussian() / 17.0 + 0.2 + dropper.motionY / 2.0;
      this.motionZ = this.rand.nextGaussian() / 13.0 + dropper.motionZ / 2.0;
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      this.velocityChanged = true;
   }

   public double getYOffset() {
      return 0.45;
   }

   protected void entityInit() {
   }

   @Nullable
   public AxisAlignedBB getCollisionBox(Entity entityIn) {
      return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox() {
      return this.getEntityBoundingBox();
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted == 2) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.magic_k,
               SoundCategory.HOSTILE,
               0.9F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }

      if (!this.hasNoGravity()) {
         this.motionY -= 0.03F;
      }

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
         f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
      }

      this.motionX *= f;
      this.motionY *= 0.98F;
      this.motionZ *= f;
      if (this.onGround) {
         this.motionY *= -0.9F;
      }

      if (this.ticksExisted > 300) {
         this.setDead();
      }
   }

   public void onCollideWithPlayer(EntityPlayer player) {
      if (this.ticksExisted > 15 && !player.world.isRemote) {
         ItemStack stack = player.getHeldItemMainhand();
         if (stack.getItem() == ItemsRegister.CRYSTALFAN) {
            NBTHelper.AddNBTint(stack, 1 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack), "charge");
            this.world.setEntityState(this, (byte)0);
            this.setDead();
         }
      }
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 0) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.item_misc_d,
               SoundCategory.PLAYERS,
               0.9F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }
   }
}
