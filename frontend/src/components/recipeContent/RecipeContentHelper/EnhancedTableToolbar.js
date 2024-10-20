import * as React from "react";
import PropTypes from "prop-types";
import { alpha } from "@mui/material/styles";
import { Toolbar, Typography, IconButton, Tooltip } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import FilterListIcon from "@mui/icons-material/FilterList";
import Button from "@mui/material/Button";
import Fab from "@mui/material/Fab";
import AddIcon from "@mui/icons-material/Add";
import NavigationIcon from "@mui/icons-material/Navigation";
import EditIcon from "@mui/icons-material/Edit";

export default function EnhancedTableToolbar({
  numSelected,
  onEditRecipe,
  onActiveRecipe,
  openAddIngredientDialog,
}) {
  function openNewIngredientsWindow() {
    openAddIngredientDialog(true);
  }

  function editIngredientsInRecipe() {
    onEditRecipe(
      onActiveRecipe.id,
      onActiveRecipe.name,
      onActiveRecipe.ingredients
    );
  }

  return (
    <Toolbar
      sx={[
        {
          pl: { sm: 2 },
          pr: { xs: 1, sm: 1 },
        },
        numSelected > 0 && {
          bgcolor: (theme) =>
            alpha(
              theme.palette.primary.main,
              theme.palette.action.activatedOpacity
            ),
        },
      ]}
    >
      {numSelected > 0 ? (
        <Typography
          sx={{ flex: "1 1 100%" }}
          color="inherit"
          variant="subtitle1"
          component="div"
        >
          {numSelected} selected
        </Typography>
      ) : (
        <Typography
          sx={{ flex: "1 1 100%" }}
          variant="h6"
          id="tableTitle"
          component="div"
        >
          Nutrition
          <EditIcon />
        </Typography>
      )}
      {numSelected > 0 ? (
        <Tooltip title="Delete">
          <IconButton>
            <DeleteIcon />
          </IconButton>
        </Tooltip>
      ) : (
        <Tooltip title="add ingredients">
          <Fab size="small" color="primary">
            <AddIcon onClick={() => openNewIngredientsWindow()}></AddIcon>
          </Fab>
        </Tooltip>
      )}
    </Toolbar>
  );
}

EnhancedTableToolbar.propTypes = {
  numSelected: PropTypes.number.isRequired,
};
