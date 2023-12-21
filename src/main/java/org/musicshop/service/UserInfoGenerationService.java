package org.musicshop.service;

import org.musicshop.pojo.model.Sales;

import java.util.List;
import java.util.Map;

public interface UserInfoGenerationService {

    Map<Long, List<Long>> generateUserInfo(Sales productInfoToAdd);
}
