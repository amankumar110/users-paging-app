package in.amankumar110.usersapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.CombinedLoadStates;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import in.amankumar110.usersapp.databinding.ActivityMainBinding;
import in.amankumar110.usersapp.utils.AlertHelper;
import in.amankumar110.usersapp.utils.UserComparator;
import in.amankumar110.usersapp.viewmodel.UserViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private UserAdapter userAdapter;
    private UserViewModel viewModel;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userAdapter = new UserAdapter(new UserComparator());
        initializeRecyclerView();

        viewModel.flowable.subscribe(data -> userAdapter.submitData(getLifecycle(),data));

        binding.swiperRefreshLayout.setOnRefreshListener(this::refreshCallback);
    }

    private void startAutoScroll() {
       Runnable autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (binding.userList.getLayoutManager() != null) {
                    int scrollAmount = 40; // Change this value to adjust scroll speed
                    binding.userList.smoothScrollBy(scrollAmount, 0);
                    handler.postDelayed(this, 100); // Repost the runnable for continuous scrolling
                }
            }
        };
        handler.postDelayed(autoScrollRunnable, 100); // Initial delay before starting the scroll
    }

    public void initializeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.userList.setLayoutManager(layoutManager);
        LoadStateAdapter adapter = new LoadStateAdapter((dialog,which)-> {
            userAdapter.retry();
            dialog.dismiss();
        });

        binding.userList.setAdapter(userAdapter.withLoadStateFooter(adapter));

        // Add LoadStateListener to handle global load states
        userAdapter.addLoadStateListener(loadStates -> {
            LoadState refreshState = loadStates.getRefresh();

            // Handle different load states
            if (refreshState instanceof LoadState.Error) {

                    // Handle error state
                LoadState.Error error = (LoadState.Error) refreshState;
                String errorMessage = error.getError().getLocalizedMessage();

                // Display an alert dialog for network errors
                AlertHelper.alert(MainActivity.this,
                        "Networking Error!",
                        errorMessage,
                        false,
                        "Retry",
                        (dialog, which) -> {
                            userAdapter.retry(); // Retry loading data
                            dialog.dismiss();
                        },
                        "Close",
                        (dialog, which) -> finish());
            }

            return null; // Return Unit as required by the function signature
        });

        // Start auto-scrolling if needed
        startAutoScroll();
    }

    public void refreshCallback() {
        userAdapter.refresh();
        binding.swiperRefreshLayout.setRefreshing(false);
    }



}