package util;
import java.sql.*;
import com.sun.rowset.CachedRowSetImpl;
public class DButil {

    private static final String dbURL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    private static final String username = "COMP214_F23_shah_29";
    private static final String password = "password";
    public static Connection dbConnect() throws SQLException {

       Connection connection = DriverManager.getConnection(dbURL, username, password);
        System.out.println("Database is connected");
        return connection;

    }

    public static void dbDisConnect(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Db is disconnected");
        }
    }
    //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        Connection connection = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            connection = dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");
            //Create statement
            stmt = connection.createStatement();
            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);
            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisConnect(connection);
        }
        //Return CachedRowSet
        return crs;
    }
    //DB Execute for prepared statements
    public static ResultSet dbExecutePreparedQuery(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            resultSet = preparedStatement.executeQuery();
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executePreparedQuery operation: " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }

        }
        return crs;
    }

    //DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        Connection connection = null;
        try {
            connection = dbConnect();
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = connection.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisConnect(connection);
        }
    }
}