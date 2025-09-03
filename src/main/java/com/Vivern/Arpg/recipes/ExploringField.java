//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.recipes;

import com.Vivern.Arpg.container.GUIResearchTable;
import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.main.AnimationTimer;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Spell;
import com.Vivern.Arpg.network.PacketExploringToClient;
import com.Vivern.Arpg.network.PacketExploringToServer;
import com.Vivern.Arpg.network.PacketHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExploringField {
   ArrayList<ExploringPoint> points = new ArrayList<>();
   public int xDisplace = 0;
   public static int YPOS1 = 20;
   public static int YPOS2 = 45;
   public static int YPOS3 = 70;
   public static int YPOS4 = 95;
   public static int YPOS5 = 120;
   public static int XX = 30;

   public static ExploringField generateExploringField(NBTTagCompound exploringNbtTag) {
      ExploringField field = new ExploringField();
      int nbtkeyNonRunes1 = 20;
      ExploringPoint Pstart = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 0, null, ExploringPointType.TUTORIAL, XX * 2, YPOS3).setIfExplored(exploringNbtTag)
      );
      ExploringPoint Pmove = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 1, Pstart, ExploringPointType.TUTORIAL, XX * 4, YPOS3)
            .setIfExplored(exploringNbtTag)
      );
      ExploringPoint Phowwin = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 2, Pmove, ExploringPointType.TUTORIAL, XX * 6, YPOS3).setIfExplored(exploringNbtTag)
      );
      ExploringPoint Psplit = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 3, Phowwin, ExploringPointType.TUTORIAL, XX * 9, YPOS3)
            .setIfExplored(exploringNbtTag)
      );

      for (int i = 1; i <= 3; i++) {
         ExploringPoint Pshards1 = field.addPoint(
            new ExploringPoint(Spell.spellsRegistry.get(i), Phowwin, XX * (4 + i), YPOS1)
               .setIfExplored(exploringNbtTag)
               .setupRTInventoryGen(i, 3)
         );
         ExploringPoint Palgiz = field.addPoint(
            new ExploringPoint(Spell.spellsRegistry.get(i + 3), Phowwin, XX * (4 + i), YPOS5)
               .setIfExplored(exploringNbtTag)
               .setupRTInventoryGen(i + 3, 3)
         );
      }

      for (int i = 1; i <= 3; i++) {
         ExploringPoint Pshards1 = field.addPoint(
            new ExploringPoint(Spell.spellsRegistry.get(i + 6), Psplit, XX * (7 + i), YPOS1)
               .setIfExplored(exploringNbtTag)
               .setupRTInventoryGen(i + 6, 3)
         );
         ExploringPoint var41 = field.addPoint(
            new ExploringPoint(Spell.spellsRegistry.get(i + 9), Psplit, XX * (7 + i), YPOS5)
               .setIfExplored(exploringNbtTag)
               .setupRTInventoryGen(i + 9, 3)
         );
      }

      ExploringPoint Pability = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 4, Psplit, ExploringPointType.TUTORIAL, XX * 12, YPOS3)
            .setIfExplored(exploringNbtTag)
      );
      ExploringPoint Panima = field.addPoint(
         new ExploringPoint(Spell.ANIMA, Pability, XX * 13, YPOS1)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-1, 0, 0, 1, 0, 0, 0, 0, -2, 0, 0, 0}, new int[]{1, 0, 0, 4, 0, 0, 2, 2, 2, 0, 0, 3}, 4)
      );
      ExploringPoint Palgiz = field.addPoint(
         new ExploringPoint(Spell.ALGIZ, Pability, XX * 13, YPOS5)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-1, 1, -1, 0, 0, 0, 0, 0, -2, 0, 0, -1}, new int[]{1, 5, 3, 0, 0, 0, 0, 0, 2, 0, 0, 2}, 4)
      );
      ExploringPoint Pira = field.addPoint(
         new ExploringPoint(Spell.IRA, Pability, XX * 12, YPOS5)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0}, new int[]{5, 2, 0, 0, 2, 0, 2, 0, 0, 3, 2, 0}, 4)
      );
      ExploringPoint Psurfaces = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 5, Pability, ExploringPointType.TUTORIAL, XX * 14, YPOS3)
            .setIfExplored(exploringNbtTag)
      );
      ExploringPoint Puruz = field.addPoint(
         new ExploringPoint(Spell.URUZ, Psurfaces, XX * 15, YPOS3)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-1, -1, -1, -1, -1, -1, 1, 0, 0, 0, 0, 0}, new int[]{3, 3, 3, 3, 1, 1, 5, 1, 2, 0, 0, 2}, 5)
      );
      ExploringPoint Ppeccatum = field.addPoint(
         new ExploringPoint(Spell.PECCATUM, Psurfaces, XX * 15, YPOS4)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0}, new int[]{4, 1, 0, 1, 2, 0, 4, 2, 2, 4, 1, 1}, 4)
      );
      ExploringPoint Pexpulsio = field.addPoint(
         new ExploringPoint(Spell.EXPULSIO, Psurfaces, XX * 15, YPOS5)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{1, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{6, 2, 0, 4, 0, 0, 2, 1, 0, 1, 3, 0}, 4)
      );
      ExploringPoint Psowilo = field.addPoint(
         new ExploringPoint(Spell.SOWILO, Puruz, XX * 15, YPOS2)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 3)
      );
      ExploringPoint Pdrakkar = field.addPoint(
         new ExploringPoint(Spell.DRAKKAR, Psowilo, XX * 15, YPOS1)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0}, new int[]{3, 2, 0, 3, 0, 0, 0, 3, 0, 0, 2, 2}, 4)
      );
      ExploringPoint Pmanifaul = field.addPoint(
         new ExploringPoint(Spell.MANIFAUL, Psowilo, XX * 14, YPOS1)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0}, new int[]{0, 0, 3, 0, 0, 0, 3, 4, 0, 0, 0, 0}, 3)
      );
      ExploringPoint Ptenebrum = field.addPoint(
         new ExploringPoint(Spell.TENEBRUM, Psowilo, XX * 16, YPOS1)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, -1, -1, 0, 0, 1, 0, 1, 0, 0, 0, 0}, new int[]{0, 1, 1, 0, 0, 3, 0, 5, 0, 0, 2, 0}, 3)
      );
      ExploringPoint PUnlockFrozen = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 6, Puruz, ExploringPointType.INTERMEDIATE, XX * 16, YPOS3)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2}, new int[]{2, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2}, 4)
      );
      ExploringPoint Pradium = field.addPoint(
         new ExploringPoint(Spell.RADIUM, PUnlockFrozen, XX * 17, YPOS1)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 0, -1, 1, 0, 0, 1, 0, -1, 0, 0, 0}, new int[]{3, 0, 2, 2, 1, 0, 4, 3, 2, 0, 0, 0}, 4)
      );
      ExploringPoint Phagalaz = field.addPoint(
         new ExploringPoint(Spell.HAGALAZ, PUnlockFrozen, XX * 17, YPOS2)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0}, new int[]{0, 0, 0, 4, 0, 0, 2, 2, 2, 0, 0, 0}, 3)
      );
      ExploringPoint PNextFrozen = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 7, PUnlockFrozen, ExploringPointType.INTERMEDIATE, XX * 18, YPOS3)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2}, new int[]{2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2}, 4)
      );
      ExploringPoint Pisa = field.addPoint(
         new ExploringPoint(Spell.ISA, PUnlockFrozen, XX * 17, YPOS4)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 0, 1, -1, 0, 1, 0, 0, 0, 0, 0, 0}, new int[]{0, 0, 4, 2, 0, 4, 0, 0, 0, 0, 0, 0}, 3)
      );
      ExploringPoint Phiems = field.addPoint(
         new ExploringPoint(Spell.HIEMS, PUnlockFrozen, XX * 17, YPOS5)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, -1, 1, 1, 0, 1, -2, -2, -1, 0, -1, -1}, new int[]{0, 2, 3, 3, 0, 5, 2, 1, 2, 0, 1, 1}, 4)
      );
      ExploringPoint Pturisaz = field.addPoint(
         new ExploringPoint(Spell.TURISAZ, PNextFrozen, XX * 19, YPOS2)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 2, -2, 0, -1, 0, 0, 0, 0, 1, -1, 0}, new int[]{0, 5, 3, 0, 2, 0, 0, 0, 0, 4, 1, 0}, 3)
      );
      ExploringPoint Psequor = field.addPoint(
         new ExploringPoint(Spell.SEQUOR, PNextFrozen, XX * 19, YPOS4)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-2, 0, 0, 1, -1, 0, -1, -2, -1, -1, 0, 0}, new int[]{4, 0, 4, 2, 1, 0, 1, 2, 3, 1, 0, 3}, 4)
      );
      ExploringPoint PNextDungeon = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 8, PNextFrozen, ExploringPointType.INTERMEDIATE, XX * 20, YPOS3)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2}, new int[]{2, 3, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2}, 5)
      );
      ExploringPoint Ptridez = field.addPoint(
         new ExploringPoint(Spell.TRIDEZ, PNextDungeon, XX * 21, YPOS1)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-2, 1, 1, 0, 0, 0, -2, 0, 0, 1, -1, 0}, new int[]{1, 3, 3, 2, 0, 0, 2, 0, 0, 3, 1, 0}, 4)
      );
      ExploringPoint Pstilla = field.addPoint(
         new ExploringPoint(Spell.STILLA, PNextDungeon, XX * 21, YPOS2)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, -1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new int[]{0, 3, 5, 2, 1, 1, 0, 1, 0, 0, 1, 0}, 4)
      );
      ExploringPoint Pstatera = field.addPoint(
         new ExploringPoint(Spell.STATERA, PNextDungeon, XX * 21, YPOS3)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 1, 0, 0, 0, 1, 1, 0, 1, 0, -2, 0}, new int[]{0, 4, 2, 0, 0, 3, 3, 0, 2, 0, 2, 0}, 3)
      );
      ExploringPoint Ppolupus = field.addPoint(
         new ExploringPoint(Spell.POLUPUS, PNextDungeon, XX * 21, YPOS4)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 0, 1, 0, 1, -1, 0, 0, -1, -1, -1, 1}, new int[]{0, 0, 4, 0, 2, 1, 0, 0, 1, 1, 1, 5}, 5)
      );
      ExploringPoint Pfluctus = field.addPoint(
         new ExploringPoint(Spell.FLUCTUS, PNextDungeon, XX * 21, YPOS5)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 0, 1, 1, 0, 0, -1, 0, 0, 0, 0, 0}, new int[]{0, 0, 4, 4, 0, 0, 2, 4, 0, 0, 0, 0}, 4)
      );
      ExploringPoint PNextAqua = field.addPoint(
         new ExploringPoint(nbtkeyNonRunes1, 9, Pstatera, ExploringPointType.INTERMEDIATE, XX * 22, YPOS3)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2}, new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}, 6)
      );
      ExploringPoint Ptiwaz = field.addPoint(
         new ExploringPoint(Spell.TIWAZ, PNextAqua, XX * 23, YPOS2)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{-1, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0}, new int[]{1, 0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0}, 3)
      );
      ExploringPoint Ptonitruum = field.addPoint(
         new ExploringPoint(Spell.TONITRUUM, PNextAqua, XX * 23, YPOS3)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{0, 0, -1, -1, 0, 0, 1, 0, 0, 0, 0, 0}, new int[]{0, 0, 2, 3, 0, 1, 5, 2, 0, 2, 2, 0}, 4)
      );
      ExploringPoint Pcaelum = field.addPoint(
         new ExploringPoint(Spell.CAELUM, PNextAqua, XX * 23, YPOS4)
            .setIfExplored(exploringNbtTag)
            .setupRTInventoryGen(new int[]{1, 0, 0, 1, 0, -1, 0, 0, 0, 0, 0, 0}, new int[]{1, 3, 0, 5, 0, 2, 1, 2, 2, 0, 0, 0}, 5)
      );
      return field;
   }

   public ExploringPoint addPoint(ExploringPoint point) {
      this.points.add(point);
      return point;
   }

   public void click(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0) {
         for (int i = 0; i < this.points.size(); i++) {
            ExploringPoint point = this.points.get(i);
            point.onClick(mouseX, mouseY, mouseButton, this.xDisplace);
         }
      }
   }

   @Nullable
   public String getText(int mouseX, int mouseY, NBTTagCompound playerTag) {
      ExploringPoint exploringNow = this.getExploringNow(playerTag);

      for (int i = 0; i < this.points.size(); i++) {
         ExploringPoint point = this.points.get(i);
         String text = point.getText(mouseX, mouseY, this.xDisplace, point == exploringNow, exploringNow != null);
         if (text != null) {
            return text;
         }
      }

      return null;
   }

   @SideOnly(Side.CLIENT)
   public void renderExploringField(GUIResearchTable gui, int renderx, int rendery, float partialTicks, NBTTagCompound playerTag) {
      ExploringPoint exploringNow = this.getExploringNow(playerTag);

      for (int i = 0; i < this.points.size(); i++) {
         ExploringPoint point = this.points.get(i);
         int x = renderx + this.xDisplace + point.posX;
         int y = rendery + point.posY;
         ShardType shardType = ShardType.byId(point.rune);
         float r = 1.0F;
         float g = 1.0F;
         float b = 1.0F;
         if (shardType != null) {
            r = shardType.colorR;
            g = shardType.colorG;
            b = shardType.colorB;
         }

         if (point.rune != 8) {
            AbstractMobModel.alphaGlow();
         }

         if (point.isOneRequirementExplored()) {
            if (point.requirement != null) {
               for (int j = 0; j < point.requirement.length; j++) {
                  if (point.requirement[j].explored) {
                     int rx = renderx + this.xDisplace + point.requirement[j].posX;
                     int ry = rendery + point.requirement[j].posY;
                     gui.drawLine(x + 9.5F, y + 9.5F, rx + 9.5F, ry + 9.5F, r, g, b);
                  }
               }
            }

            if (exploringNow == point) {
               this.drawSelection(gui, x + 9.5F, y + 9.5F, r, g, b);
            }
         }

         if (point.rune != 8) {
            AbstractMobModel.alphaGlowDisable();
         }
      }

      for (int i = 0; i < this.points.size(); i++) {
         ExploringPoint pointx = this.points.get(i);
         if (pointx.type == ExploringPointType.RUNE) {
            int xx = renderx + this.xDisplace + pointx.posX;
            int yx = rendery + pointx.posY;
            gui.mc.getTextureManager().bindTexture(GUIResearchTable.TEXTURE_OVERLAY);
            gui.drawTexturedModalRect(xx, yx, pointx.explored ? 237 : 218, 237, 19, 19);
            Spell spel = Spell.spellsRegistry.get(pointx.rune);
            if (spel != null) {
               spel.renderRune(xx, yx, pointx.explored);
            }
         }

         if (pointx.type == ExploringPointType.TUTORIAL || pointx.type == ExploringPointType.INTERMEDIATE) {
            int xx = renderx + this.xDisplace + pointx.posX;
            int yx = rendery + pointx.posY;
            gui.mc.getTextureManager().bindTexture(GUIResearchTable.TEXTURE_OVERLAY);
            gui.drawTexturedModalRect(xx, yx, (pointx.explored ? 161 : 104) + pointx.nbtBitshift % 3 * 19, 237, 19, 19);
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void drawSelection(GUIResearchTable gui, float x, float y, float r, float g, float b) {
      int time = AnimationTimer.tick;

      for (int i = 0; i < 4; i++) {
         Vec3d vec1 = GetMOP.YawToVec3d(90 * i + time).scale(14.0);
         Vec3d vec2 = GetMOP.YawToVec3d(90 * (i + 1) + time).scale(14.0);
         gui.drawLine(x + (float)vec1.x, y + (float)vec1.z, x + (float)vec2.x, y + (float)vec2.z, r, g, b);
      }

      time = AnimationTimer.tick + 45;

      for (int i = 0; i < 4; i++) {
         Vec3d vec1 = GetMOP.YawToVec3d(90 * i + time).scale(14.0);
         Vec3d vec2 = GetMOP.YawToVec3d(90 * (i + 1) + time).scale(14.0);
         gui.drawLine(x + (float)vec1.x, y + (float)vec1.z, x + (float)vec2.x, y + (float)vec2.z, r, g, b);
      }
   }

   public static void setExploredToNbt(NBTTagCompound compound, int nbtKey, int nbtBitshift, boolean explored) {
      String key = nbtKey + "";
      if (!compound.hasKey(key, 3)) {
         compound.setInteger(key, 0);
      }

      int data = compound.getInteger(key);
      int bitshift = 1 << nbtBitshift;
      if (explored) {
         data |= bitshift;
      } else {
         data &= ~bitshift;
      }

      compound.setInteger(key, data);
   }

   public static boolean isExplored(NBTTagCompound compound, ExploringPoint point) {
      String key = point.nbtKey + "";
      if (compound.hasKey(key, 3)) {
         int data = compound.getInteger(key);
         int bitshift = 1 << point.nbtBitshift;
         return (data & bitshift) > 0;
      } else {
         return false;
      }
   }

   public static boolean isExplored(NBTTagCompound compound, Spell spell) {
      String key = spell.nbtKey + "";
      if (compound.hasKey(key, 3)) {
         int data = compound.getInteger(key);
         int bitshift = 1 << spell.nbtBitshift;
         return (data & bitshift) > 0;
      } else {
         return false;
      }
   }

   @Nullable
   public ExploringPoint getExploringNow(NBTTagCompound compound) {
      if (compound != null && compound.hasKey("exploringShift", 3) && compound.hasKey("exploringKey", 3)) {
         int shift = compound.getInteger("exploringShift");
         int key = compound.getInteger("exploringKey");
         if (key < 0) {
            return null;
         }

         for (int i = 0; i < this.points.size(); i++) {
            ExploringPoint point = this.points.get(i);
            if (point.nbtBitshift == shift && point.nbtKey == key) {
               return point;
            }
         }
      }

      return null;
   }

   @Nullable
   public ExploringPoint getExploringPoint(int nbtKey, int nbtBitshift) {
      for (int i = 0; i < this.points.size(); i++) {
         ExploringPoint point = this.points.get(i);
         if (point.nbtBitshift == nbtBitshift && point.nbtKey == nbtKey) {
            return point;
         }
      }

      return null;
   }

   public static int getBitshift(NBTTagCompound compound) {
      return compound != null && compound.hasKey("exploringShift", 3) ? compound.getInteger("exploringShift") : 0;
   }

   public static int getKey(NBTTagCompound compound) {
      return compound != null && compound.hasKey("exploringKey", 3) ? compound.getInteger("exploringKey") : -1;
   }

   public static NBTTagCompound getExploringTagCompound(EntityPlayer player) {
      NBTTagCompound entityData = player.getEntityData();
      if (!entityData.hasKey("arpg_spells", 10)) {
         entityData.setTag("arpg_spells", new NBTTagCompound());
      }

      return entityData.getCompoundTag("arpg_spells");
   }

   public static void setExploringNow(NBTTagCompound compound, int nbtKey, int nbtBitshift) {
      compound.setInteger("exploringKey", nbtKey);
      compound.setInteger("exploringShift", nbtBitshift);
   }

   public static void SendExploringInfoToClient(EntityPlayerMP player) {
      PacketExploringToClient packet = new PacketExploringToClient();
      packet.writeargs(getExploringTagCompound(player));
      PacketHandler.sendTo(packet, player);
   }

   public static void SendSelectedExploringToServer(int nbtKey, int nbtBitshift) {
      PacketExploringToServer packet = new PacketExploringToServer();
      packet.writeints(nbtKey, nbtBitshift);
      PacketHandler.NETWORK.sendToServer(packet);
   }

   public static void onResearchWinOnServer(TerraformingResearchPuzzle puzzle, EntityPlayer player) {
      if (player != null) {
         setExploredToNbt(getExploringTagCompound(player), puzzle.whatResearchKey, puzzle.whatResearchBitshift, true);
         SendSelectedExploringToServer(-1, 0);
         setExploringNow(getExploringTagCompound(player), -1, 0);
      }
   }

   public static void onPlayerClone(Clone event) {
      EntityPlayer originalplayer = event.getOriginal();
      EntityPlayer newplayer = event.getEntityPlayer();
      NBTTagCompound tagCompound = getExploringTagCompound(originalplayer);
      newplayer.getEntityData().setTag("arpg_spells", tagCompound.copy());
   }

   public static class ExploringPoint {
      public int nbtKey;
      public int nbtBitshift;
      @Nullable
      public ExploringPoint[] requirement;
      public ExploringPointType type;
      public int posX;
      public int posY;
      public int rune;
      public int tutorial;
      public boolean explored;
      public int[] rtInventoryMin;
      public int[] rtInventoryMax;
      public int rtMaxDiversity;
      public int rtShardType = 0;

      public ExploringPoint(
         int nbtKey, int nbtBitshift, @Nullable ExploringPoint requirement, ExploringPointType type, int posX, int posY
      ) {
         this.nbtKey = nbtKey;
         this.nbtBitshift = nbtBitshift;
         this.requirement = requirement == null ? null : new ExploringPoint[]{requirement};
         this.type = type;
         this.posX = posX;
         this.posY = posY;
      }

      public ExploringPoint(Spell spell, @Nullable ExploringPoint requirement, int posX, int posY) {
         this.nbtKey = spell.nbtKey;
         this.nbtBitshift = spell.nbtBitshift;
         this.requirement = requirement == null ? null : new ExploringPoint[]{requirement};
         this.type = ExploringPointType.RUNE;
         this.posX = posX;
         this.posY = posY;
         this.rune = spell.id;
      }

      public ExploringPoint(
         int nbtKey, int nbtBitshift, ExploringPointType type, int posX, int posY, int rune, ExploringPoint... requirements
      ) {
         this.nbtKey = nbtKey;
         this.nbtBitshift = nbtBitshift;
         this.requirement = requirements != null && requirements.length != 0 ? requirements : null;
         this.type = type;
         this.posX = posX;
         this.posY = posY;
         this.rune = rune;
      }

      public boolean isOneRequirementExplored() {
         if (this.requirement == null) {
            return true;
         } else {
            for (int i = 0; i < this.requirement.length; i++) {
               if (this.requirement[i] != null && this.requirement[i].explored) {
                  return true;
               }
            }

            return false;
         }
      }

      public ExploringPoint setIfExplored(NBTTagCompound exploringTagCompound) {
         this.explored = ExploringField.isExplored(exploringTagCompound, this);
         return this;
      }

      public void onClick(int mouseX, int mouseY, int mouseButton, int xDisplace) {
         int xx = this.posX + xDisplace;
         if (mouseX > xx && mouseX < xx + 19 && mouseY > this.posY && mouseY < this.posY + 19 && !this.explored && this.isOneRequirementExplored()) {
            ExploringField.SendSelectedExploringToServer(this.nbtKey, this.nbtBitshift);
            if (Minecraft.getMinecraft().player != null) {
               ExploringField.setExploringNow(ExploringField.getExploringTagCompound(Minecraft.getMinecraft().player), this.nbtKey, this.nbtBitshift);
            }
         }
      }

      @Nullable
      public String getText(int mouseX, int mouseY, int xDisplace, boolean researchingNow, boolean hasAnyResearch) {
         int xx = this.posX + xDisplace;
         if (mouseX > xx && mouseX < xx + 19 && mouseY > this.posY && mouseY < this.posY + 19) {
            String text = "";
            if (!this.explored && !this.isOneRequirementExplored()) {
               return "?";
            } else {
               if (this.type == ExploringPointType.RUNE) {
                  text = Spell.spellsRegistry.get(this.rune).name;
               } else if (this.nbtKey == 20 && this.nbtBitshift == 0) {
                  text = "Initial Research";
               } else {
                  text = this.type.unlocalizedText;
               }

               if (researchingNow) {
                  text = text + ":This is being researched now:Go to the research table to continue";
               } else if (hasAnyResearch && !this.explored) {
                  text = text + ":Selecting this will reset the current research";
               }

               return text;
            }
         } else {
            return null;
         }
      }

      public void generateRTInventory(Random rand, int[] inventory) {
         int amountDiversity = 0;
         if (this.rtInventoryMin != null && this.rtInventoryMax != null) {
            List<Integer> array12 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            Collections.shuffle(array12, rand);

            for (int i = 0; i < 12; i++) {
               int ii = array12.get(i);
               int min = this.rtInventoryMin[ii - 1];
               int max = this.rtInventoryMax[ii - 1];
               inventory[ii] = Math.max(min + rand.nextInt(max - min + 1), 0);
               if (inventory[i] > 0) {
                  amountDiversity++;
               }

               if (amountDiversity >= this.rtMaxDiversity) {
                  break;
               }
            }
         }

         if (this.rtShardType != 0 && inventory[this.rtShardType] == 0) {
            int minx = this.rtInventoryMin[this.rtShardType - 1];
            int maxx = this.rtInventoryMax[this.rtShardType - 1];
            inventory[this.rtShardType] = Math.max(minx + rand.nextInt(maxx - minx + 1), 0);
         }

         if (amountDiversity == 0) {
            List<Integer> array12 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            Collections.shuffle(array12, rand);

            for (int i = 0; i < 2; i++) {
               int iix = array12.get(i);
               int minx = 1;
               int maxx = 3;
               inventory[iix] = Math.max(minx + rand.nextInt(maxx - minx + 1), 0);
            }
         }
      }

      public ExploringPoint setupRTInventoryGen(int shardTypeId, int maxDiversity) {
         int[] ints1 = new int[]{-2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2};
         int[] ints2 = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
         ints1[shardTypeId - 1] = 3;
         ints2[shardTypeId - 1] = 4;
         this.rtShardType = shardTypeId;
         return this.setupRTInventoryGen(ints1, ints2, maxDiversity);
      }

      public ExploringPoint setupRTInventoryGen(int[] min, int[] max, int maxDiversity) {
         this.rtInventoryMin = min;
         this.rtInventoryMax = max;
         this.rtMaxDiversity = maxDiversity;
         return this;
      }
   }

   public static enum ExploringPointType {
      RUNE(""),
      INTERMEDIATE("Interim Research"),
      TUTORIAL("Early Research"),
      BONUS("Bonus");

      public String unlocalizedText;

      private ExploringPointType(String unlocalizedText) {
         this.unlocalizedText = unlocalizedText;
      }
   }
}
