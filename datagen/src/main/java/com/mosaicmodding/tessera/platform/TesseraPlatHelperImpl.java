package com.mosaicmodding.tessera.platform;

import net.neoforged.neoforge.data.loading.DatagenModLoader;

public class TesseraPlatHelperImpl implements TesseraPlatHelper {

    @Override
    public boolean isDuringDatagen() {
        return DatagenModLoader.isRunningDataGen();
    }
}
