package com.example.users.cifri;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Cifri extends Activity {

    int SchPokaza=0;

    class Integ {
        private int i;
        public Integ(){i=0;}
        public int get(){
            return i;
        }
        public void add(int j){
            i=i+j;
        }
        public void set(int j){
            i=j;
        }
    }

    private DrawView.DrawThread drawThread=null;
    final Object sync = new Object();

    int Hei=10;
    int Wi=10;

    private SoundPool mSoundPool;
    int pip;
    int bom;
    int daun;
    int krestovina;
    int peremesh;
    int udvoen;
    int zasvet;
    int zatemnenie;

    private AssetManager mAssetManager;

    Integ ochki1 = new Integ();
    Integ ochki2 = new Integ();
    Integ ochkiAkt=ochki1;
    int[][] matr=new int[Wi][Hei];
    int[][] matrold=new int[Wi][Hei];

    boolean chelBool=false;
    boolean compBool=false;
    boolean igr1_igr2=false;
    boolean udvBool=false;
    boolean propBool=false;
    boolean chernBool=false;
    boolean chernClikBool=false;
    boolean ZvezdBool=false;
    boolean ZvezdClikBool=false;

    IJ_elem[] Daun=new IJ_elem[2];

    IJ_elem[] Bomba=new IJ_elem[2];
    boolean BombaBool=false;

    IJ_elem[] Podsvetka=new IJ_elem[2];
    boolean PodsvetkaBool=false;
    boolean PodsvetkaBoolClic=false;

    IJ_elem[] Peremesh=new IJ_elem[2];
    boolean PeremeshBool=false;

    boolean NastrBool=false;

    boolean vigral1=false;
    boolean vigral2=false;
    boolean konec=false;

    boolean zzz=false;

    int[] Per=new int[13];

    int schprop=0;

    Random rand=new Random();

    IJ_elem[] Vzriv=new IJ_elem[2];

    IJ_elem[] Min10=new IJ_elem[2];
    boolean Min10_bool=false;

    IJ_elem[] Min20=new IJ_elem[2];
    boolean Min20_bool=false;

    IJ_elem[] Min30=new IJ_elem[2];
    boolean Min30_bool=false;

    class IJ_elem{
        int i;
        int j;

        IJ_elem(int i,int j) {
            this.i=i;
            this.j=j;
        }

        public int getI()
        {
            return i;
        }
        public int getJ()
        {
            return j;
        }

        public void setIJ(int i,int j)
        {
            this.i=i;
            this.j=j;
        }
    }

    class IJ{
        private ArrayList<IJ_elem> ij;
        IJ() {
            ij = new ArrayList<>();
        }
        public void add(int i,int j)
        {
            ij.add(new  IJ_elem(i,j));
        }
        public void clear()
        {
            ij.clear();
        }
        public int size()
        {
            return ij.size();
        }
        public int getI(int i)
        {
            return ij.get(i).getI();
        }
        public int getJ(int i)
        {
            return ij.get(i).getJ();
        }
        public void copu(IJ ij)
        {
            for(int i=0;i<ij.size();i++)
            {
                this.add(ij.getI(i), ij.getJ(i));
            }
        }
    }

    private void playSound(int sound) {
        if (sound > 0)
            mSoundPool.play(sound, 1, 1, 1, 0, 1);
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd = null;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        if (drawThread!=null) {
            drawThread.SetStart();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (drawThread!=null) {
            drawThread.SetStop();
        }
        super.onPause();  // Always call the superclass method first
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Vzriv[0]=new IJ_elem(0,0);
        Vzriv[1]=new IJ_elem(0,0);

        Min10[0]=new IJ_elem(0,0);
        Min10[1]=new IJ_elem(0,0);

        Min20[0]=new IJ_elem(0,0);
        Min20[1]=new IJ_elem(0,0);

        Min30[0]=new IJ_elem(0,0);
        Min30[1]=new IJ_elem(0,0);

        Daun[0]=new IJ_elem(0,0);
        Daun[1]=new IJ_elem(0,0);

        Bomba[0]=new IJ_elem(0,0);
        Bomba[1]=new IJ_elem(0,0);

        Podsvetka[0]=new IJ_elem(0,0);
        Podsvetka[1]=new IJ_elem(0,0);

        Peremesh[0]=new IJ_elem(0,0);
        Peremesh[1]=new IJ_elem(0,0);

        Vzriv[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Min10[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Min20[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Min30[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Daun[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Bomba[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Podsvetka[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Peremesh[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));



        Vzriv[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Min10[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Min20[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Min30[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Daun[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Bomba[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Podsvetka[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        Peremesh[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));

        setContentView(R.layout.activity_cifri);

        Intent intent = getIntent();
        // Принимаем имя
        String igr = intent.getStringExtra("igr");

        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mAssetManager = getAssets();
        pip = loadSound("1.wav");
        bom = loadSound("bom.wav");
        daun= loadSound("daun.wav");
        krestovina= loadSound("krestovina.wav");
        peremesh= loadSound("peremesh.wav");
        udvoen= loadSound("udvoen.wav");
        zasvet= loadSound("zasvet.wav");
        zatemnenie= loadSound("zatemnenie.wav");

        if (igr.equals("comp"))
            compBool=true;
        if (igr.equals("chel"))
            chelBool=true;

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //для портретного режима

        ((RelativeLayout)findViewById(R.id.rty)).addView(new DrawView(this));

        formNach();
    }

    public void formNach() {
        for (int i=0;i<Wi;i++)
        {
            for (int j=0;j<Hei;j++)
            {
                matr[i][j]=0;
            }
        }
        ochki1.set(0);
        ochki2.set(0);
        ochkiAkt=ochki1;

        for (int aPer=0; aPer<Per.length; aPer++) {
            Per[aPer]=0;
        }
    }

    private int Okh(int k,IJ ij) {
        return k*ij.size();
    }

    private void obn_och(int k, Integ och,IJ ij,int[][] matr,int[][] matrold) {
        obn(k,ij,matr,matrold);
        if (k<64) {
            if (udvBool)
                och.add(2 * Okh(k, ij));
            else
                och.add(Okh(k, ij));
        }

        serRiski(och);
    }

    private void serRiski(Integ och) {
        if (och.get()>50 && Per[0] < 1)
        {
            if (och == ochki1) {
                Per[0] = 1;
            }
            else
            {
                Per[0] = 2;
            }
        }
        if (och.get()>100 && Per[1] < 1)
        {
            if (och == ochki1) {
                Per[1] = 1;
            }
            else
            {
                Per[1] = 2;
            }
        }
        if (och.get()>200 && Per[2] < 1)
        {
            if (och == ochki1) {
                Per[2] = 1;
            }
            else
            {
                Per[2] = 2;
            }
        }
        if (och.get()>400 && Per[3] < 1)
        {
            if (och == ochki1) {
                Per[3] = 1;
            }
            else
            {
                Per[3] = 2;
            }
        }
        if (och.get()>600 && Per[4] < 1)
        {
            if (och == ochki1) {
                Per[4] = 1;
            }
            else
            {
                Per[4] = 2;
            }
        }
        if (och.get()>800 && Per[5] < 1)
        {
            if (och == ochki1) {
                Per[5] = 1;
            }
            else
            {
                Per[5] = 2;
            }
        }
        if (och.get()>1000 && Per[6] < 1)
        {
            if (och == ochki1) {
                Per[6] = 1;
            }
            else
            {
                Per[6] = 2;
            }
        }
        if (och.get()>1500 && Per[7] < 1)
        {
            if (och == ochki1) {
                Per[7] = 1;
            }
            else
            {
                Per[7] = 2;
            }
        }
        if (och.get()>2000 && Per[8] < 1)
        {
            if (och == ochki1) {
                Per[8] = 1;
            }
            else
            {
                Per[8] = 2;
            }
        }
        if (och.get()>2500 && Per[9] < 1)
        {
            if (och == ochki1) {
                Per[9] = 1;
            }
            else
            {
                Per[9] = 2;
            }
        }
        if (och.get()>3000 && Per[10] < 1)
        {
            if (och == ochki1) {
                Per[10] = 1;
            }
            else
            {
                Per[10] = 2;
            }
        }
        if (och.get()>3500 && Per[11] < 1)
        {
            if (och == ochki1) {
                Per[11] = 1;
            }
            else
            {
                Per[11] = 2;
            }
        }
        if (och.get()>4000 && Per[12] < 1)
        {
            if (och == ochki1) {
                Per[12] = 1;
            }
            else
            {
                Per[12] = 2;
            }
        }


        int i1=0;
        int i2=0;

        for (int aPer : Per) {
            if (aPer == 1) {
                i1++;
            }
            if (aPer == 2) {
                i2++;
            }
        }

        if (i1>6)
        {
            vigral1=true;
            konec=true;
        }
        if (i2>6)
        {
            vigral2=true;
            konec=true;
        }
    }

    private void obn_och_Skil(int k, Integ och,IJ ij,int[][] matr,int[][] matrold){
        obnSkil(k,ij,matr,matrold);
        if (k<64) {
            if (udvBool)
                och.add(2 * Okh(k, ij));
            else
                och.add(Okh(k, ij));
        }
        serRiski(och);
    }

    private void obn(int k,IJ ij,int[][] matr,int[][] matrold) {
        for (int l=0;l<ij.size();l++)
        {
            int i=ij.getI(l);
            int j=ij.getJ(l);

            matrold[i][j]=matr[i][j];

            if (k==0) {
                if (rand.nextDouble() > 0.7)
                    matr[i][j] = 1;
                else
                    matr[i][j] = 0;
            }
            else
            {
                if (rand.nextDouble()>0.7)
                    matr[i][j]=k*2;
                else
                    matr[i][j]=k;
            }
        }
    }

    private void obnSkil(int k,IJ ij,int[][] matr,int[][] matrold) {
        double ver=1.4-7.0/ij.size();
        if (ver>0.7)
            ver=0.7;
        for (int l=0;l<ij.size();l++)
        {
            int i=ij.getI(l);
            int j=ij.getJ(l);

            matrold[i][j]=matr[i][j];

            if (k==0) {
                if (rand.nextDouble() > ver)
                    matr[i][j] = 1;
                else
                    matr[i][j] = 0;
            }
            else
            {
                if (rand.nextDouble()>ver)
                    matr[i][j]=k*2;
                else
                    matr[i][j]=k;
            }
        }
    }

    private void prov(int m,int n, IJ ij,int[][] matr) {

        IJ ig_lok=new IJ();

        for (int i=m-1;i<m+2;i++)
        {
            for (int j=n-1;j<n+2;j++)
            {
                if (i>-1 && j>-1 && !(i==m && j==n) && (i==m || j==n) && i<Wi && j<Hei)
                {
                    if (matr[i][j]==matr[m][n])
                    {
                        int nk=0;
                        for (int l=0;l<ij.size();l++)
                        {
                            if (ij.getI(l)==i && ij.getJ(l)==j)
                            {
                                nk=1;
                                break;
                            }
                        }

                        if (nk==0)
                        {
                            ij.add(i,j);
                            ig_lok.add(i,j);
                        }
                    }
                }
            }
        }

        for (int i=0;i<ig_lok.size();i++)
        {
            prov(ig_lok.getI(i),ig_lok.getJ(i),ij,matr);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cifri, menu);
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
            formNach();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onProvPop(RectF r, float x, float y){
        if (x >= r.left && x <= r.right &&
            y >= r.top  && y <= r.bottom) {
            return true;
        }
        else
            return false;
    }

    public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        Paint paint,paint1;
        Bitmap Nastr;
        Bitmap Skil1,Skil2,Skil3,Skil4,Skil5,Skil6;
        Bitmap Vzr;
        Rect[] rectVzr=new Rect[81];
        Rect rectNas,rectSkil;

        RectF[][] rectDst=new RectF[Wi][Hei];

        RectF PlayerRect1;
        RectF PlayerRect2;
        RectF NastrRect;
        RectF TimeRect;
        RectF KonecRect;

        RectF PeremScilRect1;
        RectF MinimScilRect2;
        RectF UdvoenScilRect3;
        RectF PropuskScilRect4;
        RectF ZasvetScilRect5;
        RectF ZatemnScilRect6;

        RectF ContiniuRect;
        RectF RESETRect;
        RectF Main_menuRect;

        Path p;

        double vis1;
        double vis3;
        double vis5;
        int vis4;

        Typeface font_ochki;
        Typeface font_text;

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                compSet();
            }
        };

        public DrawView(Context context) {
            super(context);

            p=new Path();
            p.moveTo(0,100);
            p.lineTo(100,0);
            p.lineTo(200,0);
            p.lineTo(100,100);
            p.close();

            paint1 = new Paint();

            getHolder().addCallback(this);

            paint = new Paint(Paint.ANTI_ALIAS_FLAG);

            font_ochki=Typeface.createFromAsset(getAssets(),"score.ttf");
            font_text=Typeface.createFromAsset(getAssets(),"numbers_and_text.otf");

            // настраиваем размер текста = 30
            paint.setTextSize(NachEkr.razmer_font_cifr);
            // настраиваем выравнивание текста на центр
            paint.setTextAlign(Paint.Align.CENTER);

            Nastr = BitmapFactory.decodeResource(getResources(), R.drawable.nastr);

            Skil1 = BitmapFactory.decodeResource(getResources(), R.drawable.skill_random);
            Skil2 = BitmapFactory.decodeResource(getResources(), R.drawable.skill_sum);
            Skil3 = BitmapFactory.decodeResource(getResources(), R.drawable.skill_x2);
            Skil4 = BitmapFactory.decodeResource(getResources(), R.drawable.skill_pass_3_step);
            Skil5 = BitmapFactory.decodeResource(getResources(), R.drawable.skill_frozen);
            Skil6 = BitmapFactory.decodeResource(getResources(), R.drawable.skill_dark);

            Vzr=BitmapFactory.decodeResource(getResources(), R.drawable.boom);

            int n=0;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    rectVzr[n] = new Rect(j*200, i*200, j*200+200, i*200+200);
                    n++;
                }
            }

            rectNas = new Rect(0, 0, Nastr.getWidth(), Nastr.getHeight());
            rectSkil = new Rect(0, 0, Skil1.getWidth(), Skil1.getHeight());

            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float x = event.getX();
                    float y = event.getY();

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: // нажатие
                            if (konec || zzz)
                                break;
                            if (NastrBool) {
                                if (onProvPop(ContiniuRect, x, y)) {
                                    NastrBool = false;
                                    break;
                                }
                                if (onProvPop(RESETRect, x, y)) {
                                    if (drawThread != null)
                                        drawThread.SetStop();
                                    formNach();
                                    IJ ij = new IJ();
                                    if (chelBool)
                                        obnNach(ij, true);
                                    else {
                                        obnNach(ij, true);
                                    }
                                    NastrBool = false;
                                    break;
                                }
                                if (onProvPop(Main_menuRect, x, y)) {
                                    finish();
                                    break;
                                }
                            }
                            else{
                                Work(x, y);
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

        private boolean provPasSkil(int i,int j,IJ_elem[] aun)
        {
            return (i == aun[0].getI() && j == aun[0].getJ()) || (i == aun[1].getI() && j == aun[1].getJ());
        }

        private void setPasSkil(IJ_elem[] aun)
        {
            aun[0].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));
            aun[1].setIJ((int) (rand.nextDouble() * Wi), (int) (rand.nextDouble() * Hei));
        }

        private void Work(float x, float y) {
            if (onProvPop(NastrRect, x, y)) {
                NastrBool = true;
                return;
            }
            if (provAktSkil(x, y)) return;

            int z = 0;
            for (int i = 0; i < Wi; i++) {
                for (int j = 0; j < Hei; j++) {
                    if (onProvPop(rectDst[i][j], x, y)) {
                        if (NachEkr.Zvuk)
                            playSound(pip);
                        if (drawThread != null)
                            drawThread.SetStop();
                        synchronized (sync) {
                            if (propBool) {
                                schprop++;
                                if (schprop > 3) {
                                    propBool = false;
                                    schprop = 0;
                                }
                            }

                            int vi = matr[i][j];
                            IJ ij = new IJ();

                            provPasSkil(i, j, vi, ij);

                            if (chernBool) {
                                chernClikBool = true;
                                chernBool = false;
                            }
                            if (ZvezdBool) {
                                ZvezdClikBool = true;
                                ZvezdBool = false;
                            }
                            if (ij.size() > 2 || BombaBool) {
                                if ((chelBool || propBool)&& PodsvetkaBoolClic) {
                                    obnNach(ij, true);
                                }
                                else
                                    obnNach(ij, false);
                            } else {
                                ij.clear();
                                if ((chelBool || propBool)&& PodsvetkaBoolClic)
                                    obnNach(ij, true);
                                else
                                    obnNach(ij, false);
                            }

                            if (chelBool && !propBool && !PodsvetkaBoolClic) {
                                igr1_igr2 = !igr1_igr2;

                                if (igr1_igr2)
                                    ochkiAkt = ochki2;
                                else
                                    ochkiAkt = ochki1;
                            } else {
                                if (!chelBool)
                                    ochkiAkt = ochki1;
                            }

                            if (PodsvetkaBoolClic)
                                PodsvetkaBoolClic=false;
                        }
                        z = 1;
                        break;
                    }
                }
                if (z == 1)
                    break;
            }
        }

        private boolean provAktSkil(float x, float y) {
            if (onProvPop(PeremScilRect1, x, y) && (ochkiAkt.get() >= 8)) {
                if (NachEkr.Zvuk)
                    playSound(peremesh);
                if (drawThread != null)
                    drawThread.SetStop();
                ochkiAkt.add(-8);
                for (int i = 0; i < Wi * Hei; i++) {
                    int i1 = (int) (rand.nextDouble() * Wi);
                    int i2 = (int) (rand.nextDouble() * Hei);

                    int i3 = (int) (rand.nextDouble() * Wi);
                    int i4 = (int) (rand.nextDouble() * Hei);

                    int o = matr[i1][i2];
                    matr[i1][i2] = matr[i3][i4];
                    matr[i3][i4] = o;
                }
                IJ ij = new IJ();
                if (chelBool)
                    obnNach(ij, true);
                else {
                    obnNach(ij, true);
                }
                return true;
            }

            if (onProvPop(MinimScilRect2, x, y) && (ochkiAkt.get() >= 64)) {
                if (drawThread != null)
                    drawThread.SetStop();
                ochkiAkt.add(-64);

                if (propBool) {
                    schprop++;
                    if (schprop > 3) {
                        propBool = false;
                        schprop = 0;
                    }
                }

                int min = 1000;
                for (int i = 0; i < Wi; i++) {
                    for (int j = 0; j < Hei; j++) {
                        if (matr[i][j] < min)
                            min = matr[i][j];
                    }
                }
                IJ ij = new IJ();
                for (int i = 0; i < Wi; i++) {
                    for (int j = 0; j < Hei; j++) {
                        if (matr[i][j] == min) {
                            ij.add(i, j);
                        }
                    }
                }

                if (min < 64)
                    obn_och_Skil(min, ochkiAkt, ij, matr, matrold);

                if (chelBool)
                    obnNach(ij, true);
                else {
                    obnNach(ij, false);
                    //compSet();
                }

                if (chelBool && !propBool) {
                    igr1_igr2 = !igr1_igr2;

                    if (igr1_igr2)
                        ochkiAkt = ochki2;
                    else
                        ochkiAkt = ochki1;
                } else {
                    if (!chelBool)
                        ochkiAkt = ochki1;
                }
                return true;
            }

            if (onProvPop(UdvoenScilRect3, x, y) && (ochkiAkt.get() >= 64)) {
                if (NachEkr.Zvuk)
                    playSound(udvoen);
                ochkiAkt.add(-64);
                udvBool = true;
                return true;
            }

            if (onProvPop(PropuskScilRect4, x, y) && (ochkiAkt.get() >= 64)) {
                ochkiAkt.add(-64);
                propBool = true;
                return true;
            }

            if (onProvPop(ZasvetScilRect5, x, y) && (ochkiAkt.get() >= 16)) {
                if (NachEkr.Zvuk)
                    playSound(zasvet);
                ochkiAkt.add(-16);
                ZvezdBool = true;
                return true;
            }

            if (onProvPop(ZatemnScilRect6, x, y) && (ochkiAkt.get() >= 32)) {
                if (NachEkr.Zvuk)
                    playSound(zatemnenie);
                ochkiAkt.add(-32);
                chernBool = true;
                return true;
            }
            return false;
        }

        private void provPasSkil(int i, int j, int vi, IJ ij) {
            if (provPasSkil(i, j, Vzriv)) {
                if (NachEkr.Zvuk)
                    playSound(krestovina);
                for (int ii = 0; ii < Wi; ii++) {
                    ij.add(ii, j);
                }
                for (int jj = 0; jj < Hei; jj++) {
                    ij.add(i, jj);
                }
                setPasSkil(Vzriv);
                return;
            }
            if (provPasSkil(i,j,Daun))
            {
                if (NachEkr.Zvuk)
                    playSound(daun);
                for (int ii = 0; ii < Wi; ii++) {
                    for (int jj = 0; jj < Hei; jj++) {
                        ij.add(ii, jj);
                        matrold[ii][jj]=matr[ii][jj];
                        matr[ii][jj]=matr[ii][jj]/2;
                    }
                }
                setPasSkil(Daun);
                return;
            }
            if (provPasSkil(i,j,Bomba))
            {
                if (NachEkr.Zvuk)
                    playSound(bom);
                ij.add(i, j);
                matrold[i][j]=matr[i][j];
                matr[i][j]=matr[i][j]*2;
                if (matr[i][j]==0)
                    matr[i][j]=1;
                int min = 1000;
                for (int ii = 0; ii < Wi; ii++) {
                    for (int jj = 0; jj < Hei; jj++) {
                        if (matr[ii][jj] < min)
                            min = matr[ii][jj];
                    }
                }
                for (int k = 1; k < Wi; k++) {
                    for (int ii = i - k; ii <= i + k; ii++) {
                        for (int jj = j - k; jj <= j + k; jj++) {
                            if (jj>=0 && ii>=0 && jj<Hei && ii<Wi)
                                if (matr[ii][jj] == min) {
                                    int nk=0;
                                    for (int l=0;l<ij.size();l++)
                                    {
                                        if (ij.getI(l)==ii && ij.getJ(l)==jj)
                                        {
                                            nk=1;
                                            break;
                                        }
                                    }

                                    if (nk==0)
                                    {
                                        ij.add(ii, jj);
                                        matrold[ii][jj]=matr[ii][jj];
                                        matr[ii][jj]=matr[ii][jj]*2;
                                        if (matr[ii][jj]==0)
                                            matr[ii][jj]=1;
                                    }
                                }
                        }
                    }
                }
                if (udvBool)
                    ochkiAkt.add(2 * Okh(min, ij));
                else
                    ochkiAkt.add(Okh(min, ij));
                BombaBool=true;
                setPasSkil(Bomba);
                return;
            }
            if (provPasSkil(i,j,Podsvetka)) {
                PodsvetkaBoolClic=true;
                PodsvetkaBool=true;
                ij.add(i, j);
                prov(i, j, ij, matr);
                if (ij.size() > 2)
                    obn_och(vi, ochkiAkt, ij, matr, matrold);
                setPasSkil(Podsvetka);
                return;
            }
            if (provPasSkil(i,j,Peremesh)) {
                if (NachEkr.Zvuk)
                    playSound(peremesh);
                PeremeshBool=true;
                for (int ii = 0; ii < Wi * Hei; ii++) {
                    int i1 = (int) (rand.nextDouble() * Wi);
                    int i2 = (int) (rand.nextDouble() * Hei);

                    int i3 = (int) (rand.nextDouble() * Wi);
                    int i4 = (int) (rand.nextDouble() * Hei);

                    int o = matr[i1][i2];
                    matr[i1][i2] = matr[i3][i4];
                    matr[i3][i4] = o;
                }
                setPasSkil(Peremesh);
                return;
            }

            if (provPasSkil(i,j,Min10)) {
                ochkiAkt.set((int) (ochkiAkt.get() * 0.9));
                setPasSkil(Min10);
                Min10_bool = true;
            }
            if (provPasSkil(i,j,Min20)) {
                ochkiAkt.set((int) (ochkiAkt.get() * 0.8));
                setPasSkil(Min20);
                Min20_bool = true;
            }
            if (provPasSkil(i,j,Min30)) {
                ochkiAkt.set((int) (ochkiAkt.get() * 0.7));
                setPasSkil(Min30);
                Min30_bool = true;
            }

            ij.add(i, j);
            prov(i, j, ij, matr);
            if (ij.size() > 2)
                obn_och(vi, ochkiAkt, ij, matr, matrold);
        }

        private void compSet() {
            int vi;ArrayList<Integer> maxi=new ArrayList<>();
            ArrayList<Integer> maxj=new ArrayList<>();
            ArrayList<Integer> maxsum=new ArrayList<>();

            maxsum.add(-1);
            maxj.add(-1);
            maxi.add(-1);

            int maxkol=2;

            IJ ij_loc=new IJ();

            for (int im = 0; im < Wi; im++) {
                for (int jm = 0; jm < Hei; jm++) {
                    int n=0;
                    for (int hj = 0; hj < ij_loc.size(); hj++) {
                        if (ij_loc.getI(hj)==im && ij_loc.getJ(hj)==jm)
                        {
                            n=1;
                            break;
                        }
                    }
                    if (n==0)
                    {
                        IJ ij=new IJ();
                        ij.add(im,jm);
                        prov(im, jm, ij,matr);
                        ij_loc.copu(ij);
                        if (ij.size() > maxkol) {
                            maxkol = ij.size();
                        }

                        if (ij.size()>2 && matr[im][jm]<64) {
                            int nn=-1;
                            for (int kl = 0; kl < maxsum.size(); kl++) {
                                if (Okh(matr[im][jm],ij) > maxsum.get(kl))
                                    nn=kl;
                            }
                            if (nn>-1) {
                                maxsum.add(nn+1,Okh(matr[im][jm], ij));
                                maxi.add(nn+1,im);
                                maxj.add(nn+1,jm);
                            }
                            else
                            {
                                maxsum.add(Okh(matr[im][jm], ij));
                                maxi.add(im);
                                maxj.add(jm);
                            }
                        }
                    }
                }
            }

            if (maxkol == 2) {
                Toast.makeText(getBaseContext(), "Кранты!",
                        Toast.LENGTH_SHORT).show();
                IJ ij=new IJ();
                obnNach(ij,true);
            }
            else
            {
                if (compBool && !propBool) {
                    int fg = 0;
                    if (maxi.size()>3)
                    {
                        if (NachEkr.Slognost.equals("eeasy"))
                            fg=maxi.size()-3;
                        if (NachEkr.Slognost.equals("normal"))
                            fg=maxi.size()-2;
                        if (NachEkr.Slognost.equals("hard"))
                            fg=maxi.size()-1;
                    }
                    if (maxi.size()==3)
                    {
                        if (NachEkr.Slognost.equals("eeasy"))
                            fg=maxi.size()-2;
                        if (NachEkr.Slognost.equals("normal"))
                            fg=maxi.size()-2;
                        if (NachEkr.Slognost.equals("hard"))
                            fg=maxi.size()-1;
                    }
                    if (maxi.size()==2)
                    {
                        fg=maxi.size()-1;
                    }
                    IJ ij=new IJ();
                    ij.add(maxi.get(fg),maxj.get(fg));
                    prov(maxi.get(fg), maxj.get(fg), ij,matr);
                    vi = matr[maxi.get(fg)][maxj.get(fg)];
                    obn_och(vi, ochki2,ij,matr,matrold);
                    obnNach(ij,true);
                }
            }
        }

        public void obnNach(IJ ij,boolean anim)
        {
            drawThread.obn(ij, ochki1, ochki2, matr, matrold, anim);
            drawThread.SetStart();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            int min=getWidth();

            double kNas=(double)Nastr.getHeight()/Nastr.getWidth();
            double kPl=0.35;

            vis1=0.02*min+kPl*0.37*min;

            PlayerRect1=new RectF((int)(0.02*min),(int)(0.02*min),(int)(0.02*min+0.37*min),(int)(0.02*min+kPl*0.37*min));
            PlayerRect2=new RectF((int)(0.61*min),(int)(0.02*min),(int)(0.61*min+0.37*min),(int)(0.02*min+kPl*0.37*min));

            NastrRect=new RectF((int)((0.5-0.08)*min),0,(int)((0.5+0.08)*min),(int)(kNas*0.16*min));
            TimeRect=new RectF((int)((0.5-0.07)*min),(int)(kNas*0.17*min),(int)((0.5+0.07)*min),(int)(kNas*0.17*min+kNas*0.14*min));

            vis3=vis1+0.02*min+kPl*0.30*min;

            vis4= (int) (vis3+0.05*min);

            int w1=min/Wi;
            int h1=min/Hei;

            for (int i=0;i<Wi;i++) {
                for (int j = 0; j < Hei; j++) {
                    rectDst[i][j] = new RectF(i * w1+1, vis4+j * h1+1, i * w1 + w1, vis4+j * h1 + h1);
                }
            }

            vis5= vis4+min;

            PeremScilRect1 =new RectF((int)(0.06*min),(int)(vis5+0.02*min),(int)(0.06*min+0.13*min),(int)(vis5+0.02*min+0.13*min));
            MinimScilRect2 =new RectF((int)(0.21*min),(int)(vis5+0.02*min),(int)(0.21*min+0.13*min),(int)(vis5+0.02*min+0.13*min));
            UdvoenScilRect3 =new RectF((int)(0.36*min),(int)(vis5+0.02*min),(int)(0.36*min+0.13*min),(int)(vis5+0.02*min+0.13*min));

            PropuskScilRect4 =new RectF((int)(0.51*min),(int)(vis5+0.02*min),(int)(0.51*min+0.13*min),(int)(vis5+0.02*min+0.13*min));
            ZasvetScilRect5 =new RectF((int)(0.66*min),(int)(vis5+0.02*min),(int)(0.66*min+0.13*min),(int)(vis5+0.02*min+0.13*min));
            ZatemnScilRect6 =new RectF((int)(0.81*min),(int)(vis5+0.02*min),(int)(0.81*min+0.13*min),(int)(vis5+0.02*min+0.13*min));

            int vis6= (int) ((vis4+vis5)/2);

            KonecRect=new RectF((int)((0.1)*min),(int)(vis6-(0.1)*min),(int)((0.9)*min),(int)(vis6+(0.1)*min));

            double kGam=0.25;

            double H= kGam*0.80*min;

            ContiniuRect=new RectF((int)(0.1*min),(int)(vis4+0.166*min-H/2),(int)(0.9*min),(int)(vis4+0.166*min+H/2));
            RESETRect=new RectF((int)(0.1*min),(int)(vis4+0.5*min-H/2),(int)(0.9*min),(int)(vis4+0.5*min+H/2));
            Main_menuRect=new RectF((int)(0.1*min),(int)(vis4+0.833*min-H/2),(int)(0.9*min),(int)(vis4+0.833*min+H/2));
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            int min=getWidth();

            double kNas=(double)Nastr.getHeight()/Nastr.getWidth();
            double kPl=0.35;

            vis1=0.02*min+kPl*0.37*min;

            PlayerRect1=new RectF((int)(0.02*min),(int)(0.02*min),(int)(0.02*min+0.37*min),(int)(0.02*min+kPl*0.37*min));
            PlayerRect2=new RectF((int)(0.61*min),(int)(0.02*min),(int)(0.61*min+0.37*min),(int)(0.02*min+kPl*0.37*min));

            NastrRect=new RectF((int)((0.5-0.08)*min),0,(int)((0.5+0.08)*min),(int)(kNas*0.16*min));
            TimeRect=new RectF((int)((0.5-0.07)*min),(int)(kNas*0.17*min),(int)((0.5+0.07)*min),(int)(kNas*0.17*min+kNas*0.14*min));

            vis3=vis1+0.02*min+kPl*0.30*min;

            vis4= (int) (vis3+0.05*min);

            int w1=min/Wi;
            int h1=min/Hei;

            for (int i=0;i<Wi;i++) {
                for (int j = 0; j < Hei; j++) {
                    rectDst[i][j] = new RectF(i * w1+1, vis4+j * h1+1, i * w1 + w1, vis4+j * h1 + h1);
                }
            }

            vis5= vis4+min;

            PeremScilRect1 =new RectF((int)(0.06*min),(int)(vis5+0.02*min),(int)(0.06*min+0.13*min),(int)(vis5+0.02*min+0.13*min));
            MinimScilRect2 =new RectF((int)(0.21*min),(int)(vis5+0.02*min),(int)(0.21*min+0.13*min),(int)(vis5+0.02*min+0.13*min));
            UdvoenScilRect3 =new RectF((int)(0.36*min),(int)(vis5+0.02*min),(int)(0.36*min+0.13*min),(int)(vis5+0.02*min+0.13*min));

            PropuskScilRect4 =new RectF((int)(0.51*min),(int)(vis5+0.02*min),(int)(0.51*min+0.13*min),(int)(vis5+0.02*min+0.13*min));
            ZasvetScilRect5 =new RectF((int)(0.66*min),(int)(vis5+0.02*min),(int)(0.66*min+0.13*min),(int)(vis5+0.02*min+0.13*min));
            ZatemnScilRect6 =new RectF((int)(0.81*min),(int)(vis5+0.02*min),(int)(0.81*min+0.13*min),(int)(vis5+0.02*min+0.13*min));

            int vis6= (int) ((vis4+vis5)/2);

            KonecRect=new RectF((int)((0.1)*min),(int)(vis6-(0.1)*min),(int)((0.9)*min),(int)(vis6+(0.1)*min));

            double kGam=0.25;

            double H= kGam*0.80*min;

            ContiniuRect=new RectF((int)(0.1*min),(int)(vis4+0.166*min-H/2),(int)(0.9*min),(int)(vis4+0.166*min+H/2));
            RESETRect=new RectF((int)(0.1*min),(int)(vis4+0.5*min-H/2),(int)(0.9*min),(int)(vis4+0.5*min+H/2));
            Main_menuRect=new RectF((int)(0.1*min),(int)(vis4+0.833*min-H/2),(int)(0.9*min),(int)(vis4+0.833*min+H/2));
            if (drawThread==null) {
                IJ ij = new IJ();
                drawThread = new DrawThread(holder);
                drawThread.setName("qwerty");
                drawThread.start();
                obnNach(ij, true);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (drawThread!=null) {
                drawThread.SetStop();
                drawThread.SetSTOP_ALL();
                boolean retry = true;
                while (retry) {
                    try {
                        drawThread.join();
                        drawThread=null;
                        retry = false;
                    } catch (InterruptedException e) {
                        // если не получилось, то будем пытаться еще и еще
                    }
                }
            }
        }

        public class DrawThread extends Thread {
            private final SurfaceHolder surfaceHolder;
            private long prevTime;
            private boolean stop=false;
            private boolean STOP_ALL=false;

            int sc;
            float s;
            float s1;
            float s2;
            boolean Anim=false;

            IJ ij_ =new IJ();
            Integ ochki1_ =new Integ();
            Integ ochki2_ =new Integ();
            int[][] matr_ =new int[Wi][Hei];
            int[][] matrold_ =new int[Wi][Hei];

            public DrawThread(SurfaceHolder surfaceHolder){
                this.surfaceHolder = surfaceHolder;
            }

            public void obn(IJ ij,Integ ochki1,Integ ochki2,int[][] matr,int[][] matrold,boolean anim){
                synchronized (sync) {
                    Anim = anim;
                    // сохраняем текущее время
                    prevTime = System.currentTimeMillis();
                    ij_.clear();
                    ij_.copu(ij);
                    ochki1_.set(ochki1.get());
                    ochki2_.set(ochki2.get());
                    for (int i = 0; i < Wi; i++) {
                        for (int j = 0; j < Hei; j++) {
                            matr_[i][j] = matr[i][j];
                            matrold_[i][j] = matrold[i][j];
                        }
                    }
                }
            }

            public void SetSTOP_ALL()
            {
                STOP_ALL=true;
            }

            public void SetStop()
            {
                stop=true;
            }

            public void SetStart()
            {
                stop=false;
            }

            @Override
            public void run(){
                while (!STOP_ALL) {
                    if (!stop) {
                        sc = 0;
                        synchronized (sync) {
                            drawCikl(sc);
                            if (Anim && !chernClikBool && !ZvezdClikBool && !konec) {
                                if (!stop && chelBool) {
                                    igr1_igr2 = !igr1_igr2;
                                    if (igr1_igr2)
                                        ochkiAkt = ochki2;
                                    else
                                        ochkiAkt = ochki1;
                                    ij_.clear();
                                    drawCikl(sc);
                                }
                                if (!stop && !chelBool) {
                                    SetStop();
                                    handler.sendEmptyMessage(0);
                                }
                            }
                            if (!Anim && !chelBool) {
                                SetStop();
                                handler.sendEmptyMessage(0);
                            }
                            chernClikBool = false;
                            ZvezdClikBool = false;
                            if (PodsvetkaBool) {
                                SchPokaza++;
                                if (SchPokaza>6) {
                                    PodsvetkaBool = false;
                                    SchPokaza = 0;
                                }
                            }
                        }
                    }
                }
            }

            private void drawCikl(int sc) {
                Canvas canvas;
                s = 1;
                s1 = 1;
                s2 = 360;
                zzz = true;
                int dfg;
                if (BombaBool) {
                    dfg= 500 + 81 + ij_.size();
                }
                else
                    dfg= 500;
                while (true) {
                    if (stop)
                        break;
                    if (sc >= dfg)
                        break;
                    // получаем текущее время и вычисляем разницу с предыдущим
                    // сохраненным моментом времени
                    long now = System.currentTimeMillis();
                    long elapsedTime = now - prevTime;
                    if (elapsedTime > 10) {
                        prevTime = now;
                        // если прошло больше 30 миллисекунд
                        if (!BombaBool && !NastrBool)
                            s2=s2-(float)360/500;
                        if (sc >= 20 && sc < 25)
                        {
                            s1= (float) (s1*0.9);
                        }
                        if (sc>=25 && sc<29)
                        {
                            s1= (float) (s1/0.9);
                        }
                        if (sc < 10) {
                            s = (float) (s * 0.8);
                        }
                        if (sc >= 10 && sc < 20) {
                            s = (float) (s / 0.8);
                        }
                        if (sc >= 20) {
                            if (!Anim && !chelBool)
                            {
                                if (!BombaBool) {
                                    break;
                                }
                            }
                            else
                            {
                                if (!BombaBool)
                                    zzz = false;
                            }
                        }

                        if (PodsvetkaBool && sc == 20)
                        {
                            ArrayList<Integer> maxi=new ArrayList<>();
                            ArrayList<Integer> maxj=new ArrayList<>();
                            ArrayList<Integer> maxsum=new ArrayList<>();

                            maxsum.add(-1);
                            maxj.add(-1);
                            maxi.add(-1);

                            int maxkol=2;

                            IJ ij_loc=new IJ();

                            for (int im = 0; im < Wi; im++) {
                                for (int jm = 0; jm < Hei; jm++) {
                                    int n=0;
                                    for (int hj = 0; hj < ij_loc.size(); hj++) {
                                        if (ij_loc.getI(hj)==im && ij_loc.getJ(hj)==jm)
                                        {
                                            n=1;
                                            break;
                                        }
                                    }
                                    if (n==0)
                                    {
                                        ij_=new IJ();
                                        ij_.add(im,jm);
                                        prov(im, jm, ij_,matr);
                                        ij_loc.copu(ij_);
                                        if (ij_.size() > maxkol) {
                                            maxkol = ij_.size();
                                        }

                                        if (ij_.size()>2 && matr[im][jm]<64) {
                                            int nn=-1;
                                            for (int kl = 0; kl < maxsum.size(); kl++) {
                                                if (Okh(matr[im][jm],ij_) > maxsum.get(kl))
                                                    nn=kl;
                                            }
                                            if (nn>-1) {
                                                maxsum.add(nn+1,Okh(matr[im][jm], ij_));
                                                maxi.add(nn+1,im);
                                                maxj.add(nn+1,jm);
                                            }
                                            else
                                            {
                                                maxsum.add(Okh(matr[im][jm], ij_));
                                                maxi.add(im);
                                                maxj.add(jm);
                                            }
                                        }
                                    }
                                }
                            }

                            if (maxkol == 2) {
//                                Toast.makeText(getBaseContext(), "Кранты!",
//                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                int fg=maxi.size()-1;
                                ij_=new IJ();
                                ij_.add(maxi.get(fg),maxj.get(fg));
                                prov(maxi.get(fg), maxj.get(fg), ij_,matr);
                            }
                        }

                        if(!NastrBool)
                            sc++;
                        canvas = null;
                        try {
                            // получаем объект Canvas и выполняем отрисовку
                            canvas = surfaceHolder.lockCanvas(null);
                            synchronized (surfaceHolder) {
                                // заливка канвы цветом
                                canvas.drawARGB(255, 255, 255, 255);
                                Rect textBounds = drawNach(canvas,s2,s1);
                                if (NastrBool) {
                                    paint.setColor(Color.BLACK);
                                    canvas.drawRoundRect(ContiniuRect, 50, 50, paint);
                                    canvas.drawRoundRect(RESETRect, 50, 50, paint);
                                    canvas.drawRoundRect(Main_menuRect, 50, 50, paint);

                                    paint.setColor(Color.WHITE);
                                    canvas.drawText("Continiu", ContiniuRect.centerX(), ContiniuRect.centerY() - textBounds.exactCenterY(), paint);
                                    canvas.drawText("RESET", RESETRect.centerX(), RESETRect.centerY() - textBounds.exactCenterY(), paint);
                                    canvas.drawText("Main menu", Main_menuRect.centerX(), Main_menuRect.centerY() - textBounds.exactCenterY(), paint);
                                    //stop=true;
                                    zzz=false;
                                }
                                else {
                                    for (int i = 0; i < Wi; i++) {
                                        for (int j = 0; j < Hei; j++) {
                                            int prov = matr_[i][j];

                                            canvas.save();

                                            int n = 0;
                                            int ii = 0;
                                            for (int l = 0; l < ij_.size(); l++) {
                                                if (ij_.getI(l) == i && ij_.getJ(l) == j) {
                                                    n = 1;
                                                    ii=l;
                                                    break;
                                                }
                                            }

                                            if (n == 1 && prov < 64) {
                                                canvas.scale(s, s, rectDst[i][j].centerX(), rectDst[i][j].centerY());
                                                if (sc<10 && !BombaBool)
                                                    prov = matrold_[i][j];

                                                if (BombaBool)
                                                    prov = matrold_[i][j];

                                                if (BombaBool && (1 - ii + sc) > -1)
                                                {
                                                    prov = matr_[i][j];
                                                }
                                            }

                                            if (chernClikBool)
                                                prov = 888;

                                            if (ZvezdClikBool && n == 1)
                                                prov = 999;

                                            paint.setColor(Color.BLACK);

                                            dravElem(canvas, textBounds, i, j, prov, n);

                                            canvas.restore();
                                        }
                                    }

                                    if (BombaBool) {
                                        int y=sc;
                                        for (int ii = 1; ii < ij_.size(); ii++) {
                                            if ((1 - ii + y) > -1 && (1 - ii + y) < 81)
                                                canvas.drawBitmap(Vzr, rectVzr[(1 - ii + y)], rectDst[ij_.getI(ii)][ij_.getJ(ii)], paint);
                                        }
                                        if ((y - ij_.size()) >= 81)
                                        {
                                            if (!Anim && !chelBool)
                                            {
                                                break;
                                            }
                                            else
                                            {
                                                BombaBool=!BombaBool;
                                                zzz = false;
                                            }
                                        }
                                    }

                                    paint.setTextSize(NachEkr.razmer_font_cifr * 7);
                                    Rect textBound = new Rect(); //don't new this up in a draw method
                                    paint.getTextBounds("N", 0, "N".length(), textBound);
                                    if (BombaBool)
                                    {
                                        int y=sc;
                                        if (sc<rectVzr.length) {
                                            RectF tyu = new RectF(rectDst[ij_.getI(0)][ij_.getJ(0)].left - rectDst[ij_.getI(0)][ij_.getJ(0)].width(),
                                                    rectDst[ij_.getI(0)][ij_.getJ(0)].top - rectDst[ij_.getI(0)][ij_.getJ(0)].height(),
                                                    rectDst[ij_.getI(0)][ij_.getJ(0)].right + rectDst[ij_.getI(0)][ij_.getJ(0)].height(),
                                                    rectDst[ij_.getI(0)][ij_.getJ(0)].bottom + rectDst[ij_.getI(0)][ij_.getJ(0)].height());
                                            canvas.drawBitmap(Vzr, rectVzr[y], tyu, paint);
                                        }
                                    }
                                    if (Min10_bool && sc < 100) {
                                        paint.setColor(16777215);
                                        paint.setAlpha(255 - 2*sc);
                                        canvas.drawText("-10", KonecRect.centerX(), KonecRect.centerY() - textBound.exactCenterY(), paint);
                                    }
                                    if (Min20_bool && sc < 100) {
                                        paint.setColor(16777215);
                                        paint.setAlpha(255 - 2*sc);
                                        canvas.drawText("-20", KonecRect.centerX(), KonecRect.centerY() - textBound.exactCenterY(), paint);
                                    }
                                    if (Min30_bool && sc < 100) {
                                        paint.setColor(16777215);
                                        paint.setAlpha(255 - 2*sc);
                                        canvas.drawText("-30", KonecRect.centerX(), KonecRect.centerY() - textBound.exactCenterY(), paint);
                                    }
                                    paint.setTextSize(NachEkr.razmer_font_cifr);
                                    if (konec) {
                                        paint.setColor(Color.BLACK);
                                        canvas.drawRoundRect(KonecRect, 20, 20, paint);
                                        paint.setColor(Color.WHITE);
                                        if (vigral1) {
                                            if (chelBool)
                                                canvas.drawText("Выйграл 1", KonecRect.centerX(), KonecRect.centerY() - textBounds.exactCenterY(), paint);
                                            else
                                                canvas.drawText("Выйграли Вы", KonecRect.centerX(), KonecRect.centerY() - textBounds.exactCenterY(), paint);
                                        } else {
                                            if (chelBool)
                                                canvas.drawText("Выйграл 2", KonecRect.centerX(), KonecRect.centerY() - textBounds.exactCenterY(), paint);
                                            else
                                                canvas.drawText("Выйграл компьютер", KonecRect.centerX(), KonecRect.centerY() - textBounds.exactCenterY(), paint);
                                        }
                                    }
                                }
                            }
                        } finally {
                            if (canvas != null) {
                                // отрисовка выполнена. выводим результат на экран
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                }
                if (Min10_bool)
                    Min10_bool=false;
                if (Min20_bool)
                    Min20_bool=false;
                if (Min30_bool)
                    Min30_bool=false;
                if (BombaBool)
                    BombaBool=false;
            }

            private Rect drawNach(Canvas canvas,float s,float s1) {
                Rect textBounds = new Rect();
                paint.getTextBounds("N", 0, "N".length(), textBounds);
                paint.setTypeface(font_ochki);
                paint.setColor(Color.BLACK);
                canvas.drawText(String.valueOf(ochki1_.get()), PlayerRect1.centerX(), (float) vis3, paint);
                canvas.drawText(String.valueOf(ochki2_.get()), PlayerRect2.centerX(), (float) vis3, paint);
                paint.setTypeface(font_text);
                canvas.save();
                if (chelBool){
                    if (igr1_igr2){
                        canvas.scale(s1,s1, PlayerRect2.centerX(), PlayerRect2.centerY());
                    }
                    else
                    {
                        canvas.scale((float)0.7,(float)0.7, PlayerRect2.centerX(), PlayerRect2.centerY());
                    }
                }
                canvas.drawRoundRect(PlayerRect2, 20, 20, paint);
                canvas.restore();
                canvas.save();
                if (chelBool){
                    if (!igr1_igr2){
                        canvas.scale(s1,s1, PlayerRect1.centerX(), PlayerRect1.centerY());
                    }
                    else
                    {
                        canvas.scale((float)0.7,(float)0.7, PlayerRect1.centerX(), PlayerRect1.centerY());
                    }
                }
                canvas.drawRoundRect(PlayerRect1, 20, 20, paint);
                canvas.restore();
                canvas.drawText(String.valueOf((int)(5*s/360+0.8)), TimeRect.centerX(), TimeRect.centerY()- textBounds.exactCenterY(), paint);
                paint.setColor(-2236963);
                for (int i=1;i<7+1;i++) {
                    canvas.save();
                    canvas.translate(PlayerRect1.left + (PlayerRect1.right - PlayerRect1.left) * i/7, (float) (PlayerRect1.bottom + PlayerRect1.height() * 0.1));
                    canvas.scale(-(float) (NachEkr.scale / 8 * 0.75), (float) (NachEkr.scale / 6 * 0.75));
                    canvas.drawPath(p, paint);
                    canvas.restore();
                }
                for (int i=1;i<7+1;i++) {
                    canvas.save();
                    canvas.translate(PlayerRect2.right-(PlayerRect2.right-PlayerRect2.left)*i/7, (float)(PlayerRect2.bottom+PlayerRect2.height()*0.1));
                    canvas.scale((float)(NachEkr.scale/8*0.75),(float)(NachEkr.scale/6*0.75));
                    canvas.drawPath(p,paint);
                    canvas.restore();
                }
                paint.setColor(Color.BLACK);
                int i1=0;
                int i2=0;
                for (int aPer : Per) {
                    if (aPer == 1) {
                        i1++;
                        canvas.save();
                        canvas.translate(PlayerRect1.left + (PlayerRect1.right - PlayerRect1.left) * i1 / 7, (float) (PlayerRect1.bottom + PlayerRect1.height() * 0.1));
                        canvas.scale(-(float) (NachEkr.scale / 8 * 0.75), (float) (NachEkr.scale / 6 * 0.75));
                        canvas.drawPath(p, paint);
                        canvas.restore();
                    }
                    if (aPer == 2) {
                        i2++;
                        canvas.save();
                        canvas.translate(PlayerRect2.right - (PlayerRect2.right - PlayerRect2.left) * i2 / 7, (float) (PlayerRect2.bottom + PlayerRect2.height() * 0.1));
                        canvas.scale((float) (NachEkr.scale / 8 * 0.75), (float) (NachEkr.scale / 6 * 0.75));
                        canvas.drawPath(p, paint);
                        canvas.restore();
                    }
                }
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(9);
                canvas.drawArc(TimeRect, -90, s, false, paint);
                paint.setStrokeWidth(20);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.WHITE);
                canvas.save();
                if (chelBool){
                    if (igr1_igr2){
                        canvas.scale(s1,s1, PlayerRect2.centerX(), PlayerRect2.centerY());
                    }
                    else
                    {
                        canvas.scale((float)0.7,(float)0.7, PlayerRect2.centerX(), PlayerRect2.centerY());
                    }
                }
                if (chelBool)
                    canvas.drawText("Player2", PlayerRect2.centerX(), PlayerRect2.centerY()- textBounds.exactCenterY(), paint);
                else
                    canvas.drawText("Comp", PlayerRect2.centerX(), PlayerRect2.centerY() - textBounds.exactCenterY(), paint);
                canvas.restore();
                canvas.save();
                if (chelBool){
                    if (!igr1_igr2){
                        canvas.scale(s1,s1, PlayerRect1.centerX(), PlayerRect1.centerY());
                    }
                    else
                    {
                        canvas.scale((float)0.7,(float)0.7, PlayerRect1.centerX(), PlayerRect1.centerY());
                    }
                }
                canvas.drawText(NachEkr.Name, PlayerRect1.centerX(), PlayerRect1.centerY()- textBounds.exactCenterY(), paint);
                canvas.restore();
                canvas.drawBitmap(Nastr, rectNas, NastrRect, paint);
                if(!NastrBool) {
                    if (ochkiAkt.get() >= 8)
                        canvas.drawBitmap(Skil1, rectSkil, PeremScilRect1, paint);
                    if (ochkiAkt.get() >= 64)
                        canvas.drawBitmap(Skil2, rectSkil, MinimScilRect2, paint);
                    if (ochkiAkt.get() >= 64)
                        canvas.drawBitmap(Skil3, rectSkil, UdvoenScilRect3, paint);
                    if (ochkiAkt.get() >= 64)
                        canvas.drawBitmap(Skil4, rectSkil, PropuskScilRect4, paint);
                    if (ochkiAkt.get() >= 16)
                        canvas.drawBitmap(Skil5, rectSkil, ZasvetScilRect5, paint);
                    if (ochkiAkt.get() >= 32)
                        canvas.drawBitmap(Skil6, rectSkil, ZatemnScilRect6, paint);
                }
                return textBounds;
            }

            private void dravElem(Canvas canvas, Rect textBounds, int i, int j, int prov, int n) {
                switch (prov) {
                    case 0: // нажатие
                        paint.setColor(Color.BLACK);
                        break;
                    case 1: // нажатие
                        paint.setColor(Color.BLACK);
                        break;
                    case 2: // нажатие
                        paint.setColor(-10066432);
                        break;
                    case 4: // нажатие
                        paint.setColor(-13408768);
                        break;
                    case 8: // нажатие
                        paint.setColor(-6724096);
                        break;
                    case 16: // нажатие
                        paint.setColor(-16764058);
                        break;
                    case 32: // нажатие
                        paint.setColor(-6750208);
                        break;
                    case 888: // нажатие
                        paint.setColor(Color.BLACK);
                        break;
                    case 999: // нажатие
                        paint.setColor(Color.WHITE);
                        break;
                }
                canvas.drawRoundRect(rectDst[i][j], 10, 10, paint);

                if (PodsvetkaBool && n==1)
                    paint.setColor(Color.RED);
                else
                    paint.setColor(Color.WHITE);
                if (prov!=888 && prov!=999 && prov<64)
                    canvas.drawText(String.valueOf(prov), rectDst[i][j].centerX(), rectDst[i][j].centerY()- textBounds.exactCenterY(), paint);
            }
        }
    }
}
