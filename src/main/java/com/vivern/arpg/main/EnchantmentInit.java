package com.vivern.arpg.main;

import com.vivern.arpg.enchants.AccuracyEnch;
import com.vivern.arpg.enchants.ImpulseEnch;
import com.vivern.arpg.enchants.MightEnch;
import com.vivern.arpg.enchants.RangeEnch;
import com.vivern.arpg.enchants.RapidityEnch;
import com.vivern.arpg.enchants.ReloadingEnch;
import com.vivern.arpg.enchants.ReuseEnch;
import com.vivern.arpg.enchants.SorceryEnch;
import com.vivern.arpg.enchants.SpecialEnch;
import com.vivern.arpg.enchants.WitcheryEnch;
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
