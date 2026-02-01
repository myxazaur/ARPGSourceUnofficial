package com.vivern.arpg.tileentity;

import com.vivern.arpg.entity.EntitySpellForgeCatalyst;
import com.vivern.arpg.events.Debugger;
import com.vivern.arpg.main.AnimationTimer;
import com.vivern.arpg.main.EnchantmentInit;
import com.vivern.arpg.main.GetMOP;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.ParticleFastSummon;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Sounds;
import com.vivern.arpg.main.Spell;
import com.vivern.arpg.main.SpellForgeRecipesRegister;
import com.vivern.arpg.main.Weapons;
import com.vivern.arpg.network.PacketHandler;
import com.vivern.arpg.recipes.SpellForgeRecipe;
import com.vivern.arpg.renders.AnimatedGParticle;
import com.vivern.arpg.renders.GUNParticle;
import com.vivern.arpg.renders.IMagicVision;
import com.vivern.arpg.renders.ManaBar;
import com.vivern.arpg.renders.ParticleTracker;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;

public class TileSpellForge extends TileEntityLockableLoot implements IMagicVision, ITickable, ISpellcastListener, IVialElementsAccepter, ITileEntitySynchronize {
   public static ResourceLocation forge_hit = new ResourceLocation("arpg:textures/forge_hit.png");
   public static ResourceLocation forge_hit_a = new ResourceLocation("arpg:textures/forge_hit_a.png");
   public static ResourceLocation forge_hit_b = new ResourceLocation("arpg:textures/forge_hit_b.png");
   public static ResourceLocation forge_absorption = new ResourceLocation("arpg:textures/forge_absorption.png");
   public static ResourceLocation star = new ResourceLocation("arpg:textures/star.png");
   public static ResourceLocation star3 = new ResourceLocation("arpg:textures/star3.png");
   public static ResourceLocation flare_1 = new ResourceLocation("arpg:textures/flare_1.png");
   public static final Random rand = new Random();
   public NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
   public int rotation = 0;
   public float[] elementsCollected = new float[12];
   public float elementsCollectedSumm = 0.0F;
   public float maxElementCount = 512.0F;
   public Forging currentForging;
   public float animRoundChange = 0.0F;
   public float prevanimRoundChange = 0.0F;
   public float[] animItemsForged;
   public float[] prevanimItemsForged;
   public boolean[] animItemsForgedFlags;
   public MovingSoundTileEntity soundForgeLoop;
   public int timeDestabilizedLightnings;
   public SpellForgeRecipe recipeDestabilizedLightnings;

   @Override
   public float acceptVialElements(ItemStack vial, ShardType shardType, float count) {
      if (this.elementsCollectedSumm >= this.maxElementCount && count > 0.0F) {
         return count;
      } else {
         float ret = this.addElementEnergy(shardType, count);
         PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
         return ret;
      }
   }

   public float addElementEnergy(ShardType shardType, float amount) {
      if (amount > 0.0F) {
         float add = Math.min(this.maxElementCount - this.elementsCollectedSumm, amount);
         this.elementsCollected[shardType.id - 1] = this.elementsCollected[shardType.id - 1] + add;
         this.elementsCollectedSumm += add;
         return amount - add;
      } else {
         float remove = Math.max(-this.elementsCollected[shardType.id - 1], amount);
         this.elementsCollected[shardType.id - 1] = this.elementsCollected[shardType.id - 1] + remove;
         this.elementsCollectedSumm += remove;
         return amount - remove;
      }
   }

   public void addElementEnergy(int indxInArray, float amount) {
      if (amount > 0.0F) {
         float add = Math.min(this.maxElementCount - this.elementsCollectedSumm, amount);
         this.elementsCollected[indxInArray] = this.elementsCollected[indxInArray] + add;
         this.elementsCollectedSumm += add;
      } else {
         float remove = Math.max(-this.elementsCollected[indxInArray], amount);
         this.elementsCollected[indxInArray] = this.elementsCollected[indxInArray] + remove;
         this.elementsCollectedSumm += remove;
      }
   }

   @Override
   public float getElementEnergy(ShardType shardType) {
      return Minecraft.getMinecraft().player != null
            && Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == ItemsRegister.SPELLROLL
         ? 0.0F
         : this.elementsCollected[shardType.id - 1];
   }

   @Override
   public float getElementCount(ShardType shardType) {
      return this.elementsCollected[shardType.id - 1];
   }

   @Override
   public float getMana() {
      return 0.0F;
   }

   public int getSizeInventory() {
      return 9;
   }

   public int getStacksForgedAmount() {
      return 0;
   }

   public void setInventorySlotContents(int index, ItemStack stack) {
      super.setInventorySlotContents(index, stack);
      this.destabilize();
      PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
   }

