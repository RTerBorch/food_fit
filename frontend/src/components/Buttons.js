import * as React from "react";

export default function Buttons(props) {
  return (
    <div className="row">
      <div className="col-md-12 text-center" style={{ marginTop: "30px" }}>
        <button
          className="btn btn-primary"
          onClick={props.login}
          style={{ margin: "10px" }}
        >
          Login
        </button>
        <button className="btn btn-dark" onClick={props.logout}>
          Logout
        </button>
      </div>
    </div>
  );
}
