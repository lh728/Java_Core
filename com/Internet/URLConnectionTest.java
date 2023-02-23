package com_second.Internet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class URLConnectionTest {
    public static void main(String[] args) {
        try {
            String urlName;
            if (args.length > 0) urlName = args[0];
            else urlName = "http://horstmann.com";

            URL url = new URL(urlName);
            URLConnection urlConnection = url.openConnection();

            if (args.length > 2){
                String username = args[1];
                String password = args[2];
                String input = username + ":" + password;
                Base64.Encoder encoder = Base64.getEncoder();
                String s = encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8));
                urlConnection.setRequestProperty("Authorization","Basic " + s);
            }

            urlConnection.connect();

            Map<String, List<String>> headers = urlConnection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()){
                String key = entry.getKey();
                for (String value : entry.getValue()){
                    System.out.println(key + ":" + value);
                }
            }

            System.out.println("--------------");
            System.out.println("getContentType: " + urlConnection.getContentType());
            System.out.println("getContentLength: " + urlConnection.getContentLength());
            System.out.println("getContentEncoding: " + urlConnection.getContentEncoding());
            System.out.println("getDate: " + urlConnection.getDate());
            System.out.println("getExpiration: " + urlConnection.getExpiration());
            System.out.println("getLastModified: " + urlConnection.getLastModified());
            System.out.println("--------------");

            String encoding = urlConnection.getContentEncoding();
            if (encoding == null) encoding = "UTF-8";
            try (var in = new Scanner(urlConnection.getInputStream(),encoding)){
                for (int n = 1;in.hasNextLine() && n <= 10;n++){
                    System.out.println(in.nextLine());
                }
                if (in.hasNextLine()) System.out.println("...");
            }


        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
