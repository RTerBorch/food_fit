import Box from "@mui/material/Box";
import React, { useState, useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import Paper from "@mui/material/Paper";

function randomPrice(min = 0, max = 1000) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

const initialRows = [
  {
    id: 1,
    name: "Bolognese",
    calories: randomPrice(1, 1000),
    protein: randomPrice(1, 40),
    carbs: randomPrice(1, 40),
    fat: randomPrice(1, 40),
    servings: randomPrice(1, 5),
  },
];

export default function RecipeList({ onShowRecipe }) {
  const [rows, setRows] = useState(initialRows);

  useEffect(() => {
    const updatedRows = rows.map((row) => ({
      ...row,
      protein: `${row.protein} (${(row.protein / row.servings).toFixed(2)})`,
      carbs: `${row.carbs} (${(row.carbs / row.servings).toFixed(2)})`,
      fat: `${row.fat} (${(row.fat / row.servings).toFixed(2)})`,
      calories: `${row.calories} (${(row.calories / row.servings).toFixed(2)})`,
    }));
    setRows(updatedRows);
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
