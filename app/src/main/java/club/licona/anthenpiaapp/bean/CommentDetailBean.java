package club.licona.anthenpiaapp.bean;

import java.util.Date;
import java.util.List;

public class CommentDetailBean {
    private int id;
    private String username;
    private String headdir;
    private Date createDate;
    private String content;
    private List<ReplyDetailBean> replyDetailBeanList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeaddir() {
        return headdir;
    }

    public void setHeaddir(String headdir) {
        this.headdir = headdir;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ReplyDetailBean> getReplyDetailBeanList() {
        return replyDetailBeanList;
    }

    public void setReplyDetailBeanList(List<ReplyDetailBean> replyDetailBeanList) {
        this.replyDetailBeanList = replyDetailBeanList;
    }

    @Override
    public String toString() {
        return "CommentDetailBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", headdir='" + headdir + '\'' +
                ", createDate=" + createDate +
                ", content='" + content + '\'' +
                ", replyDetailBeanList=" + replyDetailBeanList +
                '}';
    }
}
