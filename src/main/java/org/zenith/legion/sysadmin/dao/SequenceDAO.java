package org.zenith.legion.sysadmin.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zenith.legion.sysadmin.entity.Sequence;

@Mapper
public interface SequenceDAO {

    Sequence getSequence(String name);

    void increase(Sequence sequence);
}
