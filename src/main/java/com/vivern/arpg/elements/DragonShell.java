package com.vivern.arpg.elements;

import com.vivern.arpg.elements.armor.IItemAttacked;
import com.vivern.arpg.entity.StandardBullet;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.SuperKnockback;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DragonShell extends ItemWeapon implements IItemAttacked {
   static ResourceLocation explode = new ResourceLocation("arpg:textures/void_explode.png");
   static ResourceLocation whirl = new ResourceLocation("arpg:textures/whirl.png");

   public DragonShell() {
      this.setRegistryName("dragon_shell");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("dragon_shell");
      this.setMaxDamage(960);
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

   public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
      Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
      if (NBTHelper.GetNBTint(stack, "blocking") > 0) {
         if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("916DD27B-A123-455F-8C7F-6112A1B50A4C"), "Shield speed mh", -0.04, 0)
            );
         }

         if (equipmentSlot == EntityEquipmentSlot.OFFHAND) {
            multimap.put(
               SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
               new AttributeModifier(UUID.fromString("134CA27A-B123-501F-4D8F-3782C6B52C0A"), "Shield speed oh", -0.04, 0)
            );
         }
      }

      return multimap;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            this.decreaseReload(itemstack, player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            float acclvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            NBTHelper.GiveNBTint(itemstack, 0, "blocking");
            NBTHelper.GiveNBTint(itemstack, itemRand.nextInt(9000), "eyerand");
            int blocks = NBTHelper.GetNBTint(itemstack, "blocking");
            if (player.getHeldItemMainhand() == itemstack && click || player.getHeldItemOffhand() == itemstack && click2) {
               if (!player.getCooldownTracker().hasCooldown(this)) {
                  WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                  if (blocks <= 0) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.shield_block,
                        SoundCategory.AMBIENT,
                        0.4F,
                        0.95F + itemRand.nextFloat() / 10.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                     NBTHelper.SetNBTint(itemstack, parameters.geti("max_hits"), "blocking");
                     player.addExhaustion(0.6F);
                  } else {
                     if (player.ticksExisted % 7 == 0) {
                        Weapons.setPlayerAnimationOnServer(player, 18, player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                     }

                     Vec3d vec = GetMOP.entityCenterPos(player);
                     double damageRadius = parameters.getEnchanted("teleport_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        vec.x - damageRadius,
                        vec.y - damageRadius,
                        vec.z - damageRadius,
                        vec.x + damageRadius,
                        vec.y + damageRadius,
                        vec.z + damageRadius
                     );
                     List<Entity> list = world.getEntitiesWithinAABB(Entity.class, aabb);
                     boolean effect = false;

                     for (Entity en : list) {
                        if (Math.abs(en.motionX) > 0.01F || Math.abs(en.motionY) > 0.01F || Math.abs(en.motionZ) > 0.01F) {
                           boolean use = false;
                           if (en instanceof EntityThrowable) {
                              if (Team.checkIsOpponent(player, ((EntityThrowable)en).getThrower())) {
                                 use = true;
                              }
                           } else if (en instanceof EntityFireball) {
                              if (Team.checkIsOpponent(player, ((EntityFireball)en).shootingEntity)) {
                                 use = true;
                              }
                           } else if (en instanceof EntityArrow) {
                              if (Team.checkIsOpponent(player, ((EntityArrow)en).shootingEntity) && !Weapons.isArrowInGround((EntityArrow)en)) {
                                 use = true;
                              }
                           } else if (en instanceof StandardBullet && Team.checkIsOpponent(player, ((StandardBullet)en).getThrower())) {
                              use = true;
                           }

                           if (use) {
                              double x = en.posX;
                              double y = en.posY;
                              double z = en.posZ;
                              double teleport_distance = parameters.get("teleport_distance");

                              for (int i = 0; i < 8; i++) {
                                 en.setPosition(
                                    x + itemRand.nextGaussian() * teleport_distance,
                                    y + itemRand.nextGaussian() * teleport_distance,
                                    z + itemRand.nextGaussian() * teleport_distance
                                 );
                                 if (en.getEntityBoundingBox() == null || !world.collidesWithAnyBlock(en.getEntityBoundingBox())) {
                                    if (itemRand.nextFloat()
                                       < parameters.getEnchanted(
                                          "projectile_spend_hit_chance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack)
                                       )) {
                                       NBTHelper.AddNBTint(itemstack, -1, "blocking");
                                    }

                                    if (NBTHelper.GetNBTint(itemstack, "blocking") <= 0) {
                                       player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                                    }
                                    break;
                                 }
                              }

                              world.playSound(
                                 (EntityPlayer)null,
                                 en.posX,
                                 en.posY,
                                 en.posZ,
                                 SoundEvents.ENTITY_ENDERMEN_TELEPORT,
                                 SoundCategory.NEUTRAL,
                                 0.7F,
                                 0.95F + itemRand.nextFloat() / 5.0F
                              );
                              effect = true;
                           }
                        }
                     }

                     if (effect) {
                        IWeapon.fireEffect(
                           this, player, world, 64.0, vec.x, vec.y, vec.z, damageRadius, 0.0, 0.0, 0.0, 0.0, 0.0
                        );
                     }
                  }
               }
            } else if (blocks > 0) {
               NBTHelper.SetNBTint(itemstack, 0, "blocking");
               player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
            }
         }
      }
   }

   @Override
   public float onAttackedWithItem(float hurtdamage, ItemStack stack, EntityPlayer player, DamageSource source) {
      if (NBTHelper.GetNBTint(stack, "blocking") > 0) {
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
         float damageBlocks = parameters.getEnchanted("damage_reduce", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
         Entity attacker = source.getImmediateSource() == null ? source.getTrueSource() : source.getImmediateSource();
         Vec3d vec1 = player.getLookVec();
         if (!IWeapon.checkShieldAngle(stack, player, source)) {
            if (attacker != null
               && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack) > 0
               && attacker instanceof EntityLivingBase
               && attacker.getDistanceSq(player) < 25.0) {
               this.teleportAway(player.world, (EntityLivingBase)attacker, player, parameters);
            }

            return hurtdamage;
         } else {
            int blocking = NBTHelper.GetNBTint(stack, "blocking") - 1;
            if (attacker != null
               || source != DamageSource.CRAMMING
                  && source != DamageSource.DROWN
                  && source != DamageSource.FALL
                  && source != DamageSource.HOT_FLOOR
                  && source != DamageSource.IN_FIRE
                  && source != DamageSource.IN_WALL
                  && source != DamageSource.LAVA
                  && source != DamageSource.MAGIC
                  && source != DamageSource.ON_FIRE
                  && source != DamageSource.OUT_OF_WORLD
                  && source != DamageSource.STARVE
                  && source != DamageSource.WITHER) {
               if (player.hurtResistantTime > 0) {
                  return 0.0F;
               } else {
                  if (attacker != null) {
                     SuperKnockback.applyShieldBlock(
                        player,
                        attacker,
                        parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack)),
                        parameters.get("self_knockback")
                     );
                  }

                  NBTHelper.AddNBTint(stack, -1, "blocking");
                  if (blocking <= 0) {
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_break,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                     player.getCooldownTracker().setCooldown(this, this.getCooldownTime(stack));
                  } else {
                     player.world
                        .playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.shield_hit_soft,
                           SoundCategory.AMBIENT,
                           0.6F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                  }

                  player.addExhaustion(0.1F);
                  if (!player.capabilities.isCreativeMode) {
                     stack.damageItem(1, player);
                  }

                  if (attacker != null
                     && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack) > 0
                     && attacker instanceof EntityLivingBase
                     && attacker.getDistanceSq(player) < 25.0) {
                     this.teleportAway(player.world, (EntityLivingBase)attacker, player, parameters);
                  }

                  return hurtdamage - damageBlocks;
               }
            } else {
               return hurtdamage;
            }
         }
      } else {
         return hurtdamage;
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      AxisAlignedBB aabb = new AxisAlignedBB(x - a, y - a, z - a, x + a, y + a, z + a);
      List<Entity> list = world.getEntitiesWithinAABB(Entity.class, aabb);
      int count = 10;

      for (Entity en : list) {
         if ((Math.abs(en.motionX) > 0.01F || Math.abs(en.motionY) > 0.01F || Math.abs(en.motionZ) > 0.01F)
            && (
               en instanceof EntityThrowable
                  || en instanceof EntityFireball
                  || en instanceof EntityArrow && !Weapons.isArrowInGround((EntityArrow)en)
                  || en instanceof StandardBullet
            )) {
            count--;
            double xx = en.posX;
            double yy = en.posY;
            double zz = en.posZ;
            GUNParticle particle = new GUNParticle(
               explode,
               0.05F,
               0.0F,
               10,
               240,
               world,
               xx,
               yy,
               zz,
               0.0F,
               0.0F,
               0.0F,
               0.8F + itemRand.nextFloat() / 10.0F,
               0.9F + itemRand.nextFloat() / 10.0F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.scaleTickAdding = 0.07F;
            particle.alphaTickAdding = -0.1F;
            particle.randomDeath = false;
            world.spawnEntity(particle);
            particle = new GUNParticle(
               whirl,
               0.01F,
               0.0F,
               10,
               240,
               world,
               xx,
               yy,
               zz,
               0.0F,
               0.0F,
               0.0F,
               0.1F,
               0.9F + itemRand.nextFloat() / 10.0F,
               0.7F,
               true,
               itemRand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.randomDeath = false;
            particle.tracker = EnderProtector.ssh1;
            world.spawnEntity(particle);
            if (count <= 0) {
               break;
            }
         }
      }
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp(
         (float)NBTHelper.GetNBTint(itemstack, "blocking") / WeaponParameters.getWeaponParameters(itemstack.getItem()).geti("max_hits"), 0.0F, 1.0F
      );
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "blocking") > 0;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public boolean cancelOnNull() {
      return true;
   }

   public void teleportAway(World worldIn, EntityLivingBase entityLiving, EntityPlayer player, WeaponParameters parameters) {
      if (!worldIn.isRemote) {
         double maxTpDistanceSq = 144.0;
         double mobTeleportDistance = parameters.get("mob_teleport_distance");
         double mobTeleportDistance2 = mobTeleportDistance * 2.0;
         double d0 = entityLiving.posX;
         double d1 = entityLiving.posY;
         double d2 = entityLiving.posZ;

         for (int i = 0; i < 16; i++) {
            Vec3d look = GetMOP.YawToVec3d(player.rotationYaw).scale(mobTeleportDistance);
            double d3 = look.x + entityLiving.posX + (entityLiving.getRNG().nextDouble() - 0.5) * mobTeleportDistance2;
            double d4 = MathHelper.clamp(
               entityLiving.posY + (entityLiving.getRNG().nextInt((int)mobTeleportDistance2) - (int)mobTeleportDistance),
               0.0,
               worldIn.getActualHeight() - 1
            );
            double d5 = look.z + entityLiving.posZ + (entityLiving.getRNG().nextDouble() - 0.5) * mobTeleportDistance2;
            if (entityLiving.getDistanceSq(d3, d4, d5) < maxTpDistanceSq) {
               if (entityLiving.isRiding()) {
                  entityLiving.dismountRidingEntity();
               }

               if (entityLiving.attemptTeleport(d3, d4, d5)) {
                  worldIn.playSound((EntityPlayer)null, d0, d1, d2, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                  entityLiving.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                  break;
               }
            }
         }
      }
   }
}
