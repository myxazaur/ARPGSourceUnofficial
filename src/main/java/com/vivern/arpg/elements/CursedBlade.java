package com.vivern.arpg.elements;

import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.SweepParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CursedBlade extends ItemWeapon {
   static ResourceLocation sweeptex = new ResourceLocation("arpg:textures/sweep2.png");

   public CursedBlade() {
      this.setRegistryName("cursed_blade");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("cursed_blade");
      this.setMaxDamage(3500);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      this.setCanShoot(stack, entityLiving);
      if (IWeapon.canShoot(stack)) {
         EntityPlayer player = (EntityPlayer)entityLiving;
         World world = entityLiving.world;
         if (!player.getCooldownTracker().hasCooldown(this)) {
            if (!world.isRemote) {
               Weapons.setPlayerAnimationOnServer(player, 2, EnumHand.MAIN_HAND);
            }

            float sweepingAdd = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, stack) / 4.0F;
            int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, stack);
            int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
            Vec3d vec = GetMOP.PosRayTrace(3.5, 1.0F, player, 0.7 + sweepingAdd, 0.5);
            double damageRadius = 0.7 + sweepingAdd;
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
            player.getCooldownTracker().setCooldown(this, 18 - rapidity * 2 - (int)((attackspeed - 4.0) * 2.0));
            if (world.isRemote) {
               SweepParticle particle = new SweepParticle(
                  sweeptex,
                  1.9F + itemRand.nextFloat() / 2.0F,
                  6,
                  1,
                  4,
                  2,
                  170,
                  world,
                  vec.x,
                  vec.y,
                  vec.z,
                  player.rotationPitch,
                  player.rotationYaw,
                  0.8F,
                  0.2F,
                  0.85F,
                  false,
                  player.getHeldItemMainhand() == stack ? 180 - itemRand.nextInt(65) - 10 : itemRand.nextInt(65) + 10
               );
               world.spawnEntity(particle);
            }

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
                  0.8F + itemRand.nextFloat() / 5.0F
               );
               float crit = 0.0F;
               if (itemRand.nextFloat() < 0.33) {
                  crit = 11.0F;
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.cursed_blade_crit,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
               }

               int sharpAdd = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
               IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
               AttributeModifier modifier = new AttributeModifier(
                  "cursedblade", 9.0 + EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack) + crit, 0
               );
               entityAttribute.applyModifier(modifier);
               IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
               AttributeModifier modifierk = new AttributeModifier("cursedblade_k", knockback / 2.0, 0);
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
                     if (world.isRemote && crit > 0.0F) {
                        for (int i = 0; i < 10; i++) {
                           world.spawnParticle(
                              EnumParticleTypes.CRIT,
                              entitylivingbase.posX + (itemRand.nextDouble() - 0.5) * entitylivingbase.width,
                              entitylivingbase.posY + itemRand.nextDouble() * entitylivingbase.height - 0.25,
                              entitylivingbase.posZ + (itemRand.nextDouble() - 0.5) * entitylivingbase.width,
                              (itemRand.nextDouble() - 0.5) * 2.0,
                              itemRand.nextDouble() - 0.5,
                              (itemRand.nextDouble() - 0.5) * 2.0,
                              new int[0]
                           );
                        }
                     }
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
                  0.8F + itemRand.nextFloat() / 5.0F
               );
            }
         }
      }

      return true;
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
