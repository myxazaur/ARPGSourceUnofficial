//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Desktop\stuff\asbtractrpg\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.events.Debugger;
import com.Vivern.Arpg.main.BlocksRegister;
import com.Vivern.Arpg.main.CreateItemFile;
import com.Vivern.Arpg.main.ItemsElements;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.NBTHelper;
import com.Vivern.Arpg.main.OreDicHelper;
import com.Vivern.Arpg.main.ShardType;
import com.Vivern.Arpg.main.Spell;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.SpawnerTuners;
import com.Vivern.Arpg.recipes.Ingridient;
import com.Vivern.Arpg.renders.LightOnPos;
import com.Vivern.Arpg.renders.LoadedRGBChunk;
import com.Vivern.Arpg.renders.StaticRGBLight;
import com.Vivern.Arpg.tileentity.IManaBuffer;
import com.Vivern.Arpg.tileentity.ManaBuffer;
import com.Vivern.Arpg.tileentity.SpawnerTuner;
import com.Vivern.Arpg.tileentity.TileAlchemicLab;
import com.Vivern.Arpg.tileentity.TileAssemblyTable;
import com.Vivern.Arpg.tileentity.TileIndustrialMixer;
import com.Vivern.Arpg.tileentity.TileMonsterSpawner;
import com.Vivern.Arpg.tileentity.TileNetherMelter;
import com.google.common.cache.LoadingCache;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.LootContext.Builder;

public class ExpItem extends Item {
   public ExpItem() {
      this.setRegistryName("expitem");
      this.setCreativeTab(CreativeTabs.MISC);
      this.setTranslationKey("expitem");
      this.setMaxDamage(100);
      this.setMaxStackSize(1);
   }

