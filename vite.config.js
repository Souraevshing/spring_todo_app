import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
  },
  define: {
    "process.env.REACT_BASE_URL": process.env.REACT_BASE_URL,
    "process.env.REACT_USER_TOKEN": process.env.REACT_USER_TOKEN,
    "process.env.REACT_USER_SESSION_TOKEN":
      process.env.REACT_USER_SESSION_TOKEN,
  },
});
