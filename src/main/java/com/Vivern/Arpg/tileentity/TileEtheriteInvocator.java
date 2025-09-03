//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.mobs.BossOphanim;
import com.Vivern.Arpg.network.PacketHandler;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEtheriteInvocator extends TileEntity implements ITickable {
   public boolean hasCell = false;
   public int time = 0;
   public boolean invoke = false;

   public boolean setCell() {
      if (this.hasCell) {
         return false;
      } else {
         this.hasCell = true;
         if (!this.world.isRemote) {
            PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
            if (this.world.provider.getDimension() == 104) {
               for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                  ArrayList<BlockPos> list = this.getAltarAt(this.pos.offset(facing, 2));
                  if (list != null) {
                     int celllsActive = 0;

                     for (BlockPos inv : list) {
                        TileEntity te = this.world.getTileEntity(inv);
                        if (te != null && te instanceof TileEtheriteInvocator) {
                           TileEtheriteInvocator etheriteInvocator = (TileEtheriteInvocator)te;
                           if (etheriteInvocator.hasCell) {
                              celllsActive++;
                           }
                        }
                     }

                     if (celllsActive >= 4) {
                        this.invoke = true;
                        this.time = 0;
                        this.world.playSound((EntityPlayer)null, this.getPos(), Sounds.ophanim_summon, SoundCategory.HOSTILE, 5.0F, 1.0F);
                        return true;
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   @Nullable
   public ArrayList<BlockPos> getAltarAt(BlockPos pos) {
      ArrayList<BlockPos> list = new ArrayList<>();
      if (this.world.getBlockState(pos.add(2, 0, 0)).getBlock() == BlocksRegister.ETHERITEINVOCATOR) {
         list.add(pos.add(2, 0, 0));
      }

      if (this.world.getBlockState(pos.add(-2, 0, 0)).getBlock() == BlocksRegister.ETHERITEINVOCATOR) {
         list.add(pos.add(-2, 0, 0));
      }

      if (this.world.getBlockState(pos.add(0, 0, 2)).getBlock() == BlocksRegister.ETHERITEINVOCATOR) {
         list.add(pos.add(0, 0, 2));
      }

      if (this.world.getBlockState(pos.add(0, 0, -2)).getBlock() == BlocksRegister.ETHERITEINVOCATOR) {
         list.add(pos.add(0, 0, -2));
      }

      return list.size() < 4 ? null : list;
   }

   public void update() {
      if (!this.world.isRemote && this.invoke) {
         this.time++;
         if (this.time > 200) {
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
               ArrayList<BlockPos> list = this.getAltarAt(this.pos.offset(facing, 2));
               if (list != null) {
                  int celllsActive = 0;

                  for (BlockPos inv : list) {
                     TileEntity te = this.world.getTileEntity(inv);
                     if (te != null && te instanceof TileEtheriteInvocator) {
                        TileEtheriteInvocator etheriteInvocator = (TileEtheriteInvocator)te;
                        if (etheriteInvocator.hasCell) {
                           celllsActive++;
                        }
                     }
                  }

                  if (celllsActive >= 4) {
                     this.invoke = true;
                     BlockPos invokePos = this.pos.offset(facing, 2).up(8);
                     this.time = 0;

                     for (BlockPos invx : list) {
                        TileEntity te = this.world.getTileEntity(invx);
                        if (te != null && te instanceof TileEtheriteInvocator) {
                           TileEtheriteInvocator etheriteInvocator = (TileEtheriteInvocator)te;
                           etheriteInvocator.hasCell = false;
                           etheriteInvocator.invoke = false;
                           etheriteInvocator.time = 0;
                           PacketHandler.trySendPacketUpdate(this.world, invx, etheriteInvocator, 64.0);
                        }
                     }

                     for (int i = 0; i < 2; i++) {
                        BlockPos pos3 = i == 0 ? invokePos : GetMOP.getTrueHeight(this.world, invokePos).up(3);
                        AxisAlignedBB aabb = new AxisAlignedBB(pos3.add(-1, -1, -1), pos3.add(1, 1, 1));
                        if (!this.world.collidesWithAnyBlock(aabb)) {
                           BossOphanim boss = new BossOphanim(this.world);
                           boss.setPosition(pos3.getX() + 0.5, pos3.getY(), pos3.getZ() + 0.5);
                           this.world.spawnEntity(boss);
                           boss.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(boss)), (IEntityLivingData)null);
                           boss.canDropLoot = true;
                           this.hasCell = false;
                           this.invoke = false;
                           this.time = 0;
                           return;
                        }
                     }
                  }
               }
            }

            this.hasCell = false;
            this.invoke = false;
            this.time = 0;
         }
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("hasCell")) {
         this.hasCell = compound.getBoolean("hasCell");
      }

      if (compound.hasKey("invoke")) {
         this.invoke = compound.getBoolean("invoke");
      }

      if (compound.hasKey("time")) {
         this.time = compound.getInteger("time");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      compound.setBoolean("hasCell", this.hasCell);
      compound.setBoolean("invoke", this.invoke);
      compound.setInteger("time", this.time);
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
}
