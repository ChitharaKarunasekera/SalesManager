package com.example.salesmanagerapp.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.salesmanagerapp.helper.DatabaseHelper;
import com.example.salesmanagerapp.model.Customer;
import com.example.salesmanagerapp.model.Item;
import com.example.salesmanagerapp.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO {

    private static final String TAG = "DatabaseDAO";
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DatabaseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        Log.d(TAG, "Opening database connection...");
        database = dbHelper.getWritableDatabase(); // This will call onCreate if the database doesn't exist
    }

    public void close() {
        Log.d(TAG, "Closing database connection...");
        dbHelper.close();
    }

    // Add a new customer
    public long addCustomer(Customer customer) {

        ContentValues values = new ContentValues();
        Log.d(TAG, "Adding customer: " + customer.getName());
        values.put(DatabaseHelper.COLUMN_CUSTOMER_NAME, customer.getName());
        long result = database.insert(DatabaseHelper.TABLE_CUSTOMERS, null, values);
        return database.insert(DatabaseHelper.TABLE_CUSTOMERS, null, values);
    }

    // Get all customers
    @SuppressLint("Range")
    public List<Customer> getAllCustomers() {

        Log.d(TAG, "Fetching all customers...");
        List<Customer> customers = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_CUSTOMERS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMER_ID)));
                customer.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMER_NAME)));
                customers.add(customer);
            } while (cursor.moveToNext());
            cursor.close();
        }

        Log.d(TAG, "Fetched " + customers.size() + " customers");
        return customers;
    }

    // Add a new item
    public long addItem(Item item) {
        Log.d(TAG, "Adding item: " + item.getName() + " with price: " + item.getPrice());
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ITEM_NAME, item.getName());
        values.put(DatabaseHelper.COLUMN_ITEM_PRICE, item.getPrice());
        long result = database.insert(DatabaseHelper.TABLE_ITEMS, null, values);
        Log.d(TAG, "Item added with id: " + result);
        return result;
    }

    // Get all items
    @SuppressLint("Range")
    public List<Item> getAllItems() {

        Log.d(TAG, "Fetching all items...");
        List<Item> items = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ITEMS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_NAME)));
                item.setPrice(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_ITEM_PRICE)));
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.d(TAG, "Fetched " + items.size() + " items");
        return items;
    }

    // Add a new order
    public long addOrder(OrderModel order) {

        Log.d(TAG, "Adding order for customer id: " + order.getCustomerId() + " with order value: " + order.getOrderValue());
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ORDER_CUSTOMER_ID, order.getCustomerId());
        values.put(DatabaseHelper.COLUMN_ORDER_DATE, order.getDate());
        values.put(DatabaseHelper.COLUMN_ORDER_VALUE, order.getOrderValue());
        values.put(DatabaseHelper.COLUMN_ORDER_PAYMENT_AMOUNT, order.getPaymentAmount());
        values.put(DatabaseHelper.COLUMN_ORDER_BALANCE_AMOUNT, order.getBalanceAmount());
        long result = database.insert(DatabaseHelper.TABLE_ORDERS, null, values);
        Log.d(TAG, "Order added with id: " + result);
        return result;
    }

    // Get all orders
    @SuppressLint("Range")
    public List<OrderModel> getAllOrders() {
        List<OrderModel> orders = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ORDERS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OrderModel order = new OrderModel();
                order.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID)));
                order.setCustomerId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_CUSTOMER_ID)));
                order.setDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DATE)));
                order.setOrderValue(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_VALUE)));
                order.setPaymentAmount(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_PAYMENT_AMOUNT)));
                order.setBalanceAmount(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_BALANCE_AMOUNT)));
                orders.add(order);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return orders;
    }


    // Get all orders with customer names
    @SuppressLint("Range")
    public List<OrderModel> getAllOrdersWithCustomerNames() {
        Log.d(TAG, "Fetching all orders with customer names...");
        List<OrderModel> orders = new ArrayList<>();

        String query = "SELECT orders.id AS order_id, customers.id AS customer_id, customers.name AS customer_name, orders.date, orders.order_value, orders.payment_amount, orders.balance_amount " +
                "FROM orders " +
                "JOIN customers ON orders.customer_id = customers.id";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            Log.d(TAG, "Cursor is not null");
            if (cursor.moveToFirst()) {
                Log.d(TAG, "Cursor moved to first");
                do {
                    Log.d(TAG, "Processing cursor");

                    OrderModel order = new OrderModel();
                    order.setId(cursor.getInt(cursor.getColumnIndex("order_id")));
                    order.setCustomerId(cursor.getInt(cursor.getColumnIndex("customer_id")));
                    order.setDate(cursor.getString(cursor.getColumnIndex("date")));
                    order.setOrderValue(cursor.getDouble(cursor.getColumnIndex("order_value")));
                    order.setPaymentAmount(cursor.getDouble(cursor.getColumnIndex("payment_amount")));
                    order.setBalanceAmount(cursor.getDouble(cursor.getColumnIndex("balance_amount")));
                    order.setCustomerName(cursor.getString(cursor.getColumnIndex("customer_name")));

                    orders.add(order);
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "Cursor moveToFirst returned false");
            }
            cursor.close();
        } else {
            Log.d(TAG, "Cursor is null");
        }

        Log.d(TAG, "Fetched " + orders.size() + " orders");
        return orders;
    }
}

