package com.ll.k8s.domain.home.home.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
  public class HomeController {
    @Value("${custom.jwt.secretKey}")
    private String jwtSecretKey;

    @GetMapping("/")
    @ResponseBody
    public String showMain() throws UnknownHostException {
      InetAddress localHost = InetAddress.getLocalHost();
      // getHostName 메소드를 호출하여 호스트 이름을 얻습니다.
      String hostname = localHost.getHostName();
      return "home, hostname : " + hostname;
    }

    @GetMapping("/jwtSecretKey")
    @ResponseBody
    public String showJwtSecretKey() {
      return jwtSecretKey;
    }
  }

