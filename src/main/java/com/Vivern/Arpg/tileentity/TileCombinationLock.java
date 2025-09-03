//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.tileentity;

import com.Vivern.Arpg.blocks.SeaLock;
import com.Vivern.Arpg.main.Sounds;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;

public class TileCombinationLock extends TileEntity implements TileEntityClicked {
   public static SeaElement seaweed = new SeaElement("seaweed", 0);
   public static SeaElement cell = new SeaElement("cell", 1);
   public static SeaElement stone = new SeaElement("stone", 2);
   public static SeaElement worm = new SeaElement("worm", 3).setRecipe(seaweed, cell);
   public static SeaElement coral = new SeaElement("coral", 4).setRecipe(stone, cell);
   public static SeaElement shell = new SeaElement("shell", 5).setRecipe(worm, stone);
   public static SeaElement seaplant = new SeaElement("sea_plant", 6).setRecipe(seaweed, stone);
   public static SeaElement sponge = new SeaElement("sponge", 7).setRecipe(coral, cell);
   public static SeaElement fish = new SeaElement("fish", 8).setRecipe(worm, seaplant);
   public static SeaElement jellyfish = new SeaElement("jellyfish", 9).setRecipe(worm, cell);
   public static SeaElement shark = new SeaElement("shark", 10).setRecipe(fish, stone);
   public static SeaElement starfish = new SeaElement("starfish", 11).setRecipe(coral, worm);
   public static SeaElement anemone = new SeaElement("anemone", 12).setRecipe(stone, jellyfish);
   public static SeaElement seafeather = new SeaElement("sea_feather", 13).setRecipe(seaplant, coral);
   public static SeaElement lamprey = new SeaElement("lamprey", 14).setRecipe(fish, worm);
   public static SeaElement turtle = new SeaElement("turtle", 15).setRecipe(fish, shell);
   public static SeaElement seahorse = new SeaElement("sea_horse", 16).setRecipe(fish, seafeather);
   public static SeaElement coralfish = new SeaElement("coral_fish", 17).setRecipe(coral, fish);
   public static SeaElement seaurchin = new SeaElement("sea_urchin", 18).setRecipe(starfish, stone);
   public static SeaElement trilobite = new SeaElement("trilobite", 19).setRecipe(worm, shell);
   public static SeaElement crustacean = new SeaElement("crustacean", 20).setRecipe(trilobite, seaweed);
   public static SeaElement toxicfish = new SeaElement("toxic_fish", 21).setRecipe(coralfish, seaurchin);
   public static SeaElement chalk = new SeaElement("chalk", 22).setRecipe(shell, stone);
   public static SeaElement megafish = new SeaElement("megafish", 23).setRecipe(shark, seaplant);
   public static SeaElement sand = new SeaElement("sand", 24).setRecipe(seaplant, stone);
   public static SeaElement mollusk = new SeaElement("mollusk", 25).setRecipe(jellyfish, shell);
   public static SeaElement island = new SeaElement("island", 26).setRecipe(sand, stone);
   public static SeaElement pearl = new SeaElement("pearl", 27).setRecipe(shell, sand);
   public static SeaElement neuron = new SeaElement("neuron", 28).setRecipe(pearl, cell);
   public static SeaElement mind = new SeaElement("mind", 29).setRecipe(coral, neuron);
   public static SeaElement palm = new SeaElement("palm", 30).setRecipe(island, seaplant);
   public static SeaElement aspid = new SeaElement("aspid", 31).setRecipe(toxicfish, lamprey);
   public static SeaElement seasnake = new SeaElement("sea_snake", 32).setRecipe(megafish, aspid);
   public static SeaElement amphibian = new SeaElement("amphibian", 33).setRecipe(fish, island);
   public static SeaElement tryton = new SeaElement("tryton", 34).setRecipe(amphibian, mind);
   public static SeaElement sugarcane = new SeaElement("sugarcane", 35).setRecipe(palm, sponge);
   public static SeaElement coconut = new SeaElement("coconut", 36).setRecipe(palm, stone);
   public static SeaElement octopus = new SeaElement("octopus", 37).setRecipe(mollusk, mind);
   public static SeaElement mermaid = new SeaElement("mermaid", 38).setRecipe(tryton, fish);
   public static SeaElement flyfish = new SeaElement("flyfish", 39).setRecipe(fish, palm);
   public static SeaElement lightning = new SeaElement("lightning", 40).setRecipe(tryton, pearl);
   public static SeaElement ctenophore = new SeaElement("ctenophore", 41).setRecipe(jellyfish, lightning);
   public static SeaElement ship = new SeaElement("ship", 42).setRecipe(palm, tryton);
   public static SeaElement kraken = new SeaElement("kraken", 43).setRecipe(mollusk, ship);
   public static SeaElement gems = new SeaElement("gems", 44).setRecipe(stone, pearl);
   public static SeaElement siren = new SeaElement("siren", 45).setRecipe(tryton, flyfish);
   public static SeaElement metal = new SeaElement("metal", 46).setRecipe(tryton, stone);
   public static SeaElement gold = new SeaElement("gold", 47).setRecipe(metal, pearl);
   public static SeaElement stingray = new SeaElement("stingray", 48).setRecipe(shark, lightning);
   public static SeaElement cactus = new SeaElement("cactus", 49).setRecipe(palm, seaurchin);
   public static SeaElement rum = new SeaElement("rum", 50).setRecipe(sugarcane, toxicfish);
   public static SeaElement conger = new SeaElement("conger", 51).setRecipe(fish, lightning);
   public static SeaElement temple = new SeaElement("temple", 52).setRecipe(stone, siren);
   public static SeaElement piracy = new SeaElement("piracy", 53).setRecipe(ship, gold);
   public static SeaElement cannon = new SeaElement("cannon", 54).setRecipe(ship, metal);
   public static SeaElement chest = new SeaElement("chest", 55).setRecipe(gold, palm);
   public static SeaElement treasure = new SeaElement("treasure", 56).setRecipe(island, chest);
   public static SeaElement town = new SeaElement("town", 57).setRecipe(temple, lightning);
   public static SeaElement staff = new SeaElement("staff", 58).setRecipe(metal, gems);
   public static SeaElement blade = new SeaElement("blade", 59).setRecipe(metal, piracy);
   public static SeaElement trident = new SeaElement("trident", 60).setRecipe(staff, blade);
   public static SeaElement symbioticweapon = new SeaElement("symbiotic_weapon", 61).setRecipe(cannon, fish);
   public static SeaElement[] all = new SeaElement[]{
      cell,
      seaweed,
      stone,
      worm,
      coral,
      shell,
      seaplant,
      sponge,
      fish,
      jellyfish,
      shark,
      starfish,
      anemone,
      seafeather,
      lamprey,
      turtle,
      seahorse,
      coralfish,
      toxicfish,
      trilobite,
      crustacean,
      seaurchin,
      chalk,
      megafish,
      sand,
      mollusk,
      octopus,
      pearl,
      neuron,
      island,
      palm,
      aspid,
      seasnake,
      amphibian,
      tryton,
      sugarcane,
      coconut,
      mind,
      mermaid,
      flyfish,
      stingray,
      ctenophore,
      ship,
      kraken,
      lightning,
      siren,
      temple,
      staff,
      blade,
      trident,
      symbioticweapon,
      conger,
      metal,
      gold,
      gems,
      chest,
      treasure,
      town,
      piracy,
      cannon,
      cactus,
      rum
   };
   public List<SeaElement> unlocked = new ArrayList<>();
   public SeaElement selected1 = null;
   public SeaElement selected2 = null;
   public int firstFrame = 0;
   public SeaElement result = null;
   public SeaElement question = null;
   public static Random rand = new Random();

