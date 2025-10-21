package com.worden.core.book;

import com.worden.core.message.AckMessage;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder(setterPrefix = "of")
@EqualsAndHashCode(callSuper = true)
public class Book extends AckMessage<String> {

  @Builder.Default
  private String bookUrl = "";

  @Builder.Default
  private Long bookId = -1L;
  
  @Builder.Default
  private String ackId = "";

  @Override
  public boolean ack() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'ack'");
  }

  @Override
  public String getAcknowledment() {
    return ackId;
  }

};
