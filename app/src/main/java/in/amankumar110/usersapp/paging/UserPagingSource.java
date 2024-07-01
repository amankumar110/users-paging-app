package in.amankumar110.usersapp.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import java.io.IOException;
import java.util.List;

import in.amankumar110.usersapp.models.Response;
import in.amankumar110.usersapp.models.User;
import in.amankumar110.usersapp.service.ApiService;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserPagingSource extends RxPagingSource<Integer, User> {

    @NonNull
    @Override
    public Single<LoadResult<Integer, User>> loadSingle(@NonNull LoadParams<Integer> loadParams) {

        int page = loadParams.getKey() == null ? 1 : loadParams.getKey();

        return ApiService.getService()
                .getUsers(page, 10)
                .subscribeOn(Schedulers.io())
                .map(res -> res.getData().getContent())
                .doOnSuccess(results -> {
                    results.forEach(User::setImagePath);
                })
                .map(results -> toLoadResult(results, page))
                .onErrorReturn(e -> {
                    if (e instanceof IOException) {
                        Log.e("UserPagingSource", "Network Error: " + e.getLocalizedMessage(), e);
                    } else {
                        Log.e("UserPagingSource", "Error loading users: " + e.getLocalizedMessage(), e);
                    }
                    return new LoadResult.Error<>(e);
                });
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, User> pagingState) {
        return null;
    }

    public LoadResult<Integer, User> toLoadResult(List<User> data, @NonNull Integer page) {
        return new LoadResult.Page<>(data,
                page == 1 ? null : page - 1,
                data.isEmpty() ? null : page + 1);
    }
}
