package com.example.groceryapp.remote.data;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.groceryapp.MyApplication;
import com.example.groceryapp.remote.VolleySingleton;

import org.json.JSONObject;

public class DataManager {

    public DataManager() {
    }

    public void sendVolleyRequest(final DataValues dataValues) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(APICALL.baseUrl, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dataValues.setJsonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dataValues.setJsonError(error);
                    }
                }
        );

        VolleySingleton.getInstance(MyApplication.getAppContext()).addToQueue(jsonObjectRequest);

    }

}
