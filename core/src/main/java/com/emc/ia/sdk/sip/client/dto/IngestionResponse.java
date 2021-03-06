/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.sip.client.dto;

public class IngestionResponse extends NamedLinkContainer {

  private String aipId;

  public String getAipId() {
    return aipId;
  }

  public void setAipId(String aipId) {
    this.aipId = aipId;
  }

  @Override
  public String toString() {
    return String.format("aipId=%s; %s", aipId, super.toString());
  }

}
