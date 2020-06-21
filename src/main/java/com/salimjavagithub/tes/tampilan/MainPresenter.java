package com.salimjavagithub.tes.tampilan;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.salimjavagithub.tes.api.ApiClient;
import com.salimjavagithub.tes.api.ApiInterface;
import com.salimjavagithub.tes.model.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MainPresenter implements MainView.GetUsers {

    private MainView.InitView initView;
    public MainPresenter(MainView.InitView initView){
        this.initView = initView;
    }

    @Override
    public void getUserList(final String keyword, Integer page) {
        initView.showLoading();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Users> call = apiInterface.getUsers(keyword ,"30" ,"1");
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(@NonNull Call<Users> call, @NonNull Response<Users> response) {

                initView.hideLoading();
                initView.userList(response.body().getItems());
                int totalCount = response.body().getTotalCount();

                if (!response.isSuccessful() || response.body().getItems() == null || totalCount == 0) {
                    initView.userListFailure("No Result for '" + keyword + "'", "Try Searching for Other Users");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Users> call, @NonNull Throwable t) {
                initView.userListFailure("Error Loading For '" + keyword+ "'", t.toString());
                initView.hideLoading();
                t.printStackTrace();
            }
        });
    }




}
