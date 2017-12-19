package np.gov.shris.lcd.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static SharedPreference sharedPreference;
    public static final String PREFS_NAME = "LCD_PREFS";
    public static final String PREFS_KEY = "LAST_SYNC_PREFS_KEY";



    public static SharedPreference getInstance()
    {
        if (sharedPreference == null)
        {
            sharedPreference = new SharedPreference();
        }
        return sharedPreference;
    }

    public SharedPreference() {
        super();
    }

    public void saveLastSync(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(PREFS_KEY, text); //3

        editor.commit(); //4
    }

    public String getLastSync(Context context) {
        SharedPreferences settings;
        String text = "";
        //  settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, "");
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeLastSync(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(PREFS_KEY);
        editor.commit();
    }
}
