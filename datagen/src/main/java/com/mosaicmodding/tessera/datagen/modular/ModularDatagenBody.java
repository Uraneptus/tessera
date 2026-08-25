package com.mosaicmodding.tessera.datagen.modular;

import net.minecraft.resources.ResourceLocation;

public abstract class ModularDatagenBody {
    protected final ResourceLocation id;

    protected ModularDatagenBody(ResourceLocation id) {
        this.id = id;
    }

    public abstract String getDataLocation();

    public ResourceLocation getId() {
        return id;
    }
}
