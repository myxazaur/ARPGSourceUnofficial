package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.PyrocrystallineConverter;
import com.vivern.arpg.container.ContainerCrystallizer;
import com.vivern.arpg.entity.EntityCubicParticle;
import com.vivern.arpg.entity.TrailParticle;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.network.PacketDoSomethingToClients;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.recipes.GeomanticFocus;
import com.vivern.arpg.recipes.PyrocrystallineRecipe;
import com.vivern.arpg.renders.ParticleTracker;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
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
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TilePyrocrystallineConverter extends TileEntityLockable implements ISidedInventory, IFillDrain, ITickable {
   public static ResourceLocation resou = new ResourceLocation("arpg:textures/pyrocrystall_ore.png");
   public static ResourceLocation texcub = new ResourceLocation("arpg:textures/geomantic_cubic.png");
   public static ResourceLocation crtrail = new ResourceLocation("arpg:textures/crystall_beam.png");
   public static ResourceLocation crcenter = new ResourceLocation("arpg:textures/simple_magic_shoot.png");
   public static final int[] SLOTS = new int[]{0, 1, 2};
   public static final int[] FOCUS = new int[]{3};
   public NonNullList<ItemStack> containItemStacks = NonNullList.withSize(4, ItemStack.EMPTY);
   public float manaStored = 0.0F;
   public int fluidMax = 1000;
   public FluidStorage tank1 = null;
   public int ticksExisted = 0;
   public static Random rand = new Random();
   public float geomanticEnergy = 0.0F;
   public float geomanticEnergyMax = 50.0F;
   public int meltCount = 0;
   public String melt = "";
   public int progress = 0;
   public int attemptFocusDamage = 0;
   public int progressPerOne = 0;
   public Block source = null;
   public int redstone = 0;
   public int lastParticleTime = 0;
   public Vec3d lastParticlesource = null;

   public TilePyrocrystallineConverter() {
      this.tank1 = new FluidStorage(this.fluidMax, this);
      this.tank1.setTileEntity(this);
   }

   public void update() {
      this.ticksExisted++;
      if (!this.world.isRemote) {
         if (this.ticksExisted % 5 == 0) {
            boolean sendUpd = false;
            if (!this.getStackInSlot(3).isEmpty() && this.getStackInSlot(3).getItem() == ItemsRegister.GEOMANTICCRYSTAL) {
               this.melt = GeomanticFocus.getMeltFromFocus(this.getStackInSlot(3));
            }

            if (!this.melt.isEmpty()) {
               for (PyrocrystallineRecipe recipe : PyrocrystallineRecipe.registry) {
                  if (recipe.tryMeltItems(this)) {
                     sendUpd = true;
                     break;
                  }
               }

               if (this.manaStored >= 1.0F && this.melt.equals(GeomanticFocus.getMeltFromFocus(this.getStackInSlot(3)))) {
                  BlockPos focuspos = PyrocrystallineConverter.getOffsetBlock(this.world, this.getPos());
                  if (this.world.getBlockState(focuspos).getBlock() == this.source) {
                     if (this.progress < 1000) {
                        int cost = Math.min(this.progressPerOne, 1000 - this.progress);
                        if (this.meltCount >= cost) {
                           this.meltCount -= cost;
                           this.progress += cost;
                           this.manaStored--;
                           if (this.ticksExisted % 20 == 0) {
                              this.sendParticles(this.world, focuspos);
                           }
                        }
                     } else {
                        NBTHelper.AddNBTfloat(this.getStackInSlot(3), -this.attemptFocusDamage, "durability");
                        if (NBTHelper.GetNBTfloat(this.getStackInSlot(3), "durability") <= 0.0F) {
                           this.removeStackFromSlot(3);
                        }

                        Block blockfrom = Block.getBlockFromName(this.melt);
                        IBlockState statein = blockfrom == null
                           ? Block.getBlockFromItem(((ItemStack)OreDictionary.getOres(this.melt).get(0)).getItem()).getDefaultState()
                           : blockfrom.getDefaultState();
                        this.world.setBlockState(focuspos, statein);
                        if (this.meltCount <= 0) {
                           this.meltCount = 0;
                           this.melt = "";
                           this.source = null;
                           this.attemptFocusDamage = 0;
                        }

                        this.progress = 0;
                     }

                     sendUpd = true;
                  }
               }
            }

            if (sendUpd) {
               PyrocrystallineConverter.trySendPacketUpdate(this.world, this.getPos(), this, 8);
            }
         }
      } else if (this.lastParticlesource != null && this.lastParticleTime > 0) {
         this.lastParticleTime--;
         EntityCubicParticle spel = new EntityCubicParticle(
            texcub,
            0.01F + rand.nextFloat() / 100.0F,
            0.0F,
            30,
            240,
            this.world,
            this.getPos().getX() + 0.5 + rand.nextGaussian() / 10.0,
            this.getPos().getY() + 0.5 + rand.nextGaussian() / 10.0,
            this.getPos().getZ() + 0.5 + rand.nextGaussian() / 10.0,
            0.0F,
            0.0F,
            0.0F,
            1.0F - rand.nextFloat() / 3.0F,
            1.0F - rand.nextFloat() / 3.0F,
            1.0F - rand.nextFloat() / 3.0F,
            true,
            rand.nextFloat(),
            rand.nextFloat(),
            rand.nextFloat(),
            0.08F,
            true,
            -0.0012F + rand.nextFloat() / 1000.0F
         );
         SuperKnockback.applyMove(
            spel,
            -(0.1F + rand.nextFloat() / 10.0F),
            this.lastParticlesource.x,
            this.lastParticlesource.y,
            this.lastParticlesource.z
         );
         this.world.spawnEntity(spel);
         if (this.ticksExisted % 20 == 0) {
            EntityCubicParticle spel2 = new EntityCubicParticle(
               resou,
               0.065F,
               0.0F,
               20,
               240,
               this.world,
               this.lastParticlesource.x,
               this.lastParticlesource.y,
               this.lastParticlesource.z,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               1.0F,
               true,
               0.0F,
               0.0F,
               0.0F,
               0.0F,
               false,
               0.0F
            );
            this.world.spawnEntity(spel2);
         }

         float R = 1.0F - rand.nextFloat() / 3.0F;
         float G = 1.0F - rand.nextFloat() / 3.0F;
         float B = 1.0F - rand.nextFloat() / 3.0F;
         ParticleTracker<TrailParticle> tracker = new ParticleTracker.TrackerFollowStaticPoint(this.lastParticlesource, true, 0.3F, 0.014F, 0.15F);
         TrailParticle trailpart = new TrailParticle(
            crcenter,
            0.1F,
            0.0F,
            50,
            240,
            this.world,
            this.getPos().getX() + 0.5 + rand.nextGaussian() / 10.0,
            this.getPos().getY() + 0.5 + rand.nextGaussian() / 10.0,
            this.getPos().getZ() + 0.5 + rand.nextGaussian() / 10.0,
            (float)rand.nextGaussian() / 7.0F,
            (float)rand.nextGaussian() / 7.0F,
            (float)rand.nextGaussian() / 7.0F,
            R,
            G,
            B,
            true,
            0,
            false,
            0.0F,
            crtrail,
            0.1F,
            240,
            R,
            G,
            B,
            0.15F,
            5,
            0.3F,
            2.0F
         );
         trailpart.alphaGlowing = true;
         trailpart.tracker = tracker;
         trailpart.TalphaGlowing = true;
         trailpart.onlyHorizontal = true;
         this.world.spawnEntity(trailpart);
      }
   }

   public void sendParticles(World world, BlockPos focuspos) {
      if (!world.isRemote) {
         PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
         packet.writeargs(
            focuspos.getX() + 0.5,
            focuspos.getY() + 0.5,
            focuspos.getZ() + 0.5,
            this.getPos().getX(),
            this.getPos().getY(),
            this.getPos().getZ(),
            3
         );
         PacketHandler.sendToAllAround(
            packet, world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 32.0
         );
      }
   }

   public void spawnParticles(double sx, double sy, double sz) {
      this.lastParticleTime = 20;
      this.lastParticlesource = new Vec3d(sx, sy, sz);
   }

   public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
      return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
   }

   @Nullable
   public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
      return (T)(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != null ? this.tank1 : super.getCapability(capability, facing));
   }

   @Override
   public int fill(FluidStorage thisstorage, FluidStack resource, boolean doFill) {
      return !thisstorage.canFillFluidType(resource) ? 0 : thisstorage.fillInternal(resource, doFill);
   }

   @Override
   public FluidStack drain(FluidStorage thisstorage, int maxDrain, boolean doDrain) {
      return !thisstorage.canDrainFluidType(thisstorage.getFluid()) ? null : thisstorage.drainInternal(maxDrain, doDrain);
   }

   @Override
   public void onContentsChanged(FluidStorage thisstorage, boolean fluidTypeChanges) {
      PyrocrystallineConverter.trySendPacketUpdate(this.world, this.getPos(), this, fluidTypeChanges ? 64 : 8);
   }

   public boolean isItemEquals(ItemStack stack, ItemStack other) {
      return !other.isEmpty() && stack.getItem() == other.getItem() && stack.getItemDamage() == other.getItemDamage();
   }

   public void read(NBTTagCompound compound) {
      this.containItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.containItemStacks);
      this.tank1.readFromNBT(compound, "tank1");
      if (compound.hasKey("stored")) {
         this.manaStored = compound.getFloat("stored");
      }

      if (compound.hasKey("redstone")) {
         this.redstone = compound.getBoolean("redstone") ? 1 : 0;
      }

      if (compound.hasKey("geoenergy")) {
         this.geomanticEnergy = compound.getFloat("geoenergy");
      }

      if (compound.hasKey("meltcount")) {
         this.meltCount = compound.getInteger("meltcount");
      }

      if (compound.hasKey("melt")) {
         this.melt = compound.getString("melt");
      }

      if (compound.hasKey("damagetofocus")) {
         this.attemptFocusDamage = compound.getInteger("damagetofocus");
      }

      if (compound.hasKey("progress")) {
         this.progress = compound.getInteger("progress");
      }

      if (compound.hasKey("progresperone")) {
         this.progressPerOne = compound.getInteger("progresperone");
      }

      if (compound.hasKey("blocksource")) {
         this.source = Block.getBlockFromName(compound.getString("blocksource"));
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.containItemStacks);
      this.tank1.writeToNBT(compound, "tank1");
      compound.setFloat("stored", this.manaStored);
      compound.setBoolean("redstone", this.redstone > 0);
      compound.setFloat("geoenergy", this.geomanticEnergy);
      compound.setInteger("meltcount", this.meltCount);
      compound.setString("melt", this.melt);
      compound.setInteger("damagetofocus", this.attemptFocusDamage);
      compound.setInteger("progress", this.progress);
      compound.setInteger("progresperone", this.progressPerOne);
      if (this.source != null) {
         compound.setString("blocksource", this.source.getRegistryName().toString());
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

   public int getSizeInventory() {
      return 4;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.containItemStacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack getStackInSlot(int index) {
      return (ItemStack)this.containItemStacks.get(index);
   }

   public ItemStack decrStackSize(int index, int count) {
      return ItemStackHelper.getAndSplit(this.containItemStacks, index, count);
   }

   public ItemStack removeStackFromSlot(int index) {
      return ItemStackHelper.getAndRemove(this.containItemStacks, index);
   }

   public void setInventorySlotContents(int index, ItemStack stack) {
      ItemStack itemstack = (ItemStack)this.containItemStacks.get(index);
      boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
      this.containItemStacks.set(index, stack);
      if (stack.getCount() > this.getInventoryStackLimit()) {
         stack.setCount(this.getInventoryStackLimit());
      }

      if (!flag) {
         PyrocrystallineConverter.trySendPacketUpdate(this.world, this.getPos(), this, 8);
      }
   }

   public String getName() {
      return "tile_pyrocrystalline_converter";
   }

   public boolean hasCustomName() {
      return false;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isUsableByPlayer(EntityPlayer player) {
      return this.world.getTileEntity(this.pos) != this
         ? false
         : player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
            <= 64.0;
   }

   public void openInventory(EntityPlayer player) {
   }

   public void closeInventory(EntityPlayer player) {
   }

   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return true;
   }

   public int[] getSlotsForFace(EnumFacing side) {
      return side == EnumFacing.UP ? FOCUS : SLOTS;
   }

   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
      return this.isItemValidForSlot(index, itemStackIn);
   }

   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
      return true;
   }

   public int getField(int id) {
      switch (id) {
         case 0:
            return (int)(this.manaStored * 10.0F);
         case 1:
            return 0;
         case 2:
            return 0;
         case 3:
            return this.fluidMax;
         case 4:
            return this.tank1.getFluidAmount();
         case 5:
            return (int)(this.geomanticEnergy * 10.0F);
         case 6:
            return (int)this.geomanticEnergyMax;
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 0:
            this.manaStored = value / 10.0F;
         case 1:
         case 2:
         case 4:
         default:
            break;
         case 3:
            this.fluidMax = value;
            break;
         case 5:
            this.geomanticEnergy = value / 10.0F;
            break;
         case 6:
            this.geomanticEnergyMax = value;
      }
   }

   public int getFieldCount() {
      return 7;
   }

   public void clear() {
      this.containItemStacks.clear();
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerCrystallizer(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.pyrocrystalline_converter";
   }

   public static class PyrocrystallMelt {
      public int count;
      public int currentProgress;
      public Block meltResult = null;
      public ItemStack meltItem = ItemStack.EMPTY;
      public String meltName = "";
   }
}
