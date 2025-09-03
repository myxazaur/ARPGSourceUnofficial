package gloomyfolkenvivern.arpghooklib.asm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class VariableIdHelper {
   private static ClassMetadataReader classMetadataReader = new ClassMetadataReader();

   public static List<String> listLocalVariables(byte[] classData, final String methodName, Type... argTypes) {
      final List<String> localVariables = new ArrayList<>();
      String methodDesc = Type.getMethodDescriptor(Type.VOID_TYPE, argTypes);
      final String methodDescWithoutReturnType = methodDesc.substring(0, methodDesc.length() - 1);
      ClassVisitor cv = new ClassVisitor(327680) {
         public MethodVisitor visitMethod(final int acc, String name, String desc, String signature, String[] exceptions) {
            return methodName.equals(name) && desc.startsWith(methodDescWithoutReturnType) ? new MethodVisitor(327680) {
               public void visitLocalVariable(String name, String descx, String signaturex, Label start, Label end, int index) {
                  String typeName = Type.getType(descx).getClassName();
                  int fixedIndex = index + ((acc & 8) != 0 ? 1 : 0);
                  localVariables.add(fixedIndex + ": " + typeName + " " + name);
               }
            } : null;
         }
      };
      classMetadataReader.acceptVisitor(classData, cv);
      return localVariables;
   }

   public static List<String> listLocalVariables(String className, String methodName, Type... argTypes) throws IOException {
      return listLocalVariables(classMetadataReader.getClassData(className), methodName, argTypes);
   }

   public static void printLocalVariables(byte[] classData, String methodName, Type... argTypes) {
      for (String str : listLocalVariables(classData, methodName, argTypes)) {
         System.out.println(str);
      }
   }

   public static void printLocalVariables(String className, String methodName, Type... argTypes) throws IOException {
      printLocalVariables(classMetadataReader.getClassData(className), methodName, argTypes);
   }
}
