/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.client.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emc.ia.sdk.support.rest.LinkContainer;
import com.fasterxml.jackson.annotation.JsonProperty;


public class SearchResults extends LinkContainer {

  private static final String KEY = "results";
  private final List<SearchResult> results = new ArrayList<>();

  @JsonProperty("_embedded")
  protected void setEmbedded(Map<String, List<SearchResult>> embedded) {
    results.clear();
    List<SearchResult> embeddedItems = embedded.get(KEY);
    if (embeddedItems == null) {
      throw new IllegalArgumentException(String.format("Expected results under key '%s', but got keys %s", KEY,
          embedded.keySet()));
    }
    results.addAll(embeddedItems);
  }

  public List<SearchResult> getResults() {
    return results;
  }

  public void addResult(SearchResult searchResult) {
    results.add(searchResult);
  }

  public List<Row> getRows() {
    List<Row> rows = new ArrayList<>();
    for (SearchResult searchResult: results) {
      rows.addAll(searchResult.getRows());
    }
    return rows;
  }

}
