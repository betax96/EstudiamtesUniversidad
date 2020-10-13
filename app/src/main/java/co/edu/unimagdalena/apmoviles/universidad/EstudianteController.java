package co.edu.unimagdalena.apmoviles.universidad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class EstudianteController {

    private BaseDatos bd;
    private Context c;

    public EstudianteController(Context c){
        this. bd = new BaseDatos(c, 1);
        this.c = c;
    }

    public void agregarEstudiante(Estudiante e){
        try{
            SQLiteDatabase sql = bd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DefBD.col_codigo, e.getCodigo());
            values.put(DefBD.col_nombre, e.getNombre());
            values.put(DefBD.col_programa, e.getPrograma());
            long id = sql.insert(DefBD.tabla_est, null, values);
            Toast.makeText(c, "Estudiante registrado", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Log.e("Error", Log.getStackTraceString(ex));
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void modificarEstudiante(String codigo, Estudiante e){
        try{
            SQLiteDatabase sql = bd.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DefBD.col_codigo, e.getCodigo());
            values.put(DefBD.col_nombre, e.getNombre());
            values.put(DefBD.col_programa, e.getPrograma());
            sql.update(DefBD.tabla_est, values, DefBD.col_codigo+"=?",new String[]{codigo});
            Toast.makeText(c, "Estudiante modificado", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Log.e("Error", Log.getStackTraceString(ex));
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarEstudiante(String codigo){
        try{
            SQLiteDatabase sql = bd.getWritableDatabase();
            sql.delete(DefBD.tabla_est, DefBD.col_codigo+"=?",new String[]{codigo});
            Toast.makeText(c, "Estudiante eliminado", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Log.e("Error", Log.getStackTraceString(ex));
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getEstudiantesCursor(){
        SQLiteDatabase sql = bd.getReadableDatabase();
        Cursor cursor = sql.query(DefBD.tabla_est, null, null,
                null, null, null, null, null);
        return cursor;
    }


}
