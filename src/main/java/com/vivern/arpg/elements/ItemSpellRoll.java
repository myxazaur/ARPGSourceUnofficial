package com.vivern.arpg.elements;

import com.vivern.arpg.main.Booom;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.Keys;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Spell;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.ParticleTracker;
import com.vivern.arpg.renders.SparkleSubparticle;
import com.vivern.arpg.tileentity.ISpellcastListener;
import com.vivern.arpg.tileentity.ManaBuffer;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpellRoll extends ItemWeapon {
   public static int useMaxTime = 20;
   public static int spellcastRadius = 8;

   public ItemSpellRoll() {
      this.setRegistryName("spell_roll");
      this.setCreativeTab(CreativeTabs.TOOLS);
      this.setTranslationKey("spell_roll");
      this.setMaxStackSize(1);
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return slotChanged;
   }

   @SideOnly(Side.CLIENT)
   @Override
   public void bom(int param) {
      if (param == 0) {
         param = 1;
      }

      Booom.lastTick = 10;
      Booom.frequency = 3.0F;
      Booom.x = (float)itemRand.nextGaussian();
      Booom.y = (float)itemRand.nextGaussian();
      Booom.z = (float)itemRand.nextGaussian();
      Booom.power = 0.18F / param;
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!world.isRemote) {
         boolean isInUse = false;
         int burning = NBTHelper.GetNBTint(itemstack, "burning");
         int using = NBTHelper.GetNBTint(itemstack, "using");
         if (burning > 0) {
            NBTHelper.AddNBTint(itemstack, 1, "burning");
            if (burning > 13) {
               itemstack.shrink(1);
            }
         } else if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityIn;
            boolean click = Keys.isKeyPressed(player, Keys.SECONDARYATTACK);
            if (player.getHeldItemMainhand() == itemstack) {
               int casting = NBTHelper.GetNBTint(itemstack, "casting");
               if (casting <= 0) {
                  if (click) {
                     NBTHelper.GiveNBTint(itemstack, 0, "using");
                     isInUse = true;
                     if (using >= useMaxTime) {
                        int spellsAmount = NBTHelper.GetNBTint(itemstack, "spells_amount");
                        SoundEvent sound;
                        int castTime;
                        if (spellsAmount <= 3) {
                           sound = Sounds.rollcast_short;
                           castTime = 70;
                        } else if (spellsAmount <= 8) {
                           sound = Sounds.rollcast_normal;
                           castTime = 90;
                        } else {
                           sound = Sounds.rollcast_long;
                           castTime = 130;
                        }

                        NBTHelper.GiveNBTint(itemstack, castTime, "casting");
                        NBTHelper.SetNBTint(itemstack, castTime, "casting");
                        world.playSound(
                           (EntityPlayer)null, player.posX, player.posY, player.posZ, sound, SoundCategory.AMBIENT, 1.0F, 1.0F
                        );
                     } else {
                        if (using == 0) {
                           Weapons.setPlayerAnimationOnServer(player, 32, EnumHand.MAIN_HAND);
                           world.playSound(
                              (EntityPlayer)null,
                              player.posX,
                              player.posY,
                              player.posZ,
                              Sounds.roll_open,
                              SoundCategory.AMBIENT,
                              0.8F,
                              0.95F + itemRand.nextFloat() * 0.1F
                           );
                        }

                        NBTHelper.AddNBTint(itemstack, 1, "using");
                     }
                  }
               } else if (casting != 1) {
                  NBTHelper.AddNBTint(itemstack, -1, "casting");
                  IWeapon.fireBomEffect(this, player, world, casting);
                  if (casting == 50) {
                     Weapons.setPlayerAnimationOnServer(player, 33, EnumHand.MAIN_HAND);
                  }
               } else {
                  Spell[] spells = NBTHelper.readSpellsFromNbt(itemstack);
                  if (spells != null) {
                     boolean burn = false;

                     for (BlockPos pos : GetMOP.getPosesInsideSphere(
                        (int)player.posX, (int)player.posY, (int)player.posZ, spellcastRadius
                     )) {
                        TileEntity tileEntity = world.getTileEntity(pos);
                        if (tileEntity != null
                           && tileEntity instanceof ISpellcastListener
                           && ((ISpellcastListener)tileEntity)
                              .onSpellcast(player.posX, player.posY, player.posZ, itemstack, spells, player)) {
                           burn = true;
                        }
                     }

                     NBTHelper.GiveNBTint(itemstack, 1, "burning");
                     NBTHelper.SetNBTint(itemstack, 1, "burning");
                  }
               }
            }
         }

         if (!isInUse && using > 0 && using < useMaxTime) {
            NBTHelper.AddNBTint(itemstack, -1, "using");
         }
      } else if (entityIn.ticksExisted % 3 == 0) {
         int casting = NBTHelper.GetNBTint(itemstack, "casting");
         if (casting > 0) {
            int spellsAmount = NBTHelper.GetNBTint(itemstack, "spells_amount");
            int castTime;
            if (spellsAmount <= 3) {
               castTime = 70;
            } else if (spellsAmount <= 8) {
               castTime = 90;
            } else {
               castTime = 130;
            }

            this.spawnParticles(world, entityIn, (float)casting / castTime);
         }
      }
   }

   public void spawnParticles(World world, Entity player, float chanceOrange) {
      float scale = 0.015625F;
      Vec3d spawnPoint = GetMOP.PitchYawToVec3d(player.rotationPitch, player.rotationYaw + (itemRand.nextFloat() - 0.5F) * 30.0F)
         .add(player.posX, player.posY + player.getEyeHeight() - 0.2F, player.posZ);

      for (BlockPos pos : GetMOP.getPosesInsideSphere((int)player.posX, (int)player.posY, (int)player.posZ, spellcastRadius)) {
         TileEntity tileEntity = world.getTileEntity(pos);
         if (tileEntity != null && tileEntity instanceof ISpellcastListener && ((ISpellcastListener)tileEntity).canAttractParticles(player)) {
            ParticleTracker.TrackerFollowStaticPoint tfsp = new ParticleTracker.TrackerFollowStaticPoint(
               new Vec3d(pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5), true, 0.4F, 0.001F, 0.02F
            );
            int livetime = (int)(Math.sqrt(spawnPoint.squareDistanceTo(pos.getX(), pos.getY(), pos.getZ())) * 20.0);
            livetime = Math.min(livetime, 500);
            ParticleTracker.Multitracker multitracker = new ParticleTracker.Multitracker(ManaBuffer.pt_turbulence, tfsp);

            for (int i = 0; i < 3; i++) {
               float r;
               float g;
               float b;
               if (itemRand.nextFloat() < chanceOrange) {
                  float col = itemRand.nextFloat();
                  r = 0.97F - col * 0.07F;
                  g = 0.86F - col * 0.6F;
                  b = 0.33F - col * 0.33F;
               } else {
                  float col = itemRand.nextFloat();
                  r = 1.0F - col * 0.35F;
                  g = 1.0F - col * 0.2F;
                  b = 1.0F;
               }

               GUNParticle spelll = new GUNParticle(
                  Instancer.pixel,
                  scale,
                  0.0F,
                  livetime,
                  240,
                  world,
                  spawnPoint.x + (itemRand.nextFloat() - 0.5) * 0.35,
                  spawnPoint.y + (itemRand.nextFloat() - 0.5) * 0.35,
                  spawnPoint.z + (itemRand.nextFloat() - 0.5) * 0.35,
                  0.0F,
                  0.07F,
                  0.0F,
                  r,
                  g,
                  b,
                  true,
                  0
               );
               spelll.isPushedByLiquids = false;
               spelll.alphaGlowing = true;
               spelll.scaleTickAdding = -scale / livetime / 2.0F;
               spelll.tracker = multitracker;
               world.spawnEntity(spelll);
            }
         }
      }

      if (chanceOrange > 0.0F) {
         SparkleSubparticle sparkleSubparticle = new SparkleSubparticle(
            spawnPoint.x + (itemRand.nextFloat() - 0.5) * 0.35,
            spawnPoint.y + (itemRand.nextFloat() - 0.5) * 0.35,
            spawnPoint.z + (itemRand.nextFloat() - 0.5) * 0.35,
            scale,
            60,
            0.0F,
            0.0F,
            0.0F,
            0.0F
         );
         SparkleSubparticle.particles.add(sparkleSubparticle);
      }
   }

   @Override
   public boolean autoCooldown(ItemStack itemstack) {
      return false;
   }

   @Override
   public int getCooldownTime(ItemStack itemstack) {
      return 0;
   }

   @Override
   public WeaponHandleType getWeaponHandleType() {
      return WeaponHandleType.ONE_HANDED;
   }

   @Override
   public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
      super.addInformation(stack, worldIn, tooltip, flagIn);
      Spell[] spells = NBTHelper.readSpellsFromNbt(stack);
      if (spells != null) {
         for (int i = 0; i < spells.length; i++) {
            if (spells[i] != null) {
               tooltip.add(spells[i].name);
            }
         }
      }
   }
}
