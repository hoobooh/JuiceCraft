package com.usagin.juicecraft.data;

public class SumikaMemory{
    String name;
    Relationships relationships;
    DialogueTree dialogue;
    CombatSettings settings;
    float[] home;
    public SumikaMemory() {

    }

    public void saveRelationships(Relationships a){
        this.relationships=a;
    }
    public void saveDialogue(DialogueTree a){
        this.dialogue=a;
    }
    public void saveCombatSettings(CombatSettings a){
        this.settings=a;
    }
    public void saveHome(float[] a){
        this.home=a;
    }
    public Relationships getRelationships(){
        return relationships;
    }
    public DialogueTree getDialogueTree(){
        return dialogue;
    }
    public CombatSettings getCombatSettings(){
        return settings;
    }
    public float[] getHome(){
        return home;
    }
}
