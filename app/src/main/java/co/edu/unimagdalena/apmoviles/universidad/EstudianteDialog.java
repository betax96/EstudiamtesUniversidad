package co.edu.unimagdalena.apmoviles.universidad;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.google.android.material.textfield.TextInputLayout;

public class EstudianteDialog extends AppCompatDialog {
    public EstudianteDialog(Context context) {
        super(context);
    }

    private TextInputLayout tilCodigo, tilNombre, tilPrograma;
    private Button btnGuardar, btnCancelar;
    private ActionListener actionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_estudiante);

        tilCodigo = findViewById(R.id.tilCodigo);
        tilNombre = findViewById(R.id.tilNombre);
        tilPrograma = findViewById(R.id.tilPrograma);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionListener !=null){
                    actionListener.onCancelar();
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(tilCodigo.getEditText().getText())&&!TextUtils.isEmpty(tilNombre.getEditText().getText())
                    &&!TextUtils.isEmpty(tilPrograma.getEditText().getText())){
                    if(actionListener !=null){
                        String codigo = tilCodigo.getEditText().getText().toString();
                        String nombre = tilNombre.getEditText().getText().toString();
                        String programa = tilPrograma.getEditText().getText().toString();
                        actionListener.onGuardar(new Estudiante(codigo,nombre,programa));
                    }
                }else{
                    Toast.makeText(getContext(),R.string.err_valores_no_validos,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
        tilCodigo.getEditText().setText("");
        tilNombre.getEditText().setText("");
        tilPrograma.getEditText().setText("");
    }

    public void close(){
        tilCodigo.getEditText().setText("");
        tilNombre.getEditText().setText("");
        tilPrograma.getEditText().setText("");
        cancel();
    }

    public void show(Estudiante estudiante){
        show();
        tilCodigo.getEditText().setText(estudiante.getCodigo());
        tilNombre.getEditText().setText(estudiante.getNombre());
        tilPrograma.getEditText().setText(estudiante.getPrograma());
    }

    public void show(String codigo, String nombre, String programa){
        show(new Estudiante(codigo,nombre,programa));
    }

    interface ActionListener {
        void onGuardar(Estudiante estudiante);
        void onCancelar();
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
}
