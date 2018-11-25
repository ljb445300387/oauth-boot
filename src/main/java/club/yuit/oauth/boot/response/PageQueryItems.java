package club.yuit.oauth.boot.response;

import java.util.List;

import lombok.Data;

/**
 * @author yuit
 * @create Time 2018/8/6 15:56
 * @description
 * @modify by
 * @modify time
 **/
@Data
public class PageQueryItems {

    private Integer currentPage;
    private Integer pageSize;
    private Long count;
    private List<?> items;

    public PageQueryItems(Integer currentPage, Integer pageSize, Long count, List<?> items) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.count = count;
        this.items = items;
    }

    public PageQueryItems() {
    }
}
