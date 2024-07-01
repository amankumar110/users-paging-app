package in.amankumar110.usersapp;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import in.amankumar110.usersapp.databinding.UserItemLayoutBinding;
import in.amankumar110.usersapp.models.User;

public class UserAdapter extends PagingDataAdapter<User, UserAdapter.ViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_USER = 1;
    public UserAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemLayoutBinding binding = UserItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
       return position==getItemCount()?VIEW_TYPE_LOADING:VIEW_TYPE_USER;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.v("users",getItemCount()+"");
        User user = getItem(position);
        holder.binding.setUser(user);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        UserItemLayoutBinding binding;
        public ViewHolder(@NonNull UserItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

