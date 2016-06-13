package devishankar.github.com.devishankarlogitech;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.devishankar.devishankarlogitech.R;

import java.util.ArrayList;

import devishankar.github.com.devishankarlogitech.Utils.Utils;
import devishankar.github.com.devishankarlogitech.adapter.DevicesListAdapter;
import devishankar.github.com.devishankarlogitech.handlers.MessageHandler;
import devishankar.github.com.devishankarlogitech.handlers.request.FetchServicesRequest;
import devishankar.github.com.devishankarlogitech.listeners.IMessageLisetner;
import devishankar.github.com.devishankarlogitech.models.Device;

public class MainActivity extends AppCompatActivity implements IMessageLisetner {

    private static final String URL = "https://s3.amazonaws.com/harmony-recruit/devices.json";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static ProgressDialog loading;
    private static DevicesListAdapter adapter;
    private static ArrayList<Device> list = new ArrayList<>();
    private Context context;
    private NetworkConnectivityManager receiver;
    private boolean isConnected = false;
    private TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtMsg = (TextView) findViewById(R.id.txtMsg);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvMatch);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

        adapter = new DevicesListAdapter(list);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkConnectivityManager();

        registerReceiver(receiver, filter);

    }

    private void initRequest() {
        if (Utils.isAppOnline(context)) {
            Handler handler = new MessageHandler(this);
            Thread fetch = new FetchServicesRequest(this, handler,URL);
            fetch.start();
            loading = ProgressDialog.show(this, "Fetching Data", "Please wait...", false, false);
        } else {
            Toast.makeText(context, "No Internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onServices(ArrayList<Device> resp) {
        if ((null != resp)) {
            Log.d(TAG, "Response " + resp.size());

            list.clear();
            list.addAll(resp);
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(context, "No Response From Server.", Toast.LENGTH_SHORT).show();
        }
        loading.dismiss();
    }

    /*private static class MessageHandler extends Handler {
        private final Context context;

        MessageHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ArrayList<Device> resp = msg.getData().getParcelableArrayList("devices");

            if ((null != resp)) {
                Log.d(TAG, "Response " + resp.size());
                list.clear();
                list.addAll(resp);
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(context, "No Response From Server.", Toast.LENGTH_SHORT).show();
            }
            loading.dismiss();
        }
    }*/

    /*private class FetchJson extends Thread implements Runnable {
        private final Handler handler;

        FetchJson(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            super.run();
            Message msgObj = handler.obtainMessage();
            Bundle b = new Bundle();
            try {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(URL)
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
    }*/

    private class NetworkConnectivityManager extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isNetworkAvailable(context);
        }

        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {
                                Log.d(TAG, "Connected to Internet!");
                                isConnected = true;
                                txtMsg.setVisibility(View.GONE);
                                if (list.size() == 0) {
                                    initRequest();
                                }
                            }
                            return true;
                        }
                    }
                }
            }
            Log.d(TAG, "Not connected to Internet!");
            txtMsg.setVisibility(View.VISIBLE);
            isConnected = false;
            return false;
        }
    }
}
