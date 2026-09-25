package com.mosaicmodding.tessera;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.core.registries.BuiltInRegistries;

public class TesseraFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BuiltInRegistries.BLOCK.forEach(b -> {
            if (b.properties() instanceof TesseraBlockProperties tesseraProperties) {
                BlockRenderLayerMap.INSTANCE.putBlock(b, tesseraProperties.blockRenderType.getActualType());
            }
        });
    }
}
