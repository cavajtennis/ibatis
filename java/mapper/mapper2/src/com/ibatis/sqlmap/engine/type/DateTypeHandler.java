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
package com.ibatis.sqlmap.engine.type;

import com.ibatis.sqlmap.client.SqlMapException;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date (and time) implementation of TypeHandler
 */
public class DateTypeHandler extends BaseTypeHandler implements TypeHandler {

  private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss";
  private static final DateFormat format = new SimpleDateFormat(DATE_FORMAT);

  public void setParameter(PreparedStatement ps, int i, Object parameter, String jdbcType)
      throws SQLException {
    ps.setTimestamp(i, new java.sql.Timestamp(((Date) parameter).getTime()));
  }

  public Object getResult(ResultSet rs, String columnName)
      throws SQLException {
    java.sql.Timestamp sqlTimestamp = rs.getTimestamp(columnName);
    if (rs.wasNull()) {
      return null;
    } else {
      return new java.util.Date(sqlTimestamp.getTime());
    }
  }

  public Object getResult(ResultSet rs, int columnIndex)
      throws SQLException {
    java.sql.Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
    if (rs.wasNull()) {
      return null;
    } else {
      return new java.util.Date(sqlTimestamp.getTime());
    }
  }

  public Object getResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    java.sql.Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
    if (cs.wasNull()) {
      return null;
    } else {
      return new java.util.Date(sqlTimestamp.getTime());
    }
  }

  public Object valueOf(String s) {
    try {
      return format.parse(s);
    } catch (ParseException e) {
      throw new SqlMapException("Error parsing default null value date.  Format must be '" + DATE_FORMAT + "'. Cause: " + e);
    }
  }

}
