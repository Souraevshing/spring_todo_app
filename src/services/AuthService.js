import axios from "axios";

const BASE_URL = `${import.meta.env.VITE_BASE_URL}/auth`;
const userToken = `${import.meta.env.VITE_USER_TOKEN}`;
const userSession = `${import.meta.env.VITE_USER_SESSION_TOKEN}`;
const userRole = `${import.meta.env.VITE_USER_ROLE}`;

export const registerUser = (user) => axios.post(`${BASE_URL}/register`, user);

export const loginUser = (usernameOrEmail, password) =>
  axios.post(`${BASE_URL}/login`, { usernameOrEmail, password });

export const storeToken = (token) => localStorage.setItem(userToken, token);

export const getToken = () => localStorage.getItem(userToken);

export const saveLoggedInUser = (username, role) => {
  sessionStorage.setItem(userSession, username);
  sessionStorage.setItem(userRole, role);
};

export const isUserLoggedIn = () => {
  const username = sessionStorage.getItem(userSession);

  if (username == null) {
    return false;
  } else {
    return true;
  }
};

export const getLoggedInUser = () => {
  const username = sessionStorage.getItem(userSession);
  return username;
};

export const isAdminUser = () => {
  if (sessionStorage.getItem(userRole) === "ROLE_ADMIN") {
    return true;
  }
  return false;
};

export const logout = () => {
  localStorage.removeItem(userToken);
  sessionStorage.removeItem(userSession);
};
