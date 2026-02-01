package com.vivern.arpg.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderProgram {
   private int programID = ARBShaderObjects.glCreateProgramObjectARB();
   public boolean started;

   public ShaderProgram addFragment(String path) {
      return this.add(path, 35632);
   }

   public ShaderProgram addVertex(String path) {
      return this.add(path, 35633);
   }

   public ShaderProgram add(String path, int shaderType) {
      int shaderID = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
      ARBShaderObjects.glShaderSourceARB(shaderID, this.readFile(path));
      ARBShaderObjects.glCompileShaderARB(shaderID);
      if (ARBShaderObjects.glGetObjectParameteriARB(shaderID, 35713) == 0) {
         throw new RuntimeException(
            "Shader compilation error!\n" + ARBShaderObjects.glGetInfoLogARB(shaderID, ARBShaderObjects.glGetObjectParameteriARB(shaderID, 35716))
         );
      } else {
         ARBShaderObjects.glAttachObjectARB(this.programID, shaderID);
         return this;
      }
   }

   public ShaderProgram compile() {
      ARBShaderObjects.glLinkProgramARB(this.programID);
      return this;
   }

   public void start() {
      ARBShaderObjects.glUseProgramObjectARB(this.programID);
      this.started = true;
   }

   public void stop() {
      ARBShaderObjects.glUseProgramObjectARB(0);
      this.started = false;
   }

   public int getUniform(String name) {
      return ARBShaderObjects.glGetUniformLocationARB(this.programID, name);
   }

   private String readFile(String path) {
      try {
         StringBuilder builder = new StringBuilder();
         BufferedReader reader = new BufferedReader(
            new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("arpg", path)).getInputStream(), "UTF-8")
         );

         String str;
         while ((str = reader.readLine()) != null) {
            builder.append(str).append("\n");
         }

         return builder.toString();
      } catch (IOException var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
