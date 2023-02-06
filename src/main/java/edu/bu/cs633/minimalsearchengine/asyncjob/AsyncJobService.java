package edu.bu.cs633.minimalsearchengine.asyncjob;

import edu.bu.cs633.minimalsearchengine.models.dao.Admin;

public interface AsyncJobService {

    public void crawl(final Admin admin, final String url);

}
