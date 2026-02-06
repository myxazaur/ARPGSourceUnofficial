package com.Vivern.Arpg.mobs;

import com.Vivern.Arpg.elements.Buckshot;
import com.Vivern.Arpg.elements.models.MerchantModel;
import com.Vivern.Arpg.elements.models.ModelsOtherMob;
import com.Vivern.Arpg.elements.models.ModelsStormledgeMob;
import com.Vivern.Arpg.entity.IEntitySynchronize;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.Coins;
import com.Vivern.Arpg.main.FindAmmo;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Main;
import com.Vivern.Arpg.main.ParticleFastSummon;
import com.Vivern.Arpg.main.PropertiesRegistry;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.network.PacketHandler;
import com.Vivern.Arpg.network.PacketTradesToClient;
import com.Vivern.Arpg.renders.mobrender.IMultitexture;
import com.Vivern.Arpg.tileentity.IManaBuffer;
import com.Vivern.Arpg.tileentity.TileBookcase;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

public class NPCMobsPack {
   public static final ItemStack EM = ItemStack.EMPTY;
   public static EntityAINPC.IBlockAiRequirement furnaces = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == Blocks.FURNACE || bl == Blocks.LIT_FURNACE;
      }
   };
   public static EntityAINPC.IBlockAiRequirement craftables = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == Blocks.CRAFTING_TABLE;
      }
   };
   public static EntityAINPC.IBlockAiRequirement anvils = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == Blocks.ANVIL;
      }
   };
   public static EntityAINPC.IBlockAiRequirement water = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == Blocks.WATER || bl == Blocks.FLOWING_WATER;
      }
   };
   public static EntityAINPC.IBlockAiRequirement spellforge = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == BlocksRegister.SPELLFORGE;
      }
   };
   public static EntityAINPC.IBlockAiRequirement manaContainer = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         TileEntity tile = world.getTileEntity(pos);
         return tile != null && tile instanceof IManaBuffer;
      }
   };
   public static EntityAINPC.IBlockAiRequirement books = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         if (bl == Blocks.BOOKSHELF) {
            return true;
         } else {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null && tile instanceof TileBookcase) {
               TileBookcase bookcase = (TileBookcase)tile;
               if (bookcase.hasBooks()) {
                  return true;
               }
            }

            return false;
         }
      }
   };
   public static EntityAINPC.IBlockAiRequirement cauldrons = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == Blocks.CAULDRON || bl == BlocksRegister.SPLITTER;
      }
   };
   public static EntityAINPC.IBlockAiRequirement potionBrewery = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == Blocks.BREWING_STAND;
      }
   };
   public static EntityAINPC.IBlockAiRequirement enchantTable = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == Blocks.ENCHANTING_TABLE;
      }
   };
   public static EntityAINPC.IBlockAiRequirement redstone = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == Blocks.REDSTONE_WIRE
            || bl == Blocks.REDSTONE_TORCH
            || bl == Blocks.REDSTONE_LAMP
            || bl == Blocks.REDSTONE_BLOCK
            || bl == Blocks.REDSTONE_ORE
            || bl == Blocks.UNLIT_REDSTONE_TORCH
            || bl == Blocks.POWERED_COMPARATOR
            || bl == Blocks.UNPOWERED_COMPARATOR
            || bl == Blocks.POWERED_REPEATER
            || bl == Blocks.UNPOWERED_REPEATER
            || bl == Blocks.DISPENSER
            || bl == Blocks.DROPPER
            || bl == Blocks.OBSERVER
            || bl == Blocks.ACTIVATOR_RAIL
            || bl == Blocks.DETECTOR_RAIL
            || bl == Blocks.GOLDEN_RAIL
            || bl == Blocks.PISTON
            || bl == Blocks.STICKY_PISTON;
      }
   };
   public static EntityAINPC.IBlockAiRequirement industrialBlock = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         TileEntity tile = world.getTileEntity(pos);
         if (tile != null) {
            for (EnumFacing face : EnumFacing.VALUES) {
               if (tile.hasCapability(CapabilityEnergy.ENERGY, face)) {
                  return true;
               }
            }
         }

         return false;
      }
   };
   public static EntityAINPC.IBlockAiRequirement industrialMachinery = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         TileEntity tile = world.getTileEntity(pos);
         boolean hasEnergy = false;
         if (tile != null) {
            for (EnumFacing face : EnumFacing.VALUES) {
               if (tile.hasCapability(CapabilityEnergy.ENERGY, face)) {
                  hasEnergy = true;
                  break;
               }
            }

            if (hasEnergy) {
               for (EnumFacing facex : EnumFacing.VALUES) {
                  if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facex)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   };
   public static EntityAINPC.IBlockAiRequirement xmas = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         if (bl == BlocksRegister.CHRISTMASBALLS) {
            return true;
         } else {
            return bl == BlocksRegister.GARLAND ? true : bl == BlocksRegister.STARLANTERN;
         }
      }
   };
   public static EntityAINPC.IBlockAiRequirement presentBox = new EntityAINPC.IBlockAiRequirement() {
      @Override
      public boolean allowed(World world, IBlockState blockstate, BlockPos pos) {
         Block bl = blockstate.getBlock();
         return bl == BlocksRegister.PRESENTBOX;
      }
   };

   public static void init() {
      AbstractMob.addToRegister(NpcMerchant.class, "Merchant", 10782053, 11365929);
      AbstractMob.addToRegister(NpcWizard.class, "Wizard", 2900133, 15658734);
      AbstractMob.addToRegister(NpcMechanic.class, "Mechanic", 4408882, 16481849);
      AbstractMob.addToRegister(NpcBristling.class, "Bristling", 9868172, 3225418);
      AbstractMob.addToRegister(NpcZarpionTrader.class, "Zarpion Trader", 1516604, 16775043);
   }

   public static void initRender() {
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new MerchantModel(), new ResourceLocation("arpg:textures/merchant_model_tex.png"), 1.0F, NpcMerchant.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsOtherMob.WizardModel(), new ResourceLocation("arpg:textures/wizard_model_tex.png"), 0.3F, NpcWizard.class
         )
      );
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsOtherMob.MechanicModel(), new ResourceLocation("arpg:textures/mechanic_model_tex.png"), 0.3F, NpcMechanic.class
         )
      );
      AbstractMob.addToRender(new AbstractMob.RenderAbstractMobEntry(new ModelsOtherMob.BristlingModel(), 0.3F, NpcBristling.class));
      AbstractMob.addToRender(
         new AbstractMob.RenderAbstractMobEntry(
            new ModelsStormledgeMob.ZarpionModel(), new ResourceLocation("arpg:textures/zarpion_trader_tex.png"), 0.4F, NpcZarpionTrader.class
         )
      );
   }

   public static class NpcBristling extends NpcTrader {
      public static ResourceLocation[] textures = new ResourceLocation[]{
         new ResourceLocation("arpg:textures/bristling_tex0.png"),
         new ResourceLocation("arpg:textures/bristling_tex1.png"),
         new ResourceLocation("arpg:textures/bristling_tex2.png"),
         new ResourceLocation("arpg:textures/bristling_tex3.png")
      };
      private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(NpcBristling.class, DataSerializers.VARINT);

      public NpcBristling(World world) {
         super(world, 0.6F, 1.95F);
         this.hurtSound = Sounds.mob_bigcat_hurt;
         this.deathSound = Sounds.mob_bigcat_death;
         this.livingSound = Sounds.mob_bigcat_living;
         this.setattributes(50.0, 16.0, 10.0, 0.28, 6.0, 6.0, 0.0, 0.0, 0.0, 0.0);
         this.comfortabilityPointsMax = 8;
      }

      protected void entityInit() {
         super.entityInit();
         this.dataManager.register(VARIANT, 0);
      }

      public int getVARIANT() {
         return (Integer)this.dataManager.get(VARIANT);
      }

      public void setVARIANT(int variant) {
         this.dataManager.set(VARIANT, variant);
      }

      public double getYOffset() {
         return this.isRiding() ? -0.45 : super.getYOffset();
      }

      public void writeEntityToNBT(NBTTagCompound compound) {
         compound.setInteger("variant", this.getVARIANT());
         super.writeEntityToNBT(compound);
      }

      public void readEntityFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("variant")) {
            this.setVARIANT(compound.getInteger("variant"));
         }

         super.readEntityFromNBT(compound);
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         if (!this.world.isRemote) {
            Random rand = this.getRNG();
            this.setVARIANT(rand.nextInt(4));
            if (rand.nextFloat() < 0.27) {
               int profession = rand.nextInt(5);
               if (profession == 0) {
                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.CRIMBERRYWINE), 3, 0, 0.0F));
                  }

                  if (rand.nextFloat() < 0.65) {
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.BISCUIT, 3 + rand.nextInt(6)), 2, 0, 0.0F));
                  }

                  if (rand.nextFloat() < 0.65) {
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.CANDYCANE, 2 + rand.nextInt(5)), 2, 0, 0.0F));
                  }
               } else if (profession == 1) {
                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.HELLHOUNDFUR), NPCMobsPack.EM, 0, 4, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.RAWRIBS), NPCMobsPack.EM, 0, 1, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.HOTSPICYRIBS), NPCMobsPack.EM, 0, 2, 0.0F));
                  }

                  if (rand.nextFloat() < 0.5) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.LIQUIDFIRE), NPCMobsPack.EM, 0, 4, 0.0F));
                  }

                  if (rand.nextFloat() < 0.4) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.INGOTMOLTEN), NPCMobsPack.EM, 0, 8, 0.0F));
                  }

                  if (rand.nextFloat() < 0.4) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.INGOTINFERNUM), NPCMobsPack.EM, 0, 5, 0.0F));
                  }

                  if (rand.nextFloat() < 0.2) {
                     this.trades.add(new Trade(new ItemStack(Items.NETHER_STAR), NPCMobsPack.EM, 0, 40 + rand.nextInt(21), 0.0F));
                  }

                  if (rand.nextFloat() < 0.7) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.FIREEATER), NPCMobsPack.EM, 0, 4 + rand.nextInt(3), 0.0F));
                  }

                  if (rand.nextFloat() < 0.7) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.HELLHOUNDCOLLAR), NPCMobsPack.EM, 0, 4 + rand.nextInt(3), 0.0F));
                  }

                  if (rand.nextFloat() < 0.3) {
                     this.trades
                        .add(
                           new Trade(
                              new ItemStack(ItemsRegister.INGOTNORTHERN, 2), new ItemStack(ItemsRegister.DEMONITE), 200 + rand.nextInt(50), 0, 0.0F
                           )
                        );
                  }
               } else if (profession == 2) {
                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.WHITESLIMEBALL, 4), NPCMobsPack.EM, 0, 1, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.MOONSHROOMMEAT), NPCMobsPack.EM, 0, 1, 0.0F));
                  }

                  if (rand.nextFloat() < 0.7) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.SLIMEEATER), NPCMobsPack.EM, 0, 4 + rand.nextInt(3), 0.0F));
                  }
               } else if (profession == 3) {
                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.FIREWORKSLAUN), 250, 0, 0.0F));
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.FIREWORKPACK), 3, 0, 0.0F));
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.FIREWORKDRAGON), 2, 0, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.GRENADESNOW, 4), 5, 0, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.ARROWBENGAL, 13), 9, 0, 0.0F));
                  }

                  if (rand.nextFloat() < 0.5) {
                     this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.XMASSBUNDLE, 1), 4, 0, 0.0F));
                  }
               } else if (profession == 4) {
                  if (rand.nextFloat() < 0.85) {
                     this.trades.add(new Trade(new ItemStack(BlocksRegister.FROZENVASE), NPCMobsPack.EM, 0, 3, 0.0F));
                  }

                  if (rand.nextFloat() < 0.85) {
                     this.trades.add(new Trade(new ItemStack(BlocksRegister.FROZENCHANDELIER), NPCMobsPack.EM, 0, 2, 0.0F));
                  }

                  if (rand.nextFloat() < 0.85) {
                     this.trades.add(new Trade(new ItemStack(BlocksRegister.CHESTFROZEN), NPCMobsPack.EM, 0, 3, 0.0F));
                  }

                  if (rand.nextFloat() < 0.85) {
                     this.trades.add(new Trade(new ItemStack(BlocksRegister.CHRISTMASBALLS, 8), NPCMobsPack.EM, 0, 1, 0.0F));
                  }

                  if (rand.nextFloat() < 0.85) {
                     this.trades.add(new Trade(new ItemStack(BlocksRegister.GARLAND, 10), NPCMobsPack.EM, 0, 1, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.GOTHICSHOVEL), NPCMobsPack.EM, 0, 20, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.GOTHICPICKAXE), NPCMobsPack.EM, 0, 20, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.GOTHICAXE), NPCMobsPack.EM, 0, 20, 0.0F));
                  }

                  if (rand.nextFloat() < 0.6) {
                     this.trades.add(new Trade(new ItemStack(ItemsRegister.GOTHICSWORD), NPCMobsPack.EM, 0, 20, 0.0F));
                  }
               }
            } else {
               if (rand.nextFloat() < 0.25) {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.GIFT), 15, 0, 0.0F));
               }

               if (rand.nextFloat() < 0.17) {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(BlocksRegister.FROZENTORCH, 4 + rand.nextInt(5)), 1, 0, 0.0F));
               }

               if (rand.nextFloat() < 0.16) {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(BlocksRegister.GARLAND, 2 + rand.nextInt(3)), 1, 0, 0.0F));
               }

               if (rand.nextFloat() < 0.15) {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(BlocksRegister.CHRISTMASBALLS, 1 + rand.nextInt(3)), 1, 0, 0.0F));
               }
            }
         }

         return super.onInitialSpawn(difficulty, livingdata);
      }

      protected void initEntityAI() {
         this.tasks
            .addTask(
               1,
               new EntityAINPC(
                  this,
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.furnaces, 200, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.craftables, 220, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.xmas, 100, 150, 4.0F, false, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.presentBox, 250, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.industrialBlock, 120, 200, 4.0F, false, true),
                  new EntityAINPC.RequirementChairAndTable(4.0F, false, false),
                  new EntityAINPC.RequirementChairAndTable(4.0F, true, false),
                  new EntityAINPC.RequirementLight(6, 15, false)
               )
            );
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
      }

      @SideOnly(Side.CLIENT)
      @Nullable
      @Override
      public ResourceLocation getMultitexture() {
         return textures[this.getVARIANT()];
      }
   }

   public static class NpcMechanic extends NpcTrader implements IInventory {
      public ItemStack containedItemStack = ItemStack.EMPTY;

      public NpcMechanic(World world) {
         super(world, 0.6F, 1.95F);
         this.hurtSound = SoundEvents.ENTITY_VILLAGER_HURT;
         this.deathSound = SoundEvents.ENTITY_VILLAGER_DEATH;
         this.setattributes(25.0, 16.0, 8.0, 0.25, 6.0, 4.0, 0.0, 0.0, 0.0, 0.0);
         this.comfortabilityPointsMax = 9;
      }

      public float getRepairRatio() {
         return 15.0F + this.getComfortability() / 10.0F;
      }

      @Override
      public void guiclick(EntityPlayer player, int mouseX, int mouseY, int mouseButton, int page) {
         if (!this.containedItemStack.isEmpty()
            && this.containedItemStack.isItemDamaged()
            && this.containedItemStack.getItem().isRepairable()
            && mouseX > 43
            && mouseX < 68) {
            if (mouseY > 14 && mouseY < 31) {
               int cost = this.getRepairFullCost();
               if (Coins.getCoins(player) >= cost) {
                  this.containedItemStack.setItemDamage(0);
                  Coins.addCoins(player, -cost);
                  this.playSound(Sounds.wrench, 0.4F, 1.0F);
               }
            } else if (mouseY > 33 && mouseY < 50 && Coins.getCoins(player) > 0) {
               int repairFor1Money = this.getRepairPerCoin();
               this.containedItemStack.setItemDamage(Math.max(this.containedItemStack.getItemDamage() - repairFor1Money, 0));
               Coins.addCoins(player, -1);
               this.playSound(Sounds.wrench, 0.4F, 1.0F);
            }
         }
      }

      public int getRepairPerCoin() {
         return (int)(this.containedItemStack.getItem().getXpRepairRatio(this.containedItemStack) * this.getRepairRatio());
      }

      public int getRepairFullCost() {
         return MathHelper.ceil((float)this.containedItemStack.getItemDamage() / this.getRepairPerCoin());
      }

      @Override
      public boolean processInteract(EntityPlayer player, EnumHand hand) {
         if (!this.world.isRemote && !player.isSpectator()) {
            IEntitySynchronize.sendSynchronize(this, 8.0, this.getComfortability());
            player.openGui(Main.instance, 17, this.world, this.getEntityId(), 0, 0);
         }

         return true;
      }

      @Override
      public NBTTagCompound writeToNBT(NBTTagCompound compound) {
         super.writeToNBT(compound);
         NBTTagCompound stacktag = new NBTTagCompound();
         this.containedItemStack.writeToNBT(stacktag);
         compound.setTag("repairedStack", stacktag);
         return compound;
      }

      @Override
      public void readFromNBT(NBTTagCompound compound) {
         super.readFromNBT(compound);
         if (compound.hasKey("repairedStack")) {
            NBTTagCompound stacktag = compound.getCompoundTag("repairedStack");
            this.containedItemStack = new ItemStack(stacktag);
         }
      }

      public double getYOffset() {
         return this.isRiding() ? -0.45 : super.getYOffset();
      }

      protected void initEntityAI() {
         this.tasks
            .addTask(
               1,
               new EntityAINPC(
                  this,
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.furnaces, 200, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.craftables, 160, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.anvils, 150, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.industrialBlock, 140, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.industrialMachinery, 140, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.redstone, 290, 100, 4.0F, true, false),
                  new EntityAINPC.RequirementChairAndTable(4.0F, false, false),
                  new EntityAINPC.RequirementChairAndTable(4.0F, true, false),
                  new EntityAINPC.RequirementLight(8, 15, false)
               )
            );
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
      }

      public int getSizeInventory() {
         return 1;
      }

      public boolean isEmpty() {
         return this.containedItemStack.isEmpty();
      }

      public ItemStack getStackInSlot(int index) {
         return this.containedItemStack;
      }

      public ItemStack decrStackSize(int index, int count) {
         return this.containedItemStack.splitStack(count);
      }

      public ItemStack removeStackFromSlot(int index) {
         ItemStack stack = this.containedItemStack;
         this.containedItemStack = ItemStack.EMPTY;
         return stack;
      }

      public void setInventorySlotContents(int index, ItemStack stack) {
         this.containedItemStack = stack;
      }

      public int getInventoryStackLimit() {
         return 1;
      }

      public void markDirty() {
      }

      public boolean isUsableByPlayer(EntityPlayer player) {
         return player.getDistanceSq(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5) <= 64.0;
      }

      public void openInventory(EntityPlayer player) {
      }

      public void closeInventory(EntityPlayer player) {
      }

      public boolean isItemValidForSlot(int index, ItemStack stack) {
         return true;
      }

      public int getField(int id) {
         return 0;
      }

      public void setField(int id, int value) {
      }

      public int getFieldCount() {
         return 0;
      }

      public void clear() {
         this.containedItemStack = ItemStack.EMPTY;
      }
   }

   public static class NpcMerchant extends NpcTrader {
      public NpcMerchant(World world) {
         super(world, 0.6F, 1.95F);
         this.hurtSound = SoundEvents.ENTITY_VILLAGER_HURT;
         this.deathSound = SoundEvents.ENTITY_VILLAGER_DEATH;
         this.livingSound = SoundEvents.ENTITY_VILLAGER_AMBIENT;
         this.setattributes(20.0, 16.0, 8.0, 0.25, 5.0, 5.0, 0.0, 0.0, 0.0, 0.0);
         this.comfortabilityPointsMax = 6;
      }

      public double getYOffset() {
         return this.isRiding() ? -0.45 : super.getYOffset();
      }

      @Override
      public boolean processInteract(EntityPlayer player, EnumHand hand) {
         if (!this.world.isRemote && FindAmmo.getSlotFor(player.inventory, ItemsRegister.STINGER) != -1) {
            for (Trade trade : this.trades) {
               if (trade.sell != null && trade.sell[0] != null && trade.sell[0].getItem() == ItemsRegister.STINGERBOLTS) {
                  return super.processInteract(player, hand);
               }
            }

            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.STINGERBOLTS, 1), 4, 0, 0.0F));
         }

         return super.processInteract(player, hand);
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         if (!this.world.isRemote) {
            Random rand = this.getRNG();
            this.trades.add(new Trade(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.BREAD, 3), 0, 0, 0.0F));
            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(Items.IRON_PICKAXE), 10, 0, 0.0F));
            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(Blocks.TORCH, 14), 1, 0, 0.0F));
            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(Items.GUNPOWDER, 1), 1, 0, 0.0F));
            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(Items.SLIME_BALL, 2), 3, 0, 0.0F));
            this.trades.add(new Trade(new ItemStack(Items.IRON_INGOT), NPCMobsPack.EM, 0, 2, 0.0F));
            this.trades.add(new Trade(new ItemStack(Items.GOLD_INGOT), NPCMobsPack.EM, 0, 4, 0.0F));
            this.trades.add(new Trade(new ItemStack(Items.DIAMOND), NPCMobsPack.EM, 0, 8, 0.0F));
            if (rand.nextFloat() < 0.75) {
               if (rand.nextFloat() < 0.66) {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.BULLETLEAD, 12), 1, 0, 0.0F));
               } else if (rand.nextFloat() < 0.5) {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.BULLETCOPPER, 12), 1, 0, 0.0F));
               } else {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.BULLETSILVER, 10), 1, 0, 0.0F));
               }
            }

            if (rand.nextFloat() < 0.75) {
               if (rand.nextFloat() < 0.66) {
                  this.trades.add(new Trade(NPCMobsPack.EM, Buckshot.getBuckshotStack("copper", 4), 1, 0, 0.0F));
               } else if (rand.nextFloat() < 0.5) {
                  this.trades.add(new Trade(NPCMobsPack.EM, Buckshot.getBuckshotStack("lead", 4), 1, 0, 0.0F));
               } else {
                  this.trades.add(new Trade(NPCMobsPack.EM, Buckshot.getBuckshotStack("silver", 3), 1, 0, 0.0F));
               }
            }

            if (rand.nextFloat() < 0.75) {
               if (rand.nextFloat() < 0.66) {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.ROCKETCOMMON, 3), 2, 0, 0.0F));
               } else if (rand.nextFloat() < 0.5) {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.ROCKETMINING, 3), 2, 0, 0.0F));
               } else {
                  this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.ROCKETDEMOLISHING, 1), 2, 0, 0.0F));
               }
            }

            if (rand.nextFloat() < 0.4) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.ROPEGH), 10, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(Blocks.IRON_ORE), 9, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(Blocks.GOLD_ORE), 15, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.AMETHYST), 16, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.RHINESTONE), 8, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.CITRINE), 23, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.SAPPHIRE), 23, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.RUBY), 23, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(Items.EMERALD), 23, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.3) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.TOPAZ), 16, 0, 0.0F));
            }

            if (rand.nextFloat() < 0.25) {
               this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(Items.DIAMOND), 30, 0, 0.0F));
            }
         }

         return super.onInitialSpawn(difficulty, livingdata);
      }

      protected void initEntityAI() {
         this.tasks
            .addTask(
               1,
               new EntityAINPC(
                  this,
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.furnaces, 200, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.craftables, 220, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementChairAndTable(4.0F, false, false),
                  new EntityAINPC.RequirementChairAndTable(4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.water, 260, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementLight(6, 15, false)
               )
            );
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
      }
   }

   public abstract static class NpcTrader extends EntityCreature implements IEntitySynchronize, IMultitexture {
      public double maxHealth;
      public double followRange;
      public double damage;
      public double moveSpeed;
      public double armor;
      public double protection;
      public double knockResist;
      public double knockback;
      public double vampirism;
      public double jumpHeight;
      public SoundEvent hurtSound = SoundEvents.ENTITY_HOSTILE_HURT;
      public SoundEvent deathSound = SoundEvents.ENTITY_HOSTILE_DEATH;
      public SoundEvent livingSound = null;
      public SoundEvent stepSound = null;
      public List<Trade> trades = new ArrayList<>();
      public float reputation = 1.0F;
      public int comfortabilityPacked = 0;
      public int comfortabilityPointsMax = 1;
      public static float comfortabilityMax = 100.0F;
      public int comfortability = -1;
      public int coins = 0;
      public int maxcoins = 1000;
      public EntityPlayer tradingPlayer = null;

      public NpcTrader(World worldIn, float sizeWidth, float sizeHeight) {
         super(worldIn);
         this.setSize(sizeWidth, sizeHeight);
      }

      @Override
      public void onClient(double... args) {
         if (args.length == 1) {
            this.comfortability = (int)args[0];
         }
      }

      public void setComfortPoint(int shift, boolean value) {
         this.comfortability = -1;
         if (value) {
            this.comfortabilityPacked |= 1 << shift;
         } else {
            this.comfortabilityPacked &= ~(1 << shift);
         }
      }

      public void removeComfortPoint(int amount) {
         this.comfortability = -1;

         for (int i = 1; i < 1073741824; i <<= 1) {
            if ((this.comfortabilityPacked & i) > 0) {
               this.comfortabilityPacked &= ~i;
               if (--amount <= 0) {
                  return;
               }
            }
         }
      }

      public int getComfortability() {
         if (this.comfortability >= 0) {
            return this.comfortability;
         } else {
            float points = 0.0F;

            for (int i = 1; i < 1073741824; i <<= 1) {
               if ((this.comfortabilityPacked & i) > 0) {
                  points++;
               }
            }

            this.comfortability = Math.round(points / this.comfortabilityPointsMax * comfortabilityMax);
            return this.comfortability;
         }
      }

      public void guiclick(EntityPlayer player, int mouseX, int mouseY, int mouseButton, int page) {
         int multipleTimes = mouseButton == 6 ? 4 : 1;
         if (mouseX > 83 && mouseX < 205) {
            if (mouseY > 41 && mouseY < 57) {
               this.tryDoTrade(player, page, 0, multipleTimes);
            }

            if (mouseY > 66 && mouseY < 82) {
               this.tryDoTrade(player, page, 1, multipleTimes);
            }

            if (mouseY > 91 && mouseY < 107) {
               this.tryDoTrade(player, page, 2, multipleTimes);
            }

            if (mouseY > 116 && mouseY < 132) {
               this.tryDoTrade(player, page, 3, multipleTimes);
            }

            if (mouseY > 141 && mouseY < 157) {
               this.tryDoTrade(player, page, 4, multipleTimes);
            }
         }
      }

      public void tryDoTrade(EntityPlayer player, int page, int num, int multipleTimes) {
         boolean success = false;

         for (int i = 0; i < multipleTimes; i++) {
            Trade trade = this.getTradeOn(page, num);
            if (trade != null) {
               trade.tradeWithPlayer(player);
               success = true;
            }
         }

         if (success) {
            this.synchronizeTrades(player);
            this.playSound(Sounds.buy, 0.8F, 1.0F);
         }
      }

      public boolean attackEntityFrom(DamageSource source, float amount) {
         this.removeComfortPoint((int)amount);
         return super.attackEntityFrom(source, amount);
      }

      @Nullable
      public Trade getTradeOn(int page, int num) {
         return page * 5 + num < this.trades.size() ? this.trades.get(page * 5 + num) : null;
      }

      public boolean processInteract(EntityPlayer player, EnumHand hand) {
         if (!this.world.isRemote && !this.trades.isEmpty()) {
            this.synchronizeTrades(player);
            IEntitySynchronize.sendSynchronize(this, 8.0, this.getComfortability());
            player.openGui(Main.instance, 6, this.world, this.getEntityId(), 0, 0);
         }

         return true;
      }

      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted % (20.0F - this.reputation) * 2.0F == 0.0F) {
            this.coins++;
         }

         if (this.ticksExisted % ((comfortabilityMax - this.getComfortability()) * 20.0F + 40.0F) == 0.0F) {
            this.heal(1.0F);
         }
      }

      public void synchronizeTrades(EntityPlayer player) {
         if (player instanceof EntityPlayerMP) {
            NBTTagCompound tag = new NBTTagCompound();
            PacketTradesToClient packet = new PacketTradesToClient();
            packet.write(this.writeToNBT(tag), this.getEntityId());
            PacketHandler.sendTo(packet, (EntityPlayerMP)player);
         }
      }

      public void setattributes(
         double maxHealth,
         double followRange,
         double damage,
         double moveSpeed,
         double armor,
         double protection,
         double knockResist,
         double knockback,
         double vampirism,
         double jumpHeight
      ) {
         this.maxHealth = maxHealth;
         this.followRange = followRange;
         this.damage = damage;
         this.moveSpeed = moveSpeed;
         this.armor = armor;
         this.protection = protection;
         this.knockResist = knockResist;
         this.knockback = knockback;
         this.vampirism = vampirism;
         this.jumpHeight = jumpHeight;
         this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHealth);
         this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(followRange);
         this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(moveSpeed);
         this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(damage);
         this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(armor);
         this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(knockResist);
         this.getEntityAttribute(PropertiesRegistry.ARMOR_PROTECTION).setBaseValue(protection);
         this.getEntityAttribute(PropertiesRegistry.JUMP_HEIGHT).setBaseValue(jumpHeight);
         this.getEntityAttribute(PropertiesRegistry.VAMPIRISM).setBaseValue(vampirism);
         this.getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK).setBaseValue(knockback);
      }

      protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
         return this.hurtSound;
      }

      protected SoundEvent getDeathSound() {
         return this.deathSound;
      }

      protected SoundEvent getAmbientSound() {
         return this.livingSound;
      }

      protected void playStepSound(BlockPos pos, Block blockIn) {
         if (this.stepSound != null) {
            this.playSound(this.stepSound, 0.15F, 1.0F);
         } else {
            super.playStepSound(pos, blockIn);
         }
      }

      protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
         this.dropFewItems(wasRecentlyHit, lootingModifier);
         this.dropEquipment(wasRecentlyHit, lootingModifier);
      }

      public NBTTagCompound writeToNBT(NBTTagCompound compound) {
         super.writeToNBT(compound);
         int num = 0;

         for (Trade trade : this.trades) {
            trade.savetoEntityNbt(compound, num);
            num++;
         }

         compound.setLong("timePassed", this.world.getTotalWorldTime());
         compound.setInteger("coins", this.coins);
         compound.setFloat("reputation", this.reputation);
         compound.setInteger("comfortabilityPacked", this.comfortabilityPacked);
         return compound;
      }

      public void readFromNBT(NBTTagCompound compound) {
         super.readFromNBT(compound);
         int num = 0;
         this.trades.clear();

         for (; compound.hasKey("trade" + num); num++) {
            Trade trade = new Trade();
            trade.loadfromEntityNbt(compound, num);
            if (trade.buy != null && trade.sell != null) {
               this.trades.add(trade);
            }
         }

         long totalTimePassed = 0L;
         if (compound.hasKey("timePassed")) {
            totalTimePassed = compound.getLong("timePassed");
         }

         if (compound.hasKey("coins")) {
            this.coins = compound.getInteger("coins");
         }

         if (compound.hasKey("reputation")) {
            this.reputation = compound.getFloat("reputation");
         }

         if (compound.hasKey("comfortabilityPacked")) {
            this.comfortabilityPacked = compound.getInteger("comfortabilityPacked");
         }

         long razn = this.world.getTotalWorldTime() - totalTimePassed;
         if (razn > 0L) {
            this.coins = (int)Math.min(this.coins + (float)razn / ((20.0F - this.reputation) * 2.0F), (float)this.maxcoins);
         }
      }

      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         this.setHealth((float)this.maxHealth);
         return super.onInitialSpawn(difficulty, livingdata);
      }

      protected void applyEntityAttributes() {
         super.applyEntityAttributes();
         this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      }

      @SideOnly(Side.CLIENT)
      @Nullable
      @Override
      public ResourceLocation getMultitexture() {
         return null;
      }
   }

   public static class NpcWizard extends NpcTrader {
      public NpcWizard(World world) {
         super(world, 0.6F, 1.95F);
         this.hurtSound = SoundEvents.ENTITY_VILLAGER_HURT;
         this.deathSound = SoundEvents.ENTITY_VILLAGER_DEATH;
         this.setattributes(25.0, 16.0, 8.0, 0.25, 4.0, 4.0, 0.0, 0.0, 0.0, 0.0);
         this.comfortabilityPointsMax = 10;
      }

      public double getYOffset() {
         return this.isRiding() ? -0.45 : super.getYOffset();
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         if (!this.world.isRemote) {
            Random rand = this.getRNG();
            this.trades.add(new Trade(new ItemStack(ItemsRegister.MAGIC_POWDER), new ItemStack(ItemsRegister.LOOTBOXENCHANTALL, 1), 4, 0, 0.0F));
            this.trades.add(new Trade(new ItemStack(ItemsRegister.MAGIC_POWDER), new ItemStack(ItemsRegister.LOOTBOXENCHANTSIMPLE, 1), 6, 0, 0.0F));
            this.trades
               .add(new Trade(new ItemStack(ItemsRegister.MAGIC_POWDER, 2), new ItemStack(ItemsRegister.LOOTBOXENCHANTWEAPON, 1), 10, 0, 0.0F));
            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.MAGIC_POWDER, 1), 5, 0, 0.0F));
            if (rand.nextFloat() < 0.6) {
               this.trades.add(new Trade(new ItemStack(Items.EMERALD), NPCMobsPack.EM, 0, 5, 0.0F));
            }

            if (rand.nextFloat() < 0.6) {
               this.trades.add(new Trade(new ItemStack(ItemsRegister.AMETHYST), NPCMobsPack.EM, 0, 3, 0.0F));
            }

            if (rand.nextFloat() < 0.6) {
               this.trades.add(new Trade(new ItemStack(ItemsRegister.RHINESTONE), NPCMobsPack.EM, 0, 1, 0.0F));
            }

            if (rand.nextFloat() < 0.6) {
               this.trades.add(new Trade(new ItemStack(ItemsRegister.CITRINE), NPCMobsPack.EM, 0, 5, 0.0F));
            }

            if (rand.nextFloat() < 0.6) {
               this.trades.add(new Trade(new ItemStack(ItemsRegister.SAPPHIRE), NPCMobsPack.EM, 0, 5, 0.0F));
            }

            if (rand.nextFloat() < 0.6) {
               this.trades.add(new Trade(new ItemStack(ItemsRegister.RUBY), NPCMobsPack.EM, 0, 5, 0.0F));
            }

            if (rand.nextFloat() < 0.6) {
               this.trades.add(new Trade(new ItemStack(ItemsRegister.TOPAZ), NPCMobsPack.EM, 0, 3, 0.0F));
            }
         }

         return super.onInitialSpawn(difficulty, livingdata);
      }

      protected void initEntityAI() {
         this.tasks
            .addTask(
               1,
               new EntityAINPC(
                  this,
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.spellforge, 160, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.manaContainer, 160, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.books, 120, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.cauldrons, 180, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.potionBrewery, 160, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.enchantTable, 180, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.industrialBlock, 120, 200, 4.0F, false, true),
                  new EntityAINPC.RequirementChairAndTable(4.0F, false, false),
                  new EntityAINPC.RequirementChairAndTable(4.0F, false, false),
                  new EntityAINPC.RequirementLight(8, 15, false)
               )
            );
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
      }
   }

   public static class NpcZarpionTrader extends NpcTrader {
      public boolean isEnslaved;

      public NpcZarpionTrader(World world) {
         super(world, 0.7F, 1.7F);
         this.hurtSound = Sounds.mob_storm_hurt;
         this.deathSound = Sounds.mob_storm_death;
         this.livingSound = Sounds.mob_storm_living;
         this.setattributes(300.0, 48.0, 12.0, 0.1, 2.0, 6.0, 1.0, 0.1, 0.0, 0.0);
         this.comfortabilityPointsMax = 10;
      }

      @Override
      public NBTTagCompound writeToNBT(NBTTagCompound compound) {
         compound.setBoolean("isEnslaved", this.isEnslaved);
         return super.writeToNBT(compound);
      }

      @Override
      public void readFromNBT(NBTTagCompound compound) {
         if (compound.hasKey("isEnslaved")) {
            this.isEnslaved = compound.getBoolean("isEnslaved");
         }

         super.readFromNBT(compound);
      }

      @Override
      public boolean processInteract(EntityPlayer player, EnumHand hand) {
         return this.isEnslaved ? false : super.processInteract(player, hand);
      }

      public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
         if (!player.world.isRemote
            && this.isEnslaved
            && player.getHeldItemMainhand().getItem() == ItemsRegister.STORMSPAWNERPIECE
            && player.getHeldItemMainhand().getCount() >= 16) {
            player.getHeldItemMainhand().shrink(16);
            this.isEnslaved = false;
            this.world.setEntityState(this, (byte)8);
            this.world.setEntityState(this, (byte)11);
            return EnumActionResult.SUCCESS;
         } else {
            return super.applyPlayerInteraction(player, vec, hand);
         }
      }

      protected boolean isMovementBlocked() {
         return this.isEnslaved || super.isMovementBlocked();
      }

      @Override
      public void onUpdate() {
         super.onUpdate();
         if (this.ticksExisted < 3 || this.ticksExisted % 20 == 0) {
            this.world.setEntityState(this, (byte)(this.isEnslaved ? 9 : 8));
         }

         if (this.isEnslaved) {
            this.rotationPitch = -75.0F;
            this.prevRotationPitch = -75.0F;
         }

         if (this.world.isRemote && this.isEnslaved && this.ticksExisted % 3 == 0) {
            double d0 = this.posX;
            double d1 = this.posY + 0.5;
            double d2 = this.posZ;
            Vec3d pos1 = GetMOP.entityCenterPos(this);

            for (int i = 0; i < 4; i++) {
               Vec3d pos2 = null;
               if (i == 0) {
                  pos2 = pos1.add(4.0, -1.5, 4.0);
               }

               if (i == 1) {
                  pos2 = pos1.add(-4.0, -1.5, 4.0);
               }

               if (i == 2) {
                  pos2 = pos1.add(4.0, -1.5, -4.0);
               }

               if (i == 3) {
                  pos2 = pos1.add(-4.0, -1.5, -4.0);
               }

               RayTraceResult result = this.world.rayTraceBlocks(pos1, pos2, false, true, false);
               Vec3d pos3;
               if (result != null && result.hitVec != null) {
                  pos3 = result.hitVec;
               } else {
                  pos3 = pos2;
               }

               ParticleFastSummon.coloredLightning(
                  this.world,
                  0.06F,
                  5,
                  240,
                  0.9F + this.rand.nextFloat() * 0.1F,
                  0.9F + this.rand.nextFloat() * 0.1F,
                  1.0F,
                  5,
                  pos1,
                  pos3,
                  pos1.squareDistanceTo(pos3) > 2.0 ? 0.5F : 0.25F,
                  0.4F,
                  ParticleFastSummon.rand
               );
            }
         }
      }

      public double getYOffset() {
         return this.isRiding() ? -0.45 : super.getYOffset();
      }

      @Override
      public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
         if (!this.world.isRemote) {
            Random rand = this.getRNG();
            this.trades.add(new Trade(new ItemStack(ItemsRegister.STORMSPAWNERPIECE), new ItemStack(ItemsRegister.INGOTSTORMBRASS), 20, 0, 0.0F));
            this.trades.add(new Trade(new ItemStack(ItemsRegister.STABILIZATIONCELL), new ItemStack(ItemsRegister.ANTICHARGE), 200, 0, 0.0F));
            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.PHASEOLITE), 1000, 0, 0.0F));
            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.ETHERITEFUELCELL), 80, 0, 0.0F));
            this.trades.add(new Trade(NPCMobsPack.EM, new ItemStack(ItemsRegister.ETHERSIGN), 800, 0, 0.0F));
            this.trades.add(new Trade(new ItemStack(ItemsRegister.THUNDERSTONE), new ItemStack(ItemsRegister.SKYCRYSTALPIECE, 2), 55, 0, 0.0F));
            this.trades.add(new Trade(new ItemStack(ItemsRegister.SOLIDIFIEDLIGHTNING), new ItemStack(ItemsRegister.SKYCRYSTAL), 55, 0, 0.0F));
            this.isEnslaved = true;
         }

         return super.onInitialSpawn(difficulty, livingdata);
      }

      public void handleStatusUpdate(byte id) {
         super.handleStatusUpdate(id);
         if (id == 8) {
            this.isEnslaved = false;
         }

         if (id == 9) {
            this.isEnslaved = true;
         }

         if (id == 11) {
            this.world
               .playSound(
                  this.posX,
                  this.posY,
                  this.posZ,
                  Sounds.item_misc_l,
                  SoundCategory.HOSTILE,
                  1.7F,
                  0.9F + this.rand.nextFloat() / 5.0F,
                  false
               );
         }
      }

      protected void initEntityAI() {
         this.tasks
            .addTask(
               1,
               new EntityAINPC(
                  this,
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.spellforge, 160, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.manaContainer, 160, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.cauldrons, 180, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.potionBrewery, 160, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.enchantTable, 180, 200, 4.0F, true, false),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.industrialBlock, 120, 200, 4.0F, false, true),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.industrialMachinery, 120, 200, 4.0F, false, true),
                  new EntityAINPC.RequirementMoveToBlock(NPCMobsPack.redstone, 120, 200, 4.0F, false, true),
                  new EntityAINPC.RequirementChairAndTable(4.0F, false, false),
                  new EntityAINPC.RequirementChairAndTable(4.0F, false, false)
               )
            );
         this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0));
         this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
         this.tasks.addTask(8, new EntityAILookIdle(this));
      }
   }

   public static class Trade {
      public ItemStack[] buy;
      public ItemStack[] sell;
      public int coinsBuy;
      public int coinsSell;
      public float reputationAdd;
      public boolean useNbt;
      public boolean useMeta;

      public Trade(ItemStack[] buy, ItemStack[] sell, int coinsBuy, int coinsSell, float reputationAdd, boolean useNbt, boolean useMeta) {
         this.buy = buy;
         this.sell = sell;
         this.coinsBuy = coinsBuy;
         this.coinsSell = coinsSell;
         this.reputationAdd = reputationAdd;
         this.useNbt = useNbt;
         this.useMeta = useMeta;
      }

      public Trade(ItemStack[] buy, ItemStack[] sell, int coinsBuy, int coinsSell, float reputationAdd) {
         this.buy = buy;
         this.sell = sell;
         this.coinsBuy = coinsBuy;
         this.coinsSell = coinsSell;
         this.reputationAdd = reputationAdd;
         this.useNbt = false;
         this.useMeta = false;
      }

      public Trade() {
         this.buy = null;
         this.sell = null;
         this.coinsBuy = 0;
         this.coinsSell = 0;
         this.reputationAdd = 0.0F;
         this.useNbt = false;
         this.useMeta = false;
      }

      public Trade(ItemStack buy, ItemStack sell, int coinsBuy, int coinsSell, float reputationAdd) {
         this.buy = new ItemStack[]{buy, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
         this.sell = new ItemStack[]{sell, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
         this.coinsBuy = coinsBuy;
         this.coinsSell = coinsSell;
         this.reputationAdd = reputationAdd;
         this.useNbt = false;
         this.useMeta = false;
      }

      public boolean tradeWithPlayer(EntityPlayer player) {
         if (!player.world.isRemote) {
            if (player.isSpectator()) {
               return false;
            }

            if (this.coinsBuy <= 0 || Coins.getCoins(player) >= this.coinsBuy) {
               if (this.buy != null) {
                  ArrayList<int[]> removes = this.getRemoves(player.inventory.mainInventory);
                  if (removes == null) {
                     return false;
                  }

                  for (int[] ints : removes) {
                     ItemStack stack = (ItemStack)player.inventory.mainInventory.get(ints[0]);
                     stack.setCount(stack.getCount() - ints[1]);
                  }
               }

               int coins = -this.coinsBuy + this.coinsSell;
               if (coins != 0) {
                  Coins.addCoins(player, coins);
               }

               if (this.sell != null) {
                  for (int ss = 0; ss < 4; ss++) {
                     if (this.sell[ss] != null && this.sell[ss] != ItemStack.EMPTY) {
                        ItemStack newstack = this.sell[ss].copy();
                        if (!player.inventory.addItemStackToInventory(newstack)) {
                           player.world
                              .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
                        }
                     }
                  }
               }

               return true;
            }
         }

         return false;
      }

      @Nullable
      public ArrayList<int[]> getRemoves(NonNullList<ItemStack> inventory) {
         ArrayList<int[]> removes = new ArrayList<>();
         ItemStack[] buy2 = this.copyOfBuy();
         int buylength = this.buy.length;

         for (int i = 0; i < inventory.size(); i++) {
            ItemStack hasInInv = ((ItemStack)inventory.get(i)).copy();

            for (int b = 0; b < buylength; b++) {
               if (!buy2[b].isEmpty() && this.stacksMathes(hasInInv, buy2[b])) {
                  int min = Math.min(hasInInv.getCount(), buy2[b].getCount());
                  if (min > 0) {
                     removes.add(new int[]{i, min});
                     buy2[b].setCount(buy2[b].getCount() - min);
                     hasInInv.setCount(hasInInv.getCount() - min);
                  }
               }
            }
         }

         for (ItemStack buy2b : buy2) {
            if (!buy2b.isEmpty()) {
               return null;
            }
         }

         return removes;
      }

      public boolean stacksMathes(ItemStack have, ItemStack need) {
         if (this.useNbt) {
            boolean b = have.hasTagCompound();
            if (b != need.hasTagCompound()) {
               return false;
            }

            if (b && !have.getTagCompound().equals(need.getTagCompound())) {
               return false;
            }
         }

         return this.useMeta && have.getMetadata() != need.getMetadata() ? false : have.getItem() == need.getItem();
      }

      public ItemStack[] copyOfBuy() {
         ItemStack[] buy2 = new ItemStack[4];

         for (int ss = 0; ss < 4; ss++) {
            if (this.buy[ss] != null && !this.buy[ss].isEmpty()) {
               buy2[ss] = this.buy[ss].copy();
            } else {
               buy2[ss] = ItemStack.EMPTY;
            }
         }

         return buy2;
      }

      @Deprecated
      public void traderWithPlayer(EntityPlayer player) {
         int siz = 27;
         int[] removes = new int[siz];
         if (this.buy != null) {
            if (this.buy[0] != null && this.buy[0] != ItemStack.EMPTY && !this.hasStack(player.inventory, this.buy[0], removes, siz)) {
               return;
            }

            if (this.buy[1] != null && this.buy[1] != ItemStack.EMPTY && !this.hasStack(player.inventory, this.buy[1], removes, siz)) {
               return;
            }

            if (this.buy[2] != null && this.buy[2] != ItemStack.EMPTY && !this.hasStack(player.inventory, this.buy[2], removes, siz)) {
               return;
            }

            if (this.buy[3] != null && this.buy[3] != ItemStack.EMPTY && !this.hasStack(player.inventory, this.buy[3], removes, siz)) {
               return;
            }

            if (Coins.getCoins(player) < this.coinsBuy) {
               return;
            }
         }

         for (int in = 0; in < siz; in++) {
            if (removes[in] != 0) {
               ItemStack has = player.inventory.getStackInSlot(in);
               ItemStack todecrease = has.copy();
               todecrease.setCount(has.getCount() - removes[in]);
               player.inventory.setInventorySlotContents(in, todecrease);
            }
         }

         int coins = -this.coinsBuy + this.coinsSell;
         if (coins != 0) {
            Coins.addCoins(player, coins);
         }

         if (this.sell != null) {
            for (int ss = 0; ss < 4; ss++) {
               if (this.sell[ss] != null && this.sell[ss] != ItemStack.EMPTY) {
                  ItemStack newstack = this.sell[ss].copy();
                  if (!player.inventory.addItemStackToInventory(newstack) && !player.world.isRemote) {
                     player.world
                        .spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, newstack));
                  }
               }
            }
         }
      }

      @Deprecated
      public boolean hasStack(InventoryPlayer inv, ItemStack stack, int[] removes, int sizeinv) {
         int need = stack.getCount();
         int have = 0;

         for (int i = 0; i < sizeinv; i++) {
            ItemStack inslot = inv.getStackInSlot(i);
            if (inslot.getItem() == stack.getItem()
               && (!this.useMeta || inslot.getMetadata() == stack.getMetadata())
               && (!this.useNbt || this.nbtMathes(inslot, stack))) {
               have += inslot.getCount() - removes[i];
               removes[i] += Math.min(inslot.getCount(), need);
            }
         }

         return have >= need;
      }

      public void savetoEntityNbt(NBTTagCompound tag, int number) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         saveAllItems(nbttagcompound, this.buy, "buytag");
         saveAllItems(nbttagcompound, this.sell, "selltag");
         nbttagcompound.setInteger("coinsbuy", this.coinsBuy);
         nbttagcompound.setInteger("coinssell", this.coinsSell);
         nbttagcompound.setFloat("reputationAdd", this.reputationAdd);
         nbttagcompound.setBoolean("useNbt", this.useNbt);
         nbttagcompound.setBoolean("useMeta", this.useMeta);
         tag.setTag("trade" + number, nbttagcompound);
      }

      public void loadfromEntityNbt(NBTTagCompound tag, int number) {
         NBTTagCompound nbttagcompound = tag.getCompoundTag("trade" + number);
         this.buy = loadAllItems(nbttagcompound, "buytag");
         this.sell = loadAllItems(nbttagcompound, "selltag");
         this.coinsBuy = nbttagcompound.getInteger("coinsbuy");
         this.coinsSell = nbttagcompound.getInteger("coinssell");
         this.reputationAdd = nbttagcompound.getFloat("reputationAdd");
         this.useNbt = nbttagcompound.getBoolean("useNbt");
         this.useMeta = nbttagcompound.getBoolean("useMeta");
      }

      public static NBTTagCompound saveAllItems(NBTTagCompound tag, ItemStack[] stacks4, String key) {
         NBTTagList nbttaglist = new NBTTagList();

         for (int i = 0; i < 4; i++) {
            ItemStack itemstack = stacks4[i];
            if (itemstack != null && !itemstack.isEmpty()) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.setByte("num", (byte)i);
               itemstack.writeToNBT(nbttagcompound);
               nbttaglist.appendTag(nbttagcompound);
            }
         }

         tag.setTag(key, nbttaglist);
         return tag;
      }

      public static ItemStack[] loadAllItems(NBTTagCompound tag, String key) {
         ItemStack[] stacks = new ItemStack[4];
         NBTTagList nbttaglist = tag.getTagList(key, 10);

         for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("num");
            stacks[j] = new ItemStack(nbttagcompound);
         }

         return stacks;
      }

      @Deprecated
      public boolean nbtMathes(ItemStack have, ItemStack need) {
         boolean b = have.hasTagCompound();
         return b != need.hasTagCompound() ? false : !b || have.getTagCompound().equals(need.getTagCompound());
      }
   }
}
