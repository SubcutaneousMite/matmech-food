package com.example.foodorderingapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.DBHelper;
import com.example.foodorderingapp.DetailActivity;
import com.example.foodorderingapp.Models.OrderModel;
import com.example.foodorderingapp.R;

import java.util.ArrayList;

// Расширяем класс viewHolder, созданный внутри этого класса
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder>{

    ArrayList<OrderModel> list;
    Context context;

    // Конструктор
    public OrderAdapter(ArrayList<OrderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // Эта функция используется для надувания макета файла в "activity_order.xml" макет файла
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_order_food, parent, false);
        return new viewHolder(view);
    }

    // Эта функция используется для установки данных всех видов по их позиции в "activity_order.xml"
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final OrderModel model = list.get(position);

        holder.orderImage.setImageResource(model.getOrderImage());
        holder.orderName.setText(model.getOrderName());
        holder.orderNumber.setText(model.getOrderNumber());
        holder.orderPrice.setText(model.getOrderPrice());

        // Следующий код описывает, что происходит при нажатии на любой из элементов активности заказа
        // Здесь при нажатии на любой из элементов происходит переход на активность с деталями
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveToDetailActivity = new Intent(context, DetailActivity.class);
                moveToDetailActivity.putExtra("orderNumber", model.getOrderNumber());
                moveToDetailActivity.putExtra("type", 2);
                context.startActivity(moveToDetailActivity);

            }
        });

        // Следующий код описывает, что происходит при длительном нажатии на любой из элементов активности заказа
        // Здесь при длительном нажатии на любой из элементов он будет удален
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Диалоговое окно предупреждения
                // Оно будет показано, когда мы нажимаем кнопку назад на нашем мобильном устройстве
                new AlertDialog.Builder(context)
                        .setTitle("Delete Item")
                        .setIcon(R.drawable.warning)
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DBHelper helper = new DBHelper(context);

                                if(helper.deleteOrder(Integer.parseInt(model.getOrderNumber())) > 0)
                                    Toast.makeText(context, "Order is deleted successfully", Toast.LENGTH_SHORT).show();

                                else
                                    Toast.makeText(context, "          Order isn't deleted \n Please delete the order again", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();

                return true;

            }
        });

    }

    // Эта функция используется для установки размера recycler view в "activity_order.xml"
    @Override
    public int getItemCount() {
        return list.size();
    }

    // Эта функция используется для привязки всех видов, используемых в файле макета,
    // и получения этих видов по их идентификатору
    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView orderImage;
        TextView orderName, orderNumber, orderPrice;

        // Конструктор
        public viewHolder(@NonNull View itemView) {

            super(itemView);

            orderImage = itemView.findViewById(R.id.orderImage);
            orderName = itemView.findViewById(R.id.orderName);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            orderPrice = itemView.findViewById(R.id.orderPrice);

        }

    }

}

