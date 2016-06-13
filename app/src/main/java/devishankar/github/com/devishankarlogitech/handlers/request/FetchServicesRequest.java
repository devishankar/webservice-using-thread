package devishankar.github.com.devishankarlogitech.handlers.request;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import devishankar.github.com.devishankarlogitech.models.Device;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Author by Devishankar
 */

public class FetchServicesRequest extends Thread implements Runnable {
    private final String url;
    private final Handler handler;
    private final Context context;

    public FetchServicesRequest(Context context, Handler handler, String url) {
        this.context = context;
        this.handler = handler;
        this.url = url;
    }

    @Override
    public void run() {
        super.run();
        Message msgObj = handler.obtainMessage();
        Bundle b = new Bundle();
        try {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response;
            response = client.newCall(request).execute();
            String json = response.body().string();
            if (!json.equals("")) {

                JSONObject obj = new JSONObject(json);
                JSONArray arr = obj.getJSONArray("devices");
                ArrayList<Device> devices = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject item = arr.getJSONObject(i);
                    Device d = new Device();
                    d.setDeviceType(item.getString("deviceType"));
                    d.setModel(item.getString("model"));
                    d.setName(item.getString("name"));
                    devices.add(d);
                }

                b.putParcelableArrayList("devices", devices);
            } else {
                Toast.makeText(context, "Response is empty", Toast.LENGTH_LONG).show();
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        msgObj.setData(b);
        handler.sendMessage(msgObj);
    }
}