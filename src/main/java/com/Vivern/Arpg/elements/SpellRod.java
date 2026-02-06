package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Shards;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.renders.ManaBar;
import com.Vivern.Arpg.tileentity.IVialElementsAccepter;
import com.Vivern.Arpg.tileentity.TileSplitter;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpellRod extends ItemWeapon {
   public SpellRod() {
      this.setRegistryName("spell_rod");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("spell_rod");
      this.setMaxDamage(3000);
      this.setMaxStackSize(1);
   }

   public float getXpRepairRatio(ItemStack stack) {
      return 5.0F;
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
         Minecraft.getMinecraft().ingameGUI.setOverlayMessage(TextFormatting.GREEN + "Vessel used: Auto", false);
      } else {
         Minecraft.getMinecraft().ingameGUI.setOverlayMessage(TextFormatting.DARK_AQUA + "Vessel used: " + param, false);
      }
   }

   @Override
   public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
      Entity entity = world.getEntityByID((int)c);
      if (entity instanceof EntityPlayer) {
         EntityPlayer trueplayer = (EntityPlayer)entity;
         ShardType type = ShardType.byId((int)a);
         if (type != null) {
            for (int j = 0; j < 1 + itemRand.nextInt(3); j++) {
               TileSplitter.spawnTentacleParticle(
                  world,
                  new Vec3d(x, y, z),
                  new Vec3d(itemRand.nextGaussian(), 0.5 + itemRand.nextGaussian() / 4.0, itemRand.nextGaussian()).normalize(),
                  trueplayer,
                  null,
                  0.2,
                  -0.3,
                  type,
                  2.0F,
                  b == 0.0
               );
            }
         }
      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         this.setCanShoot(itemstack, entityIn);
         if (IWeapon.canShoot(itemstack)) {
            EntityPlayer player = (EntityPlayer)entityIn;
            int acc = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack);
            int sor = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SORCERY, itemstack);
            float power = Mana.getMagicPowerMax(player);
            boolean click = Keys.isKeyPressed(player, Keys.PRIMARYATTACK);
            boolean click2 = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            if (player.getHeldItemMainhand() == itemstack && (click || click2) && !player.getCooldownTracker().hasCooldown(this)) {
               if (player.isSneaking() && click) {
                  NBTHelper.GiveNBTint(itemstack, 0, "vessel");
                  NBTHelper.SetNBTint(itemstack, GetMOP.next(NBTHelper.GetNBTint(itemstack, "vessel"), 1, 4), "vessel");
                  player.getCooldownTracker().setCooldown(this, 8);
                  world.playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     Sounds.item_misc_a,
                     SoundCategory.PLAYERS,
                     0.5F,
                     0.9F + itemRand.nextFloat() / 5.0F
                  );
                  IWeapon.fireBomEffect(this, player, world, NBTHelper.GetNBTint(itemstack, "vessel"));
               } else {
                  Weapons.setPlayerAnimationOnServer(player, 22, EnumHand.MAIN_HAND);
                  player.getCooldownTracker().setCooldown(this, this.getCooldownTime(itemstack));
                  player.addStat(StatList.getObjectUseStats(this));
                  RayTraceResult result = GetMOP.rayTrace(world, 4 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ACCURACY, itemstack), player, false);
                  if (result != null && result.getBlockPos() != null) {
                     TileEntity tile = world.getTileEntity(result.getBlockPos());
                     if (tile instanceof IVialElementsAccepter) {
                        int i = NBTHelper.GetNBTint(itemstack, "vessel");
                        IVialElementsAccepter accepter = (IVialElementsAccepter)tile;
                        if (click) {
                           if (i == 0) {
                              if (Shards.getEnergyInVessel(player, 1) > 0.0F) {
                                 i = 1;
                              } else if (Shards.getEnergyInVessel(player, 2) > 0.0F) {
                                 i = 2;
                              } else if (Shards.getEnergyInVessel(player, 3) > 0.0F) {
                                 i = 3;
                              }
                           }

                           if (i != 0) {
                              float has = Shards.getEnergyInVessel(player, i);
                              ShardType shardType = Shards.getShardTypeInVesselNonull(player, i);
                              float pushed = Math.min(has, (float)(2 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack)));
                              float returned = accepter.acceptVialElements(itemstack, shardType, pushed);
                              float accepted = pushed - returned;
                              Shards.setEnergyToVessel(player, has - accepted, i);
                              if (accepted != 0.0F && result.hitVec != null) {
                                 IWeapon.fireEffect(
                                    this,
                                    player,
                                    world,
                                    64.0,
                                    result.hitVec.x,
                                    result.hitVec.y,
                                    result.hitVec.z,
                                    (double)shardType.id,
                                    0.0,
                                    (double)player.getEntityId(),
                                    0.0,
                                    0.0,
                                    0.0
                                 );
                              }
                           }
                        } else if (click2) {
                           if (i == 0) {
                              if (Shards.getEnergyInVessel(player, 1) > 0.0F && accepter.getElementCount(Shards.getShardTypeInVessel(player, 1)) > 0.0F) {
                                 i = 1;
                              } else if (Shards.getEnergyInVessel(player, 2) > 0.0F && accepter.getElementCount(Shards.getShardTypeInVessel(player, 2)) > 0.0F) {
                                 i = 2;
                              } else if (Shards.getEnergyInVessel(player, 3) > 0.0F && accepter.getElementCount(Shards.getShardTypeInVessel(player, 3)) > 0.0F) {
                                 i = 3;
                              } else if (Shards.getEnergyInVessel(player, 1) == 0.0F) {
                                 i = 1;
                              } else if (Shards.getEnergyInVessel(player, 2) == 0.0F) {
                                 i = 2;
                              } else if (Shards.getEnergyInVessel(player, 3) == 0.0F) {
                                 i = 3;
                              }
                           }

                           if (i != 0) {
                              float has = Shards.getEnergyInVessel(player, i);
                              ShardType shardType = Shards.getShardTypeInVesselNonull(player, i);
                              if (has == 0.0F) {
                                 for (int j = 1; j <= 12; j++) {
                                    if (accepter.getElementCount(ShardType.byId(j)) > 0.0F) {
                                       shardType = ShardType.byId(j);
                                       break;
                                    }
                                 }
                              }

                              float getted = -Math.min(100.0F - has, (float)(2 + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, itemstack)));
                              float returned = accepter.acceptVialElements(itemstack, shardType, getted);
                              float accepted = getted - returned;
                              Shards.setEnergyToVessel(player, has - accepted, i);
                              Shards.setShardTypeToVessel(player, shardType, i);
                              if (accepted != 0.0F && result.hitVec != null) {
                                 IWeapon.fireEffect(
                                    this,
                                    player,
                                    world,
                                    64.0,
                                    result.hitVec.x,
                                    result.hitVec.y,
                                    result.hitVec.z,
                                    (double)shardType.id,
                                    1.0,
                                    (double)player.getEntityId(),
                                    0.0,
                                    0.0,
                                    0.0
                                 );
                              }
                           }
                        }

                        if (!player.capabilities.isCreativeMode) {
                           itemstack.damageItem(1, player);
                        }
                     }
                  }
               }
            }
         }
      } else if (entityIn instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entityIn;
         if (player.getCooldownTracker().hasCooldown(this)) {
            ManaBar.energy_bars_enable = true;
         }
      }
   }

   @Override
   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      int i = NBTHelper.GetNBTint(stack, "vessel");
      if (i == 0) {
         tooltip.add(TextFormatting.GREEN + "Vessel: Auto");
      }

      if (i == 1) {
         tooltip.add(TextFormatting.DARK_AQUA + "Vessel: 1");
      }

      if (i == 2) {
         tooltip.add(TextFormatting.DARK_AQUA + "Vessel: 2");
      }

      if (i == 3) {
         tooltip.add(TextFormatting.DARK_AQUA + "Vessel: 3");
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      int rapidity = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack);
      return 4 - rapidity;
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

   public float getDestroySpeed(ItemStack stack, IBlockState state) {
      return 0.0F;
   }
}
