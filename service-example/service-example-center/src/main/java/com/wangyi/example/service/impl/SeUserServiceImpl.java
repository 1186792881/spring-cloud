package com.wangyi.example.service.impl;

import com.wangyi.example.entity.SeUser;
import com.wangyi.example.dao.SeUserMapper;
import com.wangyi.example.service.SeUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangyi
 * @since 2020-02-22
 */
@Service
public class SeUserServiceImpl extends ServiceImpl<SeUserMapper, SeUser> implements SeUserService {

}
