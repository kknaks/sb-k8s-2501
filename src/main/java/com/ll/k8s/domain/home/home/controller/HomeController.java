package com.ll.k8s.domain.home.home.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Controller
public class HomeController {
  @Value("${custom.jwt.secretKey}")
  private String jwtSecretKey;

  @GetMapping("/")
  @ResponseBody
  public String showMain(HttpSession session, HttpServletRequest request) throws UnknownHostException {
    InetAddress localHost = InetAddress.getLocalHost();
    String hostname = localHost.getHostName();

    String clientIp = request.getRemoteAddr();
    String existingData = (String) session.getAttribute("data");
    String sessionInfo;

    StringBuilder output = new StringBuilder();
    output.append("Pod Hostname: ").append(hostname).append("\n");
    output.append("Client IP: ").append(clientIp).append("\n");
    output.append("Session ID: ").append(session.getId()).append("\n");
    output.append("Existing Session Data: ").append(existingData).append("\n");

    if (existingData == null) {
      String newData = "IP: " + clientIp + ", Time: " + LocalDateTime.now();
      session.setAttribute("data", newData);
      output.append("Created new session data: ").append(newData).append("\n");
      sessionInfo = "New session created for IP: " + clientIp;
    } else {
      output.append("Found existing session data: ").append(existingData).append("\n");
      sessionInfo = "Existing session found: " + existingData;
    }

    output.append("Session Info: ").append(sessionInfo);

    return output.toString();
  }

  @GetMapping("/jwtSecretKey")
  @ResponseBody
  public String showJwtSecretKey() {
    return jwtSecretKey;
  }
}
