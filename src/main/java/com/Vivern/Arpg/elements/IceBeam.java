//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.blocks.IceSpikes;
import com.Vivern.Arpg.entity.EntityStreamLaserP;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.Freezing;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class IceBeam extends ItemWeapon {
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");
   ResourceLocation start = new ResourceLocation("arpg:textures/ice_beam.png");
   ResourceLocation snow = new ResourceLocation("arpg:textures/snowflake2.png");

   public IceBeam() {
      this.setRegistryName("ice_beam");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("ice_beam");
      this.setMaxDamage(10000);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 14.0F;
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

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      this.setCanShoot(itemstack, entityIn);
      if (IWeapon.canShoot(itemstack)) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         float mana = Mana.getMana(player);
         float spee = Mana.getManaSpeed(player);
         float power = Mana.getMagicPowerMax(player);
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, itemstack);
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
         float rapidMult = 1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * parameters.get("rapid_multiplier");
         float manacost = parameters.getEnchanted("manacost", sor) * rapidMult;
         boolean b1 = true;
         if (player.getHeldItemMainhand() == itemstack && mana > manacost && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
            Vec3d vec = GetMOP.PosRayTrace(edist, 1.0F, player, 0.3, 0.2);
            world.playSound(
               (EntityPlayer)null,
               player.posX,
               player.posY,
               player.posZ,
               Sounds.frost_shoot,
               SoundCategory.AMBIENT,
               0.5F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
            b1 = false;
            double damageRadius = parameters.get("damage_radius");
            AxisAlignedBB aabb = new AxisAlignedBB(
               vec.x - damageRadius,
               vec.y - damageRadius,
               vec.z - damageRadius,
               vec.x + damageRadius,
               vec.y + damageRadius,
               vec.z + damageRadius
            );
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
            if (!world.isRemote) {
               int animSend = NBTHelper.GetNBTint(itemstack, "animSend");
               if (animSend <= 0) {
                  NBTHelper.GiveNBTint(itemstack, 0, "animSend");
                  NBTHelper.SetNBTint(itemstack, 18, "animSend");
                  Weapons.setPlayerAnimationOnServer(player, 11, hand);
               } else {
                  NBTHelper.AddNBTint(itemstack, -1, "animSend");
               }

               if (!list.isEmpty()) {
                  for (Entity entity : list) {
                     if (Team.checkIsOpponent(player, entity)) {
                        Weapons.dealDamage(
                           new WeaponDamage(itemstack, player, null, false, false, player.getPositionEyes(1.0F), WeaponDamage.frost),
                           parameters.getEnchanted("damage", might) * rapidMult * power,
                           player,
                           entity,
                           true,
                           parameters.getEnchanted("knockback", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack)) * rapidMult,
                           player.posX,
                           player.posY,
                           player.posZ
                        );
                        entity.hurtResistantTime = 0;
                        if (entity instanceof EntityLivingBase) {
                           EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
                           PotionEffect eff = entitylivingbase.getActivePotionEffect(PotionEffects.FREEZING);
                           int dur = eff != null ? eff.getDuration() : 0;
                           PotionEffect lastdebaff = Weapons.mixPotion(
                              entitylivingbase,
                              PotionEffects.FREEZING,
                              parameters.getEnchantedi("potion_time_add", witchery) * rapidMult,
                              (float)((int)(dur * parameters.get("potion_power_depends_on_time") + 1.0F)),
                              Weapons.EnumPotionMix.WITH_MAXIMUM,
                              Weapons.EnumPotionMix.WITH_MAXIMUM,
                              Weapons.EnumMathOperation.PLUS,
                              Weapons.EnumMathOperation.PLUS,
                              parameters.getEnchantedi("potion_time_max", witchery),
                              parameters.getEnchantedi("potion_power_max", witchery)
                           );
                           Freezing.tryPlaySound(entitylivingbase, lastdebaff);
                           if (entitylivingbase.getHealth() <= 0.0F && itemRand.nextFloat() < 0.1) {
                              DeathEffects.applyDeathEffect(entitylivingbase, DeathEffects.DE_ICING);
                           }
                        }
                     }
                  }
               } else if (world.collidesWithAnyBlock(aabb)) {
                  Vec3d vec3d = player.getPositionEyes(1.0F);
                  Vec3d vec3d1 = player.getLook(1.0F);
                  Vec3d vec3d2 = vec3d.add(vec3d1.x * edist, vec3d1.y * edist, vec3d1.z * edist);
                  RayTraceResult result = player.world.rayTraceBlocks(vec3d, vec3d2, true, true, false);
                  if (result != null && result.typeOfHit == Type.BLOCK) {
                     BlockPos pos = result.getBlockPos();
                     BlockPos posoff = result.getBlockPos().offset(result.sideHit);
                     Block blockoff = world.getBlockState(posoff).getBlock();
                     Block lastblock = world.getBlockState(pos).getBlock();
                     if (lastblock == Blocks.WATER || lastblock == Blocks.FLOWING_WATER) {
                        world.setBlockState(pos, Blocks.FROSTED_ICE.getDefaultState());
                     } else if (blockoff == Blocks.WATER || blockoff == Blocks.FLOWING_WATER) {
                        world.setBlockState(posoff, Blocks.FROSTED_ICE.getDefaultState());
                     } else if (IceSpikes.canPlaceAt(worldIn, posoff, result.sideHit)
                        && (blockoff.isReplaceable(world, posoff) || blockoff == Blocks.AIR)) {
                        world.setBlockState(posoff, BlocksRegister.ICESPIKES.getDefaultState().withProperty(IceSpikes.FACING, result.sideHit));
                     }
                  }
               }
            }

            if (!player.capabilities.isCreativeMode) {
               Mana.changeMana(player, -manacost);
               Mana.setManaSpeed(player, 0.001F);
               itemstack.damageItem(GetMOP.floatToIntWithChance(rapidMult, itemRand), player);
            }

            float horizoffset = 0.0F;
            if (player.getHeldItemMainhand() == itemstack) {
               horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? 0.2F : -0.2F;
            }

            if (player.getHeldItemOffhand() == itemstack) {
               horizoffset = player.getPrimaryHand() == EnumHandSide.RIGHT ? -0.2F : 0.2F;
            }

            if (world.isRemote) {
               GUNParticle bigsmoke = new GUNParticle(
                  this.largesmoke,
                  0.8F + (float)itemRand.nextGaussian() / 13.0F,
                  0.0F,
                  15,
                  240,
                  world,
                  vec.x,
                  vec.y,
                  vec.z,
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
               GUNParticle bigsmoke2 = new GUNParticle(
                  this.snow,
                  0.15F + (float)itemRand.nextGaussian() / 20.0F,
                  0.01F,
                  10 + itemRand.nextInt(20),
                  180,
                  world,
                  vec.x,
                  vec.y,
                  vec.z,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  (float)itemRand.nextGaussian() / 20.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  false,
                  itemRand.nextInt(360),
                  true,
                  1.3F
               );
               world.spawnEntity(bigsmoke2);
               EntityStreamLaserP laser = new EntityStreamLaserP(
                  world,
                  player,
                  this.start,
                  0.21F,
                  240,
                  1.0F,
                  0.9F,
                  0.9F,
                  0.5F,
                  player.getDistance(vec.x, vec.y, vec.z),
                  1,
                  0.3F,
                  8.0F
               );
               laser.setPosition(player.posX, player.posY + 1.55, player.posZ);
               laser.horizOffset = horizoffset;
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
         }

         if (b1 && !world.isRemote) {
            NBTHelper.SetNBTint(itemstack, 0, "animSend");
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      GUNParticle bigsmoke = new GUNParticle(
         this.largesmoke,
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
      GUNParticle bigsmoke2 = new GUNParticle(
         this.snow,
         0.15F + (float)itemRand.nextGaussian() / 20.0F,
         0.01F,
         10 + itemRand.nextInt(20),
         180,
         world,
         x,
         y,
         z,
         (float)itemRand.nextGaussian() / 20.0F,
         (float)itemRand.nextGaussian() / 20.0F,
         (float)itemRand.nextGaussian() / 20.0F,
         1.0F,
         1.0F,
         1.0F,
         false,
         itemRand.nextInt(360),
         true,
         1.3F
      );
      world.spawnEntity(bigsmoke2);
      double dd0 = d1 - x;
      double dd1 = d2 - y;
      double dd2 = d3 - z;
      double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
      EntityStreamLaserP laser = new EntityStreamLaserP(
         world, this.start, 0.21F, 240, 1.0F, 0.9F, 0.9F, 0.5F, dist, 1, 0.3F, 8.0F, (float)a, (float)b, player.ticksExisted
      );
      laser.setPosition(d1, d2 + 1.55, d3);
      laser.horizOffset = (float)c;
      world.spawnEntity(laser);
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return true;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }

   @Override
   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.type == EnchantmentInit.enchantmentTypeWeapon;
   }
}
