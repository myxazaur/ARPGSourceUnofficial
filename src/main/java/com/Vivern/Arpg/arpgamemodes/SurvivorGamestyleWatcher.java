package com.Vivern.Arpg.arpgamemodes;

import baubles.api.BaublesApi;
import com.Vivern.Arpg.container.GUISurvivorEnchant;
import com.Vivern.Arpg.container.GuiHandler;
import com.Vivern.Arpg.dimensions.toxicomania.ARPGTeleporter;
import com.Vivern.Arpg.elements.Buckshot;
import com.Vivern.Arpg.elements.GemStaff;
import com.Vivern.Arpg.elements.IEnergyItem;
import com.Vivern.Arpg.elements.ItemLootCase;
import com.Vivern.Arpg.entity.SurvivorLootSpawner;
import com.Vivern.Arpg.loot.Treasure;
import com.Vivern.Arpg.main.Coins;
import com.Vivern.Arpg.main.DimensionsRegister;
import com.Vivern.Arpg.main.EnchantmentInit;
import com.Vivern.Arpg.main.GetMOP;
import com.Vivern.Arpg.main.ItemsRegister;
import com.Vivern.Arpg.main.Keys;
import com.Vivern.Arpg.main.Mana;
import com.Vivern.Arpg.main.MobReactor;
import com.Vivern.Arpg.main.MobSpawn;
import com.Vivern.Arpg.main.Sounds;
import com.Vivern.Arpg.main.Weapons;
import com.Vivern.Arpg.mobs.AbstractMob;
import com.Vivern.Arpg.mobs.MobSpawnEnder;
import com.Vivern.Arpg.mobs.MobSpawnEverfrost;
import com.Vivern.Arpg.mobs.MobSpawnNether;
import com.Vivern.Arpg.mobs.MobSpawnOverworld;
import com.Vivern.Arpg.mobs.NPCMobsPack;
import com.Vivern.Arpg.mobs.OtherMobsPack;
import com.Vivern.Arpg.network.PacketDoSomethingToClients;
import com.Vivern.Arpg.network.PacketHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@EventBusSubscriber(
   modid = "arpg"
)
public class SurvivorGamestyleWatcher {
   public static Random rand = new Random();
   public static SurvivorGamestyleWatcher currentWatcher;
   public int STAGE = 0;
   public int LEVEL = 0;
   public int POINTS = 0;
   public int TREASURE = 0;
   public int LOOTSPAWNERS = 0;
   public boolean hasAmmoTrader = false;
   public int ticksExisted;
   public int STARTCHESTS = 3;
   public int totalEnchants = 0;
   public boolean portalPlaced = true;
   public int enchSeed;
   RSLC[] weapons = new RSLC[]{
      rslc(0, 0, 1.0, 8, ItemsRegister.CHAINMACEIRON),
      rslc(0, 0, 1.4, 4, ItemsRegister.CHAINMACEDIAMOND),
      rslc(0, 0, 1.0, 5, ItemsRegister.PUMPSHOTGUN),
      rslc(0, 0, 1.0, 5, ItemsRegister.SLIMESHOTGUN),
      rslc(0, 0, 0.9, 10, ItemsRegister.WHIP),
      rslc(0, 0, 1.6, 5, ItemsRegister.GRENADELAUNCHER),
      rslc(0, 0, 0.8, 10, ItemsRegister.GEMSTAFF),
      rslc(0, 0, 0.4, 10, Items.IRON_SWORD),
      rslc(0, 0, 0.6, 8, Items.DIAMOND_SWORD),
      rslc(0, 0, 0.4, 10, Items.BOW),
      rslc(0, 0, 1.5, 5, ItemsRegister.DASHBELTBLACK),
      rslc(0, 0, 1.0, 7, ItemsRegister.LASERPISTOL),
      rslc(0, 0, 1.3, 6, ItemsRegister.LASERRIFLE),
      rslc(0, 0, 1.6, 5, ItemsRegister.LASERSNIPER),
      rslc(0, 0, 1.0, 6, ItemsRegister.MAGICBOOMERANG),
      rslc(1, 0, 0.6, 15, ItemsRegister.INFERNALBLADE),
      rslc(1, 0, 1.0, 10, ItemsRegister.CHAINMACEMOLTEN),
      rslc(1, 0, 0.9, 8, ItemsRegister.HELLMARK),
      rslc(1, 0, 0.7, 9, ItemsRegister.ROTTENSHIELD),
      rslc(1, 0, 1.1, 10, ItemsRegister.MOLTENGREATAXE),
      rslc(1, 0, 0.8, 10, ItemsRegister.MAULER),
      rslc(1, 0, 0.7, 14, ItemsRegister.LAVADROPPER),
      rslc(1, 0, 1.1, 10, ItemsRegister.STAFFOFWITHERDRY),
      rslc(1, 0, 1.1, 10, ItemsRegister.VIOLENCE),
      rslc(1, 0, 1.0, 10, ItemsRegister.NETHERGRINDER),
      rslc(1, 0, 1.0, 10, ItemsRegister.CINDERBOW)
   };
   RSLC[] usables = new RSLC[]{
      rslc(0, 0, 10, ItemsRegister.GRENADECLASSIC, 8, 16),
      rslc(0, 0, 8, ItemsRegister.GRENADEBOMB, 5, 12),
      rslc(0, 0, 8, ItemsRegister.GRENADEMOLOTOV, 8, 18),
      rslc(0, 0, 8, ItemsRegister.GRENADEOIL, 8, 20),
      rslc(0, 0, 6, Items.GOLDEN_APPLE, 1, 2),
      rslc(0, 0, 3, Items.ENDER_PEARL, 1),
      rslc(0, 0, 6, ItemsRegister.MAGICJAM, 1, 3),
      rslc(1, 0, 12, ItemsRegister.GRENADEHELL, 4, 14),
      rslc(1, 0, 10, ItemsRegister.GRENADECLASSIC, 8, 16),
      rslc(1, 0, 8, ItemsRegister.GRENADEBOMB, 5, 12),
      rslc(1, 0, 8, ItemsRegister.GRENADEMOLOTOV, 8, 18),
      rslc(1, 0, 8, ItemsRegister.GRENADEOIL, 8, 20),
      rslc(1, 1, 8, ItemsRegister.PUDDINGORANGE, 2, 5)
   };
   RSLC[] armor = new RSLC[]{
      rslc(0, 0, 1.0, 10, ItemsRegister.WIZARDHELM),
      rslc(0, 0, 1.0, 10, ItemsRegister.WIZARDCHEST),
      rslc(0, 0, 1.0, 10, ItemsRegister.WIZARDLEGS),
      rslc(0, 0, 1.0, 10, ItemsRegister.WIZARDBOOTS),
      rslc(0, 0, 1.0, 10, ItemsRegister.SLIMEHELM),
      rslc(0, 0, 1.0, 10, ItemsRegister.SLIMECHEST),
      rslc(0, 0, 1.0, 10, ItemsRegister.SLIMELEGS),
      rslc(0, 0, 1.0, 10, ItemsRegister.SLIMEBOOTS),
      rslc(0, 0, 1.0, 10, Items.DIAMOND_HELMET),
      rslc(0, 0, 1.0, 10, Items.DIAMOND_CHESTPLATE),
      rslc(0, 0, 1.0, 10, Items.DIAMOND_LEGGINGS),
      rslc(0, 0, 1.0, 10, Items.DIAMOND_BOOTS),
      rslc(0, 0, 0.7, 13, Items.IRON_HELMET),
      rslc(0, 0, 0.7, 13, Items.IRON_CHESTPLATE),
      rslc(0, 0, 0.7, 13, Items.IRON_LEGGINGS),
      rslc(0, 0, 0.7, 13, Items.IRON_BOOTS),
      rslc(0, 0, 0.5, 15, Items.CHAINMAIL_HELMET),
      rslc(0, 0, 0.5, 15, Items.CHAINMAIL_CHESTPLATE),
      rslc(0, 0, 0.5, 15, Items.CHAINMAIL_LEGGINGS),
      rslc(0, 0, 0.5, 15, Items.CHAINMAIL_BOOTS),
      rslc(0, 0, 0.3, 15, Items.LEATHER_HELMET),
      rslc(0, 0, 0.3, 15, Items.LEATHER_CHESTPLATE),
      rslc(0, 0, 0.3, 15, Items.LEATHER_LEGGINGS),
      rslc(0, 0, 0.3, 15, Items.LEATHER_BOOTS),
      rslc(0, 0, 0.2, 5, Items.GOLDEN_HELMET),
      rslc(0, 0, 0.2, 5, Items.GOLDEN_CHESTPLATE),
      rslc(0, 0, 0.2, 5, Items.GOLDEN_LEGGINGS),
      rslc(0, 0, 0.2, 5, Items.GOLDEN_BOOTS),
      rslc(1, 2, 1.3, 10, ItemsRegister.FIRELORDHELM),
      rslc(1, 2, 1.3, 10, ItemsRegister.FIRELORDCHEST),
      rslc(1, 2, 1.3, 10, ItemsRegister.FIRELORDLEGS),
      rslc(1, 2, 1.3, 10, ItemsRegister.FIRELORDBOOTS),
      rslc(1, 2, 1.3, 10, ItemsRegister.FIREMAGEHELM),
      rslc(1, 2, 1.3, 10, ItemsRegister.FIREMAGECHEST),
      rslc(1, 2, 1.3, 10, ItemsRegister.FIREMAGELEGS),
      rslc(1, 2, 1.3, 10, ItemsRegister.FIREMAGEBOOTS)
   };

