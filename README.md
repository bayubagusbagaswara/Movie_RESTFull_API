# Movie RESTFull API

Created Movie RESTFull API to provide information about movies.

# NOTE !!!
- Kita tambahkan model User
- Kita tambahkan Spring Security JWT
- Kita tambahkan Swagger Documentation

# Spring Security

1. Basic Auth, ini tanpa Role, hanya username dan password. Ini menggunakan username default dan password dari spring security
2. Basic Auth InMemoryUserDetailsManager, jadi username dan password disimpan dalam memory, tidak perlu menggunakan password dari spring security 
3. Role Based, ini menggunakan tambahan Role, seperti ADMIN atau USER 
4. Token Based, ini menggunakan token, token untuk mengakses tiap API

## Technologies

- Java JDK 1.8
- Spring Boot version 2.7.6
- Maven version 3.8.6
- Spring Data JPA, Hibernate
- PostgreSQL database version 12
- Flyway database migration
- JUnit testing

**2. Create PostgreSQL database**

```bash
CREATE DATABASE movie_db WITH OWNER postgres;
```

**3. PostgreSQL username and password**

+ open `src/main/resources/application.properties`

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/movie_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

+ change `spring.datasource.username` and `spring.datasource.password` as per your postgres installation
+ set `ddl-auto` is `validated` because we use database migration

```bash
spring.jpa.hibernate.ddl-auto=validate
```

**4. Install library**

```bash
mvc clean install compile test-compile
```

**5. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

# Explore REST API

### Movie

| Method | URL              | Description      | Request Body          | Response Success              | Response Validation Error   | Response Not Found |
|--------|------------------|------------------|-----------------------|-------------------------------|-----------------------------|--------------------|
| POST   | /movies          | Create new movie | [JSON](#create_movie) | [JSON](#create_movie_success) | [JSON](#create_movie_error) | -                  |
| PATCH  | /movies/{id}     | Update movie     | [JSON](#update_movie) | [JSON](#update_movie_success) | [JSON](#update_movie_error) | [JSON](#not_found) |
| DELETE | /movies/{id}     | Delete movie     | -                     | [JSON](#delete_movie_success) | -                           | [JSON](#not_found) | 
| GET    | /movies          | Get all movies   | -                     | [JSON](#get_movie)            | -                           | -                  |
| GET    | /movies/{id}     | Get movie detail | -                     | [JSON](#get_all_movies)       | -                           | [JSON](#not_found) |             

Test them using postman or any other rest client.

## Sample Valid JSON Request Bodys

#### <a id="create_movie">Create Movie Request Body</a>
```json
{
  "title": "Pengabdi Setan 2 Comunion",
  "description": "Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
  "rating": 7,
  "image": ""
}
```

#### <a id="create_movie_success">Create Movie Success Response</a>
```json
{
  "success": true,
  "message": "Successfully add new movie with id : 1",
  "data": {
    "id": 1,
    "title": "Pengabdi Setan 2 Comunion",
    "description": "Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
    "rating": 7.0,
    "image": "",
    "createdAt": "2022-12-11T19:00:09.767",
    "updatedAt": "null"
  }
}
```

#### <a id="create_movie_error">Create Movie Error Validation Response</a>
```json
{
  "success": false,
  "message": "Bad Request",
  "data": {
    "rating": "Rating minimum is 1",
    "description": "Description length minimum must be 10 characters and maximum must be 500 characters",
    "title": "Title must not be blank"
  }
}
```

#### <a id="update_movie">Update Movie Request Body</a>
```json
{
  "title": "Pengabdi Setan 2 Comunion update",
  "description": "Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
  "rating": 7,
  "image": ""
}
```

#### <a id="update_movie_success">Update Movie Success Response</a>
```json
{
  "success": true,
  "message": "Successfully update movie with id : 1",
  "data": {
    "id": 1,
    "title": "Pengabdi Setan 2 Comunion update",
    "description": "Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
    "rating": 7.0,
    "image": "",
    "createdAt": "2022-12-11T19:00:09.767",
    "updatedAt": "2022-12-11T19:02:39.799"
  }
}
```

#### <a id="update_movie_error">Update Movie Error Validation Response</a>
```json
{
  "success": false,
  "message": "Bad Request",
  "data": {
    "rating": "Rating minimum is 1",
    "description": "Description length minimum must be 10 characters and maximum must be 500 characters",
    "title": "Title must not be blank"
  }
}
```

#### <a id="delete_movie_success">Delete Movie Success Response</a>
```json
{
  "success": true,
  "message": "Successfully delete movie with id : 3",
  "data": null
}
```

#### <a id="get_movie">Get Movie By Id Response</a>
```json
{
  "success": true,
  "message": "Successfully get detail of movie with id : 1",
  "data": {
    "id": 1,
    "title": "Pengabdi Setan 2 Comunion update",
    "description": "Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
    "rating": 7.0,
    "image": "",
    "createdAt": "2022-12-11T19:00:09.767",
    "updatedAt": "2022-12-11T19:02:39.799"
  }
}
```

#### <a id="get_all_movies">Get All Movies Response</a>
```json
{
  "success": true,
  "message": "Successfully get all list of movie",
  "data": [
    {
      "id": 1,
      "title": "Pengabdi Setan 2 Comunion update",
      "description": "Adalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
      "rating": 7.0,
      "image": "",
      "createdAt": "2022-12-11T19:00:09.767",
      "updatedAt": "2022-12-11T19:02:39.799"
    },
    {
      "id": 2,
      "title": "KKN di Desa Penari",
      "description": "Cerita ini diambil dari sebuah kisah nyata sekelompok mahasiswa yang tengah melakukan program KKN (Kuliah Kerja Nyata) di Desa Penari",
      "rating": 8.0,
      "image": "",
      "createdAt": "2022-12-11T19:03:53.268",
      "updatedAt": "null"
    },
    {
      "id": 3,
      "title": "Perempuan Tanah Jahanam",
      "description": "Maya (Tarao Basro) jatuh bangun hidup di kota tanpa keluarga, ia hanya memiliki sahabat bernama Dini.",
      "rating": 9.0,
      "image": "",
      "createdAt": "2022-12-11T19:04:20.053",
      "updatedAt": "null"
    }
  ]
}
```

#### <a id="not_found">Resource Not Found Response</a>
```json
{
  "success": false,
  "message": "Movie not found with id : 3"
}
```
