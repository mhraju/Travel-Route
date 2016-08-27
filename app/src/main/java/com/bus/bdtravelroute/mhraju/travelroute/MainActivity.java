package com.bus.bdtravelroute.mhraju.travelroute;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bus.bdtravelroute.mhraju.travelroute.adapter.DisplayRouteAdapter;
import com.bus.bdtravelroute.mhraju.travelroute.database.RouteManager;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    RouteManager routeManager;
    FloatingActionButton addRouteFab;
    ListView displayRouteListView;
    ArrayList<Route> routeList;
    DisplayRouteAdapter displayRouteAdapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String loggedInUserName;
    LinearLayout headLineLayoutMainActivity;
    TextView errorNoteTvMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loggedInUserName = preferences.getString("userName", "unknownUser");

        getSupportActionBar().setTitle(loggedInUserName + " 's route");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        routeManager = new RouteManager(this);
        addRouteFab = (FloatingActionButton) findViewById(R.id.addRouteFab);
        displayRouteListView = (ListView) findViewById(R.id.displayUsersRouteListView);
        headLineLayoutMainActivity = (LinearLayout) findViewById(R.id.headLineLayoutMainActivity);
        headLineLayoutMainActivity.setVisibility(View.INVISIBLE);

        errorNoteTvMainActivity = (TextView) findViewById(R.id.errorNoteTvMainActivity);
        errorNoteTvMainActivity.setVisibility(View.INVISIBLE);

        routeList = new ArrayList<>();

        addRouteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRoute();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        routeList = routeManager.getUserRoutes(loggedInUserName);

        if (routeList.size() == 0) {
            headLineLayoutMainActivity.setVisibility(View.INVISIBLE);
            errorNoteTvMainActivity.setVisibility(View.VISIBLE);
        } else {
            headLineLayoutMainActivity.setVisibility(View.VISIBLE);
            errorNoteTvMainActivity.setVisibility(View.INVISIBLE);

        }

        displayRouteAdapter = new DisplayRouteAdapter(this, R.layout.list_item_display_route, routeList);
        displayRouteListView.setAdapter(displayRouteAdapter);
        displayRouteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RouteDetail.class);
                intent.putExtra("routeId", routeList.get(position).getId());
                startActivity(intent);
            }
        });
    }


    private void signOutUser() {
        editor = preferences.edit();
        editor.putString("userName", "unknownUser").apply();
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void insertRoute() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_add_route, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);


        ArrayAdapter<String> selectVehicleTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.vehicle_type));
        final Spinner selectVehicleTypeSpinner = (Spinner) view.findViewById(R.id.selectVehicleTypeSpinner);
        selectVehicleTypeSpinner.setAdapter(selectVehicleTypeAdapter);

        ArrayAdapter<String> selectStartingPointAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
        final Spinner selectStartingPointSpinner = (Spinner) view.findViewById(R.id.selectStartingPointSpinner);
        selectStartingPointSpinner.setAdapter(selectStartingPointAdapter);

        ArrayAdapter<String> selectEndingPointAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
        final Spinner selectEndingPointSpinner = (Spinner) view.findViewById(R.id.selectEndingPointSpinner);
        selectEndingPointSpinner.setAdapter(selectEndingPointAdapter);

        final EditText ticketPriceET = (EditText) view.findViewById(R.id.ticketpriceETInsertDialog);
        final EditText viaET = (EditText) view.findViewById(R.id.viaETInsertDialog);
        final EditText commentET = (EditText) view.findViewById(R.id.commentETInsertDialog);

        alertDialogBuilder.setPositiveButton("Insert Route", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Route route = new Route();
                route.setVehicleType(selectVehicleTypeSpinner.getSelectedItem().toString());
                route.setStartingPoint(selectStartingPointSpinner.getSelectedItem().toString());
                route.setEndingPoint(selectEndingPointSpinner.getSelectedItem().toString());
                route.setTicketPrice(ticketPriceET.getText().toString());
                route.setVia(viaET.getText().toString());
                route.setComment(commentET.getText().toString());
                route.setUserName(loggedInUserName);

                boolean inserted = routeManager.addRoute(route);
                if (inserted) {
                    Toast.makeText(MainActivity.this, "Your route is added", Toast.LENGTH_SHORT).show();
                    onPostResume();
                }
            }

        });


        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });


        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}

