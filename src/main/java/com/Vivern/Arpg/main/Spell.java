//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.container.GUIResearchTable;
import com.Vivern.Arpg.elements.models.AbstractMobModel;
import com.Vivern.Arpg.recipes.ExploringField;
import com.Vivern.Arpg.recipes.WriteGraph;
import com.Vivern.Arpg.tileentity.TileResearchTable;
import com.Vivern.Arpg.tileentity.WriteBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Spell {
   public static HashMap<Integer, Spell> spellsRegistry = new HashMap<>();
   public static int startId = 1;
   public static final int resampleWidth = 20;
   public static final int resampleHeight = 30;
   public static int avoidRadiusAdd = 0;
   public static WriteGraph graphSowilo = new WriteGraph(new Vec2i(30, 10), new Vec2i(11, 37), new Vec2i(38, 39), new Vec2i(19, 66))
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(2, 3))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphFrigidus = new WriteGraph(
         new Vec2i(25, 37), new Vec2i(25, 16), new Vec2i(43, 27), new Vec2i(43, 48), new Vec2i(25, 58), new Vec2i(7, 48), new Vec2i(7, 27)
      )
      .addLinks(new Vec2i(0, 1), new Vec2i(0, 2), new Vec2i(0, 3), new Vec2i(0, 4), new Vec2i(0, 5), new Vec2i(0, 6))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd)
      .addsize(0, -1);
   public static WriteGraph graphIgnis = new WriteGraph(
         new Vec2i(15, 22),
         new Vec2i(25, 10),
         new Vec2i(35, 22),
         new Vec2i(15, 48),
         new Vec2i(25, 54),
         new Vec2i(35, 48),
         new Vec2i(9, 54),
         new Vec2i(25, 63),
         new Vec2i(41, 54)
      )
      .addLinks(new Vec2i(0, 3), new Vec2i(1, 4), new Vec2i(2, 5), new Vec2i(6, 7), new Vec2i(7, 8))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd)
      .addsize(3, -1)
      .addsize(4, -1)
      .addsize(5, -1);
   public static WriteGraph graphTerra = new WriteGraph(new Vec2i(6, 24), new Vec2i(44, 24), new Vec2i(25, 56), new Vec2i(13, 36), new Vec2i(40, 36))
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(2, 3), new Vec2i(3, 4))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphAqua = new WriteGraph(
         new Vec2i(24, 63),
         new Vec2i(24, 12),
         new Vec2i(40, 21),
         new Vec2i(10, 36),
         new Vec2i(12, 40),
         new Vec2i(15, 43),
         new Vec2i(20, 44),
         new Vec2i(28, 43),
         new Vec2i(32, 44),
         new Vec2i(36, 46),
         new Vec2i(38, 50)
      )
      .addLinks(
         new Vec2i(0, 1),
         new Vec2i(1, 2),
         new Vec2i(3, 4),
         new Vec2i(4, 5),
         new Vec2i(5, 6),
         new Vec2i(6, 7),
         new Vec2i(7, 8),
         new Vec2i(8, 9),
         new Vec2i(9, 10)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphAera = new WriteGraph(
         new Vec2i(17, 12),
         new Vec2i(23, 15),
         new Vec2i(26, 20),
         new Vec2i(28, 26),
         new Vec2i(26, 33),
         new Vec2i(21, 38),
         new Vec2i(18, 45),
         new Vec2i(18, 52),
         new Vec2i(20, 58),
         new Vec2i(26, 62),
         new Vec2i(33, 62),
         new Vec2i(30, 50)
      )
      .addLinks(
         new Vec2i(0, 1),
         new Vec2i(1, 2),
         new Vec2i(2, 3),
         new Vec2i(3, 4),
         new Vec2i(4, 5),
         new Vec2i(5, 6),
         new Vec2i(6, 7),
         new Vec2i(7, 8),
         new Vec2i(8, 9),
         new Vec2i(9, 10)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphEgo = new WriteGraph(
         new Vec2i(7, 14), new Vec2i(18, 25), new Vec2i(18, 63), new Vec2i(43, 14), new Vec2i(32, 25), new Vec2i(32, 63)
      )
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(3, 4), new Vec2i(4, 5))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphFulgur = new WriteGraph(
         new Vec2i(25, 13), new Vec2i(25, 43), new Vec2i(18, 50), new Vec2i(32, 50), new Vec2i(6, 62), new Vec2i(18, 62), new Vec2i(32, 62), new Vec2i(44, 62)
      )
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(1, 3), new Vec2i(2, 4), new Vec2i(3, 7), new Vec2i(2, 5), new Vec2i(3, 6))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphVanitas = new WriteGraph(
         new Vec2i(24, 58),
         new Vec2i(16, 56),
         new Vec2i(10, 52),
         new Vec2i(6, 46),
         new Vec2i(4, 38),
         new Vec2i(6, 30),
         new Vec2i(10, 24),
         new Vec2i(16, 20),
         new Vec2i(24, 18),
         new Vec2i(32, 20),
         new Vec2i(38, 24),
         new Vec2i(42, 30),
         new Vec2i(44, 38),
         new Vec2i(42, 46),
         new Vec2i(38, 52),
         new Vec2i(32, 56),
         new Vec2i(24, 48),
         new Vec2i(16, 44),
         new Vec2i(14, 36),
         new Vec2i(20, 29),
         new Vec2i(28, 29),
         new Vec2i(34, 36),
         new Vec2i(32, 44)
      )
      .addLinks(
         new Vec2i(0, 1),
         new Vec2i(1, 2),
         new Vec2i(2, 3),
         new Vec2i(3, 4),
         new Vec2i(4, 5),
         new Vec2i(5, 6),
         new Vec2i(6, 7),
         new Vec2i(7, 8),
         new Vec2i(8, 9),
         new Vec2i(9, 10),
         new Vec2i(10, 11),
         new Vec2i(11, 12),
         new Vec2i(12, 13),
         new Vec2i(13, 14),
         new Vec2i(14, 15),
         new Vec2i(15, 0),
         new Vec2i(16, 17),
         new Vec2i(17, 18),
         new Vec2i(18, 19),
         new Vec2i(19, 20),
         new Vec2i(20, 21),
         new Vec2i(21, 22),
         new Vec2i(22, 16)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphVoluptas = new WriteGraph(new Vec2i(19, 63), new Vec2i(19, 12), new Vec2i(39, 28), new Vec2i(19, 37))
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(2, 3))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphDolor = new WriteGraph(
         new Vec2i(12, 12), new Vec2i(12, 43), new Vec2i(12, 63), new Vec2i(24, 12), new Vec2i(24, 43), new Vec2i(24, 63), new Vec2i(32, 63), new Vec2i(44, 63)
      )
      .addLinks(new Vec2i(0, 2), new Vec2i(1, 6), new Vec2i(3, 5), new Vec2i(4, 7))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphMors = new WriteGraph(
         new Vec2i(6, 12), new Vec2i(25, 12), new Vec2i(44, 12), new Vec2i(16, 21), new Vec2i(34, 21), new Vec2i(25, 63), new Vec2i(17, 51), new Vec2i(32, 36)
      )
      .addLinks(new Vec2i(0, 3), new Vec2i(3, 1), new Vec2i(1, 4), new Vec2i(4, 2), new Vec2i(1, 5), new Vec2i(6, 7))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphVita = new WriteGraph(
         new Vec2i(11, 12), new Vec2i(38, 12), new Vec2i(12, 37), new Vec2i(37, 37), new Vec2i(11, 63), new Vec2i(38, 63)
      )
      .addLinks(new Vec2i(0, 3), new Vec2i(3, 4), new Vec2i(1, 2), new Vec2i(2, 5))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphIra = new WriteGraph(
         new Vec2i(22, 8),
         new Vec2i(14, 20),
         new Vec2i(14, 34),
         new Vec2i(25, 45),
         new Vec2i(25, 68),
         new Vec2i(35, 39),
         new Vec2i(35, 31),
         new Vec2i(25, 33),
         new Vec2i(25, 26),
         new Vec2i(20, 51),
         new Vec2i(30, 61)
      )
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(2, 3), new Vec2i(3, 4), new Vec2i(3, 5), new Vec2i(5, 6), new Vec2i(7, 8), new Vec2i(9, 10))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd)
      .addsize(3, -1);
   public static WriteGraph graphAnima = new WriteGraph(
         new Vec2i(25, 38),
         new Vec2i(13, 7),
         new Vec2i(18, 13),
         new Vec2i(25, 15),
         new Vec2i(32, 13),
         new Vec2i(37, 7),
         new Vec2i(25, 27),
         new Vec2i(18, 29),
         new Vec2i(14, 35),
         new Vec2i(15, 43),
         new Vec2i(34, 32),
         new Vec2i(36, 39),
         new Vec2i(32, 46),
         new Vec2i(25, 48),
         new Vec2i(25, 68)
      )
      .addLinks(
         new Vec2i(1, 2),
         new Vec2i(2, 3),
         new Vec2i(3, 4),
         new Vec2i(4, 5),
         new Vec2i(3, 6),
         new Vec2i(6, 7),
         new Vec2i(7, 8),
         new Vec2i(8, 9),
         new Vec2i(10, 11),
         new Vec2i(11, 12),
         new Vec2i(12, 13),
         new Vec2i(13, 14)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphAlgiz = new WriteGraph(new Vec2i(5, 12), new Vec2i(25, 12), new Vec2i(45, 12), new Vec2i(25, 31), new Vec2i(25, 63))
      .addLinks(new Vec2i(0, 3), new Vec2i(1, 3), new Vec2i(2, 3), new Vec2i(3, 4))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphUruz = new WriteGraph(new Vec2i(14, 63), new Vec2i(13, 15), new Vec2i(38, 21), new Vec2i(37, 63))
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(2, 3))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphPeccatum = new WriteGraph(
         new Vec2i(9, 44),
         new Vec2i(12, 38),
         new Vec2i(18, 33),
         new Vec2i(25, 31),
         new Vec2i(32, 33),
         new Vec2i(38, 38),
         new Vec2i(41, 44),
         new Vec2i(9, 31),
         new Vec2i(18, 42),
         new Vec2i(25, 44),
         new Vec2i(32, 42),
         new Vec2i(41, 31),
         new Vec2i(25, 12),
         new Vec2i(25, 63)
      )
      .addLinks(
         new Vec2i(0, 1),
         new Vec2i(1, 2),
         new Vec2i(2, 3),
         new Vec2i(3, 4),
         new Vec2i(4, 5),
         new Vec2i(5, 6),
         new Vec2i(7, 1),
         new Vec2i(1, 8),
         new Vec2i(8, 9),
         new Vec2i(9, 10),
         new Vec2i(10, 5),
         new Vec2i(5, 11),
         new Vec2i(12, 3),
         new Vec2i(9, 13)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphExpulsio = new WriteGraph(
         new Vec2i(38, 25), new Vec2i(25, 12), new Vec2i(12, 25), new Vec2i(25, 37), new Vec2i(12, 50), new Vec2i(25, 63), new Vec2i(38, 50)
      )
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(2, 3), new Vec2i(3, 4), new Vec2i(4, 5), new Vec2i(5, 6))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphHiems = new WriteGraph(
         new Vec2i(10, 7),
         new Vec2i(40, 32),
         new Vec2i(10, 56),
         new Vec2i(40, 7),
         new Vec2i(10, 32),
         new Vec2i(40, 56),
         new Vec2i(25, 7),
         new Vec2i(25, 68),
         new Vec2i(10, 68),
         new Vec2i(25, 56),
         new Vec2i(40, 68)
      )
      .addLinks(new Vec2i(0, 1), new Vec2i(1, 2), new Vec2i(3, 4), new Vec2i(4, 5), new Vec2i(6, 7), new Vec2i(8, 9), new Vec2i(9, 10))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphRadium = new WriteGraph(
         new Vec2i(5, 27),
         new Vec2i(9, 38),
         new Vec2i(16, 43),
         new Vec2i(28, 43),
         new Vec2i(26, 60),
         new Vec2i(34, 51),
         new Vec2i(35, 42),
         new Vec2i(28, 32),
         new Vec2i(45, 26),
         new Vec2i(33, 23),
         new Vec2i(24, 27),
         new Vec2i(19, 38),
         new Vec2i(25, 37)
      )
      .addLinks(
         new Vec2i(0, 1),
         new Vec2i(1, 2),
         new Vec2i(2, 3),
         new Vec2i(4, 5),
         new Vec2i(5, 6),
         new Vec2i(6, 7),
         new Vec2i(8, 9),
         new Vec2i(9, 10),
         new Vec2i(10, 11)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphIsa = new WriteGraph(new Vec2i(22, 17), new Vec2i(28, 8), new Vec2i(25, 23), new Vec2i(25, 68))
      .addLinks(new Vec2i(0, 1), new Vec2i(2, 3))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphHagalaz = new WriteGraph(
         new Vec2i(10, 12), new Vec2i(10, 63), new Vec2i(10, 24), new Vec2i(40, 37), new Vec2i(40, 12), new Vec2i(40, 63)
      )
      .addLinks(new Vec2i(0, 1), new Vec2i(2, 3), new Vec2i(4, 5))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphTurisaz = new WriteGraph(new Vec2i(17, 13), new Vec2i(17, 62), new Vec2i(17, 50), new Vec2i(17, 26), new Vec2i(34, 38))
      .addLinks(new Vec2i(0, 3), new Vec2i(3, 2), new Vec2i(2, 1), new Vec2i(2, 4), new Vec2i(4, 3))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphSequor = new WriteGraph(
         new Vec2i(25, 51),
         new Vec2i(25, 39),
         new Vec2i(25, 62),
         new Vec2i(25, 26),
         new Vec2i(25, 13),
         new Vec2i(33, 42),
         new Vec2i(33, 60),
         new Vec2i(38, 51),
         new Vec2i(11, 39),
         new Vec2i(15, 29),
         new Vec2i(15, 50)
      )
      .addLinks(
         new Vec2i(1, 5),
         new Vec2i(5, 7),
         new Vec2i(7, 6),
         new Vec2i(6, 2),
         new Vec2i(3, 9),
         new Vec2i(9, 8),
         new Vec2i(0, 10),
         new Vec2i(10, 8),
         new Vec2i(3, 4)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphTridez = new WriteGraph(
         new Vec2i(25, 13),
         new Vec2i(25, 64),
         new Vec2i(25, 47),
         new Vec2i(41, 47),
         new Vec2i(10, 47),
         new Vec2i(41, 13),
         new Vec2i(9, 13),
         new Vec2i(35, 26),
         new Vec2i(15, 25)
      )
      .addLinks(new Vec2i(4, 8), new Vec2i(8, 6), new Vec2i(7, 5), new Vec2i(7, 3), new Vec2i(4, 2), new Vec2i(2, 3), new Vec2i(1, 2), new Vec2i(2, 0))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphStilla = new WriteGraph(
         new Vec2i(25, 62),
         new Vec2i(25, 49),
         new Vec2i(32, 56),
         new Vec2i(18, 56),
         new Vec2i(41, 37),
         new Vec2i(49, 30),
         new Vec2i(34, 30),
         new Vec2i(41, 23),
         new Vec2i(16, 30),
         new Vec2i(2, 30),
         new Vec2i(9, 23),
         new Vec2i(9, 37)
      )
      .addLinks(
         new Vec2i(3, 1),
         new Vec2i(1, 2),
         new Vec2i(2, 0),
         new Vec2i(0, 3),
         new Vec2i(4, 5),
         new Vec2i(5, 7),
         new Vec2i(7, 6),
         new Vec2i(6, 4),
         new Vec2i(11, 8),
         new Vec2i(8, 10),
         new Vec2i(10, 9),
         new Vec2i(9, 11),
         new Vec2i(1, 8),
         new Vec2i(1, 6)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphPolupus = new WriteGraph(
         new Vec2i(25, 39),
         new Vec2i(17, 34),
         new Vec2i(17, 44),
         new Vec2i(33, 34),
         new Vec2i(33, 44),
         new Vec2i(12, 26),
         new Vec2i(14, 17),
         new Vec2i(18, 10),
         new Vec2i(14, 4),
         new Vec2i(30, 10),
         new Vec2i(34, 17),
         new Vec2i(38, 26),
         new Vec2i(34, 4),
         new Vec2i(12, 52),
         new Vec2i(14, 60),
         new Vec2i(19, 65),
         new Vec2i(14, 71),
         new Vec2i(30, 65),
         new Vec2i(35, 60),
         new Vec2i(38, 52),
         new Vec2i(35, 71),
         new Vec2i(9, 4),
         new Vec2i(5, 8),
         new Vec2i(40, 4),
         new Vec2i(43, 8),
         new Vec2i(9, 72),
         new Vec2i(6, 68),
         new Vec2i(40, 71),
         new Vec2i(44, 68),
         new Vec2i(4, 39),
         new Vec2i(46, 39),
         new Vec2i(25, 28),
         new Vec2i(25, 50)
      )
      .addLinks(
         new Vec2i(22, 21),
         new Vec2i(21, 8),
         new Vec2i(8, 7),
         new Vec2i(7, 6),
         new Vec2i(6, 5),
         new Vec2i(5, 1),
         new Vec2i(1, 0),
         new Vec2i(0, 3),
         new Vec2i(3, 11),
         new Vec2i(11, 10),
         new Vec2i(10, 9),
         new Vec2i(9, 12),
         new Vec2i(12, 23),
         new Vec2i(23, 24),
         new Vec2i(0, 2),
         new Vec2i(2, 13),
         new Vec2i(13, 14),
         new Vec2i(15, 14),
         new Vec2i(15, 16),
         new Vec2i(16, 25),
         new Vec2i(25, 26),
         new Vec2i(0, 4),
         new Vec2i(4, 19),
         new Vec2i(19, 18),
         new Vec2i(18, 17),
         new Vec2i(17, 20),
         new Vec2i(20, 27),
         new Vec2i(27, 28),
         new Vec2i(29, 0),
         new Vec2i(0, 30),
         new Vec2i(31, 0),
         new Vec2i(0, 32)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphFluctus = new WriteGraph(
         new Vec2i(10, 61),
         new Vec2i(25, 39),
         new Vec2i(41, 14),
         new Vec2i(4, 39),
         new Vec2i(15, 28),
         new Vec2i(47, 38),
         new Vec2i(37, 49),
         new Vec2i(7, 30),
         new Vec2i(22, 30),
         new Vec2i(30, 47),
         new Vec2i(44, 47)
      )
      .addLinks(
         new Vec2i(0, 1),
         new Vec2i(1, 2),
         new Vec2i(3, 7),
         new Vec2i(7, 4),
         new Vec2i(4, 8),
         new Vec2i(8, 1),
         new Vec2i(1, 9),
         new Vec2i(9, 6),
         new Vec2i(6, 10),
         new Vec2i(10, 5)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphStatera = new WriteGraph(
         new Vec2i(25, 14), new Vec2i(25, 63), new Vec2i(25, 31), new Vec2i(25, 47), new Vec2i(9, 31), new Vec2i(41, 47)
      )
      .addLinks(new Vec2i(0, 4), new Vec2i(4, 3), new Vec2i(2, 5), new Vec2i(5, 1))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd + 2);
   public static WriteGraph graphTiwaz = new WriteGraph(new Vec2i(25, 13), new Vec2i(25, 62), new Vec2i(8, 31), new Vec2i(42, 31))
      .addLinks(new Vec2i(0, 2), new Vec2i(1, 0), new Vec2i(0, 3))
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphTonitruum = new WriteGraph(
         new Vec2i(25, 39),
         new Vec2i(3, 39),
         new Vec2i(47, 39),
         new Vec2i(5, 29),
         new Vec2i(45, 49),
         new Vec2i(13, 19),
         new Vec2i(36, 19),
         new Vec2i(13, 60),
         new Vec2i(37, 60),
         new Vec2i(43, 26),
         new Vec2i(23, 15),
         new Vec2i(6, 52),
         new Vec2i(27, 63)
      )
      .addLinks(
         new Vec2i(0, 5),
         new Vec2i(0, 1),
         new Vec2i(0, 6),
         new Vec2i(0, 2),
         new Vec2i(0, 7),
         new Vec2i(0, 8),
         new Vec2i(5, 10),
         new Vec2i(6, 9),
         new Vec2i(2, 4),
         new Vec2i(1, 3),
         new Vec2i(7, 11),
         new Vec2i(8, 12)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphCaelum = new WriteGraph(
         new Vec2i(18, 18),
         new Vec2i(32, 18),
         new Vec2i(25, 6),
         new Vec2i(25, 68),
         new Vec2i(25, 16),
         new Vec2i(12, 23),
         new Vec2i(9, 31),
         new Vec2i(38, 23),
         new Vec2i(41, 31),
         new Vec2i(9, 41),
         new Vec2i(41, 42),
         new Vec2i(1, 36),
         new Vec2i(49, 37)
      )
      .addLinks(
         new Vec2i(0, 3),
         new Vec2i(3, 1),
         new Vec2i(1, 2),
         new Vec2i(2, 0),
         new Vec2i(6, 5),
         new Vec2i(5, 0),
         new Vec2i(0, 4),
         new Vec2i(4, 1),
         new Vec2i(1, 7),
         new Vec2i(7, 8),
         new Vec2i(6, 11),
         new Vec2i(11, 9),
         new Vec2i(8, 12),
         new Vec2i(12, 10),
         new Vec2i(9, 6),
         new Vec2i(10, 8)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphDrakkar = new WriteGraph(
         new Vec2i(17, 1),
         new Vec2i(33, 1),
         new Vec2i(9, 10),
         new Vec2i(9, 22),
         new Vec2i(1, 31),
         new Vec2i(17, 15),
         new Vec2i(33, 15),
         new Vec2i(25, 20),
         new Vec2i(1, 61),
         new Vec2i(9, 54),
         new Vec2i(42, 10),
         new Vec2i(42, 22),
         new Vec2i(49, 31),
         new Vec2i(42, 54),
         new Vec2i(49, 61),
         new Vec2i(17, 26),
         new Vec2i(33, 26),
         new Vec2i(25, 31),
         new Vec2i(17, 37),
         new Vec2i(34, 37),
         new Vec2i(17, 72),
         new Vec2i(34, 72)
      )
      .addLinks(
         new Vec2i(3, 9),
         new Vec2i(4, 8),
         new Vec2i(3, 4),
         new Vec2i(2, 3),
         new Vec2i(2, 0),
         new Vec2i(0, 5),
         new Vec2i(5, 7),
         new Vec2i(7, 6),
         new Vec2i(6, 1),
         new Vec2i(13, 11),
         new Vec2i(11, 10),
         new Vec2i(10, 1),
         new Vec2i(11, 12),
         new Vec2i(12, 14),
         new Vec2i(7, 16),
         new Vec2i(7, 15),
         new Vec2i(15, 17),
         new Vec2i(17, 16),
         new Vec2i(17, 18),
         new Vec2i(17, 19),
         new Vec2i(21, 19),
         new Vec2i(20, 18)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphManifaul = new WriteGraph(
         new Vec2i(25, 37),
         new Vec2i(28, 37),
         new Vec2i(30, 34),
         new Vec2i(29, 29),
         new Vec2i(24, 27),
         new Vec2i(17, 30),
         new Vec2i(14, 38),
         new Vec2i(18, 44),
         new Vec2i(28, 47),
         new Vec2i(38, 43),
         new Vec2i(42, 35),
         new Vec2i(42, 24),
         new Vec2i(34, 15),
         new Vec2i(22, 13),
         new Vec2i(9, 17),
         new Vec2i(1, 28),
         new Vec2i(0, 45),
         new Vec2i(10, 60),
         new Vec2i(27, 66),
         new Vec2i(46, 64)
      )
      .addLinks(
         new Vec2i(19, 18),
         new Vec2i(18, 17),
         new Vec2i(17, 16),
         new Vec2i(16, 15),
         new Vec2i(15, 14),
         new Vec2i(14, 13),
         new Vec2i(13, 12),
         new Vec2i(12, 11),
         new Vec2i(11, 10),
         new Vec2i(10, 9),
         new Vec2i(9, 8),
         new Vec2i(8, 7),
         new Vec2i(7, 6),
         new Vec2i(6, 5),
         new Vec2i(5, 4),
         new Vec2i(4, 3),
         new Vec2i(3, 2),
         new Vec2i(2, 1)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static WriteGraph graphTenebrum = new WriteGraph(
         new Vec2i(25, 23),
         new Vec2i(25, 52),
         new Vec2i(25, 38),
         new Vec2i(11, 38),
         new Vec2i(39, 38),
         new Vec2i(9, 21),
         new Vec2i(42, 21),
         new Vec2i(42, 56),
         new Vec2i(9, 56),
         new Vec2i(16, 13),
         new Vec2i(34, 13),
         new Vec2i(18, 65),
         new Vec2i(32, 65)
      )
      .addLinks(
         new Vec2i(3, 2),
         new Vec2i(2, 4),
         new Vec2i(1, 2),
         new Vec2i(2, 0),
         new Vec2i(6, 10),
         new Vec2i(5, 9),
         new Vec2i(5, 2),
         new Vec2i(2, 6),
         new Vec2i(8, 2),
         new Vec2i(2, 7),
         new Vec2i(12, 7),
         new Vec2i(11, 8)
      )
      .calculateAvoidMatrix(50, 75, avoidRadiusAdd);
   public static final String mainTexture = "arpg_spells";
   public static ResourceLocation mainResourceLocation = new ResourceLocation("arpg:textures/arpg_spells.png");
   public static int nbtkey0 = 0;
   public static int nbtkey1 = 1;
   public static Spell IGNIS = new Spell("ignis", nbtkey0, 1).configure(graphIgnis);
   public static Spell TERRA = new Spell("terra", nbtkey0, 2).configure(graphTerra);
   public static Spell AQUA = new Spell("aqua", nbtkey0, 3).configure(graphAqua);
   public static Spell AERA = new Spell("aera", nbtkey0, 4).configure(graphAera);
   public static Spell EGO = new Spell("ego", nbtkey0, 5).configure(graphEgo);
   public static Spell FRIGIDUS = new Spell("frigidus", nbtkey0, 6).configure(graphFrigidus);
   public static Spell FULGUR = new Spell("fulgur", nbtkey0, 7).configure(graphFulgur);
   public static Spell VANITAS = new Spell("vanitas", nbtkey0, 8).configure(graphVanitas);
   public static Spell VOLUPTAS = new Spell("voluptas", nbtkey0, 9).configure(graphVoluptas);
   public static Spell DOLOR = new Spell("dolor", nbtkey0, 10).configure(graphDolor);
   public static Spell MORS = new Spell("mors", nbtkey0, 11).configure(graphMors);
   public static Spell VITA = new Spell("vita", nbtkey0, 12).configure(graphVita);
   public static Spell IRA = new Spell("ira", nbtkey0, 13).configure(graphIra).setcolor(12480329, 14781529);
   public static Spell ANIMA = new Spell("anima", nbtkey0, 14).configure(graphAnima).setcolor(15200700, 12974589);
   public static Spell ALGIZ = new Spell("algiz", nbtkey0, 15).configure(graphAlgiz).setcolor(9752445, 8871213);
   public static Spell URUZ = new Spell("uruz", nbtkey0, 16).configure(graphUruz).setcolor(6567323, 5267888);
   public static Spell PECCATUM = new Spell("peccatum", nbtkey0, 17).configure(graphPeccatum).setcolor(10747963, 8658980);
   public static Spell EXPULSIO = new Spell("expulsio", nbtkey0, 18).configure(graphExpulsio).setcolor(5330525, 2039584);
   public static Spell SOWILO = new Spell("sowilo", nbtkey0, 19).configure(graphSowilo).setcolor(16777121, 16777176);
   public static Spell HIEMS = new Spell("hiems", nbtkey0, 20).configure(graphHiems).setcolor(9950719, 16435570);
   public static Spell RADIUM = new Spell("radium", nbtkey0, 21).configure(graphRadium).setcolor(11330559, 15426220);
   public static Spell ISA = new Spell("isa", nbtkey0, 22).configure(graphIsa).setcolor(13558015, 7901667);
   public static Spell HAGALAZ = new Spell("hagalaz", nbtkey0, 23).configure(graphHagalaz).setcolor(13027786, 15334143);
   public static Spell TURISAZ = new Spell("turisaz", nbtkey0, 24).configure(graphTurisaz).setcolor(13205322, 10265676);
   public static Spell SEQUOR = new Spell("sequor", nbtkey0, 25).configure(graphSequor).setcolor(16771983, 6845951);
   public static Spell TRIDEZ = new Spell("tridez", nbtkey0, 26).configure(graphTridez).setcolor(4181457, 12563199);
   public static Spell STILLA = new Spell("stilla", nbtkey0, 27).configure(graphStilla).setcolor(4951542, 2913720);
   public static Spell POLUPUS = new Spell("polupus", nbtkey0, 28).configure(graphPolupus).setcolor(10643915, 13131164);
   public static Spell FLUCTUS = new Spell("fluctus", nbtkey0, 29).configure(graphFluctus).setcolor(4145823, 2150811);
   public static Spell STATERA = new Spell("statera", nbtkey0, 30).configure(graphStatera).setcolor(16762566, 13621759);
   public static Spell TIWAZ = new Spell("tiwaz", nbtkey1, 0).configure(graphTiwaz).setcolor(16512511, 14736639);
   public static Spell TONITRUUM = new Spell("tonitruum", nbtkey1, 1).configure(graphTonitruum).setcolor(6877684, 10250225);
   public static Spell CAELUM = new Spell("caelum", nbtkey1, 2).configure(graphCaelum).setcolor(2393599, 15909467);
   public static Spell DRAKKAR = new Spell("drakkar", nbtkey1, 3).configure(graphDrakkar).setcolor(5963918, 11213530);
   public static Spell MANIFAUL = new Spell("manifaul", nbtkey1, 4).configure(graphManifaul).setcolor(13847539, 6940345);
   public static Spell TENEBRUM = new Spell("tenebrum", nbtkey1, 5).configure(graphTenebrum).setcolor(984847, 3211335);
   public int id;
   public ResourceLocation textureLocation = mainResourceLocation;
   public int textureIndex;
   public String name;
   public WriteGraph configuration;
   public int nbtKey;
   public int nbtBitshift;
   public float colorR1;
   public float colorG1;
   public float colorB1;
   public float colorR2;
   public float colorG2;
   public float colorB2;
   public int randomColorTimeShift;
   private static Vec3d VEC3D_ONE = new Vec3d(1.0, 1.0, 1.0);

   public Spell(String spellName, int nbtKey, int nbtBitshift) {
      this.name = spellName;
      this.nbtKey = nbtKey;
      this.nbtBitshift = nbtBitshift;
   }

   public Spell configure(WriteGraph configuration) {
      this.configuration = configuration;
      return this;
   }

   public static void init() {
      registerSpell(IGNIS);
      registerSpell(TERRA);
      registerSpell(AQUA);
      registerSpell(AERA);
      registerSpell(EGO);
      registerSpell(FRIGIDUS);
      registerSpell(FULGUR);
      registerSpell(VANITAS);
      registerSpell(VOLUPTAS);
      registerSpell(DOLOR);
      registerSpell(MORS);
      registerSpell(VITA);
      registerSpell(IRA);
      registerSpell(ANIMA);
      registerSpell(ALGIZ);
      registerSpell(URUZ);
      registerSpell(PECCATUM);
      registerSpell(EXPULSIO);
      registerSpell(SOWILO);
      registerSpell(RADIUM);
      registerSpell(HAGALAZ);
      registerSpell(ISA);
      registerSpell(HIEMS);
      registerSpell(TURISAZ);
      registerSpell(SEQUOR);
      registerSpell(TRIDEZ);
      registerSpell(STILLA);
      registerSpell(POLUPUS);
      registerSpell(FLUCTUS);
      registerSpell(STATERA);
      registerSpell(TIWAZ);
      registerSpell(TONITRUUM);
      registerSpell(CAELUM);
      registerSpell(DRAKKAR);
      registerSpell(MANIFAUL);
      registerSpell(TENEBRUM);
   }

   public static int getHighestId() {
      return startId - 1;
   }

   public static void registerSpell(Spell spell) {
      spell.id = startId++;
      spellsRegistry.put(spell.id, spell);
   }

   public GUIResearchTable.AnalyzedSpell getAnalyzed(WriteBlank blank) {
      int resultSimilarity = this.configuration.checkSimilarity(blank);
      int radius = WriteGraph.defaultRadius();
      int resultFullness = this.configuration.checkLines(blank);
      int dirt = this.configuration.checkDirt(blank);
      return new GUIResearchTable.AnalyzedSpell(this, resultSimilarity, resultFullness, dirt);
   }

   public static ArrayList<Spell> getAllSpellsKnownByPlayer(EntityPlayer player) {
      ArrayList<Spell> list = new ArrayList<>();
      if (TileResearchTable.isInfiniteWriteEnabled()) {
         for (Entry<Integer, Spell> entry : spellsRegistry.entrySet()) {
            list.add(entry.getValue());
         }

         return list;
      } else {
         if (player != null) {
            NBTTagCompound arpgspells = ExploringField.getExploringTagCompound(player);

            for (Entry<Integer, Spell> entry : spellsRegistry.entrySet()) {
               if (ExploringField.isExplored(arpgspells, entry.getValue())) {
                  list.add(entry.getValue());
               }
            }
         }

         return list;
      }
   }

   public Vec3d getColor() {
      if (this.id >= 1 && this.id <= 12) {
         ShardType shardType = ShardType.byId(this.id);
         return new Vec3d(shardType.colorR, shardType.colorG, shardType.colorB);
      } else {
         float progress = 0.5F + MathHelper.sin((AnimationTimer.tick + this.randomColorTimeShift) / 50.0F) * 0.5F;
         float unprogress = 1.0F - progress;
         return new Vec3d(
            this.colorR1 * progress + this.colorR2 * unprogress,
            this.colorG1 * progress + this.colorG2 * unprogress,
            this.colorB1 * progress + this.colorB2 * unprogress
         );
      }
   }

   public Spell setcolor(int color1, int color2) {
      Vec3d v1 = ColorConverters.DecimaltoRGB(color1);
      Vec3d v2 = ColorConverters.DecimaltoRGB(color2);
      this.colorR1 = (float)v1.x;
      this.colorG1 = (float)v1.y;
      this.colorB1 = (float)v1.z;
      this.colorR2 = (float)v2.x;
      this.colorG2 = (float)v2.y;
      this.colorB2 = (float)v2.z;
      this.randomColorTimeShift = GetMOP.rand.nextInt(500);
      return this;
   }

   public void renderRune(int x, int y, boolean explored) {
      float r = 1.0F;
      float g = 1.0F;
      float b = 1.0F;
      if (explored || this.id >= 1 && this.id <= 12) {
         Vec3d col = this.getColor();
         r = (float)col.x;
         g = (float)col.y;
         b = (float)col.z;
      }

      int texId = this.id - 1;
      int xcell = texId % 14;
      int ycell = texId / 14;
      if (this.id != 8) {
         AbstractMobModel.alphaGlow();
      }

      GlStateManager.enableBlend();
      GlStateManager.color(r, g, b, explored ? 1.0F : 0.5F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(GUIResearchTable.ADDITIONAL_RUNES);
      Gui.drawModalRectWithCustomSizedTexture(x + 5, y + 3, xcell * 9, ycell * 13, 9, 13, 128.0F, 128.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      if (this.id != 8) {
         AbstractMobModel.alphaGlowDisable();
      }
   }
}
