package net.bigger212.vs_sup_fix.mixin;

import net.bigger212.vs_sup_fix.vs_sup_fix;
import net.mehvahdjukaar.supplementaries.common.entities.CannonBallEntity;
import net.mehvahdjukaar.supplementaries.common.misc.explosion.CannonBallExplosion;
import net.mehvahdjukaar.supplementaries.configs.CommonConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

@Mixin(CannonBallEntity.class)
public abstract class CannonBallEntityPatch {
// CannonBallEntity

    // onBlockHit
    @Inject(method = "onHitBlock", at = @At("HEAD"))
    @SuppressWarnings("UnreachableCode")
    private void injectOnBlockHit(BlockHitResult result, CallbackInfo ci) {
        /*
        Redirect explosion pos to ships local pos.
        */
        CannonBallEntity self = ((CannonBallEntity)(Object)this);
        Level level = self.level();

        if (!(level instanceof ServerLevel serverLevel)) return;

        Vec3 hitPos = result.getLocation();
        Ship ship = VSGameUtilsKt.getShipObjectManagingPos(serverLevel, result.getBlockPos());

        if (ship != null) {

            double radius;
            if (vs_sup_fix.DAMAGE_SHIPS_UNIQUELY) {
                radius = vs_sup_fix.CANNONBALL_BREAK_RADIUS;
            } else {
                radius = CommonConfigs.Functional.CANNONBALL_BREAK_RADIUS.get();
            }
            Vec3 movement = self.getDeltaMovement(); // double check...
            double vel = Math.abs(movement.length());
            float scaling = 5.0F;
            float maxAmount = (float)(vel * vel * (double)scaling);

            Vector3d hitPosJoml = new Vector3d(hitPos.x, hitPos.y, hitPos.z);
            Vector3d shipHitPosJoml = ship.getWorldToShip().transformPosition(hitPosJoml);
            Vec3 shipHitPos = new Vec3(shipHitPosJoml.x, shipHitPosJoml.y, shipHitPosJoml.z);
            //BlockPos shipCenter = BlockPos.ofFloored(shipHitPos);
            BlockPos shipCenter = new BlockPos((int)Math.floor(shipHitPos.x), (int)Math.floor(shipHitPos.y), (int)Math.floor(shipHitPos.z));

            CannonBallExplosion explosion = new CannonBallExplosion(
                    level,
                    null,
                    shipHitPosJoml.x, shipHitPosJoml.y, shipHitPosJoml.z,
                    shipCenter,
                    maxAmount,
                    (float) radius,
                    null
            );
            explosion.explode(); // double check
            explosion.finalizeExplosion(true); // double check
        }
    }
}
