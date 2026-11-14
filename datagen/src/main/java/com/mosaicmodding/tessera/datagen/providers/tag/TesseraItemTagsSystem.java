package com.mosaicmodding.tessera.datagen.providers.tag;

import com.mosaicmodding.tessera.TesseraBlockProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class TesseraItemTagsSystem extends ItemTagsProvider {
    private static final Map<TagKey<Block>, TagKey<Item>> BLOCK_TO_ITEM_MAPPING = Map.<TagKey<Block>, TagKey<Item>>ofEntries(
            Map.entry(BlockTags.WOOL, ItemTags.WOOL),
            Map.entry(BlockTags.PLANKS, ItemTags.PLANKS),
            Map.entry(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS),
            Map.entry(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS),
            Map.entry(BlockTags.STONE_BUTTONS, ItemTags.STONE_BUTTONS),
            Map.entry(BlockTags.BUTTONS, ItemTags.BUTTONS),
            Map.entry(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS),
            Map.entry(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS),
            Map.entry(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS),
            Map.entry(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS),
            Map.entry(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES),
            Map.entry(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES),
            Map.entry(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES),
            Map.entry(BlockTags.DOORS, ItemTags.DOORS),
            Map.entry(BlockTags.SAPLINGS, ItemTags.SAPLINGS),
            Map.entry(BlockTags.BAMBOO_BLOCKS, ItemTags.BAMBOO_BLOCKS),
            Map.entry(BlockTags.OAK_LOGS, ItemTags.OAK_LOGS),
            Map.entry(BlockTags.DARK_OAK_LOGS, ItemTags.DARK_OAK_LOGS),
            Map.entry(BlockTags.BIRCH_LOGS, ItemTags.BIRCH_LOGS),
            Map.entry(BlockTags.ACACIA_LOGS, ItemTags.ACACIA_LOGS),
            Map.entry(BlockTags.SPRUCE_LOGS, ItemTags.SPRUCE_LOGS),
            Map.entry(BlockTags.MANGROVE_LOGS, ItemTags.MANGROVE_LOGS),
            Map.entry(BlockTags.JUNGLE_LOGS, ItemTags.JUNGLE_LOGS),
            Map.entry(BlockTags.CHERRY_LOGS, ItemTags.CHERRY_LOGS),
            Map.entry(BlockTags.CRIMSON_STEMS, ItemTags.CRIMSON_STEMS),
            Map.entry(BlockTags.WARPED_STEMS, ItemTags.WARPED_STEMS),
            Map.entry(BlockTags.WART_BLOCKS, ItemTags.WART_BLOCKS),
            Map.entry(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN),
            Map.entry(BlockTags.LOGS, ItemTags.LOGS),
            Map.entry(BlockTags.SAND, ItemTags.SAND),
            Map.entry(BlockTags.SMELTS_TO_GLASS, ItemTags.SMELTS_TO_GLASS),
            Map.entry(BlockTags.SLABS, ItemTags.SLABS),
            Map.entry(BlockTags.WALLS, ItemTags.WALLS),
            Map.entry(BlockTags.STAIRS, ItemTags.STAIRS),
            Map.entry(BlockTags.ANVIL, ItemTags.ANVIL),
            Map.entry(BlockTags.RAILS, ItemTags.RAILS),
            Map.entry(BlockTags.LEAVES, ItemTags.LEAVES),
            Map.entry(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS),
            Map.entry(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS),
            Map.entry(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS),
            Map.entry(BlockTags.BEDS, ItemTags.BEDS),
            Map.entry(BlockTags.FENCES, ItemTags.FENCES),
            Map.entry(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS),
            Map.entry(BlockTags.FLOWERS, ItemTags.FLOWERS),
            Map.entry(BlockTags.SOUL_FIRE_BASE_BLOCKS, ItemTags.SOUL_FIRE_BASE_BLOCKS),
            Map.entry(BlockTags.CANDLES, ItemTags.CANDLES),
            Map.entry(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS),
            Map.entry(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES),
            Map.entry(BlockTags.IRON_ORES, ItemTags.IRON_ORES),
            Map.entry(BlockTags.DIAMOND_ORES, ItemTags.DIAMOND_ORES),
            Map.entry(BlockTags.REDSTONE_ORES, ItemTags.REDSTONE_ORES),
            Map.entry(BlockTags.LAPIS_ORES, ItemTags.LAPIS_ORES),
            Map.entry(BlockTags.COAL_ORES, ItemTags.COAL_ORES),
            Map.entry(BlockTags.EMERALD_ORES, ItemTags.EMERALD_ORES),
            Map.entry(BlockTags.COPPER_ORES, ItemTags.COPPER_ORES),
            Map.entry(BlockTags.DIRT, ItemTags.DIRT),
            Map.entry(BlockTags.TERRACOTTA, ItemTags.TERRACOTTA),
            Map.entry(BlockTags.COMPLETES_FIND_TREE_TUTORIAL, ItemTags.COMPLETES_FIND_TREE_TUTORIAL)
    );

    protected final List<TagKey<Block>> commonBlockTagsUsed;

    public TesseraItemTagsSystem(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, TesseraBlockTagsSystem blockTagsSystem, String modId, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, blockTagsSystem.contentsGetter(), modId, existingFileHelper);
        this.commonBlockTagsUsed = blockTagsSystem.commonTagsUsed;
    }

    @Override
    public IntrinsicTagAppender<Item> tag(TagKey<Item> pTag) {
        return super.tag(pTag);
    }

    public void addTagsFromBlockProperties(Set<Supplier<Block>> blocks) {
        var blockList = TesseraBlockTagsSystem.sorted(blocks);
        for (Block block : blockList) {
            var item = block.asItem();
            if (item.equals(Items.AIR)) {
                continue;
            }
            if (!(block.properties() instanceof TesseraBlockProperties tesseraProperties)) continue;
            var data = tesseraProperties.getDatagenData();
            for (TagKey<Block> tag : data.getTags()) {
                var itemTag = TagKey.create(Registries.ITEM, tag.location());
                tag(itemTag).add(item);
            }
        }
    }

    public void addCopies() {
        for (var blockTag : commonBlockTagsUsed) {
            var itemTag = BLOCK_TO_ITEM_MAPPING.get(blockTag);
            if (itemTag == null) continue;
            this.copy(blockTag, itemTag);
        }
    }
}