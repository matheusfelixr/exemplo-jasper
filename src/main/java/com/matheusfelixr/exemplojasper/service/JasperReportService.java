package com.matheusfelixr.exemplojasper.service;

import com.matheusfelixr.exemplojasper.domain.enums.ReportType;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.ValidationException;
import java.io.File;
import java.nio.file.Files;
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
            JasperExportManager.exportReportToHtmlFile(jasperPrint, "reports/"+reportName+".html");
            File returnFile = new File("reports/"+reportName+".html");
            if(!returnFile.exists()){
                throw new ValidationException("Não foi encontrado arquivo html para retornar.");
            }
            return Files.readAllBytes(returnFile.toPath());
        }
        if(reportType == ReportType.xml) {
            JasperExportManager.exportReportToXmlFile(jasperPrint, "reports/"+reportName+".xml", true);
            File returnFile = new File("reports/"+reportName+".xml");
            if(!returnFile.exists()){
                throw new ValidationException("Não foi encontrado arquivo xml para retornar.");
            }
            return Files.readAllBytes(returnFile.toPath());
        }


            return null;
    }
}
