package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.RegisterService;

/**
 * <p>
 * Description:用户校验
 * </p>
 * 
 * @author: Li Yinqiang
 * @date 2017年10月20日 下午9:27:53
 */

@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper userMapper;

	@Override
	public TaotaoResult checkData(String param, int type) {
		TbUserExample example = new TbUserExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		// 根据type来确定查询条件
		// 可选参数1、2、3分别代表username、phone、email
		switch (type) {
		case 1:
			criteria.andUsernameEqualTo(param);
			break;
		case 2:
			criteria.andPhoneEqualTo(param);
			break;
		case 3:
			criteria.andEmailEqualTo(param);
			break;
		default:
			break;
		}
		List<TbUser> list = userMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(false);
		}
		return TaotaoResult.ok(true);
	}

	// 用户注册的方法
	@Override
	public TaotaoResult register(TbUser tbUser) {
		// 校验用户名 密码是否为空
		if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())) {
			return TaotaoResult.build(400, "账号密码不能为空");
		}
		// 校验用户名 是否存在\
		TaotaoResult result = checkData(tbUser.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return TaotaoResult.build(400, "用户名已经存在");
		}
		// 校验手机号
		if (tbUser.getPhone() != null) {
			result = checkData(tbUser.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "该手机号已经被注册");
			}
		}
		// 校验邮箱
		if (tbUser.getEmail() != null) {
			result = checkData(tbUser.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "该邮箱已经被注册或邮箱未填写");
			}
		}
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		//对密码进行md5加密
		tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
		userMapper.insert(tbUser);
		return TaotaoResult.ok();
	}

}
