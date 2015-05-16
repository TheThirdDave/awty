package edu.washington.phida11.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private PendingIntent alarmIntent = null;
    private AlarmManager am;
    private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, "(425) 555-1212: Are we there yet?", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.startandstop);

        final EditText message = (EditText) findViewById(R.id.editMessage);
        final EditText number = (EditText) findViewById(R.id.editNumber);
        final EditText timeBetween = (EditText) findViewById(R.id.editTimeBetween);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("Start")) {
                    if (!message.getText().toString().equals("") && !number.getText().toString().equals("") && !timeBetween.getText().toString().equals("")) {
                        int time = 0;
                        try {
                            time = Integer.parseInt(timeBetween.getText().toString());
                        } catch (NumberFormatException nfe) {
                            System.out.println("Could not parse " + nfe);
                        }
                        if (time > 0) {

                            button.setText("Stop");
                            //Log.i("wtf", message.getText().toString());
                            //Log.i("wtf", number.getText().toString());
                            //Log.i("wtf", timeBetween.getText().toString());

                            registerReceiver(alarmReceiver, new IntentFilter("edu.washington.phida11.annoyingalarm"));

                            am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                            Intent i = new Intent();
                            i.setAction("edu.washington.phida11.annoyingalarm");
                            alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);

                            am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + time * 60000, time * 60000, alarmIntent);
                        }
                    }
                } else {
                    button.setText("Start");
                    am.cancel(alarmIntent);
                    alarmIntent.cancel();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmIntent != null) {
            am.cancel(alarmIntent);
            alarmIntent.cancel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
