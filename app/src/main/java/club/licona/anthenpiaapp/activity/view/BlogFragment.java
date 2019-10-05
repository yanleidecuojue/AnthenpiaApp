package club.licona.anthenpiaapp.activity.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import club.licona.anthenpiaapp.R;

/**
 * @author licona
 */
public class BlogFragment extends Fragment{

    public BlogFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blog, container, false);
    }
}