   public SurvivorGamestyleWatcher() {
      MobSpawn.spawnByDimension.clear();
      MobSpawn.spawnByDimension.put(0, new MobSpawnOverworld(true));
      MobSpawn.spawnByDimension.put(-1, new MobSpawnNether());
      MobSpawn.spawnByDimension.put(1, new MobSpawnEnder());
      MobSpawn.spawnByDimension.put(100, new MobSpawnEverfrost());

      for (MobSpawn mobSpawn : MobSpawn.spawnByDimension.values()) {
         mobSpawn.enableSwarms = false;
      }
   }

   public static void startSurvivor(MinecraftServer server) {
      currentWatcher = new SurvivorGamestyleWatcher();

      for (EntityPlayer player : server.getPlayerList().getPlayers()) {
         player.addItemStackToInventory(new ItemStack(Items.STONE_SWORD));
         player.addItemStackToInventory(new ItemStack(Items.STONE_PICKAXE));
         player.addItemStackToInventory(new ItemStack(Items.STONE_AXE));
         player.addItemStackToInventory(new ItemStack(Items.STONE_SHOVEL));
         player.addItemStackToInventory(new ItemStack(Blocks.TORCH, 64));
         BaublesApi.getBaublesHandler(player).setStackInSlot(6, new ItemStack(ItemsRegister.CHARMOFUNDYING));
         BaublesApi.getBaublesHandler(player).setStackInSlot(4, new ItemStack(ItemsRegister.CONTACTLENSES));
         player.setHealth(player.getMaxHealth());
         Weapons.setPotionIfEntityLB(player, MobEffects.REGENERATION, 1200, 1);
         Mana.addSwarmPoints(player, 250);
      }

      currentWatcher.enchSeed = rand.nextInt();
      currentWatcher.ticksExisted = 0;
      if (Minecraft.getMinecraft().player != null) {
         Minecraft.getMinecraft().player.sendChatMessage("You are now the Survivor!");
      }
   }

