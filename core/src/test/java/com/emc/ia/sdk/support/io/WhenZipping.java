/*
 * Copyright (c) 2016 EMC Corporation. All Rights Reserved.
 */
package com.emc.ia.sdk.support.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.emc.ia.sdk.support.test.TestCase;

public class WhenZipping extends TestCase {

  private final ZipAssembler zip = new DefaultZipAssembler();
  private final ByteArrayInputOutputStream output = new ByteArrayInputOutputStream();

  @Test
  public void shouldAddEntries() throws IOException {
    String name1 = someName();
    byte[] content1 = randomBytes();
    String name2 = someName();
    byte[] content2 = randomBytes();
    HashAssembler hashAssembler = new NoHashAssembler();

    zip.begin(output);
    zip.addEntry(name1, new ByteArrayInputStream(content1), hashAssembler);
    zip.addEntry(name2, new ByteArrayInputStream(content2), hashAssembler);
    zip.close();

    try (ZipInputStream unzip = new ZipInputStream(output.getInputStream())) {
      ZipEntry entry = unzip.getNextEntry();
      assertNotNull("Missing entry #1", entry);
      assertEquals("Zip entry #1", name1, entry.getName());
      assertArrayEquals("Zip content #1", content1, IOUtils.toByteArray(unzip));

      unzip.closeEntry();
      entry = unzip.getNextEntry();
      assertNotNull("Missing entry #2", entry);
      assertEquals("Zip entry #2", name2, entry.getName());
      assertArrayEquals("Zip content #2", content2, IOUtils.toByteArray(unzip));

      unzip.closeEntry();
      entry = unzip.getNextEntry();
      assertNull("Additional zip entries", entry);
    }
  }

  private String someName() {
    return randomString(32);
  }

  @Test
  public void shouldCalculateHashes() throws IOException {
    HashAssembler hashAssembler = mock(HashAssembler.class);
    Collection<EncodedHash> expected = Collections.singletonList(someHash());
    when(hashAssembler.get()).thenReturn(expected);
    byte[] content = randomBytes();
    zip.begin(output);
    Collection<EncodedHash> actual = zip.addEntry(someName(), new ByteArrayInputStream(content), hashAssembler);
    zip.close();

    verify(hashAssembler).initialize();
    ArgumentCaptor<byte[]> argument = ArgumentCaptor.forClass(byte[].class);
    verify(hashAssembler).add(argument.capture(), eq(content.length));
    assertArrayEquals("Content", content, Arrays.copyOf(argument.getValue(), content.length));
    assertEquals("Hash", expected, actual);
  }

  private EncodedHash someHash() {
    return new EncodedHash(someName(), someName(), someName());
  }

}
