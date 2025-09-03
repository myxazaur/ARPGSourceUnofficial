//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.elements.ItemCalibrationThing;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.Spell;
import com.Vivern.Arpg.network.PacketHandler;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TileCalibrationBundle extends TileEntity implements ISpellcastListener {
   public ArrayList<CalibrationThing> things = new ArrayList<>();
   public float attraction;
   public float range;
   public float speed;
   public ArrayList<SpellPassword> passwords = new ArrayList<>();
   public AxisAlignedBB fullHitbox;
   public boolean canAcceptSpells;

   public boolean addNewThing(ItemStack stack, ItemCalibrationThing item, int posx, int posz) {
      posx = MathHelper.clamp(posx, 0, 15);
      posz = MathHelper.clamp(posz, 0, 15);
      AxisAlignedBB vialaabb = item.boundingBox().offset(posx / 16.0F, 0.0, posz / 16.0F);
      if (this.checkNoCollide(vialaabb)) {
         CalibrationThing newThing = item.createThing(stack, posx, posz);
         newThing.hitbox = vialaabb;
         this.things.add(newThing);
         stack.shrink(1);
         this.recalculateFullHitbox();
         this.calculateCalibration();
         PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
         return true;
      } else {
         return false;
      }
   }

   public void clear() {
      this.attraction = 0.0F;
      this.range = 0.0F;
      this.speed = 0.0F;
      this.passwords = new ArrayList<>();
      this.things.clear();
   }

   public void calculateCalibration() {
      this.attraction = 0.0F;
      this.range = 0.0F;
      this.speed = 0.0F;
      this.passwords = new ArrayList<>();
      this.canAcceptSpells = false;

      for (CalibrationThing calibrationThing : this.things) {
         this.attraction = this.attraction + calibrationThing.getAttraction();
         this.range = this.range + calibrationThing.getRange();
         this.speed = this.speed + calibrationThing.getSpeed();
         if (calibrationThing.password != null) {
            this.passwords.add(calibrationThing.password);
         } else if (calibrationThing.item.canAcceptPassword) {
            this.canAcceptSpells = true;
         }
      }
   }

   public void recalculateFullHitbox() {
      boolean isfirst = true;

      for (CalibrationThing calibrationThing : this.things) {
         AxisAlignedBB boxHas = calibrationThing.hitbox;
         if (boxHas != null) {
            if (isfirst) {
               this.fullHitbox = boxHas;
               isfirst = false;
            } else {
               this.fullHitbox = this.fullHitbox.union(boxHas);
            }
         }
      }

      if (isfirst) {
         this.fullHitbox = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.3125, 1.0);
      }
   }

   public boolean hasNoThings() {
      return this.things.isEmpty();
   }

   public ItemStack removeThing(int number) {
      CalibrationThing thing = this.things.remove(number);
      ItemStack itemStack = thing.toItemstack();
      this.calculateCalibration();
      this.recalculateFullHitbox();
      return itemStack;
   }

   public boolean checkNoCollide(AxisAlignedBB aabb) {
      if (aabb == null) {
         return true;
      } else if (!(aabb.minX < 0.0) && !(aabb.minZ < 0.0) && !(aabb.maxX > 1.0) && !(aabb.maxZ > 1.0)) {
         for (CalibrationThing calibrationThing : this.things) {
            AxisAlignedBB boxHas = calibrationThing.hitbox;
            if (boxHas != null && boxHas.intersects(aabb)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean onSpellcast(double fromX, double fromY, double fromZ, ItemStack scroll, Spell[] spells, Object caster) {
      boolean atleastOne = false;
      if (this.canAcceptSpells) {
         for (CalibrationThing calibrationThing : this.things) {
            if (calibrationThing.item.canAcceptPassword && (calibrationThing.password == null || calibrationThing.password.isEmpty())) {
               calibrationThing.password = new SpellPassword(spells);
               atleastOne = true;
            }
         }
      }

      this.calculateCalibration();
      PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
      return atleastOne;
   }

   @Override
   public boolean canAttractParticles(Object caster) {
      return this.canAcceptSpells;
   }

   public void write(NBTTagCompound compound) {
      for (int i = 0; i < this.things.size(); i++) {
         CalibrationThing thing = this.things.get(i);
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         thing.writeToNbt(nbttagcompound);
         compound.setTag("slot" + i, nbttagcompound);
      }
   }

   public void read(NBTTagCompound compound) {
      this.things = new ArrayList<>();

      for (int i = 0; compound.hasKey("slot" + i); i++) {
         NBTTagCompound nbttagcompound = compound.getCompoundTag("slot" + i);
         CalibrationThing thing = new CalibrationThing(nbttagcompound);
         this.things.add(thing);
      }

      this.recalculateFullHitbox();
      this.calculateCalibration();
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
      super.readFromNBT(compound);
   }

   public NBTTagCompound getUpdateTag() {
      NBTTagCompound compound = super.getUpdateTag();
      this.write(compound);
      return compound;
   }

   public void handleUpdateTag(NBTTagCompound compound) {
      this.read(compound);
      super.handleUpdateTag(compound);
   }

   public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
      NBTTagCompound compound = packet.getNbtCompound();
      this.read(compound);
   }

   public SPacketUpdateTileEntity getUpdatePacket() {
      NBTTagCompound compound = new NBTTagCompound();
      this.write(compound);
      return new SPacketUpdateTileEntity(this.pos, 1, compound);
   }

   public static class CalibrationThing {
      public int posX;
      public int posZ;
      @Nullable
      public SpellPassword password;
      public AxisAlignedBB hitbox;
      public ItemCalibrationThing item;
      public byte randomValue;

      public CalibrationThing(int posX, int posZ, ItemCalibrationThing item) {
         this.posX = posX;
         this.posZ = posZ;
         this.item = item;
      }

      public CalibrationThing(NBTTagCompound compound) {
         String id = compound.getString("id");
         Item itm = Item.getByNameOrId(id);
         if (itm instanceof ItemCalibrationThing) {
            this.item = (ItemCalibrationThing)itm;
         } else {
            this.item = (ItemCalibrationThing)ItemsRegister.CALIBRATIONCRYSTAL_ATTRACT_SMALL;
         }

         if (compound.hasKey("allPoses")) {
            int allPoses = compound.getInteger("allPoses");
            int poses8bit = allPoses & 0xFF;
            int posx = poses8bit & 15;
            int posz = poses8bit >>> 4 & 15;
            this.posX = posx;
            this.posZ = posz;
         }

         if (this.item.boundingBox() != null) {
            this.hitbox = this.item.boundingBox().offset(this.posX / 16.0F, 0.0, this.posZ / 16.0F);
         }

         SpellPassword ps = SpellPassword.readFromNbt(compound);
         if (ps != null) {
            this.password = ps;
         }

         if (compound.hasKey("randomValue")) {
            this.randomValue = compound.getByte("randomValue");
         }
      }

      public void writeToNbt(NBTTagCompound compound) {
         ResourceLocation resourcelocation = (ResourceLocation)Item.REGISTRY.getNameForObject(this.item);
         compound.setString("id", resourcelocation == null ? "minecraft:air" : resourcelocation.toString());
         int allPoses = 0;
         int posx = MathHelper.clamp(this.posX, 0, 15);
         int posz = MathHelper.clamp(this.posZ, 0, 15);
         int poses8bit = posx | posz << 4;
         allPoses |= poses8bit;
         compound.setInteger("allPoses", allPoses);
         if (this.password != null) {
            this.password.writeToNbt(compound);
         }

         compound.setByte("randomValue", this.randomValue);
      }

      public ItemStack toItemstack() {
         ItemStack stack = new ItemStack(this.item);
         if (this.password != null && !this.password.isEmpty()) {
            this.password.writeToStackNbt(stack);
         }

         return stack;
      }

      public float getAttraction() {
         return this.item.attraction;
      }

      public float getRange() {
         return this.item.range;
      }

      public float getSpeed() {
         return this.item.speed;
      }
   }

   public static class SpellPassword {
      public Spell[] password;

      public SpellPassword(Spell[] password) {
         this.password = password;
      }

      public boolean isEmpty() {
         return this.password.length == 0;
      }

      public boolean isVoid() {
         return this.password != null && this.password.length != 0 ? this.password[0] == Spell.VANITAS : false;
      }

      @Nullable
      public Vec3d getColor() {
         if (this.password != null && this.password.length != 0) {
            float r = 0.0F;
            float g = 0.0F;
            float b = 0.0F;
            float colorsAmount = Math.min(this.password.length, 3);
            if (this.password[0] == Spell.VANITAS) {
               for (int i = 1; i < colorsAmount; i++) {
                  Spell spell = this.password[i];
                  Vec3d color = spell.getColor();
                  r = (float)(r + color.x);
                  g = (float)(g + color.y);
                  b = (float)(b + color.z);
               }

               colorsAmount--;
               return new Vec3d(r / colorsAmount, g / colorsAmount, b / colorsAmount);
            } else {
               for (int i = 0; i < colorsAmount; i++) {
                  Spell spell = this.password[i];
                  Vec3d color = spell.getColor();
                  r = (float)(r + color.x);
                  g = (float)(g + color.y);
                  b = (float)(b + color.z);
               }

               return new Vec3d(r / colorsAmount, g / colorsAmount, b / colorsAmount);
            }
         } else {
            return null;
         }
      }

      public void writeToStackNbt(ItemStack itemstack) {
         NBTHelper.writeSpellsToNbt(itemstack, this.password);
      }

      public void writeToNbt(NBTTagCompound compound) {
         NBTHelper.writeSpellsToNbt(compound, this.password);
      }

      @Nullable
      public static SpellPassword readFromNbt(NBTTagCompound compound) {
         Spell[] spells = NBTHelper.readSpellsFromNbt(compound);
         return spells != null ? new SpellPassword(spells) : null;
      }

      public boolean passwordEquals(SpellPassword obj) {
         if (this.password.length != obj.password.length) {
            return false;
         } else {
            for (int i = 0; i < this.password.length; i++) {
               if (this.password[i] != obj.password[i]) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public String toString() {
         String out = "(";

         for (Spell spell : this.password) {
            out = out + spell.name + ",";
         }

         return out + ")";
      }
   }
}
