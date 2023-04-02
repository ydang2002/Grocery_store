package com.nhuy.grocerystore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nhuy.grocerystore.R;
import com.nhuy.grocerystore.models.ViewAllModel;

public class DetailedActivity extends AppCompatActivity {

    int totalQuantity = 1, totalPrice = 0;
    ImageView detailedImg, addItem, removeItem;
    TextView price, rating, description, quantity;
    Button addToCart;
    Toolbar toolbar;
    ViewAllModel viewAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel) {
            viewAllModel = (ViewAllModel) object;
        }

        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detail_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);
        price = findViewById(R.id.detail_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detail_dec);
        addToCart = findViewById(R.id.add_to_cart);

        if (viewAllModel != null) {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            price.setText("Price: " + viewAllModel.getPrice() + "VND/kg");
            description.setText(viewAllModel.getDescription());
        }

        totalPrice = viewAllModel.getPrice() * totalQuantity;

        if (viewAllModel.getType().equals("Trứng")) {
            price.setText("Price: " + viewAllModel.getPrice() + " VND/hộp");
            totalPrice = viewAllModel.getPrice() * totalQuantity;
        }

        if (viewAllModel.getType().equals("Sữa")) {
            price.setText("Price: " + viewAllModel.getPrice() + " VND/lít");
            totalPrice = viewAllModel.getPrice() * totalQuantity;
        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

    }
}