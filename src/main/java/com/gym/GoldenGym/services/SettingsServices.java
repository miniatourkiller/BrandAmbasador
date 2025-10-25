package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.SettingsDto;

public interface SettingsServices {
    public ResponseDto createSettings(SettingsDto settingsDto);

    public ResponseDto fetchSettings();
}
