package com.example.coffee_shop_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private final Context context;
    private final ProductActionListener actionListener;

    public interface ProductActionListener {
        void onAddToCartClicked(Product product, int sugarLevel);
    }

    public ProductAdapter(Context context, List<Product> productList, ProductActionListener listener) {
        this.context = context;
        this.productList = productList;
        this.actionListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = productList.get(position);

        holder.nameTextView.setText(currentProduct.getName());

        Locale moroccanFrenchLocale = new Locale("fr", "MA");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(moroccanFrenchLocale);
        holder.priceTextView.setText(currencyFormat.format(currentProduct.getPrice()));

        holder.imageView.setImageResource(currentProduct.getImageResourceId());

        final int[] currentSugarLevel = {0};
        holder.sugarCountTextView.setText(String.valueOf(currentSugarLevel[0]));

        holder.sugarMinusButton.setOnClickListener(v -> {
            if (currentSugarLevel[0] > 0) {
                currentSugarLevel[0]--;
                holder.sugarCountTextView.setText(String.valueOf(currentSugarLevel[0]));
            }
        });

        holder.sugarPlusButton.setOnClickListener(v -> {
            currentSugarLevel[0]++;
            holder.sugarCountTextView.setText(String.valueOf(currentSugarLevel[0]));
        });

        holder.addToCartButton.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onAddToCartClicked(currentProduct, currentSugarLevel[0]);
            } else {
                Log.w("ProductAdapter", "Add to cart listener is null!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return (productList != null) ? productList.size() : 0;
    }

    public void filterList(List<Product> filteredList) {
        this.productList = filteredList;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        ImageButton sugarMinusButton;
        TextView sugarCountTextView;
        ImageButton sugarPlusButton;
        ImageButton addToCartButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImageView);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
            sugarMinusButton = itemView.findViewById(R.id.sugarMinusButton);
            sugarCountTextView = itemView.findViewById(R.id.sugarCountTextView);
            sugarPlusButton = itemView.findViewById(R.id.sugarPlusButton);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
