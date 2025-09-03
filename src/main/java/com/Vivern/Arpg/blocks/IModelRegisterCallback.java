//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModelRegisterCallback {
   @SideOnly(Side.CLIENT)
   default void registerModel() {
      if (this instanceof Item) {
         ModelLoader.setCustomModelResourceLocation((Item)this, 0, new ModelResourceLocation(((Item)this).getRegistryName(), "inventory"));
      } else if (this instanceof Block) {
         ModelResourceLocation mrl = (ModelResourceLocation)new DefaultStateMapper()
            .putStateModelLocations(((Block)this).getDefaultState().getBlock())
            .get(((Block)this).getDefaultState());
         ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock((Block)this), 0, mrl);
      }
   }
}
