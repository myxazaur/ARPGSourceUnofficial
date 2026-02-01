package com.vivern.arpg.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;
import org.apache.commons.io.IOUtils;

public class CodeMonke {
   static int t = 3;

   public static void work() {
   }

   public static String getfilename(String absPath) {
      String str = "";
      boolean writeStarted = false;

      for (int i = absPath.length() - 1; i >= 0; i--) {
         char ch = absPath.charAt(i);
         if (!writeStarted) {
            if (ch == '.') {
               writeStarted = true;
            }
         } else {
            if (ch == '\\' || ch == '/') {
               return str;
            }

            str = ch + str;
         }
      }

      return str;
   }

   public static void fixStructures(World worldIn) {
      String path = "C:/Users/Vivern/Desktop/Modding/src/main/resources/assets/arpg/structures";
      WorldServer worldServer = (WorldServer)worldIn;
      MinecraftServer minecraftServer = worldIn.getMinecraftServer();
      TemplateManager templateManager = worldServer.getStructureTemplateManager();
      File file = new File(path);
      if (file.exists()) {
         for (File fileIn : file.listFiles()) {
            if (fileIn.isFile() && fileIn.getAbsolutePath().endsWith(".nbt")) {
               System.out.println();
               System.out.println(fileIn.getAbsolutePath());
               String structure = getfilename(fileIn.getAbsolutePath());
               System.out.println();
               System.out.println(structure);
               NBTTagCompound templateNBT = readTemplate(new ResourceLocation("arpg:" + structure));

               for (NBTBase nbt : templateNBT.getTagList("palette", 10)) {
                  if (nbt instanceof NBTTagCompound) {
                     NBTTagCompound tagCompound = (NBTTagCompound)nbt;
                     if (tagCompound.hasKey("Name")) {
                        String name = tagCompound.getString("Name");
                        name = name.replaceFirst("gunmod:", "arpg:");
                        tagCompound.setString("Name", name);
                     }
                  }
               }

               writeTemplate("C:/rezes/find_and_replace/structs/" + structure + ".nbt", templateNBT);
            }
         }
      }
   }

   public static void renameAll(String path) {
      File file = new File(path);
      if (file.exists()) {
         for (File fileIn : file.listFiles()) {
            if (fileIn.isFile()) {
               fileIn.renameTo(new File(fileIn.getAbsolutePath().replaceFirst("Arpg", "Arpg")));
            } else if (fileIn.isDirectory()) {
               renameAll(fileIn.getAbsolutePath());
               fileIn.renameTo(new File(fileIn.getAbsolutePath().replaceFirst("Arpg", "Arpg")));
            }
         }
      }
   }

   public static void replaceAll(String path) {
      File file = new File(path);
      if (file.exists()) {
         for (File fileIn : file.listFiles()) {
            if (fileIn.isFile()) {
               replaceInFile(fileIn);
            } else if (fileIn.isDirectory()) {
               replaceAll(fileIn.getAbsolutePath());
            }
         }
      } else {
         System.out.println("FILE NOT FOUND!!!");
      }
   }

