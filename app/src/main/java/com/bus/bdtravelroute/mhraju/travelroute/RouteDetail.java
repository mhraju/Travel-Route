package com.bus.bdtravelroute.mhraju.travelroute;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bus.bdtravelroute.mhraju.travelroute.database.RouteManager;

public class RouteDetail extends AppCompatActivity {

    ImageView vehicleTypeImageView;
    TextView routeNameTV;

    TextView ticketPriceTV;
    TextView viaTV;
    TextView commentTV;
    TextView userNmaeTV;
    Route route;
    SharedPreferences preferences;
    String loggedInUserName;
    RouteManager routeManager = new RouteManager(this);

    int routeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loggedInUserName = preferences.getString("userName", "unknownUser");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vehicleTypeImageView = (ImageView) findViewById(R.id.vehicleTypeImageView);
        routeNameTV = (TextView) findViewById(R.id.routeNameTV);

        ticketPriceTV = (TextView) findViewById(R.id.tpRouteDetailTV);
        viaTV = (TextView) findViewById(R.id.viaRouteDetailTV);
        commentTV = (TextView) findViewById(R.id.commentRouteDetailTV);
        userNmaeTV = (TextView) findViewById(R.id.userNameRouteDetailTV);

        routeId = getIntent().getIntExtra("routeId", 0);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        route = routeManager.getRoute(routeId);
        displayRouteDetailUIComponent(route);

        getSupportActionBar().setTitle(route.getStartingPoint() + " to " + route.getEndingPoint());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.route_detail_menu, menu);

        MenuItem edit = menu.findItem(R.id.editMenuButton);
        MenuItem delete = menu.findItem(R.id.deleteMenuButton);

        if (!loggedInUserName.contentEquals(route.getUserName())) {
            edit.setVisible(false);
            delete.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.deleteMenuButton) {
            deleteFromMenu(route);
        } else if (id == R.id.editMenuButton) {
            updateRouteFromMenu(route);
        } else if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void deleteFromMenu(final Route route) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Delete Route");
        alertDialogBuilder.setMessage("Are you sure to delete the Route?");
        alertDialogBuilder.setIcon(getResources().getDrawable(R.drawable.ic_action_warning));
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                routeManager.deleteRoute(route);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

    private void updateRouteFromMenu(final Route route) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_add_route, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setCancelable(true);

        ArrayAdapter<String> spineerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.vehicle_type));
        final Spinner selectVehicleTypeSpinner = (Spinner) view.findViewById(R.id.selectVehicleTypeSpinner);
        selectVehicleTypeSpinner.setAdapter(spineerAdapter);

        ArrayAdapter<String> selectStartingPointAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
        final Spinner selectStartingPointSpinner = (Spinner) view.findViewById(R.id.selectStartingPointSpinner);
        selectStartingPointSpinner.setAdapter(selectStartingPointAdapter);

        ArrayAdapter<String> selectEndingPointAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city));
        final Spinner selectEndingPointSpinner = (Spinner) view.findViewById(R.id.selectEndingPointSpinner);
        selectEndingPointSpinner.setAdapter(selectEndingPointAdapter);

        final EditText ticketPriceET = (EditText) view.findViewById(R.id.ticketpriceETInsertDialog);
        final EditText viaET = (EditText) view.findViewById(R.id.viaETInsertDialog);
        final EditText commentET = (EditText) view.findViewById(R.id.commentETInsertDialog);

        //TODO spinner add korte hobe

        selectVehicleTypeSpinner.setSelection(spineerAdapter.getPosition(route.getVehicleType()));
        selectStartingPointSpinner.setSelection(selectStartingPointAdapter.getPosition(route.getStartingPoint()));
        selectEndingPointSpinner.setSelection(selectEndingPointAdapter.getPosition(route.getEndingPoint()));

        ticketPriceET.setText(route.getTicketPrice());
        viaET.setText(route.getVia());
        commentET.setText(route.getComment());

        alertDialogBuilder.setPositiveButton("Update Route", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                Route updatedRoute = new Route();
                updatedRoute.setId(route.getId());
                updatedRoute.setVehicleType(selectVehicleTypeSpinner.getSelectedItem().toString());

                updatedRoute.setStartingPoint(selectStartingPointSpinner.getSelectedItem().toString());
                updatedRoute.setEndingPoint(selectEndingPointSpinner.getSelectedItem().toString());
                updatedRoute.setTicketPrice(ticketPriceET.getText().toString());
                updatedRoute.setVia(viaET.getText().toString());
                updatedRoute.setComment(commentET.getText().toString());
                updatedRoute.setUserName(route.getUserName());

                boolean updated = routeManager.updateRoute(updatedRoute);
                if (updated) {
                    Toast.makeText(RouteDetail.this, "Updated", Toast.LENGTH_SHORT).show();
                    onPostResume();
                } else {
                    Toast.makeText(RouteDetail.this, "Not Updated", Toast.LENGTH_SHORT).show();

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

    private void displayRouteDetailUIComponent(Route route) {

        if (route.getVehicleType().contentEquals("Bus")) {
            vehicleTypeImageView.setImageResource(R.drawable.ic_bus);

        } else if (route.getVehicleType().contentEquals("Train")) {
            vehicleTypeImageView.setImageResource(R.drawable.ic_train);

        } else if (route.getVehicleType().contentEquals("Launch")) {
            vehicleTypeImageView.setImageResource(R.drawable.ic_boat);
        }
        routeNameTV.setText(route.getStartingPoint() + " to " + route.getEndingPoint());


        if (route.getTicketPrice().length() == 0) {
            ticketPriceTV.setText("NA");
        } else {
            ticketPriceTV.setText(route.getTicketPrice());
        }

        if(route.getVia().length()==0){
            viaTV.setText("NA");
        }else {
            viaTV.setText(route.getVia());
        }

        if (route.getComment().length() == 0) {
            commentTV.setText("NA");
        } else {
            commentTV.setText(route.getComment());

        }

        if (route.getUserName().length() == 0) {
            userNmaeTV.setText("NA");
        } else {
            userNmaeTV.setText(route.getUserName());
        }
    }


}
