package com.talhanation.camels;

import com.talhanation.camels.client.gui.CamelInventoryScreen;
import com.talhanation.camels.entities.AbstractCamelEntity;
import com.talhanation.camels.init.ModEntityTypes;
import com.talhanation.camels.init.ModItems;
import com.talhanation.camels.inventory.CamelsInventoryContainer;
import com.talhanation.camels.network.MessageCamelsGui;
import de.maxhenkel.corelib.ClientRegistry;
import de.maxhenkel.corelib.CommonRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.util.UUID;

@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "camels";
    public static SimpleChannel SIMPLE_CHANNEL;
    public static MenuType<CamelsInventoryContainer> CAMELS_CONTAINER_TYPE;

    public Main() {
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CamelsModConfig.CONFIG);
        //CamelsModConfig.loadConfig(CamelsModConfig.CONFIG, FMLPaths.CONFIGDIR.get().resolve("camels-common.toml"));

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addGenericListener(MenuType.class, this::registerContainers);
        //ModBlocks.BLOCKS.register(modEventBus);
        //ModSounds.SOUNDS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(Main.this::clientSetup));
    }

    @SuppressWarnings("deprecation")
    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        MinecraftForge.EVENT_BUS.register(this);
        SIMPLE_CHANNEL = CommonRegistry.registerChannel(Main.MOD_ID, "default");
        CommonRegistry.registerMessage(SIMPLE_CHANNEL, 0, MessageCamelsGui.class);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {

        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        ClientRegistry.registerScreen(Main.CAMELS_CONTAINER_TYPE, CamelInventoryScreen::new);
    }


    @SubscribeEvent
    public void registerContainers(RegistryEvent.Register<MenuType<?>> event) {
        CAMELS_CONTAINER_TYPE = new MenuType<>((IContainerFactory<CamelsInventoryContainer>) (windowId, inv, data) -> {
            AbstractCamelEntity rec = getCamelsByUUID(inv.player, data.readUUID());
            if (rec == null) {
                return null;
            }
            return new CamelsInventoryContainer(windowId, rec, inv);
        });
        CAMELS_CONTAINER_TYPE.setRegistryName(new ResourceLocation(Main.MOD_ID, "camels_container"));
        event.getRegistry().register(CAMELS_CONTAINER_TYPE);
    }

    @Nullable
    public static AbstractCamelEntity getCamelsByUUID(Player player, UUID uuid) {
        double distance = 10D;
        return player.level.getEntitiesOfClass(AbstractCamelEntity.class, new AABB(player.getX() - distance, player.getY() - distance, player.getZ() - distance, player.getX() + distance, player.getY() + distance, player.getZ() + distance), entity -> entity.getUUID().equals(uuid)).stream().findAny().orElse(null);
    }
}
