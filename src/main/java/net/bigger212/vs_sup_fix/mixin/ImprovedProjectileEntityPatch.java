package net.bigger212.vs_sup_fix.mixin;

import net.mehvahdjukaar.moonlight.api.entity.ImprovedProjectileEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.mod.common.world.RaycastUtilsKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ImprovedProjectileEntity.class)
public abstract class ImprovedProjectileEntityPatch {
// ImprovedProjectileEntity

    // move
    @Redirect(
            method = "Lnet/mehvahdjukaar/moonlight/api/entity/ImprovedProjectileEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/mehvahdjukaar/moonlight/api/util/math/MthUtils;collideWithSweptAABB(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;D)Lnet/minecraft/world/phys/BlockHitResult;"
            )
    )
    private BlockHitResult redirectAABBCollision(Entity entity, Vec3 movement, double distance) {
        /*
        Use the VS2 utility to include ships in the raycast.
        */
        Vec3 start = entity.position();
        Vec3 end = start.add(movement);
        ClipContext context = new ClipContext (
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                entity
        );
        return RaycastUtilsKt.clipIncludeShips(entity.level(), context);
    }
}