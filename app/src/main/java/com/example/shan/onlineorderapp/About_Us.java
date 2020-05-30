package com.example.shan.onlineorderapp;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class About_Us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] aboutUsTitles = new String[]{"MISSION","VISION","VALUES","GOALS"};
        String[] aboutUsDescription = new String[]{
                "Researchers at the MD Anderson medical center in Houston a decade ago used\n neural networks to detect cancer. Their out-of-sample results were reasonably good, \nthough worse than training, which is typical. They supposed that longer training of the \nnetworks would prove it after all that’s the way it works for doctors and were astonished to find that running the neural network for a week led to only slightly better \ntraining results and much worse evaluation results.  Therefore, in this case the mistake comes in a way that people only focus on\n training which brings less results in return after a long period than performing than the real data mining process. ",

                "Researchers at the MD Anderson medical center in Houston a decade ago used\n" +
                " neural networks to detect cancer. Their out-of-sample results were reasonably good, \n" +
                "though worse than training, which is typical. They supposed that longer training of the \n" +
                "networks would prove it after all that’s the way it works for doctors and were astonished to find that running the neural network for a week led to only slightly better \n" +
                "training results and much worse evaluation results.  Therefore, in this case the mistake comes in a way that people only focus on\n" +
                " training which brings less results in return after a long period than performing than the real data mining process. ",

                "Researchers at the MD Anderson medical center in Houston a decade ago used\n" +
                " neural networks to detect cancer. Their out-of-sample results were reasonably good, \n" +
                "though worse than training, which is typical. They supposed that longer training of the \n" +
                "networks would prove it after all that’s the way it works for doctors and were astonished to find that running the neural network for a week led to only slightly better \n" +
                "training results and much worse evaluation results.  Therefore, in this case the mistake comes in a way that people only focus on\n" +
                " training which brings less results in return after a long period than performing than the real data mining process. ",

                "Researchers at the MD Anderson medical center in Houston a decade ago used\n" +
                " neural networks to detect cancer. Their out-of-sample results were reasonably good, \n" +
                "though worse than training, which is typical. They supposed that longer training of the \n" +
                "networks would prove it after all that’s the way it works for doctors and were astonished to find that running the neural network for a week led to only slightly better \n" +
                "training results and much worse evaluation results.  Therefore, in this case the mistake comes in a way that people only focus on\n" +
                " training which brings less results in return after a long period than performing than the real data mining process. \n"};

      /* Create and customize RecyclerView. */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Connecting the recycleview to the Adapter
        AboutUsAdapter adapter = new AboutUsAdapter(aboutUsTitles,aboutUsDescription);
        recyclerView.setAdapter(adapter);

    }

    /* Create RecylcerView Adapter. */
    public static class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {
        private String[] mDataset;
        private String[] descriptionDataset;
        //Context
        private static Context context;
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public TextView title;
            public TextView description;

            public ViewHolder(View v) {
                super(v);
                context = v.getContext();
                view = v;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Intent intent = new Intent(context , EventDetailsActivity.class);
                        //context.startActivity(intent);
                    }
                });
                title = (TextView) v.findViewById(R.id.about_us_title);
                description = (TextView) v.findViewById(R.id.about_us_description);

            }
        }

        public AboutUsAdapter(String[] myDataset,String[] mydescription) {
            mDataset = myDataset;
            descriptionDataset=mydescription;
        }

        @Override
        public AboutUsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View cardview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.about_us_card, parent, false);
            return new ViewHolder(cardview);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText("Product Title " + (position + 1));
            holder.description.setText(descriptionDataset[position].toString());
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }


}
