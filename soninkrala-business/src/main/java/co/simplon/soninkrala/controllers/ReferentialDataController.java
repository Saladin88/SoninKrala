package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.dtos.TermPolicyVersionDto;
import co.simplon.soninkrala.services.ReferentialDataService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soninkrala/api/v1/referential-data")
public class ReferentialDataController {
    private final ReferentialDataService referentialDataService;

    public ReferentialDataController(ReferentialDataService referentialDataService) {
        this.referentialDataService = referentialDataService;
    }

    @GetMapping("/term-and-policy-versions")
    @ResponseStatus(HttpStatus.OK)
    public TermPolicyVersionDto getTermVersion() {
        return this.referentialDataService.fetchTermVersion();
    }
}
