package edu.bu.cs633.minimalsearchengine.rest;

import edu.bu.cs633.minimalsearchengine.models.LoginDTO;
import edu.bu.cs633.minimalsearchengine.models.dao.Admin;
import edu.bu.cs633.minimalsearchengine.models.dao.Page;
import edu.bu.cs633.minimalsearchengine.services.AdminService;
import edu.bu.cs633.minimalsearchengine.services.PageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Validated
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MSERESTController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PageService pageService;

    @PostMapping(value = "/admin/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Admin> adminLogin(@Valid @RequestBody LoginDTO loginDTO) {

        return new ResponseEntity<>(adminService.getAdminByUsername(loginDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/admin/{adminUsername}/crawl")
    public ResponseEntity<Void> crawlPages(@NotEmpty @NotBlank @NotNull @PathVariable String adminUsername,
                                           @NotEmpty @NotBlank @NotNull @RequestParam(name = "url") String url) {

        // TBI

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping(value = "/admin/{adminUsername}/indexingHistories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Admin>> getAdminsIndexingHistories(@NotEmpty @NotBlank @NotNull @PathVariable String adminUsername) {

        return new ResponseEntity<>(adminService.getAllAdminIndexingHistories(adminUsername), HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Page>> getAllPagesByQuery(@NotEmpty @RequestParam(name = "query") String query) {

        return new ResponseEntity<>(pageService.getPagesByQuery(query), HttpStatus.OK);
    }

}