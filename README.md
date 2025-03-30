# BookStore-Application

## Overview
BookStore-Application is a web application that allows users to manage their books. It includes features such as user authentication using JWT, role-based access control (RBAC), Filtering using different parameter, sorting.

## Technologies Used
- Java
- Spring Boot
- JWT authentication
- Spring Security (RBAC)
- Maven
- MongoDB

## Prerequisites
- Java 11 or higher
- SpringBoot
- Maven
- MongoDB

## Running the Application
1. Clone the repository:
   ```bash
   git clone  https://github.com/manuraj23/Bookstore-Application.git
   cd Bookstore-Application
   ```
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will be accessible at [http://localhost:8080/Application-Bookstore/](http://localhost:8080/Application-Bookstore/).

## API Endpoints
### Public Endpoints
- `POST /public/signup` - Used for SignUp
- `POST /public/logIn` -  Used for login for existing user and JWT token will be returned.

### User Endpoints
###### Pass JWT token as TokenBearer for Authentication
- `PUT /user/UpdateUser` - Login User can update their details.
- `DELETE /user/DeleteUser` - Login User can delete their account.

### Book Entry Endpoints
###### Pass JWT token as TokenBearer for Authentication
- `GET /books/getAllBooks` - Retrieve all Books present in DB.
- `POST /books/createBook` - Create a new Book in Loged user.
- `POST /updateBook/bookId/{bookId}` - Update Book by BookId.
- `DELETE /books/deleteBook/bookId/{bookId}` - Delete a specific Book.

### Admin Endpoints
###### Pass JWT token as TokenBearer for Authentication
- `GET /admin/getAllBooks` - Retrieve all Books(Admin only).
- `GET /admin/getAllUsers` - Retrive all Users(Admin Only).

### Filter and Seraching Endpoints
###### Pass JWT token as TokenBearer for Authentication
- `GET books/searchByAuthor?author=<auther>` - Retrieve all Books of given Auther (Partial match Also Supported).
- `GET books/searchByTitle?title=<title>` - Retrive all Books of Given Title (Partial match Also Supported).
- `GET books/searchByCategory?category=<category>` - Retrive all Books of Given Category.
- `GET books/searchByRating?rating=<rating>&condition=<condition>` - Retrive all Books of Given rating.


## Role-Based Access Control (RBAC)
- **Admin**: Full access to manage users and Book entries.
- **User**: Can create, update, and delete their own Book entries.


## Contributing
1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes.
4. Push to your fork and submit a pull request.

## License
This project is licensed under the MIT License.

## Contact
For queries, reach out to [Manu Raj](mailto:manuraj082004@gmail.com).