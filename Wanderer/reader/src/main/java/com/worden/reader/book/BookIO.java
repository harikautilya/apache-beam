package com.worden.reader.book;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import com.worden.common.book.BookService;
import com.worden.core.book.Book;
import com.worden.core.book.ReadBook;
import lombok.Builder;

public class BookIO {


  public static CollectBook collectBook(BookService service) {
    return null;
  }


  public static class BookProvider {

    private final BookService bookService;

    public BookProvider(BookService bookService) {
      this.bookService = bookService;
    }


    public List<ReadBook> getBooks() {
      return bookService.getBooks()
          .stream()
          .map(book -> ReadBook.builder()
              .ofBook(book)
              .ofPages(List.of())
              .build())
          .collect(Collectors.toList());
    }
  }



  public static class CollectBook extends PTransform<PBegin, PCollection<ReadBook>> {

    private final BookProvider bookProvider;

    @Builder(setterPrefix = "of")
    CollectBook(BookProvider bookProvider) {
      this.bookProvider = bookProvider;
    }

    @Override
    public @UnknownKeyFor @NonNull @Initialized PCollection<ReadBook> expand(
        @UnknownKeyFor @NonNull @Initialized PBegin input) {
          return input.apply("Collect 10 books",Create.of(bookProvider.getBooks()) )
    }

  }
}
