package com.mosaicmodding.tessera.datagen.providers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Supplier;

public class TesseraDefaultLangProvider extends LanguageProvider {

    public TesseraDefaultLangProvider(PackOutput output, String modid) {
        super(output, modid, "en_us");
    }

    @Override
    protected void addTranslations() {

    }

    protected <I extends Item> void forItems(List<Supplier<I>> items) {
        items.forEach(item -> {
            String translation = toTitleCase(BuiltInRegistries.ITEM.getKey(item.get()).getPath());
            add(item.get(), translation);
        });
    }

    protected <I extends Block> void forBlocks(List<Supplier<I>> blocks) {
        blocks.forEach(block -> {
            String translation = toTitleCase(BuiltInRegistries.BLOCK.getKey(block.get()).getPath());
            add(block.get(), translation);
        });
    }

    protected <I extends EntityType<?>> void forEntities(List<Supplier<I>> entities) {
        entities.forEach(e -> {
            String translation = toTitleCase(BuiltInRegistries.ENTITY_TYPE.getKey(e.get()).getPath());
            add(e.get(), translation);
        });
    }

    protected static String toTitleCase(String path) {
        final StringBuilder builder = new StringBuilder();

        for (String part : path.split("_")) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return builder.toString();
    }
}
