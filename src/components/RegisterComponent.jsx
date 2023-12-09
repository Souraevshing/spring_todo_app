import React, { useState } from "react";
import { registerUser } from "../services/AuthService";

const RegisterComponent = () => {
  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  async function handleRegistrationForm(e) {
    e.preventDefault();

    const newUser = { name, username, email, password };
    if (name || username || email || password) {
      try {
        const response = await registerUser(newUser);
        console.log(response.data);
      } catch (error) {
        console.error(error);
        throw Error(error);
      }
    }
  }

  const isEmailValid = (input) => {
    // Define a simple email validation regex
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(input);
  };

  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-md-6 offset-md-3">
          <div className="card bg-dark text-white">
            <div className="card-header">
              <h2 className="text-center">Sign Up</h2>
            </div>

            <div className="card-body">
              <form>
                <div className="mb-3">
                  <label className="form-label">
                    Name<sup style={{ color: "red" }}>*</sup>
                  </label>
                  <input
                    type="text"
                    name="name"
                    className="form-control"
                    placeholder="Enter name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    Username<sup style={{ color: "red" }}>*</sup>
                  </label>
                  <input
                    type="text"
                    name="username"
                    className="form-control"
                    placeholder="Enter username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    Email<sup style={{ color: "red" }}>*</sup>
                  </label>
                  <input
                    type="email"
                    name="email"
                    className="form-control"
                    placeholder="Enter email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    pattern="[^\s@]+@[^\s@]+\.[^\s@]+"
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
                    className="btn btn-outline-primary w-100"
                    onClick={(e) => handleRegistrationForm(e)}
                    type="submit"
                    disabled={
                      !name || !username || !isEmailValid(email) || !password
                    }
                  >
                    Sign Up
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

export default RegisterComponent;
