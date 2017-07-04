package com.keshav.appsflyerTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button mInAppEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppsFlyerLib.getInstance().startTracking(getApplication(), "owRQoQ79YXaeXDWetWoxZg");

        mInAppEventButton = (Button) findViewById(R.id.purchaseButton);
        mInAppEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> eventValue = new HashMap<String, Object>();
                eventValue.put(AFInAppEventParameterName.REVENUE, 500);
                eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "category_a");
                eventValue.put(AFInAppEventParameterName.CONTENT_ID, "1234567");
                eventValue.put(AFInAppEventParameterName.CURRENCY, "INR");
                AppsFlyerLib.getInstance().trackEvent(MainActivity.this, AFInAppEventType.PURCHASE, eventValue);
            }
        });

        AppsFlyerLib.getInstance().registerConversionListener(this, new AppsFlyerConversionListener() {

            @Override
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d(AppsFlyerLib.LOG_TAG, "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
                //SCREEN VALUES//
                final String install_type = "INSTALL TYPE: " + conversionData.get("af_status");
                final String install_message = "MESSAGE: " + conversionData.get("af_message");
                runOnUiThread(new Runnable() {
                    public void run() {
                        ((TextView) findViewById(R.id.helloWorld)).setText(install_type + "\n" + install_message);
                    }
                });
            }

            @Override
            public void onInstallConversionFailure(String s) {
                Log.d(AppsFlyerLib.LOG_TAG, "error getting conversion data: " + s);
                ((TextView) findViewById(R.id.helloWorld)).setText(s);

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {

            }

            @Override
            public void onAttributionFailure(String s) {
                Log.d(AppsFlyerLib.LOG_TAG, "error onAttributionFailure : " + s);

            }
        });

    }


}