   @SubscribeEvent
   public static void TickWorld(WorldTickEvent event) {
      if (currentWatcher != null) {
         currentWatcher.onUpdate(event.world);
      }
   }

   public static void onLivingDeath(LivingDeathEvent event) {
      if (currentWatcher != null) {
         Entity damager = event.getSource().getTrueSource();
         if (damager != null) {
            int dim = damager.world.provider.getDimension();
            MobSpawn mobSpawn = MobSpawn.spawnByDimension.get(dim);
            if (mobSpawn != null) {
               currentWatcher.handleKillMob(event.getEntityLiving(), mobSpawn, damager);
            }
         }
      }
   }

   public void onUpdate(World world) {
      for (EntityPlayer player : world.playerEntities) {
         this.onUpdatePlayer(player);
      }

      if (!this.portalPlaced && world.getWorldTime() % 23L == 0L) {
         for (EntityPlayer player : world.playerEntities) {
            BlockPos randpos = new BlockPos(
               (rand.nextDouble() - 0.5) * 40.0 + player.posX, 255.0, (rand.nextDouble() - 0.5) * 40.0 + player.posZ
            );
            if (this.STAGE == 1) {
               if (setNetherPortal(world, randpos)) {
                  this.portalPlaced = true;
               }
            } else {
               ARPGTeleporter teleport = DimensionsRegister.getTeleporterToDimension(this.stageDimension(this.STAGE));
               if (teleport != null && teleport.tryPlacePortal(world, randpos, rand.nextFloat() < 0.5F, world.provider.getDimension()) != null) {
                  this.portalPlaced = true;
               }
            }
         }
      }

      this.ticksExisted++;
   }

