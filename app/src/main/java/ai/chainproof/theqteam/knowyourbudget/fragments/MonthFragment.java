package ai.chainproof.theqteam.knowyourbudget.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import ai.chainproof.theqteam.knowyourbudget.R;
import ai.chainproof.theqteam.knowyourbudget.activities.MainActivity;
import ai.chainproof.theqteam.knowyourbudget.activities.ShowTransactionActivity;
import ai.chainproof.theqteam.knowyourbudget.adapters.TransactionsRVAdapter;
import ai.chainproof.theqteam.knowyourbudget.model.Transaction;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantinos Tsiounis on 23-Apr-18.
 */
public class MonthFragment extends Fragment implements TransactionsRVAdapter.ItemClickListener {

    @BindView(R.id.pieChart)
    public PieChart pieChart;
    @BindView(R.id.transactionsRV)
    public RecyclerView transactionsRV;
    private TransactionsRVAdapter transactionsRVAdapter;
    ArrayList<Transaction> transactions = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        transactions.clear();
        transactions.addAll(getArguments().<Transaction>getParcelableArrayList("transactions"));
        //Log.d("Fragment", "onCreateView: " + transactions.get(1).getCategory());

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,15,5);
        pieChart.setHoleRadius(10f);
        pieChart.setTransparentCircleRadius(12f);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setDrawEntryLabels(false);

        setData(4, 100);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        transactionsRV.setLayoutManager(layoutManager);
        transactionsRV.setItemAnimator(new DefaultItemAnimator());
        transactionsRV.setHasFixedSize(true);
        transactionsRVAdapter = new TransactionsRVAdapter(this);
        transactionsRV.setAdapter(transactionsRVAdapter);
        transactionsRVAdapter.swapList(transactions);

        return view;
    }

    public void setData(int count, float range){
        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((mult / 4), "Shopping", i));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 50));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    @Override
    public void onItemClickListener(int position) {
        Log.d("MonthFragment", "onItemClickListener: Item clicked " + position + " " + transactions.get(position).getAmount());
        Intent intent = new Intent(getActivity().getApplicationContext(), ShowTransactionActivity.class);
        intent.putExtra("transaction", transactions.get(position));
        getActivity().startActivityForResult(intent, 3);
    }
}
