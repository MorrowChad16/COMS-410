package com.company1.gpasaver;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.company1.gpasaver.databinding.FragmentHomeBindingImpl;
import com.company1.gpasaver.databinding.ItemRequestBindingImpl;
import com.company1.gpasaver.databinding.ItemTransactionBindingImpl;
import com.company1.gpasaver.databinding.ItemTutorBindingImpl;
import com.company1.gpasaver.databinding.ItemUsersBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_FRAGMENTHOME = 1;

  private static final int LAYOUT_ITEMREQUEST = 2;

  private static final int LAYOUT_ITEMTRANSACTION = 3;

  private static final int LAYOUT_ITEMTUTOR = 4;

  private static final int LAYOUT_ITEMUSERS = 5;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(5);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.company1.gpasaver.R.layout.fragment_home, LAYOUT_FRAGMENTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.company1.gpasaver.R.layout.item_request, LAYOUT_ITEMREQUEST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.company1.gpasaver.R.layout.item_transaction, LAYOUT_ITEMTRANSACTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.company1.gpasaver.R.layout.item_tutor, LAYOUT_ITEMTUTOR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.company1.gpasaver.R.layout.item_users, LAYOUT_ITEMUSERS);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_FRAGMENTHOME: {
          if ("layout/fragment_home_0".equals(tag)) {
            return new FragmentHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMREQUEST: {
          if ("layout/item_request_0".equals(tag)) {
            return new ItemRequestBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_request is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMTRANSACTION: {
          if ("layout/item_transaction_0".equals(tag)) {
            return new ItemTransactionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_transaction is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMTUTOR: {
          if ("layout/item_tutor_0".equals(tag)) {
            return new ItemTutorBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_tutor is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMUSERS: {
          if ("layout/item_users_0".equals(tag)) {
            return new ItemUsersBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_users is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(3);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "mainViewModel");
      sKeys.put(2, "userViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(5);

    static {
      sKeys.put("layout/fragment_home_0", com.company1.gpasaver.R.layout.fragment_home);
      sKeys.put("layout/item_request_0", com.company1.gpasaver.R.layout.item_request);
      sKeys.put("layout/item_transaction_0", com.company1.gpasaver.R.layout.item_transaction);
      sKeys.put("layout/item_tutor_0", com.company1.gpasaver.R.layout.item_tutor);
      sKeys.put("layout/item_users_0", com.company1.gpasaver.R.layout.item_users);
    }
  }
}
