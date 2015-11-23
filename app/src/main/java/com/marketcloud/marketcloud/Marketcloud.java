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

/**
 * Marketcloud class. <br />
 * <br />
 * Main class of the SDK: creates an instance of all the objects that will be used in the application.
 */
public class Marketcloud {

    String publicKey;
    Context context;
    Products products;
    Brands brands;
    Categories categories;
    Shippings shippings;
    TokenManager tokenManager;
    Users users;
    Carts carts;
    Orders orders;
    Addresses addresses;
    Currencies currencies;

    /**
     * Constructor.
     *
     * @param ct application context
     * @param key application public key
     */
    public Marketcloud(Context ct, String key) {
        context = ct;
        publicKey = key;
        products = new Products(publicKey, context);
        brands = new Brands(publicKey);
        categories = new Categories(publicKey, context);
        shippings = new Shippings(publicKey, context);
        tokenManager = new TokenManager(context);
        users = new Users(publicKey, tokenManager, context);
        carts = new Carts(publicKey, tokenManager, context);
        orders = new Orders(publicKey, tokenManager, context);
        addresses = new Addresses(publicKey, tokenManager, context);
        currencies = new Currencies(publicKey, tokenManager, context);
    }
}
