package com.baccouche.moez.jobfinder.Conversations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baccouche.moez.jobfinder.Model.Ad;
import com.baccouche.moez.jobfinder.Model.User;
import com.baccouche.moez.jobfinder.R;

import java.io.File;
import java.util.List;

/**
 * Created by ASUS on 18/03/2018.
 */

public class ConversationAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> listUsers;


    public ConversationAdapter(Context context, List<User> listUsers)
    {
        super(context, R.layout.custom_layout_conversation, listUsers);
        this.context = context;
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.custom_layout, parent, false);


        User user = listUsers.get(position);

        TextView tvFullName = (TextView) convertView.findViewById(R.id.tvFullName);

        ImageView imUser = (ImageView) convertView.findViewById(R.id.imUser);


        String path = user.getPicture();
        File imgFile = new File(path);
        if(imgFile.exists())
        {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imUser.setImageBitmap(bitmap);
        }

        String lastName = user.getLastName();
        String firstName = user.getFirstName();

        String fullName = firstName + " " + lastName;
        tvFullName.setText(fullName);


        return convertView;
    }

}
