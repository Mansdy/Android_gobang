package activitytest.example.com.gamesnaker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import junit.framework.TestListener;

public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("log","MainActivity  onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
