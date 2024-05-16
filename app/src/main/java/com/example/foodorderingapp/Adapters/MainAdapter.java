package com.example.foodorderingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.DetailActivity;
import com.example.foodorderingapp.Models.MainModel;
import com.example.foodorderingapp.R;

import java.util.ArrayList;
import java.util.Collection;

// Расширяем класс viewHolder, созданный внутри этого класса,
// также реализуем интерфейс Filterable для фильтрации данных в строке поиска,
// и его методы созданы внутри этого класса
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> implements Filterable {

    ArrayList<MainModel> list;
    ArrayList<MainModel> listAll;
    Context context;

    // Конструктор
    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.listAll = new ArrayList<>(list);
    }

    // Эта функция используется для надувания макета файла в "activity_main.xml" макет файла
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_main_food, parent, false);
        return new viewHolder(view);
    }

    // Эта функция используется для установки данных всех видов по их позиции в "activity_main.xml"
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final MainModel model = list.get(position);
        holder.mainImage.setImageResource(model.getMainImage());
        holder.mainName.setText(model.getMainName());
        holder.mainPrice.setText(model.getMainPrice());
        holder.mainDescription.setText(model.getMainDescription());

        // Следующий код описывает, что происходит при нажатии на любой из элементов главной активности
        // Здесь при нажатии на любой из элементов происходит переход на активность с деталями
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveToDetailActivity = new Intent(context, DetailActivity.class);
                moveToDetailActivity.putExtra("mainImage", model.getMainImage());
                moveToDetailActivity.putExtra("mainName", model.getMainName());
                moveToDetailActivity.putExtra("mainPrice", model.getMainPrice());
                moveToDetailActivity.putExtra("mainDescription", model.getMainDescription());
                moveToDetailActivity.putExtra("type", 1);
                context.startActivity(moveToDetailActivity);

            }
        });

    }

    // Эта функция используется для установки размера recycler view в "activity_main.xml"
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Этот код используется в строке поиска
    // Он будет фильтровать данные, которые мы вводим в строку поиска
    @Override
    public Filter getFilter() {
        return filter;
    }

    // Этот код будет выполнять фильтрацию и публиковать результаты в строке поиска
    Filter filter = new Filter() {

        // Этот метод будет выполнять фильтрацию в фоновом режиме и возвращать ее результат в методе publishResults()
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<MainModel> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty())
            {
                filteredList.addAll(listAll);
            }
            else
            {
                for(MainModel mainList : listAll)
                {
                    if(mainList.getMainName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filteredList.add(mainList);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        // Этот метод будет показывать отфильтрованные результаты в пользовательском интерфейсе(UI)
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends MainModel>) filterResults.values);
            notifyDataSetChanged();
        }

    };

    // Эта функция используется для привязки всех видов, используемых в файле макета,
    // и получения этих видов по их идентификатору
    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView mainImage;
        TextView mainName, mainPrice, mainDescription;

        // Конструктор
        public viewHolder(@NonNull View itemView) {

            super(itemView);

            mainImage = itemView.findViewById(R.id.mainImage);
            mainName = itemView.findViewById(R.id.mainName);
            mainPrice = itemView.findViewById(R.id.mainPrice);
            mainDescription = itemView.findViewById(R.id.mainDescription);

        }

    }

}
