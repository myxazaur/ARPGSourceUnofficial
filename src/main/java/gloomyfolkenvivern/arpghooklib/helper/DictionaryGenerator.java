package gloomyfolkenvivern.arpghooklib.helper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;

public class DictionaryGenerator {
   public static void generateMethods() {
      try {
         String path = "C:/Users/Vivern/Desktop/Modding/src/main/java/gloomyfolken/hooklib/helper/methods.csv";
         List<String> lines = FileUtils.readLines(new File(path));
         lines.remove(0);
         HashMap<Integer, String> map = new HashMap<>();

         for (String str : lines) {
            String[] splitted = str.split(",");
            int first = splitted[0].indexOf(95);
            int second = splitted[0].indexOf(95, first + 1);
            int id = Integer.valueOf(splitted[0].substring(first + 1, second));
            map.put(id, splitted[1]);
         }

         DataOutputStream out = new DataOutputStream(new FileOutputStream("methods.bin"));
         out.writeInt(map.size());

         for (Entry<Integer, String> entry : map.entrySet()) {
            out.writeInt(entry.getKey());
            out.writeUTF(entry.getValue());
         }

         out.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }
   }
}
