package com.aparshikhin.sensors.state;

import com.aparshikhin.sensors.helpers.Logger;
import com.aparshikhin.sensors.models.CommandDTO;
import com.aparshikhin.sensors.retrofit2.Common;
import com.aparshikhin.sensors.retrofit2.RetrofitServices;

import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneState {
    private static PhoneState PhoneS = new PhoneState();
    private RetrofitServices mService = Common.INSTANCE.getRetrofitService();

    public static PhoneState getPhoneState() {
        return PhoneS;
    }

    // actual phone state
    private State state;

    // candidate phone state
    private State candidate;
    // votes for current candidate phone state
    private int votes;

    private PhoneState() {
        state = State.Straight;
    }

    public void notifyStateChange(State newS) {
        if (!isStatePersisting(newS)) {
            return;
        }

        State prevS = state;

        if (prevS != newS) {
            Logger.Companion.info("state change" + newS.toString());
        }

        if ((newS == State.TiltLeft || newS == State.TiltRight) && prevS == State.Straight) {
            mService.sendCommand(new CommandDTO(newS.getCommand())).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Logger.Companion.info("onResponse");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Logger.Companion.info("onFailure");
                    Logger.Companion.info(Arrays.toString(t.getStackTrace()));
                }
            });
            Logger.Companion.info("COMMAND SEND:" + newS.getCommand());
        }

        state = newS;
    }

    private boolean isStatePersisting(State newS) {
        // state need to repeat consequently at least two times to be considered persisting
        // only such persisting states should actually change the state
        if (newS != candidate) {
            candidate = newS;
            votes = 1;
            return false;
        } else {
            votes++;
            if (votes < 2) {
                return false;
            } else {
                votes = 0;
                return true;
            }
        }
    }
}
