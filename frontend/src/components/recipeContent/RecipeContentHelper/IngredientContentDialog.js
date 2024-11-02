import * as React from "react";
import { useState, useCallback, useEffect } from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemText from "@mui/material/ListItemText";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import FolderIcon from "@mui/icons-material/Folder";
import AddIcon from "@mui/icons-material/Add";
import Typography from "@mui/material/Typography";
import { searchFoodItems } from "../../../helpers/axios_helper";
import _ from "lodash";

export default function IngredientContentDialog({
  open,
  handleClose,
  onAddIngredientToRecipe,
}) {
  const [searchInput, setSearchInput] = useState("");
  const [ingredientSearchResult, setIngredientSearchResult] = useState([]);

  const debouncedSearch = useCallback(
    _.debounce(async (value) => {
      console.log(`Triggering API call with search value: ${value}`);
      try {
        const result = await searchFoodItems(value);
        console.log(result.data);
        setIngredientSearchResult(result.data);
      } catch (error) {
        console.error("Error fetching ingredients:", error);
      }
    }, 1000), // ms debounce time
    []
  );

  useEffect(() => {
    if (!open) {
      debouncedSearch.cancel(); // Cancel the debounce call when the dialog closes
    }
  }, [open, debouncedSearch]);

  const handleInputChange = (event) => {
    const value = event.target.value;
    setSearchInput(value);
    if (open) {
      debouncedSearch(value);
    }
  };

  function onCancel() {
    handleClose();
    setSearchInput("");
  }

  return (
    <React.Fragment>
      <Dialog
        open={open}
        onClose={handleClose}
        PaperProps={{
          component: "form",
          onSubmit: (event) => {
            event.preventDefault();
            handleClose();
          },
        }}
      >
        <DialogTitle>Add ingredient</DialogTitle>
        <DialogContent>
          <DialogContentText>
            To add ingredients to this recipe, write in your search value in the
            search bar below:
          </DialogContentText>
          <TextField
            autoFocus
            required
            margin="dense"
            id="name"
            name="searchValue"
            label="Ingredients"
            type="text"
            fullWidth
            variant="standard"
            value={searchInput}
            onChange={handleInputChange}
          />

          <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
            Ingredients List
          </Typography>
          <List dense>
            {ingredientSearchResult.map((item) => (
              <ListItem
                key={item.id}
                secondaryAction={
                  <IconButton
                    edge="end"
                    aria-label="delete"
                    onClick={() => onAddIngredientToRecipe(item)}
                  >
                    <AddIcon />
                  </IconButton>
                }
              >
                <ListItemAvatar>
                  <Avatar>
                    <FolderIcon />
                  </Avatar>
                </ListItemAvatar>
                <ListItemText
                  primary={item.name}
                  secondary={
                    <>
                      <Typography variant="body2" color="textSecondary">
                        {`${item.nutrientList[0].name}: ${item.nutrientList[0].value} ${item.nutrientList[0].enhet} (per 100g)`}
                      </Typography>
                      <Typography variant="body2" color="textSecondary">
                        {`${item.nutrientList[1].name}: ${item.nutrientList[1].value} ${item.nutrientList[1].enhet} (per 100g)`}
                      </Typography>
                      <Typography variant="body2" color="textSecondary">
                        {`${item.nutrientList[2].name}: ${item.nutrientList[2].value} ${item.nutrientList[2].enhet} (per 100g)`}
                      </Typography>
                    </>
                  }
                />
              </ListItem>
            ))}
          </List>
        </DialogContent>
        <DialogActions>
          <Button onClick={onCancel}>Cancel</Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}
