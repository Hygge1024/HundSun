package com.example.hundsun.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hundsun.domain.Labels;

import java.util.List;

public interface LabelService extends IService<Labels> {
    List<Labels> getAllByP_id(int p_id);
}
