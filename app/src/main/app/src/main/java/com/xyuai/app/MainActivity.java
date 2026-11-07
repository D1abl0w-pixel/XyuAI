package com.xyuai.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.rgb(16, 16, 20));

        TextView title = new TextView(this);
        title.setText("XuyAI");
        title.setTextSize(38);
        title.setTextColor(Color.rgb(170, 80, 255));
        layout.addView(title);

        Button start = new Button(this);
        start.setText("ACTIVATE XuyAI");
        layout.addView(start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.canDrawOverlays(MainActivity.this)) {
                    startService(new Intent(
                        MainActivity.this,
                        FloatingService.class
                    ));
                } else {
                    Intent intent = new Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName())
                    );
                    startActivity(intent);
                }
            }
        });

        setContentView(layout);
    }
}
