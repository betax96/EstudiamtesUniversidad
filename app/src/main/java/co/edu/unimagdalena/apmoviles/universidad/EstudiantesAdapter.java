package co.edu.unimagdalena.apmoviles.universidad;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EstudiantesAdapter extends RecyclerView.Adapter<EstudiantesAdapter.EstudianteViewHolder> {

    private EstudianteController estudianteController;
    private Context context;
    private Cursor cursor;
    private OnClickListener onClickListener;

    EstudiantesAdapter(Context context, EstudianteController estudianteController){
        this.estudianteController = estudianteController;
        this.context = context;
        cursor = estudianteController.getEstudiantesCursor();
    }

    @NonNull
    @Override
    public EstudianteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EstudianteViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_studiante,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final EstudianteViewHolder holder, int position) {
        cursor.moveToPosition(position);
        final String codigo = cursor.getString(0);
        final String nombre = cursor.getString(1);
        final String programa = cursor.getString(2);
        holder.txtCodigo.setText(codigo);
        holder.txtNombre.setText(nombre);
        holder.txtPrograma.setText(programa);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener!=null){
                    onClickListener.onClick(holder.getAdapterPosition(), new Estudiante(codigo,nombre,programa));
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void refresh(){
        cursor.close();
        cursor = estudianteController.getEstudiantesCursor();
        notifyDataSetChanged();
    }

    public interface OnClickListener{
        void onClick(int position, Estudiante estudiante);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    static class EstudianteViewHolder extends RecyclerView.ViewHolder{

        private TextView txtCodigo;
        private TextView txtNombre;
        private TextView txtPrograma;

        public EstudianteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtPrograma = itemView.findViewById(R.id.txtPrograma);
            txtNombre = itemView.findViewById(R.id.txtNombre);
        }
    }


}
