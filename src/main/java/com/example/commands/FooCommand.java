package com.example.commands;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Introspected
@Data
@Builder
@AllArgsConstructor
public class FooCommand {

    private String foo;

    private String bar;

    private String baz;
}
