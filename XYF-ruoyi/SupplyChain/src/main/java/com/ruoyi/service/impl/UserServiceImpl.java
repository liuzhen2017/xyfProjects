package com.ruoyi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ruoyi.dto.PushMsgDto;
import com.ruoyi.dto.SendMessageCustomDTO;
import com.ruoyi.feign.InfoCenterFeign;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.UserMapper;
import com.ruoyi.domain.User;
import com.ruoyi.service.IUserService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.utils.ResponseInfo;


/**
 * 用户 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService 
{
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private SysPasswordService passwordService;

	@Autowired
	private InfoCenterFeign infoCenterFeign;

	/**
     * 查询用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
	public User selectUserById(Long userId)
	{
	    return userMapper.selectUserById(userId);
	}
	
	/**
     * 查询用户列表
     * 
     * @param user 用户信息
     * @return 用户集合
     */
	@Override
	public List<User> selectUserList(User user)
	{
	    return userMapper.selectUserList(user);
	}
	
    /**
     * 新增用户
     * 
     * @param msgDto
     * @return 结果
     */
	@Override
	public int insertUser(PushMsgDto msgDto)
	{
		//点击确定时发送消息推送登录密码
		//User user = ShiroUtils.getSysUser();

		//ShiroUtils.getSysUser().getCustNo();
		User user = User.builder()
			.loginName(msgDto.getLoginName()).userName(msgDto.getUserName()).email(msgDto.getEmail())
			.phonenumber(msgDto.getPhonenumber()).sex(msgDto.getSex()).password(msgDto.getPassword())
			.custNo(msgDto.getCustNo()).custType(msgDto.getCustType()).build();

		//密码组成：6位随机数，加盐值，再加密
		int radom = new Random().nextInt(999999);
		String salt = ShiroUtils.randomSalt();
		user.setSalt(salt);
		user.setPassword(passwordService.encryptPassword(user.getLoginName(), String.valueOf(radom), salt));

		//用户信息及加密后的密码入库
		int i = userMapper.insertUser(user);
		//入库成功后发送短信推送消息
		if(i>0){
			String url = "http://dreamy-gyl.xyf823.com/index";
			List<String> phones = new ArrayList<>();
			phones.add(user.getPhonenumber());
			List<Object> data = new ArrayList<>();
			data.add(msgDto.getCustName());
			data.add(url);
			data.add(user.getLoginName());
			data.add(String.valueOf(radom));

			//调用服务推送消息
			//恭喜您:XXX企业名称供应链申请完毕,请访问URL路径登录星云福供应链平台,用户名为XXX,密码XXX
			SendMessageCustomDTO sendMsgDto = new SendMessageCustomDTO();
			sendMsgDto.setTemplateName("010");
			sendMsgDto.setPhone(phones);
			sendMsgDto.setData(data);
			ResponseInfo<Object> response = infoCenterFeign.sendMessage(sendMsgDto);
			if(response.isSuccess()){
				log.info("短信推送成功,phone={}",user.getPhonenumber());
			}else{
				log.info("短信推送失败,phone={}",user.getPhonenumber());
			}
			return i;
		}

	    return 0;
	}
	
	/**
     * 修改用户
     * 
     * @param user 用户信息
     * @return 结果
     */
	@Override
	public int updateUser(User user)
	{
	    return userMapper.updateUser(user);
	}

	/**
     * 删除用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteUserByIds(String ids)
	{
		return userMapper.deleteUserByIds(Convert.toStrArray(ids));
	}
	
}
