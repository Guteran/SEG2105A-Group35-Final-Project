package com.example.finalproject.javaClasses;

public class ClassType {
    private String _id;
    private String _classTypeName;
    private String _classTypeDescription;

    public ClassType(){
    }

    public ClassType(String id, String classTypeName, String classTypeDescription) {
        this._id = id;
        this._classTypeName = classTypeName;
        this._classTypeDescription = classTypeDescription;
    }

    public ClassType(String classTypeName, String classTypeDescription) {
        this._classTypeName = classTypeName;
        this._classTypeDescription = classTypeDescription;
    }

    public String getId() {
        return _id;
    }

    public String getClassTypeName() {
        return _classTypeName;
    }

    public String getClassTypeDescription() {
        return _classTypeDescription;
    }

    public void setId(String id) {
        this._id = id;
    }

    public void setClassTypeName(String classTypeName) {
        this._classTypeName = classTypeName;
    }

    public void setClassTypeDescription(String classTypeDescription) {
        this._classTypeDescription = classTypeDescription;
    }
}
