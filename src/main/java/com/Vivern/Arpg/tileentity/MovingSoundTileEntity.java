//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSoundTileEntity extends MovingSound {
   public TileEntity tileEntity;
   public SoundEvent sound;
   public World world;
   public BlockPos tilepos;
   public int upriseTicks;
   public int time;
   public boolean stop = false;

   public MovingSoundTileEntity(TileEntity tileEntity, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean repeat, int upriseTicks) {
      super(sound, category);
      this.world = tileEntity.getWorld();
      this.tileEntity = tileEntity;
      this.repeat = repeat;
      this.volume = volume;
      this.pitch = pitch;
      this.sound = sound;
      this.xPosF = tileEntity.getPos().getX();
      this.yPosF = tileEntity.getPos().getY();
      this.zPosF = tileEntity.getPos().getZ();
      this.tilepos = tileEntity.getPos();
      this.upriseTicks = upriseTicks;
   }

   public void update() {
      this.time++;
      TileEntity getted = this.world.getTileEntity(this.tilepos);
      if (getted == null || getted != this.tileEntity) {
         this.donePlaying = true;
      } else if (this.stop) {
         this.donePlaying = true;
      }
   }
}
