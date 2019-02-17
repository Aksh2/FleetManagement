package learning.com.fleetmanagement.presenters;

import android.util.Log;

import learning.com.fleetmanagement.Constants;
import learning.com.fleetmanagement.view.FormActivity;
import learning.com.fleetmanagement.data.firestore.FirestoreHelper;
import learning.com.fleetmanagement.data.models.FleetVehicleModel;

public class FormActivityPresenter {
    private View view;
    private FirestoreHelper firestoreHelper;
    private static final String  TAG=FormActivity.class.getSimpleName();

    public FormActivityPresenter(View view, FirestoreHelper firestoreHelper) {
        this.view = view;
        this.firestoreHelper=firestoreHelper;
    }

    public void storeFleetItems(String name,String number,String vehicleNumber,String vehicleType){
        Log.d(TAG,"storeFleetItems: name: "+ name+" ,number: "+number+" ,vehicleNumber: "+vehicleNumber+" ,vehicleType: "+vehicleType);

        FleetVehicleModel fleetVehicleModel = new FleetVehicleModel();
        fleetVehicleModel.setName(name);
        fleetVehicleModel.setMobile(number);
        fleetVehicleModel.setVehicleNumber(vehicleNumber);
        fleetVehicleModel.setVehicleType(vehicleType);
        if(validate(fleetVehicleModel)){
            firestoreHelper.saveFleet(fleetVehicleModel);
            view.closeActivity();
        }

    }

    public interface View{
        void onSubmitButtonClicked();
        void closeActivity();
        void showError(String message);
    }

    private boolean validate(FleetVehicleModel fleetVehicleModel){
        boolean flag=true;
        if(fleetVehicleModel.getName().isEmpty()||fleetVehicleModel.getName()==null){
            view.showError(String.format("%s %s",Constants.FIELD_NAME,Constants.BASE_EMPTY_ERROR_MSG));
            flag=false;
        }else if(fleetVehicleModel.getMobile().isEmpty()||fleetVehicleModel.getMobile()==null){
            view.showError(String.format("%s %s",Constants.FIELD_MOBILE,Constants.BASE_EMPTY_ERROR_MSG));
            flag=false;
        }else if(fleetVehicleModel.getVehicleNumber().isEmpty()||fleetVehicleModel.getVehicleNumber()==null){
            view.showError(String.format("%s %s",Constants.FIELD_VEHICLE_NUMBER,Constants.BASE_EMPTY_ERROR_MSG));
            flag=false;
        }else if(fleetVehicleModel.getVehicleType().isEmpty()||fleetVehicleModel.getVehicleType()==null){
            view.showError(String.format("%s %s",Constants.FIELD_VEHICLE_TYPE,Constants.BASE_EMPTY_ERROR_MSG));
            flag=false;
        }
        return flag;
    }

    public void onDestory(){
        view=null;
        firestoreHelper=null;
    }


}