   public static void replaceInFile(File file) {
      try {
         StringBuilder sb = new StringBuilder();
         FileReader fr2 = new FileReader(file);
         BufferedReader br = new BufferedReader(fr2);

         for (String strLine = br.readLine(); strLine != null; strLine = br.readLine()) {
            String changed = modifyLine(strLine);
            sb.append(changed).append("\r\n");
         }

         br.close();
         fr2.close();
         FileWriter fileWriter = new FileWriter(file, false);
         String tostring = sb.toString();
         if (t-- > 0) {
            System.out.println(tostring);
         }

         fileWriter.write(tostring);
         fileWriter.close();
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }

   public static String modifyLine(String line) {
      String s1 = line.replaceAll("Arpg", "Arpg");
      return s1.replaceAll("arpg", "arpg");
   }

   public boolean isFileContains(File file, String contains) {
      try {
         new StringBuilder();
         FileReader fr2 = new FileReader(file);
         BufferedReader br = new BufferedReader(fr2);

         String strLine;
         while ((strLine = br.readLine()) != null) {
            if (strLine.contains(contains)) {
               br.close();
               fr2.close();
               return true;
            }
         }

         br.close();
         fr2.close();
         return false;
      } catch (Exception var7) {
         return false;
      }
   }

   public static void replaceMethod(File file, String methodName, String replaceTo) {
      Vec2i finded = findMethod(file, methodName);
      if (finded != null) {
         try {
            StringBuilder sb = new StringBuilder();
            FileReader fr2 = new FileReader(file);
            BufferedReader br = new BufferedReader(fr2);
            int i = 0;

            for (String strLine = br.readLine(); strLine != null; strLine = br.readLine()) {
               if (i < finded.x || i > finded.y) {
                  sb.append(strLine).append("\r\n");
               } else if (i == finded.y) {
                  sb.append(replaceTo);
               }

               i++;
            }

            br.close();
            fr2.close();
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(sb.toString());
            fileWriter.close();
         } catch (Exception var10) {
         }
      }
   }

   @Nullable
   public static Vec2i findMethod(File file, String methodName) {
      String find = " " + methodName + "(";

      try {
         int charStart = 123;
         int charEnd = 125;
         FileReader fr2 = new FileReader(file);
         BufferedReader br = new BufferedReader(fr2);
         ArrayList<String> lines = new ArrayList<>();

         for (String strLine = br.readLine(); strLine != null; strLine = br.readLine()) {
            lines.add(strLine);
         }

         int methodStart = 0;
         int methodEnd = 0;

         for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(find)) {
               methodStart = i;

               while (methodStart > 0 && lines.get(methodStart - 1).contains("@")) {
                  methodStart--;
               }

               methodEnd = i;
               boolean hasOne = false;
               int blocks = 0;

               while (!hasOne || blocks > 0) {
                  String end = lines.get(methodEnd);
                  int count1 = coundOf(end, '{');
                  if (count1 > 0) {
                     hasOne = true;
                     blocks += count1;
                  }

                  int count2 = coundOf(end, '}');
                  if (count2 > 0) {
                     blocks -= count2;
                  }

                  if (!hasOne || blocks > 0) {
                     methodEnd++;
                  }
               }

               br.close();
               fr2.close();
               return new Vec2i(methodStart, methodEnd);
            }
         }

         br.close();
         fr2.close();
      } catch (Exception var18) {
      }

      return null;
   }

   public static int coundOf(String string, char of) {
      int i = 0;

      for (char c : string.toCharArray()) {
         if (c == of) {
            i++;
         }
      }

      return i;
   }

   public static NBTTagCompound readTemplate(ResourceLocation resLocation) {
      String path = "C:/Users/Vivern/Desktop/Modding/src/main/resources/assets/arpg/structures/";
      String s = resLocation.getPath();
      File file1 = new File(path + s + ".nbt");
      if (!file1.exists()) {
         return null;
      } else {
         InputStream inputstream = null;

         try {
            inputstream = new FileInputStream(file1);
            return readTemplateFromStream(s, inputstream);
         } catch (Throwable var10) {
            boolean flag = false;
         } finally {
            IOUtils.closeQuietly(inputstream);
         }

         return null;
      }
   }

   private static NBTTagCompound readTemplateFromStream(String id, InputStream stream) throws IOException {
      NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(stream);
      if (!nbttagcompound.hasKey("DataVersion", 99)) {
         nbttagcompound.setInteger("DataVersion", 500);
      }

      return nbttagcompound;
   }

   public static boolean writeTemplate(String pathAndFilename, NBTTagCompound nbttagcompound) {
      File file2 = new File(pathAndFilename);
      OutputStream outputstream = null;

      boolean flag;
      try {
         outputstream = new FileOutputStream(file2);
         CompressedStreamTools.writeCompressed(nbttagcompound, outputstream);
         return true;
      } catch (Throwable var9) {
         flag = false;
      } finally {
         IOUtils.closeQuietly(outputstream);
      }

      return flag;
   }
}
