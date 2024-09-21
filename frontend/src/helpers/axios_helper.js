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

export const getAllRecipes = () => request("GET", "/recipe/show-all-recipes");

export const createNewRecipe = (name, ingredients) =>
  request("POST", `/recipe/create-new-recipe?name=${name}`, ingredients);

export const searchFoodItems = (keyword) =>
  request("POST", `/food-import/get-foot-items-by-search?keyword=${keyword}`);
