package com.mosaicmodding.tessera.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class TesseraRecipeProvider extends RecipeProvider {
    private final String modId;

    public TesseraRecipeProvider(String modId, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
        this.modId = modId;
    }

    protected void planksFromLogsRecipe(TagKey<Item> pLogs, ItemLike result, RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .requires(pLogs)
                .unlockedBy("has_logs", has(pLogs)).save(recipeOutput);
    }

    protected void woodFromLogsRecipe(ItemLike pLog, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', pLog).pattern("##").pattern("##")
                .unlockedBy("has_log", has(pLog)).save(recipeOutput);
    }

    protected void fenceRecipe(ItemLike ingredient, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('W', ingredient).define('#', Items.STICK)
                .pattern("W#W").pattern("W#W")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

    protected void pressurePlateRecipe(ItemLike ingredient, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', ingredient).pattern("##")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

    protected void doorRecipe(ItemLike ingredient, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', ingredient)
                .pattern("##").pattern("##").pattern("##")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

    protected void trapdoorRecipe(ItemLike ingredient, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 2)
                .define('#', ingredient)
                .pattern("###").pattern("###")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

    protected void fenceGateRecipe(ItemLike ingredient, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', Items.STICK).define('W', ingredient)
                .pattern("#W#").pattern("#W#")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

    protected void signRecipe(ItemLike ingredient, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', ingredient).define('X', Items.STICK)
                .pattern("###").pattern("###").pattern(" X ")
                .unlockedBy(getHasName(ingredient), has(ingredient)).save(recipeOutput);
    }

    protected void hangingSignRecipe(ItemLike ingredient, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .define('#', ingredient).define('X', Items.CHAIN)
                .pattern("X X").pattern("###").pattern("###")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

    protected void convertRecipe(RecipeCategory pCategory, ItemLike input, ItemLike pResult, RecipeOutput recipeOutput) {
        convertRecipe(pCategory, input, pResult, 1, recipeOutput);
    }

    protected void convertRecipe(RecipeCategory pCategory, ItemLike input, ItemLike pResult, int resultCount, RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(pCategory, pResult, resultCount)
                .requires(input)
                .unlockedBy(getHasName(input), has(input))
                .save(recipeOutput, path(getItemName(pResult) + "_from_" + getItemName(input)));
    }

    protected void packableBlockRecipes(ItemLike unpacked, ItemLike packed, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, packed)
                .define('#', unpacked).pattern("###").pattern("###").pattern("###")
                .unlockedBy(getHasName(unpacked), has(unpacked))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, unpacked, 9)
                .requires(packed)
                .unlockedBy(getHasName(packed), has(packed))
                .save(recipeOutput);
    }

    protected void bricksLikeBlockRecipe(ItemLike ingredient, ItemLike result, RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .define('#', ingredient).pattern("##").pattern("##")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

    protected void smeltingRecipes(RecipeCategory category, ItemLike ingredient, ItemLike result, float experience, boolean isMetal, RecipeOutput recipeOutput) {
        String resultName = getItemName(result);
        String ingredientName = getItemName(ingredient);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), category, result, experience, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, path(resultName + "_from_smelting_" + ingredientName));

        if (isMetal) {
            SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), category, result, experience, 100)
                    .unlockedBy(getHasName(ingredient), has(ingredient))
                    .save(recipeOutput, path(resultName + "_from_blasting_" + ingredientName));
        }
    }

    protected void cookingRecipes(ItemLike ingredient, ItemLike result, float experience, RecipeOutput recipeOutput) {
        String resultName = getItemName(result);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 200)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput);

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 600)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, path(resultName + "_from_campfire_cooking"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 100)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, path(resultName + "_from_campfire_smoking"));
    }

    protected ResourceLocation path(String path) {
        return ResourceLocation.fromNamespaceAndPath(this.modId, path);
    }

    public String getModId() {
        return this.modId;
    }
}
