package ru.foobarbaz.notebook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import ru.foobarbaz.R;
import ru.foobarbaz.notebook.database.HelperFactory;
import ru.foobarbaz.notebook.model.Tag;

public class TagListActivity extends Activity {
    private ScrollView mainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_tag_list);

        Button newTag = (Button) findViewById(R.id.newTag);
        newTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tagIntent = new Intent(TagListActivity.this, TagActivity.class);
                startActivity(tagIntent);
            }
        });

        Button backToNotes = (Button) findViewById(R.id.backToNotes);
        backToNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagListActivity.this.finish();
            }
        });

        mainPage = (ScrollView) findViewById(R.id.scrollView);
        HelperFactory.setHelper(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAllTags();
    }

    private View.OnClickListener tagOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TagListActivity.this, TagActivity.class);
            intent.putExtra("tagId", Integer.toString(v.getId()));
            startActivity(intent);
        }
    };

    private void showAllTags() {
        mainPage.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (Tag tag : HelperFactory.getHelper().getTagDao().getAllTags()) {
            Button button = new Button(this);
            button.setId(tag.getId());
            button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            button.setOnClickListener(tagOnClickListener);
            button.setText(tag.toString());
            linearLayout.addView(button);
        }
        mainPage.addView(linearLayout);
    }
}
