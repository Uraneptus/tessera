package com.mosaicmodding.tessera.datagen.modular;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

public abstract class ModularDatagenJsonBody<T> extends ModularDatagenBody {

    protected ModularDatagenJsonBody(ResourceLocation id) {
        super(id);
    }

    public JsonElement buildJson(RegistryOps<JsonElement> dynamicOps, T instance) {
        return getCodec().encodeStart(dynamicOps, instance).getOrThrow();
    }

    public abstract T build(ModularDatagenProvider datagen, RegistryOps<JsonElement> dynamicOps, HolderLookup.Provider provider);

    public abstract Codec<T> getCodec();
}
