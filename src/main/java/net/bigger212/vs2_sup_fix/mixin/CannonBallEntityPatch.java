package net.bigger212.vs2_sup_fix.mixin;

import net.mehvahdjukaar.supplementaries.common.entities.CannonBallEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;

@Mixin(CannonBallEntity.class)
public class CannonBallEntityPatch {

    @Inject(method = "maybeBounce", at = @At("HEAD"), cancellable = true)
    @SuppressWarnings({"UnreachableCode"})
    private void injectMaybeBounce(BlockHitResult hit, CallbackInfoReturnable<Boolean> cir) {
        World world = ((CannonBallEntity) (Object) this).getWorld();

        Ship ship = VSGameUtilsKt.getShipObjectManagingPos(world, hit.getBlockPos());

        if (ship != null) {
            // If on ship, cancel bounce by returning false immediately.
            // This is done for version 1.1 because the cannonball seems to
            // bounce through ships leaving -them unaffected though detected.
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
