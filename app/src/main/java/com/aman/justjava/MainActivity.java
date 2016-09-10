package com.aman.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private static final int PRICE_COFFEE = 5;
    private static final int PRICE_WHIPPED_CREAM = 1;
    private static final int PRICE_CHOCOLATE = 2;

    private int quantity = 2;
    private CheckBox whippedCreamCheckBox;
    private CheckBox chocolateCheckBox;
    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String orderSummary = createOrderSummary();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_subject, nameEditText.getText()));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.error_no_email_app),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.error_max_limit),
                    Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.error_min_limit),
                    Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    private int calculatePrice() {
        return quantity * (PRICE_COFFEE + (whippedCreamCheckBox.isChecked() ?
                PRICE_WHIPPED_CREAM : 0) + (chocolateCheckBox.isChecked() ? PRICE_CHOCOLATE : 0));
    }

    private String createOrderSummary() {
        return getString(R.string.order_sender_name, nameEditText.getText()) +
                "\n" + getString(R.string.order_whipped_cream, String.valueOf(whippedCreamCheckBox.isChecked())) +
                "\n" + getString(R.string.order_chocolate, String.valueOf(chocolateCheckBox.isChecked())) +
                "\n" + getString(R.string.order_quantity, quantity) +
                "\n" + getString(R.string.order_price, calculatePrice()) +
                "\n" + getString(R.string.thank_you);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}