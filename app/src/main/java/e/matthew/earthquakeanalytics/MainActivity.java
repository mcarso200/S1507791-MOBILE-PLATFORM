package e.matthew.earthquakeanalytics;
// Name                MATTHEW CARSON
// Student ID           S1507791
// Programme of Study   Computing
//
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity
{
    private String result;
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private ArrayList<EarthQuake> earthquakes;
    private ArrayList<EarthQuake> sendequakes;
    private boolean isItem;
    private DrawerLayout drawerLayout;
    final Bundle bundle = new Bundle();
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar nav= (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(nav);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ArrayList<EarthQuake> earthquakes = null;
        startProgress();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_search:
                                Fragment home = new Home_Fragment();
                                bundle.putParcelableArrayList("equake", sendequakes);
                                home.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, home, null)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case R.id.nav_stats:
                                Fragment stats = new Statistics();
                                bundle.putParcelableArrayList("equake", sendequakes);
                                stats.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, stats, null)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case R.id.nav_map:
                                Fragment map = new Map();
                                bundle.putParcelableArrayList("equake", sendequakes);
                                map.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, map, null)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                             case R.id.AdvancedSearch:
                                   Fragment adv = new advancedsearch();
                                 bundle.putParcelableArrayList("equake", sendequakes);
                                 adv.setArguments(bundle);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, adv, null)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                        }
                        return true;
                    }
                });

    }
    public void setequakes (ArrayList<EarthQuake> data) {

        this.sendequakes = data;
    }

    public void setuphome () {
        bundle.putParcelableArrayList("equake", sendequakes);
        Fragment   home =  new  Home_Fragment();
        home.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home).commit();
        navigationView.getMenu().getItem(0).setChecked(true);

}
    public static void setSnackBar(View root, String snackTitle) {
      final  Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void startProgress()
    {
        new Thread(new Task(urlSource)).start();
    }

    private  ArrayList<EarthQuake> parseXml(String xml) {
        EarthQuake earthquake = null;
       ArrayList<EarthQuake>  earthquakes = null;
       try
       {
           xml = xml.substring(4);
           XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
           factory.setNamespaceAware(true);
           XmlPullParser pullparse = factory.newPullParser();
           StringReader reader = new StringReader(xml);
           pullparse.setInput( reader);
           int eventType = pullparse.getEventType();
           isItem = false;
           while (eventType != XmlPullParser.END_DOCUMENT) {
               if(eventType == XmlPullParser.START_TAG) {
                   if(pullparse.getName().equalsIgnoreCase("channel"))
                   {
                       earthquakes  = new ArrayList<EarthQuake>();
                   } else
                   if (pullparse.getName().equalsIgnoreCase("item"))
                   {
                       earthquake = new EarthQuake();
                       isItem = true;
                   }
                     else
                       if (pullparse.getName().equalsIgnoreCase("description") && isItem == true) {

                         String description = pullparse.nextText();

                           String[] parsed = description.split(";");
                           String[] dtparsed = parsed[0].split(":",2);
                           String[] lparsed = parsed[1].split(":");
                           String[] latlonparsed = parsed[2].split(":");
                           String[] dparsed = parsed[3].split(":");
                           String[] mparsed = parsed[4].split(":");
                           String[] kparsed = dparsed[1].split("km");
                           String[] latndlon = latlonparsed[1].split(",");

                           Double lat = Double.parseDouble(latndlon[0].trim());
                           Double lon = Double.parseDouble(latndlon[1].trim());
                           int depth = Integer.parseInt(kparsed[0].trim());
                           Double mag = Double.parseDouble(mparsed[1].trim());
                           String loc = lparsed[1].trim();
                           String dateTime = dtparsed[1].trim();

                           earthquake.setGeolat(lat);
                           earthquake.setGeolong(lon);
                           earthquake.setDateTime(dateTime);
                           earthquake.setLocation(loc);
                           earthquake.setDepth(depth);
                           earthquake.setMagnitude(mag);
                   }
                       }
               else
               if(eventType == XmlPullParser.END_TAG) {
                   if (pullparse.getName().equalsIgnoreCase("item")) {
                       earthquakes.add(earthquake);
                    }
                   else
                   if (pullparse.getName().equalsIgnoreCase("channel"))
                   {
                       int size;
                       size = earthquakes.size();
                       Log.e("MyTag","There are this many earthquakes: " + size);
                   }
               }
               eventType = pullparse.next();
           }
       }
       catch (XmlPullParserException ae1)
       {
           Log.e("MyTag","Parsing error" + ae1.toString());
       }
       catch (IOException ae1)
       {
           Log.e("MyTag","IO error during parsing");
       }
       return earthquakes;
    }

    private class Task implements Runnable {
        private String url;
        public Task(String aurl) {
            url = aurl;
        }
        @Override
        public void run() {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            try {

                aurl = new URL(url);
                yc = aurl.openConnection();
                yc.setConnectTimeout(5000);
                yc.setReadTimeout(10000);
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                }
                in.close();
            } catch (IOException ae) {
                LinearLayout lin = findViewById(R.id.linear);
                setSnackBar(lin,"No internet Connection");

            }
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    if (result == null) {
                    } else {
                        earthquakes = parseXml(result);
                        if (earthquakes != null) {
                            setequakes(earthquakes);
                            setuphome();
                        } else {
                        }
                    }
                }
            });
        }
    }
}


