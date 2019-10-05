package club.licona.anthenpiaapp.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.activity.LoginActivity;

/**
 * @author licona
 */
public class MyFragment extends Fragment{

    private TextView tvLogin;

    public MyFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);
        tvLogin = view.findViewById(R.id.tv_login);
        if(getActivity().getIntent().getStringExtra("username") != null) {
            tvLogin.setText(getActivity().getIntent().getStringExtra("username"));
        }
        else {
            tvLogin.setOnClickListener(v-> {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            });
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}