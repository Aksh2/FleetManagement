package learning.com.fleetmanagement.interactors;

import java.util.List;

import learning.com.fleetmanagement.data.models.FleetVehicleModel;

public interface GetFleetListInteractor {

    interface onFinishedListener{
        void onFinished(List<FleetVehicleModel> fleetVehicleModels);
    }

    void getFleetList(onFinishedListener listener);
}
