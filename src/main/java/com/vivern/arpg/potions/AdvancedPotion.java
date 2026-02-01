package com.vivern.arpg.potions;

import com.vivern.arpg.arpgamemodes.SurvivorGamestyleWatcher;
import com.vivern.arpg.main.ArmorProtectionTracker;
import com.vivern.arpg.main.Catalyst;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.network.PacketAdvPotionToClients;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.renders.KillScore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(
   modid = "arpg"
)
public class AdvancedPotion extends Potion {
   public static Random rand = new Random();
   public static HashMap<Integer, AdvancedPotion> potionByIndex = new HashMap<>();
   public final int listIndex;
   public boolean sendToClients;
   public boolean shouldRender = false;

   public AdvancedPotion(boolean isBadEffectIn, int liquidColorIn, int index, boolean sendToClients) {
      super(isBadEffectIn, liquidColorIn);
      this.sendToClients = sendToClients;
      this.listIndex = index;
      potionByIndex.put(index, this);
   }

   public boolean hasStatusIcon() {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arpg:textures/potions.png"));
      return true;
   }

   public static PotionEffect setVariableInt(PotionEffect effect, int value, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      NBTHelper.GiveNBTint(itemstack, value, name);
      NBTHelper.SetNBTint(itemstack, value, name);
      List<ItemStack> stacks = new ArrayList<>();
      stacks.add(itemstack);
      effect.setCurativeItems(stacks);
      return effect;
   }

   public static PotionEffect setVariableFloat(PotionEffect effect, float value, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      NBTHelper.GiveNBTfloat(itemstack, value, name);
      NBTHelper.SetNBTfloat(itemstack, value, name);
      List<ItemStack> stacks = new ArrayList<>();
      stacks.add(itemstack);
      effect.setCurativeItems(stacks);
      return effect;
   }

   public static PotionEffect setVariableDouble(PotionEffect effect, double value, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      NBTHelper.GiveNBTdouble(itemstack, value, name);
      NBTHelper.SetNBTdouble(itemstack, value, name);
      List<ItemStack> stacks = new ArrayList<>();
      stacks.add(itemstack);
      effect.setCurativeItems(stacks);
      return effect;
   }

   public static PotionEffect setVariableString(PotionEffect effect, String value, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      NBTHelper.GiveNBTstring(itemstack, value, name);
      NBTHelper.SetNBTstring(itemstack, value, name);
      List<ItemStack> stacks = new ArrayList<>();
      stacks.add(itemstack);
      effect.setCurativeItems(stacks);
      return effect;
   }

   public static PotionEffect addVariableInt(PotionEffect effect, int value, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      NBTHelper.GiveNBTint(itemstack, value, name);
      NBTHelper.AddNBTint(itemstack, value, name);
      List<ItemStack> stacks = new ArrayList<>();
      stacks.add(itemstack);
      effect.setCurativeItems(stacks);
      return effect;
   }

   public static PotionEffect addVariableFloat(PotionEffect effect, float value, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      NBTHelper.GiveNBTfloat(itemstack, value, name);
      NBTHelper.AddNBTfloat(itemstack, value, name);
      List<ItemStack> stacks = new ArrayList<>();
      stacks.add(itemstack);
      effect.setCurativeItems(stacks);
      return effect;
   }

   public static PotionEffect addVariableDouble(PotionEffect effect, double value, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      NBTHelper.GiveNBTdouble(itemstack, value, name);
      NBTHelper.AddNBTdouble(itemstack, value, name);
      List<ItemStack> stacks = new ArrayList<>();
      stacks.add(itemstack);
      effect.setCurativeItems(stacks);
      return effect;
   }

   public static int getVariableInt(PotionEffect effect, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      return NBTHelper.GetNBTint(itemstack, name);
   }

   public static float getVariableFloat(PotionEffect effect, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      return NBTHelper.GetNBTfloat(itemstack, name);
   }

   public static double getVariableDouble(PotionEffect effect, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      return NBTHelper.GetNBTdouble(itemstack, name);
   }

   public static String getVariableString(PotionEffect effect, String name) {
      ItemStack itemstack = ItemStack.EMPTY;

      for (ItemStack i : effect.getCurativeItems()) {
         if (i.getItem() == ItemsRegister.EXP) {
            itemstack = i;
         }
      }

      if (itemstack == ItemStack.EMPTY) {
         itemstack = new ItemStack(ItemsRegister.EXP);
      }

      return NBTHelper.GetNBTstring(itemstack, name);
   }

   public static void sendPotionPacketToClients(PotionEffect effect, double dist, int potionIndex, int duration, int amplifier, Entity entity) {
      if (!entity.world.isRemote) {
         PacketAdvPotionToClients packet = new PacketAdvPotionToClients();
         ItemStack itemstack = ItemStack.EMPTY;
         Iterator var9 = effect.getCurativeItems().iterator();
         if (var9.hasNext()) {
            ItemStack i = (ItemStack)var9.next();
            if (i.getItem() == ItemsRegister.EXP) {
               itemstack = i;
            }
         }

         packet.writeargs(itemstack, potionIndex, duration, amplifier, entity.getEntityId());
         PacketHandler.sendToAllAround(packet, entity.world, entity.posX, entity.posY, entity.posZ, dist);
      }
   }

   public void onApplyEffect(PotionAddedEvent event) {
      if (this.sendToClients && !event.getEntity().world.isRemote) {
         sendPotionPacketToClients(
            event.getPotionEffect(), 64.0, this.listIndex, event.getPotionEffect().getDuration(), event.getPotionEffect().getAmplifier(), event.getEntity()
         );
      }
   }

