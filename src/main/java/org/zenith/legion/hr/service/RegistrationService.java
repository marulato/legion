package org.zenith.legion.hr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.docgen.DocumentConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.*;
import org.zenith.legion.general.entity.Document;
import org.zenith.legion.general.service.DocumentService;
import org.zenith.legion.general.service.FileNetService;
import org.zenith.legion.hr.dao.StaffProfileDAO;
import org.zenith.legion.hr.dto.EmployeeRegistrationDto;
import org.zenith.legion.hr.entity.EmployeeDemographic;
import org.zenith.legion.hr.entity.EmployeeRegistration;
import org.zenith.legion.hr.entity.Position;
import org.zenith.legion.hr.entity.Staff;
import org.zenith.legion.sysadmin.dao.SequenceDAO;
import org.zenith.legion.sysadmin.entity.Sequence;
import org.zenith.legion.sysadmin.entity.UserAccount;
import org.zenith.legion.sysadmin.service.UserAccountService;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Service
public class RegistrationService {

    private final DocumentService documentService;
    private final FileNetService fileNetService;
    private final StaffProfileDAO staffProfileDAO;
    private final UserAccountService accountService;

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    public RegistrationService(DocumentService documentService, FileNetService fileNetService,
                               StaffProfileDAO staffProfileDAO, UserAccountService accountService) {
        this.documentService = documentService;
        this.fileNetService = fileNetService;
        this.staffProfileDAO = staffProfileDAO;
        this.accountService = accountService;
    }
    @Transactional
    public Staff registerEmployee(EmployeeRegistrationDto dto, HttpServletRequest request) throws Exception {
        if (dto != null) {
            Staff staff = BeanUtils.mapFromDto(dto, Staff.class);
            EmployeeDemographic demographic = BeanUtils.mapFromDto(dto, EmployeeDemographic.class);
            EmployeeRegistration registration = BeanUtils.mapFromDto(dto, EmployeeRegistration.class);
            staff.setJoinedDate(registration.getActualEntryDate());
            staff.setStaffNo(MiscGenerator.getNextStaffId(staff.getJoinedDate(), staff.getDepartmentId()));
            staff.setStatus(AppConsts.STAFF_STATUS_IN_SERVICE);
            long entryNo = MiscGenerator.getNextSequenceValue("ENTRY_NO");
            staff.setEntryNo((int) entryNo);

            Long resumeDocId = saveRegDocument(request, "resumeDoc", DocumentConsts.DOC_TYPE_REG_RESUME);
            Long merDocId = saveRegDocument(request, "merDoc", DocumentConsts.DOC_TYPE_REG_MER);
            Long offerDocId = saveRegDocument(request, "offerDoc", DocumentConsts.DOC_TYPE_REG_OFFER);
            Long graduationDocId = saveRegDocument(request, "graduationDoc", DocumentConsts.DOC_TYPE_REG_GRADUATION);
            Long diplomaDocId = saveRegDocument(request, "diplomaDoc", DocumentConsts.DOC_TYPE_REG_DIPLOMA);
            Long contractDoc = saveRegDocument(request, "contractDoc", DocumentConsts.DOC_TYPE_REG_CONTRACT);
            registration.setResumeCmDocumentId(resumeDocId);
            registration.setMerCmDocumentId(merDocId);
            registration.setOfferCmDocumentId(offerDocId);
            registration.setGraduationCmDocumentId(graduationDocId);
            registration.setDiplomaCmDocumentId(diplomaDocId);
            registration.setContractCmDocumentId(contractDoc);

            Long staffId = createEmployee(staff);
            demographic.setStaffId(staffId);
            registration.setStaffId(staffId);
            SQLExecutor.save(demographic);
            SQLExecutor.save(registration);

            return staff;
        }
        return null;
    }

    public void createUserAccount(Staff staff) {
        if (staff != null) {
            UserAccount userAccount = new UserAccount();
            userAccount.setStaffId(staff.getId());
            userAccount.setStaffNo(staff.getStaffNo());
            userAccount.setName(staff.getName());
            userAccount.setNickname(staff.getNickname());
            userAccount.setEmail(staff.getEmailAddress());
            userAccount.setPhoneNo(staff.getPhoneNo());
            Position position = MasterCodeUtils.getPosition(staff.getPositionId());
            if (position != null) {
                accountService.createDefaultUser(userAccount);
                accountService.assignRoles(userAccount, Arrays.asList(MasterCodeUtils.getUserRole(position.getRoleId())));
            }

        }
    }

    public Long saveUploadedFile(MultipartFile file) throws Exception {
        if (file != null) {
            try (InputStream inputStream = file.getInputStream()) {
                return fileNetService.saveFileNetDB(file.getOriginalFilename(), inputStream.readAllBytes());
            } catch (Exception e) {
                log.error("save file to file net encountered an exception", e);
                throw e;
            }
        }
        return 0L;
    }

    public Long saveRegDocument(HttpServletRequest request, String fileName, String docType) throws Exception {
        if (request != null && StringUtils.isNotBlank(fileName)
                && request instanceof StandardMultipartHttpServletRequest) {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            MultipartFile file = req.getFile(fileName);
            Long fileNetId =  saveUploadedFile(file);
            if (fileNetId > 0) {
                Document document = new Document();
                document.setFileName(file.getOriginalFilename());
                document.setCategory(DocumentConsts.DOC_CATEGORY_REGISTRATION);
                document.setDocType(docType);
                document.setIsUploaded(AppConsts.YES);
                document.setUploadedFor(DocumentConsts.DOC_UPLOADED_FOR_NEW_EMP);
                document.setUploadedAt(new Date());
                document.setUploadedBy(AppContext.getFromWebThread().getLoginId());
                document.setStatus(DocumentConsts.DOC_STATUS_ACTIVE);
                document.setVersion(0);
                document.setFileNetId(fileNetId);
                return documentService.saveDocument(document);
            }
        }
        return 0L;
    }

    public Long createEmployee(Staff staff) {
        if (staff != null) {
            staff.createAuditValues(AppContext.getFromWebThread());
            staffProfileDAO.createEmployee(staff);
            return staff.getId();
        }
        return 0L;
    }

}
