package com.vivern.arpg.dimensions.mortuorus;

import com.vivern.arpg.dimensions.generationutils.GenerationHelper;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.BlocksRegister;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.NoiseGenerator3D;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class MortuorusCavesGenerator {
   public int genFromX;
   public int genFromZ;
   public int genToX;
   public int genToZ;
   public ChunkPrimer chunk;
   public Random rand;
   public double maxYoffset = 0.5;
   public double XZoffset = 0.18F;
   public NoiseGenerator3D noise3d;

   public MortuorusCavesGenerator(ChunkPrimer chunk, Random rand, NoiseGenerator3D noise3d) {
      this.chunk = chunk;
      this.rand = rand;
      this.noise3d = noise3d;
   }

   public MortuorusCavesGenerator setGenRange(int genFromX, int genFromZ, int genToX, int genToZ) {
      this.genFromX = genFromX;
      this.genFromZ = genFromZ;
      this.genToX = genToX;
      this.genToZ = genToZ;
      return this;
   }

   public MortuorusCavesGenerator setGenRange(ChunkPos chunk) {
      this.genFromX = chunk.getXStart();
      this.genFromZ = chunk.getZStart();
      this.genToX = chunk.getXEnd();
      this.genToZ = chunk.getZEnd();
      return this;
   }

   public static void onGenerateChunk(World world, int chunkX, int chunkZ, ChunkPrimer chunkprimer, NoiseGenerator3D noise) {
      int checkRadius = 16 + (int)Debugger.floats[4];

      for (int xx = -checkRadius; xx <= checkRadius; xx++) {
         for (int zz = -checkRadius; zz <= checkRadius; zz++) {
            int cX = chunkX + xx;
            int cZ = chunkZ + zz;
            if (GenerationHelper.canGenerateDungeonAtChunkPos(cX, cZ, world.getSeed(), 4, 10)) {
               Random rand = new Random(world.getSeed() + 1L);
               long k = rand.nextLong() / 2L * 2L + 1L;
               long l = rand.nextLong() / 2L * 2L + 1L;
               rand.setSeed(cX * k + cZ * l ^ world.getSeed());
               MortuorusCavesGenerator gen = new MortuorusCavesGenerator(chunkprimer, rand, new NoiseGenerator3D(rand.nextLong()));
               int i = cX * 16 + rand.nextInt(16);
               int j = cZ * 16 + rand.nextInt(16);
               gen.setGenRange(new ChunkPos(chunkX, chunkZ));
               gen.generate(new BlockPos(i, 15 + rand.nextInt(30), j));
            }
         }
      }
   }

   public void generate(BlockPos pos) {
      Vec3d mainvec = new Vec3d(this.rand.nextGaussian(), this.rand.nextGaussian() / 4.0, this.rand.nextGaussian()).normalize();
      Vec3d axisvec = new Vec3d(this.rand.nextGaussian() / 4.0, 4.0, this.rand.nextGaussian() / 4.0);
      this.generateTunnel(pos, 90 + this.rand.nextInt(40), mainvec, false, 1 + this.rand.nextInt(2));
      this.generateTunnel(pos, 90 + this.rand.nextInt(40), GetMOP.rotateVecAroundAxis(mainvec, axisvec, (100.0F + this.rand.nextInt(41)) * 0.017453F), false, 2);
      this.generateTunnel(
         pos, 90 + this.rand.nextInt(40), GetMOP.rotateVecAroundAxis(mainvec, axisvec, (-100.0F - this.rand.nextInt(41)) * 0.017453F), false, 2
      );
   }

   public void nextgenerate(BlockPos pos, int lastTunnels, Vec3d mainvec) {
      Vec3d axisvec = new Vec3d(this.rand.nextGaussian() / 4.0, 4.0, this.rand.nextGaussian() / 4.0);
      this.generateTunnel(pos, 60 + this.rand.nextInt(20), mainvec, this.rand.nextFloat() < 0.07F, lastTunnels);
      this.generateTunnel(
         pos,
         60 + this.rand.nextInt(20),
         GetMOP.rotateVecAroundAxis(mainvec, axisvec, (100.0F + this.rand.nextInt(41)) * 0.017453F),
         this.rand.nextFloat() < 0.07F,
         lastTunnels
      );
      this.generateTunnel(
         pos,
         60 + this.rand.nextInt(20),
         GetMOP.rotateVecAroundAxis(mainvec, axisvec, (-100.0F - this.rand.nextInt(41)) * 0.017453F),
         this.rand.nextFloat() < 0.07F,
         lastTunnels
      );
   }

   public void generateTunnel(BlockPos pos, int length, Vec3d vector, boolean canChangeHeight, int lastTunnels) {
      int rX = 3;
      int rY = 2;
      int rZ = 3;
      double vecX = vector.x;
      double vecY = vector.y;
      double vecZ = vector.z;
      double posX = pos.getX();
      double posY = pos.getY();
      double posZ = pos.getZ();

      for (int i = 0; i < length; i++) {
         posX += vecX;
         posY += vecY;
         posZ += vecZ;
         this.digTunnel(rX, rY, rZ, posX, posY, posZ);
         if (this.rand.nextFloat() < 0.02F) {
            Vec3d axisvec = new Vec3d(this.rand.nextGaussian() / 4.0, 6.0, this.rand.nextGaussian() / 4.0);
            Vec3d vecto = new Vec3d(vecX, vecY, vecZ);
            this.generateBranchA(
               new BlockPos(posX, posY, posZ),
               GetMOP.rotateVecAroundAxis(vecto, axisvec, (70.0F + this.rand.nextInt(41)) * (this.rand.nextFloat() < 0.5F ? 0.017453F : -0.017453F)),
               vecto
            );
         }

         if (this.rand.nextFloat() < 0.3F) {
            if (this.rand.nextFloat() < 0.33F) {
               rX = Math.max(rX + this.rand.nextInt(3) - 1, 1);
            } else if (this.rand.nextFloat() < 0.5F) {
               rY = Math.max(rY + this.rand.nextInt(3) - 1, 1);
            } else {
               rZ = Math.max(rZ + this.rand.nextInt(3) - 1, 1);
            }
         }

         if (this.rand.nextFloat() < 0.76F) {
            vecX += this.rand.nextGaussian() * this.XZoffset;
            if (canChangeHeight) {
               vecY += this.rand.nextGaussian() / 9.0;
            } else {
               vecY = MathHelper.clamp(vecY + this.rand.nextGaussian() / 11.0, -this.maxYoffset, this.maxYoffset);
            }

            vecZ += this.rand.nextGaussian() * this.XZoffset;
            double d0 = Math.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
            if (d0 > 0.0) {
               vecX /= d0;
               vecY /= d0;
               vecZ /= d0;
            }
         }
      }

      BlockPos finalPos = new BlockPos(posX, posY, posZ);
      if (this.rand.nextFloat() < 0.9F) {
         this.generateRoomB(finalPos, this.rand.nextInt(12) + 6, 0.3);
      }

      if (lastTunnels > 0) {
         this.nextgenerate(finalPos, --lastTunnels, new Vec3d(vecX, vecY, vecZ));
      }
   }

   public void generateRoomB(BlockPos pos, int sizeXZ, double heightMult) {
      double noiseScaling = 0.07 + this.rand.nextFloat() * 0.17;
      double noiseMultiplier = 1.5 + this.rand.nextFloat() * 3.5;

      for (int x = -sizeXZ; x <= sizeXZ; x++) {
         for (int z = -sizeXZ; z <= sizeXZ; z++) {
            if (this.isInGenRange(pos.getX() + x, pos.getZ() + z)) {
               for (int y = -sizeXZ; y <= sizeXZ; y++) {
                  double noiseValue = Math.max(
                     this.noise3d
                           .getValue(x * noiseScaling + pos.getX(), y * noiseScaling + pos.getY(), z * noiseScaling + pos.getZ())
                        * noiseMultiplier,
                     -1.0
                  );
                  double distanceValue = Math.sqrt(x * x + y * y + z * z) - sizeXZ;
                  if (noiseValue > distanceValue) {
                     BlockPos poss = new BlockPos(pos.getX() + x, pos.getY() + y * heightMult, pos.getZ() + z);
                     this.setBlockState(poss, poss.getY() == 1 ? BlocksRegister.FLUIDDARKNESS.getDefaultState() : Blocks.AIR.getDefaultState());
                  } else if (noiseValue > distanceValue - 1.0) {
                     BlockPos poss = new BlockPos(pos.getX() + x, pos.getY() + y * heightMult, pos.getZ() + z);
                     if (this.getBlockState(poss).getBlock() == BlocksRegister.BONESBLOCK) {
                        this.setBlockState(
                           poss, poss.getY() == 1 ? BlocksRegister.FLUIDDARKNESS.getDefaultState() : Blocks.AIR.getDefaultState()
                        );
                     }
                  }
               }
            }
         }
      }
   }

   public void generateBranchA(BlockPos pos, Vec3d vectorBranch, Vec3d vectorRoomIn) {
      double posX = pos.getX();
      double posY = pos.getY();
      double posZ = pos.getZ();
      int rX = 2;
      int rY = 2;
      int rZ = 2;
      double vecX = vectorBranch.x;
      double vecY = vectorBranch.y;
      double vecZ = vectorBranch.z;
      int imax = this.rand.nextInt(15) + 5;

      for (int i = 0; i < imax; i++) {
         posX += vecX;
         posY += vecY;
         posZ += vecZ;
         this.digTunnel(rX, rY, rZ, posX, posY, posZ);
         if (this.rand.nextFloat() < 0.3F) {
            if (this.rand.nextFloat() < 0.33F) {
               rX = Math.max(rX + this.rand.nextInt(3) - 1, 1);
            } else if (this.rand.nextFloat() < 0.5F) {
               rY = Math.max(rY + this.rand.nextInt(3) - 1, 1);
            } else {
               rZ = Math.max(rZ + this.rand.nextInt(3) - 1, 1);
            }
         }

         if (this.rand.nextFloat() < 0.76F) {
            vecX += this.rand.nextGaussian() * this.XZoffset;
            vecY = MathHelper.clamp(vecY + this.rand.nextGaussian() / 11.0, -this.maxYoffset, this.maxYoffset);
            vecZ += this.rand.nextGaussian() * this.XZoffset;
            double d0 = Math.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
            if (d0 > 0.0) {
               vecX /= d0;
               vecY /= d0;
               vecZ /= d0;
            }
         }

         if (this.rand.nextFloat() < 0.1F) {
            this.generateSpikedPit(new BlockPos(posX, posY, posZ), 2 + this.rand.nextInt(3), 2 + this.rand.nextInt(2));
         }
      }

      double posXs = posX;
      double posYs = posY;
      double posZs = posZ;

      for (int rr = -1; rr <= 1; rr += 2) {
         posX = posXs;
         posY = posYs;
         posZ = posZs;
         int rXx = 4;
         int rYx = 3;
         imax = 4;
         double vecXx = vectorRoomIn.x * rr;
         double vecYx = vectorRoomIn.y * rr;
         double vecZx = vectorRoomIn.z * rr;
         int imaxx = this.rand.nextInt(15) + 5;

         for (int i = 0; i < imaxx; i++) {
            posX += vecXx;
            posY += vecYx;
            posZ += vecZx;
            this.digTunnel(rXx, rYx, imax, posX, posY, posZ);
            if (this.rand.nextFloat() < 0.3F) {
               if (this.rand.nextFloat() < 0.33F) {
                  rXx = Math.max(rXx + this.rand.nextInt(3) - 1, 1);
               } else if (this.rand.nextFloat() < 0.5F) {
                  rYx = Math.max(rYx + this.rand.nextInt(3) - 1, 1);
               } else {
                  imax = Math.max(imax + this.rand.nextInt(3) - 1, 1);
               }
            }

            if (this.rand.nextFloat() < 0.76F) {
               vecXx += this.rand.nextGaussian() * this.XZoffset;
               vecYx = MathHelper.clamp(vecYx + this.rand.nextGaussian() / 11.0, -this.maxYoffset, this.maxYoffset);
               vecZx += this.rand.nextGaussian() * this.XZoffset;
               double d0 = Math.sqrt(vecXx * vecXx + vecYx * vecYx + vecZx * vecZx);
               if (d0 > 0.0) {
                  vecXx /= d0;
                  vecYx /= d0;
                  vecZx /= d0;
               }
            }

            if (this.rand.nextFloat() < 0.1F) {
               this.generateSpikedPit(new BlockPos(posX, posY, posZ), 2 + this.rand.nextInt(3), 2 + this.rand.nextInt(4));
            }
         }
      }
   }

   public void generateSpikedPit(BlockPos pos, int radius, int depth) {
      int radiusX = this.rand.nextInt(radius) + 1;
      int radiusX2 = this.rand.nextInt(radius) + 1;
      int radiusZ = this.rand.nextInt(radius) + 1;
      int radiusZ2 = this.rand.nextInt(radius) + 1;

      for (int x = -radiusX; x <= radiusX2; x++) {
         for (int z = -radiusZ; z <= radiusZ2; z++) {
            if (this.isInGenRange(pos.getX() + x, pos.getZ() + z) && x * x + z * z < this.rand.nextGaussian() + 4.0) {
               this.digSpikedPit(pos.add(x, 0, z), depth);
            }
         }
      }
   }

   public void digSpikedPit(BlockPos pos, int depth) {
      for (int y = pos.getY(); y > 2; y--) {
         BlockPos fpos = new BlockPos(pos.getX(), y, pos.getZ());
         if (this.getBlockState(fpos).isTopSolid()) {
            if (--depth <= 0) {
               this.setBlockState(fpos.up(), BlocksRegister.ICESPIKES.getDefaultState());
               return;
            }

            this.setBlockState(fpos, Blocks.AIR.getDefaultState());
         }
      }
   }

   public void digTunnel(int radiusX, int radiusY, int radiusZ, double posx, double posy, double posz) {
      double rx2 = radiusX * radiusX;
      double ry2 = radiusY * radiusY;
      double rz2 = radiusZ * radiusZ;

      for (int x = -radiusX - 1; x <= radiusX + 1; x++) {
         for (int z = -radiusZ - 1; z <= radiusZ + 1; z++) {
            if (this.isInGenRange(posx + x, posz + z)) {
               for (int y = -radiusY; y <= radiusY; y++) {
                  double fd = (double)x * x / rx2 + (double)y * y / ry2 + (double)z * z / rz2;
                  if (fd < 1.0) {
                     BlockPos poss = new BlockPos(posx + x, posy + y, posz + z);
                     this.setBlockState(poss, poss.getY() == 1 ? BlocksRegister.FLUIDDARKNESS.getDefaultState() : Blocks.AIR.getDefaultState());
                  } else if (fd < 2.0) {
                     BlockPos poss = new BlockPos(posx + x, posy + y, posz + z);
                     if (this.getBlockState(poss).getBlock() == BlocksRegister.BONESBLOCK) {
                        this.setBlockState(
                           poss, poss.getY() == 1 ? BlocksRegister.FLUIDDARKNESS.getDefaultState() : Blocks.AIR.getDefaultState()
                        );
                     }
                  }
               }
            }
         }
      }
   }

   public boolean isInGenRange(BlockPos pos) {
      return this.isInGenRange(pos.getX(), pos.getZ());
   }

   public boolean isInGenRange(int X, int Z) {
      return X >= this.genFromX && X <= this.genToX && Z >= this.genFromZ && Z <= this.genToZ;
   }

   public boolean isInGenRange(double X, double Z) {
      return this.isInGenRange(MathHelper.floor(X), MathHelper.floor(Z));
   }

   public void setBlockState(BlockPos pos, IBlockState state) {
      int i = pos.getX() & 15;
      int k = pos.getZ() & 15;
      this.chunk.setBlockState(i, MathHelper.clamp(pos.getY(), 1, 255), k, state);
   }

   public IBlockState getBlockState(BlockPos pos) {
      int i = pos.getX() & 15;
      int k = pos.getZ() & 15;
      return this.chunk.getBlockState(i, MathHelper.clamp(pos.getY(), 1, 255), k);
   }
}
