package com.example.salesmanagerapp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesmanagerapp.adapter.OrderAdapter;
import com.example.salesmanagerapp.dao.DatabaseDAO;
import com.example.salesmanagerapp.model.OrderModel;

import java.util.List;

public class OrderPageActivity extends AppCompatActivity {

    private static final String TAG = "OrderPageActivity";


    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderList;
    private DatabaseDAO databaseDAO;

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

//        recyclerViewOrders = findViewById(R.id.orderRecyclerView);
//        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
//
//        // For demonstration, adding hard-coded data
//        orderList = new ArrayList<>();
//        orderList.add(new Order("John Doe", "2023-05-10", 150.00, 100.00, 50.00));
//        orderList.add(new Order("Jane Smith", "2023-05-11", 200.00, 150.00, 50.00));
//        // Add more orders as needed
//
//        orderAdapter = new OrderAdapter(orderList);
//        recyclerViewOrders.setAdapter(orderAdapter);
    }
}