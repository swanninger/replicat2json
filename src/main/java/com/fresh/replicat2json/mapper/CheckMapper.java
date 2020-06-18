package com.fresh.replicat2json.mapper;

import com.fresh.replicat2json.domain.Check;
import com.fresh.replicat2json.model.CheckDTO;

public interface CheckMapper {
    CheckDTO CheckToCheckDTO(Check check);
}
