package com.example.grofers_app;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grofers_app.adapter_holders.ProdectHomAdapter;
import com.example.grofers_app.adapter_holders.ResponseProdect;
import com.example.grofers_app.listners.FragmentCommunication;
import com.example.grofers_app.listners.OnListnerClick;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GroceryStaplesFragment extends Fragment implements OnListnerClick {
    private RecyclerView GroceryRecyclerView;
    private FragmentCommunication fragmentCommunication;
    private GroceryAdapter groceryAdapter;
    private GroferDiscountModel groferDiscountModel;
    private List<ResponseProdect> responseProductList = new ArrayList<>();


    public GroceryStaplesFragment() {

    }


    public static Fragment newInstance() {
        GroceryStaplesFragment groceryStaplesFragment = new GroceryStaplesFragment();
        return groceryStaplesFragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groferDiscountModel = new ViewModelProvider(this).get(GroferDiscountModel.class);

        View root = inflater.inflate(R.layout.fragment_grocery_staples, container, false);
        groferDiscountModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@NonNull String s) {

            }
        });
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        fetchResposeFromJsonAssets();
    }

    private void initView(View view) {
        GroceryRecyclerView = view.findViewById(R.id.FruitsRecycleView);


    }

    private void fetchResposeFromJsonAssets() {

        try {
            InputStream inputStream = getContext().getAssets().open("Response.json");
            int data = inputStream.read();
            StringBuffer stringBuffer = new StringBuffer();
            while (data != -1) {
                char ch = (char) data;
                stringBuffer.append(ch);
                data = inputStream.read();
            }
            buildPojoFromJson(stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setRecyclerAdapter() {
        groceryAdapter = new GroceryAdapter(responseProductList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        GroceryRecyclerView.setLayoutManager(layoutManager);
        GroceryRecyclerView.setAdapter(groceryAdapter);
    }


    private void buildPojoFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ResponseModel>>() {
        }.getType();
        responseProductList = gson.fromJson(json, type);
        setRecyclerAdapter();
        groceryAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentCommunication= (FragmentCommunication) context;
    }



    @Override
    public void sendDataToDetails(ResponseProdect responseProdect, int position) {
//            Bundle bundle= new Bundle();
//            bundle.putString("name",responseProdect.getTitle());
//            bundle.putString("Url",responseProdect.getImage());
//            bundle.putString("selling",responseProdect.getSellingPrice());
//            bundle.putString("Mrp",responseProdect.getProductMRP());
//            bundle.putString("unit",responseProdect.getUnit());
//            bundle.putString("des",responseProdect.getDescription());
//            fragmentCommunication.commincation(bundle);

    }

    @Override
    public void sendToCart(ResponseProdect responseProdect, int position) {

    }
}









