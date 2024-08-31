import "./App.css";
import logo from "../logo.svg";

import Header from "./Header.js";
import AppContent from "./AppContent.js";

function App() {
  return (
    <div>
      <Header pageTitle="Frontend authenticated with JWT" logoSrc={logo} />
      <div className="container-fluid">
        <div className="row">
          <div className="col">
            <AppContent />
            <div className="row">
              <div className="col 2">
                <p>HEHEHEH</p>
              </div>{" "}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
