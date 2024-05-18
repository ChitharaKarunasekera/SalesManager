package com.example.salesmanagerapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "order_management.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_CUSTOMERS = "customers";
    public static final String TABLE_ITEMS = "items";
    public static final String TABLE_ORDERS = "orders";

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

    // Create Customer Table
    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE " + TABLE_CUSTOMERS + " ("
            + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CUSTOMER_NAME + " TEXT NOT NULL);";

    // Create Item Table
    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS + " ("
            + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ITEM_NAME + " TEXT NOT NULL, "
            + COLUMN_ITEM_PRICE + " REAL NOT NULL);";

    // Create Order Table
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " ("
            + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ORDER_CUSTOMER_ID + " INTEGER NOT NULL, "
            + COLUMN_ORDER_DATE + " TEXT NOT NULL, "
            + COLUMN_ORDER_VALUE + " REAL NOT NULL, "
            + COLUMN_ORDER_PAYMENT_AMOUNT + " REAL NOT NULL, "
            + COLUMN_ORDER_BALANCE_AMOUNT + " REAL NOT NULL, "
            + "FOREIGN KEY(" + COLUMN_ORDER_CUSTOMER_ID + ") REFERENCES " + TABLE_CUSTOMERS + "(" + COLUMN_CUSTOMER_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUSTOMERS);
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_ORDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }
}
