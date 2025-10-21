package com.worden.common.book;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder(setterPrefix = "of")
public class BookConfig {

  String subScriptionId;
  
}
