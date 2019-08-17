package ru.kurma.sweater.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kurma.sweater.model.Message;
import ru.kurma.sweater.model.User;
import ru.kurma.sweater.repository.MessageRepository;

import java.util.Map;

@Controller
public class MainController {
    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("/main")
        public String add(
                @AuthenticationPrincipal User user,
                @RequestParam String text,
                @RequestParam String tag, Map<String, Object> model
    ) {
        Message message = new Message(text, tag, user);

        messageRepository.save(message);

        model.put("messages", messageRepository.findAll());

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        if (filter != null && !filter.isEmpty()) {
            model.put("messages", messageRepository.findByTag(filter));
        } else {
            model.put("messages", messageRepository.findAll());
        }
        return "main";
    }
}
