package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityButterfly;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.Weapons;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class Butterfly extends ItemWeapon {
   public Butterfly() {
      this.setRegistryName("sword_butterfly");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("sword_butterfly");
      this.setMaxDamage(4000);
      this.setMaxStackSize(1);
   }

   public int getMaxItemUseDuration(ItemStack itemstack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.BOW;
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return true;
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int damage = itemstack.getItemDamage();
            World world = player.getEntityWorld();
            Item itemIn = itemstack.getItem();
            EnumHand hand = player.getActiveHand();
            boolean click = Mouse.isButtonDown(0);
            float mana = Mana.getMana(player);
            float spee = Mana.getManaSpeed(player);
            boolean clickb = Mouse.isButtonDown(1);
            boolean selected = player.getHeldItemMainhand() == itemstack;
            if (selected && mana > 5.0F && clickb && !player.getCooldownTracker().hasCooldown(itemIn)) {
               PotionEffect baff = new PotionEffect(MobEffects.SPEED, 200, 2);
               player.addPotionEffect(baff);
               player.getCooldownTracker().setCooldown(this, 15);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.magic_a,
                  SoundCategory.AMBIENT,
                  1.0F,
                  0.7F / (itemRand.nextFloat() * 0.4F + 0.8F)
               );
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -5.0F);
                  Mana.setManaSpeed(player, 0.001F);
               }
            }

            if (selected && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
               if (mana > 0.5F) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.swosh_a,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F / (itemRand.nextFloat() * 0.4F + 0.5F)
                  );
                  player.addStat(StatList.getObjectUseStats(this));
                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -0.5F);
                     Mana.setManaSpeed(player, 0.001F);
                  }

                  if (!world.isRemote) {
                     EntityButterfly entit = new EntityButterfly(world, player);
                     entit.shoot(player, player.rotationPitch, player.rotationYaw, 3.0F, 1.5F, 0.3F);
                     world.spawnEntity(entit);
                  }
               }

               this.sweep(player, hand, itemstack);
               Weapons.setPlayerAnimationOnServer(player, 1, EnumHand.MAIN_HAND);
            }
         }
      }
   }

   public void sweep(EntityPlayer player, EnumHand hand, ItemStack stack) {
      World world = player.world;
      float sweepingAdd = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, stack) / 5.0F;
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, stack);
      int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
      Vec3d vec = GetMOP.PosRayTrace(3.8, 1.0F, player, 0.3 + sweepingAdd, 0.2);
      double damageRadius = 0.4 + sweepingAdd;
      double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
      AxisAlignedBB aabb = new AxisAlignedBB(
         vec.x - damageRadius,
         vec.y - damageRadius,
         vec.z - damageRadius,
         vec.x + damageRadius,
         vec.y + damageRadius,
         vec.z + damageRadius
      );
      List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
      player.getCooldownTracker().setCooldown(this, 7 - rapidity - (int)((attackspeed - 4.0) * 2.0));
      if (!list.isEmpty()) {
         stack.damageItem(1, player);
         world.playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.melee_sword,
            SoundCategory.PLAYERS,
            0.7F,
            1.1F + itemRand.nextFloat() / 5.0F
         );
         int sharpAdd = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
         IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
         AttributeModifier modifier = new AttributeModifier("butterfly", 5.0 + sharpAdd, 0);
         entityAttribute.applyModifier(modifier);
         IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
         AttributeModifier modifierk = new AttributeModifier("butterfly_k", knockback / 2.0, 0);
         if (knockback > 0) {
            entityAttributek.applyModifier(modifierk);
         }

         for (EntityLivingBase entitylivingbase : list) {
            if (entitylivingbase != player && Team.checkIsOpponent(entitylivingbase, player)) {
               int artropodsAdd = (int)(
                  entitylivingbase.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD
                     ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 1.5F
                     : 0.0F
               );
               int holyAdd = (int)(
                  entitylivingbase.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD
                     ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 1.5F
                     : 0.0F
               );
               entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), (float)entityAttribute.getAttributeValue() + artropodsAdd + holyAdd);
               entitylivingbase.hurtResistantTime = 0;
               entitylivingbase.setFire(4 * EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack));
            }
         }

         entityAttribute.removeModifier(modifier);
         if (knockback > 0) {
            entityAttributek.removeModifier(modifierk);
         }
      } else {
         world.playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.melee_miss_sword,
            SoundCategory.PLAYERS,
            0.6F,
            1.1F + itemRand.nextFloat() / 5.0F
         );
      }
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }
}
