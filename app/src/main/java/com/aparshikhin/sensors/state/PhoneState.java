package com.aparshikhin.sensors.state;

import com.aparshikhin.sensors.helpers.Logger;

public class PhoneState {
    private static PhoneState PhoneS = new PhoneState();

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
