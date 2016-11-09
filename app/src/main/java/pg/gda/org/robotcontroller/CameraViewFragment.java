package pg.gda.org.robotcontroller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import io.github.controlwear.virtual.joystick.android.JoystickView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraViewFragment extends Fragment {

    private WebView webView;
    private WebSocketClient mWebSocketClient;
    private JoystickView joystickView;

    public CameraViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera_view, container, false);

        webView = (WebView)view.findViewById(R.id.web_view);
        joystickView = (JoystickView)view.findViewById(R.id.joystick);

        connectWebSocket();

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getSharedPrefValue(AppConstants.CAMERA_IP));

        joystickView.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                if (mWebSocketClient != null && mWebSocketClient.getConnection().isOpen()) {
                    mWebSocketClient.send(calculateJoystickCoordinates(angle, strength));
                }
            }
        });
        return view;
    }

    private String calculateJoystickCoordinates(int angle, int strength) {
        int x = Double.valueOf(Math.cos(Math.toRadians(angle)) * strength*4).intValue();
        int y = Double.valueOf(Math.sin(Math.toRadians(angle)) * strength*4).intValue();
        return String.format("{\"x\":\"%d\",\"y\":\"%d\"}", x, y);
    }

    private String getSharedPrefValue(String key) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI(getSharedPrefValue(AppConstants.WEBSOCKET_IP));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(final String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

}
