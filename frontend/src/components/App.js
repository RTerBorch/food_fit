import "./App.css";
import logo from "../logo.svg";

import Header from "./Header.js";
import AppContent from "./AppContent.js";
import RecipePage from "./recipeContent/RecipePage.js";

function App() {
  return (
    <div>
      <Header pageTitle="Frontend authenticated with JWT" logoSrc={logo} />
      <div className="container-fluid">
        <div className="row">
          <div className="col">{/*  <AppContent /> */}</div>
          <div>
            <RecipePage></RecipePage>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
