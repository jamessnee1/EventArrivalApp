package jamessnee.com.trainingtest;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String wifiToCheck = "A2K_TECHNOLOGIES";
    private WifiManager wifiMgr;


    //create broadcast receiver
    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction() == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
                List<ScanResult> mScanResults = wifiMgr.getScanResults();

                boolean quit = false;
                boolean foundNetwork = false;
                String foundNetworkName = "";

                //go thru wifi list
                for(ScanResult scan : mScanResults){

                    foundNetworkName = scan.SSID;

                    //if A2K network is not found, show dialog
                    if(foundNetworkName.equals(wifiToCheck)){

                        Toast.makeText(getApplicationContext(), "SSID is " + foundNetworkName + ", wifiToCheck is " +
                                wifiToCheck, Toast.LENGTH_SHORT).show();

                        foundNetwork = true;
                        createDialog("Wifi found", "Wifi was found! SSID is " + foundNetworkName, quit);

                    }

                }
                    if(!foundNetwork) {

                        quit = true;
                        createDialog("Wifi Not Found", "Error: Wifi was not found. App will now close.", quit);


                    }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //search for wifi here
        wifiMgr = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiMgr.startScan();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void createDialog(String title, String message, final boolean quit){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();

                if (quit) {
                    finish();
                }

            }
        });


        builder.show();


    }
}
