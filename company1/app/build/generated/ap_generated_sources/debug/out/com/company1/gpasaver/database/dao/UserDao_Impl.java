package com.company1.gpasaver.database.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.company1.gpasaver.models.User;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllUsers;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`userid`,`firstname`,`picture_string`,`GPA`,`rate`,`balance`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getId());
        if (value.firstName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.firstName);
        }
        if (value.picture_string == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.picture_string);
        }
        if (value.GPA == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.GPA);
        }
        if (value.rate == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.rate);
        }
        stmt.bindDouble(6, value.balance);
      }
    };
    this.__preparedStmtOfDeleteAllUsers = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Users";
        return _query;
      }
    };
  }

  @Override
  public void insertUser(final User user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllUsers() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllUsers.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllUsers.release(_stmt);
    }
  }

  @Override
  public User getUser() {
    final String _sql = "SELECT * FROM users LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "userid");
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstname");
      final int _cursorIndexOfPictureString = CursorUtil.getColumnIndexOrThrow(_cursor, "picture_string");
      final int _cursorIndexOfGPA = CursorUtil.getColumnIndexOrThrow(_cursor, "GPA");
      final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
      final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
      final User _result;
      if(_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpFirstName;
        _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        _result = new User(_tmpId,_tmpFirstName);
        _result.picture_string = _cursor.getString(_cursorIndexOfPictureString);
        _result.GPA = _cursor.getString(_cursorIndexOfGPA);
        _result.rate = _cursor.getString(_cursorIndexOfRate);
        _result.balance = _cursor.getDouble(_cursorIndexOfBalance);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
