package com.mosaicmodding.tessera.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mosaicmodding.tessera.sound.TesseraSoundType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.SoundType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void tessera$CallExtendedPlaceSound(BlockPlaceContext pContext, CallbackInfoReturnable<InteractionResult> cir, @Local SoundType soundType) {
        if (soundType instanceof TesseraSoundType extendedSoundType) {
            var level = pContext.getLevel();
            var player = pContext.getPlayer();
            var pos = pContext.getClickedPos();
            extendedSoundType.onPlayPlaceSound(level, player, pos,
                    ((event, volume, pitch) -> level.playSound(
                            player,
                            pos,
                            event,
                            SoundSource.BLOCKS,
                            (volume + 1.0F) / 2.0F,
                            pitch * 0.8F)
                    ));
        }
    }
}