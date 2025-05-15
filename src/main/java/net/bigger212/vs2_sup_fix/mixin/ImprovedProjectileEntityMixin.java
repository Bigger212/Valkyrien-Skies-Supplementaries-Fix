package net.bigger212.vs2_sup_fix.mixin;

import net.mehvahdjukaar.moonlight.api.entity.ImprovedProjectileEntity;
import org.valkyrienskies.mod.common.world.RaycastUtilsKt;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ImprovedProjectileEntity.class)
public abstract class ImprovedProjectileEntityMixin {

    //
    @Redirect(
            method = "move",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/mehvahdjukaar/moonlight/api/util/math/MthUtils;collideWithSweptAABB(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;D)Lnet/minecraft/util/hit/BlockHitResult;"
            )
    )
    private BlockHitResult redirectAABBCollision(Entity entity, Vec3d movement, double distance) {

        // Current and target positions
        Vec3d start = entity.getPos();
        Vec3d end = start.add(movement);

        // Construct the RaycastContext
        RaycastContext context = new RaycastContext(
                start,
                end,
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                entity
        );

        // Use the VS utility to include ships in the raycast
        return RaycastUtilsKt.clipIncludeShips(entity.getWorld(), context);
    }
}

