package com.bus.bdtravelroute.mhraju.travelroute.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bus.bdtravelroute.mhraju.travelroute.MainActivity;
import com.bus.bdtravelroute.mhraju.travelroute.R;
import com.bus.bdtravelroute.mhraju.travelroute.Route;
import com.bus.bdtravelroute.mhraju.travelroute.RouteDetail;
import com.bus.bdtravelroute.mhraju.travelroute.database.RouteManager;

import java.util.ArrayList;

/**
 * Created by supto on 7/16/2016.
 */
public class DisplayRouteAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList<Route> routeArrayList;
    RouteManager routeManager;

    public DisplayRouteAdapter(Context context, int resource, ArrayList<Route> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.routeArrayList = objects;

        routeManager = new RouteManager(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.list_item_display_route, null);

            viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        viewHolder.getVehiclType().setText(routeArrayList.get(position).getVehicleType());
        viewHolder.getStartingPoint().setText(routeArrayList.get(position).getStartingPoint());
        viewHolder.getEndingPoint().setText(routeArrayList.get(position).getEndingPoint());


//        rowView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setCancelable(false);
//                alertDialogBuilder.setTitle("Delete Route");
//                alertDialogBuilder.setMessage("Are you sure to delete the list?");
////                alertDialogBuilder.setIcon(getResources().getDrawable(R.drawable.ic_action_warning));
//                alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        routeManager.deleteRoute(routeArrayList.get(position).getId());
//                        routeArrayList.remove(position);
//                        notifyDataSetChanged();
//                    }
//                });
//                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog alert = alertDialogBuilder.create();
//                alert.show();
//                return true;
//            }
//        });




        return rowView;
    }

    public class ViewHolder {
        TextView vehiclType;
        TextView startingPoint;
        TextView endingPoint;

        public ViewHolder(View view) {
            this.vehiclType = (TextView) view.findViewById(R.id.vehicleTypeDisplayRouteTV);
            this.startingPoint = (TextView) view.findViewById(R.id.startingPointDisplayRouteTV);
            this.endingPoint = (TextView) view.findViewById(R.id.endingPointDisplayRouteTV);
        }

        public TextView getVehiclType() {
            return vehiclType;
        }

        public TextView getStartingPoint() {
            return startingPoint;
        }

        public TextView getEndingPoint() {
            return endingPoint;
        }
    }
}
