package com.sisteminformasi.gigtrack;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sisteminformasi.gigtrack.databinding.ItemHistoryBinding;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> historyList = new ArrayList<>();

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(historyList.get(position));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemHistoryBinding binding;

        public HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(History history) {
            binding.tvHistoryTitle.setText(history.getJudul());
            binding.tvHistoryArtist.setText(history.getArtis());
            binding.tvHistoryDuration.setText(history.getDurasi());
            binding.tvHistoryNote.setText(history.getCatatan());
        }
    }
}
