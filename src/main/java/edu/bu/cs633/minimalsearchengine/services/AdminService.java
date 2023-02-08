package edu.bu.cs633.minimalsearchengine.services;

import edu.bu.cs633.minimalsearchengine.asyncjob.AsyncJobService;
import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.*;
import edu.bu.cs633.minimalsearchengine.models.LoginDTO;
import edu.bu.cs633.minimalsearchengine.models.dao.Admin;
import edu.bu.cs633.minimalsearchengine.models.dao.Page;
import edu.bu.cs633.minimalsearchengine.repositories.AdminRepository;
import edu.bu.cs633.minimalsearchengine.utils.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final PageService pageService;
    private final AdminRepository adminRepository;
    private final AsyncJobService asyncJobService;

    @Autowired
    public AdminService(@Lazy AsyncJobService asyncJobService, final AdminRepository adminRepository, final PageService pageService) {
        this.adminRepository = adminRepository;
        this.pageService = pageService;
        this.asyncJobService = asyncJobService;
    }

    public Admin getAdminByUsername(final LoginDTO loginDTO) {

        Admin admin = adminRepository.findByUsernameIgnoreCase(loginDTO.getUsername());

        if (admin == null) {
            throw new UserNameNotFoundException("username: " + loginDTO.getUsername() + " does not exists.");
        }

        if (!admin.getPassword().trim().equals(loginDTO.getPassword().trim())) {
            throw new PasswordDidNotMatchException("password did not match.");
        }

        return admin;
    }

    public Set<Admin> getAllAdminIndexingHistories(final String adminUsername) {

        Admin admin = adminRepository.findByUsernameIgnoreCase(adminUsername);

        if (admin == null || !admin.getUsername().trim().equalsIgnoreCase(adminUsername.trim())) {
            throw new UserNameNotFoundException("Given admin username is not found in the database.");
        }

        Set<Admin> adminsHistories = adminRepository
                .findAll()
                .stream()
                .filter(Objects::nonNull)
                .filter(adminWOIndexing -> adminWOIndexing.getIndexingHistories() != null)
                .filter(adminWOIndexing -> adminWOIndexing.getIndexingHistories().size() != 0)
                .collect(Collectors.toSet());

        if (adminsHistories.size() == 0) {
            throw new NotFoundException("No indexing histories found.");
        }

        return adminsHistories;
    }

    public void crawlPages(final String adminUsername, final String url) {

        Admin admin = adminRepository.findByUsernameIgnoreCase(adminUsername);

        if (admin == null) {
            throw new UserNameNotFoundException("Unable to lookup admin by username=" + adminUsername);
        }

        if (url ==  null || url.trim().length() == 0) {
            throw new InvalidURLException("Given URL is invalid");
        }

        Set<Page> pages = pageService.getMatchingURLsIfAny(url);

        if (pages != null && pages.size() >= 1) {
            throw new ExistingIndexedURLException("url="+url+ " has already been indexed(added). Please try a different URL.");
        }

        try {
            Utilities.isValidURL(url);
        }
        catch(IOException ioException) {
            throw new InvalidURLException(ioException.getMessage());
        }

        asyncJobService.crawl(admin, url);
    }

}
