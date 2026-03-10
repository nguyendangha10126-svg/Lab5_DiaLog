package com.example.lab5_dialog__notification;

// NHỚ GIỮ LẠI DÒNG package CỦA BẠN Ở ĐÂY

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ProductManagerDB";
    private static final String TABLE_PRODUCTS = "TBL_PRODUCTS";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PRICE = "price";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_CATEGORY, product.getCategory());
        values.put(KEY_PRICE, product.getPrice());
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setName(cursor.getString(1));
                product.setCategory(cursor.getString(2));
                product.setPrice(cursor.getString(3));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_CATEGORY, product.getCategory());
        values.put(KEY_PRICE, product.getPrice());
        db.update(TABLE_PRODUCTS, values, KEY_ID + " = ?", new String[]{String.valueOf(product.getId())});
        db.close();
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}