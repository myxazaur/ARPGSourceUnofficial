package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.recipes.EnergyCost;
import com.Vivern.Arpg.tileentity.IVialElementsAccepter;
import com.Vivern.Arpg.tileentity.TileSplitter;
import com.Vivern.Arpg.tileentity.TileVial;
import com.google.common.base.Predicate;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class ItemVial extends Item {
   public ShardType shardType;
   public static Predicate<IBlockState> VIALS = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.getBlock() == BlocksRegister.BLOCKVIAL;
      }
   };

   public ItemVial(int shard) {
      if (shard == -1) {
         this.setRegistryName("vial_empty");
         this.setCreativeTab(CreativeTabs.TOOLS);
         this.setTranslationKey("vial_empty");
         this.setMaxStackSize(64);
      } else if (shard > 0) {
         this.shardType = ShardType.byId(shard);
         String name = "vial_" + this.shardType.name;
         this.setRegistryName(name);
         this.setTranslationKey(name);
      } else {
         this.setRegistryName("vial_filled");
         this.setCreativeTab(CreativeTabs.TOOLS);
         this.setTranslationKey("vial_filled");
         this.setMaxStackSize(64);
         this.setHasSubtypes(true);
      }
   }

   public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
      if (this.getHasSubtypes() && this.isInCreativeTab(tab)) {
         for (int i = 1; i <= 12; i++) {
            items.add(new ItemStack(this, 1, i));
         }
      }

      if (this == ItemsRegister.VIALEMPTY) {
         super.getSubItems(tab, items);
      }
   }

   public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      Vec3d vec3d = playerIn.getPositionEyes(1.0F);
      Vec3d vec3d1 = playerIn.getLook(1.0F);
      double blockReachDistance = playerIn.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
      Vec3d vec3d2 = vec3d.add(
         vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
      );
      RayTraceResult raytraceresult = GetMOP.rayTraceBlocks(worldIn, vec3d, vec3d2, VIALS, false, true, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         if (raytraceresult.typeOfHit == Type.BLOCK) {
            BlockPos blockpos = raytraceresult.getBlockPos();
            if (!worldIn.isBlockModifiable(playerIn, blockpos)
               || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)) {
               return new ActionResult(EnumActionResult.PASS, itemstack);
            }

            TileEntity tentity = worldIn.getTileEntity(blockpos);
            if (tentity != null && tentity instanceof IVialElementsAccepter) {
               IVialElementsAccepter accepter = (IVialElementsAccepter)tentity;
               if (this == ItemsRegister.VIALEMPTY) {
                  EnergyCost getted = accepter.provideVialElements(itemstack, 4.0F, true);
                  if (getted != null && getted.amount == 4.0F) {
                     if (!worldIn.isRemote) {
                        Weapons.addItemStackToInventory(playerIn, new ItemStack(ItemsRegister.VIAL, 1, getted.type.id));
                        itemstack.shrink(1);
                        Weapons.setPlayerAnimationOnServer(playerIn, 14, handIn);
                        worldIn.playSound(
                           (EntityPlayer)null,
                           playerIn.posX,
                           playerIn.posY,
                           playerIn.posZ,
                           SoundEvents.ITEM_BOTTLE_FILL,
                           SoundCategory.PLAYERS,
                           0.6F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        playerIn.getCooldownTracker().setCooldown(ItemsRegister.VIAL, 6);
                     } else {
                        Vec3d vec1 = new Vec3d(blockpos.getX() + 0.5, blockpos.getY() + 0.375, blockpos.getZ() + 0.5);
                        Vec3d vec2 = playerIn.getPositionEyes(1.0F)
                           .subtract(vec1)
                           .add(itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
                        TileSplitter.spawnTentacleParticle(worldIn, vec1, vec2.normalize(), playerIn, getted.type, 2.0F, false);
                     }

                     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                  }
               } else if (accepter.acceptVialElements(itemstack, ShardType.byId(itemstack.getMetadata()), 4.0F) < 4.0F) {
                  if (!worldIn.isRemote) {
                     Weapons.addItemStackToInventory(playerIn, new ItemStack(ItemsRegister.VIALEMPTY));
                     itemstack.shrink(1);
                     Weapons.setPlayerAnimationOnServer(playerIn, 14, handIn);
                     worldIn.playSound(
                        (EntityPlayer)null,
                        playerIn.posX,
                        playerIn.posY,
                        playerIn.posZ,
                        Sounds.vial_use,
                        SoundCategory.PLAYERS,
                        0.6F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                     playerIn.getCooldownTracker().setCooldown(ItemsRegister.VIAL, 6);
                  } else {
                     ShardType shardType = ShardType.byId(itemstack.getMetadata());
                     if (shardType != null) {
                        Vec3d yawTov3d = GetMOP.YawToVec3d(playerIn.rotationYaw + 90.0F);
                        Vec3d look = GetMOP.PitchYawToVec3d(playerIn.rotationPitch - 7.0F, playerIn.rotationYaw).scale(0.3);
                        Vec3d partpos = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight() - 0.2, playerIn.posZ)
                           .add(yawTov3d.scale(0.2))
                           .add(look);

                        for (int i = 0; i < 4; i++) {
                           shardType.spawnNativeParticle(
                              worldIn,
                              1.0F,
                              partpos.x,
                              partpos.y,
                              partpos.z,
                              look.x + (itemRand.nextFloat() - 0.5) * 0.08,
                              look.y + (itemRand.nextFloat() - 0.2) * 0.08,
                              look.z + (itemRand.nextFloat() - 0.5) * 0.08,
                              true
                           );
                        }
                     }
                  }

                  return new ActionResult(EnumActionResult.SUCCESS, itemstack);
               }
            }

            if (!worldIn.isRemote) {
               if (raytraceresult.sideHit == EnumFacing.UP) {
                  BlockPos posoff = blockpos.up();
                  tentity = worldIn.getTileEntity(posoff);
                  if (tentity != null && tentity instanceof TileVial) {
                     TileVial tileVial = (TileVial)tentity;
                     int posx = (int)((raytraceresult.hitVec.x + 0.03125 - blockpos.getX()) * 16.0);
                     int posz = (int)((raytraceresult.hitVec.z + 0.03125 - blockpos.getZ()) * 16.0);
                     tileVial.addNewVialItem(itemstack, posx, posz, playerIn.getHorizontalFacing().getAxis() == Axis.X);
                     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                  }
               }

               BlockPos posoff = blockpos.offset(raytraceresult.sideHit);
               if (BlocksRegister.BLOCKVIAL.canPlaceBlockAt(worldIn, posoff)) {
                  if (!worldIn.isBlockModifiable(playerIn, posoff)) {
                     return new ActionResult(EnumActionResult.FAIL, itemstack);
                  }

                  IBlockState iblockstate1 = BlocksRegister.BLOCKVIAL.getDefaultState();
                  if (!worldIn.setBlockState(posoff, iblockstate1, 11)) {
                     return new ActionResult(EnumActionResult.FAIL, itemstack);
                  }

                  BlocksRegister.BLOCKVIAL.onBlockPlacedBy(worldIn, posoff, iblockstate1, playerIn, itemstack);
                  if (playerIn instanceof EntityPlayerMP) {
                     CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)playerIn, posoff, itemstack);
                  }

                  SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, posoff, playerIn);
                  worldIn.playSound(
                     playerIn,
                     posoff,
                     soundtype.getPlaceSound(),
                     SoundCategory.BLOCKS,
                     (soundtype.getVolume() + 1.0F) / 2.0F,
                     soundtype.getPitch() * 0.8F
                  );
                  tentity = worldIn.getTileEntity(posoff);
                  if (tentity != null && tentity instanceof TileVial) {
                     TileVial tileVial = (TileVial)tentity;
                     int posx = (int)((raytraceresult.hitVec.x + 0.03125 - blockpos.getX()) * 16.0);
                     int posz = (int)((raytraceresult.hitVec.z + 0.03125 - blockpos.getZ()) * 16.0);
                     if (tileVial.addNewVialItem(itemstack, posx, posz, playerIn.getHorizontalFacing().getAxis() == Axis.X)) {
                        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                     }

                     worldIn.setBlockToAir(posoff);
                     return new ActionResult(EnumActionResult.FAIL, itemstack);
                  }

                  worldIn.setBlockToAir(posoff);
                  return new ActionResult(EnumActionResult.FAIL, itemstack);
               }
            }
         }

         return new ActionResult(EnumActionResult.PASS, itemstack);
      }
   }
}
