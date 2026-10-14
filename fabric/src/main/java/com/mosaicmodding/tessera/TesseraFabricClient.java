package com.mosaicmodding.tessera;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;

public class TesseraFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BuiltInRegistries.BLOCK.forEach(b -> {
            if (b.properties() instanceof TesseraBlockProperties tesseraProperties) {
                BlockRenderLayerMap.INSTANCE.putBlock(b, parseRenderType(tesseraProperties.blockRenderType));
            }
        });
    }

    private RenderType parseRenderType(TesseraBlockProperties.BlockRenderType type) {
        return switch (type) {
            case CUTOUT -> RenderType.cutout();
            case CUTOUT_MIPPED, CUTOUT_MIPPED_ALL -> RenderType.cutoutMipped();
            case TRANSLUCENT -> RenderType.translucent();
            case TRIPWIRE -> RenderType.tripwire();
            default -> RenderType.solid();
        };
    }
}
