package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.elements.models.IceHelmetModel;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IceHelm extends ItemArmor implements IItemHurted {
   private static final UUID[] ARMOR_MODIFIERSG = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   public static ArmorMaterial ice_mt = EnumHelper.addArmorMaterial("arpg:ice_mt", "arpg:ice_tx", 9, new int[]{3, 4, 4, 3}, 6, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F);
   public static IceHelmetModel icemodel = new IceHelmetModel();

   public IceHelm() {
      super(ice_mt, 0, EntityEquipmentSlot.HEAD);
      this.setRegistryName("ice_armor_helmet");
      this.setTranslationKey("ice_armor_helmet");
      this.setMaxDamage(1000);
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.COMBAT);
      BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, ItemArmor.DISPENSER_BEHAVIOR);
   }

   public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (equipmentSlot == this.armorType) {
         multimap.put(
            SharedMonsterAttributes.ARMOR.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0)
         );
         multimap.put(
            PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0)
         );
         multimap.put(
            SharedMonsterAttributes.ATTACK_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0)
         );
         multimap.put(
            PropertiesRegistry.ARMOR_PROTECTION.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0)
         );
      }

      return multimap;
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
      if (armorSlot != EntityEquipmentSlot.HEAD) {
         return null;
      } else if (itemStack != ItemStack.EMPTY) {
         icemodel.setModelAttributes(model);
         return icemodel;
      } else {
         return null;
      }
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return "arpg:textures/ice_armor_1.png";
   }

   @Override
   public float onHurtWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (!player.getCooldownTracker().hasCooldown(this)
         && ((ItemStack)player.inventory.armorInventory.get(3)).getItem() == ItemsRegister.ICEHELM
         && ((ItemStack)player.inventory.armorInventory.get(2)).getItem() == ItemsRegister.ICECHEST
         && ((ItemStack)player.inventory.armorInventory.get(1)).getItem() == ItemsRegister.ICELEGS
         && ((ItemStack)player.inventory.armorInventory.get(0)).getItem() == ItemsRegister.ICEBOOTS
         && source.getTrueSource() != null
         && source.getTrueSource() instanceof EntityLivingBase
         && !player.world.isRemote) {
         double damageRadius = 2.0;
         AxisAlignedBB axisalignedbb = player.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius * 0.5, -damageRadius);
         List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (Team.checkIsOpponent(player, entitylivingbase)) {
                  SuperKnockback.applyKnockback(entitylivingbase, 0.9F, player.posX, player.posY, player.posZ);
                  IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                  double baseValue = entityAttribute.getBaseValue();
                  entityAttribute.setBaseValue(1.0);
                  entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), 8.0F * Mana.getMagicPowerMax(player));
                  entitylivingbase.hurtResistantTime = 0;
                  entityAttribute.setBaseValue(baseValue);
                  player.getCooldownTracker().setCooldown(this, 70);
                  PotionEffect lastdebaff = Weapons.mixPotion(
                     entitylivingbase,
                     PotionEffects.FREEZING,
                     60.0F,
                     3.0F,
                     Weapons.EnumPotionMix.ABSOLUTE,
                     Weapons.EnumPotionMix.WITH_MAXIMUM,
                     Weapons.EnumMathOperation.PLUS,
                     Weapons.EnumMathOperation.PLUS,
                     0,
                     10
                  );
                  Freezing.tryPlaySound(entitylivingbase, lastdebaff);
                  if (entitylivingbase.getHealth() <= 0.0F && itemRand.nextFloat() < 0.4) {
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
                  }
               }
            }
         }
      }

      return hurtdamage;
   }
}
