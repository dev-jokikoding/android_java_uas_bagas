package com.example.uas_muhammadbagaskurniawan_2110031806059.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.uas_muhammadbagaskurniawan_2110031806059.R;
import com.example.uas_muhammadbagaskurniawan_2110031806059.adapter.AdapterBarang;
import com.example.uas_muhammadbagaskurniawan_2110031806059.data.api.ApiConnection;
import com.example.uas_muhammadbagaskurniawan_2110031806059.data.api.ApiInterface;
import com.example.uas_muhammadbagaskurniawan_2110031806059.data.model.Barang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvBarang;
    ArrayList<Barang> barangArrayList = new ArrayList<>();
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiConnection.Connection().create(ApiInterface.class);

        rvBarang = findViewById(R.id.rv_item);

        rvBarang.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_entry) {
            Intent intent = new Intent(this, EntryActivity.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menu_exit) {
            finishAndRemoveTask();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        Call<List<Barang>> tampilBarang = apiInterface.listBarang();
        tampilBarang.enqueue(new Callback<List<Barang>>() {
            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                barangArrayList = (ArrayList<Barang>) response.body();
                AdapterBarang adapter = new AdapterBarang(barangArrayList);
                rvBarang.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {
                // TODO
            }
        });
        super.onResume();
    }
}