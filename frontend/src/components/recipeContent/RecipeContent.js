import Box from "@mui/material/Box";
import React, { useState, useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import Paper from "@mui/material/Paper";

export default function DataTable({ onActiveRecipe }) {
  const [rows, setRows] = useState([]);

  const extractNutrient = (nutrientList, nutrientName) => {
    const nutrient = nutrientList.find((n) => n.name === nutrientName);
    return nutrient ? nutrient.value : 0; // Default to 0 if not found
  };

  useEffect(() => {
    if (onActiveRecipe) {
      const formattedRows = onActiveRecipe.map((recipe) => {
        const calories = extractNutrient(recipe.nutrientList, "Energy (kcal)");
        const protein = extractNutrient(recipe.nutrientList, "Protein");
        const carbs = extractNutrient(
          recipe.nutrientList,
          "Carbohydrates, available"
        );
        const fat = extractNutrient(recipe.nutrientList, "Fat, total");

        return {
          id: recipe.id,
          name: recipe.name,
          calories: calories,
          protein: protein,
          carbs: carbs,
          fat: fat,
        };
      });

      setRows(formattedRows);
    }
  }, [onActiveRecipe]);

  const columns = [
    { field: "id", headerName: "ID", width: 70 },
    { field: "name", headerName: "Name", width: 130 },
    { field: "calories", headerName: "Calories", type: "number", width: 70 },
    { field: "protein", headerName: "Protein", type: "number", width: 70 },
    { field: "carbs", headerName: "Carbohydrates", type: "number", width: 70 },
    { field: "fat", headerName: "Fat", type: "number", width: 70 },
  ];

  return (
    <Paper sx={{ height: 400, width: "100%" }}>
      <Box sx={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={rows}
          columns={columns}
          pageSize={5}
          checkboxSelection
          sx={{ border: 0 }}
        />
      </Box>
    </Paper>
  );
}
