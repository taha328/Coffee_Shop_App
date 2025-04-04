package com.example.coffee_shop_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PaymentActivity";
    public static final String EXTRA_TOTAL_PRICE = "TOTAL_PRICE";

    private TextView textViewTotalPriceDisplay;
    private MaterialButton buttonPayAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        textViewTotalPriceDisplay = findViewById(R.id.textViewTotalPriceDisplay);
        buttonPayAction = findViewById(R.id.buttonPayAction);

        double totalPrice = 0.0;

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TOTAL_PRICE)) {
            totalPrice = intent.getDoubleExtra(EXTRA_TOTAL_PRICE, 0.0);
            Log.d(TAG, "Received total price: " + totalPrice);
        } else {
            Log.w(TAG, "Total price not found in Intent extras. Defaulting to 0.0");
            Toast.makeText(this, R.string.error_loading_total, Toast.LENGTH_SHORT).show();
        }

        Locale moroccanFrenchLocale = new Locale("fr", "MA");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(moroccanFrenchLocale);

        String formattedTotalPrice = currencyFormat.format(totalPrice);
        textViewTotalPriceDisplay.setText(formattedTotalPrice);

        final double finalAmountToPay = totalPrice;

        buttonPayAction.setOnClickListener(v -> {
            String formattedAmountForDisplay = currencyFormat.format(finalAmountToPay);
            Log.i(TAG, "Pay button clicked. Attempting to 'pay' amount: " + formattedAmountForDisplay);

            String simulationMessage = getString(R.string.payment_simulation_toast, formattedAmountForDisplay);
            Toast.makeText(PaymentActivity.this, simulationMessage, Toast.LENGTH_LONG).show();
        });
    }
}
