package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;
public class UserDAO {
	// definicja parametrów połączenia z bazą danych PGSQL za pomocą JDBC
//	private final String url ="jdbc:postgresql://localhost:5432/postgres?currentSchema=\"users\"";
////	private final String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=users";
//
//	private final String user = "postgres";
//	private final String password = "postgres";
//	
	
	private final String user = "s49597"; // nazwa użytkownika
	private final String password = "3Ki6Xe4Jo1Xm"; // hasło
	private final String url = "jdbc:postgresql://localhost:5432/s49597?currentSchema=users";
	// koniec - parametrów połączenia z PGSQL
	
	//instrukcje SQL do realizacji CRUID (Create, Read, Update, Insert, Delete)
	// read / select data - all users
	private static final String SELECT_ALL_USERS = "select * from users";
	// read / select data user by id
	private static final String SELECT_USER_BY_ID = "select id, first_name, last_name, year_of_study, email, personal_id, country from users where id =?";
	// insert data
	private static final String INSERT_USERS_SQL = "INSERT INTO users (first_name, last_name, year_of_study, email, personal_id, country) VALUES (?, ?, ?, ?, ?, ?);";
	// update user by id
	private static final String UPDATE_USER_SQL = 
		    "UPDATE users SET first_name = ?, last_name = ?, year_of_study = ?, email = ?, personal_id = ?, country = ? WHERE id = ?";

	// delete user by id
	private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";

	Connection connection = null;
/**
* Connect to the PostgreSQL database
*
* @return a Connection object
*/
//rozpoczęcie polączenia
	public Connection DBSQLConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			if(connection.isValid(5)) System.out.println("Connection is working");
			}
		catch (SQLException e) {
			e.printStackTrace();
			}
		catch (ClassNotFoundException e) {
		
			e.printStackTrace();
			}
		return connection;
		}
//zakończenie polączenia
	private void DBSQLConnectionClose(){
		if(connection==null) return;
		try{
			connection.close();
			if(!connection.isValid(5)) System.out.println("Connection closed");
			}
		catch(SQLException e){
			e.printStackTrace();}
		}
