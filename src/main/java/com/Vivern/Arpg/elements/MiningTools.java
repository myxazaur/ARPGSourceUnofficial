//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.elements.animation.AnimationAndFluidCapProvider;
import com.Vivern.Arpg.elements.models.ChainsawModel;
import com.Vivern.Arpg.elements.models.DrillModel;
import com.Vivern.Arpg.elements.models.LaserModel;
import com.Vivern.Arpg.elements.models.MiningLaserModel;
import com.Vivern.Arpg.entity.BlocksDecayer;
import com.Vivern.Arpg.entity.EntityStreamLaserP;
import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.BlockBreaking;
import com.Vivern.Arpg.main.Booom;
import com.Vivern.Arpg.main.DeathEffects;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.FluidsRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Team;
import com.Vivern.Arpg.main.WeaponDamage;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.potions.PotionEffects;
import com.Vivern.Arpg.renders.AnimatedGParticle;
import com.Vivern.Arpg.renders.GUNParticle;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MiningTools {
   public ResourceLocation mainTexture;
   public String name;
   public Drill electricDrill;
   public Drill fuelDrill;
   public Chainsaw electricChainsaw;
   public Chainsaw fuelChainsaw;
   public MiningLaser miningLaser;
   public int toolLEVEL;
   public float averageDigSpeed;
   public Vec3d mainColor;

   public MiningTools(
      ResourceLocation mainTexture,
      String name,
      int toolLEVEL,
      float averageDigSpeed,
      String nameDrill,
      String nameChainsaw,
      int maxRF,
      int throughput,
      int needRF,
      int averageDurability,
      int fuelCapacity,
      MiningLaser miningLaser,
      Vec3d mainColor
   ) {
      this.mainTexture = mainTexture;
      this.name = name;
      this.toolLEVEL = toolLEVEL;
      this.averageDigSpeed = averageDigSpeed;
      this.mainColor = mainColor;
      this.electricDrill = new ElectricDrill(nameDrill + "_electric", averageDurability, this, maxRF, throughput, needRF);
      this.fuelDrill = new FuelDrill(nameDrill + "_fuel", averageDurability, this, fuelCapacity, needRF);
      this.electricChainsaw = new ElectricChainsaw(nameChainsaw + "_electric", averageDurability, this, maxRF, throughput, needRF);
      this.fuelChainsaw = new FuelChainsaw(nameChainsaw + "_fuel", averageDurability, this, fuelCapacity, needRF);
      this.miningLaser = miningLaser;
      this.miningLaser.toolsSet = this;
   }

   public static class Chainsaw extends AbstractMiningTool implements BlockBreaking.IMultiblockBreakHandler {
      static ChainsawModel model = new ChainsawModel();

      public Chainsaw(String name, int maxDamage, MiningTools toolsSet) {
         super(name, maxDamage, toolsSet);
         this.modesCount = 3;
      }

      @Override
      public String getNameForMode(int mode) {
         if (mode == 0) {
            return "tool.chainsaw.mode.normal";
         } else {
            return mode == 1 ? "tool.chainsaw.mode.treecapitator" : "tool.chainsaw.mode.treecapitatorleaves";
         }
      }

      @Override
      public ModelBase getModel() {
         return model;
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void bom(int param) {
         Booom.lastTick = 7;
         Booom.frequency = 1.45F;
         Booom.x = (float)itemRand.nextGaussian();
         Booom.y = 0.0F;
         Booom.z = (float)itemRand.nextGaussian();
         Booom.power = 0.08F * (param / 20.0F);
      }

      @Override
      public void onUpdateMining(ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote) {
            float breakingSpeed = this.getBreakingSpeed(itemstack, player);
            breakingSpeed *= speed / this.gatMaxSpeed(itemstack, player);
            int toolLevel = this.toolsSet.toolLEVEL;
            if (mode == 0) {
               Vec3d pos = GetMOP.PosRayTrace(this.getLength(itemstack), 1.0F, player, 0.3, 0.3);
               AxisAlignedBB miningBox = GetMOP.newAABB(pos, 0.2);
               List<BlockPos> listposes = GetMOP.getBlockPosesCollidesAABBwithTheirHitbox(world, miningBox, false);
               BlockBreaking blockBreaking = BlockBreaking.getBlockBreaking((WorldServer)world);
               int blocksBreak = 0;

               for (BlockPos blockPos : listposes) {
                  IBlockState state = world.getBlockState(blockPos);
                  if (BlockBreaking.isToolEffective("axe", state)) {
                     if (blockBreaking.damageBlock(
                        player,
                        blockPos,
                        breakingSpeed,
                        "axe",
                        toolLevel,
                        EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack),
                        EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0
                     )) {
                        blocksBreak++;
                     }
                  } else if (BlockBreaking.isToolEffective("shears", state)) {
                     if (blockBreaking.damageBlock(
                        player,
                        blockPos,
                        breakingSpeed,
                        "shears",
                        toolLevel,
                        EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack),
                        EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0
                     )) {
                        blocksBreak++;
                     }
                  } else if (blockBreaking.damageBlock(
                     player,
                     blockPos,
                     breakingSpeed,
                     "axe",
                     toolLevel,
                     EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack),
                     EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0
                  )) {
                     blocksBreak++;
                  }
               }

               if (player.ticksExisted % (4 - (int)(Math.min(speed, 20) * 0.15F)) == 0) {
                  if (!listposes.isEmpty()) {
                     IWeapon.fireBomEffect(this, player, world, speed);
                  }

                  for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(player, miningBox)) {
                     if (Team.checkIsOpponent(player, entity)) {
                        float damage = this.getDamageToMobsChainsaw(itemstack) * (speed / this.gatMaxSpeed(itemstack, player));
                        Weapons.dealDamage(
                           new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.blade),
                           damage,
                           player,
                           entity,
                           true,
                           this.getKnockbackToMobsChainsaw(itemstack)
                        );
                        entity.hurtResistantTime = 0;
                        DeathEffects.applyDeathEffect(entity, DeathEffects.DE_CUT, 0.6F);
                     }
                  }
               }

               if (blocksBreak > 0) {
                  if (!player.capabilities.isCreativeMode) {
                     this.spendCharge(itemstack, world, player, blocksBreak);
                     itemstack.damageItem(blocksBreak, player);
                  }
               } else if (!player.capabilities.isCreativeMode && itemRand.nextFloat() < 0.25F) {
                  this.spendCharge(itemstack, world, player, 1);
               }

               if (player.ticksExisted % 4 == 0) {
                  if (!listposes.isEmpty()) {
                     IWeapon.sendIWeaponState(itemstack, player, 2, itemSlot, EnumHand.MAIN_HAND);
                  } else {
                     IWeapon.sendIWeaponState(itemstack, player, 1, itemSlot, EnumHand.MAIN_HAND);
                  }
               }

               if (player.ticksExisted % 15 == 0 || speed == 0) {
                  Weapons.setPlayerAnimationOnServer(player, 49, EnumHand.MAIN_HAND);
               }
            }

            if (mode == 1 || mode == 2) {
               RayTraceResult result = GetMOP.fixedRayTraceBlocks(world, player, this.getLength(itemstack), 0.2, 0.2, true, false, true, false);
               int blocksBreak = 0;
               boolean hit = false;
               if (result.typeOfHit == Type.BLOCK && result.getBlockPos() != null) {
                  hit = true;
                  if (player.ticksExisted % 5 == 0) {
                     BlockBreaking blockBreaking = BlockBreaking.getBlockBreaking((WorldServer)world);
                     BlockPos blockPosx = result.getBlockPos();
                     IBlockState state = world.getBlockState(blockPosx);
                     if (BlockBreaking.isToolEffective("axe", state)) {
                        float digSpeedMult = 2.5F;
                        blocksBreak = blockBreaking.damageMultiblock(
                           this,
                           player,
                           blockPosx,
                           breakingSpeed * 5.0F * digSpeedMult,
                           "axe",
                           toolLevel,
                           EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack),
                           EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0
                        );
                     }
                  }
               }

               if (player.ticksExisted % (5 - (int)(Math.min(speed, 20) * 0.15F)) == 0) {
                  if (hit) {
                     IWeapon.fireBomEffect(this, player, world, speed);
                  }

                  if (result.typeOfHit == Type.ENTITY && result.entityHit != null && Team.checkIsOpponent(player, result.entityHit)) {
                     float damage = 2.75F * (speed / this.gatMaxSpeed(itemstack, player));
                     Weapons.dealDamage(
                        new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.blade), damage, player, result.entityHit, true, 0.1F
                     );
                     result.entityHit.hurtResistantTime = 0;
                     DeathEffects.applyDeathEffect(result.entityHit, DeathEffects.DE_CUT, 0.6F);
                  }
               }

               if (blocksBreak > 0) {
                  if (!player.capabilities.isCreativeMode) {
                     this.spendCharge(itemstack, world, player, blocksBreak);
                     itemstack.damageItem(blocksBreak, player);
                  }
               } else if (!player.capabilities.isCreativeMode && itemRand.nextFloat() < 0.25F) {
                  this.spendCharge(itemstack, world, player, 1);
               }

               if (player.ticksExisted % 4 == 0) {
                  if (hit) {
                     IWeapon.sendIWeaponState(itemstack, player, 2, itemSlot, EnumHand.MAIN_HAND);
                  } else {
                     IWeapon.sendIWeaponState(itemstack, player, 1, itemSlot, EnumHand.MAIN_HAND);
                  }
               }

               if (player.ticksExisted % 15 == 0 || speed == 0) {
                  Weapons.setPlayerAnimationOnServer(player, 49, EnumHand.MAIN_HAND);
               }
            }
         }
      }

      @Override
      public float getDamageToBreak(World world, IBlockState blockState, BlockPos pos) {
         ArrayList<BlockPos> list = this.getTreeBlockPoses(world, pos);
         float hardness = 0.0F;

         for (BlockPos blockPos : list) {
            hardness += world.getBlockState(blockPos).getBlockHardness(world, blockPos);
         }

         return hardness;
      }

      @Override
      public int destroy(World world, BlockPos pos, EntityPlayer player, String tool, int toolLevel, int fortune, boolean withSilkTouch) {
         int mode = NBTHelper.GetNBTint(player.getHeldItemMainhand(), "mode");
         ArrayList<BlockPos> list = this.getTreeBlockPoses(world, pos);
         if (mode != 2) {
            for (BlockPos blockPos : list) {
               Weapons.destroyBlockAsTool(world, blockPos, player, tool, toolLevel, fortune, withSilkTouch);
            }
         } else {
            BlocksDecayer decayer = new BlocksDecayer(world);
            decayer.setPosition(player.posX, player.posY, player.posZ);
            decayer.iterationsPerTick = 1;

            for (BlockPos blockPos : list) {
               Weapons.destroyBlockAsTool(world, blockPos, player, tool, toolLevel, fortune, withSilkTouch);

               for (EnumFacing face : EnumFacing.VALUES) {
                  BlockPos offpos = blockPos.offset(face);
                  IBlockState leaveState = world.getBlockState(offpos);
                  if (leaveState.getBlock().isLeaves(leaveState, world, offpos)) {
                     decayer.addDecayPos(blockPos);
                     break;
                  }
               }
            }

            world.spawnEntity(decayer);
         }

         return list.size();
      }

      public ArrayList<BlockPos> getTreeBlockPoses(World world, BlockPos start) {
         ArrayList<BlockPos> list = new ArrayList<>();
         this.next(world, start, list, start.down(), start.down(2), new int[1]);
         list.add(start);
         return list;
      }

      private void next(World world, BlockPos pos, ArrayList<BlockPos> list, BlockPos prev, BlockPos prevprev, int[] limit) {
         if (++limit[0] < 1000) {
            int startY = limit[0] < 104 ? 0 : -1;

            for (int x = -1; x <= 1; x++) {
               for (int y = startY; y <= 1; y++) {
                  for (int z = -1; z <= 1; z++) {
                     if (x != 0 || y != 0 || z != 0) {
                        BlockPos offpos = pos.add(x, y, z);
                        if ((
                              offpos.getX() != prev.getX()
                                 || offpos.getY() != prev.getY()
                                 || offpos.getZ() != prev.getZ()
                           )
                           && (
                              offpos.getX() != prevprev.getX()
                                 || offpos.getY() != prevprev.getY()
                                 || offpos.getZ() != prevprev.getZ()
                           )
                           && this.isLog(world, offpos)) {
                           boolean is = true;

                           for (BlockPos has : list) {
                              if (offpos.getX() == has.getX()
                                 && offpos.getY() == has.getY()
                                 && offpos.getZ() == has.getZ()) {
                                 is = false;
                                 break;
                              }
                           }

                           if (is) {
                              list.add(offpos);
                              this.next(world, offpos, list, pos, prev, limit);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      public boolean isLog(World world, BlockPos pos) {
         IBlockState state = world.getBlockState(pos);

         for (IProperty<?> property : state.getPropertyKeys()) {
            if (property.getValueClass() == EnumAxis.class && BlockBreaking.isToolEffective("axe", state)) {
               return true;
            }
         }

         return false;
      }
   }

   public static class CorrosiveWaterblaster extends MiningLaser {
      public static ResourceLocation slimesplash = new ResourceLocation("arpg:textures/slimesplash.png");
      public static ResourceLocation water_beam = new ResourceLocation("arpg:textures/water_beam.png");
      public int mbCapacity;
      public int fuelUnitsNeed;

      public CorrosiveWaterblaster(String name, int maxDamage, int mbCapacity, int fuelUnitsNeed) {
         super(name, maxDamage);
         this.mbCapacity = mbCapacity;
         this.fuelUnitsNeed = fuelUnitsNeed;
         this.manaNeed = 0.1F;
         this.canImpactGlass = true;
         this.startSound = Sounds.magic_water;
         this.oneshotSound = Sounds.el_water;
      }

      @Override
      public void spendCharge(ItemStack itemstack, World world, EntityPlayer player, int blocksBreak) {
         NBTHelper.AddNBTint(itemstack, -this.fuelUnitsNeed, "fuelUnits");
         super.spendCharge(itemstack, world, player, blocksBreak);
      }

      @Override
      public boolean canMining(ItemStack itemstack, World world, EntityPlayer player) {
         int unitsHas = NBTHelper.GetNBTint(itemstack, "fuelUnits");
         if (unitsHas >= this.fuelUnitsNeed) {
            return super.canMining(itemstack, world, player);
         } else {
            IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(itemstack);
            if (containerFluidHandler != null) {
               FluidStack fluidstack = containerFluidHandler.drain(5, false);
               if (fluidstack != null && fluidstack.amount > 0 && fluidstack.getFluid() != null) {
                  int rfPerBucket = fluidstack.getFluid() == FluidRegistry.WATER ? 5 : 0;
                  if (rfPerBucket > 0) {
                     fluidstack = containerFluidHandler.drain(5, true);
                     int rfPerMb = 1;
                     int set = unitsHas + rfPerMb * fluidstack.amount;
                     NBTHelper.GiveNBTint(itemstack, set, "fuelUnits");
                     NBTHelper.SetNBTint(itemstack, set, "fuelUnits");
                     if (set >= this.fuelUnitsNeed) {
                        return super.canMining(itemstack, world, player);
                     }
                  }
               }
            }

            return false;
         }
      }

      @Override
      public void makeLaserParticles(
         World world,
         @Nullable EntityPlayer player,
         double xStart,
         double yStart,
         double zStart,
         double xEnd,
         double yEnd,
         double zEnd,
         boolean hit,
         int mode,
         double distance,
         float rotatPitch,
         float rotatYaw
      ) {
         EntityStreamLaserP laser = new EntityStreamLaserP(world, player, water_beam, 0.1F, 240, 1.0F, 0.9F, 0.9F, 0.0F, distance, 1, 0.2F, 2.0F);
         laser.setPosition(xStart, yStart, zStart);
         if (player == null) {
            laser.rotatPitch = rotatPitch;
            laser.rotatYaw = rotatYaw;
            laser.prevrotatPitch = rotatPitch;
            laser.prevrotatYaw = rotatYaw;
         }

         laser.useOldPositioning = false;
         laser.barrelLength = this.getLaserParam(16);
         laser.shoulders = this.getLaserParam(17);
         laser.yoffset = this.getLaserParam(18);
         laser.hand = EnumHand.MAIN_HAND;
         laser.useModel = true;
         float[] color1 = new float[]{0.32F, 0.58F, 0.83F, 1.0F};
         float[] color2 = new float[]{0.1F, 0.7F, 0.4F, 0.2F};
         float[] color3 = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
         float[] color4 = new float[]{0.0F, 1.0F, 1.0F, 0.0F};
         laser.model = new LaserModel.LaserModelLinear(water_beam, 240, 2, 0.2F, 0.0F, color3, color3, 0.0F, 4.0F, 2.0F, -0.12F);
         laser.model.next = new LaserModel.LaserModelSpiral(null, 240, 3.0, -9, 35, 0.14F, 0.2F, 0.12F, color2, color1, color4);
         laser.model.next.next = new LaserModel.LaserModelCap(forge_hit_a, 240, 0.12F, 0.0F, color1, 0.0F, 5.0F);
         laser.model.next.next.next = new LaserModel.LaserModelCap(mana_flow, 240, 0.1F, 0.0F, color3, 0.0F, -3.0F);
         world.spawnEntity(laser);
         if (hit) {
            float randcol = itemRand.nextFloat();
            int lt = 8 + itemRand.nextInt(5);
            float scl = 0.8F + itemRand.nextFloat() * 0.55F;
            GUNParticle part = new GUNParticle(
               slimesplash,
               0.05F,
               0.0F,
               lt,
               225,
               world,
               xEnd,
               yEnd,
               zEnd,
               0.0F,
               0.0F,
               0.0F,
               0.5F + 0.3F * randcol,
               0.75F + 0.15F * randcol,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            part.alphaTickAdding = -1.0F / lt;
            part.scaleTickAdding = scl / lt;
            world.spawnEntity(part);
            if (itemRand.nextFloat() < 0.3F) {
               randcol = 0.3F;
               Vec3d spedVec = new Vec3d(xEnd - xStart, yEnd - yStart, zEnd - zStart)
                  .normalize()
                  .scale(-0.1)
                  .add(
                     (itemRand.nextFloat() - 0.5F) * randcol, (itemRand.nextFloat() - 0.5F) * randcol, (itemRand.nextFloat() - 0.5F) * randcol
                  );
               ShardType.WATER
                  .spawnNativeParticle(
                     world, 1.0F, xEnd, yEnd, zEnd, (float)spedVec.x, (float)spedVec.y, (float)spedVec.z, true
                  );
            } else {
               randcol = 0.4F;
               lt = 16 + itemRand.nextInt(12);
               Vec3d spedVec = new Vec3d(xEnd - xStart, yEnd - yStart, zEnd - zStart)
                  .normalize()
                  .scale(-0.3)
                  .add(
                     (itemRand.nextFloat() - 0.5F) * randcol, (itemRand.nextFloat() - 0.5F) * randcol, (itemRand.nextFloat() - 0.5F) * randcol
                  );
               part = new GUNParticle(
                  pixel,
                  0.03125F,
                  0.04F,
                  lt,
                  240,
                  world,
                  xEnd,
                  yEnd,
                  zEnd,
                  (float)spedVec.x,
                  (float)spedVec.y,
                  (float)spedVec.z,
                  0.45F - itemRand.nextFloat() * 0.2F,
                  1.0F - itemRand.nextFloat() * 0.3F,
                  1.0F - itemRand.nextFloat() * 0.1F,
                  false,
                  0,
                  true,
                  1.2F
               );
               part.scaleTickAdding = -0.03125F / lt;
               world.spawnEntity(part);
            }

            int ltx = 10 + itemRand.nextInt(10);
            boolean typee = itemRand.nextFloat() < 0.5F;
            scl = 0.1F;
            float randcolx = itemRand.nextFloat();
            AnimatedGParticle particle = new AnimatedGParticle(
               typee ? plasma_a : plasma_b,
               0.3F + itemRand.nextFloat() * 0.2F,
               0.0F,
               ltx,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               (itemRand.nextFloat() - 0.5F) * scl,
               (itemRand.nextFloat() - 0.5F) * scl,
               (itemRand.nextFloat() - 0.5F) * scl,
               0.2F - itemRand.nextFloat() * 0.1F,
               0.4F - itemRand.nextFloat() * 0.1F,
               0.9F,
               true,
               itemRand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.alphaTickAdding = -1.0F / ltx;
            particle.framecount = typee ? 14 : 24;
            particle.animCounter = itemRand.nextInt(10);
            particle.useNormalTime = true;
            particle.disableOnEnd = false;
            particle.noWaterBubble = true;
            particle.isPushedByLiquids = false;
            world.spawnEntity(particle);
         }
      }

      @Override
      public void onHitEntity(Entity entity, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote && Team.checkIsOpponent(player, entity)) {
            float damage = mode == 1
               ? this.getDamageToMobsLaser(itemstack) * oneshotDamageMult
               : this.getDamageToMobsLaser(itemstack) * (speed / this.gatMaxSpeed(itemstack, player));
            Weapons.dealDamage(
               new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.water),
               damage,
               player,
               entity,
               true,
               this.getKnockbackToMobsLaser(itemstack)
            );
            entity.hurtResistantTime = 0;
            entity.extinguish();
         }
      }

      @Override
      public void onHitBlock(RayTraceResult result, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote) {
            Weapons.doWetBlock(world, result.getBlockPos());
         }
      }

      @Override
      public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
         return new AnimationAndFluidCapProvider(stack, this.mbCapacity);
      }

      @Override
      public int maxFluidForDescription() {
         return this.mbCapacity;
      }

      @Override
      public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
         if (state == 1) {
            this.playOrContinueLoopSound(player, Sounds.tools_laser_beam_b, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 10, 12, 0.5F, 0.5F);
         } else if (state == 2) {
            this.playOrContinueLoopSound(player, Sounds.tools_laser_digging, SoundCategory.PLAYERS, 1.0F, 1.0F, 10, 1, 8, 1.0F, 0.8F);
            this.playOrContinueLoopSound(player, Sounds.tools_laser_beam_b, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 10, 12, 0.5F, 0.5F);
         } else {
            super.onStateReceived(player, itemstack, state, slot);
         }
      }
   }

   public static class Drill extends AbstractMiningTool {
      static DrillModel model = new DrillModel();

      public Drill(String name, int maxDamage, MiningTools toolsSet) {
         super(name, maxDamage, toolsSet);
         this.modesCount = 2;
      }

      @Override
      public String getNameForMode(int mode) {
         return mode == 0 ? "tool.drill.mode.normal" : "tool.drill.mode.large";
      }

      @Override
      public ModelBase getModel() {
         return model;
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void bom(int param) {
         Booom.lastTick = 7;
         Booom.frequency = 1.45F;
         Booom.x = (float)itemRand.nextGaussian();
         Booom.y = (float)itemRand.nextGaussian();
         Booom.z = (float)itemRand.nextGaussian();
         Booom.power = 0.08F * (param / 20.0F);
      }

      @Override
      public void onUpdateMining(ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote) {
            Vec3d pos = GetMOP.PosRayTrace(this.getLength(itemstack), 1.0F, player, 0.4, 0.4);
            AxisAlignedBB miningBox = GetMOP.newAABB(pos, mode == 0 ? 0.25 : 0.6);
            List<BlockPos> listposes = GetMOP.getBlockPosesCollidesAABBwithTheirHitbox(world, miningBox, false);
            BlockBreaking blockBreaking = BlockBreaking.getBlockBreaking((WorldServer)world);
            int blocksBreak = 0;
            float breakingSpeed = mode == 0 ? this.getBreakingSpeed(itemstack, player) : this.getBreakingSpeed(itemstack, player) / 4.0F;
            breakingSpeed *= speed / this.gatMaxSpeed(itemstack, player);
            int toolLevel = this.toolsSet.toolLEVEL;

            for (BlockPos blockPos : listposes) {
               IBlockState state = world.getBlockState(blockPos);
               String harvestTool = state.getBlock().getHarvestTool(state);
               if (BlockBreaking.isToolEffective("pickaxe", state)) {
                  if (blockBreaking.damageBlock(
                     player,
                     blockPos,
                     breakingSpeed,
                     "pickaxe",
                     toolLevel,
                     EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack),
                     EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0
                  )) {
                     blocksBreak++;
                  }
               } else if (BlockBreaking.isToolEffective("shovel", state)) {
                  if (blockBreaking.damageBlock(
                     player,
                     blockPos,
                     breakingSpeed,
                     "shovel",
                     toolLevel,
                     EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack),
                     EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0
                  )) {
                     blocksBreak++;
                  }
               } else if (blockBreaking.damageBlock(
                  player,
                  blockPos,
                  breakingSpeed,
                  "pickaxe",
                  toolLevel,
                  EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack),
                  EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0
               )) {
                  blocksBreak++;
               }
            }

            if (player.ticksExisted % (5 - (int)(Math.min(speed, 20) * 0.15F)) == 0) {
               if (!listposes.isEmpty()) {
                  IWeapon.fireBomEffect(this, player, world, speed);
               }

               for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(player, miningBox)) {
                  if (Team.checkIsOpponent(player, entity)) {
                     float damage = this.getDamageToMobsDrill(itemstack) * (speed / this.gatMaxSpeed(itemstack, player));
                     if (mode == 1) {
                        damage /= 2.0F;
                     }

                     Weapons.dealDamage(
                        new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.drill),
                        damage,
                        player,
                        entity,
                        true,
                        this.getKnockbackToMobsDrill(itemstack)
                     );
                     entity.hurtResistantTime = 0;
                     Weapons.mixPotion(
                        entity,
                        PotionEffects.BROKEN_ARMOR,
                        40.0F,
                        1.0F,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumPotionMix.WITH_MAXIMUM,
                        Weapons.EnumMathOperation.PLUS,
                        Weapons.EnumMathOperation.PLUS,
                        2000,
                        10
                     );
                  }
               }
            }

            if (blocksBreak > 0) {
               if (!player.capabilities.isCreativeMode) {
                  this.spendCharge(itemstack, world, player, blocksBreak);
                  itemstack.damageItem(blocksBreak, player);
               }
            } else if (!player.capabilities.isCreativeMode && itemRand.nextFloat() < 0.25F) {
               this.spendCharge(itemstack, world, player, 1);
            }

            if (player.ticksExisted % 4 == 0) {
               if (!listposes.isEmpty()) {
                  IWeapon.sendIWeaponState(itemstack, player, 2, itemSlot, EnumHand.MAIN_HAND);
               } else {
                  IWeapon.sendIWeaponState(itemstack, player, 1, itemSlot, EnumHand.MAIN_HAND);
               }
            }

            if (player.ticksExisted % 15 == 0 || speed == 0) {
               Weapons.setPlayerAnimationOnServer(player, 47, EnumHand.MAIN_HAND);
            }
         }
      }
   }

   public static class ElectricChainsaw extends Chainsaw implements IEnergyItem {
      public int maxRF;
      public int throughput;

      public ElectricChainsaw(String name, int maxDamage, MiningTools toolsSet, int maxRF, int throughput, int electricNeed) {
         super(name, maxDamage, toolsSet);
         this.maxRF = maxRF;
         this.throughput = throughput;
         this.electricNeed = electricNeed;
      }

      @Override
      public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
         super.onStateReceived(player, itemstack, state, slot);
         if (state == 1) {
            this.playOrContinueLoopSound(player, Sounds.tools_electric_chainsaw, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 20, 10, 0.5F, 0.5F);
         }

         if (state == 2) {
            this.playOrContinueLoopSound(player, Sounds.tools_electric_chainsaw, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 20, 10, 0.5F, 0.5F);
         }
      }

      @Override
      public int getMaxEnergyStored(ItemStack stack) {
         return this.maxRF;
      }

      @Override
      public int getThroughput() {
         return this.throughput;
      }

      @Override
      public IEnergyItem asEnergyItem() {
         return this;
      }
   }

   public static class ElectricDrill extends Drill implements IEnergyItem {
      public int maxRF;
      public int throughput;

      public ElectricDrill(String name, int maxDamage, MiningTools toolsSet, int maxRF, int throughput, int electricNeed) {
         super(name, maxDamage, toolsSet);
         this.maxRF = maxRF;
         this.throughput = throughput;
         this.electricNeed = electricNeed;
      }

      @Override
      public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
         super.onStateReceived(player, itemstack, state, slot);
         if (state == 1) {
            this.playOrContinueLoopSound(player, Sounds.tools_electric_drill, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 20, 10, 0.5F, 0.5F);
         }

         if (state == 2) {
            this.playOrContinueLoopSound(player, Sounds.tools_digging, SoundCategory.PLAYERS, 1.0F, 1.0F, 10, 1, 8, 1.0F, 0.8F);
            this.playOrContinueLoopSound(player, Sounds.tools_electric_drill, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 20, 10, 0.5F, 0.5F);
         }
      }

      @Override
      public int getMaxEnergyStored(ItemStack stack) {
         return this.maxRF;
      }

      @Override
      public int getThroughput() {
         return this.throughput;
      }

      @Override
      public IEnergyItem asEnergyItem() {
         return this;
      }
   }

   public static class ElectricMiningLaser extends MiningLaser implements IEnergyItem {
      public int maxRF;
      public int throughput;

      public ElectricMiningLaser(String name, int maxDamage, int maxRF, int throughput, int electricNeed) {
         super(name, maxDamage);
         this.maxRF = maxRF;
         this.throughput = throughput;
         this.electricNeed = electricNeed;
      }

      @Override
      public int getMaxEnergyStored(ItemStack stack) {
         return this.maxRF;
      }

      @Override
      public int getThroughput() {
         return this.throughput;
      }

      @Override
      public IEnergyItem asEnergyItem() {
         return this;
      }
   }

   public static class EyelightProspector extends ElectricMiningLaser {
      public static ResourceLocation magic_effect_2 = new ResourceLocation("arpg:textures/magic_effect_2.png");
      public static ResourceLocation generic_beam5 = new ResourceLocation("arpg:textures/generic_beam5.png");

      public EyelightProspector(String name, int maxDamage, int maxRF, int throughput, int electricNeed) {
         super(name, maxDamage, maxRF, throughput, electricNeed);
         this.itemNeed = ItemsRegister.EREBRISFRAGMENT;
         this.chargesPerItem = 1800;
      }

      @Override
      public void makeLaserParticles(
         World world,
         @Nullable EntityPlayer player,
         double xStart,
         double yStart,
         double zStart,
         double xEnd,
         double yEnd,
         double zEnd,
         boolean hit,
         int mode,
         double distance,
         float rotatPitch,
         float rotatYaw
      ) {
         EntityStreamLaserP laser = new EntityStreamLaserP(world, player, shard_trace, 0.1F, 240, 1.0F, 0.9F, 0.9F, 0.0F, distance, 1, 0.2F, 2.0F);
         laser.setPosition(xStart, yStart, zStart);
         if (player == null) {
            laser.rotatPitch = rotatPitch;
            laser.rotatYaw = rotatYaw;
            laser.prevrotatPitch = rotatPitch;
            laser.prevrotatYaw = rotatYaw;
         }

         laser.useOldPositioning = false;
         laser.barrelLength = this.getLaserParam(16);
         laser.shoulders = this.getLaserParam(17);
         laser.yoffset = this.getLaserParam(18);
         laser.hand = EnumHand.MAIN_HAND;
         laser.useModel = true;
         float[] color1 = new float[]{0.95F, 1.0F, 0.45F, 1.0F};
         float[] color2 = new float[]{0.83F, 1.0F, 0.5F, 1.0F};
         float[] color3 = new float[]{0.83F, 1.0F, 0.5F, 0.0F};
         laser.model = new LaserModel.LaserModelLinear(generic_beam5, 240, 3, 0.12F, 0.0F, color2, color3, 0.0F, 3.0F, 3.0F, -0.06F);
         laser.model.next = new LaserModel.LaserModelLinear(shard_trace, 240, 2, 0.07F, 0.0F, color3, color2, 0.0F, 0.0F, 1.6F, -0.04F);
         laser.model.next.next = new LaserModel.LaserModelCircles(magic_effect_2, 240, 2.0, 5, 2, 0.03F, 0.25F, color1, color3);
         laser.model.next.next.next = new LaserModel.LaserModelCap(forge_hit_a, 240, 0.1F, 0.0F, color1, 0.0F, -5.0F);
         world.spawnEntity(laser);
         if (hit) {
            int lt = 6 + itemRand.nextInt(4);
            float scl = 1.2F + itemRand.nextFloat() * 0.85F;
            GUNParticle part = new GUNParticle(
               magic_effect_2,
               0.05F,
               0.0F,
               lt,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               0.0F,
               0.0F,
               0.0F,
               0.95F - itemRand.nextFloat() * 0.1F,
               1.0F - itemRand.nextFloat() * 0.1F,
               0.5F,
               true,
               itemRand.nextInt(360)
            );
            part.alphaGlowing = true;
            part.alphaTickAdding = -1.0F / lt;
            part.scaleTickAdding = scl / lt;
            world.spawnEntity(part);
            float spd0 = 0.4F;
            int lt0 = 16 + itemRand.nextInt(12);
            Vec3d spedVec = new Vec3d(xEnd - xStart, yEnd - yStart, zEnd - zStart)
               .normalize()
               .scale(-0.3)
               .add((itemRand.nextFloat() - 0.5F) * spd0, (itemRand.nextFloat() - 0.5F) * spd0, (itemRand.nextFloat() - 0.5F) * spd0);
            GUNParticle pix = new GUNParticle(
               pixel,
               0.03125F,
               0.04F,
               lt0,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               (float)spedVec.x,
               (float)spedVec.y,
               (float)spedVec.z,
               1.0F - itemRand.nextFloat() * 0.2F,
               1.0F - itemRand.nextFloat() * 0.2F,
               0.6F - itemRand.nextFloat() * 0.2F,
               false,
               0,
               true,
               1.2F
            );
            pix.scaleTickAdding = -0.03125F / lt0;
            world.spawnEntity(pix);
            int ltx = 10 + itemRand.nextInt(10);
            boolean typee = itemRand.nextFloat() < 0.5F;
            float spd1 = 0.1F;
            float randcol = itemRand.nextFloat();
            AnimatedGParticle particle = new AnimatedGParticle(
               typee ? plasma_a : plasma_b,
               0.3F + itemRand.nextFloat() * 0.2F,
               0.0F,
               ltx,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               (itemRand.nextFloat() - 0.5F) * spd1,
               (itemRand.nextFloat() - 0.5F) * spd1,
               (itemRand.nextFloat() - 0.5F) * spd1,
               0.95F - itemRand.nextFloat() * 0.2F,
               1.0F - itemRand.nextFloat() * 0.2F,
               0.5F,
               true,
               itemRand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.alphaTickAdding = -1.0F / ltx;
            particle.framecount = typee ? 14 : 24;
            particle.animCounter = itemRand.nextInt(10);
            particle.useNormalTime = true;
            particle.disableOnEnd = false;
            particle.noWaterBubble = true;
            particle.isPushedByLiquids = false;
            world.spawnEntity(particle);
         }
      }

      @Override
      public void onHitEntity(Entity entity, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote && Team.checkIsOpponent(player, entity)) {
            float damage = mode == 1
               ? this.getDamageToMobsLaser(itemstack) * oneshotDamageMult
               : this.getDamageToMobsLaser(itemstack) * (speed / this.gatMaxSpeed(itemstack, player));
            Weapons.dealDamage(
               new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.laser),
               damage,
               player,
               entity,
               true,
               this.getKnockbackToMobsLaser(itemstack)
            );
            entity.hurtResistantTime = 0;
         }
      }
   }

   public static class FuelChainsaw extends Chainsaw {
      public int mbCapacity;
      public int fuelUnitsNeed;

      public FuelChainsaw(String name, int maxDamage, MiningTools toolsSet, int mbCapacity, int fuelUnitsNeed) {
         super(name, maxDamage, toolsSet);
         this.mbCapacity = mbCapacity;
         this.fuelUnitsNeed = fuelUnitsNeed;
      }

      @Override
      public void spendCharge(ItemStack itemstack, World world, EntityPlayer player, int blocksBreak) {
         NBTHelper.AddNBTint(itemstack, -this.fuelUnitsNeed, "fuelUnits");
      }

      @Override
      public boolean canMining(ItemStack itemstack, World world, EntityPlayer player) {
         int unitsHas = NBTHelper.GetNBTint(itemstack, "fuelUnits");
         if (unitsHas >= this.fuelUnitsNeed) {
            return true;
         } else {
            IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(itemstack);
            if (containerFluidHandler != null) {
               FluidStack fluidstack = containerFluidHandler.drain(5, false);
               if (fluidstack != null && fluidstack.amount > 0 && fluidstack.getFluid() != null) {
                  int rfPerBucket = FluidsRegister.getRFgeneratedFromFuelBucket(fluidstack.getFluid());
                  if (rfPerBucket > 0) {
                     fluidstack = containerFluidHandler.drain(5, true);
                     int rfPerMb = rfPerBucket / 1000;
                     int set = unitsHas + rfPerMb * fluidstack.amount;
                     NBTHelper.GiveNBTint(itemstack, set, "fuelUnits");
                     NBTHelper.SetNBTint(itemstack, set, "fuelUnits");
                     if (set >= this.fuelUnitsNeed) {
                        return true;
                     }
                  }
               }
            }

            return false;
         }
      }

      @Override
      public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
         return new AnimationAndFluidCapProvider(stack, this.mbCapacity);
      }

      @Override
      public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
         super.onStateReceived(player, itemstack, state, slot);
         if (state == 1) {
            this.playOrContinueLoopSound(player, Sounds.tools_engine_chainsaw, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 20, 10, 0.5F, 0.5F);
         }

         if (state == 2) {
            this.playOrContinueLoopSound(player, Sounds.tools_engine_chainsaw, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 20, 10, 0.5F, 0.5F);
         }

         if (state == 3) {
            this.playOrContinueLoopSound(player, Sounds.tools_engine_idle, SoundCategory.PLAYERS, 1.0F, 1.0F, 48000, 20, 10, 0.5F, 0.5F);
         }
      }

      @Override
      public boolean hasEngineTremor() {
         return true;
      }

      @Override
      public int maxFluidForDescription() {
         return this.mbCapacity;
      }
   }

   public static class FuelDrill extends Drill {
      public int mbCapacity;
      public int fuelUnitsNeed;

      public FuelDrill(String name, int maxDamage, MiningTools toolsSet, int mbCapacity, int fuelUnitsNeed) {
         super(name, maxDamage, toolsSet);
         this.mbCapacity = mbCapacity;
         this.fuelUnitsNeed = fuelUnitsNeed;
      }

      @Override
      public void spendCharge(ItemStack itemstack, World world, EntityPlayer player, int blocksBreak) {
         NBTHelper.AddNBTint(itemstack, -this.fuelUnitsNeed, "fuelUnits");
      }

      @Override
      public boolean canMining(ItemStack itemstack, World world, EntityPlayer player) {
         int unitsHas = NBTHelper.GetNBTint(itemstack, "fuelUnits");
         if (unitsHas >= this.fuelUnitsNeed) {
            return true;
         } else {
            IFluidHandlerItem containerFluidHandler = FluidUtil.getFluidHandler(itemstack);
            if (containerFluidHandler != null) {
               FluidStack fluidstack = containerFluidHandler.drain(5, false);
               if (fluidstack != null && fluidstack.amount > 0 && fluidstack.getFluid() != null) {
                  int rfPerBucket = FluidsRegister.getRFgeneratedFromFuelBucket(fluidstack.getFluid());
                  if (rfPerBucket > 0) {
                     fluidstack = containerFluidHandler.drain(5, true);
                     int rfPerMb = rfPerBucket / 1000;
                     int set = unitsHas + rfPerMb * fluidstack.amount;
                     NBTHelper.GiveNBTint(itemstack, set, "fuelUnits");
                     NBTHelper.SetNBTint(itemstack, set, "fuelUnits");
                     if (set >= this.fuelUnitsNeed) {
                        return true;
                     }
                  }
               }
            }

            return false;
         }
      }

      @Override
      public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
         return new AnimationAndFluidCapProvider(stack, this.mbCapacity);
      }

      @Override
      public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
         super.onStateReceived(player, itemstack, state, slot);
         if (state == 1) {
            this.playOrContinueLoopSound(player, Sounds.tools_engine_drill, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 20, 10, 0.5F, 0.5F);
         }

         if (state == 2) {
            this.playOrContinueLoopSound(player, Sounds.tools_digging, SoundCategory.PLAYERS, 1.0F, 1.0F, 10, 1, 8, 1.0F, 0.8F);
            this.playOrContinueLoopSound(player, Sounds.tools_engine_drill, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 20, 10, 0.5F, 0.5F);
         }

         if (state == 3) {
            this.playOrContinueLoopSound(player, Sounds.tools_engine_idle, SoundCategory.PLAYERS, 1.0F, 1.0F, 48000, 20, 10, 0.5F, 0.5F);
         }
      }

      @Override
      public boolean hasEngineTremor() {
         return true;
      }

      @Override
      public int maxFluidForDescription() {
         return this.mbCapacity;
      }
   }

   public static class LaserDigger extends ElectricMiningLaser {
      public LaserDigger(String name, int maxDamage, int maxRF, int throughput, int electricNeed) {
         super(name, maxDamage, maxRF, throughput, electricNeed);
         this.itemNeed = Items.REDSTONE;
         this.chargesPerItem = 480;
      }

      @Override
      public void makeLaserParticles(
         World world,
         @Nullable EntityPlayer player,
         double xStart,
         double yStart,
         double zStart,
         double xEnd,
         double yEnd,
         double zEnd,
         boolean hit,
         int mode,
         double distance,
         float rotatPitch,
         float rotatYaw
      ) {
         EntityStreamLaserP laser = new EntityStreamLaserP(world, player, laser_rifle_laser, 0.1F, 240, 1.0F, 0.9F, 0.9F, 0.0F, distance, 1, 0.2F, 2.0F);
         laser.setPosition(xStart, yStart, zStart);
         if (player == null) {
            laser.rotatPitch = rotatPitch;
            laser.rotatYaw = rotatYaw;
            laser.prevrotatPitch = rotatPitch;
            laser.prevrotatYaw = rotatYaw;
         }

         laser.useOldPositioning = false;
         laser.barrelLength = this.getLaserParam(16);
         laser.shoulders = this.getLaserParam(17);
         laser.yoffset = this.getLaserParam(18);
         laser.hand = EnumHand.MAIN_HAND;
         laser.useModel = true;
         float[] color1 = new float[]{1.0F, 0.07F, 0.07F, 1.0F};
         float[] color2 = new float[]{1.0F, 0.0F, 0.6F, 1.0F};
         float[] color3 = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
         laser.model = new LaserModel.LaserModelLightnings(null, 240, 15, 3, 0.24F, 0.01F, 0.55F, true, color1, color2);
         laser.model.next = new LaserModel.LaserModelLinear(laser_rifle_laser, 240, 2, 0.05F, 0.0F, color3, color2, 0.0F, 0.0F, 1.6F, -0.04F);
         laser.model.next.next = new LaserModel.LaserModelCap(blueexplode3, 240, 0.14F, 0.0F, color2, 0.0F, 4.0F);
         laser.model.next.next.next = new LaserModel.LaserModelCap(mana_flow, 240, 0.1F, 0.0F, color1, 0.0F, -3.0F);
         world.spawnEntity(laser);
         float colrand = itemRand.nextFloat();
         GUNParticle bigsmoke = new GUNParticle(
            forge_hit_a, 0.01F, 0.0F, 5, 240, world, xEnd, yEnd, zEnd, 0.0F, 0.0F, 0.0F, 1.0F, colrand * 0.3F, colrand * 0.3F, true, itemRand.nextInt(360)
         );
         bigsmoke.alphaTickAdding = -0.18F;
         bigsmoke.scaleTickAdding = 0.13F + itemRand.nextFloat() * 0.1F;
         bigsmoke.alphaGlowing = true;
         bigsmoke.isPushedByLiquids = false;
         world.spawnEntity(bigsmoke);
         float spd0 = 0.4F;
         int lt0 = 16 + itemRand.nextInt(12);
         Vec3d spedVec = new Vec3d(xEnd - xStart, yEnd - yStart, zEnd - zStart)
            .normalize()
            .scale(-0.3)
            .add((itemRand.nextFloat() - 0.5F) * spd0, (itemRand.nextFloat() - 0.5F) * spd0, (itemRand.nextFloat() - 0.5F) * spd0);
         GUNParticle pix = new GUNParticle(
            pixel,
            0.03125F,
            0.04F,
            lt0,
            240,
            world,
            xEnd,
            yEnd,
            zEnd,
            (float)spedVec.x,
            (float)spedVec.y,
            (float)spedVec.z,
            1.0F,
            colrand * 0.25F,
            colrand * 0.25F,
            false,
            0,
            true,
            1.2F
         );
         pix.scaleTickAdding = -0.03125F / lt0;
         world.spawnEntity(pix);
         int lt = 10 + itemRand.nextInt(10);
         boolean typee = itemRand.nextFloat() < 0.5F;
         float spd1 = 0.1F;
         AnimatedGParticle particle = new AnimatedGParticle(
            typee ? plasma_a : plasma_b,
            0.3F + itemRand.nextFloat() * 0.2F,
            0.0F,
            lt,
            240,
            world,
            xEnd,
            yEnd,
            zEnd,
            (itemRand.nextFloat() - 0.5F) * spd1,
            (itemRand.nextFloat() - 0.5F) * spd1,
            (itemRand.nextFloat() - 0.5F) * spd1,
            1.0F - colrand * 0.1F,
            0.0F,
            colrand * 0.4F,
            true,
            itemRand.nextInt(360)
         );
         particle.alphaGlowing = true;
         particle.alphaTickAdding = -1.0F / lt;
         particle.framecount = typee ? 14 : 24;
         particle.animCounter = itemRand.nextInt(10);
         particle.useNormalTime = true;
         particle.disableOnEnd = false;
         particle.noWaterBubble = true;
         particle.isPushedByLiquids = false;
         world.spawnEntity(particle);
      }

      @Override
      public void onHitEntity(Entity entity, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote && Team.checkIsOpponent(player, entity)) {
            float damage = mode == 1
               ? this.getDamageToMobsLaser(itemstack) * oneshotDamageMult
               : this.getDamageToMobsLaser(itemstack) * (speed / this.gatMaxSpeed(itemstack, player));
            Weapons.dealDamage(
               new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.laser),
               damage,
               player,
               entity,
               true,
               this.getKnockbackToMobsLaser(itemstack)
            );
            entity.hurtResistantTime = 0;
            DeathEffects.applyDeathEffect(entity, DeathEffects.DE_FIRE, 0.5F);
         }
      }
   }

   public static class MiningLaser extends AbstractMiningTool {
      public SoundEvent startSound = Sounds.tools_laser_start;
      public SoundEvent oneshotSound = Sounds.tools_laser_oneshot;
      public boolean canImpactGlass = false;
      public Predicate<? super IBlockState> filterGlass = new Predicate<IBlockState>() {
         public boolean apply(IBlockState input) {
            return input.getLightOpacity() == 0 && (input.getMaterial() == Material.GLASS || input.getMaterial() == Material.ICE);
         }
      };
      static MiningLaserModel model = new MiningLaserModel();
      public static float oneshotDamageMult = 8.0F;
      static ResourceLocation plasma_a = new ResourceLocation("arpg:textures/plasma_a.png");
      static ResourceLocation plasma_b = new ResourceLocation("arpg:textures/plasma_b.png");
      static ResourceLocation forge_hit_a = new ResourceLocation("arpg:textures/forge_hit_a.png");
      static ResourceLocation pixel = new ResourceLocation("arpg:textures/pixel.png");
      static ResourceLocation circle2 = new ResourceLocation("arpg:textures/circle2.png");
      static ResourceLocation blueexplode3 = new ResourceLocation("arpg:textures/blueexplode3.png");
      static ResourceLocation generic_beam4 = new ResourceLocation("arpg:textures/generic_beam4.png");
      static ResourceLocation laser_rifle_laser = new ResourceLocation("arpg:textures/laser_rifle_laser.png");
      static ResourceLocation mana_flow = new ResourceLocation("arpg:textures/mana_flow.png");
      static ResourceLocation shard_trace = new ResourceLocation("arpg:textures/shard_trace.png");

      public MiningLaser(String name, int maxDamage) {
         super(name, maxDamage, null);
         this.modesCount = 2;
      }

      public float getLaserParam(int param) {
         if (param == 16) {
            return 0.9F + Debugger.floats[16];
         } else if (param == 17) {
            return 0.2F + Debugger.floats[17];
         } else {
            return param == 18 ? -0.18F + Debugger.floats[18] : 0.0F;
         }
      }

      public float getXpRepairRatio(ItemStack stack) {
         return 7.0F;
      }

      @Override
      public String getNameForMode(int mode) {
         if (mode == 0) {
            return "tool.laser.mode.normal";
         } else if (mode == 1) {
            return "tool.laser.mode.oneshot";
         } else {
            return mode == 2 ? "tool.laser.mode.aligned" : "tool.laser.mode.explosive";
         }
      }

      @Override
      public ModelBase getModel() {
         return model;
      }

      @SideOnly(Side.CLIENT)
      @Override
      public void bom(int param) {
         Booom.lastTick = 7;
         Booom.frequency = 1.45F;
         Booom.x = (float)itemRand.nextGaussian();
         Booom.y = (float)itemRand.nextGaussian();
         Booom.z = (float)itemRand.nextGaussian();
         Booom.power = 0.08F * (param / 20.0F);
      }

      @Override
      public float gatMaxSpeed(ItemStack itemstack, EntityPlayer player) {
         return 10.0F;
      }

      @Override
      public void effect(EntityPlayer player, World world, double x, double y, double z, double a, double b, double c, double d1, double d2, double d3) {
         Entity entity = world.getEntityByID((int)d1);
         if (entity != null && entity instanceof EntityPlayer) {
            EntityPlayer shootingPlayer = (EntityPlayer)entity;
            Vec3d shootPoint = Weapons.getPlayerShootPoint(
               shootingPlayer, EnumHand.MAIN_HAND, 1.0F, this.getLaserParam(16), this.getLaserParam(17), this.getLaserParam(18), false
            );
            double dd0 = shootPoint.x - x;
            double dd1 = shootPoint.y - y;
            double dd2 = shootPoint.z - z;
            double dist = MathHelper.sqrt(dd0 * dd0 + dd1 * dd1 + dd2 * dd2);
            this.makeLaserParticles(
               world,
               shootingPlayer,
               shootPoint.x,
               shootPoint.y,
               shootPoint.z,
               x,
               y,
               z,
               c >= 20.0,
               (int)(c >= 20.0 ? c - 20.0 : c),
               dist,
               (float)a,
               (float)b
            );
         }
      }

      public void makeLaserParticles(
         World world,
         @Nullable EntityPlayer player,
         double xStart,
         double yStart,
         double zStart,
         double xEnd,
         double yEnd,
         double zEnd,
         boolean hit,
         int mode,
         double distance,
         float rotatPitch,
         float rotatYaw
      ) {
         EntityStreamLaserP laser = new EntityStreamLaserP(world, player, generic_beam4, 0.1F, 240, 1.0F, 0.9F, 0.9F, 0.0F, distance, 1, 0.2F, 2.0F);
         laser.setPosition(xStart, yStart, zStart);
         if (player == null) {
            laser.rotatPitch = rotatPitch;
            laser.rotatYaw = rotatYaw;
            laser.prevrotatPitch = rotatPitch;
            laser.prevrotatYaw = rotatYaw;
         }

         laser.useOldPositioning = false;
         laser.barrelLength = this.getLaserParam(16);
         laser.shoulders = this.getLaserParam(17);
         laser.yoffset = this.getLaserParam(18);
         laser.hand = EnumHand.MAIN_HAND;
         laser.useModel = true;
         float[] color1 = new float[]{1.0F, 0.2F, 0.1F, 1.0F};
         float[] color2 = new float[]{1.0F, 1.0F, 0.6F, 0.0F};
         float[] color3 = new float[]{1.0F, 0.0F, 1.0F, 1.0F};
         laser.model = new LaserModel.LaserModelLinear(generic_beam4, 240, 2, 0.07F, 0.0F, color1, null, 0.0F, 0.0F, 1.6F, -0.04F);
         laser.model.next = new LaserModel.LaserModelCircles(circle2, 240, 5.0, 14, 8, 0.1F, 0.02F, color1, color2);
         laser.model.next.next = new LaserModel.LaserModelCap(blueexplode3, 240, 0.14F, 0.0F, color3, 0.0F, 4.0F);
         laser.model.next.next.next = new LaserModel.LaserModelCap(forge_hit_a, 240, 0.1F, 0.0F, color1, 0.0F, -3.0F);
         laser.model.next.next.next.next = new LaserModel.LaserModelLightnings(null, 240, 15, 4, 0.3F, 0.01F, 0.75F, true, color1, color3);
         world.spawnEntity(laser);
      }

      @Override
      public void onUpdateMining(ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         float breakingSpeed = this.getBreakingSpeed(itemstack, player);
         if (mode != 1) {
            breakingSpeed *= speed / this.gatMaxSpeed(itemstack, player);
         }

         int toolLevel = this.toolsSet.toolLEVEL;
         if (mode == 0 || mode == 1) {
            if (mode == 1) {
               breakingSpeed *= 10.0F;
            }

            double blockReachDistance = this.getLengthLaser(itemstack);
            Vec3d vec3d = player.getPositionEyes(1.0F);
            Vec3d vec3d1 = player.getLook(1.0F);
            Vec3d vec3d2 = vec3d.add(
               vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance
            );
            RayTraceResult result = GetMOP.fixedRayTraceBlocks(
               world, player, 0.2, 0.2, true, vec3d, vec3d2, false, true, false, this.canImpactGlass ? null : this.filterGlass
            );
            Vec3d vec = vec3d2;
            int blocksBreak = 0;
            boolean hit = false;
            if (result != null && result.typeOfHit == Type.BLOCK && result.getBlockPos() != null) {
               hit = true;
               vec = GetMOP.normalizeRayTraceResult(result).hitVec;
               if (!world.isRemote) {
                  BlockBreaking blockBreaking = BlockBreaking.getBlockBreaking((WorldServer)world);
                  BlockPos blockPos = result.getBlockPos();
                  IBlockState state = world.getBlockState(blockPos);
                  String mostEffective = BlockBreaking.getMostEffectiveTool(state);
                  if (blockBreaking.damageBlock(
                     player,
                     blockPos,
                     breakingSpeed,
                     mostEffective,
                     toolLevel,
                     EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemstack),
                     EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, itemstack) > 0
                  )) {
                     blocksBreak++;
                  }
               }

               this.onHitBlock(result, itemstack, world, player, mode, speed, itemSlot);
            }

            if (result.entityHit != null) {
               hit = true;
               this.onHitEntity(result.entityHit, itemstack, world, player, mode, speed, itemSlot);
               vec = result.hitVec;
            }

            if (!world.isRemote) {
               if (mode == 1) {
                  IWeapon.fireEffect(
                     this,
                     player,
                     world,
                     64.0,
                     vec.x,
                     vec.y,
                     vec.z,
                     (double)player.rotationPitch,
                     (double)player.rotationYaw,
                     hit ? mode + 20 : mode,
                     (double)player.getEntityId(),
                     0.0,
                     0.0
                  );
               } else {
                  IWeapon.fireEffectExcl(
                     this,
                     player,
                     player,
                     world,
                     64.0,
                     vec.x,
                     vec.y,
                     vec.z,
                     player.rotationPitch,
                     player.rotationYaw,
                     hit ? mode + 20 : mode,
                     player.getEntityId(),
                     0.0,
                     0.0
                  );
               }
            } else {
               Vec3d shootPoint = Weapons.getPlayerShootPoint(
                  player, EnumHand.MAIN_HAND, 1.0F, this.getLaserParam(16), this.getLaserParam(17), this.getLaserParam(18), false
               );
               this.makeLaserParticles(
                  world,
                  player,
                  shootPoint.x,
                  shootPoint.y,
                  shootPoint.z,
                  vec.x,
                  vec.y,
                  vec.z,
                  hit,
                  mode,
                  vec.distanceTo(shootPoint),
                  0.0F,
                  0.0F
               );
            }

            if (mode != 0) {
               if (mode == 1) {
                  Weapons.setPlayerAnimationOnServer(player, 13, EnumHand.MAIN_HAND);
                  if (!player.capabilities.isCreativeMode) {
                     this.spendCharge(itemstack, world, player, 10);
                     itemstack.damageItem(6, player);
                  }

                  player.getCooldownTracker().setCooldown(this, 10 - EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.RAPIDITY, itemstack));
               }
            } else {
               if (player.ticksExisted % 15 == 0 || speed == 0) {
                  Weapons.setPlayerAnimationOnServer(player, 11, EnumHand.MAIN_HAND);
               }

               if (player.ticksExisted % 4 == 0) {
                  if (hit) {
                     IWeapon.sendIWeaponState(itemstack, player, 2, itemSlot, EnumHand.MAIN_HAND);
                  } else {
                     IWeapon.sendIWeaponState(itemstack, player, 1, itemSlot, EnumHand.MAIN_HAND);
                  }
               }

               if (!player.capabilities.isCreativeMode) {
                  this.spendCharge(itemstack, world, player, 1);
                  itemstack.damageItem(1, player);
               }
            }

            if (speed == 0) {
               world.playSound(
                  (EntityPlayer)null,
                  player.posX,
                  player.posY,
                  player.posZ,
                  mode == 1 ? this.oneshotSound : this.startSound,
                  SoundCategory.AMBIENT,
                  0.9F,
                  0.9F + itemRand.nextFloat() / 5.0F
               );
            }
         }
      }

      public void onHitEntity(Entity entity, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
      }

      public void onHitBlock(RayTraceResult result, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
      }

      @Override
      public void onStateReceived(EntityPlayer player, ItemStack itemstack, byte state, int slot) {
         super.onStateReceived(player, itemstack, state, slot);
         if (state == 1) {
            this.playOrContinueLoopSound(player, Sounds.tools_laser_beam_a, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 10, 12, 0.5F, 0.5F);
         }

         if (state == 2) {
            this.playOrContinueLoopSound(player, Sounds.tools_laser_digging, SoundCategory.PLAYERS, 1.0F, 1.0F, 10, 1, 8, 1.0F, 0.8F);
            this.playOrContinueLoopSound(player, Sounds.tools_laser_beam_a, SoundCategory.PLAYERS, 1.0F, 1.0F, 15, 10, 12, 0.5F, 0.5F);
         }
      }
   }

   public static class NuclearMiningRay extends ElectricMiningLaser {
      public static ResourceLocation radiation = new ResourceLocation("arpg:textures/radiation.png");

      public NuclearMiningRay(String name, int maxDamage, int maxRF, int throughput, int electricNeed) {
         super(name, maxDamage, maxRF, throughput, electricNeed);
         this.itemNeed = ItemsRegister.INGOTURANIUM;
         this.chargesPerItem = 2800;
      }

      @Override
      public void makeLaserParticles(
         World world,
         @Nullable EntityPlayer player,
         double xStart,
         double yStart,
         double zStart,
         double xEnd,
         double yEnd,
         double zEnd,
         boolean hit,
         int mode,
         double distance,
         float rotatPitch,
         float rotatYaw
      ) {
         EntityStreamLaserP laser = new EntityStreamLaserP(world, player, generic_beam4, 0.1F, 240, 1.0F, 0.9F, 0.9F, 0.0F, distance, 1, 0.2F, 2.0F);
         laser.setPosition(xStart, yStart, zStart);
         if (player == null) {
            laser.rotatPitch = rotatPitch;
            laser.rotatYaw = rotatYaw;
            laser.prevrotatPitch = rotatPitch;
            laser.prevrotatYaw = rotatYaw;
         }

         laser.useOldPositioning = false;
         laser.barrelLength = this.getLaserParam(16);
         laser.shoulders = this.getLaserParam(17);
         laser.yoffset = this.getLaserParam(18);
         laser.hand = EnumHand.MAIN_HAND;
         laser.useModel = true;
         float[] colorGreen = new float[]{0.22F, 0.77F, 0.29F, 1.0F};
         float[] colorYello = new float[]{0.95F, 0.84F, 0.27F, 1.0F};
         float[] colorGAlph = new float[]{0.0F, 1.0F, 0.0F, 0.0F};
         float[] colorWhite = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
         laser.model = new LaserModel.LaserModelLightnings(null, 240, 10, 2, 0.3F, 0.01F, 0.75F, true, colorGreen, colorYello);
         laser.model.next = new LaserModel.LaserModelLinear(generic_beam4, 240, 2, 0.08F, 0.0F, colorYello, colorGreen, 0.0F, 0.0F, 1.6F, -0.04F);
         laser.model.next.next = new LaserModel.LaserModelCircles(circle2, 240, 5.0, 14, 8, 0.13F, 0.05F, colorYello, colorGAlph);
         laser.model.next.next.next = new LaserModel.LaserModelCap(blueexplode3, 240, 0.14F, 0.0F, colorWhite, 0.0F, 4.0F);
         laser.model.next.next.next.next = new LaserModel.LaserModelCap(forge_hit_a, 240, 0.1F, 0.0F, colorYello, 0.0F, -3.0F);
         world.spawnEntity(laser);
         if (hit) {
            AnimatedGParticle rad = new AnimatedGParticle(
               radiation,
               0.2F,
               0.0F,
               16,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               0.0F,
               0.0F,
               0.0F,
               1.0F,
               1.0F,
               0.92F + itemRand.nextFloat() * 0.08F,
               true,
               itemRand.nextInt(360)
            );
            rad.framecount = 16;
            rad.scaleTickAdding = 0.015F + itemRand.nextFloat() * 0.03F;
            rad.randomDeath = false;
            rad.alphaGlowing = true;
            world.spawnEntity(rad);
            float spd0 = 0.4F;
            int lt0 = 16 + itemRand.nextInt(12);
            Vec3d spedVec = new Vec3d(xEnd - xStart, yEnd - yStart, zEnd - zStart)
               .normalize()
               .scale(-0.3)
               .add((itemRand.nextFloat() - 0.5F) * spd0, (itemRand.nextFloat() - 0.5F) * spd0, (itemRand.nextFloat() - 0.5F) * spd0);
            GUNParticle pix = new GUNParticle(
               pixel,
               0.03125F,
               0.04F,
               lt0,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               (float)spedVec.x,
               (float)spedVec.y,
               (float)spedVec.z,
               0.98F,
               1.0F,
               0.07F,
               false,
               0,
               true,
               1.2F
            );
            pix.scaleTickAdding = -0.03125F / lt0;
            world.spawnEntity(pix);
            int lt = 10 + itemRand.nextInt(10);
            boolean typee = itemRand.nextFloat() < 0.5F;
            float spd1 = 0.1F;
            float randcol = itemRand.nextFloat();
            AnimatedGParticle particle = new AnimatedGParticle(
               typee ? plasma_a : plasma_b,
               0.3F + itemRand.nextFloat() * 0.2F,
               0.0F,
               lt,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               (itemRand.nextFloat() - 0.5F) * spd1,
               (itemRand.nextFloat() - 0.5F) * spd1,
               (itemRand.nextFloat() - 0.5F) * spd1,
               0.98F - 0.7F * randcol,
               1.0F - 0.23F * randcol,
               0.07F + 0.2F * randcol,
               true,
               itemRand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.alphaTickAdding = -1.0F / lt;
            particle.framecount = typee ? 14 : 24;
            particle.animCounter = itemRand.nextInt(10);
            particle.useNormalTime = true;
            particle.disableOnEnd = false;
            particle.noWaterBubble = true;
            particle.isPushedByLiquids = false;
            world.spawnEntity(particle);
         }
      }

      @Override
      public void onHitEntity(Entity entity, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote && Team.checkIsOpponent(player, entity)) {
            float damage = mode == 1
               ? this.getDamageToMobsLaser(itemstack) * oneshotDamageMult
               : this.getDamageToMobsLaser(itemstack) * (speed / this.gatMaxSpeed(itemstack, player));
            Weapons.dealDamage(
               new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.laser),
               damage,
               player,
               entity,
               true,
               this.getKnockbackToMobsLaser(itemstack)
            );
            entity.hurtResistantTime = 0;
            DeathEffects.applyDeathEffect(entity, DeathEffects.DE_FIRE, 0.6F);
         }
      }

      @Override
      public void onHitBlock(RayTraceResult result, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote) {
            for (Entity entity : GetMOP.getEntitiesInAABBof(world, result.hitVec, 1.0, null)) {
               if (entity instanceof EntityPlayer) {
                  Mana.addRad((EntityPlayer)entity, 3, true);
               }
            }
         }
      }
   }

   public static class PlasmaCutter extends ElectricMiningLaser {
      public static ResourceLocation blueexplode = new ResourceLocation("arpg:textures/blueexplode.png");
      public static ResourceLocation plasma_beam = new ResourceLocation("arpg:textures/plasma_beam.png");

      public PlasmaCutter(String name, int maxDamage, int maxRF, int throughput, int electricNeed) {
         super(name, maxDamage, maxRF, throughput, electricNeed);
         this.itemNeed = ItemsRegister.BLUEARTHROSTELECHAROD;
         this.chargesPerItem = 1000;
      }

      @Override
      public void makeLaserParticles(
         World world,
         @Nullable EntityPlayer player,
         double xStart,
         double yStart,
         double zStart,
         double xEnd,
         double yEnd,
         double zEnd,
         boolean hit,
         int mode,
         double distance,
         float rotatPitch,
         float rotatYaw
      ) {
         EntityStreamLaserP laser = new EntityStreamLaserP(
            world, player, shard_trace, 0.1F, 240, 1.0F, 0.9F, 0.9F, 0.0F, distance, mode == 1 ? 4 : 1, 0.2F, 2.0F
         );
         laser.setPosition(xStart, yStart, zStart);
         if (player == null) {
            laser.rotatPitch = rotatPitch;
            laser.rotatYaw = rotatYaw;
            laser.prevrotatPitch = rotatPitch;
            laser.prevrotatYaw = rotatYaw;
         }

         laser.useOldPositioning = false;
         laser.barrelLength = this.getLaserParam(16);
         laser.shoulders = this.getLaserParam(17);
         laser.yoffset = this.getLaserParam(18);
         laser.hand = EnumHand.MAIN_HAND;
         laser.useModel = true;
         float[] color1 = new float[]{0.75F, 0.7F, 1.0F, 1.0F};
         float[] color2 = new float[]{0.5F, 0.0F, 1.0F, 1.0F};
         float[] color3 = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
         laser.model = new LaserModel.LaserModelLightnings(null, 240, 15, 2, 0.33F, 0.01F, 0.6F, true, color1, color2);
         laser.model.next = new LaserModel.LaserModelLinear(plasma_beam, 240, 3, 0.12F, 0.0F, color1, color1, 0.0F, 3.0F, 3.0F, -0.06F);
         laser.model.next.next = new LaserModel.LaserModelCap(blueexplode3, 240, 0.15F, 0.0F, color3, 0.0F, 3.0F);
         laser.model.next.next.next = new LaserModel.LaserModelCap(blueexplode, 240, 0.12F, 0.0F, color3, 0.0F, -5.0F);
         world.spawnEntity(laser);
         if (hit) {
            float r;
            float g;
            float b;
            if (itemRand.nextFloat() < 0.4F) {
               r = 0.625F + itemRand.nextFloat() * 0.22F;
               g = 0.4F + itemRand.nextFloat() * 0.2F;
               b = 0.93F;
            } else {
               r = 0.64F - itemRand.nextFloat() * 0.48F;
               g = 0.96F - itemRand.nextFloat() * 0.36F;
               b = 0.83F + itemRand.nextFloat() * 0.07F;
            }

            if (itemRand.nextFloat() < 0.33F) {
               float bright = 1.0F + itemRand.nextFloat() * 0.5F;
               r = Math.min(r * bright, 1.0F);
               g = Math.min(g * bright, 1.0F);
               b = Math.min(b * bright, 1.0F);
            }

            int lt = 6 + itemRand.nextInt(4);
            float scl = 0.7F + itemRand.nextFloat() * 0.45F;
            GUNParticle part = new GUNParticle(
               blueexplode, 0.05F, 0.0F, lt, 240, world, xEnd, yEnd, zEnd, 0.0F, 0.0F, 0.0F, r, g, b, true, itemRand.nextInt(360)
            );
            part.alphaGlowing = true;
            part.alphaTickAdding = -1.0F / lt;
            part.scaleTickAdding = scl / lt;
            world.spawnEntity(part);
            float spd0 = 0.4F;
            int lt0 = 16 + itemRand.nextInt(12);
            Vec3d spedVec = new Vec3d(xEnd - xStart, yEnd - yStart, zEnd - zStart)
               .normalize()
               .scale(-0.3)
               .add((itemRand.nextFloat() - 0.5F) * spd0, (itemRand.nextFloat() - 0.5F) * spd0, (itemRand.nextFloat() - 0.5F) * spd0);
            GUNParticle pix = new GUNParticle(
               pixel,
               0.03125F,
               0.04F,
               lt0,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               (float)spedVec.x,
               (float)spedVec.y,
               (float)spedVec.z,
               r,
               g,
               b,
               false,
               0,
               true,
               1.2F
            );
            pix.scaleTickAdding = -0.03125F / lt0;
            world.spawnEntity(pix);
            int ltx = 10 + itemRand.nextInt(10);
            boolean typee = itemRand.nextFloat() < 0.5F;
            float spd1 = 0.1F;
            float randcol = itemRand.nextFloat();
            AnimatedGParticle particle = new AnimatedGParticle(
               typee ? plasma_a : plasma_b,
               0.3F + itemRand.nextFloat() * 0.2F,
               0.0F,
               ltx,
               240,
               world,
               xEnd,
               yEnd,
               zEnd,
               (itemRand.nextFloat() - 0.5F) * spd1,
               (itemRand.nextFloat() - 0.5F) * spd1,
               (itemRand.nextFloat() - 0.5F) * spd1,
               0.75F - itemRand.nextFloat() * 0.2F,
               0.7F - itemRand.nextFloat() * 0.2F,
               1.0F,
               true,
               itemRand.nextInt(360)
            );
            particle.alphaGlowing = true;
            particle.alphaTickAdding = -1.0F / ltx;
            particle.framecount = typee ? 14 : 24;
            particle.animCounter = itemRand.nextInt(10);
            particle.useNormalTime = true;
            particle.disableOnEnd = false;
            particle.noWaterBubble = true;
            particle.isPushedByLiquids = false;
            world.spawnEntity(particle);
         }
      }

      @Override
      public void onHitEntity(Entity entity, ItemStack itemstack, World world, EntityPlayer player, int mode, int speed, int itemSlot) {
         if (!world.isRemote && Team.checkIsOpponent(player, entity)) {
            float damage = mode == 1
               ? this.getDamageToMobsLaser(itemstack) * oneshotDamageMult
               : this.getDamageToMobsLaser(itemstack) * (speed / this.gatMaxSpeed(itemstack, player));
            Weapons.dealDamage(
               new WeaponDamage(itemstack, player, null, false, false, player, WeaponDamage.plasma),
               damage,
               player,
               entity,
               true,
               this.getKnockbackToMobsLaser(itemstack)
            );
            entity.hurtResistantTime = 0;
            DeathEffects.applyDeathEffect(entity, DeathEffects.DE_FIRE, 0.8F);
         }
      }
   }
}
