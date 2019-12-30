package com.ljt.study.dp.command;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * @author LiJingTang
 * @date 2019-12-28 16:58
 */
public class CommandDemo {

    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        GarageDoor garageDoor = new GarageDoor();
        Stereo stereo = new Stereo("Living Room");

        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        GarageDoorUpCommand garageDoorOpen = new GarageDoorUpCommand(garageDoor);
        GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(garageDoor);

        StereoOnWithDCCommand stereoOnWithCd = new StereoOnWithDCCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);

        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setCommand(1, garageDoorOpen, garageDoorDown);
        remoteControl.setCommand(2, stereoOnWithCd, stereoOff);
        remoteControl.setCommand(3, kitchenLightOn, kitchenLightOff);

        System.out.println(remoteControl);

        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);
        remoteControl.onButtonWasPushed(1);
        remoteControl.offButtonWasPushed(1);
        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);
        remoteControl.onButtonWasPushed(3);
        remoteControl.offButtonWasPushed(3);
    }

    @Test
    public void testSimple() {
        SimpleRemoteControl remote = new SimpleRemoteControl();
        Light light = new Light();
        LightOnCommand lightOn = new LightOnCommand(light);

        GarageDoor garageDoor = new GarageDoor();
        GarageDoorUpCommand garageOpen = new GarageDoorUpCommand(garageDoor);

        remote.setCommand(lightOn);
        remote.buttonWasPressed();
        remote.setCommand(garageOpen);
        remote.buttonWasPressed();
    }

    private static class RemoteControl {

        private Command[] onCommands;
        private Command[] offCommands;

        public RemoteControl() {
            super();

            this.onCommands = new Command[7];
            this.offCommands = new Command[7];
            Command noCommand = new NoCommand();

            for (int i = 0; i < 7; i++) {
                this.onCommands[i] = noCommand;
                this.offCommands[i] = noCommand;
            }
        }

        public void setCommand(int slot, Command onCommand, Command offCommand) {
            this.onCommands[slot] = onCommand;
            this.offCommands[slot] = offCommand;
        }

        public void onButtonWasPushed(int slot) {
            this.onCommands[slot].execute();
        }

        public void offButtonWasPushed(int slot) {
            this.offCommands[slot].execute();
        }

        @Override
        public String toString() {
            StringBuilder sbuilder = new StringBuilder();
            sbuilder.append("\n------ RemoteControl ------\n");

            for (int i = 0; i < onCommands.length; i++) {
                sbuilder.append("[slot " + i + "]" + this.onCommands[i].getClass().getSimpleName()
                        + "  " + this.offCommands[i].getClass().getSimpleName() + "\n");
            }

            return sbuilder.toString();
        }
    }

    private static class SimpleRemoteControl {

        private Command slot;

        public SimpleRemoteControl() {
            super();
        }

        public void setCommand(Command command) {
            this.slot = command;
        }

        public void buttonWasPressed() {
            this.slot.execute();
        }
    }

    private interface Command {

        void execute();

        void undo();

    }

    private static class NoCommand implements Command {

        @Override
        public void execute() {
            System.out.println("NoCommand");
        }

        @Override
        public void undo() {
            this.execute();
        }
    }

    private static class Light {

        private String name;

        public Light() {
            this("");
        }

        public Light(String name) {
            super();
            this.name = name;
        }

        public void on() {
            System.out.println(this.name + (StringUtils.isNotBlank(name) ? " " : "") + "Light ON...");
        }

        public void off() {
            System.out.println(this.name + (StringUtils.isNotBlank(name) ? " " : "") + "Light OFF...");
        }
    }

    private static class LightOnCommand implements Command {

        private Light light;

        public LightOnCommand(Light light) {
            super();
            this.light = light;
        }

        @Override
        public void execute() {
            this.light.on();
        }

        @Override
        public void undo() {
            this.light.off();
        }
    }

    private static class LightOffCommand implements Command {

        private Light light;

        public LightOffCommand(Light light) {
            super();
            this.light = light;
        }

        @Override
        public void execute() {
            this.light.off();
        }

        @Override
        public void undo() {
            this.light.on();
        }
    }

    private static class GarageDoor {

        public void up() {
            System.out.println("GarageDoor ON...");
        }

        public void down() {
            System.out.println("GarageDoor DOWN...");
        }

        public void stop() {
            System.out.println("GarageDoor STOP...");
        }

        public void lightOn() {
            System.out.println("GarageDoor LIGHTON...");
        }

        public void lightOff() {
            System.out.println("GarageDoor lightOff...");
        }
    }

    private static class GarageDoorUpCommand implements Command {

        private GarageDoor garageDoor;

        public GarageDoorUpCommand(GarageDoor garageDoor) {
            super();
            this.garageDoor = garageDoor;
        }

        @Override
        public void execute() {
            this.garageDoor.up();
        }

        @Override
        public void undo() {
            this.garageDoor.down();
        }
    }

    private static class GarageDoorDownCommand implements Command {

        private GarageDoor garageDoor;

        public GarageDoorDownCommand(GarageDoor garageDoor) {
            super();
            this.garageDoor = garageDoor;
        }

        @Override
        public void execute() {
            this.garageDoor.down();
        }

        @Override
        public void undo() {
            this.garageDoor.up();
        }
    }

    /**
     * 音响
     */
    private static class Stereo {

        private String name;

        public Stereo(String name) {
            super();
            this.name = name;
        }

        public void on() {
            System.out.println(this.name + (StringUtils.isNotBlank(name) ? " " : "") + "Stereo ON...");
        }

        public void off() {
            System.out.println(this.name + (StringUtils.isNotBlank(name) ? " " : "") + "Stereo OFF...");
        }

        public void setCd() {
            System.out.println(this.name + (StringUtils.isNotBlank(name) ? " " : "") + "Stereo SETCD...");
        }

        public void setDvd() {
            System.out.println(this.name + (StringUtils.isNotBlank(name) ? " " : "") + "Stereo SETDVD...");
        }

        public void setRadio() {
            System.out.println(this.name + (StringUtils.isNotBlank(name) ? " " : "") + "Stereo SETRADIO...");
        }

        public void setVolume(short s) {
            System.out.println(this.name + (StringUtils.isNotBlank(name) ? " " : "") + "Stereo SETVOLUME..." + s);
        }
    }

    private static class StereoOnWithDCCommand implements Command {

        private Stereo stereo;

        public StereoOnWithDCCommand(Stereo stereo) {
            super();
            this.stereo = stereo;
        }

        @Override
        public void execute() {
            this.stereo.on();
            this.stereo.setCd();
            this.stereo.setVolume((short) 11);
        }

        @Override
        public void undo() {
            this.stereo.off();
        }
    }

    private static class StereoOffCommand implements Command {

        private Stereo stereo;

        public StereoOffCommand(Stereo stereo) {
            super();
            this.stereo = stereo;
        }

        @Override
        public void execute() {
            this.stereo.off();
        }

        @Override
        public void undo() {
            this.stereo.on();
        }
    }

}
