package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityLaserParticle;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.renders.SweepParticle;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GlowBlade extends ItemWeapon {
   static ResourceLocation texture = new ResourceLocation("arpg:textures/generic_beam3.png");
   static ResourceLocation sweeptex = new ResourceLocation("arpg:textures/sweep2.png");

   public GlowBlade() {
      this.setRegistryName("glow_blade");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("glow_blade");
      this.setMaxDamage(1700);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      this.setCanShoot(itemstack, entityIn);
      if (IWeapon.canShoot(itemstack)) {
         EntityPlayer player = (EntityPlayer)entityIn;
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         int damage = itemstack.getItemDamage();
         boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
         boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
         float mana = Mana.getMana(player);
         float spee = Mana.getManaSpeed(player);
         float power = Mana.getMagicPowerMax(player);
         int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
         if (player.getHeldItemMainhand() == itemstack && damage < this.getMaxDamage() && !hascooldown) {
            NBTHelper.GiveNBTboolean(itemstack, false, "aiming");
            if (click) {
               this.sweep(player, EnumHand.MAIN_HAND, itemstack);
               NBTHelper.SetNBTboolean(itemstack, false, "aiming");
            }

            if (click2 && mana > 1.0F - sor / 6.0F) {
               if (!player.capabilities.isCreativeMode) {
                  Mana.changeMana(player, -1.0F + sor / 6.0F);
                  Mana.setManaSpeed(player, 0.001F);
                  itemstack.damageItem(1, player);
               }

               worldIn.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.glow_blade,
                  SoundCategory.PLAYERS,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               int coold = (int)Math.ceil(this.getCooldownTime(itemstack) / 1.5F);
               player.getCooldownTracker().setCooldown(this, coold);
               NBTHelper.SetNBTboolean(itemstack, true, "aiming");
               double edist = 17.0 + acc * 3;
               Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, 0.4, 0.3);
               double damageRadius = 0.3;
               AxisAlignedBB aabb = new AxisAlignedBB(
                  vec.x - damageRadius,
                  vec.y - damageRadius,
                  vec.z - damageRadius,
                  vec.x + damageRadius,
                  vec.y + damageRadius,
                  vec.z + damageRadius
               );
               List<EntityLivingBase> list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
               float horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? 0.2F : -0.2F;
               IWeapon.fireEffect(
                  this,
                  player,
                  worldIn,
                  64.0,
                  vec.x,
                  vec.y,
                  vec.z,
                  (double)player.rotationPitch,
                  (double)player.rotationYaw,
                  (double)horizoffset,
                  player.posX,
                  player.posY,
                  player.posZ
               );
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     if (Team.checkIsOpponent(entitylivingbase, player) && !worldIn.isRemote) {
                        IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                        double baseValue = entityAttribute.getBaseValue();
                        entityAttribute.setBaseValue(1.0);
                        entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), (4.5F + might) * power);
                        entitylivingbase.hurtResistantTime = 0;
                        entityAttribute.setBaseValue(baseValue);
                        SuperKnockback.applyKnockback(
                           entitylivingbase,
                           1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack) / 3.0F,
                           player.posX,
                           player.posY,
                           player.posZ
                        );
                     }
                  }
               }
            }
         }
      }
   }

   public void sweep(EntityPlayer player, EnumHand hand, ItemStack stack) {
      World world = player.world;
      float sweepingAdd = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, stack) / 3.0F;
      int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
      Vec3d vec = GetMOP.PosRayTrace(3.5, 1.0F, player, 0.6 + sweepingAdd, 0.4);
      double damageRadius = 0.6 + sweepingAdd;
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
      IWeapon.fireEffect(
         this,
         player,
         world,
         64.0,
         vec.x,
         vec.y,
         vec.z,
         (double)(itemRand.nextBoolean() ? 180 - itemRand.nextInt(40) : itemRand.nextInt(40)),
         0.0,
         0.0,
         0.0,
         0.0,
         0.0
      );
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
         float crit = 0.0F;
         if (player.isInWater() && itemRand.nextFloat() < 0.2F) {
            crit = 4.0F;
            world.playSound(
               (EntityPlayer)null,
               player.posX,
               player.posY,
               player.posZ,
               SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
               SoundCategory.PLAYERS,
               0.7F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
         }

         int sharpAdd = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
         IAttributeInstance entityAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
         AttributeModifier modifier = new AttributeModifier("glowblade", 8.0 + sharpAdd + crit, 0);
         entityAttribute.applyModifier(modifier);
         IAttributeInstance entityAttributek = player.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK);
         AttributeModifier modifierk = new AttributeModifier("glowblade_k", 0.2 + knockback / 2.0, 0);
         entityAttributek.applyModifier(modifierk);

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
               if (crit > 0.0F && world.isRemote) {
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
         entityAttributek.removeModifier(modifierk);
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

      player.getCooldownTracker().setCooldown(this, this.getCooldownTime(stack) - (int)((attackspeed - 4.0) * 3.0));
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (c == 0.0) {
         SweepParticle particle = new SweepParticle(
            sweeptex,
            1.1F + itemRand.nextFloat() / 2.0F,
            6,
            1,
            4,
            2,
            (int)(player.getBrightness() * 150.0F),
            world,
            x,
            y,
            z,
            player.rotationPitch,
            player.rotationYaw,
            0.5F,
            0.7F,
            0.7F,
            false,
            (int)a
         );
         world.spawnEntity(particle);
      } else {
         double dd0 = d1 - x;
         double dd1 = d2 - y;
         double dd2 = d3 - z;
         double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
         EntityLaserParticle laser = new EntityLaserParticle(world, texture, 0.17F, 220, 0.95F, 1.0F, 0.5F, 0.0F, dist, 5, 1.0F, (float)a, (float)b);
         laser.setPosition(d1, d2 + 1.55, d3);
         laser.horizOffset = (float)c;
         laser.horizontal = false;
         world.spawnEntity(laser);
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
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 13 - rapidity * 2;
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
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }
}
