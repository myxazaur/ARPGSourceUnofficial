//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.dimensions.generationutils;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.mobs.OtherMobsPack;
import com.Vivern.Arpg.tileentity.TileMonsterSpawner;
import java.util.Random;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenVoidCrystals implements IWorldGenerator {
   public boolean generate(World worldIn, Random rand, BlockPos pos) {
      if (worldIn.isAirBlock(pos)) {
         worldIn.setBlockState(pos, BlocksRegister.VOIDCRYSTAL.getDefaultState(), 2);
         TileEntity tile = worldIn.getTileEntity(pos);
         if (tile != null && tile instanceof TileMonsterSpawner) {
            TileMonsterSpawner spawner = (TileMonsterSpawner)tile;
            spawner.addMobToSpawn(OtherMobsPack.VoidGuard.class, 10, false);
            spawner.initSpawner(450 + rand.nextInt(200), 1.5F, 0.1F + rand.nextFloat() * 0.1F, 2, 4, 6.0F, 6.0F, 32.0F, 4 + rand.nextInt(4));
         }

         return true;
      } else {
         return false;
      }
   }

   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
      if (world.provider.getDimension() == 1 && chunkX * chunkX + chunkZ * chunkZ > 900 && random.nextFloat() < 0.06F) {
         this.generate(world, random, new BlockPos(chunkX * 16 + random.nextInt(16), 48 + random.nextInt(128), chunkZ * 16 + random.nextInt(16)));
      }
   }
}
