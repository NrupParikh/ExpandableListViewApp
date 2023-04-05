package com.nrup.expandablerecyclerviewdemo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nrup.expandablerecyclerviewdemo.ConstantManager;
import com.nrup.expandablerecyclerviewdemo.R;
import com.nrup.expandablerecyclerviewdemo.model.DataItem;
import com.nrup.expandablerecyclerviewdemo.model.SubCategoryItem;
import com.nrup.expandablerecyclerviewdemo.ui.adapter.MyCategoriesExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ExpandableListView lvCategory;

    ArrayList<DataItem> arCategory;
    ArrayList<SubCategoryItem> arSubCategory;

    ArrayList<HashMap<String, String>> parentItems;
    ArrayList<ArrayList<HashMap<String, String>>> childItems;

    MyCategoriesExpandableListAdapter myCategoriesExpandableListAdapter;

    View includedLayout;
    TextView tvParent;
    TextView tvChild;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupReferences();
    }

    private void setupReferences() {

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);

        tvParent = findViewById(R.id.parent);
        tvChild = findViewById(R.id.child);

        lvCategory = findViewById(R.id.lvEp);

        arCategory = new ArrayList<>();
        arSubCategory = new ArrayList<>();

        parentItems = new ArrayList<>();
        childItems = new ArrayList<>();

        includedLayout = (View) findViewById(R.id.content);
        includedLayout.findViewById(R.id.parent);
        includedLayout.findViewById(R.id.child);

        // ----------------------- 1
        DataItem dataItem = new DataItem();
        dataItem.setCategoryId("1");
        dataItem.setCategoryName("Adventure");

        arSubCategory = new ArrayList<>();
        for (int i = 1; i < 6; i++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(i));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName("Adventure: " + i);
            arSubCategory.add(subCategoryItem);
        }
        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);

        // ----------------------- 2
        dataItem = new DataItem();
        dataItem.setCategoryId("2");
        dataItem.setCategoryName("Art");
        arSubCategory = new ArrayList<>();
        for (int j = 1; j < 6; j++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(j));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName("Art: " + j);
            arSubCategory.add(subCategoryItem);
        }
        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);

        // ----------------------- 3
        dataItem = new DataItem();
        dataItem.setCategoryId("3");
        dataItem.setCategoryName("Cooking");
        arSubCategory = new ArrayList<>();
        for (int k = 1; k < 6; k++) {

            SubCategoryItem subCategoryItem = new SubCategoryItem();
            subCategoryItem.setCategoryId(String.valueOf(k));
            subCategoryItem.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            subCategoryItem.setSubCategoryName("Cooking: " + k);
            arSubCategory.add(subCategoryItem);
        }

        dataItem.setSubCategory(arSubCategory);
        arCategory.add(dataItem);

        Log.d("TAG", "setupReferences: " + arCategory.size());

        for (DataItem data : arCategory) {
            ArrayList<HashMap<String, String>> childArrayList = new ArrayList<>();
            HashMap<String, String> mapParent = new HashMap<>();

            mapParent.put(ConstantManager.Parameter.CATEGORY_ID, data.getCategoryId());
            mapParent.put(ConstantManager.Parameter.CATEGORY_NAME, data.getCategoryName());

            int countIsChecked = 0;
            for (SubCategoryItem subCategoryItem : data.getSubCategory()) {

                HashMap<String, String> mapChild = new HashMap<>();
                mapChild.put(ConstantManager.Parameter.SUB_ID, subCategoryItem.getSubId());
                mapChild.put(ConstantManager.Parameter.SUB_CATEGORY_NAME, subCategoryItem.getSubCategoryName());
                mapChild.put(ConstantManager.Parameter.CATEGORY_ID, subCategoryItem.getCategoryId());
                mapChild.put(ConstantManager.Parameter.IS_CHECKED, subCategoryItem.getIsChecked());

                if (subCategoryItem.getIsChecked().equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {

                    countIsChecked++;
                }
                childArrayList.add(mapChild);
            }

            if (countIsChecked == data.getSubCategory().size()) {

                data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_TRUE);
            } else {
                data.setIsChecked(ConstantManager.CHECK_BOX_CHECKED_FALSE);
            }

            mapParent.put(ConstantManager.Parameter.IS_CHECKED, data.getIsChecked());
            childItems.add(childArrayList);
            parentItems.add(mapParent);

        }

        ConstantManager.parentItems = parentItems;
        ConstantManager.childItems = childItems;

        myCategoriesExpandableListAdapter = new MyCategoriesExpandableListAdapter(this, parentItems, childItems);
        lvCategory.setAdapter(myCategoriesExpandableListAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            showResult();
        }
    }

    @SuppressLint("SetTextI18n")
    private void showResult() {

        includedLayout.setVisibility(View.VISIBLE);

        for (int i = 0; i < myCategoriesExpandableListAdapter.getParentItems().size(); i++) {

            String isChecked = myCategoriesExpandableListAdapter.getParentItems().get(i).get(ConstantManager.Parameter.IS_CHECKED);

            if (Objects.requireNonNull(isChecked).equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                tvParent.setText(tvParent.getText() + myCategoriesExpandableListAdapter.getParentItems().get(i).get(ConstantManager.Parameter.CATEGORY_NAME));
            }

            for (int j = 0; j < myCategoriesExpandableListAdapter.getChildItems().get(i).size(); j++) {

                String isChildChecked = myCategoriesExpandableListAdapter.getChildItems().get(i).get(j).get(ConstantManager.Parameter.IS_CHECKED);

                assert isChildChecked != null;
                if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE)) {
                    tvChild.setText(tvChild.getText() + " , " + myCategoriesExpandableListAdapter.getParentItems().get(i).get(ConstantManager.Parameter.CATEGORY_NAME) + " " + (j + 1));
                }

            }

        }
    }
}