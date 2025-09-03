//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SkullCrasher extends ItemWeapon {
   public SkullCrasher() {
      this.setRegistryName("skull_crasher");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("skull_crasher");
      this.setMaxDamage(2300);
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
            boolean hascooldown = player.getCooldownTracker().hasCooldown(this);
            NBTHelper.GiveNBTint(itemstack, 0, "atdelay");
            int delay = NBTHelper.GetNBTint(itemstack, "atdelay");
            if (delay > 0) {
               NBTHelper.AddNBTint(itemstack, -1, "atdelay");
            }

            if (player.getHeldItemMainhand() == itemstack) {
               if (delay <= 0 && click && !hascooldown) {
                  NBTHelper.SetNBTint(itemstack, 3, "atdelay");
                  Weapons.setPlayerAnimationOnServer(player, 45, EnumHand.MAIN_HAND);
                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
               }

               if (delay == 1) {
                  if (IWeapon.doMeleeHammerAttack(this, itemstack, player, EnumHand.MAIN_HAND, !player.onGround && player.motionY < -0.02, 90, 8).success
                     )
                   {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_axe,
                        SoundCategory.PLAYERS,
                        0.7F,
                        0.6F + itemRand.nextFloat() / 5.0F
                     );
                     if (!player.onGround && player.motionY < -0.02) {
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
                  } else {
                     world.playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.melee_miss_axe,
                        SoundCategory.PLAYERS,
                        0.6F,
                        0.6F + itemRand.nextFloat() / 5.0F
                     );
                  }

                  player.addExhaustion(0.2F);
               }
            }
         }
      }
   }

   @Override
   public boolean attackEntityMelee(Entity entity, ItemStack stack, EntityPlayer player, EnumHand hand, boolean isCritical) {
      boolean ret = super.attackEntityMelee(entity, stack, player, hand, isCritical);
      if (entity instanceof EntityLivingBase && isCritical) {
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, stack);
         int impulse = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, stack);
         int witchery = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.WITCHERY, stack);
         WeaponParameters parameters = WeaponParameters.getWeaponParameters(stack.getItem());
         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
         World world = entity.world;
         BlockPos bpos = entitylivingbase.getPosition().down();
         IBlockState state = world.getBlockState(bpos);
         float hard = state.getBlockHardness(world, bpos);
         float hardnessBreaks = parameters.getEnchanted("hardness_breaks", might);
         float hardnessPenetrate = parameters.getEnchanted("hardness_penetrate", might);
         if (entitylivingbase.width < 3.0F
            && entitylivingbase.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue()
               < Math.min(impulse / 5.0F + itemRand.nextFloat(), 1.0F) * itemRand.nextFloat()) {
            int digBlocks = parameters.getEnchantedi("blocks_dig", EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, stack));
            boolean canDig = true;
            float down = 0.0F;

            for (int i = 0; i < digBlocks; i++) {
               if (Weapons.easyBreakBlockFor(world, hardnessBreaks, bpos) && canDig) {
                  world.destroyBlock(bpos, true);
                  down++;
                  bpos = bpos.down();
               } else {
                  canDig = false;
                  if (!Weapons.easyBreakBlockFor(world, hardnessPenetrate, bpos)) {
                     break;
                  }

                  down++;
                  bpos = bpos.down();
               }
            }

            if (down != 0.0F) {
               entitylivingbase.setPositionAndUpdate(bpos.getX(), bpos.getY() - down, bpos.getZ());
               int potionpower = GetMOP.floatToIntWithChance(parameters.getEnchanted("potion_power", witchery), itemRand);
               entitylivingbase.addPotionEffect(new PotionEffect(PotionEffects.SQUASHED, 200, potionpower, false, false));
            }
         }

         if (world.isRemote) {
            for (int ix = 0; ix < 10; ix++) {
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

      return ret;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
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
