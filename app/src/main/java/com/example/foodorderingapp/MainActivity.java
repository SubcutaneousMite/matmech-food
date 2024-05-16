package com.example.foodorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.foodorderingapp.Adapters.MainAdapter;
import com.example.foodorderingapp.Models.MainModel;
import com.example.foodorderingapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainAdapter adapter;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Привязка используется вместо findVIewById
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Пользовательский Toolbar
        Toolbar customToolbar = binding.mainCustomToolbar.customToolbar;
        // ИЛИ
        // Toolbar customToolbar = findViewById(R.id.mainCustomToolbar);
        setSupportActionBar(customToolbar);
        // ИЛИ
        // setSupportActionBar(binding.mainCustomToolbar.customToolbar);

        // Задание заголовка пользовательского toolbar
        getSupportActionBar().setTitle("Корм МатМеха");

        /*     Меню боковой панели навигации     */

        // Добавление переключателя в левую часть toolbar, который откроет и закроет пользовательскую боковую панель навигации
        toggle = new ActionBarDrawerToggle(MainActivity.this, binding.drawerLayout, customToolbar, R.string.open_nav_drawer, R.string.open_nav_drawer);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // По умолчанию Дом останется выбранным в меню навигации
        binding.navDrawer.setCheckedItem(R.id.menuHome);

        // Эта функция используется для показа того, что произойдет после выбора любого элемента из боковой панели навигации
        binding.navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {

                    case R.id.menuHome:
                        break;

                    case R.id.menuMyOrders:
                        startActivity(new Intent(MainActivity.this, OrderActivity.class));
                        break;

                    default:

                }
                // Эта функция используется для закрытия боковой панели навигации при выборе любого одного элемента из боковой панели навигации
                binding.drawerLayout.closeDrawers();
                return true;

            }
        });

        // Следующий код показывает, как элементы добавляются в recycler view главной активности
        // Здесь элементы добавляются из ArrayList, как показано ниже
        ArrayList<MainModel> list = new ArrayList<>();

        // Здесь мы добавляем элементы в список
        // Здесь каждый раз при добавлении элементов в список вызывается конструктор MainModel, где элементы инициализируются
        list.add(new MainModel(R.drawable.compote, "Компот из сухофруктов", "80", "Творожное блюдо по-особому теплое, вызывающее самые приятные ассоциации"));
        list.add(new MainModel(R.drawable.syrniki, "Сырники", "110", "Ароматный, насыщенный, витаминный! Вкус знакомый с детства."));
        list.add(new MainModel(R.drawable.burger, "Бургер", "120", "Бургер с большим количеством сыра"));
        list.add(new MainModel(R.drawable.burger_combo_pack, "Комбо Бургер", "300", "Бургер вместе с картофелем фри и пепси"));
        list.add(new MainModel(R.drawable.franky, "Ролл с курицей", "230", "Это пита с курицей в панировке и овощами"));
        list.add(new MainModel(R.drawable.french_fries, "Картофель фри", "89", "Это глубоко жареные, очень тонкие, посоленные кусочки картофеля, обычно подаются при комнатной температуре"));
        list.add(new MainModel(R.drawable.hamburger, "Гамбургер", "169", "Гамбургер с одной или несколькими жареными котлетами, помещёнными внутрь разрезанной булки"));
        list.add(new MainModel(R.drawable.pizza, "Пицца", "350", "Пицца с моцареллой, томатами, оливками и орегано"));
        list.add(new MainModel(R.drawable.onigiri, "Онигири", "135", "Простая и сытная закуска из риса и яиц с жидким желтком."));
        list.add(new MainModel(R.drawable.greek_salad, "Греческий салат", "185", "Сочные помидоры, огурцы, болгарский перец, хрустящий сладкий лук, маслины и нежная фета выкладываются на салатные листья и поливаются лёгкой заправкой из оливкового масла, свежего базилика и лимонного сока"));

        // Установка адаптера на recycler view
        adapter = new MainAdapter(list,this);
        binding.mainRecyclerView.setAdapter(adapter);

        // Вертикальная прокрутка с помощью linear layout manager
        // Установка linear layout manager на recycler view
        // Здесь linear layout manager используется для вертикальной прокрутки всех элементов recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.mainRecyclerView.setLayoutManager(layoutManager);

    }

    // Меню, отображаемое с правой стороны toolbar
    // Эта функция используется для отображения или раздувания меню в главной активности
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_orders_and_search_bar_button, menu);
        return true;
    }

    // Эта функция используется для показа того, что произойдет после выбора любого элемента из меню, которое находится с правой стороны toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.myOrders:
                // Переход к активности заказа из главной активности
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                break;

            case R.id.searchBar:
                // Код панели поиска
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Наберите здесь для поиска");
                searchView.setIconifiedByDefault(false);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapter.getFilter().filter(newText);
                        return true;
                    }
                });
                break;

            default:

        }
        return true;

    }

    // Этот метод вызывается, когда мы нажимаем кнопку "назад" в нашем мобильном телефоне на странице главной активности
    @Override
    public void onBackPressed() {

        // Здесь мы закрываем боковую панель, если она открыта, когда мы нажимаем кнопку "назад" в нашем мобильном телефоне
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers();
        }

        // Диалоговое окно предупреждения
        // Оно будет показано, когда мы нажимаем кнопку "назад" в нашем мобильном телефоне
        else{
            new AlertDialog.Builder(this)
                    .setTitle("Выход")
                    .setIcon(R.drawable.warning)
                    .setMessage("Вы уверены, что хотите выйти?")
                    .setPositiveButton("да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setNegativeButton("нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        }

    }

}
