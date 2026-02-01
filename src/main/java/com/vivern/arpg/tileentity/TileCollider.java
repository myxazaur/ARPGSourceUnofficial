package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.BlockCollider;
import com.vivern.arpg.blocks.ColliderPipe;
import com.vivern.arpg.container.ContainerAlchemicLab;
import com.vivern.arpg.entity.EntityMagneticField;
import com.vivern.arpg.entity.IEntitySynchronize;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.OreDicHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileCollider extends TileEntityLockable implements ITickable, IFillDrain, TileEntityClicked, ISidedInventory {
   public static final int[] SLOTS_SIDES = new int[]{0, 1};
   public static final int[] SLOTS_BOTTOM_TOP_BACK = new int[]{2, 3, 4};
   public NonNullList<ItemStack> containItemStacks = NonNullList.withSize(12, ItemStack.EMPTY);
   public EnergyStorage electricStorage;
   public int ticks = 0;
   public FluidStorage tank1 = null;
   public FluidStorage tank2 = null;
   public int fluidMax = 5000;
   public ParticleBeam beam1 = null;
   public ParticleBeam beam2 = null;
   public Random rand = new Random();
   public static final float speedExponent = 5000.0F;
   public static final float lightspeed = 80.0F;
   public static final float fluidExitExponent = 1.0F;
   public static final float itemExitExponent = 1.0F;
   public static final float speedMassExponent = 0.2F;
   public static final float blackHoleCriticalMass = 8.0F;
   public static final float muonMass = 2.0F;
   public boolean collide = false;
   public HashMap<Integer, Integer> elements = new HashMap<>();
   public static HashMap<Integer, Float> elementToMass = new HashMap<>();
   public static HashMap<Integer, ColliderMatter> elementToMatter = new HashMap<>();
   public EnumFacing launchDirection = EnumFacing.UP;
   public static boolean init = true;

   public TileCollider() {
      this.electricStorage = new EnergyStorage(800000, 2000, 2000, 0);
      this.tank1 = new FluidStorage(this.fluidMax, this);
      this.tank2 = new FluidStorage(this.fluidMax, this);
      if (init) {
         elementToMass.put(1, 0.001F);
         elementToMass.put(2, 0.004F);
         elementToMass.put(3, 0.007F);
         elementToMass.put(6, 0.012F);
         elementToMass.put(7, 0.014F);
         elementToMass.put(8, 0.016F);
         elementToMass.put(11, 0.023F);
         elementToMass.put(12, 0.024F);
         elementToMass.put(13, 0.027F);
         elementToMass.put(14, 0.028F);
         elementToMass.put(16, 0.032F);
         elementToMass.put(22, 0.048F);
         elementToMass.put(24, 0.052F);
         elementToMass.put(25, 0.055F);
         elementToMass.put(26, 0.056F);
         elementToMass.put(28, 0.058F);
         elementToMass.put(29, 0.063F);
         elementToMass.put(30, 0.065F);
         elementToMass.put(33, 0.075F);
         elementToMass.put(47, 0.107F);
         elementToMass.put(50, 0.118F);
         elementToMass.put(74, 0.183F);
         elementToMass.put(77, 0.192F);
         elementToMass.put(78, 0.195F);
         elementToMass.put(79, 0.197F);
         elementToMass.put(80, 0.2F);
         elementToMass.put(82, 0.207F);
         elementToMass.put(92, 0.238F);
         elementToMass.put(94, 0.244F);
         elementToMatter.put(1, new ColliderMatter((Fluid)null, 14.0F));
         elementToMatter.put(2, new ColliderMatter((Fluid)null, 31.0F));
         elementToMatter.put(3, new ColliderMatter((Fluid)null, 13.0F));
         elementToMatter.put(6, new ColliderMatter(OreDicHelper.DUSTCHARCOAL, false, 5.3F));
         elementToMatter.put(7, new ColliderMatter((Fluid)null, 17.3F));
         elementToMatter.put(8, new ColliderMatter((Fluid)null, 14.0F));
         elementToMatter.put(11, new ColliderMatter((Fluid)null, 23.7F));
         elementToMatter.put(12, new ColliderMatter((Fluid)null, 14.0F));
         elementToMatter.put(13, new ColliderMatter(OreDicHelper.DUSTALUMINIUM, false, 10.0F));
         elementToMatter.put(14, new ColliderMatter(OreDicHelper.SILICON, false, 12.1F));
         elementToMatter.put(16, new ColliderMatter(OreDicHelper.DUSTSULFUR, false, 15.5F));
         elementToMatter.put(22, new ColliderMatter(OreDicHelper.DUSTTITANIUM, false, 10.6F));
         elementToMatter.put(24, new ColliderMatter(OreDicHelper.DUSTCHROMIUM, false, 7.23F));
         elementToMatter.put(25, new ColliderMatter(OreDicHelper.DUSTMANGANESE, false, 7.4F));
         elementToMatter.put(26, new ColliderMatter(OreDicHelper.DUSTIRON, false, 7.1F));
         elementToMatter.put(28, new ColliderMatter(OreDicHelper.DUSTNICKEL, false, 6.6F));
         elementToMatter.put(29, new ColliderMatter(OreDicHelper.DUSTCOPPER, false, 7.1F));
         elementToMatter.put(30, new ColliderMatter((Fluid)null, 9.2F));
         elementToMatter.put(33, new ColliderMatter((Fluid)null, 13.1F));
         elementToMatter.put(47, new ColliderMatter(OreDicHelper.DUSTSILVER, false, 10.3F));
         elementToMatter.put(50, new ColliderMatter(OreDicHelper.DUSTTIN, false, 16.3F));
         elementToMatter.put(74, new ColliderMatter((Fluid)null, 9.5F));
         elementToMatter.put(77, new ColliderMatter((Fluid)null, 8.5F));
         elementToMatter.put(78, new ColliderMatter(OreDicHelper.DUSTPLATINUM, false, 9.1F));
         elementToMatter.put(79, new ColliderMatter(OreDicHelper.DUSTGOLD, false, 10.2F));
         elementToMatter.put(80, new ColliderMatter(OreDicHelper.QUICKSILVER, false, 14.8F));
         elementToMatter.put(82, new ColliderMatter(OreDicHelper.DUSTLEAD, false, 18.3F));
         elementToMatter.put(92, new ColliderMatter((Fluid)null, 12.5F));
         elementToMatter.put(94, new ColliderMatter((Fluid)null, 12.1F));
         init = false;
      }
   }

   public boolean consumePower(int amount) {
      return this.electricStorage.extractEnergy(amount, true) >= amount ? this.electricStorage.extractEnergy(amount, false) >= amount : false;
   }

   @Override
   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 1) {
         int i = this.electricStorage.getEnergyStored() / 1000;
         int[] istorage = new int[]{i};

         for (EnumFacing face : EnumFacing.VALUES) {
            BlockPos newpos = this.getPos().offset(face);
            IBlockState newstate = this.world.getBlockState(newpos);
            if (newstate.getBlock() == BlocksRegister.COLLIDERPIPE && !(Boolean)newstate.getValue(ColliderPipe.HERMETIC) && istorage[0] > 0) {
               istorage[0]--;
               this.vacuumate(istorage, newpos, face.getOpposite());
            }
         }

         int result = i - istorage[0];
         this.consumePower(result * 1000);
      }

      if (mouseButton == 2) {
         this.collide = !this.collide;
      }

      if (mouseButton == 3) {
         int dir = 1;
         int elem = 1;
         int countToUse = 1;
         boolean beamSlot = false;
         if (beamSlot && this.beam1 == null || !beamSlot && this.beam2 == null) {
            EnumFacing facing = EnumFacing.byIndex(dir);
            BlockPos offpos = this.getPos().offset(facing);
            if (this.isPipeForBeam(offpos)) {
               Integer elementCount = this.elements.get(elem);
               if (elementCount != null && elementCount >= countToUse) {
                  float mass = elementToMass.get(elem) * countToUse;
                  if (this.consumePower((int)(75000.0F * mass))) {
                     ParticleBeam newbeam = new ParticleBeam(mass, 15.0F, elem, 0.0F, offpos);
                     EnumFacing ofacing = facing.getOpposite();
                     newbeam.prevpos1 = this.getPos();
                     newbeam.prevpos2 = this.getPos().offset(ofacing);
                     newbeam.prevpos3 = newbeam.prevpos2.offset(ofacing);
                     newbeam.prevpos4 = newbeam.prevpos3.offset(ofacing);
                     if (beamSlot) {
                        this.beam1 = newbeam;
                     } else {
                        this.beam2 = newbeam;
                     }
                  }
               }
            }
         }
      }
   }

   public void vacuumate(int[] istorage, BlockPos pos, EnumFacing previous) {
      for (EnumFacing face : EnumFacing.VALUES) {
         if (face != previous) {
            BlockPos newpos = pos.offset(face);
            IBlockState newstate = this.world.getBlockState(newpos);
            if (newstate.getBlock() == BlocksRegister.COLLIDERPIPE && !(Boolean)newstate.getValue(ColliderPipe.HERMETIC) && istorage[0] > 0) {
               istorage[0]--;
               this.world.setBlockState(pos, newstate.withProperty(ColliderPipe.HERMETIC, true));
               this.vacuumate(istorage, newpos, face.getOpposite());
            }
         }
      }
   }

   public void update() {
      if (this.beam1 != null && !this.updateBeam(this.beam1)) {
         this.beam1 = null;
      }

      if (this.beam2 != null && !this.updateBeam(this.beam2)) {
         this.beam2 = null;
      }

      if (this.collide && this.beam1.pos == this.getPos() && this.beam2.pos == this.getPos()) {
         if (this.beam1.prevpos1 == this.beam2.prevpos1) {
            if (this.rand.nextFloat() < 0.5) {
               this.beam1 = null;
               this.explodeBeam(this.beam2);
               this.beam2 = null;
            } else {
               this.beam2 = null;
               this.explodeBeam(this.beam1);
               this.beam1 = null;
            }
         } else {
            float allSpeed = this.beam1.speed + this.beam2.speed;
            if (allSpeed <= 80.0F && allSpeed > 30.0F) {
               float interpSpeed = allSpeed - 30.0F;
               float atomMass1 = elementToMass.get(this.beam1.matter);
               int countOfElement1 = (int)(this.beam1.mass / atomMass1);
               float atomMass2 = elementToMass.get(this.beam2.matter);
               int countOfElement2 = (int)(this.beam2.mass / atomMass2);
               if (this.beam1.mass + this.beam2.mass < interpSpeed * 0.2F) {
                  int existedCount = Math.min(countOfElement1, countOfElement2);
                  int existedElement = this.beam1.matter + this.beam2.matter;
                  int have = this.elements.get(existedElement);
                  this.elements.replace(existedElement, Math.min(have + existedCount, 9999));
               }
            } else if (allSpeed > 80.0F && allSpeed <= 130.0F) {
               float allMass = this.beam1.mass + this.beam2.mass;
               if (!(allMass > 8.0F) && allMass < 2.0F) {
               }
            } else if (allSpeed > 130.0F) {
               float allMass = this.beam1.mass + this.beam2.mass;
               float atomMass1 = allMass * (allSpeed - 130.0F) / 30.0F;
            }

            this.beam1 = null;
            this.beam2 = null;
         }

         this.collide = false;
      }
   }

   public boolean updateBeam(ParticleBeam beam) {
      if (beam.speed < 10.0F) {
         this.explodeBeam(beam);
         return false;
      } else {
         int maxi = (int)beam.speed;

         for (int i = 0; i < maxi; i++) {
            if (this.collide && beam.pos == this.getPos()) {
               return true;
            }

            boolean stop = true;
            int fc = 0;

            for (int f = this.rand.nextInt(6); fc < 6; f = GetMOP.next(f, 1, 6)) {
               fc++;
               EnumFacing face = EnumFacing.byIndex(f);
               BlockPos newpos = this.pos.offset(face);
               if (newpos != beam.prevpos1 && this.isPipeForBeam(newpos)) {
                  beam.prevpos4 = beam.prevpos3;
                  beam.prevpos3 = beam.prevpos2;
                  beam.prevpos2 = beam.prevpos1;
                  beam.prevpos1 = beam.pos;
                  beam.pos = newpos;
                  stop = false;
                  break;
               }
            }

            if (stop) {
               this.explodeBeam(beam);
               return false;
            }

            EntityMagneticField field = this.getMagneticField(beam.pos);
            if (field != null) {
               float needStable = Math.min(field.power * beam.mass, beam.destabilization);
               beam.destabilization -= needStable;
               field.power = (int)(field.power - needStable * beam.mass);
               float speedAdd = Math.min(Math.min(field.power / 5000.0F, 1.0F), 80.0F - beam.speed);
               field.power = field.power - Math.round(speedAdd * 5000.0F);
               beam.speed = beam.speed + Math.min(Math.min(speedAdd / beam.mass, 1.0F), 80.0F - beam.speed);
               IEntitySynchronize.sendSynchronize(field, 64.0, field.power, field.maximumPower, field.fieldSize, 0.0, 0.0, 0.0);
            }
         }

         return true;
      }
   }

   public void explodeBeam(ParticleBeam beam) {
   }

   public boolean isPipeForBeam(BlockPos pos) {
      IBlockState state = this.world.getBlockState(pos);
      return state.getBlock() == BlocksRegister.COLLIDERPIPE || state.getBlock() == BlocksRegister.COLLIDER;
   }

   @Nullable
   public EntityMagneticField getMagneticField(BlockPos pos) {
      List<EntityMagneticField> list = this.getWorld().getEntitiesWithinAABB(EntityMagneticField.class, new AxisAlignedBB(pos));
      return !list.isEmpty() ? list.get(0) : null;
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
   }

   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CapabilityEnergy.ENERGY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
   }

   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      EnumFacing blockfacing = (EnumFacing)this.getWorld().getBlockState(this.getPos()).getValue(BlockCollider.FACING);
      if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != null) {
         return (T)(facing != EnumFacing.UP && facing != EnumFacing.DOWN && facing != blockfacing.getOpposite() ? this.tank1 : this.tank2);
      } else {
         return (T)(capability == CapabilityEnergy.ENERGY ? this.electricStorage : super.getCapability(capability, facing));
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("rf")) {
         this.electricStorage = new EnergyStorage(2000, 200, 200, compound.getInteger("rf"));
      }

      if (compound.hasKey("fluidmax")) {
         this.fluidMax = compound.getInteger("fluidmax");
      }

      if (compound.hasKey("collide")) {
         this.collide = compound.getBoolean("collide");
      }

      this.tank1.readFromNBT(compound, "tank1");
      this.tank2.readFromNBT(compound, "tank2");
      this.beam1 = new ParticleBeam(compound, "beam1");
      if (this.beam1.pos == null) {
         this.beam1 = null;
      }

      this.beam2 = new ParticleBeam(compound, "beam2");
      if (this.beam2.pos == null) {
         this.beam2 = null;
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      compound.setInteger("rf", this.electricStorage.getEnergyStored());
      compound.setInteger("fluidmax", this.fluidMax);
      compound.setBoolean("collide", this.collide);
      if (this.tank1 != null && this.tank2 != null) {
         this.tank1.writeToNBT(compound, "tank1");
         this.tank2.writeToNBT(compound, "tank2");
      }

      if (this.beam1 != null) {
         this.beam1.writeToNBT(compound, "beam1");
      }

      if (this.beam2 != null) {
         this.beam2.writeToNBT(compound, "beam2");
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
      return 5;
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
      if (!stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
         boolean var5 = true;
      } else {
         boolean var10000 = false;
      }

      this.containItemStacks.set(index, stack);
      if (stack.getCount() > this.getInventoryStackLimit()) {
         stack.setCount(this.getInventoryStackLimit());
      }
   }

   public String getName() {
      return "tile_collider";
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
      EnumFacing blockfacing = ((EnumFacing)this.getWorld().getBlockState(this.getPos()).getValue(BlockCollider.FACING)).getOpposite();
      return side != EnumFacing.DOWN && side != EnumFacing.UP && side != blockfacing ? SLOTS_SIDES : SLOTS_BOTTOM_TOP_BACK;
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
            return this.collide ? 1 : 0;
         case 1:
            return this.fluidMax;
         case 2:
            return this.tank1.getFluidAmount();
         case 3:
            return this.tank2.getFluidAmount();
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 0:
            this.collide = value > 0;
            break;
         case 1:
            this.fluidMax = value;
      }
   }

   public int getFieldCount() {
      return 4;
   }

   public void clear() {
      this.containItemStacks.clear();
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerAlchemicLab(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.collider";
   }

   public static class ColliderMatter {
      @Nullable
      ItemStack stack;
      @Nullable
      String oredigname;
      boolean isfluid;
      @Nullable
      Fluid fuid;
      public float countToVolume;

      public ColliderMatter(Item item, float countToVolume) {
         this.stack = new ItemStack(item);
         this.countToVolume = countToVolume;
      }

      public ColliderMatter(String oredigname, boolean isfluid, float countToVolume) {
         this.oredigname = oredigname;
         this.isfluid = isfluid;
         this.countToVolume = countToVolume;
      }

      public ColliderMatter(Fluid fuid, float countToVolume) {
         this.fuid = fuid;
         this.isfluid = true;
         this.countToVolume = countToVolume;
      }
   }

   public static class ParticleBeam {
      public float mass;
      public float speed;
      public int matter;
      public float destabilization;
      public BlockPos pos;
      public BlockPos prevpos1;
      public BlockPos prevpos2;
      public BlockPos prevpos3;
      public BlockPos prevpos4;

      public ParticleBeam(float mass, float speed, int matter, float destabilization, BlockPos pos) {
         this.mass = mass;
         this.speed = speed;
         this.matter = matter;
         this.destabilization = destabilization;
         this.pos = pos;
      }

      public ParticleBeam(NBTTagCompound nbt, String name) {
         if (nbt.hasKey(name)) {
            NBTTagCompound intag = nbt.getCompoundTag(name);
            if (intag.hasKey("mass")) {
               this.mass = intag.getFloat("mass");
               this.speed = intag.getFloat("speed");
               this.matter = intag.getInteger("matter");
               this.destabilization = intag.getFloat("unstable");
               this.pos = new BlockPos(intag.getInteger("posx"), intag.getInteger("posy"), intag.getInteger("posz"));
               this.prevpos1 = new BlockPos(intag.getInteger("prev1posx"), intag.getInteger("prev1posy"), intag.getInteger("prev1posz"));
               this.prevpos2 = new BlockPos(intag.getInteger("prev2posx"), intag.getInteger("prev2posy"), intag.getInteger("prev2posz"));
               this.prevpos3 = new BlockPos(intag.getInteger("prev3posx"), intag.getInteger("prev3posy"), intag.getInteger("prev3posz"));
               this.prevpos4 = new BlockPos(intag.getInteger("prev4posx"), intag.getInteger("prev4posy"), intag.getInteger("prev4posz"));
            }
         }
      }

      public NBTTagCompound writeToNBT(NBTTagCompound nbt, String name) {
         if (!nbt.hasKey(name)) {
            nbt.setTag(name, new NBTTagCompound());
         }

         NBTTagCompound intag = nbt.getCompoundTag(name);
         intag.setFloat("mass", this.mass);
         intag.setFloat("speed", this.speed);
         intag.setInteger("matter", this.matter);
         intag.setFloat("unstable", this.destabilization);
         intag.setInteger("posx", this.pos.getX());
         intag.setInteger("posy", this.pos.getY());
         intag.setInteger("posz", this.pos.getZ());
         intag.setInteger("prev1posx", this.prevpos1.getX());
         intag.setInteger("prev1posy", this.prevpos1.getY());
         intag.setInteger("prev1posz", this.prevpos1.getZ());
         intag.setInteger("prev2posx", this.prevpos2.getX());
         intag.setInteger("prev2posy", this.prevpos2.getY());
         intag.setInteger("prev2posz", this.prevpos2.getZ());
         intag.setInteger("prev3posx", this.prevpos3.getX());
         intag.setInteger("prev3posy", this.prevpos3.getY());
         intag.setInteger("prev3posz", this.prevpos3.getZ());
         intag.setInteger("prev4posx", this.prevpos4.getX());
         intag.setInteger("prev4posy", this.prevpos4.getY());
         intag.setInteger("prev4posz", this.prevpos4.getZ());
         return nbt;
      }
   }
}
