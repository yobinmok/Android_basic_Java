package com.example.androidbasic_step8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class JobSchedulerActivity extends AppCompatActivity {

    private JobScheduler mScheduler;
    private static final int JOB_ID = 0;
    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);

        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);
        mSeekBar = findViewById(R.id.seekBar);
        final TextView seekBarProgress = findViewById(R.id.seekBarProgress);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    seekBarProgress.setText(i + " s");
                } else {
                    seekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void scheduleJob(View view) {
        RadioGroup networkOptions = findViewById(R.id.networkOptions);
        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();
        // 기본값으로 초기화
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;

        switch (selectedNetworkID) {
            case R.id.noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName serviceName = new ComponentName(getPackageName(),NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked())
                .setRequiresCharging(mDeviceChargingSwitch.isChecked());

        int seekBarInteger = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;
        if(seekBarSet)
            builder.setOverrideDeadline(seekBarInteger * 1000);

        boolean constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE) || mDeviceChargingSwitch.isChecked() || mDeviceIdleSwitch.isChecked() || seekBarSet;
        if(constraintSet){ // 제약조건이 하나라도 설정되었다면
            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
            Toast.makeText(this, "Job Scheduled, job will run when " + "the constraints are met.", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Please set at least one constraint", Toast.LENGTH_SHORT).show();

    }

    public void cancelJobs(View view) {
        if (mScheduler != null) {
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void setJobService(){

    }
}

/*
* 지정된 제약 조건이 충족되면 Executor 작업을 시작하는 JobService를 구현합니다.
Executor 작업은 5초 동안 휴면해야 합니다.
스레드가 잠자고 있는 동안 제약 조건이 충족되지 않으면 작업을 다시 예약하고 작업이 실패했음을 알리는 Toast 메시지를 표시합니다.
* */