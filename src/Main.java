import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HTMLParseTable mTable = new HTMLParseTable();
        mTable.AddDictionary("data/HP1_1-4.html");
        mTable.AddDictionary("data/HP1_5-8.html");
        mTable.writeCSV();


    }
}
