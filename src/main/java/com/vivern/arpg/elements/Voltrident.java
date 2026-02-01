package com.vivern.arpg.elements;

import com.vivern.arpg.entity.BetweenParticle;
import com.vivern.arpg.entity.EntityStreamLaserP;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Voltrident extends ItemWeapon {
   public static ShardType shardneed = ShardType.ELECTRIC;
   ResourceLocation start = new ResourceLocation("arpg:textures/voltrident_beam.png");
   ResourceLocation sparkle = new ResourceLocation("arpg:textures/sparkle2.png");
   private static final UUID GUN_MODIFIER = UUID.fromString("134AA47C-B634-131C-7B9F-6020A9A54C5A");

   public Voltrident() {
      this.setRegistryName("voltrident");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("voltrident");
      this.setMaxDamage(7000);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack itemstack) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      int spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack);
      if (spec > 0 && equipmentSlot == EntityEquipmentSlot.MAINHAND) {
         int voltage = NBTHelper.GetNBTint(itemstack, "voltage");
         if (voltage > 0) {
            multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(GUN_MODIFIER, "Knockback resistance modifier", 0.5, 0));
         }
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      int voltage = NBTHelper.GetNBTint(itemstack, "voltage");
      if (voltage > 0) {
         NBTHelper.AddNBTint(itemstack, -1, "voltage");
      }

      if (!world.isRemote) {
         NBTHelper.GiveNBTint(itemstack, -1, "level_stop_at");
         int level_stop_at = NBTHelper.GetNBTint(itemstack, "level_stop_at");
         if (level_stop_at != -1 && entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            if (player.experienceLevel > level_stop_at) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               Weapons.addOrRemoveExperience(player, -parameters.geti("xp_decrease"));
            } else {
               NBTHelper.SetNBTint(itemstack, -1, "level_stop_at");
               world.playSound(
                  (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.ae_unpower, SoundCategory.AMBIENT, 1.0F, 1.0F
               );
            }
         }
      }

      this.setCanShoot(itemstack, entityIn);
      if (IWeapon.canShoot(itemstack)) {
         EntityPlayer player = (EntityPlayer)entityIn;
         Item itemIn = itemstack.getItem();
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         float mana = Mana.getMana(player);
         float power = Mana.getMagicPowerMax(player);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         int spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack);
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
         int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
         int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         boolean shootingSide = NBTHelper.GetNBTboolean(itemstack, "side");
         int level_stop_at = NBTHelper.GetNBTint(itemstack, "level_stop_at");
         boolean powerOn = level_stop_at != -1;
         float manacost = powerOn ? parameters.getEnchanted("manacost_powered", sor) : parameters.getEnchanted("manacost", sor);
         if (player.getHeldItemMainhand() == itemstack) {
            if (powerOn && player.ticksExisted % 7 == 0) {
               Weapons.setPlayerAnimationOnServer(player, 22, EnumHand.MAIN_HAND);
            }

            if (mana > manacost && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
               NBTHelper.GiveNBTboolean(itemstack, false, "side");
               NBTHelper.GiveNBTint(itemstack, 0, "voltage");
               boolean spendMana = true;
               double edist = parameters.getEnchanted("distance", range);
               Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, 0.4, 0.3);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.voltrident,
                  SoundCategory.AMBIENT,
                  0.7F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               if (!powerOn) {
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  Weapons.setPlayerAnimationOnServer(player, 27, EnumHand.MAIN_HAND);
               }

               double damageRadius = 0.4;
               EntityLivingBase overkill = null;
               AxisAlignedBB aabb = new AxisAlignedBB(
                  vec.x - damageRadius,
                  vec.y - damageRadius,
                  vec.z - damageRadius,
                  vec.x + damageRadius,
                  vec.y + damageRadius,
                  vec.z + damageRadius
               );
               List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
               int voltage_to_overkill = parameters.geti("voltage_to_overkill");
               if (!world.isRemote && !list.isEmpty()) {
                  for (Entity entity : list) {
                     if (Team.checkIsOpponent(player, entity)) {
                        Weapons.dealDamage(
                           new WeaponDamage(itemstack, player, null, false, false, player.getPositionEyes(1.0F), WeaponDamage.electric),
                           parameters.getEnchanted("damage", might) * power,
                           player,
                           entity,
                           true,
                           entity.isEntityAlive() ? parameters.getEnchanted("knockback", impulse) : 0.0F,
                           player.posX,
                           player.posY,
                           player.posZ
                        );
                        if (entity instanceof EntityLivingBase) {
                           EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                           if (entitylivingbase.deathTime > 0) {
                              NBTHelper.AddNBTint(itemstack, 6, "voltage");
                              world.playSound(
                                 (EntityPlayer)null,
                                 player.posX,
                                 player.posY,
                                 player.posZ,
                                 Sounds.plasma_railgun_impact,
                                 SoundCategory.AMBIENT,
                                 0.3F,
                                 1.2F + itemRand.nextFloat() / 5.0F
                              );
                           }

                           if (entitylivingbase.getHealth() <= 0.0F) {
                              DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ELECTRIC);
                              if (voltage > voltage_to_overkill
                                 && entitylivingbase.deathTime > 14
                                 && (entitylivingbase.hurtTime > 9150 || entitylivingbase.hurtTime < 9130)) {
                                 entitylivingbase.hurtTime = 9150;
                                 overkill = entitylivingbase;
                              }

                              entitylivingbase.motionX *= 0.8;
                              entitylivingbase.motionY *= 0.8;
                              entitylivingbase.motionZ *= 0.8;
                              if (powerOn) {
                                 spendMana = false;
                              }
                           }
                        }

                        entity.hurtResistantTime = 0;
                     }
                  }
               }

               if (!world.isRemote && overkill != null) {
                  List<EntityLivingBase> damaget = new ArrayList<>();
                  damaget.add(overkill);
                  double damradius = parameters.getEnchanted("damage_radius", range);

                  for (int i = 0; i < parameters.getEnchantedi("targets", spec); i++) {
                     AxisAlignedBB aabb2 = new AxisAlignedBB(
                        overkill.posX - damradius,
                        overkill.posY - damradius,
                        overkill.posZ - damradius,
                        overkill.posX + damradius,
                        overkill.posY + damradius,
                        overkill.posZ + damradius
                     );
                     List<EntityLivingBase> listo = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb2);
                     if (!listo.isEmpty()) {
                        for (EntityLivingBase entitylivingbasex : listo) {
                           Vec3d lastPos = new Vec3d(overkill.posX, overkill.posY + overkill.height / 2.0F, overkill.posZ);
                           Vec3d newPos = new Vec3d(
                              entitylivingbasex.posX,
                              entitylivingbasex.posY + entitylivingbasex.height / 2.0F,
                              entitylivingbasex.posZ
                           );
                           if (GetMOP.thereIsNoBlockCollidesBetween(world, lastPos, newPos, 1.0F, null, false)
                              && !damaget.contains(entitylivingbasex)
                              && Team.checkIsOpponent(entitylivingbasex, player)) {
                              Weapons.dealDamage(
                                 new WeaponDamage(itemstack, player, null, false, false, lastPos, WeaponDamage.electric),
                                 parameters.getEnchanted("damage_overkill", might) * power,
                                 player,
                                 entitylivingbasex,
                                 true
                              );
                              DeathEffects.applyDeathEffect(entitylivingbasex, DeathEffects.DE_ELECTRIC, 0.3F);
                              damaget.add(entitylivingbasex);
                              IWeapon.fireEffect(
                                 this,
                                 player,
                                 world,
                                 64.0,
                                 lastPos.x,
                                 lastPos.y,
                                 lastPos.z,
                                 0.0,
                                 0.0,
                                 10.0,
                                 newPos.x,
                                 newPos.y,
                                 newPos.z
                              );
                              overkill = entitylivingbasex;
                              world.playSound(
                                 (EntityPlayer)null,
                                 entitylivingbasex.posX,
                                 entitylivingbasex.posY,
                                 entitylivingbasex.posZ,
                                 Sounds.lightning_strike_a,
                                 SoundCategory.AMBIENT,
                                 2.3F,
                                 0.9F + itemRand.nextFloat() / 5.0F
                              );
                              break;
                           }
                        }
                     }
                  }
               }

               if (!player.capabilities.isCreativeMode && spendMana) {
                  Mana.changeMana(player, -manacost);
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
               }

               float horizoffset = powerOn ? 0.0F : (shootingSide ? 0.12F : -0.12F);
               NBTHelper.SetNBTboolean(itemstack, !shootingSide, "side");
               if (world.isRemote) {
                  for (int p = 0; p < 4; p++) {
                     int livetm = 15 + itemRand.nextInt(10);
                     float scl = 0.08F + (float)itemRand.nextGaussian() / 20.0F;
                     GUNParticle bigsmoke2 = new GUNParticle(
                        this.sparkle,
                        scl,
                        0.1F,
                        livetm,
                        240,
                        world,
                        vec.x,
                        vec.y,
                        vec.z,
                        (float)itemRand.nextGaussian() / 10.0F,
                        (float)itemRand.nextGaussian() / 10.0F + 0.06F,
                        (float)itemRand.nextGaussian() / 10.0F,
                        1.0F - itemRand.nextFloat() / 5.0F,
                        1.0F - itemRand.nextFloat() / 8.0F,
                        1.0F,
                        true,
                        itemRand.nextInt(360),
                        true,
                        1.9F
                     );
                     bigsmoke2.scaleTickAdding = -scl / livetm;
                     bigsmoke2.alphaGlowing = true;
                     world.spawnEntity(bigsmoke2);
                  }

                  EntityStreamLaserP laser = new EntityStreamLaserP(
                     world,
                     player,
                     this.start,
                     0.08F,
                     240,
                     0.8F,
                     0.9F,
                     1.0F,
                     0.5F,
                     player.getDistance(vec.x, vec.y, vec.z),
                     3,
                     0.3F,
                     8.0F
                  );
                  laser.setPosition(player.posX, player.posY + 1.55, player.posZ);
                  laser.horizOffset = horizoffset;
                  laser.horizontal = false;
                  laser.distanceStart = 0.6F;
                  world.spawnEntity(laser);
               }

               IWeapon.fireEffectExcl(
                  this,
                  player,
                  player,
                  world,
                  64.0,
                  vec.x,
                  vec.y,
                  vec.z,
                  player.rotationPitch,
                  player.rotationYaw,
                  horizoffset,
                  player.posX,
                  player.posY,
                  player.posZ
               );
            } else if (Keys.isKeyPressed(player, Keys.SECONDARYATTACK) && level_stop_at == -1) {
               int levelStopAtNew = CrystalStar.getLevelToStopEmpower(player, itemstack);
               if (levelStopAtNew != -1) {
                  world.playSound(
                     (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.ae_power, SoundCategory.AMBIENT, 1.0F, 1.0F
                  );
                  NBTHelper.SetNBTint(itemstack, levelStopAtNew, "level_stop_at");
               }
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (c == 10.0) {
         Vec3d vec1 = new Vec3d(x, y, z);
         Vec3d vec2 = new Vec3d(d1, d2, d3);
         BetweenParticle laser = new BetweenParticle(world, this.start, 0.15F, 240, 0.8F, 0.9F, 1.0F, 0.0F, vec1.distanceTo(vec2), 10, 0.3F, 8.0F, vec1, vec2);
         laser.setPosition(x, y, z);
         laser.alphaGlowing = true;
         world.spawnEntity(laser);
      } else {
         for (int p = 0; p < 4; p++) {
            int livetm = 15 + itemRand.nextInt(10);
            float scl = 0.08F + (float)itemRand.nextGaussian() / 20.0F;
            GUNParticle bigsmoke2 = new GUNParticle(
               this.sparkle,
               scl,
               0.1F,
               livetm,
               240,
               world,
               x,
               y,
               z,
               (float)itemRand.nextGaussian() / 10.0F,
               (float)itemRand.nextGaussian() / 10.0F + 0.06F,
               (float)itemRand.nextGaussian() / 10.0F,
               1.0F - itemRand.nextFloat() / 5.0F,
               1.0F - itemRand.nextFloat() / 8.0F,
               1.0F,
               true,
               itemRand.nextInt(360),
               true,
               1.9F
            );
            bigsmoke2.scaleTickAdding = -scl / livetm;
            bigsmoke2.alphaGlowing = true;
            world.spawnEntity(bigsmoke2);
         }

         double dd0 = d1 - x;
         double dd1 = d2 - y;
         double dd2 = d3 - z;
         double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
         EntityStreamLaserP laser = new EntityStreamLaserP(
            world, this.start, 0.08F, 240, 0.8F, 0.9F, 1.0F, 0.5F, dist, 3, 0.3F, 8.0F, (float)a, (float)b, player.ticksExisted
         );
         laser.setPosition(d1, d2 + 1.55, d3);
         laser.horizOffset = (float)c;
         laser.horizontal = false;
         laser.distanceStart = 0.7F;
         world.spawnEntity(laser);
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 1;
   }

   @Override
   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type == EnchantmentInit.enchantmentTypeWeapon;
   }
}
