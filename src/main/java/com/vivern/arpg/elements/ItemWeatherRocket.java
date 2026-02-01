package com.vivern.arpg.elements;

import com.vivern.arpg.entity.EntityWeatherRocket;
import com.vivern.arpg.main.Sounds;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWeatherRocket extends ItemItem {
   @Nullable
   public String eventName;
   public boolean stopAllBeforeStart;
   public float RED;
   public float GREEN;
   public float BLUE;
   public int textureId;
   public static ArrayList<ResourceLocation> rocketTextures = new ArrayList<>();

   public ItemWeatherRocket(String name, String eventName, boolean stopAllBeforeStart, float RED, float GREEN, float BLUE) {
      super(name, CreativeTabs.TOOLS, 0, 16);
      this.eventName = eventName;
      this.stopAllBeforeStart = stopAllBeforeStart;
      this.RED = RED;
      this.GREEN = GREEN;
      this.BLUE = BLUE;
      ResourceLocation resourceLocation = new ResourceLocation("arpg:textures/" + name + ".png");
      rocketTextures.add(resourceLocation);

      for (int i = 0; i < rocketTextures.size(); i++) {
         if (rocketTextures.get(i) == resourceLocation) {
            this.textureId = i;
            break;
         }
      }
   }

   public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
      if (!worldIn.isRemote) {
         ItemStack itemstack = player.getHeldItem(hand);
         EntityWeatherRocket entityfireworkrocket = new EntityWeatherRocket(
            worldIn, pos.getX() + hitX, pos.getY() + hitY, pos.getZ() + hitZ
         );
         entityfireworkrocket.setColor(this.RED, this.GREEN, this.BLUE);
         entityfireworkrocket.eventName = this.eventName;
         entityfireworkrocket.stopAllBeforeStart = this.stopAllBeforeStart;
         entityfireworkrocket.setTextureId(this.textureId);
         worldIn.spawnEntity(entityfireworkrocket);
         worldIn.playSound(
            (EntityPlayer)null,
            entityfireworkrocket.posX,
            entityfireworkrocket.posY,
            entityfireworkrocket.posZ,
            Sounds.weather_firework_launch,
            SoundCategory.PLAYERS,
            1.0F,
            0.9F + itemRand.nextFloat() / 5.0F
         );
         if (!player.capabilities.isCreativeMode) {
            itemstack.shrink(1);
         }
      }

      return EnumActionResult.SUCCESS;
   }
}
