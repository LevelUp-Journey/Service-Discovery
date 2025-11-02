package com.levelupjourney.servicediscovery;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication implements CommandLineRunner {

	@Value("${server.port:8761}")
	private String serverPort;

	@Value("${server.servlet.context-path:/}")
	private String contextPath;

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoveryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String base = "http://localhost:" + serverPort;
		String path = (contextPath == null || contextPath.isBlank()) ? "/" : contextPath;
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		String url = base + ("/".equals(path) ? "/" : path);

		// Print to console
		System.out.println("Eureka UI is available at: " + url);

		// Try to open default browser (may fail in headless environments)
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Throwable t) {
			System.out.println("Could not open browser automatically: " + t.getMessage());
		}
	}

}
