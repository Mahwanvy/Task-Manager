
# Task Manager Application

The Task Manager Application is a web-based task management system built with Spring Boot. It allows users to create, update, and delete tasks, as well as manage their task lists efficiently.


## Features

- User LogIn and SignUp.
- Task management: Users can create, update, and    delete tasks.
- Task Collaboration: Users can share a task to other users for collaboration.


## Getting Started

Clone the repository:

```bash
  git clone https://github.com/Mahwanvy/Task-Manager.git
```
Navigate to the project directory:

```bash
 cd task-manager
 ```

 Build the project:
 ```bash
mvn clean install
 ```
 Run the application:
  ```bash
mvn spring-boot:run
 ```
 Access the application in your web browser at http://localhost:8080 


## Database Configuration
- H2 console available at '/h2-console' 
- Configure your database connection details in the 'application.properties' file.

## Technology Used
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf (for server-side HTML rendering)
- Bootstrap (for styling)
- h2 Databse
- Maven (for dependency management)
## API Reference

#### Get All Tasks

```http
  GET /tasks/{userId}
```
Retrieve a list of all tasks for a specific user.
| Parameter | Type     | 
| :-------- | :------- | 
| `userId` | `Integer` |

#### Add Task

```http
  POST /tasks?id={userId}
```
Create a new task for a specific user.
| Parameter | Type     | 
| :-------- | :------- | 
| `userId`  | `Integer` |

#### Update Task

```http
  POST /tasks/{userId}/{taskId}
```
Update a specific task for a specific user.
| Parameter | Type     | 
| :-------- | :------- | 
| `userId`  | `Integer` |
| `taskId`  | `Integer` |

#### Delete Task

```http
  POST /tasks/{userId}/{taskId}
```
Delete a new task for a specific user.
| Parameter | Type     | 
| :-------- | :------- | 
| `userId`  | `Integer` |
| `taskId`  | `Integer` |



