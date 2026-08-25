package com.mosaicmodding.tessera.datagen.smith.blockstate;

import com.mosaicmodding.tessera.datagen.ItemModelSmithTypes;
import com.mosaicmodding.tessera.datagen.providers.block.TesseraBlockStateSystem;
import com.mosaicmodding.tessera.datagen.smith.itemmodel.ItemModelSmith;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class BlockStateSmith<T extends Block> extends AbstractBlockStateSmith<T> {

    protected final SmithStateSupplier<T> stateSupplier;
    protected final ItemModelSmith defaultItemModelSmith;

    public BlockStateSmith(Class<T> blockClass, SmithStateSupplier<T> stateSupplier) {
        this(blockClass, ItemModelSmithTypes.BLOCK_MODEL_ITEM, stateSupplier);
    }

    public BlockStateSmith(Class<T> blockClass, ItemModelSmith defaultItemModelSmith, SmithStateSupplier<T> stateSupplier) {
        super(blockClass);
        this.stateSupplier = stateSupplier;
        this.defaultItemModelSmith = defaultItemModelSmith;
    }

    @SafeVarargs
    public final void act(BlockStateSystemData<?> data, Supplier<? extends Block>... blocks) {
        act(data, List.of(blocks));
    }

    @SafeVarargs
    public final void act(BlockStateSystemData<?> data, ItemModelSmith smith, Supplier<? extends Block>... blocks) {
        act(data, smith, List.of(blocks));
    }

    public void act(BlockStateSystemData<?> data, Collection<Supplier<? extends Block>> blocks) {
        act(data, defaultItemModelSmith, blocks);
    }

    public void act(BlockStateSystemData<?> data, ItemModelSmith smith, Collection<Supplier<? extends Block>> blocks) {
        for (Supplier<? extends Block> block : blocks) {
            tryAct(data, smith, block, stateSupplier::act);
        }
    }

    public interface SmithStateSupplier<T extends Block> {
        void act(T block, TesseraBlockStateSystem provider);
    }
}