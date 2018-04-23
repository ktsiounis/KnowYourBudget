package ai.chainproof.theqteam.knowyourbudget.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ai.chainproof.theqteam.knowyourbudget.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantinos Tsiounis on 23-Apr-18.
 */
public class MonthFragment extends Fragment {

    @BindView(R.id.section_label)
    public TextView section_label;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        section_label.setText("Hello from month");

        return view;
    }
}
