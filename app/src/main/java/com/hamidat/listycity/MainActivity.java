package com.hamidat.listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // Declaring the variables so I can extend them later
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        Button addCityButton = findViewById(R.id.add_city_button);

        // Declare an array of strings to store the major city list
        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Add City Button Logic
        addCityButton.setOnClickListener(v -> {
            // Open a dialog to get input from the user
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add City");

            // Create an input field for the dialog
            final EditText input = new EditText(this);
            builder.setView(input);

            // Add Confirm and Cancel buttons
            builder.setPositiveButton("CONFIRM", (dialog, which) -> {
                String cityName = input.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    dataList.add(cityName); // Add the city to the list
                    cityAdapter.notifyDataSetChanged(); // Refresh the ListView
                }
            });

            builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());

            builder.show(); // Display the dialog
        });

        // Select and delete city logic
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            // Show a confirmation dialog before deleting (so they don't just force it)
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete City")
                    .setMessage("Are you sure you want to delete " + dataList.get(position) + "?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        dataList.remove(position); // Remove the city from the list
                        cityAdapter.notifyDataSetChanged(); // Refresh the ListView
                    })
                    .setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
}