package com.example.my.signinapp;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.UUID;

public class control_room extends AppCompatActivity {


    Button  btnDis, s_one_on, s_one_off, s_two_on, s_two_off , s_three_on, s_three_off, s_four_on, s_four_off, s_five_on, s_five_off, s_six_on, s_six_off;
    SeekBar brightness , fan_control ;

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_room);

        Intent newint = getIntent();
        address = newint.getStringExtra(activity1.EXTRA_ADDRESS); //receive the address of the bluetooth device

        new ConnectBT().execute(); //Call the class to connect


        s_one_on = (Button)findViewById(R.id.button_on);
        s_one_off = (Button)findViewById(R.id.button_off);
        s_two_on = (Button)findViewById(R.id.button2);
        s_two_off = (Button)findViewById(R.id.button3);
        s_three_on= (Button)findViewById(R.id.button4);
        s_three_off = (Button)findViewById(R.id.button5);
        s_four_on = (Button)findViewById(R.id.button6);
        s_four_off = (Button)findViewById(R.id.button7);
        s_five_on = (Button)findViewById(R.id.button8);
        s_five_off = (Button)findViewById(R.id.button9);
        s_six_on = (Button)findViewById(R.id.button10);
        s_six_off = (Button)findViewById(R.id.button11);

        fan_control = findViewById(R.id.seekBar_two);
        fan_control.setMin(0);
        fan_control.setMax(255);



        fan_control.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser && btSocket!= null){
                    try{

                       btSocket.getOutputStream().write(String.valueOf(progress + 300).getBytes());
                    }catch(IOException e){

                    }

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        brightness = findViewById(R.id.seekBar);
        brightness.setMin(0);
        brightness.setMax(255);
        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && btSocket != null)
                {
                    try
                    {

                        btSocket.getOutputStream().write(String.valueOf(progress).getBytes());
                    }
                    catch(IOException e)
                    {e.printStackTrace();
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


       //Attaching on click listener for the button to disconnect the bluetooth
        btnDis = (Button)findViewById(R.id.button12);
        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });

        //Attaching on click listeners for the various on buttons to control the voltage
        s_one_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnSectionOne();
            }
        });
        s_two_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnSectionTwo();
            }
        });
        s_three_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnSectionThree();
            }
        });
        s_four_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnSectionFour();
            }
        });
        s_five_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnSectionFive();
            }
        });
        s_six_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnSectionSix();
            }
        });

        // Attaching on click listeners for the off lights
        s_one_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffSectionOne();
            }
        });
        s_two_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffSectionTwo();
            }
        });
        s_three_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffSectionThree();
            }
        });
        s_four_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffSectionFour();
            }
        });
        s_five_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffSectionFive();
            }
        });
        s_six_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffSectionSix();
            }
        });





    }

    //Creating the disconnect method
    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    //Creating the Turn on Various section Lights
    public void turnOnSectionOne(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S1ON".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }

    }

    private void turnOnSectionTwo(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S2ON".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnSectionThree(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S3ON".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }


    private void turnOnSectionFour(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S4ON".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }


    private void turnOnSectionFive(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S5ON".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }



    private void turnOnSectionSix(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S6ON".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }



    //Declaring the methods for the off section

    private void turnOffSectionOne(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S1OFF".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }

    }

    private void turnOffSectionTwo(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S2OFF".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOffSectionThree(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S3OFF".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }

    }

    private void turnOffSectionFour(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S4OFF".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }

    }

    private void turnOffSectionFive(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S5OFF".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOffSectionSix(){
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("S6OFF".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }

    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(control_room.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a Bluetooth Module? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

  /*      Runnable r = new Runnable() {
            @Override
            public void run() {
                if(btSocket == null){
                    try{
                        wait(5000);

                    }catch(Exception e){

                    }
                }
            }
        };

        Thread threadObject = new Thread(r);
        threadObject.start();
        */
    }
}


