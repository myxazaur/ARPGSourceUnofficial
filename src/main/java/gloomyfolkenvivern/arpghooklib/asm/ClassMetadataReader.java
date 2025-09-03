package gloomyfolkenvivern.arpghooklib.asm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ClassMetadataReader {
   private static Method m;

   public byte[] getClassData(String className) throws IOException {
      String classResourceName = '/' + className.replace('.', '/') + ".class";
      return IOUtils.toByteArray(ClassMetadataReader.class.getResourceAsStream(classResourceName));
   }

   public void acceptVisitor(byte[] classData, ClassVisitor visitor) {
      new ClassReader(classData).accept(visitor, 0);
   }

   public void acceptVisitor(String className, ClassVisitor visitor) throws IOException {
      this.acceptVisitor(this.getClassData(className), visitor);
   }

   public MethodReference findVirtualMethod(String owner, String name, String desc) {
      ArrayList<String> superClasses = this.getSuperClasses(owner);

      for (int i = superClasses.size() - 1; i > 0; i--) {
         String className = superClasses.get(i);
         MethodReference methodReference = this.getMethodReference(className, name, desc);
         if (methodReference != null) {
            System.out.println("found virtual method: " + methodReference);
            return methodReference;
         }
      }

      return null;
   }

   private MethodReference getMethodReference(String type, String methodName, String desc) {
      try {
         return this.getMethodReferenceASM(type, methodName, desc);
      } catch (Exception var5) {
         return this.getMethodReferenceReflect(type, methodName, desc);
      }
   }

   protected MethodReference getMethodReferenceASM(String type, String methodName, String desc) throws IOException {
      FindMethodClassVisitor cv = new FindMethodClassVisitor(methodName, desc);
      this.acceptVisitor(type, cv);
      return cv.found ? new MethodReference(type, cv.targetName, cv.targetDesc) : null;
   }

   protected MethodReference getMethodReferenceReflect(String type, String methodName, String desc) {
      Class loadedClass = this.getLoadedClass(type);
      if (loadedClass != null) {
         for (Method m : loadedClass.getDeclaredMethods()) {
            if (this.checkSameMethod(methodName, desc, m.getName(), Type.getMethodDescriptor(m))) {
               return new MethodReference(type, m.getName(), Type.getMethodDescriptor(m));
            }
         }
      }

      return null;
   }

   protected boolean checkSameMethod(String sourceName, String sourceDesc, String targetName, String targetDesc) {
      return sourceName.equals(targetName) && sourceDesc.equals(targetDesc);
   }

   public ArrayList<String> getSuperClasses(String type) {
      ArrayList<String> superclasses = new ArrayList<>(1);
      superclasses.add(type);

      while ((type = this.getSuperClass(type)) != null) {
         superclasses.add(type);
      }

      Collections.reverse(superclasses);
      return superclasses;
   }

   private Class getLoadedClass(String type) {
      if (m != null) {
         try {
            ClassLoader classLoader = ClassMetadataReader.class.getClassLoader();
            return (Class)m.invoke(classLoader, type.replace('/', '.'));
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return null;
   }

   public String getSuperClass(String type) {
      try {
         return this.getSuperClassASM(type);
      } catch (Exception var3) {
         return this.getSuperClassReflect(type);
      }
   }

   protected String getSuperClassASM(String type) throws IOException {
      CheckSuperClassVisitor cv = new CheckSuperClassVisitor();
      this.acceptVisitor(type, cv);
      return cv.superClassName;
   }

   protected String getSuperClassReflect(String type) {
      Class loadedClass = this.getLoadedClass(type);
      if (loadedClass != null) {
         return loadedClass.getSuperclass() == null ? null : loadedClass.getSuperclass().getName().replace('.', '/');
      } else {
         return "java/lang/Object";
      }
   }

   static {
      try {
         m = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
         m.setAccessible(true);
      } catch (NoSuchMethodException var1) {
         var1.printStackTrace();
      }
   }

   private class CheckSuperClassVisitor extends ClassVisitor {
      String superClassName;

      public CheckSuperClassVisitor() {
         super(327680);
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         this.superClassName = superName;
      }
   }

   protected class FindMethodClassVisitor extends ClassVisitor {
      public String targetName;
      public String targetDesc;
      public boolean found;

      public FindMethodClassVisitor(String name, String desc) {
         super(327680);
         this.targetName = name;
         this.targetDesc = desc;
      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         System.out.println("visiting " + name + "#" + desc);
         if ((access & 2) == 0 && ClassMetadataReader.this.checkSameMethod(name, desc, this.targetName, this.targetDesc)) {
            this.found = true;
            this.targetName = name;
            this.targetDesc = desc;
         }

         return null;
      }
   }

   public static class MethodReference {
      public final String owner;
      public final String name;
      public final String desc;

      public MethodReference(String owner, String name, String desc) {
         this.owner = owner;
         this.name = name;
         this.desc = desc;
      }

      public Type getType() {
         return Type.getMethodType(this.desc);
      }

      @Override
      public String toString() {
         return "MethodReference{owner='" + this.owner + '\'' + ", name='" + this.name + '\'' + ", desc='" + this.desc + '\'' + '}';
      }
   }
}