// kontruktor, który nic nie wykonuje
	public UserDAO() {
		}

	private void printSQLException(SQLException ex) {

		
		for (Throwable e: ex) {
		if (e instanceof SQLException) {
		e.printStackTrace(System.err);
		System.err.println("SQLState: " + ((SQLException) e).getSQLState());
		System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
		System.err.println("Message: " + e.getMessage());
		Throwable t = ex.getCause();
		while (t != null) {
		System.out.println("Cause: " + t);
		t = t.getCause();
		}
		}
		}
		}
	
	/*pobranie z DB danych użytkownika o podanym id i zapisanie w obiekcie*/
	public User selectUser(int id) {
	User user = null;
	//połączenie
	try(
	Connection connection = DBSQLConnection();
	//utworzenie obiektu reprezentującego prekompilowane zapytanie SQL
	PreparedStatement preparedStatement =
	connection.prepareStatement(SELECT_USER_BY_ID);)
	{
	//ustawienie parametru
		preparedStatement.setInt(1, id);
		//wyświetlenie zapytania w konsoli
		System.out.println(preparedStatement);
		// Wykonanie zapytania
		ResultSet rs = preparedStatement.executeQuery();
		// Proces obsługi rezultatu.
		while (rs.next()) {
		String t_first_name = rs.getString("first_name");
		String t_last_name = rs.getString("last_name");
		int t_year_of_study = rs.getInt("year_of_study");
		String t_email = rs.getString("email");
		Long t_personal_id = rs.getLong("personal_id");
		String t_country = rs.getString("country");
		user = new User(id, t_first_name, t_last_name, t_year_of_study,
		t_email, t_personal_id, t_country);
		}
		}
		catch (SQLException e) { printSQLException(e); }
		return user;
		}
	
	/* dodanie rekordu z danymi nowego użytkownika*/
	public void insertUser(User user) throws SQLException {
	System.out.println(INSERT_USERS_SQL);
	try(Connection connection = DBSQLConnection();
	PreparedStatement preparedStatement =
	connection.prepareStatement(INSERT_USERS_SQL)){
	preparedStatement.setString(1, user.getFirst_name());
	preparedStatement.setString(2, user.getLast_name());
	preparedStatement.setInt(3, user.getYear_of_study());
	preparedStatement.setString(4, user.getEmail());
	preparedStatement.setLong(5, (Long)user.getPersonal_id());
	preparedStatement.setString(6, user.getCountry());
	System.out.println(preparedStatement);
	preparedStatement.executeUpdate();
	}
	catch (SQLException e) { printSQLException(e); }
	}
	
	
	
	public void updateUser(User user) throws SQLException {
	    try (Connection connection = DBSQLConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
	        
	        preparedStatement.setString(1, user.getFirst_name());
	        preparedStatement.setString(2, user.getLast_name());
	        preparedStatement.setInt(3, user.getYear_of_study());
	        preparedStatement.setString(4, user.getEmail());
	        preparedStatement.setLong(5, user.getPersonal_id());
	        preparedStatement.setString(6, user.getCountry());
	        preparedStatement.setInt(7, user.getId());
	        
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        printSQLException(e);
	    }
	}
	
	
	
	public void deleteUser(int userId) throws SQLException {
	    try (Connection connection = DBSQLConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
	        preparedStatement.setInt(1, userId);
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        printSQLException(e);
	    }
	}
	
	public ArrayList<User> selectAllUsers() {
		ArrayList<User> users = new ArrayList<>();
		//połączenia
		try (Connection connection = DBSQLConnection();
		PreparedStatement preparedStatement =
		connection.prepareStatement(SELECT_ALL_USERS);){
		System.out.println(preparedStatement);
		ResultSet rs = preparedStatement.executeQuery();
		// odebranie wyników z obiektu ResultSet.
		while (rs.next()) {
		int t_id = rs.getInt("id");
		String t_first_name = rs.getString("first_name");
		String t_last_name = rs.getString("last_name");
		int t_year_of_study = rs.getInt("year_of_study");
		String t_email = rs.getString("email");
		Long t_personal_id= rs.getLong("personal_id");
		String t_country = rs.getString("country");
		users.add(new User(t_id, t_first_name, t_last_name, t_year_of_study,
		t_email, t_personal_id, t_country));
		}
		}
		catch (SQLException e) { printSQLException(e);}
		return users;
		}
	
	
	// metoda main do testowania
	public static void main(String[] args){
		UserDAO dao = new UserDAO();
		dao.DBSQLConnection();
//		User u1= dao.selectUser(1);
//		System.out.println(u1.getFirst_name());
//		System.out.println(u1.getLast_name());
//		System.out.println(u1.getPersonal_id());

		
//		// dodanie użytkownik do bazy <CREATE>
//		 UserDAO userDAO = new UserDAO();
//	        User newUser = new User(
//	            "Thomas", // first name
//	            "Muller", // last name
//	            4, // year of study
//	            "tmuller@example.com", // email
//	            123456789L, // personal ID
//	            "Germany" // country
//	        );
//	        try {
//	            userDAO.insertUser(newUser);
//	            System.out.println("User inserted successfully!");
//	        } catch (SQLException e) {
//	            e.printStackTrace();
//	        }
		
		
//		// Select ALL USERS 
        UserDAO userDAO = new UserDAO();
        ArrayList<User> users = userDAO.selectAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found in the database.");
        } else {
            System.out.println("Users found:");
            for (User user : users) {
               System.out.println("ID: " + user.getId() +
                        ", First Name: " + user.getFirst_name() +
                        ", Last Name: " + user.getLast_name() +
                        ", Year of Study: " + user.getYear_of_study() +
                        ", Email: " + user.getEmail() +
                       ", Personal ID: " + user.getPersonal_id() +
                        ", Country: " + user.getCountry());
            }
        }

		
		

		dao.DBSQLConnectionClose();
		}
	}