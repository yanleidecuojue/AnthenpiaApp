package club.licona.anthenpiaapp.service;

import club.licona.anthenpiaapp.entity.vo.BlogCreateVO;
import club.licona.anthenpiaapp.entity.vo.BlogListAllVO;
import club.licona.anthenpiaapp.service.api.CreateBlogApi;
import club.licona.anthenpiaapp.service.api.ListAllBlogApi;
import club.licona.anthenpiaapp.util.RetrofitProvider;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class BlogService {
    /**
     * 创建博客
     * <p>
     * 调用服务器创建博客接口
     *
     * @param blogCreateVO 创建博客VO
     * @return 创建博客操作返回json结果
     */
    public Observable<String> createBlog(BlogCreateVO blogCreateVO) {
        return RetrofitProvider.get().create(CreateBlogApi.class)
                .createBlog(blogCreateVO.getToken(), blogCreateVO.getText(),
                        blogCreateVO.getContent())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 列出所有博客
     * <p>
     * 调用服务器列出所有博客接口
     *
     * @param blogListAllVO 列出所有博客VO
     * @return 列出所有博客操作返回json结果
     */
    public Observable<String> listAllBlog(BlogListAllVO blogListAllVO) {
        return RetrofitProvider.get().create(ListAllBlogApi.class)
                .listAllBlog(blogListAllVO.getToken())
                .subscribeOn(Schedulers.io());
    }
}
