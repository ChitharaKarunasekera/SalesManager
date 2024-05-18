package com.example.salesmanagerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salesmanagerapp.R;
import com.example.salesmanagerapp.model.Order;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.customerName.setText(order.getCustomerName());
        holder.orderDate.setText(order.getDate());
        holder.orderValue.setText("Order Value: $" + order.getOrderValue());
        holder.paymentAmount.setText("Payment Amount: $" + order.getPaymentAmount());
        holder.balanceAmount.setText("Balance Amount: $" + order.getBalanceAmount());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView customerName, orderDate, orderValue, paymentAmount, balanceAmount;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderValue = itemView.findViewById(R.id.orderValue);
            paymentAmount = itemView.findViewById(R.id.paymentAmount);
            balanceAmount = itemView.findViewById(R.id.balanceAmount);
        }
    }
}