   public void onRemoveEffect(EntityLivingBase entityOnEffect, PotionEffect effect, boolean byExpiry) {
      if (this.sendToClients && !entityOnEffect.world.isRemote) {
         sendPotionPacketToClients(effect, 64.0, this.listIndex, 0, 0, entityOnEffect);
      }
   }

   public float onHurtThis(EntityLivingBase entityOnEffect, DamageSource source, float damage, PotionEffect effect) {
      return damage;
   }

   public boolean onAttackThis(EntityLivingBase entityOnEffect, DamageSource source, float damage, PotionEffect effect) {
      return false;
   }

   public boolean onThisAttacks(EntityLivingBase entityOnEffect, EntityLivingBase target, DamageSource source, float damage, PotionEffect effect) {
      return false;
   }

   public void onThisDeath(LivingDeathEvent event, PotionEffect effect) {
   }

   public void onThisKills(EntityLivingBase entityOnEffect, EntityLivingBase target, DamageSource source, PotionEffect effect) {
   }

   public boolean isPotionApplicable(EntityLivingBase entityOnEffect, PotionEffect effect) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void render(EntityLivingBase entityOnEffect, double x, double y, double z, float yaw, float partialTicks, PotionEffect effect, Render entityRenderer) {
   }

   @SideOnly(Side.CLIENT)
   public void renderFirstperson(EntityPlayer player, PotionEffect effect, RenderHandEvent event) {
   }

   public int getThisDuration(EntityLivingBase entityOnEffect) {
      PotionEffect eff = entityOnEffect.getActivePotionEffect(this);
      return eff == null ? 0 : eff.getDuration();
   }

   public int getThisAmplifier(EntityLivingBase entityOnEffect) {
      PotionEffect eff = entityOnEffect.getActivePotionEffect(this);
      return eff == null ? -1 : eff.getAmplifier();
   }

   @SubscribeEvent
   public static void onLivingAttack(LivingAttackEvent event) {
      int cancel = 0;
      Collection<PotionEffect> list = event.getEntityLiving().getActivePotionEffects();
      if (!list.isEmpty()) {
         for (PotionEffect effect : list) {
            if (effect.getPotion() instanceof AdvancedPotion
               && ((AdvancedPotion)effect.getPotion()).onAttackThis(event.getEntityLiving(), event.getSource(), event.getAmount(), effect)) {
               cancel++;
            }
         }
      }

      if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase) {
         EntityLivingBase truesource = (EntityLivingBase)event.getSource().getTrueSource();
         Collection<PotionEffect> list2 = truesource.getActivePotionEffects();
         if (!list2.isEmpty()) {
            for (PotionEffect effectx : list2) {
               if (effectx.getPotion() instanceof AdvancedPotion
                  && ((AdvancedPotion)effectx.getPotion())
                     .onThisAttacks(truesource, event.getEntityLiving(), event.getSource(), event.getAmount(), effectx)) {
                  cancel++;
               }
            }
         }
      }

      event.setCanceled(cancel > 0);
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      ArmorProtectionTracker.onEntityLivingDeath(event);
      Catalyst.onLivingDeath(event);
      SurvivorGamestyleWatcher.onLivingDeath(event);
      KillScore.onLivingDeath(event);
      Collection<PotionEffect> list = event.getEntityLiving().getActivePotionEffects();
      if (!list.isEmpty()) {
         for (PotionEffect effect : list) {
            if (effect.getPotion() instanceof AdvancedPotion) {
               ((AdvancedPotion)effect.getPotion()).onThisDeath(event, effect);
            }
         }
      }

      if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase) {
         EntityLivingBase truesource = (EntityLivingBase)event.getSource().getTrueSource();
         Collection<PotionEffect> list2 = truesource.getActivePotionEffects();
         ArrayList<PotionEffect> list2Copy = new ArrayList<>();
         list2Copy.addAll(list2);
         if (!list2.isEmpty()) {
            for (PotionEffect effectx : list2Copy) {
               if (effectx.getPotion() instanceof AdvancedPotion) {
                  ((AdvancedPotion)effectx.getPotion()).onThisKills(truesource, event.getEntityLiving(), event.getSource(), effectx);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void potionAdded(PotionAddedEvent event) {
      if (event.getPotionEffect().getPotion() instanceof AdvancedPotion) {
         ((AdvancedPotion)event.getPotionEffect().getPotion()).onApplyEffect(event);
      }
   }

   @SubscribeEvent
   public static void potionRemove(PotionRemoveEvent event) {
      if (event.getPotionEffect() != null && event.getPotionEffect().getPotion() instanceof AdvancedPotion) {
         ((AdvancedPotion)event.getPotionEffect().getPotion()).onRemoveEffect(event.getEntityLiving(), event.getPotionEffect(), false);
      }
   }

   @SubscribeEvent
   public static void potionExpiry(PotionExpiryEvent event) {
      if (event.getPotionEffect() != null && event.getPotionEffect().getPotion() instanceof AdvancedPotion) {
         ((AdvancedPotion)event.getPotionEffect().getPotion()).onRemoveEffect(event.getEntityLiving(), event.getPotionEffect(), true);
      }
   }

   @SubscribeEvent
   public static void potionApplicable(PotionApplicableEvent event) {
      if (event.getPotionEffect() != null
         && event.getPotionEffect().getPotion() instanceof AdvancedPotion
         && !((AdvancedPotion)event.getPotionEffect().getPotion()).isPotionApplicable(event.getEntityLiving(), event.getPotionEffect())) {
         event.setResult(Result.DENY);
      }
   }
}
