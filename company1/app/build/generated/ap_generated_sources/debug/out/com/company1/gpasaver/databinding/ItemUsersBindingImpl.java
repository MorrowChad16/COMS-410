package com.company1.gpasaver.databinding;
import com.company1.gpasaver.R;
import com.company1.gpasaver.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemUsersBindingImpl extends ItemUsersBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    // variables
    // values
    // listeners
    private OnClickListenerImpl mUserViewModelOnItemClickAndroidViewViewOnClickListener;
    // Inverse Binding Event Handlers

    public ItemUsersBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private ItemUsersBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.imagePeople.setTag(null);
        this.itemPeople.setTag(null);
        this.labelMail.setTag(null);
        this.labelName.setTag(null);
        this.labelPhone.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.userViewModel == variableId) {
            setUserViewModel((com.company1.gpasaver.ui.home.UserViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setUserViewModel(@Nullable com.company1.gpasaver.ui.home.UserViewModel UserViewModel) {
        updateRegistration(0, UserViewModel);
        this.mUserViewModel = UserViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.userViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeUserViewModel((com.company1.gpasaver.ui.home.UserViewModel) object, fieldId);
        }
        return false;
    }
    private boolean onChangeUserViewModel(com.company1.gpasaver.ui.home.UserViewModel UserViewModel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.company1.gpasaver.ui.home.UserViewModel userViewModel = mUserViewModel;
        android.view.View.OnClickListener userViewModelOnItemClickAndroidViewViewOnClickListener = null;
        java.lang.String userViewModelPictureProfile = null;
        java.lang.String userViewModelPhoneNumber = null;
        java.lang.String userViewModelMail = null;
        java.lang.String userViewModelFullName = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (userViewModel != null) {
                    // read userViewModel::onItemClick
                    userViewModelOnItemClickAndroidViewViewOnClickListener = (((mUserViewModelOnItemClickAndroidViewViewOnClickListener == null) ? (mUserViewModelOnItemClickAndroidViewViewOnClickListener = new OnClickListenerImpl()) : mUserViewModelOnItemClickAndroidViewViewOnClickListener).setValue(userViewModel));
                    // read userViewModel.pictureProfile
                    userViewModelPictureProfile = userViewModel.getPictureProfile();
                    // read userViewModel.phoneNumber
                    userViewModelPhoneNumber = userViewModel.getPhoneNumber();
                    // read userViewModel.mail
                    userViewModelMail = userViewModel.getMail();
                    // read userViewModel.fullName
                    userViewModelFullName = userViewModel.getFullName();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.company1.gpasaver.ui.profile.ProfileViewModel.loadImage(this.imagePeople, userViewModelPictureProfile);
            this.itemPeople.setOnClickListener(userViewModelOnItemClickAndroidViewViewOnClickListener);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.labelMail, userViewModelMail);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.labelName, userViewModelFullName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.labelPhone, userViewModelPhoneNumber);
        }
    }
    // Listener Stub Implementations
    public static class OnClickListenerImpl implements android.view.View.OnClickListener{
        private com.company1.gpasaver.ui.home.UserViewModel value;
        public OnClickListenerImpl setValue(com.company1.gpasaver.ui.home.UserViewModel value) {
            this.value = value;
            return value == null ? null : this;
        }
        @Override
        public void onClick(android.view.View arg0) {
            this.value.onItemClick(arg0); 
        }
    }
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): userViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}