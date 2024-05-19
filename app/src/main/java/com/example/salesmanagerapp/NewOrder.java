package com.example.salesmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.salesmanagerapp.dao.DatabaseDAO;
import com.example.salesmanagerapp.model.Customer;
import com.example.salesmanagerapp.model.Item;
import com.example.salesmanagerapp.model.OrderModel;
import com.google.android.material.textfield.TextInputEditText;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewOrder extends AppCompatActivity {

    private Spinner customerSpinner;
    private Spinner itemSpinner;
    private TextInputEditText quantityEditText;
    private TextView orderValueTextView;
    private TextInputEditText paymentAmountEditText;
    private Button saveOrderButton;
    private Button cancelOrderButton;

    private DatabaseDAO databaseDAO;
    private List<Customer> customers;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        customerSpinner = findViewById(R.id.customerSpinner);
        itemSpinner = findViewById(R.id.itemSpinner);
        quantityEditText = findViewById(R.id.quantityEditText);
        orderValueTextView = findViewById(R.id.orderValueTextView);
        paymentAmountEditText = findViewById(R.id.paymentAmountEditText);
        saveOrderButton = findViewById(R.id.saveOrderButton);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);

        databaseDAO = new DatabaseDAO(this);
        databaseDAO.open();

        // Load data from database
        loadCustomers();
        loadItems();

        // Add listener to quantityEditText to update order value
        quantityEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                updateOrderValue();
            }
        });

        // Save order button click listener
        saveOrderButton.setOnClickListener(v -> saveOrder());

//        saveOrderButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveOrder();
//                Intent intent = new Intent(NewOrder.this, OrderPageActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel action
                finish(); // Closes the current activity and returns to the previous one
                Toast.makeText(NewOrder.this, "Order Canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCustomers() {
        customers = databaseDAO.getAllCustomers();
        ArrayAdapter<Customer> customerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customers);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(customerAdapter);
    }

    private void loadItems() {
        items = databaseDAO.getAllItems();
        ArrayAdapter<Item> itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemAdapter);
    }

    private void updateOrderValue() {
        Item selectedItem = (Item) itemSpinner.getSelectedItem();
        String quantityString = quantityEditText.getText().toString();
        if (!quantityString.isEmpty() && selectedItem != null) {
            int quantity = Integer.parseInt(quantityString);
            double price = selectedItem.getPrice();
            double orderValue = price * quantity;
            orderValueTextView.setText("Order Value: Rs. " + String.format("%.2f", orderValue));
        }
    }

    private void saveOrder() {
        Customer selectedCustomer = (Customer) customerSpinner.getSelectedItem();
        Item selectedItem = (Item) itemSpinner.getSelectedItem();
        String quantityString = quantityEditText.getText().toString();
        String paymentAmountString = paymentAmountEditText.getText().toString();

        if (selectedCustomer != null && selectedItem != null) {
            if (!quantityString.isEmpty() && !paymentAmountString.isEmpty()) {
                int quantity = Integer.parseInt(quantityString);
                double paymentAmount = Double.parseDouble(paymentAmountString);
                double price = selectedItem.getPrice();
                double orderValue = price * quantity;
                double balanceAmount = orderValue - paymentAmount;

                // Check if the payment amount is at least 40% of the total
                if (paymentAmount <= orderValue) {
                    if (paymentAmount >= orderValue * 0.4) {
                        // Get the current date
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                        OrderModel order = new OrderModel();
                        order.setCustomerId(selectedCustomer.getId());
                        order.setDate(currentDate);
                        order.setOrderValue(orderValue);
                        order.setPaymentAmount(paymentAmount);
                        order.setBalanceAmount(balanceAmount);

                        long orderId = databaseDAO.addOrder(order);
                        if (orderId != -1) {
                            Toast.makeText(this, "Order saved successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewOrder.this, OrderPageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Failed to save order.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Payment amount should be at least 40% of the total.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Check Again! Payment greater than total", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter quantity and payment amount.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseDAO.close();
    }
}