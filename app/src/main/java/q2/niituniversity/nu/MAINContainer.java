package q2.niituniversity.nu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MAINContainer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MAINSFragmentContacts.MAINSFragmentContactsFragmentInteractionListener,
        MAINUserProfile.MAINUserProfileFragmentInteractionListener, MAINWFragmentGMS.MAINWFragmentGMSFragmentInteractionListener,
        MAINSFragmentLMS.MAINSFragmentLMSFragmentInteractionListener, MAINSFragmentGPS.MAINSFragmentGPSFragmentInteractionListener{

    MenuItem mPreviousMenuItem;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maincontainer_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Getting user type from the main data.
        userType = MAINAppData.userType;

        MAINUserProfile mainUserProfile = new MAINUserProfile();
        getSupportFragmentManager().beginTransaction().add(R.id.contentFragment, mainUserProfile).commit();

        if(MAINAppData.userType.equals("STUDENT")){

        }else{

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        expandNavigationView(userType);

        View nav_header = navigationView.inflateHeaderView(R.layout.maincontainer_navigation_drawer_header);
        ImageView userImage = (ImageView) nav_header.findViewById(R.id.userImage);

        TextView userName = (TextView) nav_header.findViewById(R.id.userName);
        userName.setText(MAINAppData.userName);
        TextView userType = (TextView) nav_header.findViewById(R.id.userType);
        userType.setText(MAINAppData.userType);
    }

    private void expandNavigationView(String userType) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.clear();
        String [] menu_list;
        if (userType.equals("STUDENT")) {
            menu_list = new String[] {"GatePass Management System","Library Management System", "Important Contacts"};
            for (int i = 0, menu_listLength = menu_list.length; i < menu_listLength; i++) {
                String aMenu_list = menu_list[i];
                menu.add(R.id.group_menu, i, Menu.NONE, aMenu_list).setIcon(R.mipmap.ic_launcher);
            }
        }
        else if (userType.equals("WARDEN")) {
            menu_list = new String[] {"GatePass Management System"};
            for (int i = 0, menu_listLength = menu_list.length; i < menu_listLength; i++) {
                String aMenu_list = menu_list[i];
                menu.add(R.id.group_menu, i, Menu.NONE, aMenu_list).setIcon(R.mipmap.ic_launcher);
            }
        }
        menu.setGroupCheckable(R.id.group_menu, false, true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent a = new Intent(Intent.ACTION_MAIN);
                            a.addCategory(Intent.CATEGORY_HOME);
                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(a);
                        }
                    }).create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem mItem) {
        if (mPreviousMenuItem != null) {
            mPreviousMenuItem.setChecked(false);
            if (mPreviousMenuItem == mItem)
                return true;
        }
        mPreviousMenuItem = mItem;
        Fragment fragment = null;
        Class fragmentClass;

        // Handle navigation view item clicks here.
        int id = mItem.getItemId();

        /** Add cases for student block
         *  0 = Gate Pass Management System
         *  1 = Library Management System
         *  2 = Important Contacts
         **/

        if (userType.equals("STUDENT")) {
            switch (id) {
                case 0:
                    fragmentClass = MAINSFragmentGPS.class;
                    break;
                case 1:
                    fragmentClass = MAINSFragmentLMS.class;
                    break;
                case 2:
                    fragmentClass = MAINSFragmentContacts.class;
                    break;
                default:
                    fragmentClass = MAINUserProfile.class;
                    break;
            }
        } else {
            /** Add cases for student block
             *  0 = Gate Pass Management System
             */
            switch (id) {
                case 0:
                    fragmentClass = MAINWFragmentGMS.class;
                    break;
                default:
                    fragmentClass = MAINUserProfile.class;
                    break;
            }
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        mItem.setChecked(true);
        setTitle(mItem.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMAINUserProfileFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMAINWFragmentGMSFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMAINSFragmentContactsFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMAINSFragmentGPSFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMAINSFragmentLMSFragmentInteraction(Uri uri) {

    }
}
