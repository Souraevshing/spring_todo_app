import React, { useState } from "react";
import {
  loginUser,
  saveLoggedInUser,
  storeToken,
} from "../services/AuthService";
import { useNavigate } from "react-router-dom";

const LoginComponent = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigator = useNavigate();

  async function handleLoginForm(e) {
    e.preventDefault();
    if (username || password) {
      try {
        const response = await loginUser(username, password);

        //const token = "Basic " + window.btoa(username + ":" + password);

        //create jwt token and pass it to response header
        const _token = `Bearer ${response.data._token}`;

        if (_token) {
          storeToken(_token);
          saveLoggedInUser(username);
          navigator("/todos");
        }
        window.location.reload(false);
      } catch (error) {
        console.log(error);
        throw new Error(err);
      }
    }
  }

  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <div className="card bg-dark text-white">
            <div className="card-header">
              <h2 className="text-center">Sign In</h2>
            </div>

            <div className="card-body">
              <form>
                <div className="mb-3">
                  <label className="form-label">
                    Username or Email <sup style={{ color: "red" }}>*</sup>{" "}
                  </label>
                  <input
                    type="text"
                    name="username"
                    className="form-control"
                    placeholder="Enter email"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    Password <sup style={{ color: "red" }}>*</sup>{" "}
                  </label>
                  <input
                    type="password"
                    name="password"
                    className="form-control"
                    placeholder="Enter password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>

                <div className="form-group mb-3">
                  <button
                    className="btn btn-outline-success w-100"
                    onClick={(e) => handleLoginForm(e)}
                    type="submit"
                    disabled={!username || !password}
                  >
                    Sign In
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginComponent;
