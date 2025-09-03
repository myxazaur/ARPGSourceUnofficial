//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.models.CalibrationCrystalModel;
import com.Vivern.Arpg.elements.models.MagicCandleModel;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Spell;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.tileentity.TileCalibrationBundle;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCalibrationThing extends Item {
   public static CalibrationCrystalModel calibrationCrystalModel = new CalibrationCrystalModel();
   public static MagicCandleModel magicCandleModel = new MagicCandleModel();
   public static ResourceLocation attracttex = new ResourceLocation("arpg:textures/callibration_crystal_attract_tex.png");
   public static ResourceLocation detracttex = new ResourceLocation("arpg:textures/callibration_crystal_detract_tex.png");
   public static ResourceLocation focustex = new ResourceLocation("arpg:textures/callibration_crystal_focus_tex.png");
   public static ResourceLocation rangetex = new ResourceLocation("arpg:textures/callibration_crystal_range_tex.png");
   public static ResourceLocation speedtex = new ResourceLocation("arpg:textures/callibration_crystal_speed_tex.png");
   public static ResourceLocation slowtex = new ResourceLocation("arpg:textures/callibration_crystal_slow_tex.png");
   public static ResourceLocation candletex = new ResourceLocation("arpg:textures/magic_candle_tex.png");
   public static ResourceLocation candle_black = new ResourceLocation("arpg:textures/magic_candle_black_tex.png");
   public static ResourceLocation candle_red = new ResourceLocation("arpg:textures/magic_candle_red_tex.png");
   public static ResourceLocation candle_green = new ResourceLocation("arpg:textures/magic_candle_green_tex.png");
   public static ResourceLocation candle_brown = new ResourceLocation("arpg:textures/magic_candle_brown_tex.png");
   public static ResourceLocation candle_blue = new ResourceLocation("arpg:textures/magic_candle_blue_tex.png");
   public static ResourceLocation candle_purple = new ResourceLocation("arpg:textures/magic_candle_purple_tex.png");
   public static ResourceLocation candle_cyan = new ResourceLocation("arpg:textures/magic_candle_cyan_tex.png");
   public static ResourceLocation candle_lightgray = new ResourceLocation("arpg:textures/magic_candle_lightgray_tex.png");
   public static ResourceLocation candle_gray = new ResourceLocation("arpg:textures/magic_candle_gray_tex.png");
   public static ResourceLocation candle_pink = new ResourceLocation("arpg:textures/magic_candle_pink_tex.png");
   public static ResourceLocation candle_lime = new ResourceLocation("arpg:textures/magic_candle_lime_tex.png");
   public static ResourceLocation candle_yellow = new ResourceLocation("arpg:textures/magic_candle_yellow_tex.png");
   public static ResourceLocation candle_lightblue = new ResourceLocation("arpg:textures/magic_candle_lightblue_tex.png");
   public static ResourceLocation candle_magenta = new ResourceLocation("arpg:textures/magic_candle_magenta_tex.png");
   public static ResourceLocation candle_orange = new ResourceLocation("arpg:textures/magic_candle_orange_tex.png");
   public static ResourceLocation candle_white = new ResourceLocation("arpg:textures/magic_candle_white_tex.png");
   public static ResourceLocation candle_light = new ResourceLocation("arpg:textures/candle_light.png");
   public static ResourceLocation candle_light_uncolored = new ResourceLocation("arpg:textures/candle_light_uncolored.png");
   public static ResourceLocation candle_light_void = new ResourceLocation("arpg:textures/candle_light_void.png");
   public float attraction;
   public float range;
   public float speed;
   public boolean canAcceptPassword;
   public AxisAlignedBB aabb;
   public ModelBase model;
   public ResourceLocation texture;
   public int modelOption;
   public int maximalRandomValue;
   public int randomDisplayType;
   public static Predicate<IBlockState> BUNDLES = new Predicate<IBlockState>() {
      public boolean apply(IBlockState input) {
         return input.getBlock() == BlocksRegister.BLOCKCALIBRATIONBUNDLE;
      }
   };

   public ItemCalibrationThing(
      String name, float attraction, float range, float speed, boolean canAcceptPassword, float hitboxPixelRadius, float hitboxPixelHeight
   ) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey(name);
      this.setMaxStackSize(64);
      this.attraction = attraction;
      this.range = range;
      this.speed = speed;
      this.canAcceptPassword = canAcceptPassword;
      this.aabb = new AxisAlignedBB(
         -hitboxPixelRadius * 0.0625, 0.0, -hitboxPixelRadius * 0.0625, hitboxPixelRadius * 0.0625, hitboxPixelHeight * 0.0625, hitboxPixelRadius * 0.0625
      );
   }

   public ItemCalibrationThing setRenders(int modelOption, ModelBase model, ResourceLocation texture) {
      this.modelOption = modelOption;
      this.model = model;
      this.texture = texture;
      return this;
   }

   public ItemCalibrationThing setRenders(int modelOption, ModelBase model, ResourceLocation texture, int maximalRandomValue, int randomDisplayTickType) {
      this.modelOption = modelOption;
      this.model = model;
      this.texture = texture;
      this.maximalRandomValue = maximalRandomValue;
      this.randomDisplayType = randomDisplayTickType;
      return this;
   }

   @SideOnly(Side.CLIENT)
   public void renderInTESR(@Nullable TileCalibrationBundle.CalibrationThing thing) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.0F, -1.0F, 0.0F);
      Minecraft.getMinecraft().renderEngine.bindTexture(this.texture);
      this.model.render(null, this.modelOption, thing == null ? 0.0F : thing.randomValue, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
   }

   public void randomDisplayTick(
      TileCalibrationBundle tile, TileCalibrationBundle.CalibrationThing thing, IBlockState stateIn, World world, BlockPos blockpos, Random rand
   ) {
      if (this.randomDisplayType == 1) {
         float candleHeight = 9.0F;
         int type = thing.randomValue % 5;
         if (type == 0) {
            candleHeight = 9.0F;
         }

         if (type == 1) {
            candleHeight = 8.0F;
         }

         if (type == 2) {
            candleHeight = 10.0F;
         }

         if (type == 3) {
            candleHeight = 7.0F;
         }

         if (type == 4) {
            candleHeight = 9.0F;
         }

         candleHeight = (candleHeight + 2.0F) * 0.0625F;
         if (thing.password != null && !thing.password.isEmpty()) {
            Vec3d col = thing.password.getColor();
            if (col != null) {
               Vec3d pos = new Vec3d(
                  blockpos.getX() + thing.posX / 16.0F, blockpos.getY() + candleHeight, blockpos.getZ() + thing.posZ / 16.0F
               );
               spawnCandleLightParticle(world, pos, col, thing.password.isVoid());
            }
         } else {
            Vec3d pos = new Vec3d(
               blockpos.getX() + thing.posX / 16.0F, blockpos.getY() + candleHeight, blockpos.getZ() + thing.posZ / 16.0F
            );
            spawnCandleLightParticle(world, pos, null, false);
         }
      }
   }

   public static void spawnCandleLightParticle(World world, Vec3d pos, @Nullable Vec3d col, boolean voidd) {
      if (col == null) {
         GUNParticle particle = new GUNParticle(
            candle_light, 0.125F, 0.0F, 30, 240, world, pos.x, pos.y, pos.z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, 0
         );
         particle.alphaGlowing = true;
         world.spawnEntity(particle);
      } else {
         GUNParticle particle = new GUNParticle(
            voidd ? candle_light_void : candle_light_uncolored,
            0.125F,
            0.0F,
            30,
            240,
            world,
            pos.x,
            pos.y,
            pos.z,
            0.0F,
            0.0F,
            0.0F,
            (float)col.x,
            (float)col.y,
            (float)col.z,
            true,
            0
         );
         particle.alphaGlowing = !voidd;
         world.spawnEntity(particle);
      }
   }

   public AxisAlignedBB boundingBox() {
      return this.aabb;
   }

   public TileCalibrationBundle.CalibrationThing createThing(ItemStack itemStack, int x, int z) {
      TileCalibrationBundle.CalibrationThing thing = new TileCalibrationBundle.CalibrationThing(x, z, this);
      Spell[] spells = NBTHelper.readSpellsFromNbt(itemStack);
      if (spells != null) {
         thing.password = new TileCalibrationBundle.SpellPassword(spells);
      }

      if (this.maximalRandomValue > 0) {
         thing.randomValue = (byte)itemRand.nextInt(this.maximalRandomValue + 1);
      }

      return thing;
   }

   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      if (this.attraction < 0.0F) {
         tooltip.add(TextFormatting.DARK_PURPLE + "Attraction " + this.attraction);
      }

      if (this.range < 0.0F) {
         tooltip.add(TextFormatting.DARK_PURPLE + "Range " + this.range);
      }

      if (this.speed < 0.0F) {
         tooltip.add(TextFormatting.DARK_PURPLE + "Flow speed " + this.speed);
      }

      if (this.attraction > 0.0F) {
         tooltip.add(TextFormatting.DARK_PURPLE + "Attraction +" + this.attraction);
      }

      if (this.range > 0.0F) {
         tooltip.add(TextFormatting.DARK_PURPLE + "Range +" + this.range);
      }

      if (this.speed > 0.0F) {
         tooltip.add(TextFormatting.DARK_PURPLE + "Flow speed +" + this.speed);
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
      RayTraceResult raytraceresult = GetMOP.rayTraceBlocks(worldIn, vec3d, vec3d2, BUNDLES, false, true, false);
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
            if (!worldIn.isRemote) {
               if (raytraceresult.sideHit == EnumFacing.UP) {
                  BlockPos posoff = blockpos.up();
                  tentity = worldIn.getTileEntity(posoff);
                  if (tentity != null && tentity instanceof TileCalibrationBundle) {
                     TileCalibrationBundle tileVial = (TileCalibrationBundle)tentity;
                     int posx = (int)((raytraceresult.hitVec.x + 0.03125 - blockpos.getX()) * 16.0);
                     int posz = (int)((raytraceresult.hitVec.z + 0.03125 - blockpos.getZ()) * 16.0);
                     tileVial.addNewThing(itemstack, this, posx, posz);
                     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
                  }
               }

               BlockPos posoff = blockpos.offset(raytraceresult.sideHit);
               if (BlocksRegister.BLOCKCALIBRATIONBUNDLE.canPlaceBlockAt(worldIn, posoff)) {
                  if (!worldIn.isBlockModifiable(playerIn, posoff)) {
                     return new ActionResult(EnumActionResult.FAIL, itemstack);
                  }

                  IBlockState iblockstate1 = BlocksRegister.BLOCKCALIBRATIONBUNDLE.getDefaultState();
                  if (!worldIn.setBlockState(posoff, iblockstate1, 11)) {
                     return new ActionResult(EnumActionResult.FAIL, itemstack);
                  }

                  BlocksRegister.BLOCKCALIBRATIONBUNDLE.onBlockPlacedBy(worldIn, posoff, iblockstate1, playerIn, itemstack);
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
                  if (tentity != null && tentity instanceof TileCalibrationBundle) {
                     TileCalibrationBundle tileVial = (TileCalibrationBundle)tentity;
                     int posx = (int)((raytraceresult.hitVec.x + 0.03125 - blockpos.getX()) * 16.0);
                     int posz = (int)((raytraceresult.hitVec.z + 0.03125 - blockpos.getZ()) * 16.0);
                     if (tileVial.addNewThing(itemstack, this, posx, posz)) {
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
