package com.talhanation.camels.network;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CMoveVehiclePacket;
import net.minecraft.network.play.server.SMoveVehiclePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModServerPlayNetHandler extends ServerPlayNetHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final MinecraftServer server;
    public ServerPlayerEntity player;
    public final NetworkManager netManager;
    private double lowestRiddenX1;
    private double lowestRiddenY1;
    private double lowestRiddenZ1;
    private double lowestRiddenX;
    private double lowestRiddenY;
    private double lowestRiddenZ;
    private Entity lowestRiddenEnt;
    private boolean vehicleFloating;

    public ModServerPlayNetHandler(MinecraftServer server, NetworkManager networkManagerIn, ServerPlayerEntity playerIn) {
        super(server, networkManagerIn, playerIn);
        this.server = server;
        this.netManager = networkManagerIn;
        networkManagerIn.setNetHandler(this);
        this.player = playerIn;
        playerIn.connection = this;
    }

    private static boolean isMoveVehiclePacketInvalid(CMoveVehiclePacket packetIn) {
        return !Doubles.isFinite(packetIn.getX()) || !Doubles.isFinite(packetIn.getY()) || !Doubles.isFinite(packetIn.getZ()) || !Floats.isFinite(packetIn.getPitch()) || !Floats.isFinite(packetIn.getYaw());
    }

    private boolean func_217264_d() {
        return this.server.isServerOwner(this.player.getGameProfile());
    }


    @Override
    public void processVehicleMove(CMoveVehiclePacket packetIn) {
        PacketThreadUtil.checkThreadAndEnqueue(packetIn, this, this.player.getServerWorld());
        if (isMoveVehiclePacketInvalid(packetIn)) {
            this.disconnect(new TranslationTextComponent("multiplayer.disconnect.invalid_vehicle_movement"));
        } else {
            Entity entity = this.player.getLowestRidingEntity();
            if (entity != this.player && entity.getControllingPassenger() == this.player && entity == this.lowestRiddenEnt) {
                ServerWorld serverworld = this.player.getServerWorld();
                double d0 = entity.getPosX();
                double d1 = entity.getPosY();
                double d2 = entity.getPosZ();
                double d3 = packetIn.getX();
                double d4 = packetIn.getY();
                double d5 = packetIn.getZ();
                float f = packetIn.getYaw();
                float f1 = packetIn.getPitch();
                double d6 = d3 - this.lowestRiddenX;
                double d7 = d4 - this.lowestRiddenY;
                double d8 = d5 - this.lowestRiddenZ;
                double d9 = entity.getMotion().lengthSquared();
                double d10 = d6 * d6 + d7 * d7 + d8 * d8;
                if (d10 - d9 > 100.0D && !this.func_217264_d()) {
                    LOGGER.warn("{} (vehicle of {}) moved too quickly! {},{},{}", entity.getName().getString(), this.player.getName().getString(), d6, d7, d8);
                    this.netManager.sendPacket(new SMoveVehiclePacket(entity));
                    return;
                }

                boolean flag = serverworld.func_226665_a__(entity, entity.getBoundingBox().shrink(0.0625D));
                d6 = d3 - this.lowestRiddenX1;
                d7 = d4 - this.lowestRiddenY1 - 1.0E-6D;
                d8 = d5 - this.lowestRiddenZ1;
                entity.move(MoverType.PLAYER, new Vec3d(d6, d7, d8));
                d6 = d3 - entity.getPosX();
                d7 = d4 - entity.getPosY();
                if (d7 > -0.5D || d7 < 0.5D) {
                    d7 = 0.0D;
                }

                d8 = d5 - entity.getPosZ();
                d10 = d6 * d6 + d7 * d7 + d8 * d8;
                boolean flag1 = false;

                //if (d10 > 0.0625D) {
                if (d10 > 0.07D) {
                    flag1 = true;
                    LOGGER.warn("{} moved wrongly!", (Object)entity.getName().getString());
                }

                entity.setPositionAndRotation(d3, d4, d5, f, f1);
                this.player.setPositionAndRotation(d3, d4, d5, this.player.rotationYaw, this.player.rotationPitch); // Forge - Resync player position on vehicle moving
                boolean flag2 = serverworld.func_226665_a__(entity, entity.getBoundingBox().shrink(0.0625D));
                if (flag && (flag1 || !flag2)) {
                    entity.setPositionAndRotation(d0, d1, d2, f, f1);
                    this.player.setPositionAndRotation(d3, d4, d5, this.player.rotationYaw, this.player.rotationPitch); // Forge - Resync player position on vehicle moving
                    this.netManager.sendPacket(new SMoveVehiclePacket(entity));
                    return;
                }

                this.player.getServerWorld().getChunkProvider().updatePlayerPosition(this.player);
                this.player.addMovementStat(this.player.getPosX() - d0, this.player.getPosY() - d1, this.player.getPosZ() - d2);
                this.vehicleFloating = d7 >= -0.03125D && !this.server.isFlightAllowed() && !serverworld.checkBlockCollision(entity.getBoundingBox().grow(0.0625D).expand(0.0D, -0.55D, 0.0D));
                this.lowestRiddenX1 = entity.getPosX();
                this.lowestRiddenY1 = entity.getPosY();
                this.lowestRiddenZ1 = entity.getPosZ();
            }

        }
    }
}
