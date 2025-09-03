//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.main;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.neural.BackpropNetwork;
import com.Vivern.Arpg.neural.Layer;
import com.Vivern.Arpg.neural.SigmoidLayer;
import com.Vivern.Arpg.tileentity.WriteBlank;
import com.google.gson.Gson;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.util.JsonException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@EventBusSubscriber(
   modid = "arpg"
)
public class CreateItemFile {
   public static String mainPatch = "/Users/Vivern/Desktop/Modding/src/main/resources/assets/arpg/";
   public static boolean enable = false;
   public static int tickCooldown = 0;
   public static int seedCol = 0;
   public static int seedTex = 0;
   public static Random random = new Random();
   public static BackpropNetwork network;

   public static void ResLocationCreate(Item item) {
      if (enable) {
         String itemname = item.getTranslationKey().replaceFirst("item.", "");
         String relativePath = mainPatch + "models/item/" + itemname + ".json";
         File file = new File(relativePath);

         try {
            if (file.createNewFile()) {
            }
         } catch (IOException var17) {
            var17.printStackTrace();
         }

         try (FileWriter writer = new FileWriter(file, false)) {
            String text = "{\r\n  \"parent\": \"item/generated\",\r\n  \"textures\": {\r\n    \"layer0\": \"arpg:" + itemname + "\"\r\n  }\r\n}";
            writer.write(text);
            writer.flush();
         } catch (IOException var19) {
         }
      }
   }

   public static void ResLocationCreateHandheld(Item item) {
      if (enable) {
         String itemname = item.getTranslationKey().replaceFirst("item.", "");
         String relativePath = mainPatch + "models/item/" + itemname + ".json";
         File file = new File(relativePath);

         try {
            if (file.createNewFile()) {
            }
         } catch (IOException var17) {
            var17.printStackTrace();
         }

         try (FileWriter writer = new FileWriter(file, false)) {
            String text = "{\r\n  \"parent\": \"item/handheld\",\r\n  \"textures\": {\r\n    \"layer0\": \"arpg:" + itemname + "\"\r\n  }\r\n}";
            writer.write(text);
            writer.flush();
         } catch (IOException var19) {
         }
      }
   }

   public static void customPlantResLocationCreate(Block block, int type) {
      if (enable) {
         if (type != 3) {
            String itemname = block.getTranslationKey().replaceFirst("tile.", "");
            ResLocationCreate(
               mainPatch + "blockstates/" + itemname + ".json",
               "{\r\n  \"forge_marker\": 1,\r\n  \"defaults\": {\r\n\t\r\n  },\r\n  \"variants\": {\r\n    \r\n    \"growed=false\": { \"model\": \"arpg:"
                  + itemname
                  + "0\"},\r\n\t\"growed=true\": { \"model\": \"arpg:"
                  + itemname
                  + "1\"}\r\n\t\r\n  }\r\n}\r\n"
            );
            if (type == 1) {
               ResLocationCreate(
                  mainPatch + "models/block/" + itemname + "0.json",
                  "{\r\n    \"parent\": \"block/tinted_cross\",\r\n    \"textures\": {\r\n        \"cross\": \"arpg:customplants/"
                     + itemname
                     + "0\"\r\n    }\r\n}\r\n"
               );
               ResLocationCreate(
                  mainPatch + "models/block/" + itemname + "1.json",
                  "{\r\n    \"parent\": \"block/tinted_cross\",\r\n    \"textures\": {\r\n        \"cross\": \"arpg:customplants/"
                     + itemname
                     + "1\"\r\n    }\r\n}\r\n"
               );
            }

            if (type == 2) {
               ResLocationCreate(
                  mainPatch + "models/block/" + itemname + "0.json",
                  "{\r\n    \"parent\": \"arpg:block/big_cross\",\r\n    \"textures\": {\r\n        \"cross\": \"arpg:customplants/"
                     + itemname
                     + "0\"\r\n    }\r\n}\r\n"
               );
               ResLocationCreate(
                  mainPatch + "models/block/" + itemname + "1.json",
                  "{\r\n    \"parent\": \"arpg:block/big_cross\",\r\n    \"textures\": {\r\n        \"cross\": \"arpg:customplants/"
                     + itemname
                     + "1\"\r\n    }\r\n}\r\n"
               );
            }

            ResLocationCreate(
               mainPatch + "models/item/" + itemname + ".json",
               "{\r\n  \"parent\": \"item/generated\",\r\n  \"textures\": {\r\n    \"layer0\": \"arpg:customplants/" + itemname + "1\"\r\n  }\r\n}"
            );
            ResLocationCreate(
               mainPatch + "models/item/" + itemname + "_seed.json",
               "{\r\n  \"parent\": \"item/generated\",\r\n  \"textures\": {\r\n    \"layer0\": \"arpg:customplants/" + itemname + "_seed\"\r\n  }\r\n}"
            );
         }
      }
   }

   public static void ResLocationCreate(Block block) {
      if (enable) {
         String itemname = block.getTranslationKey().replaceFirst("tile.", "");
         ResLocationCreate(
            mainPatch + "blockstates/" + itemname + ".json",
            "{\r\n    \"variants\": {\r\n        \"normal\": { \"model\": \"arpg:" + itemname + "\" }\r\n    }\r\n}"
         );
         ResLocationCreate(
            mainPatch + "models/block/" + itemname + ".json",
            "{\r\n    \"parent\": \"block/cube_all\",\r\n    \"textures\": {\r\n        \"all\": \"arpg:" + itemname + "\"\r\n    }\r\n}\r\n"
         );
         ResLocationCreate(mainPatch + "models/item/" + itemname + ".json", "{\r\n    \"parent\": \"arpg:block/" + itemname + "\"\r\n}");
      }
   }

   public static void ResLocationCreateColumn(Block block, String top, String side) {
      if (enable) {
         String itemname = block.getTranslationKey().replaceFirst("tile.", "");
         ResLocationCreate(
            mainPatch + "blockstates/" + itemname + ".json",
            "{\r\n    \"forge_marker\": 1,\r\n    \"defaults\":\r\n    {\r\n        \"transform\": \"forge:default-block\",\r\n        \"model\": \"cube_column\",\r\n        \"textures\": {\"end\": \"arpg:"
               + top
               + "\",\"side\": \"arpg:"
               + side
               + "\"}\r\n    },\r\n    \"variants\": {    \r\n        \"normal\": [{}], \"inventory\": [{}],\r\n        \"axis\": { \"x\":{\"x\": 90, \"y\": 90}, \"y\":{}, \"z\":{\"x\": 90} }\r\n    }\r\n}"
         );
      }
   }

