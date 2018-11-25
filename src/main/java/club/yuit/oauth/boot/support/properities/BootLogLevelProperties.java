package club.yuit.oauth.boot.support.properities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther yuit
 * @create 2018/10/19 16:30
 * @description
 * @modify
 */
@Getter
@Setter
public class BootLogLevelProperties implements Serializable {
	private static final long serialVersionUID = 1L;
	private String level = "INFO";
}
