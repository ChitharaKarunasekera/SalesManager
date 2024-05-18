package com.example.salesmanagerapp;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewOrder extends AppCompatActivity {

    private Spinner customerSpinner;
    private Spinner itemSpinner;
    private TextInputEditText quantityEditText;
    private TextView orderValueTextView;
    private TextInputEditText paymentAmountEditText;
    private Button saveOrderButton;

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
        //paymentAmountEditText = findViewById(R.id.paymentAmountEditText);
        saveOrderButton = findViewById(R.id.saveOrderButton);

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
            orderValueTextView.setText("Order Value: $" + String.format("%.2f", orderValue));
        }
    }

    private void saveOrder() {
        Customer selectedCustomer = (Customer) customerSpinner.getSelectedItem();
        Item selectedItem = (Item) itemSpinner.getSelectedItem();
        String quantityString = quantityEditText.getText().toString();
        String paymentAmountString = paymentAmountEditText.getText().toString();

        if (selectedCustomer != null && selectedItem != null && !quantityString.isEmpty() && !paymentAmountString.isEmpty()) {
            int quantity = Integer.parseInt(quantityString);
            double paymentAmount = Double.parseDouble(paymentAmountString);
            double price = selectedItem.getPrice();
            double orderValue = price * quantity;
            double balanceAmount = orderValue - paymentAmount;

            OrderModel order = new OrderModel();
            order.setCustomerId(selectedCustomer.getId());
            order.setDate("2023-01-01"); // You can replace this with the current date
            order.setOrderValue(orderValue);
            order.setPaymentAmount(paymentAmount);
            order.setBalanceAmount(balanceAmount);

            long orderId = databaseDAO.addOrder(order);
            if (orderId != -1) {
                Toast.makeText(this, "Order saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save order.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseDAO.close();
    }
}