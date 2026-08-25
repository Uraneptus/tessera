package com.mosaicmodding.tessera.datagen.smith.blockstate;

import com.mosaicmodding.tessera.datagen.DatagenSystemCommons;
import com.mosaicmodding.tessera.datagen.providers.block.TesseraBlockStateSystem;
import com.mosaicmodding.tessera.datagen.smith.itemmodel.EmptyItemModelSmith;
import com.mosaicmodding.tessera.datagen.smith.itemmodel.ItemModelSmith;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class AbstractBlockStateSmith<T extends Block> {

    public final Class<T> blockClass;

    public AbstractBlockStateSmith(Class<T> blockClass) {
        this.blockClass = blockClass;
    }

    protected final void tryAct(BlockStateSystemData<?> data, ItemModelSmith itemModelSmith, Supplier<? extends Block> registryObject, BiConsumer<T, TesseraBlockStateSystem> actor) {
        var block = registryObject.get();
        if (blockClass.isInstance(block)) {
            DatagenSystemCommons.CURRENT_BLOCK = block;
            var provider = data.provider();
            actor.accept(blockClass.cast(block), provider);
            makeItemModel(data, itemModelSmith, block);
            data.consumer().accept(registryObject);
            DatagenSystemCommons.clearCachedBlockTextures();
            DatagenSystemCommons.CURRENT_BLOCK = null;
        } else {
            throw new IllegalArgumentException("Block does not match the state smith it was assigned: " + block.toString());
        }
    }

    protected final void makeItemModel(BlockStateSystemData<?> data, ItemModelSmith itemModelSmith, Block block) {
        if (itemModelSmith instanceof EmptyItemModelSmith) {
            //TODO: The .act() call here should go through the item model system data thing
            return;
        }
        itemModelSmith.act(data.provider().itemModelProvider, block.asItem());
    }

    public interface StateFunction<T extends Block> {
        void act(T block, ModelFile modelFile);
    }
}