package devishankar.github.com.devishankarlogitech.listeners;

import java.util.ArrayList;

import devishankar.github.com.devishankarlogitech.models.Device;

/**
 * Author by devishankar_r
 */

public interface IMessageLisetner {
    void onServices(ArrayList<Device> resp);
}
