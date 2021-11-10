package pmf.novak101.visitedplacesmapspractice;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pmf.novak101.visitedplacesmapspractice.shared.AdapterPlaces;
import pmf.novak101.visitedplacesmapspractice.shared.ListItemPlace;

public class MainActivity extends AppCompatActivity {

    ListView listViewMain;
    List<ListItemPlace> itemsMain = new ArrayList<ListItemPlace>();


    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle data = intent.getExtras();
                        itemsMain = data.getParcelableArrayList("items");
                        listViewMain.setAdapter(new AdapterPlaces(MainActivity.this, itemsMain));
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMain = findViewById(R.id.listViewMain);

        listViewMain.setAdapter(new AdapterPlaces(this, itemsMain));
        listViewMain.setOnItemClickListener((parent, view, position, id) -> {
            Log.i("IDIDIDIDIDIDID" , String.valueOf(id));Log.i("PSOSOSOSOSOSOSOSO" , String.valueOf(position));
            launchMap(position);
        });
    }

    public void openMaps(View view) {
        // Can add some other functionality when opening with + button
        launchMap(-1);
    }

    public void launchMap( int position){
        Intent mapsIntent= new Intent(this, MapsPlacesSelector.class);
        mapsIntent.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) itemsMain);
        if(position != -1){
            mapsIntent.putExtra("selected", itemsMain.get(position));
        }
        activityLauncher.launch(mapsIntent);
    }
}