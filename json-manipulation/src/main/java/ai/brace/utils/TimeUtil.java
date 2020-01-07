package ai.brace.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

  public static String epochToIso(long epochtime){
    ZoneOffset offset = ZoneOffset.UTC;
    LocalDateTime ldt = LocalDateTime.ofEpochSecond(epochtime, 0, offset);
    return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000'Z'").format(ldt);
  }
}
