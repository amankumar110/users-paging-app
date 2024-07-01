package in.amankumar110.usersapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.RecyclerView;

import in.amankumar110.usersapp.databinding.LoadingStateItemLayoutBinding;
import in.amankumar110.usersapp.utils.AlertHelper;

public class LoadStateAdapter extends androidx.paging.LoadStateAdapter<LoadStateAdapter.LoadStateViewHolder> {

    private LoadingStateItemLayoutBinding binding;
    private final DialogInterface.OnClickListener retryCallback;

    public LoadStateAdapter(DialogInterface.OnClickListener retryCallback){
        this.retryCallback = retryCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull LoadStateViewHolder loadStateViewHolder, @NonNull LoadState loadState) {
        bind(loadState);
    }

    @NonNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull LoadState loadState) {
        binding = LoadingStateItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new LoadStateViewHolder(binding);
    }

    public void bind(LoadState loadState) {

        if (loadState instanceof LoadState.Error) {
            Context context = binding.getRoot().getContext();
            LoadState.Error error = (LoadState.Error) loadState;
            AlertHelper.alert(context,
                    "Networking Error!",
                    error.getError().getLocalizedMessage(),
                    false,
                    "Retry",
                    retryCallback,
                    null,
                    null);
        }

        binding.progressBar.setVisibility(View.VISIBLE);
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {

    private LoadingStateItemLayoutBinding binding;
        public LoadStateViewHolder(LoadingStateItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