   public void onUpdatePlayer(EntityPlayer player) {
      if (player.dimension != this.stageDimension(this.STAGE)) {
         if (player.ticksExisted % 100 == 0) {
            Mana.setSwarmPoints(player, 0);
         }
      } else if (player.ticksExisted % 2000 == 0) {
         Mana.addSwarmPoints(player, 250 + this.LEVEL * 50);
      }

      if (rand.nextFloat() < 0.0025F && player.dimension == this.stageDimension(this.STAGE) && (this.LOOTSPAWNERS > 0 || this.STARTCHESTS > 0)) {
         BlockPos spawnpos = this.getLootSpawnerSpawnPos(player, 128);
         if (spawnpos != null) {
            SurvivorLootSpawner lootSpawner = new SurvivorLootSpawner(
               player.world, spawnpos.getX() + 0.5, spawnpos.getY(), spawnpos.getZ() + 0.5
            );
            if (rand.nextFloat() < 0.55F && this.STARTCHESTS <= 0) {
               lootSpawner.trades = this.getRandomLootTrades();
            } else {
               lootSpawner.loot = this.getRandomLootChest();
            }

            this.STARTCHESTS--;
            player.world.spawnEntity(lootSpawner);
         }
      }

      if (player.ticksExisted % 40 == 0) {
         int slot = player.inventory.findSlotMatchingUnusedItem(new ItemStack(Items.COOKED_BEEF));
         if (slot >= 0) {
            ItemStack stack = player.inventory.getStackInSlot(slot);
            stack.setCount(64);
         } else {
            player.inventory.addItemStackToInventory(new ItemStack(Items.COOKED_BEEF, 64));
         }
      }

      if (Keys.isKeyPressed(player, Keys.USE) && player instanceof EntityPlayerMP && !player.getCooldownTracker().hasCooldown(Items.ENCHANTED_BOOK)) {
         PacketDoSomethingToClients packet = new PacketDoSomethingToClients();
         int fullseed = this.enchSeed + this.totalEnchants;
         packet.writeargs(fullseed, 0.0, 0.0, 0.0, 0.0, 0.0, 9);
         PacketHandler.sendTo(packet, (EntityPlayerMP)player);
         player.getCooldownTracker().setCooldown(Items.ENCHANTED_BOOK, 20);
      }
   }

   public static int howMuchLevelsNeedToEnchant(ItemStack stack, Enchantment enchantment) {
      int haslvl = EnchantmentHelper.getEnchantmentLevel(enchantment, stack);
      if (haslvl >= enchantment.getMaxLevel()) {
         return -1;
      } else if (haslvl == 0) {
         if (enchantment == EnchantmentInit.SPECIAL) {
            return 3;
         } else {
            return enchantment != EnchantmentInit.REUSE && enchantment != EnchantmentInit.RAPIDITY ? 1 : 2;
         }
      } else if (enchantment == EnchantmentInit.REUSE || enchantment == EnchantmentInit.RAPIDITY) {
         return 2 + haslvl;
      } else {
         return enchantment != EnchantmentInit.RELOADING && enchantment != EnchantmentInit.ACCURACY ? Math.min(haslvl, 5) : Math.min(1 + haslvl, 5);
      }
   }

