package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestListWriter {
    private static final String testList = "testList.txt";
    private static File file;
    private static BufferedWriter bw;

    public static void create() throws IOException {
        file = new File(testList);

        if(!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        bw = new BufferedWriter(fw);
    }

    public static void destroy() throws IOException {
        bw.close();
    }

    public static void write(String content) throws IOException {
        bw.write(content);
    }
}
