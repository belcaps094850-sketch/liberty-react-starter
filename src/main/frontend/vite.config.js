import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  // Relative paths so it works when served from Liberty's webapp root
  base: './',
  build: {
    outDir: 'dist',
  },
  server: {
    port: 5173,
    // Proxy API calls to Liberty during development
    proxy: {
      '/api': {
        target: 'http://localhost:9080',
        changeOrigin: true,
      },
    },
  },
})
