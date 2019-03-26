package com.stephanieangulo.flost.Controllers;

public class DatabaseConnection {
    /*
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/flost";
    private  static final String USER = "root";
    private static final String PASSWORD = "";


    public static void connect() {
        Connection conn;
        PreparedStatement ps;

        try {
            // register the jdbc driver
            Class.forName(JDBC_DRIVER);

            // open connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            String insertTableSQL = "INSERT INTO Test (first, last)" +
                    " values (?, ?)";
            ps = conn.prepareStatement(insertTableSQL);

            ps.setString(1, "Stephanie");
            ps.setString(2, "Angulo");
            ps.executeUpdate();
            ps.close();
            conn.close();

        } catch(UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    */
}
