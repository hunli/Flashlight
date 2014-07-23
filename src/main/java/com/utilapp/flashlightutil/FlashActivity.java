package com.utilapp.flashlightutil;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/** A simple flashlight app to turn **/
public class FlashActivity extends Activity {
    private final static String TAG =
            FlashActivity.class.getSimpleName();

    private Camera cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Creates a layout where there exists only a  **/
        /** button toggle the light                     **/
        setContentView(R.layout.activity_flash);
    }

    @Override
    public void onResume(){
        super.onResume();
        // Creates a camera object to take control of the
        // back camera
        cam = Camera.open();
    }

    @Override
    public void onStop(){
        super.onStop();
        // Turns off the flashlight if on already
        // and then releases camera object
        Parameters p = cam.getParameters();
        flashlightOff(p);
        cam.release();
    }

    /** Creates a menu for future use if needed **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.flash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Dictates the action when toggle button is clicked **/
    public void onClicked(View v){
        Log.d(TAG, "onClicked");

        switch(v.getId())
        {
            case R.id.toggle:
                //Checks to see if the camera flash exists
                if(!getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                    Toast.makeText(this, getResources().getString(R.string.no_flash), Toast.LENGTH_SHORT);
                    return;
                }

                //Turns on the flash if it's off, else, turns on
                Parameters p = cam.getParameters();
                if(p.getFlashMode().equals(Parameters.FLASH_MODE_TORCH))
                    flashlightOff(p);
                else
                    flashlightOn(p);

                break;
            default:
                break;
        }
    }

    public void flashlightOff(Parameters p){
        //Sets the flashlight off parameter
        p.setFlashMode(Parameters.FLASH_MODE_OFF);
        cam.setParameters(p);
        cam.stopPreview();
    }

    public void flashlightOn(Parameters p){
        //Sets the flashlight on paramater
        p.setFlashMode(Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();
    }
}
