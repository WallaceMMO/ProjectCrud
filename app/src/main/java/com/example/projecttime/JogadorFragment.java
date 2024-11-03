package com.example.projecttime;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.projecttime.controller.JogadorDao;
import com.example.projecttime.controller.TimeDao;
import com.example.projecttime.model.Jogador;
import com.example.projecttime.model.Time;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class JogadorFragment extends Fragment {

    private EditText txtId, txtNome, txtAltura, txtPeso;
    private EditText txtDataNasc;
    private Spinner spnTime;
    private Button btnInsert, btnUpdate, btnDelete, btnFindOne, btnFindAll;
    private TextView txtResultado;

    private JogadorDao jogadorDao;
    private TimeDao timeDao;

    private LocalDate dataNasc;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());

    private List<Time> listaTimes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_jogador, container, false);

        txtId = view.findViewById(R.id.txtId);
        txtNome = view.findViewById(R.id.txtNome);
        txtDataNasc = view.findViewById(R.id.txtDataNasc);
        txtAltura = view.findViewById(R.id.txtAltura);
        txtPeso = view.findViewById(R.id.txtPeso);

        spnTime = view.findViewById(R.id.spnTime);

        btnInsert = view.findViewById(R.id.btnInsert);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnFindOne = view.findViewById(R.id.btnFindOne);
        btnFindAll = view.findViewById(R.id.btnFindAll);

        txtResultado = view.findViewById(R.id.txtResultado);
        txtResultado.setMovementMethod(new ScrollingMovementMethod());

        jogadorDao = new JogadorDao();
        timeDao = TimeDao.getInstance();

        txtDataNasc.setOnClickListener(v -> {
            LocalDate hoje = LocalDate.now();
            int day = hoje.getDayOfMonth();
            int month = hoje.getMonthValue() - 1; // DatePickerDialog usa 0-11 para meses
            int year = hoje.getYear();

            DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                    (view1, year1, month1, dayOfMonth) -> {
                        dataNasc = LocalDate.of(year1, month1 + 1, dayOfMonth);
                        txtDataNasc.setText(dataNasc.format(formatter));
                    }, year, month, day);
            datePicker.show();
        });

        try {
            listaTimes = timeDao.findAll();
            if (listaTimes.isEmpty()) {
                Toast.makeText(getContext(), "Nenhum time cadastrado. Cadastre um time primeiro.", Toast.LENGTH_LONG).show();
            }
            ArrayAdapter<Time> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listaTimes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnTime.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnInsert.setOnClickListener(v -> {
            Jogador jogador = new Jogador();
            try {
                jogador.setId(Integer.parseInt(txtId.getText().toString()));
                jogador.setNome(txtNome.getText().toString());

                if (dataNasc != null) {
                    jogador.setDataNasc(dataNasc);
                } else {
                    Toast.makeText(getContext(), "Por favor, selecione a data de nascimento.", Toast.LENGTH_SHORT).show();
                    return;
                }

                jogador.setAltura(Float.parseFloat(txtAltura.getText().toString()));
                jogador.setPeso(Float.parseFloat(txtPeso.getText().toString()));

                Time timeSelecionado = (Time) spnTime.getSelectedItem();
                if (timeSelecionado != null) {
                    jogador.setTime(timeSelecionado);
                } else {
                    Toast.makeText(getContext(), "Por favor, selecione um time.", Toast.LENGTH_SHORT).show();
                    return;
                }

                jogadorDao.insert(jogador);
                Toast.makeText(getContext(), "Jogador inserido com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro ao inserir jogador!", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            Jogador jogador = new Jogador();
            try {
                jogador.setId(Integer.parseInt(txtId.getText().toString()));
                jogador.setNome(txtNome.getText().toString());

                if (dataNasc != null) {
                    jogador.setDataNasc(dataNasc);
                } else {
                    Toast.makeText(getContext(), "Por favor, selecione a data de nascimento.", Toast.LENGTH_SHORT).show();
                    return;
                }

                jogador.setAltura(Float.parseFloat(txtAltura.getText().toString()));
                jogador.setPeso(Float.parseFloat(txtPeso.getText().toString()));

                Time timeSelecionado = (Time) spnTime.getSelectedItem();
                if (timeSelecionado != null) {
                    jogador.setTime(timeSelecionado);
                } else {
                    Toast.makeText(getContext(), "Por favor, selecione um time.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int result = jogadorDao.update(jogador);
                if (result > 0) {
                    Toast.makeText(getContext(), "Jogador atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Jogador não encontrado!", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro ao atualizar jogador!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            Jogador jogador = new Jogador();
            try {
                jogador.setId(Integer.parseInt(txtId.getText().toString()));
                jogadorDao.delete(jogador);
                Toast.makeText(getContext(), "Jogador deletado com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro ao deletar jogador!", Toast.LENGTH_SHORT).show();
            }
        });

        btnFindOne.setOnClickListener(v -> {
            Jogador jogador = new Jogador();
            try {
                jogador.setId(Integer.parseInt(txtId.getText().toString()));
                Jogador encontrado = jogadorDao.findOne(jogador);
                if (encontrado != null) {
                    txtResultado.setText(encontrado.toString());
                } else {
                    txtResultado.setText("Jogador não encontrado!");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro ao buscar jogador!", Toast.LENGTH_SHORT).show();
            }
        });

        btnFindAll.setOnClickListener(v -> {
            try {
                List<Jogador> jogadores = jogadorDao.findAll();
                StringBuilder sb = new StringBuilder();
                for (Jogador j : jogadores) {
                    sb.append(j.toString()).append("\n");
                }
                txtResultado.setText(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Erro ao listar jogadores!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}