   public static void ResLocationCreateStairs(Block block, String texture) {
      if (enable) {
         String itemname = block.getTranslationKey().replaceFirst("tile.", "");
         ResLocationCreate(
            mainPatch + "blockstates/" + itemname + ".json",
            "{\r\n  \"forge_marker\": 1,\r\n  \"defaults\": {\r\n    \"textures\": {\r\n      \"bottom\": \"arpg:"
               + texture
               + "\",\r\n      \"top\": \"arpg:"
               + texture
               + "\",\r\n      \"side\": \"arpg:"
               + texture
               + "\",\r\n\t  \"particle\": \"arpg:"
               + texture
               + "\"\r\n    }\r\n  },\r\n  \"variants\": {\r\n        \"facing=east,half=bottom,shape=straight\":  { \"model\": \"stone_brick_stairs\" },\r\n        \"facing=west,half=bottom,shape=straight\":  { \"model\": \"stone_brick_stairs\", \"y\": 180, \"uvlock\": true },\r\n        \"facing=south,half=bottom,shape=straight\": { \"model\": \"stone_brick_stairs\", \"y\": 90, \"uvlock\": true },\r\n        \"facing=north,half=bottom,shape=straight\": { \"model\": \"stone_brick_stairs\", \"y\": 270, \"uvlock\": true },\r\n        \"facing=east,half=bottom,shape=outer_right\":  { \"model\": \"stone_brick_outer_stairs\" },\r\n        \"facing=west,half=bottom,shape=outer_right\":  { \"model\": \"stone_brick_outer_stairs\", \"y\": 180, \"uvlock\": true },\r\n        \"facing=south,half=bottom,shape=outer_right\": { \"model\": \"stone_brick_outer_stairs\", \"y\": 90, \"uvlock\": true },\r\n        \"facing=north,half=bottom,shape=outer_right\": { \"model\": \"stone_brick_outer_stairs\", \"y\": 270, \"uvlock\": true },\r\n        \"facing=east,half=bottom,shape=outer_left\":  { \"model\": \"stone_brick_outer_stairs\", \"y\": 270, \"uvlock\": true },\r\n        \"facing=west,half=bottom,shape=outer_left\":  { \"model\": \"stone_brick_outer_stairs\", \"y\": 90, \"uvlock\": true },\r\n        \"facing=south,half=bottom,shape=outer_left\": { \"model\": \"stone_brick_outer_stairs\" },\r\n        \"facing=north,half=bottom,shape=outer_left\": { \"model\": \"stone_brick_outer_stairs\", \"y\": 180, \"uvlock\": true },\r\n        \"facing=east,half=bottom,shape=inner_right\":  { \"model\": \"stone_brick_inner_stairs\" },\r\n        \"facing=west,half=bottom,shape=inner_right\":  { \"model\": \"stone_brick_inner_stairs\", \"y\": 180, \"uvlock\": true },\r\n        \"facing=south,half=bottom,shape=inner_right\": { \"model\": \"stone_brick_inner_stairs\", \"y\": 90, \"uvlock\": true },\r\n        \"facing=north,half=bottom,shape=inner_right\": { \"model\": \"stone_brick_inner_stairs\", \"y\": 270, \"uvlock\": true },\r\n        \"facing=east,half=bottom,shape=inner_left\":  { \"model\": \"stone_brick_inner_stairs\", \"y\": 270, \"uvlock\": true },\r\n        \"facing=west,half=bottom,shape=inner_left\":  { \"model\": \"stone_brick_inner_stairs\", \"y\": 90, \"uvlock\": true },\r\n        \"facing=south,half=bottom,shape=inner_left\": { \"model\": \"stone_brick_inner_stairs\" },\r\n        \"facing=north,half=bottom,shape=inner_left\": { \"model\": \"stone_brick_inner_stairs\", \"y\": 180, \"uvlock\": true },\r\n        \"facing=east,half=top,shape=straight\":  { \"model\": \"stone_brick_stairs\", \"x\": 180, \"uvlock\": true },\r\n        \"facing=west,half=top,shape=straight\":  { \"model\": \"stone_brick_stairs\", \"x\": 180, \"y\": 180, \"uvlock\": true },\r\n        \"facing=south,half=top,shape=straight\": { \"model\": \"stone_brick_stairs\", \"x\": 180, \"y\": 90, \"uvlock\": true },\r\n        \"facing=north,half=top,shape=straight\": { \"model\": \"stone_brick_stairs\", \"x\": 180, \"y\": 270, \"uvlock\": true },\r\n        \"facing=east,half=top,shape=outer_right\":  { \"model\": \"stone_brick_outer_stairs\", \"x\": 180, \"y\": 90, \"uvlock\": true },\r\n        \"facing=west,half=top,shape=outer_right\":  { \"model\": \"stone_brick_outer_stairs\", \"x\": 180, \"y\": 270, \"uvlock\": true },\r\n        \"facing=south,half=top,shape=outer_right\": { \"model\": \"stone_brick_outer_stairs\", \"x\": 180, \"y\": 180, \"uvlock\": true },\r\n        \"facing=north,half=top,shape=outer_right\": { \"model\": \"stone_brick_outer_stairs\", \"x\": 180, \"uvlock\": true },\r\n        \"facing=east,half=top,shape=outer_left\":  { \"model\": \"stone_brick_outer_stairs\", \"x\": 180, \"uvlock\": true },\r\n        \"facing=west,half=top,shape=outer_left\":  { \"model\": \"stone_brick_outer_stairs\", \"x\": 180, \"y\": 180, \"uvlock\": true },\r\n        \"facing=south,half=top,shape=outer_left\": { \"model\": \"stone_brick_outer_stairs\", \"x\": 180, \"y\": 90, \"uvlock\": true },\r\n        \"facing=north,half=top,shape=outer_left\": { \"model\": \"stone_brick_outer_stairs\", \"x\": 180, \"y\": 270, \"uvlock\": true },\r\n        \"facing=east,half=top,shape=inner_right\":  { \"model\": \"stone_brick_inner_stairs\", \"x\": 180, \"y\": 90, \"uvlock\": true },\r\n        \"facing=west,half=top,shape=inner_right\":  { \"model\": \"stone_brick_inner_stairs\", \"x\": 180, \"y\": 270, \"uvlock\": true },\r\n        \"facing=south,half=top,shape=inner_right\": { \"model\": \"stone_brick_inner_stairs\", \"x\": 180, \"y\": 180, \"uvlock\": true },\r\n        \"facing=north,half=top,shape=inner_right\": { \"model\": \"stone_brick_inner_stairs\", \"x\": 180, \"uvlock\": true },\r\n        \"facing=east,half=top,shape=inner_left\":  { \"model\": \"stone_brick_inner_stairs\", \"x\": 180, \"uvlock\": true },\r\n        \"facing=west,half=top,shape=inner_left\":  { \"model\": \"stone_brick_inner_stairs\", \"x\": 180, \"y\": 180, \"uvlock\": true },\r\n        \"facing=south,half=top,shape=inner_left\": { \"model\": \"stone_brick_inner_stairs\", \"x\": 180, \"y\": 90, \"uvlock\": true },\r\n        \"facing=north,half=top,shape=inner_left\": { \"model\": \"stone_brick_inner_stairs\", \"x\": 180, \"y\": 270, \"uvlock\": true }\r\n    }\r\n}\r\n"
         );
      }
   }

   public static void ResLocationCreatePilaster(Block block, String top, String side, String particle) {
      if (enable) {
         String itemname = block.getTranslationKey().replaceFirst("tile.", "");
         ResLocationCreate(
            mainPatch + "blockstates/" + itemname + ".json",
            "{\r\n  \"forge_marker\": 1,\r\n  \"defaults\": {\r\n    \"textures\": {\r\n      \"down\": \"arpg:"
               + top
               + "\",\r\n\t  \"up\": \"arpg:"
               + top
               + "\",\r\n\t  \"north\": \"arpg:"
               + side
               + "\",\r\n\t  \"south\": \"arpg:"
               + side
               + "\",\r\n\t  \"west\": \"arpg:"
               + side
               + "\",\r\n\t  \"east\": \"arpg:"
               + side
               + "\",\r\n\t  \"particle\": \"arpg:"
               + particle
               + "\"\r\n    }\r\n  },\r\n  \"variants\": {\r\n    \"facing=up\": { \"model\": \"arpg:pilaster\" },\r\n\t\"facing=down\": { \"model\": \"arpg:pilaster_up\" },\r\n    \"facing=east\": { \"model\": \"arpg:pilaster_east\" },\r\n    \"facing=south\": { \"model\": \"arpg:pilaster_south\" },\r\n    \"facing=west\": { \"model\": \"arpg:pilaster_west\" },\r\n    \"facing=north\": { \"model\": \"arpg:pilaster_north\" }\r\n  }\r\n}\r\n"
         );
         ResLocationCreate(
            mainPatch + "models/item/" + itemname + ".json",
            "{\r\n    \"parent\": \"arpg:block/pilaster\",\r\n\t\"textures\": {\r\n      \"down\": \"arpg:"
               + top
               + "\",\r\n\t  \"up\": \"arpg:"
               + top
               + "\",\r\n\t  \"north\": \"arpg:"
               + side
               + "\",\r\n\t  \"south\": \"arpg:"
               + side
               + "\",\r\n\t  \"west\": \"arpg:"
               + side
               + "\",\r\n\t  \"east\": \"arpg:"
               + side
               + "\"\r\n    }\r\n}"
         );
      }
   }

