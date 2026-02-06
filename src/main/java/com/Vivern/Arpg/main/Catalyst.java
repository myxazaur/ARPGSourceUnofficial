package com.Vivern.Arpg.main;

import com.Vivern.Arpg.entity.CannonSnowball;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public abstract class Catalyst {
   public static void init() {
      CatalystDroppedLoot.allUsedClasses.add(EntityBlaze.class);
   }

   public abstract boolean isStackGood(ItemStack var1);

   public ItemStack getGoodStackForRender() {
      ArrayList<ItemStack> stacks = new ArrayList<>();

      for (Item item : Item.REGISTRY) {
         ItemStack newstack = new ItemStack(item);
         if (this.isStackGood(newstack)) {
            stacks.add(newstack);
         }
      }

      if (stacks.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         int current = (int)(Minecraft.getSystemTime() / 1000L % stacks.size());
         return stacks.get(current);
      }
   }

   public void addAllGoodStacks(List<ItemStack> list) {
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      EntityLivingBase entityLiving = event.getEntityLiving();
      if (!entityLiving.world.isRemote) {
         Class<? extends Entity> clazz = (Class<? extends Entity>)entityLiving.getClass();
         if (CatalystKillEntity.allUsedClasses.contains(clazz)) {
            Entity trueSource = event.getSource().getTrueSource();
            if (trueSource instanceof EntityPlayer) {
               EntityPlayer attacker = (EntityPlayer)trueSource;
               ItemStack itemStack = attacker.getHeldItemMainhand().isEmpty() ? attacker.getHeldItemOffhand() : attacker.getHeldItemMainhand();
               if (!itemStack.isEmpty() && itemStack.getCount() == 1) {
                  ResourceLocation resourceLocation = EntityList.getKey(clazz);
                  String nameNbt = "hex_kill_" + resourceLocation.toString();
                  checkAndApplyHexToItem(itemStack, nameNbt, entityLiving.getName() + " Killer");
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDrops(LivingDropsEvent event) {
      EntityLivingBase entityLiving = event.getEntityLiving();
      if (!entityLiving.world.isRemote && !event.getDrops().isEmpty()) {
         Class<? extends Entity> clazz = (Class<? extends Entity>)entityLiving.getClass();
         if (CatalystDroppedLoot.allUsedClasses.contains(clazz)) {
            if (!(entityLiving instanceof EntityBlaze)
               || event.getSource().getImmediateSource() == null
               || !(event.getSource().getImmediateSource() instanceof EntitySnowball) && !(event.getSource().getImmediateSource() instanceof CannonSnowball)) {
               for (EntityItem entityItem : event.getDrops()) {
                  if (entityItem.getItem().getMaxStackSize() == 1) {
                     ResourceLocation resourceLocation = EntityList.getKey(clazz);
                     String nameNbt = "hex_dropped_" + resourceLocation.toString();
                     applyHexToItem(entityItem.getItem(), nameNbt, entityLiving.getName() + "'s " + entityItem.getItem().getDisplayName());
                  }
               }

               return;
            }

            for (EntityItem entityItemx : event.getDrops()) {
               if (checkAndApplyHexToItem(entityItemx.getItem(), "hex_blaze_snowballed", "Cooled by Snowball")) {
                  return;
               }
            }

            return;
         }
      }
   }

   @SubscribeEvent
   public static void onLivingAttack(LivingAttackEvent event) {
      EntityLivingBase entityLiving = event.getEntityLiving();
      if (entityLiving instanceof EntityGhast && event.getSource().getImmediateSource() instanceof EntityFireball) {
         Entity trueSource = event.getSource().getTrueSource();
         if (trueSource instanceof EntityPlayer) {
            EntityPlayer attacker = (EntityPlayer)trueSource;
            ItemStack itemStack = attacker.getHeldItemMainhand().isEmpty() ? attacker.getHeldItemOffhand() : attacker.getHeldItemMainhand();
            checkAndApplyHexToItem(itemStack, "hex_ghast_fireball", "Returner to Sender");
         }
      }
   }

   @Deprecated
   public static boolean checkAndApplyHexToItem(ItemStack itemstack, String name, String displayname) {
      return false;
   }

   public static void applyHexToItem(ItemStack itemstack, String name, String displayname) {
      if (itemstack.getCount() == 1) {
         if (!itemstack.hasTagCompound()) {
            NBTTagCompound itemCompound = new NBTTagCompound();
            itemstack.setTagCompound(itemCompound);
         }

         NBTTagCompound itemCompound = itemstack.getTagCompound();
         if (!itemCompound.hasKey("display", 10)) {
            itemCompound.setTag("display", new NBTTagCompound());
         }

         NBTTagCompound display = itemCompound.getCompoundTag("display");
         if (!display.hasKey("Lore", 9)) {
            display.setTag("Lore", new NBTTagList());
         }

         if (display.getTagId("Lore") == 9) {
            NBTTagList nbttaglist3 = display.getTagList("Lore", 8);
            nbttaglist3.appendTag(new NBTTagString(displayname));
         }

         if (!itemCompound.hasKey("hex_arpg", 10)) {
            itemCompound.setTag("hex_arpg", new NBTTagCompound());
         }

         NBTTagCompound hex_arpg = itemCompound.getCompoundTag("hex_arpg");
         hex_arpg.setBoolean(name, true);
      }
   }

   public static boolean getHex(ItemStack itemstack, String name) {
      if (itemstack.hasTagCompound()) {
         NBTTagCompound itemCompound = itemstack.getTagCompound();
         if (itemCompound.hasKey("hex_arpg", 10)) {
            NBTTagCompound hex_arpg = itemCompound.getCompoundTag("hex_arpg");
            if (hex_arpg.hasKey(name, 1)) {
               return hex_arpg.getBoolean(name);
            }
         }
      }

      return false;
   }

   public static class CatalystColoredArmor extends Catalyst {
      public EntityEquipmentSlot slot;
      public int color;

      public CatalystColoredArmor(EntityEquipmentSlot slot, int color) {
         this.slot = slot;
         this.color = color;
      }

      @Override
      public boolean isStackGood(ItemStack itemStack) {
         return itemStack.getItem().getEquipmentSlot(itemStack) == this.slot && getLeatherArmorColor(itemStack) == this.color;
      }

      @Override
      public ItemStack getGoodStackForRender() {
         if (this.slot == EntityEquipmentSlot.HEAD) {
            return setLeatherArmorColor(new ItemStack(Items.LEATHER_HELMET), this.color);
         } else if (this.slot == EntityEquipmentSlot.CHEST) {
            return setLeatherArmorColor(new ItemStack(Items.LEATHER_CHESTPLATE), this.color);
         } else if (this.slot == EntityEquipmentSlot.LEGS) {
            return setLeatherArmorColor(new ItemStack(Items.LEATHER_LEGGINGS), this.color);
         } else {
            return this.slot == EntityEquipmentSlot.FEET ? setLeatherArmorColor(new ItemStack(Items.LEATHER_BOOTS), this.color) : ItemStack.EMPTY;
         }
      }

      @Override
      public void addAllGoodStacks(List<ItemStack> list) {
         list.add(this.getGoodStackForRender());
      }

      public static int getLeatherArmorColor(ItemStack stack) {
         NBTTagCompound nbttagcompound = stack.getTagCompound();
         if (nbttagcompound != null) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
            if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3)) {
               return nbttagcompound1.getInteger("color");
            }
         }

         return 10511680;
      }

      public static ItemStack setLeatherArmorColor(ItemStack stack, int color) {
         for (int i = 0; i < 4; i++) {
            if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("display")) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               NBTTagCompound display = new NBTTagCompound();
               display.setInteger("color", color);
               nbttagcompound.setTag("display", display);
               stack.setTagCompound(nbttagcompound);
            }
         }

         return stack;
      }
   }

   public static class CatalystDroppedLoot extends Catalyst {
      public static Set<Class<? extends Entity>> allUsedClasses = new HashSet<>();
      public Class<? extends Entity> entityType;
      public IFitItemStack itemstacksAllowed;
      public String nameNbt;

      public CatalystDroppedLoot(Class<? extends Entity> entityType, IFitItemStack itemstacksAllowed) {
         this.entityType = entityType;
         this.itemstacksAllowed = itemstacksAllowed;
         ResourceLocation resourceLocation = EntityList.getKey(entityType);
         this.nameNbt = "hex_dropped_" + resourceLocation.toString();
         allUsedClasses.add(entityType);
      }

      @Override
      public boolean isStackGood(ItemStack itemStack) {
         return this.itemstacksAllowed.isStackFits(itemStack) ? getHex(itemStack, this.nameNbt) : false;
      }
   }

   public static class CatalystEnchanted extends Catalyst {
      public Enchantment enchantment;
      public int minLevel;
      @Nullable
      public IFitItemStack itemstacksAllowed;

      public CatalystEnchanted(Enchantment enchantment, int minLevel, @Nullable IFitItemStack itemstacksAllowed) {
         this.enchantment = enchantment;
         this.minLevel = minLevel;
         this.itemstacksAllowed = itemstacksAllowed;
      }

      public CatalystEnchanted(Enchantment enchantment, int minLevel) {
         this.enchantment = enchantment;
         this.minLevel = minLevel;
      }

      @Override
      public boolean isStackGood(ItemStack itemStack) {
         return this.itemstacksAllowed != null && !this.itemstacksAllowed.isStackFits(itemStack)
            ? false
            : EnchantmentHelper.getEnchantmentLevel(this.enchantment, itemStack) >= this.minLevel;
      }
   }

   public static class CatalystItems extends Catalyst {
      public Item[] items;

      public CatalystItems(Item... items) {
         this.items = items;
      }

      @Override
      public boolean isStackGood(ItemStack itemStack) {
         for (int i = 0; i < this.items.length; i++) {
            if (this.items[i] == itemStack.getItem()) {
               return true;
            }
         }

         return false;
      }

      @Override
      public ItemStack getGoodStackForRender() {
         int current = (int)(Minecraft.getSystemTime() / 1000L % this.items.length);
         return new ItemStack(this.items[current]);
      }

      @Override
      public void addAllGoodStacks(List<ItemStack> list) {
         for (int i = 0; i < this.items.length; i++) {
            list.add(new ItemStack(this.items[i]));
         }
      }
   }

   public static class CatalystKillEntity extends Catalyst {
      public static Set<Class<? extends Entity>> allUsedClasses = new HashSet<>();
      public Class<? extends Entity> entityType;
      public IFitItemStack itemstacksAllowed;
      public String nameNbt;

      public CatalystKillEntity(Class<? extends Entity> entityType, IFitItemStack itemstacksAllowed) {
         this.entityType = entityType;
         this.itemstacksAllowed = itemstacksAllowed;
         ResourceLocation resourceLocation = EntityList.getKey(entityType);
         this.nameNbt = "hex_kill_" + resourceLocation.toString();
         allUsedClasses.add(entityType);
      }

      @Override
      public boolean isStackGood(ItemStack itemStack) {
         return this.itemstacksAllowed.isStackFits(itemStack) ? getHex(itemStack, this.nameNbt) : false;
      }
   }

   public static class CatalystMetaItems extends Catalyst {
      public Item item;
      public int minMeta;
      public int maxMeta;

      public CatalystMetaItems(Item item, int minMeta, int maxMeta) {
         this.item = item;
         this.minMeta = minMeta;
         this.maxMeta = maxMeta;
      }

      @Override
      public boolean isStackGood(ItemStack itemStack) {
         return this.item == itemStack.getItem() && itemStack.getMetadata() >= this.minMeta && itemStack.getMetadata() <= this.maxMeta;
      }

      @Override
      public ItemStack getGoodStackForRender() {
         int current = (int)(Minecraft.getSystemTime() / 1000L % (this.maxMeta - this.minMeta + 1));
         return new ItemStack(this.item, 1, this.minMeta + current);
      }

      @Override
      public void addAllGoodStacks(List<ItemStack> list) {
         for (int i = this.minMeta; i <= this.maxMeta; i++) {
            list.add(new ItemStack(this.item, 1, i));
         }
      }
   }

   public static class CatalystName extends Catalyst {
      public String nameNbt;
      public IFitItemStack itemstacksAllowed;

      public CatalystName(String nameNbt, IFitItemStack itemstacksAllowed) {
         this.nameNbt = nameNbt;
         this.itemstacksAllowed = itemstacksAllowed;
      }

      @Override
      public boolean isStackGood(ItemStack itemStack) {
         return this.itemstacksAllowed.isStackFits(itemStack) ? getHex(itemStack, this.nameNbt) : false;
      }
   }

   public static class CatalystNone extends Catalyst {
      @Override
      public boolean isStackGood(ItemStack itemStack) {
         return true;
      }
   }

   public static class CatalystPotionBottle extends Catalyst {
      public Potion potion;
      public PotionType[] types;

      public CatalystPotionBottle(Potion potion, PotionType... types) {
         this.potion = potion;
         this.types = types;
      }

      @Override
      public boolean isStackGood(ItemStack itemStack) {
         List<PotionEffect> list = PotionUtils.getEffectsFromStack(itemStack);
         if (!list.isEmpty()) {
            for (PotionEffect potionEffect : list) {
               if (potionEffect.getPotion() == this.potion) {
                  return true;
               }
            }
         }

         return false;
      }

      @Override
      public ItemStack getGoodStackForRender() {
         int current = (int)(Minecraft.getSystemTime() / 1000L % (this.types.length * 3));
         int bottle = current % 3;
         int type = current / 3;
         return PotionUtils.addPotionToItemStack(
            new ItemStack(bottle == 0 ? Items.POTIONITEM : (bottle == 1 ? Items.SPLASH_POTION : Items.LINGERING_POTION)), this.types[type]
         );
      }
   }

   public interface IFitItemStack {
      boolean isStackFits(ItemStack var1);
   }
}
