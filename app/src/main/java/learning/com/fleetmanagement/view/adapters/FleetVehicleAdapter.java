package learning.com.fleetmanagement.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import learning.com.fleetmanagement.R;
import learning.com.fleetmanagement.data.models.FleetVehicleModel;

public class FleetVehicleAdapter extends RecyclerView.Adapter<FleetVehicleAdapter.FleetItemsViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<FleetVehicleModel> fleetVehicleModelList = new ArrayList<>();

    public FleetVehicleAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public FleetItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.fleet_item_list,parent,false);
        return new FleetVehicleAdapter.FleetItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FleetItemsViewHolder holder, int position) {
        holder.nameTv.setText(fleetVehicleModelList.get(position).getName());
        holder.numberTv.setText(fleetVehicleModelList.get(position).getMobile());
        holder.vehicleTv.setText(fleetVehicleModelList.get(position).getVehicleNumber());
        holder.typeTv.setText(fleetVehicleModelList.get(position).getVehicleType());
    }

    @Override
    public int getItemCount() {
        return fleetVehicleModelList.size();
    }

    public void setData(List<FleetVehicleModel> fleetVehicleModels){
        this.fleetVehicleModelList=fleetVehicleModels;
        notifyDataSetChanged();
    }

    static class FleetItemsViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTv;
        private TextView numberTv;
        private TextView vehicleTv;
        private TextView typeTv;

        public FleetItemsViewHolder(View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.nameItemTv);
            numberTv=itemView.findViewById(R.id.numberItemTv);
            vehicleTv=itemView.findViewById(R.id.vehicleNumberItemTv);
            typeTv=itemView.findViewById(R.id.vehicleTypeItemTv);
        }
    }
}
