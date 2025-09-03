package gloomyfolkenvivern.arpghooklib.disk;

import gloomyfolkenvivern.arpghooklib.asm.HookClassTransformer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class DiskHookLib {
   File untransformedDir = new File("untransformed");
   File transformedDir = new File("transformed");
   File hooksDir = new File("hooks");

   public static void main(String[] args) throws IOException {
      new DiskHookLib().process();
   }

   void process() throws IOException {
      HookClassTransformer transformer = new HookClassTransformer();

      for (File file : getFiles(".class", this.hooksDir)) {
         transformer.registerHookContainer(FileUtils.readFileToByteArray(file));
      }

      for (File file : getFiles(".class", this.untransformedDir)) {
         byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
         String className = "";
         byte[] var6 = transformer.transform(className, bytes);
      }
   }

   private static List<File> getFiles(String postfix, File dir) throws IOException {
      ArrayList<File> files = new ArrayList<>();
      File[] filesArray = dir.listFiles();
      if (filesArray != null) {
         for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
               files.addAll(getFiles(postfix, file));
            } else if (file.getName().toLowerCase().endsWith(postfix)) {
               files.add(file);
            }
         }
      }

      return files;
   }
}
