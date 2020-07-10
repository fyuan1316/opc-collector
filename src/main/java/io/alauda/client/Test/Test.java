package io.alauda.client.Test;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.*;


public class Test {

    public static void main(String[] args) throws IOException {
        //Creating a HttpClient object
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //Creating a HttpGet object
        HttpPost httpPost = new HttpPost("http://139.155.92.20/v1/models/m:predict");

        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader("Authorization", "e70e0dc4c3f74c7380bc3dfba91bf1d3");
        httpPost.setHeader("Host", "cnvdxnmodel-service.tbdsversion.com");

        //Printing the method used
        System.out.println("Request Type: " + httpPost.getMethod());
//        HashMap<String, List<Map<String, Float>>> data = new HashMap<>();
//        List<Map<String, Float>> list = new ArrayList<Map<String, Float>>();
//        Map<String, Float> map = new HashMap<>();
//        map.put("x1", 6.2f);
//        map.put("x2", 2.2f);
//        map.put("x3", 1.1f);
//        map.put("x4", 1.2f);
//        list.add(map);
//        data.put("instances", list);

        String dataStr = "{\"instances\": [{\"x1\":6.2, \"x2\":2.2, \"x3\":1.1, \"x4\":1.2}]}";

        System.out.println(dataStr);



        // 解决中文乱码问题
        StringEntity stringEntity = new StringEntity(dataStr, "UTF-8");
        stringEntity.setContentEncoding("UTF-8");

        httpPost.setEntity(stringEntity);


        //Executing the Get request
        HttpResponse httpresponse = httpclient.execute(httpPost);

        Scanner sc = new Scanner(httpresponse.getEntity().getContent());

        //Printing the status line
        System.out.println(httpresponse.getStatusLine());
        while (sc.hasNext()) {
            System.out.println(sc.nextLine());
        }
    }
}
