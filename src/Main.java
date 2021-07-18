import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<String> dictFiles = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //parseHTMLCreateDB();
        HTMLParseTable mTable = new HTMLParseTable();
        readListDictFiles("data/list.lst");
        for (int i=0;i<dictFiles.size();++i){
            mTable.AddDictionary(dictFiles.get(i));
        }
        mTable.writeCSV();
        mTable.makeRatingTable();
    }

    public static void parseHTMPTableGetFList() throws IOException {
        HTMLParseTable mTable = new HTMLParseTable();
        File[] fileList =  mTable.getListOfFiles("data/");
        for (File file:fileList) {
            mTable.AddDictionary(file.toString());
        }
        mTable.writeCSV();
        mTable.makeRatingTable();
    }

    public static void parseHTMLCreateDB() throws IOException {
        HTMLParseTable mTable = new HTMLParseTable();
        mTable.AddDictionary("data/HP1_1-4.html");
        mTable.AddDictionary("data/HP1_5-8.html");

        mTable.writeCSV();
        mTable.makeRatingTable();
        //mTable.
//        String titles = DBDealer.readColumnNames(mTable.getTables());
//        System.out.println("-----------------------");
//        System.out.println(titles);
        //DBDealer.createDBTables(mTable);

    }

    public static void readListDictFiles(String listname){
        try {
            Scanner scanner = new Scanner(new File(listname));
            while (scanner.hasNextLine()){
                dictFiles.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
