package com.example.finalproject.javaClasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.finalproject.R;

import org.w3c.dom.Text;

import java.util.List;

public class ClassTypeList extends ArrayAdapter<ClassType> {
    private Activity context;
    List<ClassType> classTypeList;

    public ClassTypeList(Activity context, List<ClassType> classTypeList) {
        super(context, R.layout.class_type_list, classTypeList);
        this.context = context;
        this.classTypeList = classTypeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.class_type_list, null, true);

        TextView classTypeNameText = (TextView) listViewItem.findViewById(R.id.classTypeName);
        TextView classTypeDescriptionText = (TextView) listViewItem.findViewById(R.id.classTypeDescription);

        ClassType classType = classTypeList.get(position);
        classTypeNameText.setText(classType.getClassTypeName());
        classTypeDescriptionText.setText(classType.getClassTypeDescription());
        return listViewItem;
    }
}
