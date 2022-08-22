# Arbeitnow Vacancies API

`A web application that supports CRUD operations, pagination, sorting and working with 3rd party API.`

---

### Project Description

This project uses public 3rd party API to fetch information about 500 most recent vacancies from 
https://arbeitnow.com/api/job-board-api to an in-memory database every hour using Cron Job.
After that, it can respond to the requests in order to display the vacancies with pagination and sorting.

The application is developed based on N-tier architecture, which contains of 3 levels:

1. Controller - the level that receives requests from client, handles them, calls service methods and sends the response to the client.
2. Service - the level that contains the business logic and call repository methods to perform operations with the database.
3. Repository - the level that works with database and performs the CRUD operations.

---

### Useful endpoints:

1. `GET` `/vacancies?page&count&sortBy` - get all the vacancies. ?page can be used to paginate, 0 by default;
?count to set limit per page, 20 by default; ?sortBy - to sort the vacancies by following parameters (id desc by default): 
id(long), slug(String), title(String), companyName(String), createdAt(long), description(String),
remote(boolean), url(String). Also, you can set different directions and multiple criteria
of sorting semicolon-separated. 
Example of such endpoint: `/vacancies?page=1&count=10&sortBy=title:DESC;description:ASC` - will display
vacancies from 11 to 20, sorted by title in descending order, then by description in ascending order. 
4. `GET` `/vacancies/top-recent?count` - get top recent added vacancies, which are sorted by createdAt. ?count can be used
to set the count of vacancies displayed.
5. `GET` `/vacancies/statistics/vacancies-per-location` - get list of statistics DTOs in following format:
location - vacanciesPerLocation.

---

#### Developed with:

1. Java 11
2. Maven, Intellij IDEA
3. Spring Boot
4. H2 Database, Spring Data JPA
5. Lombok, Jackson, Log4j2
6. Spring Boot tests, Mockito, Spring Mock MVC

---

### How to run this app:

1. Fork this project and open it (preferably in Intellij IDEA)
2. Run the VacanciesApplication.main() method.
3. To run the tests, you can use `mvn package` or `mvn verify` to run with coverage, or just run the test classes.