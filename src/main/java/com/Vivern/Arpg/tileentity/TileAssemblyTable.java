package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.AssemblyTable;
import com.Vivern.Arpg.blocks.BlockAssemblyAugment;
import com.Vivern.Arpg.container.ContainerAssemblyTable;
import com.Vivern.Arpg.elements.models.AssemblyTableModel;
import com.Vivern.Arpg.main.AssemblyTableRecipesRegister;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.recipes.AssemblyTableRecipe;
import com.Vivern.Arpg.recipes.Ingridient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileAssemblyTable extends TileEntityLockableLoot implements ITickable, TileEntityClicked, ITileEntitySynchronize, ISidedInventory {
   public static Random rand = new Random();
   public NonNullList<ItemStack> stacks = NonNullList.withSize(16, ItemStack.EMPTY);
   public static final int[] SLOTS_SIDES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
   public static final int[] SLOTS_BOTTOM = new int[]{14, 15};
   public static final int[] SLOTS_UP = new int[0];
   public EnergyStorage electricStorage;
   public int rfprevious = 0;
   public float progress = 0.0F;
   public int currentRFtotick = 0;
   public int currentMaxCraftTime = 0;
   public AugmentInfo augments = AugmentInfo.EMPTY;
   public boolean noNeedAugments;
   public int augmIDslot9 = 0;
   public int augmIDslot10 = 0;
   public int augmIDslot11 = 0;
   public int augmIDslot12 = 0;
   public int augmIDslot13 = 0;
   public int rfMax = 10000;
   public boolean[] slotsUsed = null;
   public int sendcounter = 0;
   public boolean started = false;
   public AssemblyTableModel.AssemblyManipulatorAnimation[] animations = new AssemblyTableModel.AssemblyManipulatorAnimation[]{
      new AssemblyTableModel.AssemblyManipulatorAnimation(1),
      new AssemblyTableModel.AssemblyManipulatorAnimation(2),
      new AssemblyTableModel.AssemblyManipulatorAnimation(3)
   };
   public ArrayList<BlockPos> surroundings;
   String rfWritten = "";
   public IItemHandler[] itemHandlers = new IItemHandler[6];

   public TileAssemblyTable() {
      this.electricStorage = new EnergyStorage(this.rfMax, 10000, 10000, 0);
   }

   public static String augmentToString(int id) {
      if (id == 1) {
         return "TURNING";
      } else if (id == 2) {
         return "PRESS";
      } else if (id == 3) {
         return "WELD";
      } else if (id == 4) {
         return "PLASMA";
      } else {
         return id == 5 ? "MOLECULAR" : "NONE";
      }
   }

   public int inventorySlotToAugment(int slot) {
      if (slot == 9) {
         return this.augmIDslot9;
      } else if (slot == 10) {
         return this.augmIDslot10;
      } else if (slot == 11) {
         return this.augmIDslot11;
      } else if (slot == 12) {
         return this.augmIDslot12;
      } else {
         return slot == 13 ? this.augmIDslot13 : 0;
      }
   }

   @Override
   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 10) {
         if (mouseY == 1) {
            if (!this.rfWritten.isEmpty()) {
               this.rfWritten = this.rfWritten.substring(0, this.rfWritten.length() - 1);
            }
         } else if (mouseY == 0) {
            this.rfWritten = this.rfWritten + mouseX;
         } else if (mouseY == 2 && !this.getStackInSlot(14).isEmpty()) {
            for (AssemblyTableRecipe recipe : AssemblyTableRecipesRegister.allRecipes) {
               if (recipe.craftresult.isStackAllowed(this.getStackInSlot(14))) {
                  for (int i = 0; i < recipe.recipe.size(); i++) {
                     this.setInventorySlotContents(i, ((Ingridient)recipe.recipe.get(i)).createStack());
                  }

                  if (recipe.craftresult2 != null && recipe.craftresult2 != Ingridient.EMPTY) {
                     this.setInventorySlotContents(15, recipe.craftresult2.createStack());
                  }

                  if (recipe.augmentsRecipe.isEmpty()) {
                     break;
                  }

                  for (int i = 0; i < recipe.augmentsRecipe.size(); i++) {
                     this.setInventorySlotContents(i + 9, recipe.augmentsRecipe.get(i).ingridient.createStack());
                  }

                  this.augmIDslot9 = recipe.getAugmentCost(0).augmentID;
                  this.augmIDslot10 = recipe.getAugmentCost(1).augmentID;
                  this.augmIDslot11 = recipe.getAugmentCost(2).augmentID;
                  this.augmIDslot12 = recipe.getAugmentCost(3).augmentID;
                  this.augmIDslot13 = recipe.getAugmentCost(4).augmentID;
                  break;
               }
            }
         }

         if (!this.rfWritten.isEmpty()) {
            int i = Integer.parseInt(this.rfWritten);
            this.electricStorage = new EnergyStorage(i, 10000, 10000, i);
            this.rfMax = i;
         } else {
            this.electricStorage.extractEnergy(Integer.MAX_VALUE, false);
         }

         PacketHandler.trySendPacketUpdate(this.getWorld(), this.getPos(), this, 16.0);
      }

      if (mouseY > 38 && mouseY < 52) {
         if (mouseX > 27 && mouseX < 41) {
            this.augmIDslot9 = this.getSlotChanged(9);
            AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         } else if (mouseX > 54 && mouseX < 68) {
            this.augmIDslot10 = this.getSlotChanged(10);
            AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         } else if (mouseX > 81 && mouseX < 95) {
            this.augmIDslot11 = this.getSlotChanged(11);
            AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         } else if (mouseX > 108 && mouseX < 122) {
            this.augmIDslot12 = this.getSlotChanged(12);
            AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         } else if (mouseX > 135 && mouseX < 149) {
            this.augmIDslot13 = this.getSlotChanged(13);
            AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }
      }
   }

   public int getSlotChanged(int slot) {
      if (slot == 9) {
         int res = this.augmIDslot9;

         for (int n = 0; n < 5; n++) {
            res = GetMOP.next(res, 1, 6);
            AugmentInfo removing = new AugmentInfo(this.augments);
            removing.addById(res, -1);
            removing.addById(this.augmIDslot10, -1);
            removing.addById(this.augmIDslot11, -1);
            removing.addById(this.augmIDslot12, -1);
            removing.addById(this.augmIDslot13, -1);
            if (removing.isPositive()) {
               return res;
            }
         }
      }

      if (slot == 10) {
         int res = this.augmIDslot10;

         for (int nx = 0; nx < 5; nx++) {
            res = GetMOP.next(res, 1, 6);
            AugmentInfo removing = new AugmentInfo(this.augments);
            removing.addById(res, -1);
            removing.addById(this.augmIDslot9, -1);
            removing.addById(this.augmIDslot11, -1);
            removing.addById(this.augmIDslot12, -1);
            removing.addById(this.augmIDslot13, -1);
            if (removing.isPositive()) {
               return res;
            }
         }
      }

      if (slot == 11) {
         int res = this.augmIDslot11;

         for (int nxx = 0; nxx < 5; nxx++) {
            res = GetMOP.next(res, 1, 6);
            AugmentInfo removing = new AugmentInfo(this.augments);
            removing.addById(res, -1);
            removing.addById(this.augmIDslot10, -1);
            removing.addById(this.augmIDslot9, -1);
            removing.addById(this.augmIDslot12, -1);
            removing.addById(this.augmIDslot13, -1);
            if (removing.isPositive()) {
               return res;
            }
         }
      }

      if (slot == 12) {
         int res = this.augmIDslot12;

         for (int nxxx = 0; nxxx < 5; nxxx++) {
            res = GetMOP.next(res, 1, 6);
            AugmentInfo removing = new AugmentInfo(this.augments);
            removing.addById(res, -1);
            removing.addById(this.augmIDslot10, -1);
            removing.addById(this.augmIDslot11, -1);
            removing.addById(this.augmIDslot9, -1);
            removing.addById(this.augmIDslot13, -1);
            if (removing.isPositive()) {
               return res;
            }
         }
      }

      if (slot == 13) {
         int res = this.augmIDslot13;

         for (int nxxxx = 0; nxxxx < 5; nxxxx++) {
            res = GetMOP.next(res, 1, 6);
            AugmentInfo removing = new AugmentInfo(this.augments);
            removing.addById(res, -1);
            removing.addById(this.augmIDslot10, -1);
            removing.addById(this.augmIDslot11, -1);
            removing.addById(this.augmIDslot12, -1);
            removing.addById(this.augmIDslot9, -1);
            if (removing.isPositive()) {
               return res;
            }
         }
      }

      return 0;
   }

   public int getSizeInventory() {
      return 16;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.stacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public int addItemStack(ItemStack stack) {
      for (int i = 0; i < this.stacks.size(); i++) {
         if (((ItemStack)this.stacks.get(i)).isEmpty()) {
            this.setInventorySlotContents(i, stack);
            return i;
         }
      }

      return -1;
   }

   public void checkIdSlots() {
      AugmentInfo removing = new AugmentInfo(this.augments);
      if (this.augmIDslot9 != 0) {
         if (removing.getById(this.augmIDslot9) > 0) {
            removing.addById(this.augmIDslot9, -1);
         } else {
            this.augmIDslot9 = 0;
         }
      }

      if (this.augmIDslot10 != 0) {
         if (removing.getById(this.augmIDslot10) > 0) {
            removing.addById(this.augmIDslot10, -1);
         } else {
            this.augmIDslot10 = 0;
         }
      }

      if (this.augmIDslot11 != 0) {
         if (removing.getById(this.augmIDslot11) > 0) {
            removing.addById(this.augmIDslot11, -1);
         } else {
            this.augmIDslot11 = 0;
         }
      }

      if (this.augmIDslot12 != 0) {
         if (removing.getById(this.augmIDslot12) > 0) {
            removing.addById(this.augmIDslot12, -1);
         } else {
            this.augmIDslot12 = 0;
         }
      }

      if (this.augmIDslot13 != 0) {
         if (removing.getById(this.augmIDslot13) > 0) {
            removing.addById(this.augmIDslot13, -1);
         } else {
            this.augmIDslot13 = 0;
         }
      }
   }

   public boolean consumePower(int amount) {
      return this.electricStorage.extractEnergy(amount, true) >= amount ? this.electricStorage.extractEnergy(amount, false) >= amount : false;
   }

   public boolean checkAndStartRecipe() {
      if (!this.world.isRemote) {
         for (AssemblyTableRecipe recipe : AssemblyTableRecipesRegister.allRecipes) {
            if (recipe.tryCraft(this)) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.started = args[0] > 0.0;
      }
   }

   public void setAugmentsTilesToWork(AugmentInfo augmentInfo, int allTimeToWork) {
      if (allTimeToWork <= 0
         || augmentInfo.cutters != 0
         || augmentInfo.presses != 0
         || augmentInfo.welds != 0
         || augmentInfo.plasmaSprays != 0
         || augmentInfo.molecularPrinters != 0) {
         if (this.surroundings != null && !this.surroundings.isEmpty()) {
            for (BlockPos poss : this.surroundings) {
               TileEntity tile = this.world.getTileEntity(poss);
               if (tile instanceof TileAssemblyAugment) {
                  TileAssemblyAugment tileAssemblyAugment = (TileAssemblyAugment)tile;
                  if (allTimeToWork > 0) {
                     if (augmentInfo.getById(tileAssemblyAugment.augment) > 0) {
                        augmentInfo.addById(tileAssemblyAugment.augment, -1);
                        tileAssemblyAugment.startWorkingOnServer(allTimeToWork);
                     }
                  } else {
                     tileAssemblyAugment.startWorkingOnServer(allTimeToWork);
                  }
               }
            }
         }
      }
   }

   public void update() {
      if (!this.world.isRemote) {
         if (this.augments == AugmentInfo.EMPTY) {
            this.augments = this.findAugments();
            AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }
      } else {
         boolean isAnyoneWork = false;

         for (int i = 0; i < this.animations.length; i++) {
            this.animations[i].updateAnimation();
            if (this.animations[i].isWorkingNow()) {
               isAnyoneWork = true;
            }
         }

         if (this.started) {
            if (!isAnyoneWork) {
               int rAnim = rand.nextInt(this.animations.length);
               if (this.animations[rAnim].workTime >= this.animations[rAnim].maxworkTime) {
                  this.animations[rAnim].startWorking(rand, this);
               }
            }

            if (rand.nextFloat() < 0.4F) {
               this.world
                  .spawnParticle(
                     EnumParticleTypes.CLOUD,
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 1.05,
                     this.getPos().getZ() + 0.5,
                     (rand.nextFloat() - 0.5) * 0.2,
                     (rand.nextFloat() - 0.5) * 0.1,
                     (rand.nextFloat() - 0.5) * 0.2,
                     new int[0]
                  );
               this.world
                  .spawnParticle(
                     EnumParticleTypes.SMOKE_NORMAL,
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 1.05,
                     this.getPos().getZ() + 0.5,
                     (rand.nextFloat() - 0.5) * 0.2,
                     (rand.nextFloat() - 0.5) * 0.1,
                     (rand.nextFloat() - 0.5) * 0.2,
                     new int[0]
                  );
            }
         }
      }

      if (!this.noNeedAugments) {
         if (this.currentMaxCraftTime <= 0) {
            if (this.checkAndStartRecipe()) {
               AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
               if (!this.started) {
                  ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0);
                  this.started = true;
               }
            } else if (this.started) {
               ITileEntitySynchronize.sendSynchronize(this, 64.0, 0.0);
               this.started = false;
            }
         } else if (this.progress < this.currentMaxCraftTime) {
            if (this.consumePower(this.currentRFtotick)) {
               this.progress++;
               this.sendcounter++;
               if (this.sendcounter % 3 == 0) {
                  AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
               }

               if (!this.started) {
                  ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0);
                  this.started = true;
               }
            } else {
               if (this.started) {
                  ITileEntitySynchronize.sendSynchronize(this, 64.0, 0.0);
                  this.started = false;
               }

               if (!this.world.isRemote) {
                  this.sendcounter++;
                  if (this.sendcounter % 5 == 0 && this.electricStorage.getEnergyStored() != this.rfprevious) {
                     this.rfprevious = this.electricStorage.getEnergyStored();
                     AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
                  }
               }
            }
         } else if (this.progress >= this.currentMaxCraftTime) {
            if (!this.world.isRemote && this.electricStorage.extractEnergy(this.currentRFtotick, true) >= this.currentRFtotick) {
               this.checkAndStartRecipe();
               this.progress = 0.0F;
               this.currentMaxCraftTime = 0;
               this.currentRFtotick = 0;
               AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
            } else if (!this.world.isRemote) {
               this.sendcounter++;
               if (this.sendcounter % 5 == 0 && this.electricStorage.getEnergyStored() != this.rfprevious) {
                  this.rfprevious = this.electricStorage.getEnergyStored();
                  AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
               }
            }
         }
      }
   }

   public void setStartedAndSend(boolean started) {
   }

   public void onAugmentsChange() {
      if (!this.world.isRemote) {
         this.setAugmentsTilesToWork(null, 0);
         this.augments = this.findAugments();
         this.progress = 0.0F;
         this.currentMaxCraftTime = 0;
         this.currentRFtotick = 0;
         this.augmIDslot9 = 0;
         this.augmIDslot10 = 0;
         this.augmIDslot11 = 0;
         this.augmIDslot12 = 0;
         this.augmIDslot13 = 0;
         AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 16);
      }
   }

   public AugmentInfo findAugments() {
      AugmentInfo info = new AugmentInfo();
      if (this.noNeedAugments) {
         info.cutters = 5;
         info.presses = 5;
         info.welds = 5;
         info.plasmaSprays = 5;
         info.molecularPrinters = 5;
         return info;
      } else {
         this.surroundings = new ArrayList<>();

         for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
               BlockPos newpos = this.getPos().add(x, 0, z);
               IBlockState state = this.world.getBlockState(newpos);
               if (state.getBlock() instanceof BlockAssemblyAugment) {
                  BlockPos nextpos = newpos;
                  IBlockState next = state;

                  for (int i = 0; i < 36; i++) {
                     nextpos = nextpos.offset((EnumFacing)next.getValue(BlockAssemblyAugment.FACING));
                     next = this.world.getBlockState(nextpos);
                     if (this.world.getTileEntity(nextpos) == this) {
                        if (state.getBlock() == BlocksRegister.AUGMENTCUT) {
                           info.cutters++;
                        }

                        if (state.getBlock() == BlocksRegister.AUGMENTPRESS) {
                           info.presses++;
                        }

                        if (state.getBlock() == BlocksRegister.AUGMENTWELD) {
                           info.welds++;
                        }

                        if (state.getBlock() == BlocksRegister.AUGMENTPLASMA) {
                           info.plasmaSprays++;
                        }

                        if (state.getBlock() == BlocksRegister.AUGMENTMOLECULAR) {
                           info.molecularPrinters++;
                        }

                        this.surroundings.add(newpos);
                        break;
                     }

                     if (!(next.getBlock() instanceof BlockAssemblyAugment)) {
                        break;
                     }
                  }
               }
            }
         }

         Collections.shuffle(this.surroundings);
         return info;
      }
   }

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      if (capability == CapabilityEnergy.ENERGY) {
         return (T)this.electricStorage;
      } else if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (this.itemHandlers[facing.getIndex()] == null) {
            this.itemHandlers[facing.getIndex()] = new SidedInvWrapper(this, facing);
         }

         return (T)this.itemHandlers[facing.getIndex()];
      } else {
         return (T)super.getCapability(capability, facing);
      }
   }

   public void read(NBTTagCompound compound) {
      this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.stacks);
      }

      if (compound.hasKey("rfmax")) {
         this.rfMax = compound.getInteger("rfmax");
      }

      if (compound.hasKey("rf")) {
         this.electricStorage = new EnergyStorage(this.rfMax, 10000, 10000, compound.getInteger("rf"));
      }

      if (compound.hasKey("progress")) {
         this.progress = compound.getFloat("progress");
      }

      if (compound.hasKey("rftick")) {
         this.currentRFtotick = compound.getInteger("rftick");
      }

      if (compound.hasKey("crafttime")) {
         this.currentMaxCraftTime = compound.getInteger("crafttime");
      }

      if (compound.hasKey("augmSlot9")) {
         this.augmIDslot9 = compound.getByte("augmSlot9");
      }

      if (compound.hasKey("augmSlot10")) {
         this.augmIDslot10 = compound.getByte("augmSlot10");
      }

      if (compound.hasKey("augmSlot11")) {
         this.augmIDslot11 = compound.getByte("augmSlot11");
      }

      if (compound.hasKey("augmSlot12")) {
         this.augmIDslot12 = compound.getByte("augmSlot12");
      }

      if (compound.hasKey("augmSlot13")) {
         this.augmIDslot13 = compound.getByte("augmSlot13");
      }

      if (compound.hasKey("slotsUsed")) {
         int slots = compound.getByte("slotsUsed");
         this.slotsUsed = new boolean[5];
         if ((slots & 1) != 0) {
            this.slotsUsed[0] = true;
         }

         if ((slots & 2) != 0) {
            this.slotsUsed[1] = true;
         }

         if ((slots & 4) != 0) {
            this.slotsUsed[2] = true;
         }

         if ((slots & 8) != 0) {
            this.slotsUsed[3] = true;
         }

         if ((slots & 16) != 0) {
            this.slotsUsed[4] = true;
         }
      }

      if (compound.hasKey("noNeedAugments")) {
         this.noNeedAugments = compound.getBoolean("noNeedAugments");
      }

      if (compound.hasKey("rfWritten")) {
         this.rfWritten = compound.getString("rfWritten");
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.stacks);
      }

      compound.setInteger("rfmax", this.rfMax);
      compound.setInteger("rf", this.electricStorage.getEnergyStored());
      compound.setFloat("progress", this.progress);
      compound.setInteger("rftick", this.currentRFtotick);
      compound.setInteger("crafttime", this.currentMaxCraftTime);
      compound.setByte("augmSlot9", (byte)this.augmIDslot9);
      compound.setByte("augmSlot10", (byte)this.augmIDslot10);
      compound.setByte("augmSlot11", (byte)this.augmIDslot11);
      compound.setByte("augmSlot12", (byte)this.augmIDslot12);
      compound.setByte("augmSlot13", (byte)this.augmIDslot13);
      if (this.slotsUsed != null) {
         int slots = 0;
         if (this.slotsUsed[0]) {
            slots |= 1;
         }

         if (this.slotsUsed[1]) {
            slots |= 2;
         }

         if (this.slotsUsed[2]) {
            slots |= 4;
         }

         if (this.slotsUsed[3]) {
            slots |= 8;
         }

         if (this.slotsUsed[4]) {
            slots |= 16;
         }

         compound.setByte("slotsUsed", (byte)slots);
      }

      if (this.noNeedAugments) {
         compound.setBoolean("noNeedAugments", this.noNeedAugments);
      }

      if (!this.rfWritten.isEmpty()) {
         compound.setString("rfWritten", this.rfWritten);
      }

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

   public void setInventorySlotContents(int index, ItemStack stack) {
      super.setInventorySlotContents(index, stack);
      if (!this.world.isRemote) {
         for (AssemblyTableRecipe recipe : AssemblyTableRecipesRegister.allRecipes) {
            if (recipe.timeToCraft == this.currentMaxCraftTime && recipe.RFToTick == this.currentRFtotick && recipe.canContinueCrafting(this)) {
               return;
            }
         }

         if (this.currentMaxCraftTime > 0) {
            this.setAugmentsTilesToWork(null, 0);
         }

         this.progress = 0.0F;
         this.currentMaxCraftTime = 0;
         this.currentRFtotick = 0;
         AssemblyTable.trySendPacketUpdate(this.world, this.getPos(), this, 8);
      }
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public String getName() {
      return "tile_assembly_table";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerAssemblyTable(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.assembly_table";
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }

   public int getField(int id) {
      switch (id) {
         case 0:
            return this.electricStorage.getEnergyStored();
         case 1:
            return this.currentMaxCraftTime;
         case 2:
            return (int)this.progress;
         case 3:
            return this.currentRFtotick;
         case 4:
            return this.augmIDslot9;
         case 5:
            return this.augmIDslot10;
         case 6:
            return this.augmIDslot11;
         case 7:
            return this.augmIDslot12;
         case 8:
            return this.augmIDslot13;
         case 9:
            int slots = 0;
            if (this.slotsUsed != null) {
               if (this.slotsUsed[0]) {
                  slots |= 1;
               }

               if (this.slotsUsed[1]) {
                  slots |= 2;
               }

               if (this.slotsUsed[2]) {
                  slots |= 4;
               }

               if (this.slotsUsed[3]) {
                  slots |= 8;
               }

               if (this.slotsUsed[4]) {
                  slots |= 16;
               }
            }

            return slots;
         case 10:
            return this.getPos().getX();
         case 11:
            return this.getPos().getY();
         case 12:
            return this.getPos().getZ();
         case 13:
            return this.rfMax;
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 0:
            this.electricStorage = new EnergyStorage(this.rfMax, 10000, 10000, value);
            break;
         case 1:
            this.currentMaxCraftTime = value;
            break;
         case 2:
            this.progress = value;
            break;
         case 3:
            this.currentRFtotick = value;
            break;
         case 4:
            this.augmIDslot9 = value;
            break;
         case 5:
            this.augmIDslot10 = value;
            break;
         case 6:
            this.augmIDslot11 = value;
            break;
         case 7:
            this.augmIDslot12 = value;
            break;
         case 8:
            this.augmIDslot13 = value;
            break;
         case 9:
            this.slotsUsed = new boolean[5];
            if ((value & 1) != 0) {
               this.slotsUsed[0] = true;
            }

            if ((value & 2) != 0) {
               this.slotsUsed[1] = true;
            }

            if ((value & 4) != 0) {
               this.slotsUsed[2] = true;
            }

            if ((value & 8) != 0) {
               this.slotsUsed[3] = true;
            }

            if ((value & 16) != 0) {
               this.slotsUsed[4] = true;
            }
         case 10:
         case 11:
         case 12:
         default:
            break;
         case 13:
            this.rfMax = value;
      }
   }

   public int getFieldCount() {
      return 13;
   }

   public void playsound(SoundEvent soundEvent, int averageLength, int lengthAmplitude, int length) {
      float amplitude = (float)lengthAmplitude / averageLength;
      float difference = (float)(averageLength - length) / lengthAmplitude;
      this.world
         .playSound(
            this.pos.getX(),
            this.pos.getY(),
            this.pos.getZ(),
            soundEvent,
            SoundCategory.BLOCKS,
            1.0F,
            1.0F + difference * amplitude,
            false
         );
   }

   public int[] getSlotsForFace(EnumFacing side) {
      if (side == EnumFacing.DOWN) {
         return SLOTS_BOTTOM;
      } else {
         return side == EnumFacing.UP ? SLOTS_UP : SLOTS_SIDES;
      }
   }

   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
      return direction == EnumFacing.UP ? false : !this.getStackInSlot(index).isEmpty() && ItemStack.areItemsEqual(this.getStackInSlot(index), itemStackIn);
   }

   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
      return direction == EnumFacing.DOWN && (index == 14 || index == 15);
   }

   public static class AugmentInfo {
      public int cutters;
      public int presses;
      public int welds;
      public int plasmaSprays;
      public int molecularPrinters;
      public static AugmentInfo EMPTY = new AugmentInfo();

      public AugmentInfo(int cutters, int presses, int welds, int plasmaSprays, int molecularPrinters) {
         this.cutters = cutters;
         this.presses = presses;
         this.welds = welds;
         this.plasmaSprays = plasmaSprays;
         this.molecularPrinters = molecularPrinters;
      }

      public AugmentInfo() {
         this.cutters = 0;
         this.presses = 0;
         this.welds = 0;
         this.plasmaSprays = 0;
         this.molecularPrinters = 0;
      }

      public AugmentInfo(AugmentInfo toCopy) {
         this.cutters = toCopy.cutters;
         this.presses = toCopy.presses;
         this.welds = toCopy.welds;
         this.plasmaSprays = toCopy.plasmaSprays;
         this.molecularPrinters = toCopy.molecularPrinters;
      }

      public void addById(int id, int amount) {
         switch (id) {
            case 1:
               this.cutters += amount;
               break;
            case 2:
               this.presses += amount;
               break;
            case 3:
               this.welds += amount;
               break;
            case 4:
               this.plasmaSprays += amount;
               break;
            case 5:
               this.molecularPrinters += amount;
         }
      }

      public int getById(int id) {
         switch (id) {
            case 1:
               return this.cutters;
            case 2:
               return this.presses;
            case 3:
               return this.welds;
            case 4:
               return this.plasmaSprays;
            case 5:
               return this.molecularPrinters;
            default:
               return 0;
         }
      }

      public boolean isPositive() {
         return this.cutters >= 0 && this.presses >= 0 && this.welds >= 0 && this.plasmaSprays >= 0 && this.molecularPrinters >= 0;
      }
   }
}
