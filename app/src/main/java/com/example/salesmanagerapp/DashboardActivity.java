package com.example.salesmanagerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesmanagerapp.helper.DatabaseHelper;
import com.google.android.material.navigation.NavigationView;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DashboardActivity";

    // variables
    ImageView menuIcon;

    // drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    // Bar chart views
    View targetBar;
    View achievementBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.left_menu_icon);

        // Bar chart views
        targetBar = findViewById(R.id.targetBar);
        achievementBar = findViewById(R.id.achievementBar);

        navigationDrawer();

        // Sample values, replace with actual calculation
        int target = 20000; // Sales target value
        int achievement = calculateSalesAchievement(); // Sales achievement value

        updateBarWidth(targetBar, target, target);
        updateBarWidth(achievementBar, achievement, target);

//        Button openOrderPageButton = findViewById(R.id.open_order_page_button);
//        openOrderPageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, OrderPageActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button openNewOrderPageButton = findViewById(R.id.open_new_order_page_button);
//        openNewOrderPageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DashboardActivity.this, NewOrder.class);
//                startActivity(intent);
//            }
//        });

    }

    @SuppressLint("Range")
    private int calculateSalesAchievement() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int totalSales = 0;

        Cursor cursor = db.rawQuery("SELECT SUM(order_value) AS total FROM orders", null);
        if (cursor.moveToFirst()) {
            totalSales = cursor.getInt(cursor.getColumnIndex("total"));
        }
        cursor.close();
        db.close();

        Log.d(TAG, "Total Sales: " + totalSales);
        return totalSales;
    }

    private void updateBarWidth(View bar, int value, int maxValue) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bar.getLayoutParams();
        params.width = (int) (((double) value / maxValue) * 1000); // Adjust scaling factor as needed
        bar.setLayoutParams(params);
    }


    private void navigationDrawer() {

        // navigation drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_dashboard);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle dashboard navigation
            // Current activity is dashboard, so no need to do anything
        } else if (id == R.id.nav_orders) {
            // Handle order page navigation
            Intent intent = new Intent(this, OrderPageActivity.class);
            startActivity(intent);
        }
//        else if (id == R.id.nav_new_order) {
//            // Handle new order navigation
//            Intent intent = new Intent(this, NewOrder.class);
//            startActivity(intent);
//        }
        // Close drawer after item is selected
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}