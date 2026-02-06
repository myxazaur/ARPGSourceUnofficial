package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Weapons;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fluids.capability.wrappers.BlockWrapper;
import net.minecraftforge.fluids.capability.wrappers.FluidBlockWrapper;
import net.minecraftforge.items.ItemHandlerHelper;

public class Canister extends ItemFluidContainer {
   public Canister() {
      super(1000);
      this.setRegistryName("canister");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("canister");
      this.setMaxStackSize(1);
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public float getDestroySpeed(ItemStack stack, IBlockState state) {
      return 0.0F;
   }

   public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
      return super.doesSneakBypassUse(stack, world, pos, player);
   }

   private static IFluidHandler getFluidBlockHandler(Fluid fluid, World world, BlockPos pos) {
      Block block = fluid.getBlock();
      if (block instanceof IFluidBlock) {
         return new FluidBlockWrapper((IFluidBlock)block, world, pos);
      } else {
         return (IFluidHandler)(block instanceof BlockLiquid ? new BlockLiquidWrapper((BlockLiquid)block, world, pos) : new BlockWrapper(block, world, pos));
      }
   }

   public void placeLiquid(World world, ItemStack itemstack, BlockPos pos, EntityPlayer player, FluidStack fluidStack) {
      FluidActionResult result = FluidUtil.tryPlaceFluid(player, world, pos, itemstack, fluidStack);
      SoundEvent soundevent = fluidStack.getFluid().getEmptySound(fluidStack);
      world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote && entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
         if (this.tryFillFuelTool(itemstack, world, player, click2)) {
            player.getCooldownTracker().setCooldown(this, 5);
            return;
         }

         if (player.getHeldItemMainhand() == itemstack && (click || click2) && !player.getCooldownTracker().hasCooldown(this)) {
            boolean worldMode = !player.isSneaking();
            RayTraceResult result = GetMOP.rayTrace(world, player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue(), player, false);
            if (worldMode && result != null && result.hitVec != null) {
               RayTraceResult resultLiquids = GetMOP.rayTraceLiquids(world, player.getPositionEyes(1.0F), result.hitVec);
               if (resultLiquids != null) {
                  result = resultLiquids;
               }
            }

            if (result != null && result.getBlockPos() != null && result.sideHit != null) {
               boolean isreplaceable = world.getBlockState(result.getBlockPos()).getBlock().isReplaceable(world, result.getBlockPos());
               BlockPos blockpos1 = (!isreplaceable || result.sideHit != EnumFacing.UP) && worldMode
                  ? result.getBlockPos().offset(result.sideHit)
                  : result.getBlockPos();
               if (world.isBlockModifiable(player, blockpos1) && player.canPlayerEdit(blockpos1, result.sideHit, itemstack)) {
                  if (worldMode) {
                     if (click) {
                        FluidStack fluidStack = FluidUtil.getFluidContained(itemstack);
                        if (fluidStack != null && fluidStack.amount > 0) {
                           this.placeLiquid(world, itemstack, blockpos1, player, fluidStack);
                           itemstack.getTagCompound().removeTag("Fluid");
                        }
                     } else if (click2) {
                        FluidStack fluidStack = FluidUtil.getFluidContained(itemstack);
                        if (fluidStack == null || fluidStack.amount == 0) {
                           FluidActionResult filledResult = FluidUtil.tryPickUpFluid(itemstack, player, world, blockpos1, result.sideHit);
                           if (filledResult != null && filledResult.result.getTagCompound() != null) {
                              itemstack.setTagCompound(filledResult.result.getTagCompound().copy());
                           }
                        }
                     }
                  } else if (click) {
                     this.onUseOnTile(player, itemstack, world, blockpos1, result.sideHit, true);
                  } else if (click2) {
                     this.onUseOnTile(player, itemstack, world, blockpos1, result.sideHit, false);
                  }

                  player.getCooldownTracker().setCooldown(this, 5);
               }
            }
         }
      }
   }

   public boolean tryFillFuelTool(ItemStack itemstack, World world, EntityPlayer player, boolean click2) {
      if (player.getHeldItemOffhand() == itemstack && click2 && !player.getCooldownTracker().hasCooldown(this)) {
         ItemStack toolstack = player.getHeldItemMainhand();
         if (toolstack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem capFluidHandlerTool = (IFluidHandlerItem)toolstack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            FluidStack fluidStack = FluidUtil.getFluidContained(itemstack);
            if (fluidStack != null && fluidStack.getFluid() != null && fluidStack.amount > 0) {
               int MBfilled = capFluidHandlerTool.fill(fluidStack, true);
               fluidStack.amount -= MBfilled;
               if (fluidStack.amount > 0) {
                  fluidStack.writeToNBT(itemstack.getTagCompound().getCompoundTag("Fluid"));
               } else {
                  itemstack.getTagCompound().removeTag("Fluid");
               }

               SoundEvent soundevent = fluidStack.getFluid().getEmptySound(fluidStack);
               world.playSound(
                  (EntityPlayer)null, player.posX, player.posY, player.posZ, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F
               );
               Weapons.setPlayerAnimationOnServer(player, 1, EnumHand.OFF_HAND);
               return true;
            }
         }
      }

      return false;
   }

   public void onUseOnTile(EntityPlayer player, ItemStack itemstack, World world, BlockPos pos, EnumFacing facing, boolean fillTile) {
      TileEntity tileEntity = world.getTileEntity(pos);
      if (tileEntity != null && tileEntity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
         IFluidHandler handler = (IFluidHandler)tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
         if (handler == null) {
            return;
         }

         ItemStack containerCopy = ItemHandlerHelper.copyStackWithSize(itemstack, 1);
         IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(containerCopy);
         FluidStack fluidInCanister = FluidUtil.getFluidContained(itemstack);
         int fluidInCanisteramount = fluidInCanister == null ? 0 : fluidInCanister.amount;
         if (fillTile) {
            if (itemstack.hasTagCompound() && fluidInCanister != null) {
               int filled = handler.fill(fluidInCanister, true);
               if (filled == fluidInCanisteramount) {
                  itemstack.getTagCompound().removeTag("Fluid");
               } else if (filled != 0) {
                  FluidStack newFluid = fluidInCanister.copy();
                  newFluid.amount = fluidInCanisteramount - filled;
                  newFluid.writeToNBT(itemstack.getTagCompound().getCompoundTag("Fluid"));
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     fluidInCanister.getFluid().getEmptySound(fluidInCanister),
                     SoundCategory.PLAYERS,
                     0.8F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
               }
            }
         } else {
            int amount = 1000 - fluidInCanisteramount;
            if (amount > 0) {
               FluidStack fluidStack = handler.drain(amount, false);
               if (fluidStack != null && (fluidInCanister == null || fluidInCanisteramount == 0 || fluidStack.getFluid() == fluidInCanister.getFluid())) {
                  fluidStack = handler.drain(amount, true);
                  if (fluidStack != null && fluidStack.amount > 0) {
                     fluidStack.amount += fluidInCanisteramount;
                     NBTTagCompound fluidTAG = new NBTTagCompound();
                     fluidStack.writeToNBT(fluidTAG);
                     if (!itemstack.hasTagCompound()) {
                        itemstack.setTagCompound(new NBTTagCompound());
                     }

                     itemstack.getTagCompound().setTag("Fluid", fluidTAG);
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        fluidStack.getFluid().getFillSound(fluidStack),
                        SoundCategory.PLAYERS,
                        0.8F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  }
               }
            }
         }
      }
   }

   public void addInformation(ItemStack itemstack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      super.addInformation(itemstack, worldIn, tooltip, flagIn);
      if (itemstack.hasTagCompound()) {
         FluidStack fluidInCanister = FluidUtil.getFluidContained(itemstack);
         if (fluidInCanister != null) {
            tooltip.add(fluidInCanister.amount + "mb " + fluidInCanister.getFluid().getLocalizedName(fluidInCanister));
         }
      }
   }
}
