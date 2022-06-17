package com.example.nubank.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.nubank.R;
import com.example.nubank.databinding.FragmentMainBinding;
import com.example.nubank.ui.activity.Authetication;
import com.example.nubank.viewModel.AccountViewModel;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;

public class MainFragment extends Fragment {


    private FragmentMainBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    private GoogleSignInClient mGoogleSignInClient;
    private AccountViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        inicializeValues();


        initializeGoogle();
        initializeName();
        initializeListennerImageEye();
        clickListenerButtonLogout();
        clickListenerButtonCreditCard();
        clickListenerButtonCards();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void inicializeValues() {
        viewModel.balance.observe(getViewLifecycleOwner(), it -> binding.tvSaldoConta.setText(it));

        viewModel.totalFaturaFechada.observe(getViewLifecycleOwner(), it -> binding.tvValorFatura.setText(it));

        viewModel.limiteDisponivel.observe(getViewLifecycleOwner(), it -> binding.tvLimite.setText(getString(R.string.txt_limit, NumberFormat.getCurrencyInstance().format(it))));
    }

    private void clickListenerButtonCards() {
        binding.cardMeusCartoes.setOnClickListener(view -> {
            NavDirections action = MainFragmentDirections.actionMainFragment2ToMyCardFragment();
            Navigation.findNavController(view).navigate(action);
        });

    }

    private void clickListenerButtonCreditCard() {
        binding.imgArrowCreditCard.setOnClickListener(view -> {
            NavDirections action = MainFragmentDirections.actionMainFragment2ToCreditCardInformationFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }

    private void initializeListennerImageEye() {
        binding.imgEyes.setOnClickListener(view -> {
            if (binding.imgEyes.getContentDescription().equals(getString(R.string.dc_eyes_open))) {
                binding.tvSaldoConta.setText(R.string.txt_balance_hidden);
                binding.imgEyes.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_eye_open));
                binding.imgEyes.setContentDescription(getString(R.string.dc_eyes_closed));
            } else {
                binding.tvSaldoConta.setText(R.string.txt_balance);
                binding.imgEyes.setImageDrawable(AppCompatResources.getDrawable(getContext(), R.drawable.ic_eye_closed));
                binding.imgEyes.setContentDescription(getString(R.string.dc_eyes_open));
            }
        });

    }

    private void initializeName() {
        String nome = getActivity().getIntent().getStringExtra("nome");
        binding.tvGreetings.setText(getString(R.string.txt_greetings, nome));
    }

    private void initializeGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void clickListenerButtonLogout() {
        binding.imgLogout.setOnClickListener(view -> {
            mGoogleSignInClient.signOut();
            signOut();
            signOutGoogle();
            signOutFacebook();
            nextAcitivity();
        });
    }

    public void nextAcitivity(){
        Intent intent = new Intent(getContext(), Authetication.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void signOut() {
        auth.signOut();
    }
    private void signOutFacebook() {
        LoginManager.getInstance().logOut();
    }
    private void signOutGoogle() {
        mGoogleSignInClient.signOut();
    }

    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        FirebaseUser currentUser = auth.getCurrentUser();

        if (account != null || currentUser != null) {
            Log.i("Mensagem", "Usuário logado");

        } else {
            Log.i("Mensagem", "Usuário não logado");
        }

    }
}