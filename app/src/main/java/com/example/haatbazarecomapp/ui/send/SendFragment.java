package com.example.haatbazarecomapp.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.haatbazarecomapp.R;
import com.example.haatbazarecomapp.RegisterActivity;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    private Button signOutBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_send, container, false);
        setHasOptionsMenu(true);
        signOutBtn=root.findViewById(R.id.button4);

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signOutIntent= new Intent(getContext(), RegisterActivity.class);
                root.getContext().startActivity(signOutIntent);
            }
        });
        return root;
    }

}