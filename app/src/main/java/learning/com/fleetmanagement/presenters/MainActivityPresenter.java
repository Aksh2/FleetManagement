package learning.com.fleetmanagement.presenters;


import java.util.Collections;
import java.util.List;

import learning.com.fleetmanagement.data.firestore.FirestoreHelper;
import learning.com.fleetmanagement.data.models.FleetVehicleModel;
import learning.com.fleetmanagement.interactors.GetFleetListInteractor;

public class MainActivityPresenter implements GetFleetListInteractor.onFinishedListener {

    public static final String TAG = MainActivityPresenter.class.getSimpleName();

    private View view;
    private FirestoreHelper firestoreHelper;

    public MainActivityPresenter(View view,FirestoreHelper firestoreHelper) {
        this.view = view;
        this.firestoreHelper=firestoreHelper;
    }

    public void initialiseFleetObjects(){
        view.showProgress();
        this.firestoreHelper.getFleetList(this);

    }

    public void onDestory(){
        view=null;
        firestoreHelper=null;
    }

    public interface View{
           void showFleetListInRecyclerView(List<FleetVehicleModel> fleetVehicleModels);
           void showProgress();
           void hideProgress();

    }

    @Override
    public void onFinished(List<FleetVehicleModel> fleetVehicleModels) {
        view.hideProgress();
        view.showFleetListInRecyclerView(fleetVehicleModels);

    }
}
