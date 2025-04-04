package com.example.coffee_shop_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductAdapter.ProductActionListener {

    private static final String TAG = "MainActivity";

    private ChipGroup categoryChipGroup;
    private RecyclerView productRecyclerView;
    private FloatingActionButton fabGoToPayment;

    private final List<Category> allCategories = new ArrayList<>();
    private final List<Product> allProducts = new ArrayList<>();
    private final List<Product> currentlyDisplayedProducts = new ArrayList<>();

    private ProductAdapter productAdapter;

    private final HashMap<String, CartItem> cart = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryChipGroup = findViewById(R.id.categoryChipGroup);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        fabGoToPayment = findViewById(R.id.fabGoToCart);

        loadData();
        setupCategoryChips();
        setupRecyclerView();
        setupPaymentButton();
        filterProducts(null);

        if (categoryChipGroup.getChildCount() > 0) {

            Chip firstChip = (Chip) categoryChipGroup.getChildAt(0);
            if (firstChip != null && firstChip.getTag() == null) {
                firstChip.setChecked(true);
            }
        }
    }

// Inside MainActivity.java

    private void loadData() {
        // --- Categories remain the same (using R.drawable for chip icons) ---
        allCategories.add(new Category("cat_cap", "Cappuccino", R.drawable.img_cappo));
        allCategories.add(new Category("cat_cold", "Cold Brew", R.drawable.img_cold_brew)); // Add img_cold_brew.png if you have it
        allCategories.add(new Category("cat_esp", "Espresso", R.drawable.img_espresso));
        allCategories.add(new Category("cat_latte", "Latte", R.drawable.img_latte));
        allCategories.add(new Category("cat_flat", "Flat White", R.drawable.img_flate_white)); // Correct spelling? Use R.drawable.img_flate_white
        allCategories.add(new Category("cat_amer", "Americano", R.drawable.img_americano));

        allProducts.add(new Product("p1", "Classic Capp", 18, R.drawable.img_cappo, "cat_cap")); // Use Cappuccino image
        allProducts.add(new Product("p2", "Dark Cold Brew", 15, R.drawable.img_dark_cold_brew, "cat_cold"));
        allProducts.add(new Product("p3", "Bold Espresso", 12, R.drawable.img_bold_espresso, "cat_esp"));
        allProducts.add(new Product("p4", "Espresso", 12, R.drawable.img_espresso, "cat_esp"));// Use Espresso image
        allProducts.add(new Product("p4", "Vanilla Latte", 20, R.drawable.img_vanilla_latte, "cat_latte"));   // Use Latte image
        allProducts.add(new Product("p5", "Iced Cappuccino", 18, R.drawable.img_iced_cappo, "cat_cap")); // Use Cappuccino image again, or a specific iced one
        allProducts.add(new Product("p6", "Hazelnut Latte", 25, R.drawable.img_hazelnut_latte, "cat_latte")); // Use Latte image again
        allProducts.add(new Product("p7", "Smooth Flat White", 15, R.drawable.img_smooth_flat_white, "cat_flat")); // Use Flat white image
        allProducts.add(new Product("p8", "Hot Americano", 18, R.drawable.img_americano, "cat_amer"));   // Use Americano image
    }



    private void setupCategoryChips() {
        categoryChipGroup.removeAllViews();

        Chip allChip = new Chip(this);
        allChip.setText("All");
        allChip.setTag(null);
        allChip.setCheckable(true);
        allChip.setClickable(true);
        allChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                filterProducts(null);
            }
        });
        categoryChipGroup.addView(allChip);

        for (Category category : allCategories) {
            Chip categoryChip = createChipWithIcon(category);
            categoryChipGroup.addView(categoryChip);
        }
    }

    private Chip createChipWithIcon(Category category) {
        Chip chip = new Chip(this);
        chip.setText(category.getName());
        chip.setTag(category.getId());
        chip.setCheckable(true);
        chip.setClickable(true);

        if (category.getIconDrawableId() != 0) {
            chip.setChipIconResource(category.getIconDrawableId());
            chip.setChipIconVisible(true);
        }

        chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                String selectedTag = (String) buttonView.getTag();
                Log.d(TAG, "Category selected: " + category.getName());
                filterProducts(selectedTag);
            }
        });

        return chip;
    }


    private void setupRecyclerView() {
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this, currentlyDisplayedProducts, this);
        productRecyclerView.setAdapter(productAdapter);
    }

    private void setupPaymentButton() {
        fabGoToPayment.setOnClickListener(v -> {
            if (cart.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            double finalTotalPrice = calculateCartTotal();
            Log.d(TAG, "Proceeding to payment with total: " + finalTotalPrice);

            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
            intent.putExtra(PaymentActivity.EXTRA_TOTAL_PRICE, finalTotalPrice);
            startActivity(intent);
        });
    }

    private void filterProducts(String categoryId) {
        currentlyDisplayedProducts.clear();
        if (categoryId == null) {
            currentlyDisplayedProducts.addAll(allProducts);
            Log.d(TAG, "Filtering: Showing all products (" + currentlyDisplayedProducts.size() + ")");
        } else {
            for (Product product : allProducts) {
                if (product.getCategoryId() != null && product.getCategoryId().equals(categoryId)) {
                    currentlyDisplayedProducts.add(product);
                }
            }
            Log.d(TAG, "Filtering: Showing category " + categoryId + " (" + currentlyDisplayedProducts.size() + ")");
        }
        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        } else {
            Log.e(TAG, "Adapter was null during filtering!");
        }
    }

    @Override
    public void onAddToCartClicked(Product product, int sugarLevel) {
        if (product == null) {
            Log.w(TAG, "Attempted to add null product to cart.");
            return;
        }

        String cartKey = product.getId() + "_sugar" + sugarLevel;
        Log.d(TAG, "Add to cart requested for key: " + cartKey + " (Product: " + product.getName() + ", Sugar: " + sugarLevel + ")");

        if (cart.containsKey(cartKey)) {
            CartItem existingItem = cart.get(cartKey);
            if (existingItem != null) {
                existingItem.incrementQuantity();
                Log.d(TAG, "Incremented quantity for " + product.getName() + " (Sugar: " + sugarLevel + ") to " + existingItem.getQuantity());
            } else {
                Log.w(TAG,"Cart key existed but CartItem was null! Adding as new. Key: " + cartKey);
                cart.put(cartKey, new CartItem(product, 1, sugarLevel));
            }
        } else {
            CartItem newItem = new CartItem(product, 1, sugarLevel);
            cart.put(cartKey, newItem);
            Log.d(TAG, "Added new item to cart: " + product.getName() + " (Sugar: " + sugarLevel + ")");
        }

        Toast.makeText(this, product.getName() + " (Sugar: " + sugarLevel + ") added to cart", Toast.LENGTH_SHORT).show();
    }

    private double calculateCartTotal() {
        double total = 0.0;
        if (cart != null && !cart.isEmpty()) {
            for (CartItem item : cart.values()) {
                if (item != null && item.getProduct() != null && item.getQuantity() > 0) {
                    total += item.getProduct().getPrice() * item.getQuantity();
                } else {
                    Log.w(TAG,"Invalid CartItem found during total calculation. Skipping.");
                }
            }
        }
        return total;
    }
}
