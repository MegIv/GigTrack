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
        boolean isDark = themePreferences.isDarkMode();

        if (isDark) {
            binding.fabTheme.setImageResource(R.drawable.ic_moon);
        } else {
            binding.fabTheme.setImageResource(R.drawable.ic_sun);
        }

        // Setup Theme Switch
        binding.fabTheme.setOnClickListener(v -> {
            boolean newThemeState = !themePreferences.isDarkMode();
            themePreferences.setDarkMode(newThemeState);

            requireActivity().recreate();
        });

        // Setup RecyclerView
        adapter = new HistoryAdapter();
        binding.rvLatihan.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvLatihan.setAdapter(adapter);

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
