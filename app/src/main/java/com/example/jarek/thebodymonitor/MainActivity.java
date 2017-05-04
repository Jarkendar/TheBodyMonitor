package com.example.jarek.thebodymonitor;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

//zmienne komponentów
    private SeekBar seekBarMental, seekBarBody, seekBarWillingness;
    private TextView textViewMental, textViewBody, textViewWillingness, textViewResults;

//zmienne programowe
    private int iNumberOfResults;

//stałe nazwy
    private final String ssharedName = "the_body_monitor_shared";
    private final String ssavedResult = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//wyłączenie actionBar

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

//przypisanie komponentów do zmiennych
        seekBarMental = (SeekBar) findViewById(R.id.seekBarMental);
        seekBarBody = (SeekBar) findViewById(R.id.seekBarBody);
        seekBarWillingness = (SeekBar) findViewById(R.id.seekBarWillingness);
        textViewMental = (TextView) findViewById(R.id.textViewMental);
        textViewBody = (TextView) findViewById(R.id.textViewBody);
        textViewWillingness = (TextView) findViewById(R.id.textViewWillingness);
        textViewResults = (TextView) findViewById(R.id.textViewNumberOfResults);

//domyślne przypisanie komponentom textView wartości
        textViewMental.setText(getText(R.string.text_mental) + " " + seekBarMental.getProgress());
        textViewBody.setText(getText(R.string.text_body) + " " + seekBarBody.getProgress());
        textViewWillingness.setText(getText(R.string.text_willingess) + " " + seekBarWillingness.getProgress());

//ustawienie nasłuchiwacza do seekBar'ów
        seekBarMental.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewMental.setText(getText(R.string.text_mental) + " " + String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarBody.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewBody.setText(getText(R.string.text_body) + " " + String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarWillingness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewWillingness.setText(getText(R.string.text_willingess) + " " + String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        readFromSharedPreferences();
        textViewResults.setText(getString(R.string.text_number_of_results) + " " + iNumberOfResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToSharedPreferences();
    }

    /**
     * Metoda zapisuje w sharedPreferences licznik ilości pomiarów.
     */
    private void saveToSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(ssharedName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ssavedResult, iNumberOfResults);
        editor.apply();
    }

    /**
     * Odczytuje ilość pomiarów z sharedPreferences
     */
    private void readFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(ssharedName, MODE_PRIVATE);
        iNumberOfResults = sharedPreferences.getInt(ssavedResult, 0);
    }

    /**
     * Kliknięcie przycisku Dodaj wywołuje metodę saveStats(), obsługującą zapis danych.
     *
     * @param view komponent wywołujący metodę
     */
    public void onClickAdd(View view) {
        boolean result = saveStats();
        if (result) {
            Toast.makeText(getApplicationContext(), getText(R.string.text_operation_prefix) + " " + getText(R.string.text_succeed), Toast.LENGTH_LONG).show();
            iNumberOfResults++;
            textViewResults.setText(getString(R.string.text_number_of_results) + " " + iNumberOfResults);
        } else {
            Toast.makeText(getApplicationContext(), getText(R.string.text_operation_prefix) + " " + getText(R.string.text_failed), Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Tworzy obiekt FileOperator i wywołuje funkcję dopisu na koniec pliku.
     *
     * @return true jeśli zapis się powiódł, false jeśli zapis się nie powiódł
     */
    private boolean saveStats() {
        FileOperator fileOperator = new FileOperator();
        int params[] = {seekBarMental.getProgress(), seekBarBody.getProgress(), seekBarWillingness.getProgress()};
        return fileOperator.appendDataToFile(params);
    }

    /**
     * Metoda wyłącza activity
     *
     * @param view komponent wywołujący metodę
     */
    public void onClickEnd(View view) {
        finish();
    }
}
