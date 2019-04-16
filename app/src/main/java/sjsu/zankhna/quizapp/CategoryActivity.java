package sjsu.zankhna.quizapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sjsu.zankhna.quizapp.utils.Constants;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Context activityContext;
    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.btn_gk)
    Button generalKnowledge;
    @BindView(R.id.btn_books)
    Button books;
    @BindView(R.id.btn_science)
    Button science;
    @BindView(R.id.btn_computer)
    Button computers;
    @BindView(R.id.btn_sports)
    Button sports;
    @BindView(R.id.btn_geography)
    Button geography;
    @BindView(R.id.btn_history)
    Button history;
    @BindView(R.id.btn_music)
    Button music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        activityContext = CategoryActivity.this;
        unbinder = ButterKnife.bind(this);
        setUpToolbar();
        generalKnowledge.setOnClickListener(this);
        books.setOnClickListener(this);
        science.setOnClickListener(this);
        computers.setOnClickListener(this);
        sports.setOnClickListener(this);
        geography.setOnClickListener(this);
        history.setOnClickListener(this);
        music.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return false;
        }
        return false;
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText(getString(R.string.txt_select_category));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activityContext, QuizActivity.class);
        switch (v.getId()) {
            case R.id.btn_gk:
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, 9);
                startActivity(intent);
                break;
            case R.id.btn_books:
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, 10);
                startActivity(intent);
                break;
            case R.id.btn_science:
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, 17);
                startActivity(intent);
                break;
            case R.id.btn_computer:
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, 18);
                startActivity(intent);
                break;
            case R.id.btn_sports:
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, 21);
                startActivity(intent);
                break;
            case R.id.btn_geography:
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, 22);
                startActivity(intent);
                break;
            case R.id.btn_history:
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, 23);
                startActivity(intent);
                break;
            case R.id.btn_music:
                intent.putExtra(Constants.EXTRA_CATEGORY_ID, 12);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
