package com.developandsystem.intentweb;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SmsActivity extends AppCompatActivity implements View.OnClickListener {

    Button bSMSIntent, bSMSPending;
    EditText editNumero, editTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        instancias();
        acciones();
    }

    private void acciones() {
        bSMSPending.setOnClickListener(this);
        bSMSIntent.setOnClickListener(this);
    }

    private void instancias() {
        bSMSIntent = (Button) findViewById(R.id.buttonSMSIntent);
        bSMSPending = (Button) findViewById(R.id.buttonSMSPending);
        editNumero = (EditText) findViewById(R.id.editNumeroSMS);
        editTexto = (EditText) findViewById(R.id.editTextoSMS);
    }

    public void compruebaPermisos() {

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.buttonSMSIntent:
                int permissionCheck1 = ActivityCompat.checkSelfPermission(
                        this, android.Manifest.permission.SEND_SMS);
                if (permissionCheck1 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, 225);
                } else {
                    mandarSMSMetodo1();
                }

                break;
            case R.id.buttonSMSPending:

                int permissionCheck2 = ActivityCompat.checkSelfPermission(
                        this, android.Manifest.permission.SEND_SMS);
                if (permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, 225);
                } else {
                    mandarSMSMetodo2();
                }
                break;
        }
    }

    public void mandarSMSMetodo1() {
        // se instancia en intent diciendole que es de tipo accion y de tipo sms, donde se le pasa el numero al que tiene que enviar
        Intent i;
        i = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + editNumero.getText().toString()));
        //otra posibilidad es pasarle las cosas como extra parte a parte. primero se le pasa el numero al que se quiere llamar
        //i.putExtra("address", editNumero.getText().toString());
        // despues se le pasa el cuerpo del mensaje que se quiere enviar
        i.putExtra("sms_body", editTexto.getText().toString());
        startActivity(i);
    }

    public void mandarSMSMetodo2() {
        //parecido a la anterior pero utilizando las particularidades de un objeto propio de sms
        //Se utiliza un pendingintent y un sms manager
        PendingIntent pi = PendingIntent.getActivity(this,0,new Intent(this,SmsActivity.class),0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(editNumero.getText().toString(),null,editTexto.getText().toString(),pi,null);
    }

}
