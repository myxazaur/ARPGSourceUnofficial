package com.vivern.arpg.entity;

import com.vivern.arpg.main.Coins;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.network.PacketCoinToClient;
import com.vivern.arpg.network.PacketCoinToServer;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.renders.GUNParticle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCoin extends Entity {
   public int store = 0;
   public int ticks = 0;
   public int randomRotat = 0;
   public float randTranslate = 0.0F;
   public int t1r = 0;
   public int t2g = 0;
   public int t3b = 0;
   public static ResourceLocation res = new ResourceLocation("arpg:textures/coin_particle.png");

   public EntityCoin(World world) {
      super(world);
      this.setSize(0.3F, 0.3F);
      this.noClip = false;
      this.randomRotat = this.rand.nextInt(360);
      this.randTranslate = this.rand.nextFloat() * 50.0F;
   }

   public EntityCoin(World world, int store) {
      super(world);
      this.setSize(0.3F, 0.3F);
      this.noClip = false;
      this.store = store;
      this.motionX = this.rand.nextGaussian() / 13.0;
      this.motionY = this.rand.nextGaussian() / 17.0 + 0.4;
      this.motionZ = this.rand.nextGaussian() / 13.0;
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      this.randomRotat = this.rand.nextInt(360);
      this.randTranslate = this.rand.nextFloat() * 50.0F;
   }

   public void sendStoreToClients() {
      if (!this.world.isRemote) {
         PacketCoinToClient packet = new PacketCoinToClient();
         packet.write(this.store, this.getEntityId());
         PacketHandler.sendToAllAround(packet, this.world, this.posX, this.posY, this.posZ, 32.0);
      }
   }

   public void getStoreFromServer() {
      if (this.world.isRemote) {
         PacketCoinToServer packet = new PacketCoinToServer();
         packet.writeints(this.getEntityId());
         PacketHandler.NETWORK.sendToServer(packet);
      }
   }

   public double getYOffset() {
      return 0.15;
   }

   protected void entityInit() {
   }

   public void readEntityFromNBT(NBTTagCompound nbt) {
      if (nbt.hasKey("store")) {
         this.store = nbt.getInteger("store");
      }

      if (nbt.hasKey("ticks")) {
         this.ticks = nbt.getInteger("ticks");
      }
   }

   public void writeEntityToNBT(NBTTagCompound nbt) {
      nbt.setInteger("store", this.store);
      nbt.setInteger("ticks", this.ticks);
   }

   public void onEntityUpdate() {
      if (this.world.isRemote) {
         if (this.t1r > 0) {
            this.t1r--;
         }

         if (this.t2g > 0) {
            this.t2g--;
         }

         if (this.t3b > 0) {
            this.t3b--;
         }

         if (this.rand.nextFloat() < 0.02 && this.t1r == 0) {
            this.t1r = 30;
         }

         if (this.rand.nextFloat() < 0.02 && this.t2g == 0) {
            this.t2g = 30;
         }

         if (this.rand.nextFloat() < 0.025 && this.t3b == 0) {
            this.t3b = 30;
         }

         if (this.rand.nextFloat() < 0.1F && Minecraft.getMinecraft().gameSettings.particleSetting < 2) {
            double d0 = this.posX;
            double d1 = this.posY + 0.15;
            double d2 = this.posZ;
            double d3 = 0.22;
            double d4 = 0.27;
            int livetime = 40 + this.rand.nextInt(40);
            float scale = 0.057F + this.rand.nextFloat() / 30.0F;
            float scaleTickAdding = -(scale / livetime);
            float randispl1 = (this.rand.nextFloat() - 0.5F) / 2.0F;
            float randispl2 = (this.rand.nextFloat() - 0.5F) / 2.0F;
            float randispl3 = (this.rand.nextFloat() - 0.5F) / 2.0F;
            float randsp1 = (this.rand.nextFloat() - 0.5F) / 40.0F;
            float randsp2 = (this.rand.nextFloat() - 0.5F) / 40.0F;
            float randsp3 = (this.rand.nextFloat() - 0.5F) / 40.0F;
            GUNParticle spelll = new GUNParticle(
               res,
               scale,
               4.0E-4F,
               livetime,
               160 + this.rand.nextInt(40),
               this.world,
               d0 + randispl1,
               d1 + randispl2,
               d2 + randispl3,
               randsp1,
               randsp2,
               randsp3,
               this.getRed(),
               this.getGreen(),
               this.getBlue(),
               false,
               0,
               true,
               5.0F
            );
            spelll.dropMode = true;
            spelll.scaleTickAdding = scaleTickAdding;
            this.world.spawnEntity(spelll);
         }

         if (this.ticksExisted % 10 == 0 && this.store == 0) {
            this.getStoreFromServer();
         }
      }

      super.onEntityUpdate();
   }

   public float getRed() {
      return 1.0F - (float)Math.sin(this.t1r / 9.5F) / 2.0F;
   }

   public float getGreen() {
      return 1.0F - (float)Math.sin(this.t2g / 9.5F) / 2.0F;
   }

   public float getBlue() {
      return 1.0F - (float)Math.sin(this.t3b / 9.5F) / 2.0F;
   }

   public void onUpdate() {
      super.onUpdate();
      this.ticks++;
      if (this.ticksExisted == 2) {
         this.sendStoreToClients();
         this.world.setEntityState(this, (byte)25);
      }

      if (!this.hasNoGravity()) {
         this.motionY -= 0.03F;
      }

      this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
      this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      float f = 0.98F;
      if (this.onGround) {
         BlockPos underPos = new BlockPos(
            MathHelper.floor(this.posX),
            MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
            MathHelper.floor(this.posZ)
         );
         IBlockState underState = this.world.getBlockState(underPos);
         f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
      }

      this.motionX *= f;
      this.motionY *= 0.98F;
      this.motionZ *= f;
      if (this.onGround) {
         this.motionY *= -0.9F;
      }

      this.velocityChanged = true;
      if (this.ticks > 24000) {
         this.setDead();
      }
   }

   public void onCollideWithPlayer(EntityPlayer entityIn) {
      if (this.store != 0 && this.ticksExisted > 15 && !entityIn.world.isRemote && entityIn.isEntityAlive()) {
         this.world.setEntityState(this, (byte)0);
         Coins.addCoins(entityIn, this.store);
         this.setDead();
      }
   }

   @SideOnly(Side.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 0) {
         this.world
            .playSound(
               this.posX,
               this.posY,
               this.posZ,
               Sounds.grap_money,
               SoundCategory.PLAYERS,
               0.8F,
               0.9F + this.rand.nextFloat() / 5.0F,
               false
            );
      }
   }
}
