package com.mosaicmodding.tessera.datagen.providers.block;

import com.google.gson.JsonObject;
import com.mosaicmodding.tessera.datagen.DatagenSystemCommons;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

@SuppressWarnings("NullableProblems")
public class TesseraBlockModelBuilder extends BlockModelBuilder {

    public TesseraBlockModelBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
        super(outputLocation, existingFileHelper);
    }

    @Override
    public BlockModelBuilder texture(String key, ResourceLocation path) {
        var modified = DatagenSystemCommons.modifyTexturePath(path);
        DatagenSystemCommons.writeBlockTextureFromBlockModel(key, modified);
        return super.texture(key, modified);
    }

    @Override
    public BlockModelBuilder parent(ModelFile parent) {
        var location = parent.getLocation();
        var modified = DatagenSystemCommons.modifyModelParentPath(location);
        return super.parent(new UncheckedModelFile(modified));
    }

    @Override
    public JsonObject toJson() {

        return super.toJson();
    }
}
