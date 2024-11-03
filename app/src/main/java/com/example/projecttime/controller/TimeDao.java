package com.example.projecttime.controller;


import com.example.projecttime.model.Time;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeDao implements ICRUDDao<Time> {

    private List<Time> listaTimes;
    private static TimeDao instance;

    private TimeDao() {
        this.listaTimes = new ArrayList<>();
    }

    public static synchronized TimeDao getInstance() {
        if (instance == null) {
            instance = new TimeDao();
        }
        return instance;
    }

    @Override
    public void insert(Time time) throws SQLException {
        listaTimes.add(time);
    }

    @Override
    public int update(Time time) throws SQLException {
        for (int i = 0; i < listaTimes.size(); i++) {
            if (listaTimes.get(i).getCodigo() == time.getCodigo()) {
                listaTimes.set(i, time);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public void delete(Time time) throws SQLException {
        listaTimes.removeIf(t -> t.getCodigo() == time.getCodigo());
    }

    @Override
    public Time findOne(Time time) throws SQLException {
        for (Time t : listaTimes) {
            if (t.getCodigo() == time.getCodigo()) {
                return t;
            }
        }
        return null;
    }

    @Override
    public List<Time> findAll() throws SQLException {
        return listaTimes;
    }
}