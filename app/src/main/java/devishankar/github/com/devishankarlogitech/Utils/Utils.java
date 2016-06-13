package devishankar.github.com.devishankarlogitech.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * @author Devishankar
 */
public class Utils {
    public static boolean isAppOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static void showSnackBar(View view, String msg) {
        if (view != null)
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }
}
