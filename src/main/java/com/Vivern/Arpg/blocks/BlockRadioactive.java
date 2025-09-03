//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.Mana;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRadioactive extends BlockBlockHard {
   public int radiance;
   public int distance;
   public int breakRad;
   public int walkRad;

   public BlockRadioactive(
      Material mater, String name, BlocksRegister.Hardres hardres, String tool, int radianceRadiation, int radianceDistance, int breakRad, int walkRad
   ) {
      super(mater, name, hardres, tool, false);
      this.radiance = radianceRadiation;
      this.distance = radianceDistance;
      this.breakRad = breakRad;
      this.walkRad = walkRad;
      this.setTickRandomly(radianceRadiation != 0 && radianceDistance > 0);
   }

   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
      if (!worldIn.isRemote) {
         AxisAlignedBB axisalignedbb = new AxisAlignedBB(
            pos.add(-this.distance, -this.distance, -this.distance), pos.add(this.distance, this.distance, this.distance)
         );
         List<EntityPlayer> list = worldIn.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
         if (!list.isEmpty()) {
            for (EntityPlayer player : list) {
               Mana.addRad(player, this.radiance, true);
            }
         }
      }

      super.randomTick(worldIn, pos, state, random);
   }

   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
      if (!world.isRemote && this.breakRad != 0) {
         Mana.addRad(player, this.breakRad, true);
      }

      return super.removedByPlayer(state, world, pos, player, willHarvest);
   }

   public void onEntityWalk(World world, BlockPos pos, Entity entityIn) {
      if (!world.isRemote && this.walkRad != 0 && entityIn instanceof EntityPlayer) {
         Mana.addRad((EntityPlayer)entityIn, this.walkRad, true);
      }

      super.onEntityWalk(world, pos, entityIn);
   }
}
