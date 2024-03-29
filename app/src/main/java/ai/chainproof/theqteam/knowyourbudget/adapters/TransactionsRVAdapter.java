package ai.chainproof.theqteam.knowyourbudget.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import ai.chainproof.theqteam.knowyourbudget.R;
import ai.chainproof.theqteam.knowyourbudget.model.Transaction;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Konstantinos Tsiounis on 30-Apr-18.
 */
public class TransactionsRVAdapter extends RecyclerView.Adapter<TransactionsRVAdapter.TransactionViewHolder> {

    private ArrayList<Transaction> transactions;
    private ItemClickListener mListener;

    public TransactionsRVAdapter(ItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction, parent, false);

        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, final int position) {
        holder.amountTV.setText(transactions.get(position).getAmount() + "€");
        holder.categoryTV.setText(transactions.get(position).getCategory());
        holder.dateTV.setText(transactions.get(position).getDate());
        if(transactions.get(position).getCategory().equals("Salary") ||
                transactions.get(position).getCategory().equals("Other Income")){
            holder.signTV.setText("+");
            holder.signTV.setTextColor(Color.GREEN);
            holder.amountTV.setTextColor(Color.GREEN);
        } else {
            holder.signTV.setText("-");
            holder.signTV.setTextColor(Color.RED);
            holder.amountTV.setTextColor(Color.RED);
        }

        holder.amountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(transactions == null) return 0;
        else return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.amountTV) public TextView amountTV;
        @BindView(R.id.signTV) public TextView signTV;
        @BindView(R.id.dateTV) public TextView dateTV;
        @BindView(R.id.categoryTV) public TextView categoryTV;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            Log.d("Adapter", "onClick: item clicked");
            int position = getLayoutPosition();
            mListener.onItemClickListener(position);
        }
    }

    public interface ItemClickListener{
        void onItemClickListener(int position);
    }

    public void swapList(ArrayList<Transaction> mTransactions){
        if(transactions != null){
            transactions.clear();
            transactions.addAll(mTransactions);
        }
        else {
            transactions = mTransactions;
        }

        notifyDataSetChanged();
    }

}