   public static void ResLocationCreateTintedCross(Block block) {
      if (enable) {
         String itemname = block.getTranslationKey().replaceFirst("tile.", "");
         ResLocationCreate(
            mainPatch + "blockstates/" + itemname + ".json",
            "{\r\n    \"variants\": {\r\n        \"normal\": { \"model\": \"arpg:" + itemname + "\" }\r\n    }\r\n}"
         );
         ResLocationCreate(
            mainPatch + "models/block/" + itemname + ".json",
            "{\r\n    \"parent\": \"block/tinted_cross\",\r\n    \"textures\": {\r\n        \"cross\": \"arpg:" + itemname + "\"\r\n    }\r\n}\r\n"
         );
         ResLocationCreate(mainPatch + "models/item/" + itemname + ".json", "{\r\n    \"parent\": \"arpg:block/" + itemname + "\"\r\n}");
      }
   }

   public static void ResLocationCreateFixedBigCross(Block block) {
      if (enable) {
         String itemname = block.getTranslationKey().replaceFirst("tile.", "");
         ResLocationCreate(
            mainPatch + "blockstates/" + itemname + ".json",
            "{\r\n    \"variants\": {\r\n        \"normal\": { \"model\": \"arpg:" + itemname + "\" }\r\n    }\r\n}"
         );
         ResLocationCreate(
            mainPatch + "models/block/" + itemname + ".json",
            "{\r\n    \"parent\": \"arpg:fixed_big_cross\",\r\n    \"textures\": {\r\n        \"all\": \"arpg:" + itemname + "\"\r\n    }\r\n}\r\n"
         );
         ResLocationCreate(mainPatch + "models/item/" + itemname + ".json", "{\r\n    \"parent\": \"arpg:block/" + itemname + "\"\r\n}");
      }
   }

   public static void RotatedBlockResLocationCreate(Block block) {
      if (enable) {
         String itemname = block.getTranslationKey().replaceFirst("tile.", "");
         ResLocationCreate(
            mainPatch + "blockstates/" + itemname + ".json",
            "{\r\n  \"forge_marker\": 1,\r\n  \"defaults\": {\r\n    \"textures\": {\r\n      \"0\": \"arpg:"
               + itemname
               + "\",\r\n\t  \"particle\": \"arpg:"
               + itemname
               + "\",\r\n\t  \"model\": \"arpg:"
               + itemname
               + "\"\r\n    }\r\n  },\r\n  \"variants\": {\r\n    \"facing=east\": { \"model\": \"arpg:"
               + itemname
               + "\" },\r\n    \"facing=south\": { \"model\": \"arpg:"
               + itemname
               + "\", \"y\": 90 },\r\n    \"facing=west\": { \"model\": \"arpg:"
               + itemname
               + "\", \"y\": 180 },\r\n    \"facing=north\": { \"model\": \"arpg:"
               + itemname
               + "\", \"y\": 270 }\r\n  }\r\n}\r\n"
         );
         ResLocationCreate(mainPatch + "models/item/" + itemname + ".json", "{\r\n    \"parent\": \"arpg:block/" + itemname + "\"\r\n}");
      }
   }

   public static void ResLocationCreateStalact(String blockname, String secname) {
      if (enable) {
         ResLocationCreate(
            mainPatch + "blockstates/" + blockname + ".json",
            "{\r\n    \"variants\": {\r\n        \"variant=s1\": { \"model\": \"arpg:stalacts/"
               + secname
               + "1\" },\r\n\t\t\"variant=s2\": { \"model\": \"arpg:stalacts/"
               + secname
               + "2\" },\r\n\t\t\"variant=s3\": { \"model\": \"arpg:stalacts/"
               + secname
               + "3\" },\r\n\t\t\"variant=s4\": { \"model\": \"arpg:stalacts/"
               + secname
               + "4\" },\r\n\t\t\"variant=s5\": { \"model\": \"arpg:stalacts/"
               + secname
               + "5\" }\r\n    }\r\n}"
         );

         for (int ii = 1; ii < 6; ii++) {
            ResLocationCreate(
               mainPatch + "models/block/stalacts/" + secname + ii + ".json",
               "{\r\n    \"parent\": \"block/tinted_cross\",\r\n    \"textures\": {\r\n        \"cross\": \"arpg:stalacts/"
                  + secname
                  + ii
                  + "\"\r\n    }\r\n}\r\n"
            );
         }

         ResLocationCreate(mainPatch + "models/item/" + blockname + ".json", "{\r\n    \"parent\": \"arpg:block/stalacts/" + secname + "1\"\r\n}");
      }
   }

