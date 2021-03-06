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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Currencies class. <br />
 * <br />
 * Operates with the currencies.
 */
public class Currencies {

    private String publicKey;
    private Context context;
    private Utilities api;
    private TokenManager tm;

    /**
     * Constructor.
     *
     * @param key application public key
     * @param tokenManager token manager
     * @param ct application context
     */
    public Currencies(String key, TokenManager tokenManager, Context ct) {
        publicKey = key;
        api = new Utilities(ct, key);
        tm = tokenManager;
        context = ct;
    }

    /**
     * Creates a new currency.
     *
     * @param name       name of the currency
     * @param formatting symbol of the currency (e.g. "€", "$", etc.)
     * @return true if the creation was successful
     */
    @SuppressWarnings("unused")
    public boolean create(String name, String formatting) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = toJsonObject(name, formatting);

        return jsonObject != null && (boolean) (new Connect(context).run(new String[]{"post", "http://api.marketcloud.it/v0/currencies", publicKey + ":" + tm.getSessionToken(), jsonObject.toString()})).get("status");
    }

    /**
     * Get a list of all the currencies.
     *
     * @return a list of all the currencies
     */
    @SuppressWarnings("unused")
    public JSONObject get() throws ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null)
            return api.getInstanceList("http://api.marketcloud.it/v0/currencies", tm.getSessionToken());
        else return null;
    }

    /**
     * Get data about a specific currency.
     *
     * @param id currency id
     * @return the data of the currency
     */
    @SuppressWarnings("unused")
    public JSONObject getById(int id) throws InterruptedException, ExecutionException, JSONException {
        if (tm.getSessionToken() != null)
            return api.getById("http://api.marketcloud.it/v0/currencies/", id, tm.getSessionToken());
        else return null;
    }

    /**
     * Updates the data of a currency.
     *
     * @param name       the new currency name
     * @param formatting the new currency symbol
     * @return true if the update was successful
     */
    @SuppressWarnings("unused")
    public boolean update(String name, String formatting) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = toJsonObject(name, formatting);

        return jsonObject != null && (boolean) (new Connect(context).run(new String[]{"put", "http://api.marketcloud.it/v0/currencies", publicKey + ":" + tm.getSessionToken(), jsonObject.toString()})).get("status");
    }

    /**
     * Deletes a currency.
     *
     * @param id currency id
     * @return true if the delete was successful
     */
    @SuppressWarnings("unused")
    public boolean delete(int id) throws InterruptedException, ExecutionException, JSONException {
        return (boolean) api.delete("http://api.marketcloud.it/v0/currencies/", id, tm.getSessionToken()).get("status");
    }

    /**
     * Generate a JSONObject with the given parameters.
     *
     * @param name currency name
     * @param formatting currency symbol
     * @return JSONObject
     */
    private JSONObject toJsonObject(String name, String formatting) throws JSONException {
        return new JSONObject().put("name", name).put("formatting", formatting);
    }
}