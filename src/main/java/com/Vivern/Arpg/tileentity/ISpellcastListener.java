package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.Spell;
import net.minecraft.item.ItemStack;

public interface ISpellcastListener {
   boolean onSpellcast(double var1, double var3, double var5, ItemStack var7, Spell[] var8, Object var9);

   boolean canAttractParticles(Object var1);
}
