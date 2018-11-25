package club.yuit.oauth.boot.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuit
 * @create time  2018/3/30 20:37
 * @description
 * @modify
 * @modify time
 */
@Getter
@Setter
public class ListResponse extends BaseResponse {

    private long count;
    private List<?> items;

    protected ListResponse(){

    }

}
