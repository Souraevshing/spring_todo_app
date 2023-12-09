import axios from "axios";

const BASE_URL = `${process.env.REACT_BASE_URL}/auth`;

export const registerUser = (registerObj) =>
  axios.post(`${BASE_URL}/register`, registerObj);

export const loginUser = (usernameOrEmail, password) =>
  axios.post(`${BASE_URL}/login`, { usernameOrEmail, password });

export const storeToken = (token) =>
  localStorage.setItem(`${process.env.REACT_USER_TOKEN}`, token);

export const getToken = () =>
  localStorage.getItem(`${process.env.REACT_USER_TOKEN}`);

export const saveLoggedInUser = (username) =>
  sessionStorage.setItem(`${process.env.REACT_USER_SESSION_TOKEN}`, username);

export const isUserLoggedIn = () => {
  const username = sessionStorage.getItem(
    `${process.env.REACT_USER_SESSION_TOKEN}`
  );

  if (username == null) {
    return false;
  } else {
    return true;
  }
};

export const getLoggedInUser = () => {
  const username = sessionStorage.getItem(
    `${process.env.REACT_USER_SESSION_TOKEN}`
  );
  return username;
};

export const logout = () => {
  localStorage.removeItem(`${process.env.REACT_USER_TOKEN}`);
  sessionStorage.removeItem(`${process.env.REACT_USER_SESSION_TOKEN}`);
};
