package com.vivern.arpg.tileentity;

import com.vivern.arpg.container.ContainerResearchTable;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.NBTHelper;
import com.vivern.arpg.main.OreDicHelper;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Spell;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.network.PacketTFRPuzzleToClients;
import com.vivern.arpg.recipes.ExploringField;
import com.vivern.arpg.recipes.TFRTutorial;
import com.vivern.arpg.recipes.TerraformingPlayerCommand;
import com.vivern.arpg.recipes.TerraformingResearchPuzzle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileResearchTable extends TileEntityLockableLoot implements TileEntityClicked, ITileEntitySynchronize, ITickable {
   public NonNullList<ItemStack> stacks = NonNullList.withSize(4, ItemStack.EMPTY);
   public float[] theoreticElements = new float[12];
   public float[] appointedElements = new float[12];
   public int specialization;
   public float rotation;
   public TerraformingResearchPuzzle tfrPuzzle;
   public TerraformingPlayerCommand puzzleCommand;
   public WriteBlank.WriteBlankSpell[] blanks;
   public int rendertfrPosX;
   public int rendertfrPosY;
   public int rendertfrScale = 100;
   public static HashMap<Integer, Phenomenon> phenomenonById = new HashMap<>();
   public static HashMap<Integer, Phenomenon> phenomenonByData = new HashMap<>();
   public static Phenomenon FIRE = new Phenomenon(ShardType.FIRE);
   public static Phenomenon EARTH = new Phenomenon(ShardType.EARTH);
   public static Phenomenon WATER = new Phenomenon(ShardType.WATER);
   public static Phenomenon AIR = new Phenomenon(ShardType.AIR);
   public static Phenomenon POISON = new Phenomenon(ShardType.POISON);
   public static Phenomenon COLD = new Phenomenon(ShardType.COLD);
   public static Phenomenon ELECTRIC = new Phenomenon(ShardType.ELECTRIC);
   public static Phenomenon VOID = new Phenomenon(ShardType.VOID);
   public static Phenomenon PLEASURE = new Phenomenon(ShardType.PLEASURE);
   public static Phenomenon PAIN = new Phenomenon(ShardType.PAIN);
   public static Phenomenon DEATH = new Phenomenon(ShardType.DEATH);
   public static Phenomenon LIVE = new Phenomenon(ShardType.LIVE);
   public static Phenomenon LAVA = new Phenomenon("lava", ShardType.FIRE, ShardType.EARTH);
   public static Phenomenon STEAM = new Phenomenon("steam", ShardType.FIRE, ShardType.WATER);
   public static Phenomenon RAIN = new Phenomenon("rain", ShardType.WATER, ShardType.AIR);
   public static Phenomenon SALTS = new Phenomenon("salts", ShardType.EARTH, ShardType.POISON);
   public static Phenomenon FROSTBURN = new Phenomenon("frostburn", ShardType.COLD, ShardType.POISON);
   public static Phenomenon LIGHTNING = new Phenomenon("lightning", ShardType.ELECTRIC, ShardType.AIR);
   public static Phenomenon STREAM = new Phenomenon("stream", ShardType.WATER, ShardType.ELECTRIC);
   public static Phenomenon FLYING = new Phenomenon("flying", ShardType.AIR, ShardType.LIVE);
   public static Phenomenon FIREPLACE = new Phenomenon("fireplace", ShardType.FIRE, ShardType.PLEASURE);
   public static Phenomenon ANNIHILATION = new Phenomenon("annihilation", ShardType.DEATH, ShardType.VOID);
   public static Phenomenon SHADOW = new Phenomenon("shadow", ShardType.COLD, ShardType.VOID);
   public static Phenomenon BLOOD = new Phenomenon("blood", ShardType.WATER, ShardType.PAIN);
   public static Phenomenon REPTILE = new Phenomenon("reptile", ShardType.LIVE, ShardType.POISON);
   public static Phenomenon GRAVE = new Phenomenon("grave", ShardType.EARTH, ShardType.DEATH);
   public static Phenomenon DRINK = new Phenomenon("drink", ShardType.WATER, ShardType.PLEASURE);
   public static Phenomenon METAL = new Phenomenon("metal", ShardType.ELECTRIC, ShardType.EARTH);
   public static Phenomenon GAMES = new Phenomenon("games", ShardType.ELECTRIC, ShardType.PLEASURE);
   public static Phenomenon POTION = new Phenomenon("potion", ShardType.WATER, ShardType.POISON);
   public static Phenomenon CRYSTAL = new Phenomenon("crystal", ShardType.COLD, ShardType.EARTH, ShardType.WATER);
   public static Phenomenon GENOCIDE = new Phenomenon("genocide", ShardType.DEATH, ShardType.POISON);
   public static Phenomenon RADIATION = new Phenomenon("radiation", ShardType.POISON, ShardType.VOID);
   public static Phenomenon ASH = new Phenomenon("ash", ShardType.FIRE, ShardType.DEATH);
   public static Phenomenon HAPPINESS = new Phenomenon("happines", ShardType.LIVE, ShardType.PLEASURE);
   public static Phenomenon UNHAPPINESS = new Phenomenon("unhappines", ShardType.LIVE, ShardType.PAIN);
   public static Phenomenon MUD = new Phenomenon("mud", ShardType.WATER, ShardType.EARTH);
   public static Phenomenon GROWING = new Phenomenon("growing", ShardType.WATER, ShardType.EARTH, ShardType.LIVE);
   public static Phenomenon GAS = new Phenomenon("gas", ShardType.AIR, ShardType.POISON);
   public static Phenomenon MOUNTAIN = new Phenomenon("mountain", ShardType.COLD, ShardType.EARTH);
   public static Phenomenon ICE = new Phenomenon("ice", ShardType.COLD, ShardType.WATER);
   public static Phenomenon RAINBOW = new Phenomenon(
      "rainbow", ShardType.FIRE, ShardType.VOID, ShardType.WATER, ShardType.AIR
   );
   public static Phenomenon LIGHT = new Phenomenon("light", ShardType.FIRE, ShardType.VOID);
   public static Phenomenon FROSTBITE = new Phenomenon("frostbite", ShardType.COLD, ShardType.PAIN);
   public static Phenomenon CORRUPTION = new Phenomenon("corruption", ShardType.POISON, ShardType.PAIN);
   public static Phenomenon AURORA = new Phenomenon("aurora", ShardType.ELECTRIC, ShardType.COLD);
   public static Phenomenon ENDER = new Phenomenon("ender", ShardType.VOID, ShardType.LIVE);
   public static Phenomenon NECROMANCY = new Phenomenon("necromancy", ShardType.DEATH, ShardType.ELECTRIC);
   public static Phenomenon PLASMA = new Phenomenon("plasma", ShardType.FIRE, ShardType.ELECTRIC);
   public static Phenomenon WEALTH = new Phenomenon("wealth", ShardType.EARTH, ShardType.PLEASURE);
   public static Phenomenon GREED = new Phenomenon("greed", ShardType.EARTH, ShardType.PLEASURE, ShardType.POISON);
   public static Phenomenon STINK = new Phenomenon("stink", ShardType.AIR, ShardType.PAIN);
   public static Phenomenon WEAPON = new Phenomenon("weapon", ShardType.EARTH, ShardType.PAIN);
   public static Phenomenon DUST = new Phenomenon("dust", ShardType.EARTH, ShardType.AIR);
   public static Phenomenon AFTERWORLD = new Phenomenon("afterworld", ShardType.DEATH, ShardType.PLEASURE);
   public static Phenomenon TORTURE = new Phenomenon("torture", ShardType.PAIN, ShardType.ELECTRIC);
   public static Phenomenon NARCOTIC = new Phenomenon("narcotic", ShardType.POISON, ShardType.PLEASURE);
   public static Phenomenon ACID = new Phenomenon("acid", ShardType.FIRE, ShardType.POISON);
   public static Phenomenon FISH = new Phenomenon("fish", ShardType.WATER, ShardType.LIVE);
   public static Phenomenon MEDICINE = new Phenomenon("medicine", ShardType.POISON, ShardType.PLEASURE, ShardType.LIVE);
   public static Phenomenon GOLEM = new Phenomenon("golem", ShardType.LIVE, ShardType.EARTH);
   public static Phenomenon TORNADO = new Phenomenon("tornado", ShardType.DEATH, ShardType.AIR);
   public static Phenomenon DEMON = new Phenomenon("demon", ShardType.LIVE, ShardType.FIRE);
   public static Phenomenon RELAXATION = new Phenomenon("relaxation", ShardType.COLD, ShardType.PLEASURE);
   public static Phenomenon FOSSIL = new Phenomenon("fossil", ShardType.DEATH, ShardType.WATER, ShardType.EARTH);
   public static Phenomenon SOUL = new Phenomenon("soul", ShardType.LIVE, ShardType.ELECTRIC, ShardType.AIR);
   public static Phenomenon GHOST = new Phenomenon("ghost", ShardType.DEATH, ShardType.ELECTRIC, ShardType.AIR);
   public static Phenomenon THUNDERSTORM = new Phenomenon(
      "thunderstorm", ShardType.WATER, ShardType.ELECTRIC, ShardType.AIR
   );
   public static Phenomenon RELIGION = new Phenomenon(
      "religion", ShardType.DEATH, ShardType.LIVE, ShardType.PAIN, ShardType.PLEASURE
   );
   public static Phenomenon SOUND = new Phenomenon("sound", ShardType.AIR, ShardType.VOID);
   public static Phenomenon MUSIC = new Phenomenon("music", ShardType.AIR, ShardType.VOID, ShardType.PLEASURE);
   public static Phenomenon PLANET = new Phenomenon("planet", ShardType.EARTH, ShardType.VOID);
   public static Phenomenon OCEANIC_PLANET = new Phenomenon(
      "oceanic_planet", ShardType.EARTH, ShardType.VOID, ShardType.WATER
   );
   public static Phenomenon GAS_GIANT = new Phenomenon("gas_giant", ShardType.EARTH, ShardType.VOID, ShardType.AIR);
   public static Phenomenon TOXIC_PLANET = new Phenomenon("toxic_planet", ShardType.EARTH, ShardType.VOID, ShardType.POISON);
   public static Phenomenon INHABITED_PLANET = new Phenomenon(
      "inhabited_planet", ShardType.EARTH, ShardType.VOID, ShardType.LIVE
   );
   public static Phenomenon STAR = new Phenomenon("star", ShardType.EARTH, ShardType.VOID, ShardType.FIRE);
   public static Phenomenon MAGNETAR = new Phenomenon("magnetar", ShardType.EARTH, ShardType.VOID, ShardType.ELECTRIC);
   public static Phenomenon MOONS = new Phenomenon("moons", ShardType.EARTH, ShardType.VOID, ShardType.PLEASURE);
   public static Phenomenon COMET = new Phenomenon("comet", ShardType.EARTH, ShardType.VOID, ShardType.COLD);
   public static Phenomenon ASTEROID = new Phenomenon("asteroid", ShardType.EARTH, ShardType.VOID, ShardType.PAIN);
   public static Phenomenon BLACK_HOLE = new Phenomenon("black_hole", ShardType.EARTH, ShardType.VOID, ShardType.DEATH);
   public static Phenomenon FRACTITE = new Phenomenon("fractite", ShardType.LIVE, ShardType.COLD);
   public static Phenomenon QUAZAR = new Phenomenon(
      "quazar", ShardType.EARTH, ShardType.VOID, ShardType.DEATH, ShardType.FIRE
   );
   public static Phenomenon WHITE_DWARF = new Phenomenon(
      "white_dwarf", ShardType.EARTH, ShardType.VOID, ShardType.FIRE, ShardType.COLD
   );
   public static Phenomenon CONSTELLATION = new Phenomenon(
      "constellation", ShardType.EARTH, ShardType.VOID, ShardType.FIRE, ShardType.PLEASURE
   );
   public static Phenomenon STELLAR_WIND = new Phenomenon("stellar_wind", ShardType.VOID, ShardType.AIR, ShardType.FIRE);
   public static int startPhenomenonId = 1;

   public static boolean isInfiniteWriteEnabled() {
      return Debugger.floats[10] == 10.0F;
   }

   public void update() {
      if (this.specialization == 2 && this.tfrPuzzle != null) {
         this.tfrPuzzle.update(this.puzzleCommand);
         this.puzzleCommand = null;
         if (this.tfrPuzzle.ISWINNED) {
            this.tfrPuzzle = null;
         }
      }
   }

   public void resetPuzzle() {
      this.tfrPuzzle = null;
      this.puzzleCommand = null;
      this.theoreticElements = new float[12];
      this.appointedElements = new float[12];
   }

   public void openTable(EntityPlayer player) {
      if (!this.world.isRemote && this.specialization == 2) {
         NBTTagCompound exploringNbtTag = ExploringField.getExploringTagCompound(player);
         int key = ExploringField.getKey(exploringNbtTag);
         if (key < 0) {
            return;
         }

         int bitshift = ExploringField.getBitshift(exploringNbtTag);
         if (this.tfrPuzzle == null || this.tfrPuzzle.whatResearchKey != key || this.tfrPuzzle.whatResearchBitshift != bitshift) {
            if (key == 20 && bitshift == 0) {
               this.tfrPuzzle = TFRTutorial.genTutorial1(key, bitshift);
               this.tfrPuzzle.researchingPlayer = player;
            } else if (key == 20 && bitshift == 1) {
               this.tfrPuzzle = TFRTutorial.genTutorial2(key, bitshift);
               this.tfrPuzzle.researchingPlayer = player;
            } else if (key == 20 && bitshift == 2) {
               this.tfrPuzzle = TFRTutorial.genTutorial3(key, bitshift);
               this.tfrPuzzle.researchingPlayer = player;
            } else if (key == 20 && bitshift == 3) {
               this.tfrPuzzle = TFRTutorial.genTutorial4(key, bitshift);
               this.tfrPuzzle.researchingPlayer = player;
            } else if (key == 20 && bitshift == 4) {
               this.tfrPuzzle = TFRTutorial.genTutorial5(key, bitshift);
               this.tfrPuzzle.researchingPlayer = player;
            } else {
               if (key < 0) {
                  return;
               }

               this.tfrPuzzle = new TerraformingResearchPuzzle(10 + (int)Debugger.floats[5], 10 + (int)Debugger.floats[6]);
               this.tfrPuzzle.whatResearchKey = key;
               this.tfrPuzzle.whatResearchBitshift = bitshift;
               this.tfrPuzzle.generate((key << 8 | bitshift) ^ this.world.getSeed(), ExploringField.generateExploringField(exploringNbtTag));
               this.tfrPuzzle.researchingPlayer = player;
            }
         }

         if (player instanceof EntityPlayerMP) {
            PacketTFRPuzzleToClients packet = new PacketTFRPuzzleToClients();
            NBTTagCompound tag = new NBTTagCompound();
            this.tfrPuzzle.writeToNbt(tag);
            packet.write(tag);
            PacketHandler.sendTo(packet, (EntityPlayerMP)player);
         }
      }
   }

   public float getElementCount(boolean theoretic, ShardType element) {
      return theoretic ? this.theoreticElements[element.id - 1] : this.appointedElements[element.id - 1];
   }

   public void setElementCount(boolean theoretic, ShardType element, float amount) {
      if (theoretic) {
         this.theoreticElements[element.id - 1] = amount;
      } else {
         this.appointedElements[element.id - 1] = amount;
      }
   }

   public void addElementCount(boolean theoretic, ShardType element, float amount) {
      if (theoretic) {
         this.theoreticElements[element.id - 1] = this.theoreticElements[element.id - 1] + amount;
      } else {
         this.appointedElements[element.id - 1] = this.appointedElements[element.id - 1] + amount;
      }
   }

   @Override
   public void onClient(double... args) {
   }

   @Override
   public void onServer(EntityLivingBase playerSends, double... args) {
      if (this.specialization == 3) {
         if (args.length <= 0 || args.length > 64) {
            ItemStack ink = this.getStackInSlot(3);
            if (ink.getItem() == ItemsRegister.INK) {
               ink.setItemDamage(ink.getItemDamage() + 1);
               if (ink.getItemDamage() > ink.getMaxDamage()) {
                  ink.shrink(1);
               }
            }
         } else if (checkInventoryForRollResources(this) && playerSends instanceof EntityPlayer) {
            Spell[] spells = new Spell[args.length];
            NBTTagCompound playerSpells = ExploringField.getExploringTagCompound((EntityPlayer)playerSends);

            for (int i = 0; i < args.length; i++) {
               Spell spell = Spell.spellsRegistry.get((int)args[i]);
               if (spell == null || !ExploringField.isExplored(playerSpells, spell) && !isInfiniteWriteEnabled()) {
                  return;
               }

               spells[i] = spell;
            }

            ItemStack newRoll = new ItemStack(ItemsRegister.SPELLROLL);
            if (!newRoll.hasTagCompound()) {
               NBTTagCompound itemCompound = new NBTTagCompound();
               newRoll.setTagCompound(itemCompound);
            }

            NBTHelper.writeSpellsToNbt(newRoll, spells);
            this.setInventorySlotContents(2, newRoll);
            this.decrRollResources();
         }
      }
   }

   public static boolean checkInventoryForRollResources(IInventory inventory) {
      return inventory.getStackInSlot(2).isEmpty()
            && OreDicHelper.itemStringOredigMatches(inventory.getStackInSlot(0), "stickWood")
            && OreDicHelper.itemStringOredigMatches(inventory.getStackInSlot(1), "paper")
         ? checkInkInSlot(inventory)
         : false;
   }

   public static boolean checkInkInSlot(IInventory inventory) {
      ItemStack ink = inventory.getStackInSlot(3);
      return ink.getItem() == ItemsRegister.INK;
   }

   public void decrRollResources() {
      this.decrStackSize(0, 1);
      this.decrStackSize(1, 1);
   }

   @Override
   public void mouseClick(int mouseX, int mouseY, int mouseButton) {
      if (this.specialization == 2 && mouseX > 235 && mouseY > 174 && mouseX < 249 && mouseY < 188) {
         this.resetPuzzle();
      }

      if (this.specialization == 3 && mouseX >= 189 && mouseY >= 147 && mouseX <= 218 && mouseY <= 165) {
      }

      if (mouseX >= 7 && mouseX <= 18 && mouseY >= 38 && mouseY <= 49) {
      }

      if (mouseX >= 7 && mouseX <= 29 && mouseY >= 77 && mouseY <= 116) {
      }

      if (mouseX >= 178 && mouseX <= 216 && mouseY >= 7 && mouseY <= 29) {
      }
   }

   public void write(NBTTagCompound compound) {
      ItemStackHelper.saveAllItems(compound, this.stacks);
      compound.setInteger("specialization", this.specialization);
      compound.setFloat("rotation", this.rotation);
   }

   public void read(NBTTagCompound compound) {
      this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.stacks);
      if (compound.hasKey("specialization")) {
         this.specialization = compound.getInteger("specialization");
      }

      if (compound.hasKey("rotation")) {
         this.rotation = compound.getFloat("rotation");
      }
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      this.write(compound);
      if (this.specialization == 2 && this.tfrPuzzle != null) {
         NBTTagCompound tfrPuzzleTag = new NBTTagCompound();
         this.tfrPuzzle.writeToNbt(tfrPuzzleTag);
         compound.setTag("researchPuzzle", tfrPuzzleTag);
      }

      return super.writeToNBT(compound);
   }

   public void readFromNBT(NBTTagCompound compound) {
      this.read(compound);
      if (this.specialization == 2 && compound.hasKey("researchPuzzle")) {
         NBTTagCompound tfrPuzzleTag = compound.getCompoundTag("researchPuzzle");
         this.tfrPuzzle = new TerraformingResearchPuzzle();
         this.tfrPuzzle.readFromNbt(tfrPuzzleTag, false);
      }

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

   public int getSizeInventory() {
      return 4;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.stacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public String getName() {
      return "tile_research_table";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      return new ContainerResearchTable(playerInventory, this);
   }

   public String getGuiID() {
      return "arpg.research_table";
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }

   public int getField(int id) {
      switch (id) {
         case 0:
            return this.getPos().getX();
         case 1:
            return this.getPos().getY();
         case 2:
            return this.getPos().getZ();
         case 3:
            return this.specialization;
         case 4:
            return this.rendertfrPosX;
         case 5:
            return this.rendertfrPosY;
         case 6:
            return this.rendertfrScale;
         default:
            return 0;
      }
   }

   public void setField(int id, int value) {
      switch (id) {
         case 3:
            this.specialization = value;
         case 4:
            this.rendertfrPosX = value;
         case 5:
            this.rendertfrPosY = value;
         case 6:
            this.rendertfrScale = value;
      }
   }

   public static MoveLayout getPhenomenonMoveLayout(
      HypothesisBoard board, HypothesisCell cellFrom, Phenomenon phenomenonMoves, boolean reverseTime
   ) {
      MoveLayout moveLayoutMain = null;

      for (ShardType element : ShardType.registry) {
         if (phenomenonMoves.hasElement(element)) {
            MoveLayout layout = getElementMoveLayout(element, board, cellFrom, phenomenonMoves);
            if (moveLayoutMain == null) {
               moveLayoutMain = layout;
            } else {
               moveLayoutMain.combineWithAND(layout, board);
            }
         }
      }

      if (cellFrom.type == 1 || reverseTime) {
         for (int i = 0; i < 8; i++) {
            HypothesisWay travel = cellFrom.getLinked(i);
            if (travel != null && travel.canWalktrough(null, phenomenonMoves, board)) {
               CellPos posTo = travel.getPosBasedOnDirection(i);
               HypothesisCell cellTo = board.getCell(posTo);
               if (cellTo != null && cellTo.phenomenon != null) {
                  moveLayoutMain.add(posTo);
               }
            }
         }
      }

      if (!phenomenonMoves.isRaw) {
         for (int ix = 0; ix < 8; ix++) {
            HypothesisWay travel = cellFrom.getLinked(ix);
            if (travel != null && travel.canWalktrough(null, phenomenonMoves, board)) {
               CellPos posTo = travel.getPosBasedOnDirection(ix);
               HypothesisCell cellTo = board.getCell(posTo);
               if (cellTo != null && cellTo.type == 1 && cellTo.mark != null) {
                  moveLayoutMain.add(posTo);
               }
            }
         }
      }

      return moveLayoutMain;
   }

   public static MoveLayout getElementMoveLayout(
      ShardType element, HypothesisBoard board, HypothesisCell cellFrom, Phenomenon phenomenonMoves
   ) {
      int boardHighestSize = Math.max(board.sizeHoriz, board.sizeVert);
      MoveLayout layout = new MoveLayout(board);
      if (element == ShardType.FIRE) {
         for (int i = 0; i < 8; i++) {
            HypothesisWay travel = cellFrom.getLinked(i);
            if (i % 2 == 0) {
               for (int c = 0; travel != null && travel.canWalktrough(element, phenomenonMoves, board); c++) {
                  CellPos posTo = travel.getPosBasedOnDirection(i);
                  if (c >= boardHighestSize || layout.contains(posTo)) {
                     break;
                  }

                  if (board.canStand(posTo, phenomenonMoves)) {
                     layout.add(posTo);
                     travel = board.getCell(posTo).getLinked(i);
                  }
               }
            } else if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posTox = travel.getPosBasedOnDirection(i);
               if (board.canStand(posTox, phenomenonMoves)) {
                  layout.add(posTox);
               }
            }
         }
      }

      if (element == ShardType.COLD) {
         for (int ix = 0; ix < 8; ix++) {
            HypothesisWay travel = cellFrom.getLinked(ix);
            if (ix % 2 == 1) {
               for (int c = 0; travel != null && travel.canWalktrough(element, phenomenonMoves, board); c++) {
                  CellPos posTox = travel.getPosBasedOnDirection(ix);
                  if (c >= boardHighestSize || layout.contains(posTox)) {
                     break;
                  }

                  if (board.canStand(posTox, phenomenonMoves)) {
                     layout.add(posTox);
                     travel = board.getCell(posTox).getLinked(ix);
                  }
               }
            } else if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posToxx = travel.getPosBasedOnDirection(ix);
               if (board.canStand(posToxx, phenomenonMoves)) {
                  layout.add(posToxx);
               }
            }
         }
      }

      if (element == ShardType.EARTH) {
         for (int ixx = 0; ixx < 8; ixx += 2) {
            HypothesisWay travel = cellFrom.getLinked(ixx);
            if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posToxx = travel.getPosBasedOnDirection(ixx);
               if (board.canStand(posToxx, phenomenonMoves)) {
                  layout.add(posToxx);
               }
            }
         }
      }

      if (element == ShardType.WATER) {
         for (int ixxx = 0; ixxx < 8; ixxx++) {
            HypothesisWay travel = cellFrom.getLinked(ixxx);

            for (int c = 0; travel != null && travel.canWalktrough(element, phenomenonMoves, board); c++) {
               CellPos posToxx = travel.getPosBasedOnDirection(ixxx);
               if (c > 1 || layout.contains(posToxx)) {
                  break;
               }

               if (board.canStand(posToxx, phenomenonMoves)) {
                  layout.add(posToxx);
                  travel = board.getCell(posToxx).getLinked(ixxx);
               }
            }
         }
      }

      if (element == ShardType.AIR) {
         for (int ixxx = 0; ixxx < 8; ixxx++) {
            HypothesisWay travel = cellFrom.getLinked(ixxx);

            for (int c = 0; travel != null && travel.canWalktrough(element, phenomenonMoves, board); c++) {
               CellPos posToxxx = travel.getPosBasedOnDirection(ixxx);
               if (c >= boardHighestSize || layout.contains(posToxxx)) {
                  break;
               }

               if (board.canStand(posToxxx, phenomenonMoves)) {
                  layout.add(posToxxx);
                  travel = board.getCell(posToxxx).getLinked(ixxx);
               }
            }
         }
      }

      if (element == ShardType.ELECTRIC) {
         for (int ixxx = 1; ixxx < 8; ixxx += 2) {
            HypothesisWay travel = cellFrom.getLinked(ixxx);
            if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posToxxxx = travel.getPosBasedOnDirection(ixxx);
               if (board.canStand(posToxxxx, phenomenonMoves)) {
                  layout.add(posToxxxx);
               }
            }
         }
      }

      if (element == ShardType.POISON) {
         for (int ixxxx = 0; ixxxx < 8; ixxxx++) {
            HypothesisWay travel = cellFrom.getLinked(ixxxx);
            if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posToxxxx = travel.getPosBasedOnDirection(ixxxx);
               if (board.canStand(posToxxxx, phenomenonMoves)) {
                  layout.add(posToxxxx);
               }
            }
         }
      }

      if (element == ShardType.VOID) {
         for (int x = 0; x < board.sizeHoriz; x++) {
            for (int y = 0; y < board.sizeVert; y++) {
               HypothesisCell cell = board.cells[x][y];
               if (cell != null && board.canStand(cell.pos, phenomenonMoves)) {
                  boolean addToList = true;

                  for (int ixxxxx = 0; ixxxxx < 8; ixxxxx++) {
                     HypothesisWay way = cell.getLinked(ixxxxx);
                     if (way != null) {
                        HypothesisCell cellTo = board.getCell(way.getPosBasedOnDirection(ixxxxx));
                        if (cellTo != null
                           && cellTo.phenomenon != null
                           && cellTo.phenomenon.hasElementsExcept(ShardType.VOID)
                           && !cellTo.pos.equals(cellFrom.pos)) {
                           addToList = false;
                           break;
                        }
                     }
                  }

                  if (addToList) {
                     layout.add(cell.pos);
                  }
               }
            }
         }
      }

      if (element == ShardType.PAIN) {
         for (int ixxxxxx = 0; ixxxxxx < 8; ixxxxxx++) {
            HypothesisWay travel = cellFrom.getLinked(ixxxxxx);
            if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posToxxxx = travel.getPosBasedOnDirection(ixxxxxx);
               if (board.canStand(posToxxxx, phenomenonMoves)) {
                  layout.add(posToxxxx);
               }
            }
         }
      }

      if (element == ShardType.PLEASURE) {
         for (int ixxxxxxx = 0; ixxxxxxx < 8; ixxxxxxx++) {
            HypothesisWay travel = cellFrom.getLinked(ixxxxxxx);
            if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posToxxxx = travel.getPosBasedOnDirection(ixxxxxxx);
               if (board.canStand(posToxxxx, phenomenonMoves)) {
                  layout.add(posToxxxx);
               }
            }
         }
      }

      if (element == ShardType.DEATH) {
         for (int ixxxxxxxx = 0; ixxxxxxxx < 8; ixxxxxxxx++) {
            HypothesisWay travel = cellFrom.getLinked(ixxxxxxxx);
            if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posToxxxx = travel.getPosBasedOnDirection(ixxxxxxxx);
               HypothesisCell cellMid = board.getCell(posToxxxx);
               if (cellMid != null) {
                  for (int dirRot = -1; dirRot <= 1; dirRot++) {
                     int rotatedDir = HypothesisBoard.addDirection(ixxxxxxxx, dirRot);
                     travel = cellMid.getLinked(rotatedDir);
                     if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
                        posToxxxx = travel.getPosBasedOnDirection(rotatedDir);
                        if (board.canStand(posToxxxx, phenomenonMoves)) {
                           layout.add(posToxxxx);
                        }
                     }
                  }
               }
            }
         }
      }

      if (element == ShardType.LIVE) {
         for (int ixxxxxxxxx = 0; ixxxxxxxxx < 8; ixxxxxxxxx++) {
            HypothesisWay travel = cellFrom.getLinked(ixxxxxxxxx);
            if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
               CellPos posToxxxx = travel.getPosBasedOnDirection(ixxxxxxxxx);
               HypothesisCell cellMid = board.getCell(posToxxxx);
               if (cellMid != null) {
                  for (int dirRotx = -1; dirRotx <= 1; dirRotx++) {
                     int rotatedDir = HypothesisBoard.addDirection(ixxxxxxxxx, dirRotx);
                     travel = cellMid.getLinked(rotatedDir);
                     if (travel != null && travel.canWalktrough(element, phenomenonMoves, board)) {
                        posToxxxx = travel.getPosBasedOnDirection(rotatedDir);
                        if (board.canStand(posToxxxx, phenomenonMoves)) {
                           layout.add(posToxxxx);
                        }
                     }
                  }
               }
            }
         }
      }

      return layout;
   }

   public static void registerPhenomenons() {
      registerPhenomenon(FIRE);
      registerPhenomenon(EARTH);
      registerPhenomenon(WATER);
      registerPhenomenon(AIR);
      registerPhenomenon(POISON);
      registerPhenomenon(COLD);
      registerPhenomenon(ELECTRIC);
      registerPhenomenon(VOID);
      registerPhenomenon(PLEASURE);
      registerPhenomenon(PAIN);
      registerPhenomenon(DEATH);
      registerPhenomenon(LIVE);
      registerPhenomenon(LAVA);
      registerPhenomenon(STEAM);
      registerPhenomenon(RAIN);
      registerPhenomenon(SALTS);
      registerPhenomenon(FROSTBURN);
      registerPhenomenon(LIGHTNING);
      registerPhenomenon(STREAM);
      registerPhenomenon(FLYING);
      registerPhenomenon(FIREPLACE);
      registerPhenomenon(ANNIHILATION);
      registerPhenomenon(SHADOW);
      registerPhenomenon(BLOOD);
      registerPhenomenon(REPTILE);
      registerPhenomenon(GRAVE);
      registerPhenomenon(DRINK);
      registerPhenomenon(METAL);
      registerPhenomenon(GAMES);
      registerPhenomenon(POTION);
      registerPhenomenon(CRYSTAL);
      registerPhenomenon(GENOCIDE);
      registerPhenomenon(RADIATION);
      registerPhenomenon(ASH);
      registerPhenomenon(HAPPINESS);
      registerPhenomenon(UNHAPPINESS);
      registerPhenomenon(MUD);
      registerPhenomenon(GROWING);
      registerPhenomenon(GAS);
      registerPhenomenon(MOUNTAIN);
      registerPhenomenon(ICE);
      registerPhenomenon(RAINBOW);
      registerPhenomenon(LIGHT);
      registerPhenomenon(FROSTBITE);
      registerPhenomenon(CORRUPTION);
      registerPhenomenon(AURORA);
      registerPhenomenon(ENDER);
      registerPhenomenon(NECROMANCY);
      registerPhenomenon(PLASMA);
      registerPhenomenon(WEALTH);
      registerPhenomenon(GREED);
      registerPhenomenon(STINK);
      registerPhenomenon(WEAPON);
      registerPhenomenon(DUST);
      registerPhenomenon(AFTERWORLD);
      registerPhenomenon(TORTURE);
      registerPhenomenon(NARCOTIC);
      registerPhenomenon(ACID);
      registerPhenomenon(FISH);
      registerPhenomenon(MEDICINE);
      registerPhenomenon(GOLEM);
      registerPhenomenon(TORNADO);
      registerPhenomenon(DEMON);
      registerPhenomenon(RELAXATION);
      registerPhenomenon(FOSSIL);
      registerPhenomenon(SOUL);
      registerPhenomenon(GHOST);
      registerPhenomenon(THUNDERSTORM);
      registerPhenomenon(RELIGION);
      registerPhenomenon(SOUND);
      registerPhenomenon(MUSIC);
      registerPhenomenon(PLANET);
      registerPhenomenon(OCEANIC_PLANET);
      registerPhenomenon(GAS_GIANT);
      registerPhenomenon(TOXIC_PLANET);
      registerPhenomenon(INHABITED_PLANET);
      registerPhenomenon(STAR);
      registerPhenomenon(MAGNETAR);
      registerPhenomenon(MOONS);
      registerPhenomenon(COMET);
      registerPhenomenon(ASTEROID);
      registerPhenomenon(BLACK_HOLE);
      registerPhenomenon(FRACTITE);
      registerPhenomenon(QUAZAR);
      registerPhenomenon(WHITE_DWARF);
      registerPhenomenon(CONSTELLATION);
      registerPhenomenon(STELLAR_WIND);
   }

   public static void registerPhenomenon(Phenomenon phenomenon) {
      phenomenon.id = startPhenomenonId;
      phenomenonById.put(phenomenon.id, phenomenon);
      phenomenonByData.put(phenomenon.data, phenomenon);
      startPhenomenonId++;
   }

   @Nullable
   public static Phenomenon getPhenomenon(ShardType... elements) {
      int data = 0;

      for (ShardType element : elements) {
         int elemBit = 1 << element.id;
         if ((data & elemBit) != 0) {
            return null;
         }

         data |= elemBit;
      }

      return phenomenonByData.get(data);
   }

   @Nullable
   public static Phenomenon getPhenomenon(List<ShardType> elements) {
      int data = 0;

      for (ShardType element : elements) {
         int elemBit = 1 << element.id;
         if ((data & elemBit) != 0) {
            return null;
         }

         data |= elemBit;
      }

      return phenomenonByData.get(data);
   }

   @Nullable
   public static Phenomenon getPhenomenon(Phenomenon one, Phenomenon two) {
      if ((one.data & two.data) == 0) {
         int data = one.data | two.data;
         return phenomenonByData.get(data);
      } else {
         return null;
      }
   }

   public static List<ShardType> getIntersectedElements(Phenomenon one, Phenomenon two) {
      List<ShardType> list = new ArrayList<>();

      for (ShardType element : ShardType.registry) {
         if (one.hasElement(element) && two.hasElement(element)) {
            list.add(element);
         }
      }

      return list;
   }

   @Nullable
   public static Phenomenon subtractElements(Phenomenon phenomenon, List<ShardType> toSubtract) {
      int data = phenomenon.data;

      for (ShardType element : toSubtract) {
         int elemBit = 1 << element.id;
         int sub = ~elemBit;
         data &= sub;
      }

      return phenomenonByData.get(data);
   }

   public static class CellPos {
      public int posHoriz;
      public int posVert;

      public CellPos(int posHoriz, int posVert) {
         this.posHoriz = posHoriz;
         this.posVert = posVert;
      }

      public static CellPos fromGuiXY(float sizeBoard, int x, int y) {
         float distanceScale = 18.0F * sizeBoard;
         float halfdistanceScale = 9.0F * sizeBoard;
         int vert = Math.round(y / distanceScale);
         int horiz = Math.round((x - vert * halfdistanceScale) / distanceScale);
         return new CellPos(horiz, vert);
      }

      public float guiXcoord(float sizeBoard) {
         float distanceScale = 18.0F * sizeBoard;
         float halfdistanceScale = 9.0F * sizeBoard;
         return this.guiXcoordFast(distanceScale, halfdistanceScale);
      }

      public float guiXcoordFast(float distanceScale, float halfdistanceScale) {
         return this.posHoriz * distanceScale + this.posVert * halfdistanceScale;
      }

      public float guiYcoord(float sizeBoard) {
         float distanceScale = 18.0F * sizeBoard;
         return this.guiYcoordFast(distanceScale);
      }

      public float guiYcoordFast(float distanceScale) {
         return this.posVert * distanceScale;
      }

      public CellPos offset(int direction) {
         if (direction == 0) {
            return new CellPos(this.posHoriz - 1, this.posVert);
         } else if (direction == 1) {
            return new CellPos(this.posHoriz, this.posVert - 1);
         } else if (direction == 2) {
            return new CellPos(this.posHoriz + 1, this.posVert - 2);
         } else if (direction == 3) {
            return new CellPos(this.posHoriz + 1, this.posVert - 1);
         } else if (direction == 4) {
            return new CellPos(this.posHoriz + 1, this.posVert);
         } else if (direction == 5) {
            return new CellPos(this.posHoriz, this.posVert + 1);
         } else if (direction == 6) {
            return new CellPos(this.posHoriz - 1, this.posVert + 2);
         } else {
            return direction == 7 ? new CellPos(this.posHoriz - 1, this.posVert + 1) : null;
         }
      }

      public CellPos offset(int direction, int amount) {
         if (direction == 0) {
            return new CellPos(this.posHoriz - 1 * amount, this.posVert);
         } else if (direction == 1) {
            return new CellPos(this.posHoriz, this.posVert - 1 * amount);
         } else if (direction == 2) {
            return new CellPos(this.posHoriz + 1 * amount, this.posVert - 2 * amount);
         } else if (direction == 3) {
            return new CellPos(this.posHoriz + 1 * amount, this.posVert - 1 * amount);
         } else if (direction == 4) {
            return new CellPos(this.posHoriz + 1 * amount, this.posVert);
         } else if (direction == 5) {
            return new CellPos(this.posHoriz, this.posVert + 1 * amount);
         } else if (direction == 6) {
            return new CellPos(this.posHoriz - 1 * amount, this.posVert + 2 * amount);
         } else {
            return direction == 7 ? new CellPos(this.posHoriz - 1 * amount, this.posVert + 1 * amount) : null;
         }
      }

      @Override
      public boolean equals(Object obj) {
         return obj instanceof CellPos ? this.equals((CellPos)obj) : false;
      }

      public boolean equals(CellPos obj) {
         return obj != null && obj.posHoriz == this.posHoriz && obj.posVert == this.posVert;
      }
   }

   public static class HypothesisBoard {
      public HypothesisCell[][] cells;
      public int sizeHoriz;
      public int sizeVert;
      int idCells;
      int lastCells;
      @Nullable
      public CellPos selection;
      @Nullable
      public MoveLayout selectedLayout;

      public HypothesisBoard(int sizeHoriz, int sizeVert) {
         this.cells = new HypothesisCell[sizeHoriz][sizeVert];
         this.sizeHoriz = sizeHoriz;
         this.sizeVert = sizeVert;
      }

      public void doReversedStep(Random rand) {
         CellPos randPos = new CellPos(rand.nextInt(this.sizeHoriz), rand.nextInt(this.sizeVert));
         HypothesisCell cell = this.getCell(randPos);
         if (cell != null && cell.phenomenon != null) {
            MoveLayout layout = TileResearchTable.getPhenomenonMoveLayout(this, cell, cell.phenomenon, true);
            List<CellPos> poses = new ArrayList<>();

            for (int x = 0; x < this.sizeHoriz; x++) {
               for (int y = 0; y < this.sizeVert; y++) {
                  if (layout.cells[x][y]) {
                     poses.add(new CellPos(x, y));
                  }
               }
            }

            if (!poses.isEmpty()) {
               this.movePhenomenon(layout, cell, poses.get(rand.nextInt(poses.size())), true);
            }
         }
      }

      public void generateBoard(int seed) {
         Random rand = new Random(seed);
         this.idCells = 0;
         this.lastCells = 20;
         CellPos start = new CellPos(this.sizeHoriz / 2, this.sizeVert / 2);
         float wayChance = 1.0F;
         this.recursiveGenerateBoard(rand, start, wayChance, null, -1);
         int maxAddWays = 15 + rand.nextInt(17) + (int)Debugger.floats[3];

         for (int i = 0; i < maxAddWays; i++) {
            CellPos randPos = new CellPos(rand.nextInt(this.sizeHoriz), rand.nextInt(this.sizeVert));
            HypothesisCell cell = this.getCell(randPos);
            if (cell != null) {
               int randDirection = rand.nextInt(8);
               HypothesisWay way = cell.getLinked(randDirection);
               if (way == null) {
                  int maxlength = 2 + rand.nextInt(5);

                  for (int m = 1; m < maxlength; m++) {
                     CellPos offset = randPos.offset(randDirection, m);
                     HypothesisCell cellOffset = this.getCell(offset);
                     if (cellOffset != null) {
                        int randDirectionOpposite = directionOpposite(randDirection);
                        if (cellOffset.getLinked(randDirectionOpposite) == null) {
                           HypothesisWay newWay = new HypothesisWay(cell.pos, cellOffset.pos, randDirection, (byte)0);
                           cell.linked[randDirection] = newWay;
                           cellOffset.linked[randDirectionOpposite] = newWay;
                        }
                        break;
                     }
                  }
               }
            }
         }

         for (int r = 0; r < Debugger.floats[4]; r++) {
            this.doReversedStep(rand);
         }
      }

      public void recursiveGenerateBoard(
         Random rand, CellPos pos, float wayChance, @Nullable HypothesisCell cellFrom, int generatorDirection
      ) {
         if (this.lastCells > 0) {
            HypothesisCell cell = new HypothesisCell(this.idCells++, pos);
            if (rand.nextFloat() < Debugger.floats[2]) {
               cell.phenomenon = TileResearchTable.phenomenonById.get(rand.nextInt(12) + 1);
            }

            this.setCell(pos, cell);
            this.lastCells--;
            if (cellFrom != null) {
               byte wayType = 0;
               if (rand.nextFloat() < 0.1) {
                  wayType = 1;
               }

               HypothesisWay way = new HypothesisWay(cellFrom.pos, pos, generatorDirection, wayType);
               cell.linked[directionOpposite(generatorDirection)] = way;
               cellFrom.linked[generatorDirection] = way;
            }

            for (int i = 0; i < 8; i++) {
               if (rand.nextFloat() < wayChance && i != directionOpposite(generatorDirection)) {
                  CellPos posoff = pos.offset(i);
                  if (this.getCell(posoff) == null) {
                     this.recursiveGenerateBoard(rand, posoff, wayChance / 2.0F, cell, i);
                  }
               }
            }
         }
      }

      @Nullable
      public HypothesisCell getCell(CellPos position) {
         return position.posHoriz >= 0 && position.posVert >= 0 && position.posHoriz < this.sizeHoriz && position.posVert < this.sizeVert
            ? this.cells[position.posHoriz][position.posVert]
            : null;
      }

      public void setCell(CellPos position, HypothesisCell cell) {
         if (position.posHoriz >= 0 && position.posVert >= 0 && position.posHoriz < this.sizeHoriz && position.posVert < this.sizeVert) {
            this.cells[position.posHoriz][position.posVert] = cell;
         }
      }

      public boolean canStand(CellPos position, Phenomenon phenomenon) {
         HypothesisCell cellTo = this.getCell(position);
         return cellTo != null ? cellTo.canStand(phenomenon) : false;
      }

      public boolean movePhenomenon(
         MoveLayout layout, HypothesisCell cellFrom, CellPos posTo, boolean reverseTime
      ) {
         int boardHighestSize = Math.max(this.sizeHoriz, this.sizeVert);
         Phenomenon phenomenon = cellFrom.phenomenon;
         if (phenomenon != null && layout.contains(posTo)) {
            if (phenomenon.hasElement(ShardType.VOID) || phenomenon.hasElement(ShardType.LIVE) || phenomenon.hasElement(ShardType.DEATH)) {
               return this.setMovedPhenomenon(phenomenon, cellFrom, posTo, reverseTime);
            }

            int i = getDirection(cellFrom.pos, posTo);
            HypothesisWay travel = cellFrom.getLinked(i);

            for (int c = 0; travel != null; c++) {
               CellPos posNext = travel.getPosBasedOnDirection(i);
               phenomenon = travel.onWalktrough(phenomenon);
               if (c > boardHighestSize || posTo.equals(posNext)) {
                  return this.setMovedPhenomenon(phenomenon, cellFrom, posTo, reverseTime);
               }

               travel = this.getCell(posNext).getLinked(i);
            }
         }

         return false;
      }

      public boolean setMovedPhenomenon(
         Phenomenon phenomenon, HypothesisCell cellFrom, CellPos posTo, boolean reverseTime
      ) {
         HypothesisCell cellTo = this.getCell(posTo);
         if (reverseTime) {
            if (cellTo.type == 1 && cellTo.mark != null && !phenomenon.isRaw) {
               return false;
            } else if (cellTo.phenomenon == null) {
               cellFrom.phenomenon = null;
               cellTo.phenomenon = phenomenon;
               return true;
            } else {
               if (cellFrom.type <= 1) {
                  Phenomenon combinedResult = TileResearchTable.getPhenomenon(phenomenon, cellTo.phenomenon);
                  if (combinedResult != null) {
                     cellFrom.phenomenon = null;
                     cellFrom.type = 1;
                     cellFrom.mark = phenomenon;
                     cellTo.phenomenon = combinedResult;
                     return true;
                  }
               }

               return false;
            }
         } else if (cellTo.type == 1 && cellTo.mark != null && !phenomenon.isRaw) {
            if (cellTo.mark != phenomenon) {
               List<ShardType> intersect = TileResearchTable.getIntersectedElements(phenomenon, cellTo.mark);
               Phenomenon intersectionPhenomenon = TileResearchTable.getPhenomenon(intersect);
               if (intersectionPhenomenon != null) {
                  Phenomenon last = TileResearchTable.subtractElements(phenomenon, intersect);
                  if (last != null) {
                     Phenomenon lastMark = TileResearchTable.subtractElements(cellTo.mark, intersect);
                     if (lastMark != null || cellTo.mark.hasAll(intersect)) {
                        cellFrom.phenomenon = last;
                        cellTo.phenomenon = intersectionPhenomenon;
                        cellTo.mark = lastMark;
                        return true;
                     }
                  }
               }
            }

            return false;
         } else if (cellTo.phenomenon == null) {
            cellFrom.phenomenon = null;
            cellTo.phenomenon = phenomenon;
            return true;
         } else {
            Phenomenon combinedResult = TileResearchTable.getPhenomenon(phenomenon, cellTo.phenomenon);
            if (combinedResult != null) {
               cellFrom.phenomenon = null;
               cellFrom.mark = phenomenon;
               cellTo.phenomenon = combinedResult;
               return true;
            } else {
               return false;
            }
         }
      }

      public static int getDirection(CellPos posFrom, CellPos posTo) {
         for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 40; j++) {
               if (posTo.equals(posFrom.offset(i, j))) {
                  return i;
               }
            }
         }

         if (posFrom.posVert == posTo.posVert) {
            return posFrom.posHoriz < posTo.posHoriz ? 4 : 0;
         } else {
            if (posFrom.posVert < posTo.posVert) {
               if (posFrom.posHoriz == posTo.posHoriz) {
                  return 1;
               }

               int rh = posTo.posHoriz - posFrom.posHoriz;
               int rv = posFrom.posVert - posTo.posVert;
               if (rh * 2 == rv) {
                  return 2;
               }

               if (rh == rv) {
                  return 3;
               }
            } else {
               if (posFrom.posHoriz == posTo.posHoriz) {
                  return 5;
               }

               int rhx = posFrom.posHoriz - posTo.posHoriz;
               int rvx = posTo.posVert - posFrom.posVert;
               if (rhx * 2 == rvx) {
                  return 6;
               }

               if (rhx == rvx) {
                  return 7;
               }
            }

            return -1;
         }
      }

      public static int directionOpposite(int direction) {
         if (direction == -1) {
            return -1;
         } else {
            return direction < 4 ? direction + 4 : direction - 4;
         }
      }

      public static int addDirection(int direction, int add) {
         if (add == 0) {
            return direction;
         } else {
            int added = direction + add;
            if (added >= 8) {
               return added % 8;
            } else {
               return added < 0 ? (8 - -added % 8) % 8 : added;
            }
         }
      }
   }

   public static class HypothesisCell {
      public CellPos pos;
      public int id;
      @Nullable
      public Phenomenon phenomenon;
      @Nullable
      public Phenomenon mark;
      public byte type;
      public HypothesisWay[] linked = new HypothesisWay[8];

      public HypothesisCell(int id, CellPos pos) {
         this.id = id;
         this.pos = pos;
      }

      @Nullable
      public HypothesisWay getLinked(int direction) {
         return this.linked[direction];
      }

      public boolean canStand(Phenomenon phenomenon) {
         return this.type != 1 ? this.phenomenon == null : this.phenomenon == null && this.mark == null;
      }

      public Phenomenon onStand(Phenomenon phenomenon) {
         return phenomenon;
      }
   }

   public static class HypothesisWay {
      public CellPos pos1;
      public CellPos pos2;
      public byte wayType;
      public byte wayDirection;

      public HypothesisWay(CellPos posPrev, CellPos posNew, int generatorDirection, byte wayType) {
         if (generatorDirection < 4) {
            this.pos2 = posPrev;
            this.pos1 = posNew;
         } else {
            this.pos1 = posPrev;
            this.pos2 = posNew;
         }

         this.wayDirection = (byte)generatorDirection;
         this.wayType = wayType;
      }

      public CellPos getPosBasedOnDirection(int direction) {
         return direction < 4 ? this.pos1 : this.pos2;
      }

      public boolean canWalktrough(@Nullable ShardType element, Phenomenon allPhenomenon, HypothesisBoard board) {
         return true;
      }

      public Phenomenon onWalktrough(Phenomenon phenomenon) {
         if (this.wayType == 1 && phenomenon.isRaw) {
            ShardType opposite = phenomenon.getAsRaw().opposite();
            return opposite != null ? opposite.getRawPhenomenon() : phenomenon;
         } else {
            return phenomenon;
         }
      }
   }

   public static class MoveLayout {
      public boolean[][] cells;
      public int sizeHoriz;
      public int sizeVert;

      public MoveLayout(HypothesisBoard board) {
         this.cells = new boolean[board.sizeHoriz][board.sizeVert];
         this.sizeHoriz = board.sizeHoriz;
         this.sizeVert = board.sizeVert;
      }

      public void add(CellPos pos) {
         this.cells[pos.posHoriz][pos.posVert] = true;
      }

      public boolean contains(CellPos pos) {
         return pos.posHoriz >= 0 && pos.posVert >= 0 && pos.posHoriz < this.sizeHoriz && pos.posVert < this.sizeVert
            ? this.cells[pos.posHoriz][pos.posVert]
            : false;
      }

      public void combineWithAND(MoveLayout other, HypothesisBoard board) {
         for (int x = 0; x < board.sizeHoriz; x++) {
            for (int y = 0; y < board.sizeVert; y++) {
               this.cells[x][y] = this.cells[x][y] && other.cells[x][y];
            }
         }
      }

      public void combineWithOR(MoveLayout other, HypothesisBoard board) {
         for (int x = 0; x < board.sizeHoriz; x++) {
            for (int y = 0; y < board.sizeVert; y++) {
               this.cells[x][y] = this.cells[x][y] || other.cells[x][y];
            }
         }
      }
   }

   public static class Phenomenon {
      public int data = 0;
      public int id;
      public int elementsCount;
      public boolean isRaw;
      public String name;

      public Phenomenon(String name, ShardType... elements) {
         this.name = name;

         for (ShardType element : elements) {
            int elemBit = 1 << element.id;
            this.data |= elemBit;
         }

         this.elementsCount = elements.length;
         this.isRaw = this.elementsCount == 1;
      }

      public Phenomenon(ShardType rawElement) {
         this.name = rawElement.name;
         int elemBit = 1 << rawElement.id;
         this.data |= elemBit;
         this.elementsCount = 1;
         this.isRaw = true;
      }

      public boolean hasElement(ShardType shardType) {
         return (this.data & 1 << shardType.id) > 0;
      }

      public boolean hasElementsExcept(ShardType shardTypeExcept) {
         return (this.data ^ 1 << shardTypeExcept.id) > 0;
      }

      public boolean hasAll(List<ShardType> elements) {
         for (ShardType shardType : elements) {
            if (!this.hasElement(shardType)) {
               return false;
            }
         }

         return true;
      }

      @Nullable
      public ShardType getAsRaw() {
         if (this.isRaw) {
            for (int i = 1; i <= 12; i++) {
               if ((this.data & 1 << i) > 0) {
                  return ShardType.byId(i);
               }
            }
         }

         return null;
      }
   }
}
