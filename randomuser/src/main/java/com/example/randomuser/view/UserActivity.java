package com.example.randomuser.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.randomuser.R;
import com.example.randomuser.model.Picture;
import com.example.randomuser.model.Result;
import com.example.randomuser.model.User;
import com.example.randomuser.viewmodel.UserActivityViewModel;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {
    private ImageView userImage;
    private TextView userFullname;
    private TextView userMail;

    private UserActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initViews();

        viewModel.obterUsuario();

        viewModel.getUsuario().observe(this, user -> {
            Result result = user.getResults().get(0);
            Picasso.get().load(result.getPicture().getLarge()).into(userImage);

            userFullname.setText(result.getName().toString());
            userMail.setText(result.getEmail());
        });


    }

    public void initViews() {
        userImage = findViewById(R.id.user_picture);
        userFullname = findViewById(R.id.user_fullname);
        userMail = findViewById(R.id.user_email);
        viewModel = ViewModelProviders.of(this).get(UserActivityViewModel.class);
    }
}
