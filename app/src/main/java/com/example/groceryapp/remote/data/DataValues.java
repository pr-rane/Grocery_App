package com.example.groceryapp.remote.data;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface DataValues {

    public void setJsonResponse(JSONObject response);

    public void setJsonError(VolleyError volleyError);

}
