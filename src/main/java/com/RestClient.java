package com;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestClient {
    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static String sessionId;

    public static void main(String[] args) {

        //  Получение
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, new HttpEntity<>(headers),String.class);
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        sessionId = cookies.get(0).split(";")[0];
        headers.set("Cookie", sessionId);
        System.out.println(response.getBody());

        // Добавление
        User newUser = new User(3L, "James", "Brown", (byte) 27);
        HttpEntity<User> add = new HttpEntity<>(newUser,headers);
        String a = restTemplate.exchange(URL, HttpMethod.POST, add, String.class).getBody();
        System.out.println("1 = " + a);

        // Обновление
        newUser.setName("James");
        newUser.setLastName("Brown");
        newUser.setAge((byte) 25);
        HttpEntity<User> update = new HttpEntity<>(newUser,headers);
        String b = restTemplate.exchange(URL, HttpMethod.PUT, update, String.class).getBody();
        System.out.println("2 = " + b);

        // Удаление
        HttpEntity<User> delete = new HttpEntity<>(headers);
        String c = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, delete, String.class).getBody();
        System.out.println("3 = " + c);

        // Финал
        String resultCode = a + b + c;
        System.out.println("result = " + resultCode);
    }
}
