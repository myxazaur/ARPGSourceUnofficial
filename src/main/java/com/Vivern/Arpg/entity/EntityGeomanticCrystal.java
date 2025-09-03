//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.network.PacketDoSomethingToClients;
import com.Vivern.Arpg.network.PacketHandler;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGeomanticCrystal extends Entity {
   public ItemStack stackIn = ItemStack.EMPTY;
   public int randomRotat = 0;

   public EntityGeomanticCrystal(World world) {
      super(world);
      this.setSize(0.25F, 0.25F);
      this.randomRotat = this.rand.nextInt(360);
   }

   public EntityGeomanticCrystal(World world, EntityItem dropper) {
      super(world);
      this.setPosition(dropper.posX, dropper.posY, dropper.posZ);
      this.setSize(0.25F, 0.25F);
      this.motionX = dropper.motionX / 2.0;
      this.motionY = dropper.motionY / 2.0;
      this.motionZ = dropper.motionZ / 2.0;
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      this.velocityChanged = true;
      this.stackIn = dropper.getItem();
   }

   public void sendUpdateColor(World world) {
      if (!world.isRemote) {
         PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
         packet.writeargs(
            NBTHelper.GetNBTint(this.stackIn, "color"),
            NBTHelper.GetNBTint(this.stackIn, "colorover"),
            NBTHelper.GetNBTint(this.stackIn, "type"),
            NBTHelper.GetNBTint(this.stackIn, "size"),
            0.0,
            this.getEntityId(),
            2
         );
         PacketHandler.sendToAllAround(packet, world, this.posX, this.posY, this.posZ, 32.0);
      }
   }

   public void setClientStackColors(int color, int colorOverlay, int crystalType, int size) {
      this.stackIn = new ItemStack(ItemsRegister.GEOMANTICCRYSTAL);
      NBTHelper.GiveNBTint(this.stackIn, color, "color");
      NBTHelper.GiveNBTint(this.stackIn, colorOverlay, "colorover");
      NBTHelper.GiveNBTint(this.stackIn, crystalType, "type");
      NBTHelper.GiveNBTint(this.stackIn, size, "size");
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
      if (this.ticksExisted == 2 || this.ticksExisted % 80 == 0) {
         this.sendUpdateColor(this.world);
      }

      if (!this.hasNoGravity() && !this.isInWater()) {
         this.motionY -= 0.03F;
      }

      this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      float f = this.isInWater() ? 0.16F : 0.96F;
      if (this.onGround) {
         BlockPos underPos = new BlockPos(
            MathHelper.floor(this.posX),
            MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
            MathHelper.floor(this.posZ)
         );
         IBlockState underState = this.world.getBlockState(underPos);
         f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.96F;
      }

      this.motionX *= f;
      this.motionY *= 0.9600000190734863;
      this.motionZ *= f;
      if (this.onGround) {
         this.motionY *= -0.9F;
      }
   }

   public void onCollideWithPlayer(EntityPlayer player) {
      if (this.ticksExisted > 15) {
         if (!player.world.isRemote) {
            player.world.spawnEntity(new EntityItem(player.world, this.posX, this.posY, this.posZ, this.stackIn));
         }

         this.setDead();
      }
   }

   public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
      if (player.isSneaking()) {
         return EnumActionResult.FAIL;
      } else {
         if (!this.world.isRemote) {
            player.world.spawnEntity(new EntityItem(player.world, this.posX, this.posY, this.posZ, this.stackIn));
            this.setDead();
         }

         return EnumActionResult.SUCCESS;
      }
   }

   public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
      if (player.isSneaking()) {
         return false;
      } else {
         if (!this.world.isRemote) {
            player.world.spawnEntity(new EntityItem(player.world, this.posX, this.posY, this.posZ, this.stackIn));
            this.setDead();
         }

         return true;
      }
   }

   protected void readEntityFromNBT(NBTTagCompound compound) {
      NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");
      this.stackIn = new ItemStack(nbttagcompound);
      if (this.stackIn.isEmpty()) {
         this.setDead();
      }
   }

   protected void writeEntityToNBT(NBTTagCompound compound) {
      if (!this.stackIn.isEmpty()) {
         compound.setTag("Item", this.stackIn.writeToNBT(new NBTTagCompound()));
      }
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
