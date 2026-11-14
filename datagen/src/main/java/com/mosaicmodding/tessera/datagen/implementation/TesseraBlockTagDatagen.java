package com.mosaicmodding.tessera.datagen.implementation;

import com.mosaicmodding.tessera.TesseraCore;
import com.mosaicmodding.tessera.datagen.providers.tag.TesseraBlockTagsSystem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

@Deprecated(forRemoval = true)
public class TesseraBlockTagDatagen extends TesseraBlockTagsSystem {
    public TesseraBlockTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TesseraCore.ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Lodestone Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

    }
}