import java.sql.*;

public class DBDealer {

    public void makeCRUD(){
        try {
            //Connection conn = DriverManager.getConnection("jdbc:sqlite:/Volumes/Production/Courses/Programs/JavaPrograms/TestDB/testjava.db");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/testjava.db");
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS contacts (name TEXT, phone INTEGER, email TEXT)");
            // statement.execute("DROP TABLE IF EXISTS contacts");  // Drops contacts !!! ----------->
//            statement.execute("INSERT INTO contacts(name,phone,email)" +
//                    "VALUES('Tim',1234567,'tim@email.com')");

//            statement.execute("INSERT INTO contacts(name,phone,email)" +
//                    "VALUES('Jack',6586734,'jack@email.com')");
//
//            statement.execute("INSERT INTO contacts(name,phone,email)" +
//                    "VALUES('Juliet',9990003456,'juliet@email.com')");
//
//            statement.execute("INSERT INTO contacts(name,phone,email)" +
//                    "VALUES('Moshe',666789002,'moshe@email.com')");
//
//            statement.execute("INSERT INTO contacts(name,phone,email)" +
//                    "VALUES('Alexei',55543289,'alexei@email.com')");

//            statement.execute("UPDATE contacts SET phone=0545203533 WHERE name='Alexei'");
//            statement.execute("DELETE FROM contacts WHERE name='Alexei'");

//            statement.execute("SELECT * FROM contacts");
//            ResultSet results = statement.getResultSet();
            ResultSet results = statement.executeQuery("SELECT * FROM contacts");

            while (results.next()) {
                System.out.println(results.getString("name") + " " +
                        results.getInt("phone") + " " +
                        results.getString("email"));
            }

            results.close();
            statement.close();
            conn.close();

//            Connection conn = DriverManager.getConnection("jdbc:sqlite:D:\\databases\\testjava.db");
//            Class.forName("org.sql.JDBC");

        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

}
