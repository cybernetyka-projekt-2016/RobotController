package pg.gda.org.robotcontroller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {

    private EditText camera;
    private EditText chart;
    private EditText websocket;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = (EditText)findViewById(R.id.camera_ip);
        chart = (EditText)findViewById(R.id.chart_ip);
        websocket = (EditText)findViewById(R.id.websocket_ip);
        startBtn = (Button)findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHtmlViewsActivity();
            }
        });

    }

    private void startHtmlViewsActivity() {
        if(validateFields()){
            saveIpsToSharedPrefs();
            Intent intent = new Intent(this, HtmlViews.class);
            startActivity(intent);
        }
    }

    private void saveIpsToSharedPrefs() {
        SharedPreferences.Editor editor = getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(AppConstants.CAMERA_IP, camera.getText().toString());
        editor.putString(AppConstants.CHART_IP, chart.getText().toString());
        editor.putString(AppConstants.WEBSOCKET_IP, websocket.getText().toString());
        editor.commit();
    }

    private boolean validateFields() {
        if(!camera.getText().toString().equals("")){
            if(!chart.getText().toString().equals("")){
                if(!websocket.getText().toString().equals("")){
                    return true;
                }
            }
        }
        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        return false;
    }


}
