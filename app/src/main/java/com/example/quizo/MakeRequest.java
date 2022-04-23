package com.example.quizo;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MakeRequest {
      public static String getHTML(String urlToRead) throws Exception {
          StringBuilder result = new StringBuilder();
          URL url = new URL(urlToRead);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("GET");
          try (BufferedReader reader = new BufferedReader(
                      new InputStreamReader(conn.getInputStream()))) {
              for (String line; (line = reader.readLine()) != null; ) {
                  result.append(line);
              }
      }
      return result.toString();
   }
   static class NetworkThread implements Runnable {
     private volatile String value;
     private final String url;

       public NetworkThread(String url) {
           this.url = url;
       }

       @Override
     public void run() {
         try {
             value = MakeRequest.getHTML(url);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     public String getValue() {
         return value;

     }
 }
}

