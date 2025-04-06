/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#8B5CF6', // Purple
          light: '#A78BFA',
          dark: '#7C3AED',
        },
        secondary: {
          DEFAULT: '#4B5563', // Gray
          light: '#9CA3AF',
          dark: '#1F2937',
        },
        black: '#000000',
        white: '#FFFFFF',
      },
    },
  },
  plugins: [],
}
