package devishankar.github.com.devishankarlogitech.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import devishankar.github.com.devishankarlogitech.listeners.IMessageLisetner;
import devishankar.github.com.devishankarlogitech.models.Device;

/**
 * Author by Devishankar
 */

public class MessageHandler extends Handler {
    private static final String TAG = MessageHandler.class.getSimpleName();
    private final IMessageLisetner listener;

    public MessageHandler(Context context) {
        this.listener = (IMessageLisetner) context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        ArrayList<Device> resp = msg.getData().getParcelableArrayList("devices");
        this.listener.onServices(resp);
    }
}
