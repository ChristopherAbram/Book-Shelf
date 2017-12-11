package com.bookshelf.activity.authorized;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bookshelf.R;
import com.bookshelf.activity.AccountActivity;
import com.bookshelf.activity.CategoriesActivity;
import com.bookshelf.activity.FrontActivity;
import com.bookshelf.activity.HistoryActivity;
import com.bookshelf.activity.ItemActivity;
import com.bookshelf.activity.ItemsActivity;
import com.bookshelf.activity.SearchActivity;
import com.bookshelf.activity.SettingsActivity;
import com.bookshelf.activity.ShoppingCartActivity;
import com.bookshelf.adapter.ItemsAdapter;
import com.bookshelf.api.ItemService;
import com.bookshelf.api.RoleService;
import com.bookshelf.data.Item;
import com.bookshelf.data.Role;
import com.bookshelf.data.User;
import com.bookshelf.data.collection.Items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class HomeActivity extends AuthorizedActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = getClass().getName();
    private final int LOG_OUT_CODE = 1;

    @BindView(R.id.text)
    TextView mRoles;

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        RoleService roles = generateCallService(RoleService.class);
        Call<Role> call = roles.getRoleById(1);
        call.enqueue(new RoleCallback());

        ItemService service = generateCallService(ItemService.class);
        Call<Items> callItems = service.getItems();
        callItems.enqueue(new ItemsCallback());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        ComponentName componentName = new ComponentName(getApplicationContext(), SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        searchView.clearFocus();
                        searchView.setQuery("", false);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                }
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.shopping_cart) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_log_out) {
            showProgressBar();
            Intent intent = new Intent(this, SignOutActivity.class);
            startActivityForResult(intent, LOG_OUT_CODE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LOG_OUT_CODE) {
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(getBaseContext(), "Successfully Signed Out.", Toast.LENGTH_LONG).show();
                toFront();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                hideProgressBar();
                if(data.getExtras().containsKey("result"))
                    Toast.makeText(getBaseContext(), data.getExtras().get("result").toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void toFront(){
        Intent intent = new Intent(this, FrontActivity.class);
        startActivity(intent);
        finish();
    }

    public void toSaleItems(View view) {
        Intent intent = new Intent(this, ItemsActivity.class);
        intent.putExtra("searchType","sale");
        startActivity(intent);
    }

    public void toRandomItem(View view) {
        ItemService service = generateCallService(ItemService.class);
        Call<Items> callItems = service.getItems();
        callItems.enqueue(new RandomItemsCallback());
    }

    public void toAccount(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void toCategories(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivity(intent);
    }

    public void toItem(View view) {
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("itemID", "55555555");
        startActivity(intent);
    }

    private class RoleCallback extends Callback<Role> {
        @Override
        public void onResponse(Call<Role> call, Response<Role> response) {
            super.onResponse(call, response);

            if(response.isSuccessful()){
                mRoles.setText(response.body().toString());
            }
            else
                Toast.makeText(HomeActivity.this, "Unable to get role...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Role> call, Throwable t) {
            super.onFailure(call, t);

            Toast.makeText(HomeActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
        }
    }

    private class RandomItemsCallback extends Callback<Items> {

        @Override
        public void onResponse(Call<Items> call, Response<Items> response) {
            super.onResponse(call, response);

            if(response.isSuccessful()){
                Intent intent = new Intent(HomeActivity.this, ItemActivity.class);
                ArrayList<Item> list = response.body().getItems();
                Collections.shuffle(list);
                intent.putExtra("item", list.get(0));
                startActivity(intent);
            }
            else
                Toast.makeText(HomeActivity.this, "Unable to get items...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Items> call, Throwable t) {
            super.onFailure(call, t);

            Toast.makeText(HomeActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }

    }

    private class ItemsCallback extends Callback<Items> {

        @Override
        public void onResponse(Call<Items> call, Response<Items> response) {
            super.onResponse(call, response);

            if(response.isSuccessful()){
                final ArrayList<Item>  list = response.body().getItems();
                Collections.shuffle(list);
                ItemsAdapter adapter = new ItemsAdapter(getBaseContext(), list);
                listView.setAdapter(adapter);
                hideProgressBar();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Item item = list.get(position);
                        Intent intent = new Intent(HomeActivity.this, ItemActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                    }
                });
            }
            else
                Toast.makeText(HomeActivity.this, "Unable to get items...", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<Items> call, Throwable t) {
            super.onFailure(call, t);

            Toast.makeText(HomeActivity.this, "Ooopsss...", Toast.LENGTH_LONG).show();
        }
    }

}
