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

export default function LinkedFieldsCellEditing({ handleActiveTable }) {
  const [activeRecipe, setActiveRecipe] = useState(null);
  const editingRow = React.useRef(null);
  const [rows, setRows] = React.useState(initialRows);

  React.useEffect(() => {
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
      headerName: "name",
      width: 160,
      editable: false,
      align: "left",
      headerAlign: "left",
    },
    {
      field: "calories",
      headerName: "calories",
      type: "number",
      width: 120,
      editable: false,
      align: "left",
      headerAlign: "left",
    },
    {
      field: "protein",
      headerName: "protein",
      type: "number",
      width: 120,
      editable: false,
      align: "left",
      headerAlign: "left",
    },
    {
      field: "carbs",
      headerName: "carbs",
      type: "number",
      width: 120,
      editable: false,
      align: "left",
      headerAlign: "left",
    },
    {
      field: "fat",
      headerName: "fat",
      type: "number",
      width: 120,
      editable: false,
      align: "left",
      headerAlign: "left",
    },
    {
      field: "servings",
      headerName: "servings",
      type: "number",
      width: 120,
      editable: false,
      align: "left",
      headerAlign: "left",
    },
  ];

  const handleRowClick = (params) => {
    setActiveRecipe(params.row);
    console.log("Active Recipe:", params.row);
    handleActiveTable(true);
  };

  return (
    <Paper sx={{ height: 400, width: "100%" }}>
      <Box sx={{ height: 400, width: "100%" }}>
        <DataGrid onRowClick={handleRowClick} rows={rows} columns={columns} />
      </Box>
    </Paper>
  );
}
