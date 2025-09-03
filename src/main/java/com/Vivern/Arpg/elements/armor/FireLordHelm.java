//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.proxy.ClientProxy;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FireLordHelm extends ItemArmor {
   private static final UUID[] ARMOR_MODIFIERSG = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   public static ArmorMaterial firelord_mt = EnumHelper.addArmorMaterial(
      "arpg:firelord_mt", "arpg:firelord_tx", 9, new int[]{3, 4, 5, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F
   );

   public FireLordHelm() {
      super(firelord_mt, 0, EntityEquipmentSlot.HEAD);
      this.setRegistryName("firelord_helmet");
      this.setTranslationKey("firelord_helmet");
      this.setMaxDamage(2200);
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
            SharedMonsterAttributes.MAX_HEALTH.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor health", 2.0, 0)
         );
         multimap.put(
            SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor knockback resistance", 0.1, 0)
         );
         multimap.put(
            SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor move speed", -0.05, 2)
         );
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         World world = player.getEntityWorld();
         if (((ItemStack)player.inventory.armorInventory.get(3)).getItem() == ItemsRegister.FIRELORDHELM
            && ((ItemStack)player.inventory.armorInventory.get(2)).getItem() == ItemsRegister.FIRELORDCHEST
            && ((ItemStack)player.inventory.armorInventory.get(1)).getItem() == ItemsRegister.FIRELORDLEGS
            && ((ItemStack)player.inventory.armorInventory.get(0)).getItem() == ItemsRegister.FIRELORDBOOTS) {
            player.extinguish();
            if (player.ticksExisted % 60 == 0 && player.getHealth() < player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() / 2.0
               )
             {
               PotionEffect baff = new PotionEffect(PotionEffects.FIRE_AURA, 70);
               player.addPotionEffect(baff);
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
      if (armorSlot != EntityEquipmentSlot.HEAD) {
         return null;
      } else if (itemStack != ItemStack.EMPTY) {
         ModelBiped whm = ClientProxy.firelordhelmmodel;
         whm.isSneak = entityLiving.isSneaking();
         whm.isRiding = entityLiving.isRiding();
         whm.isChild = entityLiving.isChild();
         return whm;
      } else {
         return null;
      }
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      if (type == "overlay") {
         return null;
      } else {
         return slot == EntityEquipmentSlot.HEAD ? "arpg:textures/firelord_armor_model_tex1.png" : null;
      }
   }
}
