# Imperial Course Finder
## Introduction to this project

Undergraduate and Postgraduate students at Imperial are allowed to sign up for courses across college. Doing so is potentially particularly helpful for PhD students, who often are moving into a new area and would benefit from widening their expertise at the start of their research project. Unfortunately, however, there is currently no system that allows students to search efficiently for courses from across College. It is therefore very hard to know whether an appropriate course exists, and even harder to sign up. 

The aim of this project outlined by Dr Ouldridge is to create a prototype platform which facilitates exploration of modules across all departments at Imperial College London. 

 

## Setting up

1. Download Mysql and Workbench 8.0.
2. Clone the GitHub repository through

```git
https://github.com/RenzudadiZZA/IC_Course-finder.git
```

3. Create databse and set the username as root, password as software

```
CREATE DATABASE project_db;
```

4. Import the db either by running  'data/dbdump.sql' or create a schema based on the ERD 

   ```
   backend/spring/src/main/resources/static/assets/ERD.png
   ```

5. Run the java file with db running

```
backend/spring/src/main/java/platform/backend/BackendApplication.java
```

6. Open localhost:8080 on web browser.

## Function introduction

Users are able to search modules they want by searching the keywords  and module code on the main page. 

Also, as the Dr Ouldridge said, users might want to search by module content. So we support users to search some terms in the module description, learning outcomes, etc.., such as, Curves. 

A fitler is inevitable for a searching platform, users can filter modules by departments. (Since we are lack of database from modules outside BME departments, only Mathematics, aeronotics, computing courses are manually inserted  into our database. Filters might not apply for those departments we haven't get enough data. But the function could work as well.

Users are able to add their idea modules to a list for later review.

User can get back to the previous search results by one click on the homepage.

Some teachers might want to edit modules, some modules might not open on latest semester, so teachers can register as admin user by input their Staff ID (Enter 1111 in the current version since we don't have db for staff data). Admin can delete and add modules.

Our code support seammlessly viewing on mobile phones.

## Project structure

All the backend java codes are under

```
backend/spring/src/main/java/platform/backend
```

We use the **Layered Architecture pattern**, dividing the code into Controller, Service, Repository, and Entity layers. Benefits include clear separation of concerns, improved maintainability and testability, reduced coupling, and easier scalability. It allows the business logic (Service layer) to remain independent of data access (Repository layer), enhancing flexibility and modularity in the system design.

<img src="layer.png" alt="img" style="zoom:33%;" />

All the frontend codes are under 

```
backend/spring/src/main/resources/static
```

Well classified and named to improve code organization.

## Code quality 

First, we implemented ALL the functions talked with Dr. Ouldridge, which means all the codes we write are perfectly running!

![image-20250113223448417](./coverage.png)

We achieved 88% class coverage and 79% method coverage through Junit test. Our Junit test are design to simulate some API post/get/delete responses and test if the apis return exactly the same as we want. We use assert true, assert euqal, size of reply .etc 

Our code are well commented to ensure good readability and coordination, we use descriptive words as class/file names  and variable names. 

In terms of the frontend, we have comprehensive  error handling process and error messages for bug detection and user guidance. 

We tried to reduce database burden by limiting user input and apply optimizations on data storage, such as only store the latest access data on the same module and only store 5 recent viewed courses on the database.

We use GitHub issues to track all the features are comprehensivly implemented and use pull requests to avoid conflict on the main.

## Techniques we used

1. DB design and management: We use Mysql as database instead of Nosql, since I think is a relativly small project.(In terms of data volume)
2. Spring boot Framework to develop RESTful API to separate frontend and backend development.
3. We use html css and javascript for frontend to achieve dynamic UI and design the frontend as different modules to improve maintainability.
4. The data are using python to automatically fetch from webapp given by student support center and insert into our database.

## Version control

We use github version control and create branches when developing. This improve our work efficiency and quality. The version v1.0.0 is the first version we implemented all the freatures required.(Search, filter,login, register, like,etc.). v1.1.0 is the optimized version we unified the frontend css files and make sure all the pages are in the similar style.(Thank you Henry) Also, this version we started to let  users able to comfortably using our platform on smartphones. v1.1.1 we add some more Junit tests.

## Potential Improvement

Introduce caching to reduce database load for frequent queries, add multilingual support, improve test coverage, refactor redundant code, optimize URL parameter handling, encrypt user passwords, and enhance SQL injection prevention with parameterized queries and ORM tools.
