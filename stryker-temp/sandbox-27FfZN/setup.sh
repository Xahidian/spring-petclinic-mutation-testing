#!/bin/bash

# Remove node_modules and package-lock.json if they exist
rm -rf node_modules package-lock.json

# Clean npm cache (forcefully)
npm cache clean --force

# Start your Spring Boot project in the background
mvn spring-boot:run &  # Add '&' to run in the background

# Wait a few seconds to ensure Spring Boot starts properly
sleep 10 

# Install all project dependencies
npm install --save-dev @playwright/test

# Optional: Confirm installation
npx playwright --version
