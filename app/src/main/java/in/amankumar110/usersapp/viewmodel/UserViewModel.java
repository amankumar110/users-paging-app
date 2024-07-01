package in.amankumar110.usersapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import in.amankumar110.usersapp.models.User;
import in.amankumar110.usersapp.paging.UserPagingSource;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class UserViewModel extends ViewModel {

    public Flowable<PagingData<User>> flowable;

    public UserViewModel() {
        init();
    }

    private void init() {

        Pager<Integer,User> pager = new Pager<>(
                new PagingConfig(10,
                        10,
                        false,
                        10,
                        10*499),
                UserPagingSource::new);

        this.flowable = PagingRx.getFlowable(pager);
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(flowable,viewModelScope);
    }

}