   public boolean spawnParticleOnBlockTop(BlockPos pos) {
      BlockPos posup = pos.up();
      IBlockState iBlockStateUp = this.world.getBlockState(posup);
      if (iBlockStateUp.getCollisionBoundingBox(this.world, posup) == null) {
         IBlockState iBlockState = this.world.getBlockState(pos);
         AxisAlignedBB aabb = iBlockState.getCollisionBoundingBox(this.world, pos);
         if (aabb != null) {
            int imax = 1 + rand.nextInt(2);

            for (int i = 0; i < imax; i++) {
               double x = pos.getX() + aabb.minX + (aabb.maxX - aabb.minX) * rand.nextFloat();
               double y = pos.getY() + aabb.maxY + 0.02;
               double z = pos.getZ() + aabb.minZ + (aabb.maxZ - aabb.minZ) * rand.nextFloat();
               this.world
                  .spawnParticle(
                     EnumParticleTypes.BLOCK_CRACK, x, y, z, 0.0, 0.15 + rand.nextGaussian() / 20.0, 0.0, new int[]{Block.getStateId(iBlockState)}
                  );
            }

            return true;
         }
      }

      return false;
   }

   @Override
   public void onClient(double... args) {
      if (args.length == 1) {
         if (args[0] == 0.0) {
            int radius = 6;

            for (int x = -radius; x <= radius; x++) {
               for (int z = -radius; z <= radius; z++) {
                  if (rand.nextFloat() > Math.sqrt(x * x + z * z) / radius && (x != 0 || z != 0)) {
                     int y = this.pos.getY() + 1;

                     while (
                        y >= this.pos.getY() - 2
                           && !this.spawnParticleOnBlockTop(new BlockPos(this.pos.getX() + x, y, this.pos.getZ() + z))
                     ) {
                        y--;
                     }
                  }
               }
            }

            if (this.currentForging != null) {
               ShardType shardType = this.currentForging.recipe.getRandomWeightedShardType(rand);
               if (shardType != null) {
                  for (int p = 0; p < 16.0F + Debugger.floats[3]; p++) {
                     shardType.spawnNativeParticle(
                        this.world,
                        1.0F,
                        this.pos.getX() + 0.5,
                        this.pos.getY() + 1.1,
                        this.pos.getZ() + 0.5,
                        rand.nextGaussian() / 19.0,
                        rand.nextGaussian() / 19.0 + 0.07F,
                        rand.nextGaussian() / 19.0,
                        true
                     );
                  }

                  AnimatedGParticle anim = new AnimatedGParticle(
                     forge_hit,
                     0.6F,
                     0.0F,
                     28,
                     240,
                     this.world,
                     this.pos.getX() + 0.5,
                     this.pos.getY() + 1.01,
                     this.pos.getZ() + 0.5,
                     0.0F,
                     0.0F,
                     0.0F,
                     shardType.colorR,
                     shardType.colorG,
                     shardType.colorB,
                     true,
                     rand.nextInt(360)
                  );
                  anim.framecount = 28;
                  anim.alphaGlowing = true;
                  anim.scaleTickAdding = 0.03F;
                  anim.randomDeath = false;
                  anim.rotationPitchYaw = new Vec2f(-90.0F, 0.0F);
                  this.world.spawnEntity(anim);
                  boolean b = rand.nextFloat() < 0.5F;
                  GUNParticle part1 = new GUNParticle(
                     b ? forge_hit_a : forge_hit_b,
                     0.3F + rand.nextFloat() * 0.1F,
                     0.0F,
                     5,
                     240,
                     this.world,
                     this.pos.getX() + 0.5,
                     this.pos.getY() + 1.1,
                     this.pos.getZ() + 0.5,
                     0.0F,
                     0.0F,
                     0.0F,
                     shardType.colorR,
                     shardType.colorG,
                     shardType.colorB,
                     true,
                     rand.nextInt(91) - 45
                  );
                  part1.alphaGlowing = true;
                  this.world.spawnEntity(part1);
                  GUNParticle part2 = new GUNParticle(
                     b ? forge_hit_b : forge_hit_a,
                     0.18F + rand.nextFloat() * 0.08F,
                     0.0F,
                     5,
                     240,
                     this.world,
                     this.pos.getX() + 0.5,
                     this.pos.getY() + 1.1,
                     this.pos.getZ() + 0.5,
                     0.0F,
                     0.0F,
                     0.0F,
                     1.0F,
                     1.0F,
                     1.0F,
                     true,
                     rand.nextInt(91) - 45
                  );
                  part2.alphaGlowing = true;
                  this.world.spawnEntity(part2);
               }

               if (this.currentForging.hitsNeed > 2.0F) {
                  ManaBar.displaySpellForge(this);
               }
            }
         } else if (args[0] != 1.0 && args[0] != 4.0) {
            if (args[0] == 2.0) {
               if (this.currentForging != null) {
                  this.currentForging.canAcceptSpells = true;
                  this.currentForging.ticks = 0;
               }
            } else if (args[0] == 3.0 && this.currentForging != null) {
               this.currentForging.fullComplete = true;
               this.currentForging.ticks = 0;
            }
         } else {
            if (args[0] == 4.0) {
               this.spawnDestabilizeParticles(false);
            } else {
               ShardType[] shardTypes = new ShardType[]{
                  this.currentForging.recipe.getRandomWeightedShardType(rand),
                  this.currentForging.recipe.getRandomWeightedShardType(rand),
                  this.currentForging.recipe.getRandomWeightedShardType(rand)
               };
               int amount = (int)Math.min(this.currentForging.recipe.hitsNeed * 5.0F, 60.0F);
               float speed = MathHelper.clamp(this.currentForging.recipe.hitsNeed / 35.0F, 0.1F, 0.4F);

               for (int i = 0; i < amount; i++) {
                  ShardType st = shardTypes[rand.nextInt(3)];
                  float r;
                  float g;
                  float b;
                  if (st != null) {
                     float add = rand.nextFloat() / 2.0F;
                     r = Math.min(st.colorR + add, 1.0F);
                     g = Math.min(st.colorG + add, 1.0F);
                     b = Math.min(st.colorB + add, 1.0F);
                  } else {
                     r = 0.5F;
                     g = 0.5F;
                     b = 0.5F;
                  }

                  int lt = 20 + rand.nextInt(25);
                  float scl = 0.035F + rand.nextFloat() * 0.035F;
                  GUNParticle part = new GUNParticle(
                     star,
                     scl,
                     0.0F,
                     lt,
                     220,
                     this.world,
                     this.pos.getX() + 0.5,
                     this.pos.getY() + 1.25,
                     this.pos.getZ() + 0.5,
                     0.0F,
                     0.0F,
                     0.0F,
                     r,
                     g,
                     b,
                     false,
                     0
                  );
                  part.scaleTickAdding = -scl / lt;
                  part.tracker = Forging.tss;
                  part.airFrictionMultipl = 0.92F;
                  ParticleFastSummon.placeInSphere(part, 0.0F, speed);
                  this.world.spawnEntity(part);
               }
            }

            this.currentForging = null;
            this.animItemsForgedFlags = null;
            this.animItemsForged = null;
            this.prevanimItemsForged = null;
            if (this.soundForgeLoop != null) {
               this.soundForgeLoop.stop = true;
               this.soundForgeLoop = null;
            }
         }
      }

      if (args.length == 2) {
         if (this.currentForging == null) {
            this.currentForging = new Forging((int)args[0]);
         }

         this.currentForging.hammerCompleteness = (float)args[1];
      }
   }

