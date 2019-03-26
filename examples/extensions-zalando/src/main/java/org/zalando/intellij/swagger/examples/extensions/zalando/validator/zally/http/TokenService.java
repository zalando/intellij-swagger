package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally.http;

import com.intellij.openapi.components.ServiceManager;
import org.apache.commons.io.IOUtils;
import org.zalando.intellij.swagger.examples.extensions.zalando.validator.ZallySettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class TokenService {

  public static String getToken() throws Exception {
    final String ztokenPath = ServiceManager.getService(ZallySettings.class).getZtokenPath();

    Process process = createTokenProcess(ztokenPath);

    process.waitFor(10, TimeUnit.SECONDS);

    if (process.exitValue() == 0) {
      return handleSuccess(process);
    } else {
      return handleError(process);
    }
  }

  private static String handleError(final Process process) throws IOException {
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

      throw new TokenException(bufferedReader.readLine());
    } finally {
      IOUtils.closeQuietly(bufferedReader);
    }
  }

  private static String handleSuccess(final Process process) throws IOException {
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      return bufferedReader.readLine();
    } finally {
      IOUtils.closeQuietly(bufferedReader);
    }
  }

  private static Process createTokenProcess(final String ztokenPath) throws Exception {
    if (ztokenPath != null && !ztokenPath.isEmpty()) {
      return createTokenWithZtokenPath(ztokenPath);
    } else {
      return createToken();
    }
  }

  private static Process createToken() throws Exception {
    final ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", "ztoken");
    final String path =
        Optional.ofNullable(pb.environment().get("PATH"))
            .map(p -> p.concat(":/usr/local/bin"))
            .orElse("");

    pb.environment().put("PATH", path);
    pb.environment().put("LANG", "en_US.UTF-8");

    return pb.start();
  }

  private static Process createTokenWithZtokenPath(final String ztokenPath) throws Exception {
    final ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", ztokenPath);

    pb.environment().put("LANG", "en_US.UTF-8");

    return pb.start();
  }
}
