import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HTMLParseTable mTable = new HTMLParseTable();
        mTable.AddDictionary("data/HP1_1-4.html");
        mTable.AddDictionary("data/HP1_5-8.html");
        //mTable.writeCSV();
        String titles = DBDealer.readColumnNames(mTable.getTables());
        System.out.println("-----------------------");
        System.out.println(titles);
        titles = "HP1_1_4 TEXT,HP1_5_8 TEXT)";
        DBDealer.createDBTables(titles);


    }
}
