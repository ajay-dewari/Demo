package com.ajaysinghdewari.demo;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.ajaysinghdewari.demo.adapter.BannerPagerAdapter;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*================Banner Variables================*/
    private ViewPager mBannerViewPager;
    private BannerPagerAdapter mBannerPagerAdapter;
    private LinearLayout mBannerDotsLayout;
    private TypedArray mBannerArray;
    private int numberOfBannerImage;
    private View[] mBannerDotViews;


    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;


    private ImageButton mSearchVoiceBtn;

    private boolean isFABOpen=false;
    private FloatingActionButton fab, fab1, fab2,fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSearchVoiceBtn = (ImageButton) findViewById(R.id.search_voice_btn);

        /*===================Inetelizing Banner Variables===============*/
        mBannerArray = getResources().obtainTypedArray(R.array.banner_img_array);
        numberOfBannerImage=mBannerArray.length();
        mBannerDotViews = new View[numberOfBannerImage]; // create an empty array;

        /*===================Banner Pager Configuration=================*/
        mBannerViewPager = (ViewPager) findViewById(R.id.bannerViewPager);
        mBannerDotsLayout= (LinearLayout) findViewById(R.id.bannerDotsLayout);
        mBannerPagerAdapter=new BannerPagerAdapter(HomeActivity.this, mBannerArray);
        mBannerViewPager.setAdapter(mBannerPagerAdapter);


        /*===========================START Banner Configuration Code ======================================*/

        for (int i = 0; i < numberOfBannerImage; i++) {
            // create a new textview
            final View bannerDotView = new View(this);
/*Creating the dynamic dots for banner*/
            LinearLayout.LayoutParams dotLayoutParm=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dotLayoutParm.height = getResources().getDimensionPixelSize(R.dimen.standard_8);
            dotLayoutParm.width = getResources().getDimensionPixelSize(R.dimen.standard_8);
            dotLayoutParm.setMargins(getResources().getDimensionPixelSize(R.dimen.standard_5),0,0,0);
            bannerDotView.setLayoutParams(dotLayoutParm);
            bannerDotView.setBackground(getUnselectedDotShape());

            // add the textview to the linearlayout
            mBannerDotsLayout.addView(bannerDotView);


            // save a reference to the textview for later
            mBannerDotViews[i] = bannerDotView;
        }

        AutoSwipeBanner();
        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeDotBG(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
/*===========================END Banner Configuration Code ======================================*/

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                if(!isFABOpen){
                    showFAB();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                closeFABMenu();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }


    /*======================================START Functions for FAB Events===========================================*/
    private void showFAB(){
        isFABOpen=true;
//        final float inPixelsDistance= getResources().getDimension(R.dimen.standard_50);
        fab.animate().rotationBy(180);
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
    }

    private void closeFABMenu(){
        fab.animate().rotationBy(-180);
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }
    /*======================================END Functions for FAB Events===========================================*/


    /*======================================START Functions to configur Banner AutoSwip===========================================*/
    private GradientDrawable getUnselectedDotShape(){
        int backgroundColor = Color.TRANSPARENT;
        int strokeColor = Color.WHITE;
        int strokeSize = getResources().getDimensionPixelSize(R.dimen.standard_1);
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{backgroundColor, backgroundColor});
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(strokeSize, strokeColor);
        drawable.setCornerRadius(strokeSize);
        return drawable;
    }

    private GradientDrawable getSelectedDotShape(){
        int backgroundColor = Color.WHITE;
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{backgroundColor, backgroundColor});
        drawable.setShape(GradientDrawable.OVAL);
        return drawable;
    }

    public void AutoSwipeBanner(){
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                int currentPage=mBannerViewPager.getCurrentItem();
                if (currentPage == numberOfBannerImage-1) {
                    currentPage = -1;
                }
                mBannerViewPager.setCurrentItem(currentPage+1, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);

    }

    private void changeDotBG(int position){

        for(int i = 0; i < numberOfBannerImage; i++){
            if(position==i){
                GradientDrawable drawable = getSelectedDotShape();
                mBannerDotViews[i].setBackground(drawable);
            }else{
                GradientDrawable drawable = getUnselectedDotShape();
                mBannerDotViews[i].setBackground(drawable);
            }

        }
    }
/*======================================END Functions to configur Banner AutoSwip===========================================*/
}
