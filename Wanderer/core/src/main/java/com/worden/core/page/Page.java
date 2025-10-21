package com.worden.core.page;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder(setterPrefix = "of")
public class Page {
  private int pageId;
  private int pageNumber;
  private String content;
}
