package com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.database.EventoDAO;
import com.example.evento.R;
import com.modelo.Evento;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;
    public EditText filtroEvento;
    public EditText filtrocidade;
    public String ordem = "ASC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");
        listViewEventos = findViewById(R.id.listView_local);
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        onClickListaEventos();
        filtroEvento = findViewById(R.id.editTextFiltroEvento);
        filtrocidade = findViewById(R.id.editTextFiltroCidade);
        filtroAutomatico(filtroEvento);
        filtroAutomatico(filtrocidade);
    }

    @Override
    protected void onResume() {
        super.onResume();
        filtrarGrid(null);
    }


    private void onClickListaEventos() {
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoSelecionado = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEventoAcitivity.class);
                intent.putExtra("eventotoSelecionado", eventoSelecionado);
                startActivity(intent);
            }
        });
    }


    public void onClickNew(View v) {
        Intent intent = new Intent(MainActivity.this,
                CadastroEventoAcitivity.class);
        startActivity(intent);
    }

    public void onClickLocal(View v) {
        Intent intent = new Intent(MainActivity.this, ListarLocalActivity.class);
        startActivity(intent);
        finish();
    }

    public void filtrarGrid(View v) {
        EventoDAO eventoDAO = new EventoDAO(getApplicationContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDAO.Listar(filtroEvento.getText().toString(), filtrocidade.getText().toString(), ordem));
        listViewEventos.setAdapter(adapterEventos);
    }

    public void ordemCrescente(View V) {
        ordem = "ASC";
        filtrarGrid(null);

    }

    public void ordermDecrescente(View V) {
        ordem = "DESC";
        filtrarGrid(null);
    }

    private void filtroAutomatico(EditText filtro) {
        filtro.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarGrid(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}