   public static void onClient(double x, double y, double z, double a, double b, double c) {
      if (Minecraft.getMinecraft().player != null) {
         GuiHandler.displayGui(Minecraft.getMinecraft().player, new GUISurvivorEnchant(Minecraft.getMinecraft().player, getEnchantments((int)x)));
      }
   }

   public void onServerPacket(EntityLivingBase player, double x, double y, double z, double a, double b, double c) {
      if (player instanceof EntityPlayer) {
         if (y == 1.0) {
            int levelneed = 2;
            if (((EntityPlayer)player).experienceLevel >= levelneed && !((EntityPlayer)player).getCooldownTracker().hasCooldown(Items.GOLD_INGOT)) {
               BlockPos spawnpos = this.getLootSpawnerSpawnPos(player, 16);
               if (spawnpos != null) {
                  SurvivorLootSpawner lootSpawner = new SurvivorLootSpawner(
                     player.world, spawnpos.getX() + 0.5, spawnpos.getY(), spawnpos.getZ() + 0.5
                  );
                  ArrayList<NPCMobsPack.Trade> list = new ArrayList<>();
                  this.addSupplyTradesToList(list);
                  this.addAmmoTradesToList(list);
                  ArrayList<ItemStack> list2 = new ArrayList<>();
                  list2.add(new ItemStack(Items.GOLDEN_APPLE, 2));
                  list2.add(new ItemStack(Blocks.DIRT, 32));
                  lootSpawner.trades = list;
                  lootSpawner.loot = list2;
                  player.world.spawnEntity(lootSpawner);
                  ((EntityPlayer)player).addExperienceLevel(-levelneed);
                  player.world
                     .playSound(
                        (EntityPlayer)null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        Sounds.item_misc_a,
                        SoundCategory.AMBIENT,
                        0.8F,
                        0.9F + rand.nextFloat() / 5.0F
                     );
                  ((EntityPlayer)player).getCooldownTracker().setCooldown(Items.GOLD_INGOT, 500);
               }
            }
         } else {
            int fullseed = this.enchSeed + this.totalEnchants;
            Enchantment[] enchantments = getEnchantments(fullseed);
            Enchantment enchantmentTo = enchantments[MathHelper.clamp((int)x, 0, enchantments.length - 1)];
            int levelneed = howMuchLevelsNeedToEnchant(player.getHeldItemMainhand(), enchantmentTo);
            if (levelneed >= 0 && ((EntityPlayer)player).experienceLevel >= levelneed) {
               this.enchantPlayerHand((EntityPlayer)player, enchantmentTo);
               ((EntityPlayer)player).addExperienceLevel(-levelneed);
               player.world
                  .playSound(
                     (EntityPlayer)null,
                     player.posX,
                     player.posY,
                     player.posZ,
                     SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                     SoundCategory.AMBIENT,
                     0.8F,
                     0.9F + rand.nextFloat() / 5.0F
                  );
            }
         }
      }
   }

   public void handleSpawnedMob(EntityLiving mob, MobSpawn mobSpawn) {
      if (!(mob instanceof EntitySkeleton) && !(mob instanceof OtherMobsPack.HorribleEvoker)) {
         mob.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0);
      } else {
         mob.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(38.0);
      }

      if (mob.ticksExisted < 1200) {
         mob.setHealth(mob.getMaxHealth() / 4.0F);
      }

      if (mob.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
         mob.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
         mob.setDropChance(EntityEquipmentSlot.HEAD, 0.0F);
      }

      if (mob instanceof EntityEvoker) {
         mob.setDropItemsWhenDead(false);
      }

