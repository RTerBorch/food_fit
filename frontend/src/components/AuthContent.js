import * as React from "react";
import { request, getAllRecipes } from "../helpers/axios_helper";

export default class AuthContent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
    };
  }

  componentDidMount() {
    request("GET", "/messages", {})
      .then((response) => {
        this.setState({ data: response.data });
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }

  render() {
    return (
      <div className="row justify-content-md-center">
        <div className="col-4">
          <div className="card" style={{ width: "18rem" }}>
            <div className="card-body">
              <h5 className="card-title">Backend response</h5>
              <p className="card-text">Content:</p>
              <ul>
                {this.state.data &&
                  this.state.data.map((recipe) => (
                    <li key={recipe.id}>{recipe.name}</li>
                  ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
