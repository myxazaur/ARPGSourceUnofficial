//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.renders.IMagicVision;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TileAbsorptionTotem extends TileEntity implements IManaBuffer, ITickable, IMagicVision {
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 10.0F, 3, -0.25F, 0.1F);

   public void update() {
      this.manaBuffer.updateManaBuffer(this.world, this.pos);
      if ((this.world.getWorldTime() + 2L) % ManaBuffer.TICKRATE == 0L
         && this.getManaBuffer().getManaStored() < this.getManaBuffer().getManaStorageSize()) {
         List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.getPos()).grow(3.0));
         if (!list.isEmpty()) {
            float manaPerTickrate = 0.1F * ManaBuffer.TICKRATE;

            for (EntityPlayer player : list) {
               float manaNeed = Math.min(manaPerTickrate, this.getManaBuffer().getManaStorageSize() - this.getManaBuffer().getManaStored());
               if (Mana.getMana(player) > manaNeed) {
                  if (!this.world.isRemote) {
                     Mana.changeMana(player, -manaNeed);
                     Mana.setManaSpeed(player, 0.001F);
                     this.getManaBuffer().addMana(manaNeed);
                  } else if (Minecraft.getMinecraft().getRenderViewEntity() != null) {
                     double distModifier = MathHelper.clamp(
                        1.0 - Math.sqrt(Minecraft.getMinecraft().getRenderViewEntity().getDistanceSq(this.pos)) / 64.0, 0.0, 1.0
                     );
                     float sizeMultiplier = 2.0F + 5.0F * (float)(1.0 - distModifier);
                     ManaBuffer.spawnFlowParticles(
                        this.world,
                        player.getEntityBoundingBox(),
                        this.pos,
                        manaNeed,
                        (int)(ManaBuffer.TICKRATE * distModifier / 2.0),
                        sizeMultiplier
                     );
                  }

                  if (this.getManaBuffer().getManaStored() >= this.getManaBuffer().getManaStorageSize()) {
                     break;
                  }
               }
            }
         }
      }
   }

   public void read(NBTTagCompound compound) {
      this.manaBuffer.readFromNBT(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      this.manaBuffer.writeToNBT(compound);
      return super.writeToNBT(compound);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   @Override
   public ManaBuffer getManaBuffer() {
      return this.manaBuffer;
   }

   @Override
   public float getElementEnergy(ShardType shardType) {
      return 0.0F;
   }

   @Override
   public float getMana() {
      return this.getManaBuffer().getMana();
   }

   @Override
   public float getManaStorageSize(World world, BlockPos pos) {
      return this.getManaBuffer().getManaStorageSize();
   }
}
