/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.client.dto;

import java.util.ArrayList;
import java.util.List;


public class Row {

  private String id;
  private List<Column> columns;

  public Row() {
    setColumns(new ArrayList<Column>());
  }

  public String getId() {
    return id;
  }

  public final void setId(String id) {
    this.id = id;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public final void setColumns(List<Column> columns) {
    this.columns = columns;
  }

}
