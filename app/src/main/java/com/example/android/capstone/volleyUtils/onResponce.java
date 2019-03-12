package com.example.android.capstone.volleyUtils;

import com.example.android.capstone.moviemodel.MultiSearch;

public interface onResponce {
    void onSuccess(Object responce, Object mainResponce);
    void onFail(String error);
}

