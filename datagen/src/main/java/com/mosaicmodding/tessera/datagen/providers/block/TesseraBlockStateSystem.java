package com.mosaicmodding.tessera.datagen.providers.block;

import com.mosaicmodding.tessera.datagen.DatagenSystemCommons;
import com.mosaicmodding.tessera.datagen.IDatagenPathfinder;
import com.mosaicmodding.tessera.datagen.providers.item.TesseraItemModelSystem;
import com.mosaicmodding.tessera.datagen.smith.blockstate.ModularBlockStateSmith;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.BiFunction;

@SuppressWarnings({"unused", "NullableProblems"})
public abstract class TesseraBlockStateSystem extends BlockStateProvider implements IDatagenPathfinder {

    private final String modId;
    private final TesseraBlockModelProvider blockModels;
    public final TesseraItemModelSystem itemModelProvider;

    public TesseraBlockStateSystem(PackOutput output, String modId, ExistingFileHelper exFileHelper, TesseraItemModelSystem itemModelProvider) {
        super(output, modId, exFileHelper);
        this.modId = modId;
        this.itemModelProvider = itemModelProvider;
        this.blockModels = new TesseraBlockModelProvider(output, modId, exFileHelper);
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public String getFolder() {
        return "";
    }

    @Override
    public TesseraBlockModelProvider models() {
        return blockModels;
    }

    @Override
    public TesseraItemModelSystem itemModels() {
        return itemModelProvider;
    }

    public void setTexturePath(String folder) {
        DatagenSystemCommons.BLOCK_TEXTURE.setFolder(folder);
    }

    public ModularBlockStateSmith.ModelFileSupplier fromFunction(BiFunction<String, ResourceLocation, ModelFile> modelFileFunction) {
        return b -> {
            var name = getBlockName(b);
            return modelFileFunction.apply(name, getBlockTexture(name));
        };
    }

    public void varyingRotationBlock(Block block, ModelFile model) {
        ConfiguredModel.Builder<VariantBlockStateBuilder> builder = getVariantBuilder(block).partialState().modelForState()
                .modelFile(model)
                .nextModel().modelFile(model).rotationY(90)
                .nextModel().modelFile(model).rotationY(180)
                .nextModel().modelFile(model).rotationY(270);
        simpleBlock(block, builder.build());
    }
}