//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Team;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class SummonedSnowman extends EntitySummoned {
   public static ResourceLocation displayicon = new ResourceLocation("arpg:textures/snowman_display_icon.png");
   public final ItemStack weaponstack;

   public SummonedSnowman(World world) {
      super(world);
      this.setSize(0.7F, 1.7F);
      this.weaponstack = new ItemStack(ItemsRegister.CONIFERROD);
   }

   public SummonedSnowman(World world, double x, double y, double z) {
      super(world);
      this.setSize(0.7F, 1.7F);
      this.setPositionAndUpdate(x, y, z);
      this.weaponstack = new ItemStack(ItemsRegister.CONIFERROD);
   }

   public SummonedSnowman(World world, double x, double y, double z, EntityPlayer owner, ItemStack itemstack) {
      super(world);
      this.setSize(0.7F, 1.7F);
      this.setPositionAndUpdate(x, y, z);
      this.setOwner(owner);
      this.allowedFollow = true;
      this.followPlayerMaxRange = 20.0;
      this.followPlayerMinRange = 11.0;
      this.team = Team.getTeamFor(owner);
      this.weaponstack = itemstack;
   }

   @Override
   public ResourceLocation getDisplayIcon() {
      return displayicon;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_SNOWMAN_HURT;
   }

   protected SoundEvent getDeathSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_SNOWMAN_DEATH;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.ticksExisted > 1800) {
         this.expelling();
      }

      if (this.ticksExisted % 20 == 0) {
         double max = Double.MAX_VALUE;
         EntityLivingBase targ = null;
         double damageRadius = 17.0;
         AxisAlignedBB axisalignedbb = this.getEntityBoundingBox()
            .expand(damageRadius * 2.0, damageRadius * 2.0, damageRadius * 2.0)
            .offset(-damageRadius, -damageRadius, -damageRadius);
         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityLivingBase entitylivingbase : list) {
               if (GetMOP.findEntityOnPath(
                        15.0,
                        this.getPositionEyes(1.0F),
                        entitylivingbase.getPositionVector().add(0.0, entitylivingbase.height / 2.0F, 0.0),
                        1.0F,
                        this,
                        true,
                        0.1,
                        0.01
                     )
                     == entitylivingbase
                  && entitylivingbase.isCreatureType(EnumCreatureType.MONSTER, false)
                  && entitylivingbase != this.getOwner()
                  && EntitySelectors.NOT_SPECTATING.apply(entitylivingbase)
                  && entitylivingbase.getHealth() > 0.0F) {
                  double dist = entitylivingbase.getDistance(this);
                  if (dist < max) {
                     max = dist;
                     targ = entitylivingbase;
                  }
               }
            }
         }

         this.setAttackTarget(targ);
      }

      if (this.owner != null && this.owner.isDead) {
         this.setDead();
      }
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int enchMight = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, this.weaponstack);
      int enchImpulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, this.weaponstack);
      int enchRapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, this.weaponstack);
      this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(17.0);
      this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25 + enchRapidity / 20.0);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5 + enchMight);
      this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0);
      this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0 + enchMight);
      this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(4.0 + enchRapidity / 2.0);
      this.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK).setBaseValue(enchImpulse);
   }

   protected void initEntityAI() {
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0, false));
   }

   public void onDeath(DamageSource cause) {
      super.onDeath(cause);
      int enchReuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, this.weaponstack);
      if (this.world.getGameRules().getBoolean("doMobLoot") && enchReuse > 0) {
         this.entityDropItem(new ItemStack(Items.SNOWBALL, this.rand.nextInt(3 + enchReuse * 2) + 1), 0.0F);
      }
   }
}
