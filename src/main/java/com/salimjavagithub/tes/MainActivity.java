package com.salimjavagithub.tes;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.salimjavagithub.tes.api.ApiClient;
import com.salimjavagithub.tes.api.ApiInterface;
import com.salimjavagithub.tes.model.Items;
import com.salimjavagithub.tes.model.Users;
import com.salimjavagithub.tes.tampilan.Adapter;
import com.salimjavagithub.tes.tampilan.EndlessOnScrollListener;
import com.salimjavagithub.tes.tampilan.MainPresenter;
import com.salimjavagithub.tes.tampilan.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainView.InitView {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MainPresenter mainPresenter;
    private RelativeLayout emptyView;
    private TextView errorTitle, errorMessage;
    LinearLayoutManager manager;
    int currentItems, totalItems, scrollOutItems;
    private List<Items> users;
    private  List<Users> news_list;
    Boolean isScrolling = false;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int page;
    private int pagenumber;

    private int currentPage = PAGE_START;
    private ApiInterface gitService;
    private Adapter adapter;
    private int scrollData;
    private static String queryx;

    // private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mainPresenter = new MainPresenter(this) {
            @Override
            public void getUserList(String keyword) {

            }
        };
    }

    private void init() {

        emptyView = findViewById(R.id.empty_view);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        progressBar = findViewById(R.id.progress);

        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
     //   manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.addOnScrollListener(scrollData(page));
        


    }

    private EndlessOnScrollListener scrollData(final Integer page) {
        return new EndlessOnScrollListener() {
            @Override
            public void onLoadMore() {
                //masukan disini methods atau action mengambil data baru
                //presenter.getDataNews(page,"","desc");

                mainPresenter.getUserList(queryx,page);
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu );

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Search Github Users");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (users != null){
                    users.clear();
                }
                errorView(View.VISIBLE, "Test Visible", "Search Github Users");
                return true;
            }
        });


        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    page= currentPage;
                    mainPresenter.getUserList(query,page);
                    queryx=query;
                    return false;
                }
            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });

      //  RecyclerView.OnScrollListener()

        return true;
    }




    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        errorView(View.INVISIBLE, "", "");
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void userList(List<Items> items) {
        if (users != null){
            users.clear();
        }
        users = items;
        Adapter adapter = new Adapter(users, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void userListFailure(String errorMessage, String keyword) {
        errorView(View.VISIBLE,  errorMessage, keyword);
    }

    private void errorView(int visibility, String title, String message){
        emptyView.setVisibility(visibility);
        errorTitle.setText(title);
        errorMessage.setText(message);
    }



}
