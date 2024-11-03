package com.example.projecttime;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projecttime.controller.TimeDao;
import com.example.projecttime.model.Time;

import java.sql.SQLException;
import java.util.List;

public class TimeFragment extends Fragment {

    private EditText txtCodigo, txtNome, txtCidade;
    private Button btnInsert, btnUpdate, btnDelete, btnFindOne, btnFindAll;
    private TextView txtResultado;

    private TimeDao timeDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        txtCodigo = view.findViewById(R.id.txtCodigo);
        txtNome = view.findViewById(R.id.txtNome);
        txtCidade = view.findViewById(R.id.txtCidade);

        btnInsert = view.findViewById(R.id.btnInsert);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnFindOne = view.findViewById(R.id.btnFindOne);
        btnFindAll = view.findViewById(R.id.btnFindAll);

        txtResultado = view.findViewById(R.id.txtResultado);
        txtResultado.setMovementMethod(new ScrollingMovementMethod());

        timeDao = TimeDao.getInstance();

        btnInsert.setOnClickListener(v -> {
            Time time = new Time();
            time.setCodigo(Integer.parseInt(txtCodigo.getText().toString()));
            time.setNome(txtNome.getText().toString());
            time.setCidade(txtCidade.getText().toString());
            try {
                timeDao.insert(time);
                Toast.makeText(getContext(), "Time inserido com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            Time time = new Time();
            time.setCodigo(Integer.parseInt(txtCodigo.getText().toString()));
            time.setNome(txtNome.getText().toString());
            time.setCidade(txtCidade.getText().toString());
            try {
                int result = timeDao.update(time);
                if (result > 0) {
                    Toast.makeText(getContext(), "Time atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Time não encontrado!", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnDelete.setOnClickListener(v -> {
            Time time = new Time();
            time.setCodigo(Integer.parseInt(txtCodigo.getText().toString()));
            try {
                timeDao.delete(time);
                Toast.makeText(getContext(), "Time deletado com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnFindOne.setOnClickListener(v -> {
            Time time = new Time();
            time.setCodigo(Integer.parseInt(txtCodigo.getText().toString()));
            try {
                Time encontrado = timeDao.findOne(time);
                if (encontrado != null) {
                    txtResultado.setText(encontrado.toStringPrint());
                } else {
                    txtResultado.setText("Time não encontrado!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnFindAll.setOnClickListener(v -> {
            try {
                List<Time> times = timeDao.findAll();
                StringBuilder sb = new StringBuilder();
                for (Time t : times) {
                    sb.append(t.toStringPrint()).append("\n");
                }
                txtResultado.setText(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return view;
    }
}