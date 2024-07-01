package in.amankumar110.usersapp.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import in.amankumar110.usersapp.models.User;
public class UserComparator extends DiffUtil.ItemCallback<User> {
    @Override
    public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
        return oldItem.getId() == newItem.getId();
    }
}
