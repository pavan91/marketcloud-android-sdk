/*
    Copyright (c) 2015 Marketcloud
    http://www.marketcloud.it

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        https://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.marketcloud.marketcloud;

import android.content.Context;

import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Connect class. <br />
 * <br />
 * Performs a synchronous HTTP request.
 */
public class Connect {

    private Context context;
    private JSONObject jo;

    /**
     * Constructor.
     *
     * @param ct application context
     */
    public Connect(Context ct) {
        context = ct;
    }

    /**
     * Processes an HTTP request in background.
     *
     * @param params the url, the headers and the parameters of the HTTP request
     * @return a JSONArray containing the response to the request
     */
    protected JSONObject run(String... params) {

        try {
            //switch connection types (possibilities: GET, POST, DELETE, PUT and PATCH)
            String type = params[0];

            //prepare the connection
            SyncHttpClient client = new SyncHttpClient();
            client.addHeader("Authorization", params[2]);

            //prepare a response handler
            ResponseHandler rh = new ResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    //parse the response
                    try {
                        jo = new JSONObject(new String(responseBody, "UTF-8"));
                    } catch (JSONException | UnsupportedEncodingException ignored) {}
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //parse the response
                    try {
                        jo = new JSONObject(new String(responseBody, "UTF-8"));
                    } catch (JSONException | UnsupportedEncodingException ignored) {}
                }
            };

            //connect to the given url using the proper request
            switch (type) {
                case "get":
                    client.get(params[1], rh);
                    break;
                case "post":
                    client.post(context, params[1], new StringEntity(params[3]), "application/json", rh);
                    break;
                case "delete":
                    client.delete(params[1] + params[3], rh);
                    break;
                case "patch":
                    client.patch(context, params[1], new StringEntity("[" + params[3] + "]"), "application/json", rh);
                    break;
                case "put":
                    client.put(context, params[1], new StringEntity(params[3]), "application/json", rh);
                    break;
            }

            //return output
            return jo;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}