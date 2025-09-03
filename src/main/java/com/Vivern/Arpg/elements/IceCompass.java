//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.dimensions.generationutils.GenerationHelper;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.tileentity.ChestLock;
import com.Vivern.Arpg.tileentity.TileARPGChest;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class IceCompass extends Item {
   public static BlockPos posCastle;
   public static float arrowRotation;
   public static float prevArrowRotation;
   public static float rotationTarget;
   public static float rotationSpeed;

   public IceCompass() {
      this.setRegistryName("ice_compass");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("ice_compass");
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         BlockPos pos = raytraceresult.getBlockPos();
         TileEntity tile = world.getTileEntity(pos);
         if (tile != null && tile instanceof TileARPGChest) {
            TileARPGChest chest = (TileARPGChest)tile;
            if (chest.isLockedWith(ChestLock.WINTER_CURSE)) {
               world.playSound(null, pos, Sounds.ice_compass_unlock, SoundCategory.BLOCKS, 0.5F, 0.9F + itemRand.nextFloat() / 5.0F);
               if (world.isRemote) {
                  for (EnumFacing face : EnumFacing.HORIZONTALS) {
                     double x = pos.getX() + 0.5 + face.getXOffset() * 0.5;
                     double y = pos.getY() + 0.5;
                     double z = pos.getZ() + 0.5 + face.getZOffset() * 0.5;
                     float speed = 0.14F;
                     GUNParticle bigsmoke = new GUNParticle(
                        ChestLock.lock_winter,
                        0.62F,
                        0.0F,
                        30,
                        240,
                        world,
                        x,
                        y,
                        z,
                        face.getXOffset() * speed,
                        0.0F,
                        face.getZOffset() * speed,
                        1.0F,
                        1.0F,
                        1.0F,
                        true,
                        1
                     );
                     bigsmoke.alphaGlowing = true;
                     bigsmoke.alphaTickAdding = -0.03333333F;
                     bigsmoke.snapToFace(face);
                     world.spawnEntity(bigsmoke);
                  }

                  double x = pos.getX() + 0.5;
                  double y = pos.getY() + 0.5;
                  double z = pos.getZ() + 0.5;

                  for (int i = 0; i < 8; i++) {
                     ShardType.COLD
                        .spawnNativeParticle(
                           world,
                           1.35F,
                           x,
                           y,
                           z,
                           (itemRand.nextFloat() - 0.5F) * 0.25F,
                           itemRand.nextFloat() * 0.2F,
                           (itemRand.nextFloat() - 0.5F) * 0.25F,
                           true
                        );
                  }
               }

               if (!world.isRemote) {
                  chest.lockOrUnlockWith(ChestLock.WINTER_CURSE, false);
                  PacketHandler.trySendPacketUpdate(world, pos, chest, 64.0);
               }
            }

            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
         } else {
            return new ActionResult(EnumActionResult.PASS, itemstack);
         }
      }
   }

   public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote && isSelected && entityIn.ticksExisted % 32 == 0) {
         NBTHelper.GiveNBTlong(stack, world.getSeed(), "worldseed");
         NBTHelper.SetNBTlong(stack, world.getSeed(), "worldseed");
      }

      if (world.isRemote && Minecraft.getMinecraft().player == entityIn) {
         if (isSelected) {
            if (entityIn.world.provider.getDimension() == 100) {
               long seed = NBTHelper.GetNBTlong(stack, "worldseed");
               int scanRange = 60;
               int halfscanRange = scanRange / 2;
               int xx = entityIn.ticksExisted % scanRange - halfscanRange;
               new ChunkPos(entityIn.getPosition());

               for (int zz = -halfscanRange; zz <= halfscanRange; zz++) {
                  int x = xx + ((int)entityIn.posX >> 4);
                  int z = zz + ((int)entityIn.posZ >> 4);
                  float dungeonValue = GenerationHelper.getDungeonValue(x, z, seed, 7);
                  if (dungeonValue >= 0.0F && dungeonValue < 0.1F) {
                     BlockPos newPos = new BlockPos(x << 4, 0, z << 4);
                     if (posCastle == null || entityIn.getDistanceSq(newPos) < entityIn.getDistanceSq(posCastle)) {
                        posCastle = newPos;
                     }
                  }
               }
            } else {
               posCastle = null;
            }
         }

         if (posCastle != null) {
            Vec3d direction = new Vec3d(posCastle.getX() - entityIn.posX, 0.0, posCastle.getZ() - entityIn.posZ);
            Vec3d pw = GetMOP.Vec3dToPitchYaw(direction);
            rotationTarget = (float)pw.y - entityIn.rotationYaw + 180.0F;
         } else if (entityIn.ticksExisted % 30 == 0) {
            rotationTarget = itemRand.nextInt(360);
         }

         prevArrowRotation = arrowRotation;
         float between = GetMOP.getDirectionBetweenAngles(arrowRotation, rotationTarget);
         rotationSpeed *= 0.92F;
         rotationSpeed += between * 0.013F;
         arrowRotation = arrowRotation + rotationSpeed;
      }
   }
}
