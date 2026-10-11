package com.mosaicmodding.tessera;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
@DatagenOnly
public class TesseraDatagenBlockData {

    private static HashMap<TesseraBlockProperties, TesseraDatagenBlockData> DATAGEN_DATA_CACHE;

    private final List<TagKey<Block>> tags = new ArrayList<>();

    public ResourceLocation renderType;
    public boolean noLootDatagen = false;
    public boolean lootDropSelf = false;

    public static TesseraDatagenBlockData copyDatagenDataFrom(TesseraBlockProperties from, TesseraBlockProperties to) {
        var copy = getDatagenData(from).copy();
        return DATAGEN_DATA_CACHE.put(to, copy);
    }

    public static TesseraDatagenBlockData getDatagenData(TesseraBlockProperties properties) {
        if (!TesseraCore.PLAT_HELPER.isDuringDatagen()) {
            throw new UnsupportedOperationException("Cannot access datagen data outside of datagen");
        }
        if (DATAGEN_DATA_CACHE == null) {
            DATAGEN_DATA_CACHE = new HashMap<>();
        }
        return DATAGEN_DATA_CACHE.computeIfAbsent(properties, p -> new TesseraDatagenBlockData());
    }

    public TesseraDatagenBlockData addTag(TagKey<Block> blockTagKey) {
        tags.add(blockTagKey);
        return this;
    }

    @SafeVarargs
    public final TesseraDatagenBlockData addTags(TagKey<Block>... blockTagKeys) {
        tags.addAll(Arrays.asList(blockTagKeys));
        return this;
    }

    public TesseraDatagenBlockData addTag(BlockItemTagKey key) {
        addTag(key.blockTag());
        return this;
    }

    public final TesseraDatagenBlockData addTags(BlockItemTagKey... blockTagKeys) {
        for (BlockItemTagKey key : blockTagKeys) {
            addTag(key.blockTag());
        }
        return this;
    }

    public TesseraDatagenBlockData copy() {
        TesseraDatagenBlockData copy = new TesseraDatagenBlockData();
        copy.tags.addAll(tags);
        copy.renderType = renderType;
        copy.noLootDatagen = noLootDatagen;
        copy.lootDropSelf = lootDropSelf;
        return copy;
    }

    public List<TagKey<Block>> getTags() {
        return tags;
    }

    public TesseraDatagenBlockData needsPickaxe() {
        return addTag(BlockTags.MINEABLE_WITH_PICKAXE);
    }

    public TesseraDatagenBlockData needsAxe() {
        return addTag(BlockTags.MINEABLE_WITH_AXE);
    }

    public TesseraDatagenBlockData needsShovel() {
        return addTag(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    public TesseraDatagenBlockData needsHoe() {
        return addTag(BlockTags.MINEABLE_WITH_HOE);
    }

    public TesseraDatagenBlockData needsStone() {
        return addTag(BlockTags.NEEDS_STONE_TOOL);
    }

    public TesseraDatagenBlockData needsIron() {
        return addTag(BlockTags.NEEDS_IRON_TOOL);
    }

    public TesseraDatagenBlockData needsDiamond() {
        return addTag(BlockTags.NEEDS_DIAMOND_TOOL);
    }

    public TesseraDatagenBlockData setRenderType(TesseraBlockProperties.BlockRenderType renderType) {
        this.renderType = renderType.getLocation();
        return this;
    }

    public TesseraDatagenBlockData noLootDatagen() {
        noLootDatagen = true;
        return this;
    }

    public TesseraDatagenBlockData lootDropSelf() {
        lootDropSelf = true;
        return this;
    }
}