      if (mob instanceof EntitySlime) {
         NBTTagCompound compound = new NBTTagCompound();
         ((EntitySlime)mob).writeEntityToNBT(compound);
         compound.setInteger("Size", 5 + rand.nextInt(3));
         ((EntitySlime)mob).readEntityFromNBT(compound);
      }
   }

   public void handleKillMob(EntityLivingBase mob, MobSpawn mobSpawn, Entity damager) {
      for (MobReactor reactor : mobSpawn.reactors) {
         if (mob.getClass() == reactor.entry.getEntityClass()) {
            this.addPoints(reactor.pointsCost, mob.world);
            break;
         }
      }

      if (!(mob instanceof AbstractMob)) {
         int i = GetMOP.floatToIntWithChance(mob.getMaxHealth() / 7.0F, rand);
         if (i > 1) {
            int coinsfall = rand.nextInt(i);
            Coins.dropMoneyToWorld(mob.world, coinsfall, 4, mob.posX, mob.posY + mob.height * 0.25, mob.posZ);
         }
      }
   }

   public void addPoints(int points, World world) {
      if (world.provider.getDimension() == this.stageDimension(this.STAGE)) {
         this.POINTS += points;
         if (this.POINTS >= 350 + this.LEVEL * 50) {
            this.LEVEL++;
            this.POINTS = 0;
            Minecraft.getMinecraft().player.sendChatMessage("LEVEL " + this.LEVEL);
         }

         if (this.LEVEL > 4) {
            this.newStage(world);
         }

         this.TREASURE += points;
         if (this.TREASURE >= 60) {
            this.LOOTSPAWNERS++;
            this.TREASURE = 0;
         }
      }
   }

   public void newStage(World world) {
      this.LEVEL = 0;
      this.STAGE++;
      this.TREASURE = 0;
      this.LOOTSPAWNERS = 0;
      this.hasAmmoTrader = false;
      this.portalPlaced = false;
   }

   public double getShopMoney(boolean armor) {
      return armor ? 6.0 : 10.0;
   }

   public int stageDimension(int stage) {
      if (stage == 0) {
         return 0;
      } else if (stage == 1) {
         return -1;
      } else {
         return stage == 2 ? 100 : 0;
      }
   }

   public ArrayList<NPCMobsPack.Trade> getRandomLootTrades() {
      ArrayList<NPCMobsPack.Trade> list = new ArrayList<>();
      if (rand.nextFloat() < (this.hasAmmoTrader ? 0.92F : 0.5F)) {
         if (rand.nextFloat() < 0.73) {
            int id = this.byWeight(rand, this.weapons);
            RSLC rslc = this.weapons[id];
            Item item = rslc.item;
            ItemStack itemStack = ItemStack.EMPTY;
            if (item == ItemsRegister.GEMSTAFF) {
               itemStack = GemStaff.getStackWithGem(rand.nextInt(8));
            } else {
               itemStack = new ItemStack(item, rslc.countmin + rand.nextInt(rslc.countmax - rslc.countmin + 1));
            }

            NPCMobsPack.Trade trade = new NPCMobsPack.Trade(
               ItemStack.EMPTY, itemStack, (int)Math.round(this.getShopMoney(false) * rslc.price * (0.85F + rand.nextFloat() * 0.3F)), 0, 0.0F
            );
            list.add(trade);
         } else {
            for (int i = 0; i < rand.nextInt(3) + 1; i++) {
               RSLC rslc = this.armor[this.byWeight(rand, this.armor)];
               ItemStack itemStack = new ItemStack(rslc.item, rslc.countmin + rand.nextInt(rslc.countmax - rslc.countmin + 1));
               list.add(
                  new NPCMobsPack.Trade(
                     ItemStack.EMPTY, itemStack, (int)Math.round(this.getShopMoney(true) * rslc.price * (0.85F + rand.nextFloat() * 0.3F)), 0, 0.0F
                  )
               );
            }
         }
      } else {
         this.addAmmoTradesToList(list);
         this.hasAmmoTrader = true;
      }

      return list;
   }

   public ArrayList<ItemStack> getRandomLootChest() {
      ArrayList<ItemStack> list = new ArrayList<>();
      if (rand.nextFloat() < 0.35) {
         int id = this.byWeight(rand, this.weapons);
         RSLC rslc = this.weapons[id];
         Item item = rslc.item;
         if (item == ItemsRegister.GEMSTAFF) {
            list.add(GemStaff.getStackWithGem(rand.nextInt(8)));
         } else {
            list.add(new ItemStack(item, rslc.countmin + rand.nextInt(rslc.countmax - rslc.countmin + 1)));
         }
      } else if (rand.nextFloat() < 0.67) {
         RSLC rslc = this.usables[this.byWeight(rand, this.usables)];
         list.add(new ItemStack(rslc.item, rslc.countmin + rand.nextInt(rslc.countmax - rslc.countmin + 1)));
      } else {
         for (int i = 0; i < rand.nextInt(2) + 1; i++) {
            RSLC rslc = this.armor[this.byWeight(rand, this.armor)];
            list.add(new ItemStack(rslc.item, rslc.countmin + rand.nextInt(rslc.countmax - rslc.countmin + 1)));
         }
      }

      return list;
   }

   public void addAmmoTradesToList(ArrayList<NPCMobsPack.Trade> list) {
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, Buckshot.getBuckshotStack("lead", 8), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, Buckshot.getBuckshotStack("copper", 8), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, Buckshot.getBuckshotStack("silver", 6), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, Buckshot.getBuckshotStack("gold", 5), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(Items.ARROW, 20), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(ItemsRegister.ARROWBOUNCING, 15), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(ItemsRegister.ROCKETCOMMON, 6), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(ItemsRegister.ROCKETDEMOLISHING, 2), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(ItemsRegister.ROCKETMINING, 7), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(ItemsRegister.SLIMECELL, 3), 1, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, IEnergyItem.getFullcharged(ItemsRegister.IONBATTERY, 5), 3, 0, 0.0F));
   }

   public void addSupplyTradesToList(ArrayList<NPCMobsPack.Trade> list) {
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(ItemsRegister.GRENADECLASSIC, 4), 3, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(ItemsRegister.GRENADEBOMB, 2), 3, 0, 0.0F));
      list.add(new NPCMobsPack.Trade(ItemStack.EMPTY, new ItemStack(ItemsRegister.GRENADEOIL, 6), 3, 0, 0.0F));
   }

   public void enchantPlayerHand(EntityPlayer player, Enchantment enchantment) {
      ItemStack itemStack = player.getHeldItemMainhand();
      if (EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack) > 0) {
         NBTTagList nbttaglist = itemStack.getEnchantmentTagList();

         for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            Enchantment enchantmentHas = Enchantment.getEnchantmentByID(nbttagcompound.getShort("id"));
            short j = nbttagcompound.getShort("lvl");
            if (enchantment == enchantmentHas) {
               nbttagcompound.setShort("lvl", (short)(j + 1));
               this.totalEnchants++;
               return;
            }
         }
      } else {
         itemStack.addEnchantment(enchantment, 1);
         this.totalEnchants++;
      }
   }

   public static Enchantment[] getEnchantments(int seed) {
      Enchantment[] base = EnchantmentInit.ENCHANTMENTSLIST.toArray(new Enchantment[0]);
      List<Treasure> list = ItemLootCase.entriesWeaponEnch();
      int[] weights = new int[list.size()];

      for (int i = 0; i < list.size(); i++) {
         Treasure treasure = list.get(i);
         weights[i] = (int)(treasure.chance * 100.0F);
      }

      Random random1 = new Random(seed);
      Enchantment[] ret = new Enchantment[2];

      for (int i = 0; i < ret.length; i++) {
         int i1 = GetMOP.byWeight(random1, weights);
         ItemStack itemStack = new ItemStack(list.get(i1).item);
         itemStack.setTagCompound(list.get(i1).nbtTag);
         Enchantment enchantment = (Enchantment)EnchantmentHelper.getEnchantments(itemStack).keySet().toArray()[0];
         ret[i] = enchantment;
      }

      return ret;
   }

   public static boolean setNetherPortal(World world, BlockPos randpos) {
      GetMOP.BlockTraceResult result = GetMOP.blockTrace(world, randpos, EnumFacing.DOWN, 250, GetMOP.SOLID_NON_PLANTS_BLOCKS);
      if (result == null) {
         return false;
      } else {
         BlockPos pos = result.prevPos;
         IBlockState obsidian = Blocks.OBSIDIAN.getDefaultState();
         IBlockState fire = Blocks.FIRE.getDefaultState();

         for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 4; y++) {
               world.setBlockState(pos.add(0, y, x), x != -2 && x != 2 && y != 0 && y != 4 ? Blocks.AIR.getDefaultState() : obsidian);
            }
         }

         world.setBlockState(pos.add(0, 1, 0), fire);
         System.out.println("PLACED NETHER PORTAL!!!!!!!!!!");
         return true;
      }
   }

   public static RSLC rslc(int stage, int level, int rarity, Item item, int count) {
      return new RSLC(rarity, stage, level, count, count, item, -1.0);
   }

   public static RSLC rslc(int stage, int level, int rarity, Item item) {
      return new RSLC(rarity, stage, level, 1, 1, item, -1.0);
   }

   public static RSLC rslc(int stage, int level, int rarity, Item item, int countmin, int countmax) {
      return new RSLC(rarity, stage, level, countmin, countmax, item, -1.0);
   }

   public static RSLC rslc(int stage, int level, double price, int rarity, Item item, int count) {
      return new RSLC(rarity, stage, level, count, count, item, price);
   }

   public static RSLC rslc(int stage, int level, double price, int rarity, Item item) {
      return new RSLC(rarity, stage, level, 1, 1, item, price);
   }

   public static RSLC rslc(int stage, int level, double price, int rarity, Item item, int countmin, int countmax) {
      return new RSLC(rarity, stage, level, countmin, countmax, item, price);
   }

   public int byWeight(Random rand, RSLC... weights) {
      int summ = 0;

      for (int i = 0; i < weights.length; i++) {
         if (weights[i].stage == this.STAGE && weights[i].level <= this.LEVEL) {
            summ += weights[i].rarity;
         }
      }

      return summ <= 0 ? 0 : this.byWeight(summ, rand, weights);
   }

   public int byWeight(int summ, Random rand, RSLC... weights) {
      int r = rand.nextInt(summ);
      int all = 0;

      for (int i = 0; i < weights.length; i++) {
         if (weights[i].stage == this.STAGE && weights[i].level <= this.LEVEL) {
            int weight = weights[i].rarity;
            all += weight;
            if (r < all) {
               return i;
            }
         }
      }

      return -1;
   }

   public BlockPos getLootSpawnerSpawnPos(Entity player, int diameter) {
      double y = player.posY;
      if (player.dimension == -1) {
         GetMOP.BlockTraceResult result0 = GetMOP.blockTrace(
            player.world, player.getPosition().up(), EnumFacing.UP, 120, GetMOP.SOLID_NON_PLANTS_BLOCKS
         );
         if (result0 != null && Math.abs(player.getPosition().up().getY() - result0.prevPos.getY()) > 8) {
            y = result0.prevPos.getY();
         } else {
            y = 120.0;
         }
      } else {
         y += 60.0;
      }

      BlockPos randpos = new BlockPos(
         (rand.nextDouble() - 0.5) * diameter + player.posX, y, (rand.nextDouble() - 0.5) * diameter + player.posZ
      );
      BlockPos spawnpos = null;
      if (player.world.isAirBlock(randpos)) {
         GetMOP.BlockTraceResult result = GetMOP.blockTrace(player.world, randpos, EnumFacing.DOWN, 120, GetMOP.SOLID_NON_PLANTS_BLOCKS);
         if (result != null) {
            spawnpos = randpos;
         }
      } else {
         GetMOP.BlockTraceResult result0 = GetMOP.blockTrace(player.world, randpos, EnumFacing.DOWN, 60, GetMOP.SOLID_NON_PLANTS_BLOCKS);
         if (result0 != null && player.world.isAirBlock(result0.pos)) {
            spawnpos = result0.pos;
         }
      }

      return spawnpos;
   }

   public static class RSLC {
      int rarity;
      int stage;
      int level;
      int countmin;
      int countmax;
      double price;
      Item item;

      public RSLC(int rarity, int stage, int level, int countmin, int countmax, Item item, double price) {
         this.item = item;
         this.rarity = rarity;
         this.stage = stage;
         this.level = level;
         this.countmin = countmin;
         this.countmax = countmax;
         this.price = price;
      }
   }
}
