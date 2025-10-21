package com.worden.common.book;

import java.util.List;
import com.worden.common.book.dao.BookDao;
import com.worden.core.book.Book;

public class BookService {

  private final BookDao pubsubBookDao;

  /**
   * Construcutor for the book service.
   * 
   * @param pubsubBookDao dao that uses pub sub as source.
   */
  public BookService(BookDao pubsubBookDao) {
    this.pubsubBookDao = pubsubBookDao;
  }


  public List<Book> getBooks() {
    return pubsubBookDao.getBooks();
  }

}