   public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      player.setActiveHand(hand);
      RayTraceResult raytraceresult = this.rayTrace(world, player, false);
      if (raytraceresult == null) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else if (raytraceresult.typeOfHit != Type.BLOCK) {
         return new ActionResult(EnumActionResult.PASS, itemstack);
      } else {
         BlockPos pos = raytraceresult.getBlockPos();
         BlockPos posup = pos.up();
         if (!itemstack.hasDisplayName() && !world.isRemote) {
         }

         if (!world.isRemote && player.isSneaking()) {
         }

         if (!world.isRemote) {
            if (itemstack.hasDisplayName()) {
               boolean nooredigname = "craftwritetool_nooredig".equals(itemstack.getDisplayName());
               if (!"craftwritetool".equals(itemstack.getDisplayName()) && !nooredigname) {
                  if ("classfinder".equals(itemstack.getDisplayName())) {
                     String dn = Debugger.string;

                     try {
                        Class clazz = Class.forName(dn);
                        System.out.println("FINDED : |" + dn + "|");
                        Field[] fields = clazz.getDeclaredFields();
                        System.out.println("FIELDS:");

                        for (Field field : fields) {
                           System.out.println(((field.getModifiers() & 8) != 0 ? "static " : "") + field.getType().getName() + " " + field.getName());
                        }

                        System.out.println("---");
                        Method[] methods = clazz.getDeclaredMethods();
                        System.out.println("METHODS:");
                        int i = 0;

                        for (Method method : methods) {
                           System.out.println(method.getName() + " " + i + ((method.getModifiers() & 8) != 0 ? " static" : ""));
                           i++;
                        }

                        System.out.println("---");
                        if (Debugger.floats[0] != 0.0F) {
                           System.out.println("PARAMETERS:");
                           Class<?>[] parameters = methods[(int)Debugger.floats[1]].getParameterTypes();

                           for (Class<?> claz : parameters) {
                              System.out.println(claz.getName());
                           }

                           System.out.println("---");
                           System.out.println("RETURN TYPE:");
                           Class<?> claz = methods[(int)Debugger.floats[1]].getReturnType();
                           System.out.println(claz.getName());
                           System.out.println("---");
                        }

                        System.out.println("CONSTRUCTORS:");
                        Constructor[] cons = clazz.getConstructors();

                        for (Constructor cntr : cons) {
                           System.out.println((cntr.isAccessible() ? "public " : " ") + cntr.getName() + " (");

                           for (Parameter par : cntr.getParameters()) {
                              System.out.println(", " + par.getType().getName() + " " + par.getName());
                           }

                           System.out.println(" )");
                        }

                        System.out.println("---");
                     } catch (Exception var34) {
                        System.out.println("NOT FINDED : |" + dn + "|");
                     }
                  } else if ("oredig".equals(itemstack.getDisplayName())) {
                     System.out.println(OreDicHelper.getOreNames(player.getHeldItemOffhand()));
                  } else if ("assemblytable".equals(itemstack.getDisplayName())) {
                     if (player.isSneaking()) {
                        TileEntity tile = world.getTileEntity(pos);
                        if (tile != null && tile instanceof TileAssemblyTable) {
                           try {
                              TileAssemblyTable assemblytable = (TileAssemblyTable)tile;
                              String text = "addRecipe(";
                              if (!assemblytable.getStackInSlot(14).isEmpty()) {
                                 text = text + Ingridient.getIngridient(assemblytable.getStackInSlot(14)).toString() + ", ";
                                 text = text + assemblytable.electricStorage.getEnergyStored() + ", ";
                                 text = text + Ingridient.getIngridient(assemblytable.getStackInSlot(15)).toString() + ", ";
                                 text = text + ", ";

                                 for (int i = 0; i < 9; i++) {
                                    if (i == 8) {
                                       text = text + Ingridient.getIngridient(assemblytable.getStackInSlot(i)).toString();
                                    } else {
                                       text = text + Ingridient.getIngridient(assemblytable.getStackInSlot(i)).toString() + ", ";
                                    }
                                 }

                                 for (int ix = 9; ix <= 13; ix++) {
                                    ItemStack st = assemblytable.getStackInSlot(ix);
                                    String AUGMENT = TileAssemblyTable.augmentToString(assemblytable.inventorySlotToAugment(ix));
                                    if (st.isEmpty()) {
                                       break;
                                    }

                                    if (ix == 9) {
                                       text = text + ", ";
                                    }

                                    text = text + "new AugmentCost(" + AUGMENT + ", " + Ingridient.getIngridient(st).toString() + ")";
                                    if (ix < 13 && !assemblytable.getStackInSlot(ix + 1).isEmpty()) {
                                       text = text + ", ";
                                    }
                                 }
                              }

                              text = text + ");";
                              System.out.print("\n");
                              System.out.print(text);
                           } catch (Exception var33) {
                           }
                        }
                     }
                  } else if ("hardres".equals(itemstack.getDisplayName())) {
                     System.out.println("hardness: " + world.getBlockState(pos).getBlock().getBlockHardness(world.getBlockState(pos), world, pos));

                     try {
                        Block bl = world.getBlockState(pos).getBlock();
                        Field field = Block.class.getDeclaredField("blockResistance");
                        field.setAccessible(true);
                        float res = field.getFloat(bl);
                        System.out.println("resistance: " + res);
                     } catch (Exception var31) {
                        var31.printStackTrace();
                     }
                  } else if ("spellforge".equals(itemstack.getDisplayName())) {
                     if (player.isSneaking()) {
                        TileEntity tile = world.getTileEntity(pos);
                        if (tile != null && tile instanceof TileEntityChest) {
                           try {
                              TileEntityChest chest = (TileEntityChest)tile;
                              String text = "addRecipe(, ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(17)).toString() + ", ";
                              int[] array = new int[12];

                              for (int j = 0; j < 3; j++) {
                                 for (int ix = 0; ix < 4; ix++) {
                                    int indx = j * 9 + ix;
                                    if (!chest.getStackInSlot(indx).isEmpty()) {
                                       array[MathHelper.clamp(chest.getStackInSlot(indx).getMetadata() - 1, 0, 11)] += chest.getStackInSlot(indx)
                                          .getCount();
                                    }
                                 }
                              }

                              for (int ixx = 0; ixx < 12; ixx++) {
                                 text = text + array[ixx] + ", ";
                              }

                              if (!chest.getStackInSlot(22).isEmpty()) {
                                 int shardFallback = chest.getStackInSlot(22).getMetadata();
                                 text = text + "ShardType." + ShardType.byId(shardFallback).name.toUpperCase() + ", ";
                              } else {
                                 text = text + "null, ";
                              }

                              text = text + Ingridient.getIngridient(chest.getStackInSlot(5)).toString() + ", ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(6)).toString() + ", ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(7)).toString() + ", ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(14)).toString() + ", ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(15)).toString() + ", ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(16)).toString() + ", ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(23)).toString() + ", ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(24)).toString() + ", ";
                              text = text + Ingridient.getIngridient(chest.getStackInSlot(25)).toString();
                              ItemStack roll = chest.getStackInSlot(4);
                              if (!roll.isEmpty()) {
                                 Spell[] spells = NBTHelper.readSpellsFromNbt(roll);
                                 if (spells != null) {
                                    for (int ixx = 0; ixx < spells.length; ixx++) {
                                       if (spells[ixx] != null) {
                                          text = text + ", Spell." + spells[ixx].name.toUpperCase();
                                       }
                                    }
                                 }
                              }

                              if (chest.getStackInSlot(13).isEmpty()) {
                                 text = text + ");";
                                 System.out.print("\n");
                                 System.out.print(text);
                              } else {
                                 text = text + ")";
                                 System.out.print("\n");
                                 System.out.print(text);
                                 System.out.print("\n");
                                 System.out.print(".setCatalyst(");
                                 System.out.print(Debugger.getAsIngridient(chest.getStackInSlot(13), false));
                                 System.out.print(");");
                              }
                           } catch (Exception var32) {
                              var32.printStackTrace();
                           }
                        }
                     }
                  } else if ("mixer".equals(itemstack.getDisplayName())) {
                     if (player.isSneaking()) {
                        TileEntity tile = world.getTileEntity(pos);
                        if (tile != null && tile instanceof TileIndustrialMixer) {
                           try {
                              TileIndustrialMixer mixer = (TileIndustrialMixer)tile;
                              String text = "addRecipe(";
                              text = text
                                 + (mixer.getStackInSlot(7).isEmpty() ? "null, " : Debugger.itemgetter(mixer.getStackInSlot(7).getItem()) + ", ");
                              text = text
                                 + (mixer.getStackInSlot(6).isEmpty() ? "null, " : Debugger.itemgetter(mixer.getStackInSlot(6).getItem()) + ", ");
                              text = text + ", ";
                              text = text + Ingridient.getIngridient(mixer.getStackInSlot(3)).toString() + ", ";
                              text = text + Ingridient.getIngridient(mixer.getStackInSlot(4)).toString() + ", ";
                              text = text + Ingridient.getIngridient(mixer.getStackInSlot(5)).toString() + ", ";
                              text = text + ", ";
                              text = text + Ingridient.getIngridient(mixer.getStackInSlot(0)).toString() + ", ";
                              text = text + Ingridient.getIngridient(mixer.getStackInSlot(1)).toString() + ", ";
                              text = text + Ingridient.getIngridient(mixer.getStackInSlot(2)).toString() + ")";
                              if (mixer.tank1.getFluidAmount() > 0) {
                                 text = text
                                    + ".setFluidCost1(\""
                                    + mixer.tank1.getFluid().getUnlocalizedName().replaceFirst("fluid.", "")
                                    + "\", "
                                    + mixer.tank1.getFluidAmount()
                                    + ")";
                              }

                              if (mixer.tank2.getFluidAmount() > 0) {
                                 text = text
                                    + ".setFluidCost2(\""
                                    + mixer.tank2.getFluid().getUnlocalizedName().replaceFirst("fluid.", "")
                                    + "\", "
                                    + mixer.tank2.getFluidAmount()
                                    + ")";
                              }

                              if (mixer.tank3.getFluidAmount() > 0) {
                                 text = text
                                    + ".setFluidOutput1(\""
                                    + mixer.tank3.getFluid().getUnlocalizedName().replaceFirst("fluid.", "")
                                    + "\", "
                                    + mixer.tank3.getFluidAmount()
                                    + ")";
                              }

                              if (mixer.tank4.getFluidAmount() > 0) {
                                 text = text
                                    + ".setFluidOutput2(\""
                                    + mixer.tank4.getFluid().getUnlocalizedName().replaceFirst("fluid.", "")
                                    + "\", "
                                    + mixer.tank4.getFluidAmount()
                                    + ")";
                              }

                              text = text + ";";
                              System.out.print(text);
                              System.out.print("\n");
                           } catch (Exception var30) {
                           }
                        }
                     }
                  } else if ("nethermelter".equals(itemstack.getDisplayName())) {
                     if (player.isSneaking()) {
                        TileEntity tile = world.getTileEntity(pos);
                        if (tile != null && tile instanceof TileNetherMelter) {
                           try {
                              TileNetherMelter melter = (TileNetherMelter)tile;
                              String textx = "addRecipe(";
                              textx = textx + Ingridient.getIngridient(melter.getStackInSlot(5)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(melter.getStackInSlot(6)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(melter.getStackInSlot(7)).toString() + ", ";
                              textx = textx + ", ";
                              textx = textx + Ingridient.getIngridient(melter.getStackInSlot(0)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(melter.getStackInSlot(1)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(melter.getStackInSlot(2)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(melter.getStackInSlot(3)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(melter.getStackInSlot(4)).toString();
                              textx = textx + ");";
                              System.out.print(textx);
                              System.out.print("\n");
                           } catch (Exception var29) {
                           }
                        }
                     }
                  } else if ("alchemiclab".equals(itemstack.getDisplayName())) {
                     if (player.isSneaking()) {
                        TileEntity tile = world.getTileEntity(pos);
                        if (tile != null && tile instanceof TileAlchemicLab) {
                           try {
                              TileAlchemicLab lab = (TileAlchemicLab)tile;
                              String textx = "addRecipe(, ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(6)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(7)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(8)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(9)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(10)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(11)).toString() + ", ";
                              textx = textx + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(0)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(1)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(2)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(3)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(4)).toString() + ", ";
                              textx = textx + Ingridient.getIngridient(lab.getStackInSlot(5)).toString() + ")";
                              if (lab.tank1.getFluidAmount() > 0) {
                                 textx = textx
                                    + ".setFluidCost(\""
                                    + lab.tank1.getFluid().getUnlocalizedName().replaceFirst("fluid.", "")
                                    + "\", "
                                    + lab.tank1.getFluidAmount()
                                    + ")";
                              }

                              if (lab.tank2.getFluidAmount() > 0) {
                                 textx = textx
                                    + ".setFluidOutput(\""
                                    + lab.tank2.getFluid().getUnlocalizedName().replaceFirst("fluid.", "")
                                    + "\", "
                                    + lab.tank2.getFluidAmount()
                                    + ")";
                              }

                              textx = textx + ";";
                              System.out.print(textx);
                              System.out.print("\n");
                           } catch (Exception var28) {
                           }
                        }
                     }
                  } else if ("exploringreset".equals(itemstack.getDisplayName())) {
                     NBTTagCompound entityData = player.getEntityData();
                     entityData.setTag("arpg_spells", new NBTTagCompound());
                  } else if (itemstack.getDisplayName().contains("loottable_")) {
                     world.getLootTableManager().reloadLootTables();
                     String loottable = itemstack.getDisplayName().replaceFirst("loottable_", "");
                     world.setBlockToAir(posup);
                     world.setBlockState(posup, Blocks.CHEST.getDefaultState());
                     TileEntity tileentity = world.getTileEntity(posup);
                     if (tileentity instanceof TileEntityLockableLoot) {
                        TileEntityLockableLoot lockableChest = (TileEntityLockableLoot)tileentity;
                        ResourceLocation resourceLocation = new ResourceLocation("arpg", "chests/" + loottable);

                        try {
                           Field field = LootTableManager.class.getDeclaredField("registeredLootTables");
                           field.setAccessible(true);
                           LoadingCache<ResourceLocation, LootTable> registeredLootTables = (LoadingCache<ResourceLocation, LootTable>)field.get(
                              world.getLootTableManager()
                           );
                           registeredLootTables.refresh(resourceLocation);
                        } catch (Exception var27) {
                           var27.printStackTrace();
                        }

                        lockableChest.clear();
                        this.fillWithLoot(player, loottable, lockableChest);
                     }
                  } else if ("sieve".equals(itemstack.getDisplayName())) {
                     if (player.isSneaking()) {
                        TileEntity tile = world.getTileEntity(pos);
                        if (tile != null && tile instanceof TileEntityChest) {
                           TileEntityChest chest = (TileEntityChest)tile;
                           if (!chest.getStackInSlot(0).isEmpty()) {
                              String textxx = "addRecipe(";
                              textxx = textxx + Ingridient.getIngridient(chest.getStackInSlot(0)).toString() + ", , Sound, new float[] {}";

                              for (int ixxx = 1; ixxx < 27 && !chest.getStackInSlot(ixxx).isEmpty(); ixxx++) {
                                 textxx = textxx + ", ";
                                 textxx = textxx + Ingridient.getIngridient(chest.getStackInSlot(ixxx)).toString();
                              }

                              textxx = textxx + ");";
                              System.out.print(textxx);
                              System.out.print("\n");
                           }
                        }
                     }
                  } else if ("spawner".equals(itemstack.getDisplayName())) {
                     int num = (int)Debugger.floats[15];

                     try {
                        Field field = SpawnerTuners.class.getFields()[num];
                        SpawnerTuner tuner = (SpawnerTuner)field.get(null);
                        world.setBlockState(posup, BlocksRegister.MOBSPAWNERFROZEN.getDefaultState());
                        TileMonsterSpawner spawner = (TileMonsterSpawner)world.getTileEntity(posup);
                        tuner.setupSpawner(world, spawner, itemRand);
                     } catch (ArrayIndexOutOfBoundsException var23) {
                        player.sendMessage(new TextComponentString("Index out of bound, use /debugvar 15 x "));
                     } catch (NullPointerException var24) {
                        player.sendMessage(new TextComponentString("Tile is null"));
                     } catch (ClassCastException var25) {
                        player.sendMessage(new TextComponentString("Field is not a SpawnerTuner or tile is not a TileMonsterSpawner"));
                     } catch (Exception var26) {
                        var26.printStackTrace();
                     }
                  } else if ("generated_elements".equals(itemstack.getDisplayName())) {
                     ItemsElements.writeGeneratedItemElements();
                  } else if ("manabuffer".equals(itemstack.getDisplayName())) {
                     if (player.isSneaking()) {
                        TileEntity tile = world.getTileEntity(pos);
                        if (tile != null && tile instanceof IManaBuffer) {
                           ManaBuffer.Calibration calibration = ManaBuffer.getCalibrationAt(player.world, pos);
                           ManaBuffer manabuffer = ((IManaBuffer)tile).getManaBuffer();
                           System.out.println(manabuffer.initialAttraction + calibration.attraction);
                        }
                     }
                  } else if ("startswarm".equals(itemstack.getDisplayName())) {
                     Mana.setSwarmTicks(player, -1);
                  }
               } else if (player.isSneaking()) {
                  TileEntity tile = world.getTileEntity(pos);
                  if (tile != null && tile instanceof TileEntityChest) {
                     TileEntityChest chest = (TileEntityChest)tile;
                     if (!chest.getStackInSlot(12).isEmpty()) {
                        ItemStack[] stacks = new ItemStack[]{
                           chest.getStackInSlot(0),
                           chest.getStackInSlot(1),
                           chest.getStackInSlot(2),
                           chest.getStackInSlot(9),
                           chest.getStackInSlot(10),
                           chest.getStackInSlot(11),
                           chest.getStackInSlot(18),
                           chest.getStackInSlot(19),
                           chest.getStackInSlot(20)
                        };
                        CreateItemFile.craftingLocationCreate(stacks, chest.getStackInSlot(12), true, !nooredigname);
                        Minecraft.getMinecraft().player.sendChatMessage("Created recipe json file to: " + chest.getStackInSlot(12));
                     }

                     if (!chest.getStackInSlot(17).isEmpty()) {
                        ItemStack[] stacks = new ItemStack[]{
                           chest.getStackInSlot(5),
                           chest.getStackInSlot(6),
                           chest.getStackInSlot(7),
                           chest.getStackInSlot(14),
                           chest.getStackInSlot(15),
                           chest.getStackInSlot(16),
                           chest.getStackInSlot(23),
                           chest.getStackInSlot(24),
                           chest.getStackInSlot(25)
                        };
                        CreateItemFile.craftingLocationCreate(stacks, chest.getStackInSlot(17), true, !nooredigname);
                        Minecraft.getMinecraft().player.sendChatMessage("Created recipe json file to: " + chest.getStackInSlot(17));
                     }
                  } else {
                     CreateItemFile.printUpdatedRecipesCode();
                  }
               }
            }
         } else {
            if ("reloadres".equals(itemstack.getDisplayName())) {
               Minecraft mc = Minecraft.getMinecraft();
               mc.getRenderItem().getItemModelMesher().getModelManager().onResourceManagerReload(mc.getResourceManager());
               player.sendMessage(new TextComponentString(I18n.translateToLocal("debug.reload_resourcepacks.message")));
            }

            if (itemstack.getDisplayName() != null && itemstack.getDisplayName().length() == 1) {
               char cha = itemstack.getDisplayName().charAt(0);
               System.out.println((int)cha);
            }
         }

         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
      }
   }

   public void fillWithLoot(EntityPlayer player, String lootTable, TileEntityLockableLoot tile) {
      if (lootTable != null && player.world instanceof WorldServer) {
         WorldServer server = (WorldServer)player.world;
         ResourceLocation resourceLocation = new ResourceLocation("chests", lootTable);
         LootTableManager ltm = new LootTableManager(new File(CreateItemFile.mainPatch + "loot_tables"));
         LootTable loottable = ltm.getLootTableFromLocation(resourceLocation);
         Random random = new Random();
         Builder lootcontext$builder = new Builder(server);
         if (player != null) {
            lootcontext$builder.withLuck(player.getLuck()).withPlayer(player);
         }

         loottable.fillInventory(tile, random, lootcontext$builder.build());
      }
   }

   public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
      if ("testmoney".equals(itemstack.getDisplayName()) && target instanceof AbstractMob) {
         AbstractMob mob = (AbstractMob)target;
         int money = 0;

         for (int i = 0; i < 15500; i++) {
            if (itemRand.nextFloat() < mob.moneyDroppedChance) {
               int difference = mob.moneyDroppedMax - mob.moneyDroppedMin + 1;
               if (difference > 0) {
                  int coinsfall = mob.moneyDroppedMin + itemRand.nextInt(difference);
                  if (coinsfall > 0) {
                     money += coinsfall;
                  }
               }
            }
         }

         System.out.println(money / 15500.0F);
         return true;
      } else {
         return super.itemInteractionForEntity(itemstack, playerIn, target, hand);
      }
   }

