package com.example.projecttime.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Jogador {
    private int id;
    private String nome;
    private LocalDate dataNasc;
    private float altura;
    private float peso;
    private Time time;

    public Jogador() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataNascStr = dataNasc != null ? dataNasc.format(formatter) : "N/A";
        return
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNasc=" + dataNascStr +
                ", altura=" + altura +
                ", peso=" + peso +
                ", time=" + (time != null ? time.getNome() : "N/A");
    }
}