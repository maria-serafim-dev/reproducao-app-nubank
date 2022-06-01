package com.example.nubank.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.nubank.R;
import com.example.nubank.databinding.ActivityMainBinding;
import com.example.nubank.viewModel.AccountViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private GoogleSignInClient mGoogleSignInClient;
    private AccountViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        NavController navController = navHostFragment.getNavController();
    }

}