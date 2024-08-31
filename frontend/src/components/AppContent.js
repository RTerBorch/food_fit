import * as React from "react";

import { request, setAuthHeader } from "../helpers/axios_helper";

import Buttons from "./Buttons";
import AuthContent from "./AuthContent";
import LoginForm from "./LoginForm";
import WelcomeContent from "./WelcomeContent";

export default class AppContent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      componentToShow: "welcome",
    };
  }

  login = () => {
    this.setState({ componentToShow: "login" });
  };

  logout = () => {
    this.setState({ componentToShow: "welcome" });
    setAuthHeader(null);
  };

  onLogin = (e, username, password) => {
    e.preventDefault();
    console.log("Login Request Data:", { username, password });

    request("POST", "/login", {
      username: username,
      password: password,
    })
      .then((response) => {
        setAuthHeader(response.data.token);
        this.setState({ componentToShow: "messages" });
      })
      .catch((error) => {
        setAuthHeader(null);
        this.setState({ componentToShow: "welcome" });
      });
  };

  onRegister = (event, firstName, lastName, username, password, email) => {
    event.preventDefault();
    console.log("Register Request Data:", {
      firstName,
      lastName,
      username,
      password,
      email,
    });

    request("POST", "/register", {
      firstName: firstName,
      lastName: lastName,
      username: username,
      password: password,
      email: email,
    })
      .then((response) => {
        setAuthHeader(response.data.token);
        this.setState({ componentToShow: "messages" });
      })
      .catch((error) => {
        setAuthHeader(null);
        this.setState({ componentToShow: "welcome" });
      });
  };

  render() {
    return (
      <>
        <Buttons login={this.login} logout={this.logout} />

        {this.state.componentToShow === "welcome" && <WelcomeContent />}
        {this.state.componentToShow === "login" && (
          <LoginForm onLogin={this.onLogin} onRegister={this.onRegister} />
        )}
        {this.state.componentToShow === "messages" && <AuthContent />}
      </>
    );
  }
}
