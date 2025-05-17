package net.bigger212.vs2_sup_fix.mixin;

import com.google.common.collect.Multiset;
import net.mehvahdjukaar.supplementaries.SuppPlatformStuff;
import net.mehvahdjukaar.supplementaries.common.misc.explosion.CannonBallExplosion;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.joml.RoundingMode;
import org.joml.Vector3d;
import org.joml.Vector3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.core.api.ships.properties.ChunkClaim;
import org.valkyrienskies.core.api.world.ServerShipWorld;
import org.valkyrienskies.core.api.world.ShipWorld;
import org.valkyrienskies.core.impl.game.ships.ShipData;
import org.valkyrienskies.mod.common.CompatUtil;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.mod.common.util.VectorConversionsMCKt;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(CannonBallExplosion.class)
public class CannonBallExplosionPatch {
// TODO Get the explosion to work on ships!


//    @Inject(method = "destroyBlockNoEffects", at = @At("HEAD"), cancellable = true)
//    private void onDestroyBlockVSCompatible(BlockPos pos, World level, @Nullable Entity entity, int recursionLeft, Multiset<SoundEvent> sounds, CallbackInfoReturnable<Boolean> cir) {
//        if (!(level instanceof ServerWorld serverWorld)) return;
//
//        BlockState blockState = level.getBlockState(pos);
//        if (blockState.isAir()) {
//            cir.setReturnValue(false);
//            return;
//        } else {
//
//            Ship ship = VSGameUtilsKt.getShipObjectManagingPos(level, pos);
//
//            if (ship == null) return;
//
//            Vector3d transformed = ship.getWorldToShip().transformPosition(VectorConversionsMCKt.toJOMLD(pos));
//
//            BlockPos newpos = VectorConversionsMCKt.toBlockPos(new Vector3i(
//                    (int) Math.floor(transformed.x),
//                    (int) Math.floor(transformed.y),
//                    (int) Math.floor(transformed.z)
//            ));
//
//            System.out.println("rerouting explosion to pos: " + pos);
//
//            FluidState fluidState = level.getFluidState(pos);
//            if (level.isClient && !(blockState.getBlock() instanceof AbstractFireBlock)) {
//                sounds.add(SuppPlatformStuff.getSoundType(blockState, pos, level, entity).getBreakSound());
//                level.addBlockBreakParticles(pos, blockState);
//            }
//
//            BlockEntity blockEntity = blockState.hasBlockEntity() ? level.getBlockEntity(pos) : null;
//            Block.dropStacks(blockState, level, pos, blockEntity, entity, ItemStack.EMPTY);
//            boolean bl = level.setBlockState(pos, fluidState.getBlockState(), 3, recursionLeft);
//            if (bl) {
//                level.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(entity, blockState));
//            }
//
//            cir.setReturnValue(bl);
//        }
//    }
}
