package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.foodorderingapp.Adapters.OrderAdapter;
import com.example.foodorderingapp.Models.OrderModel;
import com.example.foodorderingapp.databinding.ActivityOrderBinding;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {


    ActivityOrderBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Используется привязка вместо findVIewById
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Настройка пользовательской панели инструментов
        Toolbar customToolbar = binding.orderCustomToolbar.customToolbar;
        // ИЛИ
        // Toolbar customToolbar = findViewById(R.id.mainCustomToolbar);
        setSupportActionBar(customToolbar);
        // ИЛИ
        // setSupportActionBar(binding.mainCustomToolbar.customToolbar);


        // Установка заголовка на пользовательской панели инструментов
        getSupportActionBar().setTitle("Мои заказы");



        /*    Код для "Кнопки назад (<-)"    */
        // Установка кнопки "назад" для возврата к предыдущей активности
        // Здесь кнопка "назад" используется для возврата к активности заказа
        customToolbar.setNavigationIcon(R.drawable.back_icon);

        // Эта функция показывает, что произойдет при нажатии на кнопку "назад"
        // Здесь мы просто возвращаемся к предыдущей активности, то есть активности заказа, когда нажимаем кнопку "назад"
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Следующий код предназначен для получения данных из базы данных
        // Создание объекта помощника базы данных
        DBHelper helper = new DBHelper(this);



        // Следующий код показывает, какие элементы добавлены в recycler view активности заказа
        ArrayList<OrderModel> list = helper.getOrders();


        // Здесь мы добавляем элементы в массив через базу данных


        // Установка адаптера на recycler view
        OrderAdapter adapter = new OrderAdapter(list, this);
        binding.orderRecyclerView.setAdapter(adapter);


        // Вертикальная прокрутка с использованием линейного менеджера макета
        // Установка линейного менеджера макета на recycler view
        // Здесь линейный менеджер макета используется для вертикальной прокрутки всех элементов recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.orderRecyclerView.setLayoutManager(layoutManager);

    }

}
