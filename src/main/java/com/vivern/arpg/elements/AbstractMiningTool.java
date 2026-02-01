package com.vivern.arpg.elements;

import com.vivern.arpg.elements.animation.EnumFlick;
import com.vivern.arpg.elements.animation.Flicks;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Ln;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractMiningTool extends ItemWeapon {
   public MiningTools toolsSet;
   public int electricNeed;
   public float manaNeed;
   public Item itemNeed;
   public int chargesPerItem;
   public int modesCount;

   public AbstractMiningTool(String name, int maxDamage, MiningTools toolsSet) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey(name);
      this.setMaxDamage(maxDamage);
      this.setMaxStackSize(1);
      this.toolsSet = toolsSet;
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
      return new AnimationCapabilityProvider();
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
   }

   public Vec3d getColor() {
      return this.toolsSet.mainColor;
   }

   public abstract ModelBase getModel();

   public IEnergyItem asEnergyItem() {
      return null;
   }

   public float getLength(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this.toolsSet.name).getEnchanted("length", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
   }

   public float getLengthLaser(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this.toolsSet.name)
         .getEnchanted("length_laser", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
   }

   public float getDamageToMobsLaser(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this.toolsSet.name)
         .getEnchanted("damage_laser", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
   }

   public float getDamageToMobsDrill(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this.toolsSet.name)
         .getEnchanted("damage_drill", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
   }

   public float getDamageToMobsChainsaw(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this.toolsSet.name)
         .getEnchanted("damage_chainsaw", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
   }

   public float getKnockbackToMobsDrill(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this.toolsSet.name)
         .getEnchanted("knockback_drill", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
   }

   public float getKnockbackToMobsChainsaw(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this.toolsSet.name)
         .getEnchanted("knockback_chainsaw", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
   }

   public float getKnockbackToMobsLaser(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this.toolsSet.name)
         .getEnchanted("knockback_laser", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
   }

   public void spendCharge(ItemStack itemstack, World world, EntityPlayer player, int blocksBreak) {
      if (this.itemNeed != null) {
         float reusModif = 1.0F - 0.15F * EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
         this.addAmmo(NBTHelper.GetNBTint(itemstack, "ammo"), itemstack, -GetMOP.floatToIntWithChance(1 * blocksBreak, itemRand));
      }

      if (this.electricNeed != 0) {
         float reusModif = 1.0F - 0.15F * EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
         this.asEnergyItem().extractEnergyFromItem(itemstack, GetMOP.floatToIntWithChance(reusModif * this.electricNeed * blocksBreak, itemRand), false);
      }

      if (this.manaNeed != 0.0F) {
         Mana.changeMana(player, -this.manaNeed * blocksBreak * (1.0F - 0.15F * EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack)));
         Mana.setManaSpeed(player, 0.001F);
      }
   }

   public void onUpdateMining(ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
   }

   public void inUpdate(ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
   }

   public boolean canMining(ItemStack itemstack, World world, EntityPlayer player) {
      if (this.itemNeed == null || NBTHelper.GetNBTint(itemstack, "ammo") > 0 && this.isReloaded(itemstack)) {
         return this.electricNeed != 0 && this.asEnergyItem().extractEnergyFromItem(itemstack, this.electricNeed, true) < this.electricNeed
            ? false
            : this.manaNeed == 0.0F || Mana.getMana(player) > Math.max(this.manaNeed, 1.0F);
      } else {
         return false;
      }
   }

   public boolean hasSpeed() {
      return true;
   }

   public float gatMaxSpeed(ItemStack itemstack, EntityPlayer player) {
      return 20 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 5;
   }

   public boolean hasEngineTremor() {
      return false;
   }

   public float getBreakingSpeed(ItemStack itemstack, EntityPlayer player) {
      return this.toolsSet.averageDigSpeed * (1.0F + 0.1F * EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemstack));
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (world.isRemote) {
         if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            if (this.hasSpeed()) {
               if (Flicks.instance.confirmPack(player).get(itemSlot, EnumFlick.SHOOT) == null) {
                  Flicks.instance.setClientAnimation(player, itemSlot, EnumFlick.SHOOT, 0, Integer.MAX_VALUE, 0, 0);
               } else {
                  int speed = NBTHelper.GetNBTint(itemstack, "speed");
                  Flicks.instance.setTendency(player, itemSlot, EnumFlick.SHOOT, Math.min(speed, (int)this.gatMaxSpeed(itemstack, player) + 10));
               }
            }

            if (IWeapon.canShoot(itemstack)
               && player.getHeldItemMainhand() == itemstack
               && !player.getCooldownTracker().hasCooldown(this)
               && Keys.isKeyPressed(player, Keys.PRIMARYATTACK)
               && this.canMining(itemstack, world, player)) {
               this.onUpdateMining(itemstack, world, player, NBTHelper.GetNBTint(itemstack, "mode"), NBTHelper.GetNBTint(itemstack, "speed"), itemSlot);
            }
         }
      } else {
         int speed = NBTHelper.GetNBTint(itemstack, "speed");
         boolean decrementSpeed = true;
         boolean setFalseTremor = true;
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer playerx = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, playerx);
            boolean click = Keys.isKeyPressed(playerx, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(playerx, Keys.SECONDARYATTACK);
            if (playerx.getHeldItemMainhand() == itemstack) {
               int mode = NBTHelper.GetNBTint(itemstack, "mode");
               this.inUpdate(itemstack, world, playerx, mode, speed, itemSlot);
               if (this.hasEngineTremor() && this.canMining(itemstack, world, playerx)) {
                  setFalseTremor = false;
                  if (!NBTHelper.GetNBTboolean(itemstack, "tremor")) {
                     NBTHelper.GiveNBTboolean(itemstack, true, "tremor");
                     NBTHelper.SetNBTboolean(itemstack, true, "tremor");
                     IWeapon.sendIWeaponState(itemstack, playerx, 3, itemSlot, EnumHand.MAIN_HAND);
                  }
               }

               if ((click || click2) && !playerx.getCooldownTracker().hasCooldown(this)) {
                  if (click) {
                     if (this.canMining(itemstack, world, playerx)) {
                        this.onUpdateMining(itemstack, world, playerx, mode, speed, itemSlot);
                        if (this.hasSpeed()) {
                           NBTHelper.GiveNBTint(itemstack, 0, "speed");
                           int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
                           int addSpeed = rapidity == 1 ? (playerx.ticksExisted % 2 == 0 ? 1 : 0) : (rapidity > 1 ? 1 : 0);
                           NBTHelper.SetNBTint(itemstack, Math.min(speed + 1 + addSpeed, (int)this.gatMaxSpeed(itemstack, playerx)), "speed");
                           decrementSpeed = false;
                        }
                     } else if (this.itemNeed != null && this.initiateReload(itemstack, playerx, this.itemNeed, this.chargesPerItem)) {
                        world.playSound(
                           (EntityPlayer)null,
                           playerx.posX,
                           playerx.posY,
                           playerx.posZ,
                           Sounds.crystal_cutter_rel,
                           SoundCategory.NEUTRAL,
                           0.7F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                        Weapons.setPlayerAnimationOnServer(playerx, 4, EnumHand.MAIN_HAND);
                     }
                  } else if (click2) {
                     NBTHelper.GiveNBTint(itemstack, 0, "mode");
                     int newMode = GetMOP.next(NBTHelper.GetNBTint(itemstack, "mode"), 1, this.modesCount);
                     NBTHelper.SetNBTint(itemstack, newMode, "mode");
                     IWeapon.sendIWeaponState(itemstack, playerx, newMode + 20, itemSlot, EnumHand.MAIN_HAND);
                     playerx.getCooldownTracker().setCooldown(this, 20 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * 5);
                     Weapons.setPlayerAnimationOnServer(playerx, 28, EnumHand.MAIN_HAND);
                     world.playSound(
                        (EntityPlayer)null,
                        playerx.posX,
                        playerx.posY,
                        playerx.posZ,
                        Sounds.acid_fire_mode,
                        SoundCategory.AMBIENT,
                        0.9F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  }
               }
            }
         }

         if (this.hasSpeed() && decrementSpeed && speed > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "speed");
         }

         if (this.hasEngineTremor() && setFalseTremor) {
            NBTHelper.SetNBTboolean(itemstack, false, "tremor");
         }
      }
   }

   @Override
   public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
      if (state >= 20) {
         int modeReceived = state - 20;
         if (Minecraft.getMinecraft().ingameGUI != null) {
            String message = Ln.translate(this.getNameForMode(modeReceived));
            Minecraft.getMinecraft().ingameGUI.setOverlayMessage(TextFormatting.DARK_AQUA + message, false);
         }
      }
   }

   public abstract String getNameForMode(int var1);

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return this.itemNeed != null;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / this.chargesPerItem, 0.0F, 1.0F);
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this.toolsSet.name);
      int reloading = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack);
      return parameters.getEnchantedi("reload", reloading);
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   public float getDestroySpeed(ItemStack stack, IBlockState state) {
      return 0.0F;
   }

   @Override
   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type != EnumEnchantmentType.DIGGER
            && enchantment.type != EnumEnchantmentType.BREAKABLE
            && enchantment.type != EnchantmentInit.enchantmentTypeWeapon
         ? super.canApplyAtEnchantingTable(stack, enchantment)
         : true;
   }
}
