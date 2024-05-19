package com.example.salesmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesmanagerapp.adapter.OrderAdapter;
import com.example.salesmanagerapp.dao.DatabaseDAO;
import com.example.salesmanagerapp.model.OrderModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class OrderPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "OrderPageActivity";


    private ImageView menuIcon;

    // drawer Menu
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderList;
    private DatabaseDAO databaseDAO;
    private FloatingActionButton addFloatingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_order_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "Activity created");


        recyclerViewOrders = findViewById(R.id.orderRecyclerView);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        databaseDAO = new DatabaseDAO(this);
        databaseDAO.open();

        Log.d(TAG, "Database connection opened");

        // Retrieve orders from database
        orderList = databaseDAO.getAllOrdersWithCustomerNames();
        Log.d(TAG, "Orders retrieved from database");

        databaseDAO.close();
        Log.d(TAG, "Database connection closed");

        // Set up adapter and RecyclerView
        orderAdapter = new OrderAdapter(orderList);
        recyclerViewOrders.setAdapter(orderAdapter);
        Log.d(TAG, "RecyclerView adapter set");

        // menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.left_menu_icon);
        addFloatingBtn = findViewById(R.id.fab);

        navigationDrawer();

        // Set up FloatingActionButton click listener
        addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderPageActivity.this, NewOrder.class);
                startActivity(intent);
            }
        });
    }

    private void navigationDrawer() {

        // navigation drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_orders);

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
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_orders) {
            // Handle dashboard navigation
            // Current activity is dashboard, so no need to do anything
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