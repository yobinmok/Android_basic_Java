package com.example.androidbasic_step10;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbasic_step10.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_UPDATE_WORD = "extra_word_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        // observer -> 데이터 관찰하며 변경 있을 시 onChanged 실행
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(words);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word myWord = adapter.getWordAtPosition(position);
                Toast.makeText(MainActivity.this, "Deleting " + myWord.getWord(), Toast.LENGTH_LONG).show();

                // Delete the word
                mWordViewModel.deleteWord(myWord);
            }
        });
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new WordListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Word word = adapter.getWordAtPosition(position);
                launchUpdateWordActivity(word);
            }
        });
    }

    private void launchUpdateWordActivity(Word word) {
        Intent intent = new Intent(this, NewWordActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWord());
        intent.putExtra(EXTRA_DATA_ID, word.getId());
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String word_data = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NewWordActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                mWordViewModel.updateWord(new Word(id, word_data));
            } else {
                Toast.makeText(this, "업데이트할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            mWordViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}