package com.vivern.arpg.main;

public class NamedAttributeModifier {
   public int operation;
   public float value;
   public String name;

   public NamedAttributeModifier(String name, float value, int operation) {
      this.value = value;
      this.name = name;
      this.operation = operation;
   }
}
