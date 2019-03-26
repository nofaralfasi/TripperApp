package com.tripper.tripper.trip.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tripper.tripper.R;
import com.tripper.tripper.core.Trip;
import com.tripper.tripper.dialogs.ChangesNotSavedDialogFragment;
import com.tripper.tripper.trip.fragment.TripUpdateFragment;
import com.tripper.tripper.trip.fragment.TripsListFragment;
import com.tripper.tripper.trip.interfaces.OnGetCurrentTrip;
import com.tripper.tripper.trip.interfaces.OnSetCurrentTrip;
import com.tripper.tripper.utils.DatabaseUtils;
import com.tripper.tripper.utils.NotificationUtils;
import com.tripper.tripper.utils.SharedPreferencesUtils;
import com.tripper.tripper.utils.StartActivityUtils;

public class TripActivity extends AppCompatActivity implements OnSetCurrentTrip, OnGetCurrentTrip,
        ChangesNotSavedDialogFragment.OnHandleDialogResult {

    public static final String TAG = TripActivity.class.getSimpleName();

    private Trip currentTrip;
    private String saveTrip = "saveTrip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_main);

        if(savedInstanceState != null){
            currentTrip = savedInstanceState.getParcelable(saveTrip);
        }

        Toolbar myToolbar = findViewById(R.id.MainToolBar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Trip lastTrip = DatabaseUtils.getLastTrip(this);
        if(lastTrip != null && savedInstanceState == null){
            currentTrip = lastTrip;
            if(SharedPreferencesUtils.getIsNotificationsWindowOpen(this)){
                NotificationUtils.initNotification(this, lastTrip.getTitle());
            }
            StartActivityUtils.startLandmarkMainActivity(this, lastTrip);
        }

        TripsListFragment tripsListFragment = new TripsListFragment();
        tripsListFragment.setArguments(getIntent().getExtras());

        if (getFragmentManager().findFragmentById(R.id.trip_main_fragment_container) == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.trip_main_fragment_container, tripsListFragment, TripsListFragment.TAG)
                    .commit();
        }


    }

    @Override
    public void onSetCurrentTrip(Trip trip) {
        currentTrip = trip;
    }

    @Override
    public Trip onGetCurrentTrip() {
        return currentTrip;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(saveTrip, currentTrip);
    }

    @Override
    public void onBackPressed() {
        TripUpdateFragment myFragment = (TripUpdateFragment)getFragmentManager().findFragmentByTag(TripUpdateFragment.TAG);
        if (myFragment != null && myFragment.isVisible()) {
            ChangesNotSavedDialogFragment notSavedDialog = new ChangesNotSavedDialogFragment();
            notSavedDialog.setTargetFragment(myFragment, ChangesNotSavedDialogFragment.NOT_SAVED_DIALOG);
            notSavedDialog.show(getFragmentManager(), "Not_saved_dialog");
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public void onHandleDialogResult(int whichButton) {
        ChangesNotSavedDialogFragment.DialogOptions whichOptionEnum = ChangesNotSavedDialogFragment.DialogOptions.values()[whichButton];
        switch (whichOptionEnum){
            case YES:
                super.onBackPressed();
                break;
            case NO:
                break;
        }
    }
}
