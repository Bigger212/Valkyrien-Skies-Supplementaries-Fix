package net.bigger212.vs2_sup_fix.mixin;

import net.mehvahdjukaar.supplementaries.common.entities.CannonBallEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.core.impl.game.ships.ShipData;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;

@Mixin(CannonBallEntity.class)
public class CannonBallEntityPatch {

    @Inject(method = "onBlockHit", at = @At("HEAD"))
    @SuppressWarnings("UnreachableCode")
    private void injectOnBlockHit(BlockHitResult result, CallbackInfo ci) {
        Vec3d hitPos = result.getPos();
        World world = ((CannonBallEntity)(Object) this).getWorld();

        if (!(world instanceof ServerWorld serverWorld)) return;

        Ship ship = VSGameUtilsKt.getShipObjectManagingPos(world, result.getBlockPos());

        if (ship != null) {
            Explosion explosion = new Explosion(world, null,
                    world.getDamageSources().explosion(null), null,
                    hitPos.x, hitPos.y, hitPos.z,
                    2.5f,
                    false, Explosion.DestructionType.DESTROY_WITH_DECAY);

            explosion.collectBlocksAndDamageEntities();
            explosion.affectWorld(false);
            // This works but it needs to use the CannonBallExplosion because
            // there are no effects.
        }
    }
}
