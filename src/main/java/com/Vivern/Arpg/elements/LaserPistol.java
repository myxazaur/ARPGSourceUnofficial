package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityStreamLaserP;
import com.Vivern.Arpg.main.DeathEffects;
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
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class LaserPistol extends ItemWeapon {
   ResourceLocation texture = new ResourceLocation("arpg:textures/laser_sniper_laser.png");
   ResourceLocation res = new ResourceLocation("arpg:textures/greenlaser.png");

   public LaserPistol() {
      this.setRegistryName("laser_pistol");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("laser_pistol");
      this.setMaxDamage(8200);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 6.0F;
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

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      this.setCanShoot(itemstack, entityIn);
      if (IWeapon.canShoot(itemstack)) {
         EntityPlayer player = (EntityPlayer)entityIn;
         this.decreaseReload(itemstack, player);
         int ammo = NBTHelper.GetNBTint(itemstack, "ammo");
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
         EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
         boolean b1 = true;
         if (hand != null && (click && hand == EnumHand.MAIN_HAND || click2 && hand == EnumHand.OFF_HAND)) {
            if (ammo > 0 && this.isReloaded(itemstack)) {
               if (!player.getCooldownTracker().hasCooldown(this)) {
                  float rapidMult = 1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * parameters.get("rapid_multiplier");
                  float size = parameters.getEnchanted("damage_radius", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                  double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                  b1 = false;
                  RayTraceResult result = GetMOP.fixedRayTraceBlocks(
                     world,
                     player,
                     edist,
                     size,
                     size,
                     true,
                     false,
                     true,
                     false,
                     player.rotationPitch,
                     player.rotationYaw + (hand == EnumHand.MAIN_HAND ? 1 : -1)
                  );
                  Vec3d vec = result.hitVec;
                  float horizoffset = 0.0F;
                  if (player.getHeldItemMainhand() == itemstack) {
                     horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? 0.2F : -0.2F;
                  }

                  if (player.getHeldItemOffhand() == itemstack) {
                     horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? -0.2F : 0.2F;
                  }

                  if (world.isRemote) {
                     if (result.sideHit != null) {
                        float displaceX = result.sideHit.getXOffset() * 0.0625F;
                        float displaceY = result.sideHit.getYOffset() * 0.0625F;
                        float displaceZ = result.sideHit.getZOffset() * 0.0625F;
                        GUNParticle bigsmoke = new GUNParticle(
                           this.res,
                           0.2F + itemRand.nextFloat() * 0.1F,
                           0.0F,
                           45,
                           240,
                           world,
                           vec.x + displaceX,
                           vec.y + displaceY,
                           vec.z + displaceZ,
                           0.0F,
                           0.0F,
                           0.0F,
                           1.0F,
                           1.0F,
                           1.0F,
                           true,
                           itemRand.nextInt(360)
                        );
                        bigsmoke.alphaTickAdding = -0.022F;
                        bigsmoke.alphaGlowing = true;
                        bigsmoke.scaleTickAdding = -0.0044F;
                        bigsmoke.snapToFace(result.sideHit);
                        world.spawnEntity(bigsmoke);
                     }

                     EntityStreamLaserP laser = new EntityStreamLaserP(
                        world,
                        player,
                        this.texture,
                        0.07F,
                        240,
                        0.5F,
                        1.0F,
                        0.5F,
                        0.5F,
                        player.getDistance(vec.x, vec.y, vec.z),
                        1,
                        0.0F,
                        1.0F
                     );
                     laser.setPosition(player.posX, player.posY + 1.55, player.posZ);
                     laser.horizOffset = horizoffset;
                     world.spawnEntity(laser);
                  } else {
                     if (player.ticksExisted % 2 == 0) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.laserpistol,
                           SoundCategory.AMBIENT,
                           0.7F,
                           0.95F + itemRand.nextFloat() / 10.0F
                        );
                     }

                     player.addStat(StatList.getObjectUseStats(this));
                     IWeapon.fireBomEffect(this, player, world, 0);
                     int animSend = NBTHelper.GetNBTint(itemstack, "animSend");
                     if (animSend <= 0) {
                        NBTHelper.GiveNBTint(itemstack, 0, "animSend");
                        NBTHelper.SetNBTint(itemstack, 18, "animSend");
                        Weapons.setPlayerAnimationOnServer(player, 10, hand);
                     } else {
                        NBTHelper.AddNBTint(itemstack, -1, "animSend");
                     }

                     float siz = size + parameters.get("damage_radius");
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        vec.x - siz,
                        vec.y - siz,
                        vec.z - siz,
                        vec.x + siz,
                        vec.y + siz,
                        vec.z + siz
                     );
                     List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
                     float wdamage = parameters.getEnchanted("damage", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack));
                     float wknockback = parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack));
                     if (!list.isEmpty()) {
                        for (Entity entitylivingbase : list) {
                           if (Team.checkIsOpponent(player, entitylivingbase)) {
                              Weapons.dealDamage(
                                 new WeaponDamage(itemstack, player, null, false, false, player.getPositionEyes(1.0F), WeaponDamage.laser),
                                 wdamage * rapidMult,
                                 player,
                                 entitylivingbase,
                                 true,
                                 wknockback * rapidMult
                              );
                              entitylivingbase.hurtResistantTime = 0;
                              DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_FIRE, 0.2F);
                           }
                        }
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
                     if (!player.capabilities.isCreativeMode) {
                        int r = GetMOP.floatToIntWithChance(rapidMult, itemRand);
                        this.addAmmo(ammo, itemstack, -r);
                        itemstack.damageItem(r, player);
                     }
                  }
               }
            } else if (this.initiateMetadataReload(
               itemstack, player, new ItemStack(ItemsRegister.IONBATTERY, 1, 1), this.getMaxAmmo(itemstack), new ItemStack(ItemsRegister.IONBATTERY, 1, 0)
            )) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.lasergun_rel,
                  SoundCategory.NEUTRAL,
                  0.7F,
                  0.95F + itemRand.nextFloat() / 10.0F
               );
               Weapons.setPlayerAnimationOnServer(player, 4, EnumHand.MAIN_HAND);
            }
         }

         if (b1 && !world.isRemote) {
            NBTHelper.SetNBTint(itemstack, 0, "animSend");
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      GUNParticle bigsmoke = new GUNParticle(
         this.res,
         0.8F + (float)itemRand.nextGaussian() / 13.0F,
         0.0F,
         15,
         240,
         world,
         x,
         y,
         z,
         (float)itemRand.nextGaussian() / 20.0F,
         (float)itemRand.nextGaussian() / 25.0F,
         (float)itemRand.nextGaussian() / 20.0F,
         0.6F + (float)itemRand.nextGaussian() / 10.0F,
         0.7F,
         1.0F,
         true,
         itemRand.nextInt(360)
      );
      bigsmoke.alphaTickAdding = -0.05F;
      bigsmoke.alphaGlowing = true;
      world.spawnEntity(bigsmoke);
      double dd0 = d1 - x;
      double dd1 = d2 - y;
      double dd2 = d3 - z;
      double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
      EntityStreamLaserP laser = new EntityStreamLaserP(
         world, this.texture, 0.07F, 240, 0.5F, 1.0F, 0.5F, 0.5F, dist, 1, 0.0F, 1.0F, (float)a, (float)b, player.ticksExisted
      );
      laser.setPosition(d1, d2 + 1.55, d3);
      laser.horizOffset = (float)c;
      world.spawnEntity(laser);
   }

   public void onUpdate2(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Mouse.isButtonDown(1);
         if (player.getActiveItemStack() == itemstack && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            Vec3d vec = GetMOP.PosRayTrace(15.0, 1.0F, player, 0.05, 0.05);
            if (!worldIn.isRemote) {
               if (damage <= itemIn.getMaxDamage() - 1) {
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.laserpistol,
                     SoundCategory.AMBIENT,
                     0.7F,
                     1.0F
                  );
                  player.addStat(StatList.getObjectUseStats(this));
                  AxisAlignedBB aabb = new AxisAlignedBB(
                     vec.x - 0.1,
                     vec.y - 0.1,
                     vec.z - 0.1,
                     vec.x + 0.1,
                     vec.y + 0.1,
                     vec.z + 0.1
                  );
                  List<EntityLivingBase> list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
                  if (!list.isEmpty()) {
                     for (EntityLivingBase entitylivingbase : list) {
                        if (entitylivingbase != (EntityLivingBase)entityIn) {
                           IAttributeInstance entityAttribute = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
                           double baseValue = entityAttribute.getBaseValue();
                           entityAttribute.setBaseValue(1.0);
                           entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), 1.0F);
                           entitylivingbase.hurtResistantTime = 0;
                           entityAttribute.setBaseValue(baseValue);
                        }
                     }
                  }

                  if (!player.capabilities.isCreativeMode) {
                     itemstack.damageItem(1, player);
                  }
               }

               if (itemstack.getItemDamage() > itemIn.getMaxDamage() - 1 && player.inventory.hasItemStack(new ItemStack(ItemsRegister.IONBATTERY, 1))) {
                  player.inventory.clearMatchingItems(new ItemStack(ItemsRegister.IONBATTERY, 1).getItem(), -1, 1, null);
                  itemstack.setItemDamage(0);
                  player.getCooldownTracker().setCooldown(this, 40);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.laserpistol_rel,
                     SoundCategory.NEUTRAL,
                     0.7F,
                     0.6F / (itemRand.nextFloat() * 0.4F + 0.8F)
                  );
               }
            }

            if (worldIn.isRemote && damage <= itemIn.getMaxDamage() - 1) {
               Entity spelll = new GUNParticle(
                  this.res, 0.3F, -0.05F, 7, 240, world, vec.x, vec.y, vec.z, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, true, 0
               );
               world.spawnEntity(spelll);
               EntityStreamLaserP laser = new EntityStreamLaserP(
                  world,
                  player,
                  this.texture,
                  0.07F,
                  240,
                  0.5F,
                  1.0F,
                  0.5F,
                  0.5F,
                  player.getDistance(vec.x, vec.y, vec.z),
                  1,
                  0.0F,
                  1.0F
               );
               laser.setPosition(player.posX, player.posY + 1.55, player.posZ);
               world.spawnEntity(laser);
            }
         }
      }
   }

   public int getMaxAmmo(ItemStack itemstack) {
      return WeaponParameters.getWeaponParameters(this).getEnchantedi("clipsize", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REUSE, itemstack));
   }

   @Override
   public float getAdditionalDurabilityBar(ItemStack itemstack) {
      return MathHelper.clamp((float)NBTHelper.GetNBTint(itemstack, "ammo") / this.getMaxAmmo(itemstack), 0.0F, 1.0F);
   }

   @Override
   public boolean hasAdditionalDurabilityBar(ItemStack itemstack) {
      return true;
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
}
