package com.mosaicmodding.tessera.datagen.smith.itemmodel;


import com.mosaicmodding.tessera.datagen.smith.itemmodel.data.DatagenItemQuery;
import com.mosaicmodding.tessera.datagen.smith.itemmodel.data.ItemModelSystemData;

import java.util.Collections;
import java.util.List;

public class EmptyItemModelSmith extends ItemModelSmith{
    public EmptyItemModelSmith() {
        super(null);
    }

    @Override
    public List<ItemModelSmithResult> act(ItemModelSystemData data, DatagenItemQuery queried) {
        return Collections.emptyList();
    }
}
