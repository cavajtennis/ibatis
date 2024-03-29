/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.ibatis.db.dao.jdbc;

import com.ibatis.db.dao.DaoException;
import com.ibatis.db.dao.DaoTransaction;
import com.ibatis.sqlmap.client.SqlMapClient;

import java.sql.SQLException;

public class SqlMap2DaoTransaction implements DaoTransaction {


  private SqlMapClient client;

  public SqlMap2DaoTransaction(SqlMapClient sqlMap) {
    this.client = sqlMap;
  }

  public void commit() throws DaoException {
    try {
      client.commitTransaction();
      client.endTransaction();
    } catch (SQLException e) {
      throw new DaoException("Error committing transaction. Cause: " + e, e);
    }
  }

  public void rollback() throws DaoException {
    try {
      client.endTransaction();
    } catch (SQLException e) {
      throw new DaoException("Error rolling back transaction. Cause: " + e, e);
    }
  }

  public void release() throws DaoException {
    // No implementation required.
  }

  public SqlMapClient getSqlMapClient() {
    return client;
  }

}
