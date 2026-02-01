package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityArrowBengal;
import com.vivern.arpg.entity.EntityArrowBouncing;
import com.vivern.arpg.entity.EntityArrowFirejet;
import com.vivern.arpg.entity.EntityArrowFish;
import com.vivern.arpg.entity.EntityArrowFrozen;
import com.vivern.arpg.entity.EntityArrowMithril;
import com.vivern.arpg.entity.EntityArrowModern;
import com.vivern.arpg.entity.EntityArrowShell;
import com.vivern.arpg.entity.EntityArrowTwin;
import com.vivern.arpg.entity.EntityArrowVicious;
import com.vivern.arpg.entity.EntityArrowVoid;
import com.vivern.arpg.entity.EntityArrowWind;
import com.vivern.arpg.main.Ln;
import com.vivern.arpg.main.WeaponParameters;
import java.util.List;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class Arrows {
   public static IBehaviorDispenseItem ARROWS_DISPENSE = new BehaviorArrowsDispense();

   public abstract static class AbstractItemArrow extends ItemArrow {
      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
         tooltip.add(TextFormatting.GRAY + Ln.translate("damage") + ": " + parameters.get("damage"));
         String name = this.getRegistryName().getPath();
         tooltip.add(TextFormatting.WHITE + Ln.translate("description." + name));
      }
   }

   public static class ArrowBengal extends AbstractItemArrow {
      public ArrowBengal() {
         this.setRegistryName("bengal_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("bengal_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowBengal entityarrow = new EntityArrowBengal(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowBouncing extends AbstractItemArrow {
      public ArrowBouncing() {
         this.setRegistryName("bouncing_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("bouncing_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowBouncing entityarrow = new EntityArrowBouncing(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowFirejet extends AbstractItemArrow {
      public ArrowFirejet() {
         this.setRegistryName("firejet_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("firejet_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowFirejet entityarrow = new EntityArrowFirejet(worldIn, shooter);
         entityarrow.itemArrow = this;
         entityarrow.setFire(150);
         entityarrow.firee = 15.0F;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowFish extends AbstractItemArrow {
      public ArrowFish() {
         this.setRegistryName("arrowfish");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("arrowfish");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowFish entityarrow = new EntityArrowFish(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowFrozen extends AbstractItemArrow {
      public ArrowFrozen() {
         this.setRegistryName("frozen_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("frozen_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowFrozen entityarrow = new EntityArrowFrozen(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }

      @Override
      public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
         tooltip.add("пїЅ7" + Ln.translate("damage") + ": " + parameters.get("damage"));
         String name = this.getRegistryName().getPath();
         tooltip.add("пїЅf" + Ln.translate("description." + name));
      }
   }

   public static class ArrowMithril extends AbstractItemArrow {
      public ArrowMithril() {
         this.setRegistryName("mithril_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("mithril_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowMithril entityarrow = new EntityArrowMithril(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowModern extends AbstractItemArrow {
      public ArrowModern() {
         this.setRegistryName("modern_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("modern_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowModern entityarrow = new EntityArrowModern(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowShell extends AbstractItemArrow {
      public ArrowShell() {
         this.setRegistryName("shell_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("shell_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowShell entityarrow = new EntityArrowShell(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowTwin extends AbstractItemArrow {
      public ArrowTwin() {
         this.setRegistryName("twin_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("twin_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowTwin entityarrow = new EntityArrowTwin(worldIn, shooter);
         entityarrow.createTwin = true;
         entityarrow.isMain = true;
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowVicious extends AbstractItemArrow {
      public ArrowVicious() {
         this.setRegistryName("vicious_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("vicious_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowVicious entityarrow = new EntityArrowVicious(worldIn, shooter);
         entityarrow.itemArrow = this;
         entityarrow.hasMoreArrows = true;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowVoid extends AbstractItemArrow {
      public ArrowVoid() {
         this.setRegistryName("void_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("void_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, new BehaviorArrowsDispense(0.0F));
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowVoid entityarrow = new EntityArrowVoid(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class ArrowWind extends AbstractItemArrow {
      public ArrowWind() {
         this.setRegistryName("wind_arrow");
         this.setCreativeTab(CreativeTabs.COMBAT);
         this.setTranslationKey("wind_arrow");
         BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, Arrows.ARROWS_DISPENSE);
      }

      public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
         EntityArrowWind entityarrow = new EntityArrowWind(worldIn, shooter);
         entityarrow.itemArrow = this;
         return entityarrow;
      }

      public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player) {
         return false;
      }
   }

   public static class BehaviorArrowsDispense extends BehaviorDefaultDispenseItem {
      public float upwardMotion;

      public BehaviorArrowsDispense() {
         this(0.1F);
      }

      public BehaviorArrowsDispense(float upwardMotion) {
         this.upwardMotion = upwardMotion;
      }

      public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
         if (stack.getItem() instanceof ItemArrow) {
            World world = source.getWorld();
            IPosition position = BlockDispenser.getDispensePosition(source);
            EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
            EntityArrow projectile = ((ItemArrow)stack.getItem()).createArrow(world, stack, null);
            projectile.setPosition(position.getX(), position.getY(), position.getZ());
            projectile.shoot(
               enumfacing.getXOffset(),
               enumfacing.getYOffset() + this.upwardMotion,
               enumfacing.getZOffset(),
               this.getProjectileVelocity(),
               this.getProjectileInaccuracy()
            );
            projectile.pickupStatus = PickupStatus.ALLOWED;
            world.spawnEntity(projectile);
            stack.shrink(1);
         }

         return stack;
      }

      protected void playDispenseSound(IBlockSource source) {
         source.getWorld().playEvent(1002, source.getBlockPos(), 0);
      }

      protected float getProjectileInaccuracy() {
         return 6.0F;
      }

      protected float getProjectileVelocity() {
         return 1.1F;
      }
   }
}
