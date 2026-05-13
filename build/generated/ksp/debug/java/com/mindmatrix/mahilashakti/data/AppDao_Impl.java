package com.mindmatrix.mahilashakti.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDao_Impl implements AppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Member> __insertionAdapterOfMember;

  private final EntityInsertionAdapter<SavingsEntry> __insertionAdapterOfSavingsEntry;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<Loan> __insertionAdapterOfLoan;

  private final EntityDeletionOrUpdateAdapter<Loan> __updateAdapterOfLoan;

  public AppDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMember = new EntityInsertionAdapter<Member>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `members` (`id`,`name`,`phone`,`address`,`photoUri`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Member entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhone());
        statement.bindString(4, entity.getAddress());
        if (entity.getPhotoUri() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPhotoUri());
        }
      }
    };
    this.__insertionAdapterOfSavingsEntry = new EntityInsertionAdapter<SavingsEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `savings` (`id`,`memberId`,`amount`,`status`,`date`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsEntry entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMemberId());
        statement.bindDouble(3, entity.getAmount());
        final String _tmp = __converters.fromPaymentStatus(entity.getStatus());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getDate());
      }
    };
    this.__insertionAdapterOfLoan = new EntityInsertionAdapter<Loan>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `loans` (`id`,`memberId`,`amount`,`interestRate`,`dueDate`,`status`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Loan entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMemberId());
        statement.bindDouble(3, entity.getAmount());
        statement.bindDouble(4, entity.getInterestRate());
        statement.bindString(5, entity.getDueDate());
        final String _tmp = __converters.fromRepaymentStatus(entity.getStatus());
        statement.bindString(6, _tmp);
      }
    };
    this.__updateAdapterOfLoan = new EntityDeletionOrUpdateAdapter<Loan>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `loans` SET `id` = ?,`memberId` = ?,`amount` = ?,`interestRate` = ?,`dueDate` = ?,`status` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Loan entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMemberId());
        statement.bindDouble(3, entity.getAmount());
        statement.bindDouble(4, entity.getInterestRate());
        statement.bindString(5, entity.getDueDate());
        final String _tmp = __converters.fromRepaymentStatus(entity.getStatus());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public long insertMember(final Member member) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfMember.insertAndReturnId(member);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertSavings(final SavingsEntry entry) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfSavingsEntry.insertAndReturnId(entry);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public long insertLoan(final Loan loan) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfLoan.insertAndReturnId(loan);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateLoan(final Loan loan) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfLoan.handle(loan);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Member> getMembers() {
    final String _sql = "SELECT * FROM members ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
      final List<Member> _result = new ArrayList<Member>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Member _item;
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final String _tmpPhone;
        _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpPhotoUri;
        if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
          _tmpPhotoUri = null;
        } else {
          _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
        }
        _item = new Member(_tmpId,_tmpName,_tmpPhone,_tmpAddress,_tmpPhotoUri);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countMembers() {
    final String _sql = "SELECT COUNT(*) FROM members";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double totalPaidSavings() {
    final String _sql = "SELECT COALESCE(SUM(amount), 0) FROM savings WHERE status = 'PAID'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<MemberSavingsSummary> getSavingsSummaries() {
    final String _sql = "\n"
            + "        SELECT members.id AS memberId,\n"
            + "               members.name AS memberName,\n"
            + "               COALESCE(SUM(CASE WHEN savings.status = 'PAID' THEN savings.amount ELSE 0 END), 0) AS totalPaid,\n"
            + "               COALESCE(SUM(CASE WHEN savings.status = 'PENDING' THEN 1 ELSE 0 END), 0) AS pendingCount\n"
            + "        FROM members\n"
            + "        LEFT JOIN savings ON members.id = savings.memberId\n"
            + "        GROUP BY members.id, members.name\n"
            + "        ORDER BY members.name\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMemberId = 0;
      final int _cursorIndexOfMemberName = 1;
      final int _cursorIndexOfTotalPaid = 2;
      final int _cursorIndexOfPendingCount = 3;
      final List<MemberSavingsSummary> _result = new ArrayList<MemberSavingsSummary>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final MemberSavingsSummary _item;
        final long _tmpMemberId;
        _tmpMemberId = _cursor.getLong(_cursorIndexOfMemberId);
        final String _tmpMemberName;
        _tmpMemberName = _cursor.getString(_cursorIndexOfMemberName);
        final double _tmpTotalPaid;
        _tmpTotalPaid = _cursor.getDouble(_cursorIndexOfTotalPaid);
        final int _tmpPendingCount;
        _tmpPendingCount = _cursor.getInt(_cursorIndexOfPendingCount);
        _item = new MemberSavingsSummary(_tmpMemberId,_tmpMemberName,_tmpTotalPaid,_tmpPendingCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int activeLoanCount(final long memberId) {
    final String _sql = "SELECT COUNT(*) FROM loans WHERE memberId = ? AND status = 'ACTIVE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, memberId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int activeLoanCount() {
    final String _sql = "SELECT COUNT(*) FROM loans WHERE status = 'ACTIVE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public double totalActiveLoanDue() {
    final String _sql = "SELECT COALESCE(SUM(amount + (amount * interestRate / 100.0)), 0) FROM loans WHERE status = 'ACTIVE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final double _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getDouble(0);
      } else {
        _result = 0.0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<LoanWithMember> getLoansWithMembers() {
    final String _sql = "\n"
            + "        SELECT loans.id AS loanId,\n"
            + "               members.name AS memberName,\n"
            + "               loans.amount AS amount,\n"
            + "               loans.interestRate AS interestRate,\n"
            + "               loans.dueDate AS dueDate,\n"
            + "               loans.status AS status\n"
            + "        FROM loans\n"
            + "        INNER JOIN members ON members.id = loans.memberId\n"
            + "        ORDER BY loans.status, loans.dueDate\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfLoanId = 0;
      final int _cursorIndexOfMemberName = 1;
      final int _cursorIndexOfAmount = 2;
      final int _cursorIndexOfInterestRate = 3;
      final int _cursorIndexOfDueDate = 4;
      final int _cursorIndexOfStatus = 5;
      final List<LoanWithMember> _result = new ArrayList<LoanWithMember>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final LoanWithMember _item;
        final long _tmpLoanId;
        _tmpLoanId = _cursor.getLong(_cursorIndexOfLoanId);
        final String _tmpMemberName;
        _tmpMemberName = _cursor.getString(_cursorIndexOfMemberName);
        final double _tmpAmount;
        _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
        final double _tmpInterestRate;
        _tmpInterestRate = _cursor.getDouble(_cursorIndexOfInterestRate);
        final String _tmpDueDate;
        _tmpDueDate = _cursor.getString(_cursorIndexOfDueDate);
        final RepaymentStatus _tmpStatus;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfStatus);
        _tmpStatus = __converters.toRepaymentStatus(_tmp);
        _item = new LoanWithMember(_tmpLoanId,_tmpMemberName,_tmpAmount,_tmpInterestRate,_tmpDueDate,_tmpStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Loan getLoan(final long loanId) {
    final String _sql = "SELECT * FROM loans WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, loanId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "memberId");
      final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
      final int _cursorIndexOfInterestRate = CursorUtil.getColumnIndexOrThrow(_cursor, "interestRate");
      final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDate");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final Loan _result;
      if (_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final long _tmpMemberId;
        _tmpMemberId = _cursor.getLong(_cursorIndexOfMemberId);
        final double _tmpAmount;
        _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
        final double _tmpInterestRate;
        _tmpInterestRate = _cursor.getDouble(_cursorIndexOfInterestRate);
        final String _tmpDueDate;
        _tmpDueDate = _cursor.getString(_cursorIndexOfDueDate);
        final RepaymentStatus _tmpStatus;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfStatus);
        _tmpStatus = __converters.toRepaymentStatus(_tmp);
        _result = new Loan(_tmpId,_tmpMemberId,_tmpAmount,_tmpInterestRate,_tmpDueDate,_tmpStatus);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
