package net.bigger212.vs2_sup_fix.mixin;

import net.bigger212.vs2_sup_fix.vs2_sup_fix;
import net.mehvahdjukaar.supplementaries.common.entities.CannonBallEntity;
import net.mehvahdjukaar.supplementaries.common.misc.explosion.CannonBallExplosion;
import net.mehvahdjukaar.supplementaries.configs.CommonConfigs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.core.api.world.ShipWorld;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

@Mixin(CannonBallEntity.class)
public abstract class CannonBallEntityPatch {
// CannonBallEntity

    // onBlockHit
    @Inject(method = "onBlockHit", at = @At("HEAD"))
    @SuppressWarnings("UnreachableCode")
    private void injectOnBlockHit(BlockHitResult result, CallbackInfo ci) {
        /*
        Redirect explosion pos to ships local pos.
        */
        CannonBallEntity self = ((CannonBallEntity)(Object)this);
        if (!self.getWorld().isClient) {
            Vec3d hitPos = result.getPos();
            World world = self.getWorld();
            if (!(world instanceof ServerWorld)) return;

            Ship ship = VSGameUtilsKt.getShipObjectManagingPos(world, result.getBlockPos());
            if (ship != null) {

                double radius;
                if (vs2_sup_fix.DAMAGE_SHIPS_UNIQUELY) {
                    radius = vs2_sup_fix.CANNONBALL_BREAK_RADIUS;
                } else {
                    radius = CommonConfigs.Functional.CANNONBALL_BREAK_RADIUS.get();
                }
                Vec3d movement = self.getVelocity();
                double vel = Math.abs(movement.length());
                float scaling = 5.0F;
                float maxAmount = (float)(vel * vel * (double)scaling);

                Vector3d hitPosJoml = new Vector3d(hitPos.x, hitPos.y, hitPos.z);
                Vector3d shipHitPosJoml = ship.getWorldToShip().transformPosition(hitPosJoml);
                Vec3d shipHitPos = new Vec3d(shipHitPosJoml.x, shipHitPosJoml.y, shipHitPosJoml.z);
                BlockPos shipCenter = BlockPos.ofFloored(shipHitPos);

                CannonBallExplosion explosion = new CannonBallExplosion(
                        world,
                        null,
                        shipHitPosJoml.x, shipHitPosJoml.y, shipHitPosJoml.z,
                        shipCenter,
                        maxAmount,
                        (float) radius,
                        null
                );
                explosion.collectBlocksAndDamageEntities();
                explosion.affectWorld(true);
            }
        }
    }
}
