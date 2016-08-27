package com.bus.bdtravelroute.mhraju.travelroute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bus.bdtravelroute.mhraju.travelroute.adapter.DisplayRouteAdapter;
import com.bus.bdtravelroute.mhraju.travelroute.database.RouteManager;

import java.util.ArrayList;

public class AllRouteActivity extends AppCompatActivity {

    ListView allRouteListView;
    RouteManager routeManager;
    ArrayList<Route> routeList;
    DisplayRouteAdapter displayRouteAdapter;
    LinearLayout headLineLayoutAllRouteActivity;
    TextView errorNoteTvAllRouteActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allRouteListView = (ListView) findViewById(R.id.displayAllRouteListView);
        routeManager = new RouteManager(this);
        routeList = new ArrayList<>();

        headLineLayoutAllRouteActivity = (LinearLayout) findViewById(R.id.headLineLayoutAllRouteActivity);
        headLineLayoutAllRouteActivity.setVisibility(View.INVISIBLE);

        errorNoteTvAllRouteActivity = (TextView) findViewById(R.id.errorNoteTvAllRouteActivity);
        errorNoteTvAllRouteActivity.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        routeList = routeManager.getAllRoute();
        if(routeList.size()==0){
            headLineLayoutAllRouteActivity.setVisibility(View.INVISIBLE);
            errorNoteTvAllRouteActivity.setVisibility(View.VISIBLE);
        }else{
            headLineLayoutAllRouteActivity.setVisibility(View.VISIBLE);
            errorNoteTvAllRouteActivity.setVisibility(View.INVISIBLE);

        }


        displayRouteAdapter = new DisplayRouteAdapter(this, R.layout.list_item_display_route, routeList);

        allRouteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllRouteActivity.this, RouteDetail.class);
                intent.putExtra("routeId", routeList.get(position).getId());
                startActivity(intent);
            }
        });

        allRouteListView.setAdapter(displayRouteAdapter);
    }
}
