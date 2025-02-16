/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.b.l.db;

/**
 *
 * @author User
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;

public class GsonUtil {
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

    public static Gson getGson() {
        return gson;
    }
}
