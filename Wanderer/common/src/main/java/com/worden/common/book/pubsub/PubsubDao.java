package com.worden.common.book.pubsub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.stub.GrpcSubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStub;
import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import com.google.pubsub.v1.AcknowledgeRequest;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PullRequest;
import com.google.pubsub.v1.PullResponse;
import com.worden.common.book.dao.BookDao;
import com.worden.common.message.MessageDao;
import com.worden.core.book.Book;

/**
 * Book dao layer that collects information from pubsub subcription.
 */
public class PubsubDao implements BookDao, MessageDao<String, Book> {

  private final String subscriptionId;
  private final SubscriberStubSettings subscriberStubSettings;
  private final GoogleCredentials credentials;

  public PubsubDao(String subscriptionId) throws IOException {
    this.subscriptionId = subscriptionId;
    this.subscriberStubSettings = SubscriberStubSettings.newBuilder()
        .setTransportChannelProvider(
            SubscriberStubSettings.defaultGrpcTransportProviderBuilder()
                .setMaxInboundMessageSize(20 * 1024).build())
        .build();
    this.credentials = GoogleCredentials.getApplicationDefault();
  }

  /**
   * {@inheritDoc}
   * <p>
   * This method is not implementede as pubsub is not expected to add books.
   * </p>
   */
  @Override
  public boolean addBook(Book book) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addBook'");
  }

  /**
   * {@inheritDoc}
   * <p>
   * Recieve messages and process them.
   * </p>
   */
  @Override
  public List<Book> receiveMessages() {

    List<Book> books = new ArrayList<>();

    try (SubscriberStub subcriber = GrpcSubscriberStub.create(subscriberStubSettings)) {
      String subscriptionName =
          ProjectSubscriptionName.format(credentials.getQuotaProjectId(), subscriptionId);

      PullRequest pullRequest = PullRequest.newBuilder()
          .setMaxMessages(10)
          .setSubscription(subscriptionName)
          .build();

      PullResponse pullResponse = subcriber.pullCallable().call(pullRequest);

      pullResponse.getReceivedMessagesList()
          .stream()
          .map((x) -> MessageMapper.parseMessaage(x))
          .filter(x -> x != null)
          .forEach(books::add);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return books;

  }

  /**
   * {@inheritDoc}
   * <p>
   * Acknowledge messages
   * </p>
   */
  @Override
  public void acknowledgeMessage(List<Book> books) {
    try (SubscriberStub subcriber = GrpcSubscriberStub.create(subscriberStubSettings)) {
      String subscriptionName =
          ProjectSubscriptionName.format(credentials.getQuotaProjectId(), subscriptionId);

      AcknowledgeRequest acknowledgeRequest = AcknowledgeRequest.newBuilder()
          .setSubscription(subscriptionName)
          .addAllAckIds(books.stream().map(Book::getAckId).collect(Collectors.toList()))
          .build();

      subcriber.acknowledgeCallable().call(acknowledgeRequest);

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * {@inheritDoc}
   * <p>
   * Call pubsub dao receiveMessages.
   * </p>
   */
  @Override
  public List<Book> getBooks() {
    return receiveMessages();
  }



}