   public static final void lightAbsorbing(World world, BlockPos posup) {
      List<LoadedRGBChunk> customList = new ArrayList<>();
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX(), posup.getZ()));
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX() + 16, posup.getZ()));
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX() - 16, posup.getZ()));
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX(), posup.getZ() + 16));
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX(), posup.getZ() - 16));
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX() + 16, posup.getZ() + 16));
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX() - 16, posup.getZ() - 16));
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX() + 16, posup.getZ() - 16));
      customList.add(StaticRGBLight.getActualLoadedRGBChunk(posup.getX() - 16, posup.getZ() + 16));
      List<LightOnPos> lights = new ArrayList<>();

      for (int l = 0; l < 1; l++) {
         for (int xc = -14; xc < -13; xc++) {
            int fX = posup.getX() + xc;

            for (int zc = -14; zc < -13; zc++) {
               int fZ = posup.getZ() + zc;
               Debugger.startPROFILING();
               LoadedRGBChunk chunk = StaticRGBLight.getActualLoadedRGBChunk(customList, posup.getX() + xc, posup.getZ() + zc);
               Debugger.endPROFILING(1);
               if (chunk != null) {
                  for (int yc = -14; yc < -13; yc++) {
                     int fY = posup.getY() + yc;
                     if (l == 0) {
                        Debugger.startPROFILING();
                        BlockPos finalpos = posup.add(xc, yc, zc);
                        Block block = world.getBlockState(finalpos).getBlock();
                        if (block == Blocks.TORCH) {
                           int bkcoordRed = LoadedRGBChunk.getBakedCoordRed(finalpos.getX(), finalpos.getY(), finalpos.getZ());
                           chunk.setLight(bkcoordRed, (byte)8, (byte)14);
                           int bkcoordGreen = LoadedRGBChunk.getBakedCoordGreen(finalpos.getX(), finalpos.getY(), finalpos.getZ());
                           chunk.setLight(bkcoordGreen, (byte)6, (byte)14);
                           int bkcoordBlue = LoadedRGBChunk.getBakedCoordBlue(finalpos.getX(), finalpos.getY(), finalpos.getZ());
                           chunk.setLight(bkcoordBlue, (byte)2, (byte)14);
                        } else if (block == Blocks.SEA_LANTERN) {
                           int bkcoordRed = LoadedRGBChunk.getBakedCoordRed(finalpos.getX(), finalpos.getY(), finalpos.getZ());
                           chunk.setLight(bkcoordRed, (byte)6, (byte)15);
                           int bkcoordGreen = LoadedRGBChunk.getBakedCoordGreen(finalpos.getX(), finalpos.getY(), finalpos.getZ());
                           chunk.setLight(bkcoordGreen, (byte)11, (byte)15);
                           int bkcoordBlue = LoadedRGBChunk.getBakedCoordBlue(finalpos.getX(), finalpos.getY(), finalpos.getZ());
                           chunk.setLight(bkcoordBlue, (byte)11, (byte)15);
                        } else {
                           chunk.clearOnPosAllChannels(finalpos.getX(), finalpos.getY(), finalpos.getZ());
                        }

                        Debugger.endPROFILING(2);
                     }

                     Debugger.startPROFILING();
                     int coordRED = LoadedRGBChunk.getBakedCoordRed(fX, fY, fZ);
                     int coordGREEN = LoadedRGBChunk.getBakedCoordGreen(fX, fY, fZ);
                     int coordBLUE = LoadedRGBChunk.getBakedCoordBlue(fX, fY, fZ);
                     Debugger.endPROFILING(3);

                     for (byte i = 0; i < 1; i++) {
                        Debugger.startPROFILING();
                        byte redOnCurrentPos = LoadedRGBChunk.getLightForChannel(chunk.getBakedLight(coordRED), i);
                        byte combinedRed = 0;
                        byte greenOnCurrentPos = LoadedRGBChunk.getLightForChannel(chunk.getBakedLight(coordGREEN), i);
                        byte combinedGreen = 0;
                        byte blueOnCurrentPos = LoadedRGBChunk.getLightForChannel(chunk.getBakedLight(coordBLUE), i);
                        byte combinedBlue = 0;
                        Debugger.endPROFILING(4);
                        EnumFacing face = EnumFacing.NORTH;
                        int offsX = fX + face.getXOffset();
                        int offsY = fY + face.getYOffset();
                        int offsZ = fZ + face.getZOffset();
                        Debugger.startPROFILING();
                        LoadedRGBChunk chunk2 = face != EnumFacing.UP && face != EnumFacing.DOWN
                           ? StaticRGBLight.getActualLoadedRGBChunk(customList, offsX, offsZ)
                           : chunk;
                        Debugger.endPROFILING(5);
                        if (chunk2 != null) {
                           Debugger.startPROFILING();
                           byte redOnOffsetPos = LoadedRGBChunk.getLightForChannel(
                              chunk2.getBakedLight(LoadedRGBChunk.getBakedCoordRed(offsX, offsY, offsZ)), i
                           );
                           byte greenOnOffsetPos = LoadedRGBChunk.getLightForChannel(
                              chunk2.getBakedLight(LoadedRGBChunk.getBakedCoordGreen(offsX, offsY, offsZ)), i
                           );
                           byte blueOnOffsetPos = LoadedRGBChunk.getLightForChannel(
                              chunk2.getBakedLight(LoadedRGBChunk.getBakedCoordBlue(offsX, offsY, offsZ)), i
                           );
                           if (combinedRed < redOnOffsetPos) {
                              combinedRed = redOnOffsetPos;
                           }

                           if (combinedGreen < greenOnOffsetPos) {
                              combinedGreen = greenOnOffsetPos;
                           }

                           if (combinedBlue < blueOnOffsetPos) {
                              combinedBlue = blueOnOffsetPos;
                           }

                           Debugger.endPROFILING(6);
                        }

                        Debugger.startPROFILING();
                        combinedRed--;
                        if (combinedRed > 0 && combinedRed > redOnCurrentPos) {
                           lights.add(new LightOnPos(coordRED, i, combinedRed, chunk));
                        }

                        combinedGreen--;
                        if (combinedGreen > 0 && combinedGreen > greenOnCurrentPos) {
                           lights.add(new LightOnPos(coordGREEN, i, combinedGreen, chunk));
                        }

                        combinedBlue--;
                        if (combinedBlue > 0 && combinedBlue > blueOnCurrentPos) {
                           lights.add(new LightOnPos(coordBLUE, i, combinedBlue, chunk));
                        }

                        Debugger.endPROFILING(7);
                     }
                  }
               }
            }
         }

         Debugger.startPROFILING();

         for (LightOnPos lightOn : lights) {
            lightOn.chunk.setLight(lightOn.bakedCoord, lightOn.channel, lightOn.value);
         }

         lights.clear();
         Debugger.endPROFILING(8);
      }
   }

   public static final void lightAbsorbingO(World world, BlockPos posup) {
      int xin = posup.getX();
      int yin = posup.getY();
      int zin = posup.getZ();
      LoadedRGBChunk chunkMid = StaticRGBLight.getActualLoadedRGBChunk(posup.getX(), posup.getZ());
      LoadedRGBChunk chunkPlusX = StaticRGBLight.getActualLoadedRGBChunk(posup.getX() + 16, posup.getZ());
      LoadedRGBChunk chunkMinusX = StaticRGBLight.getActualLoadedRGBChunk(posup.getX() - 16, posup.getZ());
      LoadedRGBChunk chunkPlusZ = StaticRGBLight.getActualLoadedRGBChunk(posup.getX(), posup.getZ() + 16);
      LoadedRGBChunk chunkMinusZ = StaticRGBLight.getActualLoadedRGBChunk(posup.getX(), posup.getZ() - 16);
      LoadedRGBChunk chunkPlusXZ = StaticRGBLight.getActualLoadedRGBChunk(posup.getX() + 16, posup.getZ() + 16);
      LoadedRGBChunk chunkMinusXZ = StaticRGBLight.getActualLoadedRGBChunk(posup.getX() - 16, posup.getZ() - 16);
      LoadedRGBChunk chunkPluXMiuZ = StaticRGBLight.getActualLoadedRGBChunk(posup.getX() + 16, posup.getZ() - 16);
      LoadedRGBChunk chunkPluZMiuX = StaticRGBLight.getActualLoadedRGBChunk(posup.getX() - 16, posup.getZ() + 16);
      List<LoadedRGBChunk> customList = new ArrayList<>();
      customList.add(chunkMid);
      customList.add(chunkPlusX);
      customList.add(chunkMinusX);
      customList.add(chunkPlusZ);
      customList.add(chunkMinusZ);
      customList.add(chunkPlusXZ);
      customList.add(chunkMinusXZ);
      customList.add(chunkPluXMiuZ);
      customList.add(chunkPluZMiuX);
      List<LightOnPos> lights = new ArrayList<>();

      for (int l = 0; l < 15; l++) {
         for (int xc = -14; xc < 15; xc++) {
            int X = xin + xc;
            int bccfX = blockToInchunkCoords(X);
            int xshift = 0;
            if (xc > 0 && bccfX < blockToInchunkCoords(xin)) {
               xshift = 1;
            } else if (xc < 0 && bccfX > blockToInchunkCoords(xin)) {
               xshift = -1;
            }

            for (int zc = -14; zc < 15; zc++) {
               int Z = zin + zc;
               int bccfZ = blockToInchunkCoords(Z);
               int zshift = 0;
               if (zc > 0 && bccfZ < blockToInchunkCoords(zin)) {
                  zshift = 1;
               } else if (zc < 0 && bccfZ > blockToInchunkCoords(zin)) {
                  zshift = -1;
               }

               LoadedRGBChunk chunk = xshift == 0
                  ? (zshift == 0 ? chunkMid : (zshift == 1 ? chunkPlusZ : chunkMinusZ))
                  : (
                     xshift == 1
                        ? (zshift == 0 ? chunkPlusX : (zshift == 1 ? chunkPlusXZ : chunkPluXMiuZ))
                        : (zshift == 0 ? chunkMinusX : (zshift == 1 ? chunkPluZMiuX : chunkMinusXZ))
                  );
               if (chunk != null) {
                  for (int yc = -14; yc < 15; yc++) {
                     if (Math.abs(xc) + Math.abs(zc) + Math.abs(yc) < 16) {
                        int Y = yin + yc;
                        int bccfY = MathHelper.clamp(Y, 0, 255);
                        if (l == 0) {
                           Block block = world.getBlockState(new BlockPos(X, Y, Z)).getBlock();
                           int lightt = StaticRGBLight.mapColors.getOrDefault(block, 0);
                           int bkcoordRed = bccfX | bccfZ << 4 | bccfY << 8;
                           int bkcoordGreen = bkcoordRed | 65536;
                           int bkcoordBlue = bkcoordRed | 131072;
                           chunk.arrXYZ[bkcoordRed] = 0L;
                           chunk.arrXYZ[bkcoordGreen] = 0L;
                           chunk.arrXYZ[bkcoordBlue] = 0L;
                           if (lightt != 0) {
                              chunk.setLight(bkcoordRed, (byte)(lightt & 15), (byte)((lightt & 61440) >>> 12));
                              chunk.setLight(bkcoordGreen, (byte)((lightt & 240) >>> 4), (byte)((lightt & 983040) >>> 16));
                              chunk.setLight(bkcoordBlue, (byte)((lightt & 3840) >>> 8), (byte)((lightt & 15728640) >>> 20));
                           }
                        } else {
                           int opac = world.getChunk(X >> 4, Z >> 4).getBlockLightOpacity(new BlockPos(X, Y, Z));
                           if (opac < 250) {
                              int coordRED = bccfX | bccfZ << 4 | bccfY << 8;
                              int coordGREEN = coordRED | 65536;
                              int coordBLUE = coordRED | 131072;

                              for (byte i = 0; i < 64; i = (byte)(i + 4)) {
                                 long shifted = 15L << i;
                                 byte redOnCurrentPos = (byte)((chunk.arrXYZ[coordRED] & shifted) >>> i);
                                 byte combinedRed = 0;
                                 byte greenOnCurrentPos = (byte)((chunk.arrXYZ[coordGREEN] & shifted) >>> i);
                                 byte combinedGreen = 0;
                                 byte blueOnCurrentPos = (byte)((chunk.arrXYZ[coordBLUE] & shifted) >>> i);
                                 byte combinedBlue = 0;
                                 int offsX = X + 1;
                                 int bccofsX = blockToInchunkCoords(offsX);
                                 LoadedRGBChunk chunk2 = Math.abs(bccofsX - bccfX) > 1 ? StaticRGBLight.getActualLoadedRGBChunk(customList, offsX, Z) : chunk;
                                 if (chunk2 != null) {
                                    int oFcoordRED = bccofsX | bccfZ << 4 | bccfY << 8;
                                    int oFcoordGREEN = oFcoordRED | 65536;
                                    int oFcoordBLUE = oFcoordRED | 131072;
                                    byte redOnOffsetPos = (byte)((chunk2.arrXYZ[oFcoordRED] & shifted) >>> i);
                                    byte greenOnOffsetPos = (byte)((chunk2.arrXYZ[oFcoordGREEN] & shifted) >>> i);
                                    byte blueOnOffsetPos = (byte)((chunk2.arrXYZ[oFcoordBLUE] & shifted) >>> i);
                                    if (combinedRed < redOnOffsetPos) {
                                       combinedRed = redOnOffsetPos;
                                    }

                                    if (combinedGreen < greenOnOffsetPos) {
                                       combinedGreen = greenOnOffsetPos;
                                    }

                                    if (combinedBlue < blueOnOffsetPos) {
                                       combinedBlue = blueOnOffsetPos;
                                    }
                                 }

                                 offsX = X - 1;
                                 bccofsX = blockToInchunkCoords(offsX);
                                 chunk2 = Math.abs(bccofsX - bccfX) > 1 ? StaticRGBLight.getActualLoadedRGBChunk(customList, offsX, Z) : chunk;
                                 if (chunk2 != null) {
                                    int oFcoordREDx = bccofsX | bccfZ << 4 | bccfY << 8;
                                    int oFcoordGREENx = oFcoordREDx | 65536;
                                    int oFcoordBLUEx = oFcoordREDx | 131072;
                                    byte redOnOffsetPosx = (byte)((chunk2.arrXYZ[oFcoordREDx] & shifted) >>> i);
                                    byte greenOnOffsetPosx = (byte)((chunk2.arrXYZ[oFcoordGREENx] & shifted) >>> i);
                                    byte blueOnOffsetPosx = (byte)((chunk2.arrXYZ[oFcoordBLUEx] & shifted) >>> i);
                                    if (combinedRed < redOnOffsetPosx) {
                                       combinedRed = redOnOffsetPosx;
                                    }

                                    if (combinedGreen < greenOnOffsetPosx) {
                                       combinedGreen = greenOnOffsetPosx;
                                    }

                                    if (combinedBlue < blueOnOffsetPosx) {
                                       combinedBlue = blueOnOffsetPosx;
                                    }
                                 }

                                 offsX = Y + 1;
                                 bccofsX = MathHelper.clamp(offsX, 0, 255);
                                 int oFcoordREDxx = bccfX | bccfZ << 4 | bccofsX << 8;
                                 int oFcoordGREENxx = oFcoordREDxx | 65536;
                                 int oFcoordBLUExx = oFcoordREDxx | 131072;
                                 byte redOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordREDxx] & shifted) >>> i);
                                 byte greenOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordGREENxx] & shifted) >>> i);
                                 byte blueOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordBLUExx] & shifted) >>> i);
                                 if (combinedRed < redOnOffsetPosxx) {
                                    combinedRed = redOnOffsetPosxx;
                                 }

                                 if (combinedGreen < greenOnOffsetPosxx) {
                                    combinedGreen = greenOnOffsetPosxx;
                                 }

                                 if (combinedBlue < blueOnOffsetPosxx) {
                                    combinedBlue = blueOnOffsetPosxx;
                                 }

                                 offsX = Y - 1;
                                 bccofsX = MathHelper.clamp(offsX, 0, 255);
                                 int oFcoordREDxxx = bccfX | bccfZ << 4 | bccofsX << 8;
                                 oFcoordGREENxx = oFcoordREDxxx | 65536;
                                 oFcoordBLUExx = oFcoordREDxxx | 131072;
                                 redOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordREDxxx] & shifted) >>> i);
                                 greenOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordGREENxx] & shifted) >>> i);
                                 blueOnOffsetPosxx = (byte)((chunk.arrXYZ[oFcoordBLUExx] & shifted) >>> i);
                                 if (combinedRed < redOnOffsetPosxx) {
                                    combinedRed = redOnOffsetPosxx;
                                 }

                                 if (combinedGreen < greenOnOffsetPosxx) {
                                    combinedGreen = greenOnOffsetPosxx;
                                 }

                                 if (combinedBlue < blueOnOffsetPosxx) {
                                    combinedBlue = blueOnOffsetPosxx;
                                 }

                                 offsX = Z + 1;
                                 bccofsX = blockToInchunkCoords(offsX);
                                 chunk2 = Math.abs(bccofsX - bccfZ) > 1 ? StaticRGBLight.getActualLoadedRGBChunk(customList, X, offsX) : chunk;
                                 if (chunk2 != null) {
                                    oFcoordGREENxx = bccfX | bccofsX << 4 | bccfY << 8;
                                    oFcoordBLUExx = oFcoordGREENxx | 65536;
                                    redOnOffsetPosxx = (byte)(oFcoordGREENxx | 131072);
                                    greenOnOffsetPosxx = (byte)((chunk2.arrXYZ[oFcoordGREENxx] & shifted) >>> i);
                                    blueOnOffsetPosxx = (byte)((chunk2.arrXYZ[oFcoordBLUExx] & shifted) >>> i);
                                    byte blueOnOffsetPosxxx = (byte)((chunk2.arrXYZ[redOnOffsetPosxx] & shifted) >>> i);
                                    if (combinedRed < greenOnOffsetPosxx) {
                                       combinedRed = greenOnOffsetPosxx;
                                    }

                                    if (combinedGreen < blueOnOffsetPosxx) {
                                       combinedGreen = blueOnOffsetPosxx;
                                    }

                                    if (combinedBlue < blueOnOffsetPosxxx) {
                                       combinedBlue = blueOnOffsetPosxxx;
                                    }
                                 }

                                 offsX = Z - 1;
                                 bccofsX = blockToInchunkCoords(offsX);
                                 chunk2 = Math.abs(bccofsX - bccfZ) > 1 ? StaticRGBLight.getActualLoadedRGBChunk(customList, X, offsX) : chunk;
                                 if (chunk2 != null) {
                                    oFcoordGREENxx = bccfX | bccofsX << 4 | bccfY << 8;
                                    oFcoordBLUExx = oFcoordGREENxx | 65536;
                                    redOnOffsetPosxx = (byte)(oFcoordGREENxx | 131072);
                                    greenOnOffsetPosxx = (byte)((chunk2.arrXYZ[oFcoordGREENxx] & shifted) >>> i);
                                    blueOnOffsetPosxx = (byte)((chunk2.arrXYZ[oFcoordBLUExx] & shifted) >>> i);
                                    byte blueOnOffsetPosxxxx = (byte)((chunk2.arrXYZ[redOnOffsetPosxx] & shifted) >>> i);
                                    if (combinedRed < greenOnOffsetPosxx) {
                                       combinedRed = greenOnOffsetPosxx;
                                    }

                                    if (combinedGreen < blueOnOffsetPosxx) {
                                       combinedGreen = blueOnOffsetPosxx;
                                    }

                                    if (combinedBlue < blueOnOffsetPosxxxx) {
                                       combinedBlue = blueOnOffsetPosxxxx;
                                    }
                                 }

                                 combinedRed--;
                                 if (combinedRed > 0 && combinedRed > redOnCurrentPos) {
                                    lights.add(new LightOnPos(coordRED, i, combinedRed, chunk));
                                 }

                                 combinedGreen--;
                                 if (combinedGreen > 0 && combinedGreen > greenOnCurrentPos) {
                                    lights.add(new LightOnPos(coordGREEN, i, combinedGreen, chunk));
                                 }

                                 combinedBlue--;
                                 if (combinedBlue > 0 && combinedBlue > blueOnCurrentPos) {
                                    lights.add(new LightOnPos(coordBLUE, i, combinedBlue, chunk));
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         for (LightOnPos lightOn : lights) {
            lightOn.chunk.arrXYZ[lightOn.bakedCoord] = lightOn.chunk.arrXYZ[lightOn.bakedCoord] & ~(15L << (int)lightOn.channel)
               | Math.max(lightOn.value, 0L) << (int)lightOn.channel;
         }

         lights.clear();
      }
   }

   public static final int blockToInchunkCoords(int xz) {
      if (xz > 0) {
         return xz % 16;
      } else if (xz == 0) {
         return 0;
      } else {
         int i = 16 - -xz % 16;
         if (i == 16) {
            i = 0;
         }

         return i;
      }
   }

   public static void inclear(World world, BlockPos pos) {
      Chunk ch = world.getChunk(pos);
      LoadedRGBChunk loadedrgb = StaticRGBLight.getActualLoadedRGBChunk(ch);
      loadedrgb.clear();
   }
}
