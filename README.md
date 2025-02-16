# 📖 Java Admin Panel - User Management

## 🛠️ Project Overview
This project is a **Java-based Admin Panel** designed to manage users through a simple **CRUD (Create, Read, Update, Delete)** interface. It provides administrators with the ability to:
- ➕ Add new users
- 🗑️ Delete existing users
- ✏️ Update user information
- 📋 List all users

The application leverages **Java EE** technologies, including **EJB (Enterprise JavaBeans)**, **DAO (Data Access Object)** patterns, and **JSF (JavaServer Faces)** with **xHTML** for the front end.

---

## ⚙️ Technologies & Tools Used
- **Java EE** (Enterprise Edition)
- **EJB** (Enterprise JavaBeans)
- **DAO** (Data Access Object Pattern)
- **JSF** (JavaServer Faces) with **xHTML**
- **beans.xml** for dependency management
- **web.xml** for servlet configurations
- **Maven** for dependency and build management
- **PostgreSQL** (or other RDBMS) for user data persistence

---

## 🏗️ Project Structure
```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── model
│   │   │   │   └── User.java
│   │   │   ├── dao
│   │   │   │   └── UserDAO.java
│   │   │   ├── ejb
│   │   │   │   └── UserService.java
│   │   │   └── controller
│   │   │       └── UserController.java
│   │   └── webapp
│   │       └── WEB-INF
│   │           └── web.xml
│   │       └── resources
│   │           └── beans.xml
│   │       └── pages
│   │           ├── index.xhtml
│   │           ├── addUser.xhtml
│   │           ├── editUser.xhtml
│   │           └── listUsers.xhtml
└── pom.xml
```

---

## 🚀 Getting Started

### 🖥️ Prerequisites
Ensure you have the following installed:
- **JDK 11+**
- **Apache Tomcat** or any Java EE-compliant server
- **Maven**
- **PostgreSQL**

### 🔧 Installation Steps
1. **Clone the Repository**:
    ```bash
    git clone https://github.com/Barbarossa/WebAppUsers.git
    cd WebAppUsers
    ```

2. **Configure Database**:
   - Update `UserDAO.java` with your database credentials.

3. **Build the Application**:
    ```bash
    mvn clean install
    ```

4. **Deploy to Server**:
    - Deploy the generated `.war` file to **Apache Tomcat** or **WildFly**.

5. **Access the Application**:
    - Open: `http://localhost:8080/admin-panel`.

---

## 🖼️ Application Features

### 1️⃣ **Add User**
- Navigate to the **Add User** page and fill in user details.

### 2️⃣ **Edit User**
- Update existing user information.

### 3️⃣ **Delete User**
- Remove unwanted user accounts.

### 4️⃣ **List Users**
- View all registered users in a tabular format.

---

## ⚠️ Security Considerations
- Input validation on all forms.
- User authentication (to be implemented in future versions).

---

## 📚 Code Overview

### 🔹 Model - `User.java`
```java
public class User {
    private Long id;
    private String username;
    private String email;
    // Getters and Setters
}
```

### 🔹 DAO - `UserDAO.java`
```java
public class UserDAO {
    public void addUser(User user) { /* Insert SQL logic */ }
    public void deleteUser(Long id) { /* Delete logic */ }
    public List<User> listUsers() { /* Query logic */ }
    public void updateUser(User user) { /* Update logic */ }
}
```

### 🔹 EJB - `UserService.java`
```java
@Stateless
public class UserService {
    @Inject
    private UserDAO userDAO;
    public void addUser(User user) { userDAO.addUser(user); }
    public void deleteUser(Long id) { userDAO.deleteUser(id); }
    public List<User> listUsers() { return userDAO.listUsers(); }
    public void updateUser(User user) { userDAO.updateUser(user); }
}
```

### 🔹 Controller - `UserController.java`
```java
@ManagedBean
@ViewScoped
public class UserController {
    @Inject
    private UserService userService;
    private User user = new User();

    public void addUser() { userService.addUser(user); }
    public List<User> getUsers() { return userService.listUsers(); }
}
```

---

## 🌐 Web Configuration

### **beans.xml** (Dependency Management)
```xml
<beans xmlns="http://xmlns.jcp.org/xml/ns/javaee"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
       http://xmlns.jcp.org/xml/ns/javaee/beans_1_1.xsd"
       bean-discovery-mode="all">
</beans>
```

### **web.xml** (Servlet Configuration)
```xml
<web-app xmlns="http://java.sun.com/xml/ns/javaee" version="3.0">
    <context-param>
        <param-name>javax.faces.PROJECT_STAGE</param-name>
        <param-value>Development</param-value>
    </context-param>
    <servlet>
        <servlet-name>FacesServlet</servlet-name>
        <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>FacesServlet</servlet-name>
        <url-pattern>*.xhtml</url-pattern>
    </servlet-mapping>
</web-app>
```

---

## 🎯 Future Improvements
- 🔐 Add authentication & authorization
- 📊 Add user activity logging
- 📱 Improve UI with responsive design

**Happy Coding! 😊**

