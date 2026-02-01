package com.vivern.arpg.main;

import com.vivern.arpg.blocks.AbstractBomb;
import com.vivern.arpg.blocks.IBlockHardBreak;
import com.vivern.arpg.blocks.IceSpikes;
import com.vivern.arpg.elements.IRepulsable;
import com.vivern.arpg.elements.IWeapon;
import com.vivern.arpg.entity.StandardBullet;
import com.vivern.arpg.mobs.AbstractMob;
import com.vivern.arpg.mobs.HostileProjectiles;
import com.vivern.arpg.network.PacketAnimationsToClients;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.potions.Frostburn;
import com.vivern.arpg.potions.PotionEffects;
import com.vivern.arpg.renders.PlayerAnimation;
import com.vivern.arpg.renders.PlayerAnimations;
import com.google.common.base.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockConcretePowder;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent.Throwable;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class Weapons {
   public static final byte ANIM_CLASSIC_SWING = 1;
   public static final byte ANIM_SPEAR_ATTACK = 2;
   public static final byte ANIM_GUN_AIM_SHOOT_1 = 3;
   public static final byte ANIM_RELOADING_GUN_1 = 4;
   public static final byte ANIM_PISTOL_AIM_SHOOT_1 = 5;
   public static final byte ANIM_PISTOL_RELOADING_1 = 6;
   public static final byte ANIM_FLAT_BLOW = 7;
   public static final byte ANIM_CHAIN_MACE_THROW = 8;
   public static final byte ANIM_CHAIN_MACE_SPIN = 9;
   public static final byte ANIM_PISTOL_AIM_SHOOT_2 = 10;
   public static final byte ANIM_GUN_AIM_SHOOT_2 = 11;
   public static final byte ANIM_GUN_AIM_SHOOT_3 = 12;
   public static final byte ANIM_GUN_AIM_SHOOT_4 = 13;
   public static final byte ANIM_STAFF_SHOOT_1 = 14;
   public static final byte ANIM_HAMMER_AXE_ATTACK = 15;
   public static final byte ANIM_DAGGER_ATTACK = 16;
   public static final byte ANIM_SPEAR_ATTACK_FAST = 17;
   public static final byte ANIM_SHIELD = 18;
   public static final byte ANIM_RELOADING_GRENADESLAUN_1 = 19;
   public static final byte ANIM_RELOADING_GRENADESLAUN_2 = 20;
   public static final byte ANIM_STAFF_CAST_GROUNDHIT = 21;
   public static final byte ANIM_STAFF_SHOOT_2 = 22;
   public static final byte CASC_STAFF_SHOOT_2 = 23;
   public static final byte ANIM_HARD_SWING = 24;
   public static final byte CASC_HARD_SWING_REPEAT = 25;
   public static final byte ANIM_STAFF_SHOOT_3 = 26;
   public static final byte ANIM_STAFF_SHOOT_4 = 27;
   public static final byte ANIM_RELOADING_SHOTGUN = 28;
   public static final byte ANIM_FORGING_1 = 29;
   public static final byte ANIM_FORGING_2 = 30;
   public static final byte ANIM_FORGING_3 = 31;
   public static final byte ANIM_ROLL_OPEN = 32;
   public static final byte ANIM_ROLL_FINISH = 33;
   public static final byte ANIM_RELOADING_GUN_2 = 34;
   public static final byte ANIM_RELOADING_MINIGUN_1 = 35;
   public static final byte ANIM_WHIP_SPECATTACK = 36;
   public static final byte ANIM_WHIP_ATTACK_1 = 37;
   public static final byte ANIM_WHIP_ATTACK_2 = 38;
   public static final byte ANIM_WHIP_ATTACK_3 = 39;
   public static final byte ANIM_WHIP_ATTACK_4 = 40;
   public static final byte ANIM_WHIP_ATTACK_5 = 41;
   public static final byte ANIM_WHIP_ATTACK_6 = 42;
   public static final byte ANIM_RELOADING_MINIGUN_2 = 43;
   public static final byte ANIM_JACKHAMMER_1 = 44;
   public static final byte ANIM_HAMMER_AXE_ATTACK_2 = 45;
   public static final byte ANIM_HAMMER_AXE_ATTACK_3 = 46;
   public static final byte ANIM_AIM_DRILL = 47;
   public static final byte CASC_AIM_DRILL = 48;
   public static final byte ANIM_AIM_CHAINSAW = 49;
   public static final byte CASC_AIM_CHAINSAW = 50;
   public static HashMap<Byte, Byte> animationsRegistere = new HashMap<>();
   public static HashMap<Byte, PlayerAnimation> animationsRegister = new HashMap<>();

   public static void registerAnimation(PlayerAnimation animation) {
      animationsRegister.put(animation.ID, animation);
   }

   public static boolean itemStacksRelEquals(ItemStack stack1, ItemStack stack2) {
      if (stack1.getItem() == stack2.getItem()) {
         int i1 = stack1.getItemDamage();
         int i2 = stack2.getItemDamage();
         int n1 = NBTHelper.GetNBTint(stack1, "reload_time");
         int n2 = NBTHelper.GetNBTint(stack2, "reload_time");
         if (i1 >= i2 && n1 >= n2) {
            return true;
         }
      }

      return false;
   }

   public static float getPlayerAnimationValue(EntityPlayer player, EnumHand hand, float partialTicks) {
      int an = (Integer)player.getDataManager().get(PropertiesRegistry.ANIMATIONS);
      int leftIdBinar = -16777216;
      int leftAnimBinar = 16711680;
      int rightIdBinar = 65280;
      int rightAnimBinar = 255;
      int ret = hand == EnumHand.MAIN_HAND ? an & rightAnimBinar : (an & leftAnimBinar) >>> 16;
      int id = an & (hand == EnumHand.MAIN_HAND ? rightIdBinar : leftIdBinar);
      if (hand == EnumHand.OFF_HAND) {
         id >>>= 24;
      } else {
         id >>>= 8;
      }

      float animationValue = animationsRegister.getOrDefault((byte)id, PlayerAnimations.DEFAULT).animationLength;
      float r = ret;
      return MathHelper.clamp((r - partialTicks) / animationValue, 0.0F, 1.0F);
   }

   public static int getPlayerAnimationId(Entity player, EnumHand hand) {
      int an = (Integer)player.getDataManager().get(PropertiesRegistry.ANIMATIONS);
      int leftIdBinar = -16777216;
      int rightIdBinar = 65280;
      int id = an & (hand == EnumHand.MAIN_HAND ? rightIdBinar : leftIdBinar);
      if (hand == EnumHand.OFF_HAND) {
         id >>>= 24;
      } else {
         id >>>= 8;
      }

      return id;
   }

   public static void decrementPlayerAnimation(EntityPlayer player) {
      int an = (Integer)player.getDataManager().get(PropertiesRegistry.ANIMATIONS);
      int leftAnimBinar = 16711680;
      int rightAnimBinar = 255;
      int noBinar = -16711936;
      int leftTicks = (an & leftAnimBinar) >>> 16;
      int rightTicks = an & rightAnimBinar;
      if (leftTicks > 0) {
         leftTicks--;
      }

      if (rightTicks > 0) {
         rightTicks--;
      }

      an = an & noBinar | rightTicks | leftTicks << 16;
      player.getDataManager().set(PropertiesRegistry.ANIMATIONS, an);
   }

   public static void setPlayerAnimationOnServer(EntityPlayer player, int id, EnumHand hand) {
      if (!player.world.isRemote) {
         PacketAnimationsToClients packet = new PacketAnimationsToClients();
         packet.write(hand, id, player.getEntityId());
         PacketHandler.sendToAllAround(packet, player.world, player.posX, player.posY, player.posZ, 64.0);
      }
   }

   public static void setPlayerAnimationOnClient(EntityPlayer player, int id, EnumHand hand) {
      if (hand == EnumHand.MAIN_HAND) {
         int animateData = (Integer)player.getDataManager().get(PropertiesRegistry.ANIMATIONS);
         PlayerAnimation animation = animationsRegister.getOrDefault((byte)id, PlayerAnimations.DEFAULT);
         byte lastId = (byte)((animateData & 0xFF00) >>> 8);
         int lastValue = animateData & 0xFF;
         byte cascade = animation.cascadeAnimation(lastId, lastValue);
         if (cascade != id) {
            id = cascade;
            animation = animationsRegister.getOrDefault((byte)cascade, PlayerAnimations.DEFAULT);
         }

         int animationLen = animation.animationLength;
         int a2 = animateData & -65536;
         int a3 = a2 | animationLen;
         int b = id << 8;
         int a4 = a3 | b;
         player.getDataManager().set(PropertiesRegistry.ANIMATIONS, a4);
      }

      if (hand == EnumHand.OFF_HAND) {
         int animateData = (Integer)player.getDataManager().get(PropertiesRegistry.ANIMATIONS);
         int animationValue = animationsRegister.getOrDefault((byte)id, PlayerAnimations.DEFAULT).animationLength;
         int a1 = animationValue << 16;
         int a2 = animateData & 65535;
         int a3 = a2 | a1;
         int b = id << 24;
         int a4 = a3 | b;
         player.getDataManager().set(PropertiesRegistry.ANIMATIONS, a4);
      }
   }

   @SubscribeEvent
   public static void throwableImpact(Throwable event) {
      if (("entity.morb.name".equals(event.getThrowable().getName()) || event.getThrowable().hasCustomName())
         && event.getRayTraceResult() != null
         && event.getRayTraceResult().entityHit != null
         && event.getRayTraceResult().entityHit instanceof AbstractMob
         && !((AbstractMob)event.getRayTraceResult().entityHit).canBeCaptured(event.getThrowable().getThrower())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void playerAttackEntity(AttackEntityEvent event) {
      EntityPlayer player = event.getEntityPlayer();
      ItemStack stack = player.getHeldItemMainhand();
      if (stack.getItem() instanceof IWeapon && !((IWeapon)stack.getItem()).canAttackMelee(stack, player)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void equipmentChange(LivingEquipmentChangeEvent event) {
      if (event.getEntityLiving() instanceof EntityPlayer) {
         ItemStack stack = event.getFrom();
         ItemStack stack2 = event.getTo();
         if (!itemStacksRelEquals(stack, stack2)) {
            if (stack.getItem() instanceof IWeapon) {
               int relTime = ((IWeapon)stack.getItem()).getReloadTime(stack);
               if (NBTHelper.GetNBTint(stack, "reload_time") > relTime * 0.4 && !((IWeapon)stack.getItem()).autoReload(stack)) {
                  NBTHelper.SetNBTint(stack, relTime + 10, "reload_time");
               }

               if (((EntityPlayer)event.getEntityLiving()).getCooldownTracker().hasCooldown(stack.getItem())
                  && !((IWeapon)stack.getItem()).autoCooldown(stack)) {
               }
            }

            if (stack2.getItem() instanceof IWeapon) {
               int relTimex = ((IWeapon)stack2.getItem()).getReloadTime(stack2);
               if (NBTHelper.GetNBTint(stack2, "reload_time") > relTimex * 0.4 && !((IWeapon)stack2.getItem()).autoReload(stack2)) {
                  NBTHelper.SetNBTint(stack2, relTimex + 10, "reload_time");
               }

               if (((EntityPlayer)event.getEntityLiving()).getCooldownTracker().hasCooldown(stack2.getItem())
                  && !((IWeapon)stack2.getItem()).autoCooldown(stack2)) {
               }
            }
         }
      }
   }

   public static boolean easyBreakBlockFor(World world, float hardnessBreaks, BlockPos pos) {
      IBlockState block = world.getBlockState(pos);
      return easyBreakBlockFor(world, hardnessBreaks, pos, block);
   }

   public static boolean easyBreakBlockFor(World world, float hardnessBreaks, BlockPos pos, IBlockState block) {
      float blockhard = block.getBlock().getBlockHardness(block, world, pos);
      return blockhard >= 0.0F && (blockhard <= hardnessBreaks || block.getBlock().isReplaceable(world, pos))
         ? !(block.getBlock() instanceof BlockLiquid) && !(block.getBlock() instanceof IFluidBlock)
         : false;
   }

   public static int getEntityRepulseType(Entity entity) {
      if (entity instanceof EntityFireball) {
         return 1;
      } else if (entity instanceof EntityArrow || entity instanceof EntityPotion || entity instanceof EntityFallingBlock || entity instanceof EntityTNTPrimed) {
         return 2;
      } else {
         return entity instanceof IRepulsable ? ((IRepulsable)entity).getRepulseType() : 0;
      }
   }

   public static void setAcceleration(Entity entity) {
      if (entity instanceof EntityFireball) {
         EntityFireball fireball = (EntityFireball)entity;
         fireball.accelerationX = fireball.motionX * 0.1;
         fireball.accelerationY = fireball.motionY * 0.1;
         fireball.accelerationZ = fireball.motionZ * 0.1;
      }

      entity.velocityChanged = true;
   }

   @SubscribeEvent
   public static void onBreakSpeedGetting(BreakSpeed event) {
      IBlockState state = event.getState();
      Block block = state.getBlock();
      if (block instanceof IBlockHardBreak) {
         event.setNewSpeed(((IBlockHardBreak)block).getBlockBreakingSpeed(event.getEntityPlayer(), state, event.getPos(), event.getOriginalSpeed()));
      }
   }

   public static double getDistanceToMobHitbox(float width, float height, double mobx, double moby, double mobz, double x, double y, double z) {
      float width2 = width / 2.0F;
      float f = Math.max(0.0F, Math.abs((float)(mobx - x)) - width2);
      float f1 = (float)(moby - y);
      float f2 = Math.max(0.0F, Math.abs((float)(mobz - z)) - width2);
      if (f1 < 0.0F) {
         f1 += height;
         if (f1 > 0.0F) {
            f1 = 0.0F;
         }
      }

      return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
   }

   public static double getDistanceToMobHitbox(Entity mob, double x, double y, double z) {
      return getDistanceToMobHitbox(mob.width, mob.height, mob.posX, mob.posY, mob.posZ, x, y, z);
   }

   public static boolean freezeBlock(World world, BlockPos pos) {
      IBlockState s = world.getBlockState(pos);
      return freezeBlock(world, pos, s, s.getBlock());
   }

   public static boolean freezeBlock(World world, BlockPos pos, IBlockState state, Block block) {
      if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
         world.setBlockState(pos, Blocks.ICE.getDefaultState());
         return true;
      } else if (block != Blocks.LAVA && block != Blocks.FLOWING_LAVA) {
         if (block == Blocks.MAGMA) {
            world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
            return true;
         } else if (block == BlocksRegister.FLUIDSLIME) {
            if ((Integer)state.getValue(BlockLiquid.LEVEL) == 0) {
               world.setBlockState(pos, BlocksRegister.BROWNSLIME.getDefaultState());
            } else {
               world.setBlockState(pos, BlocksRegister.SLIMEGLOB.getDefaultState());
            }

            return true;
         } else if (block == BlocksRegister.ICESPIKES && (Boolean)state.getValue(IceSpikes.NOTPERMANENT)) {
            world.setBlockState(pos, state.withProperty(IceSpikes.NOTPERMANENT, false));
            return true;
         } else {
            return false;
         }
      } else {
         if ((Integer)state.getValue(BlockLiquid.LEVEL) == 0) {
            world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
         } else {
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
         }

         return true;
      }
   }

   public static boolean triggerExplodeBlock(World world, BlockPos pos, @Nullable EntityLivingBase igniter, @Nullable Entity entityExplosive) {
      IBlockState s = world.getBlockState(pos);
      return triggerExplodeBlock(world, pos, s, s.getBlock(), igniter, entityExplosive);
   }

   public static boolean triggerExplodeBlock(
      World world, BlockPos pos, IBlockState state, Block block, @Nullable EntityLivingBase igniter, @Nullable Entity entityExplosive
   ) {
      if (block == Blocks.TNT) {
         if (!world.isRemote) {
            world.setBlockToAir(pos);
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, igniter);
            entitytntprimed.setFuse((short)(world.rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8));
            world.spawnEntity(entitytntprimed);
         }

         return true;
      } else if (block instanceof AbstractBomb) {
         ((AbstractBomb)block).blockexploded(world, pos, igniter, entityExplosive);
         world.setBlockToAir(pos);
         return true;
      } else {
         return false;
      }
   }

   public static boolean dealDamage(
      @Nullable DamageSource source, float damage, @Nullable Entity damager, Entity target, boolean blockKnockback, float superKnockback
   ) {
      if (damager != null) {
         return dealDamage(source, damage, damager, target, blockKnockback, superKnockback, damager.posX, damager.posY, damager.posZ);
      } else {
         return source != null && source.getImmediateSource() != null
            ? dealDamage(
               source,
               damage,
               damager,
               target,
               blockKnockback,
               superKnockback,
               source.getImmediateSource().posX,
               source.getImmediateSource().posY,
               source.getImmediateSource().posZ
            )
            : dealDamage(source, damage, damager, target, blockKnockback);
      }
   }

   public static boolean dealDamage(
      @Nullable DamageSource source,
      float damage,
      @Nullable Entity damager,
      Entity target,
      boolean blockKnockback,
      float superKnockback,
      double knobckFromX,
      double knobckFromY,
      double knobckFromZ
   ) {
      if (superKnockback != 0.0F) {
         SuperKnockback.applyKnockback(target, superKnockback, knobckFromX, knobckFromY, knobckFromZ);
      }

      return dealDamage(source, damage, damager, target, blockKnockback);
   }

   public static boolean dealDamage(@Nullable DamageSource source, float damage, @Nullable Entity damager, Entity target, boolean blockKnockback) {
      boolean ret = false;
      if (canDealDamageTo(target)) {
         if (source == null) {
            source = new WeaponDamage(null, damager, null, false, false, damager, "weapon");
         } else if (damager != null && source.getTrueSource() == null && source instanceof WeaponDamage) {
            ((WeaponDamage)source).setEntityDamager(damager);
         }

         if (blockKnockback && target instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)target;
            IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
            double baseValue = entityAttribute.getBaseValue();
            entityAttribute.setBaseValue(1.0);
            ret = entitylivingbase.attackEntityFrom(source, damage);
            entityAttribute.setBaseValue(baseValue);
         } else {
            ret = target.attackEntityFrom(source, damage);
         }
      }

      return ret;
   }

   public static boolean canDealDamageTo(Entity target) {
      if (target instanceof EntityItem) {
         return false;
      } else {
         return target instanceof EntityXPOrb ? false : !(target instanceof HostileProjectiles.Plasma);
      }
   }

   public static boolean isPotionActive(Entity entity, Potion potion) {
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
         return entitylivingbase.isPotionActive(potion);
      } else {
         return false;
      }
   }

   @Nullable
   public static PotionEffect setPotionIfEntityLB(Entity entity, Potion potion, int time, int power) {
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
         PotionEffect eff = new PotionEffect(potion, time, power);
         entitylivingbase.addPotionEffect(eff);
         return eff;
      } else {
         return null;
      }
   }

   @Nullable
   public static void setPotionIfEntityLB(Entity entity, PotionEffect potionEffect) {
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
         entitylivingbase.addPotionEffect(potionEffect);
      }
   }

   public static PotionEffect mixPotion(
      Entity entity,
      Potion potion,
      float time,
      float power,
      EnumPotionMix mixTypeTime,
      EnumPotionMix mixTypePower,
      EnumMathOperation operationTime,
      EnumMathOperation operationPower,
      int timeBound,
      int powerBound
   ) {
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
         return mixPotion(entitylivingbase, potion, time, power, mixTypeTime, mixTypePower, operationTime, operationPower, timeBound, powerBound);
      } else {
         return null;
      }
   }

   public static PotionEffect mixPotion(
      EntityLivingBase entitylivingbase,
      Potion potion,
      float time,
      float power,
      EnumPotionMix mixTypeTime,
      EnumPotionMix mixTypePower,
      EnumMathOperation operationTime,
      EnumMathOperation operationPower,
      int timeBound,
      int powerBound
   ) {
      PotionEffect eff = entitylivingbase.getActivePotionEffect(potion);
      int lastTime = 0;
      int lastPower = 0;
      if (eff != null) {
         lastTime = eff.getDuration();
         lastPower = eff.getAmplifier() + 1;
      }

      int newTime = 0;
      int newPower = 0;
      if (operationTime == EnumMathOperation.PLUS) {
         newTime = Math.round(lastTime + time);
      } else if (operationTime == EnumMathOperation.MINUS) {
         newTime = Math.round(lastTime - time);
      } else if (operationTime == EnumMathOperation.MULTIPLY) {
         newTime = Math.round(lastTime * time);
      } else if (operationTime == EnumMathOperation.DIVIDE) {
         newTime = Math.round(lastTime / time);
      } else if (operationTime == EnumMathOperation.POWER) {
         newTime = (int)Math.round(Math.pow(lastTime, time));
      } else if (operationTime == EnumMathOperation.ROOT) {
         newTime = (int)Math.round(Math.pow(lastTime, 1.0 / time));
      } else if (operationTime == EnumMathOperation.ADD_MULTIPLIED) {
         newTime = Math.round(lastTime + lastTime * time);
      } else if (operationTime == EnumMathOperation.NONE) {
         newTime = Math.round(time);
      }

      if (operationPower == EnumMathOperation.PLUS) {
         newPower = Math.round(lastPower + power);
      } else if (operationPower == EnumMathOperation.MINUS) {
         newPower = Math.round(lastPower - power);
      } else if (operationPower == EnumMathOperation.MULTIPLY) {
         newPower = Math.round(lastPower * power);
      } else if (operationPower == EnumMathOperation.DIVIDE) {
         newPower = Math.round(lastPower / power);
      } else if (operationPower == EnumMathOperation.POWER) {
         newPower = (int)Math.round(Math.pow(lastPower, power));
      } else if (operationPower == EnumMathOperation.ROOT) {
         newPower = (int)Math.round(Math.pow(lastPower, 1.0 / power));
      } else if (operationPower == EnumMathOperation.ADD_MULTIPLIED) {
         newPower = Math.round(lastTime + lastPower * power);
      } else if (operationPower == EnumMathOperation.NONE) {
         newPower = Math.round(power);
      }

      if (mixTypeTime == EnumPotionMix.GREATEST) {
         newTime = Math.max(newTime, lastTime);
      } else if (mixTypeTime == EnumPotionMix.LOWEST) {
         newTime = Math.min(newTime, lastTime);
      } else if (mixTypeTime == EnumPotionMix.WITH_MAXIMUM) {
         newTime = Math.min(newTime, timeBound);
      } else if (mixTypeTime == EnumPotionMix.WITH_MINIMUM) {
         newTime = Math.max(newTime, timeBound);
      }

      if (mixTypePower == EnumPotionMix.GREATEST) {
         newPower = Math.max(newPower, lastPower);
      } else if (mixTypePower == EnumPotionMix.LOWEST) {
         newPower = Math.min(newPower, lastPower);
      } else if (mixTypePower == EnumPotionMix.WITH_MAXIMUM) {
         newPower = Math.min(newPower, powerBound);
      } else if (mixTypePower == EnumPotionMix.WITH_MINIMUM) {
         newPower = Math.max(newPower, powerBound);
      }

      if (eff != null) {
         entitylivingbase.removePotionEffect(potion);
         if (newTime > 0 && newPower > 0) {
            PotionEffect effect = new PotionEffect(potion, newTime, newPower - 1, eff.getIsAmbient(), eff.doesShowParticles());
            effect.setCurativeItems(eff.getCurativeItems());
            entitylivingbase.addPotionEffect(effect);
         }

         return eff;
      } else {
         if (newTime > 0 && newPower > 0) {
            PotionEffect effect = new PotionEffect(potion, newTime, newPower - 1);
            entitylivingbase.addPotionEffect(effect);
         }

         return null;
      }
   }

   public static boolean destroyBlock(World world, BlockPos pos, boolean dropBlock, boolean playEvent) {
      IBlockState iblockstate = world.getBlockState(pos);
      Block block = iblockstate.getBlock();
      if (block.isAir(iblockstate, world, pos)) {
         return false;
      } else {
         if (playEvent) {
            world.playEvent(2001, pos, Block.getStateId(iblockstate));
         }

         if (dropBlock) {
            block.dropBlockAsItem(world, pos, iblockstate, 0);
         }

         return world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
      }
   }

   public static EntityFallingBlock destroyBlockWithFalling(World world, BlockPos blockpos) {
      IBlockState state = world.getBlockState(blockpos);
      EntityFallingBlock entityfallingblock = new EntityFallingBlock(
         world, blockpos.getX() + 0.5, blockpos.getY() + 0.2, blockpos.getZ() + 0.5, state
      );
      world.spawnEntity(entityfallingblock);
      return entityfallingblock;
   }

   public static void destroyBlockAsTool(
      World world, BlockPos pos, @Nullable EntityPlayer player, String tool, int toolLevel, int fortune, boolean withSilkTouch
   ) {
      IBlockState state = world.getBlockState(pos);
      if (!(state.getBlockHardness(world, pos) < 0.0F) && !GetMOP.IFLUID_BLOCKS.apply(state) && state.getMaterial() != Material.AIR) {
         if (player == null) {
            player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), Double.MAX_VALUE, false);
         }

         if (player != null) {
            if (canHarvestBlock(state.getBlock(), world, pos, tool, toolLevel)) {
               boolean actuallyRemoved = state.getBlock().removedByPlayer(state, world, pos, player, true);
               if (actuallyRemoved) {
                  ItemStack illusiveStack = new ItemStack(new IllusiveItem(tool, toolLevel));
                  if (withSilkTouch) {
                     illusiveStack.addEnchantment(Enchantments.SILK_TOUCH, 1);
                  }

                  if (fortune > 0) {
                     illusiveStack.addEnchantment(Enchantments.FORTUNE, fortune);
                  }

                  state.getBlock().harvestBlock(world, player, pos, state, world.getTileEntity(pos), illusiveStack);
               }
            }

            world.playEvent(2001, pos, Block.getStateId(state));
            state.getBlock().onPlayerDestroy(world, pos, state);
         } else {
            if (canHarvestBlock(state.getBlock(), world, pos, tool, toolLevel)) {
               state.getBlock().dropBlockAsItem(world, pos, state, fortune);
            }

            world.destroyBlock(pos, false);
         }
      }
   }

   public static boolean canHarvestBlock(@Nonnull Block block, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, String toolHas, int toolLevel) {
      IBlockState state = world.getBlockState(pos);
      state = state.getBlock().getActualState(state, world, pos);
      if (state.getMaterial().isToolNotRequired()) {
         return true;
      } else if (toolHas != null && !toolHas.isEmpty()) {
         String toolNeedToBlock = block.getHarvestTool(state);
         int levelForBlockTool = toolNeedToBlock != null && !toolNeedToBlock.equals(toolHas) ? -1 : toolLevel;
         return levelForBlockTool >= block.getHarvestLevel(state);
      } else {
         return false;
      }
   }

   public static boolean doWetBlock(World world, BlockPos pos) {
      IBlockState iblockstate = world.getBlockState(pos);
      Block block = iblockstate.getBlock();
      if (block == Blocks.FIRE) {
         world.setBlockToAir(pos);
         world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 1.0F);
         return true;
      } else if (block == Blocks.FARMLAND) {
         world.setBlockState(pos, iblockstate.withProperty(BlockFarmland.MOISTURE, 7), 2);
         return true;
      } else if (block == Blocks.SPONGE) {
         world.setBlockState(pos, iblockstate.withProperty(BlockSponge.WET, true));
         return true;
      } else if (block == Blocks.CONCRETE_POWDER) {
         world.setBlockState(
            pos,
            Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, iblockstate.getValue(BlockConcretePowder.COLOR)),
            3
         );
         return true;
      } else if (block != Blocks.LAVA && block != Blocks.FLOWING_LAVA) {
         return false;
      } else {
         if ((Integer)iblockstate.getValue(BlockLiquid.LEVEL) == 0) {
            world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
         } else {
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
         }

         world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.6F, 1.0F);
         return true;
      }
   }

   public static boolean isBlockConsideredWater(World world, Block block) {
      return block == Blocks.WATER
         || block == Blocks.FLOWING_WATER
         || block == BlocksRegister.FLUIDLARVAWATER
         || block == BlocksRegister.FLUIDPOISON
         || block == BlocksRegister.FLUIDHYDROTHERMAL;
   }

   public static boolean isBlockFiery(World world, Block block) {
      if (block == Blocks.FIRE
         || block == Blocks.LAVA
         || block == Blocks.FLOWING_LAVA
         || block == Blocks.TORCH
         || block == BlocksRegister.PALMTORCH
         || block == BlocksRegister.OREMOLTEN
         || block == BlocksRegister.MAGMABLOOM) {
         return true;
      } else {
         return block instanceof IFluidBlock ? ((IFluidBlock)block).getFluid().getTemperature() >= 1000 : false;
      }
   }

   public static boolean isEntityStayOnColdBlock(World world, Entity entity) {
      if (!entity.onGround) {
         return false;
      } else {
         AxisAlignedBB aabb = entity.getEntityBoundingBox();

         for (double x = aabb.minX; x <= aabb.maxX; x++) {
            for (double z = aabb.minZ; z <= aabb.maxZ; z++) {
               BlockPos pos = new BlockPos(x, aabb.minY, z);
               BlockPos pos2 = new BlockPos(x, aabb.minY - 0.05, z);
               if (isSolidColdBlock(world.getBlockState(pos).getBlock())) {
                  return true;
               }

               if (!pos.equals(pos2) && isSolidColdBlock(world.getBlockState(pos2).getBlock())) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean isSolidColdBlock(Block block) {
      return block == Blocks.SNOW
         || block == Blocks.SNOW_LAYER
         || block == Blocks.ICE
         || block == Blocks.FROSTED_ICE
         || block == Blocks.PACKED_ICE
         || block == BlocksRegister.SNOWICE
         || block == BlocksRegister.LOOSESNOW
         || block == BlocksRegister.OREICEGL
         || block == BlocksRegister.OREICESN
         || block == BlocksRegister.CLEANICE
         || block == BlocksRegister.ICESPIKES
         || block == BlocksRegister.ICEPANE
         || block == BlocksRegister.SNOWICE;
   }

   public static float resistance(Block block) {
      return block.getExplosionResistance(null) * 5.0F;
   }

   public static void addItemStackToInventory(EntityPlayer player, ItemStack itemStack) {
      if (!player.addItemStackToInventory(itemStack)) {
         player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, itemStack));
      }
   }

   public static void addOrRemoveExperience(EntityPlayer player, int amount) {
      if (amount > 0) {
         player.addExperience(amount);
      } else {
         int total = Math.max(player.experienceTotal + amount, 0);
         player.experience = 0.0F;
         player.experienceTotal = 0;
         player.experienceLevel = 0;
         player.experience = player.experience + (float)total / player.xpBarCap();

         for (player.experienceTotal += total; player.experience >= 1.0F; player.experience = player.experience / player.xpBarCap()) {
            player.experience = (player.experience - 1.0F) * player.xpBarCap();
            player.experienceLevel++;
         }
      }
   }

   public static Vec3d getPlayerShootPoint(EntityPlayer player, @Nullable EnumHand hand) {
      float yaw = player.rotationYawHead;
      if (hand != null) {
         if ((hand != EnumHand.MAIN_HAND || player.getPrimaryHand() != EnumHandSide.RIGHT)
            && (hand != EnumHand.OFF_HAND || player.getPrimaryHand() != EnumHandSide.LEFT)) {
            yaw -= 90.0F;
         } else {
            yaw += 90.0F;
         }
      }

      float ff = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ff1 = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      Vec3d offset = new Vec3d(-ff1 * 0.2, player.getEyeHeight() - 0.1, -ff * 0.2);
      return player.getPositionVector().add(offset);
   }

   public static Vec3d getPlayerShootPoint(EntityPlayer player, @Nullable EnumHand hand, float partialTicks) {
      return getPlayerShootPoint(player, hand, partialTicks, 0.2, -0.1);
   }

   public static Vec3d getPlayerShootPoint(EntityPlayer player, @Nullable EnumHand hand, float partialTicks, double shoulders, double yoffset) {
      return getPlayerShootPoint(player, hand, partialTicks, 0.0F, shoulders, yoffset, false);
   }

   public static Vec3d getPlayerShootPoint(
      EntityPlayer player, @Nullable EnumHand hand, float partialTicks, float barrelLength, double shoulders, double yoffset, boolean useArmPivotHeight
   ) {
      float yaw = GetMOP.partial(player.rotationYawHead, player.prevRotationYawHead, partialTicks);
      float yaw1 = yaw;
      if (hand != null) {
         if ((hand != EnumHand.MAIN_HAND || player.getPrimaryHand() != EnumHandSide.RIGHT)
            && (hand != EnumHand.OFF_HAND || player.getPrimaryHand() != EnumHandSide.LEFT)) {
            yaw1 = yaw - 90.0F;
         } else {
            yaw1 = yaw + 90.0F;
         }
      }

      float ff = MathHelper.cos(-yaw1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ff1 = MathHelper.sin(-yaw1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      Vec3d offset = new Vec3d(-ff1 * shoulders, 0.0, -ff * shoulders);
      Vec3d posvec = new Vec3d(
         GetMOP.partial(player.posX, player.prevPosX, (double)partialTicks),
         GetMOP.partial(player.posY, player.prevPosY, (double)partialTicks),
         GetMOP.partial(player.posZ, player.prevPosZ, (double)partialTicks)
      );
      float pitchUp = GetMOP.partial(player.rotationPitch, player.prevRotationPitch, partialTicks) - 90.0F;
      Vec3d lookUp = GetMOP.PitchYawToVec3d(pitchUp, yaw).scale(yoffset);
      float eyeHeight = useArmPivotHeight ? getPlayerArmPivotHeight(player) : player.getEyeHeight();
      if (barrelLength != 0.0F) {
         Vec3d look = player.getLook(partialTicks).scale(barrelLength);
         return posvec.add(
            offset.x + lookUp.x + look.x,
            eyeHeight + offset.y + lookUp.y + look.y,
            offset.z + lookUp.z + look.z
         );
      } else {
         return posvec.add(
            offset.x + lookUp.x, eyeHeight + offset.y + lookUp.y, offset.z + lookUp.z
         );
      }
   }

   public static float getPlayerArmPivotHeight(EntityPlayer player) {
      if (player.isPlayerSleeping()) {
         return 0.2F;
      } else if (!player.isElytraFlying() && player.height != 0.6F) {
         return player.isSneaking() ? 1.2F : 1.4F;
      } else {
         return 0.4F;
      }
   }

   public static void shoot(
      Entity projectile,
      EnumHand hand,
      EntityLivingBase player,
      float rotationPitchIn,
      float rotationYawIn,
      float pitchOffset,
      float velocity,
      float inaccuracy,
      float yPosAdd,
      float fixAngle,
      float barrelLength,
      float shoulders
   ) {
      Vec3d vec3dEyes = new Vec3d(player.posX, player.posY + player.getEyeHeight() + yPosAdd, player.posZ);
      float yaw;
      if ((hand != EnumHand.MAIN_HAND || player.getPrimaryHand() != EnumHandSide.RIGHT)
         && (hand != EnumHand.OFF_HAND || player.getPrimaryHand() != EnumHandSide.LEFT)) {
         yaw = -1.0F;
      } else {
         yaw = 1.0F;
      }

      Vec3d yawVec = GetMOP.YawToVec3d(player.rotationYawHead + yaw * 90.0F);
      vec3dEyes = vec3dEyes.add(yawVec.x * shoulders, yawVec.y * shoulders, yawVec.z * shoulders);
      if (barrelLength != 0.0F) {
         Vec3d look = player.getLookVec();
         vec3dEyes = vec3dEyes.add(look.x * barrelLength, look.y * barrelLength, look.z * barrelLength);
      }

      projectile.setPosition(vec3dEyes.x, vec3dEyes.y, vec3dEyes.z);
      if (projectile instanceof EntityThrowable) {
         ((EntityThrowable)projectile).shoot(player, rotationPitchIn, rotationYawIn - yaw * fixAngle, pitchOffset, velocity, inaccuracy);
      } else if (projectile instanceof EntityArrow) {
         ((EntityArrow)projectile).shoot(player, rotationPitchIn, rotationYawIn - yaw * fixAngle, pitchOffset, velocity, inaccuracy);
      } else if (projectile instanceof StandardBullet) {
         ((StandardBullet)projectile).shoot(player, rotationPitchIn, rotationYawIn - yaw * fixAngle, pitchOffset, velocity, inaccuracy);
      } else {
         SuperKnockback.shoot(projectile, player, rotationPitchIn, rotationYawIn - yaw * fixAngle, pitchOffset, velocity, inaccuracy);
      }
   }

   public static void shoot(
      Entity projectile,
      EnumHand hand,
      EntityLivingBase player,
      float rotationPitchIn,
      float rotationYawIn,
      float pitchOffset,
      float velocity,
      float inaccuracy,
      float yPosAdd,
      float fixAngle,
      float barrelLength
   ) {
      shoot(projectile, hand, player, rotationPitchIn, rotationYawIn, pitchOffset, velocity, inaccuracy, yPosAdd, fixAngle, barrelLength, 0.2F);
   }

   public static List<AbstractMob> getPlayerMinions(EntityPlayer player) {
      List<AbstractMob> list = new ArrayList<>();

      for (Entity entity : player.world.loadedEntityList) {
         if (entity instanceof AbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            if (player == mob.owner) {
               list.add(mob);
            }
         }
      }

      return list;
   }

   public static List<AbstractMob> getPlayerMinions(EntityPlayer player, Predicate<EntityLivingBase> filterMinion) {
      List<AbstractMob> list = new ArrayList<>();

      for (Entity entity : player.world.loadedEntityList) {
         if (entity instanceof AbstractMob) {
            AbstractMob mob = (AbstractMob)entity;
            if (player == mob.owner && filterMinion.apply(mob)) {
               list.add(mob);
            }
         }
      }

      return list;
   }

   public static boolean disableShield(Entity entity, boolean always) {
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;
         player.disableShield(always);
         return true;
      } else {
         return false;
      }
   }

   public static void doWetEntity(Entity entity) {
      entity.extinguish();
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
         if (entitylivingbase.isPotionActive(PotionEffects.FROSTBURN)) {
            Frostburn.freezeWhileFrostburning(entitylivingbase);
         }
      }
   }

   public static boolean isArrowInGround(EntityArrow entity) {
      try {
         Class thisclass = entity.getClass();
         Field field = thisclass.getField("inGround");
         field.setAccessible(true);
         return field.getBoolean(entity);
      } catch (Exception var4) {
         return false;
      }
   }

   public static void setPositionDirty(Entity entity) {
      try {
         Class thisclass = entity.getClass();
         Field field = thisclass.getField("isPositionDirty");
         field.setAccessible(true);
         field.setBoolean(entity, true);
      } catch (Exception var3) {
      }
   }

   static {
      registerAnimation(PlayerAnimations.DEFAULT);
      registerAnimation(new PlayerAnimations.PAClassicSwing((byte)1, 10));
      registerAnimation(new PlayerAnimations.PASpearAttack((byte)2, 14));
      registerAnimation(new PlayerAnimations.PAGunAimShootNormal((byte)3, 25));
      registerAnimation(new PlayerAnimations.PAReloadingGunNormal((byte)4, 40, 0.0F, 0.0F));
      registerAnimation(new PlayerAnimations.PAPistolAimShoot((byte)5, 25));
      registerAnimation(new PlayerAnimations.PAPistolReloadingSpin((byte)6, 20));
      registerAnimation(new PlayerAnimations.PAFlatBlow((byte)7, 30));
      registerAnimation(new PlayerAnimations.PAChainMaceThrow((byte)8, 10));
      registerAnimation(new PlayerAnimations.PAChainMaceSpin((byte)9, 20));
      registerAnimation(new PlayerAnimations.PAPistolAimShootNoPunch((byte)10, 25));
      registerAnimation(new PlayerAnimations.PAGunAimShootNoPunch((byte)11, 25));
      registerAnimation(new PlayerAnimations.PAGunAimShootLowPunch((byte)12, 25));
      registerAnimation(new PlayerAnimations.PAGunAimShootLinear((byte)13, 25));
      registerAnimation(new PlayerAnimations.PAStaffShootNormal((byte)14, 18));
      registerAnimation(new PlayerAnimations.PAHammerAxeAttack((byte)15, 30));
      registerAnimation(new PlayerAnimations.PAHammerAxeAttack((byte)45, 25));
      registerAnimation(new PlayerAnimations.PAHammerAxeAttack((byte)46, 60));
      registerAnimation(new PlayerAnimations.PADaggerAttack((byte)16, 9));
      registerAnimation(new PlayerAnimations.PASpearAttack((byte)17, 9));
      registerAnimation(new PlayerAnimations.PAShield((byte)18, 10));
      registerAnimation(new PlayerAnimations.PAGrenadeslaunReloading((byte)19, 40));
      registerAnimation(new PlayerAnimations.PAGrenadeslaunReloading((byte)20, 30));
      registerAnimation(new PlayerAnimations.PAStaffCastGroundhit((byte)21, 30));
      registerAnimation(new PlayerAnimations.PAStaffShootMiddle((byte)22, 25, (byte)23));
      registerAnimation(new PlayerAnimations.CASCStaffShootMiddle((byte)23, 25));
      registerAnimation(new PlayerAnimations.PAHardSwing((byte)24, 40, (byte)25));
      registerAnimation(new PlayerAnimations.CASCHardSwingRepeat((byte)25, 25));
      registerAnimation(new PlayerAnimations.PAStaffShootMiddlePunch((byte)26, 25, (byte)23, 1.0F));
      registerAnimation(new PlayerAnimations.PAStaffShootMiddlePunch((byte)27, 15, (byte)23, 0.5F));
      registerAnimation(new PlayerAnimations.PAReloadingShotgun((byte)28, 20));
      registerAnimation(new PlayerAnimations.PAForging((byte)29, 20, 1.0F, 0.0F, -1.0F, 0.0F));
      registerAnimation(new PlayerAnimations.PAForging((byte)30, 20, 0.0F, 0.05F, 0.4F, -0.2F));
      registerAnimation(new PlayerAnimations.PAForging((byte)31, 20, -1.3F, 0.2F, 1.0F, 0.15F));
      registerAnimation(new PlayerAnimations.PARollOpen((byte)32, 25));
      registerAnimation(new PlayerAnimations.PARollFinish((byte)33, 70));
      registerAnimation(new PlayerAnimations.PAReloadingGunNormal((byte)34, 60, 1.0F, 0.1F));
      registerAnimation(new PlayerAnimations.PAReloadingMinigun((byte)35, 80, 0.3F, Vec3d.ZERO));
      registerAnimation(new PlayerAnimations.PAWhipSpecAttack((byte)36, 20));
      registerAnimation(new PlayerAnimations.PAWhipAttack((byte)37, 16));
      registerAnimation(new PlayerAnimations.PAWhipAttack((byte)38, 18));
      registerAnimation(new PlayerAnimations.PAWhipAttack((byte)39, 20));
      registerAnimation(new PlayerAnimations.PAWhipAttack((byte)40, 22));
      registerAnimation(new PlayerAnimations.PAWhipAttack((byte)41, 24));
      registerAnimation(new PlayerAnimations.PAWhipAttack((byte)42, 26));
      registerAnimation(new PlayerAnimations.PAReloadingMinigun((byte)43, 50, 0.5F, new Vec3d(0.5, -0.6, 0.8)));
      registerAnimation(new PlayerAnimations.PAJackhammer((byte)44, 32));
      registerAnimation(new PlayerAnimations.PAAimDrill((byte)47, 25, (byte)48));
      registerAnimation(new PlayerAnimations.CascAimDrill((byte)48, 25));
      registerAnimation(new PlayerAnimations.PAAimChainsaw((byte)49, 25, (byte)50));
      registerAnimation(new PlayerAnimations.CascAimChainsaw((byte)50, 25));
   }

   public static enum EnumMathOperation {
      PLUS,
      MINUS,
      MULTIPLY,
      DIVIDE,
      POWER,
      ROOT,
      ADD_MULTIPLIED,
      NONE;

      public float apply(float var1, float var2) {
         switch (this) {
            case PLUS:
               return var1 + var2;
            case MINUS:
               return var1 - var2;
            case MULTIPLY:
               return var1 * var2;
            case DIVIDE:
               return var1 / var2;
            case POWER:
               return (float)Math.pow(var1, var2);
            case ROOT:
               return (float)Math.pow(var1, 1.0F / var2);
            case ADD_MULTIPLIED:
               return var1 + var1 * var2;
            default:
               return var2;
         }
      }

      public double apply(double var1, double var2) {
         switch (this) {
            case PLUS:
               return var1 + var2;
            case MINUS:
               return var1 - var2;
            case MULTIPLY:
               return var1 * var2;
            case DIVIDE:
               return var1 / var2;
            case POWER:
               return Math.pow(var1, var2);
            case ROOT:
               return Math.pow(var1, 1.0 / var2);
            case ADD_MULTIPLIED:
               return var1 + var1 * var2;
            default:
               return var2;
         }
      }

      public int apply(int var1, int var2) {
         switch (this) {
            case PLUS:
               return var1 + var2;
            case MINUS:
               return var1 - var2;
            case MULTIPLY:
               return var1 * var2;
            case DIVIDE:
               return var1 / var2;
            case POWER:
               return (int)Math.pow(var1, var2);
            case ROOT:
               return (int)Math.pow(var1, 1.0F / var2);
            case ADD_MULTIPLIED:
               return var1 + var1 * var2;
            default:
               return var2;
         }
      }

      public long apply(long var1, long var2) {
         switch (this) {
            case PLUS:
               return var1 + var2;
            case MINUS:
               return var1 - var2;
            case MULTIPLY:
               return var1 * var2;
            case DIVIDE:
               return var1 / var2;
            case POWER:
               return (long)Math.pow(var1, var2);
            case ROOT:
               return (long)Math.pow(var1, 1.0F / (float)var2);
            case ADD_MULTIPLIED:
               return var1 + var1 * var2;
            default:
               return var2;
         }
      }

      public byte ordinalByte() {
         return (byte)this.ordinal();
      }

      @Nullable
      public static EnumMathOperation byId(int id) {
         return id >= 0 && id <= 7 ? values()[id] : null;
      }
   }

   public static enum EnumPotionMix {
      GREATEST,
      LOWEST,
      WITH_MAXIMUM,
      WITH_MINIMUM,
      ABSOLUTE;
   }

   public static class IllusiveItem extends Item {
      public IllusiveItem(String tool, int toolLevel) {
         this.setRegistryName("illusive_item");
         this.setTranslationKey("illusive_item");
         this.setHarvestLevel(tool, toolLevel);
      }
   }
}
