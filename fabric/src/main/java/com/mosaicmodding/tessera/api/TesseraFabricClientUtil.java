package com.mosaicmodding.tessera.api;

import com.mosaicmodding.tessera.TesseraBlockProperties;
import net.minecraft.client.renderer.RenderType;

public class TesseraFabricClientUtil {

    public static RenderType parseRenderType(TesseraBlockProperties.BlockRenderType type) {
        return switch (type) {
            case CUTOUT -> RenderType.cutout();
            case CUTOUT_MIPPED, CUTOUT_MIPPED_ALL -> RenderType.cutoutMipped();
            case TRANSLUCENT -> RenderType.translucent();
            case TRIPWIRE -> RenderType.tripwire();
            default -> RenderType.solid();
        };
    }
}
