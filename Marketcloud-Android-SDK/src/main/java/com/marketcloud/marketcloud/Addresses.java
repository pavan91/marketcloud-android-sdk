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
 * Addresses class. <br />
 * <br />
 * Creates an address instance associated to a specific user.<br />
 * <br />
 * Every address has two required fields (the full name of the user and his email) and a list of optional fields s.a.
 * city, phone number, etc..<br />
 * A list of "create" method allows a flexible Address instance creation, from the minimal version to a full one.<br />
 * In order to get a detailed list of all the optional fields, please check these methods.
 */
public class Addresses {

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
    public Addresses(String key, TokenManager tokenManager, Context ct) {
        publicKey = key;
        api = new Utilities(context, key);
        tm = tokenManager;
        context = ct;
    }

    /**
     * Creates a basic new address.
     *
     * @param name name
     * @param email e-mail address
     * @return the new address
     */
    @SuppressWarnings("unused")
    public JSONObject create(String name, String email) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null) {
                JSONObject jo = toJsonObject(name, email);

                if (jo != null)
                    return new Connect(context)
                            .run("post",
                                    "http://api.marketcloud.it/v0/addresses",
                                    publicKey + ":" + tm.getSessionToken(),
                                    jo.toString());

        }
        return null;
    }

    /**
     * Create a new address.
     *<br />
     * You have to provide the required parameters "full_name" and "email".<br />
     * You can add the following optional information:<br />
     * - country,<br />
     * - state,<br />
     * - city,<br />
     * - address1,<br />
     * - address2,<br />
     * - postal_code,<br />
     * - phone_number,<br />
     * - alternate_phone_number.<br />
     *<br />
     * Example:<br />
     * {<br />
     *     "full_name" : "John Smith",<br />
     *     "email" : "johnsmith@example.com",<br />
     *     "country" : "USA"<br />
     *     "state" : "California",<br />
     *     "city" : "Los Angeles",<br />
     *     "address1" : "Example Street, 1",<br />
     *     "postal_code" : "90096",<br />
     *     "phone_number" : "0 123 456 789"<br />
     * }<br />
     *
     * @param jsonObject the address data
     * @return the new address
     */
    @SuppressWarnings("unused")
    public JSONObject create(JSONObject jsonObject) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null)
            return new Connect(context)
                    .run(
                            "post",
                            "http://api.marketcloud.it/v0/addresses",
                            publicKey + ":" + tm.getSessionToken(),
                            jsonObject.toString())
                    ;

        return null;
    }

    /**
     * Create a new address.
     *<br />
     * You have to provide the required parameters "full_name" and "email".<br />
     * You can add the following optional information:<br />
     * - country,<br />
     * - state,<br />
     * - city,<br />
     * - address1,<br />
     * - address2,<br />
     * - postal_code,<br />
     * - phone_number,<br />
     * - alternate_phone_number.<br />
     *<br />
     * Example:<br />
     * {<br />
     *     "full_name" : "John Smith",<br />
     *     "email" : "johnsmith@example.com",<br />
     *     "country" : "USA"<br />
     *     "state" : "California",<br />
     *     "city" : "Los Angeles",<br />
     *     "address1" : "Example Street, 1",<br />
     *     "postal_code" : "90096",<br />
     *     "phone_number" : "0 123 456 789"<br />
     * }<br />
     *
     * @param json the address data
     * @return the new address
     */
    @SuppressWarnings("unused")
    public JSONObject create(String json) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null)
            return new Connect(context)
                    .run(
                            "post",
                            "http://api.marketcloud.it/v0/addresses",
                            publicKey + ":" + tm.getSessionToken(),
                            json)
                    ;
        return null;
    }

    /**
     * Retrieve all the addresses of a user.
     *
     * @return a list with the data of the user's addresses.
     */
    @SuppressWarnings("unused")
    public JSONObject get() throws ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null)
            return api.getInstanceList("http://api.marketcloud.it/v0/addresses", tm.getSessionToken());
        else return null;
    }

    /**
     * Retrieve an address by its id.
     *
     * @param id address id
     * @return the data of the address
     */
    @SuppressWarnings("unused")
    public JSONObject getById(int id) throws InterruptedException, ExecutionException, JSONException {
        if (tm.getSessionToken() != null)
            return api.getById("http://api.marketcloud.it/v0/addresses/", id, tm.getSessionToken());
        else return null;
    }

    /**
     * Updates an address.
     *
     * @param id address id
     * @param name new name
     * @param email new email
     * @return the data of the updated address
     */
    @SuppressWarnings("unused")
    public JSONObject update(int id, String name, String email) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null) {
            JSONObject jo = toJsonObject(name, email);

            if (jo != null)
                return new Connect(context)
                        .run(
                                "put",
                                "http://api.marketcloud.it/v0/addresses/" + id,
                                publicKey + ":" + tm.getSessionToken(),
                                jo.toString())
                        ;
        }

        return null;
    }

    /**
     * Update an address.
     *<br />
     * You can choose which information to update, between:<br />
     * - full_name,<br />
     * - email,<br />
     * - country,<br />
     * - state,<br />
     * - city,<br />
     * - address1,<br />
     * - address2,<br />
     * - postal_code,<br />
     * - phone_number,<br />
     * - alternate_phone_number.<br />
     *<br />
     * Example:<br />
     * {<br />
     *     "address1" : "New Road, 1",<br />
     *     "phone_number" : "0 987 654 321"<br />
     * }<br />
     *
     * @param id address id
     * @param jsonObject information to update
     * @return the updated address
     */
    @SuppressWarnings("unused")
    public JSONObject update(int id, JSONObject jsonObject) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null)
            return new Connect(context)
                    .run(
                            "put",
                            "http://api.marketcloud.it/v0/addresses/" + id,
                            publicKey + ":" + tm.getSessionToken(),
                            jsonObject.toString())
                    ;

        return null;
    }

    /**
     * Update an address.
     *<br />
     * You can choose which information to update, between:<br />
     * - full_name,<br />
     * - email,<br />
     * - country,<br />
     * - state,<br />
     * - city,<br />
     * - address1,<br />
     * - address2,<br />
     * - postal_code,<br />
     * - phone_number,<br />
     * - alternate_phone_number.<br />
     *<br />
     * Example:<br />
     * {<br />
     *     "address1" : "New Road, 1",<br />
     *     "phone_number" : "0 987 654 321"<br />
     * }<br />
     *
     * @param id address id
     * @param json information to update
     * @return the updated address
     */
    @SuppressWarnings("unused")
    public JSONObject update(int id, String json) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null)
            return new Connect(context)
                    .run(
                            "put",
                            "http://api.marketcloud.it/v0/addresses/" + id,
                            publicKey + ":" + tm.getSessionToken(),
                            json)
                    ;

        return null;
    }

    /**
     * Deletes an address.
     *
     * @param id address id
     * @return true if deleted (now or in the past), false if not (something is wrong)
     */
    @SuppressWarnings("unused")
    public boolean delete(int id) throws InterruptedException, ExecutionException, JSONException {
        return tm.getSessionToken() != null && (boolean) api.delete("http://api.marketcloud.it/v0/addresses/", id, tm.getSessionToken()).get("status");
    }

    /**
     * Create a new JSONObject given a name and an email.
     *
     * @param name user's name
     * @param email user's email
     * @return a JSONObject
     */
    private JSONObject toJsonObject(String name, String email) throws JSONException {
            return new JSONObject().put("full_name", name).put("email", email);
    }
}