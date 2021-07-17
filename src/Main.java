import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        parseHTMLCreateDB();


    }

    public static void parseHTMLCreateDB() throws IOException {
        HTMLParseTable mTable = new HTMLParseTable();
        mTable.AddDictionary("data/HP1_1-4.html");
        mTable.AddDictionary("data/HP1_5-8.html");
        mTable.makeRatingTable();
        //mTable.writeCSV();
        mTable.writeCSVTableStatus();
        String titles = DBDealer.readColumnNames(mTable.getTables());
        System.out.println("-----------------------");
        System.out.println(titles);
        //DBDealer.createDBTables(mTable);

    }
}
