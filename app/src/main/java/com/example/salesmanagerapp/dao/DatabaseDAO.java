package com.example.salesmanagerapp.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.salesmanagerapp.helper.DatabaseHelper;
import com.example.salesmanagerapp.model.Customer;
import com.example.salesmanagerapp.model.Item;
import com.example.salesmanagerapp.model.Order;
import com.example.salesmanagerapp.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DatabaseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Add a new customer
    public long addCustomer(Customer customer) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CUSTOMER_NAME, customer.getName());
        return database.insert(DatabaseHelper.TABLE_CUSTOMERS, null, values);
    }

    // Get all customers
    @SuppressLint("Range")
    public List<Customer> getAllCustomers() {
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
        return customers;
    }

    // Add a new item
    public long addItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ITEM_NAME, item.getName());
        values.put(DatabaseHelper.COLUMN_ITEM_PRICE, item.getPrice());
        return database.insert(DatabaseHelper.TABLE_ITEMS, null, values);
    }

    // Get all items
    @SuppressLint("Range")
    public List<Item> getAllItems() {
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
        return items;
    }

    // Add a new order
    public long addOrder(OrderModel order) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ORDER_CUSTOMER_ID, order.getCustomerId());
        values.put(DatabaseHelper.COLUMN_ORDER_DATE, order.getDate());
        values.put(DatabaseHelper.COLUMN_ORDER_VALUE, order.getOrderValue());
        values.put(DatabaseHelper.COLUMN_ORDER_PAYMENT_AMOUNT, order.getPaymentAmount());
        values.put(DatabaseHelper.COLUMN_ORDER_BALANCE_AMOUNT, order.getBalanceAmount());
        return database.insert(DatabaseHelper.TABLE_ORDERS, null, values);
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
}

