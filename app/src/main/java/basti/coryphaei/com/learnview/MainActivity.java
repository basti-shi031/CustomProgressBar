package basti.coryphaei.com.learnview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private RingView ringView;
    private Handler mHandler;
    private int progress = 0;
    private Button startButton;
    private int clikeTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ringView = (RingView) findViewById(R.id.ringview);
        startButton = (Button) findViewById(R.id.start);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == clikeTime){
                    if (progress<=100||progress>=0){
                        ringView.setProgress(progress);

                        int increament = (int) (Math.random()*5);

                        progress += increament;

                        Message message = Message.obtain();
                        message.what = clikeTime;
                        mHandler.sendMessageDelayed(message, 100);
                    }
                }
            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = 0;
                clikeTime++;
                Message message = Message.obtain();
                message.what = clikeTime;
                mHandler.sendMessageDelayed(message, 100);
            }
        });
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
