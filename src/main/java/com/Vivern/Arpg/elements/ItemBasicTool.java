//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBasicTool extends ItemItem {
   public boolean isRepairable = false;
   public Item itemRepair = null;
   public boolean damages = true;
   public boolean axe = false;
   public boolean pickaxe = false;
   public boolean shovel = false;
   public boolean shears = false;
   public int axeLVL = -1;
   public int pickaxeLVL = -1;
   public int shovelLVL = -1;
   public int shearsLVL = -1;
   public float axeSPEED = 0.0F;
   public float pickaxeSPEED = 0.0F;
   public float shovelSPEED = 0.0F;
   public float shearsSPEED = 0.0F;
   public int enchantability = 5;

   public ItemBasicTool(String name, CreativeTabs tab, int maxdamage, int maxstacksize, boolean isRepairable) {
      super(name, tab, maxdamage, maxstacksize);
      this.isRepairable = isRepairable;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type == EnumEnchantmentType.DIGGER
         || enchantment.type == EnumEnchantmentType.BREAKABLE
         || enchantment.type == EnumEnchantmentType.ALL;
   }

   public ItemBasicTool setHarvestLvl(String toolClass, int level, float speed) {
      if (toolClass.equals("axe")) {
         this.axeLVL = level;
         this.axeSPEED = speed;
      }

      if (toolClass.equals("pickaxe")) {
         this.pickaxeLVL = level;
         this.pickaxeSPEED = speed;
      }

      if (toolClass.equals("shovel")) {
         this.shovelLVL = level;
         this.shovelSPEED = speed;
      }

      if (toolClass.equals("shears")) {
         this.shearsLVL = level;
         this.shearsSPEED = speed;
      }

      this.setHarvestLevel(toolClass, level);
      return this;
   }

   public ItemBasicTool setToolDamages(boolean damages) {
      this.damages = damages;
      return this;
   }

   public ItemBasicTool setToolEnchantability(int enchantability) {
      this.enchantability = enchantability;
      return this;
   }

   public ItemBasicTool setupTool(boolean axe, boolean pickaxe, boolean shovel, boolean shears) {
      this.axe = axe;
      this.pickaxe = pickaxe;
      this.shovel = shovel;
      this.shears = shears;
      return this;
   }

   public ItemBasicTool setupToolSpeed(float axe, float pickaxe, float shovel, float shears) {
      this.axeSPEED = axe;
      this.pickaxeSPEED = pickaxe;
      this.shovelSPEED = shovel;
      this.shearsSPEED = shears;
      return this;
   }

   public ItemBasicTool setupToolLVL(int axe, int pickaxe, int shovel, int shears) {
      this.axeLVL = axe;
      this.pickaxeLVL = pickaxe;
      this.shovelLVL = shovel;
      this.shearsLVL = shears;
      return this;
   }

   public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
      return this.gethLevel(toolClass);
   }

   public int gethLevel(String toolClass) {
      if ("axe".equals(toolClass)) {
         return this.axeLVL;
      } else if ("pickaxe".equals(toolClass)) {
         return this.pickaxeLVL;
      } else if ("shovel".equals(toolClass)) {
         return this.shovelLVL;
      } else {
         return "shears".equals(toolClass) ? this.shearsLVL : -1;
      }
   }

   public float getspeed(String toolClass) {
      if ("axe".equals(toolClass)) {
         return this.axeSPEED;
      } else if ("pickaxe".equals(toolClass)) {
         return this.pickaxeSPEED;
      } else if ("shovel".equals(toolClass)) {
         return this.shovelSPEED;
      } else {
         return "shears".equals(toolClass) ? this.shearsSPEED : 0.0F;
      }
   }

   public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
      return this.canHarvestBlock(state);
   }

   public boolean canHarvestBlock(IBlockState state) {
      int lvl = state.getBlock().getHarvestLevel(state);
      String tool = state.getBlock().getHarvestTool(state);
      if (tool == null || tool.isEmpty()) {
         return true;
      } else {
         return lvl < 1 ? true : this.gethLevel(tool) >= lvl;
      }
   }

   public static String getToolForMaterial(Material material) {
      if (material == Material.WOOD
         || material == Material.CACTUS
         || material == Material.GOURD
         || material == Material.LEAVES
         || material == Material.PLANTS
         || material == Material.VINE) {
         return "axe";
      } else if (material == Material.CARPET
         || material == Material.CLOTH
         || material == Material.LEAVES
         || material == Material.PLANTS
         || material == Material.VINE
         || material == Material.SPONGE
         || material == Material.WEB) {
         return "shears";
      } else {
         return material != Material.CLAY
               && material != Material.CRAFTED_SNOW
               && material != Material.GRASS
               && material != Material.GROUND
               && material != Material.SAND
               && material != Material.SNOW
            ? "pickaxe"
            : "shovel";
      }
   }

   public float getDestroySpeed(ItemStack stack, IBlockState state) {
      int lvl = state.getBlock().getHarvestLevel(state);
      String tool = state.getBlock().getHarvestTool(state);
      if (tool == null || tool.isEmpty()) {
         tool = getToolForMaterial(state.getMaterial());
      }

      float maxspeed = 1.0F;
      if (this.gethLevel(tool) >= lvl) {
         maxspeed = Math.max(1.0F, this.getspeed(tool));
      }

      return maxspeed;
   }

   public boolean isRepairable() {
      return this.isRepairable;
   }

   public ItemBasicTool setItemToRepair(Item item) {
      this.itemRepair = item;
      return this;
   }

   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
      if (!this.isRepairable) {
         return false;
      } else if (toRepair.getItem() == repair.getItem()) {
         return true;
      } else {
         return this.itemRepair == null ? false : repair.getItem() == this.itemRepair;
      }
   }

   public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
      if (this.damages) {
         stack.damageItem(1, entityLiving);
      }

      return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
   }

   public int getItemEnchantability() {
      return this.enchantability;
   }
}
