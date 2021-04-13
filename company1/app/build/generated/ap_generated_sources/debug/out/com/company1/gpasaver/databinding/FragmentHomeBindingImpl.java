package com.company1.gpasaver.databinding;
import com.company1.gpasaver.R;
import com.company1.gpasaver.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHomeBindingImpl extends FragmentHomeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentHomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private FragmentHomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (android.widget.TextView) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[3]
            , (android.widget.ProgressBar) bindings[1]
            );
        this.labelStatus.setTag(null);
        this.listPeople.setTag(null);
        this.mboundView0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.progressPeople.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x20L;
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
        if (BR.mainViewModel == variableId) {
            setMainViewModel((com.company1.gpasaver.ui.home.HomeViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMainViewModel(@Nullable com.company1.gpasaver.ui.home.HomeViewModel MainViewModel) {
        this.mMainViewModel = MainViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.mainViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeMainViewModelPeopleProgress((androidx.databinding.ObservableInt) object, fieldId);
            case 1 :
                return onChangeMainViewModelPeopleLabel((androidx.databinding.ObservableInt) object, fieldId);
            case 2 :
                return onChangeMainViewModelPeopleRecycler((androidx.databinding.ObservableInt) object, fieldId);
            case 3 :
                return onChangeMainViewModelMessageLabel((androidx.databinding.ObservableField<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeMainViewModelPeopleProgress(androidx.databinding.ObservableInt MainViewModelPeopleProgress, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMainViewModelPeopleLabel(androidx.databinding.ObservableInt MainViewModelPeopleLabel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMainViewModelPeopleRecycler(androidx.databinding.ObservableInt MainViewModelPeopleRecycler, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMainViewModelMessageLabel(androidx.databinding.ObservableField<java.lang.String> MainViewModelMessageLabel, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
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
        com.company1.gpasaver.ui.home.HomeViewModel mainViewModel = mMainViewModel;
        androidx.databinding.ObservableInt mainViewModelPeopleProgress = null;
        int mainViewModelPeopleLabelGet = 0;
        androidx.databinding.ObservableInt mainViewModelPeopleLabel = null;
        int mainViewModelPeopleProgressGet = 0;
        androidx.databinding.ObservableInt mainViewModelPeopleRecycler = null;
        androidx.databinding.ObservableField<java.lang.String> mainViewModelMessageLabel = null;
        int mainViewModelPeopleRecyclerGet = 0;
        java.lang.String mainViewModelMessageLabelGet = null;

        if ((dirtyFlags & 0x3fL) != 0) {


            if ((dirtyFlags & 0x31L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.peopleProgress
                        mainViewModelPeopleProgress = mainViewModel.peopleProgress;
                    }
                    updateRegistration(0, mainViewModelPeopleProgress);


                    if (mainViewModelPeopleProgress != null) {
                        // read mainViewModel.peopleProgress.get()
                        mainViewModelPeopleProgressGet = mainViewModelPeopleProgress.get();
                    }
            }
            if ((dirtyFlags & 0x32L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.peopleLabel
                        mainViewModelPeopleLabel = mainViewModel.peopleLabel;
                    }
                    updateRegistration(1, mainViewModelPeopleLabel);


                    if (mainViewModelPeopleLabel != null) {
                        // read mainViewModel.peopleLabel.get()
                        mainViewModelPeopleLabelGet = mainViewModelPeopleLabel.get();
                    }
            }
            if ((dirtyFlags & 0x34L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.peopleRecycler
                        mainViewModelPeopleRecycler = mainViewModel.peopleRecycler;
                    }
                    updateRegistration(2, mainViewModelPeopleRecycler);


                    if (mainViewModelPeopleRecycler != null) {
                        // read mainViewModel.peopleRecycler.get()
                        mainViewModelPeopleRecyclerGet = mainViewModelPeopleRecycler.get();
                    }
            }
            if ((dirtyFlags & 0x38L) != 0) {

                    if (mainViewModel != null) {
                        // read mainViewModel.messageLabel
                        mainViewModelMessageLabel = mainViewModel.messageLabel;
                    }
                    updateRegistration(3, mainViewModelMessageLabel);


                    if (mainViewModelMessageLabel != null) {
                        // read mainViewModel.messageLabel.get()
                        mainViewModelMessageLabelGet = mainViewModelMessageLabel.get();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x38L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.labelStatus, mainViewModelMessageLabelGet);
        }
        if ((dirtyFlags & 0x32L) != 0) {
            // api target 1

            this.labelStatus.setVisibility(mainViewModelPeopleLabelGet);
        }
        if ((dirtyFlags & 0x34L) != 0) {
            // api target 1

            this.listPeople.setVisibility(mainViewModelPeopleRecyclerGet);
        }
        if ((dirtyFlags & 0x31L) != 0) {
            // api target 1

            this.progressPeople.setVisibility(mainViewModelPeopleProgressGet);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): mainViewModel.peopleProgress
        flag 1 (0x2L): mainViewModel.peopleLabel
        flag 2 (0x3L): mainViewModel.peopleRecycler
        flag 3 (0x4L): mainViewModel.messageLabel
        flag 4 (0x5L): mainViewModel
        flag 5 (0x6L): null
    flag mapping end*/
    //end
}