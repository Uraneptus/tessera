package com.mosaicmodding.tessera;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * An extension of Block Properties designed to interface with {@link TesseraDatagenBlockData}
 * Some of these values are optional; they only get added during datagen.
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class TesseraBlockProperties extends BlockBehaviour.Properties {

    public enum BlockRenderType {
        SOLID("solid", RenderType.solid()),
        CUTOUT("cutout", RenderType.cutout()),
        CUTOUT_MIPPED("cutout_mipped", RenderType.cutoutMipped()),
        CUTOUT_MIPPED_ALL("cutout_mipped_all", RenderType.cutoutMipped()), //Mipped All is a neo only thing refer to NamedRenderTypeManager
        TRANSLUCENT("translucent", RenderType.translucent()),
        TRIPWIRE("tripwire", RenderType.tripwire());

        private final ResourceLocation location;
        private final RenderType actualType;

        BlockRenderType(String name, RenderType actualType) {
            this.location = ResourceLocation.withDefaultNamespace(name);
            this.actualType = actualType;
        }

        public ResourceLocation getLocation() {
            return location;
        }

        public RenderType getActualType() {
            return actualType;
        }
    }

    public BlockRenderType blockRenderType;

    public TesseraBlockProperties() {
        super();
    }

    public static TesseraBlockProperties of() {
        return new TesseraBlockProperties();
    }

    public TesseraBlockProperties copy() {
        return copy(this);
    }

    public static TesseraBlockProperties copy(BlockBehaviour behaviour) {
        return copy(behaviour.properties);
    }

    public static TesseraBlockProperties copy(BlockBehaviour.Properties properties) {
        TesseraBlockProperties copy = TesseraBlockProperties.of();
        copy.destroyTime = properties.destroyTime;
        copy.explosionResistance = properties.explosionResistance;
        copy.hasCollision = properties.hasCollision;
        copy.isRandomlyTicking = properties.isRandomlyTicking;
        copy.lightEmission = properties.lightEmission;
        copy.mapColor = properties.mapColor;
        copy.soundType = properties.soundType;
        copy.friction = properties.friction;
        copy.speedFactor = properties.speedFactor;
        copy.dynamicShape = properties.dynamicShape;
        copy.canOcclude = properties.canOcclude;
        copy.isAir = properties.isAir;
        copy.requiresCorrectToolForDrops = properties.requiresCorrectToolForDrops;
        copy.jumpFactor = properties.jumpFactor;
        copy.drops = properties.drops;
        copy.ignitedByLava = properties.ignitedByLava;
        copy.forceSolidOn = properties.forceSolidOn;
        copy.pushReaction = properties.pushReaction;
        copy.spawnTerrainParticles = properties.spawnTerrainParticles;
        copy.instrument = properties.instrument;
        copy.replaceable = properties.replaceable;
        copy.isValidSpawn = properties.isValidSpawn;
        copy.isRedstoneConductor = properties.isRedstoneConductor;
        copy.isSuffocating = properties.isSuffocating;
        copy.isViewBlocking = properties.isViewBlocking;
        copy.emissiveRendering = properties.emissiveRendering;
        copy.requiredFeatures = properties.requiredFeatures;
        copy.offsetFunction = properties.offsetFunction;
        copy.hasPostProcess = properties.hasPostProcess;

        if (properties instanceof TesseraBlockProperties from) {
            from.copyDatagenDataTo(copy);
            copy.blockRenderType = from.blockRenderType;
        }

        return copy;
    }


    public TesseraBlockProperties offsetFunction(BlockBehaviour.OffsetFunction offsetFunction) {
        this.offsetFunction = offsetFunction;
        return this;
    }

    @Override
    @NotNull
    public TesseraBlockProperties offsetType(@NotNull BlockBehaviour.OffsetType pOffsetType) {
        return (TesseraBlockProperties) super.offsetType(pOffsetType);
    }

    @DatagenOnly
    public TesseraBlockProperties addDatagenData(Consumer<TesseraDatagenBlockData> function) {
        if (TesseraCore.PLAT_HELPER.isDuringDatagen()) {
            function.accept(getDatagenData());
        }
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties copyDatagenDataTo(TesseraBlockProperties to) {
        if (TesseraCore.PLAT_HELPER.isDuringDatagen()) {
            TesseraDatagenBlockData.copyDatagenDataFrom(this, to);
        }
        return this;
    }

    @DatagenOnly
    public TesseraDatagenBlockData getDatagenData() {
        return TesseraDatagenBlockData.getDatagenData(this);
    }

    @DatagenOnly
    public TesseraBlockProperties addTag(TagKey<Block> tag) {
        addDatagenData(d -> d.addTag(tag));
        return this;
    }

    @DatagenOnly
    @SafeVarargs
    public final TesseraBlockProperties addTags(TagKey<Block>... tags) {
        addDatagenData(d -> d.addTags(tags));
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties addTag(BlockItemTagKey tag) {
        addDatagenData(d -> d.addTag(tag));
        return this;
    }

    @DatagenOnly
    public final TesseraBlockProperties addTags(BlockItemTagKey... tags) {
        addDatagenData(d -> d.addTags(tags));
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties noLootDatagen() {
        addDatagenData(TesseraDatagenBlockData::noLootDatagen);
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties needsPickaxe() {
        addDatagenData(TesseraDatagenBlockData::needsPickaxe);
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties needsAxe() {
        addDatagenData(TesseraDatagenBlockData::needsAxe);
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties needsShovel() {
        addDatagenData(TesseraDatagenBlockData::needsShovel);
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties needsHoe() {
        addDatagenData(TesseraDatagenBlockData::needsHoe);
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties needsStone() {
        addDatagenData(TesseraDatagenBlockData::needsStone);
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties needsIron() {
        addDatagenData(TesseraDatagenBlockData::needsIron);
        return this;
    }

    @DatagenOnly
    public TesseraBlockProperties needsDiamond() {
        addDatagenData(TesseraDatagenBlockData::needsDiamond);
        return this;
    }

    public TesseraBlockProperties setCutout() {
        return setRenderType(BlockRenderType.CUTOUT);
    }

    public TesseraBlockProperties setTranslucent() {
        return setRenderType(BlockRenderType.TRANSLUCENT);
    }

    public TesseraBlockProperties setRenderType(BlockRenderType renderType) {
        addDatagenData(d -> d.setRenderType(renderType));
        this.blockRenderType = renderType;
        return this;
    }

    @Override
    @NotNull
    public TesseraBlockProperties noCollission() {
        return (TesseraBlockProperties) super.noCollission();
    }

    @Override
    @NotNull
    public TesseraBlockProperties noOcclusion() {
        return (TesseraBlockProperties) super.noOcclusion();
    }

    @Override
    @NotNull
    public TesseraBlockProperties friction(float friction) {
        return (TesseraBlockProperties) super.friction(friction);
    }

    @Override
    @NotNull
    public TesseraBlockProperties speedFactor(float factor) {
        return (TesseraBlockProperties) super.speedFactor(factor);
    }

    @Override
    @NotNull
    public TesseraBlockProperties jumpFactor(float factor) {
        return (TesseraBlockProperties) super.jumpFactor(factor);
    }

    @Override
    @NotNull
    public TesseraBlockProperties sound(@NotNull SoundType type) {
        return (TesseraBlockProperties) super.sound(type);
    }

    @Override
    @NotNull
    public TesseraBlockProperties lightLevel(@NotNull ToIntFunction<BlockState> lightMap) {
        return (TesseraBlockProperties) super.lightLevel(lightMap);
    }

    @Override
    @NotNull
    public TesseraBlockProperties strength(float destroyTime, float explosionResistance) {
        return (TesseraBlockProperties) super.strength(destroyTime, explosionResistance);
    }

    @Override
    @NotNull
    public TesseraBlockProperties instabreak() {
        return (TesseraBlockProperties) super.instabreak();
    }

    @Override
    @NotNull
    public TesseraBlockProperties strength(float strength) {
        return (TesseraBlockProperties) super.strength(strength);
    }

    @Override
    @NotNull
    public TesseraBlockProperties randomTicks() {
        return (TesseraBlockProperties) super.randomTicks();
    }

    @Override
    @NotNull
    public TesseraBlockProperties dynamicShape() {
        return (TesseraBlockProperties) super.dynamicShape();
    }

    @Override
    @NotNull
    public TesseraBlockProperties noLootTable() {
        noLootDatagen();
        return (TesseraBlockProperties) super.noLootTable();
    }

    @Override
    @NotNull
    //@SuppressWarnings("deprecation")
    public TesseraBlockProperties dropsLike(@NotNull Block block) {
        noLootDatagen();
        return (TesseraBlockProperties) super.dropsLike(block);
    }

    /* Is a neoforge thing
    @Override
    @NotNull
    public LodestoneBlockProperties lootFrom(@NotNull Supplier<? extends Block> blockIn) {
        noLootDatagen();
        return (LodestoneBlockProperties) super.lootFrom(blockIn);
    }

     */

    @Override
    @NotNull
    public TesseraBlockProperties air() {
        return (TesseraBlockProperties) super.air();
    }

    @Override
    @NotNull
    public TesseraBlockProperties isValidSpawn(@NotNull BlockBehaviour.StateArgumentPredicate<EntityType<?>> predicate) {
        return (TesseraBlockProperties) super.isValidSpawn(predicate);
    }

    @Override
    @NotNull
    public TesseraBlockProperties isRedstoneConductor(@NotNull BlockBehaviour.StatePredicate predicate) {
        return (TesseraBlockProperties) super.isRedstoneConductor(predicate);
    }

    @Override
    @NotNull
    public TesseraBlockProperties isSuffocating(@NotNull BlockBehaviour.StatePredicate predicate) {
        return (TesseraBlockProperties) super.isSuffocating(predicate);
    }

    @Override
    @NotNull
    public TesseraBlockProperties isViewBlocking(@NotNull BlockBehaviour.StatePredicate predicate) {
        return (TesseraBlockProperties) super.isViewBlocking(predicate);
    }

    @Override
    @NotNull
    public TesseraBlockProperties hasPostProcess(@NotNull BlockBehaviour.StatePredicate predicate) {
        return (TesseraBlockProperties) super.hasPostProcess(predicate);
    }

    @Override
    @NotNull
    public TesseraBlockProperties emissiveRendering(@NotNull BlockBehaviour.StatePredicate predicate) {
        return (TesseraBlockProperties) super.emissiveRendering(predicate);
    }

    @Override
    @NotNull
    public TesseraBlockProperties requiresCorrectToolForDrops() {
        return (TesseraBlockProperties) super.requiresCorrectToolForDrops();
    }

    @Override
    @NotNull
    public TesseraBlockProperties mapColor(@NotNull Function<BlockState, MapColor> p_285406_) {
        return (TesseraBlockProperties) super.mapColor(p_285406_);
    }

    @Override
    @NotNull
    public TesseraBlockProperties mapColor(@NotNull DyeColor p_285331_) {
        return (TesseraBlockProperties) super.mapColor(p_285331_);
    }

    @Override
    @NotNull
    public TesseraBlockProperties mapColor(@NotNull MapColor p_285137_) {
        return (TesseraBlockProperties) super.mapColor(p_285137_);
    }

    @Override
    @NotNull
    public TesseraBlockProperties destroyTime(float destroyTime) {
        return (TesseraBlockProperties) super.destroyTime(destroyTime);
    }

    @Override
    @NotNull
    public TesseraBlockProperties explosionResistance(float explosionResistance) {
        return (TesseraBlockProperties) super.explosionResistance(explosionResistance);
    }

    @Override
    @NotNull
    public TesseraBlockProperties ignitedByLava() {
        return (TesseraBlockProperties) super.ignitedByLava();
    }

    @Override
    @NotNull
    public TesseraBlockProperties liquid() {
        return (TesseraBlockProperties) super.liquid();
    }

    @Override
    @NotNull
    public TesseraBlockProperties forceSolidOn() {
        return (TesseraBlockProperties) super.forceSolidOn();
    }

    @Override
    @NotNull
    public TesseraBlockProperties pushReaction(@NotNull PushReaction p_278265_) {
        return (TesseraBlockProperties) super.pushReaction(p_278265_);
    }

    @Override
    @NotNull
    public TesseraBlockProperties noTerrainParticles() {
        return (TesseraBlockProperties) super.noTerrainParticles();
    }

    @Override
    @NotNull
    public TesseraBlockProperties requiredFeatures(FeatureFlag @NotNull ... pRequiredFeatures) {
        return (TesseraBlockProperties) super.requiredFeatures(pRequiredFeatures);
    }

    @Override
    @NotNull
    public TesseraBlockProperties instrument(@NotNull NoteBlockInstrument p_282170_) {
        return (TesseraBlockProperties) super.instrument(p_282170_);
    }

    @Override
    @NotNull
    public TesseraBlockProperties replaceable() {
        return (TesseraBlockProperties) super.replaceable();
    }
}