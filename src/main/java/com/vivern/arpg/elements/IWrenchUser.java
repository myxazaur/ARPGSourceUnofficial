package com.vivern.arpg.elements;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchUser {
   void onUseWrench(World var1, Entity var2, ItemStack var3, float var4, @Nullable BlockPos var5);
}
