package com.example.salesmanagerapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "order_management.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;

    // Table names
    public static final String TABLE_CUSTOMERS = "customers";
    public static final String TABLE_ITEMS = "items";
    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_SALES = "sales";

    // Customer Table Columns
    public static final String COLUMN_CUSTOMER_ID = "id";
    public static final String COLUMN_CUSTOMER_NAME = "name";

    // Item Table Columns
    public static final String COLUMN_ITEM_ID = "id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_PRICE = "price";

    // Order Table Columns
    public static final String COLUMN_ORDER_ID = "id";
    public static final String COLUMN_ORDER_CUSTOMER_ID = "customer_id";
    public static final String COLUMN_ORDER_DATE = "date";
    public static final String COLUMN_ORDER_VALUE = "order_value";
    public static final String COLUMN_ORDER_PAYMENT_AMOUNT = "payment_amount";
    public static final String COLUMN_ORDER_BALANCE_AMOUNT = "balance_amount";

    // Sales Table Columns
    public static final String COLUMN_SALES_ID = "id";
    public static final String COLUMN_SALES_TARGET = "target";
    public static final String COLUMN_SALES_ACHIEVEMENT = "achievement";

    // SQL statements to create tables
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + TABLE_CUSTOMERS + " (" +
            COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CUSTOMER_NAME + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS + " (" +
            COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
            COLUMN_ITEM_PRICE + " REAL NOT NULL);";

    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " (" +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ORDER_CUSTOMER_ID + " INTEGER NOT NULL, " +
            COLUMN_ORDER_DATE + " TEXT NOT NULL, " +
            COLUMN_ORDER_VALUE + " REAL NOT NULL, " +
            COLUMN_ORDER_PAYMENT_AMOUNT + " REAL NOT NULL, " +
            COLUMN_ORDER_BALANCE_AMOUNT + " REAL NOT NULL, " +
            "FOREIGN KEY(" + COLUMN_ORDER_CUSTOMER_ID + ") REFERENCES " + TABLE_CUSTOMERS + "(" + COLUMN_CUSTOMER_ID + "));";

    private static final String CREATE_TABLE_SALES = "CREATE TABLE " + TABLE_SALES + " (" +
            COLUMN_SALES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SALES_TARGET + " REAL NOT NULL, " +
            COLUMN_SALES_ACHIEVEMENT + " REAL NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the SQL statements to create tables
        db.execSQL(CREATE_TABLE_CUSTOMERS);
        Log.d(TAG, "Customers table created");
        db.execSQL(CREATE_TABLE_ITEMS);
        Log.d(TAG, "Items table created");
        db.execSQL(CREATE_TABLE_ORDERS);
        Log.d(TAG, "Orders table created");
        db.execSQL(CREATE_TABLE_SALES);
        Log.d(TAG, "Sales table created");

        // Optionally, insert initial data into tables
        db.execSQL("INSERT INTO customers (name) VALUES ('John Doe');");
        db.execSQL("INSERT INTO customers (name) VALUES ('Jane Smith');");
        db.execSQL("INSERT INTO customers (name) VALUES ('Mary Mitchell');");
        db.execSQL("INSERT INTO customers (name) VALUES ('Anthony Jones');");
        db.execSQL("INSERT INTO customers (name) VALUES ('Mark Scott');");
        db.execSQL("INSERT INTO customers (name) VALUES ('William Brown');");
        Log.d(TAG, "Initial data inserted into customers table");

        db.execSQL("INSERT INTO items (name, price) VALUES ('USB Flash Drive 32GB', 1500.00);");
        db.execSQL("INSERT INTO items (name, price) VALUES ('Wireless Mouse', 2500.00);");
        db.execSQL("INSERT INTO items (name, price) VALUES ('Bluetooth Headphones', 4500.00);");
        db.execSQL("INSERT INTO items (name, price) VALUES ('External Hard Drive 1TB', 8000.00);");
        db.execSQL("INSERT INTO items (name, price) VALUES ('Laptop Cooling Pad', 3000.00);");
        db.execSQL("INSERT INTO items (name, price) VALUES ('Mechanical Keyboard', 7000.00);");
        db.execSQL("INSERT INTO items (name, price) VALUES ('Smartphone Stand', 1200.00);");
        db.execSQL("INSERT INTO items (name, price) VALUES ('Webcam HD', 6000.00);");
        Log.d(TAG, "Initial data inserted into items table");

        db.execSQL("INSERT INTO orders (customer_id, date, order_value, payment_amount, balance_amount) VALUES (1, '2024-02-01', 1000.00, 500.00, 500.00);");
        db.execSQL("INSERT INTO orders (customer_id, date, order_value, payment_amount, balance_amount) VALUES (2, '2024-05-02', 750.00, 750.00, 0.00);");
        db.execSQL("INSERT INTO orders (customer_id, date, order_value, payment_amount, balance_amount) VALUES (3, '2024-03-03', 2500.00, 1000.00, 1500.00);");
        db.execSQL("INSERT INTO orders (customer_id, date, order_value, payment_amount, balance_amount) VALUES (4, '2024-04-04', 300.00, 100.00, 200.00);");
        db.execSQL("INSERT INTO orders (customer_id, date, order_value, payment_amount, balance_amount) VALUES (5, '2024-05-10', 1200.00, 600.00, 600.00);");
        db.execSQL("INSERT INTO orders (customer_id, date, order_value, payment_amount, balance_amount) VALUES (6, '2024-06-15', 1800.00, 1800.00, 0.00);");
        Log.d(TAG, "Initial data inserted into orders table");

        // Insert initial data into sales table
        db.execSQL("INSERT INTO sales (target, achievement) VALUES (10000, 8000);");
        db.execSQL("INSERT INTO sales (target, achievement) VALUES (15000, 12000);");
        Log.d(TAG, "Initial data inserted into sales table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
        Log.d(TAG, "Database onUpgrade called");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS items");
        db.execSQL("DROP TABLE IF EXISTS customers");
        db.execSQL("DROP TABLE IF EXISTS sales");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG, "Database onOpen called");
    }
}