   public void update() {
      if (this.currentForging != null) {
         this.currentForging.update(this);
         if (this.soundForgeLoop == null && this.world.isRemote) {
            this.soundForgeLoop = new MovingSoundTileEntity(this, Sounds.spell_forge_loop, SoundCategory.BLOCKS, 1.0F, 1.0F, true, 20);
            Minecraft.getMinecraft().getSoundHandler().playSound(this.soundForgeLoop);
         }
      } else if (this.soundForgeLoop != null && this.world.isRemote) {
         this.soundForgeLoop.stop = true;
         this.soundForgeLoop = null;
      }

      if (this.world.isRemote) {
         this.prevanimRoundChange = this.animRoundChange;
         if (this.currentForging != null) {
            if (this.animRoundChange < 1.0F) {
               this.animRoundChange += 0.1F;
            }
         } else {
            this.animRoundChange = 0.0F;
         }

         if (this.animItemsForgedFlags != null && this.animItemsForged != null && this.prevanimItemsForged != null) {
            for (int i = 0; i < this.animItemsForgedFlags.length; i++) {
               if (this.animItemsForgedFlags[i]) {
                  this.prevanimItemsForged[i] = this.animItemsForged[i];
                  if (this.animItemsForged[i] < 1.0F) {
                     this.animItemsForged[i] = this.animItemsForged[i] + 0.2F;
                  }
               }
            }
         }

         if (this.timeDestabilizedLightnings > 0) {
            this.timeDestabilizedLightnings--;
            if (rand.nextFloat() < this.timeDestabilizedLightnings / 60.0F) {
               this.spawnDestabilizeParticles(true);
            }

            if (this.timeDestabilizedLightnings <= 0) {
               this.recipeDestabilizedLightnings = null;
            }
         }
      }
   }

   @Override
   public boolean onSpellcast(double fromX, double fromY, double fromZ, ItemStack scroll, Spell[] spells, Object caster) {
      if (this.currentForging != null
         && this.currentForging.recipe != null
         && this.currentForging.canAcceptSpells
         && this.currentForging.recipe.spells != null
         && this.currentForging.recipe.spells.length == spells.length) {
         for (int i = 0; i < spells.length; i++) {
            if (spells[i] != this.currentForging.recipe.spells[i]) {
               return false;
            }
         }

         this.currentForging.trySetFullComplete(this);
      }

      return false;
   }

