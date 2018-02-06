package com.leap.mars.model.chat;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description :
 */
public class News {

  private String article;// 文章标题
  private String source;// 文章资源引用地址
  private String icon;// 信息图标
  private String detailurl;// 详细地址链接
  private String name;// 菜谱名称
  private String info;// 菜谱提示信息

  public String getArticle() {
    return article;
  }

  public void setArticle(String article) {
    this.article = article;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getDetailurl() {
    return detailurl;
  }

  public void setDetailurl(String detailurl) {
    this.detailurl = detailurl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
