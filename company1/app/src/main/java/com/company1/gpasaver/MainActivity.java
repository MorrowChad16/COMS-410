package com.company1.gpasaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import com.company1.gpasaver.models.User;
import java.util.ArrayList;


/**
 * Inflates the Navigation drawer for navigation to fragments.
 * The Nav drawer is meant as a base for inflating all fragments.
 *
 * From Google's docs, they want a single activity inflating different fragments
 * for different functionality. Obviously this could get a bit more complex, so we can change it later if needed.
 */
public class MainActivity extends BaseActivity {
  private AppBarConfiguration mAppBarConfiguration;
  protected ArrayList<User> user = new ArrayList<>();
  protected String users_list = "http://10.0.2.2:8080/usersList";
  public static User main_user;
  @SuppressLint("StaticFieldLeak")
  public static MySingleton Mysig;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DrawerLayout drawer = findViewById(R.id.main);
    NavigationView navigationView = findViewById(R.id.nav_view);

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration = new AppBarConfiguration.Builder(
      R.id.nav_home, R.id.nav_profile, R.id.nav_share, R.id.nav_send, R.id.nav_requests, R.id.nav_settings)
      .setDrawerLayout(drawer)
      .build();

    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);

    MySingleton.mainUser = (User) getIntent ().getSerializableExtra ("serialize_tutor");
    try{
      if(MySingleton.mainUser.getIsTutor() == 1){
        navigationView.getMenu().findItem(R.id.nav_tutor_new).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_history).setVisible(false);
      } else{
        navigationView.getMenu().findItem(R.id.nav_ViewStudentActivity).setVisible(false);
      }
    } catch (Exception e){
      Log.e("MainActivity",e.toString());
    }

    MenuItem logout = navigationView.getMenu().findItem(R.id.nav_logout);
    logout.setOnMenuItemClickListener(menuItem -> {
      Toast.makeText(getApplicationContext(), "Logging out...", Toast.LENGTH_SHORT).show();
      MySingleton.mainUser = null;
      Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
      startActivity(intent);
      return true;
    });
  }

  public static User getUser() {
    return main_user;
  }

  public void setUser(User someVariable) {
    this.main_user = someVariable;
  }

  @Override protected int getActivityLayout() {
    return R.layout.activity_main;
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
      || super.onSupportNavigateUp();
  }
}
