package com.xinyunfu.customer.domain.feedback;

import com.xinyunfu.customer.dto.feedback.FeedbackAddDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * 用户反馈内容
 */
@Data
public class CustFeedbackDTO {
    private Long id;
    private Long userNo;            //反馈用户
    private String content;         //反馈内容
    private String contentType;     //反馈类型
    private String contact;         //联系方式
    private String contactType;     //联系方式类型
    private String firstImage;      //第一张图片
    private String secondImage;     //第二张图片
    private String thirdImage;      //第三张图片
    private String fourthImage;     //第四张图片
    private String fifthImage;      //第五张图片
    private String sixthImage;      //第六张图片
    private Timestamp createTime;   //创建时间

    public CustFeedbackDTO(Long userNo, FeedbackAddDTO feedbackAdd){
        this.userNo = userNo;
        this.content = feedbackAdd.getContent();
        this.contentType = feedbackAdd.getContentType();
        this.contact = feedbackAdd.getContact();
        this.contactType = feedbackAdd.getContactType();

        List<String> images = feedbackAdd.getImages();
        if(Objects.nonNull(images) && !images.isEmpty()){
            for (int i = 0; i < images.size(); i++) {
                if(i==0) firstImage = images.get(i);
                if(i==1) secondImage = images.get(i);
                if(i==2) thirdImage = images.get(i);
                if(i==3) fourthImage = images.get(i);
                if(i==4) fifthImage = images.get(i);
                if(i==5) sixthImage = images.get(i);
            }
        }
    }
}
