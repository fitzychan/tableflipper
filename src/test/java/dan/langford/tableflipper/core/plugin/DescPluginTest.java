package dan.langford.tableflipper.core.plugin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dan.langford.tableflipper.core.TomService;
import java.io.StringReader;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DescPluginTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Test
  public void testExpressions() {

    TomService mockTom = new TomService();
    mockTom.load(new StringReader("descriptions:\n  one: this is one\n  two: this is two"));

    TomPlugin p = new DescPlugin(mockTom);

    assertEquals("this is one", p.resolve("one").orElseThrow());
    assertEquals("this is two", p.resolve("two").orElseThrow());
    assertThrows(NoSuchElementException.class, () -> p.resolve("three").orElseThrow());
  }
}
