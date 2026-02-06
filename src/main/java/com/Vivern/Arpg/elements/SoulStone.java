package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Ln;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.NPCMobsPack;
import com.Vivern.Arpg.recipes.Soul;
import com.Vivern.Arpg.renders.GUNParticle;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SoulStone extends ItemWeapon {
   public static final int SS_EMPTY = 0;
   public static final int SS_COMMON = 1;
   public static final int SS_EXHAUSTED = 2;
   public static final int SS_STRANGER = 3;
   public static final int SS_GREEDY = 4;
   public static final int SS_WINTRY = 5;
   public static final int SS_MUTATED = 6;
   public static final int SS_MYSTIC = 7;
   public static final int SS_PALE = 8;
   public static final int SS_NOBLE = 9;
   public static final int SS_COLLECTIVE = 10;
   public static final int SS_SHADOW = 11;
   public static final int SS_WORSHIPPING = 12;
   public static final int SS_SINFUL = 13;
   public static final int SS_FESTIVE = 14;
   public static ResourceLocation largesmoke = new ResourceLocation("arpg:textures/simple_magic_shoot.png");

   public SoulStone() {
      this.setRegistryName("soul_stone");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("soul_stone");
      this.addPropertyOverride(new ResourceLocation("soul"), new IItemPropertyGetter() {
         @SideOnly(Side.CLIENT)
         public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            return SoulStone.getSoul(stack);
         }
      });
   }

   public int getItemStackLimit(ItemStack stack) {
      return getSoul(stack) == 0 ? 64 : 1;
   }

   public static int getSoul(ItemStack stone) {
      return NBTHelper.GetNBTint(stone, "soul");
   }

   public static ItemStack getSouledStack(Soul soul) {
      return getSouledStack(soul.id);
   }

   public static ItemStack getSouledStack(int soul) {
      ItemStack stack = new ItemStack(ItemsRegister.SOULSTONE);
      NBTHelper.GiveNBTint(stack, soul, "soul");
      NBTHelper.SetNBTint(stack, soul, "soul");
      NBTHelper.GiveNBTint(stack, soul, "soul");
      NBTHelper.SetNBTint(stack, soul, "soul");
      return stack;
   }

   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
      catchSoul(stack, target, attacker, 0);
      return super.hitEntity(stack, target, attacker);
   }

   public static void catchSoul(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, int catchPower) {
      if (getSoul(stack) == 0
         && target.getHealth() <= 0.0F
         && target.deathTime < 2
         && target instanceof EntityCreature
         && !(target instanceof EntityPlayer)
         && itemRand.nextFloat() < target.getMaxHealth() / 28.0F) {
         ItemStack filledStone = getFilledSoulStoneForMob(target);
         Soul soul = Soul.byId(getSoul(filledStone));
         if (!soul.canCatchWithPower(catchPower)) {
            return;
         }

         attacker.world
            .playSound(
               (EntityPlayer)null,
               attacker.posX,
               attacker.posY,
               attacker.posZ,
               Sounds.soul_steal,
               SoundCategory.PLAYERS,
               1.3F,
               0.9F + itemRand.nextFloat() / 5.0F
            );
         IWeapon.fireEffect(
            ItemsRegister.SOULSTONE,
            attacker,
            attacker.world,
            32.0,
            target.posX,
            target.posY + target.height / 2.0F,
            target.posZ,
            attacker.posX,
            attacker.posY + attacker.height / 2.0F,
            attacker.posZ,
            0.0,
            0.0,
            0.0
         );
         if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)attacker;
            int empty = player.inventory.getFirstEmptyStack();
            if (empty != -1) {
               player.inventory.setInventorySlotContents(empty, filledStone);
            } else {
               player.world
                  .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, filledStone));
            }
         } else {
            attacker.setHeldItem(EnumHand.MAIN_HAND, filledStone);
         }

         stack.shrink(1);
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      for (int i = 0; i < 10; i++) {
         GUNParticle bigsmoke = new GUNParticle(
            largesmoke,
            0.2F + (float)itemRand.nextGaussian() / 15.0F,
            0.0F,
            30,
            240,
            world,
            x,
            y,
            z,
            (float)itemRand.nextGaussian() / 20.0F,
            (float)itemRand.nextGaussian() / 25.0F,
            (float)itemRand.nextGaussian() / 20.0F,
            0.8F + itemRand.nextFloat() / 5.0F,
            0.8F + itemRand.nextFloat() / 5.0F,
            1.0F,
            true,
            itemRand.nextInt(360)
         );
         bigsmoke.alphaTickAdding = -0.03F;
         bigsmoke.alphaGlowing = true;
         world.spawnEntity(bigsmoke);
         SuperKnockback.applyMove(bigsmoke, -0.3F, a, b, c);
      }
   }

   public static ItemStack getFilledSoulStoneForMob(EntityLivingBase mob) {
      if (mob instanceof AbstractMob) {
         AbstractMob abstractMob = (AbstractMob)mob;
         return getSouledStack(abstractMob.soul);
      } else if (mob instanceof EntityGhast || mob instanceof EntityBlaze) {
         return getSouledStack(Soul.EXHAUSTED);
      } else if (mob instanceof EntityVillager || mob instanceof EntityVindicator || mob instanceof NPCMobsPack.NpcMerchant) {
         return getSouledStack(Soul.GREEDY);
      } else if (mob instanceof EntityEnderman || mob instanceof EntityEndermite || mob instanceof EntityShulker) {
         return getSouledStack(Soul.STRANGER);
      } else if (mob instanceof NPCMobsPack.NpcWizard || mob instanceof EntityWitch || mob instanceof EntityEvoker || mob instanceof EntityIllusionIllager) {
         return getSouledStack(Soul.MYSTIC);
      } else if (mob instanceof EntityAnimal) {
         return getSouledStack(Soul.INNOCENT);
      } else {
         return mob.isEntityUndead() ? getSouledStack(Soul.PALE) : getSouledStack(Soul.COMMON);
      }
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

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 0;
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
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   public String getItemStackDisplayName(ItemStack stack) {
      int soulId = getSoul(stack);
      if (soulId != 0) {
         Soul soul = Soul.byId(soulId);
         if (soul != null) {
            return super.getItemStackDisplayName(stack) + " <" + Ln.translate("soul.name." + soul.name) + ">";
         }
      }

      return super.getItemStackDisplayName(stack);
   }

   @Override
   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      int soulId = getSoul(stack);
      if (soulId != 0) {
         Soul soul = Soul.byId(soulId);
         if (soul != null) {
            tooltip.add(Ln.translate("soul.desc." + soul.name));
         }
      }
   }
}
