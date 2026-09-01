package com.mosaicmodding.tessera.sound;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Lazy sound type similar to what neoforge has, and it provides hooks for when each individual block sound is played.
 */
public class TesseraSoundType extends SoundType {
    private final Supplier<SoundEvent> breakSound;
    private final Supplier<SoundEvent> stepSound;
    private final Supplier<SoundEvent> placeSound;
    private final Supplier<SoundEvent> hitSound;
    private final Supplier<SoundEvent> fallSound;

    public TesseraSoundType(float volumeIn, float pitchIn, Supplier<SoundEvent> breakSoundIn, Supplier<SoundEvent> stepSoundIn, Supplier<SoundEvent> placeSoundIn, Supplier<SoundEvent> hitSoundIn, Supplier<SoundEvent> fallSoundIn) {
        super(volumeIn, pitchIn, null, null, null, null, null);
        this.breakSound = breakSoundIn;
        this.stepSound = stepSoundIn;
        this.placeSound = placeSoundIn;
        this.hitSound = hitSoundIn;
        this.fallSound = fallSoundIn;
    }

    /**
     * Called by mixin injection when the block broken sound plays in
     * <p>{@link net.minecraft.client.renderer.LevelRenderer#levelEvent(int, BlockPos, int)}
     */
    public void onPlayBreakSound(Level level, Player player, BlockPos pos, BlockState state, TesseraSoundType.EquivalentEffectSoundAcceptor acceptor) {
    }

    /**
     * Called by mixin injection when an entity triggers the block step sound in
     * <p>{@link Entity#playStepSound(BlockPos, BlockState)}
     */
    public void onPlayStepSound(Level level, Entity entity, BlockPos pos, BlockState state, TesseraSoundType.EquivalentEffectSoundAcceptor acceptor) {
    }

    /**
     * Called by mixin injection when a player triggers the block place sound in
     * <p>{@link net.minecraft.world.item.BlockItem#place(BlockPlaceContext)}
     */
    public void onPlayPlaceSound(Level level, Player player, BlockPos pos, TesseraSoundType.EquivalentEffectSoundAcceptor acceptor) {
    }

    /**
     * Called by mixin injection when the block breaking progress sound is played in
     * <p> {@link net.minecraft.client.multiplayer.MultiPlayerGameMode#continueDestroyBlock(BlockPos, Direction)}
     */
    public void onPlayHitSound(Level level, Player player, BlockPos pos, TesseraSoundType.EquivalentEffectSoundAcceptor acceptor) {

    }

    /**
     * Called by mixin injection when an entity plays the fall sound in
     * <p> {@link LivingEntity#playBlockFallSound()}
     */
    public void onPlayFallSound(Level level, LivingEntity entity, BlockPos pos, TesseraSoundType.EquivalentEffectSoundAcceptor acceptor) {
    }

    @Override
    public @NotNull SoundEvent getBreakSound() {
        return this.breakSound.get();
    }

    @Override
    public @NotNull SoundEvent getStepSound() {
        return this.stepSound.get();
    }

    @Override
    public @NotNull SoundEvent getPlaceSound() {
        return this.placeSound.get();
    }

    @Override
    public @NotNull SoundEvent getHitSound() {
        return this.hitSound.get();
    }

    @Override
    public @NotNull SoundEvent getFallSound() {
        return this.fallSound.get();
    }

    /**
     * An interface that serves as a context-appropriate vanilla-matching playSound call.
     * Volume and Pitch parameters will be modified in the same way as vanilla does it for block sounds.
     * Avoid this if you want more precise control over volume/pitch.
     */
    public interface EquivalentEffectSoundAcceptor {
        default void playSound(Supplier<SoundEvent> event, float volume, float pitch) {
            playSound(event.get(), volume, pitch);
        }
        void playSound(SoundEvent event, float volume, float pitch);
    }
}
