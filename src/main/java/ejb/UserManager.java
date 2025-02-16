package ejb;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.UserDAO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;
import model.User;

@Model
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private User user;
    private ArrayList<User> users;
    private UserDAO userDAO = new UserDAO();

    // Getter and Setter for 'user' and 'users'
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    // Method to initialize the bean
    @PostConstruct
    private void init() {
        user = new User();
        users = userDAO.selectAllUsers();
    }

    // Method to select a user by ID and navigate to the edit page
    public String selectUser(int userId) {
        this.user = userDAO.selectUser(userId);
        return "update.xhtml"; // Navigate to the update page
    }

    // Method to update the selected user's details
    public String updateUser() {
        try {
            userDAO.updateUser(user);
            return "index.xhtml?faces-redirect=true"; // Redirect to user list after editing
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Stay on the same page if there is an error
        }
        
    }
    
    
	public String deleteUser(int userId) throws SQLException {
	    userDAO.deleteUser(userId);
	    setUsers(userDAO.selectAllUsers());
	    return "delete.xhtml"; 
	}


    // Method to save a new user
    public String saveUser() {
        try {
            userDAO.insertUser(user);
            return "index.xhtml?faces-redirect=true"; // Redirect to user list after adding
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Stay on the same page if there is an error
        }
    }
}
