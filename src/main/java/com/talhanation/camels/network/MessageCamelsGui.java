package com.talhanation.camels.network;

import com.talhanation.camels.entities.*;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;

public class MessageCamelsGui implements Message<MessageCamelsGui> {

    private UUID uuid;
    private UUID camel;


    public MessageCamelsGui() {
        this.uuid = new UUID(0, 0);
    }

    public MessageCamelsGui(Player player, UUID camel) {
        this.uuid = player.getUUID();
        this.camel = camel;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUUID().equals(uuid)) {
            return;
        }

        ServerPlayer player = context.getSender();
        player.level.getEntitiesOfClass(AbstractCamelEntity.class, player.getBoundingBox()
                        .inflate(16.0D), v -> v
                        .getUUID()
                        .equals(this.camel))
                .stream()
                .filter(Entity::isAlive)
                .findAny()
                .ifPresent(camel -> camel.openInventory(player));
    }

    @Override
    public MessageCamelsGui fromBytes(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        this.camel = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeUUID(camel);
    }

}