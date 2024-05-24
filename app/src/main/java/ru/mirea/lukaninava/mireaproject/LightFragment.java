package ru.mirea.lukaninava.mireaproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class LightFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor brightness;
    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        text = view.findViewById(R.id.tv_text);
        setUpSensor();
        return view;
    }

    // Настройка датчика яркости
    private void setUpSensor() {
        sensorManager = (SensorManager) requireActivity().getSystemService(requireActivity().SENSOR_SERVICE);
        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    // Регистрация слушателя датчика при возобновлении фрагмента
    @Override
    public void onResume() {
        super.onResume();
        if (brightness != null) {
            sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // Отмена регистрации слушателя датчика при приостановке фрагмента
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    // Обработка изменений в датчике яркости
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float light1 = event.values[0];
            text.setText("Sensor: " + light1 + "\n" + brightness(light1));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Возвращает сообщение о яркости на основе значения яркости
    private String brightness(float brightness) {
        if (brightness == 0) {
            return "Полная темнота";
        } else if (brightness >= 1 && brightness <= 10) {
            return "Темно";
        } else if (brightness >= 11 && brightness <= 50) {
            return "Средняя яркость";
        } else if (brightness >= 51 && brightness <= 5000) {
            return "Нормально";
        } else if (brightness >= 5001 && brightness <= 25000) {
            return "Очень ярко";
        } else {
            return "Этот свет слепит";
        }
    }
}