//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements.armor;

import com.Vivern.Arpg.elements.models.AdamantiumArmorModel;
import com.Vivern.Arpg.elements.models.CoralArmorModel;
import com.Vivern.Arpg.elements.models.CrystalMantleModel;
import com.Vivern.Arpg.elements.models.HazardSuitModel;
import com.Vivern.Arpg.elements.models.IceHelmetModel;
import com.Vivern.Arpg.elements.models.NorthernArmorModel;
import com.Vivern.Arpg.elements.models.SnowcoatArmorModel;
import com.Vivern.Arpg.elements.models.ThundererArmorModel;
import com.Vivern.Arpg.elements.models.ToxiniumArmorModel;
import com.Vivern.Arpg.entity.EntityFiremageSetBonus;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.proxy.ClientProxy;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Armors {
   public static AbstractArmorSet firelordSET = new AbstractArmorSet("firelord", "helmet") {
      @Override
      public void onUpdateFullset(ARPGArmor item, ItemStack itemstack, World world, EntityLivingBase player, int itemSlot, boolean isSelected) {
         player.extinguish();
         if (player.ticksExisted % 60 == 0 && player.getHealth() < player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() / 2.0) {
            PotionEffect baff = new PotionEffect(PotionEffects.FIRE_AURA, 70);
            player.addPotionEffect(baff);
         }
      }

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (armorSlot == EntityEquipmentSlot.HEAD) {
            ModelBiped whm = ClientProxy.firelordhelmmodel;
            whm.isSneak = entityLiving.isSneaking();
            whm.isRiding = entityLiving.isRiding();
            whm.isChild = entityLiving.isChild();
            return whm;
         } else if (armorSlot == EntityEquipmentSlot.CHEST) {
            ModelBiped whm = ClientProxy.firelordchestmodel;
            whm.isSneak = entityLiving.isSneaking();
            whm.isRiding = entityLiving.isRiding();
            whm.isChild = entityLiving.isChild();
            return whm;
         } else {
            return null;
         }
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         if (armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.FEET) {
            return "arpg:textures/firelord_armor_model_tex2.png";
         } else if (armorSlot != EntityEquipmentSlot.HEAD) {
            return "arpg:textures/firelord_armor_model_tex1.png";
         } else {
            return type == "overlay" ? null : "arpg:textures/firelord_armor_model_tex1.png";
         }
      }
   };
   public static AbstractArmorSet northernSET = new AbstractArmorSet("northern_armor", "helmet") {
      public NorthernArmorModel armormodel = new NorthernArmorModel();
      public String armortexture = "arpg:textures/northern_armor_model_tex.png";

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         this.armormodel.setModelAttributes(model);
         this.armormodel.helm.showModel = armorSlot == EntityEquipmentSlot.HEAD;
         this.armormodel.chest.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.leftarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.leftleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.rightboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         this.armormodel.leftboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         return this.armormodel;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortexture;
      }
   };
   public static AbstractArmorSet hazardSET = new AbstractArmorSet("hazard_suit", "helmet") {
      public HazardSuitModel armormodel = new HazardSuitModel();
      public String armortexture = "arpg:textures/hazard_suit_model_tex.png";

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         this.armormodel.setModelAttributes(model);
         this.armormodel.helm.showModel = armorSlot == EntityEquipmentSlot.HEAD;
         this.armormodel.chest.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.leftarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.leftleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.rightboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         this.armormodel.leftboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         return this.armormodel;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortexture;
      }

      @Override
      public float onHurtWithItem(
         ARPGArmor item, float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source, EntityEquipmentSlot armorSlot
      ) {
         if (armorSlot == EntityEquipmentSlot.LEGS && source == DamageSource.FALL && isCharged(stack)) {
            return Math.max(hurtdamage - 8.0F, 0.0F);
         } else {
            return armorSlot == EntityEquipmentSlot.FEET && source == DamageSource.HOT_FLOOR && isCharged(stack)
               ? Math.max(hurtdamage - 8.0F, 0.0F)
               : hurtdamage;
         }
      }

      @Override
      public void onUpdateFullset(ARPGArmor item, ItemStack itemstack, World world, EntityLivingBase entity, int itemSlot, boolean isSelected) {
         if (entity.ticksExisted % 40 == 0) {
            Collection<PotionEffect> collection = entity.getActivePotionEffects();
            Potion potion = null;
            int amp = 0;

            for (PotionEffect effect : collection) {
               if (effect.getPotion().isBadEffect()) {
                  potion = effect.getPotion();
                  amp = effect.getAmplifier();
                  break;
               }
            }

            if (potion != null) {
               Weapons.mixPotion(
                  entity,
                  potion,
                  100.0F,
                  0.0F,
                  Weapons.EnumPotionMix.WITH_MINIMUM,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumMathOperation.MINUS,
                  Weapons.EnumMathOperation.MINUS,
                  10,
                  0
               );
               EntityLivingBase enemy = GetMOP.findNearestEnemy(world, entity, entity.posX, entity.posY, entity.posZ, 6.0, true);
               if (enemy != null) {
                  enemy.addPotionEffect(new PotionEffect(potion, 100, amp));
               }
            }
         }
      }
   };
   public static AbstractArmorSet slimeSET = new AbstractArmorSet("slime", "helmet") {
      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         return null;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return armorSlot == EntityEquipmentSlot.LEGS ? "arpg:textures/slime_armor_2.png" : "arpg:textures/slime_armor_1.png";
      }
   };
   public static AbstractArmorSet firemageSET = new AbstractArmorSet("firemage", "hat") {
      @Override
      public void onUpdateFullset(ARPGArmor item, ItemStack itemstack, World world, EntityLivingBase player, int itemSlot, boolean isSelected) {
         player.extinguish();
         if (!world.isRemote && player.ticksExisted % 100 == 0) {
            float power = player instanceof EntityPlayer ? Mana.getMagicPowerMax((EntityPlayer)player) : 1.0F;
            EntityFiremageSetBonus entit = new EntityFiremageSetBonus(player.world, player);
            entit.shoot(player, player.rotationPitch, player.rotationYaw, 0.5F, 0.001F, 3.8F);
            entit.setPosition(player.posX, player.posY + 1.0, player.posZ);
            entit.magicPower = power;
            world.spawnEntity(entit);
         }
      }

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (armorSlot == EntityEquipmentSlot.HEAD) {
            ModelBiped whm = ClientProxy.firehatmodel;
            whm.isSneak = entityLiving.isSneaking();
            whm.isRiding = entityLiving.isRiding();
            whm.isChild = entityLiving.isChild();
            return whm;
         } else if (armorSlot == EntityEquipmentSlot.CHEST) {
            ModelBiped whm = ClientProxy.magichoodie;
            whm.isSneak = entityLiving.isSneaking();
            whm.isRiding = entityLiving.isRiding();
            whm.isChild = entityLiving.isChild();
            return whm;
         } else {
            return null;
         }
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         if (armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.FEET) {
            return "arpg:textures/fire_mage_chestplate_model_tex.png";
         } else {
            return armorSlot == EntityEquipmentSlot.HEAD ? "arpg:textures/fire_mage_hat_model_tex.png" : "arpg:textures/fire_mage_armor2.png";
         }
      }
   };
   public static AbstractArmorSet iceSET = new AbstractArmorSet("ice_armor", "helmet") {
      public IceHelmetModel icemodel = new IceHelmetModel();

      @Override
      public float onHurtWithItem(
         ARPGArmor item, float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source, EntityEquipmentSlot armorSlot
      ) {
         if (!player.world.isRemote
            && !player.getCooldownTracker().hasCooldown(item)
            && this.isFullset(player)
            && player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == stack
            && source.getTrueSource() != null
            && source.getTrueSource() instanceof EntityLivingBase) {
            double damageRadius = 2.0;
            AxisAlignedBB axisalignedbb = player.getEntityBoundingBox()
               .expand(damageRadius * 2.0, damageRadius, damageRadius * 2.0)
               .offset(-damageRadius, -damageRadius * 0.5, -damageRadius);
            List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
            if (!list.isEmpty()) {
               for (EntityLivingBase entitylivingbase : list) {
                  if (Team.checkIsOpponent(player, entitylivingbase)) {
                     player.getCooldownTracker().setCooldown(item, 70);
                     Weapons.dealDamage(
                        new WeaponDamage(stack, player, null, false, false, player, WeaponDamage.frost),
                        8.0F * Mana.getMagicPowerMax(player),
                        player,
                        entitylivingbase,
                        true,
                        0.9F,
                        player.posX,
                        player.posY,
                        player.posZ
                     );
                     entitylivingbase.hurtResistantTime = 0;
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
                     DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING, 0.4F);
                  }
               }
            }
         }

         return hurtdamage;
      }

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (armorSlot == EntityEquipmentSlot.HEAD) {
            this.icemodel.setModelAttributes(model);
            return this.icemodel;
         } else {
            return null;
         }
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return armorSlot == EntityEquipmentSlot.LEGS ? "arpg:textures/ice_armor_2.png" : "arpg:textures/ice_armor_1.png";
      }
   };
   public static AbstractArmorSet wizardSET = new AbstractArmorSet("wizard", "hat") {
      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (armorSlot == EntityEquipmentSlot.HEAD) {
            ModelBiped whm = ClientProxy.wizardhatmodel;
            whm.isSneak = entityLiving.isSneaking();
            whm.isRiding = entityLiving.isRiding();
            whm.isChild = entityLiving.isChild();
            return whm;
         } else {
            return null;
         }
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         if (armorSlot == EntityEquipmentSlot.CHEST || armorSlot == EntityEquipmentSlot.FEET) {
            return "arpg:textures/wizard_armor.png";
         } else if (armorSlot != EntityEquipmentSlot.HEAD) {
            return "arpg:textures/wizard_armor2.png";
         } else {
            return type == "overlay" ? null : "arpg:textures/wizard_hat_model_tex.png";
         }
      }
   };
   public static AbstractArmorSet adamantiumSET = new AbstractArmorSet("adamantium_armor", "helmet") {
      public AdamantiumArmorModel armormodel = new AdamantiumArmorModel();
      public String armortexture = "arpg:textures/adamantium_armor_model_tex.png";

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         this.armormodel.setModelAttributes(model);
         this.armormodel.helm.showModel = armorSlot == EntityEquipmentSlot.HEAD;
         this.armormodel.chest.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.leftarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.leftleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.rightboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         this.armormodel.leftboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         return this.armormodel;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortexture;
      }
   };
   public static AbstractArmorSet toxiniumSET = new AbstractArmorSet("toxinium", "helmet") {
      public ToxiniumArmorModel armormodel = new ToxiniumArmorModel();
      public String armortexture = "arpg:textures/toxinium_armor_model_tex.png";

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         this.armormodel.setModelAttributes(model);
         this.armormodel.helm.showModel = armorSlot == EntityEquipmentSlot.HEAD;
         this.armormodel.chest.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.leftarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.leftleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.rightboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         this.armormodel.leftboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         return this.armormodel;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortexture;
      }

      @Override
      public void onUpdateWearing(ARPGArmor item, ItemStack itemstack, World world, EntityLivingBase entity, int itemSlot, boolean isSelected) {
         if (entity.ticksExisted % 40 == 0 && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (player.inventory.armorInventory.get(3) == itemstack && isCharged((ItemStack)player.inventory.armorInventory.get(3))) {
               Weapons.mixPotion(
                  player,
                  PotionEffects.TOXIN,
                  30.0F,
                  0.0F,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumPotionMix.ABSOLUTE,
                  Weapons.EnumMathOperation.MINUS,
                  Weapons.EnumMathOperation.MINUS,
                  0,
                  0
               );
               if (((ItemStack)player.inventory.armorInventory.get(2)).getItem() == ItemsRegister.TOXINIUMCHEST
                  && isCharged((ItemStack)player.inventory.armorInventory.get(2))
                  && ((ItemStack)player.inventory.armorInventory.get(1)).getItem() == ItemsRegister.TOXINIUMLEGS
                  && isCharged((ItemStack)player.inventory.armorInventory.get(1))
                  && ((ItemStack)player.inventory.armorInventory.get(0)).getItem() == ItemsRegister.TOXINIUMBOOTS
                  && isCharged((ItemStack)player.inventory.armorInventory.get(0))) {
                  Weapons.mixPotion(
                     player,
                     PotionEffects.CHLORITE,
                     200.0F,
                     2.0F,
                     Weapons.EnumPotionMix.ABSOLUTE,
                     Weapons.EnumPotionMix.ABSOLUTE,
                     Weapons.EnumMathOperation.MINUS,
                     Weapons.EnumMathOperation.MINUS,
                     0,
                     0
                  );
                  Weapons.mixPotion(
                     player,
                     MobEffects.SLOWNESS,
                     100.0F,
                     0.0F,
                     Weapons.EnumPotionMix.ABSOLUTE,
                     Weapons.EnumPotionMix.ABSOLUTE,
                     Weapons.EnumMathOperation.MINUS,
                     Weapons.EnumMathOperation.MINUS,
                     0,
                     0
                  );
                  Weapons.mixPotion(
                     player,
                     MobEffects.WEAKNESS,
                     100.0F,
                     0.0F,
                     Weapons.EnumPotionMix.ABSOLUTE,
                     Weapons.EnumPotionMix.ABSOLUTE,
                     Weapons.EnumMathOperation.MINUS,
                     Weapons.EnumMathOperation.MINUS,
                     0,
                     0
                  );
               }
            }
         }
      }
   };
   public static AbstractArmorSet coralSET = new AbstractArmorSet("coral_armor", "helmet") {
      public CoralArmorModel armormodel = new CoralArmorModel();
      public String armortexture = "arpg:textures/coral_armor_model_tex.png";

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         this.armormodel.setModelAttributes(model);
         this.armormodel.helm.showModel = armorSlot == EntityEquipmentSlot.HEAD;
         this.armormodel.chest.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.leftarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.leftleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.rightboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         this.armormodel.leftboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         return this.armormodel;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortexture;
      }
   };
   public static AbstractArmorSet crystalSET = new AbstractArmorSet("crystal_mantle", "helmet") {
      public CrystalMantleModel armormodel = new CrystalMantleModel();
      public String armortexture = "arpg:textures/crystal_mantle_model_tex.png";

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         this.armormodel.setModelAttributes(model);
         this.armormodel.helm.showModel = armorSlot == EntityEquipmentSlot.HEAD;
         this.armormodel.chest.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.leftarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.leftleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.rightboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         this.armormodel.leftboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         return this.armormodel;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortexture;
      }
   };
   public static AbstractArmorSet snowcoatSET = new AbstractArmorSet("snowcoat_armor", "helmet") {
      public SnowcoatArmorModel armormodel = new SnowcoatArmorModel();
      public String[] armortextures = new String[]{
         "arpg:textures/snowcoat_armor_model_tex.png", "arpg:textures/snowcoat_armor_model_tex_blue.png", "arpg:textures/snowcoat_armor_model_tex_white.png"
      };

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         this.armormodel.setModelAttributes(model);
         this.armormodel.helm.showModel = armorSlot == EntityEquipmentSlot.HEAD;
         this.armormodel.chest.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.leftarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.leftleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.rightboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         this.armormodel.leftboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         return this.armormodel;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortextures[MathHelper.clamp(NBTHelper.GetNBTint(stack, "colorid"), 0, 2)];
      }

      @Override
      public void constructItem(ARPGArmor item) {
         item.addPropertyOverride(new ResourceLocation("type"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
               return NBTHelper.GetNBTint(stack, "colorid");
            }
         });
      }
   };
   public static AbstractArmorSet thundererSET = new AbstractArmorSet("thunderer_armor", "helmet") {
      public ThundererArmorModel armormodel = new ThundererArmorModel();
      public String armortexture = "arpg:textures/thunderer_armor_model_tex.png";

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         this.armormodel.setModelAttributes(model);
         this.armormodel.helm.showModel = armorSlot == EntityEquipmentSlot.HEAD;
         this.armormodel.chest.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.leftarm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
         this.armormodel.rightleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.leftleg.showModel = armorSlot == EntityEquipmentSlot.LEGS;
         this.armormodel.rightboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         this.armormodel.leftboot.showModel = armorSlot == EntityEquipmentSlot.FEET;
         return this.armormodel;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortexture;
      }
   };
   public static AbstractArmorSet SET = new AbstractArmorSet("northern_armor", "helmet") {
      public HazardSuitModel armormodel = new HazardSuitModel();
      public String armortexture = "arpg:textures/hazard_suit_model_tex.png";

      @Override
      public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
         if (armorSlot == EntityEquipmentSlot.HEAD) {
         }

         if (armorSlot == EntityEquipmentSlot.CHEST) {
         }

         if (armorSlot == EntityEquipmentSlot.LEGS) {
         }

         if (armorSlot == EntityEquipmentSlot.FEET) {
         }

         return null;
      }

      @Override
      public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
         return this.armortexture;
      }
   };

   public static void initAttributes() {
      slimeSET.setParameters(500, 8, AbstractArmorSet.EnumArmorRepairRatio.NORMAL)
         .modif(0, 15.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 4.0, SharedMonsterAttributes.ARMOR_TOUGHNESS)
         .modif(0, 0.1, SharedMonsterAttributes.MOVEMENT_SPEED)
         .modif(0, 0.19, PropertiesRegistry.JUMP_HEIGHT)
         .createArmorItems();
      wizardSET.setParameters(400, 16, AbstractArmorSet.EnumArmorRepairRatio.EXPENSIVE)
         .modif(0, 13.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 19.0, PropertiesRegistry.MANA_MAX)
         .modif(0, 0.4, PropertiesRegistry.MANASPEED_MAX)
         .modif(0, 0.4, PropertiesRegistry.MAGIC_POWER_MAX)
         .createArmorItems();
      firelordSET.setParameters(3000, 7, AbstractArmorSet.EnumArmorRepairRatio.CHEAP)
         .modif(0, 15.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 3.5, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 8.0, SharedMonsterAttributes.MAX_HEALTH)
         .modif(0, 0.4, SharedMonsterAttributes.KNOCKBACK_RESISTANCE)
         .modif(2, -0.2, SharedMonsterAttributes.MOVEMENT_SPEED)
         .createArmorItems();
      firemageSET.setParameters(900, 20, AbstractArmorSet.EnumArmorRepairRatio.CHEAP)
         .modif(0, 12.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 2.0, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 28.0, PropertiesRegistry.MANA_MAX)
         .modif(0, 0.4, PropertiesRegistry.MANASPEED_MAX)
         .modif(0, 0.4, PropertiesRegistry.MAGIC_POWER_MAX)
         .createArmorItems();
      iceSET.setParameters(1300, 10, AbstractArmorSet.EnumArmorRepairRatio.NORMAL)
         .modif(0, 14.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 3.5, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 0.12, SharedMonsterAttributes.MOVEMENT_SPEED)
         .modif(0, 0.2, PropertiesRegistry.JUMP_HEIGHT)
         .modif(0, 0.4, SharedMonsterAttributes.ATTACK_SPEED)
         .createArmorItems();
      northernSET.setParameters(4000, 7, AbstractArmorSet.EnumArmorRepairRatio.NORMAL)
         .modif(0, 13.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 6.5, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 10.0, SharedMonsterAttributes.MAX_HEALTH)
         .modif(0, 0.6, SharedMonsterAttributes.KNOCKBACK_RESISTANCE)
         .modif(2, -0.16, SharedMonsterAttributes.MOVEMENT_SPEED)
         .modif(0, 0.2, SharedMonsterAttributes.ATTACK_SPEED)
         .modif(2, 0.1, SharedMonsterAttributes.ATTACK_DAMAGE)
         .createArmorItems();
      snowcoatSET.setParameters(1100, 22, AbstractArmorSet.EnumArmorRepairRatio.NORMAL)
         .modif(0, 10.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 2.0, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 36.0, PropertiesRegistry.MANA_MAX)
         .modif(0, 0.6, PropertiesRegistry.MANASPEED_MAX)
         .modif(0, 0.5, PropertiesRegistry.MAGIC_POWER_MAX)
         .createArmorItems();
      toxiniumSET.setParameters(5000, 1, AbstractArmorSet.EnumArmorRepairRatio.CHEAP)
         .modif(0, 15.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 9.0, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 14.0, SharedMonsterAttributes.MAX_HEALTH)
         .modif(0, 0.08, SharedMonsterAttributes.MOVEMENT_SPEED)
         .modif(0, 0.26, PropertiesRegistry.JUMP_HEIGHT)
         .modif(0, 0.06, PropertiesRegistry.AIRBORNE_MOBILITY)
         .modif(0, 0.4, SharedMonsterAttributes.ATTACK_SPEED)
         .setElectric(250000, 5000)
         .createArmorItems();
      hazardSET.setParameters(1600, 8, AbstractArmorSet.EnumArmorRepairRatio.CHEAP)
         .modif(0, 15.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 6.0, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 6.0, SharedMonsterAttributes.MAX_HEALTH)
         .modif(0, 45.0, PropertiesRegistry.MANA_MAX)
         .modif(0, 0.5, PropertiesRegistry.MANASPEED_MAX)
         .modif(0, 0.45, PropertiesRegistry.MAGIC_POWER_MAX)
         .modif(0, 0.08, SharedMonsterAttributes.MOVEMENT_SPEED)
         .setElectric(250000, 5000)
         .createArmorItems();
      adamantiumSET.setParameters(7000, 6, AbstractArmorSet.EnumArmorRepairRatio.NORMAL)
         .modif(0, 20.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 12.0, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 16.0, SharedMonsterAttributes.MAX_HEALTH)
         .modif(2, -0.24, SharedMonsterAttributes.MOVEMENT_SPEED)
         .modif(2, 0.2, SharedMonsterAttributes.ATTACK_DAMAGE)
         .modif(0, 0.9, SharedMonsterAttributes.KNOCKBACK_RESISTANCE)
         .createArmorItems();
      crystalSET.setParameters(2000, 22, AbstractArmorSet.EnumArmorRepairRatio.EXPENSIVE)
         .modif(0, 18.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 8.0, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 10.0, SharedMonsterAttributes.MAX_HEALTH)
         .modif(0, 54.0, PropertiesRegistry.MANA_MAX)
         .modif(0, 0.7, PropertiesRegistry.MANASPEED_MAX)
         .modif(0, 0.4, PropertiesRegistry.MAGIC_POWER_MAX)
         .modif(0, 0.1, SharedMonsterAttributes.MOVEMENT_SPEED)
         .createArmorItems();
      coralSET.setParameters(2200, 20, AbstractArmorSet.EnumArmorRepairRatio.EXPENSIVE)
         .modif(0, 18.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 11.0, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 14.0, SharedMonsterAttributes.MAX_HEALTH)
         .modif(0, 64.0, PropertiesRegistry.MANA_MAX)
         .modif(0, 0.7, PropertiesRegistry.MANASPEED_MAX)
         .modif(0, 0.4, PropertiesRegistry.MAGIC_POWER_MAX)
         .modif(0, 0.16, SharedMonsterAttributes.MOVEMENT_SPEED)
         .modif(0, 0.4, EntityPlayer.SWIM_SPEED)
         .modif(0, 0.04, PropertiesRegistry.AIRBORNE_MOBILITY)
         .createArmorItems();
      thundererSET.setParameters(13000, 10, AbstractArmorSet.EnumArmorRepairRatio.NORMAL)
         .modif(0, 20.0, SharedMonsterAttributes.ARMOR)
         .modif(0, 15.0, PropertiesRegistry.ARMOR_PROTECTION)
         .modif(0, 20.0, SharedMonsterAttributes.MAX_HEALTH)
         .modif(0, 0.16, SharedMonsterAttributes.MOVEMENT_SPEED)
         .modif(0, 0.06, PropertiesRegistry.AIRBORNE_MOBILITY)
         .modif(2, 0.3, SharedMonsterAttributes.ATTACK_DAMAGE)
         .modif(0, 1.0, SharedMonsterAttributes.KNOCKBACK_RESISTANCE)
         .modif(0, 75.0, PropertiesRegistry.MANA_MAX)
         .modif(0, 0.7, PropertiesRegistry.MANASPEED_MAX)
         .modif(0, 0.4, PropertiesRegistry.MAGIC_POWER_MAX)
         .createArmorItems();
   }

   static {
      initAttributes();
   }
}
