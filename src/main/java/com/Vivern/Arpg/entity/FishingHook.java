package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.elements.ItemFishingRod;
import com.Vivern.Arpg.loot.Bait;
import com.Vivern.Arpg.loot.Fishing;
import com.Vivern.Arpg.loot.FishingTreasure;
import com.Vivern.Arpg.main.FishingTracker;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.SuperKnockback;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FishingHook extends EntityThrowable {
   public ItemFishingRod fishingRod;
   ItemStack rodstack;
   public boolean returning = false;
   public int returnTime = 0;
   public boolean captureFish = false;
   public float fishingPitch = 0.0F;
   public float fishingYaw = 0.0F;
   public int fishTime = 0;
   public Vec3d fishVector;
   public int peckTime = 0;
   public boolean dropped = false;
   public FishingTreasure fishs;
   public List<Block> allliquids = new ArrayList<>();
   public Bait bait = null;

   public FishingHook(World worldIn) {
      super(worldIn);
   }

   public FishingHook(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public FishingHook(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   public FishingHook(World world, EntityLivingBase thrower, ItemFishingRod fishingRod, ItemStack rodstack, List<Block> allliquids, Bait bait) {
      super(world, thrower);
      this.fishingRod = fishingRod;
      this.rodstack = rodstack;
      this.allliquids = allliquids;
      this.bait = bait;
   }

   public boolean shouldStopFishing() {
      ItemStack itemstack = this.thrower.getHeldItemMainhand();
      boolean flag = itemstack.getItem() == this.fishingRod;
      if (!this.thrower.isDead
         && this.thrower.isEntityAlive()
         && flag
         && this.getDistance(this.thrower) <= this.fishingRod.length + 1.0F) {
         return false;
      } else {
         this.returnHook();
         return true;
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 8) {
         FishingTracker.fishing = true;
      }

      if (id == 9) {
         FishingTracker.fishing = false;
      }
   }

   public void onUpdate() {
      super.onUpdate();
      if (!this.world.isRemote) {
         if (!this.returning) {
            if (this.fishingRod != null && this.rodstack != null && !this.shouldStopFishing()) {
               int lure = EnchantmentHelper.getEnchantmentLevel(Enchantments.LURE, this.rodstack);
               if (NBTHelper.GetNBTboolean(this.rodstack, "destroy")) {
                  this.returning = true;
               }

               if (this.getDistance(this.thrower) > this.fishingRod.length) {
                  this.motionX /= 1.2;
                  this.motionY /= 1.2;
                  this.motionZ /= 1.2;
                  SuperKnockback.applyMove(
                     this,
                     -0.7F,
                     this.thrower.posX,
                     this.thrower.posY + this.thrower.height / 2.0F,
                     this.thrower.posZ
                  );
               }

               Block block = this.isInLiqid(-1.0F);
               if (block != null) {
                  if (this.allliquids.contains(block)) {
                     if (NBTHelper.GetNBTboolean(this.rodstack, "pecked") && !this.captureFish) {
                        this.peckTime--;
                     }

                     if (this.peckTime < 1) {
                        NBTHelper.SetNBTboolean(this.rodstack, false, "pecked");
                        this.captureFish = false;
                     }

                     if (NBTHelper.GetNBTint(this.rodstack, "use") > 0 && this.peckTime > 0) {
                        this.captureFish = true;
                     }

                     if (!this.captureFish) {
                        if (this.rand.nextFloat() < this.fishingRod.getFishingLuck(this.thrower, this.rodstack, this.world) / 200.0F
                           && Math.abs(this.motionZ * this.motionX) < 0.1
                           && this.peckTime < 1
                           && this.bait != null) {
                           this.world
                              .playSound(
                                 (EntityPlayer)null,
                                 this.thrower.posX,
                                 this.thrower.posY,
                                 this.thrower.posZ,
                                 Sounds.fishing_splash,
                                 SoundCategory.AMBIENT,
                                 0.8F,
                                 1.0F
                              );
                           NBTHelper.SetNBTboolean(this.rodstack, true, "pecked");
                           this.peckTime = 25;
                           this.fishingPitch = -this.thrower.rotationPitch;
                           this.fishingYaw = -this.thrower.rotationYaw;
                           this.fishVector = new Vec3d(this.rand.nextGaussian(), this.rand.nextGaussian(), 0.0);
                           this.fishs = Fishing.getActualCapturedFish(block, this.world.getBiomeForCoordsBody(this.getPosition()), 2.0F);
                        }
                     } else if (this.fishs != null) {
                        if (this.rand.nextFloat() < this.fishs.vecChance) {
                           this.fishVector = new Vec3d(this.rand.nextGaussian(), this.rand.nextGaussian(), 0.0);
                        }

                        this.world.setEntityState(this, (byte)8);
                        this.fishingPitch = (float)(this.fishingPitch + this.fishVector.x * this.fishs.speed);
                        this.fishingYaw = (float)(this.fishingYaw + this.fishVector.y * this.fishs.speed);
                        this.fishTime++;
                        double result1 = this.fishingPitch + this.thrower.rotationPitch;
                        double result2 = this.fishingYaw + this.thrower.rotationYaw;
                        FishingTracker.posxx = result2;
                        FishingTracker.posyy = result1;
                        double rdist = Math.sqrt(result1 * result1 + result2 * result2);
                        if (this.ticksExisted % 12 == 0) {
                           this.world
                              .playSound(
                                 (EntityPlayer)null,
                                 this.thrower.posX,
                                 this.thrower.posY,
                                 this.thrower.posZ,
                                 Sounds.fishing_reel,
                                 SoundCategory.AMBIENT,
                                 0.6F,
                                 1.0F
                              );
                        }

                        if (rdist > 46.0) {
                           this.tryLooseBait();
                           this.captureFish = false;
                           this.fishTime = 0;
                           this.world.setEntityState(this, (byte)9);
                           this.peckTime = 0;
                           NBTHelper.SetNBTboolean(this.rodstack, false, "pecked");
                           this.world
                              .playSound(
                                 (EntityPlayer)null,
                                 this.thrower.posX,
                                 this.thrower.posY,
                                 this.thrower.posZ,
                                 Sounds.fishing_break,
                                 SoundCategory.AMBIENT,
                                 0.8F,
                                 1.0F
                              );
                        } else if (this.fishTime + lure * 5 > this.fishs.fishTime) {
                           ItemStack fishstack = new ItemStack(this.fishs.item, 1, this.fishs.damage);
                           if (fishstack != null) {
                              this.giveFish(fishstack);
                           }

                           this.returning = true;
                           this.world.setEntityState(this, (byte)9);
                           this.world
                              .playSound(
                                 (EntityPlayer)null,
                                 this.thrower.posX,
                                 this.thrower.posY,
                                 this.thrower.posZ,
                                 Sounds.fishing_success,
                                 SoundCategory.AMBIENT,
                                 0.8F,
                                 1.0F
                              );
                           this.tryLooseBait();
                        }
                     } else {
                        this.captureFish = false;
                        this.fishTime = 0;
                        this.world.setEntityState(this, (byte)9);
                        this.peckTime = 0;
                        NBTHelper.SetNBTboolean(this.rodstack, false, "pecked");
                     }
                  }

                  if (!this.dropped) {
                     this.world
                        .playSound(
                           (EntityPlayer)null,
                           this.posX,
                           this.posY,
                           this.posZ,
                           Sounds.fishing_drop,
                           SoundCategory.AMBIENT,
                           0.7F,
                           1.0F
                        );
                     this.dropped = true;
                  }
               }

               Block block2 = this.isInLiqid(0.0F);
               if (block2 != null && block2 instanceof BlockLiquid) {
                  this.motionY = 0.001;
               }
            }
         } else {
            this.motionX /= 1.2;
            this.motionY /= 1.2;
            this.motionZ /= 1.2;
            SuperKnockback.applyMove(
               this,
               -1.0F,
               this.thrower.posX,
               this.thrower.posY + this.thrower.height / 2.0F,
               this.thrower.posZ
            );
            this.returnTime++;
            if (this.getDistance(this.thrower) < 1.0F || this.returnTime > 20) {
               this.returnHook();
            }
         }
      }
   }

   protected void onImpact(RayTraceResult result) {
      if (!this.returning) {
         double frictionMultipl = 3.0;
         if (result.typeOfHit == Type.BLOCK
            && this.world
                  .getBlockState(result.getBlockPos())
                  .getBlock()
                  .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
               != null) {
            if (result.sideHit == EnumFacing.UP || result.sideHit == EnumFacing.DOWN) {
               this.motionY = 0.0;
               this.motionX /= 1.005F * frictionMultipl;
               this.motionZ /= 1.005F * frictionMultipl;
            }

            if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH) {
               this.motionZ = 0.0;
               this.motionX /= 1.005F * frictionMultipl;
               this.motionY /= 1.001F * frictionMultipl;
            }

            if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST) {
               this.motionX = 0.0;
               this.motionZ /= 1.005F * frictionMultipl;
               this.motionY /= 1.001F * frictionMultipl;
            }
         }
      }
   }

   public Block isInLiqid(float offset) {
      return this.world.getBlockState(this.getPosition().add(0.0, offset, 0.0)).getBlock();
   }

   public void returnHook() {
      this.world.setEntityState(this, (byte)9);
      this.world
         .playSound(
            (EntityPlayer)null,
            this.thrower.posX,
            this.thrower.posY,
            this.thrower.posZ,
            Sounds.fishing_reset,
            SoundCategory.AMBIENT,
            0.6F,
            1.0F
         );
      if (this.rodstack != null) {
         NBTHelper.SetNBTboolean(this.rodstack, true, "hasHook");
      }

      this.setDead();
   }

   public void giveFish(ItemStack fishstack) {
      EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, fishstack);
      double d0 = this.thrower.posX - this.posX;
      double d1 = this.thrower.posY - this.posY;
      double d2 = this.thrower.posZ - this.posZ;
      double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      double d4 = 0.1;
      entityitem.motionX = d0 * 0.1;
      entityitem.motionY = d1 * 0.1 + MathHelper.sqrt(d3) * 0.08;
      entityitem.motionZ = d2 * 0.1;
      this.world.spawnEntity(entityitem);
   }

   public void tryLooseBait() {
      if (this.rand.nextFloat() < this.bait.looseChance) {
         NBTHelper.SetNBTstring(this.rodstack, "", "bait");
      }
   }
}
