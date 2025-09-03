//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.entity.EntityStreamLaserP;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class StaffOfCorpse extends Item {
   ResourceLocation texture = new ResourceLocation("arpg:textures/laser_sniper_laser.png");
   ResourceLocation smoke = new ResourceLocation("arpg:textures/smoke.png");
   ResourceLocation fire = new ResourceLocation("arpg:textures/bluelaser.png");
   ResourceLocation largesmoke = new ResourceLocation("arpg:textures/largecloud.png");
   ResourceLocation electr = new ResourceLocation("arpg:textures/blueexplode2.png");
   ResourceLocation start = new ResourceLocation("arpg:textures/ghosty_beam.png");

   public StaffOfCorpse() {
      this.setRegistryName("staff_of_corpse");
      this.setCreativeTab(CreativeTabs.COMBAT);
      this.setTranslationKey("staff_of_corpse");
      this.setMaxDamage(35);
      this.setMaxStackSize(1);
   }

   public int getMaxItemUseDuration(ItemStack itemstack) {
      return 72000;
   }

   public EnumAction getItemUseAction(ItemStack stack) {
      return EnumAction.BOW;
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      return new ActionResult(EnumActionResult.PASS, itemstack);
   }

   public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
      return true;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public void onUpdate(ItemStack itemstack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         int damage = itemstack.getItemDamage();
         World world = player.getEntityWorld();
         Item itemIn = itemstack.getItem();
         EnumHand hand = player.getActiveHand();
         boolean click = Mouse.isButtonDown(1);
         float mana = Mana.getMana(player);
         float spee = Mana.getManaSpeed(player);
         float power = Mana.getMagicPowerMax(player);
         int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
         int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
         int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
         int spec = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SPECIAL, itemstack);
         int might = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack);
         NBTHelper.GiveNBTint(itemstack, 0, "charge");
         if (player.getActiveItemStack() == itemstack && mana > 0.2F - sor / 30 && click && !player.getCooldownTracker().hasCooldown(itemIn)) {
            NBTHelper.AddNBTint(itemstack, 1, "charge");
            Vec3d vec = GetMOP.PosRayTrace(20.0, 1.0F, player, 0.05, 0.05);
            int charge = NBTHelper.GetNBTint(itemstack, "charge");
            if (charge == -27) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  Sounds.staff_of_corpse,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
            }

            if (charge >= 0) {
               if (!worldIn.isRemote) {
                  if (charge >= 28 + spec * 8) {
                     NBTHelper.SetNBTint(itemstack, -28, "charge");
                     player.getCooldownTracker().setCooldown(this, (40 - rapidity * 5) * (spec > 0 ? 5 : 1));
                     player.addStat(StatList.getObjectUseStats(this));
                  } else {
                     double damageRadius = charge / 6 + 1;
                     AxisAlignedBB aabb = new AxisAlignedBB(
                        vec.x - damageRadius,
                        vec.y - damageRadius,
                        vec.z - damageRadius,
                        vec.x + damageRadius,
                        vec.y + damageRadius,
                        vec.z + damageRadius
                     );
                     List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, aabb);
                     if (!list.isEmpty()) {
                        for (Entity entity : list) {
                           if (Team.checkIsOpponent(player, entity)) {
                              Weapons.dealDamage(
                                 null,
                                 (0.8F + (1.5F + might * 0.9F) / (charge + 1)) * power,
                                 player,
                                 entity,
                                 true,
                                 EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.IMPULSE, itemstack) / 5.0F,
                                 vec.x,
                                 vec.y,
                                 vec.z
                              );
                              entity.hurtResistantTime = 0;
                              if (itemRand.nextFloat() < 0.2) {
                                 Weapons.setPotionIfEntityLB(entity, PotionEffects.INCORPOREITY, 200, spec);
                              }
                           }
                        }
                     }

                     if (!player.capabilities.isCreativeMode) {
                        Mana.changeMana(player, -0.2F + sor / 30);
                        Mana.setManaSpeed(player, 0.001F);
                     }
                  }
               }

               if (worldIn.isRemote && damage <= itemIn.getMaxDamage() - 1) {
                  for (int ss = 0; ss < 2; ss++) {
                     GUNParticle bigsmoke = new GUNParticle(
                        this.largesmoke,
                        1.5F + (float)itemRand.nextGaussian() / 3.0F,
                        0.0F,
                        30,
                        180,
                        world,
                        vec.x,
                        vec.y,
                        vec.z,
                        (float)itemRand.nextGaussian() / 10.0F,
                        (float)itemRand.nextGaussian() / 19.0F,
                        (float)itemRand.nextGaussian() / 10.0F,
                        0.3F,
                        1.0F,
                        0.7F + (float)itemRand.nextGaussian() / 5.0F,
                        true,
                        itemRand.nextInt(360),
                        true,
                        1.0F
                     );
                     bigsmoke.alphaTickAdding = -0.025F;
                     bigsmoke.alphaGlowing = true;
                     world.spawnEntity(bigsmoke);
                  }

                  EntityStreamLaserP laser = new EntityStreamLaserP(
                     world,
                     player,
                     this.start,
                     0.07F + charge / 280,
                     156 + charge * 3,
                     1.0F,
                     1.0F,
                     1.0F,
                     0.5F,
                     player.getDistance(vec.x, vec.y, vec.z),
                     1,
                     0.3F,
                     8.0F
                  );
                  laser.setPosition(player.posX, player.posY + 1.55, player.posZ);
                  world.spawnEntity(laser);
               }
            }
         } else if (NBTHelper.GetNBTint(itemstack, "charge") > -28) {
            NBTHelper.SetNBTint(itemstack, -28, "charge");
            player.getCooldownTracker().setCooldown(this, (40 - rapidity * 5) * (spec > 0 ? 5 : 1));
            player.addStat(StatList.getObjectUseStats(this));
         }
      }
   }
}
