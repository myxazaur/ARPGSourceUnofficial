package com.Vivern.Arpg.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.annotation.Nullable;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

public class FileHelper {
   @Nullable
   public static File getConfigFile() {
      File minecraftHome = (File)FMLInjectionData.data()[6];
      if (minecraftHome != null && minecraftHome.isDirectory()) {
         for (File childFile : minecraftHome.listFiles()) {
            if (childFile.getName().equals("config") && childFile.isDirectory()) {
               return childFile;
            }
         }
      }

      return null;
   }

   @Nullable
   public static File getOrCreateChildFile(File parent, String name) {
      File child = getChildFile(parent, name);
      if (child == null) {
         child = createChildFile(parent, name);
      }

      return child;
   }

   @Nullable
   public static File getChildFile(File parent, String name) {
      if (parent != null && parent.isDirectory()) {
         for (File childFile : parent.listFiles()) {
            if (childFile.getName().equals(name)) {
               return childFile;
            }
         }
      }

      return null;
   }

   @Nullable
   public static File createChildFile(File parent, String name) {
      parent.mkdirs();
      File newfile = new File(parent, name);

      try {
         newfile.createNewFile();
         return newfile;
      } catch (IOException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   @Nullable
   public static String readFromResources(String name) {
      try {
         InputStream resourceStream = FileHelper.class.getResourceAsStream("/" + name);
         InputStreamReader reader = new InputStreamReader(resourceStream);
         StringBuilder sb = new StringBuilder();
         BufferedReader br = new BufferedReader(reader);

         for (String strLine = br.readLine(); strLine != null; strLine = br.readLine()) {
            sb.append(strLine).append("\r\n");
         }

         br.close();
         reader.close();
         return sb.toString();
      } catch (Exception var6) {
         var6.printStackTrace();
         return null;
      }
   }

   public static boolean writeToFile(File file, String data) {
      try {
         FileWriter fileWriter = new FileWriter(file, false);
         fileWriter.write(data);
         fileWriter.close();
         return true;
      } catch (Exception var3) {
         var3.printStackTrace();
         return false;
      }
   }
}
