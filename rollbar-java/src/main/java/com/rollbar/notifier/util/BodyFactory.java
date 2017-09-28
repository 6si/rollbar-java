package com.rollbar.notifier.util;

import com.rollbar.api.payload.data.body.Body;
import com.rollbar.api.payload.data.body.ExceptionInfo;
import com.rollbar.api.payload.data.body.Frame;
import com.rollbar.api.payload.data.body.Trace;
import com.rollbar.api.payload.data.body.TraceChain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Body factory helper to build the proper body depending on the throwable or the description.
 */
public class BodyFactory {

  /**
   * Builds the body for the throwable and description supplied.
   *
   * @param throwable the throwable.
   * @param description the description.
   * @return the body.
   */
  public Body from(Throwable throwable, String description) {
    if (throwable.getCause() == null) {
      return new Body.Builder().bodyContent(trace(throwable, description)).build();
    } else {
      return new Body.Builder().bodyContent(traceChain(throwable, description)).build();
    }
  }

  private static Trace trace(Throwable throwable, String description) {
    return new Trace.Builder()
        .frames(frames(throwable))
        .exception(info(throwable, description))
        .build();
  }

  private static TraceChain traceChain(Throwable throwable, String description) {
    ArrayList<Trace> chain = new ArrayList<>();
    do {
      chain.add(trace(throwable, description));
      description = null;
      throwable = throwable.getCause();
    } while (throwable != null);
    return new TraceChain.Builder()
        .traces(chain)
        .build();
  }

  private static List<Frame> frames(Throwable throwable) {
    StackTraceElement[] elements = throwable.getStackTrace();

    ArrayList<Frame> result = new ArrayList<>();
    for (int i = elements.length - 1; i >= 0; i--) {
      result.add(frame(elements[i]));
    }

    return result;
  }

  private static ExceptionInfo info(Throwable throwable, String description) {
    String className = throwable.getClass().getSimpleName();
    String message = throwable.getMessage();
    return new ExceptionInfo.Builder()
        .className(className)
        .message(message)
        .description(description)
        .build();
  }

  private static Frame frame(StackTraceElement element) {
    String filename = element.getFileName();
    Integer lineNumber = element.getLineNumber();
    String method = element.getMethodName();
    String className = element.getClassName();

    return new Frame.Builder()
        .filename(filename)
        .lineNumber(lineNumber)
        .method(method)
        .className(className)
        .build();
  }
}
