package com.talhanation.camels.init;

import com.talhanation.camels.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundInit {


    public static SoundEvent ENTITY_CAMEL_AMBIENT;
    public static SoundEvent ENTITY_CAMEL_HURT;
    public static SoundEvent ENTITY_CAMEL_DEATH;
    public static SoundEvent ENTITY_CAMEL_ANGRY;

    public static SoundEvent ENTITY_CAMEL_AMBIENT_BABY;
    public static SoundEvent ENTITY_CAMEL_HURT_BABY;
    public static SoundEvent ENTITY_CAMEL_DEATH_BABY;
    public static SoundEvent ENTITY_CAMEL_ANGRY_BABY;

    public static void registerSounds()
    {
        ENTITY_CAMEL_AMBIENT = registerSound("entity.camel.ambient");
        ENTITY_CAMEL_HURT = registerSound("entity.camel.hurt");
        ENTITY_CAMEL_DEATH = registerSound("entity.camel.death");
        ENTITY_CAMEL_ANGRY = registerSound("entity.camel.angry");

        ENTITY_CAMEL_AMBIENT_BABY = registerSound("entity.camel.baby_ambient");
        ENTITY_CAMEL_HURT_BABY = registerSound("entity.camel.baby_hurt");
        ENTITY_CAMEL_DEATH_BABY = registerSound("entity.camel.baby_death");
        ENTITY_CAMEL_ANGRY_BABY = registerSound("entity.camel.baby_angry");
    }

    private static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

}
