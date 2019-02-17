package learning.com.fleetmanagement.data.firestore;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;


import java.util.ArrayList;
import java.util.List;

import learning.com.fleetmanagement.Constants;
import learning.com.fleetmanagement.data.models.FleetVehicleModel;
import learning.com.fleetmanagement.interactors.GetFleetListInteractor;

public class FirestoreHelper implements GetFleetListInteractor {

    private static final String TAG=FirestoreHelper.class.getSimpleName();
    private FirebaseFirestore db;
    private List<FleetVehicleModel> fleetVehicleModelList;
    public FirestoreHelper() {
        this.db = FirebaseFirestore.getInstance();
        fleetVehicleModelList=new ArrayList<>();
    }

    public void saveFleet(FleetVehicleModel fleetVehicleModel){
        db.collection("vehicles")
                .document(fleetVehicleModel.getName())
                .set(fleetVehicleModel,SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"Document snapshot saved successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Error writing the document",e);

                    }
                });
    }

    
    private String getValue(QueryDocumentSnapshot documentSnapshot,String key){
        if(documentSnapshot.contains(key)){
            return  String.valueOf(documentSnapshot.getData().get(key));
        }else{
            return null;
        }
    }

    @Override
    public void getFleetList(final onFinishedListener listener) {
        FirestoreHelper.this.fleetVehicleModelList.clear();
        db.collection("vehicles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Log.d(TAG,document.getId()+" => " + document.getData());
                                FleetVehicleModel fleetVehicleModel = new FleetVehicleModel();
                                fleetVehicleModel.setName(getValue(document,Constants.FIELD_NAME));
                                fleetVehicleModel.setMobile(getValue(document,Constants.FIELD_MOBILE));
                                fleetVehicleModel.setVehicleNumber(getValue(document,Constants.FIELD_VEHICLE_NUMBER));
                                fleetVehicleModel.setVehicleType(getValue(document,Constants.FIELD_VEHICLE_TYPE));
                                FirestoreHelper.this.fleetVehicleModelList.add(fleetVehicleModel);

                            }
                            listener.onFinished(fleetVehicleModelList);

                        }else{
                            Log.d(TAG,"Error getting documents: " + task.getException());
                        }
                    }
                });
    }
}
