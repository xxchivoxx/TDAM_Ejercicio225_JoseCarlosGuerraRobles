package com.example.carlos.tdam_ejercicio225_josecarlosguerrarobles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by carlos on 10/03/18.
 */

public class BaseDatos extends SQLiteOpenHelper{
    private static final String DB_NAME = "Constructora.db";
    private static final int DB_VERSION = 1;
    private Context con;
    String sql="";
    public BaseDatos(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE cliente( " +
                    "idCliente INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombreCliente TEXT," +
                    "direccionCliente TEXT," +
                    "celularCliente TEXT," +
                    "mailCliente TEXT," +
                    "descripcionObraCliente TEXT," +
                    "montoCliente TEXT," +
                    "estadoCliente INTEGER)");
            db.execSQL("CREATE TABLE trabajador(" +
                    "idTrabajador INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombreTrabajador TEXT," +
                    "actividadTrabajador TEXT," +
                    "celularTrabajador TEXT)");
            db.execSQL("CREATE TABLE detalle_cliente_trabajador(" +
                    "idCliente INTEGER," +
                    "idTrabajador INTEGER," +
                    "fechaInicioTrabajador TEXT," +
                    "fechaFinTrabajador TEXT,"+
                    "cantidadObraTrabajador TEXT," +
                    "pagoEstimadoTrabajador TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists trabajador");
        db.execSQL("drop table if exists cliente");
        db.execSQL("drop table if exists detalle_cliente_trabajador");
    }

    public ArrayList<Build> getAllBuildings(){

        ArrayList<Build> listBuildings = new ArrayList<Build>();
        SQLiteDatabase database = getReadableDatabase();

        String sql = "SELECT * FROM cliente order by nombreCliente asc";

        Cursor cursor = database.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                Build build = new Build();
                build.setIdCliente(Integer.parseInt(cursor.getString(0)));
                build.setNombreCliente(cursor.getString(1));
                build.setDireccionCliente(cursor.getString(2));
                build.setCelularCliente(cursor.getString(3));
                build.setMailCliente(cursor.getString(4));
                build.setDescripcionObraCliente(cursor.getString(5));
                build.setMontoCliente(cursor.getString(6));
                build.setEstado(Integer.parseInt(cursor.getString(7)));

                listBuildings.add(build);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return listBuildings;
    }

    public ArrayList<Worker> getAllWorkers(){

        ArrayList<Worker> workersList = new ArrayList<Worker>();
        SQLiteDatabase database = getReadableDatabase();

        String sql = "SELECT * FROM trabajador order by nombreTrabajador asc";

        Cursor cursor = database.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                Worker worker = new Worker();
                worker.setIdTrabajador(Integer.parseInt(cursor.getString(0)));
                worker.setNombreTrabajador(cursor.getString(1));
                worker.setActividadTrabajador(cursor.getString(2));
                worker.setCelularTrabajador(cursor.getString(3));

                workersList.add(worker);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return workersList;
    }

    public ArrayList<Detalle> getSingleDetail(int idCliente, int idTrabajador){

        ArrayList<Detalle> detalleList = new ArrayList<Detalle>();
        SQLiteDatabase database = getReadableDatabase();

        String sql = "SELECT * FROM detalle_cliente_trabajador WHERE idCliente="+idCliente+" and idTrabajador="+idTrabajador;

        Cursor cursor = database.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                Detalle detalle= new Detalle();
                detalle.setIdTrabajador(cursor.getInt(1));
                detalle.setIdCliente(cursor.getInt(0));
                detalle.setFechaInicio(cursor.getString(2));
                detalle.setFechaFin(cursor.getString(3));
                detalle.setCantidad(cursor.getString(4));
                detalle.setPaga(cursor.getString(5));

                detalleList.add(detalle);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return detalleList;
    }

    public ArrayList<Worker> getDetaillWorkers(int idCliente){

        ArrayList<Worker> workersList = new ArrayList<Worker>();
        SQLiteDatabase database = getReadableDatabase();

        String sql = "SELECT t.idTrabajador, t.nombreTrabajador, t.actividadTrabajador, t.celularTrabajador " +
                "FROM trabajador as t " +
                "inner join detalle_cliente_trabajador as d on d.idTrabajador=t.idTrabajador " +
                "where d.idCliente="+idCliente;

        Cursor cursor = database.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                Worker worker = new Worker();
                worker.setIdTrabajador(Integer.parseInt(cursor.getString(0)));
                worker.setNombreTrabajador(cursor.getString(1));
                worker.setActividadTrabajador(cursor.getString(2));
                worker.setCelularTrabajador(cursor.getString(3));

                workersList.add(worker);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return workersList;
    }

    public void InsertWorker(Worker worker){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO trabajador VALUES(null," +
                "'"+worker.getNombreTrabajador()+"'," +
                "'"+worker.getActividadTrabajador()+"'" +
                ",'"+worker.getCelularTrabajador()+"')");
    }

