package com.company1.gpasaver.ui.requests;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.company1.gpasaver.MySingleton;
import com.company1.gpasaver.R;
import com.company1.gpasaver.models.User;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestViewHolder> {
    private List<Request> requests;
    private Context ctx;
    private final String REQUEST_REQ_URL = "http://coms-510-01.cs.iastate.edu:80/get_requests.php";
    private final String ACCEPT_REQ_URL = "http://coms-510-01.cs.iastate.edu:80/accept_request.php";
    private final String REJECT_REQ_URL = "http://coms-510-01.cs.iastate.edu:80/reject_request.php";
    private final String TAG = "RequestAdapter";
    private RecyclerView parent;

    private class Request {
        private String name, imgurl;
        private int user_id;

        public Request(String name, String imgurl, int user_id) {
            this.name = name;
            this.imgurl = imgurl;
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return imgurl;
        }

        public void setImage(String url) {
            this.imgurl = url;
        }

        public int getUserId() {
            return user_id;
        }

        public void setUserId(int user_id) {
            this.user_id = user_id;
        }
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public ImageView imageView;
        public LinearLayout layout;
        public Button btnAccept, btnReject;

        /**
         * Instantiated for each list element in the RecyclerView.
         * @param v LinearLayout which contains the transaction TextViews
         */
        public RequestViewHolder(LinearLayout v) {
            super(v);
            layout = v;
            nameView = v.findViewById(R.id.label_name);
            imageView = v.findViewById(R.id.img_requester);
            btnAccept = v.findViewById(R.id.btn_accept);
            btnReject = v.findViewById(R.id.btn_reject);
        }
    }

    /**
     * Initializes the request model with a "loading" message.
     *
     * @param ctx Adapter context.
     * @param parent Associated RecyclerView.
     */
    public RequestsAdapter(Context ctx, RecyclerView parent) {
        this.ctx = ctx;
        this.parent = parent;
        requests = new ArrayList<>();

        Log.d(TAG, "Initialized empty PaymentHistoryAdapter.");

        fetchRequests();
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);

        RequestViewHolder vh = new RequestViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RequestViewHolder vh, int position) {
        vh.nameView.setText(requests.get(position).getName());
        Picasso.get().load(requests.get(position).getImage()).into(vh.imageView);

        JSONObject req_body = new JSONObject();

        try {
            req_body.put("tutor_id", MySingleton.getInstance(ctx).mainUser.getId());
            req_body.put("user_id", requests.get(position).getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        vh.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Reject the request, and update the list */
                Log.d(TAG, "Rejecting " + requests.get(position).getName());

                StringRequest objRequest = new StringRequest
                        (com.android.volley.Request.Method.POST, REJECT_REQ_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String resp) {
                                Log.d(TAG, "Received JSON response from server: " + resp);
                                Toast.makeText(ctx, "Rejected " + requests.get(position).getName(), Toast.LENGTH_SHORT).show();
                                fetchRequests();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                /* Display error message */
                                Toast.makeText(ctx, "Error rejecting request", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Error in request: " + error.toString());
                            }
                        }) {
                    /* Override methods in StringRequest to send a body with the request. */
                    @Override
                    public byte[] getBody() {
                        return req_body.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };

                MySingleton.getInstance(ctx).addToRequestQueue(objRequest);
            }
        });

        vh.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Accept the request, and update the list */
                Log.d(TAG, "Accepting " + requests.get(position).getName());

                StringRequest objRequest = new StringRequest
                        (com.android.volley.Request.Method.POST, ACCEPT_REQ_URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String resp) {
                                fetchRequests();
                                Log.d(TAG, "Received JSON response from server: " + resp);
                                Toast.makeText(ctx, "Accepted " + requests.get(position).getName(), Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                /* Display error message */
                                Toast.makeText(ctx, "Error rejecting request", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Error in request: " + error.toString());
                            }
                        }) {
                    /* Override methods in StringRequest to send a body with the request. */
                    @Override
                    public byte[] getBody() {
                        return req_body.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };

                MySingleton.getInstance(ctx).addToRequestQueue(objRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    /**
     * Fetches the tutor's available requests from the backend and loads them
     * into the view model.
     *
     * Expects a JSON response from the backend of the form:
     *
     * [
     *     ["John Smith"],
     * ]
     */
    private void fetchRequests() {
        JSONObject request = new JSONObject();
        User current_user = MySingleton.getInstance(ctx).mainUser;

        Log.d(TAG, "Fetching requests for userid " + current_user.getId() + "..");

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
                (com.android.volley.Request.Method.POST, REQUEST_REQ_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        requests.clear();
                        Log.d(TAG, "Received JSON response from server: " + resp);

                        try {
                            JSONArray response = new JSONArray(resp);

                            /* Parse transactions from response */
                            for (int i = 0; i < response.length(); ++i) {
                                JSONObject entry = response.getJSONObject(i);

                                Log.d(TAG, "entry: " + entry.toString());

                                requests.add(new com.company1.gpasaver.ui.requests.RequestsAdapter.Request(entry.getString("name"), entry.getString("imgurl"), entry.getInt("user_id")));
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
                        Toast.makeText(ctx, "Error getting requests", Toast.LENGTH_SHORT).show();
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
        Log.d(TAG, "Sent JSON request to destination:" + REQUEST_REQ_URL);
    }

    /**
     * Loads 4 test requests into the view model.
     */
    private void loadTestRequests() {
        requests.clear();

        for (int i = 0; i < 4; ++i) {
            requests.add(new Request("Recipient " + i, "none", 0));
        }

        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });

        Log.d(TAG, "Loaded test requests.");
    }
}