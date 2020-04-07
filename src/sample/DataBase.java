package sample;

import com.sun.prism.impl.ps.BaseShaderContext;

import java.sql.*;

public class DataBase {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "java_diploma";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConnection = null;

    private Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConnection;
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConnection = getDbConnection();
        System.out.println("Is connected? - " + dbConnection.isValid(1000));
    }

    public boolean registrationUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sqlCommand = "INSERT INTO `users_fx` (`login`, `email`, `password`) VALUES (?, ?, ?)";

        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `users_fx` WHERE `login` = '" + login + "' LIMIT 1");
//        System.out.println(resultSet);
        if (resultSet.next()) {
            return false;
        }
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlCommand);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, password);
        preparedStatement.executeUpdate();
        return true;
    }

    public ResultSet getUser (String login, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String sqlCommand = "SELECT * FROM `users_fx` WHERE `login` = ? AND `password` = ?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlCommand);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public void getDataFromUsersTable (User user) throws SQLException, ClassNotFoundException {
        String sqlCommand = "SELECT * FROM `users_fx` WHERE `login` = ? AND `password` = ?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlCommand);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            user.setId(Integer.parseInt(resultSet.getString("id")));
            user.setEmail(resultSet.getString("email"));
        }

        System.out.println("DB - " + user.getId());
        System.out.println("DB - " + user.getLogin());
        System.out.println("DB - " + user.getPassword());
        System.out.println("DB - " + user.getEmail());
    }

    public void updateLoginInUsersTable(User user, String newData) throws SQLException, ClassNotFoundException {
        String sqlCommand = "UPDATE `users_fx` SET `login` = ? WHERE `users_fx`.`id` = " + user.getId();
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlCommand);
        preparedStatement.setString(1, newData);
        preparedStatement.executeUpdate();
    }

    public void updateEmailInUsersTable(User user, String newData) throws SQLException, ClassNotFoundException {
        String sqlCommand = "UPDATE `users_fx` SET `email` = ? WHERE `users_fx`.`id` = " + user.getId();
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlCommand);
        preparedStatement.setString(1, newData);
        preparedStatement.executeUpdate();
    }

    public void updatePasswordInUsersTable(User user, String newData) throws SQLException, ClassNotFoundException {
        String sqlCommand = "UPDATE `users_fx` SET `password` = ? WHERE `users_fx`.`id` = " + user.getId();
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlCommand);
        preparedStatement.setString(1, newData);
        preparedStatement.executeUpdate();
    }

    public ResultSet getArticlesFromArticlesTable() throws SQLException, ClassNotFoundException {
        String sqlCommand = "SELECT * FROM `articles` ORDER BY `id` DESC";
        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sqlCommand);
        return resultSet;
    }

    public void addNewArticle(String title, String intro, String text, int views) throws SQLException, ClassNotFoundException {
        String sqlCommand = "INSERT INTO `articles` (`title`, `intro`, `text`, `views`) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlCommand);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, intro);
        preparedStatement.setString(3, text);
        preparedStatement.setInt(4, views);
        preparedStatement.executeUpdate();
    }

    public ResultSet getArticleFromArticleTable(int getArticleIdFromNode) throws SQLException, ClassNotFoundException {
        String sqlCommand = "SELECT * FROM `articles` WHERE `id` = " + getArticleIdFromNode;
        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sqlCommand);
        return resultSet;
    }

    public boolean addLinkToLinkSorterTable(String fullLink, String shortLink) throws SQLException, ClassNotFoundException {
        String sqlCommand = "INSERT INTO `link_shorter` (`link`, `short_link`) VALUES (?, ?)";
        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT `short_link` FROM `link_shorter` WHERE `short_link` = '" + shortLink + "' LIMIT 1");
        if (resultSet.next()) {
            return false;
        }
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(sqlCommand);
        preparedStatement.setString(1, fullLink);
        preparedStatement.setString(2, shortLink);
        preparedStatement.executeUpdate();
        return true;
    }


    public ResultSet findAllShortLinks()  throws SQLException, ClassNotFoundException  {
        String sqlCommand = "SELECT * FROM `link_shorter` ORDER BY `id` DESC";
        Statement statement = getDbConnection().createStatement();
        return statement.executeQuery(sqlCommand);
    }
}
