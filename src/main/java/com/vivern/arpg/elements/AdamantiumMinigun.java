package com.vivern.arpg.elements;

import com.vivern.arpg.elements.animation.EnumFlick;
import com.vivern.arpg.elements.animation.Flicks;
import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.ColorConverters;
import com.vivern.arpg.main.DeathEffects;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Team;
import com.vivern.arpg.main.WeaponDamage;
import com.vivern.arpg.main.WeaponParameters;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.BulletParticle;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AdamantiumMinigun extends ItemWeapon {
   public static int maxammo = 256;
   private static final UUID GUN_MODIFIER = UUID.fromString("243AA47C-B634-131C-8F9B-6020A9A54C5A");

   public AdamantiumMinigun() {
      this.setRegistryName("adamantium_minigun");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("adamantium_minigun");
      this.setMaxDamage(8500);
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
      if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
         int speed = NBTHelper.GetNBTint(itemstack, "speed");
         multimap.put(
            SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(GUN_MODIFIER, "Speed modifier", -0.25 * (speed / 60.0) - 0.02, 2)
         );
      }

      return multimap;
   }

   public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
      return new AnimationCapabilityProvider();
   }

   @Override
   public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
      if (state == 0) {
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.SHOOT, 0, Integer.MAX_VALUE, 0, 0);
      } else if (state < 60) {
         Flicks.instance.setTendency(player, slot, EnumFlick.SHOOT, Math.min(state / 4, 15));
      } else if (state == 70) {
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.RELOAD, 0, 65, -1, 65);
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (world.isRemote) {
         if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            if (Flicks.instance.confirmPack(player).get(itemSlot, EnumFlick.SHOOT) == null) {
               Flicks.instance.setClientAnimation(player, itemSlot, EnumFlick.SHOOT, 0, Integer.MAX_VALUE, 0, 0);
            } else {
               int speed = NBTHelper.GetNBTint(itemstack, "speed");
               Flicks.instance.setTendency(player, itemSlot, EnumFlick.SHOOT, Math.min(speed / 3, 30));
            }
         }
      } else {
         boolean shooted = false;
         this.setCanShoot(itemstack, entityIn);
         boolean coolingMode = NBTHelper.GetNBTboolean(itemstack, "coolingMode");
         int reuse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack);
         int heat = NBTHelper.GetNBTint(itemstack, "heat");
         if (coolingMode || reuse == 0) {
            this.decrementHeat(itemstack, heat, 1);
         } else if (heat < 0) {
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (entityIn.ticksExisted % Math.max(reuse * parameters.geti("reuse_cooling_delay"), 1) == 0) {
               this.decrementHeat(itemstack, heat, 1);
            }
         } else {
            this.decrementHeat(itemstack, heat, 1);
         }

         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack);
            int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
            int speed = NBTHelper.GetNBTint(itemstack, "speed");
            this.decreaseReload(itemstack, player);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
            boolean isInHand = player.getHeldItemMainhand() == itemstack;
            WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
            if (heat >= 0) {
               if (isInHand && reuse > 0 && Keys.isKeyPressed(player, Keys.SECONDARYATTACK) && player.isSneaking() && !hascooldown) {
                  NBTHelper.GiveNBTboolean(itemstack, false, "coolingMode");
                  NBTHelper.SetNBTboolean(itemstack, !coolingMode, "coolingMode");
                  player.getCooldownTracker().setCooldown(this, 8);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.item_misc_d,
                     SoundCategory.AMBIENT,
                     0.9F,
                     coolingMode ? 0.8F : 1.0F + itemRand.nextFloat() / 5.0F
                  );
               }

               if (click && isInHand) {
                  if (ammo > 0 && this.isReloaded(itemstack)) {
                     if (speed < 60) {
                        NBTHelper.GiveNBTint(itemstack, 0, "speed");
                        int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
                        int addSpeed = rapidity == 1 ? (player.ticksExisted % 2 == 0 ? 1 : 0) : (rapidity > 1 ? 1 : 0);
                        NBTHelper.AddNBTint(itemstack, 1 + addSpeed, "speed");
                     }

                     if (!hascooldown) {
                        String bulletname = NBTHelper.GetNBTstring(itemstack, "bullet");
                        ItemBullet bullet = ItemBullet.getItemBulletFromString(bulletname);
                        boolean nonullbullet = bullet != null;
                        float damageadd = 0.0F;
                        float knockbackadd = 0.0F;
                        if (nonullbullet) {
                           damageadd = bullet.damage;
                           knockbackadd = bullet.knockback;
                        }

                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.adamantium_minigun,
                           SoundCategory.AMBIENT,
                           0.9F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                        player.addStat(StatList.getObjectUseStats(this));
                        IWeapon.fireBomEffect(this, player, world, 0);
                        Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
                        if (heat < parameters.geti("heat_to_melee")) {
                           NBTHelper.GiveNBTint(itemstack, 0, "heat");
                           NBTHelper.AddNBTint(itemstack, 5, "heat");
                        } else {
                           NBTHelper.SetNBTint(itemstack, -heat, "heat");
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              SoundEvents.BLOCK_FIRE_EXTINGUISH,
                              SoundCategory.AMBIENT,
                              0.9F,
                              0.9F + itemRand.nextFloat() / 5.0F
                           );
                        }

                        shooted = true;
                        if (!player.capabilities.isCreativeMode) {
                           this.addAmmo(ammo, itemstack, -1);
                           itemstack.damageItem(1, player);
                        }

                        double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                        double damageRadius = 0.2;
                        double spread = parameters.getEnchanted("inaccuracy", acc) - parameters.getEnchanted("spread_reduction", acc) * speed;
                        float rotP = player.rotationPitch + (float)(itemRand.nextGaussian() * spread);
                        float rotY = player.rotationYaw + (float)(itemRand.nextGaussian() * spread);
                        Vec3d vec = GetMOP.RotatedPosRayTrace(edist, 1.0F, player, 0.2, 0.2, rotP, rotY);
                        if (nonullbullet) {
                           bullet.onImpact(world, player, vec.x, vec.y, vec.z, null, null);
                        }

                        boolean collidesWithAny = false;
                        boolean playedSound = false;
                        AxisAlignedBB aabb = new AxisAlignedBB(
                           vec.x - damageRadius,
                           vec.y - damageRadius,
                           vec.z - damageRadius,
                           vec.x + damageRadius,
                           vec.y + damageRadius,
                           vec.z + damageRadius
                        );
                        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
                        if (world.collidesWithAnyBlock(aabb)) {
                           collidesWithAny = true;
                           world.playSound(
                              (EntityPlayer)null,
                              vec.x,
                              vec.y,
                              vec.z,
                              Sounds.bullet_a,
                              SoundCategory.AMBIENT,
                              0.9F,
                              0.85F + itemRand.nextFloat() / 5.0F
                           );
                           playedSound = true;
                        }

                        if (!list.isEmpty()) {
                           for (Entity entity : list) {
                              if (Team.checkIsOpponent(player, entity)) {
                                 if (!playedSound) {
                                    world.playSound(
                                       (EntityPlayer)null,
                                       vec.x,
                                       vec.y,
                                       vec.z,
                                       Sounds.bullet_a,
                                       SoundCategory.AMBIENT,
                                       0.9F,
                                       0.95F + itemRand.nextFloat() / 5.0F
                                    );
                                    playedSound = true;
                                 }

                                 Weapons.dealDamage(
                                    new WeaponDamage(itemstack, player, null, false, true, player, WeaponDamage.bullet),
                                    parameters.getEnchanted("damage_ranged", might) + damageadd * parameters.get("bullet_damage"),
                                    player,
                                    entity,
                                    true,
                                    parameters.getEnchanted("knockback_ranged", impulse) + knockbackadd * parameters.get("bullet_knockback"),
                                    player.posX,
                                    player.posY,
                                    player.posZ
                                 );
                                 entity.hurtResistantTime = 0;
                                 collidesWithAny = true;
                                 if (nonullbullet) {
                                    bullet.onDamageCause(world, entity, player, null);
                                 }

                                 DeathEffects.applyDeathEffect(entity, DeathEffects.DE_DISMEMBER, 0.2F);
                              }
                           }
                        }

                        int c = nonullbullet ? ColorConverters.RGBtoDecimal(bullet.colorR, bullet.colorG, bullet.colorB) : 16777215;
                        IBlockState dustState = GetMOP.firstBlockStateContains(world, aabb, GetMOP.SOLID_BLOCKS);
                        Vec3d shootPoint = Weapons.getPlayerShootPoint(player, EnumHand.MAIN_HAND);
                        shootPoint = shootPoint.add(0.0, -0.1, 0.0);
                        IWeapon.fireEffect(
                           this,
                           player,
                           world,
                           64.0,
                           shootPoint.x,
                           shootPoint.y,
                           shootPoint.z,
                           vec.x,
                           vec.y,
                           vec.z,
                           (double)c,
                           dustState == null ? -1.0 : Block.getStateId(dustState),
                           collidesWithAny ? 1.0 : 0.0
                        );
                     }
                  } else if (this.initiateBulletReload(itemstack, player, ItemsRegister.ADAMANTIUMMINIGUNCLIP, maxammo, true)) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.adamantium_minigun_rel,
                        SoundCategory.AMBIENT,
                        0.8F,
                        1.0F
                     );
                     Weapons.setPlayerAnimationOnServer(player, 35, EnumHand.MAIN_HAND);
                     IWeapon.sendIWeaponState(itemstack, player, 70, itemSlot, EnumHand.MAIN_HAND);
                  }
               } else if (speed > 0) {
                  NBTHelper.AddNBTint(itemstack, -1, "speed");
               }
            } else {
               if (speed >= 2) {
                  NBTHelper.AddNBTint(itemstack, -2, "speed");
               } else if (speed > 0) {
                  NBTHelper.AddNBTint(itemstack, -1, "speed");
               }

               if (heat < 0 && isInHand) {
                  NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
                  int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
                  if (delay > 0) {
                     NBTHelper.AddNBTint(itemstack, -1, "atdelay");
                  }

                  if (delay <= 0 && Keys.isKeyPressed(player, Keys.SECONDARYATTACK) && !hascooldown) {
                     NBTHelper.SetNBTint(itemstack, 6, "atdelay");
                     Weapons.setPlayerAnimationOnServer(player, 24, EnumHand.MAIN_HAND);
                     double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                     player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.swosh_d,
                        SoundCategory.PLAYERS,
                        0.6F,
                        0.8F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  if (delay == 1) {
                     int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, itemstack);
                     int sweeping = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING, itemstack);
                     int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack);
                     if (coolingMode) {
                        reuse = -reuse;
                     }

                     if (IWeapon.doMeleeSwordAttack(this, itemstack, player, EnumHand.MAIN_HAND, heat < -100).success) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.melee_axe,
                           SoundCategory.PLAYERS,
                           0.9F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        NBTHelper.SetNBTint(itemstack, Math.min(heat + (20 - reuse * 6), 0), "heat");
                     } else {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.swosh_b,
                           SoundCategory.PLAYERS,
                           0.6F,
                           0.5F + itemRand.nextFloat() / 5.0F
                        );
                        NBTHelper.SetNBTint(itemstack, Math.min(heat + (10 - reuse * 5), 0), "heat");
                     }

                     player.addExhaustion(0.25F);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      entity.setFire(isCritical ? 8 : 5);
      return super.attackEntityMelee(entity, stack, player, hand, isCritical);
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      Vec3d from = new Vec3d(x, y, z);
      Vec3d to = new Vec3d(a, b, c);
      float dist = (float)from.distanceTo(to);
      Vec3d col = ColorConverters.DecimaltoRGB((int)d1);
      BulletParticle part = new BulletParticle(
         world,
         from,
         to,
         0.15F,
         (int)MathHelper.clamp(dist / 3.5, 1.0, 8.0),
         5,
         dist,
         (float)col.x,
         (float)col.y,
         (float)col.z,
         d3 > 0.0
      );
      part.smokeRed = 0.9F;
      part.smokeGreen = 0.2F;
      part.smokeBlue = 0.2F;
      part.blockDustId = (int)d2;
      world.spawnEntity(part);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 6;
      Booom.frequency = 0.525F;
      Booom.x = (float)itemRand.nextGaussian();
      Booom.y = (float)itemRand.nextGaussian();
      Booom.z = (float)itemRand.nextGaussian();
      Booom.power = 0.3F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(itemstack.getItem());
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      if (NBTHelper.GetNBTint(itemstack, "heat") < 0) {
         return parameters.getEnchantedi("cooldown_melee", rapidity);
      } else {
         int speed = NBTHelper.GetNBTint(itemstack, "speed");
         return Math.max(
            parameters.getEnchantedi("max_shoot_delay", rapidity) + (int)(speed * parameters.getEnchanted("speed_to_cooldown", rapidity)),
            parameters.getEnchantedi("min_shoot_delay", rapidity)
         );
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / maxammo, 0.0F, 1.0F);
   }

   public void decrementHeat(ItemStack itemstack, int heat, int amount) {
      if (heat > 0) {
         NBTHelper.SetNBTint(itemstack, Math.max(heat - amount, 0), "heat");
      }

      if (heat < 0) {
         NBTHelper.SetNBTint(itemstack, Math.min(heat + amount, 0), "heat");
      }
   }
}
