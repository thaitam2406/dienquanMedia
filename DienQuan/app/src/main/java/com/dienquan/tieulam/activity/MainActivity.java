package com.dienquan.tieulam.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.leftorright.lor.LorRApplication;
import com.leftorright.lor.R;
import com.leftorright.lor.activity.presenter.MainActivityPresenter;
import com.leftorright.lor.activity.presenter.MainActivityPresenterImpl;
import com.leftorright.lor.activity.views.MainActivityView;
import com.leftorright.lor.customizeView.FooterView;
import com.leftorright.lor.eventBus.MessageEvent;
import com.leftorright.lor.fragment.ApplicationFragmentManager;
import com.leftorright.lor.fragment.login.LoginActivity;
import com.leftorright.lor.fragment.postcard.PostCardFragment;
import com.leftorright.lor.fragment.postcard.PostCardView;
import com.leftorright.lor.pushNotify.RegistrationIntentService;
import com.leftorright.lor.serverAPI.models.cardDetail.CardItem;
import com.leftorright.lor.util.AppContants;
import com.leftorright.lor.util.SharedPrefHelper;
import com.leftorright.lor.util.Util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivityCompat implements ApplicationFragmentManager.OnUpdateToolbarListener
        , MainActivityView, PostCardView.IPostCardView {

    /**/
    @Bind(R.id.footer)
    FooterView mFooter;
    @Bind(R.id.fragment_container)
    FrameLayout mFragContainer;
    @Bind(R.id.titleToolBar)
    TextView tvTitleToolBar;
    Toolbar toolbar;
    View oldButton, currButton;
    int oldId = 1, currId = 1;
    boolean isExitApp = false;

    String mPushScreen;


    /**
     * on Search click listener
     */
    View.OnClickListener onSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Util.showToast(getApplication(), "Search click");
            startActivityForResult(new Intent(MainActivity.this, SearchImageActivity.class), 0);
        }
    };


    /**
     * set Back Button ToolBar Listener
     */
    View.OnClickListener onBackButtToolbar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getSupportFragmentManager().popBackStack();
        }
    };
    private ApplicationFragmentManager fragmentManager;
    private boolean isEnableChangeTab = true;
    CountDownTimer countDownTimer = new CountDownTimer(500, 500) {

        public void onTick(long millisUntilFinished) {
        }

        public void onFinish() {
            isEnableChangeTab = true;
        }
    };
    FooterView.FooterListener mFooterLitener = new FooterView.FooterListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onFooterClicked(View button) {
            if (isEnableChangeTab) {
                countDownTimer.start();
                isEnableChangeTab = false;
                switch (button.getId()) {
                    case R.id.foot_btn_top20:
                        if (currId != 1) {
                            setIdIdentify(button, 1);
                            handleSetButtonAction(1);
                            fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_TOP20);
                        }
                        break;
                    case R.id.foot_btn_male:
                        if (currId != 2) {
                            setIdIdentify(button, 2);
                            handleSetButtonAction(2);
                            fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_MALE);
                        }
                        break;
                    case R.id.foot_btn_posting:
                        if (currId != 3) {
                            votingClicked(button);
                        }
                        break;
                    case R.id.foot_btn_female:
                        if (currId != 4) {
                            setIdIdentify(button, 4);
                            handleSetButtonAction(4);
                            fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_FEMALE);
                        }
                        break;
                    case R.id.foot_btn_profile:
                        if (currId != 5) {
                            setIdIdentify(button, 5);
                            handleSetButtonAction(5);


                        }
                        break;
                }
            }
        }
    };
    private MainActivityPresenter mainActivityPresenter;

    private void goToProfile() {
        if (SharedPrefHelper.getUserType(getActivity()) != AppContants.USER_TYPE_ANONYMOUS)
            startActivityForResult(new Intent(getActivity(), ProfileActivity.class), AppContants.REQUEST_LOGIN_TO_PROFILE);
        else {
            SharedPrefHelper.setUser(getActivity(), AppContants.USER_TYPE_NULL, "", "");
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, AppContants.REQUEST_LOGIN_TO_PROFILE);
        }
    }

    private void votingClicked(View button) {
        if (button.getId() == R.id.foot_btn_posting)
            switch (SharedPrefHelper.getUserType(getActivity())) {
                case AppContants.USER_TYPE_LOGGED_IN:
                    setIconToolbar(3);
                    setIdIdentify(button, 3);
                    fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_POSTCARD);
                    break;
                case AppContants.USER_TYPE_ANONYMOUS:
                case AppContants.USER_TYPE_NULL:
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getString(R.string.title))
                            .setMessage(getString(R.string.check_login_content))
                            .setPositiveButton(
                                    getString(R.string.check_login_pos_btn),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            goToProfile();
                                        }
                                    })
                            .setNegativeButton(
                                    getString(R.string.check_login_nav_btn),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }
                            )
                            .show();
                    break;
            }
    }

    private void setIdIdentify(View button, int pos) {
        oldButton = currButton;
        if (oldButton == null)
            oldButton = mFooter.getVotingBtn();
        currButton = button;
        oldId = currId;
        currId = pos;
    }


    @SuppressLint("NewApi")
    private void handleSetButtonAction(int i) {
        ImageButton img = (ImageButton) currButton;
        switch (i) {
            case 1:
                img.setImageResource(R.drawable.ic_20_active);
                break;
            case 2:
                img.setImageResource(R.drawable.ic_boy_active);
                break;
            case 3:
                img.setImageResource(R.drawable.add_btn);
                break;
            case 4:
                img.setImageResource(R.drawable.ic_girl_active);
                break;
            case 5:
                img.setImageResource(R.drawable.ic_event);
                break;
        }
        resetDeactiveBtn();
        setIconToolbar(i);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void resetDeactiveBtn() {
        ImageButton img = (ImageButton) oldButton;
        switch (oldId) {
            case 1:
                img.setImageResource(R.drawable.ic_20);
                break;
            case 2:
                img.setImageResource(R.drawable.ic_boy);
                break;
            case 3:
                img.setImageResource(R.drawable.add_btn);
                break;
            case 4:
                img.setImageResource(R.drawable.ic_girl);
                break;
            case 5:
                img.setImageResource(R.drawable.ic_event);
                break;
        }
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        initToolBar();
//        configTransparentStatusBar();
    }

    @Override
    public void initData() {
        initialize();
        mFooter.setFooterListener(mFooterLitener);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onConfigurationChanged() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPushScreen = getIntent().getStringExtra(AppContants.IKEY_GCM_PUSHDATA);
    }

    /**
     * init first variable
     */
    private void initialize() {
        ButterKnife.bind(this);
        this.fragmentManager = new ApplicationFragmentManager(this, getSupportFragmentManager());
        fragmentManager.setOnUpdateToolbarListener(this);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (SharedPrefHelper.getUserType(this) == AppContants.USER_TYPE_NULL) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, AppContants.REQUEST_LOGIN);
        } else {
            registerGCM();
            handlePushCode();
        }

        //init presenter
        Activity activity = this;
        mainActivityPresenter = new MainActivityPresenterImpl(activity, this);

        logHashKey();
    }

    private void logHashKey() {
        Log.e("hashkey", "init");
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.android.lor", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (currId == 3 && oldButton != null) {
            oldButton.performClick();
        } else {
            if (!isExitApp) {
                Util.showToast(getApplication(), getString(R.string.text_exit_app));
                isExitApp = true;
            } else {
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                goToProfile();
                return true;
            case R.id.action_bell:
//                handleRightIconToolBarClick();
                startActivity(new Intent(getActivity(), ShareActivity.class));

                break;
            case R.id.menu_logout:
                LoginManager.getInstance().logOut();
                SharedPrefHelper.onRemoveKey(getActivity(), SharedPrefHelper.KEY_USER_ID);
                LorRApplication.Instance().setUserId("");
                SharedPrefHelper.setUser(this, AppContants.USER_TYPE_NULL, "", "");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, AppContants.REQUEST_LOGIN);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleRightIconToolBarClick() {
        if (fragmentManager.getCurFragment().equals(ApplicationFragmentManager.BOTTOM_TAB_POSTCARD)) {
            setIconToolbar(oldId);
            getSupportFragmentManager().popBackStack();
        } else {
            Util.showToast(getApplication(), "Bell click");
        }
    }

    @Override
    public void UpdateToolbar(String tag, String title, String subTitle) {
        //check show/hide footer
        if (mainActivityPresenter != null) {
            mainActivityPresenter.checkShowFooter(tag);
            mainActivityPresenter.checkShowHeader(tag);
        }
        if (tvTitleToolBar != null) {
            tvTitleToolBar.setText(title);
            tvTitleToolBar.setTextColor(getResources().getColor(R.color.white));
        }
    }


    /**
     * create Toolbar
     */
    private void initToolBar() {
        // Adding Toolbar to Main screen
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        showProfileIcon();
        setSupportActionBar(toolbar);
    }

    @Override
    public void showProfileIcon() {
        toolbar.setNavigationIcon(R.drawable.ic_profile);
    }

    /**
     * Hide/Show icon toolBar depend on which tab selected
     *
     * @param i
     */
    private void setIconToolbar(int i) {
        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();

        enableSetting(false);
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
                //disable search temporary
                break;
            case 3:
                setIconRight(R.drawable.ic_clear_white);
                break;
            case 7:
                enableSetting(true);
                break;
            case 6:
                showBackButtToolbar();
                setIconRight(0);
                break;
        }
    }

    void enableSetting(boolean isEnableSetting) {
        Menu menu = toolbar.getMenu();
        if (isEnableSetting) {
            menu.findItem(R.id.action_bell).setVisible(false);
            menu.findItem(R.id.action_setting).setVisible(true);
        } else {
            menu.findItem(R.id.action_bell).setVisible(true);
            menu.findItem(R.id.action_setting).setVisible(false);

        }
    }

    private void setIconRight(int id) {
        Menu menu = toolbar.getMenu();
        MenuItem menuItemSearch = menu.findItem(R.id.action_bell);
        if (id != 0) {
            menuItemSearch.setIcon(id);
            menuItemSearch.setVisible(true);
        } else {
            menuItemSearch.setVisible(false);
        }
    }

    @Override
    public void showHideBottomBar(boolean isShow) {
        if (isShow) {
            mFooter.setVisibility(View.VISIBLE);
        } else {
            mFooter.setVisibility(View.GONE);
        }
    }

    @Override
    public void chooseImageFromGalleryLeft() {
        mainActivityPresenter.chooseImageFromGallery(true);
    }

    @Override
    public void chooseImageFromGalleryRight() {
        mainActivityPresenter.chooseImageFromGallery(false);
    }

    @Override
    public void searchImage(boolean isLeft) {
        startActivityForResult(
                (new Intent(this, SearchImageActivity.class))
                        .putExtra(AppContants.IKEY_SIDE, isLeft),
                AppContants.REQUEST_SEARCH);
    }

    @Override
    public void takeImage(boolean isLeft) {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                isLeft ? AppContants.REQUEST_CAM_LEFT : AppContants.REQUEST_CAM_RIGHT);
    }

    @Override
    public void postDone() {
        fragmentManager.getmFragmentManager().popBackStack();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int id = requestCode;
        boolean isLelf = (id == AppContants.PICK_PHOTO_LEFT) ? true : false;

        if (requestCode == AppContants.REQUEST_LOGIN_TO_PROFILE) {
            switch (SharedPrefHelper.getUserType(getActivity())) {
                case AppContants.USER_TYPE_LOGGED_IN:
                case AppContants.USER_TYPE_ANONYMOUS:
                    mFooterLitener.onFooterClicked(findViewById(R.id.foot_btn_top20));
                    break;
                case AppContants.USER_TYPE_NULL:
                    finish();
                    break;
            }
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppContants.REQUEST_LOGIN:
                    if (SharedPrefHelper.getUserType(getActivity()) == AppContants.USER_TYPE_LOGGED_IN) {
                        registerGCM();
                    }
                    fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_TOP20);
                    break;
                case AppContants.PICK_PHOTO_LEFT:
                case AppContants.PICK_PHOTO_RIGHT:
                    if (data != null) {
                        if (data.getData() != null) {
                            Uri imageUri = data.getData();
                            try {
                                Bitmap bm = Util.getBitmap(imageUri, this);
                                setPathImage(isLelf, bm);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return;
                    }
                case AppContants.REQUEST_SEARCH:
                    try {
                        Uri uri = Uri.parse(data.getStringExtra(AppContants.IKEY_IMG));
                        boolean isLeft = data.getBooleanExtra(AppContants.IKEY_SIDE, true);
                        setPathImage(isLeft, Util.getBitmap(uri, getBaseContext()));
                    } catch (Exception ex) {
                        Util.log("SEARCH_IMG", ex.toString());
                    }
                    break;
                case AppContants.REQUEST_CAM_LEFT:
                    setPathImage(true, (Bitmap) data.getExtras().get("data"));
                    break;
                case AppContants.REQUEST_CAM_RIGHT:
                    setPathImage(false, (Bitmap) data.getExtras().get("data"));
                    break;
            }
        } else
            finish();
    }

    private void handlePushCode() {
        if (mPushScreen != null && !mPushScreen.equals("")) {
            switch (mPushScreen) {
                case AppContants.GCM_PUSH_TOP20:
                    fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_TOP20);
                    break;
                case AppContants.GCM_PUSH_MALE:
                    fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_MALE);
                    break;
                case AppContants.GCM_PUSH_FEMALE:
                    fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_FEMALE);
                    break;
            }
            mPushScreen = "";
        } else
            fragmentManager.addFragment(ApplicationFragmentManager.BOTTOM_TAB_TOP20);
    }

    private void setPathImage(boolean isLeft, Bitmap bm) {
        Fragment fragment = fragmentManager.findFragmentById(ApplicationFragmentManager.BOTTOM_TAB_POSTCARD);
        if (fragment != null && fragment instanceof PostCardFragment) {
            ((PostCardFragment) fragment).setPathImage(isLeft, bm);
        }
    }

    private void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void onEventMainThread(CardItem cardItem) {
        fragmentManager.addFragment(ApplicationFragmentManager.COMMENT_FRAGMENT, cardItem);
        setIconToolbar(6);
    }

    public void onEventMainThread(MessageEvent messageEvent) {
        if(messageEvent.getMessage().equals("createQuestion")){
            mFooter.getmPostingBtn().callOnClick();
        }
    }

    /**
     * show button Back on Left ToolBar
     */
    private void showBackButtToolbar() {
        toolbar.setNavigationOnClickListener(onBackButtToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow_white);
    }

    //gcm
    public void registerGCM() {
        // Start IntentService to register this application with GCM.
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
       /* LocalBroadcastManager.getInstance(this).registerReceiver(mPushNotifyReceiver,
                new IntentFilter(AppContants.IKEY_REGISTRATION_GCM_COMPLETE));*/

    }
    /*private BroadcastReceiver mPushNotifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!Checker.isEmptyString(intent.getStringExtra(AppConstants.IKEY_REGISTRATION_GCM_REGID)))
                sendRegistrationToServer(intent.getStringExtra(AppConstants.IKEY_REGISTRATION_GCM_REGID));
        }
    };*/
}
