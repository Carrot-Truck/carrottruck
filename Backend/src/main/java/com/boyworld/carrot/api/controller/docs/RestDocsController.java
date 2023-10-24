package com.boyworld.carrot.api.controller.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * REST Docs Controller
 *
 * @author 최영환
 */
@Controller
public class RestDocsController {

    @GetMapping("/docs")
    public String restDocs() {
        return "index";
    }
}
