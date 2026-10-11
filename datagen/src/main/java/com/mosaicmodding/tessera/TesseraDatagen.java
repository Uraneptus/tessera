package com.mosaicmodding.tessera;

import com.mosaicmodding.tessera.datagen.implementation.TesseraBlockTagDatagen;
import com.mosaicmodding.tessera.datagen.implementation.TesseraItemTagDatagen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TesseraDatagen.ID)
public class TesseraDatagen {
    public static final String ID = "tessera";
    public static final String NAME = "Tessera Datagen";
    public static final Logger DATAGEN_LOGGER = LoggerFactory.getLogger(NAME);

    public TesseraDatagen(IEventBus bus) {
        bus.addListener(this::gatherData);

        TesseraCore.init();
    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {

        var lookupProvider = event.getLookupProvider();
        var packOutput = event.getGenerator().getPackOutput();
        var existingFileHelper = event.getExistingFileHelper();

        boolean includeServer = event.includeServer();

        var blockTagDatagen = new TesseraBlockTagDatagen(packOutput, lookupProvider, existingFileHelper);
        var itemTagDatagen = new TesseraItemTagDatagen(packOutput, lookupProvider, blockTagDatagen.contentsGetter(), existingFileHelper);

        event.getGenerator().addProvider(includeServer, blockTagDatagen);
        event.getGenerator().addProvider(includeServer, itemTagDatagen);
    }
}
