package learning.com.fleetmanagement.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import learning.com.fleetmanagement.R;
import learning.com.fleetmanagement.view.adapters.FleetVehicleAdapter;
import learning.com.fleetmanagement.data.firestore.FirestoreHelper;
import learning.com.fleetmanagement.data.models.FleetVehicleModel;
import learning.com.fleetmanagement.presenters.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    static final String TAG=MainActivity.class.getSimpleName();
    private MainActivityPresenter presenter;
    private RecyclerView recyclerView;
    private FleetVehicleAdapter fleetVehicleAdapter;
    private ProgressBar progressBar;
    private ConstraintLayout emptyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        presenter = new MainActivityPresenter(this,new FirestoreHelper());
       initializeRecyclerView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.presenter.initialiseFleetObjects();
    }

    private void initializeViews(){
        recyclerView = findViewById(R.id.fleetRv);
        progressBar=findViewById(R.id.progressBar);
        emptyList=findViewById(R.id.layout_empty_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FormActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        fleetVehicleAdapter = new FleetVehicleAdapter(this);
        recyclerView.setAdapter(fleetVehicleAdapter);
    }

    @Override
    public void showFleetListInRecyclerView(List<FleetVehicleModel> fleetVehicleModels) {
        Log.d(TAG,"fleetVehicles: "+ fleetVehicleModels);
        if(!fleetVehicleModels.isEmpty()) {
            emptyList.setVisibility(View.GONE);
            fleetVehicleAdapter.setData(fleetVehicleModels);
        }else {
            progressBar.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
