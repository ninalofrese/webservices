package com.example.albumsapi.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albumsapi.R;
import com.example.albumsapi.model.Album;
import com.example.albumsapi.view.adapters.RecyclerAlbumAdapter;
import com.example.albumsapi.viewmodel.AlbumsActivityViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.signin.SignInOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.albumsapi.view.LoginActivity.GOOGLE_ACCOUNT;

public class AlbumsActivity extends AppCompatActivity {
    private RecyclerView recyclerAlbum;
    private ProgressBar progressBar;
    private SearchView search;
    private RecyclerAlbumAdapter adapter;
    private List<Album> albumList = new ArrayList<>();
    private AlbumsActivityViewModel viewModel;

    private String itemBusca = "Iron Maiden";
    private int pagina = 1;

    private ImageView profileImage;
    private TextView profileNome;
    private TextView profileEmail;
    private Button profileButton;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        initViews();
        setScrollView();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        pegaOsDados();

        profileButton.setOnClickListener(view -> {
            googleSignInClient.signOut().addOnCompleteListener(task -> {
                Intent intent = new Intent(AlbumsActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        });

        viewModel.getAllAlbums(itemBusca);

        viewModel.getListAlbums().observe(this, albums -> {
            if (albums != null && !albums.isEmpty()) {
                adapter.update(albums);
            } else {
                adapter.update(this.albumList);
            }
        });

        viewModel.getErrorAlbum().observe(this, s -> {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });

        viewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                itemBusca = query;
                adapter.clear();
                viewModel.getAllAlbums(itemBusca);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() > 3) {
                    itemBusca = query;
                    adapter.clear();
                    viewModel.getAllAlbums(itemBusca);
                }

                return false;
            }
        });

    }

    public void initViews() {
        recyclerAlbum = findViewById(R.id.recyclerViewAlbum);
        progressBar = findViewById(R.id.progress_bar);
        search = findViewById(R.id.search_artist);
        viewModel = ViewModelProviders.of(this).get(AlbumsActivityViewModel.class);
        adapter = new RecyclerAlbumAdapter(albumList);
        recyclerAlbum.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerAlbum.setAdapter(adapter);
        profileImage = findViewById(R.id.profile_image);
        profileNome = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        profileButton = findViewById(R.id.profile_logout);
    }

    private void pegaOsDados() {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        Picasso.get().load(googleSignInAccount.getPhotoUrl()).centerInside().fit().into(profileImage);
        profileNome.setText(googleSignInAccount.getDisplayName());
        profileEmail.setText(googleSignInAccount.getEmail());
    }

    private void setScrollView() {
        recyclerAlbum.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //número de itens visíveis na tela (com margem para 1 acima e 1 abaixo, por conta do recycler)
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean ultimoItem = lastVisible + 5 >= totalItemCount;

                if (totalItemCount > 0 && ultimoItem) {
                    pagina++;
                    //viewModel.getAllAlbums(itemBusca, pagina);
                }
            }
        });
    }
}
