package com.matheusfelixr.exemplojasper.service;

import com.matheusfelixr.exemplojasper.domain.enums.ReportType;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportService {

    public byte[] generateReport(List<?> listDataSource, String reportName, ReportType reportType) throws Exception{
        File file = ResourceUtils.getFile("classpath:".concat(reportName));
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDataSource);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if(reportType == ReportType.PDF) {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
        if(reportType == ReportType.HTML) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "teste.html");
            return null
        }

        return null;
    }
}
