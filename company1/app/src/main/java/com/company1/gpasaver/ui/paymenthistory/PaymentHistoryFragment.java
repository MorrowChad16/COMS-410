package com.company1.gpasaver.ui.paymenthistory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.company1.gpasaver.R;

import java.util.List;

public class PaymentHistoryFragment extends Fragment {
    private PaymentHistoryAdapter adapter;
    private RecyclerView payment_history_list;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_payment_history, container, false);

        Log.d("PaymentHistoryFragment", "Created PaymentHistoryFragment view.");

        payment_history_list = root.findViewById(R.id.payment_history_list);
        adapter = new PaymentHistoryAdapter(root.getContext(), payment_history_list);

        payment_history_list.setAdapter(adapter);
        return root;
    }
}
