package com.vivern.arpg.tileentity;

import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.OreDicHelper;
import com.vivern.arpg.main.SieveRecipesRegister;
import com.vivern.arpg.recipes.Ingridient;
import com.vivern.arpg.recipes.SieveRecipe;
import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;

public class TileSieve extends TileEntity implements ITickable, ITileEntitySynchronize {
   public int prevSievePower;
   public int sievePower;
   public double prevTimeExisted;
   public double timeExisted;
   public boolean rotated;
   public ItemStack input;
   public float ticksSieved;
   public SieveRecipe currentRecipe;
   public int clientIdItem;
   public boolean clientShouldSpawnParticle;

   public void addSievePower(int amount, int maximum) {
      this.sievePower += amount;
      if (this.sievePower > maximum) {
         this.sievePower = maximum;
      }

      ITileEntitySynchronize.sendSynchronize(this, 32.0, this.sievePower);
   }

   public void update() {
      this.prevSievePower = this.sievePower;
      this.prevTimeExisted = this.timeExisted;
      int limitedPower = Math.min(this.sievePower, 100);
      this.timeExisted += limitedPower / 60.0;
      if (this.sievePower > 0) {
         this.sievePower--;
         if (!this.world.isRemote) {
            if (this.input == null) {
               AxisAlignedBB aabb = new AxisAlignedBB(
                  this.pos.getX(),
                  this.pos.getY() + 0.5,
                  this.pos.getZ(),
                  this.pos.getX() + 1,
                  this.pos.getY() + 1.1F,
                  this.pos.getZ() + 1
               );
               List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, aabb);
               if (!list.isEmpty()) {
                  for (EntityItem entityItem : list) {
                     SieveRecipe recipe = SieveRecipesRegister.getRecipe(entityItem.getItem());
                     if (recipe != null) {
                        ItemStack splitted = entityItem.getItem().splitStack(1);
                        this.input = splitted;
                        this.currentRecipe = recipe;
                        this.ticksSieved = 0.0F;
                        ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0, Item.getIdFromItem(this.input.getItem()));
                        break;
                     }
                  }
               }
            } else {
               this.ticksSieved = this.ticksSieved + this.sievePower / 40.0F;
               if (this.ticksSieved > this.currentRecipe.timeToCraft) {
                  for (int i = 0; i < this.currentRecipe.output.size(); i++) {
                     Ingridient ingredientOut = (Ingridient)this.currentRecipe.output.get(i);
                     if (GetMOP.rand.nextFloat() < this.currentRecipe.chances[i]) {
                        ItemStack out = ingredientOut.createStack();
                        if (!OreDicHelper.isMissing(out.getItem())) {
                           this.world
                              .spawnEntity(
                                 new EntityItem(
                                    this.world,
                                    this.pos.getX() + 0.5,
                                    this.pos.getY() + 0.17F,
                                    this.pos.getZ() + 0.5,
                                    out
                                 )
                              );
                        }
                     }
                  }

                  this.input = null;
                  this.currentRecipe = null;
                  this.ticksSieved = 0.0F;
                  ITileEntitySynchronize.sendSynchronize(this, 64.0, 0.0, 0.0);
               }

               if (this.currentRecipe != null && GetMOP.rand.nextFloat() < 0.05F + limitedPower * 0.001F) {
                  this.world
                     .playSound(
                        (EntityPlayer)null, this.getPos(), this.currentRecipe.sound, SoundCategory.BLOCKS, 0.5F, 0.9F + GetMOP.rand.nextFloat() * 0.2F
                     );
               }
            }
         } else if (this.clientShouldSpawnParticle) {
            double xx = this.pos.getX() + 0.0625F + 0.875F * GetMOP.rand.nextFloat();
            double yy = this.pos.getY() + 0.4F;
            double zz = this.pos.getZ() + 0.0625F + 0.875F * GetMOP.rand.nextFloat() + this.getSieveOffset(1.0F) * 0.0625F;
            this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, xx, yy, zz, 0.0, 0.0, 0.0, new int[]{this.clientIdItem});
         }
      }
   }

   public float getSieveOffset(float partialTicks) {
      double spower = GetMOP.partial((float)this.sievePower, (float)this.prevSievePower, partialTicks);
      double time = GetMOP.partial(this.timeExisted, this.prevTimeExisted, (double)partialTicks);
      double maxoffset = GetMOP.getfromto(spower, 0.0, 60.0) * 2.0 * 0.0625;
      double offset = Math.sin(time * 2.0) * maxoffset;
      return (float)offset;
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.sievePower = (int)args[0];
      }

      if (args.length == 2) {
         if (args[0] == 1.0) {
            this.clientIdItem = (int)args[1];
            this.clientShouldSpawnParticle = true;
         } else {
            this.clientShouldSpawnParticle = false;
         }
      }
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.setInteger("sievePower", this.sievePower);
      if (this.input != null) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         this.input.writeToNBT(nbttagcompound);
         compound.setTag("item", nbttagcompound);
      }

      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      if (compound.hasKey("sievePower")) {
         this.sievePower = compound.getInteger("sievePower");
      }

      if (compound.hasKey("item")) {
         NBTTagCompound nbttagcompound = compound.getCompoundTag("item");
         this.input = new ItemStack(nbttagcompound);
         SieveRecipe recipe = SieveRecipesRegister.getRecipe(this.input);
         if (recipe != null) {
            this.currentRecipe = recipe;
            this.ticksSieved = 0.0F;
            ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0, Item.getIdFromItem(this.input.getItem()));
         } else {
            this.input = null;
         }
      }

      this.read(compound);
      super.readFromNBT(compound);
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("rotated")) {
         this.rotated = compound.getBoolean("rotated");
      }
   }

   public void write(NBTTagCompound compound) {
      compound.setBoolean("rotated", this.rotated);
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
