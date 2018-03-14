package com.example.carlos.tdam_ejercicio225_josecarlosguerrarobles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Detalle_Obra extends AppCompatActivity {
    private TextView txtNombre, txtDireccion, txtCelular, txtMail, txtDescripcion, txtMonto, txtEstado;
    String id, nombre, direccion, celular, mail, descripcion, monto, estado;
    private ArrayList<Worker> workerList = new ArrayList<Worker>();
    private ArrayList<Worker> workerList2 = new ArrayList<Worker>();
    private RecyclerView rcvWorkers, rcvDetalleTrabajadores;
    private WorkerAdapter adapter,adapterDetalle;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__obra);

        rcvWorkers= (RecyclerView) findViewById(R.id.rcvTodosTrabajadores);
        adapter = new WorkerAdapter(this,workerList,this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(Detalle_Obra.this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rcvWorkers.setLayoutManager(layoutManager2);
        rcvWorkers.setAdapter(adapter);

        rcvDetalleTrabajadores= (RecyclerView) findViewById(R.id.rcvDetalleTrabajadores);
        adapterDetalle = new WorkerAdapter(this,workerList2,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Detalle_Obra.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvDetalleTrabajadores.setLayoutManager(layoutManager);
        rcvDetalleTrabajadores.setAdapter(adapterDetalle);


        getSingleDataBuild();
        initAllComponents();
        fetchWorkersRecords();
        fetchDetailsRecords();
        adapterDetalle.setOnItemClickListener(new WorkerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                //SE INFLA EL EDIT
                Toast.makeText(Detalle_Obra.this, "ssssssssss", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = getLayoutInflater();
                View mView1 = inflater.inflate(R.layout.detalle_edit,null);
                final BaseDatos dbFunctions = new BaseDatos(Detalle_Obra.this);
                ArrayList<Detalle> detalles = dbFunctions.getSingleDetail(Integer.parseInt(id),workerList2.get(position).getIdTrabajador());
                final Button btnGuardar, btnEliminar;
                final TextView _txtNombre, _txtActividad;
                final EditText edtFechaInicio, edtFechaFin, edtCantidad, edtPago;

                _txtNombre = mView1.findViewById(R.id.txtNombre);
                _txtActividad = mView1.findViewById(R.id.txtActividad);

                edtFechaInicio = mView1.findViewById(R.id.edtFechaInicio);
                edtFechaFin = mView1.findViewById(R.id.edtFechaFin);
                edtCantidad = mView1.findViewById(R.id.edtCantidad);
                edtPago = mView1.findViewById(R.id.edtPago);
                _txtNombre.setText(workerList2.get(position).getNombreTrabajador());
                _txtActividad.setText(workerList2.get(position).getActividadTrabajador());
                edtFechaInicio.setText(detalles.get(0).getFechaInicio());
                edtFechaFin.setText(detalles.get(0).getFechaFin());
                edtCantidad.setText(detalles.get(0).getCantidad());
                edtPago.setText(detalles.get(0).getPaga());

                btnGuardar = mView1.findViewById(R.id.btnGuardar);
                btnEliminar = mView1.findViewById(R.id.btnEliminar);

                AlertDialog.Builder builder = new AlertDialog.Builder(Detalle_Obra.this);
                builder.setView(mView1).setTitle("Agregar un trabajador")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });
                final int idCliente = Integer.parseInt(id);
                final int idTrabajador = workerList2.get(position).getIdTrabajador();
                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Se normalizan datos y se mandan a la funcion de guardar

                        String fechaIni, fechFin, cantidad, pago;
                        fechaIni = edtFechaInicio.getText().toString();
                        fechFin = edtFechaFin.getText().toString();
                        cantidad = edtCantidad.getText().toString();
                        pago = edtPago.getText().toString();


                        if (fechaIni.equals("") || fechFin.equals("") || cantidad.equals("") || pago.equals("")){
                            Snackbar.make(view,"Espacios incompletos",Snackbar.LENGTH_SHORT).show();

                        }else {
                            Update(idCliente,idTrabajador,fechaIni,fechFin,cantidad,pago);
                            Snackbar.make(view,"Updating",Snackbar.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      dbFunctions.DeletDetail(idCliente,idTrabajador);
                        Snackbar.make(v,"Deletting",Snackbar.LENGTH_SHORT).show();
                        fetchDetailsRecords();
                        adapterDetalle.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
        adapter.setOnItemClickListener(new WorkerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                //SE INFLA EL EDIT
                LayoutInflater inflater = getLayoutInflater();
                View mView1 = inflater.inflate(R.layout.detalle,null);

                final Button btnGuardar;
                final TextView _txtNombre, _txtActividad;
                final EditText edtFechaInicio, edtFechaFin, edtCantidad, edtPago;

                _txtNombre = mView1.findViewById(R.id.txtNombre);
                _txtActividad = mView1.findViewById(R.id.txtActividad);

                edtFechaInicio = mView1.findViewById(R.id.edtFechaInicio);
                edtFechaFin = mView1.findViewById(R.id.edtFechaFin);
                edtCantidad = mView1.findViewById(R.id.edtCantidad);
                edtPago = mView1.findViewById(R.id.edtPago);
                _txtNombre.setText(workerList.get(position).getNombreTrabajador());
                _txtActividad.setText(workerList.get(position).getActividadTrabajador());

                btnGuardar = mView1.findViewById(R.id.btnGuardar);

                AlertDialog.Builder builder = new AlertDialog.Builder(Detalle_Obra.this);
                builder.setView(mView1).setTitle("Agregar un trabajador")
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

                        String fechaIni, fechFin, cantidad, pago;
                        fechaIni = edtFechaInicio.getText().toString();
                        fechFin = edtFechaFin.getText().toString();
                        cantidad = edtCantidad.getText().toString();
                        pago = edtPago.getText().toString();
                        int idCliente = Integer.parseInt(id);
                        int idTrabajador = workerList.get(position).getIdTrabajador();

                        if (fechaIni.equals("") || fechFin.equals("") || cantidad.equals("") || pago.equals("")){
                            Snackbar.make(view,"Espacios incompletos",Snackbar.LENGTH_SHORT).show();

                        }else {
                            Save(idCliente,idTrabajador,fechaIni,fechFin,cantidad,pago);
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

    private void Update(int idCl, int idTr, String fechIni, String fechFin, String cant, String pago) {
        try {
            BaseDatos dbfunctions = new BaseDatos(Detalle_Obra.this);
            Detalle detalle = new Detalle();
            detalle.setIdTrabajador(idTr);
            detalle.setIdCliente(idCl);
            detalle.setFechaFin(fechFin);
            detalle.setFechaInicio(fechIni);
            detalle.setPaga(pago);
            detalle.setCantidad(cant);
            dbfunctions.UpdateDetail(detalle);
            Toast.makeText(this, "Actualizando...", Toast.LENGTH_SHORT).show();
            fetchDetailsRecords();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void Save(int id1, int id2, String fechaIni, String fechaFin, String cantidad, String pago){
        try {
            BaseDatos dbfunctions = new BaseDatos(Detalle_Obra.this);
            dbfunctions.InsertDetalle(id1,id2,fechaIni,fechaFin,cantidad,pago);
            Toast.makeText(this, "Guardado...", Toast.LENGTH_SHORT).show();
            fetchDetailsRecords();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchWorkersRecords() {
        try{


            workerList.clear();

            BaseDatos dbFunctions = new BaseDatos(this);
            //SQLiteDatabase db = dbFunctions.getWritableDatabase();
            //db.execSQL("INSERT INTO trabajador VALUES(null,'Calors','pintor','3111667745')");
            ArrayList<Worker> data;
            data = dbFunctions.getAllWorkers();

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
                Toast.makeText(Detalle_Obra.this, "No Records found.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void fetchDetailsRecords() {
        try{


            workerList2.clear();

            BaseDatos dbFunctions = new BaseDatos(this);
            //SQLiteDatabase db = dbFunctions.getWritableDatabase();
            //db.execSQL("INSERT INTO trabajador VALUES(null,'Calors','pintor','3111667745')");
            ArrayList<Worker> data;
            data = dbFunctions.getDetaillWorkers(Integer.parseInt(id));


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
                    workerList2.add(worker);

                    //NOtificar cambios a la lista
                }adapterDetalle.notifyDataSetChanged();

            }else {
                Toast.makeText(Detalle_Obra.this, "No Records found.", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void initAllComponents() {
        txtNombre = findViewById(R.id.txtNombre);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtCelular = findViewById(R.id.txtCelular);
        txtMail = findViewById(R.id.txtMail);
        txtDescripcion = findViewById(R.id.txtDescipcion);
        txtMonto = findViewById(R.id.txtMonto);
        txtEstado = findViewById(R.id.txtEstado);

        txtNombre.setText("Nombre: "+nombre);
        txtDireccion.setText("Direccion: "+direccion);
        txtCelular.setText("Celular: "+celular);
        txtMail.setText("Mail: "+mail);
        txtDescripcion.setText("Descripcion: "+descripcion);
        txtMonto.setText("Monto: $"+monto);
        if(estado.equals("0")){
            txtEstado.setText("Pendiente");
            txtEstado.setTextColor(Color.RED);
        }
        else{
            txtEstado.setText("Finalizada");
            txtEstado.setTextColor(Color.GREEN);
        }


    }

    private void getSingleDataBuild(){
        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        nombre = intent.getStringExtra("nombre");
        direccion = intent.getStringExtra("direccion");
        celular = intent.getStringExtra("celular");
        mail = intent.getStringExtra("mail");
        descripcion = intent.getStringExtra("descripcion");
        monto = intent.getStringExtra("monto");
        estado = intent.getStringExtra("estado");
    }

}
