package com.raghsonline.qms.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {

    private static final String QUOTE_FILE_NAME = "quotes.md";


    public static List<String> loadQuotes() {
        List<String> quotesList = new ArrayList<>();
        String lineRead = null;
        Resource resource = new ClassPathResource(QUOTE_FILE_NAME);
        try {
            File file = resource.getFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            while((lineRead=bufferedReader.readLine())!=null){
                quotesList.add(lineRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(QUOTE_FILE_NAME);
            File file = new File(String.valueOf(url));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            while((lineRead=bufferedReader.readLine())!=null){
                quotesList.add(lineRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

       /* try {
            URL url = IOUtil.class.getResource(QUOTE_FILE_NAME);
            Path path = Paths.get(url.toURI());
            quotesList = Files.readAllLines(path, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/

        System.out.println("loadQuotes() completed with #" + quotesList.size() + " entries");

        return quotesList;
    }
}
