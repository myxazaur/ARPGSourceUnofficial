//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.WeaponParameters;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.tileentity.TileSpellForge;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpellHammer extends ItemWeapon {
   public SpellHammer() {
      this.setRegistryName("spell_hammer");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("spell_hammer");
      this.setMaxDamage(1000);
      this.setMaxStackSize(1);
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      if (param == 0) {
         Booom.lastTick = 20;
         Booom.frequency = 0.157F;
         Booom.x = 1.0F;
         Booom.y = itemRand.nextFloat() < 0.5 ? 0.2F : -0.2F;
         Booom.z = 0.0F;
         Booom.power = -0.3F;
      }

      if (param == 1) {
         Booom.lastTick = 22;
         Booom.frequency = 0.43F;
         Booom.x = itemRand.nextFloat() < 0.5 ? 0.1F : -0.1F;
         Booom.y = 0.0F;
         Booom.z = 1.0F;
         Booom.power = itemRand.nextFloat() < 0.5 ? -0.3F + itemRand.nextFloat() * 0.15F : 0.3F - itemRand.nextFloat() * 0.15F;
      }

      if (param == 2) {
         Booom.lastTick = 22;
         Booom.frequency = 0.43F;
         Booom.x = itemRand.nextFloat() < 0.5 ? 0.1F : -0.1F;
         Booom.y = 0.0F;
         Booom.z = 1.0F;
         Booom.power = itemRand.nextFloat() < 0.5 ? -0.1F : 0.1F;
      }

      if (param == 3) {
         Booom.lastTick = 25;
         Booom.frequency = 0.126F;
         Booom.x = 1.0F;
         Booom.y = 0.0F;
         Booom.z = 0.0F;
         Booom.power = 0.1F;
      }
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
                  NBTHelper.SetNBTint(itemstack, 7, "atdelay");
                  int randAnim = itemRand.nextInt(3);
                  if (randAnim == 0) {
                     Weapons.setPlayerAnimationOnServer(player, 29, EnumHand.MAIN_HAND);
                  }

                  if (randAnim == 1) {
                     Weapons.setPlayerAnimationOnServer(player, 30, EnumHand.MAIN_HAND);
                  }

                  if (randAnim == 2) {
                     Weapons.setPlayerAnimationOnServer(player, 31, EnumHand.MAIN_HAND);
                  }

                  double attackspeed = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                  player.getCooldownTracker().setCooldown(this, this.getModifiedMeleeCooldown(attackspeed, this.getCooldownTime(itemstack)));
                  IWeapon.fireBomEffect(this, player, world, 0);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.swosh_d,
                     SoundCategory.PLAYERS,
                     0.8F,
                     0.95F + itemRand.nextFloat() / 10.0F
                  );
               }

               if (delay == 1) {
                  int range = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RANGE, itemstack);
                  WeaponParameters parameters = WeaponParameters.getWeaponParameters(this);
                  boolean doMobAttack = true;
                  RayTraceResult result = GetMOP.rayTrace(world, parameters.getEnchanted("length", range), player, false);
                  if (result != null && result.sideHit == EnumFacing.UP && result.getBlockPos() != null) {
                     TileEntity tileEntity = world.getTileEntity(result.getBlockPos());
                     if (tileEntity instanceof TileSpellForge) {
                        TileSpellForge spellForge = (TileSpellForge)tileEntity;
                        if (spellForge.onHammerHit(itemstack, player)) {
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.spell_forge,
                              SoundCategory.PLAYERS,
                              3.0F,
                              0.95F + itemRand.nextFloat() / 10.0F
                           );
                        } else {
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.spell_hammer,
                              SoundCategory.PLAYERS,
                              1.4F,
                              0.95F + itemRand.nextFloat() / 10.0F
                           );
                        }

                        doMobAttack = false;
                        itemstack.damageItem(1, player);
                        IWeapon.fireBomEffect(this, player, world, 1);
                     }
                  }

                  if (doMobAttack) {
                     if (IWeapon.doMeleeHammerAttack(this, itemstack, player, EnumHand.MAIN_HAND, false, 45, 7).success) {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.spell_hammer,
                           SoundCategory.PLAYERS,
                           1.4F,
                           0.98F + itemRand.nextFloat() / 10.0F
                        );
                        IWeapon.fireBomEffect(this, player, world, 2);
                     } else {
                        world.playSound(
                           (EntityPlayer)null,
                           player.posX,
                           player.posY,
                           player.posZ,
                           Sounds.melee_miss_axe,
                           SoundCategory.PLAYERS,
                           1.0F,
                           0.9F + itemRand.nextFloat() / 5.0F
                        );
                        IWeapon.fireBomEffect(this, player, world, 3);
                     }
                  }

                  player.addExhaustion(0.2F);
               }
            }
         }
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
