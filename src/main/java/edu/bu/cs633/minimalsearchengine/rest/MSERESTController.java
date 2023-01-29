package edu.bu.cs633.minimalsearchengine.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MSERESTController {

    @PostMapping(value = "/admin/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> adminLogin() {

        // TBI

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/admin/{adminUsername}/crawl")
    public ResponseEntity<Void> crawlPages(@NotEmpty @NotBlank @NotNull @PathVariable String adminUsername,
                                           @NotEmpty @NotBlank @NotNull @RequestParam(name = "url") String url) {

        // TBI

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/admin/{adminUsername}/indexingHistories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getAdminsIndexingHistories(@NotEmpty @NotBlank @NotNull @PathVariable String adminUsername) {

        // TBI

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> getAllPagesByQuery(@NotEmpty @NotBlank @NotNull @RequestParam(name = "query") String query) {

        // TBI

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}