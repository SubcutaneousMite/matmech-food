package com.example.foodorderingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodorderingapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Привязка используется вместо findVIewById
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Настраиваем кастомный тулбар
        Toolbar customToolbar = binding.detailCustomToolbar.customToolbar;
        // ИЛИ
        // Toolbar customToolbar = findViewById(R.id.mainCustomToolbar);
        setSupportActionBar(customToolbar);
        // ИЛИ
        // setSupportActionBar(binding.mainCustomToolbar.customToolbar);

        /*    Код для кнопки "Назад" (<-)    */
        // Устанавливаем кнопку "Назад", чтобы перейти к предыдущей активности
        // Здесь кнопка "Назад" используется для перехода к главной активности
        customToolbar.setNavigationIcon(R.drawable.back_icon);

        // Эта функция показывает, что произойдет, когда мы нажмем на кнопку "Назад"
        // Здесь мы просто переходим к предыдущей активности, которая является главной активностью, когда нажимаем кнопку "Назад"
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Создаем объект помощника базы данных
        final DBHelper helper = new DBHelper(this);

        // Код для страницы обработки заказа
        if((getIntent().getIntExtra("type", 0)) == 1){

            // Устанавливаем заголовок кастомного тулбара
            getSupportActionBar().setTitle("Страница обработки заказа");

            // Здесь мы получаем значение через intent, которое передается из главного адаптера
            final int detailImage = getIntent().getIntExtra("mainImage", 0);
            final String detailName = getIntent().getStringExtra("mainName");
            final int detailPrice = Integer.parseInt(getIntent().getStringExtra("mainPrice"));
            final String detailDescription = getIntent().getStringExtra("mainDescription");

            // Устанавливаем вышеуказанные данные на представлениях активности деталей
            binding.detailImage.setImageResource(detailImage);
            binding.detailName.setText(detailName);
            binding.detailPrice.setText(String.format("%d", detailPrice));
            binding.detailDescription.setText(detailDescription);

            /*  Код для увеличения и уменьшения количества  */
            // Инициализация значения count
            count = 1;

            // Код для увеличения количества
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    binding.quantity.setText(""+count);

                    // Код для увеличения цены при увеличении количества
                    int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                    binding.detailPrice.setText(""+(newPrice+detailPrice));
                }
            });

            // Код для уменьшения количества
            binding.subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count>1){
                        count--;
                        binding.quantity.setText(""+count);

                        // Код для уменьшения цены при уменьшении количества
                        int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                        binding.detailPrice.setText(""+(newPrice-detailPrice));
                    }
                }
            });

            // Код для кнопки "Заказать сейчас"
            binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Сначала имя пользователя и номер телефона будут проверены
                    if(!validateUsername() | !validateMobileNumber()) {
                        return;
                    }

                    // После проверки нижеследующий код для покупки товара будет выполнен
                    else {

                        // Диалоговое окно предупреждения
                        // Оно будет показано, когда мы нажмем кнопку "Заказать сейчас" на нашем телефоне
                        new AlertDialog.Builder(DetailActivity.this)
                                .setTitle("Купить товар")
                                .setIcon(R.drawable.warning)
                                .setMessage("Вы уверены, что хотите купить этот товар?")
                                .setPositiveButton("да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        boolean isInserted = helper.insertOrder(
                                                detailImage,
                                                detailName,
                                                Integer.parseInt(binding.quantity.getText().toString()),
                                                detailDescription,
                                                binding.personName.getText().toString(),
                                                binding.mobileNumber.getText().toString(),
                                                Integer.parseInt(binding.detailPrice.getText().toString()),
                                                detailPrice
                                        );

                                        if (isInserted)
                                            Toast.makeText(DetailActivity.this, "Заказ успешно оформлен", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(DetailActivity.this, "          Заказ не оформлен \n Пожалуйста, попробуйте снова", Toast.LENGTH_SHORT).show();

                                    }
                                }).setNegativeButton("нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                }).show();

                    }

                }
            });

        }

        // Код для страницы обновления заказа
        else{

            // Устанавливаем заголовок кастомного тулбара
            getSupportActionBar().setTitle("Страница обновления заказа");

            // Здесь мы получаем значение через intent, которое передается из адаптера заказа
            final int id = Integer.parseInt(getIntent().getStringExtra("orderNumber"));

            // Объект курсора используется для получения заказа по его id и используется ниже для установки данных всех представлений активности деталей
            Cursor cursor = helper.getOrderById(id);
            final int image = cursor.getInt(1);

            // Устанавливаем вышеуказанные данные на представлениях активности деталей
            binding.detailImage.setImageResource(cursor.getInt(1));
            binding.detailName.setText(cursor.getString(2));
            binding.quantity.setText(String.format("%d", cursor.getInt(3)));
            binding.detailDescription.setText(cursor.getString(4));
            binding.personName.setText(cursor.getString(5));
            binding.mobileNumber.setText(cursor.getString(6));
            binding.detailPrice.setText(String.format("%d", cursor.getInt(7)));

            // Получаем оригинальную цену товара из базы данных
            // Используется при увеличении или уменьшении количества, когда цена также увеличивается или уменьшается
            int originalPrice = cursor.getInt(8);

            /*  Код для увеличения и уменьшения количества  */
            // Инициализация значения count
            count = Integer.parseInt(binding.quantity.getText().toString());

            // Код для увеличения количества
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    binding.quantity.setText(""+count);

                    // Код для увеличения цены при увеличении количества
                    int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                    binding.detailPrice.setText(""+(newPrice+originalPrice));
                }
            });

            // Код для уменьшения количества
            binding.subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count>1){
                        count--;
                        binding.quantity.setText(""+count);

                        // Код для уменьшения цены при уменьшении количества
                        int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                        binding.detailPrice.setText(""+(newPrice-originalPrice));
                    }
                }
            });

            // Изменяем текст кнопки "Заказать сейчас" на "Обновить заказ"
            binding.insertBtn.setText("Обновить заказ");

            // Код для кнопки "Обновить заказ"
            binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Сначала имя пользователя и номер телефона будут проверены
                    if(!validateUsername() | !validateMobileNumber()) {
                        return;
                    }

                    // После проверки нижеследующий код для обновления товара будет выполнен
                    else {

                        // Диалоговое окно предупреждения
                        // Оно будет показано, когда мы нажмем кнопку "Обновить заказ" на нашем телефоне
                        new AlertDialog.Builder(DetailActivity.this)
                                .setTitle("Обновить товар")
                                .setIcon(R.drawable.warning)
                                .setMessage("Вы уверены, что хотите обновить этот товар?")
                                .setPositiveButton("да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        boolean isUpdated = helper.updateOrder(
                                                id,
                                                image,
                                                binding.detailName.getText().toString(),
                                                Integer.parseInt(binding.quantity.getText().toString()),
                                                binding.detailDescription.getText().toString(),
                                                binding.personName.getText().toString(),
                                                binding.mobileNumber.getText().toString(),
                                                Integer.parseInt(binding.detailPrice.getText().toString())
                                        );

                                        if (isUpdated)
                                            Toast.makeText(DetailActivity.this, "Заказ успешно обновлен", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(DetailActivity.this, "          Заказ не обновлен \n Пожалуйста, попробуйте снова", Toast.LENGTH_SHORT).show();

                                    }
                                }).setNegativeButton("нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                }).show();

                    }

                }
            });

        }

    }

    // Метод проверки имени пользователя
    private boolean validateUsername(){

        String Username = binding.personName.getText().toString();
        //        ^           Начало строки
        //       [A-Za-z]     Первый символ не пробел, но может быть либо заглавной, либо строчной буквой
        //       [A-Z a-z]    Остальные символы могут быть либо пробелами, либо заглавными, либо строчными буквами
        //        +           Строка содержит хотя бы одну букву
        //        ₽           Конец строки
        String checkUsername = "^[A-Za-z][A-Z a-z]+$";

        if(Username.isEmpty()){
            binding.personName.setError("Поле не может быть пустым");
            return false;
        }
        else if(Username.length()>20){
            binding.personName.setError("Имя слишком длинное");
            return false;
        }
        else if(Username.length()<=2){
            binding.personName.setError("Имя слишком короткое");
            return false;
        }
        else if(!Username.matches(checkUsername)){
            binding.personName.setError("Имя не может содержать цифры или специальные символы или первый пробел");
            return false;
        }
        else {
            binding.personName.setError(null);
            return true;
        }

    }

    // Проверка номера телефона
    private boolean validateMobileNumber(){

        String mobileNumber = binding.mobileNumber.getText().toString();
        //        [1-9]          Соответствует первой цифре и проверяет, лежит ли номер между 1 и 9
        //        [0-9]          Соответствует остальным цифрам и проверяет, лежит ли номер между 0 и 9
        //        {9}            Указывает количество оставшихся цифр
        String checkMobileNumber = "[1-9][0-9]{9}";

        if(mobileNumber.isEmpty()){
            binding.mobileNumber.setError("Поле не может быть пустым");
            return false;
        }
        else if(mobileNumber.length()>11){
            binding.mobileNumber.setError("Номер телефона должен содержать 11 цифр");
            return false;
        }
        else if(mobileNumber.length()<11){
            binding.mobileNumber.setError("Номер телефона должен содержать 11 цифр");
            return false;
        }
        else {
            binding.mobileNumber.setError(null);
            return true;
        }

    }

}
