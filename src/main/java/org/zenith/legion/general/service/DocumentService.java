package org.zenith.legion.general.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.docgen.DocumentConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.general.dao.DocumentDAO;
import org.zenith.legion.general.entity.Document;

import java.util.Date;

@Service
public class DocumentService {

    private final DocumentDAO documentDAO;

    @Autowired
    public DocumentService(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    public Long saveDocument(Document document) {
        if (document != null) {
            Document existsDoc = documentDAO.getDocumentById(document.getCmDocumentId());
            if (existsDoc != null) {
                SQLExecutor.update(document);
                return document.getCmDocumentId();
            } else {
                document.createAuditValues(AppContext.getFromWebThread());
                return documentDAO.create(document);
            }
        }
        return 0L;
    }

    public void updateDocument(Document document, boolean increaseVersion) {
        if (document != null) {
            Document existsDoc = documentDAO.getDocumentById(document.getCmDocumentId());
            if (existsDoc != null && !DocumentConsts.DOC_STATUS_DELETED.equals(existsDoc.getStatus())) {
                if (existsDoc.getVersion().equals(document.getVersion()) && increaseVersion) {
                    document.setVersion(document.getVersion() + 1);
                }
                SQLExecutor.update(document);
            }
        }
    }

    public void updateReferenceId(Long docId, Long refId) {
        Document existsDoc = documentDAO.getDocumentById(docId);
        if (existsDoc != null) {
            existsDoc.setReferenceId(refId);
            SQLExecutor.update(existsDoc);
        }
    }

    public void deleteDocument(Long documentId, String reason) {
        if (documentId > 0) {
            Document existsDoc = documentDAO.getDocumentById(documentId);
            if (existsDoc != null && !DocumentConsts.DOC_STATUS_DELETED.equals(existsDoc.getStatus())) {
                existsDoc.setIsDeleted(AppConsts.YES);
                existsDoc.setDeletedFor(reason);
                existsDoc.setDeletedAt(new Date());
                existsDoc.setDeletedBy(AppContext.getFromWebThread().getLoginId());
                existsDoc.setStatus(DocumentConsts.DOC_STATUS_DELETED);
                SQLExecutor.update(existsDoc);
            }
        }
    }

    public void voidDocument(Long documentId, String reason) {
        if (documentId > 0) {
            Document existsDoc = documentDAO.getDocumentById(documentId);
            if (existsDoc != null && !DocumentConsts.DOC_STATUS_DELETED.equals(existsDoc.getStatus())) {
                existsDoc.setIsVoid(AppConsts.YES);
                existsDoc.setVoidedFor(reason);
                existsDoc.setVoidedAt(new Date());
                existsDoc.setVoidedBy(AppContext.getFromWebThread().getLoginId());
                existsDoc.setStatus(DocumentConsts.DOC_STATUS_VOIDED);
                SQLExecutor.update(existsDoc);
            }
        }
    }

    public void reActivateDocument(Long documentId, String reason) {
        if (documentId > 0) {
            Document existsDoc = documentDAO.getDocumentById(documentId);
            if (existsDoc != null && DocumentConsts.DOC_STATUS_VOIDED.equals(existsDoc.getStatus())) {
                existsDoc.setIsVoid(AppConsts.NO);
                existsDoc.setStatus(DocumentConsts.DOC_STATUS_ACTIVE);
                SQLExecutor.update(existsDoc);
            }
        }
    }

    public void recoverDocument(Long documentId, String reason) {
        if (documentId > 0) {
            Document existsDoc = documentDAO.getDocumentById(documentId);
            if (existsDoc != null && DocumentConsts.DOC_STATUS_DELETED.equals(existsDoc.getStatus())) {
                existsDoc.setIsDeleted(AppConsts.NO);
                existsDoc.setStatus(AppConsts.ACCOUNT_STATUS_ACTIVE);
                SQLExecutor.update(existsDoc);
            }
        }
    }

}
