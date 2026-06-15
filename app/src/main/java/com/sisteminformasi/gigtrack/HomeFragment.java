package com.sisteminformasi.gigtrack;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.sisteminformasi.gigtrack.databinding.FragmentHomeBinding;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ThemePreferences themePreferences;
    private DatabaseHelper databaseHelper;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        themePreferences = new ThemePreferences(requireContext());
        databaseHelper = new DatabaseHelper(requireContext());

        // Setup Theme Switch
        binding.switchTheme.setChecked(themePreferences.isDarkMode());
        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            themePreferences.setDarkMode(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Setup RecyclerView
        adapter = new HistoryAdapter();
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvHistory.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {
        List<History> histories = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllLatihan();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String judul = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_JUDUL));
                String artis = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ARTIS));
                String catatan = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATATAN));
                String durasi = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DURASI));
                
                histories.add(new History(id, judul, artis, catatan, durasi));
            } while (cursor.moveToNext());
            cursor.close();
        }
        
        adapter.setHistoryList(histories);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
