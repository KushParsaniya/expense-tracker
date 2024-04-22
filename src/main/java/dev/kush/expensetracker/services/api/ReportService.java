package dev.kush.expensetracker.services.api;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {
    String generateReport(String email, ) throws FileNotFoundException, JRException;

    String sendReport(String email);
}
