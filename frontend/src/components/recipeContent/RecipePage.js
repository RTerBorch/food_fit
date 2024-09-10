import RecipeList from "./RecipeList";
import RecipeTable from "./RecipeTable";
import Grid from "@mui/material/Grid2"; // Correct import for Grid2
import React, { useState, useEffect } from "react";
import { Button } from "@mui/material";

export default function RecipePage() {
  const [showRecipeActive, setShowRecipeActive] = useState(false);
  const [activeRecipe, setActiveRecipe] = useState(null);

  function showRecipe(recipe) {
    setActiveRecipe([recipe]);
    setShowRecipeActive(true);
  }

  return (
    <div>
      <Button
        variant="contained"
        color="primary"
        onClick={() => setShowRecipeActive(!showRecipeActive)}
      >
        Switch Table
      </Button>
      <Grid container spacing={1} sx={{ padding: 2, justifyContent: "center" }}>
        <Grid xs={12} md={6}>
          {!showRecipeActive ? (
            <RecipeList
              sx={{
                minHeight: 400,
                padding: 2,
                display: "flex",
                flexDirection: "column",
                flexGrow: 1,
              }}
              handleActiveTable={setShowRecipeActive} // TODO TA BORT
              onShowRecipe={showRecipe}
            />
          ) : (
            <RecipeTable
              onActiveRecipe={activeRecipe}
              sx={{
                minHeight: 400,
                padding: 2,
                display: "flex",
                flexDirection: "column",
                flexGrow: 1,
              }}
            />
          )}
        </Grid>
      </Grid>
    </div>
  );
}