   @Override
   public boolean canAttractParticles(Object caster) {
      return this.currentForging != null && this.currentForging.canAcceptSpells;
   }

   public boolean tryDestroyFloor() {
      int maxi = 2 + rand.nextInt(6);

      for (int i = 0; i < maxi; i++) {
         int psX = this.pos.getX() + rand.nextInt(9) - 4;
         int psY = this.pos.getY() + rand.nextInt(2) - 2;
         int psZ = this.pos.getZ() + rand.nextInt(9) - 4;
         if (Weapons.easyBreakBlockFor(this.world, 1.0F, new BlockPos(psX, psY, psZ))) {
            this.world.destroyBlock(new BlockPos(psX, psY, psZ), true);
         }
      }

      if (this.world.isAirBlock(this.pos.down())) {
         for (int ix = 0; ix < this.getSizeInventory(); ix++) {
            ItemStack stack = this.getStackInSlot(ix);
            if (!stack.isEmpty()) {
               EntityItem entityItem = new EntityItem(
                  this.getWorld(),
                  this.getPos().getX() + 0.5,
                  this.getPos().getY() + 1.5,
                  this.getPos().getZ() + 0.5,
                  stack
               );
               entityItem.motionX = (float)(Math.random() * 0.4 - 0.2);
               entityItem.motionY = 0.3;
               entityItem.motionZ = (float)(Math.random() * 0.4 - 0.2);
               this.getWorld().spawnEntity(entityItem);
            }
         }

         Weapons.destroyBlockWithFalling(this.world, this.pos);
         return true;
      } else {
         return false;
      }
   }

