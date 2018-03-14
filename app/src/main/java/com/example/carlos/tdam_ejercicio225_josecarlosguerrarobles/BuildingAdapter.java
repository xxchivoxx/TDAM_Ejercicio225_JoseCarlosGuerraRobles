package com.example.carlos.tdam_ejercicio225_josecarlosguerrarobles;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by carlos on 12/03/18.
 */

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<Build> list;
    AlertDialog dialog;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public BuildingAdapter(Activity activity, ArrayList<Build> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context;
        context = parent.getContext();

        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.build,parent,false);

        return new BuildingAdapter.MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Build build = list.get(position);

        holder.txtNombre.setText("Nombre: "+build.getNombreCliente());
        holder.txtDireccion.setText("Direccion: "+build.getDireccionCliente());
        holder.txtCelular.setText("Celular: "+build.getCelularCliente());
        holder.txtMail.setText("Mail: "+build.getMailCliente());
        holder.txtDescipcionObra.setText("Descripcion de la obra: "+build.getDescripcionObraCliente());
        holder.txtMonto.setText("Monto: "+build.getMontoCliente());
        if(build.getEstado()==1){
            holder.txtEstado.setText("Finalizada");
            holder.txtEstado.setTextColor(Color.GREEN);
        }
        else {
            holder.txtEstado.setText("Pendiente");
            holder.txtEstado.setTextColor(Color.RED);
        }


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open edition window
                final int id = build.getIdCliente();


                LayoutInflater inflater = activity.getLayoutInflater();
                View mView1 = inflater.inflate(R.layout.add_building,null);

                final Button btnGuardar;
                final EditText edtNombre, edtDireccion, edtCelular, edtMail,edtDescipcionObra, edtMonto;
                final BaseDatos dbfunction = new BaseDatos(activity);
                Build _build = new Build();
                _build = dbfunction.getSingleBuilding(id);
                edtNombre = mView1.findViewById(R.id.edtNombre);
                edtDireccion = mView1.findViewById(R.id.edtDireccion);
                edtCelular = mView1.findViewById(R.id.edtCelular);
                edtMail = mView1.findViewById(R.id.edtMail);
                edtDescipcionObra = mView1.findViewById(R.id.edtDescripcionObra);
                edtMonto = mView1.findViewById(R.id.edtMonto);

                btnGuardar = mView1.findViewById(R.id.btnGuardar);


                edtNombre.setText(_build.getNombreCliente());
                edtDireccion.setText(_build.getDireccionCliente());
                edtCelular.setText(_build.getCelularCliente());
                edtMail.setText(_build.getMailCliente());
                edtDescipcionObra.setText(_build.getDescripcionObraCliente());
                edtMonto.setText(_build.getMontoCliente());

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setView(mView1).setTitle("Editar Cliente")
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
                            //Save(nombre,direccion,celular,mail,descripcio_obra,monto,estado);
                            Build b = new Build();
                            b.setIdCliente(id);
                            b.setNombreCliente(nombre);
                            b.setDireccionCliente(direccion);
                            b.setCelularCliente(celular);
                            b.setMailCliente(mail);
                            b.setDescripcionObraCliente(descripcio_obra);
                            b.setMontoCliente(monto);
                            dbfunction.UpdateBuilding(b);
                            Snackbar.make(view,"Saving",Snackbar.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Delete worker
                final BaseDatos _dbFunctions = new BaseDatos(activity);
                final int id = build.getIdCliente();
                _dbFunctions.DeleteBuilding(id);

                Toast.makeText(activity,  " Eliminado...", Toast.LENGTH_SHORT).show();
                ((Buildings)activity).fetchBuildingsRecords();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre,txtDireccion, txtCelular, txtMail,txtDescipcionObra, txtMonto,txtEstado;
        TextView btnEdit, btnDelete;
        TextView ejemplo;
        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtNombre = this.itemView.findViewById(R.id.txtNombre);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtCelular = itemView.findViewById(R.id.txtCelular);
            txtMail = itemView.findViewById(R.id.txtMail);
            txtDescipcionObra = itemView.findViewById(R.id.txtDescripcionObra);
            txtMonto = itemView.findViewById(R.id.txtMonto);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
