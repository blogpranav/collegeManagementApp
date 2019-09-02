package Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by NICSI on 3/15/2018.
 */

public class MarshmallowPermission {

    public int result = -1;
    final int state = 0;

    public MarshmallowPermission(Context context, String permission) {
        try {

            if (Build.VERSION.SDK_INT >= 23) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        == PackageManager.PERMISSION_GRANTED) {

                    this.result = 0;
                } else {

                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{permission},
                            state);

                }
            }


        } catch (Exception e) {
        }
    }
}
