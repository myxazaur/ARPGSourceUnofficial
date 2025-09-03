//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.entity;

import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.renders.GUNParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityElectricAcidRadiationPotion extends EntityThrowable {
   ResourceLocation texturbubble = new ResourceLocation("arpg:textures/bilebiter_shoot4.png");
   ResourceLocation texturspin = new ResourceLocation("arpg:textures/spin.png");
   ResourceLocation textursplash = new ResourceLocation("arpg:textures/acid_splash1.png");

   public EntityElectricAcidRadiationPotion(World world) {
      super(world);
   }

   public EntityElectricAcidRadiationPotion(World world, EntityLivingBase thrower) {
      super(world, thrower);
   }

   public EntityElectricAcidRadiationPotion(World world, double x, double y, double z) {
      super(world, x, y, z);
   }

   protected void onImpact(RayTraceResult result) {
      if ((
            result.entityHit != null
               || this.world
                     .getBlockState(result.getBlockPos())
                     .getBlock()
                     .getCollisionBoundingBox(this.world.getBlockState(result.getBlockPos()), this.world, result.getBlockPos())
                  != null
         )
         && !this.world.isRemote) {
         this.world.setEntityState(this, (byte)5);
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 5) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.de_acid,
               SoundCategory.AMBIENT,
               1.2F,
               0.75F + this.rand.nextFloat() / 10.0F,
               false
            );
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               SoundEvents.BLOCK_GLASS_BREAK,
               SoundCategory.AMBIENT,
               1.1F,
               0.95F + this.rand.nextFloat() / 10.0F,
               false
            );

         for (int ss = 0; ss < 8; ss++) {
            Entity bubble = new GUNParticle(
               this.texturbubble,
               0.1F + (float)this.rand.nextGaussian() / 25.0F,
               -0.01F,
               50 + this.rand.nextInt(20),
               200,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 9.0F,
               (float)this.rand.nextGaussian() / 9.0F + 0.15F,
               (float)this.rand.nextGaussian() / 9.0F,
               0.4F + (float)this.rand.nextGaussian() / 5.0F,
               0.9F + (float)this.rand.nextGaussian() / 10.0F,
               1.0F,
               false,
               this.rand.nextInt(180),
               true,
               1.3F
            );
            this.world.spawnEntity(bubble);
            Entity spin = new GUNParticle(
               this.texturspin,
               0.5F + (float)this.rand.nextGaussian() / 3.0F,
               -0.01F,
               20 + this.rand.nextInt(20),
               200,
               this.world,
               this.posX,
               this.posY,
               this.posZ,
               (float)this.rand.nextGaussian() / 15.0F,
               (float)this.rand.nextGaussian() / 15.0F + 0.15F,
               (float)this.rand.nextGaussian() / 15.0F,
               0.4F + (float)this.rand.nextGaussian() / 5.0F,
               0.9F + (float)this.rand.nextGaussian() / 10.0F,
               1.0F,
               true,
               this.rand.nextInt(180),
               false,
               1.3F
            );
            this.world.spawnEntity(spin);
         }
      }
   }
}
