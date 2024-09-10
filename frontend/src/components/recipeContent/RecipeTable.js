import * as React from "react";
import { DataGrid } from "@mui/x-data-grid";
import Paper from "@mui/material/Paper";

export default function DataTable({ onActiveRecipe }) {
  const columns = [
    { field: "id", headerName: "ID", width: 70 },
    { field: "name", headerName: "Name", width: 130 },
    { field: "calories", headerName: "Calories", type: "number", width: 70 },
    { field: "protein", headerName: "Protein", type: "number", width: 70 },
    { field: "carbs", headerName: "Carbohydrates", type: "number", width: 70 },
    { field: "fat", headerName: "Fat", type: "number", width: 70 },
    { field: "servings", headerName: "Servings", type: "number", width: 70 },
  ];

  const paginationModel = { page: 0, pageSize: 5 };

  return (
    <Paper sx={{ height: 400, width: "100%" }}>
      <DataGrid
        rows={onActiveRecipe}
        columns={columns}
        initialState={{ pagination: { paginationModel } }}
        pageSizeOptions={[5, 10]}
        checkboxSelection
        sx={{ border: 0 }}
      />
    </Paper>
  );
}
