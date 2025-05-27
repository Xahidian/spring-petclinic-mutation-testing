#!/bin/bash

# Remove node_modules and package-lock.json if they exist npm run test:mutation
rm -rf node_modules package-lock.json

# Clean npm cache (forcefully)
npm cache clean --force

# Start your Spring Boot project in the background
# ./mvnw.cmd clean package (remove old compiled classes and rebuild everything fresh with the updated (non-faulty) code)

mvn spring-boot:run &  # Add '&' to run in the background ./mvnw.cmd -Dtest=playwright.PlaywrightTest test

#./mvnw.cmd -Dtest=playwright.PlaywrightTest#testVisitAdditionDetected test



# Wait a few seconds to ensure Spring Boot starts properly
sleep 10 

# Install all project dependencies
npm install --save-dev @playwright/test

# Optional: Confirm installation 
npx playwright --version

# 1. ./mvnw.cmd -Dtest=org.springframework.samples.petclinic.owner.OwnerControllerTests test OwnerController.java
# 2. ./mvnw.cmd -Dtest=org.springframework.samples.petclinic.owner.VisitControllerTests test VisitController.java
# 3. ./mvnw.cmd -Dtest=org.springframework.samples.petclinic.vet.VetControllerTests test  VetController.java
# 4.  Owner.java
# 5. OwnerController.java
# 6. OwnerController.java
# 7.  OwnerController.java
# 8. PetController .java
# 9. OwnerController.java
# 10. PetController.java

# mvn test -Dtest='org.springframework.samples.petclinic.**.*Test'
# MR Coverage  = java -javaagent:jacocoagent.jar=destfile=jacoco-mr.exec -jar target/spring-petclinic-2.4.2.jar