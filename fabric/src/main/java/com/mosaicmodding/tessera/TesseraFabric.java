package com.mosaicmodding.tessera;

import net.fabricmc.api.ModInitializer;

public class TesseraFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        TesseraCore.init();
    }
}
