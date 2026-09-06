package com.mosaicmodding.tessera.mixin;

import com.mosaicmodding.tessera.sound.TesseraSoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    private ClientLevel level;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "levelEvent",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;stateById(I)Lnet/minecraft/world/level/block/state/BlockState;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void tessera$CallExtendedBreakSound(int pType, BlockPos pPos, int pData, CallbackInfo ci, RandomSource randomSource, BlockState blockState, SoundType soundType) {
        if (soundType instanceof TesseraSoundType extendedSoundType) {
            extendedSoundType.onPlayBreakSound(level, minecraft.player, pPos, blockState,
                    ((event, volume, pitch) -> level.playLocalSound(
                            pPos,
                            event,
                            SoundSource.BLOCKS,
                            (volume + 1.0F) / 2.0F,
                            pitch * 0.8F,
                            false
                    )));
        }
    }
}