package com.example.shan.onlineorderapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.core.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Navigation_Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtCurrUser;
    public SessionManager session;
    TextView txtname;
    TextView txtemail;
    String name;
    String email;
    private RequestDBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_home_black_24dp);
        toolbar.setTitle(R.string.nav_title);
        setSupportActionBar(toolbar);
        TextView txtFooter = (TextView) findViewById(R.id.txtfooter);
        txtCurrUser = (TextView) findViewById(R.id.txtCurUser);
        txtFooter.setText(mobileConfig.footer);
        RequestDBHelper DB = new RequestDBHelper(getBaseContext());
        txtCurrUser.setText("WELCOME " + DB.GetCurrentUser());

        txtname=(TextView)findViewById(R.id.nav_name);
        txtemail=(TextView)findViewById(R.id.nav_email) ;




        /* Create and customize RecyclerView. */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] cardTitles = new String[]{"PRODUCT MENU","VIEW MAPS","TALK TO US","ABOUT US"};
        Integer[] cardImage = new Integer[]{R.drawable.supermarket,R.drawable.map1,R.drawable.talk,R.drawable.about};
        String[]cardDescription = new String[]{"click to shop with us","get directions","make suggestions","our valves"};

        // Add 8 cards
        MyHomeAdapter adapter = new MyHomeAdapter(cardTitles,cardDescription,cardImage);
        recyclerView.setAdapter(adapter);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //ImageView PRODUCT_MENU = (ImageView) findViewById(R.id.productmenu_button);



    }

/*
    // SqLite database handler
      db = new RequestDBHelper(getApplicationContext());

    // session manager
    session = new SessionManager(getApplicationContext());

    if (!session.isLoggedIn()) {
        logoutUser();
    }

    // Fetching user details from sqlite
    HashMap<String, String> user = db.getUserDetails();

    name = user.get("name");
    email = user.get("email");

    // Displaying the user details on the screen
    txtname.setText(name);
    txtemail.setText(email);


*/
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Online Order Customer Register");
        // Set up the input

        builder.setMessage("You sure you want to logout?");
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Launching the login activity
                Intent intent = new Intent(Navigation_Drawer.this, LoginActivity1.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_cart){

            Intent myIntent = new Intent(getBaseContext(), Shopping_cart.class);
            startActivity(myIntent);
            return true;
        }
        else if (id == R.id.action_about) {
            Intent myIntent = new Intent(getBaseContext(), AboutActivity.class);
            startActivity(myIntent);
            return true;
        }else if (id == R.id.action_addcustomer) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Online Order Customer Service");
            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            input.setHint("Enter Name:");
            builder.setView(input);
            builder.setMessage("Set Device Username:");
            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String curUser="";
                    curUser = input.getText().toString();
                    RequestDBHelper DB = new RequestDBHelper(getBaseContext());
                    DB.UpdateUser(curUser.toUpperCase());
                    Toast.makeText(getBaseContext(), "User is  " + curUser.toUpperCase(), Toast.LENGTH_LONG).show();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_category) {
            // Handle the category action
            Intent loadCategoryActivity = new Intent(Navigation_Drawer.this, CategoryActivity.class);
            Navigation_Drawer.this.startActivity(loadCategoryActivity);


        } else if (id == R.id.nav_location) {

            Intent loadLoadActivity = new Intent(Navigation_Drawer.this, MapsActivity.class);
            Navigation_Drawer.this.startActivity(loadLoadActivity);
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_call) {

            Intent loadCallActivity = new Intent(Navigation_Drawer.this, Contact_UsActivity.class);
            Navigation_Drawer.this.startActivity(loadCallActivity);


        }
        else if (id == R.id.nav_send) {

            Intent loadMessageActivity = new Intent(Navigation_Drawer.this, Send_MessageActivity.class);
            Navigation_Drawer.this.startActivity(loadMessageActivity);

        }else if (id ==R.id.logout){
            logoutUser();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* Create RecylcerView Adapter. */
    public static class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.ViewHolder> {
        private String[] mytitledataset;
        private Integer[] my_image_dataset;
        private String[] mydesc_dataset;
        //Context
        private static Context context;
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public TextView title;
            public ImageView image;
            public TextView description;

            public ViewHolder(View v) {
                super(v);
                context = v.getContext();
                view = v;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                   public void onClick(View view) {
                        if (title.getText().toString() == "PRODUCT MENU") {
                            view.getContext().startActivity(new Intent(view.getContext(),MainActivity.class));
                        }

                        if (title.getText().toString() == "VIEW MAPS") {
                            view.getContext().startActivity(new Intent(view.getContext(),MapsActivity.class));
                        }

                        if (title.getText().toString() == "TALK TO US") {
                            view.getContext().startActivity(new Intent(view.getContext(),Suggestion.class));
                        }
                        if (title.getText().toString() == "ABOUT US") {
                            view.getContext().startActivity(new Intent(view.getContext(),About_Us.class));
                        }


                    }
                });
                title = (TextView) v.findViewById(R.id.my_home_card_title);
                image = (ImageView) v.findViewById(R.id.home_image);
                description = (TextView) v.findViewById(R.id.home_description);
            }
        }

        public MyHomeAdapter(String[]title,String[]description,Integer[]image) {
            mytitledataset = title ;
            my_image_dataset = image;
            mydesc_dataset = description;


        }

        @Override
        public MyHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View cardview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_cards_mofel, parent, false);
            return new ViewHolder(cardview);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText(mytitledataset[position].toString());
            holder.image.setImageResource(my_image_dataset[position]);
            holder.description.setText(mydesc_dataset[position].toString());

        }

        @Override
        public int getItemCount() {
            return mytitledataset.length;
        }
    }

}
