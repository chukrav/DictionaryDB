import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBDealer {

    public void makeCRUD() {
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

    public static void fillDictionaryTab(Statement statement, HashMap<String, String> dictionary,
                                         HashMap<String, Integer> primaryKey) throws SQLException {
        String INSERT_DICT = "INSERT INTO dictionary(id,word,translate) VALUES(";
        //statement.execute("INSERT INTO contacts(name,phone,email)" +
        // "VALUES('Tim',1234567,'tim@email.com')");
        for (Map.Entry element : dictionary.entrySet()) {
            String word = (String) element.getKey();
            String translat = (String) element.getValue();
            translat = translat.replaceAll(",",";");
            int id = primaryKey.get(word);
            String insertWord = INSERT_DICT + id + "," + word + "," + translat + ")";
            statement.execute(insertWord);
        }

    }

    public static void fillTablesStateTab(Statement statement, String columnTitles, int[][] statesTable)
            throws SQLException {
        // In statesTable col-1 = id, columns = [0,1,2] status values.
//        "INSERT INTO tablesinc(id,HP1_1_4,HP1_5_8) VALUES(";
        String INSERT_STATETABROW = "INSERT INTO tablesinc(id,";

        for (int i = 0; i < statesTable.length; ++i) {
            //String insertLine = INSERT_STATETABROW+columnTitles+") VALUES(";
            StringBuffer insertLine = new StringBuffer(INSERT_STATETABROW + columnTitles + ") VALUES(");
            for (int j = 0; j < statesTable[i].length; ++j) {
                insertLine.append(statesTable[i][j] + ",");
            }
//            int idx = insertLine.lastIndexOf(",");
            String line = insertLine.substring(0, insertLine.lastIndexOf(",")) + ")";
            statement.execute(line);
        }
    }

    //colTitles = "HP1_1-4 TEXT,HP1_5-8 TEX)"
    //public static void createDBTables(String colTitles){
    public static void createDBTables(HTMLParseTable mTable) {
        String CREATE_DICTIONARY = "CREATE TABLE IF NOT EXISTS dictionary (id INTEGER, word TEXT, translate TEXT)";
        String DROP_DICTIONARY = "DROP TABLE IF EXISTS dictionary";
        String DROP_TABLES_INCLUDE = "DROP TABLE IF EXISTS tablesinc";
        String CREATE_TABLES_INCLUDE = "CREATE TABLE IF NOT EXISTS tablesinc (id INTEGER, "; // Add tables' names

        //String colTitles = mTable.getStrTableNames();
        String colTitles = readColumnNames(mTable.getTables());

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/dictionaryNZ.db");
            Statement statement = conn.createStatement();
            statement.execute(DROP_DICTIONARY);  // Drops dictionary !!! ----------->
            statement.execute(CREATE_DICTIONARY);
            statement.execute(DROP_TABLES_INCLUDE);  // Drops dictionary !!! ----------->
            statement.execute(CREATE_TABLES_INCLUDE + colTitles);

            //fillDictionaryTab(statement,mTable.getDictionary(),mTable.getPrimaryKey());
            //fillTablesStateTab(statement,mTable.getStrTableNames(), mTable.makeRatingTable());


        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static String readColumnNames(HashMap<String, List<Integer>> tables) {
        StringBuffer outColNames = new StringBuffer();
        for (Map.Entry element : tables.entrySet()) {
            String tableName = (String) element.getKey();
            tableName = tableName.replaceAll("-", "_");
            outColNames.append(tableName + " INTEGER,");
        }
        String outNames = outColNames.toString();
        outNames = outNames.substring(0, outColNames.length() - 1) + ")";

        return outNames;
    }

}
