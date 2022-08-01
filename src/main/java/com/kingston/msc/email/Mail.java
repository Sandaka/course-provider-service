package com.kingston.msc.email;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 7/26/22
 */
@Getter
@Setter
@Data
@ToString
public class Mail {

    private String from;
    private String mailTo;
    private String subject;
    private List<Object> attachments;
    private Map<String, Object> props;

}
