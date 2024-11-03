package com.example.projecttime.controller;

import com.example.projecttime.model.Jogador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JogadorDao implements ICRUDDao<Jogador> {

    private List<Jogador> listaJogadores;

    public JogadorDao() {
        this.listaJogadores = new ArrayList<>();
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        listaJogadores.add(jogador);
    }

    @Override
    public int update(Jogador jogador) throws SQLException {
        for (int i = 0; i < listaJogadores.size(); i++) {
            if (listaJogadores.get(i).getId() == jogador.getId()) {
                listaJogadores.set(i, jogador);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        listaJogadores.removeIf(j -> j.getId() == jogador.getId());
    }

    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        for (Jogador j : listaJogadores) {
            if (j.getId() == jogador.getId()) {
                return j;
            }
        }
        return null;
    }

    @Override
    public List<Jogador> findAll() throws SQLException {
        return listaJogadores;
    }
}