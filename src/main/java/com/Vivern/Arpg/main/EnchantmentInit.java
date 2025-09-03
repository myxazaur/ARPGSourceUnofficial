package com.Vivern.Arpg.main;

import com.Vivern.Arpg.enchants.AccuracyEnch;
import com.Vivern.Arpg.enchants.ImpulseEnch;
import com.Vivern.Arpg.enchants.MightEnch;
import com.Vivern.Arpg.enchants.RangeEnch;
import com.Vivern.Arpg.enchants.RapidityEnch;
import com.Vivern.Arpg.enchants.ReloadingEnch;
import com.Vivern.Arpg.enchants.ReuseEnch;
import com.Vivern.Arpg.enchants.SorceryEnch;
import com.Vivern.Arpg.enchants.SpecialEnch;
import com.Vivern.Arpg.enchants.WitcheryEnch;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class EnchantmentInit {
   public static final List<Enchantment> ENCHANTMENTSLIST = new ArrayList<>();
   public static final Enchantment MIGHT = new MightEnch();
   public static final Enchantment ACCURACY = new AccuracyEnch();
   public static final Enchantment SPECIAL = new SpecialEnch();
   public static final Enchantment IMPULSE = new ImpulseEnch();
   public static final Enchantment REUSE = new ReuseEnch();
   public static final Enchantment RAPIDITY = new RapidityEnch();
   public static final Enchantment SORCERY = new SorceryEnch();
   public static final Enchantment RELOADING = new ReloadingEnch();
   public static final Enchantment RANGE = new RangeEnch();
   public static final Enchantment WITCHERY = new WitcheryEnch();
   public static EnumEnchantmentType enchantmentTypeWeapon;

   @SubscribeEvent
   public static void RegisterEnch(Register<Enchantment> event) {
      event.getRegistry().registerAll(ENCHANTMENTSLIST.toArray(new Enchantment[0]));
   }
}
