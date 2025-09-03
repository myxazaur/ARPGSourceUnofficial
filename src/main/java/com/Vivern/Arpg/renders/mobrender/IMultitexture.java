package com.Vivern.Arpg.renders.mobrender;

import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMultitexture {
   @SideOnly(Side.CLIENT)
   @Nullable
   ResourceLocation getMultitexture();
}
