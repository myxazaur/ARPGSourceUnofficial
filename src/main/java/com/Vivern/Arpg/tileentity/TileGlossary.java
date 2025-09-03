//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsElements;
import java.util.ArrayList;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class TileGlossary extends TileEntity implements ITickable {
   public int rotation;
   public ArrayList<BlockPos> allWatchPositions;
   public int watchLocation = 0;
   public int watchStep = 10;
   public ItemsElements.ElementsPack clientPackDisplayed;
   public int cooldown = 0;
   public int prevanimationPapers = 0;
   public int animationPapers = 0;
   public int animationPapersTarget = 0;
   public static int animationPapersMax = 20;
   public int animationRunes = 0;

   public void fillWatchPositions() {
      this.allWatchPositions = new ArrayList<>();
      int radiusXZ = 5;
      int yUp = 4;
      int yDown = 1;
      int indx = 0;

      for (int x = -radiusXZ; x <= radiusXZ; x++) {
         for (int z = -radiusXZ; z <= radiusXZ; z++) {
            for (int y = -yDown; y <= yUp; y++) {
               BlockPos watchPos = new BlockPos(
                  this.pos.getX() + x, this.pos.getY() + y, this.pos.getZ() + z
               );
               if (this.world.getBlockState(watchPos).getBlock() == BlocksRegister.BOOKCASEWOODEN) {
                  this.allWatchPositions.add(watchPos);
               }
            }
         }
      }
   }

   public void startAnimation(Random rand, @Nullable BlockPos casePos) {
      int randi = rand.nextInt(5);
      int v = (int)(randi * 0.25F * animationPapersMax);
      if (this.animationPapersTarget == v) {
         randi = GetMOP.next(randi, 1, 5);
         v = (int)(randi * 0.25F * animationPapersMax);
      }

      this.animationPapersTarget = v;
      this.animationRunes = 10;
      if (casePos != null) {
         int i = casePos.getX() - this.pos.getX();
         int k = casePos.getY() - this.pos.getY();
         int j = casePos.getZ() - this.pos.getZ();

         for (int j2 = 0; j2 < 3; j2++) {
            this.world
               .spawnParticle(
                  EnumParticleTypes.ENCHANTMENT_TABLE,
                  this.pos.getX() + 0.5,
                  this.pos.getY() + 1.8,
                  this.pos.getZ() + 0.5,
                  i + rand.nextFloat() - 0.5,
                  k - rand.nextFloat() - 1.0F,
                  j + rand.nextFloat() - 0.5,
                  new int[0]
               );
         }
      }
   }

   public boolean continueWatch(ItemStack stack, Random rand) {
      int max = this.watchLocation + this.watchStep;

      for (int i = this.watchLocation; i < max; i++) {
         if (i >= this.allWatchPositions.size()) {
            if (this.clientPackDisplayed != null) {
               this.startAnimation(rand, null);
            }

            this.clientPackDisplayed = null;
            this.watchLocation = 0;
            return true;
         }

         BlockPos watchPos = this.allWatchPositions.get(i);
         TileEntity tile = this.world.getTileEntity(watchPos);
         if (tile instanceof TileBookcase) {
            TileBookcase tileBookcase = (TileBookcase)tile;
            ItemsElements.ElementsPack pack = tileBookcase.getInformation(stack);
            if (pack != null && pack != ItemsElements.EMPTY_ELEMENTS) {
               if (pack != this.clientPackDisplayed) {
                  this.startAnimation(rand, tileBookcase.getPos());
               }

               this.clientPackDisplayed = pack;
               this.watchLocation = 0;
               return true;
            }
         }
      }

      this.watchLocation = max;
      return false;
   }

   public void update() {
      if (this.world.isRemote) {
         this.prevanimationPapers = this.animationPapers;
         if (this.animationPapers != this.animationPapersTarget) {
            if (this.animationPapers < this.animationPapersTarget) {
               this.animationPapers++;
            } else {
               this.animationPapers--;
            }
         }

         if (this.animationRunes > 0) {
            this.animationRunes--;
         }

         if (this.cooldown <= 0) {
            if (this.allWatchPositions == null) {
               this.fillWatchPositions();
            }

            RayTraceResult mouseOver = Minecraft.getMinecraft().objectMouseOver;
            if (mouseOver != null && this.pos.equals(mouseOver.getBlockPos())) {
               EntityPlayer player = Minecraft.getMinecraft().player;
               if (player != null && this.continueWatch(player.getHeldItemMainhand(), player.getRNG())) {
                  this.cooldown = 10;
                  this.fillWatchPositions();
               }
            }
         } else {
            this.cooldown--;
         }
      }
   }
}
