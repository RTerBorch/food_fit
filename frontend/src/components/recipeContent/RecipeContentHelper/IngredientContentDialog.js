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
import _ from "lodash";

export default function IngredientContentDialog({ open, handleClose }) {
  const [searchValue, setSearchValue] = useState("");

  const debouncedSearch = useCallback(
    _.debounce((value) => {
      console.log(`Triggering API call with search value: ${value}`);
      // TODO API SEARCH CALL
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
    setSearchValue(value);
    if (open) {
      debouncedSearch(value);
    }
  };

  function onCancel() {
    handleClose();
    setSearchValue("");
  }

  // Demo objects for the list
  const demoItems = [
    { id: 1, primary: "Flour", secondary: "500g" },
    { id: 2, primary: "Sugar", secondary: "200g" },
    { id: 3, primary: "Butter", secondary: "100g" },
    { id: 4, primary: "Eggs", secondary: "2 large" },
    { id: 5, primary: "Milk", secondary: "1 cup" },
  ];

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
            value={searchValue}
            onChange={handleInputChange}
          />

          <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
            Ingredients List
          </Typography>
          <List dense>
            {demoItems.map((item) => (
              <ListItem
                key={item.id}
                secondaryAction={
                  <IconButton edge="end" aria-label="delete">
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
                  primary={item.primary}
                  secondary={item.secondary}
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
