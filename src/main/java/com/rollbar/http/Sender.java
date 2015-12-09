package com.rollbar.http;

/**
 * Senders can send JSON string payloads to Rollbar.
 */
public interface Sender {
    /**
     * Send the json payload (already serialized as a String) to Rollbar
     * @param jsonPayload the payload to send
     * @return a {@link RollbarResponse} indicating what happened.
     * @throws ConnectionFailedException if the connection failed before receiving a response from Rollbar.
     */
    RollbarResponse send(String jsonPayload) throws ConnectionFailedException;
}
