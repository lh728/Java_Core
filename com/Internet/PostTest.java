package com_second.Internet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class PostTest {
    public static void main(String[] args) throws IOException {
        String s = args.length > 0 ? args[0] : "post/post.properties";
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(s))){
            properties.load(in);
        }
        String urlString = properties.remove("url").toString();
        Object userAgent = properties.remove("User-Agent");
        Object redirects = properties.remove("redirects");
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        String result = doPost(new URL(urlString), properties, userAgent == null ? null : userAgent.toString(),
                redirects == null ? -1 : Integer.parseInt(redirects.toString()));
        System.out.println(result);
    }



    public static String doPost (URL url, Map<Object,Object> nameValuePairs,String userAgent,
                                 int redirects) throws IOException {
        var connection = (HttpURLConnection) url.openConnection();
        if (userAgent != null)
            connection.setRequestProperty("User-Agent",userAgent);
        if (redirects >= 0)
            connection.setInstanceFollowRedirects(false);

        connection.setDoOutput(true);

        try (var out = new PrintWriter(connection.getOutputStream())){
            var first = true;
            for (Map.Entry<Object,Object> pair : nameValuePairs.entrySet()){
                if (first) first = false;
                else out.print('&');
                String name = pair.getKey().toString();
                String value = pair.getValue().toString();
                out.print(name);
                out.print('=');
                out.print(URLEncoder.encode(value, StandardCharsets.UTF_8));
            }
        }
        String encoding = connection.getContentEncoding();
        if (encoding == null) encoding = "UTF-8";

        if (redirects > 0){
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode ==
            HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_SEE_OTHER){
                String location = connection.getHeaderField("Location");
                if (location != null){
                    URL base = connection.getURL();
                    connection.disconnect();
                    return doPost(new URL(base,location),nameValuePairs,userAgent,redirects - 1);
                }
            }
        } else if (redirects == 0) {
            throw  new IOException("Too many redirects");
        }

        var response = new StringBuilder();
        try (var in = new Scanner(connection.getInputStream(),encoding)){
            while (in.hasNextLine()){
                response.append(in.nextLine());
                response.append("\n");
            }
        }catch (IOException e){
            InputStream errorStream = connection.getErrorStream();
            if (errorStream == null) throw  e;
            try (var in = new Scanner(errorStream)){
                response.append(in.nextLine());
                response.append("\n");
            }
        }
        return response.toString();

    }
}
