package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.LordOfPainSpike;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TheLordOfPain extends ItemWeapon {
   public TheLordOfPain() {
      this.setRegistryName("the_lord_of_pain");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("the_lord_of_pain");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!worldIn.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int damage = itemstack.getItemDamage();
            World world = player.getEntityWorld();
            Item itemIn = itemstack.getItem();
            EnumHand hand = player.getActiveHand();
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            if (player.getHeldItemMainhand() == itemstack && Mana.getMana(player) > 5.0F - sor / 1.1F && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
               boolean causeCooldown = false;
               int range = 5 - acc;
               double edist = 10 + acc;
               Vec3d vec3d = player.getPositionEyes(1.0F);
               Vec3d vec3d1 = player.getLook(1.0F);
               Vec3d vec3d2 = vec3d.add(vec3d1.x * edist, vec3d1.y * edist, vec3d1.z * edist);
               RayTraceResult impact = world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
               if (impact != null && impact.hitVec != null) {
                  BlockPos pos = new BlockPos(impact.hitVec.x, impact.hitVec.y - 1.0, impact.hitVec.z);
                  if (!world.isAirBlock(pos)) {
                     for (int i = 0; i < 20; i++) {
                        BlockPos newpos = pos.add(itemRand.nextInt(range * 2 + 1) - range, 0, itemRand.nextInt(range * 2 + 1) - range);
                        boolean continuee = true;
                        if (world.isAirBlock(newpos) || !world.isAirBlock(newpos.up())) {
                           continuee = false;

                           for (int ii = -3; ii < 3; ii++) {
                              BlockPos newnewpos = newpos.add(0, ii, 0);
                              if (!world.isAirBlock(newnewpos) && world.isAirBlock(newnewpos.up())) {
                                 newpos = newnewpos;
                                 continuee = true;
                                 break;
                              }
                           }
                        }

                        if (continuee) {
                           int interpDist = (int)Math.round(pos.getDistance(newpos.getX(), newpos.getY(), newpos.getZ()) * 4.0);
                           LordOfPainSpike projectile = new LordOfPainSpike(world, player, itemstack, power, 20 - interpDist, 1.2 - interpDist / 40.0);
                           double posofX = itemRand.nextFloat() * 0.4 - 0.2;
                           double posofZ = itemRand.nextFloat() * 0.4 - 0.2;
                           projectile.setPosition(newpos.getX() + 0.5 + posofX, newpos.getY() + 0.2, newpos.getZ() + 0.5 + posofZ);
                           world.spawnEntity(projectile);
                           causeCooldown = true;
                        }
                     }
                  }
               }

               if (causeCooldown) {
                  if (!player.capabilities.isCreativeMode) {
                     Mana.changeMana(player, -5.0F + sor / 1.1F);
                     Mana.setManaSpeed(player, 0.001F);
                  }

                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     SoundEvents.EVOCATION_ILLAGER_CAST_SPELL,
                     SoundCategory.AMBIENT,
                     0.9F,
                     0.95F + itemRand.nextFloat() / 5.0F
                  );
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  player.addStat(StatList.getObjectUseStats(this));
                  this.bom();
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   private void bom() {
      Booom.lastTick = 15;
      Booom.frequency = -0.21F;
      Booom.x = 1.0F;
      Booom.y = itemRand.nextFloat() < 0.5 ? 1.0F : -1.0F;
      Booom.z = 0.0F;
      Booom.power = -0.32F;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.TWO_HANDED;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 50 - rapidity * 9;
   }

   @Override
   public int getReloadTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public float getZoom(ItemStack itemstack, EntityPlayer player) {
      return 0.0F;
   }

   @Override
   public boolean autoReload(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public boolean hasZoom(ItemStack itemstack) {
      return false;
   }
}
