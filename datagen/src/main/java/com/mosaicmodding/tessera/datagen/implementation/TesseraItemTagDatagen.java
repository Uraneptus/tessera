package com.mosaicmodding.tessera.datagen.implementation;

import com.mosaicmodding.tessera.TesseraCore;
import com.mosaicmodding.tessera.datagen.providers.tag.TesseraItemTagsSystem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;


@SuppressWarnings("NullableProblems")
public class TesseraItemTagDatagen extends TesseraItemTagsSystem {
    public TesseraItemTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockProvider, ExistingFileHelper existingFileHelper) {
        super(output, provider, blockProvider, TesseraCore.ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Lodestone Item Tags";
    }

    @SuppressWarnings({"unchecked"})
    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}