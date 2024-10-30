package com.teammoeg.frostedheart.content.nutrition.recipe;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public class NutritionRecipe implements Recipe<Inventory> {
    public final float fat,carbohydrate,protein,vegetable;
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;

    public static RegistryObject<RecipeSerializer<NutritionRecipe>> SERIALIZER;
    public static RegistryObject<RecipeType<NutritionRecipe>> TYPE;




    public static class Serializer implements RecipeSerializer<NutritionRecipe> {


        @Override
        public NutritionRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(GsonHelper.getAsString(json, "item", "")));
            float fat = GsonHelper.getAsFloat(json, "fat");
            float carbohydrate = GsonHelper.getAsFloat(json, "carbohydrate");
            float protein = GsonHelper.getAsFloat(json, "protein");
            float vegetable = GsonHelper.getAsFloat(json, "vegetable");
            return new NutritionRecipe(recipeId, group,fat,carbohydrate,protein,vegetable,Ingredient.of(item));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, NutritionRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeFloat(recipe.fat);
            buffer.writeFloat(recipe.carbohydrate);
            buffer.writeFloat(recipe.protein);
            buffer.writeFloat(recipe.vegetable);
        }

        @javax.annotation.Nullable
        @Override
        public NutritionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf byteBuf) {
            String group = byteBuf.readUtf();
            Ingredient ingredient = Ingredient.fromNetwork(byteBuf);
            float fat = byteBuf.readFloat();
            float carbohydrate = byteBuf.readFloat();
            float protein = byteBuf.readFloat();
            float vegetable = byteBuf.readFloat();
            return new NutritionRecipe(recipeId, group,fat,carbohydrate,protein,vegetable,  ingredient);
        }

    }

    public NutritionRecipe(ResourceLocation id, String group, float fat, float carbohydrate, float protein, float vegetable, Ingredient ingredient) {
        super();
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.vegetable = vegetable;
        this.id = id;
        this.group = group;
        this.ingredient = ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }


    public boolean conform(ItemStack conformStack) {
        return ingredient.test(conformStack);
    }

    @Override
    public boolean matches(Inventory iInventory, Level world) {
        return false;
    }

    @Override
    public ItemStack assemble(Inventory inventory, RegistryAccess registryAccess) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE.get();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public static NutritionRecipe getRecipeFromItem(Level world, ItemStack itemStack) {
        List<NutritionRecipe> list = new ArrayList<>();
        if (world != null) {
            list.addAll(world.getRecipeManager().getAllRecipesFor(TYPE.get()));
        }
        for (NutritionRecipe recipe : list) {
            if (recipe.conform(itemStack)) {
                return recipe;
            }
        }
        return null;
    }
}
