package com.example.kotlinbt.database;

public class ItemData {
    public int Id;
    public String identName;
    public String identNum;
    public String status;
    public int targetCheck;

    public ItemData(int id, String identName, String identNum, int targetCheck) {
        this.Id = id;
        this.identName = identName;
        this.identNum = identNum;
        this.targetCheck = targetCheck;
        this.status = "checking";
    }
}
