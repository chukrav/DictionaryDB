import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void createDBTables(){
        String CREATE_DICTIONARY = "CREATE TABLE IF NOT EXISTS dictionary (id INTEGER, word TEXT, translate TEXT)";
        String DROP_DICTIONARY = "DROP TABLE IF EXISTS dictionary";
        String DROP_TABLES_INCLUDE = "DROP TABLE IF EXISTS tablesinc";
        String CREATE_TABLES_INCLUDE = "CREATE TABLE IF NOT EXISTS tablesinc (id INTEGER, "; // Add tables' names

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/dictionaryNZ.db");
            Statement statement = conn.createStatement();
            statement.execute(DROP_DICTIONARY);  // Drops dictionary !!! ----------->
            statement.execute(CREATE_DICTIONARY);


        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static String readColumnNames(HashMap<String, List<Integer>> tables){
        StringBuffer outColNames = new StringBuffer();
        for (Map.Entry element:tables.entrySet() ){
            String tableName = (String) element.getKey();
            outColNames.append(tableName + " TEXT,");
        }
        String outNames = outColNames.toString();
        outNames = outNames.substring(0,outColNames.length()-2)+")";

        return outNames;
    }

}
