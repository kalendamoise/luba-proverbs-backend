package com.focusandcode.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    /**
     * Versioning using url mapping
     */
    @GetMapping("v1/person")
    public PersonV1 personV1() {
        return new PersonV1("Moise Kabukala");
    }
    @GetMapping("v2/person")
    public PersonV2 personV2() {
        return new PersonV2(new Name("Moise", "Kabukala"));
    }

    /**
     * Versioning using url variable
     */
    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 paramV1() {
        return new PersonV1("Moise Kabukala");
    }

    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 paramV2() {
        return new PersonV2(new Name("Moise", "Kabukala"));
    }


    /**
     * Versioning using header variable
     */
    @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 headerV1() {
        return new PersonV1("Moise Kabukala");
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 headerV2() {
        return new PersonV2(new Name("Moise", "Kabukala"));
    }


    /**
     * Versioning using mime type variable
     */
    @GetMapping(value = "/person/produces", produces = "application/kalenda.app-v1+json")
    public PersonV1 producesV1() {
        return new PersonV1("Moise Kabukala");
    }

    @GetMapping(value = "/person/produces", produces = "application/kalenda.app-v2+json")
    public PersonV2 producesV2() {
        return new PersonV2(new Name("Moise", "Kabukala"));
    }
}
