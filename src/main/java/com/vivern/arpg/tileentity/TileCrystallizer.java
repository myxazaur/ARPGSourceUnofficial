package com.vivern.arpg.tileentity;

import com.vivern.arpg.blocks.Crystallizer;
import com.vivern.arpg.container.ContainerCrystallizer;
import com.vivern.arpg.entity.EntityGeomanticCrystal;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.FluidsRegister;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.OreDicHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileCrystallizer extends TileEntityLockable implements IManaBuffer, ISidedInventory, IFillDrain, ITickable {
   public static final int[] SLOT = new int[]{0};
   public NonNullList<ItemStack> containItemStacks = NonNullList.withSize(1, ItemStack.EMPTY);
   public ManaBuffer manaBuffer = new ManaBuffer(this, this, 80.0F, 8, 1.0F, 6.0F);
   public int fluidMax = 1000;
   public FluidStorage tank1 = null;
   public int ticksExisted = 0;
   public static Random rand = new Random();
   public int radius = 3;
   public static float maxDissolved = 1000.0F;
   public float totalDissolved = 0.0F;
   public HashMap<String, Float> solution = new HashMap<>();
   public int redstone = 0;
   public static FluidStack fluidcost = new FluidStack(FluidsRegister.HYDROTHERMAL, 20);
   public int solutionPrimColor = 16777215;
   public int solutionSecnColor = 16777215;

   public TileCrystallizer() {
      this.tank1 = new FluidStorage(this.fluidMax, this);
      this.tank1.setTileEntity(this);
      this.solution.put(OreDicHelper.DUSTALUMINIUM, 0.0F);
      this.solution.put(OreDicHelper.DUSTCOPPER, 0.0F);
      this.solution.put(OreDicHelper.DUSTGOLD, 0.0F);
      this.solution.put(OreDicHelper.DUSTIRON, 0.0F);
      this.solution.put(OreDicHelper.DUSTLEAD, 0.0F);
      this.solution.put(OreDicHelper.DUSTQUARTZ, 0.0F);
      this.solution.put(OreDicHelper.DUSTSILVER, 0.0F);
      this.solution.put(OreDicHelper.DUSTSULFUR, 0.0F);
      this.solution.put(OreDicHelper.DUSTTIN, 0.0F);
      this.solution.put(OreDicHelper.DUSTTITANIUM, 0.0F);
      this.solution.put(OreDicHelper.DUSTCOAL, 0.0F);
      this.solution.put(OreDicHelper.DUSTCHROMIUM, 0.0F);
      this.solution.put(OreDicHelper.DUSTBERYLLIUM, 0.0F);
      this.solution.put(OreDicHelper.DUSTTITANIUM, 0.0F);
      this.solution.put(OreDicHelper.DUSTMANGANESE, 0.0F);
      this.solution.put(OreDicHelper.DUSTPLATINUM, 0.0F);
      this.solution.put(OreDicHelper.DUSTNICKEL, 0.0F);
      this.solution.put("magic", 0.0F);
   }

   public float getMaterialCount(String orename) {
      return this.solution.get(orename);
   }

   public void addMaterialCount(String orename, float amount) {
      this.solution.replace(orename, this.solution.get(orename) + amount);
   }

   public int tryDissolveItem(ItemStack itemstack) {
      if (itemstack != null && !itemstack.isEmpty() && this.totalDissolved < maxDissolved) {
         if (itemstack.getItem() != ItemsRegister.MAGIC_POWDER) {
            int[] ids = OreDictionary.getOreIDs(itemstack);

            for (int id : ids) {
               String name = OreDictionary.getOreName(id);
               name = transformName(name);
               if (this.solution.containsKey(name)) {
                  int mincount = (int)Math.ceil((maxDissolved - this.totalDissolved) / 25.0F);
                  float adding = Math.min(25.0F * Math.min(itemstack.getCount(), mincount), maxDissolved - this.totalDissolved);
                  this.addMaterialCount(name, adding);
                  this.totalDissolved += adding;
                  return mincount;
               }
            }
         } else if (this.solution.containsKey("magic")) {
            int mincount = (int)Math.ceil((maxDissolved - this.totalDissolved) / 25.0F);
            float adding = Math.min(25.0F * Math.min(itemstack.getCount(), mincount), maxDissolved - this.totalDissolved);
            this.addMaterialCount("magic", adding);
            this.totalDissolved += adding;
            return mincount;
         }
      }

      return 0;
   }

   public static String transformName(String name) {
      if (OreDicHelper.DUSTCHARCOAL.equals(name)) {
         return OreDicHelper.DUSTCOAL;
      } else {
         return !OreDicHelper.DUSTCERTUSQUARTZ.equals(name) && !OreDicHelper.DUSTNETHERQUARTZ.equals(name) && !OreDicHelper.DUSTSILICONDIOXIDE.equals(name)
            ? name
            : OreDicHelper.DUSTQUARTZ;
      }
   }

   public boolean growItemStack(ItemStack stack) {
      float size = NBTHelper.GetNBTfloat(stack, "size");
      if (size >= 100.0F) {
         return false;
      } else {
         boolean empty = true;
         boolean applyTypeCalc = size < 1.0F;
         float firstsize = 0.0F;
         float durabilityAdd = 0.0F;
         float maxfinalcount = 0.0F;
         if (this.tank1.getFluid() != null && this.tank1.getFluid().containsFluid(fluidcost)) {
            for (String name : this.solution.keySet()) {
               float count = this.solution.get(name);
               if (count > 0.0F) {
                  float finalcount = count / 100.0F;
                  NBTHelper.AddNBTfloat(stack, finalcount, name);
                  NBTHelper.AddNBTfloat(stack, finalcount, "size");
                  durabilityAdd += finalcount;
                  if (maxfinalcount < finalcount) {
                     maxfinalcount = finalcount;
                  }

                  this.addMaterialCount(name, -finalcount);
                  this.totalDissolved -= finalcount;
                  firstsize += finalcount;
                  empty = false;
               }
            }
         }

         if (empty) {
            NBTHelper.AddNBTfloat(stack, 0.1F, "size");
            NBTHelper.AddNBTfloat(stack, 1.0F, "durability");
         } else {
            this.tank1.drain(fluidcost.copy(), true);
            NBTHelper.AddNBTfloat(stack, maxfinalcount * durabilityAdd, "durability");
         }

         if (applyTypeCalc) {
            int typein = Math.round(firstsize * 10.0F) % 4;
            NBTHelper.SetNBTint(stack, typein, "type");
         }

         return true;
      }
   }

   public void calculateColor(ItemStack stack) {
      float R = 1.0F;
      float G = 1.0F;
      float B = 1.0F;
      float Rs = 1.0F;
      float Gs = 1.0F;
      float Bs = 1.0F;
      float alum = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTALUMINIUM);
      R = (float)(R - 0.009 * alum);
      G = (float)(G - 0.009 * alum);
      float ir = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTIRON);
      G = (float)(G - 0.008 * ir);
      B = (float)(B - 0.018 * ir);
      float cr = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTCHROMIUM);
      G = (float)(G - 0.018 * cr);
      B = (float)(B - 0.018 * cr);
      float be = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTBERYLLIUM);
      R = (float)(R - 0.019 * be);
      G = (float)(G - 0.003 * be);
      B = (float)(B - 0.019 * be);
      float ti = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTTITANIUM);
      Rs = (float)(Rs - 0.017 * ti);
      Gs = (float)(Gs - 0.017 * ti);
      R = (float)(R - 0.007 * ti);
      G = (float)(G - 0.007 * ti);
      float sulf = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTSULFUR);
      R = (float)(R - 0.023 * sulf);
      G = (float)(G - 0.023 * sulf);
      B = (float)(B - 0.023 * sulf);
      float mang = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTMANGANESE);
      R = (float)(R - 0.011 * mang);
      G = (float)(G - 0.022 * mang);
      B = (float)(B - 0.003 * mang);
      float magic = NBTHelper.GetNBTfloat(stack, "magic");
      Rs = (float)(Rs - 0.003 * magic);
      Bs = (float)(Bs - 0.02 * magic);
      float cu = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTCOPPER);
      Rs = (float)(Rs - 0.017 * cu);
      Bs = (float)(Bs - 0.004 * cu);
      float le = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTLEAD);
      Rs = (float)(Rs - 0.004 * le);
      Gs = (float)(Gs - 0.008 * le);
      Bs = (float)(Bs - 0.016 * le);
      float tin = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTTIN);
      R = (float)(R - 0.0055 * tin);
      G = (float)(G - 0.005 * tin);
      B = (float)(B - 0.0057 * tin);
      float gold = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTGOLD);
      R = (float)(R - 0.008 * gold);
      G = (float)(G - 0.008 * gold);
      B = (float)(B - 0.022 * gold);
      float pt = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTPLATINUM);
      Gs = (float)(Gs - 0.018 * pt);
      Bs = (float)(Bs - 0.009 * pt);
      float ni = NBTHelper.GetNBTfloat(stack, OreDicHelper.DUSTNICKEL);
      R = (float)(R - 0.0045 * ni);
      G = (float)(G - 0.002 * ni);
      B = (float)(B - 0.005 * ni);
      NBTHelper.SetNBTint(stack, ColorConverters.RGBtoDecimal(R, G, B), "color");
      NBTHelper.SetNBTint(stack, ColorConverters.RGBtoDecimal(Rs, Gs, Bs), "colorover");
   }

   public void updateGuiColors() {
      float R = 1.0F;
      float G = 1.0F;
      float B = 1.0F;
      float Rs = 1.0F;
      float Gs = 1.0F;
      float Bs = 1.0F;
      float alum = this.getMaterialCount(OreDicHelper.DUSTALUMINIUM);
      R = (float)(R - 9.0E-4 * alum);
      G = (float)(G - 9.0E-4 * alum);
      float ir = this.getMaterialCount(OreDicHelper.DUSTIRON);
      G = (float)(G - 8.0E-4 * ir);
      B = (float)(B - 0.0018 * ir);
      float cr = this.getMaterialCount(OreDicHelper.DUSTCHROMIUM);
      G = (float)(G - 0.0018 * cr);
      B = (float)(B - 0.0018 * cr);
      float be = this.getMaterialCount(OreDicHelper.DUSTBERYLLIUM);
      R = (float)(R - 0.0019 * be);
      G = (float)(G - 3.0E-4 * be);
      B = (float)(B - 0.0019 * be);
      float ti = this.getMaterialCount(OreDicHelper.DUSTTITANIUM);
      Rs = (float)(Rs - 0.0017 * ti);
      Gs = (float)(Gs - 0.0017 * ti);
      R = (float)(R - 7.0E-4 * ti);
      G = (float)(G - 7.0E-4 * ti);
      float sulf = this.getMaterialCount(OreDicHelper.DUSTSULFUR);
      R = (float)(R - 0.0023 * sulf);
      G = (float)(G - 0.0023 * sulf);
      B = (float)(B - 0.0023 * sulf);
      float mang = this.getMaterialCount(OreDicHelper.DUSTMANGANESE);
      R = (float)(R - 0.0011 * mang);
      G = (float)(G - 0.0022 * mang);
      B = (float)(B - 3.0E-4 * mang);
      float magic = this.getMaterialCount("magic");
      Rs = (float)(Rs - 3.0E-4 * magic);
      Bs = (float)(Bs - 0.002 * magic);
      float cu = this.getMaterialCount(OreDicHelper.DUSTCOPPER);
      Rs = (float)(Rs - 0.0017 * cu);
      Bs = (float)(Bs - 4.0E-4 * cu);
      float le = this.getMaterialCount(OreDicHelper.DUSTLEAD);
      Rs = (float)(Rs - 4.0E-4 * le);
      Gs = (float)(Gs - 8.0E-4 * le);
      Bs = (float)(Bs - 0.0016 * le);
      float tin = this.getMaterialCount(OreDicHelper.DUSTTIN);
      R = (float)(R - 5.5E-4 * tin);
      G = (float)(G - 5.0E-4 * tin);
      B = (float)(B - 5.7E-4 * tin);
      float gold = this.getMaterialCount(OreDicHelper.DUSTGOLD);
      R = (float)(R - 8.0E-4 * gold);
      G = (float)(G - 8.0E-4 * gold);
      B = (float)(B - 0.0022 * gold);
      float pt = this.getMaterialCount(OreDicHelper.DUSTPLATINUM);
      Gs = (float)(Gs - 0.0018 * pt);
      Bs = (float)(Bs - 9.0E-4 * pt);
      float ni = this.getMaterialCount(OreDicHelper.DUSTNICKEL);
      R = (float)(R - 4.5E-4 * ni);
      G = (float)(G - 2.0E-4 * ni);
      B = (float)(B - 5.0E-4 * ni);
      this.solutionPrimColor = ColorConverters.RGBtoDecimal(R, G, B);
      this.solutionSecnColor = ColorConverters.RGBtoDecimal(Rs, Gs, Bs);
   }

   public void update() {
      this.getManaBuffer().updateManaBuffer(this.world, this.pos);
      this.ticksExisted++;
      if (!this.world.isRemote) {
         if (this.getManaBuffer().getManaStored() > 2.0F && this.ticksExisted % 5 == 0 && this.redstone == 0) {
            int radX = rand.nextInt(this.radius * 2 + 1) - this.radius;
            int radY = rand.nextInt(this.radius * 2 + 1) - this.radius;
            int radZ = rand.nextInt(this.radius * 2 + 1) - this.radius;
            BlockPos randomPos = this.getPos().add(radX, radY, radZ);
            if (this.world.getBlockState(randomPos).getBlock() == BlocksRegister.FLUIDHYDROTHERMAL) {
               if (collidesWithSoftBlock(this.world, randomPos)) {
                  this.world.setBlockToAir(randomPos);
                  this.world.createExplosion(null, randomPos.getX(), randomPos.getY(), randomPos.getZ(), 2.7F, false);
                  this.world.setBlockState(randomPos, Blocks.WATER.getDefaultState());
                  this.getManaBuffer().addMana(-2.0F);
               } else {
                  AxisAlignedBB aabb = new AxisAlignedBB(randomPos);
                  List<EntityGeomanticCrystal> list = this.world.getEntitiesWithinAABB(EntityGeomanticCrystal.class, aabb);
                  int ii = Math.min(list.size(), 6);

                  for (int i = 0; i < ii; i++) {
                     EntityGeomanticCrystal randomEntity = list.get(rand.nextInt(list.size()));
                     if (this.growItemStack(randomEntity.stackIn)) {
                        this.calculateColor(randomEntity.stackIn);
                        randomEntity.sendUpdateColor(this.world);
                        this.getManaBuffer().addMana(-2.0F);
                        this.updateGuiColors();
                        Crystallizer.trySendPacketUpdate(this.world, this.getPos(), this, 8);
                        break;
                     }
                  }
               }
            }
         } else if (this.ticksExisted % 40 == 0) {
            int radX = rand.nextInt(this.radius * 2 + 1) - this.radius;
            int radY = rand.nextInt(this.radius * 2 + 1) - this.radius;
            int radZ = rand.nextInt(this.radius * 2 + 1) - this.radius;
            BlockPos randomPos = this.getPos().add(radX, radY, radZ);
            AxisAlignedBB aabb = new AxisAlignedBB(randomPos);
            List<EntityGeomanticCrystal> list = this.world.getEntitiesWithinAABB(EntityGeomanticCrystal.class, aabb);
            int ii = Math.min(list.size(), 4);

            for (int ix = 0; ix < ii; ix++) {
               EntityGeomanticCrystal randomEntity = list.get(rand.nextInt(list.size()));
               if (this.world.getBlockState(randomEntity.getPosition()).getBlock() == BlocksRegister.FLUIDHYDROTHERMAL) {
                  if (this.growItemStack(randomEntity.stackIn)) {
                     this.updateGuiColors();
                     Crystallizer.trySendPacketUpdate(this.world, this.getPos(), this, 8);
                  }

                  this.calculateColor(randomEntity.stackIn);
                  randomEntity.sendUpdateColor(this.world);
                  break;
               }
            }
         }

         if (this.ticksExisted % 60 == 0) {
            int disc = this.tryDissolveItem(this.getStackInSlot(0));
            if (disc > 0) {
               this.decrStackSize(0, disc);
               this.updateGuiColors();
               Crystallizer.trySendPacketUpdate(this.world, this.getPos(), this, 8);
            }
         }
      }
   }

   public static boolean collidesWithSoftBlock(World world, BlockPos pos) {
      return isNonGermeticBlock(world, 3.0F, pos.up())
         || isNonGermeticBlock(world, 3.0F, pos.down())
         || isNonGermeticBlock(world, 3.0F, pos.west())
         || isNonGermeticBlock(world, 3.0F, pos.south())
         || isNonGermeticBlock(world, 3.0F, pos.north())
         || isNonGermeticBlock(world, 3.0F, pos.east());
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
      Crystallizer.trySendPacketUpdate(this.world, this.getPos(), this, fluidTypeChanges ? 64 : 8);
   }

   public boolean isItemEquals(ItemStack stack, ItemStack other) {
      return !other.isEmpty() && stack.getItem() == other.getItem() && stack.getItemDamage() == other.getItemDamage();
   }

   public void read(NBTTagCompound compound) {
      this.containItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.containItemStacks);
      this.tank1.readFromNBT(compound, "tank1");
      if (compound.hasKey("redstone")) {
         this.redstone = compound.getBoolean("redstone") ? 1 : 0;
      }

      if (compound.hasKey("colorprim")) {
         this.solutionPrimColor = compound.getInteger("colorprim");
      }

      if (compound.hasKey("colorsecn")) {
         this.solutionSecnColor = compound.getInteger("colorsecn");
      }

      if (compound.hasKey("solutions")) {
         NBTTagCompound sol = compound.getCompoundTag("solutions");

         for (String nbtkey : sol.getKeySet()) {
            this.solution.put(nbtkey, sol.getFloat(nbtkey));
         }
      }

      super.readFromNBT(compound);
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.containItemStacks);
      this.tank1.writeToNBT(compound, "tank1");
      compound.setBoolean("redstone", this.redstone > 0);
      compound.setInteger("colorprim", this.solutionPrimColor);
      compound.setInteger("colorsecn", this.solutionSecnColor);
      NBTTagCompound sol = new NBTTagCompound();
      if (this.solution != null) {
         for (String name : this.solution.keySet()) {
            sol.setFloat(name, this.solution.get(name));
         }
      }

      compound.setTag("solutions", sol);
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
      return 1;
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
         Crystallizer.trySendPacketUpdate(this.world, this.getPos(), this, 8);
      }
   }

   public String getName() {
      return "tile_crystallizer";
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
      Crystallizer.trySendPacketUpdate(this.world, this.getPos(), this, 8);
   }

   public void closeInventory(EntityPlayer player) {
   }

   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return true;
   }

   public int[] getSlotsForFace(EnumFacing side) {
      return SLOT;
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
            return (int)(this.getManaBuffer().getManaStored() * 10.0F);
         case 1:
            return (int)this.getManaBuffer().getManaStorageSize();
         case 2:
            return 0;
         case 3:
            return this.fluidMax;
         case 4:
            return this.tank1.getFluidAmount();
         case 5:
            return (int)this.totalDissolved;
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 0:
         case 1:
         case 2:
         case 4:
         default:
            break;
         case 3:
            this.fluidMax = value;
            break;
         case 5:
            this.totalDissolved = value;
      }
   }

   public int getFieldCount() {
      return 6;
   }

   public void clear() {
      this.containItemStacks.clear();
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerCrystallizer(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.crystallizer";
   }

   public static boolean isNonGermeticBlock(World world, float hardnessBreaks, BlockPos pos) {
      IBlockState block = world.getBlockState(pos);
      if (block.getBlock() == BlocksRegister.FLUIDHYDROTHERMAL) {
         return false;
      } else if (block.getMaterial().isLiquid()) {
         return true;
      } else {
         float blockhard = block.getBlockHardness(world, pos);
         float blockres = block.getBlock().getExplosionResistance(null);
         return blockhard >= 0.0F
            && (
               Math.max(blockhard, blockres) <= hardnessBreaks
                  || block.getBlock().isReplaceable(world, pos)
                  || !Block.FULL_BLOCK_AABB.equals(block.getBoundingBox(world, pos))
            );
      }
   }

   @Override
   public ManaBuffer getManaBuffer() {
      return this.manaBuffer;
   }
}
