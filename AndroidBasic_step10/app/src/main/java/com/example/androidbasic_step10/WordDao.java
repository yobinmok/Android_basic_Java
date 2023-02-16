package com.example.androidbasic_step10;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // 기본 제공 annotation 을 사용하면 SQL 필요 없음!
    void insert(Word word);

    @Query("DELETE FROM word_table") // 기본 제공 annotation 이 없을 경우 직접 SQL문 작성성
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();

    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();

    @Delete
    void deleteWord(Word word);

    @Update
    void update(Word... word);
}
