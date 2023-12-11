import { defineConfig, loadEnv } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig(({ command, mode }) => {
  // Load env file based on `mode` in the current working directory.
  // Set the third parameter to '' to load all env regardless of the `VITE_` prefix.
  const env = loadEnv(mode, process.cwd());

  return {
    plugins: [react()],
    server: {
      port: 3000,
    },
    define: {
      "process.env.VITE_BASE_URL": JSON.stringify(env.VITE_BASE_URL),
      "process.env.VITE_USER_TOKEN": JSON.stringify(env.VITE_USER_TOKEN),
      "process.env.VITE_USER_SESSION_TOKEN": JSON.stringify(
        env.VITE_USER_SESSION_TOKEN
      ),
      "process.env.VITE_APP_ENV": JSON.stringify(env.VITE_APP_ENV),
    },
  };
});
