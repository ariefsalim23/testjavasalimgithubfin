package com.salimjavagithub.tes.tampilan;

import com.salimjavagithub.tes.model.Items;

import java.util.List;

public interface MainView {

    interface InitView {

        void showLoading();
        void hideLoading();
        void userList(List<Items> users);
        void userListFailure(String errorMessage, String keyword);

    }

    interface GetUsers { void getUserList(String keyword);

        void getUserList(String keyword, Integer page);
    }
}
