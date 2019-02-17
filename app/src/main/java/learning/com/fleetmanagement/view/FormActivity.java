package learning.com.fleetmanagement.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import learning.com.fleetmanagement.R;
import learning.com.fleetmanagement.data.firestore.FirestoreHelper;
import learning.com.fleetmanagement.presenters.FormActivityPresenter;

public class FormActivity extends AppCompatActivity implements View.OnClickListener,FormActivityPresenter.View {

    private FormActivityPresenter presenter;
    private Button submitBtn;
    private EditText nameEt,mobileEt,vehicleNumberEt,vehicleTypeEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        initialize();

    }

    private void initialize(){
        presenter = new FormActivityPresenter(this,new FirestoreHelper());
        submitBtn = findViewById(R.id.submitBt);
        submitBtn.setOnClickListener(this);
        nameEt=findViewById(R.id.nameEt);
        mobileEt=findViewById(R.id.mobileEt);
        vehicleNumberEt=findViewById(R.id.vehicleNumberEt);
        vehicleTypeEt=findViewById(R.id.vehicleTypeEt);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSubmitButtonClicked() {
        presenter.storeFleetItems(nameEt.getText().toString(),
                mobileEt.getText().toString(),
                vehicleNumberEt.getText().toString(),
                vehicleTypeEt.getText().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitBt:
                onSubmitButtonClicked();
            break;
        }
    }

    @Override
    public void closeActivity() {
        this.finish();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
    }
}
