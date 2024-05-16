package com.example.foodorderingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foodorderingapp.Models.OrderModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBName = "foodOrderingDatabase.db";
    final static int DBVersion = 2;

    // Конструктор
    // В этом конструкторе передаются четыре аргумента в супер-функцию:
    // 1.  context    Показывает, откуда вызван база данных
    // 2.  DBName     Здесь передается имя базы данных
    // 3.  factory    Здесь передается значение factory
    // 4.  DBVersion  Здесь передается значение версии базы данных
    //                Значение DBVersion будет изменено при любых изменениях в базе данных и нижеследующий метод onUpgrade также будет вызван
    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    // Создание таблицы в вышеуказанной базе данных "foodOrderingDatabase.db"
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table orders" +
                        "(id integer primary key autoincrement," +
                        "image int," +
                        "foodname text," +
                        "quantity int," +
                        "description text," +
                        "name text," +
                        "phone text," +
                        "price int," +
                        "originalprice int)"
        );

    }

    // Этот метод вызывается при любых изменениях в базе данных
    // Если мы изменим что-либо в базе данных, сначала нам нужно изменить вышеуказанную DBVersion, а затем этот метод будет вызван
    // В этом методе будет удалена вышеуказанная таблица и новая обновленная таблица будет создана на ее месте
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists orders");
        onCreate(db);
    }

    /* ************    CRUD ОПЕРАЦИИ     ************ */

    // Операция создания
    // Этот метод используется для вставки заказа в базу данных
    // Этот метод вызывается при нажатии на кнопку "Order Now", и этот заказ будет вставлен в базу данных
    public boolean insertOrder(int image, String foodName, int quantity, String description, String name, String phone, int price, int originalPrice){

        /*
          id = 0
          image = 1
          foodName = 2
          quantity = 3
          description = 4
          name = 5
          phone = 6
          price = 7
          originalPrice = 8
        */

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("image", image);
        values.put("foodname", foodName);
        values.put("quantity", quantity);
        values.put("description", description);
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);
        values.put("originalprice", originalPrice);

        // Здесь при вызове метода insert будет возвращен id вставленной строки
        long value = db.insert("orders", null, values);
        db.close();

        // Если возвращается 0 или <0 значение, строка не вставлена
        if(value<=0)
            return false;
            // Если >0 значение возвращается, строка вставлена
        else
            return true;

    }

    // Операция чтения
    // Этот метод используется для чтения заказа из базы данных
    // Этот метод вызывается из активности заказа, где мы получаем все заказы из базы данных и показываем эти заказы в активности заказа
    public ArrayList<OrderModel> getOrders(){

        ArrayList<OrderModel> orders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id,image,foodName,price from orders", null);

        /*
          id = 0
          image = 1
          foodName = 2
          quantity = 3
          description = 4
          name = 5
          phone = 6
          price = 7
          originalPrice = 8
        */

        if(cursor.moveToFirst()){

            OrderModel orderModel = new OrderModel();
            orderModel.setOrderNumber(cursor.getInt(0) + "");
            orderModel.setOrderImage(cursor.getInt(1));
            orderModel.setOrderName(cursor.getString(2));
            orderModel.setOrderPrice(cursor.getInt(3) + "");
            orders.add(orderModel);
            while(cursor.moveToNext()){

                OrderModel model = new OrderModel();
                model.setOrderNumber(cursor.getInt(0) + "");
                model.setOrderImage(cursor.getInt(1));
                model.setOrderName(cursor.getString(2));
                model.setOrderPrice(cursor.getInt(3) + "");
                orders.add(model);

            }

        }

        cursor.close();
        db.close();
        return orders;

    }

    // Этот метод используется для чтения заказа по его id из базы данных
    // Этот метод вызывается из активности деталей, где мы получаем один заказ по его id из базы данных и показываем этот заказ в активности деталей
    public Cursor getOrderById(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from orders where id=" + id, null);

        if(cursor != null)
            cursor.moveToFirst();

        return cursor;

    }

    // Операция обновления
    // Этот метод используется для обновления заказа в базе данных
    // Этот метод вызывается при нажатии на кнопку "Update Order", и этот заказ будет обновлен в базе данных
    public boolean updateOrder(int id, int image, String foodName, int quantity, String description, String name, String phone, int price){

        /*
          id = 0
          image = 1
          foodName = 2
          quantity = 3
          description = 4
          name = 5
          phone = 6
          price = 7
          originalPrice = 8
        */

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("image", image);
        values.put("foodname", foodName);
        values.put("quantity", quantity);
        values.put("description", description);
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);

        // Здесь при вызове метода update будет возвращен id обновленной строки
        long value = db.update("orders", values, "id="+id, null);
        db.close();

        // Если возвращается 0 или <0 значение, строка не обновлена
        if(value<=0)
            return false;
            // Если >0 значение возвращается, строка обновлена
        else
            return true;

    }

    // Операция удаления
    // Этот метод используется для удаления заказа из базы данных
    // Этот метод вызывается при долгом нажатии на любой элемент активности заказа, и он удалит этот заказ из базы данных
    public int deleteOrder(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        int value = db.delete("orders", "id="+id, null);
        return value;

    }
}