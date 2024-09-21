import Box from "@mui/material/Box";
import React, { useState, useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import Paper from "@mui/material/Paper";
import { getAllRecipes } from "../../helpers/axios_helper";

export default function RecipeList({ onShowRecipe }) {
  const [rows, setRows] = useState([]);

  // Fetch recipes when the component is mounted
  useEffect(() => {
    getAllRecipes()
      .then((response) => {
        console.log(response.data);
        const fetchedRecipes = response.data.map((recipe) => ({
          id: recipe.id,
          name: recipe.name,
          calories: recipe.caloriesInRecipe,
          protein: recipe.proteinInRecipe,
          carbs: recipe.carbsInRecipe,
          fat: recipe.fatInRecipe,
          created: recipe.created,
          ingredients: recipe.ingredients,
        }));

        setRows(fetchedRecipes);
      })
      .catch((error) => {
        console.error("Error fetching recipes:", error);
      });
  }, []);

  const columns = [
    {
      field: "name",
      headerName: "Name",
      width: 160,
    },
    {
      field: "calories",
      headerName: "Calories",
      width: 120,
    },
    {
      field: "protein",
      headerName: "Protein",
      width: 120,
    },
    {
      field: "carbs",
      headerName: "Carbs",
      width: 120,
    },
    {
      field: "fat",
      headerName: "Fat",
      width: 120,
    },
    {
      field: "servings",
      headerName: "Servings",
      width: 120,
    },
  ];

  const handleRowClick = (params) => {
    console.log("Params.row: ", params.row);
    onShowRecipe(params.row);
  };

  return (
    <Paper sx={{ height: 400, width: "100%" }}>
      <Box sx={{ height: 400, width: "100%" }}>
        <DataGrid onRowClick={handleRowClick} rows={rows} columns={columns} />
      </Box>
    </Paper>
  );
}
