package com.example.users.cifri;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;


public class NachEkr extends Activity {

    public static SharedPreferences mSettings;
    public static String Slognost;
    public static String Name;
    public static boolean Zvuk;

    private static final float razmer_dp_font_nachekr = 50.0f;
    public static int razmer_font_nachekr;

    private static final float razmer_dp_font_cifr = 30.0f;
    public static int razmer_font_cifr;

    public static float scale;

    RectF ExitRect;
    RectF NewGameRect;
    RectF OptionsRect;

    DrawView draw;
    RelativeLayout vd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nach_ekr);

        mSettings=getSharedPreferences("settings",Context.MODE_PRIVATE);
        Name=mSettings.getString("name","Player1");
        Slognost=mSettings.getString("slognost","eeasy");
        Zvuk=mSettings.getBoolean("zvuk",true);

        scale = this.getResources().getDisplayMetrics().density;
        razmer_font_nachekr = (int) (razmer_dp_font_nachekr * scale + 0.5f);
        razmer_font_cifr= (int) (razmer_dp_font_cifr * scale + 0.5f);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //для портретного режима

        vd=(RelativeLayout)findViewById(R.id.rel);

        draw=new DrawView(this);
        vd.addView(draw);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nach_ekr, menu);
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

    public boolean onProvPop(RectF r, float x, float y){
        if (x > r.left && x <= r.right &&
                y > r.top  && y <= r.bottom) {
            return true;
        }
        else
            return false;
    }

    public class DrawView extends SurfaceView implements SurfaceHolder.Callback {
        private DrawThread drawThread=null;
        Paint paint;

        public DrawView(Context context) {
            super(context);

            getHolder().addCallback(this);

            paint = new Paint(Paint.ANTI_ALIAS_FLAG);

            String font="numbers_and_text.otf";
            Typeface CF=Typeface.createFromAsset(getAssets(),font);
            paint.setTypeface(CF);

            paint.setTextSize(razmer_font_nachekr);
            paint.setTextAlign(Paint.Align.CENTER);

            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float x = event.getX();
                    float y = event.getY();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: // нажатие
                            if (onProvPop(NewGameRect,x,y))
                            {
                                Intent intent = new Intent(getContext(), Vibr_Igroka.class);
                                startActivity(intent);
                                break;
                            }
                            if (onProvPop(OptionsRect,x,y))
                            {
                                Intent intent = new Intent(getContext(), Options.class);
                                startActivity(intent);
                                break;
                            }
                            if (onProvPop(ExitRect,x,y))
                            {
                                finish();
                                break;
                            }
                            break;
                        case MotionEvent.ACTION_MOVE: // движение
                            break;
                        case MotionEvent.ACTION_UP: // отпускание
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            break;
                    }
                    return true;
                }
            });
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            int min1=getWidth();
            int min2=getHeight();

            double kGam=0.25;

            double H= kGam*0.80*min1;


            NewGameRect=new RectF((int)(0.1*min1),(int)(0.2*min2),(int)(0.1*min1+0.8*min1),(int)(0.2*min2+H));
            OptionsRect=new RectF((int)(0.1*min1),(int)(0.5*min2-H/2),(int)(0.1*min1+0.8*min1),(int)(0.5*min2+H/2));
            ExitRect=new RectF((int)(0.1*min1),(int)(0.8*min2-H),(int)(0.1*min1+0.8*min1),(int)(0.8*min2));
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder(), getResources());
            drawThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            while (retry) {
                try {
                    if (drawThread!=null)
                        drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    // если не получилось, то будем пытаться еще и еще
                }
            }
        }

        class DrawThread extends Thread{
            private final SurfaceHolder surfaceHolder;

            public DrawThread(SurfaceHolder surfaceHolder, Resources resources){
                this.surfaceHolder = surfaceHolder;
            }


            @Override
            public void run() {

                Canvas canvas;
                canvas = null;
                try {
                    // получаем объект Canvas и выполняем отрисовку
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        // заливка канвы цветом
                        canvas.drawARGB(255, 255, 255, 255);

                        paint.setColor(Color.BLACK);

                        canvas.drawRoundRect(NewGameRect, 50, 50, paint);
                        canvas.drawRoundRect(OptionsRect, 50, 50, paint);
                        canvas.drawRoundRect(ExitRect, 50, 50, paint);

                        Rect textBounds = new Rect(); //don't new this up in a draw method
                        paint.getTextBounds("N", 0, "N".length(), textBounds);

                        paint.setColor(Color.WHITE);
                        canvas.drawText("New Game", NewGameRect.centerX(), NewGameRect.centerY()- textBounds.exactCenterY(), paint);
                        canvas.drawText("Options", OptionsRect.centerX(), OptionsRect.centerY()- textBounds.exactCenterY(), paint);
                        canvas.drawText("Exit", ExitRect.centerX(), ExitRect.centerY()- textBounds.exactCenterY(), paint);
                    }
                } finally {
                    if (canvas != null) {
                        // отрисовка выполнена. выводим результат на экран
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                drawThread = null;
            }
        }
    }
}
