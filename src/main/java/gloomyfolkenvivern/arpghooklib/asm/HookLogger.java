package gloomyfolkenvivern.arpghooklib.asm;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface HookLogger {
   void debug(String var1);

   void warning(String var1);

   void severe(String var1);

   void severe(String var1, Throwable var2);

   public static class SystemOutLogger implements HookLogger {
      @Override
      public void debug(String message) {
         System.out.println("[DEBUG] " + message);
      }

      @Override
      public void warning(String message) {
         System.out.println("[WARNING] " + message);
      }

      @Override
      public void severe(String message) {
         System.out.println("[SEVERE] " + message);
      }

      @Override
      public void severe(String message, Throwable cause) {
         this.severe(message);
         cause.printStackTrace();
      }
   }

   public static class VanillaLogger implements HookLogger {
      private Logger logger;

      public VanillaLogger(Logger logger) {
         this.logger = logger;
      }

      @Override
      public void debug(String message) {
         this.logger.fine(message);
      }

      @Override
      public void warning(String message) {
         this.logger.warning(message);
      }

      @Override
      public void severe(String message) {
         this.logger.severe(message);
      }

      @Override
      public void severe(String message, Throwable cause) {
         this.logger.log(Level.SEVERE, message, cause);
      }
   }
}
