package com.mr.pizza.registration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PIZZA on 2017-02-17.
 */

public class ValidateRequest extends StringRequest {

    final static private String URL = "http://yiyobaby.dothome.co.kr/android/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        System.out.println("AAAAAAAAAAAAAAA");
        System.out.println(userID);
        System.out.println(listener);

    }

    @Override
    public Map<String, String> getParams(){
        for( String key : parameters.keySet() ){ System.out.println( String.format("키 : %s, 값 : %s", key, parameters.get(key)) ); }
        return parameters;
    }


}
