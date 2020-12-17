package com.talhanation.camels.entities.ai.goal;
import java.util.EnumSet;

import com.talhanation.camels.init.ModEntityTypes;
import com.talhanation.camels.entities.CamelEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

public class CamelFollowCaravanGoal extends Goal {
    private CamelEntity camel;
    private double speedModifier;
    private int distCheckCounter;

    public CamelFollowCaravanGoal(CamelEntity camelln, double speedModifier) {
        this.camel = camelln;
        this.speedModifier = speedModifier;
        setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean shouldExecute()
    {
        if ((this.camel.isTame()) && (!canLeadCaravan(this.camel)) && (!this.camel.inCaravan())) {
            java.util.List<CamelEntity> list = this.camel.world.getEntitiesWithinAABB(this.camel.getClass(), this.camel.getBoundingBox().grow(9.0D, 4.0D, 9.0D));
            CamelEntity camel = null;
            double distance = Double.MAX_VALUE;

            for (CamelEntity caravanCamel : list) {
                if ((caravanCamel.isTame()) && (caravanCamel.inCaravan()) && (!caravanCamel.hasCaravanTrail())) {
                    double distanceSq = this.camel.getDistanceSq(caravanCamel);
                    if (distanceSq <= distance) {
                        distance = distanceSq;
                        camel = caravanCamel;
                    }
                }
            }
            if (camel == null) {
                for (CamelEntity caravanLeader : list) {
                    if ((caravanLeader.isTame()) && (canLeadCaravan(caravanLeader)) && (!caravanLeader.hasCaravanTrail())) {
                        double distanceSq = this.camel.getDistanceSq(caravanLeader);
                        if (distanceSq <= distance) {
                            distance = distanceSq;
                            camel = caravanLeader;
                        }
                    }
                }
            }

            if (camel == null)
                return false;
            if (distance < 4.0D)
                return false;
            if ((!canLeadCaravan(camel)) && (!firstCanBeCaravanLeader(camel, 1))) {
                return false;
            }
            this.camel.joinCaravan(camel);
            return true;
        }

        return false;
    }

    public boolean shouldContinueExecuting()
    {
        if ((this.camel.inCaravan()) && (this.camel.getCaravanHead() != null) && (this.camel.getCaravanHead().isAlive()) && (firstCanBeCaravanLeader(this.camel, 0))) {
            double distanceSq = this.camel.getDistanceSq(this.camel.getCaravanHead());

            if (distanceSq > 676.0D) {
                if (this.speedModifier <= 3.0D) {
                    this.speedModifier *= 1.75D;
                    this.distCheckCounter = 40;
                    return true;
                }
                if (this.distCheckCounter == 0) {
                    return false;
                }
            }
            if (this.distCheckCounter > 0) {
                this.distCheckCounter -= 1;
            }
            return true;
        }
        return false;
    }

    public void resetTask()
    {
        this.camel.leaveCaravan();
        this.speedModifier = 2.1D;
    }

    public void tick()
    {
        if (this.camel.inCaravan()) {
            CamelEntity caravanLeader = this.camel.getCaravanHead();
            double distance = this.camel.getDistance((net.minecraft.entity.Entity)java.util.Objects.requireNonNull(caravanLeader));
            Vector3d vec3d = new Vector3d(caravanLeader.getPosX() - this.camel.getPosX(), caravanLeader.getPosY() - this.camel.getPosY(), caravanLeader.getPosZ() - this.camel.getPosZ()).normalize().scale(Math.max(distance - 2.0D, 0.0D));
            this.camel.getNavigator().tryMoveToXYZ(this.camel.getPosX() + vec3d.x, this.camel.getPosY() + vec3d.y, this.camel.getPosZ() + vec3d.z, this.speedModifier);
        }
    }

    private boolean firstCanBeCaravanLeader(CamelEntity camel, int amount) {
        if (amount > 8)
            return false;
        if (camel.inCaravan()) {
            if ((camel.getCaravanHead() != null) && (canLeadCaravan(camel.getCaravanHead()))) {
                return true;
            }
            CamelEntity caravanLeader = camel.getCaravanHead();
            amount++;
            return firstCanBeCaravanLeader(caravanLeader, amount);
        }

        return false;
    }

    private boolean canLeadCaravan(CamelEntity camel)
    {
        return (camel.getLeashed()) || ((camel.isHorseSaddled()) && (camel.isBeingRidden()) && (camel.hasChest()));
    }
}
