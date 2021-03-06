package io.github.rothschil.mail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.github.rothschil.common.constant.Constants;
import io.github.rothschil.base.response.enums.Status;
import io.github.rothschil.common.exception.DrunkardException;
import io.github.rothschil.common.utils.StringUtils;
import io.github.rothschil.base.vo.MailVo;

import java.util.Date;
import java.util.Optional;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/12 - 10:27
 * @since 1.0.0
 */
@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private JavaMailSenderImpl mailSender;

    @Autowired
    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 对外提供发送邮件入口
     *
     * @param mailVo 邮件实体
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/11-10:03
     **/
    public void send(MailVo mailVo) {
        try {
            checkMail(mailVo);
            sendMimeMail(mailVo);
        } catch (Exception e) {
            logger.error("发送邮件失败:", e);
        }
    }

    /**
     * 检查邮件实体是否符合规范，如收件人、主题、内容等
     *
     * @param mailVo 邮件实体
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/11-10:03
     **/
    private void checkMail(MailVo mailVo) {
        if (StringUtils.isEmpty(mailVo.getTo())) {
            throw new DrunkardException(Status.TARGET_IS_EMPTY, "收件人");
        }
        if (StringUtils.isEmpty(mailVo.getSubject())) {
            throw new DrunkardException(Status.TARGET_IS_EMPTY, "邮件主题");
        }
        if (StringUtils.isEmpty(mailVo.getText())) {
            throw new DrunkardException(Status.TARGET_IS_EMPTY, "邮件内容");
        }
    }

    /**
     * 发送邮件
     *
     * @param mailVo 邮件实体
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/11-10:04
     **/
    private void sendMimeMail(MailVo mailVo) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            mailVo.setFrom(getMailSendFrom());
            messageHelper.setFrom(mailVo.getFrom());
            messageHelper.setTo(mailVo.getTo().split(","));
            messageHelper.setSubject(mailVo.getSubject());
            messageHelper.setText(mailVo.getText());
            if (!StringUtils.isEmpty(mailVo.getCc())) {
                messageHelper.setCc(mailVo.getCc().split(","));
            }
            if (!StringUtils.isEmpty(mailVo.getBcc())) {
                messageHelper.setCc(mailVo.getBcc().split(","));
            }
            if (mailVo.getMultipartFiles() != null) {
                for (MultipartFile multipartFile : mailVo.getMultipartFiles()) {
                    Optional<MultipartFile> opt = Optional.ofNullable(multipartFile);
                    messageHelper.addAttachment(opt.get().getOriginalFilename(), multipartFile);
                }
            }
            if (StringUtils.isEmpty(mailVo.getSentDate())) {
                mailVo.setSentDate(new Date());
                messageHelper.setSentDate(mailVo.getSentDate());
            }
            mailSender.send(messageHelper.getMimeMessage());
            mailVo.setStatus(Constants.SUCCESS);
            logger.info("发送邮件成功：{} to {}", mailVo.getFrom(), mailVo.getTo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String getMailSendFrom() {
        return mailSender.getJavaMailProperties().getProperty("from");
    }
}
