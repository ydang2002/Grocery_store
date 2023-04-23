package com.nhuy.grocerystore.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhuy.grocerystore.R;
import com.nhuy.grocerystore.adapter.NavCategoryAdapter;
import com.nhuy.grocerystore.adapter.NavCategoryDetailAdapter;
import com.nhuy.grocerystore.models.HomeCategory;
import com.nhuy.grocerystore.models.NavCategoryDetailModel;
import com.nhuy.grocerystore.models.NavCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NavCategoryDetailModel> list;
    NavCategoryDetailAdapter adapter;
    FirebaseFirestore db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_category);

        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        String type = getIntent().getStringExtra("type");
//        Log.d("LogType: ", type);
        recyclerView = findViewById(R.id.nav_cat_det_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NavCategoryDetailAdapter(this, list);
        recyclerView.setAdapter(adapter);

        if(type != null && type.equalsIgnoreCase("Nước")) {
            db.collection("NavCategoryDetailed").whereEqualTo("type", "Nước").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        NavCategoryDetailModel viewAllModel = documentSnapshot.toObject(NavCategoryDetailModel.class);
                        list.add(viewAllModel);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);                    }

                }
            });
        }
//
//        db.collection("NavCategoryDetailed")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                NavCategoryDetailModel homeCategory = document.toObject(NavCategoryDetailModel.class);
//                                list.add(homeCategory);
//                                adapter.notifyDataSetChanged();
//                            }
//                        } else {
//                            Toast.makeText(NavCategoryActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
    }
}