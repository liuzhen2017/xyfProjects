package com.ruoyi.domain;

/**
 * @author XRZ
 * @date 2019/8/1
 * @Description :
 */

public class GroupNoDTO {
    private String groupNo;
    private String groupNoDesc;

    public GroupNoDTO(String groupNo, String groupNoDesc) {
        this.groupNo = groupNo;
        this.groupNoDesc = groupNoDesc;
    }

    public GroupNoDTO() {
    }

    public String getGroupNoDesc() {
        return groupNoDesc;
    }

    public void setGroupNoDesc(String groupNoDesc) {
        this.groupNoDesc = groupNoDesc;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }
}
