package com.xyuai.app;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FloatingService extends Service {

    private WindowManager windowManager;
    private View floatingButton;
    private LinearLayout menu;
    private boolean menuVisible = false;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            TextView button = new TextView(this);
            button.setText("X");
            button.setTextSize(22);
            button.setTextColor(Color.WHITE);
            button.setGravity(Gravity.CENTER);
            button.setBackgroundColor(Color.rgb(135, 53, 213));

            floatingButton = button;

            WindowManager.LayoutParams params =
                new WindowManager.LayoutParams(
                    120,
                    120,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
                );

            params.gravity = Gravity.TOP | Gravity.START;
            params.x = 30;
            params.y = 250;

            button.setOnClickListener(v -> toggleMenu());

            windowManager.addView(floatingButton, params);

        } catch (Exception e) {
            stopSelf();
        }
    }

    private void toggleMenu() {
        if (menuVisible) {
            windowManager.removeView(menu);
            menuVisible = false;
            return;
        }

        menu = new LinearLayout(this);
        menu.setOrientation(LinearLayout.VERTICAL);
        menu.setPadding(25, 25, 25, 25);
        menu.setBackgroundColor(Color.rgb(25, 25, 35));

        TextView title = new TextView(this);
        title.setText("XuyAI MENU");
        title.setTextSize(22);
        title.setTextColor(Color.rgb(170, 80, 255));
        menu.addView(title);

        TextView info = new TextView(this);
        info.setText("Демонстрационное меню");
        info.setTextColor(Color.WHITE);
        info.setPadding(0, 20, 0, 20);
        menu.addView(info);

        TextView close = new TextView(this);
        close.setText("ЗАКРЫТЬ");
        close.setTextColor(Color.WHITE);
        close.setPadding(10, 20, 10, 10);
        menu.addView(close);

        close.setOnClickListener(v -> toggleMenu());

        WindowManager.LayoutParams params =
            new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            );

        params.gravity = Gravity.CENTER;

        windowManager.addView(menu, params);
        menuVisible = true;
    }

    @Override
    public void onDestroy() {
        if (windowManager != null) {
            if (menu != null && menuVisible) {
                windowManager.removeView(menu);
            }

            if (floatingButton != null) {
                windowManager.removeView(floatingButton);
            }
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
