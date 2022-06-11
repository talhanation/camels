package com.talhanation.camels.entities.ai;

import com.talhanation.camels.entities.AbstractCamelEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class CamelFollowCaravanGoal extends Goal{
    public final AbstractCamelEntity abstractCamel;
    private double speedModifier;
    private static final int CARAVAN_LIMIT = 8;
    private int distCheckCounter;

    public CamelFollowCaravanGoal(AbstractCamelEntity abstractCamel, double speed) {
        this.abstractCamel = abstractCamel;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        if (!this.abstractCamel.isLeashed() && !this.abstractCamel.inCaravan()) {
            List<Entity> list = this.abstractCamel.level.getEntities(this.abstractCamel, this.abstractCamel.getBoundingBox().inflate(9.0D, 4.0D, 9.0D), (p_25505_) -> {
                EntityType<?> entitytype = p_25505_.getType();
                return entitytype == EntityType.LLAMA || entitytype == EntityType.TRADER_LLAMA;
            });
            AbstractCamelEntity camel = null;
            double d0 = Double.MAX_VALUE;

            for(Entity entity : list) {
                AbstractCamelEntity camel1 = (AbstractCamelEntity)entity;
                if (camel1.inCaravan() && !camel1.hasCaravanTail()) {
                    double d1 = this.abstractCamel.distanceToSqr(camel1);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        camel = camel1;
                    }
                }
            }

            if (camel == null) {
                for(Entity entity1 : list) {
                    AbstractCamelEntity camel2 = (AbstractCamelEntity)entity1;
                    if (camel2.isLeashed() && !camel2.hasCaravanTail()) {
                        double d2 = this.abstractCamel.distanceToSqr(camel2);
                        if (!(d2 > d0)) {
                            d0 = d2;
                            camel = camel2;
                        }
                    }
                }
            }

            if (camel == null) {
                return false;
            } else if (d0 < 4.0D) {
                return false;
            } else if (!camel.isLeashed() && !this.firstIsLeashed(camel, 1)) {
                return false;
            } else {
                this.abstractCamel.joinCaravan(camel);
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        if (this.abstractCamel.inCaravan() && this.abstractCamel.getCaravanHead().isAlive() && this.firstIsLeashed(this.abstractCamel, 0)) {
            double d0 = this.abstractCamel.distanceToSqr(this.abstractCamel.getCaravanHead());
            if (d0 > 676.0D) {
                if (this.speedModifier <= 3.0D) {
                    this.speedModifier *= 1.2D;
                    this.distCheckCounter = reducedTickDelay(40);
                    return true;
                }

                if (this.distCheckCounter == 0) {
                    return false;
                }
            }

            if (this.distCheckCounter > 0) {
                --this.distCheckCounter;
            }

            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        this.abstractCamel.leaveCaravan();
        this.speedModifier = 2.1D;
    }

    public void tick() {
        if (this.abstractCamel.inCaravan()) {
            if (!(this.abstractCamel.getLeashHolder() instanceof LeashFenceKnotEntity)) {
                AbstractCamelEntity llama = this.abstractCamel.getCaravanHead();
                double d0 = (double)this.abstractCamel.distanceTo(llama);
                float f = 2.0F;
                Vec3 vec3 = (new Vec3(llama.getX() - this.abstractCamel.getX(), llama.getY() - this.abstractCamel.getY(), llama.getZ() - this.abstractCamel.getZ())).normalize().scale(Math.max(d0 - 2.0D, 0.0D));
                this.abstractCamel.getNavigation().moveTo(this.abstractCamel.getX() + vec3.x, this.abstractCamel.getY() + vec3.y, this.abstractCamel.getZ() + vec3.z, this.speedModifier);
            }
        }
    }

    private boolean firstIsLeashed(AbstractCamelEntity camel, int p_25508_) {
        if (p_25508_ > 8) {
            return false;
        } else if (camel.inCaravan()) {
            if (camel.getCaravanHead().isLeashed()) {
                return true;
            } else {
                AbstractCamelEntity camel1 = camel.getCaravanHead();
                ++p_25508_;
                return this.firstIsLeashed(camel1, p_25508_);
            }
        } else {
            return false;
        }
    }
}