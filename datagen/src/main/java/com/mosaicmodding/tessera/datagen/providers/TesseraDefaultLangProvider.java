package com.mosaicmodding.tessera.datagen.providers;

import com.mosaicmodding.tessera.TesseraDatagen;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.*;

public class TesseraDefaultLangProvider extends LanguageProvider {

    public TesseraDefaultLangProvider(PackOutput output, String modid) {
        super(output, modid, "en_us");
    }

    @Override
    protected void addTranslations() {

    }

    protected void forItems(List<Supplier<Item>> items, @Nullable Function<String, String> additionalStringProcessor) {
        forRegistry(items, BuiltInRegistries.ITEM, additionalStringProcessor, this::add);
    }

    protected void forItems(List<Supplier<Item>> items) {
        forRegistry(items, BuiltInRegistries.ITEM, null, this::add);
    }

    protected void forBlocks(List<Supplier<Block>> blocks, @Nullable Function<String, String> additionalStringProcessor) {
        forRegistry(blocks, BuiltInRegistries.BLOCK, additionalStringProcessor, this::add);
    }

    protected void forBlocks(List<Supplier<Block>> blocks) {
        forRegistry(blocks, BuiltInRegistries.BLOCK, null, this::add);
    }

    protected void forEntities(List<Supplier<EntityType<?>>> entities, @Nullable Function<String, String> additionalStringProcessor) {
        forRegistry(entities, BuiltInRegistries.ENTITY_TYPE, additionalStringProcessor, this::add);
    }

    protected void forEntities(List<Supplier<EntityType<?>>> entities) {
        forRegistry(entities, BuiltInRegistries.ENTITY_TYPE, null, this::add);
    }

    protected void forMobEffects(List<Supplier<MobEffect>> effects, @Nullable Function<String, String> additionalStringProcessor) {
        forRegistry(effects, BuiltInRegistries.MOB_EFFECT, additionalStringProcessor, this::add);
    }

    protected void forMobEffects(List<Supplier<MobEffect>> effects) {
        forRegistry(effects, BuiltInRegistries.MOB_EFFECT, null, this::add);
    }

    //This is an example for adding other registry translation gen
    // protected void forOther(List<Supplier<SoundEvent>> sounds) {
    //    forRegistry(sounds, BuiltInRegistries.SOUND_EVENT, null, (sound, translation) -> this.add(sound.getLocation().toLanguageKey(), translation));
    // }

    protected <T> void forRegistry(List<Supplier<T>> entries, Registry<T> registry, @Nullable Function<String, String> additionalStringProcessor, BiConsumer<T, String> translationConsumer) {
        for (Supplier<T> entry : entries) {
            var supplied = entry.get();
            var key = registry.getKey(supplied);
            if (key == null) {
                TesseraDatagen.DATAGEN_LOGGER.error("Registry key for {} of registry {} is null. Translation was not generated!", supplied, registry);
                continue;
            }
            var translation = toTitleCase(key.getPath());
            if (additionalStringProcessor != null) {
                translation = additionalStringProcessor.apply(translation);
            }
            translationConsumer.accept(supplied, translation);
        }
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
