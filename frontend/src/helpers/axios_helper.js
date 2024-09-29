import axios from "axios";

export const getAuthToken = () => {
  return window.localStorage.getItem("auth_token");
};

export const setAuthHeader = (token) => {
  if (token) {
    window.localStorage.setItem("auth_token", token);
  } else {
    window.localStorage.removeItem("auth_token");
  }
};

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.headers.post["Content-Type"] = "application/json";

const addAuthHeader = () => {
  const token = getAuthToken();
  return token ? { Authorization: `Bearer ${token}` } : {};
};

export const request = (method, url, data) => {
  return axios({
    method: method,
    url: url,
    headers: addAuthHeader(),
    data: data,
  });
};

export const getAllRecipes = () => request("GET", "/recipes"); // Updated URL to be consistent

// Create a new recipe with name and ingredients
export const createNewRecipe = (name, ingredients) => {
  const params = new URLSearchParams({ name });
  return request("POST", `/recipes?${params.toString()}`, ingredients);
};

// Edit an existing recipe by ID
export const editRecipe = (id, name, ingredients) => {
  const params = new URLSearchParams({ name });
  return request("PUT", `/recipes/${id}?${params.toString()}`, ingredients);
};

// Delete a recipe by ID
export const deleteRecipe = (id) => request("DELETE", `/recipes/${id}`);

// Search food items by keyword
export const searchFoodItems = (keyword) => {
  const params = new URLSearchParams({ keyword });
  return request(
    "POST",
    `/food-import/get-food-items-by-search?${params.toString()}`
  );
};
