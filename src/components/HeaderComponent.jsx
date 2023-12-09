import React from "react";
import { NavLink } from "react-router-dom";
import { isUserLoggedIn, logout } from "../services/AuthService";
import { useNavigate } from "react-router-dom";

const HeaderComponent = () => {
  const isAuth = isUserLoggedIn();

  const navigator = useNavigate();

  function handleLogout() {
    logout();
    navigator("/login");
  }

  return <header>
      <nav className="navbar navbar-expand-md navbar-dark bg-dark">
        <div className="container">
          <a href="/" className="navbar-brand">
            TODO MANAGEMENT
          </a>

          <div className="collapse navbar-collapse">
            <ul className="navbar-nav me-auto mb-2 mb-md-0">
              {isAuth && (
                <li className="nav-item">
                  <NavLink to="/todos" className="nav-link">
                    TODOS
                  </NavLink>
                </li>
              )}
            </ul>

            <ul className="navbar-nav ms-auto">
              {!isAuth && (
                <>
                  <li className="nav-item">
                    <NavLink to="/register" className="nav-link">
                      <button className="btn btn-outline-primary" type="button">
                        Sign Up
                      </button>
                    </NavLink>
                  </li>

                  <li className="nav-item">
                    <NavLink to="/login" className="nav-link">
                      <button className="btn btn-outline-success" type="button">
                        Sign In
                      </button>
                    </NavLink>
                  </li>
                </>
              )}

              {isAuth && (
                <li className="nav-item">
                  <NavLink
                    to="/login"
                    className="nav-link"
                    onClick={handleLogout}
                  >
                    <button className="btn btn-outline-danger" type="button">
                      Log Out
                    </button>
                  </NavLink>
                </li>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </header>
};

export default HeaderComponent;
