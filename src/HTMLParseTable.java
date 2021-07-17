import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.sql.Statement;
import java.util.*;

public class HTMLParseTable {
    private String filename;
    private Set<String> words;
    private HashMap<String, String> dictionary;
    private HashMap<String, Integer> primaryKey;
    private HashMap<String, List<Integer>> tables;
    private int ID;
    private int ID0;
    private int ii = 0;
    private String strTableNames;

    public HTMLParseTable() {
        this.filename = null;
        words = new HashSet<>();
        dictionary = new HashMap<>();
        primaryKey = new HashMap<>();
        tables = new HashMap<>();
        ID = 1;
        ID0 = 1;
    }

    public void AddDictionary(String filename) throws IOException {
        ID0 = ID;
        File input = new File(filename);
        //String fileName = new File(filename).getName();
        String fileName = input.getName();
        String[] parts = fileName.split("\\.");
        fileName = parts[0];
        if (!tables.containsKey(fileName)) {
            tables.put(fileName, new ArrayList<>());
        }

        Document doc = Jsoup.parse(input, "UTF-8");
        //Document doc = Jsoup.connect("").get();
        Elements rows = doc.select("tr");
        for (Element row : rows) {
            Elements columns = row.select("td");
            String word = null;
            String transl = null;
            ii++;
            for (Element column : columns) {
                //System.out.print(column.text()+", ");
                if (word == null) {
                    word = column.text();
                } else {
                    transl = column.text();
                }

                if (transl != null) {
                    if (words.contains(word)) {
                        String transPrev = dictionary.get(word);
                        if (transPrev.length() < transl.length())
                            dictionary.put(word, transl);
                    } else {
                        words.add(word);
                        dictionary.put(word, transl);
                        primaryKey.put(word, ID);
                        tables.get(fileName).add(ID);
                        ID++;
                    }
                }

            }
            //System.out.println();
        }
        System.out.println("Input table contains: " + ii + " rows.");
        System.out.println("Unique words in dictionary: " + words.size());
        System.out.println("Unique words added: " + (ID - ID0));
    }

    public File[] getListOfFiles(String path) {
        //File folder = new File("data/");
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".html");
            }
        });
        return listOfFiles;
        //if (listOfFiles[i].isFile())
        //listOfFiles[i].isDirectory()
    }

    public void writeCSV() throws IOException {
        PrintWriter fd = new PrintWriter(new FileWriter("data/dictionary.csv"));
        fd.println("ID\tword\ttranslation");
        for (Map.Entry element : dictionary.entrySet()) {
            String word = (String) element.getKey();
            String translat = (String) element.getValue();
            int primKey = primaryKey.get(word);
            fd.println(primKey + "\t" + word + "\t" + translat);
        }
        fd.close();
    }

    public void writeCSVTableStatus() throws IOException {
        PrintWriter fd = new PrintWriter(new FileWriter("data/tableStatus.csv"));
        String titles = strTableNames.replaceAll(",","\t");
        fd.println("id\t"+titles);
//        String[] arr = strTableNames.split(",");
//        for (Map.Entry element : tables.entrySet()) {
//        for (int i=0;i<arr.length;++i){
//            String word = (String) element.getKey();
//            String translat = (String) element.getValue();
//            int primKey = primaryKey.get(word);
//            fd.println(primKey + "\t" + word + "\t" + translat);
//        }
//        fd.close();
        //System.out.println(strTableNames);
//        StringBuffer titles = new StringBuffer();
//        List<String> list = new ArrayList<>();
//        for (Map.Entry element : tables.entrySet()) {
//            String title = (String) element.getKey();
//            titles.append(title+",");
//            list.add(title);
//            //System.out.println(title);
//        }

        int[][] tableStatus = makeRatingTable();

        fd.close();

    }

    //    fillTablesStateTab(Statement statement, String columnTitles, Integer[][] statesTable)
    public int[][] makeRatingTable() {
//        "HP1_1_4,HP1_5_8"
        int colLen = tables.size();
        colLen++; // id add to array size;
        int rowsLen = primaryKey.size();
        int[][] statusTable = new int[rowsLen][colLen];
        int ii = 0;
        for (int i = 0; i < rowsLen; ++i) {
            statusTable[i][ii] = i + 1;
        }
        ii++;
        StringBuffer names = new StringBuffer();
        List<String> list = new ArrayList<>();
        for (Map.Entry element : tables.entrySet()) {
            String key = (String) element.getKey();
            names.append(key + ",");
           list.add(key);
        }
        for (int i=1;i<list.size();++i){
            List<Integer> entries = tables.get(list.get(i));
            for (int j=0;j<entries.size();++j){
                statusTable[entries.get(j)][i] = 1;
            }
        }
        strTableNames = names.substring(0, names.lastIndexOf(",") );
        strTableNames = strTableNames.replaceAll("-","_");
        System.out.println("===: "+strTableNames);
        return statusTable;
    }

    public String getStrTableNames() {
        return strTableNames;
    }

    public Set<String> getWords() {
        return words;
    }

    public HashMap<String, String> getDictionary() {
        return dictionary;
    }

    public HashMap<String, Integer> getPrimaryKey() {
        return primaryKey;
    }

    public HashMap<String, List<Integer>> getTables() {
        return tables;
    }
}
