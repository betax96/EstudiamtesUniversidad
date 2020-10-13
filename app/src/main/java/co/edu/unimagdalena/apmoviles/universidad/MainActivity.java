package co.edu.unimagdalena.apmoviles.universidad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnAgregar, btnModificar, btnEliminar;
    private EstudianteDialog agregarDialog;
    private RecyclerView recEstudiantes;
    private EstudiantesAdapter estudiantesAdapter;
    private EstudianteController estudianteController;

    private boolean flagEliminar, flagModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agregarDialog = new EstudianteDialog(this);
        estudianteController = new EstudianteController(this);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
        recEstudiantes = findViewById(R.id.recEstudiantes);

        agregarDialog.setActionListener(new EstudianteDialog.ActionListener() {
            @Override
            public void onGuardar(Estudiante estudiante) {
                estudianteController.agregarEstudiante(estudiante);
                agregarDialog.close();
                estudiantesAdapter.refresh();
            }

            @Override
            public void onCancelar() {
                agregarDialog.close();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarDialog.show();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagModificar = true;
                flagEliminar = false;
                Toast.makeText(MainActivity.this, R.string.selec_estudiante_modificar,Toast.LENGTH_SHORT).show();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagEliminar = true;
                flagModificar = false;
                Toast.makeText(MainActivity.this, R.string.selec_estudiante_eliminar,Toast.LENGTH_SHORT).show();
            }
        });

        estudiantesAdapter = new EstudiantesAdapter(this, estudianteController);

        estudiantesAdapter.setOnClickListener(new EstudiantesAdapter.OnClickListener() {
            @Override
            public void onClick(int position, final Estudiante estudiante) {
                if(flagModificar){
                    final EstudianteDialog modificarDialog = new EstudianteDialog(MainActivity.this);
                    modificarDialog.setActionListener(new EstudianteDialog.ActionListener() {
                        @Override
                        public void onGuardar(Estudiante estudianteModificado) {
                            estudianteController.modificarEstudiante(estudiante.getCodigo(), estudianteModificado);
                            modificarDialog.close();
                            estudiantesAdapter.refresh();
                        }

                        @Override
                        public void onCancelar() {
                            modificarDialog.close();
                        }
                    });
                    modificarDialog.show(estudiante);
                }else if(flagEliminar){
                    estudianteController.eliminarEstudiante(estudiante.getCodigo());
                    estudiantesAdapter.refresh();
                }

                flagEliminar = false;
                flagModificar = false;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recEstudiantes.setLayoutManager(layoutManager);
        recEstudiantes.setItemAnimator(new DefaultItemAnimator());
        recEstudiantes.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recEstudiantes.setAdapter(estudiantesAdapter);


    }
}
