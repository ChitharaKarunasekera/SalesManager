package com.example.salesmanagerapp.repository;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginRepository {

    private static final String TAG = "LoginRepository";

    public interface LoginCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public void login(String username, String password, LoginCallback callback) {
        new LoginTask(username, password, callback).execute();
    }

    private static class LoginTask extends AsyncTask<Void, Void, String> {

        private String username;
        private String password;
        private LoginCallback callback;

        LoginTask(String username, String password, LoginCallback callback) {
            this.username = username;
            this.password = password;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                String urlString = "http://www.axienta.lk/VantageCoreWebAPI/api/avLogin/Get?id=" + username + "&password=" + password + "&macaddress=123&versionnumber=123&deviceid=123";
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                Log.d(TAG, " %%% My Login Status Code: " + responseCode);

                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    Log.d(TAG, "%%% My response: " + response.toString());

                    if (response.toString().contains("success")) {
                        return "success";
                    } else {
                        return "failure";
                    }
                } else {
                    return "failure";
                }
            } catch (Exception e) {
                return "failure";
            }
        }


        @Override
        protected void onPostExecute(String result) {
            if ("success".equals(result)) {
                callback.onSuccess();
            } else {
                Log.d(TAG, "onPostExecute() results: " + result);
                callback.onFailure("Invalid username or password");
            }
        }
    }
}
