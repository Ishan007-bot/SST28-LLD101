package com.example.reports;

/**
 * Viewer now depends on Report interface (not concrete class).
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}