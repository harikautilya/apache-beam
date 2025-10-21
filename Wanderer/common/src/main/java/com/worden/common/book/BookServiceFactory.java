package com.worden.common.book;

import java.io.IOException;
import com.worden.common.book.pubsub.PubsubDao;;

public class BookServiceFactory {

  public static BookService create(BookConfig config) {
    try {
      return new BookService(new PubsubDao(config.getSubScriptionId()));
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Something went wrong here");
    }
  }
}
