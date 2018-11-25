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
public class PageAndSortResponse extends BaseResponse {

	private Integer currentPage;
	private Integer pageSize;
	private long count;
	List<?> items;

	protected PageAndSortResponse() {
	}

}
