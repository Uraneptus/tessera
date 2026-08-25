package com.mosaicmodding.tessera.datagen.smith.blockstate;

import com.mosaicmodding.tessera.datagen.providers.block.TesseraBlockStateSystem;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record BlockStateSystemData<T extends TesseraBlockStateSystem>(T provider, Consumer<Supplier<? extends Block>> consumer) {
}
