package com.mosaicmodding.tessera.datagen.smith.itemmodel.data;

import com.mosaicmodding.tessera.datagen.providers.item.TesseraItemModelSystem;
import com.mosaicmodding.tessera.datagen.smith.itemmodel.EmptyItemModelSmith;
import com.mosaicmodding.tessera.datagen.smith.itemmodel.ItemModelSmith;
import com.mosaicmodding.tessera.datagen.smith.itemmodel.ItemModelSmithResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemModelSystemData {
    private final TesseraItemModelSystem provider;
    private final Set<Item> items;

    public ItemModelSystemData(TesseraItemModelSystem provider, DeferredRegister.Items items) {
        this(provider, items.getEntries());
    }

    public ItemModelSystemData(TesseraItemModelSystem provider, Collection<? extends Supplier<? extends Item>> items) {
        this.provider = provider;
        this.items = items.stream().map(Supplier::get).filter(i -> !(i instanceof BlockItem)).collect(Collectors.toSet());
    }

    public TesseraItemModelSystem provider() {
        return provider;
    }

    public Set<Item> items() {
        return items;
    }

    public DatagenItemQuery ofClass(Class<? extends Item> clazz) {
        return new DatagenItemQuery(items.stream().filter(clazz::isInstance));
    }

    public DatagenItemQuery allRemaining() {
        return new DatagenItemQuery(items.stream());
    }

    public Optional<ItemModelSmithResult> approveAct(ItemModelSmith smith, Item item) {
        if (smith instanceof EmptyItemModelSmith) {
            return Optional.empty();
        }
        var result = smith.act(provider, item);
        items.remove(item);
        return Optional.of(result);
    }
}