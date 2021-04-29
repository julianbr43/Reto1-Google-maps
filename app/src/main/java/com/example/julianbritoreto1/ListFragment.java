package com.example.julianbritoreto1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListItemFragment extends Fragment implements  OnLocItemAction{

    private RecyclerView locItemViewList;
    private LinearLayoutManager layoutManager;
    private LocItemAdapter adpater;
    private OnChangeFragment observer;
    private AppModel model;


    public ListItemFragment() {
        adpater = new LocItemAdapter();
    }


    // TODO: Rename and change types and number of parameters
    public static ListItemFragment newInstance() {
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adpater.setItems(model.getItems());
        adpater.setObsever(this);
        View root = inflater.inflate(R.layout.fragment_list_item,container,false);
        locItemViewList = root.findViewById(R.id.locItemsViewList);
        locItemViewList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(root.getContext());
        locItemViewList.setLayoutManager(layoutManager);
        locItemViewList.setAdapter(adpater);

        return root;
    }


    public void setModel(AppModel model) {  this.model = model; }
    public void setObserver(OnChangeFragment observer) {
        this.observer = observer;
    }

    @Override
    public void onViewLocation(LocationItem item) {
        observer.requestFragment(R.id.mapLocation);
        model.setState(model.STATE_E_LOOKING);
        model.setShwItem(item);
    }
}