package net.bigger212.eur_sup_fix.mixin;

import net.mehvahdjukaar.supplementaries.common.entities.CannonBallEntity;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3d;
import org.joml.primitives.AABBd;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.core.impl.game.ships.ShipData;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import static org.valkyrienskies.mod.common.util.VectorConversionsMCKt.toJOML;
import static org.valkyrienskies.mod.common.util.VectorConversionsMCKt.toMinecraft;
import org.valkyrienskies.core.util.AABBdUtilKt;
import static org.valkyrienskies.core.util.AABBdUtilKt.toAABBd;

@Mixin(CannonBallEntity.class)
public abstract class CannonBallEntityMixin {

    //
//    @Inject(method = "tick", at = @At("TAIL"))
//    @SuppressWarnings("all")
//    private void injectOnTick(CallbackInfo ci) {
//        // manage cannonball
//        CannonBallEntity self = (CannonBallEntity) (Object) this;
//        World world = self.getWorld();
//
//        if (world.isClient) return;
//
//        Vec3d position = self.getPos();
//        Vec3d velocity = self.getVelocity();
//
//        // detect if cannonball collides with ship box
//        System.out.println("Checking for ship at position: " + position);  // Debug: check position
//        Ship ship = VSGameUtilsKt.getShipObjectManagingPos(world, new Vector3d(position.x, position.y, position.z));
//        System.out.println("Ship found: " + ship);  // Debug: print the ship information
//
//        if (ship instanceof ServerShip serverShip) {
//
//            Vector3d jomlPos = new Vector3d(position.x, position.y, position.z);
//            Vector3d jomlVel = new Vector3d(velocity.x, velocity.y, velocity.z);
//
//            // Convert the ship's AABB from local to world space
//            AABBd shipAABB = toAABBd(serverShip.getShipAABB(), new AABBd());
//
//            // Debug: print the ship's AABB and the cannonball's position
//            System.out.println("Ship's AABB: " + shipAABB);
//            System.out.println("Cannonball Position: " + jomlPos);
//
//            // Check if the cannonball is inside the ship's AABB
//            if (shipAABB.containsPoint(jomlPos)) {
//                System.out.println("Cannonball is inside the ship's AABB");  // Debug: indicate collision detection
//
//                // Transform the cannonball's position into the ship's local space
//                Vector3d localPos = serverShip.getShipToWorld().transformPosition(jomlPos, new Vector3d());
//                Vector3d localVel = serverShip.getShipToWorld().transformDirection(jomlVel, new Vector3d());
//
//                // Transform back to world space (optional, depending on use case)
//                Vector3d correctedWorldPos = serverShip.getWorldToShip().transformPosition(localPos, new Vector3d());
//
//                // Debug: print the transformed positions
//                System.out.println("Local Position: " + localPos);
//                System.out.println("Corrected World Position: " + correctedWorldPos);
//
//                // Apply the corrected position and velocity
//                self.setPosition(correctedWorldPos.x, correctedWorldPos.y, correctedWorldPos.z);
//                self.setVelocity(localVel.x, localVel.y, localVel.z);
//            } else {
//                // Debug: print if the cannonball is not inside the ship's AABB
//                System.out.println("Cannonball is not inside the ship's AABB");
//            }
//        }
//    }

//    @Inject(
//            method = "onEntityHit",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private static void injectOnEntityHit(EntityHitResult result, CallbackInfo ci) {
//
//    }
//
//    @Inject(
//            method = "onBlockHit",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    private void injectOnBlockHit(BlockHitResult result, CallbackInfo ci) {
//
//    }

    //
    @Inject(
            method = "onEntityHit",
            at = @At("HEAD"),
            cancellable = true
    )
    private void logEntityHit(EntityHitResult result, CallbackInfo ci) {
        // Call Valkyrien Skies' ship-aware raycasting logic
        System.out.println("CANNONBALL HIT ENTITY: " + result.getEntity().getName().getString());
    }

    @Inject(
            method = "onBlockHit",
            at = @At("HEAD"),
            cancellable = true
    )
    private void logBlockHit(BlockHitResult result, CallbackInfo ci) {
        // Cast 'this' to the CannonBallEntity to access getWorld()
        World world = ((CannonBallEntity)(Object)this).getWorld();
        BlockPos pos = result.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();
        Identifier id = Registries.BLOCK.getId(block);
        System.out.println("CANNONBALL HIT BLOCK: " + id.toString());
    }
}