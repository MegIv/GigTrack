package com.sisteminformasi.gigtrack;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.sisteminformasi.gigtrack.databinding.ActivityDetailPracticeBinding;
import java.util.Locale;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailPracticeActivity extends AppCompatActivity {

    private ActivityDetailPracticeBinding binding;
    private DatabaseHelper databaseHelper;
    
    // Stopwatch Logic
    private int seconds = 0;
    private boolean running = false;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPracticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        executorService = Executors.newSingleThreadExecutor();

        // Get Data from Intent
        String title = getIntent().getStringExtra("TITLE");
        String artist = getIntent().getStringExtra("ARTIST");
        String coverUrl = getIntent().getStringExtra("COVER");

        binding.tvTitle.setText(title);
        binding.tvArtist.setText(artist);
        Glide.with(this).load(coverUrl).placeholder(android.R.drawable.ic_menu_gallery).into(binding.ivCover);

        // Stopwatch Controls
        runTimer();

        binding.btnStartPause.setOnClickListener(v -> {
            if (!running) {
                running = true;
                binding.btnStartPause.setText("Pause");
            } else {
                running = false;
                binding.btnStartPause.setText("Resume");
            }
        });

        binding.btnReset.setOnClickListener(v -> {
            running = false;
            seconds = 0;
            updateTimeDisplay();
            binding.btnStartPause.setText("Start");
        });

        // Save Logic
        binding.btnSave.setOnClickListener(v -> {
            String notes = binding.etNotes.getText().toString();
            String duration = binding.tvStopwatch.getText().toString();
            
            long result = databaseHelper.insertLatihan(title, artist, notes, duration);
            if (result != -1) {
                Toast.makeText(this, "Session Saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving session", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void runTimer() {
        executorService.execute(() -> {
            while (true) {
                if (running) {
                    seconds++;
                    // Update UI harus dikembalikan ke Main Thread
                    runOnUiThread(this::updateTimeDisplay);
                }
                try {
                    Thread.sleep(1000); // Jeda 1 detik di background thread
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    private void updateTimeDisplay() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
        binding.tvStopwatch.setText(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}
