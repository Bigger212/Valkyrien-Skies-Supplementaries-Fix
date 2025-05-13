package net.bigger212.eur_sup_fix.mixin;

import net.mehvahdjukaar.moonlight.api.entity.ImprovedProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.valkyrienskies.mod.common.world.RaycastUtilsKt;

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
        System.out.println("Using VS-aware AABB collision for entity: " + entity.getName().getString());

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

        // Use the VS extension method to include ships in the raycast
        BlockHitResult hitResult = RaycastUtilsKt.clipIncludeShips(entity.getWorld(), context);

        return hitResult != null ? hitResult
                : BlockHitResult.createMissed(end, Direction.UP, BlockPos.ofFloored(end));
    }

}
