package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class GlacideBlade extends ItemWeapon {
   static ResourceLocation sparkle = new ResourceLocation("arpg:textures/snowflake3.png");

   public GlacideBlade() {
      this.setRegistryName("glacide_blade");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("glacide_blade");
      this.setMaxDamage(800);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            EnumHand hand = null;
            if (click && player.getHeldItemMainhand() == itemstack && !hascooldown) {
               hand = EnumHand.MAIN_HAND;
            } else if (click2 && player.getHeldItemOffhand() == itemstack && !hascooldown) {
               hand = EnumHand.OFF_HAND;
            }

            if (hand != null) {
               MeleeAttackResult meleeAttackResult = IWeapon.doMeleeSwordAttack(this, itemstack, player, hand, false);
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.glacide_blade,
                  SoundCategory.PLAYERS,
                  1.0F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
               Weapons.setPlayerAnimationOnServer(player, 1, hand);
               player.addExhaustion(0.1F);
               double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
               player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               Vec3d vec = meleeAttackResult.position;
               IWeapon.fireEffect(this, player, world, 64.0, vec.x, vec.y, vec.z, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
               if (meleeAttackResult.success && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack) > 0) {
                  for (int i = 0; i < 10; i++) {
                     BlockPos pos = new BlockPos(
                        vec.x + itemRand.nextGaussian(),
                        vec.y + itemRand.nextGaussian(),
                        vec.z + itemRand.nextGaussian()
                     );
                     boolean isnotl = !world.getBlockState(pos).getMaterial().isLiquid();
                     if (isnotl && world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
                        IBlockState dn = world.getBlockState(pos.down());
                        if (Blocks.SNOW_LAYER.canPlaceBlockAt(world, pos)
                           && dn.getBlock() != Blocks.SNOW_LAYER
                           && dn.getBlock() != BlocksRegister.LOOSESNOW) {
                           world.setBlockState(pos, Blocks.SNOW_LAYER.getDefaultState());
                        } else if (dn.getBlock() == Blocks.ICE || dn.getBlock() == Blocks.FROSTED_ICE) {
                           world.setBlockState(pos, BlocksRegister.LOOSESNOW.getDefaultState());
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      BlockPos pos = new BlockPos(x, y, z);
      int light = Math.max(world.getLightFor(EnumSkyBlock.BLOCK, pos), world.getLightFor(EnumSkyBlock.SKY, pos)) * 15;
      int countOfParticles = 30;
      float R = 0.13F + (float)itemRand.nextGaussian() / 40.0F;

      for (int i = 0; i < countOfParticles; i++) {
         float rand1 = itemRand.nextFloat() * 2.0F - 1.0F;
         float rand2 = itemRand.nextFloat() * 2.0F - 1.0F;
         float X = rand1 * R;
         float new_R = (float)Math.sqrt(R * R - X * X);
         float Y = rand2 * new_R;
         float Z = (float)Math.sqrt(new_R * new_R - Y * Y);
         if (itemRand.nextBoolean()) {
            Z *= -1.0F;
         }

         GUNParticle particle = new GUNParticle(
            sparkle,
            0.15F + (float)itemRand.nextGaussian() / 30.0F,
            0.01F,
            22 + itemRand.nextInt(10),
            light,
            world,
            x,
            y,
            z,
            X,
            Y,
            Z,
            1.0F - (float)itemRand.nextGaussian() / 14.0F,
            1.0F,
            1.0F,
            false,
            itemRand.nextInt(360),
            true,
            1.0F
         );
         world.spawnEntity(particle);
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.SEMI_ONE_HANDED;
   }

   @Override
   public boolean canAttackMelee(ItemStack itemstack, EntityPlayer player) {
      return false;
   }

   @Override
   public int getItemEnchantability() {
      return 2;
   }
}
