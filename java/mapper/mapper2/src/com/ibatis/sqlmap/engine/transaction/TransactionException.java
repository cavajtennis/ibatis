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
package com.ibatis.sqlmap.engine.transaction;

import com.ibatis.common.exception.NestedException;

public class TransactionException extends NestedException {

  public TransactionException() {
  }

  public TransactionException(String msg) {
    super(msg);
  }

  public TransactionException(Throwable cause) {
    super(cause);
  }

  public TransactionException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
