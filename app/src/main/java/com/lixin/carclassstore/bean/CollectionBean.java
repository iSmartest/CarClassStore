package com.lixin.carclassstore.bean;

/**
 * Created by 小火
 * Create time on  2017/3/30
 * My mailbox is 1403241630@qq.com
 */

public class CollectionBean {
    private Long id;
    private String title;
    private String keyWord;
    private Long source;
    private Long cateId;
    private String cateName;
    private Long subCateId;
    private String subCateName;
    private Long reqType;
    private String url;
    private String docUrl;
    private String image;
    private Long viewCount;
    private Long createTime;
    private Long updateTime;
    private String desc;
    private Long readNum;
    private Long collectNum;
    private Long praiseNum;
    private Long isCollected;
    private String author;

    public CollectionBean() {
    }

    public CollectionBean(Long id) {
        this.id = id;
    }

    public CollectionBean(Long id, String title, String keyWord, Long source, Long cateId, String cateName, Long subCateId, String subCateName, Long reqType, String url, String docUrl, String image, Long viewCount, Long createTime, Long updateTime, String desc, Long readNum, Long collectNum, Long praiseNum, Long isCollected, String author) {
        this.id = id;
        this.title = title;
        this.keyWord = keyWord;
        this.source = source;
        this.cateId = cateId;
        this.cateName = cateName;
        this.subCateId = subCateId;
        this.subCateName = subCateName;
        this.reqType = reqType;
        this.url = url;
        this.docUrl = docUrl;
        this.image = image;
        this.viewCount = viewCount;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.desc = desc;
        this.readNum = readNum;
        this.collectNum = collectNum;
        this.praiseNum = praiseNum;
        this.isCollected = isCollected;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Long getSubCateId() {
        return subCateId;
    }

    public void setSubCateId(Long subCateId) {
        this.subCateId = subCateId;
    }

    public String getSubCateName() {
        return subCateName;
    }

    public void setSubCateName(String subCateName) {
        this.subCateName = subCateName;
    }

    public Long getReqType() {
        return reqType;
    }

    public void setReqType(Long reqType) {
        this.reqType = reqType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getReadNum() {
        return readNum;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    public Long getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Long collectNum) {
        this.collectNum = collectNum;
    }

    public Long getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Long praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Long getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(Long isCollected) {
        this.isCollected = isCollected;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
