package devishankar.github.com.devishankarlogitech.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

    public static final Creator<Device> CREATOR = new Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
    private String deviceType;
    private String model;
    private String name;


    private Device(Parcel in) {
        deviceType = in.readString();
        model = in.readString();
        name = in.readString();
    }

    public Device() {

    }

    /**
     * @return The deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType The deviceType
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return The model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model The model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceType);
        dest.writeString(model);
        dest.writeString(name);
    }
}
