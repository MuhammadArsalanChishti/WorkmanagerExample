package com.mac.wm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mac.wm.loader.AppLoader;
import com.mac.wm.workers.PredictionWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private CardView progressBar;
    private TextView loading_messageTxt;
    List<String> ids = new ArrayList<>();
    AppLoader appLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*progressBar = findViewById(R.id.progress_circular);
        loading_messageTxt = findViewById(R.id.loading_messageTxt);*/
        //final AppLoader appLoader = new AppLoader(this,progressBar,loading_messageTxt,"start");
        //final AppLoader appLoader = new AppLoader(this,R.id.progress_circular,R.id.loading_messageTxt,"start");
         appLoader = new AppLoader(this,"Please Wait Getting Response... ");


        Button work_btn = findViewById(R.id.work_btn);
        work_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appLoader.showProgressBar();
                predictionWork();
            }
        });

    }
    public void predictionWork(){
      setIds();
        String[] predictionUrl = new String[ids.size()];
        for (int i = 0; i <ids.size() ; i++) {
            String url = "https://api.actransit.org"  + "?stpid=" + ids.get(i) + "&rt=&vid=" + "&token=" + "";

            predictionUrl[i] = url;
        }
        String[] myArray = new String[ids.size()];
        ids.toArray(myArray);


        Data.Builder inputData = new Data.Builder();
        inputData.putStringArray("url",predictionUrl);
        inputData.putStringArray("id",myArray);
        OneTimeWorkRequest apiWork = new OneTimeWorkRequest.Builder(PredictionWorker.class).setInputData(inputData.build()).build();
        WorkManager.getInstance(this).enqueue(apiWork);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(apiWork.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                String status = workInfo.getState().name();
                if(workInfo.getState() == WorkInfo.State.SUCCEEDED){
                    Data outputData = workInfo.getOutputData();
                    Toast.makeText(MainActivity.this, "Work finish", Toast.LENGTH_SHORT).show();
                    Log.e("onChanged", "onChanged: "+outputData.getString("outputData") );
                    appLoader.hideProgressBar();
                }
            }
        });
    }
    private void setIds(){
        ids.add("58123");
        ids.add("52246");
        ids.add("57793");
        ids.add("58355");
        ids.add("56210");
        /*ids.add("56036");
        ids.add("56051");
        ids.add("56718");
        ids.add("58350");
        ids.add("57890");
        ids.add("53211");
        ids.add("50433");
        ids.add("55402");
        ids.add("51814");
        ids.add("51816");
        ids.add("51817");
        ids.add("51819");
        ids.add("51823");
        ids.add("51530");
        ids.add("54001");
        ids.add("59881");
        ids.add("54710");
        ids.add("58478");
        ids.add("58123");
        ids.add("52246");
        ids.add("57793");
        ids.add("58355");
        ids.add("56210");
        ids.add("56036");
        ids.add("56051");
        ids.add("56718");
        ids.add("58350");
        ids.add("57890");
        ids.add("53211");
        ids.add("50433");
        ids.add("55402");
        ids.add("51814");
        ids.add("51816");
        ids.add("51817");
        ids.add("51819");
        ids.add("51823");
        ids.add("51530");
        ids.add("54001");
        ids.add("59881");
        ids.add("54710");
        ids.add("58478");
        ids.add("58123");
        ids.add("52246");
        ids.add("57793");
        ids.add("58355");
        ids.add("56210");
        ids.add("56036");
        ids.add("56051");
        ids.add("56718");
        ids.add("58350");
        ids.add("57890");
        ids.add("53211");
        ids.add("50433");
        ids.add("55402");
        ids.add("51814");
        ids.add("51816");
        ids.add("51817");
        ids.add("51819");
        ids.add("51823");
        ids.add("51530");
        ids.add("54001");
        ids.add("59881");
        ids.add("54710");
        ids.add("58478");*/
        Log.e("setIds", "setIds: count -> "+ids.size());
    }
}