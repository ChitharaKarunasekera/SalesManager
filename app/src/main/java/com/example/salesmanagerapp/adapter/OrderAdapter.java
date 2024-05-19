package com.example.salesmanagerapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesmanagerapp.R;
import com.example.salesmanagerapp.model.OrderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderModel> orderList;

    public OrderAdapter(List<OrderModel> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.customerNameTextView.setText(order.getCustomerName());
        holder.dateTextView.setText(order.getDate());
        holder.orderValueTextView.setText("Order Value: " + String.format("Rs. %.2f", order.getOrderValue()));
        holder.paymentAmountTextView.setText("Payment Amount: " + String.format("Rs. %.2f", order.getPaymentAmount()));
        holder.balanceAmountTextView.setText("Balance Amount: " + String.format("Rs. %.2f", order.getBalanceAmount()));

        // Set the background drawable for the item view
        holder.itemView.setBackgroundResource(R.drawable.order_item_background);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView;
        TextView dateTextView;
        TextView orderValueTextView;
        TextView paymentAmountTextView;
        TextView balanceAmountTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.customerName);
            dateTextView = itemView.findViewById(R.id.orderDate);
            orderValueTextView = itemView.findViewById(R.id.orderValue);
            paymentAmountTextView = itemView.findViewById(R.id.paymentAmount);
            balanceAmountTextView = itemView.findViewById(R.id.balanceAmount);
        }
    }
}