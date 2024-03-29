package com.ibatis.sqlmap;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.engine.cache.CacheKey;
import com.ibatis.common.exception.NestedRuntimeException;
import testdomain.Account;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheStatementTest extends BaseSqlMapTest {

  // SETUP & TEARDOWN

  protected void setUp() throws Exception {
    initSqlMap("com/ibatis/sqlmap/maps/SqlMapConfig.xml", null);
    initScript("scripts/account-init.sql");
  }

  protected void tearDown() throws Exception {
  }

  public void testMappedStatementQueryWithCache() throws SQLException {
    List list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);

    int firstId = System.identityHashCode(list);

    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);

    int secondId = System.identityHashCode(list);

    assertEquals(firstId, secondId);

    Account account = (Account) list.get(1);
    account.setEmailAddress("new.clinton@ibatis.com");
    sqlMap.update("updateAccountViaInlineParameters", account);

    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);

    int thirdId = System.identityHashCode(list);

    assertTrue(firstId != thirdId);

  }

  public void testFlushDataCache() throws SQLException {
    List list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int firstId = System.identityHashCode(list);
    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int secondId = System.identityHashCode(list);
    assertEquals(firstId, secondId);
    sqlMap.flushDataCache();
    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int thirdId = System.identityHashCode(list);
    assertTrue(firstId != thirdId);
  }

  public void testFlushDataCacheOnExecute() throws SQLException {
    List list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int firstId = System.identityHashCode(list);
    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int secondId = System.identityHashCode(list);
    assertEquals(firstId, secondId);
    sqlMap.update("updateAccountViaInlineParameters", list.get(0));
    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int thirdId = System.identityHashCode(list);
    assertTrue(firstId != thirdId);
  }

  public void testFlushDataCacheOnExecuteInBatch() throws SQLException {
    List list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int firstId = System.identityHashCode(list);
    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int secondId = System.identityHashCode(list);
    assertEquals(firstId, secondId);
    sqlMap.startBatch();
    sqlMap.update("updateAccountViaInlineParameters", list.get(0));
    sqlMap.executeBatch();
    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int thirdId = System.identityHashCode(list);
    assertTrue(firstId != thirdId);
  }

  public void testFlushDataCacheOnExecuteInBatchWithTx() throws SQLException {
    List list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int firstId = System.identityHashCode(list);
    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int secondId = System.identityHashCode(list);
    assertEquals(firstId, secondId);
    try {
      sqlMap.startTransaction();
      sqlMap.startBatch();
      sqlMap.update("updateAccountViaInlineParameters", list.get(0));
      sqlMap.executeBatch();
      sqlMap.commitTransaction();
    } finally {
      sqlMap.endTransaction();
    }
    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);
    int thirdId = System.identityHashCode(list);
    assertTrue(firstId != thirdId);
  }

  public void testMappedStatementQueryWithThreadedCache() throws SQLException {

    Map results = new HashMap();

    TestCacheThread.startThread(sqlMap, results, "getCachedAccountsViaResultMap");
    Integer firstId = (Integer) results.get("id");

    TestCacheThread.startThread(sqlMap, results, "getCachedAccountsViaResultMap");
    Integer secondId = (Integer) results.get("id");

    assertTrue(firstId.equals(secondId));

    List list = (List) results.get("list");

    Account account = (Account) list.get(1);
    account.setEmailAddress("new.clinton@ibatis.com");
    sqlMap.update("updateAccountViaInlineParameters", account);

    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);

    int thirdId = System.identityHashCode(list);

    assertTrue(firstId.intValue() != thirdId);

  }

  public void testMappedStatementQueryWithThreadedReadWriteCache() throws SQLException {

    Map results = new HashMap();

    TestCacheThread.startThread(sqlMap, results, "getRWCachedAccountsViaResultMap");
    Integer firstId = (Integer) results.get("id");

    TestCacheThread.startThread(sqlMap, results, "getRWCachedAccountsViaResultMap");
    Integer secondId = (Integer) results.get("id");

    assertFalse(firstId.equals(secondId));

    List list = (List) results.get("list");

    Account account = (Account) list.get(1);
    account.setEmailAddress("new.clinton@ibatis.com");
    sqlMap.update("updateAccountViaInlineParameters", account);

    list = sqlMap.queryForList("getCachedAccountsViaResultMap", null);

    int thirdId = System.identityHashCode(list);

    assertTrue(firstId.intValue() != thirdId);

  }

  public void testCacheKeyWithSameHashcode() {
    CacheKey key1 = new CacheKey();
    CacheKey key2 = new CacheKey();

    key1.update("HS1CS001");
    key2.update("HS1D4001");

    assertEquals("Expect same hashcode.", key1.hashCode(), key2.hashCode());
    assertFalse("Expect not equal", key1.equals(key2));
  }

  public void testCacheKeyWithTwoParamsSameHashcode() {
    CacheKey key1 = new CacheKey();
    CacheKey key2 = new CacheKey();

    key1.update("HS1CS001");
    key1.update("HS1D4001");

    key2.update("HS1D4001");
    key2.update("HS1CS001");

    assertEquals("Expect same hashcode.", key1.hashCode(), key2.hashCode());
    assertFalse("Expect not equal", key1.equals(key2));
  }

  private static class TestCacheThread extends Thread {
    private SqlMapClient sqlMap;
    private Map results;
    private String statementName;

    public TestCacheThread(SqlMapClient sqlMap, Map results, String statementName) {
      this.sqlMap = sqlMap;
      this.results = results;
      this.statementName = statementName;
    }

    public void run() {
      try {
        SqlMapSession session = sqlMap.openSession();
        List list = session.queryForList(statementName, null);
        int firstId = System.identityHashCode(list);
        list = session.queryForList(statementName, null);
        int secondId = System.identityHashCode(list);
        //assertEquals(firstId, secondId);
        results.put("id", new Integer(System.identityHashCode(list)));
        results.put("list", list);
        session.close();
      } catch (SQLException e) {
        throw new NestedRuntimeException("Error.  Cause: " + e);
      }
    }

    public static void startThread(SqlMapClient sqlMap, Map results, String statementName) {
      Thread t = new TestCacheThread(sqlMap, results, statementName);
      t.start();
      try {
        t.join();
      } catch (InterruptedException e) {
        throw new NestedRuntimeException("Error.  Cause: " + e);
      }
    }
  }


}
