/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 * EMC Confidential: Restricted Internal Distribution
 */
package com.emc.ia.sdk.sip.ingestion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


/**
 * Client that interacts with an Archive.
 */
public interface ArchiveClient {

  /**
   * Configure the Archive client with options.
   * @param configuration Options that change the behavior of the client
   */
  void configure(Map<String, String> configuration);

  /**
   * Ingest a Submission Information Package (SIP) into the Archive.
   * @param sip The SIP to add to the Archive
   * @return The ID of the Archival Information Package (AIP) that was generated from the SIP
   * @throws IOException When an I/O error occurs
   */
  String ingest(InputStream sip) throws IOException;

}