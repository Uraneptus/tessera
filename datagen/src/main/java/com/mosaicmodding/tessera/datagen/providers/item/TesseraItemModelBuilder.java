package com.mosaicmodding.tessera.datagen.providers.item;

import com.mosaicmodding.tessera.datagen.DatagenSystemCommons;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

@SuppressWarnings("NullableProblems")
public class TesseraItemModelBuilder extends ItemModelBuilder {

    public final TesseraItemModelSystem provider;

    public TesseraItemModelBuilder(TesseraItemModelSystem provider, ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
        super(outputLocation, existingFileHelper);
        this.provider = provider;
    }

    @Override
    public ItemModelBuilder texture(String key, ResourceLocation path) {
        var modified = DatagenSystemCommons.modifyTexturePath(path);
        return super.texture(key, modified);
    }

    @Override
    public ItemModelBuilder parent(ModelFile parent) {
        var location = parent.getLocation();
        var modified = DatagenSystemCommons.modifyModelParentPath(location);
        return super.parent(new UncheckedModelFile(modified));

    }
}
