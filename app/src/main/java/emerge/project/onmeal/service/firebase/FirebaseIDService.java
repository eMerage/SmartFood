package emerge.project.onmeal.service.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

/**
 * Created by Himanshu Emerge on 11/8/2017.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIDService";
    EncryptedPreferences encryptedPreferences;
    private static final String PUSH_TOKEN = "pushToken";


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();
        Log.d(TAG, " Refreshed token: " + refreshedToken);

        encryptedPreferences.edit().putString(PUSH_TOKEN, refreshedToken).apply();


    }



}
