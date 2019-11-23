package com.arisembeks.designapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.dtech.customui.buttonprocess.ProgressButtonListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import com.dtech.customui.buttonprocess.MaterialButtonProcess;

import bottombar.BottomBarAnim;
import bottombar.CustomBottomItem;
import bottombar.ItemAdapter;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemSelectorInterface {

    BottomBarAnim bottomBarAnim;
    public static final int BOOKMARK = 0;
    public static final int LIKES = 1;
    public static final int SEARCH = 2;
    public static final int PROFILE = 3;

    MaterialButtonProcess buttonProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initUi();
    }

    private void initUi() {
        buttonProcess = findViewById(R.id.btp);
        //buttonProcess.setView(true);
        buttonProcess.setProgressButtonListener(new ProgressButtonListener() {
            @Override
            public void onStartProgress() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonProcess.stop();
                    }
                }, 3000);
            }

            @Override
            public void onStopProgress() {

            }
        });
        bottomBarAnim = findViewById(R.id.bottombar);
        bottomBarAnim.setOnItemSelector(this);
        bottomBarAnim.hideDivider();
        bottomBarAnim.apply(BOOKMARK);
        initItems();

    }

    private void initItems() {
        @SuppressLint("ResourceType") CustomBottomItem bookmark = new CustomBottomItem(BOOKMARK, R.raw.infinity, getString(R.string.item_0), getString(R.color.white), getString(R.color.black));
        @SuppressLint("ResourceType") CustomBottomItem likes = new CustomBottomItem(LIKES, R.raw.infinity, getString(R.string.item_1), getString(R.color.white), getString(R.color.black));
        @SuppressLint("ResourceType") CustomBottomItem search = new CustomBottomItem(SEARCH, R.raw.infinity, getString(R.string.item_2), getString(R.color.white), getString(R.color.black));
        @SuppressLint("ResourceType") CustomBottomItem profile = new CustomBottomItem(PROFILE, R.raw.infinity, getString(R.string.item_3), getString(R.color.white), getString(R.color.black));

        bottomBarAnim.addItem(bookmark);
        bottomBarAnim.addItem(likes);
        bottomBarAnim.addItem(search);
        bottomBarAnim.addItem(profile);
    }

    @Override
    public void itemSelect(int selectedID) {
        switch (selectedID){
            case BOOKMARK:
                //todo do something, when Bookmark is selected
                Toast.makeText(MainActivity.this, "Bookmark", Toast.LENGTH_LONG).show();
                break;
            case LIKES:
                //todo do something, when Likes is selected
                Toast.makeText(MainActivity.this, "Likes", Toast.LENGTH_LONG).show();
                break;
            case SEARCH:
                //todo do something, when Search is selected
                Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_LONG).show();
                break;
            case PROFILE:
                //todo do something, when Profile is selected
                Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
