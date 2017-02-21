package com.mr.pizza.registration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PIZZA on 2017-02-17.
 */

public class AddRequest extends StringRequest {

    final static private String URL = "http://yiyobaby.dothome.co.kr/android/CourseAdd.php";
    private Map<String, String> parameters;

    public AddRequest(String userID, String courseID ,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID", courseID);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
