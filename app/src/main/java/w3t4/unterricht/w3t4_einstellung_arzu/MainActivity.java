package w3t4.unterricht.w3t4_einstellung_arzu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.prefs.Preferences;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;


public class MainActivity extends AppCompatActivity
{
    private Button btnPrefs, btnPrefsAnzeigen;
    private TextView lbl;
    /**
     * Hier wird ein Objekt erzeugt, welches, wenn der Aufruf zurückkommt , eine bestimmte
     * Methode aufruft. Wenn eine Activity nämlich einen Rückgabewert zurückliefert, wird dieser in
     * <code>onActivityResult</code> ausgelesen.
     */
    private ActivityResultLauncher<Intent> startForResult;

    // ==============================================================
    private class MyOAR implements ActivityResultCallback<ActivityResult>
    {
        @Override
        public void onActivityResult(ActivityResult result)
        {
            prefsAnzeigen();
        }
    }

    // ==============================================================
    private class MyOCL implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if (view == btnPrefs)
                prefs();
            else if (view == btnPrefsAnzeigen)
                prefsAnzeigen();
        }
    }

    // --------------------------------------------------------------
    private void prefs()
    {
        Intent intent = new Intent(this, PrefsActivity.class);
        startForResult.launch(intent);
    }

    // --------------------------------------------------------------
    private void prefsAnzeigen()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        lbl.setText("Updates: " + sharedPreferences.getBoolean("cbUpdates", false) + "\n");
        lbl.append("Update-Intervall: " + sharedPreferences.getString("update_intervall", "-1") + "\n");
    }

    // --------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultContracts.StartActivityForResult safr = new ActivityResultContracts.StartActivityForResult();
        startForResult = registerForActivityResult(safr, new MyOAR());

        btnPrefs = (Button) findViewById(R.id.btnPrefs);
        btnPrefsAnzeigen = (Button) findViewById(R.id.btnPrefsAnzeigen);
        lbl = (TextView) findViewById(R.id.lbl);
        MyOCL ocl = new MyOCL();
        btnPrefs.setOnClickListener(ocl);
        btnPrefsAnzeigen.setOnClickListener(ocl);
    }

    // --------------------------------------------------------------
}