package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Ln;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.renders.ManaBar;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public abstract class ItemWeapon extends Item implements IWeapon {
   public static ArrayList<MovingSoundWeapon> loopedSoundsPlayed = new ArrayList<>();
   public static Predicate<? super MovingSoundWeapon> loopedSoundsRemover = snd -> snd.isDonePlaying();

   public boolean hasSpecialDescription() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      IEnergyItem.addRFInformation(stack, worldIn, tooltip, flagIn);
      if (this.maxFluidForDescription() > 0 && stack.hasTagCompound() && stack.getTagCompound().hasKey("Fluid")) {
         NBTTagCompound tagFluid = stack.getTagCompound().getCompoundTag("Fluid");
         String namefluid = tagFluid.getString("FluidName");
         Fluid fluid = FluidRegistry.getFluid(namefluid);
         namefluid = fluid == null ? namefluid : I18n.translateToLocal(fluid.getUnlocalizedName());
         int stored = tagFluid.getInteger("Amount");
         int maxstored = this.maxFluidForDescription();
         tooltip.add(TextFormatting.RED + "Fluid <" + namefluid + "> " + stored + "/" + maxstored);
         addTooltipBar((float)stored / maxstored, 9689, TextFormatting.GOLD, TextFormatting.DARK_GRAY, tooltip);
      }

      if (Keyboard.isKeyDown(42)) {
         addTooltip(parameters, tooltip, "damage");
         addTooltip(parameters, tooltip, "knockback");
         addTooltip(parameters, tooltip, "damage_radius");
         addTooltip(parameters, tooltip, "rf_to_shoot");
         addTooltip(parameters, tooltip, "manacost");
         addTooltip(parameters, tooltip, "length");
         addTooltip(parameters, tooltip, "end_size");
         addTooltip(parameters, tooltip, "min_pull_time");
         addTooltip(parameters, tooltip, "max_pull_time");
         addTooltip(parameters, tooltip, "shield_angle");
         addTooltip(parameters, tooltip, "damage_reduce");
         addTooltip(parameters, tooltip, "max_hits");
         float max_pull_time = parameters.get("max_pull_time");
         if (max_pull_time != 0.0F) {
            addTooltip(parameters, tooltip, "velocity");
            tooltip.add(TextFormatting.GRAY + Ln.translate("bow_damage_velocity"));
         }
      }

      tooltip.add(TextFormatting.DARK_AQUA + Ln.translate("cooldown") + ": " + this.getCooldownTime(stack));
      int reload = this.getReloadTime(stack);
      if (reload > 0) {
         tooltip.add(TextFormatting.DARK_AQUA + Ln.translate("reload") + ": " + reload);
      }

      String name = this.getRegistryName().getPath();
      tooltip.add(TextFormatting.WHITE + Ln.translate("description." + name));
      if (this.hasSpecialDescription()) {
         if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack) > 0) {
            tooltip.add(TextFormatting.LIGHT_PURPLE + "*Special* " + TextFormatting.AQUA + Ln.translate("descspecial." + name));
         } else if (Keyboard.isKeyDown(42)) {
            tooltip.add(TextFormatting.DARK_PURPLE + "*If special* " + TextFormatting.DARK_AQUA + Ln.translate("descspecial." + name));
         }
      }
   }

   public static void addTooltip(WeaponParameters parameters, List<String> tooltip, String parameter) {
      float value = parameters.get(parameter);
      if (value != 0.0F) {
         tooltip.add(TextFormatting.GRAY + Ln.translate(parameter) + ": " + ManaBar.asString(value));
      }
   }

   public static void addTooltipBar(float progress, int charCode, TextFormatting filledColor, TextFormatting depletedColor, List<String> tooltip) {
      StringBuilder sb = new StringBuilder();
      sb.append(filledColor);
      boolean depleted = false;
      int count = (int)(progress * 16.0F);

      for (int i = 0; i < 16; i++) {
         if (i >= count && !depleted) {
            sb.append(depletedColor);
            depleted = true;
         }

         sb.append((char)charCode);
      }

      tooltip.add(sb.toString());
   }

   public void playOrContinueLoopSound(
      EntityLivingBase entity,
      SoundEvent soundEvent,
      SoundCategory category,
      float volume,
      float pitch,
      int playtime,
      int startTime,
      int endTime,
      float startPitch,
      float endPitch
   ) {
      loopedSoundsPlayed.removeIf(loopedSoundsRemover);

      for (MovingSoundWeapon soundhas : loopedSoundsPlayed) {
         if (soundhas.soundEvent == soundEvent && soundhas.entity == entity) {
            soundhas.endDate = entity.world.getTotalWorldTime() + playtime;
            return;
         }
      }

      MovingSoundWeapon sound = new MovingSoundWeapon(
         entity, this, soundEvent, category, volume, pitch, playtime, startTime, endTime, startPitch, endPitch
      );
      Minecraft.getMinecraft().getSoundHandler().playSound(sound);
      loopedSoundsPlayed.add(sound);
   }

   public int maxFluidForDescription() {
      return 0;
   }

   public int getItemEnchantability() {
      return 2;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type == EnchantmentInit.enchantmentTypeWeapon;
   }

   @SideOnly(Side.CLIENT)
   public static class MovingSoundWeapon extends MovingSound {
      public final EntityLivingBase entity;
      public Item itemInHand;
      public int startTime;
      public int ticksExisted;
      public SoundEvent soundEvent;
      public int endTime;
      public float startPitch;
      public float endPitch;
      public float initVolume;
      public float initPitch;
      public long endDate;

      public MovingSoundWeapon(
         EntityLivingBase entity,
         Item itemInHand,
         SoundEvent soundEvent,
         SoundCategory category,
         float initVolume,
         float initPitch,
         int playtime,
         int startTime,
         int endTime,
         float startPitch,
         float endPitch
      ) {
         super(soundEvent, category);
         this.entity = entity;
         this.repeat = true;
         this.initVolume = initVolume;
         this.initPitch = initPitch;
         this.itemInHand = itemInHand;
         this.startTime = startTime;
         this.soundEvent = soundEvent;
         this.endTime = endTime;
         this.startPitch = startPitch;
         this.endPitch = endPitch;
         this.endDate = entity.world.getTotalWorldTime() + playtime;
      }

      public void update() {
         if (!this.entity.isDead && this.entity.world != null) {
            ItemStack stack = this.entity.getHeldItemMainhand();
            if (!stack.isEmpty() && stack.getItem() == this.itemInHand) {
               long playtime = this.endDate - this.entity.world.getTotalWorldTime();
               float ft1 = GetMOP.getfromto((float)this.ticksExisted, 0.0F, (float)this.startTime);
               float ft2 = 1.0F - GetMOP.getfromto((float)playtime, 0.0F, (float)this.endTime);
               this.ticksExisted++;
               this.volume = (ft1 - ft2) * this.initVolume;
               this.pitch = this.startPitch * (1.0F - ft1) + this.initPitch * (ft1 - ft2) + this.endPitch * ft2;
               this.xPosF = (float)this.entity.posX;
               this.yPosF = (float)this.entity.posY;
               this.zPosF = (float)this.entity.posZ;
            } else {
               this.donePlaying = true;
            }
         } else {
            this.donePlaying = true;
         }
      }

      public boolean isDonePlaying() {
         boolean b1 = super.isDonePlaying()
            || this.entity.isDead
            || this.entity.world == null
            || this.entity.world.getTotalWorldTime() >= this.endDate;
         if (!b1) {
            ItemStack stack = this.entity.getHeldItemMainhand();
            if (stack.isEmpty() || stack.getItem() != this.itemInHand) {
               b1 = true;
            }
         }

         return b1;
      }
   }
}
