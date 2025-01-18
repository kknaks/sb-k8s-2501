package com.ll.k8s.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

  @Bean
  public HttpSessionIdResolver httpSessionIdResolver() {
    return new IpBasedSessionIdResolver();
  }

  static class IpBasedSessionIdResolver implements HttpSessionIdResolver {
    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
      String clientIp = extractIp(request);
      return Collections.singletonList(clientIp);
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
      // IP 기반이라 따로 처리 불필요
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {

    }

    private String extractIp(HttpServletRequest request) {
      String ip = request.getHeader("X-Forwarded-For");
      if (ip == null || ip.isEmpty()) {
        ip = request.getRemoteAddr();
      }
      return ip;
    }
  }
}
