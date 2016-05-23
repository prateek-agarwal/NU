package q2.niituniversity.nu;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Pradumn K Mahanta on 22-05-2016.
 */
public class MAINAppData {

    //SESSION MANAGING
    private static final String PREF_NAME = "NU!!";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static String TAG = MAINAppData.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    public MAINAppData(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(KEY_IS_LOGGEDIN, false);

    }

    //USER DETAILS
    public static String userType = "STUDENT";
    public static String userName = "STUDENT";
    public static SharedPreferences googleLoggedInDetails;
}
