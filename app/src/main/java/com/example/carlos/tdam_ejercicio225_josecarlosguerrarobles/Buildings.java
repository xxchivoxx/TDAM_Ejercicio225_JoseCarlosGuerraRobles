package com.example.carlos.tdam_ejercicio225_josecarlosguerrarobles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class Buildings extends AppCompatActivity {
    private ArrayList<Build> buildList = new ArrayList<Build>();
    private FloatingActionButton fap;
    private Dialog dialog;
    private RecyclerView rcvBuildings;
    private BuildingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
    try {
        rcvBuildings = (RecyclerView) findViewById(R.id.rcvBuildings);
        adapter = new BuildingAdapter(this,buildList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Buildings.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvBuildings.setLayoutManager(layoutManager);
        rcvBuildings.setAdapter(adapter);
        fetchBuildingsRecords();

        adapter.setOnItemClickListener(new BuildingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id, nombre, direccion, celular,mail, descripcion, monto, estado;
                id = ""+buildList.get(position).getIdCliente();
                nombre = ""+buildList.get(position).getNombreCliente();
                direccion = ""+buildList.get(position).getDireccionCliente();
                celular = ""+buildList.get(position).getCelularCliente();
                mail = ""+buildList.get(position).getMailCliente();
                descripcion = ""+buildList.get(position).getDescripcionObraCliente();
                monto = ""+buildList.get(position).getMontoCliente();
                estado = ""+buildList.get(position).getEstado();

                Intent goDetalleObra = new Intent(Buildings.this,Detalle_Obra.class);

                goDetalleObra.putExtra("id",id);
                goDetalleObra.putExtra("nombre",nombre);
                goDetalleObra.putExtra("direccion",direccion);
                goDetalleObra.putExtra("celular",celular);
                goDetalleObra.putExtra("mail",mail);
                goDetalleObra.putExtra("descripcion",descripcion);
                goDetalleObra.putExtra("monto",monto);
                goDetalleObra.putExtra("estado",estado);

                startActivity(goDetalleObra);
                Toast.makeText(Buildings.this, ""+buildList.get(position).getNombreCliente(), Toast.LENGTH_SHORT).show();
            }
        });
        fap = findViewById(R.id.fab);
    }catch (Exception e){
        Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

    }

        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View mView1 = inflater.inflate(R.layout.add_building,null);

                final Button btnGuardar;
                final EditText edtNombre, edtDireccion, edtCelular, edtMail,edtDescipcionObra, edtMonto;

                edtNombre = mView1.findViewById(R.id.edtNombre);
                edtDireccion = mView1.findViewById(R.id.edtDireccion);
                edtCelular = mView1.findViewById(R.id.edtCelular);
                edtMail = mView1.findViewById(R.id.edtMail);
                edtDescipcionObra = mView1.findViewById(R.id.edtDescripcionObra);
                edtMonto = mView1.findViewById(R.id.edtMonto);

                btnGuardar = mView1.findViewById(R.id.btnGuardar);

                AlertDialog.Builder builder = new AlertDialog.Builder(Buildings.this);
                builder.setView(mView1).setTitle("Agregar un nuevo Cliente")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Se normalizan datos y se mandan a la funcion de guardar
                        String nombre, direccion, celular, mail, descripcio_obra;
                        String monto;
                        int estado = 0;

                        nombre = edtNombre.getText().toString();
                        direccion = edtDireccion.getText().toString();
                        celular = edtCelular.getText().toString();
                        mail = edtMail.getText().toString();
                        descripcio_obra = edtDescipcionObra.getText().toString();
                        monto = edtMonto.getText().toString();

                        if (nombre.equals("") && direccion.equals("") && celular.equals("") && mail.equals("") && descripcio_obra.equals("") && monto.equals("") ){
                            Snackbar.make(view,"Espacios incompletos",Snackbar.LENGTH_SHORT).show();

                        }else {
                            Save(nombre,direccion,celular,mail,descripcio_obra,monto,estado);
                            Snackbar.make(view,"Saving",Snackbar.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void Save(String name, String address, String cellphone, String mail, String description, String mount, int state){
        try {
            BaseDatos dbfunctions = new BaseDatos(Buildings.this);
            Build build = new Build();

            build.setNombreCliente(name);
            build.setDireccionCliente(address);
            build.setCelularCliente(cellphone);
            build.setMailCliente(mail);
            build.setDescripcionObraCliente(description);
            build.setMontoCliente(mount);
            build.setEstado(state);

            dbfunctions.InsertBuilding(build);
            Toast.makeText(this, "Guardado...", Toast.LENGTH_SHORT).show();
            fetchBuildingsRecords();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void fetchBuildingsRecords() {

        try{


            buildList.clear();

            BaseDatos dbFunctions = new BaseDatos(this);

            ArrayList<Build> data = dbFunctions.getAllBuildings();

            if (data.size()>0){
                for (int i = 0; i<data.size(); i++){

                    //ALmacenar temporalmente los datos en variables
                    int id = data.get(i).getIdCliente();
                    String nombre = data.get(i).getNombreCliente();
                    String direccion = data.get(i).getDireccionCliente();
                    String celular = data.get(i).getCelularCliente();
                    String mail = data.get(i).getMailCliente();
                    String descripcion =  data.get(i).getDescripcionObraCliente();
                    String monto = data.get(i).getMontoCliente();
                    int estado = data.get(i).getEstado();

                    //Crear un nuevo trabajador con los datos obtenidos de la DB
                    Build build = new Build();

                    build.setIdCliente(id);
                    build.setNombreCliente(nombre);
                    build.setDireccionCliente(direccion);
                    build.setCelularCliente(celular);
                    build.setMailCliente(mail);
                    build.setDescripcionObraCliente(descripcion);
                    build.setMontoCliente(monto);
                    build.setEstado(estado);


                    //Agregar trabajadores a la lista
                    buildList.add(build);

                    //NOtificar cambios a la lista
                }adapter.notifyDataSetChanged();

            }else {
                Toast.makeText(Buildings.this, "No Records found.", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
