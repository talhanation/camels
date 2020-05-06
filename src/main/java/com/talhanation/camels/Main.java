package com.talhanation.camels;

import com.talhanation.camels.client.model.ModelEntityCamel;
import com.talhanation.camels.init.ModEntityTypes;
import com.talhanation.camels.init.SoundInit;
import com.talhanation.camels.items.CamelSpawnEggItem;
import com.talhanation.camels.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.client.event.ColorHandlerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod("camels")
public class Main
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "camels";

    public Main() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        SoundInit.SOUNDS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {
        ModEntityTypes.addEntitySpawns();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @OnlyIn(Dist.CLIENT)
    private void registerItemColors(ColorHandlerEvent.Item event) {
        for(RegistryObject<Item> items : ModItems.SPAWN_EGGS) {
            //RegistryObject#isPresent causes a null pointer when it's false :crying: thanks forge
            if(ObfuscationReflectionHelper.getPrivateValue(RegistryObject.class, items, "value") != null) {
                Item item = items.get();
                if(item instanceof CamelSpawnEggItem) {
                    event.getItemColors().register((itemColor, itemsIn) -> {
                        return ((CamelSpawnEggItem) item).getColor(itemsIn);
                    }, item);
                }
            }
        }
    }
}
