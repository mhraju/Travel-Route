package com.bus.bdtravelroute.mhraju.travelroute;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bus.bdtravelroute.mhraju.travelroute.adapter.DisplayRouteAdapter;
import com.bus.bdtravelroute.mhraju.travelroute.database.RouteManager;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ListView searchRouteListView;
    RadioGroup vehicleTypeRadioGroup;
    RadioButton selectedButton;
    TextView errorNoteTVSearch;

    ArrayList<Route> routeArrayList;
    DisplayRouteAdapter searchedRouteAdapter;
    RouteManager routeManager;

    Spinner selectStartingPointSpinnerSearch;
    Spinner selectEndingPointSpinnerSearch;
    ArrayAdapter<String> selectCitySpinnerSearchAdapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String loggedInUserName;

    LinearLayout headLineLayoutSearchActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loggedInUserName = preferences.getString("userName", "unknownUser");

        searchRouteListView = (ListView) findViewById(R.id.searchRouteListView);
        errorNoteTVSearch = (TextView) findViewById(R.id.errorNoteTvSearch);

        errorNoteTVSearch.setVisibility(View.INVISIBLE);

        vehicleTypeRadioGroup = (RadioGroup) findViewById(R.id.vehicleTypeRadioGroup);
        selectedButton = (RadioButton) findViewById(R.id.anyRadioButton);
        selectedButton.setChecked(true);


        routeManager = new RouteManager(this);
        routeArrayList = new ArrayList<>();

        headLineLayoutSearchActivity = (LinearLayout) findViewById(R.id.headLineLayoutSearchActivity);
        headLineLayoutSearchActivity.setVisibility(View.INVISIBLE);

        selectCitySpinnerSearchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
        selectStartingPointSpinnerSearch = (Spinner) findViewById(R.id.selectStartingPointSpinnerSearch);
        selectStartingPointSpinnerSearch.setAdapter(selectCitySpinnerSearchAdapter);
        selectEndingPointSpinnerSearch = (Spinner) findViewById(R.id.selectEndingPointSpinnerSearch);
        selectEndingPointSpinnerSearch.setAdapter(selectCitySpinnerSearchAdapter);


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        searchedRouteAdapter = new DisplayRouteAdapter(this, R.layout.list_item_display_route, routeArrayList);
        searchRouteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, RouteDetail.class);
                intent.putExtra("routeId", routeArrayList.get(position).getId());
                startActivity(intent);
            }
        });
        searchRouteListView.setAdapter(searchedRouteAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signOutButton) {
            signOutUser();
        } else if (id == R.id.viewAllRouteButton) {
            Intent intent = new Intent(SearchActivity.this, AllRouteActivity.class);
            startActivity(intent);
        } else if (id == R.id.myRouteButton) {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void signOutUser() {
        editor = preferences.edit();
        editor.putString("userName", "unknownUser").apply();
        Intent intent = new Intent(SearchActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void search(View view) {
        int selectedId = vehicleTypeRadioGroup.getCheckedRadioButtonId();
        selectedButton = (RadioButton) findViewById(selectedId);
        String vehicleType = selectedButton.getText().toString();

        String from = selectStartingPointSpinnerSearch.getSelectedItem().toString();
        String destination = selectEndingPointSpinnerSearch.getSelectedItem().toString();

        if (vehicleType.contentEquals("Any")) {
            routeArrayList = routeManager.searchRoute(from, destination);
        } else {
            routeArrayList = routeManager.searchRoute(vehicleType, from, destination);
        }

        if (routeArrayList.size() == 0) {
            errorNoteTVSearch.setVisibility(View.VISIBLE);
            headLineLayoutSearchActivity.setVisibility(View.INVISIBLE);
        } else {
            errorNoteTVSearch.setVisibility(View.INVISIBLE);
            headLineLayoutSearchActivity.setVisibility(View.VISIBLE);
            Toast.makeText(SearchActivity.this, "Found " + routeArrayList.size() + " routes", Toast.LENGTH_SHORT).show();
        }

        onPostResume();


    }
}
