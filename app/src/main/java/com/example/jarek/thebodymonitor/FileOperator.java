package com.example.jarek.thebodymonitor;

import android.os.Environment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


class FileOperator {
    private final static String sfileName = "monitor_Data.txt";

    /**
     * Metoda dopisuje do pliku o nazwie sfileName kolejne dane. Kolejność zapisu to x parametrów
     * użytkownika, czas (godziny:minuty:sekundy), data (dzień.miesiąc.rok).
     * @param params tablica danych wprowadzonych przez użytkownika
     * @return true jeśli zapis się powiódł, false jeśli zapis się nie powiódł
     */
    boolean appendDataToFile(int params[]){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss;dd.MM.yyyy");
        //przechowuje ścieżkę
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), sfileName);
        //włącza głowicę dopisującą do końca pliku
            FileWriter fileOutputStream = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileOutputStream);
            for (int x: params) {
                bufferedWriter.append(String.valueOf(x)).append(";");
            }
            bufferedWriter.append(dateFormat.format(new Date())).append("\n");
            bufferedWriter.flush();//zatwierdza dane
            bufferedWriter.close();//zamyka głowicę
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
