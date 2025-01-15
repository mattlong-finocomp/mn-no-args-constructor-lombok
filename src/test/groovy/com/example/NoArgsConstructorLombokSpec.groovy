package com.example

import io.micronaut.core.util.SupplierUtil
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification
import jakarta.inject.Inject

import java.util.function.Supplier


@MicronautTest
class NoArgsConstructorLombokSpec extends Specification {

    @Inject
    EmbeddedServer server

    Supplier<HttpClient> httpClient = SupplierUtil.memoizedNonEmpty { server.getApplicationContext().createBean(HttpClient.class, server.getURL())}

    void 'test it works'() {
        expect:
        server.running
    }

    void "can deserialize into lombok command"() {
        given:
        FooRequest fooRequest = new FooRequest(foo: "A", bar: "B", baz: "C")

        when:
        httpClient.get().toBlocking().retrieve(HttpRequest.POST("/foo", fooRequest))

        then:
        noExceptionThrown()
    }

    void "builder with noArgsConstructor cant deserialize into lombok command"() {
        given:
        FooRequest fooRequest = new FooRequest(foo: "A", bar: "B", baz: "C")

        when:
        httpClient.get().toBlocking().retrieve(HttpRequest.POST("/foo/bar", fooRequest))

        then:
        // in 4.7.3 no exception is thrown and it is deserialized as expected
        HttpClientResponseException ex = thrown()
        println(ex.message)
        println(ex.response.body())
        ex == null // just to draw attention to this test
    }

    @Serdeable
    static class FooRequest {
        String foo
        String bar
        String baz
    }
}
