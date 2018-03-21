package com.rollbar.api.payload.data;

import static com.rollbar.test.Factory.person;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class PersonTest {

  @Test
  public void shouldBeEqual() {
    Person person1 = person();
    Person person2 = person();

    assertThat(person2, is(person1));
  }

  @Test
  public void shouldReturnAsJson() {
    Person person = person();

    Map<String, Object> expected = new HashMap<>();
    if (person.getId() != null) {
      expected.put("id", person.getId());
    }
    if (person.getEmail() != null) {
      expected.put("email", person.getEmail());
    }
    if (person.getUsername() != null) {
      expected.put("username", person.getUsername());
    }

    assertThat(person.asJson(), is(expected));
  }

  @Test
  public void shouldAllowMetadata() {
    Person basePerson = person();

    Map<String, Object> metadataMap = new HashMap<>();
    metadataMap.put("a", "b");
    metadataMap.put("num", 42);
    metadataMap.put("id", "should not show up");

    Person person = new Person.Builder(basePerson)
      .metadata(metadataMap)
      .build();

    Map<String, Object> expected = new HashMap<>();
    expected.put("a", "b");
    expected.put("num", 42);
    if (person.getId() != null) {
      expected.put("id", person.getId());
    }
    if (person.getEmail() != null) {
      expected.put("email", person.getEmail());
    }
    if (person.getUsername() != null) {
      expected.put("username", person.getUsername());
    }

    assertThat(person.asJson(), is(expected));
  }
}
