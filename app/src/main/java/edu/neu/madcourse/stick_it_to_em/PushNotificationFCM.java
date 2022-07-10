package edu.neu.madcourse.stick_it_to_em;


import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class PushNotificationFCM {

    private static final String TARGET_URL = "https://fcm.googleapis.com/fcm/send";

    private static final String SERVER_KEY = "AAAAeH9BuTQ:APA91bGvUWsiBFlbetY2v438uyu3Clfo4Fd1u9qOUgjzxgQ6SyTdLUj_58iSOdpZ2Jd3Uo3WBDH1Gd6X4lpDCf9EB4RNQLpmo4kbQYjOvx2ZdaIkG35mF8l7ZYg_CchhTIpUogsmhrSA";

    public static void sendNotifications(Context context, String token, String heading, String msgContent) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);
        // Prepare data
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {

            jNotification.put("title", heading);
            jNotification.put("body", msgContent);

            // Populate the Payload object.
            // Note that "to" is a topic, not a token representing an app instance
            jPayload.put("to", token);
            jPayload.put("notification", jNotification);

            Log.d("TAG", jPayload.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, TARGET_URL, jPayload, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("TAG", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", "key="+SERVER_KEY);
                    return params;
                }
            };
            queue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String fcmHttpConnection(String serverToken, JSONObject jsonObject) {
        try {

            // Open the HTTP connection and send the payload
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", serverToken);
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
//
//
//            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setDoInput(true);
//            con.connect();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            return convertInputStreamToString(inputStream);
        } catch (IOException e) {
            return "NULL";
        }

    }

    public static String convertInputStreamToString(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream).useDelimiter("\\A");
        return sc.hasNext()?sc.next().replace(",",",\n") : "";
    }
}