   public void setupQuestion(Random rand) {
      if (this.question == null) {
         int size = all.length;
         this.question = all[rand.nextInt(size - 3) + 3];
      }
   }

   @Override
   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (mouseY > 101 && mouseY < 122) {
         if (mouseX > 26 && mouseX < 47) {
            this.changeFrame(-6);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseX > 50 && mouseX < 71) {
            this.changeFrame(-2);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseX > 74 && mouseX < 95) {
            this.changeFrame(-1);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseX > 163 && mouseX < 184) {
            this.changeFrame(1);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseX > 187 && mouseX < 208) {
            this.changeFrame(2);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseX > 211 && mouseX < 232) {
            this.changeFrame(6);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }
      }

      if (mouseY > 52 && mouseY < 85) {
         if (mouseX > 80 && mouseX < 113) {
            this.selected1 = null;
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseX > 144 && mouseX < 177) {
            this.selected2 = null;
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }
      }

      if (mouseY > 59 && mouseY < 80 && mouseX > 118 && mouseX < 139 && this.selected1 != null && this.selected2 != null) {
         SeaElement el = tryMatch(this.selected1, this.selected2);
         if (el != null) {
            if (!this.unlocked.contains(el)) {
               this.unlocked.add(el);
               this.sort();
            }

            this.result = el;
            if (el == this.question) {
               this.world
                  .playSound((EntityPlayer)null, this.pos, Sounds.sealock_win, SoundCategory.BLOCKS, 0.5F, 0.85F + rand.nextFloat() / 4.0F);
            } else {
               this.world
                  .playSound((EntityPlayer)null, this.pos, Sounds.alchemic_craft, SoundCategory.BLOCKS, 0.4F, 0.85F + rand.nextFloat() / 4.0F);
            }

            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         } else {
            this.world
               .playSound((EntityPlayer)null, this.pos, Sounds.item_misc_e, SoundCategory.BLOCKS, 0.4F, 0.85F + rand.nextFloat() / 4.0F);
            boolean send = this.result != null;
            this.result = null;
            if (send) {
               SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
            }
         }
      }

      if (mouseY > 132 && mouseY < 165 && mouseX > 16 && mouseX < 241) {
         int framenumber = (mouseX - 16) / 32 + this.firstFrame;
         if (framenumber < this.unlocked.size()) {
            SeaElement el = this.unlocked.get(framenumber);
            this.select(el);
            this.world
               .playSound((EntityPlayer)null, this.pos, Sounds.item_misc_d, SoundCategory.BLOCKS, 0.4F, 0.85F + rand.nextFloat() / 4.0F);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }
      }

