package com.gtzhou.wechat.message.resp;

import java.util.List;

/**
 * ��ͼ����Ϣ
 * @author Administrator
 *
 */
public class NewsMessage extends BaseMessage{
	
	// ͼ����Ϣ����������Ϊ 10 ������   
    private int ArticleCount;  
    // ����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ�� item Ϊ��ͼ   
    private List<Article> Articles;  

    public int getArticleCount() {  
        return ArticleCount;  
    }  

    public void setArticleCount(int articleCount) {  
        ArticleCount = articleCount;  
    }  

    public List<Article> getArticles() {  
        return Articles;  
    }  

    public void setArticles(List<Article> articles) {  
        Articles = articles;  
    }  

}
