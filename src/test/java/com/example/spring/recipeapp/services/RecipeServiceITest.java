package com.example.spring.recipeapp.services;

import com.example.spring.recipeapp.commands.RecipeCommand;
import com.example.spring.recipeapp.converters.RecipeToRecipeCommand;
import com.example.spring.recipeapp.domain.Recipe;
import com.example.spring.recipeapp.repositories.reactive.RecipeReactiveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceITest {
    private static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    private RecipeServiceImpl recipeService;

    @Autowired
    private RecipeReactiveRepository recipeReactiveRepository;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    public void testSaveOfDescription() throws Exception {
        //given
        Iterable<Recipe> recipes = recipeReactiveRepository.findAll().toIterable();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand).block();

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
