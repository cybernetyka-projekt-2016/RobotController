package pg.gda.org.robotcontroller;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class SonarChartFragment extends Fragment {

    private WebView webView;

    public SonarChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sonar_chart, container, false);

        webView = (WebView)view.findViewById(R.id.sonar_web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getSharedPrefValue(AppConstants.CHART_IP));

        return view;
    }


    private String getSharedPrefValue(String key) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
