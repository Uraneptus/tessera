package com.mosaicmodding.tessera.datagen.providers.loot;

import com.mosaicmodding.tessera.TesseraBlockProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class TesseraBlockLootSubProvider extends BlockLootSubProvider {

    protected TesseraBlockLootSubProvider(Set<Item> explosionResistant, FeatureFlagSet enabledFeatures, HolderLookup.Provider registries) {
        super(explosionResistant, enabledFeatures, registries);
    }

    protected abstract List<Supplier<Block>> modBlockEntries();

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return modBlockEntries().stream()
                .map(Supplier::get)
                .filter(b -> !(b.properties() instanceof TesseraBlockProperties blockProperties && blockProperties.getDatagenData().noLootDatagen))
                .collect(Collectors.toList());
    }
}
