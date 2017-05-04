package com.apress.websockets.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	@Autowired
	private SimpMessagingTemplate template;

	@RequestMapping("/send/{topic}")
	public String sender(@PathVariable String topic, @RequestParam String message) {
		StringBuilder builder = new StringBuilder();
		builder.append("[") //
				.append(dateFormatter.format(new Date())) //
				.append("]") //
				.append(message);

		this.template.convertAndSend("/topic/" + topic, builder.toString());
		return "OK-Sent";
	}

}
