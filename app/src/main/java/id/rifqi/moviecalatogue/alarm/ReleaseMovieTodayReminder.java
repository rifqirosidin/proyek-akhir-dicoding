package id.rifqi.moviecalatogue.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import id.rifqi.moviecalatogue.R;
import id.rifqi.moviecalatogue.api.MovieResponse;
import id.rifqi.moviecalatogue.api.RetrofitClient;
import id.rifqi.moviecalatogue.api.WebService;
import id.rifqi.moviecalatogue.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseMovieTodayReminder extends BroadcastReceiver {

    private final int requestCode = 102;
    private static int notifId;
    int notifyDelay;
    ArrayList<Movie> movies = new ArrayList<>();
    AlarmManager alarmManager;
    Calendar calendar;

       @Override
    public void onReceive(Context context, Intent intent) {

        String message = context.getString(R.string.release_movie_today);
        getReleaseMovieToday(context, message);

    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_Release_Movie_Today";
        String CHANNEL_NAME = "AlarmManager channel";

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_app)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME
                    , NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000,1000,1000,1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);

            }

            Notification notification = builder.build();

            if (notificationManagerCompat != null){

                notificationManagerCompat.notify(notifId, notification);
            }
        }

    }
    public void setRepeatingAlarm(Context context) {

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            Intent intent = new Intent(context, ReleaseMovieTodayReminder.class);
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 30);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (alarmManager != null)
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , AlarmManager.INTERVAL_DAY, pendingIntent);



    }

    private PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, ReleaseMovieTodayReminder.class);
        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
        Toast.makeText(context, "Repeating alarm dibatalkan", Toast.LENGTH_SHORT).show();
    }

    public void getReleaseMovieToday(final Context context, final String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateRelease = dateFormat.format(date);
        WebService api = RetrofitClient.getClient().create(WebService.class);
        Call<MovieResponse> call = api.getReleaseMovieToday(dateRelease, dateRelease);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    movies = response.body().getResults();
                    for (int i=0; i < movies.size(); i++){
                      showAlarmNotification(context, movies.get(i).getTitle(),message, notifId);
                      notifId++;

                    }

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
