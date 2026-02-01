package com.vivern.arpg.elements;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.FindAmmo;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Ln;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketItemBoomToClient;
import com.vivern.arpg.network.PacketWeaponEffectToClients;
import com.vivern.arpg.network.PacketWeaponStateToClients;
import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IWeapon {
   String defaultcolor = "пїЅ7";

   default boolean autoReload(ItemStack itemstack) {
      return false;
   }

   boolean autoCooldown(ItemStack var1);

   default boolean hasZoom(ItemStack itemstack) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   default void bom(int param) {
   }

   default int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return parameters.getEnchantedi("cooldown", rapidity);
   }

   default int getReloadTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int reloading = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RELOADING, itemstack);
      return parameters.getEnchantedi("reload", reloading);
   }

   default float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }

   default boolean canChangeItem(ItemStack itemstack, EntityPlayer player) {
      return this.autoCooldown(itemstack) || !player.getCooldownTracker().hasCooldown(itemstack.getItem());
   }

   @SideOnly(Side.CLIENT)
   default float getAdditionalDurabilityBar(ItemStack itemstack) {
      return 0.0F;
   }

   @SideOnly(Side.CLIENT)
   default boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return false;
   }

   default void setReload(ItemStack itemstack) {
      NBTHelper.GiveNBTint(itemstack, this.getReloadTime(itemstack), "reload_time");
      NBTHelper.SetNBTint(itemstack, this.getReloadTime(itemstack), "reload_time");
   }

   default void decreaceMana(EntityPlayer player, float amount) {
      if (!player.capabilities.isCreativeMode) {
         Mana.changeMana(player, amount);
         Mana.setManaSpeed(player, 0.001F);
      }
   }

   default void startReload(ItemStack itemstack) {
      NBTHelper.GiveNBTint(itemstack, this.getReloadTime(itemstack), "reload_time");
      NBTHelper.SetNBTint(itemstack, this.getReloadTime(itemstack) - 1, "reload_time");
   }

   default void decreaseReload(ItemStack itemstack, EntityPlayer player) {
      if (player.getHeldItemMainhand() == itemstack || player.getHeldItemOffhand() == itemstack || this.autoReload(itemstack)) {
         int rel = NBTHelper.GetNBTint(itemstack, "reload_time");
         if (rel > 0 && rel < this.getReloadTime(itemstack)) {
            NBTHelper.AddNBTint(itemstack, -1, "reload_time");
         }
      }
   }

   default boolean isReloaded(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "reload_time") < 1;
   }

   default boolean isReloadNeed(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "reload_time") >= this.getReloadTime(itemstack);
   }

   default boolean initiateReload(ItemStack itemstack, EntityPlayer player, Item itemAmmo, int maxAmmo) {
      WeaponHandleType weaponHandleType = this.getWeaponHandleType();
      boolean hascooldown = false;
      if (weaponHandleType == WeaponHandleType.ONE_HANDED && player.getHeldItemOffhand() == itemstack) {
         hascooldown = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
      } else {
         hascooldown = player.getCooldownTracker().hasCooldown(itemstack.getItem());
      }

      if (!hascooldown) {
         NBTHelper.GiveNBTint(itemstack, 0, "ammo");
         if (NBTHelper.GetNBTint(itemstack, "ammo") == 0) {
            if (player.inventory.hasItemStack(new ItemStack(itemAmmo))) {
               player.inventory.clearMatchingItems(itemAmmo, -1, 1, null);
               this.startReload(itemstack);
               NBTHelper.SetNBTint(itemstack, maxAmmo, "ammo");
               return false;
            }
         } else if (this.isReloadNeed(itemstack)) {
            this.startReload(itemstack);
            return true;
         }
      }

      return false;
   }

   default boolean initiateReload(ItemStack itemstack, EntityPlayer player, Item itemAmmo, int maxAmmo, Item toReturn) {
      WeaponHandleType weaponHandleType = this.getWeaponHandleType();
      boolean hascooldown = false;
      if (weaponHandleType == WeaponHandleType.ONE_HANDED && player.getHeldItemOffhand() == itemstack) {
         hascooldown = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
      } else {
         hascooldown = player.getCooldownTracker().hasCooldown(itemstack.getItem());
      }

      if (!hascooldown) {
         NBTHelper.GiveNBTint(itemstack, 0, "ammo");
         if (NBTHelper.GetNBTint(itemstack, "ammo") == 0) {
            if (player.inventory.hasItemStack(new ItemStack(itemAmmo))) {
               player.inventory.clearMatchingItems(itemAmmo, -1, 1, null);
               this.startReload(itemstack);
               NBTHelper.SetNBTint(itemstack, maxAmmo, "ammo");
               ItemStack newstack = new ItemStack(toReturn, 1);
               if (!player.addItemStackToInventory(newstack)) {
                  player.world
                     .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
               }

               return false;
            }
         } else if (this.isReloadNeed(itemstack)) {
            this.startReload(itemstack);
            return true;
         }
      }

      return false;
   }

   default boolean initiateBulletReload(ItemStack itemstack, EntityPlayer player, Item itemClip, int maxAmmo, boolean returnEmptyClip) {
      WeaponHandleType weaponHandleType = this.getWeaponHandleType();
      boolean hascooldown = false;
      if (weaponHandleType == WeaponHandleType.ONE_HANDED && player.getHeldItemOffhand() == itemstack) {
         hascooldown = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
      } else {
         hascooldown = player.getCooldownTracker().hasCooldown(itemstack.getItem());
      }

      if (!hascooldown) {
         NBTHelper.GiveNBTint(itemstack, 0, "ammo");
         if (NBTHelper.GetNBTint(itemstack, "ammo") == 0) {
            if (player.inventory.hasItemStack(new ItemStack(itemClip))) {
               int clipstackIndex = FindAmmo.getNonEmptyClipStack(player.inventory, itemClip);
               if (clipstackIndex < 0) {
                  return false;
               }

               ItemStack clipstack = (ItemStack)player.inventory.mainInventory.get(clipstackIndex);
               if (clipstack.isEmpty()) {
                  return false;
               }

               String nbtname = NBTHelper.GetNBTstring(clipstack, "bullet");
               NBTHelper.GiveNBTstring(itemstack, nbtname, "bullet");
               NBTHelper.SetNBTstring(itemstack, nbtname, "bullet");
               if (!((ItemStack)player.inventory.mainInventory.get(clipstackIndex)).isEmpty()) {
                  ItemStackHelper.getAndSplit(player.inventory.mainInventory, clipstackIndex, 1);
               }

               this.startReload(itemstack);
               NBTHelper.SetNBTint(itemstack, maxAmmo, "ammo");
               if (returnEmptyClip) {
                  ItemStack newstack = new ItemStack(itemClip, 1);
                  if (!player.addItemStackToInventory(newstack)) {
                     player.world
                        .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
                  }
               }

               return false;
            }
         } else if (this.isReloadNeed(itemstack)) {
            this.startReload(itemstack);
            return true;
         }
      }

      return false;
   }

   default boolean initiateBulletReload(ItemStack itemstack, EntityPlayer player, ItemStack itemClip, int maxAmmo, boolean returnEmptyClip) {
      WeaponHandleType weaponHandleType = this.getWeaponHandleType();
      boolean hascooldown = false;
      if (weaponHandleType == WeaponHandleType.ONE_HANDED && player.getHeldItemOffhand() == itemstack) {
         hascooldown = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
      } else {
         hascooldown = player.getCooldownTracker().hasCooldown(itemstack.getItem());
      }

      if (!hascooldown) {
         NBTHelper.GiveNBTint(itemstack, 0, "ammo");
         if (NBTHelper.GetNBTint(itemstack, "ammo") == 0) {
            int clipstackIndex = FindAmmo.getNonEmptyClipStack(player.inventory, itemClip.getItem(), itemClip.getCount());
            if (clipstackIndex < 0) {
               return false;
            }

            ItemStack clipstack = (ItemStack)player.inventory.mainInventory.get(clipstackIndex);
            if (clipstack.isEmpty()) {
               return false;
            }

            String nbtname = NBTHelper.GetNBTstring(clipstack, "bullet");
            NBTHelper.GiveNBTstring(itemstack, nbtname, "bullet");
            NBTHelper.SetNBTstring(itemstack, nbtname, "bullet");
            player.inventory.clearMatchingItems(itemClip.getItem(), -1, itemClip.getCount(), clipstack.getTagCompound());
            this.startReload(itemstack);
            NBTHelper.SetNBTint(itemstack, maxAmmo, "ammo");
            if (returnEmptyClip) {
               ItemStack newstack = new ItemStack(itemClip.getItem(), itemClip.getCount());
               if (!player.addItemStackToInventory(newstack)) {
                  player.world
                     .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
               }
            }

            return false;
         }

         if (this.isReloadNeed(itemstack)) {
            this.startReload(itemstack);
            return true;
         }
      }

      return false;
   }

   default boolean initiateRocketReload(ItemStack itemstack, EntityPlayer player, int maxAmmo) {
      WeaponHandleType weaponHandleType = this.getWeaponHandleType();
      boolean hascooldown = false;
      if (weaponHandleType == WeaponHandleType.ONE_HANDED && player.getHeldItemOffhand() == itemstack) {
         hascooldown = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
      } else {
         hascooldown = player.getCooldownTracker().hasCooldown(itemstack.getItem());
      }

      if (!hascooldown) {
         NBTHelper.GiveNBTint(itemstack, 0, "ammo");
         if (NBTHelper.GetNBTint(itemstack, "ammo") == 0) {
            List<Integer> rocketslots = FindAmmo.getFirstRocket(player.inventory, maxAmmo);
            if (rocketslots != null) {
               ItemStack stack = (ItemStack)player.inventory.mainInventory.get(rocketslots.get(0));
               ItemRocket itemrocket = (ItemRocket)stack.getItem();
               String nbtname = itemrocket.NBTname;
               NBTHelper.GiveNBTstring(itemstack, nbtname, "rocket");
               NBTHelper.SetNBTstring(itemstack, nbtname, "rocket");
               int countdeleted = 0;

               for (int sloti : rocketslots) {
                  ItemStack st = (ItemStack)player.inventory.mainInventory.get(sloti);
                  int remove = Math.min(st.getCount(), maxAmmo - countdeleted);
                  ItemStackHelper.getAndSplit(player.inventory.mainInventory, sloti, remove);
                  countdeleted += remove;
                  if (countdeleted >= maxAmmo) {
                     break;
                  }
               }

               this.startReload(itemstack);
               NBTHelper.SetNBTint(itemstack, maxAmmo, "ammo");
               return false;
            }
         } else if (this.isReloadNeed(itemstack)) {
            this.startReload(itemstack);
            return true;
         }
      }

      return false;
   }

   default boolean initiateMetadataReload(ItemStack itemstack, EntityPlayer player, ItemStack ammostack, int maxAmmo, @Nullable ItemStack toReturn) {
      WeaponHandleType weaponHandleType = this.getWeaponHandleType();
      boolean hascooldown = false;
      if (weaponHandleType == WeaponHandleType.ONE_HANDED && player.getHeldItemOffhand() == itemstack) {
         hascooldown = player.getCooldownTracker().hasCooldown(ItemsRegister.EXP);
      } else {
         hascooldown = player.getCooldownTracker().hasCooldown(itemstack.getItem());
      }

      if (!hascooldown) {
         NBTHelper.GiveNBTint(itemstack, 0, "ammo");
         if (NBTHelper.GetNBTint(itemstack, "ammo") == 0) {
            if (player.inventory.hasItemStack(ammostack)) {
               player.inventory.clearMatchingItems(ammostack.getItem(), ammostack.getMetadata(), ammostack.getCount(), null);
               this.startReload(itemstack);
               NBTHelper.SetNBTint(itemstack, maxAmmo, "ammo");
               if (toReturn != null) {
                  ItemStack newstack = toReturn.copy();
                  if (!player.addItemStackToInventory(newstack)) {
                     player.world
                        .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
                  }
               }

               return false;
            }
         } else if (this.isReloadNeed(itemstack)) {
            this.startReload(itemstack);
            return true;
         }
      }

      return false;
   }

   default void addAmmo(int currentammo, ItemStack itemstack, int amount) {
      NBTHelper.SetNBTint(itemstack, Math.max(currentammo + amount, 0), "ammo");
   }

   WeaponHandleType getWeaponHandleType();

   default WeaponHandleType getWeaponHandleType(ItemStack itemstack) {
      return this.getWeaponHandleType();
   }

   default void setCanShoot(ItemStack itemstack, Entity entity) {
      boolean can = this.getCanShoot(itemstack, entity);
      if (!can || itemstack.hasTagCompound()) {
         if (!itemstack.hasTagCompound()) {
            NBTTagCompound itemCompound = new NBTTagCompound();
            itemstack.setTagCompound(itemCompound);
         } else {
            itemstack.getTagCompound().setBoolean("iw_usable", can);
         }
      }
   }

   default boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return true;
   }

   static boolean canShoot(ItemStack itemstack) {
      return itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("iw_usable") ? itemstack.getTagCompound().getBoolean("iw_usable") : true;
   }

   default boolean getCanShoot(ItemStack itemstack, Entity entity) {
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         ItemStack mainH = player.getHeldItemMainhand();
         ItemStack offH = player.getHeldItemOffhand();
         if (offH != itemstack && mainH != itemstack) {
            return true;
         } else {
            if (offH == itemstack && !mainH.isEmpty()) {
               Multimap<String, AttributeModifier> map = mainH.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);

               for (Entry<String, AttributeModifier> entry : map.entries()) {
                  if (SharedMonsterAttributes.ATTACK_DAMAGE.getName().equals(entry.getKey())) {
                     return false;
                  }

                  if (SharedMonsterAttributes.ATTACK_SPEED.getName().equals(entry.getKey())) {
                     return false;
                  }
               }
            }

            switch (this.getWeaponHandleType(itemstack)) {
               case TWO_HANDED:
                  if (mainH == itemstack) {
                     if (!offH.isEmpty() && !(offH.getItem() instanceof IWeapon)) {
                        return false;
                     }

                     return true;
                  } else if (offH == itemstack) {
                     if (mainH.isEmpty()) {
                        return true;
                     }

                     return false;
                  }
               case ONE_HANDED:
                  if (mainH == itemstack) {
                     return true;
                  } else if (offH == itemstack) {
                     if (mainH.getItem() instanceof IWeapon) {
                        if (((IWeapon)mainH.getItem()).getWeaponHandleType(mainH) == WeaponHandleType.TWO_HANDED) {
                           return false;
                        }

                        return true;
                     }

                     return true;
                  }
               case SEMI_ONE_HANDED:
                  if (mainH == itemstack) {
                     return true;
                  } else if (offH == itemstack) {
                     if (mainH.getItem() instanceof IWeapon) {
                        if (((IWeapon)mainH.getItem()).getWeaponHandleType(mainH) == WeaponHandleType.TWO_HANDED) {
                           return false;
                        }

                        if (((IWeapon)mainH.getItem()).getWeaponHandleType(mainH) == WeaponHandleType.SEMI_ONE_HANDED) {
                           return false;
                        }

                        return true;
                     }

                     return true;
                  }
               default:
                  return false;
            }
         }
      } else {
         return false;
      }
   }

   static void fireEffect(
      Item thisItem,
      EntityPlayer player,
      World world,
      double distance,
      double x,
      double y,
      double z,
      double a,
      double b,
      double c,
      double d1,
      double d2,
      double d3
   ) {
      if (!world.isRemote) {
         PacketWeaponEffectToClients packet = new PacketWeaponEffectToClients();
         packet.writeargs(x, y, z, a, b, c, d1, d2, d3, Item.getIdFromItem(thisItem));
         PacketHandler.sendToAllAround(packet, world, player.posX, player.posY, player.posZ, distance);
      }
   }

   static void fireEffectExcl(
      Item thisItem,
      EntityPlayer player,
      EntityPlayer excludingplayer,
      World world,
      double distance,
      double x,
      double y,
      double z,
      double a,
      double b,
      double c,
      double d1,
      double d2,
      double d3
   ) {
      if (!world.isRemote) {
         PacketWeaponEffectToClients packet = new PacketWeaponEffectToClients();
         packet.writeargs(x, y, z, a, b, c, d1, d2, d3, Item.getIdFromItem(thisItem));
         PacketHandler.sendToAllAroundExcluding(packet, world, player.posX, player.posY, player.posZ, distance, excludingplayer);
      }
   }

   static void fireEffect(
      Item thisItem,
      Entity senderForPosition,
      World world,
      double distance,
      double x,
      double y,
      double z,
      double a,
      double b,
      double c,
      double d1,
      double d2,
      double d3
   ) {
      if (!world.isRemote) {
         PacketWeaponEffectToClients packet = new PacketWeaponEffectToClients();
         packet.writeargs(x, y, z, a, b, c, d1, d2, d3, Item.getIdFromItem(thisItem));
         PacketHandler.sendToAllAround(
            packet, world, senderForPosition.posX, senderForPosition.posY, senderForPosition.posZ, distance
         );
      }
   }

   static void fireBomEffect(Item thisItem, EntityPlayer player, World world, int parameter) {
      if (!world.isRemote && player instanceof EntityPlayerMP) {
         PacketItemBoomToClient packet = new PacketItemBoomToClient();
         packet.write(Item.getIdFromItem(thisItem), parameter);
         PacketHandler.sendTo(packet, (EntityPlayerMP)player);
      }
   }

   static void sendIWeaponState(ItemStack itemStack, EntityPlayer player, int state_byte, int slot, EnumHand hand) {
      if (!player.world.isRemote) {
         PacketWeaponStateToClients packet = new PacketWeaponStateToClients();
         packet.writeargs((byte)state_byte, player.getEntityId(), hand == EnumHand.MAIN_HAND ? slot : 40);
         PacketHandler.sendToAllAround(packet, player.world, player.posX, player.posY, player.posZ, 64.0);
      }
   }

   @SideOnly(Side.CLIENT)
   default void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
   }

   @SideOnly(Side.CLIENT)
   default void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
   }

   default int getModifiedMeleeCooldown(double entityAttackSpeed, int cooldown) {
      return (int)Math.round(cooldown * (4.0 / entityAttackSpeed));
   }

   default boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      float artropods = 1.0F;
      float holy = 1.0F;
      if (entity instanceof EntityLivingBase) {
         artropods = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 0.1F + 1.0F
            : 1.0F;
         holy = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 0.1F + 1.0F
            : 1.0F;
      }

      boolean ret = entity.attackEntityFrom(
         new WeaponDamage(stack, player, null, false, false, player, WeaponDamage.blade),
         (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy
      );
      entity.hurtResistantTime = 0;
      int firelvl = WeaponParameters.getWeaponParameters(stack.getItem())
         .getEnchantedi("fire", EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack));
      if (firelvl > 0) {
         entity.setFire(firelvl);
      }

      return ret;
   }

   static MeleeAttackResult doMeleeSwordAttack(IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      int sharpness = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack);
      int sweeping = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack);
      int knockback = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      return doMeleeSwordAttack(
         iweapon,
         stack,
         player,
         hand,
         parameters.getEnchanted("damage", sharpness),
         parameters.getEnchanted("knockback", knockback),
         parameters.getEnchanted("length", sweeping),
         parameters.getEnchanted("size", sweeping),
         parameters.getEnchanted("end_size", sweeping),
         isCritical
      );
   }

   static MeleeAttackResult doMeleeSwordAttack(
      IWeapon iweapon,
      ItemStack stack,
      EntityPlayer player,
      EnumHand hand,
      float damage,
      float knockback,
      float length,
      float size,
      float endSize,
      boolean isCritical
   ) {
      World world = player.world;
      Vec3d vec = GetMOP.PosRayTrace(length, 1.0F, player, size, size / 2.0F);
      AxisAlignedBB aabb = new AxisAlignedBB(
         vec.x - endSize,
         vec.y - endSize,
         vec.z - endSize,
         vec.x + endSize,
         vec.y + endSize,
         vec.z + endSize
      );
      List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
      boolean ret = false;
      if (!list.isEmpty()) {
         IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
         AttributeModifier modifier = new AttributeModifier("iw_attackmelee", damage, 0);
         entityAttribute.applyModifier(modifier);
         IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
         AttributeModifier modifierk = new AttributeModifier("iw_knockbackmelee", knockback, 0);
         if (knockback > 0.0F) {
            entityAttributek.applyModifier(modifierk);
         }

         stack.damageItem(1, player);

         for (Entity entity : list) {
            if (Weapons.canDealDamageTo(entity) && Team.checkIsOpponent(player, entity) && iweapon.attackEntityMelee(entity, stack, player, hand, isCritical)) {
               ret = true;
            }
         }

         entityAttribute.removeModifier(modifier);
         if (knockback > 0.0F) {
            entityAttributek.removeModifier(modifierk);
         }
      }

      return new MeleeAttackResult(vec, aabb, list, null, ret);
   }

   static MeleeAttackResult doMeleeSpearAttack(IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      int sharpness = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack);
      int sweeping = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack);
      int knockback = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      return doMeleeSpearAttack(
         iweapon,
         stack,
         player,
         hand,
         parameters.getEnchanted("damage", sharpness),
         parameters.getEnchanted("knockback", knockback),
         parameters.getEnchanted("length", sweeping),
         parameters.getEnchanted("size", sweeping),
         isCritical
      );
   }

   static MeleeAttackResult doMeleeSpearAttack(
      IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, float damage, float knockback, float length, float size, boolean isCritical
   ) {
      World world = player.world;
      Vec3d vec3d = player.getPositionEyes(1.0F);
      Vec3d vec3d1 = player.getLook(1.0F);
      Vec3d vec3d2 = vec3d.add(vec3d1.x * length, vec3d1.y * length, vec3d1.z * length);
      RayTraceResult raytraceresult = player.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
      if (raytraceresult != null) {
         vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
      }

      List<Entity> list = GetMOP.findEntitiesOnPath(vec3d, vec3d2, world, player, size, size / 2.0F);
      boolean ret = false;
      if (!list.isEmpty()) {
         IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
         AttributeModifier modifier = new AttributeModifier("iw_attackmelee", damage, 0);
         entityAttribute.applyModifier(modifier);
         IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
         AttributeModifier modifierk = new AttributeModifier("iw_knockbackmelee", knockback, 0);
         if (knockback > 0.0F) {
            entityAttributek.applyModifier(modifierk);
         }

         stack.damageItem(1, player);

         for (Entity entity : list) {
            if (Weapons.canDealDamageTo(entity) && Team.checkIsOpponent(player, entity) && iweapon.attackEntityMelee(entity, stack, player, hand, isCritical)) {
               ret = true;
            }
         }

         entityAttribute.removeModifier(modifier);
         if (knockback > 0.0F) {
            entityAttributek.removeModifier(modifierk);
         }
      }

      return new MeleeAttackResult(vec3d2, null, list, raytraceresult, ret);
   }

   static MeleeAttackResult doMeleeHammerAttack(
      IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical, int sweepAngle, int sweepStepAngle
   ) {
      int sharpness = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack);
      int sweeping = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack);
      int knockback = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      return doMeleeHammerAttack(
         iweapon,
         stack,
         player,
         hand,
         parameters.getEnchanted("damage", sharpness),
         parameters.getEnchanted("knockback", knockback),
         parameters.getEnchanted("length", sweeping),
         parameters.getEnchanted("size", sweeping),
         parameters.getEnchanted("end_size", sweeping),
         isCritical,
         sweepAngle,
         sweepStepAngle
      );
   }

   static MeleeAttackResult doMeleeHammerAttack(
      IWeapon iweapon,
      ItemStack stack,
      EntityPlayer player,
      EnumHand hand,
      float damage,
      float knockback,
      float length,
      float size,
      float endSize,
      boolean isCritical,
      int sweepAngle,
      int sweepStepAngle
   ) {
      World world = player.world;
      sweepAngle /= 2;
      Vec3d vec = null;
      AxisAlignedBB lastaabb = null;
      int pit = (int)player.rotationPitch + sweepAngle;

      while (pit > player.rotationPitch - sweepAngle) {
         vec = GetMOP.RotatedPosRayTrace(length, 1.0F, player, size, size, pit, player.rotationYaw);
         AxisAlignedBB aabb2 = new AxisAlignedBB(
            vec.x - size,
            vec.y - size,
            vec.z - size,
            vec.x + size,
            vec.y + size,
            vec.z + size
         );
         lastaabb = aabb2;
         List<EntityLivingBase> list2 = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb2);
         if (!list2.isEmpty()) {
            AxisAlignedBB aabb = new AxisAlignedBB(
               vec.x - endSize,
               vec.y - endSize,
               vec.z - endSize,
               vec.x + endSize,
               vec.y + endSize,
               vec.z + endSize
            );
            lastaabb = aabb;
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
            if (!list.isEmpty()) {
               IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
               AttributeModifier modifier = new AttributeModifier("iw_attackmelee", damage, 0);
               entityAttribute.applyModifier(modifier);
               IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
               AttributeModifier modifierk = new AttributeModifier("iw_knockbackmelee", knockback, 0);
               if (knockback > 0.0F) {
                  entityAttributek.applyModifier(modifierk);
               }

               stack.damageItem(1, player);
               boolean ret = false;

               for (Entity entity : list) {
                  if (Weapons.canDealDamageTo(entity)
                     && Team.checkIsOpponent(player, entity)
                     && iweapon.attackEntityMelee(entity, stack, player, hand, isCritical)) {
                     ret = true;
                  }
               }

               entityAttribute.removeModifier(modifier);
               if (knockback > 0.0F) {
                  entityAttributek.removeModifier(modifierk);
               }

               if (ret) {
                  return new MeleeAttackResult(vec, aabb, list, null, ret);
               }
            }
         }

         pit -= sweepStepAngle;
      }

      return new MeleeAttackResult(vec, lastaabb, null, null, false);
   }

   static MeleeAttackResult doMeleeDaggerAttack(IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      int sharpness = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack);
      int sweeping = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack);
      int knockback = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      return doMeleeDaggerAttack(
         iweapon,
         stack,
         player,
         hand,
         parameters.getEnchanted("damage", sharpness),
         parameters.getEnchanted("knockback", knockback),
         parameters.getEnchanted("length", sweeping),
         isCritical
      );
   }

   static MeleeAttackResult doMeleeDaggerAttack(
      IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, float damage, float knockback, float length, boolean isCritical
   ) {
      Vec3d vec3d = player.getPositionEyes(1.0F);
      Vec3d vec3d1 = player.getLook(1.0F);
      Vec3d vec3d2 = vec3d.add(vec3d1.x * length, vec3d1.y * length, vec3d1.z * length);
      RayTraceResult result = GetMOP.fixedRayTraceBlocks(player.world, player, 0.2F, 0.15F, true, vec3d, vec3d2, false, true, false);
      if (result != null && result.entityHit != null) {
         boolean ret = false;
         if (Team.checkIsOpponent(player, result.entityHit) && Weapons.canDealDamageTo(result.entityHit)) {
            float angle = Math.abs(GetMOP.getDirectionBetweenAngles(result.entityHit.rotationYaw, player.rotationYaw));
            if (angle < 80.0F) {
               isCritical = true;
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
               damage += parameters.get("critical_damage");
            }

            IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
            AttributeModifier modifier = new AttributeModifier("iw_attackmelee", damage, 0);
            entityAttribute.applyModifier(modifier);
            IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
            AttributeModifier modifierk = new AttributeModifier("iw_knockbackmelee", knockback, 0);
            if (knockback > 0.0F) {
               entityAttributek.applyModifier(modifierk);
            }

            stack.damageItem(1, player);
            result.entityHit.hurtResistantTime = 0;
            if (iweapon.attackEntityMelee(result.entityHit, stack, player, hand, isCritical)) {
               ret = true;
            }

            entityAttribute.removeModifier(modifier);
            if (knockback > 0.0F) {
               entityAttributek.removeModifier(modifierk);
            }
         }

         List<Entity> list = new ArrayList<>();
         list.add(result.entityHit);
         return new MeleeAttackResult(result.hitVec, null, list, result, ret);
      } else {
         return new MeleeAttackResult(result.hitVec, null, null, result, false);
      }
   }

   static MeleeAttackResult doMeleeWhipAttack(IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand) {
      int sharpness = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack);
      int sweeping = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack);
      int knockback = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack);
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      return doMeleeWhipAttack(
         iweapon,
         stack,
         player,
         hand,
         parameters.getEnchanted("damage", sharpness),
         parameters.getEnchanted("knockback", knockback),
         parameters.getEnchanted("length", sweeping),
         parameters.getEnchanted("size", sweeping),
         parameters.getEnchanted("end_size", sweeping)
      );
   }

   static MeleeAttackResult doMeleeWhipAttack(
      IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, float damage, float knockback, float length, float size, float endSize
   ) {
      World world = player.world;
      Vec3d vec3dEyes = new Vec3d(player.posX, player.posY + player.getEyeHeight() - 0.2F, player.posZ);
      float yaw;
      if ((hand != EnumHand.MAIN_HAND || player.getPrimaryHand() != EnumHandSide.RIGHT)
         && (hand != EnumHand.OFF_HAND || player.getPrimaryHand() != EnumHandSide.LEFT)) {
         yaw = -90.0F;
      } else {
         yaw = 90.0F;
      }

      Vec3d yawVec = GetMOP.YawToVec3d(player.rotationYawHead + yaw);
      float shoulders = 0.2F;
      vec3dEyes = vec3dEyes.add(yawVec.x * shoulders, yawVec.y * shoulders, yawVec.z * shoulders);
      Vec3d vec3dLook = player.getLook(1.0F);
      yawVec = vec3dEyes.add(vec3dLook.x * length, vec3dLook.y * length, vec3dLook.z * length);
      AxisAlignedBB aabbEnd = new AxisAlignedBB(
         yawVec.x - endSize,
         yawVec.y - endSize,
         yawVec.z - endSize,
         yawVec.x + endSize,
         yawVec.y + endSize,
         yawVec.z + endSize
      );
      RayTraceResult resultHit = world.rayTraceBlocks(vec3dEyes, yawVec, false, true, false);
      Vec3d vec3dHit = resultHit == null ? yawVec : resultHit.hitVec;
      boolean criticalPossible = resultHit == null || aabbEnd.contains(vec3dHit);
      List<Entity> listEntitiesOnEnd = criticalPossible ? world.getEntitiesWithinAABBExcludingEntity(player, aabbEnd) : null;
      List<Entity> listEntitiesOnLine = GetMOP.findEntitiesOnPath(vec3dEyes, vec3dHit, world, player, size, size * 0.75);
      if (criticalPossible && !listEntitiesOnEnd.isEmpty()) {
         listEntitiesOnLine.removeAll(listEntitiesOnEnd);
         criticalPossible = listEntitiesOnLine.isEmpty();
      }

      if (!listEntitiesOnLine.isEmpty() || criticalPossible && !listEntitiesOnEnd.isEmpty()) {
         IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
         AttributeModifier modifier = new AttributeModifier("iw_attackmelee", damage, 0);
         entityAttribute.applyModifier(modifier);
         IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
         AttributeModifier modifierk = new AttributeModifier("iw_knockbackmelee", knockback, 0);
         if (knockback > 0.0F) {
            entityAttributek.applyModifier(modifierk);
         }

         stack.damageItem(1, player);
         boolean ret = false;
         if (criticalPossible && !listEntitiesOnEnd.isEmpty()) {
            for (Entity entity : listEntitiesOnEnd) {
               if (Weapons.canDealDamageTo(entity) && Team.checkIsOpponent(player, entity) && iweapon.attackEntityMelee(entity, stack, player, hand, true)) {
                  ret = true;
               }
            }
         }

         if (!listEntitiesOnLine.isEmpty()) {
            for (Entity entityx : listEntitiesOnLine) {
               if (Weapons.canDealDamageTo(entityx) && Team.checkIsOpponent(player, entityx) && iweapon.attackEntityMelee(entityx, stack, player, hand, false)) {
                  ret = true;
               }
            }
         }

         entityAttribute.removeModifier(modifier);
         if (knockback > 0.0F) {
            entityAttributek.removeModifier(modifierk);
         }

         RayTraceResult res = new RayTraceResult(vec3dHit, null);
         if (ret) {
            res.typeOfHit = criticalPossible ? Type.ENTITY : Type.BLOCK;
         } else {
            res.typeOfHit = Type.MISS;
         }

         if (resultHit != null) {
            res.sideHit = resultHit.sideHit;
         }

         MeleeAttackResult meleeResult = new MeleeAttackResult(yawVec, aabbEnd, listEntitiesOnLine, GetMOP.normalizeRayTraceResult(res), ret);
         meleeResult.attackedEntities2 = listEntitiesOnEnd;
         return meleeResult;
      } else {
         RayTraceResult resx = new RayTraceResult(vec3dHit, null);
         resx.typeOfHit = criticalPossible ? Type.MISS : Type.BLOCK;
         if (resultHit != null) {
            resx.sideHit = resultHit.sideHit;
         }

         MeleeAttackResult meleeResult = new MeleeAttackResult(yawVec, aabbEnd, listEntitiesOnLine, GetMOP.normalizeRayTraceResult(resx), false);
         meleeResult.attackedEntities2 = listEntitiesOnEnd;
         return meleeResult;
      }
   }

   static boolean doMeleeSplashAroundAttack(
      IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand, float damage, float knockback, float sizeXZ, float sizeY, boolean isCritical
   ) {
      World world = player.world;
      Vec3d vec = GetMOP.entityCenterPos(player);
      AxisAlignedBB aabb = new AxisAlignedBB(
         vec.x - sizeXZ,
         vec.y - sizeY,
         vec.z - sizeXZ,
         vec.x + sizeXZ,
         vec.y + sizeY,
         vec.z + sizeXZ
      );
      List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
      if (!list.isEmpty()) {
         IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
         AttributeModifier modifier = new AttributeModifier("iw_attackmelee", damage, 0);
         entityAttribute.applyModifier(modifier);
         IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
         AttributeModifier modifierk = new AttributeModifier("iw_knockbackmelee", knockback, 0);
         if (knockback > 0.0F) {
            entityAttributek.applyModifier(modifierk);
         }

         stack.damageItem(1, player);
         boolean ret = false;

         for (Entity entity : list) {
            if (Weapons.canDealDamageTo(entity) && Team.checkIsOpponent(player, entity) && iweapon.attackEntityMelee(entity, stack, player, hand, isCritical)) {
               ret = true;
            }
         }

         entityAttribute.removeModifier(modifier);
         if (knockback > 0.0F) {
            entityAttributek.removeModifier(modifierk);
         }

         return ret;
      } else {
         return false;
      }
   }

   static boolean checkShieldAngle(ItemStack stack, EntityPlayer playerWithShield, DamageSource source) {
      float shieldAngle = WeaponParameters.getWeaponParameters(stack.getItem())
         .getEnchanted("shield_angle", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack));
      Vec3d vec1 = playerWithShield.getLookVec();
      Vec3d vec2 = playerWithShield.getPositionEyes(1.0F);
      Vec3d vec3;
      if (source instanceof WeaponDamage) {
         WeaponDamage weaponDamage = (WeaponDamage)source;
         if (weaponDamage.getIsThornsDamage()) {
            return false;
         }

         if (weaponDamage.damagePosition == null) {
            return false;
         }

         vec3 = weaponDamage.damagePosition;
      } else {
         if (source instanceof EntityDamageSource) {
            EntityDamageSource entityDamageSource = (EntityDamageSource)source;
            if (entityDamageSource.getIsThornsDamage()) {
               return false;
            }
         }

         Entity attacker = source.getImmediateSource() == null ? source.getTrueSource() : source.getImmediateSource();
         if (attacker == null) {
            return true;
         }

         vec3 = GetMOP.entityCenterPos(attacker);
      }

      double angle = GetMOP.getAngleBetweenVectors(
         vec1.x,
         vec1.y,
         vec1.z,
         vec3.x - vec2.x,
         vec3.y - vec2.y,
         vec3.z - vec2.z
      );
      return angle < shieldAngle;
   }

   default void guiClick(ItemStack itemstack, EntityLivingBase player, int mouseX, int mouseY, int mouseButton) {
   }

   default void receiveClientString(ItemStack itemstack, EntityLivingBase player, String string) {
   }

   // FIX: Fix issues related to `numberValue`
   static Predicate<EntityLivingBase> getSummonerFilter(ItemStack itemstack, int filterMode, String filterParamString, boolean inverted) {
      boolean percent = false;
      float numberValue = 0;
      filterParamString = filterParamString.toLowerCase();
      if (filterMode == 3 || filterMode == 4) {
         boolean floatt = false;
         if (filterParamString.contains("%")) {
            percent = true;
            filterParamString = filterParamString.replaceFirst("%", "");
         }

         if (filterParamString.contains(".") || filterParamString.contains(",")) {
            floatt = true;
            filterParamString = filterParamString.replaceFirst(".", "");
            filterParamString = filterParamString.replaceFirst(",", "");
         }

         try {
            if (floatt) {
               numberValue = Float.parseFloat(filterParamString);
            } else {
               numberValue = Integer.parseInt(filterParamString);
            }
         } catch (NumberFormatException var9) {
            return null;
         }
      }

      Predicate<EntityLivingBase> filter = null;
      if (inverted) {
         if (filterMode == 1) {
            final String finalString = filterParamString;
            filter = new Predicate<EntityLivingBase>() {
               public boolean apply(EntityLivingBase input) {
                  String entityname = EntityList.getEntityString(input).toLowerCase();
                  return entityname != null && !entityname.isEmpty() ? !entityname.contains(finalString) : false;
               }
            };
         } else if (filterMode == 2) {
            final String finalString = filterParamString;
            filter = new Predicate<EntityLivingBase>() {
               public boolean apply(EntityLivingBase input) {
                  String entityname = input.getName().toLowerCase();
                  return entityname != null && !entityname.isEmpty() ? !entityname.contains(finalString) : false;
               }
            };
         } else if (filterMode == 3) {
            final boolean finalpercent = percent;
            float finalNumberValue = numberValue;
            filter = new Predicate<EntityLivingBase>() {
               public boolean apply(EntityLivingBase input) {
                  return finalpercent ? input.getHealth() / input.getMaxHealth() >= finalNumberValue / 100.0F : input.getHealth() >= finalNumberValue;
               }
            };
         } else if (filterMode == 4) {
            final float finalValue = numberValue;
            filter = new Predicate<EntityLivingBase>() {
               public boolean apply(EntityLivingBase input) {
                  if (input instanceof AbstractMob) {
                     AbstractMob inmob = (AbstractMob)input;
                     return inmob.getLeadershipNeed() > finalValue;
                  } else {
                     return 0.0F > finalValue;
                  }
               }
            };
         } else if (filterMode == 5) {
            final String finalString = filterParamString;
            filter = new Predicate<EntityLivingBase>() {
               public boolean apply(EntityLivingBase input) {
                  String team = Team.getTeamFor(input);
                  return team.isEmpty() ? true : !team.contains(finalString);
               }
            };
         }
      } else if (filterMode == 1) {
         final String finalString = filterParamString;
         filter = new Predicate<EntityLivingBase>() {
            public boolean apply(EntityLivingBase input) {
               String entityname = EntityList.getEntityString(input).toLowerCase();
               return entityname != null && !entityname.isEmpty() ? entityname.contains(finalString) : false;
            }
         };
      } else if (filterMode == 2) {
         final String finalString = filterParamString;
         filter = new Predicate<EntityLivingBase>() {
            public boolean apply(EntityLivingBase input) {
               String entityname = input.getName().toLowerCase();
               return entityname != null && !entityname.isEmpty() ? entityname.contains(finalString) : false;
            }
         };
      } else if (filterMode == 3) {
         final boolean finalpercent = percent;
         float finalNumberValue1 = numberValue;
         filter = new Predicate<EntityLivingBase>() {
            public boolean apply(EntityLivingBase input) {
               return finalpercent ? input.getHealth() / input.getMaxHealth() < finalNumberValue1 / 100.0F : input.getHealth() < finalNumberValue1;
            }
         };
      } else if (filterMode == 4) {
         final float finalValue = numberValue;
         filter = new Predicate<EntityLivingBase>() {
            public boolean apply(EntityLivingBase input) {
               if (input instanceof AbstractMob) {
                  AbstractMob inmob = (AbstractMob)input;
                  return inmob.getLeadershipNeed() < finalValue;
               } else {
                  return true;
               }
            }
         };
      } else if (filterMode == 5) {
         final String finalString = filterParamString;
         filter = new Predicate<EntityLivingBase>() {
            public boolean apply(EntityLivingBase input) {
               String team = Team.getTeamFor(input);
               return team.isEmpty() ? false : team.contains(finalString);
            }
         };
      }

      return filter;
   }

   static boolean showDescription() {
      return true;
   }

   default WeaponParameters setDefaultDescription(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      tooltip.add("пїЅ7Damage: " + parameters.get("damage") + parameters.get("damage_ench") * EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
      tooltip.add(
         "пїЅ7Knockback: " + parameters.get("knockback") + parameters.get("knockback_ench") * EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack)
      );
      tooltip.add("пїЅ7Cooldown: " + this.getCooldownTime(stack));
      int reload = this.getReloadTime(stack);
      if (reload > 0) {
         tooltip.add("пїЅ7Reload time: " + reload);
      }

      Ln.translate("description_" + stack.getItem().getRegistryName().getPath());
      return parameters;
   }

   public static class MeleeAttackResult {
      @Nullable
      public Vec3d position;
      @Nullable
      public AxisAlignedBB axisAlignedBB;
      @Nullable
      public RayTraceResult raytrace;
      public boolean success;
      @Nullable
      public List<Entity> attackedEntities;
      @Nullable
      public List<Entity> attackedEntities2;

      public MeleeAttackResult(
         @Nullable Vec3d position,
         @Nullable AxisAlignedBB axisAlignedBB,
         @Nullable List<Entity> attackedEntities,
         @Nullable RayTraceResult raytrace,
         boolean success
      ) {
         this.position = position;
         this.axisAlignedBB = axisAlignedBB;
         this.attackedEntities = attackedEntities;
         this.raytrace = raytrace;
         this.success = success;
      }
   }

   public static enum WeaponHandleType {
      TWO_HANDED("two handed"),
      ONE_HANDED("one handed"),
      SEMI_ONE_HANDED("semi one handed"),
      NONE("none");

      private final String name;

      private WeaponHandleType(String name) {
         this.name = name;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static WeaponHandleType fromName(String name) {
         switch (name) {
            case "two handed":
               return TWO_HANDED;
            case "one handed":
               return ONE_HANDED;
            case "semi one handed":
               return SEMI_ONE_HANDED;
            default:
               return NONE;
         }
      }

      public String getName() {
         return this.name;
      }
   }
}
