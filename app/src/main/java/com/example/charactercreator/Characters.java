package com.example.charactercreator;

public class Characters {
    private int id;
    private String name;
    private String race;
    private String charClass;


    public Characters(int id, String name, String race, String charClass){
        this.id = id;
        this.name = name;
        this.race = race;
        this.charClass = charClass;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRace(){
        return race;
    }

    public String getCharClass() {
        return charClass;
    }

    public String toString(){
        return "Id: " + getId() + "\n" + "Name: " + getName() + "\n" + "Race: " + getRace() + "\n" + "Class: " + getCharClass() + "\n";
    }
}