      if (mouseY > 164 && mouseY < 197) {
         if (mouseX > 80 && mouseX < 113) {
            this.select(seaweed);
            this.world
               .playSound((EntityPlayer)null, this.pos, Sounds.item_misc_d, SoundCategory.BLOCKS, 0.4F, 0.85F + rand.nextFloat() / 4.0F);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseX > 112 && mouseX < 145) {
            this.select(cell);
            this.world
               .playSound((EntityPlayer)null, this.pos, Sounds.item_misc_d, SoundCategory.BLOCKS, 0.4F, 0.85F + rand.nextFloat() / 4.0F);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }

         if (mouseX > 144 && mouseX < 177) {
            this.select(stone);
            this.world
               .playSound((EntityPlayer)null, this.pos, Sounds.item_misc_d, SoundCategory.BLOCKS, 0.4F, 0.85F + rand.nextFloat() / 4.0F);
            SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
         }
      }

      if (mouseY > 94 && mouseY < 127 && mouseX > 112 && mouseX < 145) {
         this.select(this.result);
         this.world
            .playSound((EntityPlayer)null, this.pos, Sounds.item_misc_d, SoundCategory.BLOCKS, 0.4F, 0.85F + rand.nextFloat() / 4.0F);
         SeaLock.trySendPacketUpdate(this.world, this.getPos(), this, 8);
      }
   }

   public void changeFrame(int i) {
      if (this.firstFrame + i < 0) {
         this.firstFrame = 0;
      } else if (this.firstFrame + i > this.unlocked.size() - 1) {
         this.firstFrame = Math.max(this.unlocked.size() - 1, 0);
      } else {
         this.firstFrame += i;
      }
   }

   public void select(SeaElement elem) {
      if (this.selected1 == null && this.selected2 != elem) {
         this.selected1 = elem;
      }

      if (this.selected2 == null && this.selected1 != elem) {
         this.selected2 = elem;
      }
   }

   public void sort() {
      long l = 0L;

      for (SeaElement el : this.unlocked) {
         l |= 1L << el.id;
      }

      this.unlocked.clear();

      for (SeaElement el : all) {
         if ((l & 1L << el.id) > 0L) {
            this.unlocked.add(el);
         }
      }
   }

   public void write(NBTTagCompound compound) {
      long l = 0L;

      for (SeaElement el : this.unlocked) {
         l |= 1L << el.id;
      }

      compound.setLong("unlocked", l);
      if (this.selected1 != null) {
         compound.setInteger("sel1", this.selected1.id);
      } else {
         compound.setInteger("sel1", -1);
      }

      if (this.selected2 != null) {
         compound.setInteger("sel2", this.selected2.id);
      } else {
         compound.setInteger("sel2", -1);
      }

      compound.setInteger("frame", this.firstFrame);
      if (this.result != null) {
         compound.setInteger("result", this.result.id);
      } else {
         compound.setInteger("result", -1);
      }

      if (this.question != null) {
         compound.setInteger("question", this.question.id);
      } else {
         compound.setInteger("question", -1);
      }
   }

   public void read(NBTTagCompound compound) {
      if (compound.hasKey("unlocked")) {
         this.unlocked.clear();
         long l = compound.getLong("unlocked");

         for (SeaElement el : all) {
            if ((l & 1L << el.id) > 0L) {
               this.unlocked.add(el);
            }
         }
      }

      if (compound.hasKey("sel1")) {
         this.selected1 = this.byId(compound.getInteger("sel1"));
      }

      if (compound.hasKey("sel2")) {
         this.selected2 = this.byId(compound.getInteger("sel2"));
      }

      if (compound.hasKey("frame")) {
         this.firstFrame = compound.getInteger("frame");
      }

      if (compound.hasKey("result")) {
         this.result = this.byId(compound.getInteger("result"));
      }

      if (compound.hasKey("question")) {
         this.question = this.byId(compound.getInteger("question"));
      }
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

   public static SeaElement tryMatch(SeaElement elem1, SeaElement elem2) {
      for (SeaElement el : all) {
         if (el.need1 == elem1 && el.need2 == elem2) {
            return el;
         }

         if (el.need2 == elem1 && el.need1 == elem2) {
            return el;
         }
      }

      return null;
   }

   public boolean isElementUnlocked(SeaElement elem) {
      return this.unlocked.contains(elem);
   }

   public SeaElement byId(int id) {
      if (id < 0) {
         return null;
      } else {
         for (SeaElement el : all) {
            if (id == el.id) {
               return el;
            }
         }

         return null;
      }
   }

   public static class SeaElement {
      public final String name;
      public final int id;
      public SeaElement need1;
      public SeaElement need2;
      public int texX;
      public int texY;

      public SeaElement(String name, int id) {
         this.name = name;
         this.id = id;
         int i = id / 8;
         this.texX = id * 32 - i * 256;
         this.texY = i * 32;
      }

      public SeaElement setRecipe(SeaElement a, SeaElement b) {
         this.need1 = a;
         this.need2 = b;
         return this;
      }
   }
}
