package com.example.carlos.tdam_ejercicio225_josecarlosguerrarobles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Workers extends AppCompatActivity {

    private ArrayList<Worker> workerList = new ArrayList<Worker>();
    private RecyclerView rcvWorkers;
    private WorkerAdapter adapter;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fetchWorkersRecords();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View mView1 = inflater.inflate(R.layout.add_worker,null);

                final EditText input_nombre = (EditText) mView1.findViewById(R.id.edtNombre);
                final EditText input_actividad = (EditText) mView1.findViewById(R.id.edtActividad);
                final EditText input_celular = (EditText) mView1.findViewById(R.id.edtCelular);

                final Button btnSave = (Button) mView1.findViewById(R.id.btnGuardar);

                AlertDialog.Builder builder = new AlertDialog.Builder(Workers.this);
                builder.setView(mView1).setTitle("Agregar un nuevo TRABAJADOR")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String nombre = input_nombre.getText().toString();
                        String actividad = input_actividad.getText().toString();
                        String celular = input_celular.getText().toString();


                        if (nombre.equals("") && actividad.equals("") && celular.equals("") ){
                            Snackbar.make(view,"Espacios incompletos",Snackbar.LENGTH_SHORT).show();

                        }else {
                            Save(nombre,actividad,celular);
                            dialog.dismiss();
                            Snackbar.make(view,"Saving",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog = builder.create();
                dialog.show();
            }


        });
    }
    public void fetchWorkersRecords() {
        try{
            rcvWorkers= (RecyclerView) findViewById(R.id.rcvWorkers);
            adapter = new WorkerAdapter(this,workerList,this);

            LinearLayoutManager layoutManager = new LinearLayoutManager(Workers.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvWorkers.setLayoutManager(layoutManager);
            rcvWorkers.setAdapter(adapter);

            workerList.clear();

            BaseDatos dbFunctions = new BaseDatos(this);
            //SQLiteDatabase db = dbFunctions.getWritableDatabase();
            //db.execSQL("INSERT INTO trabajador VALUES(null,'Calors','pintor','3111667745')");

            ArrayList<Worker> data = dbFunctions.getAllWorkers();

            if (data.size()>0){
                for (int i = 0; i<data.size(); i++){

                    //ALmacenar temporalmente los datos en variables
                    int id = data.get(i).getIdTrabajador();
                    String nombre = data.get(i).getNombreTrabajador();
                    String actividad = data.get(i).getActividadTrabajador();
                    String celular = data.get(i).getCelularTrabajador();

                    //Crear un nuevo trabajador con los datos obtenidos de la DB
                    Worker worker = new Worker();

                    worker.setIdTrabajador(id);
                    worker.setNombreTrabajador(nombre);
                    worker.setActividadTrabajador(actividad);
                    worker.setCelularTrabajador(celular);

                    //Agregar trabajadores a la lista
                    workerList.add(worker);

                    //NOtificar cambios a la lista
                }adapter.notifyDataSetChanged();

            }else {
                Toast.makeText(Workers.this, "No Records found.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void Save(String nombre, String actividad, String celular) {


        BaseDatos dbfunctions = new BaseDatos(Workers.this);
        Worker worker = new Worker();

        worker.setNombreTrabajador(nombre);
        worker.setActividadTrabajador(actividad);
        worker.setCelularTrabajador(celular);

        dbfunctions.InsertWorker(worker);
        Toast.makeText(this, "Guardado...", Toast.LENGTH_SHORT).show();
        fetchWorkersRecords();
    }
}
