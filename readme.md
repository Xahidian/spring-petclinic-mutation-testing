# spring-petclinic-mutation-testing
Experimental version of Spring PetClinic used  to evaluate fault detection using mutation testing and Playwright-based metamorphic testing.
# 🧪 Spring PetClinic – Mutation & Metamorphic Testing for Thesis

This repository contains a **modified version** of the official [Spring PetClinic](https://github.com/spring-projects/spring-petclinic) application. It was adapted as part of my **Master’s thesis** to evaluate how well **Metamorphic Testing** (using Playwright) compares to traditional testing in detecting injected faults (mutation testing) in a web application.

> 🎓 Author: [MD Zahid](https://github.com/Xahidian)  
> 🎓 Program: Master’s in Information Technology  
> 🏫 University: Åbo Akademi University, Finland  
> 📘 Thesis Focus: Fault injection, Metamorphic Testing, Playwright automation, Mutation analysis

---

## 📌 Project Overview

This research aims to explore how **Metamorphic Testing (MT)** — a testing technique that doesn't rely on a test oracle — can detect faults in real-world applications like Spring PetClinic.

The project includes:
- Fault injections (manual mutants) in controllers and services
- Comparison of detection by:
  - Unit tests
  - Traditional functional tests
  - Metamorphic Relation (MR) based tests written in Playwright

---

## ⚙️ Technologies Used

- 🟨 Java 17
- 🧪 Spring Boot, Spring MVC, JPA
- 🧰 Maven
- 🎭 Playwright (JavaScript/TypeScript)
- 🐘 H2 / In-memory DB
- 🔍 Manual fault injection & result tracking

---

## 🔍 Modifications Made

- Injected **10+ artificial faults (mutants)** in core components
- Built **custom Playwright test suite** to simulate MR-based testing
- Documented **MR definitions** (e.g., Additivity, Consistency, Commutativity)
- Created an evaluation matrix comparing fault detection results

---

## 🧬 Metamorphic Relations (MRs)

These MR tests are designed to capture behavior that should remain consistent across related input transformations. Examples include:

- **MR1 – Additivity**: Adding a new pet should not affect unrelated owner data
- **MR6 – Read-only**: Visiting the same page repeatedly should return identical data
- **MR15 – Update consistency**: Updating pet type should reflect in visit details

Total: **18 Metamorphic Relation tests** covering multiple test properties.

---

## 🧪 Mutation Testing Summary

| Fault Injected In                            | Mutation Example                               | Detected by Unit Test | Detected by MR Test | Notes                        |
|---------------------------------------------|------------------------------------------------|------------------------|----------------------|-------------------------------|
| `VisitController.processNewVisitForm()`     | Skipped `visitService.save(visit)`            | ❌                    | ✅                   | MR caught visit inconsistency |
| `OwnerService.findAll()`                    | Returned empty list                           | ❌                    | ✅                   | Unit didn’t validate count    |
| `PetController.processUpdatePetForm()`      | Updated pet type but didn’t persist           | ❌                    | ✅                   | MR15 caught it                |
| `OwnerService.findByLastName()`             | Returned shuffled results                     | ❌                    | ✅                   | MR11 detected ordering issue  |
| *(More entries in thesis document)*         |                                                |                        |                      |                               |

📌 Full mutation testing table is included in the thesis document.

---

## 🚀 How to Run the Project

### 1. Clone the Repo

```bash
git clone https://github.com/Xahidian/spring-petclinic-mutation-testing.git
cd spring-petclinic-mutation-testing
