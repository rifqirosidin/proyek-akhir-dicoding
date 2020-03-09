package id.rifqi.moviecalatogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.alarm.DailyAlarmReceiver;
import id.rifqi.moviecalatogue.alarm.ReleaseMovieTodayReminder;
import id.rifqi.moviecalatogue.api.MovieResponse;
import id.rifqi.moviecalatogue.api.RetrofitClient;
import id.rifqi.moviecalatogue.api.WebService;
import id.rifqi.moviecalatogue.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LanguageSettingActivity extends AppCompatActivity {

    RadioButton radioButtonLanguage;
    RadioGroup radioGrouplanguage;
    Button btnSave;
    Locale newLocale = new Locale("");
    Context mContext;
    Switch switchDailyReminder, switchReleaseMovieReminder;
    SharedPreferences sharedPref;
    int statusDailyReminder;
    int statusReleaseMovieReminder;
    int defaultValueSharPref = 0;
    private DailyAlarmReceiver dailyReminder = new DailyAlarmReceiver();
    private ReleaseMovieTodayReminder releaseMovie = new ReleaseMovieTodayReminder();
    ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setting);
        setTitle(getString(R.string.title_setting_activity));
        radioGrouplanguage = findViewById(R.id.radioGroup_language);
        btnSave = findViewById(R.id.save_setting);
        switchDailyReminder = findViewById(R.id.switch_daily_reminder);
        switchReleaseMovieReminder = findViewById(R.id.switch_release_movie_reminder);
        mContext = getApplicationContext();

        sharedPref = mContext.getSharedPreferences("switch", Context.MODE_PRIVATE);


        switchOnclicked();
        checkedSwitch();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int seletedId = radioGrouplanguage.getCheckedRadioButtonId();
                radioButtonLanguage = findViewById(seletedId);

                if (seletedId == R.id.indonesia) {
                    newLocale = new Locale("in");
                } else if (seletedId == R.id.english) {
                    newLocale = new Locale("en");
                }

                switchOnclicked();

                Locale.setDefault(newLocale);
                Configuration config = new Configuration();
                config.locale = newLocale;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                Toast.makeText(getApplicationContext(), R.string.alert, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LanguageSettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void switchOnclicked() {

        switchDailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    sharPrefDayliReminder(1);
                    notifyDailyReminder();
                    Toast.makeText(getApplicationContext(), "Repeating alarm set up", Toast.LENGTH_SHORT).show();
                } else {
                    sharPrefDayliReminder(0);
                    dailyReminder.cancelAlarm(getApplicationContext());

                }
            }
        });

        switchReleaseMovieReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    sharPrefReleaseMovieliReminder(1);
                    releaseMovie.setRepeatingAlarm(getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Repeating alarm set up", Toast.LENGTH_SHORT).show();
                }else {
                    sharPrefReleaseMovieliReminder(0);
                    releaseMovie.cancelAlarm(getApplicationContext());
                }
            }
        });
    }

    public void checkedSwitch() {
       statusDailyReminder = sharedPref.getInt(getString(R.string.saveDailyReminder), defaultValueSharPref);
        statusReleaseMovieReminder = sharedPref.getInt(getString(R.string.saveReleaseMovieReminder), defaultValueSharPref);

       if (statusDailyReminder == 1) {
           switchDailyReminder.setChecked(true);

       }
       if (statusReleaseMovieReminder == 1) {
           switchReleaseMovieReminder.setChecked(true);
       }

    }

    public void notifyDailyReminder() {

        dailyReminder.setRepeatingAlarm(getApplicationContext());
    }

    public void sharPrefDayliReminder(int dailyReminder) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saveDailyReminder), dailyReminder);
        editor.apply();

    }

    public void sharPrefReleaseMovieliReminder(int releaseMovieReminder) {
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(getString(R.string.saveReleaseMovieReminder), releaseMovieReminder);
        editor.apply();
    }



    @Override
    protected void onResume() {
        super.onResume();

    }
}
