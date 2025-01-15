package com.example;

import com.example.commands.BarCommand;
import com.example.commands.FooCommand;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.extern.slf4j.Slf4j;

@Controller("/foo")
@Slf4j
public class FooController {

    @Post
    public String foo(@Body FooCommand fooCommand) {
        log.info("foo command: {}", fooCommand);
        return "ok";
    }

    @Post("/bar")
    public String bar(@Body BarCommand barCommand) {
        log.info("bar command: {}", barCommand);
        return "ok";
    }
}
