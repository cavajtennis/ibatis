package com.ibatis.sqlmap;

import testdomain.Book;
import testdomain.Magazine;
import testdomain.Document;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cbegin
 * Date: May 14, 2005
 * Time: 1:19:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class DiscriminatorTest extends BaseSqlMapTest {

   protected void setUp() throws Exception {
    initSqlMap("com/ibatis/sqlmap/maps/SqlMapConfig.xml", null);
    initScript("scripts/docs-init.sql");
  }

  public void testDiscriminator () throws Exception {

    List list = sqlMap.queryForList("getDocuments",null);
    assertEquals(6, list.size());

    assertTrue (list.get(0) instanceof Book);
    assertTrue (list.get(1) instanceof Magazine);
    assertTrue (list.get(2) instanceof Book);
    assertTrue (list.get(3) instanceof Magazine);
    assertTrue (list.get(4) instanceof Document);
    assertTrue (list.get(5) instanceof Document);

    assertEquals(1, ((Document)list.get(0)).getId());
    assertEquals(2, ((Document)list.get(1)).getId());
    assertEquals(3, ((Document)list.get(2)).getId());
    assertEquals(4, ((Document)list.get(3)).getId());
    assertEquals(5, ((Document)list.get(4)).getId());
    assertEquals(6, ((Document)list.get(5)).getId());

    assertEquals(new Integer(55),((Book)list.get(0)).getPages());
    assertEquals("Lyon",((Magazine)list.get(1)).getCity());
    assertEquals(new Integer(3587),((Book)list.get(2)).getPages());
    assertEquals("Paris",((Magazine)list.get(3)).getCity());
  }


}
