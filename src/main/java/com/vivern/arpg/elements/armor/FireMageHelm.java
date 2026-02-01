package com.vivern.arpg.elements.armor;

import com.vivern.arpg.entity.EntityFiremageSetBonus;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.proxy.ClientProxy;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FireMageHelm extends ItemArmor {
   private static final UUID[] ARMOR_MODIFIERSG = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   public static ArmorMaterial firemage_mt = EnumHelper.addArmorMaterial(
      "arpg:firemage_mt", "arpg:firemage_tx", 9, new int[]{2, 3, 4, 3}, 7, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1.0F
   );

   public FireMageHelm() {
      super(firemage_mt, 0, EntityEquipmentSlot.HEAD);
      this.setRegistryName("firemage_hat");
      this.setTranslationKey("firemage_hat");
      this.setMaxDamage(700);
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
            SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(),
            new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor toughness", this.toughness, 0)
         );
         multimap.put(
            PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.1, 0)
         );
         multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 8.0, 0));
         multimap.put(
            PropertiesRegistry.MANASPEED_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.1, 0)
         );
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         World world = player.getEntityWorld();
         float power = Mana.getMagicPowerMax(player);
         if (((ItemStack)player.inventory.armorInventory.get(3)).getItem() == ItemsRegister.FIREMAGEHELM
            && ((ItemStack)player.inventory.armorInventory.get(2)).getItem() == ItemsRegister.FIREMAGECHEST
            && ((ItemStack)player.inventory.armorInventory.get(1)).getItem() == ItemsRegister.FIREMAGELEGS
            && ((ItemStack)player.inventory.armorInventory.get(0)).getItem() == ItemsRegister.FIREMAGEBOOTS) {
            player.extinguish();
            if (!worldIn.isRemote && entityIn.ticksExisted % 100 == 0) {
               EntityFiremageSetBonus entit = new EntityFiremageSetBonus(player.world, player);
               entit.shoot(player, player.rotationPitch, player.rotationYaw, 0.5F, 0.001F, 3.8F);
               entit.setPosition(player.posX, player.posY + 1.0, player.posZ);
               world.spawnEntity(entit);
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
      if (armorSlot != EntityEquipmentSlot.HEAD) {
         return null;
      } else if (itemStack != ItemStack.EMPTY) {
         ModelBiped whm = ClientProxy.firehatmodel;
         whm.isSneak = entityLiving.isSneaking();
         whm.isRiding = entityLiving.isRiding();
         whm.isChild = entityLiving.isChild();
         return whm;
      } else {
         return null;
      }
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
      return slot == EntityEquipmentSlot.HEAD ? "arpg:textures/fire_mage_hat_model_tex.png" : null;
   }
}
