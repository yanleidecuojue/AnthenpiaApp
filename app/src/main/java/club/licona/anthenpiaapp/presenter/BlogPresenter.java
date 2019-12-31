package club.licona.anthenpiaapp.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import club.licona.anthenpiaapp.entity.vo.BlogCreateVO;
import club.licona.anthenpiaapp.entity.vo.BlogInfoVO;
import club.licona.anthenpiaapp.entity.vo.BlogListAllVO;
import club.licona.anthenpiaapp.entity.vo.ResponseVO;
import club.licona.anthenpiaapp.service.BlogService;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author licona
 */
public class BlogPresenter {

    private BlogService blogService;

    public BlogPresenter() {
        this.blogService = new BlogService();
    }

    /**
     * 创建博客操作
     *
     * @param createBlogObservable 创建博客事件Observable
     * @return 创建博客操作结果Observable
     */
    public Observable<ResponseVO> createBlog(Observable<BlogCreateVO> createBlogObservable) {
        return createBlogObservable.observeOn(Schedulers.io())
                .flatMap(blogService::createBlog)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    return new ResponseVO<>(code, msg);
                });

    }

    /**
     * 列出所有博客操作
     *
     * @param  listAllBlogObservable 列出所有博客事件Observable
     * @return 列出所有博客操作结果Observable
     */
    public Observable<ResponseVO<List<BlogInfoVO>>> listAllBlog(Observable<BlogListAllVO> listAllBlogObservable) {
        return listAllBlogObservable.observeOn(Schedulers.io())
                .flatMap(blogService::listAllBlog)
                .observeOn(Schedulers.computation())
                .map(resultJson -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode head = objectMapper.readTree(resultJson);
                    int code = head.get("code").asInt();
                    String msg = head.get("msg").asText();
                    if (code == 0) {
                        BlogInfoVO[] vo = objectMapper.readValue(head.get("data").toString(), BlogInfoVO[].class);
                        List<BlogInfoVO> blogInfoVOList = Arrays.stream(vo).collect(Collectors.toList());
                        return new ResponseVO<>(code, msg, blogInfoVOList);
                    } else {
                        return new ResponseVO<>(code, msg);
                    }
                });
    }
}