package com.aparshikhin.sensors.state;

public enum State {
    TiltLeft("SWIPE TO PREVIOUS SLIDE"),
    TiltRight("SWIPE TO NEXT SLIDE"),
    Straight(""),
    Downwards("");

    private String command;

    State(String description) {
        this.command = description;
    }

    public String getCommand() {
        return command;
    }
}
