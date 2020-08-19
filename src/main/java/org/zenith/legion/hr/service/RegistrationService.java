package org.zenith.legion.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.BeanUtils;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.common.utils.MiscGenerator;
import org.zenith.legion.common.utils.SpringUtils;
import org.zenith.legion.hr.dto.EmployeeRegistrationDto;
import org.zenith.legion.hr.entity.EmployeeDemographic;
import org.zenith.legion.hr.entity.EmployeeRegistration;
import org.zenith.legion.hr.entity.Staff;
import org.zenith.legion.sysadmin.dao.SequenceDAO;
import org.zenith.legion.sysadmin.entity.Sequence;

@Service
public class RegistrationService {

    private SequenceDAO sequenceDAO;

    @Autowired
    public RegistrationService(SequenceDAO sequenceDAO) {
        this.sequenceDAO = sequenceDAO;
    }
    @Transactional
    public void registerEmployee(EmployeeRegistrationDto dto) throws Exception {
        if (dto != null) {
            Staff staff = BeanUtils.mapFromDto(dto, Staff.class);
            EmployeeDemographic demographic = BeanUtils.mapFromDto(dto, EmployeeDemographic.class);
            EmployeeRegistration registration = BeanUtils.mapFromDto(dto, EmployeeRegistration.class);
            staff.setJoinedDate(registration.getActualEntryDate());
            staff.setStaffId(MiscGenerator.getNextStaffId(staff.getJoinedDate(), staff.getDepartmentId()));
            long entryNo = MiscGenerator.getNextSequenceValue("ENTRY_NO");
            staff.setEntryNo((int) entryNo);
            demographic.setStaffId(staff.getStaffId());
            registration.setStaffId(staff.getStaffId());

        }
    }

}
