//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSoundEntity extends MovingSound {
   public final Entity entity;

   public MovingSoundEntity(Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean repeat) {
      super(sound, category);
      this.entity = entity;
      this.repeat = repeat;
      this.volume = volume;
      this.pitch = pitch;
   }

   public void update() {
      if (!this.entity.isDead) {
         this.xPosF = (float)this.entity.posX;
         this.yPosF = (float)this.entity.posY;
         this.zPosF = (float)this.entity.posZ;
      } else {
         this.donePlaying = true;
      }
   }
}