   public boolean onHammerHit(ItemStack spellHammer, Entity entityHits) {
      if (this.tryDestroyFloor()) {
         return false;
      } else {
         boolean firstHit = false;
         if (this.currentForging == null) {
            if (this.checkForgeBlocked()) {
               if (entityHits instanceof EntityPlayerMP) {
                  entityHits.sendMessage(new TextComponentString("пїЅ4This spell forge is blocked"));
               }

               return false;
            }

            this.currentForging = this.getForging(entityHits);
            firstHit = true;
         }

         ITileEntitySynchronize.sendSynchronize(this, 48.0, 0.0);
         if (this.currentForging != null && this.currentForging.recipe != null) {
            boolean canContinue = true;
            if (this.currentForging.recipe.catalyst != null) {
               canContinue = false;
               float bounds = 0.3F;
               AxisAlignedBB aabb = new AxisAlignedBB(
                  this.pos.getX() + bounds,
                  this.pos.getY() + 1,
                  this.pos.getZ() + bounds,
                  this.pos.getX() + 1.0F - bounds,
                  this.pos.getY() + 1.6F,
                  this.pos.getZ() + 1.0F - bounds
               );
               List<EntitySpellForgeCatalyst> list = this.world.getEntitiesWithinAABB(EntitySpellForgeCatalyst.class, aabb);
               if (list.size() == 1) {
                  EntitySpellForgeCatalyst entityCatalyst = list.get(0);
                  if (entityCatalyst != null && entityCatalyst.getItem() != null && this.currentForging.recipe.catalyst.isStackGood(entityCatalyst.getItem())) {
                     canContinue = true;
                  }
               }
            }

            if (canContinue) {
               if (!firstHit) {
                  this.currentForging.hammerCompleteness = this.currentForging.hammerCompleteness
                     + (1.0F + EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.MIGHT, spellHammer) * 0.2F);
                  if (this.currentForging.hammerCompleteness >= this.currentForging.hitsNeed) {
                     if (this.currentForging.recipe.spells != null && this.currentForging.recipe.spells.length != 0) {
                        this.currentForging.canAcceptSpells = true;
                        this.currentForging.ticks = 0;
                        ITileEntitySynchronize.sendSynchronize(this, 64.0, 2.0);
                     } else {
                        this.currentForging.trySetFullComplete(this);
                     }
                  }
               }

               ITileEntitySynchronize.sendSynchronize(this, 64.0, this.currentForging.recipe.id, this.currentForging.hammerCompleteness);
               return true;
            }

            if (this.currentForging.hammerCompleteness > 0.0F) {
               this.destabilize();
               PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
            }
         }

         return false;
      }
   }

   public Forging getForging(Entity entityCrafts) {
      for (SpellForgeRecipe recipe : SpellForgeRecipesRegister.allRecipes) {
         if (recipe.tryCraft(this, entityCrafts, false)) {
            return new Forging(recipe, 0.0F);
         }
      }

      return null;
   }

   public boolean isEmpty() {
      for (ItemStack itemstack : this.stacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public int addItemStack(ItemStack stack) {
      for (int i = 0; i < this.stacks.size(); i++) {
         if (((ItemStack)this.stacks.get(i)).isEmpty()) {
            this.setInventorySlotContents(i, stack);
            return i;
         }
      }

      return -1;
   }

   public void read(NBTTagCompound compound) {
      super.readFromNBT(compound);
      this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      if (!this.checkLootAndRead(compound)) {
         ItemStackHelper.loadAllItems(compound, this.stacks);
      }

      if (compound.hasKey("CustomName", 8)) {
         this.customName = compound.getString("CustomName");
      }

      if (compound.hasKey("rotation")) {
         this.rotation = compound.getInteger("rotation");
      }

      this.elementsCollectedSumm = 0.0F;

      for (int i = 0; i < 12; i++) {
         String tagname = "collected_" + i;
         if (compound.hasKey(tagname)) {
            float amount = compound.getFloat(tagname);
            this.elementsCollected[i] = amount;
            this.elementsCollectedSumm += amount;
         }
      }

      if (compound.hasKey("maxElementsStore")) {
         this.maxElementCount = compound.getFloat("maxElementsStore");
      }
   }

   public NBTTagCompound write(NBTTagCompound compound) {
      super.writeToNBT(compound);
      if (!this.checkLootAndWrite(compound)) {
         ItemStackHelper.saveAllItems(compound, this.stacks);
      }

      if (this.hasCustomName()) {
         compound.setString("CustomName", this.customName);
      }

      compound.setInteger("rotation", this.rotation);

      for (int i = 0; i < 12; i++) {
         compound.setFloat("collected_" + i, this.elementsCollected[i]);
      }

      compound.setFloat("maxElementsStore", this.maxElementCount);
      return compound;
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

   public int getInventoryStackLimit() {
      return 64;
   }

   public String getName() {
      return "tile_spell_forge";
   }

   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
      this.fillWithLoot(playerIn);
      return new Container9(playerInventory, this);
   }

   public String getGuiID() {
      return "minecraft:dispenser";
   }

   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }

   public void completeCraft(SpellForgeRecipe recipe) {
      if (recipe.tryCraft(this, null, true)) {
         this.getWorld()
            .spawnEntity(
               new EntityItem(
                  this.getWorld(),
                  this.getPos().getX() + 0.5,
                  this.getPos().getY() + 1.5,
                  this.getPos().getZ() + 0.5,
                  recipe.craftresult.createStack()
               )
            );
         this.currentForging = null;
         PacketHandler.trySendPacketUpdate(this.world, this.pos, this, 64.0);
         ITileEntitySynchronize.sendSynchronize(this, 64.0, 1.0);
      }
   }

   public void destabilize() {
      if (this.currentForging != null) {
         if (!this.world.isRemote) {
            ITileEntitySynchronize.sendSynchronize(this, 64.0, 4.0);
            this.world
               .playSound((EntityPlayer)null, this.getPos(), Sounds.spell_forge_fail, SoundCategory.BLOCKS, 1.4F, 0.9F + rand.nextFloat() * 0.2F);

            for (int i = 0; i < this.getSizeInventory(); i++) {
               ItemStack stack = this.decrStackSize(i, 1);
               if (!stack.isEmpty()) {
                  EntityItem entityItem = new EntityItem(
                     this.getWorld(),
                     this.getPos().getX() + 0.5,
                     this.getPos().getY() + 1.5,
                     this.getPos().getZ() + 0.5,
                     stack
                  );
                  entityItem.motionX = (float)(Math.random() * 0.4 - 0.2);
                  entityItem.motionY = 0.3;
                  entityItem.motionZ = (float)(Math.random() * 0.4 - 0.2);
                  this.getWorld().spawnEntity(entityItem);
               }
            }
         }

         this.currentForging = null;
      }
   }

   public void spawnDestabilizeParticles(boolean lightning) {
      if (!lightning) {
         if (this.currentForging != null) {
            ShardType shardType = this.currentForging.recipe.getRandomWeightedShardType(rand);
            if (shardType != null) {
               float r = Math.min(shardType.colorR + 0.3F, 1.0F);
               float g = Math.min(shardType.colorG + 0.3F, 1.0F);
               float b = Math.min(shardType.colorB + 0.3F, 1.0F);
               Vec3d poss = new Vec3d(
                  this.getPos().getX() + 0.5, this.getPos().getY() + 1.25, this.getPos().getZ() + 0.5
               );
               ParticleFastSummon.round(this.getWorld(), poss, 0.05F, 3.5F, 7, 240, r, g, b, 1, true);
               ParticleFastSummon.round(this.getWorld(), poss, 0.05F, 2.5F, 6, 240, shardType.colorR, shardType.colorG, shardType.colorB, 1, false);
               ParticleFastSummon.round(this.getWorld(), poss, 0.05F, 4.0F, 9, 240, shardType.colorR, shardType.colorG, shardType.colorB, 1, false);
               ParticleFastSummon.round(this.getWorld(), poss, 0.05F, 3.0F, 8, 240, r, g, b, 1, false);
            }

            this.timeDestabilizedLightnings = 60;
            this.recipeDestabilizedLightnings = this.currentForging.recipe;
         }
      } else {
         Vec3d poss = new Vec3d(
            this.getPos().getX() + 0.5, this.getPos().getY() + 1.25, this.getPos().getZ() + 0.5
         );
         Vec3d pos2 = poss.add(rand.nextGaussian() * 1.8, rand.nextGaussian() * 1.7 + 0.1, rand.nextGaussian() * 1.8);
         RayTraceResult result = this.world.rayTraceBlocks(poss, pos2, false, true, false);
         Vec3d pos3;
         if (result != null && result.hitVec != null) {
            pos3 = result.hitVec;
         } else {
            pos3 = pos2;
         }

         ShardType shardType = this.recipeDestabilizedLightnings.getRandomWeightedShardType(rand);
         if (shardType != null) {
            ParticleFastSummon.coloredLightning(
               this.world,
               0.035F,
               10 + rand.nextInt(10),
               240,
               shardType.colorR,
               shardType.colorG,
               shardType.colorB,
               7,
               poss,
               pos3,
               poss.squareDistanceTo(pos3) > 2.0 ? 0.5F : 0.25F,
               0.2F,
               ParticleFastSummon.rand
            );
         }
      }
   }

   @Nullable
   public ShardType getRandomElement() {
      int k = 0;

      for (int i = rand.nextInt(12); k < 12; i = GetMOP.next(i, 1, 12)) {
         if (this.elementsCollected[i] > 0.0F) {
            return ShardType.byId(i + 1);
         }

         k++;
      }

      return null;
   }

   public boolean checkForgeBlocked() {
      for (EnumFacing face : EnumFacing.HORIZONTALS) {
         BlockPos psss = this.pos.offset(face);
         if (this.world.getBlockState(psss).getBlock().getCollisionBoundingBox(this.world.getBlockState(psss), this.world, psss) != null) {
            return true;
         }
      }

      for (int x = -1; x <= 1; x++) {
         for (int z = -1; z <= 1; z++) {
            BlockPos psss = this.pos.add(x, 1, z);
            if (this.world.getBlockState(psss).getBlock().getCollisionBoundingBox(this.world.getBlockState(psss), this.world, psss)
               != null) {
               return true;
            }
         }
      }

      return false;
   }

   public static class Forging {
      static ParticleTracker.TrackerVoidRender ptvr = new ParticleTracker.TrackerVoidRender(4.0F, 0.0F, 60.0F);
      static ParticleTracker.TrackerSinusScaling tss = new ParticleTracker.TrackerSinusScaling(0.25F, 0.75F);
      public SpellForgeRecipe recipe;
      public float hammerCompleteness;
      public boolean canAcceptSpells = false;
      public boolean fullComplete = false;
      public boolean destabilized = false;
      public int ticks;
      public float hitsNeed;
      public float absorbingRed;
      public float absorbingGreen;
      public float absorbingBlue;
      public ShardType lastClientShardType;

      public Forging(SpellForgeRecipe recipe, float hammerCompleteness) {
         this.recipe = recipe;
         this.hammerCompleteness = hammerCompleteness;
         this.hitsNeed = recipe.hitsNeed;
      }

      public Forging(int recipeId) {
         if (recipeId < SpellForgeRecipesRegister.allRecipes.size()) {
            this.recipe = SpellForgeRecipesRegister.allRecipes.get(recipeId);
            if (this.recipe != null) {
               this.hitsNeed = this.recipe.hitsNeed;
            }
         }
      }

      public void update(TileSpellForge tileEntity) {
         if (this.fullComplete) {
            if (this.ticks == 0) {
               tileEntity.world
                  .playSound(
                     (EntityPlayer)null,
                     tileEntity.getPos(),
                     this.hitsNeed >= 3.0F ? Sounds.spell_forge_complete : Sounds.spell_forge_smallcomplete,
                     SoundCategory.BLOCKS,
                     1.8F,
                     1.0F
                  );
            }

            this.ticks++;
            if (this.ticks > (this.hitsNeed >= 3.0F ? 70 : 0) && !tileEntity.world.isRemote && this.recipe != null) {
               tileEntity.completeCraft(this.recipe);
            }
         } else if (this.canAcceptSpells) {
            this.ticks++;
            if (!tileEntity.world.isRemote && this.ticks > 160) {
               this.destabilized = true;
               this.canAcceptSpells = false;
               tileEntity.destabilize();
               PacketHandler.trySendPacketUpdate(tileEntity.getWorld(), tileEntity.getPos(), tileEntity, 64.0);
            }
         }

         if (!tileEntity.world.isRemote && TileSpellForge.rand.nextFloat() < 0.2F && tileEntity.checkForgeBlocked()) {
            this.destabilized = true;
            this.canAcceptSpells = false;
            tileEntity.destabilize();
            PacketHandler.trySendPacketUpdate(tileEntity.getWorld(), tileEntity.getPos(), tileEntity, 64.0);
         }

         if (tileEntity.world.isRemote && this.recipe != null) {
            if (TileSpellForge.rand.nextFloat() < 0.08F || this.lastClientShardType == null) {
               this.lastClientShardType = this.recipe.getRandomWeightedShardType(TileSpellForge.rand);
            }

            if (this.canAcceptSpells && this.lastClientShardType != null) {
               this.absorbingRed = GetMOP.followNumber(this.absorbingRed, this.lastClientShardType.colorR, 0.05F);
               this.absorbingGreen = GetMOP.followNumber(this.absorbingGreen, this.lastClientShardType.colorG, 0.05F);
               this.absorbingBlue = GetMOP.followNumber(this.absorbingBlue, this.lastClientShardType.colorB, 0.05F);
               if (AnimationTimer.normaltick % 2 == 0) {
                  AnimatedGParticle anim = new AnimatedGParticle(
                     TileSpellForge.forge_absorption,
                     0.6F + TileSpellForge.rand.nextFloat() * 0.35F,
                     0.0F,
                     28,
                     240,
                     tileEntity.world,
                     tileEntity.pos.getX() + 0.5,
                     tileEntity.pos.getY() + 1.1,
                     tileEntity.pos.getZ() + 0.5,
                     0.0F,
                     0.0F,
                     0.0F,
                     this.absorbingRed,
                     this.absorbingGreen,
                     this.absorbingBlue,
                     true,
                     TileSpellForge.rand.nextInt(360)
                  );
                  anim.framecount = 25;
                  anim.alphaGlowing = true;
                  anim.randomDeath = false;
                  anim.rotationPitchYaw = new Vec2f(TileSpellForge.rand.nextInt(360), TileSpellForge.rand.nextInt(360));
                  tileEntity.world.spawnEntity(anim);
               }

               Vec3d pos = new Vec3d(
                  tileEntity.pos.getX() + 0.5,
                  tileEntity.pos.getY() + 1.1,
                  tileEntity.pos.getZ() + 0.5
               );
               Vec3d randPos = pos.add(
                  TileSpellForge.rand.nextGaussian() * 2.0, TileSpellForge.rand.nextGaussian() * 1.5 + 0.5, TileSpellForge.rand.nextGaussian() * 2.0
               );
               Vec3d motion = pos.subtract(randPos).scale(Debugger.floats[0] + 0.1);
               this.lastClientShardType
                  .spawnNativeParticle(
                     tileEntity.world,
                     0.8F,
                     randPos.x,
                     randPos.y,
                     randPos.z,
                     motion.x,
                     motion.y,
                     motion.z,
                     true
                  );
            }

            if (this.lastClientShardType != null && TileSpellForge.rand.nextFloat() < 0.5F) {
               EnumFacing face = EnumFacing.HORIZONTALS[TileSpellForge.rand.nextInt(4)];
               EnumFacing face2 = face.rotateY();
               double offset = (TileSpellForge.rand.nextInt(3) - 1) * 0.15625F;
               Vec3d pos1 = new Vec3d(
                  tileEntity.pos.getX() + 0.5 + face.getXOffset() * 0.5625 + face2.getXOffset() * offset,
                  tileEntity.pos.getY() + 0.875F,
                  tileEntity.pos.getZ() + 0.5 + face.getZOffset() * 0.5625 + face2.getZOffset() * offset
               );
               Vec3d pos2 = new Vec3d(
                  tileEntity.pos.getX()
                     + 0.5
                     + face.getXOffset() * 2.5
                     + face2.getXOffset() * offset * 3.0
                     + TileSpellForge.rand.nextGaussian(),
                  tileEntity.pos.getY() - 2,
                  tileEntity.pos.getZ()
                     + 0.5
                     + face.getZOffset() * 2.5
                     + face2.getZOffset() * offset * 3.0
                     + TileSpellForge.rand.nextGaussian()
               );
               RayTraceResult result = tileEntity.world.rayTraceBlocks(pos1, pos2, false, true, false);
               Vec3d pos3;
               if (result != null && result.hitVec != null) {
                  pos3 = result.hitVec;
               } else {
                  pos3 = pos2;
               }

               ParticleFastSummon.coloredLightning(
                  tileEntity.world,
                  0.03F,
                  4,
                  240,
                  this.lastClientShardType.colorR,
                  this.lastClientShardType.colorG,
                  this.lastClientShardType.colorB,
                  5,
                  pos1,
                  pos3,
                  pos1.squareDistanceTo(pos3) > 2.0 ? 0.5F : 0.25F,
                  0.1F,
                  ParticleFastSummon.rand
               );
            }

            if (this.fullComplete) {
               if (AnimationTimer.normaltick % 8 == 0) {
                  Vec3d pos1 = new Vec3d(
                     tileEntity.pos.getX() + 0.5,
                     tileEntity.pos.getY() + 1.25F,
                     tileEntity.pos.getZ() + 0.5
                  );
                  ShardType typecomplete = this.recipe.getRandomWeightedShardType(TileSpellForge.rand);
                  if (typecomplete != null) {
                     ParticleFastSummon.round(
                        tileEntity.world,
                        pos1,
                        3.0F + TileSpellForge.rand.nextFloat() * 2.0F,
                        0.0F,
                        7 + TileSpellForge.rand.nextInt(5),
                        240,
                        typecomplete.colorR,
                        typecomplete.colorG,
                        typecomplete.colorB,
                        1,
                        true
                     );
                     float add = TileSpellForge.rand.nextFloat() < this.ticks / 50.0F ? this.ticks / 70.0F : 0.0F;
                     float r = Math.min(typecomplete.colorR + add, 1.0F);
                     float g = Math.min(typecomplete.colorG + add, 1.0F);
                     float b = Math.min(typecomplete.colorB + add, 1.0F);
                     GUNParticle part1 = new GUNParticle(
                        TileSpellForge.star3,
                        0.1F,
                        0.0F,
                        15,
                        240,
                        tileEntity.world,
                        pos1.x,
                        pos1.y,
                        pos1.z,
                        0.0F,
                        0.0F,
                        0.0F,
                        r,
                        g,
                        b,
                        true,
                        0
                     );
                     part1.tracker = tss;
                     part1.alphaGlowing = true;
                     part1.scaleTickAdding = 0.2F;
                     part1.scaleMax = 3.0F + TileSpellForge.rand.nextFloat();
                     part1.alphaTickAdding = -0.04F;
                     tileEntity.world.spawnEntity(part1);
                  }
               }

               if (AnimationTimer.normaltick % 11 == 0) {
                  Vec3d pos1 = new Vec3d(
                     tileEntity.pos.getX() + 0.5,
                     tileEntity.pos.getY() + 1.25F,
                     tileEntity.pos.getZ() + 0.5
                  );
                  ShardType typecomplete = tileEntity.getRandomElement();
                  if (typecomplete != null) {
                     GUNParticle part2 = new GUNParticle(
                        TileSpellForge.flare_1,
                        0.2F,
                        0.0F,
                        60,
                        210,
                        tileEntity.world,
                        pos1.x,
                        pos1.y,
                        pos1.z,
                        0.0F,
                        0.0F,
                        0.0F,
                        typecomplete.colorR,
                        typecomplete.colorG,
                        typecomplete.colorB,
                        true,
                        TileSpellForge.rand.nextInt(31) - 15
                     );
                     part2.tracker = ptvr;
                     part2.alphaGlowing = true;
                     tileEntity.world.spawnEntity(part2);
                  }
               }
            }
         }
      }

      public boolean trySetFullComplete(TileSpellForge tileEntity) {
         if (this.recipe != null) {
            if (this.recipe.catalyst == null) {
               this.fullComplete = true;
               this.ticks = 0;
               ITileEntitySynchronize.sendSynchronize(tileEntity, 64.0, 3.0);
               return true;
            }

            BlockPos pos = tileEntity.getPos();
            boolean canContinue = false;
            float bounds = 0.3F;
            AxisAlignedBB aabb = new AxisAlignedBB(
               pos.getX() + bounds,
               pos.getY() + 1,
               pos.getZ() + bounds,
               pos.getX() + 1.0F - bounds,
               pos.getY() + 1.6F,
               pos.getZ() + 1.0F - bounds
            );
            List<EntitySpellForgeCatalyst> list = tileEntity.getWorld().getEntitiesWithinAABB(EntitySpellForgeCatalyst.class, aabb);
            if (list.size() == 1) {
               EntitySpellForgeCatalyst entityCatalyst = list.get(0);
               if (entityCatalyst != null && entityCatalyst.getItem() != null && this.recipe.catalyst.isStackGood(entityCatalyst.getItem())) {
                  canContinue = true;
                  entityCatalyst.startSpendForForge();
               }
            }

            if (canContinue) {
               this.fullComplete = true;
               this.ticks = 0;
               ITileEntitySynchronize.sendSynchronize(tileEntity, 64.0, 3.0);
               return true;
            }
         }

         return false;
      }
   }
}
