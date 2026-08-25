package com.mosaicmodding.tessera;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TesseraCore.ID)
public class TesseraNeo {

    public TesseraNeo(IEventBus bus) {
        TesseraCore.init();
    }
}
