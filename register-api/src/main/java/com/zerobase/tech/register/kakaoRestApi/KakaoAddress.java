package com.zerobase.tech.register.kakaoRestApi;

import com.zerobase.tech.register.exceptions.CustomException;
import com.zerobase.tech.register.exceptions.ErrorCode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class KakaoAddress {
    private static String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
    private static String GEOCODE_USER_INFO = "KakaoAK [mykey]";

    public ArrayList<String> getLocation(String location){

        ArrayList<String> locationList = new ArrayList<>();
        URL url;
        String latitude,logitude;
        String addressName,roadAddressName;
        try {
            String address = URLEncoder.encode(location, "UTF-8");
            url = new URL(GEOCODE_URL + address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", GEOCODE_USER_INFO);
            connection.setRequestProperty("content-type", "application/json");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            System.out.println("connection 값 :"+connection );

            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("documents");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                JSONObject jsonObject1 = object.getJSONObject("address");
                JSONObject jsonObject2 = object.getJSONObject("road_address");
                addressName = jsonObject1.getString("address_name");
                roadAddressName  = jsonObject2.getString("address_name");
                latitude = jsonObject1.getString("x");
                logitude = jsonObject1.getString("y");

                locationList.add(addressName);
                locationList.add(roadAddressName);
                locationList.add(logitude);
                locationList.add(latitude);

            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.NOT_INPUT_ADDRESS);
        }
        return locationList;
    }
}
