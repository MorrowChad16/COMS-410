package com.company1.gpasaver.ui.paymenthistory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.company1.gpasaver.MySingleton;
import com.company1.gpasaver.R;
import com.company1.gpasaver.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.TransactionViewHolder> {
    private List<Transaction> transactions;
    private Context ctx;
    private final String HISTORY_REQ_URL = "http://coms-510-01.cs.iastate.edu:80/get_payments.php";
    private final String TAG = "PaymentHistoryAdapter";
    private RecyclerView parent;

    private class Transaction {
        private String recipient;
        private float amount;
        private String timestamp;

        public Transaction(String recipient, float amount, String timestamp) {
            this.recipient = recipient;
            this.amount = amount;
            this.timestamp = timestamp;
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String toString() {
            return amount + " sent to " + recipient + " on " + timestamp;
        }
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        public TextView amountView;
        public TextView recipientView;
        public TextView timestampView;
        public LinearLayout layout;

        /**
         * Instantiated for each list element in the RecyclerView.
         * @param v LinearLayout which contains the transaction TextViews
         */
        public TransactionViewHolder(LinearLayout v) {
            super(v);
            layout = v;
            amountView = v.findViewById(R.id.label_amount);
            recipientView = v.findViewById(R.id.label_recipient);
            timestampView = v.findViewById(R.id.label_timestamp);
        }
    }

    /**
     * Initializes the payment history model with a "loading" message.
     *
     * @param ctx Adapter context.
     * @param parent Associated RecyclerView.
     */
    public PaymentHistoryAdapter(Context ctx, RecyclerView parent) {
        this.ctx = ctx;
        this.parent = parent;
        transactions = new ArrayList<>();

        Log.d(TAG, "Initialized empty PaymentHistoryAdapter.");

        fetchPayments();
    }

    @Override
    public PaymentHistoryAdapter.TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);

        TransactionViewHolder vh = new TransactionViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder vh, int position) {
        vh.amountView.setText("$" + transactions.get(position).getAmount());
        vh.recipientView.setText(transactions.get(position).getRecipient());
        vh.timestampView.setText(transactions.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    /**
     * Fetches the user's payment history from the backend and loads
     * the transactions into the view model.
     *
     * Sends a JSON request with a single numeric attribute 'id' containing the user's ID.
     * Expects a response of the form:
     *
     * [
     *     {
     *         "amount": 10.23,
     *         "recipient", "John Smith",
     *         "timestamp": "2020-10-11",
     *     },
     *     {... more transactions ...},
     * ]
     */
    private void fetchPayments() {
        JSONObject request = new JSONObject();
        User current_user = MySingleton.getInstance(ctx).mainUser;

        Log.d(TAG, "Fetching payments for userid " + current_user.getId() + "..");

        try {
            request.put("id", current_user.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
         * JsonObjectRequest doesn't allow the server to respond with a raw json array..
         * a workaround is to use a customized StringRequest and parse the response directly.
         */
        StringRequest objRequest = new StringRequest
            (Request.Method.POST, HISTORY_REQ_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String resp) {
                    transactions.clear();
                    Log.d(TAG, "Received JSON response from server: " + resp);

                    try {
                        JSONArray response = new JSONArray(resp);

                        /* Parse transactions from response */
                        for (int i = 0; i < response.length(); ++i) {
                            JSONObject entry = response.getJSONObject(i);

                            transactions.add(new Transaction(
                                    entry.getString("recipient"),
                                    (float) entry.getDouble("amount"),
                                    entry.getString("timestamp")));
                        }

                        /* Update recyclerview with new data */
                        parent.post(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    /* Display error message */
                    Toast.makeText(ctx, "Error getting payment history", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Error in request: " + error.toString());
                }
            }) {
            /* Override methods in StringRequest to send a body with the request. */
                @Override
                public byte[] getBody() {
                    return request.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };

        MySingleton.getInstance(ctx).addToRequestQueue(objRequest);
        Log.d(TAG, "Sent JSON request to destination:" + HISTORY_REQ_URL);
    }

    /**
     * Loads 16 test payments into the view model.
     */
    private void loadTestPayments() {
        transactions.clear();

        for (int i = 0; i < 16; ++i) {
             transactions.add(new Transaction("Recipient " + i, i, "Today"));
        }
        
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

        Log.d(TAG, "Loaded test payments.");
    }
}
