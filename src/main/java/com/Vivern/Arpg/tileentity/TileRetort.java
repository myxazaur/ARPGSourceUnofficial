package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.Retort;
import com.Vivern.Arpg.elements.Beaker;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.renders.GUNParticle;
import com.Vivern.Arpg.renders.ParticleTracker;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TileRetort extends TileEntity implements ITickable, ITileEntitySynchronize {
   public static Random rand = new Random();
   public static ResourceLocation star1 = new ResourceLocation("arpg:textures/forge_hit_a.png");
   public static ResourceLocation star2 = new ResourceLocation("arpg:textures/blueexplode3.png");
   public static ResourceLocation star3 = new ResourceLocation("arpg:textures/mana_flow.png");
   public static ResourceLocation blob = new ResourceLocation("arpg:textures/blob.png");
   public Beaker.BeakerFluid fluid;
   public boolean refineInitiated;
   public int refineTick;
   public BlockPos vialPosToRefine;
   static ParticleTracker ptssh1 = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(0.0, 12.0, 0.025), new Vec3d(13.0, 19.0, -0.0125), new Vec3d(20.0, 22.0, 0.21), new Vec3d(22.0, 26.0, -0.15)}
   );
   static ParticleTracker ptssh2 = new ParticleTracker.TrackerSmoothShowHide(
      null, new Vec3d[]{new Vec3d(0.0, 12.0, 0.0166), new Vec3d(13.0, 19.0, -0.0083), new Vec3d(20.0, 22.0, 0.14), new Vec3d(22.0, 26.0, -0.1)}
   );
   static ParticleTracker ptss = new ParticleTracker.TrackerSinusScaling(0.25F, 1.5F);

   public void onPlayerUseBeaker(ItemStack beaker, EntityPlayer playerIn) {
      if (!this.refineInitiated) {
         if (this.fluid == null) {
            this.fluid = new Beaker.BeakerFluid(8);
         }

         Beaker.BeakerFluid fluidIn = Beaker.readFromNbt(beaker, 16);
         boolean changed = false;
         if (playerIn.isSneaking()) {
            if (!this.fluid.isEmpty) {
               byte idElementTransfused = this.fluid.transfuseTo(fluidIn);
               changed = true;
               EnumFacing facing = (EnumFacing)this.world.getBlockState(this.pos).getValue(Retort.FACING);
               if (idElementTransfused > 0) {
                  TileSplitter.spawnTentacleParticle(
                     this.world,
                     new Vec3d(this.pos.getX() + 0.5, this.pos.getY() + 0.7, this.pos.getZ() + 0.5),
                     new Vec3d(facing.getXOffset(), 1.0, facing.getZOffset()).normalize(),
                     playerIn,
                     ShardType.byId(idElementTransfused),
                     2.0F,
                     false
                  );
               }
            }
         } else if (!fluidIn.isEmpty) {
            byte idElementTransfused = fluidIn.transfuseTo(this.fluid);
            this.fluid.combineLays();
            changed = true;
            EnumFacing facing = (EnumFacing)this.world.getBlockState(this.pos).getValue(Retort.FACING);
            if (idElementTransfused > 0) {
               TileSplitter.spawnTentacleParticle(
                  this.world,
                  new Vec3d(this.pos.getX() + 0.5, this.pos.getY() + 0.7, this.pos.getZ() + 0.5),
                  new Vec3d(facing.getXOffset(), 1.0, facing.getZOffset()).normalize(),
                  playerIn,
                  ShardType.byId(idElementTransfused),
                  2.0F,
                  true
               );
            }
         }

         if (changed) {
            Beaker.writeToNbt(beaker, fluidIn);
            PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 64.0);
         }
      }
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         this.refineInitiated = args[0] > 0.0;
         if (this.refineInitiated) {
            EnumFacing facing = (EnumFacing)this.world.getBlockState(this.pos).getValue(Retort.FACING);
            if (facing != null && this.fluid != null && !this.fluid.isEmpty) {
               ShardType shardType = ShardType.byId(this.fluid.elements[0]);
               facing = facing.getOpposite();
               Vec3d pointStart = new Vec3d(
                  this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5
               );
               Vec3d pointTop = pointStart.add(0.0, 0.25, 0.0);
               Vec3d pointNext = pointTop.add(facing.getXOffset() * 0.1875, 0.3125, facing.getZOffset() * 0.1875);
               Vec3d pointTip = pointTop.add(facing.getXOffset(), -0.0625, facing.getZOffset());
               Vec3d pointFinal = pointStart.add(facing.getXOffset(), -0.3125, facing.getZOffset());
               ParticleTracker ptt = new ParticleTracker.TrackerTrajectory(
                  new int[]{0, 6, 12, 19, 25}, new Vec3d[]{pointStart, pointTop, pointNext, pointTip, pointFinal}
               );
               int lt = 25;
               GUNParticle part = new GUNParticle(
                  star1,
                  0.0F,
                  0.0F,
                  lt,
                  240,
                  this.world,
                  pointStart.x,
                  pointStart.y,
                  pointStart.z,
                  0.0F,
                  0.0F,
                  0.0F,
                  shardType.colorR,
                  shardType.colorG,
                  shardType.colorB,
                  true,
                  rand.nextInt(360)
               );
               part.tracker = new ParticleTracker.Multitracker(ptt, ptss, ptssh1);
               part.alphaGlowing = true;
               part.randomDeath = false;
               this.world.spawnEntity(part);
               lt = 25;
               part = new GUNParticle(
                  star2,
                  0.0F,
                  0.0F,
                  lt,
                  240,
                  this.world,
                  pointStart.x,
                  pointStart.y,
                  pointStart.z,
                  0.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  true,
                  rand.nextInt(360)
               );
               part.tracker = new ParticleTracker.Multitracker(ptt, ptssh2);
               part.alphaGlowing = true;
               part.randomDeath = false;
               this.world.spawnEntity(part);
               this.world
                  .playSound(
                     pointStart.x,
                     pointStart.y,
                     pointStart.z,
                     Sounds.retort,
                     SoundCategory.BLOCKS,
                     1.0F,
                     0.9F + rand.nextFloat() / 5.0F,
                     false
                  );
            }
         }
      }
   }

   public void update() {
      if (this.refineInitiated) {
         this.refineTick++;
         if (this.refineTick >= 25) {
            if (!this.world.isRemote) {
               this.tryFillVial(this.vialPosToRefine);
            }

            this.refineInitiated = false;
            this.refineTick = 0;
         } else if (this.world.isRemote) {
            EnumFacing facing = (EnumFacing)this.world.getBlockState(this.pos).getValue(Retort.FACING);
            if (facing != null && this.fluid != null && !this.fluid.isEmpty) {
               facing = facing.getOpposite();
               ShardType shardType = ShardType.byId(this.fluid.elements[0]);
               if (this.refineTick > 10) {
                  Vec3d point = new Vec3d(
                     this.pos.getX() + 0.5 + facing.getXOffset() * 0.1875,
                     this.pos.getY() + 1.0625,
                     this.pos.getZ() + 0.5 + facing.getZOffset() * 0.1875
                  );
                  int lt = 10 + rand.nextInt(6);
                  float scl = 0.06F + rand.nextFloat() * 0.04F;
                  GUNParticle part = new GUNParticle(
                     star3,
                     scl,
                     0.0F,
                     lt,
                     240,
                     this.world,
                     point.x + (rand.nextFloat() - 0.5) * 0.5,
                     point.y + (rand.nextFloat() - 0.5) * 0.5,
                     point.z + (rand.nextFloat() - 0.5) * 0.5,
                     facing.getXOffset() * 0.07F,
                     -0.03F,
                     facing.getZOffset() * 0.07F,
                     shardType.colorR,
                     shardType.colorG,
                     shardType.colorB,
                     true,
                     rand.nextInt(360)
                  );
                  part.alpha = 0.0F;
                  part.alphaTickAdding = 0.14F;
                  part.scaleTickAdding = -scl / lt;
                  part.alphaGlowing = true;
                  part.randomDeath = false;
                  this.world.spawnEntity(part);
               }

               if (this.refineTick < 15) {
                  int lt = 10 + rand.nextInt(6);
                  float scl = 0.05F + rand.nextFloat() * 0.035F;
                  GUNParticle part = new GUNParticle(
                     blob,
                     scl,
                     -0.005F,
                     lt,
                     240,
                     this.world,
                     this.pos.getX() + 0.5 + (rand.nextFloat() - 0.5) * 0.5,
                     this.pos.getY() + 0.1875 + rand.nextFloat() * 0.1875,
                     this.pos.getZ() + 0.5 + (rand.nextFloat() - 0.5) * 0.5,
                     0.0F,
                     0.0F,
                     0.0F,
                     shardType.colorR,
                     shardType.colorG,
                     shardType.colorB,
                     true,
                     rand.nextInt(61) - 30
                  );
                  part.alpha = 0.0F;
                  part.alphaTickAdding = 0.3F;
                  part.randomDeath = false;
                  this.world.spawnEntity(part);
               }

               if (this.refineTick == 23) {
                  Vec3d point = new Vec3d(
                     this.pos.getX() + 0.5 + facing.getXOffset(),
                     this.pos.getY() + 0.625,
                     this.pos.getZ() + 0.5 + facing.getZOffset()
                  );

                  for (int i = 0; i < 8; i++) {
                     shardType.spawnNativeParticle(
                        this.world,
                        1.0F,
                        point.x,
                        point.y,
                        point.z,
                        rand.nextGaussian() / 26.0,
                        rand.nextGaussian() / 26.0 + 0.035F,
                        rand.nextGaussian() / 26.0,
                        true
                     );
                  }
               }

               if (this.refineTick % 3 == 0) {
                  this.fluid.remove();
               }
            }
         }
      }
   }

   public void startRefining(BlockPos vialPos) {
      if (this.fluid.isReadyToRefine() && !this.refineInitiated) {
         this.refineInitiated = true;
         this.refineTick = 0;
         this.vialPosToRefine = vialPos;
         ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0);
      }
   }

   public void tryFillVial(BlockPos vialPos) {
      if (this.fluid.isReadyToRefine()) {
         TileEntity tentity = this.world.getTileEntity(vialPos);
         if (tentity != null && tentity instanceof TileVial) {
            TileVial tileVial = (TileVial)tentity;

            for (int i = 0; i < tileVial.vials.length; i++) {
               ItemStack itemstack = tileVial.vials[i];
               if (itemstack != null
                  && !itemstack.isEmpty()
                  && itemstack.getItem() == ItemsRegister.VIALEMPTY
                  && GetMOP.approximatelyEqual(tileVial.positions[i][0], 8, 1)
                  && GetMOP.approximatelyEqual(tileVial.positions[i][1], 8, 1)) {
                  tileVial.removeVialItem(i);
                  tileVial.addNewVialItem(
                     new ItemStack(ItemsRegister.VIAL, 1, this.fluid.elements[0]), tileVial.positions[i][0], tileVial.positions[i][1], tileVial.rotations[i]
                  );
                  this.fluid = new Beaker.BeakerFluid(8);
                  PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 64.0);
                  return;
               }
            }
         }

         ShardType.spawnShards(
            this.world,
            ShardType.byId(this.fluid.elements[0]),
            vialPos.getX() + 0.5,
            vialPos.getY() + 0.5,
            vialPos.getZ() + 0.5,
            4.0F
         );
         this.fluid = new Beaker.BeakerFluid(8);
         PacketHandler.trySendPacketUpdate(this.world, this.getPos(), this, 64.0);
      }
   }

   public void write(NBTTagCompound compound) {
      if (this.fluid != null) {
         Beaker.writeToNbt(compound, this.fluid);
      }
   }

   public void read(NBTTagCompound compound) {
      this.fluid = Beaker.readFromNbt(compound, 8);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }
}
