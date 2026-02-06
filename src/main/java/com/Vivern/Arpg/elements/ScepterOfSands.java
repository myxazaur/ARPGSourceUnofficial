package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntitySand;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ScepterOfSands extends ItemWeapon {
   private static final ResourceLocation minisand1 = new ResourceLocation("arpg:textures/minisand1.png");
   private static final ResourceLocation minisand2 = new ResourceLocation("arpg:textures/minisand2.png");
   private static final ResourceLocation minisand3 = new ResourceLocation("arpg:textures/minisand3.png");

   public ScepterOfSands() {
      this.setRegistryName("scepter_of_sands");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("scepter_of_sands");
      this.setMaxDamage(10000);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 20.0F;
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
         int damage = itemstack.getItemDamage();
         boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
         float mana = Mana.getMana(player);
         NBTHelper.GiveNBTint(itemstack, 0, "crystal");
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
         float manacost = parameters.getEnchanted("manacost", sor);
         float power = Mana.getMagicPowerMax(player);
         EnumHand hand = player.getHeldItemMainhand() == itemstack ? EnumHand.MAIN_HAND : (player.getHeldItemOffhand() == itemstack ? EnumHand.OFF_HAND : null);
         float rapidMult = 1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack) * parameters.get("rapid_multiplier");
         manacost *= rapidMult;
         int rapidOne = GetMOP.floatToIntWithChance(rapidMult, itemRand);
         if (hand != null && mana > manacost && click) {
            if (!world.isRemote) {
               int crystal = NBTHelper.GetNBTint(itemstack, "crystal");
               if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0 && crystal < parameters.geti("max_crystal_charge")) {
                  NBTHelper.AddNBTint(itemstack, rapidOne, "crystal");
                  if (crystal < 64 && crystal + rapidOne >= 64) {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.rrrrar,
                        SoundCategory.AMBIENT,
                        0.7F,
                        0.9F + itemRand.nextFloat() / 5.0F
                     );
                  }
               }

               Weapons.setPlayerAnimationOnServer(player, 22, hand);
               world.playSound(
                  (EntityPlayer)null, player.posX, player.posY, player.posZ, Sounds.sand_scepter, SoundCategory.AMBIENT, 0.7F, 1.3F
               );
               player.addStat(StatList.getObjectUseStats(this));

               for (int i = 0; i < rapidOne; i++) {
                  EntitySand sand = new EntitySand(world, player, itemstack, power);
                  Weapons.shoot(
                     sand,
                     hand,
                     player,
                     player.rotationPitch - 3.0F,
                     player.rotationYaw,
                     0.0F,
                     parameters.get("velocity"),
                     parameters.getEnchanted("inaccuracy", acc),
                     -0.4F,
                     0.0F,
                     0.2F,
                     0.0F
                  );
                  sand.livetime = parameters.getEnchantedi("livetime", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
                  sand.crystalCharge = crystal;
                  world.spawnEntity(sand);
               }
            }

            if (world.isRemote) {
               for (int i = 0; i < 3; i++) {
                  ResourceLocation sandptex = minisand1;
                  float scale = 0.04F;
                  if (itemRand.nextFloat() > 0.8) {
                     sandptex = minisand2;
                     scale = 0.06F;
                  } else if (itemRand.nextFloat() > 0.7) {
                     sandptex = minisand3;
                     scale = 0.09F;
                  }

                  GUNParticle sandp = new GUNParticle(
                     sandptex,
                     scale,
                     0.01F,
                     30 + itemRand.nextInt(15),
                     -1,
                     world,
                     player.posX,
                     player.posY + player.getEyeHeight() - 0.1F,
                     player.posZ,
                     (float)itemRand.nextGaussian() / 30.0F,
                     (float)itemRand.nextGaussian() / 30.0F,
                     (float)itemRand.nextGaussian() / 30.0F,
                     1.0F,
                     1.0F,
                     0.9F + (float)itemRand.nextGaussian() / 10.0F,
                     false,
                     itemRand.nextInt(360),
                     true,
                     1.1F
                  );
                  sandp.shoot(player, player.rotationPitch, player.rotationYaw, 0.5F, 1.6F, 9.4F - acc);
                  world.spawnEntity(sandp);
               }
            }

            int traceCount = GetMOP.floatToIntWithChance(rapidMult * parameters.get("trace_count"), itemRand);

            for (int i = 0; i < traceCount; i++) {
               double edist = parameters.getEnchanted("distance", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack));
               float spread = parameters.getEnchanted("inaccuracy_trace", acc);
               Vec3d end = GetMOP.RotatedPosRayTrace(
                  edist,
                  1.0F,
                  player,
                  0.1,
                  0.1,
                  player.rotationPitch + (itemRand.nextFloat() - 0.5F) * spread,
                  player.rotationYaw + (itemRand.nextFloat() - 0.5F) * spread
               );
               AxisAlignedBB aabbox = new AxisAlignedBB(
                  end.x - 0.7,
                  end.y - 0.7,
                  end.z - 0.7,
                  end.x + 0.7,
                  end.y + 0.7,
                  end.z + 0.7
               );
               List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, aabbox);
               if (!list.isEmpty()) {
                  for (EntityLivingBase entitylivingbase : list) {
                     if (entitylivingbase != player) {
                        SuperKnockback.applyKnockback(
                           entitylivingbase,
                           parameters.getEnchanted("knockback_trace", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack)),
                           player.posX,
                           player.posY,
                           player.posZ
                        );
                        if (world.isRemote) {
                           AxisAlignedBB aabb = entitylivingbase.getEntityBoundingBox();
                           double rY = aabb.minY + (aabb.maxY - aabb.minY) * itemRand.nextDouble();
                           double rZ = aabb.minZ + (aabb.maxZ - aabb.minZ) * itemRand.nextDouble();
                           double rX = aabb.minX + (aabb.maxX - aabb.minX) * itemRand.nextDouble();
                           double fromX = player.posX;
                           double fromY = player.posY + player.getEyeHeight() / 2.0;
                           double fromZ = player.posZ;
                           ResourceLocation sandptex = minisand1;
                           float scale = 0.04F;
                           if (itemRand.nextFloat() > 0.8) {
                              sandptex = minisand2;
                              scale = 0.06F;
                           } else if (itemRand.nextFloat() > 0.7) {
                              sandptex = minisand3;
                              scale = 0.09F;
                           }

                           GUNParticle sandp = new GUNParticle(
                              sandptex,
                              scale,
                              0.01F,
                              30 + itemRand.nextInt(15),
                              -1,
                              world,
                              rX,
                              rY,
                              rZ,
                              (float)itemRand.nextGaussian() / 30.0F,
                              (float)itemRand.nextGaussian() / 30.0F,
                              (float)itemRand.nextGaussian() / 30.0F,
                              1.0F,
                              1.0F,
                              0.9F + (float)itemRand.nextGaussian() / 10.0F,
                              false,
                              itemRand.nextInt(360),
                              true,
                              1.1F
                           );
                           world.spawnEntity(sandp);
                           float dist = (float)sandp.getDistance(fromX, fromY, fromZ);
                           float kx = (float)((fromX - sandp.posX) / dist / 2.0);
                           float ky = (float)((fromY - sandp.posY) / dist / 2.0);
                           float kz = (float)((fromZ - sandp.posZ) / dist / 2.0);
                           sandp.motionX -= kx;
                           sandp.motionY -= ky;
                           sandp.motionZ -= kz;
                        }
                     }
                  }
               }
            }

            if (!world.isRemote && !player.capabilities.isCreativeMode) {
               Mana.changeMana(player, -manacost);
               Mana.setManaSpeed(player, 0.001F);
               itemstack.damageItem(rapidOne, player);
            }
         } else if (NBTHelper.GetNBTint(itemstack, "crystal") > 0) {
            NBTHelper.AddNBTint(itemstack, -1, "crystal");
         }
      }
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }
}
