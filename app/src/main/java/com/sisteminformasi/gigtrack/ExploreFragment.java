package com.sisteminformasi.gigtrack;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.sisteminformasi.gigtrack.databinding.FragmentExploreBinding;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;
    private SongAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(requireContext());
        
        // Setup RecyclerView
        adapter = new SongAdapter();
        binding.rvSongs.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvSongs.setAdapter(adapter);

        // Click to Navigate to Detail
        adapter.setOnItemClickListener(song -> {
            Intent intent = new Intent(requireContext(), DetailPracticeActivity.class);
            intent.putExtra("TITLE", song.getTitle());
            intent.putExtra("ARTIST", song.getArtist().getName());
            intent.putExtra("COVER", song.getAlbum().getCoverMedium());
            startActivity(intent);
        });

        // Search Button
        binding.btnSearch.setOnClickListener(v -> {
            String query = binding.etQuery.getText().toString().trim();
            if (!query.isEmpty()) {
                searchSongs(query);
            }
        });
    }

    private void searchSongs(String query) {
        binding.progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getService().searchSongs(query).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (!isAdded()) return;
                binding.progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setSongs(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                if (!isAdded()) return;
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Offline Mode: Loading last history", Toast.LENGTH_SHORT).show();
                loadOfflineData();
            }
        });
    }

    private void loadOfflineData() {
        List<Song> offlineSongs = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllLatihan();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String judul = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_JUDUL));
                String artis = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ARTIS));
                
                // Map history back to Song model (simplified)
                Song song = new Song();
                song.setTitle(judul);
                
                Artist artist = new Artist();
                artist.setName(artis);
                song.setArtist(artist);
                
                Album album = new Album();
                album.setCoverMedium(""); // No cover in offline history table
                song.setAlbum(album);

                offlineSongs.add(song);
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.setSongs(offlineSongs);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
