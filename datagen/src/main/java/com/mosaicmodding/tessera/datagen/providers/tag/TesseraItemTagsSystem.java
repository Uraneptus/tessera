package com.mosaicmodding.tessera.datagen.providers.tag;

import com.mosaicmodding.tessera.TesseraBlockProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class TesseraItemTagsSystem extends ItemTagsProvider {

    public TesseraItemTagsSystem(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
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
}