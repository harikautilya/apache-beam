package com.worden.core.message;

public abstract class AckMessage<P> {



  public abstract boolean ack();

  public abstract P getAcknowledment();

}
