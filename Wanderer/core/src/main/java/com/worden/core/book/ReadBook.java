package com.worden.core.book;

import java.util.List;
import com.worden.core.page.Page;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder(setterPrefix = "of")
public class ReadBook {
  private Book book;
  private List<Page> pages;
}