   public static void ResLocationCreate(String relativePath, String text) {
      if (enable) {
         File file = new File(relativePath);

         try {
            if (file.createNewFile()) {
            }
         } catch (IOException var16) {
            var16.printStackTrace();
         }

         try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(text);
            writer.flush();
         } catch (IOException var18) {
         }
      }
   }

   public static void ResLocationCreateCalibration(Item item) {
      if (enable) {
         String itemname = item.getTranslationKey().replaceFirst("item.", "");
         String relativePath = mainPatch + "models/item/" + itemname + ".json";
         File file = new File(relativePath);

         try {
            if (file.createNewFile()) {
            }
         } catch (IOException var17) {
            var17.printStackTrace();
         }

         try (FileWriter writer = new FileWriter(file, false)) {
            String text = "{\r\n    \"parent\": \"builtin/entity\",\r\n    \"display\": {\r\n        \"gui\": {\r\n            \"rotation\": [ 30, -45, 0 ],\r\n            \"translation\": [ 0, 3.5, 11.25 ],\r\n            \"scale\": [ 1.0, 1.0, 1.0 ]\r\n        },\r\n        \"ground\": {\r\n            \"rotation\": [ 0, 0, 0 ],\r\n            \"translation\": [ 4, 8, 4 ],\r\n            \"scale\": [ 0.5, 0.5, 0.5 ]\r\n        },\r\n        \"fixed\": {\r\n            \"rotation\": [ 0, 180, 0 ],\r\n            \"translation\": [ 0, 0, 0 ],\r\n            \"scale\": [ 1, 1, 1 ]\r\n        },\r\n        \"head\": {\r\n            \"rotation\": [ 0, 180, 0 ],\r\n            \"translation\": [ 0, 0, 0 ],\r\n            \"scale\": [ 1, 1, 1 ]\r\n        },\r\n        \"firstperson_righthand\": {\r\n            \"rotation\": [ 0, -90, -15 ],\r\n            \"translation\": [ 0, 3.5, 0 ],\r\n            \"scale\": [ 1, 1, 1 ]\r\n        },\r\n        \"thirdperson_righthand\": {\r\n            \"rotation\": [ 0, -90, 0 ],\r\n            \"translation\": [ -6, 10.0, 5.0 ],\r\n            \"scale\": [ 0.625, 0.625, 0.625 ]\r\n        }\r\n    },\r\n    \"overrides\": [\r\n        {\r\n            \"predicate\": {\r\n                \"blocking\": 1\r\n            },\r\n            \"model\": \"item/shield_blocking\"\r\n        }\r\n    ]\r\n}\r\n";
            writer.write(text);
            writer.flush();
         } catch (IOException var19) {
         }
      }
   }

   public static void printUpdatedRecipesCode() {
      if (enable) {
         System.out.println("PRINTING RECIPES CODE ^^^^^^^^^^^^^^^^^^^^^|||||||||||||||||||||||");
         File folder = new File(mainPatch + "recipes");

         for (File file : folder.listFiles()) {
            String name = file.getName().replaceFirst(".json", "");
            System.out.print("registerRecipe(\"" + name + "\");");
            System.out.print("\n");
         }

         System.out.println("PRINTING RECIPES CODE END ^^^^^^^^^^^^^^^^^|||||||||||||||||||||||");
      }
   }

   public static void ResLocationCreateSpeleothem(String name) {
      if (enable) {
         for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
               String relativePath = mainPatch + "models/block/stalacts/" + name + x + y + ".json";
               File file = new File(relativePath);

               try {
                  file.createNewFile();
               } catch (IOException var42) {
                  var42.printStackTrace();
               }

               try (FileWriter writer = new FileWriter(file, false)) {
                  double[] arrayy = new double[]{3.2 * x, 3.2 * y, 3.2 * (x + 1), 3.2 * (y + 1)};
                  String arrayText = "[ " + arrayy[0] + ", " + arrayy[1] + ", " + arrayy[2] + ", " + arrayy[3] + " ]";
                  String text = "{\r\n    \"ambientocclusion\": false,\r\n    \"textures\": {\r\n        \"0\": \"arpg:stalacts/"
                     + name
                     + "\"\r\n    },\r\n    \"elements\": [\r\n        {\r\n            \"from\": [ -2, 0, 8 ], \r\n            \"to\": [ 18, 16, 8 ], \r\n            \"rotation\": { \"origin\": [ 8, 8, 8 ], \"axis\": \"y\", \"angle\": 45.0 },\r\n            \"faces\": {\r\n                \"north\": { \"texture\": \"#0\", \"uv\": "
                     + arrayText
                     + " },\r\n                \"south\": { \"texture\": \"#0\", \"uv\": "
                     + arrayText
                     + " }\r\n            }\r\n        },\r\n        {\r\n            \"from\": [ -2, 0, 8 ], \r\n            \"to\": [ 18, 16, 8 ], \r\n            \"rotation\": { \"origin\": [ 8, 8, 8 ], \"axis\": \"y\", \"angle\": -45.0 },\r\n            \"faces\": {\r\n                \"north\": { \"texture\": \"#0\", \"uv\": "
                     + arrayText
                     + " },\r\n                \"south\": { \"texture\": \"#0\", \"uv\": "
                     + arrayText
                     + " }\r\n            }\r\n        }\r\n    ]\r\n}";
                  writer.write(text);
                  writer.flush();
               } catch (IOException var46) {
                  var46.printStackTrace();
               }
            }
         }

         String relativePath = mainPatch + "blockstates/" + name + ".json";
         File file = new File(relativePath);

         try {
            file.createNewFile();
         } catch (IOException var39) {
            var39.printStackTrace();
         }

         try (FileWriter writer = new FileWriter(file, false)) {
            String text = "{\r\n    \"forge_marker\": 1,\r\n    \"defaults\":\r\n    {\r\n        \"transform\": \"forge:default-block\",\r\n        \"textures\": {\r\n\t\t\"particle\": \"arpg:stalacts/"
               + name
               + "\"\r\n        }\r\n    },\r\n    \"variants\": {\r\n\t\t\"normal\": [{}], \r\n\t\t\"type=0\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "00\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "10\" },\r\n\t\t\t{ \"model\": \"arpg:stalacts/"
               + name
               + "20\" }\r\n        ],\r\n\t\t\"type=1\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "01\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "11\" },\r\n\t\t\t{ \"model\": \"arpg:stalacts/"
               + name
               + "21\" }\r\n        ],\r\n\t\t\"type=2\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "02\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "12\" },\r\n\t\t\t{ \"model\": \"arpg:stalacts/"
               + name
               + "22\" }\r\n        ],\r\n\t\t\"type=3\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "03\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "13\" },\r\n\t\t\t{ \"model\": \"arpg:stalacts/"
               + name
               + "23\" }\r\n        ],\r\n\t\t\"type=4\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "04\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "14\" },\r\n\t\t\t{ \"model\": \"arpg:stalacts/"
               + name
               + "24\" }\r\n        ],\r\n\t\t\"type=5\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "30\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "40\" },\r\n\t\t\t{ \"model\": \"arpg:stalacts/"
               + name
               + "32\" }\r\n        ],\r\n\t\t\"type=6\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "34\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "44\" },\r\n\t\t\t{ \"model\": \"arpg:stalacts/"
               + name
               + "42\" }\r\n        ],\r\n\t\t\"type=7\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "31\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "41\" }\r\n        ],\r\n\t\t\"type=8\": [\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "33\" },\r\n            { \"model\": \"arpg:stalacts/"
               + name
               + "43\" }\r\n        ]\r\n\t\t\r\n  }\r\n}";
            writer.write(text);
            writer.flush();
         } catch (IOException var44) {
            var44.printStackTrace();
         }
      }
   }

   public static void craftingLocationCreate(ItemStack[] stacks, ItemStack output, boolean shaped, boolean useOredig) {
      if (enable) {
         if (shaped) {
            char[] chars = new char[]{'A', 'B', 'C', 'L', 'S', 'M', 'G', 'H', 'J'};
            boolean useOredic = false;
            HashMap<String, String> ingridients = new HashMap<>();
            HashMap<String, Character> keys = new HashMap<>();
            char[] matrix = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

            for (int i = 0; i < 9; i++) {
               if (!stacks[i].isEmpty()) {
                  Item item = stacks[i].getItem();
                  if (!OreDicHelper.getOreNames(stacks[i]).isEmpty() && useOredig) {
                     String orename = OreDicHelper.getOreNames(stacks[i]).get(0);
                     useOredic = true;
                     String text = "\"" + chars[i] + "\": {\r\n            \"type\": \"forge:ore_dict\",\r\n\t\t\t\"ore\": \"" + orename + "\"\r\n        }";
                     if (!ingridients.containsKey(orename)) {
                        ingridients.put(orename, text);
                        keys.put(orename, chars[i]);
                     }

                     matrix[i] = keys.get(orename);
                  } else {
                     String itemname = item.getRegistryName().toString();
                     String text = "\"" + chars[i] + "\": {\r\n            \"item\": \"" + itemname + "\"\r\n        }";
                     if (!ingridients.containsKey(itemname)) {
                        ingridients.put(itemname, text);
                        keys.put(itemname, chars[i]);
                     }

                     matrix[i] = keys.get(itemname);
                  }
               }
            }

            boolean f1 = true;
            boolean f2 = true;
            if (matrix[2] == ' ' && matrix[5] == ' ' && matrix[8] == ' ') {
               f2 = false;
               if (matrix[1] == ' ' && matrix[4] == ' ' && matrix[7] == ' ') {
                  f1 = false;
               }
            }

            boolean m1 = matrix[3] != ' ' || matrix[4] != ' ' || matrix[5] != ' ';
            boolean m2 = matrix[6] != ' ' || matrix[7] != ' ' || matrix[8] != ' ';
            String finaltext = "{\r\n    \"type\": \"" + (useOredic ? "forge:ore_shaped" : "minecraft:crafting_shaped") + "\",\r\n    \"pattern\": [";
            finaltext = finaltext + "\"" + matrix[0] + (f1 ? matrix[1] : "") + (f2 ? matrix[2] : "") + "\"" + (!m1 && !m2 ? "" : ",") + "\r\n";
            if (m1 || m2) {
               finaltext = finaltext + "        \"" + matrix[3] + (f1 ? matrix[4] : "") + (f2 ? matrix[5] : "") + "\"" + (m2 ? "," : "") + "\r\n";
            }

            if (m2) {
               finaltext = finaltext + "        \"" + matrix[6] + (f1 ? matrix[7] : "") + (f2 ? matrix[8] : "") + "\"";
            }

            finaltext = finaltext + "],\r\n    \"key\": {";

            for (Entry<String, String> entry : ingridients.entrySet()) {
               finaltext = finaltext + entry.getValue() + ",";
            }

            finaltext = finaltext.substring(0, finaltext.length() - 1);
            String outputname = output.getItem().getRegistryName().toString();
            finaltext = finaltext
               + "},\r\n    \"result\": {\r\n        \"item\": \""
               + outputname
               + "\",\r\n        \"count\": "
               + output.getCount()
               + "\r\n    }\r\n}";
            ResLocationCreate(mainPatch + "recipes/" + output.getItem().getRegistryName().getPath() + ".json", finaltext);
         }
      }
   }

   public static BufferedImage getSampleImage(int id) {
      String relativePath = mainPatch + "textures/texture_gen" + id + ".png";
      File file = new File(relativePath);

      try {
         return ImageIO.read(file);
      } catch (IOException var4) {
         var4.printStackTrace();
         return new BufferedImage(64, 64, 2);
      }
   }

   public static BufferedImage getImage(String name) {
      String relativePath = mainPatch + "textures/" + name + ".png";
      File file = new File(relativePath);

      try {
         return ImageIO.read(file);
      } catch (IOException var4) {
         var4.printStackTrace();
         return new BufferedImage(64, 64, 2);
      }
   }

   public static void runTextureProcessor(String name) {
      BufferedImage img = getImage(name);
      BufferedImage imagenew = blackToTranslucent2(img);

      try {
         String relativePath = "/Users/Vivern/Desktop/Modding/run/mods/tabula/textures/" + name + ".png";
         File file = new File(relativePath);
         ImageIO.write(imagenew, "png", file);
      } catch (IOException var5) {
         var5.printStackTrace();
      }
   }

   public static void saveWriteBlankToFile(String path, WriteBlank blank) {
      BufferedImage imagenew = new BufferedImage(blank.sizeX, blank.sizeY, 2);

      for (int xx = 0; xx < imagenew.getWidth(); xx++) {
         for (int yy = 0; yy < imagenew.getHeight(); yy++) {
            int colors = 255 - (blank.pixels[xx][yy] + 128);
            imagenew.setRGB(xx, yy, ColorConverters.RGBAtoDecimal255(colors, colors, colors, 255));
         }
      }

      try {
         File file = new File(path);
         ImageIO.write(imagenew, "png", file);
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   public static void saveImageToFile(String path, BufferedImage imagenew) {
      try {
         File file = new File(path);
         ImageIO.write(imagenew, "png", file);
      } catch (IOException var3) {
         var3.printStackTrace();
      }
   }

   @SubscribeEvent
   public static void Tick(ClientTickEvent event) {
   }

   public static void generateTexture() {
   }

   public static void generateTexture(
      ModelBase model, String name, boolean doubleSize, BufferedImage decorSample, int seedCol, int seedTex, java.awt.Color... colors
   ) {
      Random randColor = new Random(seedCol);
      Random randTexture = new Random(seedTex);
      String relativePath = "/Users/Vivern/Desktop/Modding/run/mods/tabula/textures/" + name + ".png";
      File file = new File(relativePath);

      try {
         file.createNewFile();
         int mult = doubleSize ? 2 : 1;
         BufferedImage image = new BufferedImage(model.textureWidth * mult, model.textureHeight * mult, 2);
         int counter = 0;

         for (ModelRenderer rend : model.boxList) {
            java.awt.Color colorin = colors[randColor.nextInt(colors.length)];
            java.awt.Color colorbn = colorin.darker();
            drawModelBox(rend, mult, image, colorin, colorbn, decorSample, randTexture);
            counter++;
         }

         System.out.println("count: " + counter);
         ImageIO.write(image, "png", file);
         System.out.println("Texture Generated: " + name);
      } catch (IOException var18) {
         var18.printStackTrace();
      }
   }

   public static void drawModelBox(
      ModelRenderer renderer, int sizeM, BufferedImage image, java.awt.Color colorInside, java.awt.Color colorBound, BufferedImage sample, Random rand
   ) {
      int textureOffsetX = (Integer)ReflectionHelper.getPrivateValue(ModelRenderer.class, renderer, new String[]{"textureOffsetX"});
      int textureOffsetY = (Integer)ReflectionHelper.getPrivateValue(ModelRenderer.class, renderer, new String[]{"textureOffsetY"});
      ModelBox box = (ModelBox)renderer.cubeList.get(0);
      int sizex = (int)(box.posX2 - box.posX1);
      int sizey = (int)(box.posY2 - box.posY1);
      int sizez = (int)(box.posZ2 - box.posZ1);
      int seed = rand.nextInt();
      drawBoundedQuad(
         textureOffsetX * sizeM, sizez * sizeM + textureOffsetY * sizeM, sizez * sizeM, sizey * sizeM, image, colorInside, colorBound, 1, seed, sample
      );
      drawBoundedQuad(
         textureOffsetX * sizeM + sizez * sizeM,
         sizez * sizeM + textureOffsetY * sizeM,
         sizex * sizeM,
         sizey * sizeM,
         image,
         colorInside,
         colorBound,
         2,
         seed,
         sample
      );
      drawBoundedQuad(
         textureOffsetX * sizeM + sizez * sizeM + sizex * sizeM,
         sizez * sizeM + textureOffsetY * sizeM,
         sizez * sizeM,
         sizey * sizeM,
         image,
         colorInside,
         colorBound,
         3,
         seed,
         sample
      );
      drawBoundedQuad(
         textureOffsetX * sizeM + sizez * 2 * sizeM + sizex * sizeM,
         sizez * sizeM + textureOffsetY * sizeM,
         sizex * sizeM,
         sizey * sizeM,
         image,
         colorInside,
         colorBound,
         4,
         seed,
         sample
      );
      drawBoundedQuad(
         textureOffsetX * sizeM + sizez * sizeM, textureOffsetY * sizeM, sizex * sizeM, sizez * sizeM, image, colorInside, colorBound, 5, seed, sample
      );
      drawBoundedQuad(
         textureOffsetX * sizeM + sizez * sizeM + sizex * sizeM,
         textureOffsetY * sizeM,
         sizex * sizeM,
         sizez * sizeM,
         image,
         colorInside,
         colorBound,
         6,
         seed,
         sample
      );
   }

   public static void drawBoundedQuad(
      int x,
      int y,
      int width,
      int height,
      BufferedImage image,
      java.awt.Color colorInside,
      java.awt.Color colorBound,
      int number,
      int seed,
      BufferedImage sample
   ) {
      if (width < 3 && height >= 3) {
         for (int xx = x; xx < x + width; xx++) {
            for (int yy = y; yy < y + height; yy++) {
               if (number == 1 && xx == x + width - 1) {
                  setRGB(image, xx, yy, colorBound.getRGB());
               } else if (number == 3 && xx == x) {
                  setRGB(image, xx, yy, colorBound.getRGB());
               } else if (number == 4) {
                  setRGB(image, xx, yy, colorBound.getRGB());
               } else {
                  setRGB(image, xx, yy, colorInside.getRGB());
               }
            }
         }
      } else if (height < 3) {
         for (int xx = x; xx < x + width; xx++) {
            for (int yyx = y; yyx < y + height; yyx++) {
               if ((number == 5 || number == 6) && yyx == y + height - 1) {
                  setRGB(image, xx, yyx, colorBound.getRGB());
               } else if (number < 5 && yyx == y) {
                  setRGB(image, xx, yyx, colorBound.getRGB());
               } else {
                  setRGB(image, xx, yyx, colorInside.getRGB());
               }
            }
         }
      } else {
         for (int xx = x; xx < x + width; xx++) {
            for (int yyxx = y; yyxx < y + height; yyxx++) {
               if (xx != x && xx != x + width - 1 && yyxx != y && yyxx != y + height - 1) {
                  setRGB(image, xx, yyxx, colorInside.getRGB());
               } else {
                  setRGB(image, xx, yyxx, colorBound.getRGB());
               }
            }
         }
      }

      if (number == 1) {
         Random rand = new Random(seed);
         int sampleX = rand.nextInt(Math.max(64 - width, 1));
         int sampleY = rand.nextInt(Math.max(64 - height, 1));

         for (int xx = x; xx < x + width; xx++) {
            for (int yyxxx = y; yyxxx < y + height; yyxxx++) {
               java.awt.Color color = new java.awt.Color(getRGB(image, xx, yyxxx));
               java.awt.Color color2 = new java.awt.Color(getRGB(sample, xx - x + sampleX, yyxxx - y + sampleY));
               setRGB(image, xx, yyxxx, mix(color, color2).getRGB());
            }
         }
      } else if (number == 3) {
         Random rand = new Random(seed);
         int sampleX = rand.nextInt(Math.max(64 - width, 1));
         int sampleY = rand.nextInt(Math.max(64 - height, 1));

         for (int xx = x; xx < x + width; xx++) {
            for (int yyxxx = y; yyxxx < y + height; yyxxx++) {
               java.awt.Color color = new java.awt.Color(getRGB(image, xx, yyxxx));
               java.awt.Color color2 = new java.awt.Color(getRGB(sample, width - 1 + sampleX - (xx - x), yyxxx - y + sampleY));
               setRGB(image, xx, yyxxx, mix(color, color2).getRGB());
            }
         }
      } else {
         int add = 0;
         if (width % 2 != 0) {
            width--;
            add = 1;
         }

         width /= 2;
         Random rand = new Random(seed + number);
         int sampleX = rand.nextInt(Math.max(64 - width, 1));
         int sampleY = rand.nextInt(Math.max(64 - height, 1));

         for (int xx = x; xx < x + width; xx++) {
            for (int yyxxx = y; yyxxx < y + height; yyxxx++) {
               java.awt.Color color0 = new java.awt.Color(getRGB(image, xx, yyxxx));
               int xcoordInverted = MathHelper.clamp(x + width * 2 + add - (xx - x) - 1, 0, image.getWidth() - 1);
               java.awt.Color color1 = new java.awt.Color(getRGB(image, xcoordInverted, yyxxx));
               java.awt.Color color2 = new java.awt.Color(getRGB(sample, xx - x + sampleX, yyxxx - y + sampleY));
               setRGB(image, xx, yyxxx, mix(color0, color2).getRGB());
               setRGB(image, xcoordInverted, yyxxx, mix(color1, color2).getRGB());
            }
         }
      }
   }

   public static java.awt.Color mix(java.awt.Color color, java.awt.Color color2) {
      return new java.awt.Color(
         (int)((float)color.getRed() * color2.getRed() / 255.0F),
         (int)((float)color.getGreen() * color2.getGreen() / 255.0F),
         (int)((float)color.getBlue() * color2.getBlue() / 255.0F)
      );
   }

   public static int mixIntColors(int color1, int color2, float ratio) {
      float[] col1 = ColorConverters.DecimaltoRGBA(color1);
      float[] col2 = ColorConverters.DecimaltoRGBA(color2);
      float unratio = 1.0F - ratio;
      return ColorConverters.RGBAtoDecimal(
         col1[0] * ratio + col2[0] * unratio, col1[1] * ratio + col2[1] * unratio, col1[2] * ratio + col2[2] * unratio, col1[3] * ratio + col2[3] * unratio
      );
   }

   public static int addIntColors(int color1, int color2) {
      java.awt.Color coll1 = new java.awt.Color(color1);
      java.awt.Color coll2 = new java.awt.Color(color2);
      java.awt.Color colres = new java.awt.Color(
         Math.min(coll1.getRed() + coll2.getRed(), 255),
         Math.min(coll1.getGreen() + coll2.getGreen(), 255),
         Math.min(coll1.getBlue() + coll2.getBlue(), 255),
         Math.min(coll1.getAlpha(), coll2.getAlpha())
      );
      return colres.getRGB();
   }

   public static void setRGB(BufferedImage image, int x, int y, int rgb) {
      if (x >= 0 && y >= 0 && y < image.getHeight() && x < image.getWidth()) {
         image.setRGB(x, y, rgb);
      }
   }

   public static int getRGB(BufferedImage image, int x, int y) {
      return x >= 0 && y >= 0 && y < image.getHeight() && x < image.getWidth() ? image.getRGB(x, y) : 0;
   }

   public static java.awt.Color hex2Rgb(String colorStr) {
      colorStr = colorStr.toUpperCase();
      return new java.awt.Color(
         Integer.valueOf(colorStr.substring(0, 2), 16), Integer.valueOf(colorStr.substring(2, 4), 16), Integer.valueOf(colorStr.substring(4, 6), 16)
      );
   }

   public static void printLang() {
      if (enable) {
         System.out.println("PRINTING LANG ^^^^^^^^^^^^^^^^^^^^^|||||||||||||||||||||||");

         for (Item item : Item.REGISTRY) {
            if (!(item instanceof ItemBlock)
               && item.getRegistryName().getNamespace().equals("arpg")
               && !Ln.canTranslate("item." + item.getRegistryName().getPath() + ".name")) {
               System.out
                  .print(
                     "item." + item.getRegistryName().getPath() + ".name=" + toUpperCaseFirst(item.getRegistryName().getPath().replace('_', ' '))
                  );
               System.out.print("\n");
            }
         }

         System.out.println("");
         System.out.println("^^^^^^^^^^^^^^^^^|||||||||||||||||||||||");
         System.out.println("");

         for (Item itemx : Item.REGISTRY) {
            if (itemx instanceof ItemBlock
               && itemx.getRegistryName().getNamespace().equals("arpg")
               && !Ln.canTranslate("tile." + itemx.getRegistryName().getPath() + ".name")) {
               System.out
                  .print(
                     "tile." + itemx.getRegistryName().getPath() + ".name=" + toUpperCaseFirst(itemx.getRegistryName().getPath().replace('_', ' '))
                  );
               System.out.print("\n");
            }
         }

         System.out.println("PRINTING LANG END ^^^^^^^^^^^^^^^^^|||||||||||||||||||||||");
      }
   }

   public static String toUpperCaseFirst(String s1) {
      String s2 = "";
      s2 = s2 + s1.substring(0, 1).toUpperCase();

      for (int i = 1; i < s1.length(); i++) {
         if (" ".equals(s1.substring(i - 1, i))) {
            s2 = s2 + s1.substring(i, i + 1).toUpperCase();
         } else {
            s2 = s2 + s1.substring(i, i + 1);
         }
      }

      return s2;
   }

   public static BufferedImage blackToColor(BufferedImage image, java.awt.Color color) {
      BufferedImage imagenew = new BufferedImage(image.getWidth(), image.getHeight(), 2);

      for (int xx = 0; xx < image.getWidth(); xx++) {
         for (int yy = 0; yy < image.getHeight(); yy++) {
            java.awt.Color lastcolor = new java.awt.Color(image.getRGB(xx, yy));
            float blackness = (765 - lastcolor.getRed() - lastcolor.getGreen() - lastcolor.getBlue()) / 765.0F;
            float unblackness = 1.0F - blackness;
            java.awt.Color newcolor = new java.awt.Color(
               (int)MathHelper.clamp(255.0F * unblackness + color.getRed() * blackness, 0.0F, 255.0F),
               (int)MathHelper.clamp(255.0F * unblackness + color.getGreen() * blackness, 0.0F, 255.0F),
               (int)MathHelper.clamp(255.0F * unblackness + color.getBlue() * blackness, 0.0F, 255.0F),
               lastcolor.getAlpha()
            );
            imagenew.setRGB(xx, yy, newcolor.getRGB());
         }
      }

      return imagenew;
   }

   public static BufferedImage blackToTranslucent(BufferedImage image) {
      BufferedImage imagenew = new BufferedImage(image.getWidth(), image.getHeight(), 2);

      for (int xx = 0; xx < image.getWidth(); xx++) {
         for (int yy = 0; yy < image.getHeight(); yy++) {
            java.awt.Color lastcolor = new java.awt.Color(image.getRGB(xx, yy));
            float blackness = (765 - lastcolor.getRed() - lastcolor.getGreen() - lastcolor.getBlue()) / 765.0F;
            float unblackness = 1.0F - blackness;
            java.awt.Color newcolor = new java.awt.Color(
               lastcolor.getRed(), lastcolor.getGreen(), lastcolor.getBlue(), (int)MathHelper.clamp(unblackness * 255.0F, 0.0F, 255.0F)
            );
            imagenew.setRGB(xx, yy, newcolor.getRGB());
         }
      }

      return imagenew;
   }

   public static BufferedImage blackToTranslucent2(BufferedImage image) {
      BufferedImage imagenew = new BufferedImage(image.getWidth(), image.getHeight(), 2);

      for (int xx = 0; xx < image.getWidth(); xx++) {
         for (int yy = 0; yy < image.getHeight(); yy++) {
            java.awt.Color lastcolor = new java.awt.Color(image.getRGB(xx, yy));
            int blackness = Math.max(Math.max(255 - lastcolor.getRed(), 255 - lastcolor.getGreen()), 255 - lastcolor.getBlue());
            int highestColor = Math.max(Math.max(lastcolor.getRed(), lastcolor.getGreen()), lastcolor.getBlue());
            float allColorsmultiplier = 255.0F / highestColor;
            int unblackness = 255 - blackness;
            java.awt.Color newcolor = new java.awt.Color(
               (int)(lastcolor.getRed() * allColorsmultiplier),
               (int)(lastcolor.getGreen() * allColorsmultiplier),
               (int)(lastcolor.getBlue() * allColorsmultiplier),
               MathHelper.clamp(unblackness, 0, 255)
            );
            imagenew.setRGB(xx, yy, newcolor.getRGB());
         }
      }

      return imagenew;
   }

   public static BufferedImage powColor(BufferedImage image, float pow) {
      BufferedImage imagenew = new BufferedImage(image.getWidth(), image.getHeight(), 2);

      for (int xx = 0; xx < image.getWidth(); xx++) {
         for (int yy = 0; yy < image.getHeight(); yy++) {
            java.awt.Color lastcolor = new java.awt.Color(image.getRGB(xx, yy));
            java.awt.Color newcolor = new java.awt.Color(
               (int)MathHelper.clamp(Math.pow(lastcolor.getRed() / 255.0, pow) * 255.0, 0.0, 255.0),
               (int)MathHelper.clamp(Math.pow(lastcolor.getGreen() / 255.0, pow) * 255.0, 0.0, 255.0),
               (int)MathHelper.clamp(Math.pow(lastcolor.getBlue() / 255.0, pow) * 255.0, 0.0, 255.0),
               lastcolor.getAlpha()
            );
            imagenew.setRGB(xx, yy, newcolor.getRGB());
         }
      }

      return imagenew;
   }

   public static String getNulledNumberString(int numberTo, int targetLength) {
      String number = numberTo + "";

      while (number.length() < targetLength) {
         number = "0" + number;
      }

      return number;
   }

   public static void combineCinemaSprites(String foldername, String imagename, int startIndex, int indexStep, int cycleAmount, boolean cycleAbsoluteAdd) {
      ArrayList<BufferedImage> list = new ArrayList<>();
      int i = startIndex;

      while (true) {
         String number = getNulledNumberString(i, 4);
         BufferedImage img = getImageInPC(foldername, imagename + number);
         if (img == null) {
            i = list.size() - cycleAmount;
            int width = list.get(0).getWidth();
            int height = list.get(0).getHeight();
            int allheight = i * width;
            float onecycleRatio = 1.0F / (cycleAmount + 1);
            BufferedImage result = new BufferedImage(width, allheight, 2);

            for (int ix = 0; ix < i; ix++) {
               for (int w = 0; w < width; w++) {
                  for (int h = 0; h < height; h++) {
                     int rgbInOne = list.get(ix).getRGB(w, h);
                     if (ix < cycleAmount) {
                        int rgbInTwo = list.get(ix + i).getRGB(w, h);
                        rgbInOne = mixIntColors(rgbInOne, rgbInTwo, onecycleRatio * (ix + 1));
                     }

                     result.setRGB(w, h + ix * height, rgbInOne);
                  }
               }
            }

            try {
               String relativePath = "/Users/Vivern/Pictures/" + imagename + ".png";
               File file = new File(relativePath);
               ImageIO.write(result, "png", file);
            } catch (IOException var18) {
               var18.printStackTrace();
            }

            return;
         }

         list.add(img);
         i += indexStep;
      }
   }

   public static BufferedImage getImageInPC(String foldername, String imagename) {
      String relativePath = "/Users/Vivern/Pictures/" + foldername + "/" + imagename + ".png";
      File file = new File(relativePath);

      try {
         return ImageIO.read(file);
      } catch (IOException var5) {
         var5.printStackTrace();
         return null;
      }
   }

   public static void makeTextureFromImage(String foldername, String imagename, int overlap) {
      BufferedImage image = getImageInPC(foldername, imagename);
      BufferedImage result = new BufferedImage(image.getWidth() - overlap, image.getHeight(), 2);
      int halfWidth = image.getWidth() / 2;

      for (int xx = 0; xx < result.getWidth(); xx++) {
         for (int yy = 0; yy < result.getHeight(); yy++) {
            float ratio = GetMOP.getfromto((float)xx, (float)(halfWidth - overlap), (float)halfWidth);
            int color1 = getColorCycledSafe(image, xx + halfWidth, yy);
            int color2 = getColorCycledSafe(image, xx + halfWidth + overlap, yy);
            int resultColor = mixIntColors(color2, color1, ratio);
            result.setRGB(xx, yy, resultColor);
         }
      }

      BufferedImage finalresult = new BufferedImage(image.getWidth() - overlap, image.getHeight() - overlap, 2);
      int halfHeight = image.getHeight() / 2;

      for (int xx = 0; xx < finalresult.getWidth(); xx++) {
         for (int yy = 0; yy < finalresult.getHeight(); yy++) {
            float ratio = GetMOP.getfromto((float)yy, (float)(halfHeight - overlap), (float)halfHeight);
            int color1 = getColorCycledSafe(result, xx, yy + halfHeight);
            int color2 = getColorCycledSafe(result, xx, yy + halfHeight + overlap);
            int resultColor = mixIntColors(color2, color1, ratio);
            finalresult.setRGB(xx, yy, resultColor);
         }
      }

      try {
         String relativePath = "/Users/Vivern/Pictures/" + foldername + "/tex_" + imagename + ".png";
         File file = new File(relativePath);
         ImageIO.write(finalresult, "png", file);
      } catch (IOException var14) {
         var14.printStackTrace();
      }
   }

   public static int getColorCycledSafe(BufferedImage image, int x, int y) {
      if (x < 0) {
         x = 0;
      }

      if (y < 0) {
         y = 0;
      }

      x %= image.getWidth();
      y %= image.getHeight();
      return image.getRGB(x, y);
   }

   public static void resampleImageBilinear(WriteBlank oldPixels, WriteBlank newPixels) {
      int oldw = oldPixels.sizeX;
      int oldh = oldPixels.sizeY;
      int neww = newPixels.sizeX;
      int newh = newPixels.sizeY;

      for (int j = 0; j < newh; j++) {
         float tmp = (float)j / (newh - 1) * (oldh - 1);
         int h = (int)Math.floor(tmp);
         if (h < 0) {
            h = 0;
         } else if (h >= oldh - 1) {
            h = oldh - 2;
         }

         float u = tmp - h;

         for (int i = 0; i < neww; i++) {
            tmp = (float)i / (neww - 1) * (oldw - 1);
            int w = (int)Math.floor(tmp);
            if (w < 0) {
               w = 0;
            } else if (w >= oldw - 1) {
               w = oldw - 2;
            }

            float t = tmp - w;
            float d1 = (1.0F - t) * (1.0F - u);
            float d2 = t * (1.0F - u);
            float d3 = t * u;
            float d4 = (1.0F - t) * u;
            int p1 = oldPixels.pixels[w][h] + 128;
            int p2 = oldPixels.pixels[w + 1][h] + 128;
            int p3 = oldPixels.pixels[w + 1][h + 1] + 128;
            int p4 = oldPixels.pixels[w][h + 1] + 128;
            newPixels.pixels[i][j] = (byte)(MathHelper.clamp(p1 * d1 + p2 * d2 + p3 * d3 + p4 * d4, 0.0F, 255.0F) - 128.0F);
         }
      }
   }

   public static void resampleImageBilinear(BufferedImage oldPixels, BufferedImage newPixels) {
      int oldw = oldPixels.getWidth();
      int oldh = oldPixels.getHeight();
      int neww = newPixels.getWidth();
      int newh = newPixels.getHeight();

      for (int j = 0; j < newh; j++) {
         float tmp = (float)j / (newh - 1) * (oldh - 1);
         int h = (int)Math.floor(tmp);
         if (h < 0) {
            h = 0;
         } else if (h >= oldh - 1) {
            h = oldh - 2;
         }

         float u = tmp - h;

         for (int i = 0; i < neww; i++) {
            tmp = (float)i / (neww - 1) * (oldw - 1);
            int w = (int)Math.floor(tmp);
            if (w < 0) {
               w = 0;
            } else if (w >= oldw - 1) {
               w = oldw - 2;
            }

            float t = tmp - w;
            float d1 = (1.0F - t) * (1.0F - u);
            float d2 = t * (1.0F - u);
            float d3 = t * u;
            float d4 = (1.0F - t) * u;
            int p1 = oldPixels.getRGB(w, h);
            int p2 = oldPixels.getRGB(w + 1, h);
            int p3 = oldPixels.getRGB(w + 1, h + 1);
            int p4 = oldPixels.getRGB(w, h + 1);
            Vec3d col1 = ColorConverters.DecimaltoRGB(p1).scale(d1);
            Vec3d col2 = ColorConverters.DecimaltoRGB(p2).scale(d2);
            Vec3d col3 = ColorConverters.DecimaltoRGB(p3).scale(d3);
            Vec3d col4 = ColorConverters.DecimaltoRGB(p4).scale(d4);
            int color = ColorConverters.RGBAtoDecimal(
               (float)(col1.x + col2.x + col3.x + col4.x),
               (float)(col1.y + col2.y + col3.y + col4.y),
               (float)(col1.z + col2.z + col3.z + col4.z),
               1.0F
            );
            newPixels.setRGB(i, j, color);
         }
      }
   }

   public static void networkTest() {
      Layer[] layers = new Layer[]{new SigmoidLayer(9, 4), new SigmoidLayer(4, 3), new SigmoidLayer(3, 2)};
      network = new BackpropNetwork(layers);
      float[][] data = new float[][]{
         {1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F},
         {1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F},
         {1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F},
         {0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F},
         {0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F},
         {1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F},
         {0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F},
         {0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F},
         {1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F},
         {1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F},
         {1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F},
         {1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F},
         {0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 1.0F, 0.0F},
         {0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F},
         {1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F},
         {0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F},
         {0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F},
         {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F},
         {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}
      };
      float[][] answers = new float[][]{
         {1.0F, 0.0F},
         {1.0F, 0.0F},
         {1.0F, 0.0F},
         {1.0F, 0.0F},
         {1.0F, 0.0F},
         {1.0F, 0.0F},
         {0.0F, 1.0F},
         {0.0F, 1.0F},
         {0.0F, 1.0F},
         {0.0F, 1.0F},
         {0.0F, 1.0F},
         {0.0F, 1.0F},
         {1.0F, 1.0F},
         {1.0F, 1.0F},
         {1.0F, 1.0F},
         {1.0F, 1.0F},
         {1.0F, 1.0F},
         {1.0F, 1.0F},
         {1.0F, 1.0F},
         {1.0F, 1.0F},
         {1.0F, 1.0F},
         {0.0F, 0.0F},
         {0.0F, 0.0F},
         {0.0F, 0.0F},
         {0.0F, 0.0F},
         {0.0F, 0.0F},
         {0.0F, 0.0F}
      };

      for (int i = 0; i < 500.0F + Debugger.floats[0]; i++) {
         int r = random.nextInt(27);
         network.learnPattern(data[r], answers[r], 0.2F, 0.6F + Debugger.floats[1]);
      }
   }

   public static ItemCameraTransforms readJsonItemCameraTransforms(String itemname) {
      try {
         File file2 = new File(mainPatch + "models/item/" + itemname + ".json");
         FileReader fr2 = new FileReader(file2);
         BufferedReader reader2 = new BufferedReader(fr2);
         String jsonString = "";
         String line2 = reader2.readLine();
         boolean writeStarted = false;
         int i = 0;

         while (line2 != null) {
            if (writeStarted) {
               jsonString = jsonString + line2;
            }

            line2 = reader2.readLine();
            if (line2.contains("display")) {
               line2 = "{";
               writeStarted = true;
            } else if (writeStarted && line2.contains("},")) {
               if (++i == 7) {
                  jsonString = jsonString + "}";
                  break;
               }
            }
         }

         if (Debugger.floats[0] > 0.0F) {
            System.out.println(jsonString);
         }

         Field field = ModelBlock.class.getDeclaredField("SERIALIZER");
         field.setAccessible(true);
         Object obj = field.get(null);
         if (obj instanceof Gson) {
            Gson gsonn = (Gson)obj;
            ItemCameraTransforms cameraTransforms = (ItemCameraTransforms)JsonUtils.gsonDeserialize(gsonn, jsonString, ItemCameraTransforms.class);
            if (cameraTransforms == null) {
               throw new JsonException("gsonDeserialize is failed");
            }

            return cameraTransforms;
         }
      } catch (Exception var12) {
      }

      return null;
   }
}
