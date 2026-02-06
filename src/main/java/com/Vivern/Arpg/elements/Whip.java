package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.animation.EnumFlick;
import com.Vivern.Arpg.elements.animation.Flicks;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Whip extends ItemWeapon {
   public static ResourceLocation forge_hit_a = new ResourceLocation("arpg:textures/forge_hit_a.png");
   public static ResourceLocation largecloud = new ResourceLocation("arpg:textures/largecloud.png");
   public static ResourceLocation sweep_cloud = new ResourceLocation("arpg:textures/sweep_cloud.png");
   public static ResourceLocation pixel = new ResourceLocation("arpg:textures/pixel.png");
   public SoundEvent soundSwosh = Sounds.swosh_e;

   public Whip(String name, int maxDamage) {
      this.setRegistryName(name);
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey(name);
      this.setMaxDamage(maxDamage);
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

   @Override
   public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
      if (state == 1) {
         int cooldown = this.getCooldownTime(itemstack);
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.ATTACK, 0, cooldown, 1, 0);
      }

      if (state == 2) {
         int cooldown = this.getCooldownTime(itemstack);
         Flicks.instance.setClientAnimation(player, slot, EnumFlick.ATTACK, 0, cooldown - 4, 1, 7);
      }
   }

   public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
      return new AnimationCapabilityProvider();
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            EnumHand hand = null;
            float cooldown = 0.0F;
            boolean keypressed = false;
            if (player.getHeldItemMainhand() == itemstack) {
               hand = EnumHand.MAIN_HAND;
               cooldown = player.getCooldownTracker().getCooldown(this, 0.0F);
               keypressed = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            } else if (player.getHeldItemOffhand() == itemstack) {
               hand = EnumHand.OFF_HAND;
               cooldown = player.getCooldownTracker().getCooldown(ItemsRegister.EXP, 0.0F);
               keypressed = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            }

            NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
            int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
            if (delay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "atdelay");
            }

            if (hand != null) {
               WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
               float soundPitchSwosh = parameters.get("sound_pitch_swosh");
               float soundPitchClap = parameters.get("sound_pitch_clap");
               this.onWhipUpdateInHand(world, itemstack, player, hand, itemSlot, keypressed);
               if (delay == 0 && cooldown > 0.2F && this.isSpecattackReady(world, itemstack, player, hand, itemSlot, keypressed)) {
                  int specAttackReady = NBTHelper.GetNBTint(itemstack, "sa_ready");
                  if (keypressed && specAttackReady == 2) {
                     Vec3d hitVec = this.specAttack(this, itemstack, player, hand);
                     player.getCooldownTracker().setCooldown((Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), 7);
                     NBTHelper.SetNBTint(itemstack, 0, "sa_ready");
                     IWeapon.sendIWeaponState(itemstack, player, 2, itemSlot, hand);
                     Weapons.setPlayerAnimationOnServer(player, 36, hand);
                  }

                  if (!keypressed && specAttackReady == 1) {
                     NBTHelper.SetNBTint(itemstack, 2, "sa_ready");
                  }
               }

               if (delay <= 0 && cooldown <= 0.0F && keypressed) {
                  NBTHelper.SetNBTint(itemstack, 11, "atdelay");
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  int cooldownModified = this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack));
                  player.getCooldownTracker().setCooldown((Item)(hand == EnumHand.MAIN_HAND ? this : ItemsRegister.EXP), cooldownModified);
                  IWeapon.sendIWeaponState(itemstack, player, 1, itemSlot, hand);
                  Weapons.setPlayerAnimationOnServer(player, getPlayerAnimationByCooldown(cooldownModified), hand);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     this.soundSwosh,
                     SoundCategory.PLAYERS,
                     0.85F,
                     soundPitchSwosh + itemRand.nextFloat() / 5.0F
                  );
               }

               if (delay == 4) {
                  Vec3d vec3dEyes = player.getPositionEyes(1.0F);
                  Vec3d vec3dLook = player.getLook(1.0F);
                  float length = parameters.getEnchanted("length", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                  Vec3d vec3dEnd = vec3dEyes.add(vec3dLook.x * length, vec3dLook.y * length, vec3dLook.z * length);
                  RayTraceResult resultHit = world.rayTraceBlocks(vec3dEyes, vec3dEnd, false, true, false);
                  Vec3d vec3dHit = resultHit == null ? vec3dEnd : resultHit.hitVec;
                  world.playSound(
                     (EntityPlayer)null,
                     vec3dHit.x,
                     vec3dHit.y,
                     vec3dHit.z,
                     Sounds.whip_clap,
                     SoundCategory.PLAYERS,
                     2.0F,
                     soundPitchClap + itemRand.nextFloat() / 5.0F
                  );
               }

               if (delay == 1) {
                  MeleeAttackResult maResult = IWeapon.doMeleeWhipAttack(this, itemstack, player, hand);
                  RayTraceResult result = maResult.raytrace;
                  IWeapon.fireEffect(
                     this,
                     player,
                     world,
                     64.0,
                     result.hitVec.x,
                     result.hitVec.y,
                     result.hitVec.z,
                     result.typeOfHit != Type.BLOCK ? 1.0 : 0.0,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     0.0
                  );
                  player.addExhaustion(0.1F);
                  NBTHelper.GiveNBTint(itemstack, 0, "sa_ready");
                  if (!keypressed) {
                     NBTHelper.SetNBTint(itemstack, 2, "sa_ready");
                  } else {
                     NBTHelper.SetNBTint(itemstack, 1, "sa_ready");
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      float artropods = 1.0F;
      float holy = 1.0F;
      if (entity instanceof EntityLivingBase) {
         artropods = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack) * 0.1F + 1.0F
            : 1.0F;
         holy = ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD
            ? EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack) * 0.1F + 1.0F
            : 1.0F;
      }

      boolean ret = entity.attackEntityFrom(
         DamageSource.causePlayerDamage(player),
         (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * artropods * holy * (isCritical ? 1.8F : 1.0F)
      );
      entity.hurtResistantTime = 0;
      int firelvl = WeaponParameters.getWeaponParameters(stack.getItem())
         .getEnchantedi("fire", EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack));
      if (firelvl > 0) {
         entity.setFire(firelvl);
      }

      return ret;
   }

   public void onWhipUpdateInHand(World world, ItemStack itemstack, EntityPlayer player, EnumHand hand, int itemSlot, boolean keypressed) {
      NBTHelper.GiveNBTint(itemstack, 0, "charge");
      int charge = NBTHelper.GetNBTint(itemstack, "charge");
      if (charge < 0) {
         NBTHelper.AddNBTint(itemstack, 1, "charge");
      }
   }

   public boolean isSpecattackReady(World world, ItemStack itemstack, EntityPlayer player, EnumHand hand, int itemSlot, boolean keypressed) {
      int charge = NBTHelper.GetNBTint(itemstack, "charge");
      return charge >= 0;
   }

   public Vec3d specAttack(IWeapon iweapon, ItemStack stack, EntityPlayer player, EnumHand hand) {
      World world = player.world;
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
      float damage = parameters.getEnchanted("splash_damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack));
      float knockback = parameters.getEnchanted("splash_knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack));
      float length = parameters.getEnchanted("length", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack));
      float size = 0.85F;
      float endSize = parameters.getEnchanted("splash_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, stack));
      Vec3d vec = GetMOP.PosRayTrace(length, 1.0F, player, size, size / 2.0F);
      AxisAlignedBB aabb = new AxisAlignedBB(
         vec.x - endSize,
         vec.y - endSize,
         vec.z - endSize,
         vec.x + endSize,
         vec.y + endSize,
         vec.z + endSize
      );
      List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
      stack.damageItem(1, player);
      boolean ret = false;
      boolean killSomeone = false;
      if (!list.isEmpty()) {
         for (Entity entity : list) {
            if (Team.checkIsOpponent(player, entity)
               && Weapons.dealDamage(
                  new WeaponDamage(stack, player, null, false, false, vec, WeaponDamage.blade),
                  damage,
                  player,
                  entity,
                  false,
                  knockback,
                  vec.x,
                  vec.y,
                  vec.z
               )) {
               this.onSpecAttackDamage(entity, vec, iweapon, stack, player, hand, damage);
               entity.hurtResistantTime = 0;
               ret = true;
               if (!killSomeone && !entity.isEntityAlive()) {
                  killSomeone = true;
               }
            }
         }
      }

      this.onSpecAttack(vec, iweapon, stack, player, hand, damage, ret, killSomeone);
      return vec;
   }

   public void onSpecAttack(
      Vec3d pos, IWeapon iweapon, ItemStack itemstack, EntityPlayer player, EnumHand hand, float damage, boolean hitSomeone, boolean killSomeone
   ) {
      player.world
         .playSound(
            (EntityPlayer)null,
            player.posX,
            player.posY,
            player.posZ,
            Sounds.whip_specattack,
            SoundCategory.PLAYERS,
            1.8F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
      int whipMaxCharge = WeaponParameters.getWeaponParameters(this)
         .getEnchantedi("max_charge", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
      NBTHelper.SetNBTint(itemstack, -whipMaxCharge, "charge");
      Vec3d poseyes = player.getPositionEyes(1.0F);
      Vec3d scaledVec = new Vec3d(
         poseyes.x * 0.25 + pos.x * 0.75,
         poseyes.y * 0.25 + pos.y * 0.75,
         poseyes.z * 0.25 + pos.z * 0.75
      );
      IWeapon.fireEffect(
         this,
         player,
         player.world,
         64.0,
         scaledVec.x,
         scaledVec.y,
         scaledVec.z,
         hand == EnumHand.MAIN_HAND ? 2.0 : 3.0,
         (double)player.rotationPitch,
         (double)player.rotationYaw,
         0.0,
         0.0,
         0.0
      );
   }

   public void onSpecAttackDamage(Entity entity, Vec3d pos, IWeapon iweapon, ItemStack itemstack, EntityPlayer player, EnumHand hand, float damage) {
      WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
      int time = parameters.getEnchantedi("potion_time", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack));
      int power = parameters.geti("potion_power");
      Weapons.setPotionIfEntityLB(entity, MobEffects.SLOWNESS, time, power);
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      Booom.lastTick = 16;
      Booom.frequency = -0.196F;
      Booom.x = 1.0F;
      Booom.y = 0.0F;
      Booom.z = 0.0F;
      Booom.power = 0.42F * param;
      Booom.FOVlastTick = 6;
      Booom.FOVfrequency = -0.5F;
      Booom.FOVpower = 0.03F;
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      if (a != 2.0 && a != 3.0) {
         GUNParticle part1 = new GUNParticle(
            forge_hit_a,
            0.12F + itemRand.nextFloat() * 0.1F,
            0.0F,
            4,
            240,
            world,
            x,
            y,
            z,
            0.0F,
            0.0F,
            0.0F,
            1.0F,
            1.0F,
            1.0F,
            true,
            itemRand.nextInt(360)
         );
         part1.scaleTickAdding = 0.07F;
         part1.alphaGlowing = true;
         world.spawnEntity(part1);
         if (a == 1.0) {
            for (int ss = 0; ss < 3; ss++) {
               int lt = 15 + itemRand.nextInt(13);
               GUNParticle bigsmoke = new GUNParticle(
                  largecloud,
                  0.16F + itemRand.nextFloat() * 0.16F,
                  -0.004F,
                  lt,
                  240,
                  world,
                  x,
                  y,
                  z,
                  (float)itemRand.nextGaussian() / 26.0F,
                  (float)itemRand.nextGaussian() / 26.0F,
                  (float)itemRand.nextGaussian() / 26.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  itemRand.nextInt(360)
               );
               bigsmoke.alphaGlowing = true;
               bigsmoke.alpha = 0.7F;
               bigsmoke.alphaTickAdding = -0.7F / lt;
               bigsmoke.scaleTickAdding = 0.01F;
               world.spawnEntity(bigsmoke);
            }
         }
      } else {
         boolean lh = a == 3.0;
         int lt = 42;
         float scl = 1.6F + itemRand.nextFloat() * 0.3F;
         AnimatedGParticle part = new AnimatedGParticle(
            sweep_cloud, scl, 0.0F, lt, 220, world, x, y, z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, lh ? 45 : 225
         );
         part.framecount = 11;
         part.animDelay = 3;
         part.disableOnEnd = true;
         if (lh) {
            part.rotationPitchYaw = new Vec2f((float)b + 60.0F, (float)c);
         } else {
            part.rotationPitchYaw = new Vec2f((float)b + 240.0F, (float)c);
         }

         world.spawnEntity(part);

         for (int ss = 0; ss < 10; ss++) {
            GUNParticle part1 = new GUNParticle(
               pixel,
               0.025F + itemRand.nextFloat() * 0.00625F,
               0.01F,
               40 + itemRand.nextInt(20),
               200 + itemRand.nextInt(40),
               world,
               x + itemRand.nextGaussian() * 0.7,
               y,
               z + itemRand.nextGaussian() * 0.7,
               (float)itemRand.nextGaussian() / 25.0F,
               (float)itemRand.nextGaussian() / 17.0F + 0.16F,
               (float)itemRand.nextGaussian() / 25.0F,
               1.0F,
               1.0F,
               1.0F,
               false,
               0
            );
            part1.scaleTickAdding = -5.0E-4F;
            world.spawnEntity(part1);
         }
      }
   }

   public static byte getPlayerAnimationByCooldown(int cooldown) {
      if (cooldown >= 26) {
         return 42;
      } else if (cooldown >= 24) {
         return 41;
      } else if (cooldown >= 22) {
         return 40;
      } else if (cooldown >= 20) {
         return 39;
      } else {
         return (byte)(cooldown >= 18 ? 38 : 37);
      }
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return NBTHelper.GetNBTint(itemstack, "charge") < 0;
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      int whipMaxCharge = WeaponParameters.getWeaponParameters(this)
         .getEnchantedi("max_charge", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack));
      return 1.0F - MathHelper.clamp((float)(-NBTHelper.GetNBTint(itemstack, "charge")) / whipMaxCharge, 0.0F, 1.0F);
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
