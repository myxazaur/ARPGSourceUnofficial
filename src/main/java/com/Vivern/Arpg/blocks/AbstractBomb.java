package com.Vivern.Arpg.blocks;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractBomb extends Block {
   public AbstractBomb(Material materialIn) {
      super(materialIn);
   }

   public abstract void blockexploded(World var1, BlockPos var2, @Nullable EntityLivingBase var3, @Nullable Entity var4);

   public abstract void activate(World var1, BlockPos var2, @Nullable EntityLivingBase var3);
}
