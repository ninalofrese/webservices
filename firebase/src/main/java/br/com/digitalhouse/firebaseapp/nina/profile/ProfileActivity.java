package br.com.digitalhouse.firebaseapp.nina.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.com.digitalhouse.firebaseapp.R;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;

public class ProfileActivity extends AppCompatActivity {
    private static final int PERMISSION_CODE = 100;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private CircleImageView imageViewProfile;
    private Button btnRegister;
    private ProgressBar progressBar;
    private EasyImage easyImage;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnRegister = findViewById(R.id.btn_register);
        textInputLayoutName = findViewById(R.id.textinput_name);
        textInputLayoutEmail = findViewById(R.id.textinput_email);
        imageViewProfile = findViewById(R.id.imageview_profile);
        progressBar = findViewById(R.id.progressBar);
        easyImage = new EasyImage.Builder(this).build();
        user = FirebaseAuth.getInstance().getCurrentUser();

        textInputLayoutName.getEditText().setText(user.getDisplayName());
        textInputLayoutEmail.getEditText().setText(user.getEmail());

        imageViewProfile.setOnClickListener(view -> {
            int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionCamera == PackageManager.PERMISSION_GRANTED && permissionStorage == PackageManager.PERMISSION_GRANTED) {
                easyImage.openCameraForImage(ProfileActivity.this);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            easyImage.openCameraForImage(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(@NotNull MediaFile[] mediaFiles, @NotNull MediaSource mediaSource) {
                for (MediaFile mediaFile : mediaFiles) {
                    try {
                        File file = mediaFile.getFile();

                        File fileReducted = reduzirTamanho(file);
                        InputStream stream = new FileInputStream(fileReducted);
                        salvarImagemFirebase(stream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCanceled(@NotNull MediaSource source) {
                super.onCanceled(source);
            }

            @Override
            public void onImagePickerError(@NotNull Throwable error, @NotNull MediaSource source) {
                super.onImagePickerError(error, source);
            }
        });
    }

    private File reduzirTamanho(File file) {
        //TODO:Reduzir o tamanho da imagem antes de mandar para o Firebase, para que fique mais rápido
    }

    private void salvarImagemFirebase(InputStream stream) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(user.getUid() + "/image/profile");

        UploadTask uploadTask = storageReference.putStream(stream);
        uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    Picasso.get().load(uri).rotate(90).into(imageViewProfile);
                });
            }
        });
    }
}
