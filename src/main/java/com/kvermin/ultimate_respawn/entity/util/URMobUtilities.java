package com.kvermin.ultimate_respawn.entity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class URMobUtilities {
    public static BlockPos getBlockBelow(Entity pMob) {
        return getBlockBelow(pMob.position(), pMob.level(), pMob);
    }
    public static BlockPos getBlockBelow(Vec3 posAbove, Level level) {
        return getBlockBelow(posAbove, level, null);
    }
    public static BlockPos getBlockBelow(Vec3 posAbove, Level level, Entity entity) {
        Vec3 vec3 = new Vec3(posAbove.x(), level.getMinY(), posAbove.z());
        return level.clip(new ClipContext(posAbove, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity)).getBlockPos().offset(0, 1, 0);
    }
}
