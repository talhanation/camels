package com.talhanation.camels.entity.ai;


import java.util.List;
import java.util.Objects;

import com.talhanation.camels.entity.EntityCamel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

public class AICamelCaravan extends EntityAIBase {
    private EntityCamel camel;

    private double speedModifier;
    private int distCheckCounter;

    public AICamelCaravan(EntityCamel camel, double speedModifier) {
        this.camel = camel;
        this.speedModifier = speedModifier;
        setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (this.camel.isTame() && this.camel.hasChest() && !canLeadCaravan(this.camel) && !this.camel.inCaravan()) {
            List<EntityCamel> list = this.camel.world.getEntitiesWithinAABB(this.camel.getClass(), this.camel.getEntityBoundingBox().grow(9.0D, 4.0D, 9.0D));
            EntityCamel camel = null;
            double distance = Double.MAX_VALUE;
            for (EntityCamel caravanCamel : list) {
                if (caravanCamel.isTame() && caravanCamel.inCaravan() && !caravanCamel.hasCaravanTrail()) {
                    double distanceSq = this.camel.getDistanceSq((Entity)caravanCamel);
                    if (distanceSq <= distance) {
                        distance = distanceSq;
                        camel = caravanCamel;
                    }
                }
            }
            if (camel == null)
                for (EntityCamel caravanLeader : list) {
                    if (caravanLeader.isTame() && canLeadCaravan(caravanLeader) && !caravanLeader.hasCaravanTrail()) {
                        double distanceSq = this.camel.getDistanceSq((Entity)caravanLeader);
                        if (distanceSq <= distance) {
                            distance = distanceSq;
                            camel = caravanLeader;
                        }
                    }
                }
            if (camel == null)
                return false;
            if (distance < 4.0D)
                return false;
            if (!canLeadCaravan(camel) && !firstCanBeCaravanLeader(camel, 1))
                return false;
            this.camel.joinCaravan(camel);
            return true;
        }
        return false;
    }

    public boolean shouldContinueExecuting() {
        if (this.camel.inCaravan() && this.camel.getCaravanHead() != null && this.camel.getCaravanHead().isEntityAlive() && firstCanBeCaravanLeader(this.camel, 0)) {
            double distanceSq = this.camel.getDistanceSq((Entity)this.camel.getCaravanHead());
            if (distanceSq > 676.0D) {
                if (this.speedModifier <= 3.0D) {
                    this.speedModifier *= 1.2D;
                    this.distCheckCounter = 40;
                    return true;
                }
                if (this.distCheckCounter == 0)
                    return false;
            }
            if (this.distCheckCounter > 0)
                this.distCheckCounter--;
            return true;
        }
        return false;
    }

    public void resetTask() {
        this.camel.leaveCaravan();
        this.speedModifier = 2.1D;
    }

    public void updateTask() {
        if (this.camel.inCaravan()) {
            EntityCamel caravanLeader = this.camel.getCaravanHead();
            double distance = this.camel.getDistance((Entity)Objects.requireNonNull(caravanLeader));
            Vec3d vec3d = (new Vec3d(caravanLeader.posX - this.camel.posX, caravanLeader.posY - this.camel.posY, caravanLeader.posZ - this.camel.posZ)).normalize().scale(Math.max(distance - 2.0D, 0.0D));
            this.camel.getNavigator().tryMoveToXYZ(this.camel.posX + vec3d.x, this.camel.posY + vec3d.y, this.camel.posZ + vec3d.z, this.speedModifier);
        }
    }

    private boolean firstCanBeCaravanLeader(EntityCamel camel, int amount) {
        if (amount > 8)
            return false;
        if (camel.inCaravan()) {
            if (camel.getCaravanHead() != null && canLeadCaravan(camel.getCaravanHead()))
                return true;
            EntityCamel caravanLeader = camel.getCaravanHead();
            amount++;
            return firstCanBeCaravanLeader(caravanLeader, amount);
        }
        return false;
    }

    private boolean canLeadCaravan(EntityCamel camel) {
        return (camel.getLeashed() || (camel.isHorseSaddled() && camel.isBeingRidden() && camel.hasChest()));
    }
}
