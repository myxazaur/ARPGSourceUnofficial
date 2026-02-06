package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.ARPGChestTESR;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public enum EnumChest {
   FROZEN(
      0,
      new ResourceLocation("arpg:textures/chest_frozen.png"),
      180,
      ARPGChestTESR.model,
      ARPGChestTESR.modelBig,
      SoundEvents.BLOCK_CHEST_OPEN,
      SoundEvents.BLOCK_CHEST_CLOSE
   ),
   TOXIC(
      1,
      new ResourceLocation("arpg:textures/chest_toxic.png"),
      200,
      ARPGChestTESR.model,
      ARPGChestTESR.modelBig,
      SoundEvents.BLOCK_CHEST_OPEN,
      SoundEvents.BLOCK_CHEST_CLOSE
   ),
   RUSTED(
      2,
      new ResourceLocation("arpg:textures/chest_rusted.png"),
      0,
      ARPGChestTESR.model,
      ARPGChestTESR.modelBig,
      Sounds.chest_open_metal,
      Sounds.chest_close_metal
   ),
   CRYSTAL(
      3,
      new ResourceLocation("arpg:textures/chest_crystal.png"),
      230,
      ARPGChestTESR.model,
      ARPGChestTESR.modelBig,
      Sounds.chest_open_stone,
      Sounds.chest_close_stone
   ),
   ROTTEN(
      4,
      new ResourceLocation("arpg:textures/chest_rotten.png"),
      0,
      ARPGChestTESR.model,
      ARPGChestTESR.modelBig,
      SoundEvents.BLOCK_CHEST_OPEN,
      SoundEvents.BLOCK_CHEST_CLOSE
   ),
   SUNKEN(
      5,
      new ResourceLocation("arpg:textures/chest_sunken.png"),
      210,
      ARPGChestTESR.model,
      ARPGChestTESR.modelBig,
      SoundEvents.BLOCK_CHEST_OPEN,
      SoundEvents.BLOCK_CHEST_CLOSE
   ),
   CORAL(
      6,
      new ResourceLocation("arpg:textures/chest_coral.png"),
      190,
      ARPGChestTESR.model,
      ARPGChestTESR.modelBig,
      Sounds.chest_open_stone,
      Sounds.chest_close_stone
   ),
   STORM(
      7,
      new ResourceLocation("arpg:textures/chest_storm.png"),
      240,
      ARPGChestTESR.modelStorm,
      ARPGChestTESR.modelBigStorm,
      Sounds.chest_open_plasma,
      Sounds.chest_close_plasma
   );

   @Deprecated
   public ResourceLocation normal;
   @Deprecated
   public ResourceLocation large;
   @Deprecated
   public boolean glow;
   public int id;
   public ModelBase model;
   public ModelBase modelLarge;
   public ResourceLocation texture;
   public int light;
   public SoundEvent soundOpen;
   public SoundEvent soundClose;

   private EnumChest(int id, ResourceLocation texture, int light, ModelBase model, ModelBase modelLarge, SoundEvent soundOpen, SoundEvent soundClose) {
      this.id = id;
      this.texture = texture;
      this.light = light;
      this.model = model;
      this.modelLarge = modelLarge;
      this.soundOpen = soundOpen;
      this.soundClose = soundClose;
   }

   public Block getBlock() {
      if (this == FROZEN) {
         return BlocksRegister.CHESTFROZEN;
      } else if (this == TOXIC) {
         return BlocksRegister.CHESTTOXIC;
      } else if (this == RUSTED) {
         return BlocksRegister.CHESTRUSTED;
      } else if (this == CRYSTAL) {
         return BlocksRegister.CHESTCRYSTAL;
      } else if (this == ROTTEN) {
         return BlocksRegister.CHESTROTTEN;
      } else if (this == SUNKEN) {
         return BlocksRegister.CHESTSUNKEN;
      } else if (this == CORAL) {
         return BlocksRegister.CHESTCORAL;
      } else {
         return (Block)(this == STORM ? BlocksRegister.CHESTSTORM : Blocks.CHEST);
      }
   }

   public static EnumChest byId(int id) {
      return values()[MathHelper.clamp(id, 0, values().length - 1)];
   }
}
