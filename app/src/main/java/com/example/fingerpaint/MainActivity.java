package com.example.fingerpaint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fingerpaint.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
            implements View.OnTouchListener {

    Canvas canvas;
    Bitmap bitmap;
    Paint paint = new Paint();
    Path path = new Path();
    float x1, y1;
    int w, h;
    float fontSize = 10f;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size =new Point();
        display.getSize(size);
        w = size.x;
        h = size.y;

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(fontSize);
        paint.setStyle(Paint.Style.STROKE);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas. drawColor(Color.WHITE);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
/*
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

 */
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
        /*
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


         */
        switch (item.getItemId()){
            case R.id.menu_font_size_s:
                fontSize = 5f;
                paint.setStrokeWidth(fontSize);
                return true;
            case R.id.menu_font_size_m:
                fontSize = 10f;
                paint.setStrokeWidth(fontSize);
                return true;
            case R.id.menu_font_size_l:
                fontSize = 15f;
                paint.setStrokeWidth(fontSize);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
/*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
 */

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
            if (keyCode == KeyEvent.KEYCODE_BACK ) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_exit)
                        .setPositiveButton(R.string.button_ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                        .setNegativeButton(R.string.button_cancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                        .show();
                return true;
            }
            return  super.onKeyDown(keyCode, keyEvent);
        }
        // タッチイベント
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path. reset();
                    path. moveTo(x, y);
                    x1 = x;
                    y1 = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.quadTo(x1, y1, x, y);
                    x1 = x;
                    y1 = y;
                    canvas.drawPath(path, paint);
                    path.reset();
                    path.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    if (x == x1 && y == y1) {
                        path.quadTo( x1+1f, y1+1f, x, y);
                        canvas.drawPath(path, paint);
                    }
                    path.reset();
                    break;
                default:
                    return false;
            }

            ImageView imageView = findViewById(R.id.imageView);
            imageView. setImageBitmap(bitmap);
            return true;
        }
    }
