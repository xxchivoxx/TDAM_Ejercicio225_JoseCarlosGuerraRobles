package com.example.carlos.tdam_ejercicio225_josecarlosguerrarobles;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 * Created by carlos on 11/03/18.
 */

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.MyViewHolder> {
    private Activity activity;
    private ArrayList<Worker> list;
    private AlertDialog dialog;
    private Workers cWorkers;
    private Detalle_Obra cDetalle_obra;
    private WorkerAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(WorkerAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public  WorkerAdapter(Activity activity, ArrayList<Worker> list,Workers workers){
        this.activity = activity;
        this.list = list;
        cWorkers = workers;
    }
    public  WorkerAdapter(Activity activity, ArrayList<Worker> list,Detalle_Obra detalle_obra){
        this.activity = activity;
        this.list = list;
        cDetalle_obra = detalle_obra;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context;
        context =parent.getContext();

        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        View itemView;
        if(cWorkers!=null){
            itemView = inflater.inflate(R.layout.worker,parent,false);
        }
        else{
            itemView = inflater.inflate(R.layout.worker_single_item,parent,false);
        }


        return new WorkerAdapter.MyViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Worker worker = list.get(position);

        holder.txtNombre.setText("Nombre: "+worker.getNombreTrabajador());
        holder.txtActividad.setText("Actividad: "+worker.getActividadTrabajador());
        holder.txtCelular.setText("Celular: "+worker.getCelularTrabajador());
        if(cWorkers!=null){
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open edition window
                final int id = worker.getIdTrabajador();


                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.edit_worker,null);

                final EditText input_nombre = (EditText) view1.findViewById(R.id.edtNombre);
                final EditText input_actividad = (EditText) view1.findViewById(R.id.edtActividad);
                final EditText input_celular = (EditText) view1.findViewById(R.id.edtCelular);

                final Button btnSave = (Button) view1.findViewById(R.id.btnGuardar);


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setView(view1).setTitle("Edit Records").setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog.dismiss();
                    }
                });

                final BaseDatos _dbFunctions = new BaseDatos(activity);
                final Worker _worker = _dbFunctions.getSingleWorker(id);
                input_nombre.setText(_worker.getNombreTrabajador());
                input_actividad.setText(_worker.getActividadTrabajador());
                input_celular.setText(_worker.getCelularTrabajador());


                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String nombre = input_nombre.getText().toString();
                        String actividad = input_actividad.getText().toString();
                        String celular = input_celular.getText().toString();


                        _worker.setNombreTrabajador(nombre);
                        _worker.setActividadTrabajador(actividad);
                        _worker.setCelularTrabajador(celular);


                        _dbFunctions.UpdateWorker(_worker);

                        Toast.makeText(activity, nombre + " updated.", Toast.LENGTH_SHORT).show();
                        ((Workers)activity).fetchWorkersRecords();
                        dialog.dismiss();
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
                    final int id = worker.getIdTrabajador();
                    _dbFunctions.DeleteWorker(id);

                    Toast.makeText(activity,  " Eliminado...", Toast.LENGTH_SHORT).show();
                    ((Workers)activity).fetchWorkersRecords();
                    //dialog.dismiss();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtActividad, txtCelular;
        TextView btnEdit, btnDelete;

        public MyViewHolder(View itemView, final WorkerAdapter.OnItemClickListener listener) {
            super(itemView);

            txtNombre = this.itemView.findViewById(R.id.txtNombre);
            txtActividad = this.itemView.findViewById(R.id.txtActividad);
            txtCelular = this.itemView.findViewById(R.id.txtCelular);
            if(cWorkers!=null){
                btnEdit = this.itemView.findViewById(R.id.btnEdit);
                btnDelete = this.itemView.findViewById(R.id.btnDelete);
            }
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
