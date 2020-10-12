package com.example.taski_firebase_version;

public class My_Does {
    String titel_DO;
    String date_DO;
    String desc_DO;
    String key_DO;

    public String getTeil_Key() {
        return teil_Key;
    }

    public void setTeil_Key(String teil_Key) {
        this.teil_Key = teil_Key;
    }

    public My_Does(String titel_DO, String date_DO, String desc_DO, String key_DO, String teil_Key) {
        this.titel_DO = titel_DO;
        this.date_DO = date_DO;
        this.desc_DO = desc_DO;
        this.key_DO = key_DO;
        this.teil_Key = teil_Key;
    }

    String teil_Key;
    String A_doingDate;
    String A_doingDesc;
    String A_doingTitle;

    public String getKey_DO() {
        return key_DO;
    }

    public void setKey_DO(String key_DO) {
        this.key_DO = key_DO;
    }

    public My_Does() {
    }


    public My_Does(String titel_DO, String date_DO, String desc_DO) {
        this.titel_DO = titel_DO;
        this.date_DO = date_DO;
        this.desc_DO = desc_DO;
    }


    public String getTitel_DO() {
        return titel_DO;
    }


    public String getDate_DO() {
        return date_DO;
    }


    public String getDesc_DO() {
        return desc_DO;
    }

    @Override
    public String toString() {
        return "My_Does{" +
                "titel_DO='" + titel_DO + '\'' +
                ", date_DO='" + date_DO + '\'' +
                ", desc_DO='" + desc_DO + '\'' +
                ", key_DO='" + key_DO + '\'' +
                ", teil_Key='" + teil_Key + '\'' +
                '}';
    }
}