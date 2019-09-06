package emerge.project.onmeal;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import emerge.project.onmeal.data.RealmMigrations.RealmMigrations;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class OnMeal extends Application {




    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        Realm.init(this);

        final RealmConfiguration configuration = new RealmConfiguration.Builder().name("foodeey.realm")
                .schemaVersion(0)
                .migration(new RealmMigrations())
                .deleteRealmIfMigrationNeeded()
                .build();




        Realm.setDefaultConfiguration(configuration);
        Realm.getInstance(configuration);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }



}
