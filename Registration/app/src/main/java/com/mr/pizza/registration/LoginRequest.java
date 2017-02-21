package com.mr.pizza.registration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PIZZA on 2017-02-18.
 */

public class LoginRequest extends StringRequest {

    final static private String URL = "http://yiyobaby.dothome.co.kr/android/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
