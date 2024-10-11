import * as React from "react";
import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";
import FormControlLabel from "@mui/material/FormControlLabel";
import Switch from "@mui/material/Switch";
import Fab from "@mui/material/Fab";
import AddIcon from "@mui/icons-material/Add";
import TablePagination from "@mui/material/TablePagination";
import EnhancedTable from "./RecipeContentHelper/EnhancedTable";
import { editRecipe } from "../../helpers/axios_helper";

export default function RecipeContent({ onActiveRecipe }) {
  const [dense, setDense] = React.useState(false);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [page, setPage] = React.useState(0);

  const handleChangeDense = (event) => {
    setDense(event.target.checked);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0); // Reset to the first page
  };

  function onEditRecipe(id, name, ingredients) {
    editRecipe(id, name, ingredients)
      .then((response) => {
        console.log("Recipe updated:", response.data);
      })
      .catch((error) => {
        console.error("Error updating recipe:", error);
      });
  }

  return (
    <Box sx={{ width: "100%" }}>
      <Paper sx={{ width: "100%", mb: 2 }}>
        <EnhancedTable
          onActiveRecipe={onActiveRecipe}
          onEditRecipe={onEditRecipe}
          dense={dense}
        />
      </Paper>
    </Box>
  );
}