    public void InsertBuilding(Build build){
        try {
            SQLiteDatabase db = getWritableDatabase();

            sql="INSERT INTO cliente VALUES(null," +
                    "'"+build.getNombreCliente()+"'," +
                    "'"+build.getDireccionCliente()+"'," +
                    "'"+build.getCelularCliente()+"'," +
                    "'"+build.getMailCliente()+"'," +
                    "'"+build.getDescripcionObraCliente()+"'," +
                    "'"+build.getMontoCliente()+"'," +
                    ""+build.getEstado()+")";
            db.execSQL(sql);
        }catch (Exception e){
            Toast.makeText(con, e.getMessage().toString()+""+sql, Toast.LENGTH_LONG).show();
        }

    }

    public void InsertDetalle(int id1, int id2, String fe1, String fe2, String cant, String pag){
        try {
            SQLiteDatabase db = getWritableDatabase();

            sql="INSERT INTO detalle_cliente_trabajador VALUES(" +
                    ""+id1+"," +
                    ""+id2+"," +
                    "'"+fe1+"'," +
                    "'"+fe2+"'," +
                    "'"+cant+"'," +
                    "'"+pag+"')";
            db.execSQL(sql);
        }catch (Exception e){
            Toast.makeText(con, e.getMessage().toString()+""+sql, Toast.LENGTH_LONG).show();
        }
    }

    public Worker getSingleWorker(int id){

        SQLiteDatabase db = getReadableDatabase();

        String sql = "SELECT * FROM trabajador WHERE idTrabajador="+id;
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor != null)
            cursor.moveToNext();

        Worker worker = new Worker();
        worker.setIdTrabajador(cursor.getShort(0));
        worker.setNombreTrabajador(cursor.getString(1));
        worker.setActividadTrabajador(cursor.getString(2));
        worker.setCelularTrabajador(cursor.getString(3));

        cursor.close();
        db.close();
        return worker;
    }
    public Build getSingleBuilding(int id){

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM cliente WHERE idCliente="+id;
        Cursor cursor = db.rawQuery(sql,null);

        if (cursor != null)
            cursor.moveToNext();

        Build build= new Build();
        build.setIdCliente(cursor.getShort(0));
        build.setNombreCliente(cursor.getString(1));
        build.setDireccionCliente(cursor.getString(2));
        build.setCelularCliente(cursor.getString(3));
        build.setMailCliente(cursor.getString(4));
        build.setDescripcionObraCliente(cursor.getString(5));
        build.setMontoCliente(cursor.getString(6));
        build.setEstado(cursor.getInt(7));

        cursor.close();
        db.close();
        return build;
    }

    public void DeleteWorker(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM trabajador WHERE idTrabajador="+id);
        db.close();
    }
    public void DeleteBuilding(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM detalle_cliente_trabajador WHERE idCliente="+id);
        db.execSQL("DELETE FROM cliente WHERE idCliente="+id);
        db.close();
    }

    public void UpdateWorker(Worker worker){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.execSQL("UPDATE trabajador SET " +
                "nombreTrabajador= '"+worker.getNombreTrabajador()+"'," +
                "actividadTrabajador = '"+worker.getActividadTrabajador()+"'," +
                "celularTrabajador = '"+worker.getCelularTrabajador()+"' " +
                "WHERE idTrabajador="+worker.getIdTrabajador());
        db.close();
    }
    public void UpdateDetail(Detalle det){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.execSQL("UPDATE detalle_cliente_trabajador SET " +
                "fechaInicioTrabajador= '"+det.getFechaInicio()+"'," +
                "fechaFinTrabajador = '"+det.getFechaFin()+"'," +
                "pagoEstimadoTrabajador = '"+det.getPaga()+"'," +
                "cantidadObraTrabajador= '"+det.getCantidad()+"' " +
                "WHERE idTrabajador="+det.getIdTrabajador()+" and idCliente="+det.getIdCliente());
        db.close();
    }
    public void UpdateBuilding(Build build){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.execSQL("UPDATE cliente SET " +
                "nombreCliente= '"+build.getNombreCliente()+"'," +
                "direccionCliente = '"+build.getDireccionCliente()+"'," +
                "celularCliente = '"+build.getCelularCliente()+"'," +
                "mailCliente = '"+build.getMailCliente()+"'," +
                "descripcionObraCliente = '"+build.getDescripcionObraCliente()+"'," +
                "montoCliente = '"+build.getMontoCliente()+"'," +
                "estadoCliente= '"+build.getEstado()+"' " +
                "WHERE idCliente="+build.getIdCliente());
        db.close();
    }
    public void DeletDetail(int idCliente,int idTrabajador){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM detalle_cliente_trabajador WHERE idCliente="+idCliente+" and idTrabajador="+idTrabajador);
        db.close();
    }
    public void UpdateEstado(int id, int estado){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.execSQL("UPDATE cliente SET " +
                "estdoCliente= "+estado+"" +
                "idCliente="+id);
        db.close();
    }
}
