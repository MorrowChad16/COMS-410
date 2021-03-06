// Generated by data binding compiler. Do not edit!
package com.company1.gpasaver.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.company1.gpasaver.R;
import com.company1.gpasaver.ui.home.UserViewModel;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemTransactionBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout itemTransaction;

  @NonNull
  public final TextView labelAmount;

  @NonNull
  public final TextView labelRecipient;

  @NonNull
  public final TextView labelTimestamp;

  @Bindable
  protected UserViewModel mUserViewModel;

  protected ItemTransactionBinding(Object _bindingComponent, View _root, int _localFieldCount,
      LinearLayout itemTransaction, TextView labelAmount, TextView labelRecipient,
      TextView labelTimestamp) {
    super(_bindingComponent, _root, _localFieldCount);
    this.itemTransaction = itemTransaction;
    this.labelAmount = labelAmount;
    this.labelRecipient = labelRecipient;
    this.labelTimestamp = labelTimestamp;
  }

  public abstract void setUserViewModel(@Nullable UserViewModel userViewModel);

  @Nullable
  public UserViewModel getUserViewModel() {
    return mUserViewModel;
  }

  @NonNull
  public static ItemTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_transaction, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemTransactionBinding>inflateInternal(inflater, R.layout.item_transaction, root, attachToRoot, component);
  }

  @NonNull
  public static ItemTransactionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_transaction, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemTransactionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemTransactionBinding>inflateInternal(inflater, R.layout.item_transaction, null, false, component);
  }

  public static ItemTransactionBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ItemTransactionBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemTransactionBinding)bind(component, view, R.layout.item_transaction);
  }
}
