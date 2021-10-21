package com.example.finalproject.javaClasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.List;


public class UserList extends ArrayAdapter<User> {
    private Activity context;
    List<User> userList;

    public UserList(Activity context, List<User> userList) {
        super(context, R.layout.class_type_list, userList);
        this.context = context;
        this.userList = userList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.user_list, null, true);

        TextView usernameText = (TextView) listViewItem.findViewById(R.id.usernameTitleTextView);
        TextView userRoleText = (TextView) listViewItem.findViewById(R.id.userRoleTextView);

        User user = userList.get(position);
        usernameText.setText(user.getUsername());
        userRoleText.setText(user.getUserType());
        return listViewItem;
    }

}
