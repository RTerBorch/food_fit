export const extractNutrient = (nutrientList, nutrientName) => {
  const nutrient = nutrientList.find((n) => n.name === nutrientName);
  return nutrient ? nutrient.value : 0; // Default to 0 if not found
};

export const descendingComparator = (a, b, orderBy) => {
  if (b[orderBy] < a[orderBy]) return -1;
  if (b[orderBy] > a[orderBy]) return 1;
  return 0;
};

export const getComparator = (order, orderBy) => {
  return order === "desc"
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
};
